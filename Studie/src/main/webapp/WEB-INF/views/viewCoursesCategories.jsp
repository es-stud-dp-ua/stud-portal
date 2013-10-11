<%@ page import="ua.dp.stud.studie.model.KindOfCourse" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include.jsp" %>

<portlet:defineObjects/>
<%
    List<KindOfCourse> coursescategories = (List) request.getAttribute("coursescategories");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <div id="newsTable">
	    <c:if test="${not empty coursescategories}">
		    <%for (KindOfCourse currCategoria : coursescategories){%>
			    <div id="singleStd">
				    <table width="100%">
                        <tbody>
						    <tr>
						        <td style="width: 110px;">
						            <a href="">111</a>
						        </td>
						        <td>
						            <a href=""><%=currCategoria.getKindOfCourse()%></a>
						        </td>
						    </tr>
						</tbody>
				    </table>
				</div>
			<%}%>
		</c:if>
	</div>
 </body>
</html>