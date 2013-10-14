<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<portlet:defineObjects/>

<portlet:actionURL var="postUrl"/>
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>

<div id="formDiv">
    <span><small>* - <spring:message code="form.requiredFlag"/></small></span><br/><br/>
    <form:form method="POST" commandName="question" action="${postUrl}" id="contact-form" class="">
        <label for="sentFrom"><spring:message code="form.sentFrom"/></label>

        <div></div>
        <form:input path="sentFrom" cssClass="control-group"/>
        <form:errors path="sentFrom" cssClass="error"/>
        <br/>
        <label for="sentFromName"><spring:message code="form.sentFromName"/></label>

        <div></div>
        <form:input path="sentFromName" cssClass="control-group"/>
        <form:errors path="sentFromName" cssClass="error"/>
        <br/>
        <label for="sentFromPhone"><spring:message code="form.sentFromMobile"/></label>

        <div></div>
        <form:input path="sentFromMobile" cssClass="control-group"/>
        <form:errors path="sentFromMobile" cssClass="error"/>
        <br/>
        <label for="subject"><spring:message code="form.subject"/></label>

        <div></div>
        <form:input path="subject" cssClass="control-group"/>
        <form:errors path="subject" cssClass="error"/>
        <br/>
        <label for="text"><spring:message code="form.text"/></label>

        <div></div>
        <form:textarea cols="60" path="text" cssClass="control-group" style="resize: vertical; min-height: 100px;"/>
        <form:errors path="text" cssClass="error"/>
        <br/><br/>
        <input class="btn" type="submit" value="<spring:message code="form.btSend"/>" />
    </form:form>
</div>

