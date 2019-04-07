package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.web;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.MitarbeiterBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.KundeBean;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/app/update/"})
public class UpdateServlet extends HttpServlet {
    
    @EJB
    ValidationBean validationBean;
            
    @EJB
    UserBean userBean;
    
    @EJB
    KundeBean kundeBean;
    
    @EJB
    MitarbeiterBean mitarbeiterBean;
    
    // Aktuelle Userdaten anzeigen
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = this.userBean.getCurrentUser();
        
        if (session.getAttribute("update_form") == null) {
            // Keine Formulardaten mit fehlergetRequestedbestellunghaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("update_form", this.createUpdateForm(user));
        }
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/updateUser.jsp");
        dispatcher.forward(request, response);
        
        // Alte Formulardaten aus der Session entfernen
       session.removeAttribute("update_form");
    }
    
    // Aktuelle Userdaten überspeichern und aktualisieren
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Formulareingaben auslesen        
        String username     = request.getParameter("update_username");
        
        String address      = request.getParameter("update_street");
        int plz             = 000000;
        String nfeMessage   = null;
        
        // Parse Integer PLZ, und facnge die entstehende Exception
        // Exception-Text wird zunächst in Variable gespeichert, um dann an der richtigen Stelle in das Errors String-Array
        // übergeben zu werden.
        try {
            plz             = Integer.parseInt(request.getParameter("update_plz"));
        } catch(NumberFormatException nfe) {
            nfeMessage      = "Bitte geben Sie einen numerischen ganzzahligen Wert für die PLZ ein.";
        }
        
        String email        = request.getParameter("update_email");
        String givenname    = request.getParameter("update_givenname");
        String name         = request.getParameter("update_name");
        
        
        List<String> errors;
        
        //aktuell eingeloggten User holen
        User user = userBean.getCurrentUser();
        
        //Eingegebenen Werte dem Userobjekt zuweisen
        user.setNachname(name);
        user.setVorname(givenname);
        user.setUsername(username);
        user.setAdresse(address);
        user.setEmail(email);
        user.setPlz(plz);
        
        //User Objekt validieren
        errors = this.validationBean.validate(user);
        
        if (nfeMessage != null) {
            errors.add(nfeMessage);
        }
        
        // Neuen Benutzer anlegen
        if (errors.isEmpty()) {
            this.userBean.update(user);    
        }   
        
        
        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            //request.login(username, password1);
            response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);
            
            HttpSession session = request.getSession();
            session.setAttribute("update_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
        }
    }
    //Befüllung der Maske mit den entsprechenden Daten des eingeloggten Benutzers
    //um Änderungen der Daten zuzulassen
    private FormValues createUpdateForm(User user) {
        Map<String, String[]> values = new HashMap<>();

        values.put("update_username", new String[]{
            user.getUsername()
        });
        values.put("update_email", new String[]{
            user.getEmail()
            });
        values.put("update_street", new String[]{
            user.getAdresse()
            });
        values.put("update_givenname", new String[]{
            user.getVorname()
            });
        values.put("update_name", new String[]{
            user.getNachname()
            });
        values.put("update_plz", new String[]{
            Integer.toString(user.getPlz())
            });
        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }
}

      