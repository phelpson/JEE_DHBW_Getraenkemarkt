/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.Philip;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Philip Mayer
 */
@Entity
@Table(name = "Auftrag")
public class AuftragEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long auftragId;
    
    @Column(nullable=false)
    private Date auftragDate;
    
    @Column(nullable=false)
    private Time auftragTime;
    
    // for later implementation of relation model 
    // @OneToOne(optional = false, fetch = FetchType.LAZY)
    @Column(nullable=false)
    private int kundenId = 0;
    
    // * Foreign Key Relation
    // * For later implementation of relation model 
    // * getraenkeId is placeholder for Getraenk Object
    // * @OneToMany
    // * @Column(nullable=false)
    // * List <Getraenk> getraenk = new ArrayList<>();
    
    private String auftragStatus = "";
    
    public AuftragEntity(){
        
    }
    
    public AuftragEntity(long auftragId, Date auftragDate, Time auftragTime, int kundenId, 
                                                String auftragStatus){
        this.auftragId      = auftragId;
        this.auftragDate    = auftragDate;
        this.auftragTime    = auftragTime;
        this.kundenId       = kundenId;
        this.auftragStatus  = auftragStatus;      
    }

    public long getId() {
        return this.auftragId;
    }

    public void setId(long id) {
        this.auftragId = id;
    }
    public Date getAuftragDate(){
        return this.auftragDate;
    }
    public void setAuftragDate(Date bestellungDate){
        this.auftragDate = bestellungDate;
    }
    public Time getAuftragTime(){
        return this.auftragTime;
    }
    public void setAuftragTime(Time bestellungTime){
        this.auftragTime = bestellungTime;
    }
    public int getKundenId(){
        return this.kundenId;
    }
    public void setlieferantID(int lieferantID){
        this.kundenId = lieferantID;
    }
    public String getAuftragStatus(){
        return this.auftragStatus;
    }
    public void setAuftragStatus(String bestellungStatus){
        this.auftragStatus = bestellungStatus;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (auftragId != null ? auftragId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuftragEntity)) {
            return false;
        }
        AuftragEntity other = (AuftragEntity) object;
        if ((this.auftragId == null && other.auftragId != null) || (this.auftragId != null && !this.auftragId.equals(other.auftragId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Autrags ID lautet: " + auftragId;
    }
    
}