/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Kunde;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
        user.addToGroup(user.getDisAttribut());
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
    @RolesAllowed({"app-user","Kunde"})
    public void changePassword(User user, String oldPassword, String newPassword) throws InvalidCredentialsException {
        if (user == null || !user.checkPassword(oldPassword)) {
            throw new InvalidCredentialsException("Benutzername oder Passwort sind falsch.");
        }

        user.setPassword(newPassword);
    }
    
    /**
     * Benutzer löschen
     * @param user Zu löschender Benutzer
     */
    @RolesAllowed({"app-user","Kunde"})
    public void delete(User user) {
        this.em.remove(user);
    }
    
    /**
     * Benutzer aktualisieren
     * @param user Zu aktualisierender Benutzer
     * @return Gespeicherter Benutzer
     */
    @RolesAllowed({"app-user","Kunde"})
    public User update(User user) {
        return em.merge(user);
    }

    // Für REST-Call implementiert
    // Roles Allowed only allow for Mitarbeiter
    // @RolesAllowed("")
    public List<User> findByQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            query = "";
        }
        
        query = "%" + query + "%";

        return em.createQuery("SELECT u FROM User u"
                            + "    WHERE u.username     LIKE :query"
                            + "       OR u.vorname      LIKE :query"
                            + "       OR u.nachname     LIKE :query")
                .setParameter("query", query)
                .getResultList();
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
