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
            ;
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
        <form:form method="post" action="${actionLink}" id="jform" name="jform" enctype="multipart/form-data" commandName="organization" modelAttribute="organization">
            <input type="hidden" size="0" id="x1" name="t"/>
            <input type="hidden" size="0" id="y1" name="l"/>
            <input type="hidden" size="0" id="w" name="w"/>
            <input type="hidden" size="0" id="h" name="h"/>
            <form:input type="hidden" path="id" />

            <table width="100%" margin-bottom="15px">
                <tr>
                    <td width="50%" align="center">
                        <style>
                            .thumb {
                                height: 253px;
                                width: 443px;
                            }
                        </style>
                        <div id="lup">
                        </div>
                        <% if (orgs.getMainImage() == null) { %>
                        <div id="mainPic" style="vertical-align: top; "
                             style="background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
                            <!-- Output for our douwnload Image-->
                            <output id="list"></output>
                        </div>
                        <% } else { %>

                        <div id="mainPic" style="vertical-align: top; ">
                            <img id="img" style="vertical-align: top; " src="${mainImage}"/>
                            <output id="list"></output>
                        </div>
                        <% } %>
                        <div id="rdn">
                        </div>
                        <div id="mainImageLoader">
                            <div id="mainImgloaderBtn">
                                <input type="file" value="<%=imageService.getPathToLargeImage(orgs.getMainImage(), orgs)%>" id="mainImage" name="mainImage">

                                <div id="nt"><spring:message code="form.addMainPictures"/></div>
                            </div>
                            <div class="defImage"><spring:message  code='form.useDefaultImage'/>
                                <input type="checkbox" name="defaultImage" id="defaultImage" value="<%=flag.toString()%>" onclick="<%if(flag) flag=false; else flag=true;%>" style="margin-left: 3px;" title="<spring:message code='form.useDefaultImageTitle'/>"/>
                            </div>
                        </div>

                        <script>
                    function handleFileSelect(evt) {
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
                            span.innerHTML = ['<img id="cropbox" width="443px" class="thumb" src="', e.target.result,
                            '" title="', escape(theFile.name), '"/>'].join('');
                            document.getElementById('list').insertBefore(span, null);
                            a();
                    };
                            a();
                    })(f);
                            document.getElementById('img').parentNode.removeChild(document.getElementById('img'));
                            // Read in the image file as a data URL.
                            reader.readAsDataURL(f);
                            // Read in the image file as a data URL.
                            reader.readAsDataURL(f);
                            a();
                    }
            document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);
                        
                        </script>
                        <br/>
                        <div id="imageLoader">
                            <div id="imgloaderBtn">
                                <input name="images" type="file" id="aui_3_2_0_11607"
                                       accept="image/jpeg,image/png,image/gif"/ multiple>
                                       <div id="nt"><spring:message code="form.addPictures"/></div>
                            </div>
                        </div>
                        <br/>
                    </td>
                    <td rowspan=2 width="50%" align="left">
                        <form:input title="${organisation.title}" path="title" id="title" cols="90" rows="2" maxlength="100" onkeypress="return isNotMax(event)"
                                    name="title"/>
                        <form:errors path="title" cssClass="error"></form:errors>
                            <div style="width: 450px; float: bottom ;padding-left: 8px;">
                            <form:textarea path="text" title="${organisation.text}" class="ckeditor" id="text" cols="60" rows="10" maxlength="8000" gename="text"/>
                            <textarea style="visibility: hidden;width: 0px;" id="text1" name="text1"  ></textarea>
                            <form:errors path="text" cssClass="error" ></form:errors>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td width="50%" align="right">
                            <div id="eventSetting">
                                <div style="font-size:14px;margin-top: 130px;">
                                    <table>
                                        <tr><label>
                                            <div style="font-weight: bold;"><spring:message code="form.contacts"/> </div>
                                            <textarea path="contacts" id="contacts" cols="150" rows="5" maxlength="3000"  name="contacts"><%=orgs.getContacts()%></textarea>
                                        <form:errors path="contacts" cssClass="error"></form:errors>
                                            <div style="font-weight: bold; "><spring:message
                                                    code="addOrganisation.type"/></div>
                                        </label></tr>
                                    <% for (String currentType : allTypes) {
                                        temp = new String("form." + currentType);%>
                                    <tr>
                                        <td>
                                            <div style="float: right; margin-right: 10px;"><spring:message
                                                    code="<%=temp%>"/></div>
                                        </td>

                                        <td>
                                            <% if (orgs.getOrganizationType().toString() == currentType) {%>
                                            <input type="radio" name="type" value="<%=currentType%>" checked=""
                                                   style="float: right;"/>
                                            <%} else { %>
                                            <input type="radio" name="type" value="<%=currentType%>"
                                                   style="float: right;"/>
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
            <%@include file="otdelnaya.jsp" %>
            <div id="sbm">
                <input style="width: 200px;margin-top:5px;" type="submit" value="<spring:message
                       code='<%=(request.isUserInRole("Administrator"))?"form.submit.save"
                                                                                             :"form.submit.user"%>'/>"/>
            </div>
        </form:form>
    <script id="" src="${pageContext.request.contextPath}/js/valid.js" type="text/javascript"></script>
    </div>
     <script defer="defer">
        lat = ${organization.lat} ;
         lng = ${organization.lng};
      </script>
</body>
</html>
