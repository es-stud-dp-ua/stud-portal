<%@ page import="ua.dp.stud.studie.model.Grant" %>
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

<%Grant grant = (Grant) request.getAttribute("grant");
ImageService imageServices = (ImageService) pageContext.findAttribute("imageService");%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<portlet:renderURL var="home">
		<portlet:param name="view" value="allGrants" />
</portlet:renderURL>
	
	<div class="portlet-content-controlpanel fs20">

			<%
				if (request.isUserInRole("Administrator")) {
			%>
			<a style="float: right"
				href='<portlet:renderURL><portlet:param name="id" value="<%=grant.getId().toString()%>"/><portlet:param name="view" value="deleteGrant"/></portlet:renderURL>'
				onclick='return confirm("<spring:message code="form.confDelete"/>")'>
				<div class="panelbtn panelbtn-right icon-pcpremove"
					aria-hidden="true"></div>
			</a>

			<a style="float: right"
				href='<portlet:renderURL><portlet:param name="id" value="<%=grant.getId().toString()%>"/><portlet:param name="view" value="editGrant" /></portlet:renderURL>'>
				<div class="panelbtn panelbtn-right icon-pcppencil"
					aria-hidden="true"></div>
			</a>
			<%
				}
			%>



			<a href="${home}"> <!--<spring:message code="form.back"/>-->
				<div class="panelbtn panelbtn-right fs20 icon-pcparrow-left"
					aria-hidden="true"></div>
			</a>


	</div>
	
	<div id="desc">
                     <div width="100%" style="float: center">
                         <img src="<%= imageServices.getPathToLargeImage(
                        		 grant.getMainImage(),
                        		 grant) %>" class="newsImage" style="float: center">
   					  </div>
   					  	<br/>
   					  	<br/>
   					  
   					  <div id="labels"><spring:message code="form.grantUniversity"/> <%=grant.getUniversity()%></div><br/>
					  <div id="labels"><spring:message code="form.grantCity"/> <%=grant.getCity()%></div><br/>
					  <div id="labels"><spring:message code="form.grantCountry"/> <%=grant.getCountry()%></div><br/>
					  <div id="labels"><spring:message code="form.grantSpeciality"/> <%=grant.getSpeciality()%></div><br/>
					  <div id="labels"><spring:message code="form.grantQualification"/> <%=grant.getQualification()%></div><br/>
					  <div id="labels"><spring:message code="form.grantEducationPeriod"/> <%=grant.getEducationPeriod()%></div><br/>
					  <div id="labels"><spring:message code="form.grantEducationLanguage"/> <%=grant.getEducationLanguage()%></div><br/>
					  <div id="labels"><spring:message code="form.grantReceiptOfDocuments"/> <%=grant.getReceiptOfDocuments()%><br/></div><br/>
					  <div id="labels"><b><spring:message code="form.grantDescription"/> </b>
					  <%=grant.getDescription()%></div><br/><br/>
					  <div id="labels"><b><spring:message code="form.grantDocuments"/></b>
					  <%=grant.getDocuments()%></div>
	</div>

</body>
</html>