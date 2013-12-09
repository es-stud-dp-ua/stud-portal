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

<%if ((request.isUserInRole("Administrator"))||(request.isUserInRole("Press"))) {%>
<script id="" src="${pageContext.request.contextPath}/js/cropbox.js" type="text/javascript"></script>
<script id="" src="${pageContext.request.contextPath}/js/edit.js" type="text/javascript"></script>

<%
    ImageService imageService = (ImageService) pageContext.findAttribute("imageService");
    Boolean russianLocaleEnabled = request.getLocale().getLanguage().equals("ru");
    News news = (News) request.getAttribute("news");
%>
<portlet:defineObjects/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    </head>
    <body>
    

    <portlet:renderURL var="Home">
        <portlet:param name="newsID" value="<%=news.getId().toString()%>"/>
    </portlet:renderURL>

    <portlet:actionURL var="actionLink" name="editNews"></portlet:actionURL>
    <div class="portlet-content-controlpanel fs20">
        <a href="${Home}">
            <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
        </a>
        <% if ((request.isUserInRole("Administrator"))||(request.isUserInRole("Press"))) { %>
        <a style="margin-left: 10px;"
           href='<portlet:renderURL><portlet:param name="newsId" value="<%=news.getId().toString()%>"/><portlet:param name="mode" value="delete" /></portlet:renderURL>'
           onclick='return confirm("<spring:message code="form.confDelete"/>")'>
            <div class="panelbtn panelbtn-right fs20 icon-pcpremove" aria-hidden="true"></div>
        </a>
        <%}%>
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
