<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="ua.dp.stud.studie.model.Grant" %>
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
    Collection<Grant> grants = (Collection) request.getAttribute("grants");
%>

<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<div>
<%@include file="leftBar.jsp" %>

<%if (request.isUserInRole("Administrator")){ %>
		       <portlet:renderURL var="LinkAddGrant">
       				 <portlet:param name="add" value="grant"/>
    		   </portlet:renderURL>
    			<div class="fs20"style="width: 10.15%;float: right;">
                		<a style="float: right" href="${LinkAddGrant}">
                    		<!--<spring:message code="viewSingle.Edit"/>-->
                    		<div class="panelbtn panelbtn-right icon-pcpplus" aria-hidden="true"></div>
                		</a>
				</div>
		    <%} %>

		    <% if (!grants.isEmpty()) {
 for (Grant grant : grants){%>
     <div id="newsTable" style="padding-top: 15px;float: left">
     	 <% if (request.isUserInRole("Administrator")) { %>
						<a style="float: right"
           href="<portlet:actionURL><portlet:param name="id" value="<%=grant.getId().toString()%>"/><portlet:param name="view" value="deleteGrant" /></portlet:actionURL>">
           <div class="icon-pcpremove fs20" aria-hidden="true"></div>
        </a>
				         <a style="float: right"
           href="<portlet:renderURL><portlet:param name="id" value="<%=grant.getId().toString()%>"/><portlet:param name="view" value="editGrant" /></portlet:renderURL>">
           <div class="icon-pcppencil fs20" aria-hidden="true"></div>
        </a>
				<%}%>
                         <div class="newsHeader" style="padding-top: 10px; padding-left: 175px; font-size: 20pt; ">
                                 <a href='
                                 <portlet:renderURL>
                                     <portlet:param name="id" value="<%=grant.getId().toString()%>"/>
                                         <portlet:param name="view" value="singleGrant"/>
                                 </portlet:renderURL>
                                 '><%=grant.getUniversity()%>
                                 </a>
                            </div>
                     
     </div>
             <%}%>

<%}%>
     </div>

		    </body>
 </html>