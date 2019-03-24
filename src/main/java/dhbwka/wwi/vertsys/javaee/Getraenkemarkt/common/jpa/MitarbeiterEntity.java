/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
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
    private DateFormat eintrittsdatum;
    
     // Constructor
    public MitarbeiterEntity() {
        // empty base constructor
    }
    
    public MitarbeiterEntity(DateFormat eintrittsdatum) {
        this.eintrittsdatum = eintrittsdatum;
        
    }
    public Long getMitarbeiterId() {
        return this.mitarbeiterId;
    }
    public void setMitarbeiterId(Long mitarbeiterId) {
        this.mitarbeiterId = mitarbeiterId;
    }
    public void setDate (DateFormat eintrittsdatum) {
        this.eintrittsdatum = eintrittsdatum;
    }
    public DateFormat getDate() {
        return this.eintrittsdatum;
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
