/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.web;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.MitarbeiterBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.KundeBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.KundeEntity;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.MitarbeiterEntity;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
import java.io.IOException;
import java.text.DateFormat;
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
    
    @EJB
    ValidationBean validationBean;
            
    @EJB
    UserBean userBean;
    
    @EJB
    KundeBean kundeBean;
    
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
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Formulareingaben auslesen        
        String username     = request.getParameter("signup_username");
        String password1    = request.getParameter("signup_password1");
        String password2    = request.getParameter("signup_password2");
        
        String companyname  = request.getParameter("signup_companyname");
        String address      = request.getParameter("signup_street");
        int plz             = Integer.parseInt(request.getParameter("signup_plz"));
        
        String usage        = request.getParameter("signup_usage");
        String email        = request.getParameter("signup_email");
        String givenname    = request.getParameter("signup_givenname");
        String name         = request.getParameter("signup_name");
        
        String disAttribut = usage;
        DateFormat date = DateFormat.getDateInstance();
        
        if (usage.equals("Kunde") == true) {
            KundeEntity kunde = new KundeEntity("das ist ein test");
            disAttribut = "Kunde";
        }
        else if (usage.equals("Getränkemarkt Mitarbeiter") == true) {
            MitarbeiterEntity mitarbeiter = new MitarbeiterEntity(date);
            disAttribut = "Mitarbeiter";
        }
        
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
        
        List<String> errors = this.validationBean.validate(user);
        this.validationBean.validate(user.getPassword(), errors);
        
        if (password1 != null && password2 != null && !password1.equals(password2)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
        }
        
        // Neuen Benutzer anlegen
        if (errors.isEmpty()) {
            try {
                this.userBean.signup(username, 
                password1,
                email,
                givenname,
                name,
                address,
                plz,
                disAttribut);
            } catch (UserBean.UserAlreadyExistsException ex) {
                errors.add(ex.getMessage());
            }
        }
        
        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
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
