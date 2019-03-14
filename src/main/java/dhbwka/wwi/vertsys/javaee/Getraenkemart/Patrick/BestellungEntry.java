/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemart.Patrick;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Patrick Mahler
 */
@Entity
public class BestellungEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Date bestellungDate;
    private Time bestellungTime;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private int lieferantID = 0;
    @OneToMany()
    private int getraenkID = 0;
    private String bestellungStatus = "";
    
    public BestellungEntry(Long id, Date bestellungDate, Time bestellungTime, int lieferantID, 
                                                int getraenkID, String bestellungStatus){
        this.id                 = id;
        this.bestellungDate     = bestellungDate;
        this.bestellungTime     = bestellungTime;
        this.lieferantID        = lieferantID;
        this.getraenkID         = getraenkID;
        this.bestellungStatus   = bestellungStatus;      
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BestellungEntry)) {
            return false;
        }
        BestellungEntry other = (BestellungEntry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dhbwka.wwi.vertsys.javaee.Getraenkemart.Patrick.BestellungEntry[ id=" + id + " ]";
    }
    
}
