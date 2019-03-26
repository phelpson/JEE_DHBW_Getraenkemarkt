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
 * @author LU_MA
 */
@Entity
@Table(name = "Mitarbeiter")
public class MitarbeiterEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long mitarbeiterId;
    
    @Column(name = "eintrittsdatum")
    private String eintrittsdatum;
    
     // Constructor
    public MitarbeiterEntity() {
        // empty base constructor
    }
    
    public MitarbeiterEntity(String  eintrittsdatum) {
        this.eintrittsdatum = eintrittsdatum;
        
    }
    public long getMitarbeiterId() {
        return this.mitarbeiterId;
    }
    public void setMitarbeiterId(long mitarbeiterId) {
        this.mitarbeiterId = mitarbeiterId;
    }
    public void setDate (String  eintrittsdatum) {
        this.eintrittsdatum = eintrittsdatum;
    }
    public String  getDate() {
        return this.eintrittsdatum;
    }
   

    @Override
    public String toString() {
        return "Luca.MitarbeiterEntity[ id=" + mitarbeiterId + " ]";
    }
    
}
