/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.jpa.GetraenkeEntity;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.jpa.BestellungEntry;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.LieferantEntry;
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
    public BestellungEntry findById(long id){
        return this.em.find(BestellungEntry.class, id);
    }
    /**
     * @return Bestellung durch LieferantID
     * @param lieferantID
     */
    public BestellungEntry findByLieferantID(int lieferantID){
        return this.em.find(BestellungEntry.class, lieferantID);
    }
    /**
     * @return Bestellung durch UserID
     * @param userID
     */
    public BestellungEntry findByUserID(int userID){
        return this.em.find(BestellungEntry.class, userID);
    }
    
    /**
     * Speichert einer neuen Bestellung
     * @param id                ID der Bestellung
     * @param bestellungDate    Datum der Bestellung
     * @param bestellungTime    Zeit der Bestellung
     * @param lieferantEntry    ID des Lieferanten(Fremdschlüssel)
     * @param getraenkeEntity   ID der Getränke (Fremdschlüssel)
     * @param userEntity        Verknüpfung zum User (Fremdschlüssel)
     * @param bestellungStatus  Status der Bestellung
     * @return                  die gespeicherte Bestellung
     */
    public BestellungEntry createNewEntry(Long id, Date bestellungDate, Time bestellungTime, LieferantEntry lieferantEntry, 
                                                List<GetraenkeEntity> getraenkeEntity, User userEntity, String bestellungStatus){
        BestellungEntry entry = new BestellungEntry(
                 id,  bestellungDate,  bestellungTime,  lieferantEntry, 
                                                 getraenkeEntity,  userEntity,  bestellungStatus);
                    
        em.persist(entry);
        return em.merge(entry);
    }
}
