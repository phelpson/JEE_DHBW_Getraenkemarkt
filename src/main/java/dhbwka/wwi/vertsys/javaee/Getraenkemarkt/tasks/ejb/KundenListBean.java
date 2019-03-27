/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.jpa.Kunde;
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
@RolesAllowed("app-user")
public class KundenListBean extends Kunde {
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

    public void saveNew(Kunde kunde) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Kunde findById(long parseLong) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
