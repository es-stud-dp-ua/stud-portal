<%@ page import="ua.dp.stud.StudPortalLib.model.Category" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.Organization" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.OrganizationType" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/myscripts.js"></script>-->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="include.jsp" %>
<%
    Collection<String> allTypes = (Collection) (OrganizationType.allTypes());
    String temp;
    Organization orgs = (Organization) request.getAttribute("organization");
    Boolean flag=true;
%>
<portlet:defineObjects/>

<html>
    <head>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

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
    <body >

        <script type="text/javascript">
            function a() {
            jQuery('#cropbox').Jcrop({onChange: setCoords, onSelect: setCoords, bgColor: 'black',
                    bgOpacity: .4,
                    setSelect: [100, 0, 253, 353],
                    aspectRatio: 1});
            }
            function setCoords(c) {
            jQuery('#x1').val(c.x);
                    jQuery('#y1').val(c.y);
                    jQuery('#x2').val(c.x2);
                    jQuery('#y2').val(c.y2);
                    jQuery('#w').val(c.w);
                    jQuery('#h').val(c.h);
            }

            ;</script>
        <script language="javascript" type="text/javascript">
                    $(document).ready(function() {
            });
                    function isNotMax(e, id) {
                    var validateValueTextArea = document.getElementById(id);
                            validateValueTextArea.value = validateValueTextArea.value.substr(0, validateValueTextArea.getAttribute('maxlength'));
                    }
        </script>
    <portlet:renderURL var="home"> </portlet:renderURL>

    <portlet:actionURL var="actionLink" name="addOrganisation"></portlet:actionURL>
    <div class="portlet-content-controlpanel fs20">
        <a href="${home}">
            <!--<spring:message code="form.back"/>-->
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

     <script>
        $("#jform").on("submit",function(event){

                                        document.getElementById('text1').innerHTML = CKEDITOR.instances.text.getData();
            if ($("#jform").valid()){
                return true
            }
            return false

                                    });

                             $(document).ready(function() {
                     $.validator.setDefaults({ ignore: [] });
                             $('#jform').validate({
                                 onsubmit: false,
                     rules: {
                     title: {
                     required: true,
                             minlength: 5,
                             maxlength: 100
                     },
                             text1: {
                     required: true,
                             minlength: 300,
                             maxlength: 3000
                     },
                             contacts:{
                             required: true,
                             maxlength:300
                     }
                     },
                             messages: {
                     title: {
                     required: "<spring:message code="val.required"/>",
                             minlength:  "<spring:message code="val.title.minlength"/>",
                             maxlength:  "<spring:message code="val.title.maxlength"/>"
                     },
                             text1: {
                     required: "<spring:message code="val.required"/>",
                             minlength:  "<spring:message code="val.text.minlength"/>",
                             maxlength:  "<spring:message code="val.text.maxlength"/>"
                     },
                             contacts: {
                             required: "<spring:message code="val.required"/>",
                             maxlength:  "<spring:message code="val.contacts.maxlength"/>"
                     }
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
