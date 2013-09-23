<%@ page import="ua.dp.stud.StudPortalLib.model.Category" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.News" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="include.jsp" %>
<%--
    Document   : addnews
    Author     : Tradunsky V.V.
--%>

<%if (request.isUserInRole("Administrator") || request.isUserInRole("User")) {%>
<script id="" src="${pageContext.request.contextPath}/js/cropbox.js" type="text/javascript"></script>
<script id="" src="${pageContext.request.contextPath}/js/edit.js" type="text/javascript"></script>

<%
    ImageService imageService = (ImageService) pageContext.findAttribute("imageService");
    Boolean russianLocaleEnabled = request.getLocale().getLanguage().equals("ru");
    News news = (News) request.getAttribute("news");
%>
<portlet:defineObjects/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<portlet:renderURL var="home">
    <portlet:param name="newsID" value="<%=news.getId().toString()%>"/>
</portlet:renderURL>

<portlet:actionURL var="actionLink" name="editNews"></portlet:actionURL>
<div class="portlet-content-controlpanel fs20">
    <a href="${home}">
        <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
    </a>
    <% if (request.isUserInRole("Administrator")) { %>
    <a style="margin-left: 10px;"
       href='<portlet:renderURL><portlet:param name="newsId" value="<%=news.getId().toString()%>"/><portlet:param name="mode" value="delete" /></portlet:renderURL>'
       onclick='return confirm("<spring:message code="form.confDelete"/>")'>
        <div class="panelbtn panelbtn-right fs20 icon-pcpremove" aria-hidden="true"></div>
    </a>
    <%}%>
</div>
<liferay-ui:error key="no-images" message='<spring:message code="form.noImages"/>'/>
<liferay-ui:error key="no-images" message='<spring:message code="form.noImages"/>'/>
<c:if test="${exception != null}">
    ${exception}
</c:if>

<div style="width: 900px;">
    <form:form method="POST" id="jform" name="jform" commandName="news" action="${actionLink}" enctype="multipart/form-data">
                <form:errors path="*" cssClass="errorblock" element="div" />
                <form:input type="hidden" path="id"/>
                <input type="hidden" size="0" id="x1" name="t" value="0"/>
                <input type="hidden" size="0" id="y1" name="l" value="0"/>
                <input type="hidden" size="0" id="w" name="w" value="100"/>
                <input type="hidden" size="0" id="h" name="h" value="100"/>

                <div class="textBox">
                    <div><label cssClass="control-group" for="topic"><spring:message code="news.topic"/></label>
                    </div>
                    <form:input path="topic" style="width: 100%;"/>
                    <form:errors path="topic" cssClass="error"/>
                </div>

                <div style="min-height: 270px">
                <div id="lup"></div>
                <div id="mainPic"
                     style="background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
                    <!-- Output for our douwnload Image-->
                    <output id="list">
                    </output>
                </div>
                <div id="rdn"></div></div>
                <div><label for="mainImage" generated="true" class="error" style=""></label></div>
                <div class="grayrect">
                    <div class="greenbtn"><spring:message code="form.addMainPicture"/></div>
                    <input name="mainImage" id="mainImage" type="file" class="nphotos"/>
                </div>

                <form:errors path="text" cssClass="error"/>
                <form:textarea path="text" class="ckeditor" id="text" cols="60" rows="10" maxlength="8000" gename="text"></form:textarea>
				<textarea style="visibility: hidden;width: 0px;" id="text1" name="text1"  ></textarea>

                <div id="eventSetting" style="text-align: left;">
                    <div style="font-size:14px">
                        <% if (request.isUserInRole("Administrator")) { %>
                        <label for="onMainpage"><spring:message code="form.onMainPage"/></label>
                        <form:checkbox path="onMainpage" />
                        <form:errors path="onMainpage" cssClass="error"/>
                        <%}%>
                        <br/>
                        <label for="inCalendar"><spring:message code="form.onCalendar"/></label>
                        <form:checkbox path="inCalendar" />
                        <form:errors path="inCalendar" cssClass="error"/>
                        <div class="textBox">
                            <form:input path="publicationInCalendar" id="calendarDate"/>
                            <form:errors path="publicationInCalendar" cssClass="error"/>
                        </div>
                    </div>
                </div>
                <div id="sbm"><input type="submit" value="<spring:message code='<%=(request.isUserInRole("Administrator"))?"form.submit.adminEdit" :"form.submit.user"%>'/>"/></div>
        </form:form>
        <script>
            function f() {
				window.setInterval("document.getElementById('text1').innerHTML = CKEDITOR.instances.text.getData(); if(document.getElementById('text').value!=CKEDITOR.instances.text.getData()) $('#jform').valid()", 500);
			}
            window.onload = f;
			$(document).ready(function() {
				$.validator.setDefaults({ ignore: [] });
				$('#jform').validate({
					rules: {
						topic: {
							required: true,
							minlength: 5,
							maxlength: 100
						},
						text1: {
							required: true,
							minlength: 100,
							maxlength: 10000
						},
						mainImage: {
                            accept: "jpg|jpeg|png"
						}
					},
					messages: {
						topic: {
							required: "<spring:message code="news.topic.empty"/>",
							minlength:  "<spring:message code="news.topic.between"/>",
							maxlength:  "<spring:message code="news.topic.between"/>"
						},
						text1: {
							required: "<spring:message code="news.text.empty"/>",
							minlength:  "<spring:message code="news.text.between"/>",
							maxlength:  "<spring:message code="news.text.between"/>"
						},
						mainImage: {
                            accept: "<spring:message code="news.mainImage.accept"/>"
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
<%}%>
