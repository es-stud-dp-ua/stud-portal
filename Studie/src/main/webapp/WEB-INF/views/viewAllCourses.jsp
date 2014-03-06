<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include.jsp" %>


<html>
<head>
<title>Insert title here</title>
</head>
<body>

    <div id="contentDiv">
        <portlet:renderURL var="LinkAddCourse">
	        <portlet:param name="add" value="course"/>
	    </portlet:renderURL>
        <portlet:resourceURL var="coursesByKindAndType" id="coursesByKindAndType" />
        <%@include file="leftBar.jsp" %>
        <script>
$(document).ready(function() {
                    	$(".btnselected").addClass('btntype').removeClass('btnselected');
                    	$("#courses").removeClass('btntype').addClass('btnselected');
                    });
</script>
        <%if (request.isUserInRole("Administrator")){ %>
        <div class="portlet-content-controlpanel fs20"style="width: 10.15%; float: right;">
            <a style="float: right" href="${LinkAddCourse}">
                <div class="panelbtn panelbtn-right icon-pcpplus" aria-hidden="true"></div>
            </a>
        </div>
        <%} %>
	    <div class="textBox" style="padding-top:12px;">
	        <spring:message code="Kind.label"/>
	        <select id="kindOfCourse" style="width: 30%; margin-right: 5px; margin-left: 5px;" title="<spring:message code="dropboxKind.label"/>">
		        <option value="all"><spring:message code="All.msg"/></option>
                <c:forEach var="kind" items="${kindOfCourses}">
			        <option value="${kind.id}">${kind.kindOfCourse}</option>
			    </c:forEach>
		    </select>
		    <%if (request.isUserInRole("Administrator")){ %>
		        <portlet:renderURL var="categories">
                    <portlet:param name="view" value="coursesCategories"/>
                </portlet:renderURL>
		        <a href="${categories}"><div style="display:inline;" id='changeBut' class="icon-pcppencil fs20" aria-hidden="true"></div></a>
		    <%} %>
            <spring:message code="Type.label"/>
		    <select id="courseType" style="width: 15%; margin-right: 5px; margin-left: 5px;" title="<spring:message code="dropboxType.label"/>">
		        <option value="all"><spring:message code="All.msg"/></option>
		        <c:forEach var="type" items="${coursesType}">
			        <option value="${type}">${type}</option>
			    </c:forEach>
		    </select>
		    <input type=button name="sort" value='<spring:message code="button.Sort"/>' onclick="refresh($('#kindOfCourse').val(),$('#courseType').val());" style="position: absolute; height: 26px;"/>
	    </div>
	    <div id="coursesList">
	        <c:forEach var="course" items="${courses}">
        	    <p><a href='<portlet:renderURL><portlet:param name="courseId" value="${course.id}"/><portlet:param name="view" value="course"/></portlet:renderURL>'>${course.courseName}</a></p>
            </c:forEach>
	    </div>
	</div>
</body>
<script>
	function refresh(kindOfCourse, coursesType)
	{
        $.ajax
        ({
            url: "${coursesByKindAndType}",
            data: {kindOfCourse: kindOfCourse, coursesType: coursesType},
            dataType: "html",
            type: "GET",
            contentType: "application/json;charset=utf-8",
            success: function (data)
            {
            	$('#coursesList').html(data);
            }
        });
    }
</script>
</html>