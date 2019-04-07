package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.web;


import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.KundeBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.ejb.BestellungBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.Kunde;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Bestellung;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.BestellungStatus;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die tabellarische Auflisten der Bestellungen.
 */
@WebServlet(urlPatterns = {"/app/bestellungen/list/"})
public class BestellungListServlet extends HttpServlet {

    @EJB
    private KundeBean kundebean;
    
    @EJB
    private BestellungBean bestellungBean;

//    Get Methode für das Anzeige der Liste der Bestellungen
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kunden und Statuuus für die Suchfelder ermitteln
        request.setAttribute("kunde", this.kundebean.findAllSorted());
        request.setAttribute("statuses", BestellungStatus.values());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchkunde = request.getParameter("search_kunde");
        String searchStatus = request.getParameter("search_status");

        // Anzuzeigende Bestellungen suchen
        BestellungStatus status = null;
        
        Kunde kunde = null;

        if (searchkunde != null) {
            try {
                kunde = this.kundebean.findById(Long.parseLong(searchkunde));
            } catch (NumberFormatException ex) {
                kunde = null;
            }
        }

        if (searchStatus != null) {
            try {
                status = BestellungStatus.valueOf(searchStatus);
            } catch (IllegalArgumentException ex) {
                status = null;
            }

        }

        List<Bestellung> bestellungen = this.bestellungBean.search(searchText, kunde, status);
        request.setAttribute("bestellungen", bestellungen);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/bestellungen/bestellung_list.jsp").forward(request, response);
    }
}
