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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * Statische Hilfsmethoden
 */
public class WebUtils {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    /**
     * Stellt sicher, dass einer URL der Kontextpfad der Anwendung vorangestellt
     * ist. Denn sonst ruft man aus Versehen Seiten auf, die nicht zur eigenen
     * Webanwendung gehören.
     *
     * @param request HttpRequest-Objekt
     * @param url Die aufzurufende URL
     * @return Die vollständige URL
     */
    public static String appUrl(HttpServletRequest request, String url) {
        return request.getContextPath() + url;
    }
    
    /**
     * Anhängen eines Query-Parameters an eine vorhandene URL. Enthält die
     * URL noch keine Parameter, wird der Parameter als ?name=wert angehängt,
     * sonst als &name=wert.
     * 
     * @param url Zu verändernde URL
     * @param param Name des Parameters
     * @param value Wert des Parameters
     * @return Verlängerte URL
     */
    public static String addQueryParameter(String url, String param, String value) {
        if (!url.contains("?")) {
            url += "?";
        } else {
            url += "&";
        }
        
        try {
            url += URLEncoder.encode(param, "utf-8") + "=" + URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WebUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return url;
    }

    /**
     * Formatiert ein Datum für die Ausgabe, z.B. 31.12.9999
     *
     * @param date Datum
     * @return String für die Ausgabe
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    /**
     * Formatiert eine Uhrzeit für die Ausgabe, z.B. 11:50:00
     *
     * @param time Uhrzeit
     * @return String für die Ausgabe
     */
    public static String formatTime(Time time) {
        return TIME_FORMAT.format(time);
    }

    /**
     * Erzeugt ein Datumsobjekt aus dem übergebenen String, z.B. 03.06.1986
     *
     * @param input Eingegebener String
     * @return Datumsobjekt oder null bei einem Fehler
     */
    public static Date parseDate(String input) {
        try {
            java.util.Date date = DATE_FORMAT.parse(input);
            return new Date(date.getTime());
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * Erzeugt ein Uhrzeitobjekt aus dem übergebenen String, z.B. 09:20:00
     *
     * @param input Eingegebener String
     * @return Uhrzeitobjekt oder null bei einem Fehler
     */
    public static Time parseTime(String input) {
        try {
            java.util.Date date = TIME_FORMAT.parse(input);
            return new Time(date.getTime());
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * Formatiert eine Double-Zahl für die Ausgabe, z.B. 8,15
     * 
     * @param d Zahl
     * @return String für die Ausgabe
     */
    public String formatDouble(Double d) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.GERMANY));
        return df.format(d);
    }
    
    /**
     * Formatiert eine Integer-Zahl für die Ausgabe, z.B. 2.450
     * 
     * @param i Zahl
     * @return String für die Ausgabe
     */
    public String formatInteger(int i) {
        return String.format("%,d", i);
    }
}
