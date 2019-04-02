package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.MitarbeiterEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.ejb.Stateless;


/**
 *
 * @author LU_MA
 */
@Stateless
public class MitarbeiterBean extends EntityBean <MitarbeiterEntity, Long>{
    
    
    public MitarbeiterBean() {
        super(MitarbeiterEntity.class);
    }

    public MitarbeiterBean(Class<MitarbeiterEntity> entityClass) {
        super(entityClass);
    }
    
    public String generiereEintrittsdatum() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime date = LocalDateTime.now();
        return dtf.format(date);
    }
    
    // neuen Eintrag erstellen
    public MitarbeiterEntity createNewEntry(String dateString) {
        MitarbeiterEntity mitarbeiter = new MitarbeiterEntity(dateString);
        this.em.persist(mitarbeiter);
        return this.em.merge(mitarbeiter);
    }
    
    // Mitarbeiter via MitarbeiterId (ID - Primary Key) suchen
    public MitarbeiterEntity findByMitarbeiterId(long mitarbeiterId){
        return this.em.find(MitarbeiterEntity.class, mitarbeiterId);
    }
    
    // Mitarbeiter via Namen suchen
    public MitarbeiterEntity findByNachNamen(String mitarbeiternamen) {
        return this.em.find(MitarbeiterEntity.class, mitarbeiternamen);
    }

    // MA Infos updaten
    public MitarbeiterEntity updateMitarbeiter(MitarbeiterEntity mitarbeiterEntity) {
        return this.em.merge(mitarbeiterEntity);
    }
}

