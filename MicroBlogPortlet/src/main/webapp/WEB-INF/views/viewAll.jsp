<%@ page import="ua.dp.stud.StudPortalLib.model.News" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.CustomFunctions" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include.jsp" %>
<portlet:defineObjects/>
<%
    ImageService imageService = (ImageService) pageContext.findAttribute("imageService");
%>
<div id="microblig">
    <c:if test="${not empty newsList}">
        <table style="width: 100%;"><tbody><tr>
            <c:forEach items="${newsList}" var="currentNews" varStatus="i" begin="0" >
				<%News currentNews = (News) pageContext.getAttribute("currentNews");%>
				<c:if test="${i.index eq 2}"></tr><tr></c:if>
				<td><div>
					<liferay-portlet:renderURL plid="${newsArchivePageID}" var="linkToSingle" portletName="NewsArchive_WAR_studnewsArchive">
						<liferay-portlet:param name="newsID" value="${currentNews.id}"/>
					</liferay-portlet:renderURL>
					<a href="${linkToSingle}">
						<img src="<%=imageService.getPathToMicroblogImage(currentNews.getMainImage(),currentNews)%>" class="newsImage">
					</a>
					<%if (request.isUserInRole("Administrator")) {%>
						<portlet:renderURL var="removeLink">
							<portlet:param name="newsID" value="${currentNews.id}"/>
							<portlet:param name="mode" value="remove"/>
						</portlet:renderURL>
						<a href="${removeLink}"
						   style="float: right;" onclick='return confirm("<spring:message code="form.confDelete"/>")'>
							<div class="panelbtn panelbtn-right fs20 icon-pcpremove" aria-hidden="true"></div>
						</a>
					<%}%>
					<div class="newsHeader">
						<a href="${linkToSingle}">${currentNews.topic}</a>
					</div>
				</div>
				<div class="reply_link_wrap">
					<span class="rel_author">${currentNews.author}</span>
					<span class="rel_view">${currentNews.numberOfViews}</span>
					<span class="rel_date"><%=CustomFunctions.getCreationDate(currentNews.getPublication())%></span>
				</div></td>
            </c:forEach>
        </tr></tbody></table>
    </c:if>
</div>