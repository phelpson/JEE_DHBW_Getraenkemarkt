/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Kunde;
import java.util.List;
import javax.annotation.security.RolesAllowed;
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
    
    /**
     *
     * @return
     */
    public List<Kunde> findAllSorted() {
        return this.em.createQuery("SELECT p FROM Kunde p").getResultList();
    }

    public Kunde saveNew(Kunde kunde) {
        if (kunde == null || kunde.equals(null)) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        this.em.persist(kunde);
        return this.em.merge(kunde);
    }

    public Kunde findById(long parseLong) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
