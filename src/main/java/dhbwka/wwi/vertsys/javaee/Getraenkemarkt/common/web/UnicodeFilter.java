/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * HTTP-Filter, der alle Anfragen abfängt, bevor sie das eigentliche Servlet
 * erreichen und sicherstellt, dass Anfrage und Antwort UTF-8 kodiert werden.
 */
@WebFilter(filterName = "UnicodeFilter", urlPatterns = {"/*"})
public class UnicodeFilter implements Filter {

    /**
     * Diese Methode wird vom Webcontainer bei jeder HTTP-Anfrage aufgerufen.
     * Sie stellt sicher, dass der Webcontainer Anfrage und Antwort als UTF-8
     * kodiert behandelt.
     *
     * @param request HTTP-Anfrage
     * @param response HTTP-Antwort
     * @param chain Filterkette
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        
        chain.doFilter(request, response);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Lifecycle Methoden">
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
    //</editor-fold>
    
}
