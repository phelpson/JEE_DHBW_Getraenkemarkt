package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.web;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.ejb.BestellungBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.KundenListBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Kunde;
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

@WebServlet(urlPatterns = {"/app/bestellungen/kunden/"})
public class KundenListServlet extends HttpServlet {

    @EJB
    KundenListBean kundelistbean;

    @EJB
    BestellungBean bestellungBean;

    @EJB
    ValidationBean validationBean;

//    Gibt eine List von Kunden zurück, die mittels einer Tickbox anzuhaken sind.
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Alle vorhandenen Kunden ermitteln
        request.setAttribute("kunden", this.kundelistbean.findAllSorted());
       
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/bestellungen/kunden_list.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("kunden_form");
    }
}
