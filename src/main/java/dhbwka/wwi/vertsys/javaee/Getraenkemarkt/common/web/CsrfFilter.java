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

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * HTTP-Filter, der alle Anfragen abfängt, bevor sie das eigentliche Servlet
 * erreichen und CSRF-Attacken abwehrt.
 *
 * Vgl. https://de.wikipedia.org/wiki/Cross-Site-Request-Forgery Vgl.
 * https://dzone.com/articles/preventing-csrf-java-web-apps
 */
@WebFilter(filterName = "CsrfFilter", urlPatterns = {"/*"})
public class CsrfFilter implements Filter {

    public static final int MAX_CACHE_SIZE = 500;
    public static final int MAX_TOKEN_AGE = 1000 * 60 * 60 * 3; // 3 Stunden

    /**
     * Diese Methode wird vom Webcontainer bei jeder HTTP-Anfrage aufgerufen.
     * Handelt es sich um eine GET-Anfrage, wird ein neues CSRF-Token generiert
     * und im Request-Attribut "csrf_token" abgelegt. Handelt es sich um eine
     * andere Anfrage, wird geprüft, ob das "csrf_token" an den Server zurück
     * gesendet wurde und gültig ist.
     *
     * @param req HTTP-Anfrage
     * @param res HTTP-Antwort
     * @param chain Filterkette
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        switch (request.getMethod()) {
            case "GET":
                this.insertCsrfToken(request, response);
                break;
            default:
                this.checkCsrfToken(request, response);
        }

        chain.doFilter(request, response);
    }

    /**
     * Neues CSRF-Token generieren, in der Session speichern und der Seite als
     * Request-Attribut mitgeben. Innerhalb der HTML-Seite muss darauf geachtet
     * werden, das Token im Form Field "csrf_token" wieder an den Server
     * zurückzuschicken, wenn ein Formular oder dergleichen abgeschickt wird.
     * Jedes Token kann nur einmal genutzt werden.
     *
     * @param request HTTP-Anfrage
     * @param response HTTP-Antwort
     * @throws IOException
     * @throws ServletException
     */
    private void insertCsrfToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        CsrfToken csrfToken = new CsrfToken();
        request.setAttribute("csrf_token", csrfToken.token);

        List<CsrfToken> tokenCache = getTokenCache(request);

        synchronized (tokenCache) {
            if (tokenCache.size() > MAX_CACHE_SIZE) {
                tokenCache = tokenCache.subList(0, MAX_CACHE_SIZE);
            }

            tokenCache.add(0, csrfToken);
        }
    }

    /**
     * Prüft, ob die abgefangene Anfrage ein gültiges CSRF-Token im
     * Anfrageparameter "csrf_token" enthält.
     *
     * @param request HTTP-Anfrage
     * @param response HTTP-Antwort
     * @throws IOException
     * @throws ServletException
     */
    private void checkCsrfToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String tokenParameter = request.getParameter("csrf_token");

        if (tokenParameter == null) {
            throw new ServletException("CSRF-Token fehlt. Dies könnte ein Angriff sein!");
        }

        List<CsrfToken> tokenCache = this.getTokenCache(request);
        CsrfToken existingToken = null;

        synchronized (tokenCache) {
            for (CsrfToken csrfToken : tokenCache) {
                if (csrfToken.token.equals(tokenParameter)) {
                    existingToken = csrfToken;
                    break;
                }
            }
            
            if (existingToken != null) {
                tokenCache.remove(existingToken);
            }
        }

        if (existingToken == null) {
            throw new ServletException("Ungültiges CSRF-Token empfangen. Dies könnte ein Angriff sein!");
        } else if (System.currentTimeMillis() - existingToken.timestamp > MAX_TOKEN_AGE) {
            throw new ServletException("Empfangenes CSRF-Token ist zu alt. Wahrscheinlich wurde die Seite lange Zeit nicht mehr aufgerufen. Es könnte aber auch ein Angriff sein!");
        }
    }

    /**
     * Liest den Cache mit allen aktuell gültigen CSRF-Tokens aus der Session
     * aus. Existiert noch kein Cache, wird er erzeugt und in der Session
     * abgelegt.
     *
     * @param request HTTP-Anfrage
     * @return Der CSRF-Token-Cache
     */
    private List<CsrfToken> getTokenCache(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<CsrfToken> tokenCache = (List<CsrfToken>) session.getAttribute("csrf_token_cache");

        if (tokenCache == null) {
            tokenCache = new ArrayList<>();
            session.setAttribute("csrf_token_cache", tokenCache);
        }

        return tokenCache;
    }

    //<editor-fold defaultstate="collapsed" desc="Methoden, die wir leider mitschleppen müssen">
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
    //</editor-fold>

}

/**
 * Hilfsklasse für die gespeicherten CSRF-Tokens. Beinhaltet den zufälligen
 * Tokencode sowie einen Zeitstempel.
 */
class CsrfToken implements Serializable {

    public long timestamp = System.currentTimeMillis();
    public String token = UUID.randomUUID().toString().replace("-", "");

}
