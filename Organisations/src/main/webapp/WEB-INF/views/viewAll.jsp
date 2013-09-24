<%@ page import="ua.dp.stud.StudPortalLib.model.Organization" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.OrganizationType" %>
<%@ page import="ua.dp.stud.StudPortalLib.service.OrganizationService" %>
<%@ page import="ua.dp.stud.StudPortalLib.service.impl.OrganizationServiceImpl" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@include file="include.jsp" %>


<portlet:defineObjects/>
<%
    Collection<Organization> orgs = (Collection) request.getAttribute("organisations");
    Integer pagesCount = (Integer) request.getAttribute("pagesCount");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    int leftPageNumb = (Integer) request.getAttribute("leftPageNumb");
    int rightPageNumb = (Integer) request.getAttribute("rightPageNumb");
    boolean skippedBeginning = (Boolean) request.getAttribute("skippedBeginning");
    boolean skippedEnding = (Boolean) request.getAttribute("skippedEnding");
    OrganizationType type = null;
    if (request.getAttribute("type") != null)
        type = (OrganizationType) request.getAttribute("type");
    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    String imagePath = new StringBuilder(themeDisplay.getPortalURL()).append('/')
            .append(themeDisplay.getPathImage()).append("/image_gallery?img_id=").toString();
    Collection<String> allTypes = (Collection) (OrganizationType.allTypes());
    String temp;
%>

<html>
    <head>
    </head>
    <body>
    <liferay-ui:success message='<spring:message code="msg.successAdd"/>' key="success-add"/>
    <div id="contentDiv">
        <%if (request.isUserInRole("Administrator") || request.isUserInRole("User")) { %>
        <div class="portlet-content-controlpanel fs20">
            <a style="float: right" href='<portlet:renderURL><portlet:param name="mode" value="add"/></portlet:renderURL>'>
                <div class="panelbtn panelbtn-right icon-pcpfile" aria-hidden="true"></div>
            </a>
        </div>
        <%}%>
        <div class="cmt-types">
            <form method="post" action="<portlet:renderURL/>">
                <% for (String currentType : allTypes) {
                temp = new String("form." + currentType);%>
                <div class="ribbon-wrapper">
                    <button class="btntype"
                            style=" width: 150px; height: 40px;   margin-left: -10px;  border-color: #4473B9;" name="type"
                            value="<%=currentType%>" id="<%=currentType%>">
                        <spring:message code="<%=temp%>"/></button>
                    <div class="ribbon-edge-topleft"></div>
                    <div class="ribbon-edge-bottomleft"></div>
                </div>

                <br/>
                <% }
                if (type != null) {%>
                <script>
                    $(document).ready(function() {
                        $("#" + "<%=type.toString()%>").removeClass('btntype').addClass('btnselected');
                    });
                </script>
                <%}%>
            </form>
        </div>
        <div id="newsTable">
            <% if (!orgs.isEmpty()) {
                 for (Organization currentOrgs : orgs){%>
            <div width="100%">
                <img src="<%= imageService.getPathToMicroblogImage(currentOrgs.getMainImage(),currentOrgs) %>"
                     class="newsImage">

                <div class="newsHeader">
                    <a href='<portlet:renderURL><portlet:param name="orgsId" value="<%=currentOrgs.getId().toString()%>"/></portlet:renderURL>' >
                        <%=currentOrgs.getTitle()%>
                    </a>
                </div>
                <div class="newsText"><%= CustomFunctions.truncateHtml(currentOrgs.getText(), 700) %>
                </div>
                <% if (request.isUserInRole("Administrator")) { %>
                <div class="portlet-content-controlpanel fs20"style="width: 8.6%;float: right;">
                <a style="float: right" href='<portlet:renderURL><portlet:param name="orgsID" value="<%=currentOrgs.getId().toString()%>"/><portlet:param name="mode" value="delete" /></portlet:renderURL>'
                   onclick='return confirm("<spring:message code="form.confDelete"/>")'>
                    <div class="panelbtn panelbtn-right fs20 icon-pcpremove" aria-hidden="true"></div>
                </a>
                <a style="float: right" href='<portlet:renderURL><portlet:param name="orgsID" value="<%=currentOrgs.getId().toString()%>"/><portlet:param name="mode" value="edit" /></portlet:renderURL>'
                    <!--<spring:message code="viewSingle.Edit"/>-->
                    <div class="panelbtn panelbtn-right icon-pcppencil" aria-hidden="true"></div>
                </a>
            </div>
            <%}%>

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
        <%}%>
        <%if(orgs.size()>9||currentPage>1){%>
        <table width="90%">
            <tr>
                <td width="80" align="left">
            <portlet:actionURL name="pagination" var="pagPrev">
                <portlet:param name="direction" value="prev"/>
                <portlet:param name="pageNumber" value="<%=String.valueOf(currentPage)%>"/>
                <% if (type != null) {%><portlet:param name="type" value="<%=String.valueOf(type)%>"/><%} %>
            </portlet:actionURL>
            <a href="${pagPrev}">
                <img class="paginationImage"
                     src="${pageContext.request.contextPath}/images/pagin-left.png"/>
            </a>
            </td>

            <td width="150" align="center" valign="center">
                <%-- PAGINATION --%>
                <%if (skippedBeginning) {%>
                <%-- HIDING FIRST PAGES --%>
                <a href="<portlet:actionURL name="pagination"><portlet:param name="pageNumber" value="1"/>
                    <% if (type!=null) {%><portlet:param name="type" value="<%=String.valueOf(type)%>"/><%} %>
                    </portlet:actionURL>">1</a>
                <label> ... </label>
                <%}%>
                <%-- SHOWING CURRENT PAGE NEAREST FROM LEFT AND RIGHT --%>
                <%
                    for (int pageNumb = leftPageNumb; pageNumb <= rightPageNumb; ++pageNumb) {
                        if (pageNumb != currentPage) {%>
                <a href="<portlet:actionURL name="pagination"><portlet:param name="pageNumber" value="<%=String.valueOf(pageNumb)%>"/>
                    <% if (type!=null) {%><portlet:param name="type" value="<%=String.valueOf(type)%>"/><%} %>
                    </portlet:actionURL>"><%=pageNumb%>
                </a>
                <%} else {%>
                <label style="color: #28477C; font-size: 40px;"><%=pageNumb%>
                </label>
                <%}%>
                <%}%>
                <%if (skippedEnding) {%>
                <%-- HIDING LAST PAGES --%>
                <label> ... </label>
                <a href="<portlet:actionURL name="pagination"><portlet:param name="pageNumber" value="<%=String.valueOf(pagesCount)%>"/>
                    <% if (type!=null) {%><portlet:param name="type" value="<%=String.valueOf(type)%>"/><%} %>
                    </portlet:actionURL>"><%=String.valueOf(pagesCount)%>
                </a>
                <%}%>
            </td>
            <td width="80" align="right">
            <portlet:actionURL name="pagination" var="pagNext">
                <portlet:param name="direction" value="next"/>
                <portlet:param name="pageNumber" value="<%=String.valueOf(currentPage)%>"/>
                <% if (type != null) {%><portlet:param name="type" value="<%=String.valueOf(type)%>"/><%} %>
            </portlet:actionURL>
            <a href="${pagNext}">
                <img class="paginationImage"
                     src="${pageContext.request.contextPath}/images/pagin-right.png"/>
            </a>
            </td>
            </tr>
        </table>
           
        <%}
            }
            else
            {
        %>
        ` <h1><b><spring:message code="orgs.empty"/></b></h1>
            <%}%>
    </div>
</div>
</body>
</html>