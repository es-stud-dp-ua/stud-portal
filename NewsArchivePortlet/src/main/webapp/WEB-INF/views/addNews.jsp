<%@ page import="ua.dp.stud.StudPortalLib.model.Category" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.News" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="include.jsp" %>
<%--
    Document   : addnews
    Author     : Tradunsky V.V.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="include.jsp" %>


<%
    Boolean russianLocaleEnabled = request.getLocale().getLanguage().equals("ru");
%>
<%if ((request.isUserInRole("Administrator"))||(request.isUserInRole("Press"))) { %>
<script id="" src="${pageContext.request.contextPath}/js/cropbox.js" type="text/javascript"></script>
<script id="" src="${pageContext.request.contextPath}/js/edit.js" type="text/javascript"></script>


<portlet:defineObjects/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    </head>
    <body>

    <portlet:renderURL var="home">
        <portlet:param name="nAction" value="home"/>
    </portlet:renderURL>

    <portlet:actionURL var="actionLink" name="addNews">
    </portlet:actionURL>
    <div class="portlet-content-controlpanel fs20">
        <a href="${home}">
            <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
        </a>
    </div>

    <liferay-ui:error key="no-images" message='<spring:message code="form.noImages"/>'/>
    <c:if test="${exception != null}">
        ${exception}
    </c:if>

    <div style="width: 900px;">
       <%@include file="NewsAddEditForm.jsp" %>
    </div>

    

</body>
</html>
<%}%>

