package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Kunde;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Kategorien.
 */
@Stateless
public class KundeBean extends EntityBean<Kunde, Long> {


    public KundeBean() {
        super(Kunde.class);
    }


    public KundeBean(Class<Kunde> entityClass) {
        super(entityClass);
    }
    /**
     * Auslesen aller Kategorien, alphabetisch sortiert.
     * @return Liste mit allen Kategorien
     */
    public List<Kunde> findAllSorted() {
        return this.em.createQuery("SELECT c FROM Kunde c").getResultList();
    }
    
    // Kunde via Kundennummer (ID - Primary Key) suchen
    public Kunde findByKundeId(int kundeId){
        //test
        return this.em.find(Kunde.class, kundeId);
    }
    
    // Kunde via Firmenname suchen
    public Kunde findByFirmenname(String firmenname) {
        return this.em.find(Kunde.class, firmenname);
    }
    
    public List<Kunde> findByUsername(User user) {   
        String username = user.getUsername();
        return em.createQuery("SELECT a FROM Kunde a WHERE a.user = :username").setParameter("username",user).getResultList();         
    }
}
