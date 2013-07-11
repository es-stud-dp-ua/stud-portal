<%@ page import="ua.dp.stud.StudPortalLib.model.Category" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.ImageImpl" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.Organization" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.OrganizationType" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/myscripts.js"></script>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include.jsp" %>

<%
    Collection<String> allTypes=(Collection) (OrganizationType.allTypes());
    String temp;
    Organization orgs = (Organization) request.getAttribute("organisation");
%>
<portlet:defineObjects/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    </head>
    <body>
        <script type="text/javascript">
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
    <portlet:actionURL var="actionLink" name="editCommunity"></portlet:actionURL>
    <div class="portlet-content-controlpanel fs20" >
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
    <liferay-ui:error key="dplTopic"  message='<spring:message code="msg.dplTopic"/>'/>
    <c:if test="${exception}" >
    ${exception}
    </c:if>
    <div width="100%" align="center">
        <form method="POST" action="${actionLink}" enctype="multipart/form-data">

            <input type="hidden" name="orgsId" value="<%=orgs.getId()%>">

            <table width="100%" margin-bottom="15px">
                <tr>
                    <td width="50%" align="center">
                        <style>
                            .thumb {
                                height: 253px;
                                width: 443px;
                            }
                        </style>
                        <div id="lup"> </div>
                        <div id="mainPic" style="background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
                            <!-- Output for our douwnload Image-->
                            <input type="file" id="mainImage" name="mainImage" />
                            <output id="list"></output>
                        </div>
                        <div id="rdn"></div>             
                        <script>
                            function handleFileSelect(evt)
                            {
                                var files = evt.target.files; // FileList object
                                // Loop through the FileList and render image files as thumbnails.
                                var f = files[files.length - 1];
                                // Only process im11age files.
                                document.getElementById('list').innerHTML = '';
                                var reader = new FileReader();
                                // Closure to capture the file information.
                                reader.onload = (function(theFile) {
                                    return function(e) {
                                        // Render thumbnail.
                                        var span = document.createElement('span');
                                        span.innerHTML = ['<img class="thumb" src="', e.target.result,
                                            '" title="', escape(theFile.name), '"/>'].join('');
                                        document.getElementById('list').insertBefore(span, null);
                                    };
                                })(f);
                                // Read in the image file as a data URL.
                                reader.readAsDataURL(f);
                            }
                            document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);
                        </script>
                        <br/>
                        <div id="imageLoader" style="margin-top: 265px;">
                            <div id="imgloaderBtn">
                                <input name="images" type="file" id="aui_3_2_0_11607" accept="image/jpeg,image/png,image/gif"/ multiple>
                                       <div id="nt"><spring:message code="form.addPictures"/></div>
                            </div>
                        </div>
                        <br/>
                    </td>
                    <td rowspan=2 width="50%" align="left">
                        <textarea id="topicInput" cols="90" rows="2" maxlength="80" onkeypress="return isNotMax(event)" name="topic">${organisation.Title}
                        </textarea><br/>
                        <div style="width: 450px; float: bottom ;padding-left: 8px;" >
                            <textarea class="ckeditor" id="textInput" cols="60" rows="10" maxlength="8000" onkeypress="return isNotMax(event)" name="text">${organisation.Text}
                            </textarea><br/><br/>
                        </div>
                        <div id="sbm">

                            <input style="width: 100px;" type="submit" value="<spring:message
                                   code='<%=(request.isUserInRole("Administrator"))?"form.submit.save"
                                                                                             :"form.submit.user"%>'/>"/>
                        </div>
                        <br/><br/>
                    </td>                
                </tr>
                <tr>
                    <td width="50%" align="right">
                        <div id="eventSetting">
                            <div style="font-size:14px">
                                <div style="float: right; margin-top: 30px; ">
                                    <table >
                                        <tr><label><div style="font-weight: bold; "><spring:message code="addOrganisation.type"/></div></label></tr>
                                            <% for(String currentType: allTypes){
                                temp=new String("form."+currentType);%>
                                        <tr>
                                            <td><div style="float: right; margin-right: 10px;"><spring:message code="<%=temp%>"/></div></td>
                                            <td>
                                                <% if (orgs.getOrganizationType().toString()==currentType) {%>
                                                <input type="radio" name="type" value="<%=currentType%>" checked="" style="float: right;"/>
                                                <%} else { %>
                                                <input type="radio" name="type" value="<%=currentType%>" style="float: right;"/>
                                                <%}%>
                                            </td>
                                        </tr>
                                        <%}%>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>


</script>
</body>
</html>
