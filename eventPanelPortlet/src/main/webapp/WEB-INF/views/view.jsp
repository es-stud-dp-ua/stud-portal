<%@ page import="ua.dp.stud.StudPortalLib.model.News" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.Organization" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@include file="include.jsp" %>

<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/icoMoon/style.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>

<%
    int myNewsSize = (Integer) request.getAttribute("myNewsSize");
    int myOrgSize = (Integer) request.getAttribute("myOrgSize");
    int adminOrgSize = (Integer) request.getAttribute("adminOrgSize");
    int adminNewsSize = (Integer) request.getAttribute("adminNewsSize");
    int newsInMyComm = (Integer) request.getAttribute("newsInMyComm");

    if (request.getAttribute("class") != null) {
        Collection<News> newsList = new ArrayList<News>();
        Collection<Organization> orgList = new ArrayList<Organization>();
        if (request.getAttribute("type") == "News") {
            newsList = (Collection) request.getAttribute("newsList");
        } else if (request.getAttribute("type") == "Organization") {
            orgList = (Collection) request.getAttribute("orgList");
        }
        int pageCount = (Integer) request.getAttribute("pageCount");
        int currentPage = (Integer) request.getAttribute("currentPage");
        ImageService imageService = (ImageService) pageContext.findAttribute("imageService");
        ;
        String className = (String) request.getAttribute("class");
%>
<div style="display: none;">
    <div id="eventContainer" class="${class}">
        <div class="LeftSwith leftButton">
            <a onclick="rewindPanel(${currentPage}, 'prev', '${class}');">
                <div id="leftBtn"></div>
            </a>
        </div>
        <div class="LeftSwith EventContent">
            <% if (request.getAttribute("type") == "News") {
                for (News currentNews : newsList) {%>
            <liferay-portlet:renderURL plid="${plid}" var="linkToSingle" portletName="${portlet_name}">
                <liferay-portlet:param name="newsID" value="<%=currentNews.getId().toString()%>"/>
            </liferay-portlet:renderURL>
            <div style=<%if (className.equals("adminNews") || className.equals("newsInMyComm")){%>"min-height: 110px;"
            <%} else {%>"min-height: 60px;"<%}%>>
            <img src="<%= imageService.getPathToMicroblogImage(currentNews.getMainImage(),currentNews) %>"
                 class="newsImage">
            <a href="${linkToSingle}"><p><%=currentNews.getTopic()%>
            </p></a>
            <%if (className.equals("adminNews") || className.equals("newsInMyComm")) {%>
            <div>
                <a href="javascript:;" class="disapprove" currentPage="${currentPage}" epClass="${class}" epID="<%=currentNews.getId()%>" approve="false">
                    <div style="padding-top: 10px;" id="like"><span aria-hidden="true" class="icon-thumbs-up"></span>
                    </div>
                </a>
                <a onclick="approve(${currentPage}, '${class}', <%=currentNews.getId()%>, true, '');">
                    <div id="like"><span aria-hidden="true" class="icon-thumbs-up-2"></span></div>
                </a>
            </div>
            <%}%>
        </div>
        <%
            }
        } else if (request.getAttribute("type") == "Organization") {
            for (Organization currentOrg : orgList) {
        %>
        <liferay-portlet:renderURL plid="${plid}" var="linkToSingle1" portletName="${portlet_name}">
            <liferay-portlet:param name="orgsID" value="<%=currentOrg.getId().toString()%>"/>
        </liferay-portlet:renderURL>
        <div style=<%if (className.equals("adminCommunity")){%>"min-height: 110px;"
        <%} else {%>"min-height: 60px;"<%}%>>
        <img src="<%= imageService.getPathToMicroblogImage(currentOrg.getMainImage(),currentOrg) %>" class="newsImage">
        <a href="${linkToSingle1}"><p><%=currentOrg.getTitle()%>
        </p></a>
        <%if (className.equals("adminCommunity")) {%>
        <div>
            <a href="javascript:;" class="disapprove" currentPage="${currentPage}" epClass="${class}" epID="<%=currentOrg.getId()%>" approve="false">
                <div style="padding-top: 10px;" id="like"><span aria-hidden="true" class="icon-thumbs-up"></span></div>
            </a>
            <a onclick="approve(${currentPage}, '${class}', <%=currentOrg.getId()%>, true, '');">
                <div id="like"><span aria-hidden="true" class="icon-thumbs-up-2"></span></div>
            </a>
        </div>
        <%}%>
    </div>
    <%
            }
        }
    %>
    <div style="text-align: center;">
        ${currentPage} <spring:message code="viewAll.From"/> ${pageCount}
    </div>
</div>
<div class="LeftSwith rightButton">
    <a onclick="rewindPanel(${currentPage}, 'next', '${class}');">
        <div id="rightBtn"></div>
    </a>
</div>
</div></div>
<%
    }
