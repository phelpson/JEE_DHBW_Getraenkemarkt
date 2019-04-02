/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.data;

import java.util.List;

/**
 *
 * @author Philip Mayer
 */
public class UserDTO {
    String username;
    String email;
    String vorname;
    String nachname;
    String adresse;
    String art;
    int countGesamtBestellungen;
    int plz;
    List<BestellungDTO> bestellungen;




    public UserDTO() {
        // Empty Constructor
    }
   //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public List<BestellungDTO> getBestellungen() {
        return bestellungen;
    }

    public void setBestellungen(List<BestellungDTO> bestellung) {
        this.bestellungen = bestellung;
    }
    
    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public int getCountGesamtBestellungen() {
        return countGesamtBestellungen;
    }

    public void setCountGesamtBestellungen(int countGesamtBestellungen) {
        this.countGesamtBestellungen = countGesamtBestellungen;
    }
     //</editor-fold>
}
