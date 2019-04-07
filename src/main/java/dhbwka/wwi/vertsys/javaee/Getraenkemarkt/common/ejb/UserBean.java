package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Spezielle EJB zum Anlegen eines Benutzers und Aktualisierung des Passworts.
 */
@Stateless
public class UserBean {

    @PersistenceContext
    EntityManager em;
    
    @Resource
    EJBContext ctx;

    /**
     * Gibt das Datenbankobjekt des aktuell eingeloggten Benutzers zurück,
     *
     * @return Eingeloggter Benutzer oder null
     */
    public User getCurrentUser() {
        return this.em.find(User.class, this.ctx.getCallerPrincipal().getName());
    }

    
    public void signup(User user) throws UserAlreadyExistsException {
        if (em.find(User.class, user.getUsername()) != null) {
            throw new UserAlreadyExistsException("Der Benutzername $B ist bereits vergeben.".replace("$B", user.getUsername()));
        }
        //hier muss Gruppe vergeben werden
        user.addToGroup("app-user");
        this.em.persist(user);
        this.em.merge(user);
    }

    /**
     * Passwort ändern (ohne zu speichern)
     * @param user
     * @param oldPassword
     * @param newPassword
     * @throws UserBean.InvalidCredentialsException
     */
    @RolesAllowed("app-user")
    public void changePassword(User user, String oldPassword, String newPassword) throws InvalidCredentialsException {
        if (user == null || !user.checkPassword(oldPassword)) {
            throw new InvalidCredentialsException("Benutzername oder Passwort sind falsch.");
        }

        user.setPassword(newPassword);
    }
    
    /**
     * Benutzer aktualisieren
     * @param user Zu aktualisierender Benutzer
     * @return Gespeicherter Benutzer
     */
    @RolesAllowed("app-user")
    public User update(User user) {
        return em.merge(user);
    }

    public List<User> findByUsername(String query) {
        if (query == null || query.trim().isEmpty()) {
            query = "";
        }      
        query = "%" + query + "%";
        return em.createQuery("SELECT u FROM User u"
                + "    WHERE u.username     LIKE :query")
                .setParameter("query", query)
                .getResultList();
    }
    
     // User per Name auslesen und die Standardgruppe hinzufügen. 
    public User findUserForAuth(String userName){
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> from = query.from(User.class);
        query.select(from);
        query.where(cb.and(
        cb.equal(from.get("username"), userName)));
        List<User> result = em.createQuery(query).getResultList(); // getResultList() verhindert Nullpointer
        User user = result != null && result.size() == 1 ? result.get(0) : null;
        if(user!= null){
            user.addToGroup("app-user"); // Defaultgruppe
        }
        
        return  user;
    }
    
    
    // Exception-Handling für bereits angelegte Usernamen oder falsche Credetials
    /**
     * Fehler: Der Benutzername ist bereits vergeben
     */
    public class UserAlreadyExistsException extends Exception {

        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    /**
     * Fehler: Das übergebene Passwort stimmt nicht mit dem des Benutzers
     * überein
     */
    public class InvalidCredentialsException extends Exception {

        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

}
