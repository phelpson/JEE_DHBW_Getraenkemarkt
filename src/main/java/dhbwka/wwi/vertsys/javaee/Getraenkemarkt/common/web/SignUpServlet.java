package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.web;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.MitarbeiterBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.MitarbeiterEntity;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.KundeBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.Kunde;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet für die Registrierungsseite. Hier kann sich ein neuer Benutzer
 * registrieren. Anschließend wird der auf die Startseite weitergeleitet.
 */
@WebServlet(urlPatterns = {"/signup/"})
public class SignUpServlet extends HttpServlet {
    
    //EJB für die Validierung eines Benutzers
    @EJB
    ValidationBean validationBean;
    //UserBean        
    @EJB
    UserBean userBean;
    //KundeBean
    @EJB
    KundeBean kundeBean;
    //MitarbeiterBean
    @EJB
    MitarbeiterBean mitarbeiterBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/signup.jsp");
        dispatcher.forward(request, response);
        
        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("signup_form");
    }
    
    // Absenden des Registrierungsformulars zum Anlegen von neunen Usern, Kunden oder Mitarbeitern
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Formulareingaben auslesen        
        String username     = request.getParameter("signup_username");
        //passwort eingabe
        String password1    = request.getParameter("signup_password1");
        //passwort bestätigung
        String password2    = request.getParameter("signup_password2");
        //firmenname
        String companyname  = request.getParameter("signup_companyname");
        String address      = request.getParameter("signup_street");
        int plz             = 000000;
        String nfeMessage   = null;
        
        // Parse Integer PLZ, und facnge die entstehende Exception
        // Exception-Text wird zunächst in Variable gespeichert, um dann an der richtigen Stelle in das Errors String-Array
        // übergeben zu werden.
        try {
            plz             = Integer.parseInt(request.getParameter("signup_plz"));
        } catch(NumberFormatException nfe) {
            nfeMessage = "Bitte geben Sie einen numerischen ganzzahligen Wert für die PLZ ein.";
        }
        
        String usage        = request.getParameter("signup_usage");
        String email        = request.getParameter("signup_email");
        String givenname    = request.getParameter("signup_givenname");
        String name         = request.getParameter("signup_name");
        
        // Diskriminierendes Attribut für die DB Zuordnung zwischen Mitarbeiter und Kunde
        String disAttribut = usage;
        //error Liste erzeugen
        List<String> errors;
        
        //Kunde erzeugen 
        Kunde kunde = new Kunde (companyname);
        // Eingaben prüfen
        User user = new User(
                username, 
                password1,
                email,
                givenname,
                name,
                address,
                plz,
                disAttribut
        );
        //Zuweisung der Verbindung zwischen dem User und dem Kunden
        kunde.setUser(user);
        //Mitarbeiter erzeugen
        MitarbeiterEntity mitarbeiter = new MitarbeiterEntity (this.mitarbeiterBean.generiereEintrittsdatum());
        //Zuweisung der Verbindung zwischen dem User und dem Mitarbeiter
        mitarbeiter.setUser(user);
        
        //validierung des User Objekts in der Datenbank
        errors = this.validationBean.validate(user);
        
        this.validationBean.validate(user.getPassword(), errors);
        
        //Validierung des Kundenobjekts
        List<String> kunden_errors = this.validationBean.validate(kunde);
        this.validationBean.validate(kunde, errors);
        
        //Validierung des Mitarbeiterobjekts
        List<String> mitarbeiter_errors = this.validationBean.validate(mitarbeiter);
        this.validationBean.validate(mitarbeiter, errors);
        //Überprüfung der Übereinstimmung der beiden eingegebenen Passwörter
        if (password1 != null && password2 != null && !password1.equals(password2)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
        }
        
        if (nfeMessage != null) {
            errors.add(nfeMessage);
        }
        
        // Neuen Benutzer anlegen wenn keine Fehlermeldungen vorliegen 
        if (errors.isEmpty() && kunden_errors.isEmpty() &&  mitarbeiter_errors.isEmpty()) {
            try {
                this.userBean.signup(user);
                //setzten des DisAttributes je nach Auswahl des Nutzers im Dropdown (Kunde/Mitarbeiter)
                if (usage.equals("Kunde") == true) {
                    //setzen des DisAttributs 
                    user.setDisAttribut("Kunde");
                    //Speichern des Kundenobjekts
                    this.kundeBean.saveNew(kunde);
                }
                else if (usage.equals("Mitarbeiter") == true) {
                    //setzen des DisAttributs
                    user.setDisAttribut("Mitarbeiter");
                    //Speicherung des Mitarbeiterobjekts
                    this.mitarbeiterBean.saveNew(mitarbeiter);    
                } 

                         
            } catch (UserBean.UserAlreadyExistsException ex) {
                errors.add(ex.getMessage());
            }
        }   
        
        
        // Weiter zur nächsten Seite
        if (errors.isEmpty() && kunden_errors.isEmpty() &&  mitarbeiter_errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            request.login(username, password1);
            response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);
            
            HttpSession session = request.getSession();
            session.setAttribute("signup_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
        }
    }
    
}
