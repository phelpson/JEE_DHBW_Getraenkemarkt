/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Bestellung;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Kunde;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Datenbankklasse für einen Benutzer.
 */
@Entity
@Table(name = "Getraenkemarkt_USER")
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    @Id
    @Column(name = "USERNAME", length = 64)
    @Size(min = 5, max = 64, message = "Der Benutzername muss zwischen fünf und 64 Zeichen lang sein.")
    @NotNull(message = "Der Benutzername darf nicht leer sein.")
    private String username;
    
    public class Password {
        @Size(min = 6, max = 64, message = "Das Passwort muss zwischen sechs und 64 Zeichen lang sein.")
        public String password = "";
    }
    @Transient
    private final Password password = new Password();

    @Column(name = "PASSWORD_HASH", length = 64)
    @NotNull(message = "Das Passwort darf nicht leer sein.")
    private String passwordHash;
    
    @Column(name = "E-Mail", length = 64)
    @Size(min = 6, max = 64, message = "Die E-Mail-Adresse muss zwischen 6 und 64 Zeichen lang sein.")
    @NotNull(message  = "Die E-Mail Adresse darf nicht leer sein.")
    private String email;
    
    @Column
    private String vorname = "";
      
    @Column
    private String nachname = "";
      
    @Column(name="address")
    @NotNull(message  = "Das Adress-Feld darf nicht leer sein.")
    private String adresse = "";
    
    @Column(name="post_code")
    @NotNull(message  = "Die PLZ darf nicht leer sein.")
    private int plz = 0;

    // Diskriminierendes Attribut für die Generalisierung/Spezialisierung
    // User, Kunde, Mitarbeiter ggf. Lieferant
    @Column(name = "disAttribut")
    private String disAttribut;
    
//    @Column
//    @OneToOne
//    Kunde kunde;
    

    @ElementCollection
    @CollectionTable(
            name = "Getraenkemarkt_USER_GROUP",
            joinColumns = @JoinColumn(name = "USERNAME")
    )
    @Column(name = "GROUPNAME")
    List<String> groups = new ArrayList<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Bestellung> bestellungen = new ArrayList<>();
    

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public User() {
    }

    // Constructor
    public User(
            String username, String password, String email, String vorname, 
            String nachname, String adresse, int plz, String disAttribut) {
        this.username = username;
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
        this.email = email;
        this.vorname = vorname;
        this.nachname = nachname;
        this.adresse = adresse;
        this.plz = plz;
        this.disAttribut = disAttribut;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public String getUsername() {
        return username;
    }

    public void setUsername(String id) {
        this.username = id;
    }

    @XmlTransient
    public List<Bestellung> getbestellungen() {
        return bestellungen;
    }

    public void setbestellungen(List<Bestellung> bestellungen) {
        this.bestellungen = bestellungen;
    }
    
     public void setAdresse(String adresse) {
        this.adresse= adresse;
    }
    public String getAdresse() {
        return this.adresse;
    }
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPlz(int plz) {
        this.plz = plz;
    }
    public int getPlz () {
        return this.plz;
    }
     public void setVorname(String vorname) {
        this.vorname = vorname;
    }
    public String getVorname () {
        return this.vorname;
    }
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
    public String getNachname () {
        return this.nachname;
    }
    public void setDisAttribut(String disAttribut) {
        this.disAttribut = disAttribut;
    }
    public String getDisAttribut() {
        return this.disAttribut;
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Passwort setzen und prüfen">
    /**
     * Berechnet der Hash-Wert zu einem Passwort.
     *
     * @param password Passwort
     * @return Hash-Wert
     */
    private String hashPassword(String password) {
        byte[] hash;

        if (password == null) {
            password = "";
        }

        // Hashwert zum Passwort berechnen
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            hash = "!".getBytes(StandardCharsets.UTF_8);
        }

        // Hashwert in einen Hex-String umwandeln
        // Vgl. https://stackoverflow.com/a/9855338
        char[] hashHex = new char[hash.length * 2];

        for (int i = 0; i < hash.length; i++) {
            int v = hash[i] & 0xFF;
            hashHex[i * 2] = HEX_ARRAY[v >>> 4];
            hashHex[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }

        return new String(hashHex);
    }

    /**
     * Berechnet einen Hashwert aus dem übergebenen Passwort und legt ihn im
     * Feld passwordHash ab. Somit wird das Passwort niemals als Klartext
     * gespeichert.
     * 
     * Gleichzeitig wird das Passwort im nicht gespeicherten Feld password
     * abgelegt, um durch die Bean Validation Annotationen überprüft werden
     * zu können.
     *
     * @param password Neues Passwort
     */
    public void setPassword(String password) {
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
    }

    /**
     * Nur für die Validierung bei einer Passwortänderung!
     * @return Neues, beim Speichern gesetztes Passwort
     */
    public Password getPassword() {
        return this.password;
    }
    
    /**
     * Prüft, ob das übergebene Passwort korrekt ist.
     *
     * @param password Zu prüfendes Passwort
     * @return true wenn das Passwort stimmt sonst false
     */
    public boolean checkPassword(String password) {
        return this.passwordHash.equals(this.hashPassword(password));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Zuordnung zu Benutzergruppen">
    /**
     * @return Eine unveränderliche Liste aller Benutzergruppen
     */
    public List<String> getGroups() {
        List<String> groupsCopy = new ArrayList<>();

        this.groups.forEach((groupname) -> {
            groupsCopy.add(groupname);
        });

        return groupsCopy;
    }

    /**
     * Fügt den Benutzer einer weiteren Benutzergruppe hinzu.
     *
     * @param groupname Name der Benutzergruppe
     */
    public void addToGroup(String groupname) {
        if (!this.groups.contains(groupname)) {
            this.groups.add(groupname);
        }
    }

    /**
     * Entfernt den Benutzer aus der übergebenen Benutzergruppe.
     *
     * @param groupname Name der Benutzergruppe
     */
    public void removeFromGroup(String groupname) {
        this.groups.remove(groupname);
    }
    //</editor-fold>

}
