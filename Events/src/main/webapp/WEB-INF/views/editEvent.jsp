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
    Events event = (Events) request.getAttribute("event");
SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    Boolean flag=true;
    Boolean archive=true;
    if (request.getAttribute("archive") != null){
        archive = (Boolean) request.getAttribute("archive");
    }
%>
<portlet:defineObjects/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <script>
            $(function() {
                $.datepicker.setDefaults($.datepicker.regional['ru']);
                $("#datepicker1").datepicker({ dateFormat: "mm/dd/yy", showAnim:'slide', showButtonPanel:true});
                $("#datepicker2").datepicker({ dateFormat: "mm/dd/yy", showAnim:'slide', showButtonPanel:true});
            });</script>
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
    <portlet:renderURL var="home">
        <portlet:param name="archive" value="<%=archive.toString()%>"/>
        <portlet:param name="currentPage" value="<%=currentPage.toString()%>"/>
    </portlet:renderURL>

    <portlet:actionURL var="actionLink" name="editEvent">
        <portlet:param name="archive" value="<%=archive.toString()%>"/>
        <portlet:param name="currentPage" value="<%=currentPage.toString()%>"/>
    </portlet:actionURL>
    <div class="portlet-content-controlpanel fs20">
        <a href="${home}">
            <!--<spring:message code="form.back"/>-->
            <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
        </a>
        <% if (request.isUserInRole("Administrator")) { %>

        <a style="margin-left: 10px;" href="<portlet:renderURL/>&eventID=<%=event.getId()%>&mode=delete"
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




        <script >
        $("#event").on("submit",function(event){

                                          document.getElementById('text1').innerHTML = CKEDITOR.instances.textInput.getData();

                                                                                                  });
                                                  $(document).ready(function() {
                                          $.validator.setDefaults({ ignore: [] });
                                                  $('#event').validate({
                                          rules: {
                                          title: {
                                          required: true,
                                                  minlength: 5,
                                                  maxlength: 100
                                          },
                                                  text1: {
                                          required: true,
                                                  minlength: 500,
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
