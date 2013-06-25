<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>

<table id="calendarTable">
    <h1>AAAAAAAAA</h1>
    <tr>
		<c:forEach var="entry" items="${news}">
			<td
					<c:choose>
						<c:when test="${fn:length(entry.value) gt 0}">class="haveEvent">
						<span<c:choose>
							<c:when test="${days[entry.key]==6 || days[entry.key]==7 }"> class="dayoff">
								<spring:message code="${days[entry.key]}"/>
							</c:when>
							<c:otherwise>
								> <spring:message code="${days[entry.key]}"/>
							</c:otherwise>
						</c:choose></span>
							<br>
							<a href="#">${entry.key}</a>
						</c:when>
						<c:otherwise>
							>
						<span <c:choose>
							<c:when test="${days[entry.key]==6 || days[entry.key]==7 }"> class="dayoff">
								<spring:message code="${days[entry.key]}"/>
							</c:when>
							<c:otherwise>
								> <spring:message code="${days[entry.key]}"/>
							</c:otherwise>
						</c:choose>
						</span>

							<br>
							${entry.key}
						</c:otherwise>
					</c:choose>

					<c:if test="${fn:length(entry.value) gt 0}">
						<div class="pop-up">
							<c:forEach var="event" items="${entry.value}">
								<div class="inner">
									<liferay-portlet:renderURL plid="${plid}" var="linkToSingleNews" portletName="NewsArchive_WAR_NewsArchivePortlet10">
										<liferay-portlet:param name="newsID" value="${event.id}" />
									</liferay-portlet:renderURL>
									<% News news = (News) pageContext.getAttribute("event"); %>
										<img src="<%= imgService.getPathToCalendarImage(news.getMainImage(), news) %>" alt="image" style="float: left; padding-right: 10px;">
										<p>
											<a href="${linkToSingleNews}">${event.topic}</a>
										</p>

										<br>
								</div>
							</c:forEach>
						</div>
					</c:if>
			</td>
		</c:forEach>
	</tr>
</table>

<div id="calendar_month">
	<div class="right" onclick="rewind(${currentYear},${currentMonth},'nextMonth');"></div>
	<div class="month"><spring:message code="${monthToShow}"/></div>
	<div class="left" onclick="rewind(${currentYear},${currentMonth},'prevMonth');"></div>
</div>



<h1>YO YO YO</h1>