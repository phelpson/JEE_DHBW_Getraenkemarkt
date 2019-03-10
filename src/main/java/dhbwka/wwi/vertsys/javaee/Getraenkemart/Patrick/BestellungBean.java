/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemart.Patrick;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Patrick Mahler
 * Enterprise Java Bean zum Aufgeben, Löschen und Speichern von Bestellungen
 */
@Stateless
public class BestellungBean {
    @PersistenceContext
    protected EntityManager em;
    
    /**
     * @return Liste mit allen Bestellungen
     */
    
    public List<BestellungEntry> findAllEntries(){
        return em.createQuery("SELECT e FROM BestellungEntry e "
                             + "ORDER BY e.bestellungDate DESC,"
                             + "         e.bestellungTime DESC")
                .getResultList();
    }
    /**
     * @return Bestellung mit genannter ID
     * @param id
     */
    public List<BestellungEntry> findById(long id){
        return this.em.find(BestellungEntry.class, id);
    }
    /**
     * Speichert einen neuen Gästebucheintrag
     * @param id                ID der Bestellung
     * @param bestellungDate    Datum der Bestellung
     * @param bestellungTime    Zeit der Bestellung
     * @param lieferantID       ID des Lieferanten(Fremdschlüssel)
     * @param getraenkID        ID der Getränke (Fremdschlüssel)
     * @param bestellungStatus  Status der Bestellung
     * @return                  die gespeicherte Bestellung
     */
    public BestellungEntry createNewEntry(Long id, Date bestellungDate, Time bestellungTime, int lieferantID, 
                                                int getraenkID, String bestellungStatus){
        BestellungEntry entry = new BestellungEntry(id, bestellungDate, bestellungTime, lieferantID, 
                                                    getraenkID, bestellungStatus);
                    
        em.persist(entry);
        return em.merge(entry);
    }
}
