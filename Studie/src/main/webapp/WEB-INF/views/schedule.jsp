<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--
<%@include file="include.jsp" %>
-->
<body>
<%@include file="leftBar.jsp" %>
<script>
    $(document).ready(function () {
        $(".btnselected").addClass('btntype').removeClass('btnselected');
        $("#schedule").removeClass('btntype').addClass('btnselected');
    });
</script>
<portlet:actionURL var="actionLink" name="uploadSchedule"></portlet:actionURL>
<div class="textBox" style="padding-left: 130px">
    <form:select path="study" id="studyList">
        <option value="<spring:message code='Schedule.none'/>"><spring:message
                code='Schedule.choose.university'/></option>
        <c:forEach var="study" items="${study}">
            <option value="${study.id}">${study.title}</option>
        </c:forEach>
    </form:select>
</div>
</div>

<form:form method="POST" commandName="schedule" action="${actionLink}" enctype="multipart/form-data" id="scheduleForm">
    <div class="textBox" style="padding-left: 130px;">
        <form:select path="faculty.id" id="facultiesList" style="display:none;">
        </form:select>
    </div>
    <div class="textBox" style="padding-left: 130px;">
        <form:select path="year" id="yearsList" style="display:none;">
            <option value="<spring:message code='Schedule.none'/>"><spring:message
                    code='Schedule.choose.year'/></option>
            <option value="FIRST">1</option>
            <option value="SECOND">2</option>
            <option value="THIRD">3</option>
            <option value="FOURTH">4</option>
            <option value="FIFTH">5</option>
        </form:select>
    </div>
    <div id="schedule_label" style="width: 150px; font-size: 12pt; padding-left: 130px"></div>
    <%if (request.isUserInRole("Administrator")) {%>
    <div style="left: 30%; " id="scheduleLoader" style="display: none;">
        <input type="file" name="fileschedule" id="scheduleLoad"/>
    </div>
    <div style="left: 30%;" id="scheduleLoaderF" style="display: none;">
    </div>
    <input type="submit" style="margin-top: 15px;position: absolute;
                   left: 41%; display: none;" id="sbm_schedule" value="<spring:message
                   code='<%=(request.isUserInRole("Administrator"))?"schedule.submit.admin":"form.submit.user"%>'/>"/>
    <%}%>
    <c:if test="${status}">
        <div id="schedule_status" style="color:green; hidden: false;">
            <spring:message code="schedule.upload"/>
        </div>
    </c:if>
    <div id="isPresent" style="color:red; display:none">
        <spring:message code="schedule.empty"/>
    </div>


</form:form>


</body>

<portlet:resourceURL var="facultiesByStudy" id="facultiesByStudy"/>
<portlet:resourceURL var="getSchedule" id="getSchedule"/>
<portlet:resourceURL var="uploadSchedule" id="uploadSchedule"/>
<script>
    $("#studyList").change(function () {
        $('#facultiesList').hide();
        $('#yearsList').hide();
        $('#scheduleLoader').hide();
        $('#scheduleLoaderF').hide();
        $('#sbm_schedule').hide();
        $('#schedule_label').html("");
        if ($('#studyList').val() != "<spring:message code='Schedule.none'/>") {
            faculties($('#studyList').val());
            $('#schedule_status').hide();
        }
    });
    $("#facultiesList").change(function () {
        $('#yearsList').hide();
        $('#scheduleLoader').hide();
        $('#scheduleLoaderF').hide();
        $('#sbm_schedule').hide();
        $('#schedule_label').html("");
        if ($('#facultiesList').val() != "<spring:message code='Schedule.none'/>") {
            $('#yearsList').val("<spring:message code='Schedule.none'/>");
            $('#yearsList').show();
        }
    });
    $("#yearsList").change(function () {
        $('#schedule_label').html("");
        if ($('#yearsList').val() != "<spring:message code='Schedule.none'/>") {
            getSchedule($('#facultiesList').val(), $('#yearsList').val());
            $('#scheduleLoader').show();
            $('#scheduleLoaderF').show();
            $('#sbm_schedule').show();
        }
    });


    function faculties(studyId) {
        $.ajax
        ({
            url: "${facultiesByStudy}",
            data: {studyId: studyId},
            dataType: "html",
            type: "GET",
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                $('#facultiesList').html("<option value='<spring:message code='Schedule.none'/>'><spring:message code='Schedule.choose.faculty'/></option>" + data);
                $('#facultiesList').show();
            }
        });
    }


    function getSchedule(facultyId, year) {
        $.ajax
        ({
            url: "${getSchedule}",
            data: {facultyId: facultyId, year: year},
            dataType: "html",
            type: "GET",
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                if (data != "") {
                    $('#schedule_label').html("<a href='" + data + "'><spring:message code='schedule.download'/></a>");
                    $('#isPresent').hide();
                }
                else
                    $('#isPresent').show();
            }
        });
    }
</script>
</html>