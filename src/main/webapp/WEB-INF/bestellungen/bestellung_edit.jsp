<%-- 
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        <c:choose>
            <c:when test="${edit}">
                Bestellung bearbeiten
            </c:when>
            <c:otherwise>
                Bestellung anlegen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/bestellung_edit.css"/>" />
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script>
        $( function() {
          $( ".datepicker" ).datepicker({
                dateFormat: "dd.mm.yy",
                dayNamesMin: [ "So", "Mo", "Di", "Mi", "Do", "Fr", "Sa" ],
                monthNames: [ "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember" ]});
        } );
        </script>
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>
        
        <div class="menuitem">
            <a href="<c:url value="/app/bestellungen/list/"/>">Liste</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <%-- Eingabefelder --%>
                <label for="bestellung_owner">Eigentümer:</label>
                <div class="side-by-side">
                    <input type="text" name="bestellung_owner" value="${bestellung_form.values["bestellung_owner"][0]}" readonly="readonly">
                </div>

                <label for="bestellung_kunde">Kunde</label>
                <div class="side-by-side">
                    <select name="bestellung_kunde">

                        <c:forEach items="${kunden}" var="kunde">
                            <option value="${kunde.id}" ${bestellung_form.values["bestellung_kunde"][0] == kunde.id.toString() ? 'selected' : ''}>
                                <c:out value="${kunde.name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <label for="bestellung_due_date">
                    Wunschdatum der Lieferung:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input class="datepicker" type="text" name="bestellung_due_date" value="${bestellung_form.values["bestellung_due_date"][0]}">
                    <input type="text" name="bestellung_due_time" value="${bestellung_form.values["bestellung_due_time"][0]}">
                </div>
                
                

                <label for="bestellung_status">
                    Status:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side margin">
                    <select name="bestellung_status">
                        <c:forEach items="${statuses}" var="status">
                            <option value="${status}" ${bestellung_form.values["bestellung_status"][0] == status ? 'selected' : ''}>
                                <c:out value="${status.label}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <label for="bestellung_getraenk">
                    Getränk:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side margin">
                    <select name="bestellung_getraenk">
                        <c:forEach items="${getraenk}" var="getraenk">
                            <option value="${getraenk}" ${bestellung_form.values["bestellung_getraenk"][0] == getraenk ? 'selected' : ''}>
                                <c:out value="${getraenk.label}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <label for="bestellung_short_text">
                    Bezeichnung:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="bestellung_short_text" value="${bestellung_form.values["bestellung_short_text"][0]}">
                </div>

                <label for="bestellung_long_text">
                    Beschreibung:
                </label>
                <div class="side-by-side">
                    <textarea name="bestellung_long_text"><c:out value="${bestellung_form.values['bestellung_long_text'][0]}"/></textarea>
                </div>

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>

                    <c:if test="${edit}">
                        <button class="icon-trash" type="submit" name="action" value="delete">
                            Löschen
                        </button>
                    </c:if>
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty bestellung_form.errors}">
                <ul class="errors">
                    <c:forEach items="${bestellung_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>
