package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Eine Getränkebestellung.
 */
@Entity
@XmlRootElement
public class Bestellung implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "bestellung_ids")
    @TableGenerator(name = "bestellung_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @ManyToOne
    @NotNull(message = "Die Aufgabe muss einem Benutzer geordnet werden.")
    private User owner;

    @ManyToOne
    private Kunde kunde;
   
    @Column(length = 50)
    @NotNull(message = "Die Bezeichnung darf nicht leer sein.")
    @Size(min = 1, max = 50, message = "Die Bezeichnung muss zwischen ein und 50 Zeichen lang sein.")
    private String shortText;

    @Lob
    @NotNull
    private String longText;

    @NotNull(message = "Das Datum darf nicht leer sein.")
    private Date dueDate;

    @NotNull(message = "Die Uhrzeit darf nicht leer sein.")
    private Time dueTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BestellungStatus status = BestellungStatus.OPEN;
    
    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private GetraenkeEnum getraenk = GetraenkeEnum.Bier;

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Bestellung() {
    }

    public Bestellung(User owner, Kunde kunde, String shortText, String longText, Date dueDate, Time dueTime, GetraenkeEnum getraenk) {
        this.owner = owner;
        this.kunde = kunde;
        this.shortText = shortText;
        this.longText = longText;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.getraenk = getraenk;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Kunde getkunde() {
        return kunde;
    }

    public void setkunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Time getDueTime() {
        return dueTime;
    }

    public void setDueTime(Time dueTime) {
        this.dueTime = dueTime;
    }

    public BestellungStatus getStatus() {
        return this.status;
    }

    public void setStatus(BestellungStatus status) {
        this.status = status;
    }
    
    public GetraenkeEnum getGetraenkEnum(){
        return this.getraenk; 
    }
    public void setGetraenkEnum(GetraenkeEnum getraenk){
        this.getraenk = getraenk; 
    }

    //</editor-fold>

}
