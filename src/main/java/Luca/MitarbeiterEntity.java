/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Luca;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author LU_MA
 */
@Entity
@Table(name = "Mitarbeiter")
public class MitarbeiterEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mitarbeiterId;
    
      @Column
    private String vorname = "";
      
      @Column
    private String nachname = "";
    
     @Column
    private String adresse = "";
    
    @Column
    private int plz = 0;
    
     // Constructor
    public MitarbeiterEntity() {
        // empty base constructor
    }
    
    public MitarbeiterEntity(Long mitarbeiterId,String vorname, String nachname, String adresse, int plz) {
        this.mitarbeiterId = mitarbeiterId;
        this.vorname = vorname;
        this.nachname = nachname;
        this.adresse = adresse;
        this.plz = plz;
    }
    

    public Long getMitarbeiterId() {
        return this.mitarbeiterId;
    }

    public void setMitarbeiterId(Long mitarbeiterId) {
        this.mitarbeiterId = mitarbeiterId;
    }
    
    public void setMitarbeitervorname(String mitarbeitervorname) {
       this.vorname= mitarbeitervorname;
    }
    
    public String getMitarbeitervorname() {
        return this.vorname;
    }
    
    public void setNachname(String mitarbeiternachname) {
       this.nachname= mitarbeiternachname;
    }
    
    public String getMitarbeiternachname() {
        return this.nachname;
    }
    
    public void setAdresse(String adresse) {
        this.adresse= adresse;
    }
    public String getAdresse() {
        return this.adresse;
    }
    public void setPlz(int plz) {
        this.plz = plz;
    }
    public int getPlz () {
        return this.plz;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mitarbeiterId != null ? mitarbeiterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MitarbeiterEntity)) {
            return false;
        }
        MitarbeiterEntity other = (MitarbeiterEntity) object;
        if ((this.mitarbeiterId == null && other.mitarbeiterId != null) || (this.mitarbeiterId != null && !this.mitarbeiterId.equals(other.mitarbeiterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Luca.MitarbeiterEntity[ id=" + mitarbeiterId + " ]";
    }
    
}
