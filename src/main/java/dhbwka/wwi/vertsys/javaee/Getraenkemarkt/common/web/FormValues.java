package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kleine Hilfsklasse, die als Objekt in der HTTP-Session abgelegt werden
 * kann, um die fehlerhaften Eingaben eines Formulars zwischenzuspeichern.
 */
public class FormValues {
    
    private Map<String, String[]> values = new HashMap<>();
    private List<String> errors = new ArrayList<>();

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public Map<String, String[]> getValues() {
        return values;
    }
    
    public void setValues(Map<String, String[]> values) {
        // Werte kopieren, da eine direkte Zuweisung die Werte wieder verliert,
        // wenn die Methode mit request.getParameterMap() aufgerufen wird!
        this.values = new HashMap<>();
        
        for (String key : values.keySet()) {
            this.values.put(key, values.get(key));
        }
    }
    
    public List<String> getErrors() {
        //auslesen der Error-Liste
        return errors;
    }
    
    public void setErrors(List<String> errors) {
        //Error in errors Liste setzen
        this.errors = errors;
    }
    //</editor-fold>
    
}
