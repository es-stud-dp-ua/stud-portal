<%@ page import="ua.dp.stud.studie.model.Studie" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include.jsp" %>


<portlet:defineObjects/>
<%
    List<Studie> studie = (List) request.getAttribute("studie");
    int buttonId = 0;
    if (request.getParameter("buttonId") != null) {
        buttonId = Integer.parseInt(request.getParameter("buttonId"));
    }
    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    String imagePath = new StringBuilder(themeDisplay.getPortalURL()).append('/')
            .append(themeDisplay.getPathImage()).append("/image_gallery?img_id=").toString();
    String temp;
    String[] type = {"studie", "StudentCouncil", "schedule", "Courses", "grants"};
%>

<html>
<head>

</head>

<body>

<portlet:renderURL var="home"> </portlet:renderURL>
<div id="contentDiv">
	<%if (request.isUserInRole("Administrator")){ %>
		<portlet:renderURL var="addLink">
			<portlet:param name="mode" value="add"/>
		</portlet:renderURL>
		<div class="portlet-content-controlpanel fs20">
			<a style="float: right" href="${addLink}">
				<div class="panelbtn panelbtn-right icon-pcpfile" aria-hidden="true"></div>
			</a>
		</div>
	<%} %>

    <%@include file="leftBar.jsp" %>
    <script>
$(document).ready(function() {
                    	$(".btnselected").addClass('btntype').removeClass('btnselected');
                    	$("#study").removeClass('btntype').addClass('btnselected');
                    });
</script>
	<div id="newsTable">
		<c:if test="${not empty studie}">
			<%for (Studie currStudy : studie){%>
				<portlet:renderURL var="linkToSingle">
					<portlet:param name="studieID" value="<%=currStudy.getId().toString()%>"/>
				</portlet:renderURL>
				<div id="singleStd">
						<table width="100%"><tbody><tr>
						<td style="width: 110px;"><a href="${linkToSingle}">
							<img src="<%= imageService.getPathToMicroblogImage(currStudy.getMainImage(),currStudy) %>" class="studieImage">
						</a></td>
						<td>
							<a href="${linkToSingle}" style="font-weight: bold; font-size: 14px;"><%=currStudy.getTitle()%></a>
		<% if (request.isUserInRole("Administrator")) { %>
		<portlet:renderURL var="deleteLink">
			<portlet:param name="studieId" value="<%=currStudy.getId().toString()%>"/>
			<portlet:param name="mode" value="delete"/>
		</portlet:renderURL>
		<portlet:renderURL var="editLink">
			<portlet:param name="studieId" value="<%=currStudy.getId().toString()%>"/>
			<portlet:param name="mode" value="edit"/>
		</portlet:renderURL>
		<div class="portlet-content-controlpanel fs20"style="width: 10%;float: right;">
                <a style="float: right" href="${deleteLink}"
                   onclick='return confirm("<spring:message code="form.confDelete"/>")'>
                    <div class="panelbtn panelbtn-right fs20 icon-pcpremove" aria-hidden="true"></div>
                </a>
                <a style="float: right" href="${editLink}">
                    <!--<spring:message code="viewSingle.Edit"/>-->
                    <div class="panelbtn panelbtn-right icon-pcppencil" aria-hidden="true"></div>
                </a>
            </div>                
		<%}%>
						</td>
						<tr></tbody></table>
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
			</div>
			<%}%>
		</c:if>
	</div>

</div>

</body>
</html>