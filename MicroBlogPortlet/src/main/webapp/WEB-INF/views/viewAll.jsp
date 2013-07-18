<%@ page import="ua.dp.stud.StudPortalLib.model.News" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.CustomFunctions" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include.jsp" %>
<portlet:defineObjects/>
<%
    Collection<News> news = (Collection) request.getAttribute("news");
    ImageService imageService = (ImageService) pageContext.findAttribute("imageService");
%>
<div id="microblig">
    <div id="tableHeader">
        <spring:message code="header.latestNews"/><br/>
        <br/>
    </div>
    <c:if test="${not empty news}">
        <div id="newsTable">
            <%for (News currentNews : news) {%>
				<div width="100%" id="newsContent">
					<liferay-portlet:renderURL plid="${newsArchivePageID}" var="linkToSingle" portletName="NewsArchive_WAR_studnewsArchive">
						<liferay-portlet:param name="newsID" value="<%=currentNews.getId().toString()%>"/>
					</liferay-portlet:renderURL>
					<a href="${linkToSingle}">
						<img src="<%=imageService.getPathToMicroblogImage(currentNews.getMainImage(),currentNews)%>" class="newsImage">
					</a>
					<%if (request.isUserInRole("Administrator")) {%>
						<portlet:renderURL var="removeLink">
							<portlet:param name="newsID" value="<%=currentNews.getId().toString()%>"/>
							<portlet:param name="mode" value="remove"/>
						</portlet:renderURL>
						<a href="${removeLink}"
						   style="float: right;" onclick='return confirm("<spring:message code="form.confDelete"/>")'>
							<div class="panelbtn panelbtn-right fs20 icon-pcpremove" aria-hidden="true"></div>
						</a>
					<%}%>
					<div class="newsHeader">
						<a href="${linkToSingle}"><%=currentNews.getTopic()%></a>
					</div>
					<div class="newsText"><%=CustomFunctions.truncateHtml(currentNews.getText(), 700)%></div>
				</div>
				<div class="reply_link_wrap">
					<span class="rel_author"><%=currentNews.getAuthor()%></span>
					<span class="rel_view"><%=currentNews.getNumberOfViews()%></span>
					<span class="rel_date"><%=CustomFunctions.getCreationDate(currentNews.getPublication())%></span>
				</div>
				<div width="100%" align="right">
					<table width="100%">
						<tr>
							<td width="60">
								<img width="60" class="newsDecorImage"
									 src="${pageContext.request.contextPath}/images/news-decor-line-left-end.png"/>
							</td>
							<td width="auto" align="left">
								<img width="100%" class="newsDecorImage"
									 src="${pageContext.request.contextPath}/images/news-decor-line.png"/>
							</td>
							<td width="52">
								<img width="52" class="newsDecorImage"
									 src="${pageContext.request.contextPath}/images/news-decor-center.png"/>
							</td>
							<td width="auto" align="right">
								<img width="100%" class="newsDecorImage"
									 src="${pageContext.request.contextPath}/images/news-decor-line.png"/>
							</td>
							<td width="50">
								<img width="50" class="newsDecorImage"
									 src="${pageContext.request.contextPath}/images/news-decor-line-right-end.png"/>
							</td>
						</tr>
					</table>
				</div>
            <%}%>
        </div>
    </c:if>
</div>