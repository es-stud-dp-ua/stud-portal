<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<portlet:actionURL var="postUrl">
    <portlet:param name="edit" value="email"/>
</portlet:actionURL>

<style type="text/css">
    .error {
        color: #ff0000;
    }
</style>

<form:form method="POST" commandName="mailChanger" action="${postUrl}">
    <table>
        <tr>
            <td><label for="newMail"><spring:message code="form.newMail"/></label></td>
        </tr>
        <tr>
            <td><spring:message code="form.currentMail"/>${curmail}</td>
        </tr>
        <tr>
            <td><form:input path="newMail"/><form:errors path="newMail" cssClass="error"/></td>
        </tr>
        <tr>
            <td><input type="submit" value=
                <spring:message code="form.btSave"/>></td>
        </tr>
    </table>
</form:form>