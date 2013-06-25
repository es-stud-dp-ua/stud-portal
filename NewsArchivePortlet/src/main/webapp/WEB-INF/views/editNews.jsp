<%@ page import="ua.dp.stud.StudPortalLib.model.Category" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.News" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>

<%--
    Document   : addnews
    Author     : Tradunsky V.V.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="include.jsp" %>

<%if (request.isUserInRole("Administrator") || request.isUserInRole("User")) {%>
<script id="" src="${pageContext.request.contextPath}/js/cropbox.js" type="text/javascript"></script>

<%
    //todo LOCALE!
    Locale locale = (Locale) request.getSession().getAttribute("org.apache.struts.action.LOCALE");
    String language = locale.getLanguage();
    String country = locale.getCountry();
    ResourceBundle res = ResourceBundle.getBundle("messages", new Locale(language, country));
    String ex = (String) request.getAttribute("exception");
    ImageService imageService = new ImageService();
    Boolean russianLocaleEnabled = request.getLocale().getLanguage().equals("ru");
    News news = (News) request.getAttribute("news");
%>
<portlet:defineObjects/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<script language="javascript" type="text/javascript">
    function isNotMax(e, id) {
        var validateValueTextArea = document.getElementById(id);
        validateValueTextArea.value = validateValueTextArea.value.substr(0, validateValueTextArea.getAttribute('maxlength'));
    }

    $(function () {
        <%
            if (russianLocaleEnabled){ %>
                $.datepicker.setDefaults($.datepicker.regional['ru']);
        <%}%>

        $("#calendarDate").datepicker({dateFormat: 'yy-mm-dd'});
    });
</script>

<portlet:renderURL var="home"> </portlet:renderURL>

<portlet:actionURL var="actionLink" name="editNews"></portlet:actionURL>
<div class="portlet-content-controlpanel fs20">
    <a href="${home}">
    <!--<spring:message code="form.back"/>-->
    <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
    </a>
	<% if (request.isUserInRole("Administrator")) { %>

		<a style="margin-left: 10px;" href="<portlet:renderURL/>&newsId=<%=news.getId()%>&mode=delete"
		   onclick='return confirm("<spring:message code="form.confDelete"/>")'>
			<!--<spring:message code="form.delete"/>-->
			<div class="panelbtn panelbtn-right fs20 icon-pcpremove" aria-hidden="true"></div>
		</a>
	<%}%>
</div>
<liferay-ui:error key="no-images" message='<%=res.getString("msg.noImages")%>'/>
<%if (ex != null) {%>
<%=ex%>
<%}%>

<div style="width: 900px;">
    <form method="POST" action="${actionLink}" enctype="multipart/form-data">
        <input type="hidden" name="newsId" value="<%=news.getId()%>">
        <div>
            <input type="hidden" size="0" id="x1" name="t"/>
            <input type="hidden" size="0" id="y1" name="l"/>
            <input type="hidden" size="0" id="w" name="w"/>
            <input type="hidden" size="0" id="h" name="h"/>
			<div style="float:right; width: 438px;">
				<textarea id="topicInput" cols="90" rows="2" maxlength="80" onkeyup="isNotMax(event, getAttribute('id'))" name="topic" placeholder="<spring:message code="form.topic"/>"> <%=news.getTopic()%></textarea><br/>
				<textarea class="ckeditor" id="textInput" cols="60" rows="10" maxlength="8000" name="text" placeholder="<spring:message code="form.text"/>"><%=news.getText()%></textarea><br/><br/>
				<div id="sbm">
					<input type="submit" value="<spring:message code='<%=(request.isUserInRole("Administrator"))?"form.submit.adminEdit" :"form.submit.user"%>'/>"/>
					<br/><br/>
                        <% if (request.isUserInRole("Administrator")) { %>
                        <div class="disable">
                            <p><spring:message code="form.author"/>:<%=news.getAuthor()%>
                            </p>

                            <p><spring:message code="form.creationDate"/>:<%=news.getPublicationInCalendar()%>
                            </p>
                        </div>
                        <%}%>
				</div>
				<br/><br/>
			</div>
			<div style="width: 450px;">

                <div id="lup"></div>
                <div id="mainPic" style="background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
                    <!-- Output for our douwnload Image-->
                    <output id="list">
                    </output>
                </div>
                <div id="rdn"></div>

                <div class="grayrect" style="margin-top: 283px;">
                    <div class="greenbtn" >
                        <spring:message code="form.addMainPicture"/>
                    </div>
                    <input name="mainImage" id="mainImage" type="file" class="nphotos" />
                </div>


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
                                                        span.innerHTML = ['<img id="cropbox" class="thumb" src="', e.target.result,
                                                            '" title="', escape(theFile.name), '"/>'].join('');
                                                        document.getElementById('list').insertBefore(span, null);
                                                    a();
                                                };a();
                                            })(f);

                                            // Read in the image file as a data URL.
                                            reader.readAsDataURL(f);
                                               a();
                                        }


                                            document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);
                </script>


				<div class="grayrect" style="margin-top: 371px;">
                    <div class="greenbtn" >
                        <spring:message code="form.addPictures"/>
                    </div>
                    <input name="images" type="file" class="nphotos" accept="image/jpeg,image/png,image/gif"/ multiple>
                </div>
					<br/>
					<div id="eventSetting" style="text-align: right;">
						<div style="font-size:14px">
                            <% if (request.isUserInRole("Administrator")) { %>
                            <label for="onMainPageChBx"><spring:message code="form.onMainPage"/></label>
                            <input id="onMainPageChBx" type="checkbox" name="onMainPage"
                                    <%if (news.getOnMainpage() == 1) {%>
                                   CHECKED
                                    <%}%>/><br/>
                            <%}%>
                            <br/>
                            <label for="inCalendarChBx"><spring:message code="form.onCalendar"/></label>
                            <input id="inCalendarChBx" type="checkbox" name="inCalendar"
                                    <%if (news.getInCalendar() == 1) {%>
                                   CHECKED
                                    <%}%>/><br/>
                            <input type="text" name="startDate" id="calendarDate"
                                   value="${pubDate}"/>
                        </div>
					</div>
			</div>
		</div>
    </form>
</div>
</body>
</html>
<%}%>
