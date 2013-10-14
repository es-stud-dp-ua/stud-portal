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
                    <div class="panelbtn panelbtn-right icon-pcpplus" aria-hidden="true"></div>
                </a>
            </div>


<portlet:renderURL var="LinkEditCourse">
			<portlet:param name="edit" value="course"/>
</portlet:renderURL>
<div class="portlet-content-controlpanel fs20"style="width: 10.15%;float: right;">

                <a style="float: right" href="${LinkEditCourse}">

                    <div class="panelbtn panelbtn-right icon-pcppencil" aria-hidden="true"></div>
                </a>
</div>




<div class="textBox">
	<select style="width: 30%;">
	<c:forEach var="kind" items="${kindOfCourses}">
		<option value="${kind.typeId}">${kind.kindOfCourse}</option>
	</c:forEach>
	</select>
	
	<select style="width: 30%;">	
		<c:forEach var="type" items="${coursesType}">
		<option value="${type}">${type}</option>
		</c:forEach>		
	</select>
</div>
</body>
</html>