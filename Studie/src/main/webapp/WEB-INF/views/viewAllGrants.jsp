<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="ua.dp.stud.studie.model.Grant" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page isELIgnored="false" %>
<%@include file="include.jsp" %>

<% ImageService imageServices = (ImageService) pageContext.findAttribute("imageService"); %>


<%@ taglib prefix="theme" uri="http://liferay.com/tld/theme" %>
<%
    Collection<Grant> grants = (Collection) request.getAttribute("grants");
%>

<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<div>

		    <% if (!grants.isEmpty()) {
 for (Grant grant : grants){%>
     <div id="newsTable" style="padding-top: 15px;float: center">
                     <div width="100%">
                         <div class="newsHeader" style="padding-top: 50px; padding-left: 175px; font-size: 20pt; ">
                            <b>
                                 <a href='
                                 <portlet:renderURL>
                                     <portlet:param name="id" value="<%=grant.getId().toString()%>"/>
                                         <portlet:param name="view" value="singleOnlineCourse"/>
                                 </portlet:renderURL>
                                 '><%=grant.getUniversity()%>
                                 </a>
                            </b>
                            </div>
                     
           			</div>
     </div>
           <br/><br/>
             <%}%>

<%}%>
     </div>

		    </body>
 </html>