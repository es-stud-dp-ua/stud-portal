<%@ page import="ua.dp.stud.StudPortalLib.model.News" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.Events" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.Organization" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.Course" %>
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
	int myEventsSize = (Integer) request.getAttribute("myEventsSize");
	int myCoursesSize = (Integer) request.getAttribute("myCoursesSize");
	int adminEventsSize = (Integer) request.getAttribute("adminEventsSize");
    int adminCoursesSize = (Integer) request.getAttribute("adminCoursesSize");
    if (request.getAttribute("class") != null) {
        Collection<News> newsList = new ArrayList<News>();
        Collection<Organization> orgList = new ArrayList<Organization>();
		Collection<Events> eventsList = new ArrayList<Events>();
		Collection<Course> coursesList = new ArrayList<Course>();
        if (request.getAttribute("type").equals("News")) {
            newsList = (Collection) request.getAttribute("newsList");
        } else if (request.getAttribute("type").equals("Organization")) {
            orgList = (Collection) request.getAttribute("orgList");
        } else if (request.getAttribute("type").equals("Events")) {
			eventsList = (Collection) request.getAttribute("eventsList");
		} else if (request.getAttribute("type").equals("Courses")) {
          			coursesList = (Collection) request.getAttribute("coursesList");
        }
        int pageCount = (Integer) request.getAttribute("pageCount");
        int currentPage = (Integer) request.getAttribute("currentPage");
        ImageService imageService = (ImageService) pageContext.findAttribute("imageService");
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
            <% if (request.getAttribute("type").equals("News")) {
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
        } else if (request.getAttribute("type").equals("Organization")) {
            for (Organization currentOrg : orgList) {%>
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
        <%String temp = new String("form." + currentOrg.getOrganizationType());%>
        <div style="font-size:12px""><spring:message code="<%=temp%>"/></div>
    </div>
    <%
            }
        } else if (request.getAttribute("type").equals("Events")) {
			for (Events currentEvent : eventsList) {%>
		<liferay-portlet:renderURL plid="${plid}" var="linkToSingle2" portletName="${portlet_name}">
            <liferay-portlet:param name="eventID" value="<%=currentEvent.getId().toString()%>"/>
        </liferay-portlet:renderURL>
        <div style=<%if (className.equals("adminEvents")){%>"min-height: 110px;"
        <%} else {%>"min-height: 60px;"<%}%>>
        <img src="<%= imageService.getPathToMicroblogImage(currentEvent.getMainImage(),currentEvent) %>" class="newsImage">
        <a href="${linkToSingle2}"><p><%=currentEvent.getTitle()%>
        </p></a>
        <%if (className.equals("adminEvents")) {%>
        <div>
            <a href="javascript:;" class="disapprove" currentPage="${currentPage}" epClass="${class}" epID="<%=currentEvent.getId()%>" approve="false">
                <div style="padding-top: 10px;" id="like"><span aria-hidden="true" class="icon-thumbs-up"></span></div>
            </a>
            <a onclick="approve(${currentPage}, '${class}', <%=currentEvent.getId()%>, true, '');">
                <div id="like"><span aria-hidden="true" class="icon-thumbs-up-2"></span></div>
            </a>
        </div>
        <%}%>
    </div>
    <%
                }
            } else if (request.getAttribute("type").equals("Courses")) {
    			for (Course currentCourse : coursesList) {%>
    		<liferay-portlet:renderURL plid="${plid}" var="linkToSingle3" portletName="${portlet_name}">
                <liferay-portlet:param name="courseId" value="<%=currentCourse.getId().toString()%>"/>
                <portlet:param name="view" value="course"/>
            </liferay-portlet:renderURL>
            <div style=<%if (className.equals("adminCourses")){%>"min-height: 110px;"
            <%} else {%>"min-height: 60px;"<%}%>>
            <img src="<%= imageService.getPathToMicroblogImage(currentCourse.getMainImage(),currentCourse) %>" class="newsImage">
            <a href="${linkToSingle3}"><p><%=currentCourse.getCourseName()%>
            </p></a>
            <%if (className.equals("adminCourses")) {%>
            <div>
                <a href="javascript:;" class="disapprove" currentPage="${currentPage}" epClass="${class}" epID="<%=currentCourse.getId()%>" approve="false">
                    <div style="padding-top: 10px;" id="like"><span aria-hidden="true" class="icon-thumbs-up"></span></div>
                </a>
                <a onclick="approve(${currentPage}, '${class}', <%=currentCourse.getId()%>, true, '');">
                    <div id="like"><span aria-hidden="true" class="icon-thumbs-up-2"></span></div>
                </a>
            </div>
            <%}%>
        </div>
	<%}}%>
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
<%if ((request.isUserInRole("User")) && (!request.isUserInRole("Administrator"))) {%>
<div id="ep">
    <div class="event-panel" id="event-panel-wraper">
        <div id="elem">
            <div class="event <%if (myOrgSize > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                 dataclass="myCommunity" title="<spring:message code="viewAll.myCommunities"/>">
                <span aria-hidden="true" class="icon-earth"></span>
            </div>
            <c:if test="${myOrgSize > 0 }"> <span id="count">${myOrgSize}</span></c:if>
        </div>
		<div id="elem">
            <div class="event <%if (myEventsSize > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                 dataclass="myEvents" title="<spring:message code="viewAll.myEvents"/>">
                <span aria-hidden="true" class="icon-bubbles-3"></span>
            </div>
            <c:if test="${myEventsSize > 0 }"> <span id="count">${myEventsSize}</span></c:if>
        </div>
        <div id="elem">
                <div class="event <%if (myCoursesSize > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                  dataclass="myCourses" title="<spring:message code="viewAll.myCourses"/>">
                 <span aria-hidden="true" class="icon-stackoverflow"></span>
                </div>
                <c:if test="${myCoursesSize > 0 }"> <span id="count">${myCoursesSize}</span></c:if>
        </div>
    </div>
</div>
<%}%>
<%if (request.isUserInRole("Press")) {%>
<div id="ep">
    <div class="event-panel" id="event-panel-wraper">
         <div id="elem">
                    <div class="event <%if (myNewsSize > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                         dataclass="myNews" title="<spring:message code="viewAll.myNews"/>">
                        <span aria-hidden="true" class="icon-earth"></span>
                    </div>
                    <c:if test="${myNewsSize > 0 }"> <span id="count">${myNewsSize}</span></c:if>
         </div>
         <div id="elem">
                     <div class="event <%if (myOrgSize > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                          dataclass="myCommunity" title="<spring:message code="viewAll.myCommunities"/>">
                         <span aria-hidden="true" class="icon-earth"></span>
                     </div>
                     <c:if test="${myOrgSize > 0 }"> <span id="count">${myOrgSize}</span></c:if>
         </div>
         <div id="elem">
             <div class="event <%if (myEventsSize > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                          dataclass="myEvents" title="<spring:message code="viewAll.myEvents"/>">
                         <span aria-hidden="true" class="icon-bubbles-3"></span>
             </div>
              <c:if test="${myEventsSize > 0 }"> <span id="count">${myEventsSize}</span></c:if>
         </div>
         <div id="elem">
              <div class="event <%if (myCoursesSize > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                  dataclass="myCourses" title="<spring:message code="viewAll.myCourses"/>">
                  <span aria-hidden="true" class="icon-stackoverflow"></span>
              </div>
              <c:if test="${myCoursesSize > 0 }"> <span id="count">${myCoursesSize}</span></c:if>
         </div>


    </div>

</div>


<%}%>
<%if (request.isUserInRole("Administrator")) {%>
<div id="ep">
    <div class="event-panel" id="event-panel-wraper">
        <div id="elem">
            <div class="event <%if (adminNewsSize > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                 dataclass="adminNews" title="<spring:message code="viewAll.News"/>">
                <span aria-hidden="true" class="icon-office"></span>
            </div>
            <c:if test="${adminNewsSize > 0}"><span class="count_adminNews" id="count">${adminNewsSize}</span></c:if>
        </div>
        <div id="elem">
            <div class="event <%if (adminOrgSize > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                 dataclass="adminCommunity" title="<spring:message code="viewAll.Communities"/>">
                <span aria-hidden="true" class="icon-share"></span>
            </div>
            <c:if test="${adminOrgSize > 0}"><span class="count_adminCommunity" id="count">${adminOrgSize}</span></c:if>
        </div>
		<div id="elem">
            <div class="event <%if (adminEventsSize > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                 dataclass="adminEvents" title="<spring:message code="viewAll.adminEvents"/>">
                <span aria-hidden="true" class="icon-bubbles"></span>
            </div>
            <c:if test="${adminEventsSize > 0}"><span class="count_adminEvents" id="count">${adminEventsSize}</span></c:if>
        </div>
        <div id="elem">
                    <div class="event <%if (adminCoursesSize > 0){%> newEvent <%}%>" rel="<portlet:renderURL/>&mode=pagination"
                         dataclass="adminCourses" title="<spring:message code="viewAll.adminCourses"/>">
                        <span aria-hidden="true" class="icon-stackoverflow"></span>
                    </div>
                    <c:if test="${adminCoursesSize > 0}"><span class="count_adminCourses" id="count">${adminCoursesSize}</span></c:if>
        </div>
    </div>
</div>
<%}%>
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
        var count = $(".count_"+modelView).text();
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
               if(count-1==0){
                $(".count_"+modelView).remove();
                $("#eventContainer").remove();
               } else {
            	   $(".count_"+modelView).html(count-1);
               }
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
