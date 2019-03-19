/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Luca;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author LU_MA
 */
@Entity
public class GetraenkeEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long getraenkeId;
    
    private String getraenkename = "";
    private int flaschenProKiste = 0;

    GetraenkeEntity() {
       
    }
    
     GetraenkeEntity(Long getraenkeId,String getraenkename, int flaschenProKiste) {
       
    }

    public Long getGetraenkeId() {
        return this.getraenkeId ;
    }

    public void setGetraenkeId(Long getraenkeId) {
        this.getraenkeId = getraenkeId;
    }
    
    public String getGetrankeName(){
        return this.getraenkename;
    }
    
    public void setGetrankeName(String getrankeName){
        this.getraenkename = getrankeName;
    }
    
    public int getFlaschenProKiste(){
        return this.flaschenProKiste;
    }
    
    public void setFlaschenProKiste(int flaschenProKiste){
        this.flaschenProKiste = flaschenProKiste;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getraenkeId != null ? getraenkeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GetraenkeEntity)) {
            return false;
        }
        GetraenkeEntity other = (GetraenkeEntity) object;
        if ((this.getraenkeId == null && other.getraenkeId != null) || (this.getraenkeId != null && !this.getraenkeId.equals(other.getraenkeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Luca.GetraenkeEntity[ id=" + getraenkeId + " ]";
    }
    
}
