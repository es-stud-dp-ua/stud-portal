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
        <%
    Boolean russianLocaleEnabled = request.getLocale().getLanguage().equals("ru");
    Council council = (Council) request.getAttribute("council");
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
                    $('#defaultEntry').timeEntry().change(function() {
            var log = $('#log');
                    log.val(log.val() + ($('#defaultEntry').val() || 'blank') + '\n');
            });

            </script>
        <script language="javascript" type="text/javascript">
                    $(document).ready(function() {
            });
                    function isNotMax(e, id) {
                    var validateValueTextArea = document.getElementById(id);
                            validateValueTextArea.value = validateValueTextArea.value.substr(0, validateValueTextArea.getAttribute('maxlength'));
                    }
        </script>
    <portlet:renderURL var="home"> 
    </portlet:renderURL>

    <portlet:actionURL var="actionLink" name="editCouncil"></portlet:actionURL>
	
	<div class="portlet-content-controlpanel fs20">
        <a href="${home}">
            <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
        </a>
    </div>

    <liferay-ui:error key="no-images" message='<spring:message code="msg.noImages"/>'/>
    <liferay-ui:error key="dplTopic" message='<spring:message code="msg.dplTopic"/>'/>


    <div width="100%" align="center">
        <form:form method="post" id="councilForm"  action="${actionLink}"  enctype="multipart/form-data" commandName="council" modelAttribute="council">
            <input type="hidden" size="0" id="x1" name="t" value="0"/>
             <input type="hidden" size="0" id="y1" name="l" value="0"/>
             <input type="hidden" size="0" id="w" name="w" value="100"/>
             <input type="hidden" size="0" id="h" name="h" value="100"/>
             <input type="hidden" name="id" value="<%=council.getId()%>">

           <div id="labels" width="50%" align="center"><spring:message code="form.councilName"/></div>

                        <div style="width: 450px; padding-center: 20px;">

                                      <form:select path="studie.id">
                                             <form:options items="${studie}" itemValue="id" itemLabel="title"/>
                                      </form:select>
                          </div>
                        <br/>
           </div>
            <table width="100%" margin-bottom="15px">
                   <tr>
                     <td  width="50%" align="center">
                         <div id="labels"><spring:message code="form.councilContact"/></div>
                            <div style="margin-left: 5px;">
                                <form:textarea title="${council.councilContact}" path="councilContact" class="ckeditor" id="councilContact" cols="60" rows="5" maxlength="10000"
                                          name="councilContact"/>
                            </div>   
                    </td>
                        <td width="50%" align="center">
                            <div id="labels"><spring:message code="form.councilDescription"/></div>
                            <div style="margin-left: 5px;">
                                <form:textarea title="${council.councilDescription}" path="councilDescription" class="ckeditor" id="councilDescription" cols="60" rows="5" maxlength="10000"
                                           name="councilDescription"/>
                            </div>
                    </td>
                </tr>
            </table>
            <br/>
            <div id="sbm" align="center">
                <input type="submit" value="<spring:message
                       code="form.submit.council"/>"/>
            </div>
        </form:form>
    </div>
</body>
</html>
