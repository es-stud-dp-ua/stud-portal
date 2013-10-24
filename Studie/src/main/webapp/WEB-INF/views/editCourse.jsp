<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>
<%@include file="include.jsp" %>

<%@ page import="ua.dp.stud.StudPortalLib.model.CoursesType" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.Course" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>

<html>
<head>
<title>����</title>
</head>
<body>

<portlet:actionURL var="add" name="editCourse"></portlet:actionURL>

<%@include file="AddEdit.jsp" %>


</html>