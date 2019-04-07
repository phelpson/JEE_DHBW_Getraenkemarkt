package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


//Kundenobjekt, welches in der Datenbank abgespeichert werden soll
@Entity
@XmlRootElement
public class Kunde implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "kunden_ids")
    @TableGenerator(name = "kunden_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @Column(length = 30)
    @NotNull(message = "Der Firmennameame darf nicht leer sein.")
    @Size(min = 3, max = 48, message = "Der Firmenname muss zwischen drei und 48 Zeichen lang sein.")
    private String firmenName;

//    Fremdschlusselbeziehung mit der Tabelle Bestellung. Jeder Kunde kann eine oder mehrere Bestellungen anlegen.
    @OneToMany(mappedBy = "kunde", fetch = FetchType.LAZY)
    List<Bestellung> bestellungen = new ArrayList<>();
    
//    Fremdschlüsselbeziehung mit der tabelle User. Jeder Kunde muss auch einen User haben.
//    Hier Spezialisierungsform des Users.
    @OneToOne 
    @NotNull(message = "Der User im Kunden darf nicht leer sein.")
    private User user;

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Kunde() {
    }

    public Kunde(String name) {
        this.firmenName = name;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public long getId() {
        return id;
    }
    
    public String getFirmenname(){
        return firmenName;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getName() {
        return firmenName;
    }

    public void setName(String name) {
        this.firmenName = name;
    }

    @XmlTransient
    public List<Bestellung> getbestellungen() {
        return bestellungen;
    }

    public void setbestellungen(List<Bestellung> bestellungen) {
        this.bestellungen = bestellungen;
    }
    
    //</editor-fold>

}
