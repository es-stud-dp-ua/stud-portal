<%@ page import="ua.dp.stud.studie.model.OnlineCourse" %>
<%@ page import="com.liferay.portal.model.Layout" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.ImageImpl" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@ page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@ page import="com.liferay.portal.theme.PortletDisplay"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include.jsp"%>
<portlet:defineObjects/>

<%OnlineCourse course = (OnlineCourse) request.getAttribute("onlineCourse");
ImageService imageServices = (ImageService) pageContext.findAttribute("imageService");%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<portlet:renderURL var="home">
		<portlet:param name="view" value="allOnlineCourses" />
</portlet:renderURL>
	
	<div class="portlet-content-controlpanel fs20">
			<a href="${home}"> <!--<spring:message code="form.back"/>-->
				<div class="panelbtn panelbtn-right fs20 icon-pcparrow-left"
					aria-hidden="true"></div>
			</a>
	</div>
     <div id="newsTable" style="padding-top: 15px">
                     <div width="100%" style="float: center">
                         <img src="<%= imageServices.getPathToMicroblogImage(
                        		 course.getMainImage(),
                        		 course) %>" class="newsImage" style="float: left">
 
                         <div class="newsHeader" style="padding-top: 50px; padding-left: 175px; font-size: 20pt; ">
                            <b>
                                 <%=course.getOnlineCourseName()%>
                            </b>
                         </div>
   					  </div>
   					  <br/>
   					  <br/>
   					  <div id="onlineCourseDescription" style="float: center" >
						<%=course.getOnlineCourseDescription()%>
					</div>
	</div>

	

</body>
</html>