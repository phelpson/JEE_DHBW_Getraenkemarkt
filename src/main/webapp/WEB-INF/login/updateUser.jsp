
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Update User
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
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
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                    <%-- Eingabefelder --%>
                    <label for="update_username">
                        Benutzername:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input id="grau" type="text" name="update_username" value="${update_form.values["update_username"][0]}" readonly="readonly"   >
                    </div>
                    
                    <%-- Vorname --%>
                    <label for="update_givenname">
                        Vorname
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="update_givenname" value="${update_form.values["update_givenname"][0]}">
                    </div>
                    
                    <%-- Nachname --%>
                    <label for="update_name">
                        Nachname
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="update_name" value="${update_form.values["update_name"][0]}">
                    </div>

                    
                    <%-- E-Mail Adresse --%>
                    <label for="update_email">
                        E-Mail:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="update_email" value="${update_form.values["update_email"][0]}">
                    </div>
                    
                    
                    <%-- Adressdaten - Straße --%>
                    <label for="update_street">
                        Strasse + Hausnummer
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="update_street" value="${update_form.values["update_street"][0]}">
                    </div>
                    
                    <%-- Adressdaten - PLZ --%>
                    <label for="update_plz">
                        PLZ
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="number" name="update_plz" value="${update_form.values["update_plz"][0]}">
                    </div>
                                                     
                    
                    <label for="Pflichtfelder">
                        <span class="required">*</span>
                        Pflichtfelder
                    </label>
                                        
 
                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side" style="margin-top: 30px;">
                        <button type="submit">
                            Update User
                        </button>
                    </div>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty update_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${update_form.errors}" var="error">
                            <li id="errors">${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>