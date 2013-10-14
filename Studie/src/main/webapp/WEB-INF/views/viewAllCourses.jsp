<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
<portlet:defineObjects/>
<%
    Collection<Organization> orgs = (Collection) request.getAttribute("organisations");
    Integer pagesCount = (Integer) request.getAttribute("pagesCount");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    int leftPageNumb = (Integer) request.getAttribute("leftPageNumb");
    int rightPageNumb = (Integer) request.getAttribute("rightPageNumb");
    boolean skippedBeginning = (Boolean) request.getAttribute("skippedBeginning");
    boolean skippedEnding = (Boolean) request.getAttribute("skippedEnding");
    OrganizationType type = null;
    if (request.getAttribute("type") != null)
        type = (OrganizationType) request.getAttribute("type");
    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    String imagePath = new StringBuilder(themeDisplay.getPortalURL()).append('/')
            .append(themeDisplay.getPathImage()).append("/image_gallery?img_id=").toString();
    Collection<String> allTypes = (Collection) (OrganizationType.allTypes());
    String temp;
%>
=======
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include.jsp" %>

>>>>>>> 11a42c660eddf484dc51cb2964aeb794a3ead75e
-->
<html>
<head>
<title>Insert title here</title>
</head>
<body>
View all courses page
<portlet:renderURL var="LinkAddCourse">
			<portlet:param name="add" value="course"/>
</portlet:renderURL>
<div class="portlet-content-controlpanel fs20"style="width: 10.15%;float: right;">
                <!--<a style="float: right" href="${deleteLink}"
                   onclick='return confirm("<spring:message code="form.confDelete"/>")'>
                    <div class="panelbtn panelbtn-right fs20 icon-pcpremove" aria-hidden="true"></div>
                </a>-->
                <a style="float: right" href="${LinkAddCourse}">
                    <!--<spring:message code="viewSingle.Edit"/>-->
                    <div class="panelbtn panelbtn-right icon-pcppencil" aria-hidden="true"></div>
                </a>
            </div>
<!--
<div class="textBox">
	<select style="width: 30%;">
	<c:forEach var="kind" items="${kindOfCourses}">
		<option value="${kind.typeId}">${kind.kindOfCourse}</option>
	</c:forEach>
	</select>
	<!-- <select style="width: 30%;">
	<c:forEach var="type" items="${coursesType}">
		<option value="${type.name}">${type.name}</option>
	</c:forEach>
	</select>-->
</div> -->
</body>
</html>