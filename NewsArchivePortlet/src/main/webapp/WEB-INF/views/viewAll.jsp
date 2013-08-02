<%@ page import="ua.dp.stud.StudPortalLib.model.News" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@include file="include.jsp" %>


<%@ taglib prefix="theme" uri="http://liferay.com/tld/theme" %>


<portlet:defineObjects/>
<%
    Collection<News> news = (Collection) request.getAttribute("news");
    int pagesCount = (Integer) request.getAttribute("pagesCount");
    int currentPage = (Integer) request.getAttribute("currentPage");
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
<%if (request.isUserInRole("Administrator") || request.isUserInRole("User")) { %>
<portlet:renderURL var="addNewsUrl">
    <portlet:param name="mode" value="add"/>
</portlet:renderURL>
<div class="portlet-content-controlpanel fs20">
    <a style="float: right" href="${addNewsUrl}">
        <div class="panelbtn panelbtn-right icon-pcpfile" aria-hidden="true"></div>
    </a>
</div>
<%}%>
<div id="contentDiv">
    <liferay-ui:success message='<spring:message code="msg.successAdd"/>' key="success-add"/>

    <c:if test="${not empty news}">
        <div id="newsTable">
            <%for (News currentNews : news) {%>
				<div><div width="100%">
					<% if (request.isUserInRole("Administrator")) { %>
					<a style="float: right"
					   href='<portlet:renderURL><portlet:param name="newsId" value="<%=currentNews.getId().toString()%>"/><portlet:param name="mode" value="delete" /></portlet:renderURL>'
					   onclick='return confirm("<spring:message code="form.confDelete"/>")'>
						<div class="panelbtn panelbtn-right fs20 icon-pcpremove" aria-hidden="true"></div>
					</a>
					<%}%>
					<a href='<portlet:renderURL><portlet:param name="newsID" value="<%=currentNews.getId().toString()%>"/></portlet:renderURL>'>
						<img src="<%= imageService.getPathToMicroblogImage(currentNews.getMainImage(),currentNews) %>"
							 class="newsImage">
					</a>
					<div class="newsHeader">
						<a href='<portlet:renderURL><portlet:param name="newsID" value="<%=currentNews.getId().toString()%>"/></portlet:renderURL>'>
							<%=currentNews.getTopic()%>

						</a>
					</div>
					<div class="newsText">
							<%--50 as said Anton--%>
						<%= CustomFunctions.truncateHtml(currentNews.getText(), 700) %>
					</div>
				</div>
				<div class="reply_link_wrap">
					<span class="rel_author"><%=currentNews.getAuthor()%></span>
					<span class="rel_view"><%=currentNews.getNumberOfViews()%></span>
					<span class="rel_date"><%=CustomFunctions.getCreationDate(currentNews.getPublication())%></span>
				</div>
				<div width="100%" align="right">
					<table width="90%">
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

    <table width="90%">
        <tr>
            <td width="80" align="left">
                <portlet:actionURL name="pagination" var="pagPrev">
                    <portlet:param name="direction" value="prev"/>
                    <portlet:param name="pageNumber" value="<%=String.valueOf(currentPage)%>"/>
                </portlet:actionURL>
                <a href="${pagPrev}">
                    <img class="paginationImage"
                         src="${pageContext.request.contextPath}/images/pagin-left.png"/>
                </a>
            </td>

            <td width="auto" align="center" valign="center">
                <%-- PAGINATION --%>
                <%if (skippedBeginning) {%>
                <%-- HIDING FIRST PAGES --%>
                <a href="<portlet:actionURL name="pagination"><portlet:param name="pageNumber" value="1"/></portlet:actionURL>">1</a>
                <label> ... </label>
                <%}%>

                <%-- SHOWING CURRENT PAGE NEAREST FROM LEFT AND RIGHT --%>
                <%
                    for (int pageNumb = leftPageNumb; pageNumb <= rightPageNumb; ++pageNumb) {
                        if (pageNumb != currentPage) {
                %>
                <a href="<portlet:actionURL name="pagination"><portlet:param name="pageNumber" value="<%=String.valueOf(pageNumb)%>"/></portlet:actionURL>"><%=pageNumb%>
                </a>
                <%} else {%>
                <label style="color: #28477C; font-size: 40px;"><%=pageNumb%>
                </label>
                <%}%>
                <%}%>

                <%if (skippedEnding) {%>
                <%-- HIDING LAST PAGES --%>
                <label> ... </label>
                <a href="<portlet:actionURL name="pagination"><portlet:param name="pageNumber" value="<%=String.valueOf(pagesCount)%>"/></portlet:actionURL>"><%=pagesCount%>
                </a>
                <%}%>
            </td>
            <td width="80" align="right">
                <portlet:actionURL name="pagination" var="pagNext">
                    <portlet:param name="direction" value="next"/>
                    <portlet:param name="pageNumber" value="<%=String.valueOf(currentPage)%>"/>
                </portlet:actionURL>
                <a href="${pagNext}">
                    <img class="paginationImage"
                         src="${pageContext.request.contextPath}/images/pagin-right.png"/>
                </a>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
