<%@ page import="ua.dp.stud.studie.model.Council" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page isELIgnored="false" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>

<% ImageService imageService = (ImageService) pageContext.findAttribute("imageService"); %>


<%@ taglib prefix="theme" uri="http://liferay.com/tld/theme" %>
<%
    Collection<Council> councils = (Collection) request.getAttribute("councils");
%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    </head>
    <body>
    
    <portlet:renderURL var="LinkAddCouncil">
        <portlet:param name="add" value="council"/>
    </portlet:renderURL>
    <div class="portlet-content-controlpanel fs20"style="width: 10.15%;float: right;">
                <a style="float: right" href="${LinkAddCouncil}">
                    <!--<spring:message code="viewSingle.Edit"/>-->
                    <div class="panelbtn panelbtn-right icon-pcpplus" aria-hidden="true"></div>
                </a>
</div>
 <div style="float:left"><%@include file="leftBar.jsp"%></div>

<div style="padding-left: 100px; width: 60%; float: left;">
<table border="1" style="border-color: grey;">
<tr>
<td >
<div style="padding-left: 100px;"><h2><spring:message code="studh2"/></h2></div>
<br/>

<br/>
<div style="font-size: 12px;"><spring:message code="studt1"/></div>
<br/>
<br/>
<div style="font-size: 12px;"><spring:message code="studt2"/></div>
</td>
<tr>
</table>
</div>

<% if (!councils.isEmpty()) {
for (Council council : councils){%>
		<div id="newsTable" style="padding-top: 15px;">
				 <% if (request.isUserInRole("Administrator")) { %>
				         <a style="float: right"
           href="<portlet:renderURL><portlet:param name="councilId" value="<%=council.getId().toString()%>"/><portlet:param name="edit" value="council" /></portlet:renderURL>">
           <div class="icon-pcppencil fs20" aria-hidden="true"></div>
        </a>
<%}%>

                    <div width="100%">
                        <img src="<%= imageService.getPathToMicroblogImage(
                                         council.getMainImage(),
                                         council) %>" class="newsImage" style="float: left">
<hr/>
                        <div class="newsHeader" style="padding-top: 50px; padding-left: 150px; font-size: 20pt; ">
                           <b>
                                <a href='
                            		<portlet:renderURL>
                            		    <portlet:param name="id" value="<%=council.getId().toString()%>"/>
                                        <portlet:param name="mode" value="showCouncil"/>
                            		</portlet:renderURL>
                            		'><%=council.getCouncilName()%>
                                </a>
                           </b>

                        </div>

                    </div>

        </div>
        
        <%}%>

<%}%>

</body>
</html>
