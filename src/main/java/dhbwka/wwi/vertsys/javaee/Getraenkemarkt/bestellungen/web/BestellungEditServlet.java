package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.web;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.KundeBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.ejb.BestellungBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.GetraenkeEnum;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Bestellung;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.BestellungStatus;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anlegen oder Bearbeiten einer Bestellung.
 */
@WebServlet(urlPatterns = "/app/bestellungen/bestellung/*")
public class BestellungEditServlet extends HttpServlet {

    @EJB
    BestellungBean bestellungBean;
    
    @EJB
    KundeBean kundeBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;
    

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kunden und Stati für die Suchfelder ermitteln
        request.setAttribute("kunden", this.kundeBean.findAllSorted());
        request.setAttribute("statuses", BestellungStatus.values());
        request.setAttribute("getraenk", GetraenkeEnum.values());
        String attr = userBean.getCurrentUser().getDisAttribut();
        request.setAttribute("rolle", attr);
        // Zu bearbeitende Bestellung einlesen
        HttpSession session = request.getSession();

        Bestellung bestellung = this.getRequestedbestellung(request);
        request.setAttribute("edit", bestellung.getId() != 0);
                                
        if (session.getAttribute("bestellung_form") == null) {
            // Keine Formulardaten mit fehlergetRequestedbestellunghaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("bestellung_form", this.createbestellungForm(bestellung));
        }

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/bestellungen/bestellung_edit.jsp").forward(request, response);
        session.removeAttribute("bestellung_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.savebestellung(request, response);
                break;
            case "delete":
                this.deletebestellung(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Aufgabe speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void savebestellung(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String bestellungkunde = request.getParameter("bestellung_kunde");
        String bestellungDueDate = request.getParameter("bestellung_due_date");
        String bestellungDueTime = request.getParameter("bestellung_due_time");
        String bestellungentatus = request.getParameter("bestellung_status");
        String bestellungGetraenk = request.getParameter("bestellung_getraenk");
        String bestellungenhortText = request.getParameter("bestellung_short_text");
        String bestellungLongText = request.getParameter("bestellung_long_text");

        Bestellung bestellung = this.getRequestedbestellung(request);

        if (bestellungkunde != null && !bestellungkunde.trim().isEmpty()) {
            try {
                bestellung.setkunde(this.kundeBean.findById(Long.parseLong(bestellungkunde)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }

        Date dueDate = WebUtils.parseDate(bestellungDueDate);
        Time dueTime = WebUtils.parseTime(bestellungDueTime);

        if (dueDate != null) {
            bestellung.setDueDate(dueDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (dueTime != null) {
            bestellung.setDueTime(dueTime);
        } else {
            errors.add("Die Uhrzeit muss dem Format hh:mm:ss entsprechen.");
        }

        try {

            bestellung.setStatus(BestellungStatus.valueOf(bestellungentatus));
            bestellung.setGetraenkEnum(GetraenkeEnum.valueOf(bestellungGetraenk));

        } catch (IllegalArgumentException ex) {
            errors.add("Der ausgewählte Status ist nicht vorhanden.");
        }

        bestellung.setShortText(bestellungenhortText);
        bestellung.setLongText(bestellungLongText);

        this.validationBean.validate(bestellung, errors);
        Boolean validateOwner = this.validationBean.validateOwner(bestellung);
        
        if(validateOwner == false){
            errors.add("Die ausgewählte Bestellung kann nur vom Owner bearbeitet werden!");
        }
   
            // Datensatz speichern
            if (errors.isEmpty()) {
                this.bestellungBean.update(bestellung);
            }

            // Weiter zur nächsten Seite
            if (errors.isEmpty()) {
                // Keine Fehler: Startseite aufrufen
                response.sendRedirect(WebUtils.appUrl(request, "/app/bestellungen/list/"));
            } else {
                // Fehler: Formuler erneut anzeigen
                FormValues formValues = new FormValues();
                formValues.setValues(request.getParameterMap());
                formValues.setErrors(errors);

                HttpSession session = request.getSession();
                session.setAttribute("bestellung_form", formValues);

                response.sendRedirect(request.getRequestURI());
            }
       
    }

    /**
     * Aufgerufen in doPost: Vorhandene Aufgabe löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deletebestellung(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();
        
          // Datensatz löschen
        Bestellung bestellung = this.getRequestedbestellung(request);
        
        //validation of delete prozess. only owner can delete 
          Boolean validateOwner = this.validationBean.validateOwner(bestellung);
        
         if(validateOwner == false){
            errors.add("Die ausgewählte Bestellung kann nur vom Owner gelöscht werden!");
        }
         
        if (errors.isEmpty()) {
             this.bestellungBean.delete(bestellung);
              // Zurück zur Übersicht
             response.sendRedirect(WebUtils.appUrl(request, "/app/bestellungen/list/"));
         }else{
            // Fehler: Formuler erneut anzeigen
                FormValues formValues = new FormValues();
                formValues.setValues(request.getParameterMap());
                formValues.setErrors(errors);

                HttpSession session = request.getSession();
                session.setAttribute("bestellung_form", formValues);

                response.sendRedirect(request.getRequestURI());
        }
       
    }

    /**
     * Zu bearbeitende Bestellung aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Bestellung getRequestedbestellung(HttpServletRequest request) {
        User user = this.userBean.getCurrentUser();
        Bestellung bestellung = new Bestellung();
        bestellung.setOwner(user);
        if("Kunde".equals(user.getDisAttribut())){
            bestellung.setkunde(this.kundeBean.findByUsername(user).get(0));           
        }
        
        bestellung.setDueDate(new Date(System.currentTimeMillis()));
        bestellung.setDueTime(new Time(System.currentTimeMillis()));

        // ID aus der URL herausschneiden
        String bestellungId = request.getPathInfo();

        if (bestellungId == null) {
            bestellungId = "";
        }

        bestellungId = bestellungId.substring(1);

        if (bestellungId.endsWith("/")) {
            bestellungId = bestellungId.substring(0, bestellungId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            bestellung = this.bestellungBean.findById(Long.parseLong(bestellungId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return bestellung;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param bestellung Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createbestellungForm(Bestellung bestellung) {
        Map<String, String[]> values = new HashMap<>();

        values.put("bestellung_owner", new String[]{
            bestellung.getOwner().getUsername()
        });

        if (bestellung.getkunde() != null) {
            values.put("bestellung_kunde", new String[]{
                "" + bestellung.getkunde().getId()
            });
        }

        values.put("bestellung_due_date", new String[]{
            WebUtils.formatDate(bestellung.getDueDate())
        });

        values.put("bestellung_due_time", new String[]{
            WebUtils.formatTime(bestellung.getDueTime())
        });

        values.put("bestellung_status", new String[]{
            bestellung.getStatus().toString()
        });
        
        // Getraenk Enum mit Hilfsklasse zwischenspeichern
        values.put("bestellung_getraenk", new String[] {
            bestellung.getGetraenkEnum().toString()
        });
       
        values.put("bestellung_short_text", new String[]{
            bestellung.getShortText()
        });

        values.put("bestellung_long_text", new String[]{
            bestellung.getLongText()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }
    


}
