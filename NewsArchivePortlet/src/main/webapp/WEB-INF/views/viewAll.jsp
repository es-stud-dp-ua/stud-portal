<%@ page import="ua.dp.stud.StudPortalLib.model.News" %>
<%@ page import="ua.dp.stud.StudPortalLib.dto.NewsDto" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@include file="include.jsp" %>


<%@ taglib prefix="theme" uri="http://liferay.com/tld/theme" %>


<portlet:defineObjects/>
<%
    Collection<NewsDto> newsDto = (Collection) request.getAttribute("newsDto");
    int pagesCount = (Integer) request.getAttribute("pagesCount");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    ImageService imageService = (ImageService) pageContext.findAttribute("imageService");
    //todo: remove unused variables
    int nearbyPages = (Integer) request.getAttribute("nearbyPages"); //number of pages to show to left and right of current
    int overallPages = (Integer) request.getAttribute("overallPages"); //overall number of pages
    int leftPageNumb = (Integer) request.getAttribute("leftPageNumb"),
            rightPageNumb = (Integer) request.getAttribute("rightPageNumb");
    boolean skippedBeginning = (Boolean) request.getAttribute("skippedBeginning"),
            skippedEnding = (Boolean) request.getAttribute("skippedEnding");
%>

<html>
    <head>
    </head>
    <body>
        <%if ((request.isUserInRole("Administrator"))||(request.isUserInRole("Press"))) { %>
    <portlet:renderURL var="addNewsUrl">
        <portlet:param name="mode" value="add"/>
    </portlet:renderURL>
    <div class="portlet-content-controlpanel fs20">
        <a style="float: right" href="${addNewsUrl}">
            <div class="panelbtn panelbtn-right icon-pcpfile" aria-hidden="true"></div>
        </a>
    </div>
    <%}%>
        <liferay-ui:success message='<spring:message code="msg.successAdd"/>' key="success-add"/>

        <c:if test="${not empty newsDto}">
            <div id="newsTable">
                <%for (NewsDto currentNews : newsDto) {%>
				<portlet:renderURL var="newsSingleLink">
					<portlet:param name="newsID" value="<%=currentNews.getId().toString()%>"/>
                                        <portlet:param name="currentPage" value="<%=currentPage.toString()%>"/>
				</portlet:renderURL>
                <div>
					<div style="height: 210px;">
						<div style="min-height: 200px; width: 680px; float: right;">
							<% if ((request.isUserInRole("Administrator"))||(request.isUserInRole("Press"))) { %>
							<div class="portlet-content-controlpanel fs20"style="width: 9.5%;float: right;">
                <a style="float: right"
                 href='<portlet:renderURL><portlet:param name="newsId" value="<%=currentNews.getId().toString()%>"/><portlet:param name="mode" value="delete" /><portlet:param name="currentPage" value="<%=currentPage.toString()%>"/> </portlet:renderURL>'
                 onclick='return confirm("<spring:message code="form.confDelete"/>")'>
                <div class="panelbtn panelbtn-right fs20 icon-pcpremove" aria-hidden="true"></div>
              </a>
                    <a style="float: right"
           href="<portlet:renderURL><portlet:param name="newsId" value="<%=currentNews.getId().toString()%>"/><portlet:param name="mode" value="edit" /></portlet:renderURL>">
           <div class="panelbtn panelbtn-right icon-pcppencil" aria-hidden="true"></div>
        </a>
            </div>
							<%}%>
                            <% request.setAttribute("currentNewsName",currentNews.getName());%>
                            <div class="newsHeader" style="width: 90%"><a href='${newsSingleLink}'><c:out value="${currentNewsName}" escapeXml="true" /></a></div>
							<div class="reply_link_wrap">
								<span class="rel_author"><%=currentNews.getAuthor()%></span>
								<span class="rel_view"><%=currentNews.getNumberOfViews()%></span>
								<span class="rel_date"><%=currentNews.getCreationDate()%></span>
							</div>
							<div class="newsText"><%=currentNews.getText() %></div>
							<div><a href='${newsSingleLink}'><spring:message code="readMore"/></a></div>
                        </div>
						<div style="width: 200px;"><a href='${newsSingleLink}'>
                            <img src="<%=currentNews.getImgPath() %>"
                                 class="newsImage">
                        </a></div>
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
                    </div></div>
                    <%}%>
            </div>
        </c:if>
            <%if((newsDto == null ?0:newsDto.size())>9||currentPage>1){%>
        <table width="90%">
            <tr>
                <td width="80" align="left">
            <portlet:renderURL var="pagPrev">
                <portlet:param name="direction" value="prev"/>
                <portlet:param name="currentPage" value="<%=String.valueOf(currentPage)%>"/>
            </portlet:renderURL>
             <c:if test="${currentPage!=1}">
            <a href="${pagPrev}">
                <img class="paginationImage"
                     src="${pageContext.request.contextPath}/images/pagin-left.png"/>
            </a>
            </c:if>
            </td>

            <td width="auto" align="center" valign="center">
                <%-- PAGINATION --%>
                <%if (skippedBeginning) {%>
                <%-- HIDING FIRST PAGES --%>
                <a href="<portlet:renderURL><portlet:param name="direction" value="temp"/><portlet:param name="currentPage" value="1"/></portlet:renderURL>">1</a>
                <label> ... </label>
                <%}%>

                <%-- SHOWING CURRENT PAGE NEAREST FROM LEFT AND RIGHT --%>
                <%
                    for (int pageNumb = leftPageNumb; pageNumb <= rightPageNumb; ++pageNumb) {
                        if (pageNumb != currentPage) {
                %>
                <a href="<portlet:renderURL>
                <portlet:param name="currentPage" value="<%=String.valueOf(pageNumb)%>"/>
               <portlet:param name="direction" value="temp"/>
                </portlet:renderURL>"><%=pageNumb%>
                </a>
                <%if(pageNumb>0){ %>
                <label>  </label>
                <%}%>
                <%if(pageNumb>9){ %>
                <label>  </label>
                <%}%>
                <%if(pageNumb>99){ %>
                <label>  </label>
                <%}%>
                <%} else {%>
                <label style="color: #28477C; font-size: 40px;"><%=pageNumb%>
                </label>
                <%if(pageNumb>0){ %>
                <label>  </label>
                <%}%>
                <%if(pageNumb>9){ %>
                <label>  </label>
                <%}%>
                <%if(pageNumb>99){ %>
                <label>  </label>
                <%}%>
                <%}%>
                <%}%>

                <%if (skippedEnding) {%>
                <%-- HIDING LAST PAGES --%>
                <label> ... </label>
                <a href="<portlet:renderURL>
                <portlet:param name="currentPage" value="<%=String.valueOf(pagesCount)%>"/>
                <portlet:param name="direction" value="temp"/>
                </portlet:renderURL>"><%=pagesCount%>
                </a>
                <%}%>
            </td>
            <td width="80" align="right">
            <portlet:renderURL var="pagNext">
                <portlet:param name="direction" value="next"/>
                <portlet:param name="currentPage" value="<%=String.valueOf(currentPage)%>"/>
            </portlet:renderURL>
            <c:if test="${currentPage!=pagesCount}">
            <a href="${pagNext}">
                            <img class="paginationImage"
                                 src="${pageContext.request.contextPath}/images/pagin-right.png"/>
                        </a>
            </c:if>

            </td>
            </tr>
        </table>
            <%}%>
</body>
</html>
