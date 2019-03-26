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

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.KundeEntity;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.ejb.CategoryBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.ejb.KundenListBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.ejb.TaskBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.jpa.Category;
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
    private KundenListBean KundenListBean;
    
    @EJB
    private TaskBean taskBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
       // request.setAttribute("categories", this.categoryBean.findAllSorted());
        request.setAttribute("kunden", this.KundenListBean.findAllSortedKunden());
        request.setAttribute("statuses", TaskStatus.values());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchCategory = request.getParameter("search_category");
        String searchStatus = request.getParameter("search_status");

        // Anzuzeigende Aufgaben suchen
        KundeEntity kunde = null;
        TaskStatus status = null;
        
         Category category = null;

        if (searchCategory != null) {
            try {
                kunde = this.KundenListBean.findById(Long.parseLong(searchCategory));
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

        List<Task> tasks = this.taskBean.search(searchText, category, status);
        request.setAttribute("tasks", tasks);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/tasks/task_list.jsp").forward(request, response);
    }
}
