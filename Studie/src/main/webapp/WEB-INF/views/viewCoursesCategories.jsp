<%@ page import="ua.dp.stud.studie.model.KindOfCourse" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include.jsp" %>
<html>
<body>
    <div id="CategoriesTable">
	    <c:if test="${not empty KOC}">
	        <c:forEach items="${KOC}" var="cat">
                <div id="">
                    <input type="text" value="${cat.kindOfCourse}" disabled>
                    ${cat.countOfCourses}
                </div>
            </c:forEach>
		</c:if>
	</div>
 </body>
</html>