<%@include file="AddEdit.jsp" %>
<html>
<head>
<title>Жопа</title>
</head>
<body>
<portlet:actionURL var="edit" name="editCourse"></portlet:actionURL>
<form:form method="POST" commandName="course" action="${edit}" enctype="multipart/form-data" id="courseForm">

Edit course page
<br/>
            <div id="labels" style="width: 150px; font-size: 12pt">
            <spring:message code="course.TName"/>
            </div>

</form:form>
</body>
</html>