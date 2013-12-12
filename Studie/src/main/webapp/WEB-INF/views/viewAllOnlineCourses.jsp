<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include.jsp" %>


<html>
<head>
<title>Insert title here</title>
</head>
<body>
<div>
<%if (request.isUserInRole("Administrator")){ %>
		        <portlet:renderURL var="categories">
                    <portlet:param name="view" value="onlineCoursesCategories"/>
                </portlet:renderURL>
		        <a href="${categories}"><div style="display:inline;" id='changeBut' class="icon-pcppencil fs20" aria-hidden="true"></div></a>
		    <%} %>
</div>
		    </body>
		    </html>