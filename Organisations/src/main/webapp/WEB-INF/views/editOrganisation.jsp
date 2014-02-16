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

                    $(document).ready(function() {
            $.Placeholder.init({color: "#aaa"});
            });
                    function isNotMax(e) {
                    e = e || window.event;
                            var target = e.target || e.srcElement;
                            var code = e.keyCode ? e.keyCode : (e.which ? e.which : e.charCode)
                            switch (code) {
                    case 13:
                            case 8:
                            case 9:
                            case 46:
                            case 37:
                            case 38:
                            case 39:
                            case 40:
                            return true;
                    }
                    return target.value.length <= target.getAttribute('maxlength');
                    }
        </script>
    <portlet:renderURL var="home"> </portlet:renderURL>
    <portlet:actionURL var="actionLink" name="editOrganisation"></portlet:actionURL>
    <div class="portlet-content-controlpanel fs20">
        <a href="${home}">
            <!--<spring:message code="form.back"/>-->
            <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
        </a>
        <% if (request.isUserInRole("Administrator")) { %>

        <a style="margin-left: 10px;" href="<portlet:renderURL/>&orgsId=<%=orgs.getId()%>&mode=delete"
           onclick='return confirm("<spring:message code="form.confDelete"/>")'>
            <!--<spring:message code="form.delete"/>-->
            <div class="panelbtn panelbtn-right fs20 icon-pcpremove" aria-hidden="true"></div>
        </a>
        <%}%>
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
                                       debugger
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
                             text1: {
                     required: true,
                             minlength: 300,
                             maxlength: 3000
                     },
                             contacts:{
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
     <script defer="defer">
        lat = ${organization.lat} ;
         lng = ${organization.lng};
      </script>
</body>
</html>
