/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.jpa.GetraenkeEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author LU_MA
 */
public class GetraenkeBean {
    
    /*
    * Entity Manager
    */
    @PersistenceContext
    protected EntityManager em;
    
    // Liste mit allen verfügbaren KundenIds zurückgeben
    public List<GetraenkeEntity> findAllEntries() {
         return em.createQuery("SELECT a FROM GetraenkeEntity a"
                             + "ORDER BY a.GetraenkeId DESC,")
                .getResultList();
    }
    
     // Getränk via GetraenkeID (ID - Primary Key) suchen
    public GetraenkeEntity findByGetraenkeId(int getraenkeId){
        return this.em.find(GetraenkeEntity.class, getraenkeId);
    }
    
    // Getränk via Namen suchen
    public GetraenkeEntity findByGetraenkeNamen(String getraenkenamen) {
        return this.em.find(GetraenkeEntity.class, getraenkenamen);
    }
    
   
    /**
     * Neues Getränk anlegen + persistieren
     * 
     * 
     * @param getraenkeId   getraenkeId (ID) des Getränks
     * @param getrankename  Name des Getränks
     * @param flaschenProKiste Flaschen pro Kiste
     * @return              neues Getränk nach Erstellung
     */
    public GetraenkeEntity createNewEntry(
            Long getraenkeId, 
            String getraenkename, int flaschenProKiste){
        
        GetraenkeEntity getraenkeEntity = new GetraenkeEntity(
                getraenkeId, getraenkename, flaschenProKiste);
                        
        em.persist(getraenkeEntity);
        return em.merge(getraenkeEntity);
    }
    
    // Kundeninformationen updaten
    public GetraenkeEntity updateGetraenk(GetraenkeEntity getraenkeEntity) {
        return this.em.merge(getraenkeEntity);
    }
    
    // Kundeninformationen löschen
    public void deleteKunde(GetraenkeEntity getraenkeEntity) {
        this.em.remove(getraenkeEntity);
    }
    
    
    
}
