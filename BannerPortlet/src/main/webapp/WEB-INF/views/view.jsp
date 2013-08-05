<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.Events" %>
<%@ page import="ua.dp.stud.bannerPortlet.model.BannerImage" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.CustomFunctions" %>
<%@include file="include.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>
<%
    Collection<BannerImage> bannerImages =
            (Collection<BannerImage>) request.getAttribute("bannerImages");
    ImageService imageService = (ImageService) pageContext.findAttribute("imageService");
%>

<html>
<head>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
<liferay-ui:success message='<spring:message code="msg.successAdd"/>' key="success"/>

<div style="width: 100%">
    <% if (request.isUserInRole("Administrator")) {%>
    <div style="margin-right: 450px;">
        <a href="<portlet:renderURL/>&mode=add">
            <div class="panelbtn panelbtn-right fs20 icon-pcpplus" aria-hidden="true"></div>
        </a>&nbsp;&nbsp;&nbsp;
        <a href="<portlet:renderURL/>&mode=edit">
            <div class="panelbtn panelbtn-right fs20 icon-pcppencil" aria-hidden="true"></div>
        </a>
    </div>
    <%}%>
	<div style="float: right; width: 450px; max-width: 450px;">
		<c:if test="${empty eventsList}">
        <b><spring:message code="empty.eventsList"/></b><br>
		</c:if>
		<c:if test="${not empty eventsList}">
		    <h3><spring:message code="empty.unlost"/></h3><br>
			<c:forEach items="${eventsList}" var="currentEvent">
				<liferay-portlet:renderURL plid="${eventsPageID}" var="linkToSingle" portletName="Events_WAR_studevents">
					<liferay-portlet:param name="newsID" value="${currentEvent.id}>"/>
				</liferay-portlet:renderURL>
				<div>
					<div><a href="${linkToSingle}" class="eventLink">${currentEvent.title}</a></div>
					<%Events event = (Events) pageContext.getAttribute("currentEvent");%>
					<div><%=CustomFunctions.getEventsDate(event.getEventDateStart(), event.getEventDateEnd())%></div>
				</div>
			</c:forEach>
		</c:if>
	</div>
	<c:if test="${ not empty bannerImages}">
		<div style="max-width: 450px;">
			<img id="left-top" src="${pageContext.request.contextPath}/images/frame2.png">
			<img id="left-bottom" src="${pageContext.request.contextPath}/images/frame1.png">
			<img id="right-top" src="${pageContext.request.contextPath}/images/frame1.png">
			<div class="banner"><div align="left" class="images">
					<%for (BannerImage image : bannerImages) {%>
					<a href="<%=image.getUrl()%>">
						<img src="<%= imageService.getPathToLargeImage(image.getMainImage(),image) %>">
					</a>
					<%}%>
			</div><div class="pagination"><ul></ul></div></div>
		</div>
	</c:if>

</div>
</body>
</html>
