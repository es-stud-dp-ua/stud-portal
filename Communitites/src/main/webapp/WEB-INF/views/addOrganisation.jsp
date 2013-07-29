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
    Locale locale = (Locale) request.getSession().getAttribute("org.apache.struts.action.LOCALE");
    String language = locale.getLanguage();
    String country = locale.getCountry();
    //todo: remove res and type
    ResourceBundle res = ResourceBundle.getBundle("messages", new Locale(language, country));
%>
<portlet:defineObjects/>
<html>
    <head>
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
    <body>
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

    <liferay-ui:error key="no-images" message='<%=res.getString("msg.noImages")%>'/>
    <liferay-ui:error key="dplTopic" message='<%=res.getString("msg.dplTopic")%>'/>
    <c:if test="${exception}">
        ${exception}
    </c:if>

    <div width="100%" align="center">
        <form:form method="post" id="jform" name="jform" action="${actionLink}"  enctype="multipart/form-data" commandName="organization" modelAttribute="organization">
            <input type="hidden" size="0" id="x1" name="t"/>
            <input type="hidden" size="0" id="y1" name="l"/>
            <input type="hidden" size="0" id="w" name="w"/>
            <input type="hidden" size="0" id="h" name="h"/>
            <table width="100%" margin-bottom="15px">
                <tr>
                    <td width="50%" align="center">
                        <!--<input type="file" name="mainImage" accept="image/jpeg,image/png,image/gif" />-->
                        <style>
                            .thumb {
                                height: 253px;
                                width: 443px;
                            }
                        </style>
                        <div id="lup">
                        </div>
                        <div id="mainPic"
                             style="background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
                            <!-- Output for our douwnload Image-->
                            <output id="list"></output>
                        </div>
                        <div id="rdn">
                        </div>
                        <div id="mainImageLoader">
                            <div id="mainImgloaderBtn">
                                <input type="file" id="mainImage" name="mainImage">
                                <div id="nt"><spring:message code="form.addMainPictures"/></div>
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
                                    span.innerHTML = ['<img id="cropbox" class="thumb" src="', e.target.result,
                                    '" title="', escape(theFile.name), '"/>'].join('');
                                    document.getElementById('list').insertBefore(span, null);
                                    a();
                            };
                                    a();
                            })(f);
                                    // Read in the image file as a data URL.
                                    reader.readAsDataURL(f);
                                    a();
                            }
                            document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);</script>
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
                        <form:input path="title" id="title" cols="90" rows="2" maxlength="100"  name="title"/>
                        <form:errors path="title" cssClass="error"></form:errors>
                            <textarea path="text" class="ckeditor" id="text" cols="60" rows="10" maxlength="10000"
                                      name="text" ></textarea>
                            <textarea style="visibility: hidden;width: 0px;" id="text1" name="text1"></textarea>
                        <form:errors path="text" cssClass="error" ></form:errors>
                            <br/><br/>
                            <div id="sbm">
                                <input type="submit" value="<spring:message
                                       code='<%=(request.isUserInRole("Administrator"))?"form.submit.admin"
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
                                    <table>
                                        <tr><label>
                                            <div style="font-weight: bold; "><spring:message
                                                    code="addOrganisation.type"/></div>
                                        </label></tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.SPORTS"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= OrganizationType.SPORTS %>"
                                                       checked="" style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px; "><spring:message
                                                        code="form.YOUNGSTERS"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= OrganizationType.YOUNGSTERS %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.VOLUNTEERING"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= OrganizationType.VOLUNTEERING %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.CHARITY"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= OrganizationType.CHARITY %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.INT_ORGS"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= OrganizationType.INT_ORGS %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.ART"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= OrganizationType.ART %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.OTHERS"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= OrganizationType.OTHERS %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                    </table>
                                </div>
                                <br/>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </form:form>
        <script type="text/javascript">


        </script>
    </div>
</body>
</html>
