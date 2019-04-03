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
        Liste der Bestellungen
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/bestellung_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/bestellungen/bestellung/new/"/>">Bestellung anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/bestellungen/kunden/"/>">Kunden anzeigen</a>
        </div>
        
        <div class="menuitem">
            <a href="<c:url value="/app/update/"/>">Userdaten verwalten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Beschreibung"/>

            <select name="search_kunde">
                <option value="">Alle Kunden</option>

                <c:forEach items="${kunde}" var="kunde">
                    <option value="${kunde.id}" ${param.search_kunde == kunde.id ? 'selected' : ''}>
                        <c:out value="${kunde.name}" />
                    </option>
                </c:forEach>
            </select>

            <select name="search_status">
                <option value="">Alle Stati</option>

                <c:forEach items="${statuses}" var="status">
                    <option value="${status}" ${param.search_status == status ? 'selected' : ''}>
                        <c:out value="${status.label}"/>
                    </option>
                </c:forEach>
            </select>

            <button class="icon-search" type="submit">
                Suchen
            </button>
        </form>

        <%-- Gefundene Bestellungen --%>
        <c:choose>
            <c:when test="${empty bestellungen}">
                <p>
                    Es wurden keine Bestellungen gefunden. 🐈
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.web.WebUtils"/>
                
                <table  id="list_box" >
                    <thead>
                        <tr>
                            <th>Bezeichnung</th>
                            <th>Kunde</th>
                            <th>Eigentümer</th>
                            <th>Status</th>
                            <th>Fällig am</th>
                        </tr>
                    </thead>
                    <c:forEach items="${bestellungen}" var="bestellung">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/bestellungen/bestellung/${bestellung.id}/"/>">
                                    <c:out value="${bestellung.shortText}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${bestellung.kunde.name}"/>
                            </td>
                            <td>
                                <c:out value="${bestellung.owner.username}"/>
                            </td>
                            <td>
                                <c:out value="${bestellung.status.label}"/>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(bestellung.dueDate)}"/>
                                <c:out value="${utils.formatTime(bestellung.dueTime)}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>