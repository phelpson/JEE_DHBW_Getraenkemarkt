/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.data;

/**
 *
 * @author Philip Mayer
 */

//Data Transfer Object, welches die Entity Bestellung.jpa in String-Felder für den JSON Output mappt
public class BestellungDTO {
    
//    Variablen die für das JSON gemappt werden sollen
    private String shortText;
    private String longText;
    private String getraenk;
    private String dueDate;
    private String dueTime;
    private String status;
    
//    Default Konstruktur für die einfach und schnelle Instanzierung des DTOs
    public BestellungDTO () {
    }
    
//    Erweiterter Konstruktur für die effizientere Instanzierung des DTOs
    public BestellungDTO (String shortText, String longText, String getraenk, String dueDate, String dueTime) {
        this.shortText = shortText;
        this.longText = longText;
        this.getraenk = getraenk;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }
    
//    Getter und Setter für den Aufruf der privaten Variablen 
//    <editor-fold defaultstate="collapsed" desc="Setter und Getter">
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

    public String getGetraenk() {
        return getraenk;
    }

    public void setGetraenk(String getraenk) {
        this.getraenk = getraenk;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
//</editor-fold>
    
}
