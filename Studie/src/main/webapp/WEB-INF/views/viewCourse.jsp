<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.liferay.portal.model.Layout" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.ImageImpl" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
${course.id}
<portlet:renderURL var="LinkEditCourse">
            <portlet:param name="id" value="${course.id}"/>
 			<portlet:param name="showEdit" value="course"/>
</portlet:renderURL>

<portlet:actionURL var="LinkDeleteCourse">
            <portlet:param name="id" value="${course.id}"/>
 			<portlet:param name="delete" value="course"/>
</portlet:actionURL>

<div class="portlet-content-controlpanel fs20"style="width: 10.15%;float: right;">
<a style="float: right" href="${LinkDeleteCourse}">
<div class="panelbtn panelbtn-right icon-pcpremove" aria-hidden="true"></div>
</a>
<a style="float: right" href="${LinkEditCourse}">
<div class="panelbtn panelbtn-right icon-pcppencil" aria-hidden="true"></div>
</a>
</div>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <table>
        <tr>

            <td><img style="width:70px" src="${mainImage}" alt=""/> </td>
            <td style="left: 70%; font-size: 18pt"> <strong> ${course.courseName}</strong></td>
        </tr>
        </table

        <br/>
        <div >
            ${course.coursesContact}
        </div>
        <br/>
        <div id="labels" style="width: 150px; font-size: 15pt; left: 10%;">
                 <spring:message code="More"/>
        </div>
        <br/>
            ${course.coursesDescription}
        </div>

</body>
</html>