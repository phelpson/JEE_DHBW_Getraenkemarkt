package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author LU_MA
 */

// Mitarbeiter-Entity zum Persistieren der Daten in der Datenbank
@Entity
@Table(name = "Mitarbeiter")
public class MitarbeiterEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //MitarbeiterID
    @Id
    //Anlegen einer Mitarbeiter ID Spalte
    @Column(name = "id")
    //automatische Generierung der ID beim Anlegen
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long mitarbeiterId;
    
    //Spalte für das Eintrittsdatum anlegen
    @Column(name = "eintrittsdatum")
    private String eintrittsdatum;
    
    //1:1 Beziehung (Fremdschlüssel-Beziehung) zu User Tabelle erstellen
    @OneToOne 
    @NotNull(message = "Der User im Mitarbeiter darf nicht leer sein.")
    private User user;
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
     // Constructor
    public MitarbeiterEntity() {
        // empty base constructor
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public MitarbeiterEntity(String  eintrittsdatum) {
        this.eintrittsdatum = eintrittsdatum;
        
    }
    //gibt Mitarbeiter ID zurück
    public long getMitarbeiterId() {
        return this.mitarbeiterId;
    }
    //setzt die MitarbeiterID des aufgerufenen Mitarbeiters
    public void setMitarbeiterId(long mitarbeiterId) {
        this.mitarbeiterId = mitarbeiterId;
    }
    //setzt das Eintrittsdatum des Mitarbeiters
    public void setDate (String  eintrittsdatum) {
        this.eintrittsdatum = eintrittsdatum;
    }
    //gibt das Eintrittsdatum zurück
    public String  getDate() {
        return this.eintrittsdatum;
    }
    //gibt das zugehörige Userobjekt zurück. Wird über die 1:1 Beziehung ermöglicht
    public User getUser() {
        return user;
    }
    //weist dem Mitarbeiter das Zugehörige Userobjekt zu. Wird über die 1:1 Beziehung ermöglicht
    public void setUser(User user) {
        this.user = user;
    }
    //</editor-fold>    
}
