/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Philip Mayer
 */

@Entity
@Table(name = "Kunden")
public class KundeEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long kundeId;
    
    @Column(name="company_name")
    private String firmenname = "";

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public KundeEntity() {
        // empty base constructor
    }
    
    public KundeEntity(String firmenname) {
        this.firmenname = firmenname;
        
        // Erstellen eine Kunden-Kategorie
        
    }
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Getter + Setter">
    public void setKundenId(Long kundeId) {
        this.kundeId = kundeId;
    }
    public long getKundenId() {
        return this.kundeId;
    }
    public void setFirmenname(String firmenname) {
       this.firmenname= firmenname;
    }
    public String getFirmenname() {
        return this.firmenname;
    }
    //</editor-fold>

    // Methods
    @Override
    public String toString() {
        return "Kunde: " + kundeId  + " " + firmenname;
    }
}
