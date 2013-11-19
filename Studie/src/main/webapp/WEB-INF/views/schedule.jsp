<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<!--<%@include file="include.jsp" %>-->
<body>
    <%@include file="leftBar.jsp" %>
    <div id="labels" style="width: 150px; font-size: 12pt; padding-left: 130px">
        <spring:message code="schedule.university"/>
    </div>
    <div class="textBox" style="padding-left: 130px">
        <form:select path="coursesType">
            <option value="${type}">${type}</option>
            <c:forEach var="type" items="${coursesType}">
                <option value="${type}">${type}</option>
            </c:forEach>
        </form:select>
    </div>
</body>
</html>