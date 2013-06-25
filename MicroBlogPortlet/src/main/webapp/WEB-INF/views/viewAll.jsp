<%@ page import="ua.dp.stud.StudPortalLib.model.News" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.CustomFunctions" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@include file="include.jsp"%>


<portlet:defineObjects/>
<%
    Collection<News> news = (Collection)request.getAttribute("news");
    Long newsArchivePageID = (Long)request.getAttribute("newsArchivePageID");
    ImageService imageService = new ImageService();
%>

<html>
<head>
</head>

<body>

<div id="contentDiv">
    <div id="tableHeader">
        <spring:message code="header.latestNews" /><br/>
        <br/>
    </div>

    <% if (! news.isEmpty()){%>
    <table id="newsTable">
        <%for(News currentNews:news){%>
        <tr>
            <td width="100%">
                <div width="100%">
                    <a href="<liferay-portlet:renderURL plid="<%=newsArchivePageID%>" portletName="NewsArchive_WAR_studnewsArchive"/>&newsID=<%=currentNews.getId()%>">
                        <img src="<%=imageService.getPathToMicroblogImage(currentNews.getMainImage(),currentNews)%>" class="newsImage">
                    </a>
                    <%if (request.isUserInRole("Administrator")){%>
                        <a href="<liferay-portlet:renderURL/>&mode=remove&newsID=<%=currentNews.getId()%>"
                            style="float: right;" onclick='return confirm("<spring:message code="form.confDelete"/>")'>
                            <div class="panelbtn panelbtn-right fs20 icon-pcpremove" aria-hidden="true"></div>
                        </a>
                    <%}%>
                    <div class="newsHeader">
                        <a href="<liferay-portlet:renderURL
                               plid="<%=newsArchivePageID%>"
                               portletName="NewsArchive_WAR_studnewsArchive"/>&newsID=<%=currentNews.getId()%>">
                            <%=currentNews.getTopic()%>
                        </a>
                    </div>
                    <%--50 as said Anton--%>
                    <div class="newsText"><%=CustomFunctions.truncateWords(currentNews.getText(), 50)%> </div>
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
    <%}%>
</div>

</body>
</html>