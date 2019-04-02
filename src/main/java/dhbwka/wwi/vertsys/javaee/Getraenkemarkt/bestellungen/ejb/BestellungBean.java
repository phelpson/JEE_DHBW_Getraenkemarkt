package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Kunde;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Bestellung;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.BestellungStatus;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Aufgaben
 */
@Stateless
public class BestellungBean extends EntityBean<Bestellung, Long> { 
   
    public BestellungBean() {
        super(Bestellung.class);
    }
    
    @PersistenceContext
    EntityManager em;
    
    public List<Bestellung> findByShortText(String shortText) {
        shortText = "%" + shortText + "%";
        return em.createQuery("SELECT b FROM Bestellung b WHERE b.shortText LIKE :shortText ORDER BY b.dueDate, b.dueTime")
                .setParameter("shortText", shortText)
                .getResultList();
    }
    
    /**
     * Suche nach Aufgaben anhand ihrer Bezeichnung, Kategorie und Status.
     * 
     * @param search In der Kurzbeschreibung enthaltener Text (optional)
     * @param kunde Kategorie (optional)
     * @param status Status (optional)
     * @return Liste mit den gefundenen Aufgaben
     */
    public List<Bestellung> search(String search, Kunde kunde, BestellungStatus status) {
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        
        // SELECT t FROM bestellung t
        CriteriaQuery<Bestellung> query = cb.createQuery(Bestellung.class);
        Root<Bestellung> from = query.from(Bestellung.class);
        query.select(from);

        // ORDER BY dueDate, dueTime
        query.orderBy(cb.asc(from.get("dueDate")), cb.asc(from.get("dueTime")));
        
        // WHERE t.shortText LIKE :search
        Predicate p = cb.conjunction();
        
        if (search != null && !search.trim().isEmpty()) {
            p = cb.and(p, cb.like(from.get("shortText"), "%" + search + "%"));
            query.where(p);
        }
        
        // WHERE t.kunde = :kunde
        if (kunde != null) {
            p = cb.and(p, cb.equal(from.get("kunde"), kunde));
            query.where(p);
        }
        
        // WHERE t.status = :status
        if (status != null) {
            p = cb.and(p, cb.equal(from.get("status"), status));
            query.where(p);
        }
        
        return em.createQuery(query).getResultList();
    }
}
