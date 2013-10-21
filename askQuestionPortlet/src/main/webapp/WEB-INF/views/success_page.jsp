<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>

<html>
<head>
    <title></title>
</head>
<body>

<portlet:renderURL var="home">
    <portlet:param name="myaction" value="home"/>
</portlet:renderURL>

<a href="${home}"><spring:message code="form.back"/></a>

<h2><spring:message code="result.success"/></h2>


</body>
</html>