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
    <script>
    $(document).ready(function() {
                    	$(".btnselected").addClass('btntype').removeClass('btnselected');
                    	$("#schedule").removeClass('btntype').addClass('btnselected');
                    });
    </script>
    <portlet:actionURL var="actionLink" name="uploadSchedule"></portlet:actionURL>
    <div class="textBox" style="padding-left: 130px">
            <form:select path="study" id="studyList">
                <option value="<spring:message code='Schedule.none'/>"><spring:message code='Schedule.choose.university'/></option>
                <c:forEach var="study" items="${study}">
                    <option value="${study.id}">${study.title}</option>
                </c:forEach>
            </form:select>
        </div>
    </div>
        <form:form method="POST" commandName="schedule" action="${actionLink}" enctype="multipart/form-data" id="scheduleForm">
    <div class="textBox" style="padding-left: 130px;">
        <form:select path="faculty" id="facultiesList" style="display:none;">
        </form:select>
    </div>
    <div class="textBox" style="padding-left: 130px;">
        <form:select path="year" id="yearsList" style="display:none;">
            <option value="<spring:message code='Schedule.none'/>"><spring:message code='Schedule.choose.year'/></option>
            <option value="FIRST">1</option>
            <option value="SECOND">2</option>
            <option value="THIRD">3</option>
            <option value="FOURTH">4</option>
            <option value="FIFTH">5</option>
        </form:select>
    </div>
    <div id="schedule_label" style="width: 150px; font-size: 12pt; padding-left: 130px"></div>
    <div style="left: 30%;" id="scheduleLoader">
            <input type="file" id="scheduleLoad" />
        </div>
    <div style="left: 30%;" id="scheduleLoaderF">
    </div>
    <input type="submit" style="margin-top: 15px;position: absolute;
                   left: 41%; " value="<spring:message
                   code='<%=(request.isUserInRole("Administrator"))?"form.submit.admin":"form.submit.user"%>'/>"/>
    </form:form>
</body>
        <portlet:resourceURL var="facultiesByStudy" id="facultiesByStudy" />
        <portlet:resourceURL var="getSchedule" id="getSchedule" />
        <portlet:resourceURL var="uploadSchedule" id="uploadSchedule" />
        <script>
            $( "#studyList" ).change(function() {
                    $('#facultiesList').hide();
                    $('#yearsList').hide();
                    $('#schedule_label').html("");
                    if ($('#studyList').val()!= "<spring:message code='Schedule.none'/>") {
                        faculties($('#studyList').val());
                    }
            });
            $( "#facultiesList" ).change(function() {
                    $('#yearsList').hide();
                    $('#schedule_label').html("");
                    if ($('#facultiesList').val()!= "<spring:message code='Schedule.none'/>") {
                        $('#yearsList').val("<spring:message code='Schedule.none'/>");
                        $('#yearsList').show();
                    }
            });
            $( "#yearsList" ).change(function() {
                                $('#schedule_label').html("");
                                if ($('#yearsList').val()!= "<spring:message code='Schedule.none'/>") {
                                    getSchedule($('#facultiesList').val(),$('#yearsList').val());
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
          function getSchedule(facultyId, year)
                    {
                          $.ajax
                          ({
                              url: "${getSchedule}",
                              data: {facultyId: facultyId, year:year},
                              dataType: "html",
                              type: "GET",
                              contentType: "application/json;charset=utf-8",
                              success: function (data)
                              {
                                $('#schedule_label').html("<a href='"+data+"'>DOWNLOAD</a>");
                              }
                          });
                      }
        </script>
</html>