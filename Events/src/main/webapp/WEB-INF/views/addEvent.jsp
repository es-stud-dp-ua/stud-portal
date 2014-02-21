<%@ page import="ua.dp.stud.StudPortalLib.model.Events" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.EventsType" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include.jsp" %>

<%
    Collection<String> allTypes = (Collection) (EventsType.allTypes());
    String temp;
        Boolean flag=true;
    Events event = (Events) request.getAttribute("event");
SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

%>
<portlet:defineObjects/>
<html>
    <head>
        <script>
            $(function() {
            $.datepicker.setDefaults($.datepicker.regional['ru']);
                    $("#datepicker1").datepicker({ dateFormat: "mm/dd/yy", showAnim:'slide', showButtonPanel:true});
                    $("#datepicker2").datepicker({ dateFormat: "mm/dd/yy", showAnim:'slide', showButtonPanel:true});
            });</script>
        <meta http-equiv="Content-Type" content="charset=utf-8">
        <% Boolean russianLocaleEnabled = request.getLocale().getLanguage().equals("ru");  %>
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
    <body>
                 <script src="${pageContext.request.contextPath}/js/a.js" type="text/javascript"></script>
                 <script src="${pageContext.request.contextPath}/js/setCoords.js" type="text/javascript"></script>

                 <script type="text/javascript">
                            $(document).ready(function() {
                                $.Placeholder.init({color: "#aaa"});
                            });

                            function isNotMax(e, validateValueTextArea) {

                                 validateValueTextArea.value = validateValueTextArea.value
                                 .substr(0, validateValueTextArea
                                 .getAttribute('maxlength'));
                               }

                        </script>



    <portlet:renderURL var="home"> </portlet:renderURL>

    <portlet:actionURL var="actionLink" name="addEvents"></portlet:actionURL>
    <div class="portlet-content-controlpanel fs20">
        <a href="${home}">
            <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
        </a>
    </div>

    <liferay-ui:error key="no-images" message='<spring:message code="msg.noImages"/>'/>
    <liferay-ui:error key="dplTopic" message='<spring:message code="msg.dplTopic"/>'/>
    <c:if test="${exception}">
        ${exception}
    </c:if>

    <div width="100%" align="center">
        <%@include file="CommonForm.jsp" %>
        <script >
           
        $.validator.addMethod('filesize', function(value, element, param) {
            // param = size (en bytes) 
            // element = element to validate (<input>)
            // value = value of the element (file name)
            return this.optional(element) || (element.files[0].size <= param) 
        });
        
        
        
        $("#jform").on("submit",function(event){

                         document.getElementById('text1').innerHTML = CKEDITOR.instances.text.getData();

                                                                                 });
                                 $(document).ready(function() {
                         $.validator.setDefaults({ ignore: [] });
                                 $('#jform').validate({
                         rules: {
                         	title: {
                       			  required: true,
                                 minlength: 5,
                                 maxlength: 100
                         },
                         mainImage: { 
                        	 filesize: 5242880  
                         },
                             text1: {
                         			required: true,
                                 minlength: 300,
                                 maxlength: 10000
                         },
                       		 EventDateStart:
                                     {required: true },
                        	 EventDateEnd:
                                     {required: true }

                         },
                                 messages: {
                         title: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.title.minlength"/>",
                                 maxlength:  "<spring:message code="val.title.maxlength"/>"
                         },
                         mainImage:{
                        	 filesize: "<spring:message code="val.text.minsize"/>"
                         },
                                 text1: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.text.minlength"/>",
                                 maxlength:  "<spring:message code="val.text.maxlength"/>"
                         },
                         EventDateStart:
                                 {required: "<spring:message code="val.required"/>" },
                         EventDateEnd:
                                 {required: "<spring:message code="val.required"/>" }
                         },
                                 highlight: function(label) {
                         $(label).removeClass("invisiblevalid");
                                 $(label).closest('.control-group').addClass('error');
                         },
                                 success: function(label) {
                         $(label).removeClass("error");
                                 label.addClass("invisiblevalid");
                         }
                         });
                         });
        </script>

    </div>
</body>
</html>
