/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.MitarbeiterEntity;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.MitarbeiterEntity;
import java.text.DateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author LU_MA
 */
public class MitarbeiterBean {
    
 /*
    * Entity Manager
    */
    @PersistenceContext
    protected EntityManager em;
    
    // Liste mit allen verfügbaren KundenIds zurückgeben
    public List<MitarbeiterEntity> findAllEntries() {
         return em.createQuery("SELECT a FROM MitarbeiterEntity a"
                             + "ORDER BY a.MitarbeiterId DESC,")
                .getResultList();
    }
    
    // Mitarbeiter via MitarbeiterId (ID - Primary Key) suchen
    public MitarbeiterEntity findByMitarbeiterId(int mitarbeiterId){
        return this.em.find(MitarbeiterEntity.class, mitarbeiterId);
    }
    
    // Mitarbeiter via Namen suchen
    public MitarbeiterEntity findByNachNamen(String mitarbeiternamen) {
        return this.em.find(MitarbeiterEntity.class, mitarbeiternamen);
    }
    
    // Kunde via PLZ suchen
    public MitarbeiterEntity findByxx(int plz) {
        return this.em.find(MitarbeiterEntity.class, plz);
    }
    
    /**
     * Neuen Mitarbeiter anlegen + persistieren
     * 
     * 
     * @param mitarbeiterId Mitarbeiternummer (ID) des Mitarbeiters
     * @param date          Datum Eintritt
     * @return              neues Mitarbeiter nach Erstellung
     */
    public MitarbeiterEntity createNewEntry(DateFormat date){
        
       MitarbeiterEntity mitarbeiter = new MitarbeiterEntity(date);;
                        
        em.persist(mitarbeiter);
        return em.merge(mitarbeiter);
    }
    
    // Kundeninformationen updaten
    public MitarbeiterEntity updateKunde(MitarbeiterEntity mitarbeiterEntity) {
        return this.em.merge(mitarbeiterEntity);
    }
    
    // Kundeninformationen löschen
    public void deleteKunde(MitarbeiterEntity mitarbeiterEntity) {
        this.em.remove(mitarbeiterEntity);
    }
    
}

