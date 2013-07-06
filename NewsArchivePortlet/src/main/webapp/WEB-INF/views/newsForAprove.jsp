<%@ page import="ua.dp.stud.StudPortalLib.model.News" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.CustomFunctions" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@include file="include.jsp" %>


<%@ taglib prefix="theme" uri="http://liferay.com/tld/theme" %>
<%if (request.isUserInRole("Administrator") ||request.isUserInRole("User")) { %>
<portlet:defineObjects/>
<%
    Collection<Collection<News>> news = (Collection<Collection<News>>) request.getAttribute("newsForAprove");
    int pagesCount = (Integer) request.getAttribute("pagesCount");
    int currentPage = (Integer) request.getAttribute("currentPage");

    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    String imagePath = new StringBuilder(themeDisplay.getPortalURL()).append('/')
            .append(themeDisplay.getPathImage()).append("/image_gallery?img_id=").toString();
    ImageService imageService = (ImageService)pageContext.findAttribute("imageService");

    int nearbyPages = 2; //number of pages to show to left and right of current
    int overallPages = 7; //overall number of pages
    int leftPageNumb = Math.max(1, currentPage - nearbyPages),
            rightPageNumb = Math.min(pagesCount, currentPage + nearbyPages);
    boolean skippedBeginning = false,
            skippedEnding = false;
    //todo: move this code to controller
    if (pagesCount <= overallPages) {
        leftPageNumb = 1;                 //all pages are shown
        rightPageNumb = pagesCount;
    } else {
        if (currentPage > 2 + nearbyPages) { //if farther then page #1 + '...' + nearby pages
            skippedBeginning = true;        // will look like 1 .. pages
        } else {
            leftPageNumb = 1;             //shows all first pages
            rightPageNumb = 2 + 2 * nearbyPages; //#1 + nearby pages + current + nearby pages
        }

        if (currentPage < pagesCount - (nearbyPages + 1)) { //if farther then nearby + '...' + last
            skippedEnding = true;         //will look like pages .. lastPage
        } else {
            leftPageNumb = (pagesCount - 1) - 2 * nearbyPages;  //shows all last pages:
            rightPageNumb = pagesCount;
        }
    }

%>

<html>
<head>
</head>

<body>
    <%if (request.isUserInRole("Administrator") ||request.isUserInRole("User")) { %>
    <div class="portlet-content-controlpanel fs20">
        <a style="float: right" href="<portlet:renderURL/>&mode=add">
            <div class="panelbtn panelbtn-right icon-pcpfile" aria-hidden="true"></div>
            <!--<spring:message code="viewAll.addNews"/>-->
        </a>
    </div>
    <%} %>
<div id="contentDiv">

    <liferay-ui:success message='<%=<spring:message code="viewAll.successAdd"/>%>' key="success-add"/>

    <% if (!news.isEmpty()) {%>
    <table id="newsTable">
        <%
            for (Collection<News> currentNewsForAprove : news) {
                    for (News currentNews :currentNewsForAprove )
                                               {
        %>
        <tr>
            <td width="100%">
                <div width="100%">
                    <a href="<portlet:renderURL/>&newsID=<%=currentNews.getId()%>">
                        <img src="<%= imageService.getPathToMicroblogImage(currentNews.getMainImage(),currentNews) %>"
                             class="newsImage">
                    </a>

                    <div class="newsHeader">
                        <a href="<portlet:renderURL/>&newsID=<%=currentNews.getId()%>">
                            <%=currentNews.getTopic()%>

                        </a>
                    </div>
                    <div class="newsText">
                        <%--50 as said Anton--%>
                        <%= CustomFunctions.truncateWords(currentNews.getText(), 50) %>


                        <%--another way of doing that--%>
                        <%--<%@taglib uri="http://www.stud.dp.ua/customTags" prefix="jj" %>--%>
                        <%--pageContext.setAttribute("currentNews", currentNews);--%>
                        <%--${jj:truncateWords(currentNews.text, 50)}--%>
                    </div>
                    <div id="portlet-content-controlpanel">
                        <% if (request.isUserInRole("Administrator")) { %>
                        <a style="float: right" href="<portlet:renderURL/>&newsId=<%=currentNews.getId()%>&mode=delete"
                           onclick='return confirm("<spring:message code="form.confDelete"/>")'>
                            <!--<spring:message code="form.delete"/>-->
                            <div class="panelbtn panelbtn-right fs20 icon-pcpremove" aria-hidden="true"></div>
                        <%}%>
                    </div>
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
                </div>
            </td>
        </tr>
        <%}%>
    </table>
    <% } }%>

    <table width="90%">
        <tr>
            <td width="80" align="left">
                <a href="<portlet:actionURL name="pagination"/>&direction=prev&pageNumber=<%=currentPage%>">
                    <img class="paginationImage"
                         src="${pageContext.request.contextPath}/images/pagin-left.png"/>
                </a>
            </td>

            <td width="auto" align="center" valign="center">
                <%-- PAGINATION --%>
                <%if (skippedBeginning) {%>
                <%-- HIDING FIRST PAGES --%>
                <a href="<portlet:actionURL name="pagination"/>&pageNumber=1">1</a>
                <label> ... </label>
                <%}%>

                <%-- SHOWING CURRENT PAGE NEAREST FROM LEFT AND RIGHT --%>
                <%
                    for (int pageNumb = leftPageNumb; pageNumb <= rightPageNumb; ++pageNumb) {
                        if (pageNumb != currentPage) {
                %>
                <a href="<portlet:actionURL name="pagination"/>&pageNumber=<%=pageNumb%>"><%=pageNumb%>
                </a>
                <%} else {%>
                <label><%=pageNumb%>
                </label>
                <%}%>
                <%}%>

                <%if (skippedEnding) {%>
                <%-- HIDING LAST PAGES --%>
                <label> ... </label>
                <a href="<portlet:actionURL name="pagination"/>&pageNumber=<%=pagesCount%>"><%=pagesCount%>
                </a>
                <%}%>
            </td>
            <td width="80" align="right">
                <a href="<portlet:actionURL name="pagination"/>&direction=next&pageNumber=<%=currentPage%>">
                    <img class="paginationImage"
                         src="${pageContext.request.contextPath}/images/pagin-right.png"/>
                </a>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
<%}%>