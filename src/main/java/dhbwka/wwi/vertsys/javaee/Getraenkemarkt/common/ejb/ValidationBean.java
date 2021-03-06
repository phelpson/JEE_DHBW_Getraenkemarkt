package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Bestellung;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 * Kleine EJB, die dafür genutzt werden kann, die Werte einer Entity zu
 * validieren, bevor diese gespeichert wird. Zwar validiert der Entity Manager
 * die Bean beim Speichern ebenfalls, da das aber erst am Ende der Transaktion
 * erfolgt, ist es schwer, rechtzeitig darauf zu reagieren.
 */
@Stateless
public class ValidationBean {
    
    @Resource
    Validator validator;
    
    @EJB
    UserBean userbean;
    
    /**
     * Wertet die "Bean Validation" Annotationen des übergebenen Objekts aus
     * (@Min, @Max, @Size, @NotNull, …) und gib eine Liste der dabei gefundenen
     * Fehler zurück.
     * 
     * @param <T>
     * @param object Zu überprüfendes Objekt
     * @return Liste mit Fehlermeldungen
     */
    public <T> List<String> validate(T object) {
        List<String> messages = new ArrayList<>();
        return this.validate(object, messages);
    }
    
    /**
     * Wertet die "Bean Validation" Annotationes des übergebenen Objekts aus
     * und stellt die gefundenen Meldungen in messages.
     * 
     * @param <T>
     * @param object Zu überprüfendes Objekt
     * @param messages List mit Fehlermeldungen
     * @return Selbe Liste wie messages
     */
    public <T> List<String> validate(T object, List<String> messages) {
        Set<ConstraintViolation<T>> violations = this.validator.validate(object);
        
        violations.forEach((ConstraintViolation<T> violation) -> {
            messages.add(violation.getMessage());
        });
        
        return messages;
    }
    
    public boolean validateOwner( Bestellung bestellung) {
       
        String owner = bestellung.getOwner().getUsername();
        String currentuser = userbean.getCurrentUser().getUsername();
        User user = userbean.getCurrentUser();
        
        if ("Mitarbeiter".equals(user.getDisAttribut())) {
            return true;
        }
        else if ("Kunde".equals(user.getDisAttribut())) {
            if (owner.equals(currentuser))
                return true;
            return false;
        }
        else {
            return false;
        }
    }
}
