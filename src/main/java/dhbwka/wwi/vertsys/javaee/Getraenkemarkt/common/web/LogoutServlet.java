package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet, dass den Anwender ausloggt (die Session beendet) und ihn dann
 * auf die Startseite weiterleitet.
 */
@WebServlet(urlPatterns = {"/logout/"})
public class LogoutServlet extends HttpServlet {
    
    public static final String URL = "/logout/";

    /**
     * GET-Anfrage: Seite anzeigen
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        request.getSession().invalidate();
        response.sendRedirect(WebUtils.appUrl(request, "/"));
    }

}