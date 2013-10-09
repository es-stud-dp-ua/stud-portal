<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page contentType="text/html" isELIgnored="false" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>
<portlet:defineObjects/>


<html>
<head>
</head>
<body>

<script type="text/javascript">
	jQuery.validator.addMethod('regexp',
            function (value, element, regexp) {
                var re = new RegExp(regexp);
                return this.optional(element) || re.test(value);
            }, "Please check your input."
    );
    $(document).ready(function () {
        	$('#registration-form').validate({
            rules: {
                lastName: {
                    required: true,
                    minlength: 2,
                    maxlength: 30,
                    regexp: "<spring:message code="regexp.name"/>"
                },
                firstName: {
                    required: true,
                    minlength: 2,
                    maxlength: 30,
                    regexp: "<spring:message code="regexp.name"/>"
                },
                emailAddress: {
                    required: true,
                    email: true
                },
                password1: {
                    required: true,
                    rangelength: [6, 24],
                    regexp: '^[a-z0-9_-]+$'
                },
                password2: {
                    required: true,
                    equalTo: "#password1"
                },
                phoneNumber: {
                    required: true,
                    regexp: '^[0-9/+]'
                },
                rules: "required"
            },
            messages: {
                lastName: {
                    required: "<spring:message code="js.required"/>",
                    minlength: "<spring:message code="js.minlength"/>",
                    maxlength: "<spring:message code="js.maxlength"/>",
                    regexp: "<spring:message code="js.Name.regexp"/>"
                },
                firstName: {
                    required: "<spring:message code="js.required"/>",
                    minlength: "<spring:message code="js.minlength"/>",
                    maxlength: "<spring:message code="js.maxlength"/>",
                    regexp: "<spring:message code="js.Name.regexp"/>"
                },
                emailAddress: {
                    required: "<spring:message code="js.required"/>",
                    email: "<spring:message code="js.email"/>"
                },
                password1: {
                    required: "<spring:message code="js.required"/>",
                    rangelength: "<spring:message code="js.rangelength"/>",
                    regexp: "<spring:message code="js.password1.regexp"/>"
                },
                password2: {
                    required: "<spring:message code="js.required"/>",
                    equalTo: "<spring:message code="js.password"/>"
                },
                phoneNumber: {
                    required: "<spring:message code="js.required"/>",
                    regexp: "<spring:message code="js.phoneNumber.regexp"/>"
                },
                rules: "<spring:message code="js.rules.required"/>"
            },
            highlight: function (label) {
                $(label).closest('.control-group').addClass('error');
            },
            success: function (label) {
                label
                        .text('OK!').addClass('valid')
                        .closest('.control-group').addClass('success');
            }
        });
    });
    $(function () {
        var pickerOpts = {
            closeText: "<spring:message code="datetime.closeText"/>",
            currentText: "<spring:message code="datetime.currentText"/>",
            nextText: "<spring:message code="datetime.nextText"/>",
            prevText: "<spring:message code="datetime.prevText"/>",
            monthNames: ["<spring:message code="datetime.monthNames.Jan"/>", "<spring:message code="datetime.monthNames.Feb"/>",
                "<spring:message code="datetime.monthNames.Mar"/>", "<spring:message code="datetime.monthNames.Apr"/>",
                "<spring:message code="datetime.monthNames.May"/>", "<spring:message code="datetime.monthNames.Jun"/>",
                "<spring:message code="datetime.monthNames.Jul"/>", "<spring:message code="datetime.monthNames.Aug"/>",
                "<spring:message code="datetime.monthNames.Sep"/>", "<spring:message code="datetime.monthNames.Oct"/>",
                "<spring:message code="datetime.monthNames.Nov"/>", "<spring:message code="datetime.monthNames.Dec"/>"],
            monthNamesShort: ["<spring:message code="datetime.monthNamesShort.Jan"/>", "<spring:message code="datetime.monthNamesShort.Feb"/>",
                "<spring:message code="datetime.monthNamesShort.Mar"/>", "<spring:message code="datetime.monthNamesShort.Apr"/>",
                "<spring:message code="datetime.monthNamesShort.May"/>", "<spring:message code="datetime.monthNamesShort.Jun"/>",
                "<spring:message code="datetime.monthNamesShort.Jul"/>", "<spring:message code="datetime.monthNamesShort.Aug"/>",
                "<spring:message code="datetime.monthNamesShort.Sep"/>", "<spring:message code="datetime.monthNamesShort.Oct"/>",
                "<spring:message code="datetime.monthNamesShort.Nov"/>", "<spring:message code="datetime.monthNamesShort.Dec"/>"],
            dayNames: ["<spring:message code="datetime.dayNames.Sun"/>", "<spring:message code="datetime.dayNames.Mon"/>",
                "<spring:message code="datetime.dayNames.Tue"/>", "<spring:message code="datetime.dayNames.Wed"/>",
                "<spring:message code="datetime.dayNames.Thu"/>", "<spring:message code="datetime.dayNames.Fri"/>",
                "<spring:message code="datetime.dayNames.Sat"/>"],
            dayNamesShort: ["<spring:message code="datetime.dayNamesShort.Sun"/>", "<spring:message code="datetime.dayNamesShort.Mon"/>",
                "<spring:message code="datetime.dayNamesShort.Tue"/>", "<spring:message code="datetime.dayNamesShort.Wed"/>",
                "<spring:message code="datetime.dayNamesShort.Thu"/>", "<spring:message code="datetime.dayNamesShort.Fri"/>",
                "<spring:message code="datetime.dayNamesShort.Sat"/>"],
            dayNamesMin: ["<spring:message code="datetime.dayNamesMin.Sun"/>", "<spring:message code="datetime.dayNamesMin.Mon"/>",
                "<spring:message code="datetime.dayNamesMin.Tue"/>", "<spring:message code="datetime.dayNamesMin.Wed"/>",
                "<spring:message code="datetime.dayNamesMin.Thu"/>", "<spring:message code="datetime.dayNamesMin.Fri"/>",
                "<spring:message code="datetime.dayNamesMin.Sat"/>"],
            firstDay: 1,
            dateFormat: "dd.mm.yy",
            showAnim: "blind",
            changeMonth: true,
            changeYear: true,
            yearRange: '-100:+0'
        };
        $("#datepicker").datepicker(pickerOpts);
    });
