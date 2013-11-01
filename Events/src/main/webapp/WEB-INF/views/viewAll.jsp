<%@ page import="ua.dp.stud.StudPortalLib.model.Events" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.EventsType" %>
<%@ page import="ua.dp.stud.StudPortalLib.service.EventsService" %>
<%@ page import="ua.dp.stud.StudPortalLib.service.impl.EventsServiceImpl" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@include file="include.jsp" %>


<portlet:defineObjects/>
<%
    Collection<Events> events = (Collection) request.getAttribute("events");
    Integer pagesCount = (Integer) request.getAttribute("pagesCount");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    int leftPageNumb = (Integer) request.getAttribute("leftPageNumb");
    int rightPageNumb = (Integer) request.getAttribute("rightPageNumb");
    boolean skippedBeginning = (Boolean) request.getAttribute("skippedBeginning");
    boolean skippedEnding = (Boolean) request.getAttribute("skippedEnding");
    EventsType type = null;
    if (request.getAttribute("type") != null)
        type = (EventsType) request.getAttribute("type");
    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    String imagePath = new StringBuilder(themeDisplay.getPortalURL()).append('/')
            .append(themeDisplay.getPathImage()).append("/image_gallery?img_id=").toString();
    Collection<String> allTypes = (Collection) EventsType.allTypes();
    String temp;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    Boolean archive=true;
    if (request.getAttribute("archive") != null){
        archive = (Boolean) request.getAttribute("archive");
}
%>

