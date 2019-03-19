/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemart.Patrick;

import Luca.GetraenkeEntity;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Persistence Entity
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
    private LieferantEntry lieferantEntry = null;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private GetraenkeEntity getraenkeEntity = null;
    
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private User userEntity = null;
    
    private String bestellungStatus = "";
    
    public BestellungEntry(){
        
    }
    
    public BestellungEntry(Long id, Date bestellungDate, Time bestellungTime, LieferantEntry lieferantEntry, 
                                                GetraenkeEntity getraenkeEntity, User userEntity, String bestellungStatus){
        this.id                 = id;
        this.bestellungDate     = bestellungDate;
        this.bestellungTime     = bestellungTime;
        this.lieferantEntry     = lieferantEntry;
        this.getraenkeEntity    = getraenkeEntity;
        this.userEntity         = userEntity;
        this.bestellungStatus   = bestellungStatus;      
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Date getbestellungDate(){
        return this.bestellungDate;
    }
    public void setbestellungDate(Date bestellungDate){
        this.bestellungDate = bestellungDate;
    }
    public Time getbestellungTime(){
        return this.bestellungTime;
    }
    public void setbestellungTime(Time bestellungTime){
        this.bestellungTime = bestellungTime;
    }
    public LieferantEntry getlieferantID(){
        return this.lieferantEntry;
    }
    public void setlieferantID(LieferantEntry lieferantEntry){
        this.lieferantEntry = lieferantEntry;
    }
    public GetraenkeEntity getgetraenkID(){
        return this.getraenkeEntity;
    }
    public void setgetraenkID(GetraenkeEntity getraenkeEntity){
        this.getraenkeEntity = getraenkeEntity;
    }
    public User getuserID(){
        return this.userEntity;
    }
    public void setuserID(User userEntity){
        this.userEntity = userEntity;
    }
    public String getbestellungStatus(){
        return this.bestellungStatus;
    }
    public void setbestellungStatus(String bestellungStatus){
        this.bestellungStatus = bestellungStatus;
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
