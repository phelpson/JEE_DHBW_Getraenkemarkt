/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemart.Patrick;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Patrick Mahler
 */

@Entity
public class LieferantEntry implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lieferantID;
    private String lieferantFirmName    = "";
    private String lieferantAdresse     = "";
    private int anzBestellungen         = 0;
    
    public LieferantEntry(){
        
    }
    
    
    public LieferantEntry(Long lieferantID, String lieferantFirmName, String lieferantAdresse, int anzBestellungen){
        this.lieferantID                = lieferantID;
        this.lieferantFirmName          = lieferantFirmName;
        this.lieferantAdresse           = lieferantAdresse;
        this.anzBestellungen            = anzBestellungen;   
    }
    
    public Long getlieferantID(){
        return this.lieferantID;
    }
    public void setlieferantID(Long lieferantID){
        this.lieferantID = lieferantID;
    }
    
    public String getlieferantAdresse(){
        return this.lieferantAdresse;
    }
    public void setlieferantAdresse(String lieferantAdresse){
        this.lieferantAdresse = lieferantAdresse;
    }
    
    public String getlieferantFirmName(){
        return this.lieferantFirmName;
    }
    public void setlieferantFirmName(String lieferantFirmName){
        this.lieferantFirmName = lieferantFirmName;
    }
    
    public int getanzBestellungen(){
        return this.anzBestellungen;
    }
    public void setanzBestellungen(int anzBestellungen){
        this.anzBestellungen = anzBestellungen;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lieferantID != null ? lieferantID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LieferantEntry)) {
            return false;
        }
        LieferantEntry other = (LieferantEntry) object;
        if ((this.lieferantID == null && other.lieferantID != null) || (this.lieferantID != null && !this.lieferantID.equals(other.lieferantID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dhbwka.wwi.vertsys.javaee.Getraenkemart.Patrick.LieferantEntry[ lieferantID=" + lieferantID + " ]";
    }
}

