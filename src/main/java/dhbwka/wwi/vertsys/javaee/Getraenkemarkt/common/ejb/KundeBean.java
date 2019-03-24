/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.KundeEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Philip Mayer
 */

@Stateless
public class KundeBean {
    /*
    * Entity Manager
    */
    @PersistenceContext
    protected EntityManager em;
    
    // Liste mit allen verfügbaren KundenIds zurückgeben
    public List<KundeEntity> findAllEntries() {
         return em.createQuery("SELECT a FROM KundeEntity a"
                             + "ORDER BY a.kundeId DESC,")
                .getResultList();
    }
    
    // Kunde via Kundennummer (ID - Primary Key) suchen
    public KundeEntity findByKundeId(int kundeId){
        return this.em.find(KundeEntity.class, kundeId);
    }
    
    // Kunde via Firmenname suchen
    public KundeEntity findByFirmenname(String firmenname) {
        return this.em.find(KundeEntity.class, firmenname);
    }
    
    // Kunde via PLZ suchen
    public KundeEntity findByPlz(int plz) {
        return this.em.find(KundeEntity.class, plz);
    }
    
    /**
     * Neuen Kunden anlegen + persistieren
     * 
     * 
     * @param kundeId       Kundennummer (ID) des Kunden
     * @param firmenname    Firmenname des Kunden
     * @return              neues Kundenobject nach Erstellung
     */
    public KundeEntity createNewEntry(String firmenname){
        
        KundeEntity kundeEntity = new KundeEntity(firmenname);
                        
        em.persist(kundeEntity);
        return em.merge(kundeEntity);
    }
    
    // Kundeninformationen updaten
    public KundeEntity updateKunde(KundeEntity kundeEntity) {
        return this.em.merge(kundeEntity);
    }
    
    // Kundeninformationen löschen
    public void deleteKunde(KundeEntity kundeEntity) {
        this.em.remove(kundeEntity);
    }
    
}
