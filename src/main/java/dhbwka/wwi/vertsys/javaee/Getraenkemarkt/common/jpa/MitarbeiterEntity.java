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
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    //</editor-fold>    
}