%>
<%if (request.isUserInRole("User") || request.isUserInRole("Administrator")) {%>
<div id="ep">
    <div class="event-panel" id="event-panel-wraper">
        <div id="elem">
            <div class="event <%if (myNewsSize > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                 dataclass="myNews" title="<spring:message code="viewAll.myNews"/>">
                <span aria-hidden="true" class="icon-bubbles-2"></span>
            </div>
            <c:if test="${myNewsSize > 0 }"><span id="count">${myNewsSize}</span></c:if>
        </div>
        <div id="elem">
            <div class="event <%if (myOrgSize > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                 dataclass="myCommunity" title="<spring:message code="viewAll.myCommunities"/>">
                <span aria-hidden="true" class="icon-earth"></span>
            </div>
            <c:if test="${myOrgSize > 0 }"> <span id="count">${myOrgSize}</span></c:if>
        </div>
        <div id="elem">
            <div class="event <%if (newsInMyComm > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                 dataclass="newsInMyComm" title="<spring:message code="viewAll.NewsInMyComm"/>">
                <span aria-hidden="true" class="icon-stackoverflow"></span>
            </div>
            <c:if test="${newsInMyComm > 0}"><span id="count">+${newsInMyComm}</span></c:if>
        </div>
        <%if (request.isUserInRole("Administrator")) {%>
        <div id="elem">
            <div class="event <%if (adminNewsSize > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                 dataclass="adminNews" title="<spring:message code="viewAll.News"/>">
                <span aria-hidden="true" class="icon-office"></span>
            </div>
            <c:if test="${adminNewsSize > 0}"><span id="count">${adminNewsSize}</span></c:if>
        </div>
        <div id="elem">
            <div class="event <%if (adminOrgSize > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                 dataclass="adminCommunity" title="<spring:message code="viewAll.Communities"/>">
                <span aria-hidden="true" class="icon-share"></span>
            </div>
            <c:if test="${adminOrgSize > 0}">
                <span id="count">${adminOrgSize}</span></c:if>
        </div>
        <%}%>
    </div>
</div>
<div id="epOpenModal" class="modalDialog">
	<div>
		<a href="javascript:;" title='<spring:message code="viewAll.Disapprove.Cancel"/>' class="close" onclick="$('#epOpenModal').hide()">X</a>
		<h3><spring:message code="viewAll.Disapprove.Cause"/></h3>
		<div>
			<div><spring:message code="viewAll.Disapprove.Message"/></div>
			<textarea id="epComment"></textarea>
			<button id="epSendApprove" ><spring:message code="viewAll.Disapprove.Answer"/></button>
		</div>
	</div>
</div>
<script>
    function rewindPanel(currentPage, direction, paramsName) {
        $.ajax({
            url: "<portlet:renderURL/>&mode=pagination",
            cache: false,
            data: {currentPage: currentPage, direction: direction, modelView: paramsName},
            dataType: "html",
            type: "GET",
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                var myNews = $("." + paramsName);
                myNews.html($('.' + paramsName, data));
                setOnClick();
            }
        });
    }
    function approve(currentPage, modelView, objectId, appr, comment) {
        $.ajax({
            url: "<portlet:renderURL/>&mode=approve",
            cache: false,
            data: {currentPage: currentPage, modelView: modelView, objectId: objectId, appr: appr, comment:comment},
            dataType: "html",
            type: "GET",
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                var myNews = $("." + modelView);
                myNews.html($('.' + modelView, data));
            }
        });
    }
    $('#epSendApprove').click(function() {
        $('#epOpenModal').hide();
        if ($('#epComment').val().length > 5) {
            approve(EPWrapper.currentPage, EPWrapper.epClass, EPWrapper.epID, EPWrapper.approve, $('#epComment').val());
        } else {
            alert('<spring:message code="viewAll.Disapprove.TooShort"/>');
        }
    });
</script>
<%}%>