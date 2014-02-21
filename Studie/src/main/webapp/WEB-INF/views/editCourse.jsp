<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>
<%@include file="include.jsp" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>


<html>
<head>
<title>����</title>
</head>
<body>


<script type="text/javascript">

                    $(document).ready(function() {
            $.Placeholder.init({color: "#aaa"});
            });
                    function isNotMax(e) {
                    e = e || window.event;
                            var target = e.target || e.srcElement;
                            var code = e.keyCode ? e.keyCode : (e.which ? e.which : e.charCode)
                            switch (code) {
                    case 13:
                            case 8:
                            case 9:
                            case 46:
                            case 37:
                            case 38:
                            case 39:
                            case 40:
                            return true;
                    }
                    return target.value.length <= target.getAttribute('maxlength');
                    }
        </script>
<liferay-ui:error  key="image" message='Please, select a picture! '/>

    <c:if test="${exception}">
        ${exception}
    </c:if>
<portlet:actionURL var="add" name="editCourse"></portlet:actionURL>

<%@include file="AddEdit.jsp" %>

  <script>
         $("#courseForm").on("submit",function(event){

                                         document.getElementById('text1').innerHTML = CKEDITOR.instances.text.getData();

                                     });

                              $(document).ready(function() {
                      $.validator.setDefaults({ ignore: [] });
                              $('#courseForm').validate({

                      rules: {
                      courseName: {
                      required: true,
                              minlength: 5,
                              maxlength: 100
                      },
                      coursesContact: {
                              required: true,
                              minlength: 10,
                              maxlength: 500
                               },
                      text1: {
                      required: true,
                              minlength: 300,
                              maxlength: 3000
                      }

                      },
                              messages: {
                      courseName: {
                      required: "<spring:message code="val.required"/>",
                              minlength:  "<spring:message code="val.title.minlength"/>",
                              maxlength:  "<spring:message code="val.title.maxlength"/>"
                      },
                      coursesContact: {
                              required: "<spring:message code="val.required"/>",
                              minlength:  "<spring:message code="val.coursescontsct.minlength"/>",
                              maxlength:  "<spring:message code="val.coursescontsct.maxlength"/>"
                      },
                              text1: {
                      required: "<spring:message code="val.required"/>",
                              minlength:  "<spring:message code="val.text.minlength"/>",
                              maxlength:  "<spring:message code="val.text.maxlength"/>"
                      }
                      },
                              highlight: function(label) {
                      $(label).removeClass("invisiblevalid");
                              $(label).closest('.control-group').addClass('error');
                      },
                              success: function(label) {
                      $(label).removeClass("error");
                              label.addClass("invisiblevalid");
                      }
                      });
                      });

      </script>

</html>