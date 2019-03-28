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

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Signup
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/logout/"/>">Einloggen</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                    <%-- Eingabefelder --%>
                    <label for="signup_username">
                        Benutzername:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_username" value="${signup_form.values["signup_username"][0]}">
                    </div>

                    <label for="signup_password1">
                        Passwort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="signup_password1" value="${signup_form.values["signup_password1"][0]}">
                    </div>

                    <label for="signup_password2">
                        Passwort (wdh.):
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="signup_password2" value="${signup_form.values["signup_password2"][0]}">
                    </div>
                    
                    <%-- E-Mail Adresse --%>
                    <label for="signup_email">
                        E-Mail:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_email" value="${signup_form.values["signup_email"][0]}">
                    </div>
                    
                    <%-- Firmenname --%>
                    <label for="signup_companyname">
                        Firmenname
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_companyname" value="${signup_form.values["signup_companyname"][0]}">
                    </div>
                    
                    <%-- Adressdaten - Straße --%>
                    <label for="signup_street">
                        Strasse + Hausnummer
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_street" value="${signup_form.values["signup_street"][0]}">
                    </div>
                    
                    <%-- Adressdaten - PLZ --%>
                    <label for="signup_plz">
                        PLZ
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="number" name="signup_plz" value="${signup_form.values["signup_plz"][0]}">
                    </div>
                    
                    <%-- Dropdown Feld --%>
                    <label for="signup_usage">
                        Art der Nutzung
                        <span class="required">*</span>
                    </label>
                    <select name="signup_usage" class="form-control" style="width=200px;">
                        <%-- <option value="${signup_form.values["signup_usage"][0]}">Lieferant</option> --%>
                        <option value="Kunde">Kunde</option>
                        <option value="Mitarbeiter">Mitarbeiter</option>
                        <%-- <option value="${signup_form.values["signup_usage"][0]}">Kunde</option>
                        <option value="${signup_form.values["signup_usage"][1]}">Getränkemarkt Mitarbeiter</option> --%>
                    </select>
                    
                    
                    <%-- Vorname --%>
                    <label for="signup_givenname">
                        Vorname
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_givenname" value="${signup_form.values["signup_givenname"][0]}">
                    </div>
                    
                    <%-- Nachname --%>
                    <label for="signup_name">
                        Nachname
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="signup_name" value="${signup_form.values["signup_name"][0]}">
                    </div>
                    
                    <label for="Pflichtfelder">
                        <span class="required">*</span>
                        Pflichtfelder
                    </label>
 
                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side" style="margin-top: 30px;">
                        <button class="icon-pencil" type="submit">
                            Registrieren
                        </button>
                    </div>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty signup_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${signup_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>