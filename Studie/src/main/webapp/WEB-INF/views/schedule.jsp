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
        <spring:message code="Schedule.university"/>
    </div>
    <div class="textBox" style="padding-left: 130px">
            <form:select path="study" id="studyList">
                <option value="<spring:message code='Schedule.none'/>"><spring:message code='Schedule.choose.university'/></option>
                <c:forEach var="study" items="${study}">
                    <option value="${study.id}">${study.title}</option>
                </c:forEach>
            </form:select>
    </div>
    <div class="textBox" style="padding-left: 130px;">
                <form:select path="study" id="facultiesList" style="display:none;">
                </form:select>
    </div>
    <div class="textBox" style="padding-left: 130px;">
                <form:select path="study" id="yearsList" style="display:none;">
                    <option value="<spring:message code='Schedule.none'/>"><spring:message code='Schedule.choose.year'/></option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </form:select>
    </div>
</body>
<portlet:resourceURL var="facultiesByStudy" id="facultiesByStudy" />
<script>
    $( "#studyList" ).change(function() {
            $('#facultiesList').hide();
            $('#yearsList').hide();
            if ($('#studyList').val()!= "<spring:message code='Schedule.none'/>") {
                faculties($('#studyList').val());
            }
    });
    $( "#facultiesList" ).change(function() {
            $('#yearsList').hide();
            if ($('#facultiesList').val()!= "<spring:message code='Schedule.none'/>") {
                $('#yearsList').show();
            }
    });
	function faculties(studyId)
	{
        $.ajax
        ({
            url: "${facultiesByStudy}",
            data: {studyId: studyId},
            dataType: "html",
            type: "GET",
            contentType: "application/json;charset=utf-8",
            success: function (data)
            {
            	$('#facultiesList').html("<option value='<spring:message code='Schedule.none'/>'><spring:message code='Schedule.choose.faculty'/></option>"+data);
            	$('#facultiesList').show();
            }
        });
    }
</script>
</html>