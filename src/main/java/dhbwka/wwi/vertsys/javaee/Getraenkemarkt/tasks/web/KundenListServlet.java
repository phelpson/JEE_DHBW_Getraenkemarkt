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

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.ejb.CategoryBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.ejb.TaskBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.jpa.Category;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.jpa.Task;
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

/**
 * Seite zum Anzeigen und Bearbeiten der Kategorien. Die Seite besitzt ein
 * Formular, mit dem ein neue Kategorie angelegt werden kann, sowie eine Liste,
 * die zum Löschen der Kategorien verwendet werden kann.
 */
@WebServlet(urlPatterns = {"/app/tasks/categories/"})
public class KundenListServlet extends HttpServlet {

    @EJB
    CategoryBean categoryBean;

    @EJB
    TaskBean taskBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Alle vorhandenen Kategorien ermitteln
        request.setAttribute("categories", this.categoryBean.findAllSorted());

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/tasks/kunden_list.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("categories_form");
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
            case "create":
                this.createCategory(request, response);
                break;
            case "delete":
                this.deleteCategories(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue Kategorie anlegen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void createCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        String name = request.getParameter("name");

        Category category = new Category(name);
        List<String> errors = this.validationBean.validate(category);

        // Neue Kategorie anlegen
        if (errors.isEmpty()) {
            this.categoryBean.saveNew(category);
        }

        // Browser auffordern, die Seite neuzuladen
        if (!errors.isEmpty()) {
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("categories_form", formValues);
        }

        response.sendRedirect(request.getRequestURI());
    }

    /**
     * Aufgerufen in doPost(): Markierte Kategorien löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Markierte Kategorie IDs auslesen
        String[] categoryIds = request.getParameterValues("category");

        if (categoryIds == null) {
            categoryIds = new String[0];
        }

        // Kategorien löschen
        for (String categoryId : categoryIds) {
            // Zu löschende Kategorie ermitteln
            Category category;

            try {
                category = this.categoryBean.findById(Long.parseLong(categoryId));
            } catch (NumberFormatException ex) {
                continue;
            }

            if (category == null) {
                continue;
            }

            // Bei allen betroffenen Aufgaben, den Bezug zur Kategorie aufheben
            List<Task> tasks = category.getTasks();

            if (tasks != null) {
                tasks.forEach((Task task) -> {
                    task.setCategory(null);
                    this.taskBean.update(task);
                });
            }

            // Und weg damit
            this.categoryBean.delete(category);
        }

        // Browser auffordern, die Seite neuzuladen
        response.sendRedirect(request.getRequestURI());
    }

}
