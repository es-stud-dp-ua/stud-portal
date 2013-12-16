<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="ua.dp.stud.studie.model.OnlineCourse" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page isELIgnored="false" %>
<%@include file="include.jsp" %>

<% ImageService imageServices = (ImageService) pageContext.findAttribute("imageService"); %>


<%@ taglib prefix="theme" uri="http://liferay.com/tld/theme" %>
<%
    Collection<OnlineCourse> courses = (Collection) request.getAttribute("onlineCourses");
%>

<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<div>
<%if (request.isUserInRole("Administrator")){ %>
		        <portlet:renderURL var="categories">
                    <portlet:param name="view" value="onlineCoursesCategories"/>
                </portlet:renderURL>
		        <a href="${categories}"><div style="display:inline;" id='changeBut' class="icon-pcppencil fs20" aria-hidden="true"></div></a>
		    <%} %>
		    
		    <% if (!courses.isEmpty()) {
 for (OnlineCourse course : courses){%>
     <div id="newsTable" style="padding-top: 15px;">
     			 <% if (request.isUserInRole("Administrator")) { %>
				         <a style="float: right"
           href="<portlet:actionURL><portlet:param name="id" value="<%=course.getId().toString()%>"/><portlet:param name="view" value="editOnlineCourse" /></portlet:actionURL>">
           <div class="icon-pcppencil fs20" aria-hidden="true"></div>
        </a>
        				<a style="float: right"
           href="<portlet:actionURL><portlet:param name="id" value="<%=course.getId().toString()%>"/><portlet:param name="view" value="deleteOnlineCourse" /></portlet:actionURL>">
           <div class="icon-pcpremove fs20" aria-hidden="true"></div>
        </a>
				<%}%>
                     <div width="100%">
                         <img src="<%= imageServices.getPathToMicroblogImage(
                        		 course.getMainImage(),
                        		 course) %>" class="newsImage" style="float: left">
 
                         <div class="newsHeader" style="padding-top: 50px; padding-left: 175px; font-size: 20pt; ">
                            <b>
                                 <a href='
                                 <portlet:renderURL>
                                     <portlet:param name="id" value="<%=course.getId().toString()%>"/>
                                         <portlet:param name="view" value="singleOnlineCourse"/>
                                 </portlet:renderURL>
                                 '><%=course.getOnlineCourseName()%>
                                 </a>
                            </b>
                            </div>
                     
           </div>
           </div>
             <%}%>

<%}%>
     </div>

		    </body>
 </html>