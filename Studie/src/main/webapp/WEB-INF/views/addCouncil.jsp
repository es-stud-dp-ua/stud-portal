<%@ page import="ua.dp.stud.studie.model.Council" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="include.jsp" %>

<portlet:defineObjects/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <% Boolean russianLocaleEnabled = request.getLocale().getLanguage().equals("ru");

        %>
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

    <portlet:actionURL var="actionLink" name="addCouncil"></portlet:actionURL>

<div class="portlet-content-controlpanel fs20">
        <a href="${home}">
            <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
        </a>
    </div>

    <liferay-ui:error key="no-images" message='<spring:message code="msg.noImages"/>'/>
    <liferay-ui:error key="duplTop" message='Choose another university. Stud council with the same name is present!'/>
     <c:if test="${exception}">
             ${exception}
         </c:if>

    <%@include file="addEditCouncil.jsp" %>


    <script>
            $("#councilForm").on("submit",function(event){

                                            document.getElementById('text1').innerHTML = CKEDITOR.instances.councilDescription.getData();
                                            document.getElementById('text11').innerHTML = CKEDITOR.instances.councilContact.getData();
                                        });

                                 $(document).ready(function() {
                         $.validator.setDefaults({ ignore: [] });
                                 $('#councilForm').validate({

                         rules: {
                           text1: {
                             required: true,
                             minlength: 100,
                             maxlength: 3000
                           },
                          text11: {
                             required: true,
                             minlength: 100,
                             maxlength: 3000
                          }

                         },
                                 messages: {
                         text11: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.textcouncil.minlength"/>",
                                 maxlength:  "<spring:message code="val.textcouncil.maxlength"/>"
                         },
                                 text1: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.textcouncil.minlength"/>",
                                 maxlength:  "<spring:message code="val.textcouncil.maxlength"/>"
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

</body>
</html>
