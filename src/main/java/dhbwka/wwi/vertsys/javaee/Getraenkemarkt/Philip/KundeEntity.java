/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.Philip;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Philip Mayer
 */

@Entity
@Table(name = "Kunde")
public class KundeEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long kundeId;
    
    @Column
    private String firmenname = "";
    
    @Column
    private String adresse = "";
    
    @Column
    private int plz = 0;
    
    @Column
    private String land = "DE";
    
    
    // Constructor
    public KundeEntity() {
        // empty base constructor
    }
    
    public KundeEntity(Long kundeId, String firmenname, String adresse, int plz, String land) {
        this.kundeId = kundeId;
        this.firmenname = firmenname;
        this.adresse = adresse;
        this.plz = plz;
        this.land = land;
    }
    
    
    // Getter + Setter
    
    public void setKundenId(Long kundeId) {
        this.kundeId = kundeId;
    }
    public long getKundenId() {
        return this.kundeId;
    }
    public void setFirmenname(String firmenname) {
       this.firmenname= firmenname;
    }
    public String getFirmenname() {
        return this.firmenname;
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
    public void setLand(String land){
        this.land = land;
    }
    public String getLand() {
        return this.land;
    }

    // Methods
            
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kundeId != null ? kundeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KundeEntity)) {
            return false;
        }
        KundeEntity other = (KundeEntity) object;
        if ((this.kundeId == null && other.kundeId != null) || (this.kundeId != null && !this.kundeId.equals(other.kundeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Kunde: " + kundeId  + " " + firmenname + " " + adresse + " " + plz  + " " + land;
    }
}
