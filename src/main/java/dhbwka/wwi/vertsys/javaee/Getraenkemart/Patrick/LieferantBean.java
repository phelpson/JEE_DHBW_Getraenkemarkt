/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dhbwka.wwi.vertsys.javaee.Getraenkemart.Patrick;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 *
 * @author Patrick Mahler
 *  * Enterprise Java Bean zum Erstellen, Bearbeiten, Löschen und Speichern von Lieferanten
 */
@Stateless
public class LieferantBean {
    @PersistenceContext
    protected EntityManager em;
    
    /**
     * @return Liste mit allen Lieferant
     */
    
    public List<LieferantEntry> findAllEntries(){
        return em.createQuery("SELECT e FROM LieferantEntry e "
                             + "ORDER BY e.lieferantFirmName DESC")
                .getResultList();
    }
    /**
     * @return Lieferant mit genannter ID
     * @param lieferantID
     */
    public LieferantEntry findById(long lieferantID){
        return this.em.find(LieferantEntry.class, lieferantID);
    }
    
    /**
     * Speichert einen neuen Lieferanten
     * @param lieferantID       
     * @param lieferantFirmName 
     * @param lieferantAdresse       
     * @param anzBestellungen  
     * @return                  
     */
    public LieferantEntry createNewEntry(Long lieferantID, String lieferantFirmName, String lieferantAdresse,
                                            int anzBestellungen){
       
        LieferantEntry entry = new LieferantEntry(lieferantID, lieferantFirmName, 
                                                            lieferantAdresse, anzBestellungen);
                    
        em.persist(entry);
        return em.merge(entry);
    }
}
