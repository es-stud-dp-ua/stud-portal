<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<portlet:renderURL var="home">
    <portlet:param name="myaction" value="home"/>
</portlet:renderURL>


<a href="${home}"><spring:message code="form.back"/></a>

<h1><spring:message code="result.mailchanged"/></h1>