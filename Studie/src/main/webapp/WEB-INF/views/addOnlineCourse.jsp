<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<!--<%@include file="include.jsp" %>-->

<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

        <style type="text/css">
            .error {
                color: #ff0000;
            }
            label.valid {
                width: 24px;
                height: 24px;
                background: url(${pageContext.request.contextPath}/images/valid.png) center center no-repeat;
                display: inline-block;
                text-indent: -9999px;
            }
            label.error {
                font-weight: bold;s
                color: red;
                padding: 2px 8px;
                margin-top: 2px;
            }
        </style>
</head>
<body>
<liferay-ui:error  key="image" message='Please, select a picture! '/>
<portlet:actionURL var="addEdit" name="saveOnlineCourse"></portlet:actionURL>
<%@include file="addEditOnlineCourse.jsp" %>

</body>
</html>