/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.web;


import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.ejb.KundeBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.ejb.KundenListBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.ejb.TaskBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.jpa.Kunde;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.jpa.Task;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.jpa.TaskStatus;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die tabellarische Auflisten der Aufgaben.
 */
@WebServlet(urlPatterns = {"/app/tasks/list/"})
public class TaskListServlet extends HttpServlet {

    @EJB
    private KundeBean kundebean;
    
    @EJB
    private TaskBean taskBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
       // request.setAttribute("kunden", this.kundeBean.findAllSorted());
        request.setAttribute("kunde", this.kundebean.findAllSorted());
        request.setAttribute("statuses", TaskStatus.values());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchkunde = request.getParameter("search_kunde");
        String searchStatus = request.getParameter("search_status");

        // Anzuzeigende Aufgaben suchen
       
        TaskStatus status = null;
        
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
                status = TaskStatus.valueOf(searchStatus);
            } catch (IllegalArgumentException ex) {
                status = null;
            }

        }

        List<Task> tasks = this.taskBean.search(searchText, kunde, status);
        request.setAttribute("tasks", tasks);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/tasks/task_list.jsp").forward(request, response);
    }
}
