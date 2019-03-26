/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.MitarbeiterEntity;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author LU_MA
 */
@Stateless
public class MitarbeiterBean {
    
    /*
    * Entity Manager
    */
    @PersistenceContext
    protected EntityManager em;
    
    // neuen Eintrag erstellen
    public MitarbeiterEntity createNewEntry(String dateString) {
        MitarbeiterEntity mitarbeiter = new MitarbeiterEntity(dateString);
        this.em.persist(mitarbeiter);
        return this.em.merge(mitarbeiter);
    }
        
    // Liste mit allen verfügbaren MitabeiterIds zurückgeben
    public List<MitarbeiterEntity> findAllEntries() {
         return em.createQuery("SELECT a FROM MitarbeiterEntity a"
                             + "ORDER BY a.MitarbeiterId DESC,")
                .getResultList();
    }
    
    // Mitarbeiter via MitarbeiterId (ID - Primary Key) suchen
    public MitarbeiterEntity findByMitarbeiterId(long mitarbeiterId){
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

    // Kundeninformationen updaten
    public MitarbeiterEntity updateKunde(MitarbeiterEntity mitarbeiterEntity) {
        return this.em.merge(mitarbeiterEntity);
    }
    
    // Kundeninformationen löschen
    public void deleteKunde(MitarbeiterEntity mitarbeiterEntity) {
        this.em.remove(mitarbeiterEntity);
    }
    
}

