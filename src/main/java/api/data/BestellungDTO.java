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
public class BestellungDTO {
    
    private String shortText;
    private String longText;
    private String getraenk;
    private String dueDate;
    private String dueTime;
    private String status;
    
    
    public BestellungDTO () {//(String shortText, String longText, String getraenk, String dueDate, String dueTime) {
//        this.shortText = shortText;
//        this.longText = longText;
//        this.getraenk = getraenk;
//        this.dueDate = dueDate;
//        this.dueTime = dueTime;
    }
    
    public BestellungDTO (String shortText, String longText, String getraenk, String dueDate, String dueTime) {
        this.shortText = shortText;
        this.longText = longText;
        this.getraenk = getraenk;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }
    
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
}