</script>

<portlet:actionURL var="actionLink" name="addNewUser"></portlet:actionURL>
<liferay-ui:error key="error.email" message='<spring:message code="msg.wrong.email"/>'></liferay-ui:error>
<liferay-ui:error key="error.global" message='<spring:message code="result.exeption"/>'></liferay-ui:error>
<liferay-ui:error key="error.data" message='<spring:message code="msg.wrong"/>'></liferay-ui:error>

<form:form method="POST" action="${actionLink}" commandName="userInfo" id="registration-form" class=""
           enctype="multipart/form-data">
    <fieldset id="mainContent">
        <center>
            <legend cssClass="control-group" id="formTitle"><spring:message code="reg-caption"/></legend>
        </center>
        <fieldset class="fieldReg">
            <legend cssClass="control-group" id="titleLevel2"><spring:message code="reg-ness-field"/></legend>
            <div class="colunmBx">
                <div class="textBox">
                    <div><label cssClass="control-group" for="lastNameBx"><spring:message code="last-name"/></label>
                    </div>
                    <form:input path="lastName" class="inpts" type="text"/>
                    <form:errors path="lastName" cssClass="error"/>
                </div>
                <div class="textBox">
                    <div><label cssClass="control-group" for="firstName"><spring:message code="first-name"/></label>
                    </div>
                    <form:input path="firstName" class="inpts" type="text"/>
                    <form:errors path="firstName" cssClass="error"/>
                </div>
                <div class="textBox">
                        <div><label cssClass="control-group" for="phoneNumber"><spring:message code="phone-number"/></label>
                        </div>
                        <form:input path="phoneNumber" class="inpts" type="text"/>
                    	<form:errors path="phoneNumber" cssClass="error"/>
                    </div>
                <div class="textBox">
                    <div><label cssClass="control-group" for="emailAddress"><spring:message
                            code="email-address"/></label></div>
                    <form:input path="emailAddress" class="inpts" type="text"/>
                    <form:errors path="emailAddress" cssClass="error"/>
                </div>
                <div class="textBox">
                    <div><label cssClass="control-group" for="password1"><spring:message code="password1"/></label>
                    </div>
                    <form:input path="password1" class="inpts" type="password"/>
                    <form:errors path="password1" cssClass="error"/>
                </div>
                <div class="textBox">
                    <div><label cssClass="control-group" for="password2"><spring:message code="password2"/></label>
                    </div>
                    <form:input path="password2" class="inpts" type="password"/>
                    <form:errors path="password2" cssClass="error"/>
                </div>
         </div>
            <div>
	            <p><spring:message code="if-log-in"/></p>
				<a cssClass="control-group" href="/web/guest/home?p_p_id=58&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&saveLastPath=0&_58_struts_action=%2Flogin%2Flogin">
            	<spring:message code="log-in"/></a>
            </div>
        </fieldset>
<<<<<<< HEAD
    <fieldset class="fieldReg">
=======
        <fieldset class="fieldReg">
            <div id="titleLevel2" class="splLink">
				<span id="spoiler">+</span>
				<span style="margin-left: 15px;"><spring:message code="reg-not-ness-field"/></span>
			</div>
>>>>>>> dd6c868f628f6e94f8f3f2e8ab821e9cee45bfe7
            <div class="splCont" style="display:none;">
                <div class="LoadFoto">
                    <div class="textBox">
                        <div><label for="image"><spring:message code="image"/></label></div>
                        <input type="file" id="portret" name="portret" accept="image/jpeg,image/png,image/gif"/>
                    </div>
                    
                </div>
            </div>
        </fieldset>
        
        <fieldset style="padding: 7px;">
            <input path="rules" type="checkbox" name="rules">&nbsp;<spring:message code="rules"/></input>
            <form:errors path="rules" cssClass="error"/>
        </fieldset>
        <div id="submitButton">
            <input type="submit" value='<spring:message code="form.send"/>'/>
        </div>
    </fieldset>
</form:form>
</body>
</html>