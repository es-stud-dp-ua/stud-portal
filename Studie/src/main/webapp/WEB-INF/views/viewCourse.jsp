<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<portlet:renderURL var="LinkEditCourse">
 			<portlet:param name="showEdit" value="course"/>
</portlet:renderURL>

<portlet:renderURL var="LinkDeleteCourse">
 			<portlet:param name="delete" value="course"/>
</portlet:renderURL>

<div class="portlet-content-controlpanel fs20"style="width: 10.15%;float: right;">
<a style="float: right" href="${LinkDeleteCourse}">
<div class="panelbtn panelbtn-right icon-pcpremove" aria-hidden="true"></div>

</a>
<a style="float: right" href="${LinkEditCourse}">
<div class="panelbtn panelbtn-right icon-pcppencil" aria-hidden="true"></div>
</a>
</div>

<portlet:actionURL var="actionLink" name="editCourse"></portlet:actionURL>
<div width = "100%" style="height: 500px">
<form:form method="POST" commandName="course" action="${actionLink}" enctype="multipart/form-data" id="courseForm">
    <div class="textBox" style="height: 500px">
                    <div style="height: 500px"><label style="height: 500px" cssClass="control-group" for="courseName"><spring:message code="Check"/></label>
                    </div>
                    <form:checkbox path="courseName" />
                    <form:errors path="courseName" cssClass="error"/>
    </div>
</form:form>
</div>
</body>
</html>