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
    
//    Methode die ein Eintrittsdatum für den Mitarbeiter generiert.
//    Der Mitarbeiter hat in der Tabelle Mitarbeiter ledigtlich seine ID und das Eintrittsdatum gespeichert.
//    Das Eintrittsdatum wird, aus Wartungsgründen, vom Servlet zum Bean verschoben.
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
}

