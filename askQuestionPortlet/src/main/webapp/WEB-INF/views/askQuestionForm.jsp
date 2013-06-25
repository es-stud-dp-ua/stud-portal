<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<portlet:defineObjects />

<portlet:actionURL var="postUrl"/>
<!--TODO: move all css and javascript to appropriate files -->
<html>
<head>
    <style type="text/css">
        .error {
            color: #ff0000;
        }

        label.valid {
            width: 24px;
            height: 24px;
            background: url(${pageContext.request.contextPath}/images/valid.png) center center no-repeat;
            display: inline-block;
            text-indent: -9999px;
        }

        label.error {
            font-weight: bold;
            color: red;
            padding: 2px 8px;
            margin-top: 2px;
        }

    </style>
</head>

<!--TODO:format html-->
<body>
<div style="margin-left: 20px;">
    <span><small>* - <spring:message code="form.requiredFlag"/></small></span><br/><br/>
    <form:form method="POST" commandName="question" action="${postUrl}" id="contact-form" class="">
    <label for="sentFrom"><spring:message code="form.sentFrom"/></label>
        <div style="">
        </div>
        <form:input path="sentFrom" cssClass="control-group"/>
        <form:errors path="sentFrom" cssClass="error"/>
        <br/>
        <label for="subject"><spring:message code="form.subject"/></label>
        <div style="">
        </div>
        <form:input path="subject" cssClass="control-group"/>
        <form:errors path="subject" cssClass="error"/>
        <br/>
        <label for="text"><spring:message code="form.text"/></label>
        <div>
        </div>
        <form:textarea cols="60" path="text" cssClass="control-group" style="resize: vertical; min-height: 100px;"/>
        <form:errors path="text" cssClass="error"/>
        <br/><br/>
        <input class="btn" type="submit" value = <spring:message code="form.btSend"/> />
    </form:form>
</div>


<script type="text/javascript">

    jQuery.extend(jQuery.validator.messages, {
        required:"<spring:message code="js.required"/>",
        email:"<spring:message code="js.email"/>"
    });


    $(document).ready(function () {
        $('#contact-form').validate({
            rules:{
                sentFrom:{
                    required:true,
                    email:true
                },
                subject:{
                    required:false
                },
                text:{
                    required:true
                }
            },
            highlight:function (label) {
                $(label).closest('.control-group').addClass('error');
            },
            success:function (label) {
                label
                        .text('OK!').addClass('valid')
                        .closest('.control-group').addClass('success');
            }
        });

    });
</script>
<br/>
</body>
</html>