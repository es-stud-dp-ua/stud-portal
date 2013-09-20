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
<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/myscripts.js"></script>
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
    Boolean flag=true;
%>
<portlet:defineObjects/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <script>
            $(function() {
                $.datepicker.setDefaults($.datepicker.regional['ru']);
                $("#datepicker1").datepicker();
                $("#datepicker2").datepicker();
            });</script>
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
    <portlet:actionURL var="actionLink" name="editEvent"></portlet:actionURL>
    <div class="portlet-content-controlpanel fs20">
        <a href="${home}">
            <!--<spring:message code="form.back"/>-->
            <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
        </a>
        <% if (request.isUserInRole("Administrator")) { %>

        <a style="margin-left: 10px;" href="<portlet:renderURL/>&eventId=<%=event.getId()%>&mode=delete"
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
        <form:form method="post" action="${actionLink}"  enctype="multipart/form-data" commandName="event" modelAttribute="event">
            <input type="hidden" size="0" id="x1" name="t"/>
            <input type="hidden" size="0" id="y1" name="l"/>
            <input type="hidden" size="0" id="w" name="w"/>
            <input type="hidden" size="0" id="h" name="h"/>
            <input type="hidden" name="eventId" value="<%=event.getId()%>">

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
                        <% if (event.getMainImage() == null) { %>
                        <div id="mainPic" style="vertical-align: top; z-index: 2;"
                             style="background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
                            <!-- Output for our douwnload Image-->

                            <output id="list"></output>
                        </div>
                        <% } else { %>

                        <div id="mainPic" style="vertical-align: top; z-index: 2;">
                            <img id="img" style="vertical-align: top; z-index: 1;" src="${mainImage}"/>
                            <output id="list"></output>
                        </div>
                        <% } %>


                        <div id="rdn">
                        </div>
                        <div id="mainImageLoader">
                            <div id="mainImgloaderBtn">
                                <input type="file" id="mainImage" name="mainImage">
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
                        span.innerHTML = ['<img id="cropbox"  width="453px"  class="thumb" src="', e.target.result,
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
                    </td>
                    <td rowspan=2 width="50%" align="left">
                        <form:input title="${event.title}" path="title" style="margin-left:2%;width:95%;" id="topicInput" cols="90" rows="2" maxlength="100" onkeypress="return isNotMax(event)"
                                    name="title"/>
                        <form:errors path="title" cssClass="error"></form:errors>
                            <div style="width: 450px; float: bottom ;padding-left: 8px;">
                            <form:textarea path="text" title="${event.text}" class="ckeditor" id="textInput" cols="60" rows="10" maxlength="8000"
                                           onkeypress="return isNotMax(event)" name="text"/>
                            <form:errors path="text" cssClass="error" ></form:errors>
                                <br/><br/>
                            </div>

                            <br/><br/>
                        </td>
                    </tr>
                    <tr>
                        <td width="50%" align="right">
                            <div id="eventSetting">
                                <div style="margin-right: 10px; margin-top: 55px;"><spring:message code="form.dateStart"/><div id="redStar4">*</div></div><input type="text" name="EventDateStart" value="<%=dateFormat.format(event.getEventDateStart()) %>" id="datepicker1"/><input type="text" value="<%=timeFormat.format(event.getEventDateStart())%>" placeholder="HH:mm" maxlength="5" style="width: 15%;margin-left: 1%;" name="startTime" id="defaultEntry"/>
                            <div style="margin-right: 10px;"><spring:message code="form.dateEnd"/></div><input type="text" name="EventDateEnd" id="datepicker2" <%if(event.getEventDateEnd()!=null){%>value="<%= dateFormat.format(event.getEventDateEnd()) %>"<%}%>/><input type="text" placeholder="HH:mm" <%if(event.getEventDateEnd()!=null){%>value="<%=timeFormat.format(event.getEventDateEnd())%>"<%}%> maxlength="5" style="width: 15%;margin-left: 1%;" name="endTime" id="endTime"/>
                            <div style="margin-right: 10px;"><spring:message code="form.location"/></div> <form:input path="location" title="<%=event.getLocation()%>"id="location" cols="60" rows="2" maxlength="100"  name="location"/>
                            <div style="font-size:14px">
                                <div style="float: right;  ">
                                    <table>
                                        <tr><label>
                                            <div style="font-weight: bold; "><spring:message
                                                    code="addEvent.type"/></div>
                                        </label></tr>
                                        <% for (String currentType : allTypes) {
                                        temp = new String("form." + currentType);%>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="<%=temp%>"/></div>
                                            </td>
                                            <td>
                                                <% if (event.getType().toString() == currentType) {%>
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
            <div id="sbm">
                <input style="width: 190px;
                       position: absolute;
                       bottom: 3%;
                       right: 40%;" type="submit" value="<spring:message
                       code='<%=(request.isUserInRole("Administrator"))?"form.submit.save":"form.submit.user"%>'/>"/>
            </div>
        </form:form>
    </div>
</body>
</html>
