
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Kunde;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author LU_MA
 */

//Verwaltungsbean für die Kunden-Liste, die auf dem KundenListServlet dargestellt werden soll.
@Stateless
public class KundenListBean {
     @PersistenceContext
    protected EntityManager em;
    
     public KundenListBean() {
    }
    
//  Alle Kunden aus der Datenbank holen.
    public List<Kunde> findAllSorted() {
        return this.em.createQuery("SELECT p FROM Kunde p").getResultList();
    }
    
}
