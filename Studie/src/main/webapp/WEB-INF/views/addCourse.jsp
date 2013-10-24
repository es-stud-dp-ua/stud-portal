<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>
<!--<%@include file="include.jsp" %>-->
<%@ page import="ua.dp.stud.StudPortalLib.model.CoursesType" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.Course" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
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
                font-weight: bold;
                color: red;
                padding: 2px 8px;
                margin-top: 2px;
            }
        </style>
</head>
<body>

<script language="javascript" type="text/javascript">
   $(document).ready(function()) {};
   function isNotMax(e, id) {
           var validateValueTextArea = document.getElementById(id);
           validateValueTextArea.value = validateValueTextArea.value.substr(0, validateValueTextArea.getAttribute('maxlength'));
       }
   }
</script>

<portlet:actionURL var="add" name="saveNewCourse"></portlet:actionURL>
<%@include file="AddEdit.jsp" %>

</body>
</html>