
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
@Stateless
public class KundenListBean {
     @PersistenceContext
    protected EntityManager em;
    
     public KundenListBean() {
    }
    
    public List<Kunde> findAllSorted() {
        return this.em.createQuery("SELECT p FROM Kunde p").getResultList();
    }
    
}