<html>
    <head>
    </head>
    <body>
    <liferay-ui:success message='<spring:message code="msg.successAdd"/>' key="success-add"/>
    <div id="contentDiv">
        <%if (request.isUserInRole("Administrator") || request.isUserInRole("User")) { %>
        <div class="portlet-content-controlpanel fs20">
            <%if(!archive){%>
            <a style="float: right" href='<portlet:renderURL><portlet:param name="archive" value="true"/></portlet:renderURL>'>
                <div class="Archive" aria-hidden="true"><spring:message code="form.soon"/></div>
            </a>
            <%}else{%>
            <a style="float: right" href='<portlet:renderURL><portlet:param name="archive" value="false"/></portlet:renderURL>'>
                <div class="Archive" aria-hidden="true"><spring:message code="form.archive"/></div>
            </a>
            <%}%>
            <a style="float: right" href='<portlet:renderURL><portlet:param name="mode" value="add"/></portlet:renderURL>'>
                <div class="panelbtn panelbtn-right icon-pcpfile" aria-hidden="true"></div>
            </a>

        </div>
        <%}%>
        <div class="cmt-types">
            <form method="post" action='<portlet:renderURL><portlet:param name="archive" value="<%=archive.toString()%>"/></portlet:renderURL>'>
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

    <portlet:actionURL var="actionLink"><portlet:param name="sort" value="date"/></portlet:actionURL>

   <%--     <a href='
            	<portlet:renderURL>
    		    <portlet:param name="date" value="all"/>
    		    <portlet:param name="sort" value="events"/>
    		</portlet:renderURL>
    		'>All
    		</a>
            --%>

         <script>
                    $(function() {
                    $.datepicker.setDefaults($.datepicker.regional['ru']);
                            $("#datepicker1").datepicker({ dateFormat: "mm/dd/yy", showAnim:'slide', showButtonPanel:true});
                    });</script>
          <form action="${actionLink}" >
                   <input type="text" style="width:75px;" name="EventSortDate" id="datepicker1"/>

                    <div id="sbm">
                          <input type="submit" value=Submit>
                     </div>
          </form>



        <div id="newsTable">
            <% if (!events.isEmpty()) {
                 for (Events currentEvent : events){%>
            <div width="100%">
                <img src="<%= imageService.getPathToMicroblogImage(currentEvent.getMainImage(),currentEvent) %>"
                     class="newsImage">
                <div style="color: #363636">
                    <% if (currentEvent.getEventDateEnd()!=null&&currentEvent.getEventDateStart()!=currentEvent.getEventDateEnd()){ %>
                    <%=dateFormat.format(currentEvent.getEventDateStart())%> - <%=dateFormat.format(currentEvent.getEventDateEnd())%>
                    <% } else { %>
                    <%=dateFormat.format(currentEvent.getEventDateStart())%>  <%}%>
                    &nbsp &nbsp &nbsp
                    <%=currentEvent.getLocation()%>
                </div>
                <div class="newsHeader">
                    <a href='<portlet:renderURL><portlet:param name="eventID" value="<%=currentEvent.getId().toString()%>"/><portlet:param name="archive" value="<%=archive.toString()%>"/><portlet:param name="currentPage" value="<%=currentPage.toString()%>"/></portlet:renderURL>'>
                        <%=currentEvent.getTitle()%>
                    </a>
                </div>
                <div class="newsText"><%= CustomFunctions.truncateHtml(currentEvent.getText(), 300) %>
                </div>
                <% if (request.isUserInRole("Administrator")) { %>
                <div class="portlet-content-controlpanel fs20"style="width: 8.6%;float: right;">
                    <a style="float: right" href='<portlet:renderURL><portlet:param name="eventID" value="<%=currentEvent.getId().toString()%>"/><portlet:param name="currentPage" value="<%=currentPage.toString()%>"/><portlet:param name="archive" value="<%=archive.toString()%>"/><portlet:param name="mode" value="delete" /></portlet:renderURL>'
                       onclick='return confirm("<spring:message code="form.confDelete"/>")'>
                        <div class="panelbtn panelbtn-right fs20 icon-pcpremove"  aria-hidden="true"></div>
                    </a>
                    <a style="float: right" href='<portlet:renderURL><portlet:param name="eventId" value="<%=currentEvent.getId().toString()%>"/><portlet:param name="archive" value="<%=archive.toString()%>"/><portlet:param name="mode" value="edit" /></portlet:renderURL>'>
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
            <table width="90%">
                <tr>
                    <%if(events.size()>9||currentPage>1){%>
                    <td width="80" align="left">
                <portlet:actionURL name="pagination" var="pagPrev">
                    <portlet:param name="direction" value="prev"/>
                    <portlet:param name="pageNumber" value="<%=String.valueOf(currentPage)%>"/>
                    <portlet:param name="archive" value="<%=archive.toString()%>"/>
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
                        for (int pageNumb = leftPageNumb; pageNumb <= rightPageNumb; pageNumb++) {
                            if (pageNumb != currentPage) {%>
                    <a href="<portlet:actionURL name="pagination"><portlet:param name="pageNumber" value="<%=String.valueOf(pageNumb)%>"/>
                        <portlet:param name="archive" value="<%=archive.toString()%>"/>
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
                    <a href='  <portlet:actionURL name="pagination">  <portlet:param name="pageNumber" value="<%=String.valueOf(pagesCount)%>"/>
                       <% if (type!=null) {%><portlet:param name="type" value="<%=String.valueOf(type)%>"/><%}%> </portlet:actionURL>'>
                        <%=String.valueOf(pagesCount)%>
                    </a>
                    <%}%>
                </td>
                <td width="80" align="right">
                <portlet:actionURL name="pagination" var="pagNext">
                    <portlet:param name="direction" value="next"/>
                    <portlet:param name="pageNumber" value="<%=String.valueOf(currentPage)%>"/>
                    <portlet:param name="archive" value="<%=archive.toString()%>"/>
                    <% if (type != null) {%><portlet:param name="type" value="<%=String.valueOf(type)%>"/><%} %>
                </portlet:actionURL>
                <a href="${pagNext}">
                    <img class="paginationImage"
                         src="${pageContext.request.contextPath}/images/pagin-right.png"/>
                </a>
                </td>
                <%}%>
                </tr>
            </table>
            <%
                }
                else
                {
            %>
            <h1><b>&nbsp &nbsp &nbsp<spring:message code="events.empty"/></b></h1>
                <%}%>
        </div>
    </div>
</body>
</html>