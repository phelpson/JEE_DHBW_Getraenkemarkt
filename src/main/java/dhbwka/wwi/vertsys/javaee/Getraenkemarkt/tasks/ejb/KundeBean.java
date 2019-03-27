/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.jpa.Kunde;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Kategorien.
 */
@Stateless
@RolesAllowed("app-user")
public class KundeBean extends EntityBean<Kunde, Long> {

    public KundeBean() {
        super(Kunde.class);
    }

    /**
     * Auslesen aller Kategorien, alphabetisch sortiert.
     *
     * @return Liste mit allen Kategorien
     */
    public List<Kunde> findAllSorted() {
        return this.em.createQuery("SELECT c FROM Kunde c").getResultList();
    }
    
      // Liste mit allen verfügbaren KundenIds zurückgeben
    public List<Kunde> findAllEntries() {
         return em.createQuery("SELECT a FROM Kunde a"
                             + "ORDER BY a.kundeId DESC,")
                .getResultList();
    }
    
    // Kunde via Kundennummer (ID - Primary Key) suchen
    public Kunde findByKundeId(int kundeId){
        //test
        return this.em.find(Kunde.class, kundeId);
    }
    
    // Kunde via Firmenname suchen
    public Kunde findByFirmenname(String firmenname) {
        return this.em.find(Kunde.class, firmenname);
    }
    
    // Kunde via PLZ suchen
    public Kunde findByPlz(int plz) {
        return this.em.find(Kunde.class, plz);
    }
    
    /**
     * Neuen Kunden anlegen + persistieren
     * 
     * 
     * @param kundeId       Kundennummer (ID) des Kunden
     * @param firmenname    Firmenname des Kunden
     * @return              neues Kundenobject nach Erstellung
     */
    public Kunde createNewEntry(String firmenname){
        
        Kunde Kunde = new Kunde(firmenname);
                        
        em.persist(Kunde);
        return em.merge(Kunde);
    }
    
    // Kundeninformationen updaten
    public Kunde updateKunde(Kunde Kunde) {
        return this.em.merge(Kunde);
    }
    
    // Kundeninformationen löschen
    public void deleteKunde(Kunde Kunde) {
        this.em.remove(Kunde);
    }
}