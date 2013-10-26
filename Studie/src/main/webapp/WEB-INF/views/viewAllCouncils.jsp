<%@ page import="ua.dp.stud.studie.model.Council" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@include file="include.jsp" %>


<%@ taglib prefix="theme" uri="http://liferay.com/tld/theme" %>



<html>
    <head>
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
<portlet:renderURL var="LinkEditCouncil">
			<portlet:param name="edit" value="council"/>
</portlet:renderURL>
<div class="portlet-content-controlpanel fs20"style="width: 10.15%;float: right;">

                <a style="float: right" href="name ${LinkEditCouncil}">

                    <div class="panelbtn panelbtn-right icon-pcppencil" aria-hidden="true"></div>
                </a>

</div>




${council.id}

 <portlet:renderURL var="LinkShowCouncil">
            <portlet:param name="id" value="${council.id}"/>
 			<portlet:param name="mode" value="showCouncil"/>
 </portlet:renderURL>


 <div>
  <a href="${LinkShowCouncil}">ololo</a>
 </div>

</body>
</html>
