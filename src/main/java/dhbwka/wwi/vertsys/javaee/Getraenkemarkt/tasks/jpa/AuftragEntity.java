/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.jpa;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.KundeEntity;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    private long auftragId;
    
    @NotNull(message = "Das Datum darf nicht leer sein.")
    @Column(nullable=false)
    private Date auftragDate;
    
    @NotNull(message = "Die Uhrzeit darf nicht leer sein.")
    @Column(nullable=false)
    private Time auftragTime;
    
    // for later implementation of relation model 
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Column(nullable=false)
    private KundeEntity kundeEntity = null;
    
    // * Foreign Key Relation
    // * For later implementation of relation model 
    // * getraenkeId is placeholder for Getraenk Object
    // * @OneToMany
    // * @Column(nullable=false)
    // * List <Getraenk> getraenk = new ArrayList<>();
    @ManyToOne
    @Enumerated(EnumType.STRING)
    @NotNull
    private AuftragsStatus status = AuftragsStatus.OPEN;
   
    
    @Lob
    @NotNull
    private String longText;

    
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public AuftragEntity(){
        
    }
    
    public AuftragEntity(long auftragId, Date auftragDate, Time auftragTime, KundeEntity kundeEntity, 
                                                AuftragsStatus status){
        this.auftragId      = auftragId;
        this.auftragDate    = auftragDate;
        this.auftragTime    = auftragTime;
        this.kundeEntity    = kundeEntity;
        this.status         = status;      
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter + Setter">
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
    public KundeEntity getKundenId(){
        return this.kundeEntity;
    }
    public void setlieferantID(KundeEntity kundeEntity){
        this.kundeEntity = kundeEntity;
    }
    public AuftragsStatus getAuftragStatus(){
        return this.status;
    }
    public void setAuftragStatus(AuftragsStatus status){
        this.status = status;
    }
    //</editor-fold>

    
}