<%@ page import="ua.dp.stud.StudPortalLib.model.News" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<% ImageService imgService = new ImageService(); %>

<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
<liferay-portlet:renderURL var="link"/>

<div id="outerCalendarContainer">
	<div id="bodyCalend"><table id="calendarTable">
        <tr>
            <c:forEach var="entry" items="${news}" >
                <td
                        <c:choose>
                            <c:when test="${fn:length(entry.value) gt 0}">class="haveEvent">
                                <span <c:choose>
                                    <c:when test="${days[entry.key]==6 || days[entry.key]==7 }"> class="dayoff">
                                        <spring:message code="${days[entry.key]}"/>
                                    </c:when>
                                    <c:otherwise>
                                        > <spring:message code="${days[entry.key]}"/>
                                    </c:otherwise>
                                </c:choose></span>
                                    <br>
                                    <span class="off">${entry.key}</span>
                            </c:when>
                            <c:otherwise>
                                >
                            <span  <c:choose>
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
                                <c:forEach var="event" items="${entry.value}" varStatus="status">
                                    <div class="inner">
                                        <liferay-portlet:renderURL plid="${plid}" var="linkToSingleNews" portletName="${archive_name}">
                                            <liferay-portlet:param name="newsID" value="${event.id}" />
                                        </liferay-portlet:renderURL>
                                        <% News news = (News) pageContext.getAttribute("event"); %>

                                        <div style="min-height: 71px;"><img src="<%= imgService.getPathToCalendarImage(news.getMainImage(), news) %>" alt="image" style="float: left; padding-right: 10px;">
											<p>
												<a href="${linkToSingleNews}">${event.topic}</a>
											</p>
										</div>

                                        <c:if test="${fn:length(entry.value)-1 ne status.index}">
                                           <br>
                                        </c:if>

                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                </td>
            </c:forEach>
        </tr>
    </table></div>

    <div id="calendar_month">
        <div class="right" onclick="rewind(${currentYear},${currentMonth},'nextMonth');"></div>
        <div class="month"><spring:message code="${monthToShow}"/></div>
        <div class="left" onclick="rewind(${currentYear},${currentMonth},'prevMonth');"></div>
    </div>

</div>