/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.Philip;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Philip Mayer
 */

@Stateless
public class AuftragBean {
    
    @PersistenceContext
    protected EntityManager em;
    
    // Liste mit allen verfügbaren Aufträgen zurückgeben
    public List<AuftragEntity> findAllEntries() {
         return em.createQuery("SELECT a FROM AuftragEntity a"
                             + "ORDER BY a.auftragDate DESC,"
                             + "         a.auftragTime DESC")
                .getResultList();
    }
    
    // Spezifischer Auftrag aus dem DB holen
    public AuftragEntity findById(int id) {
        return this.em.find(AuftragEntity.class, id);
    }
    
    // Spezifische Aufträge anhand der Kundennummer identifizieren
    public AuftragEntity findByCustomerId(int kundenId) {
        return this.em.find(AuftragEntity.class, kundenId);
    }
    
    /**
     * Speichern eines neuen Auftrags
     * 
     * 
     * @param auftragId      ID des Auftrags
     * @param auftragDate    Datum des Auftrags
     * @param auftragTime    Zeit des Auftrags
     * @param kundenID       ID des Kunden(Fremdschlüssel)
     * @param getraenkID     ID der Getränke (Fremdschlüssel)
     * @param auftragStatus  Status des Auftrags
     * @return               die gespeicherte Auftrag
     */
    public AuftragEntity createNewEntry(
            Long auftragId, 
            Date auftragDate, 
            Time auftragTime, 
            int kundenID,                                      
            int getraenkID, 
            String auftragStatus){
        
        AuftragEntity entity = new AuftragEntity(
                auftragId, auftragDate, auftragTime, kundenID, getraenkID, auftragStatus);
                        
        em.persist(entity);
        return em.merge(entity);
    }
    
    
    
}
