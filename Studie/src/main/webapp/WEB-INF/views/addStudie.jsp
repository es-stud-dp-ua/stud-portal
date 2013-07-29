<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>
<%@include file="include.jsp" %>

<portlet:defineObjects/>

<script src="${pageContext.request.contextPath}/js/dynamic_list_helper.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/dynamic_list_special.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/main_edit.js" type="text/javascript"></script>
<%
    //todo: remove this
    Locale locale = (Locale) request.getSession().getAttribute("org.apache.struts.action.LOCALE");
    String language = locale.getLanguage();
    String country = locale.getCountry();
    ResourceBundle res = ResourceBundle.getBundle("messages", new Locale(language, country));
%>
<portlet:defineObjects/>

<portlet:renderURL var="home"> </portlet:renderURL>
<portlet:actionURL var="actionLink" name="addStudie"></portlet:actionURL>

<liferay-ui:error key="no-images" message='<%=res.getString("msg.noImages")%>'/>
<liferay-ui:error key="dplTopic" message='<%=res.getString("msg.dplTopic")%>'/>
<div class="portlet-content-controlpanel fs20">
    <a href="${home}">
        <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
    </a>
</div>
<div width="100%">
    <form:form method="POST" commandName="study" action="${actionLink}" enctype="multipart/form-data" id="studyForm">
        <form:errors path="*" cssClass="errorblock" element="div" />
		<input type="hidden" size="0" id="x1" name="t"/>
        <input type="hidden" size="0" id="y1" name="l"/>
        <input type="hidden" size="0" id="w" name="w"/>
        <input type="hidden" size="0" id="h" name="h"/>
		<div style="width: 460px; float: right;">
			 <form:textarea path="title" id="topicInput" cols="90" rows="2" maxlength="80" onkeyup="isNotMax(event, getAttribute('id'))"></form:textarea>
			 <form:errors path="title" cssClass="error"/>
			 <br/>

			<div style="width: 450px; padding-left: 8px;">
				<div><label><spring:message code="studie.text"/></label></div>
				<form:errors path="text" cssClass="error"/>
				<form:textarea path="text" class="ckeditor" id="textInput" cols="60" rows="10" maxlength="8000" onkeyup="isNotMax(event, getAttribute('id'))"></form:textarea>
			</div>
			<br/>
			<div style="width: 450px; padding-left: 8px;">
				<div><label><spring:message code="studie.enrollees"/></label></div>
				<form:errors path="enrollees" cssClass="error"/>
				<form:textarea path="enrollees" class="ckeditor" id="textInput" cols="60" rows="10" maxlength="8000" onkeyup="isNotMax(event, getAttribute('id'))"></form:textarea>
			</div>
		</div>
		<div style="width: 460px;">
			<div style="height: 300px;">
				<div id="lup"></div>
				<div id="mainPic"
					 style="background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
					<!-- Output for our douwnload Image-->
					<output id="list"></output>
				</div>
				<div id="rdn"></div>
			</div>
			<div id="mainImageLoader">
				<div id="mainImgloaderBtn">
					<input name="mainImage" type="file" id="mainImage" accept="image/jpeg,image/png,image/gif"/>
					<div id="nt"><spring:message code="form.addPicture"/></div>
				</div>
			</div>
			<div id="imageLoader">
				<div id="imgloaderBtn">
					<input name="images" type="file" id="aui_3_2_0_11607"
						   accept="image/jpeg,image/png,image/gif"/ multiple>
					<div id="nt"><spring:message code="form.addPictures"/></div>
				</div>
			</div>
			<br/>
			<div style="width: 230px; float: right;">
				<div class="textBox">
					<div><label cssClass="control-group" for="freeTrainig"><spring:message code="studie.FreeTrainig"/></label>
					</div>
					<form:checkbox path="freeTrainig" />
					<form:errors path="freeTrainig" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="paidTrainig"><spring:message code="studie.PaidTrainig"/></label>
					</div>
					<form:checkbox path="paidTrainig" />
					<form:errors path="paidTrainig" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="militaryDepartment"><spring:message code="studie.MilitaryDepartment"/></label>
					</div>
					<form:checkbox path="militaryDepartment" />
					<form:errors path="militaryDepartment" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="hostel"><spring:message code="studie.Hostel"/></label>
					</div>
					<form:checkbox path="hostel" />
					<form:errors path="hostel" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="postgraduateEducation"><spring:message code="studie.PostgraduateEducation"/></label>
					</div>
					<form:checkbox path="postgraduateEducation" />
					<form:errors path="postgraduateEducation" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="postgraduateAndDoctoralStudies"><spring:message code="studie.PostgraduateAndDoctoralStudies"/></label>
					</div>
					<form:checkbox path="postgraduateAndDoctoralStudies" />
					<form:errors path="postgraduateAndDoctoralStudies" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="preparatoryDepartment"><spring:message code="studie.PreparatoryDepartment"/></label>
					</div>
					<form:checkbox path="preparatoryDepartment" />
					<form:errors path="preparatoryDepartment" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="countOfStudents"><spring:message code="studie.CountOfStudents"/></label>
					</div>
					<form:input path="countOfStudents" name="countOfStudents"/>
					<form:errors path="countOfStudents" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="countOfTeachers"><spring:message code="studie.CountOfTeachers"/></label>
					</div>
					<form:input path="countOfTeachers" name="countOfTeachers"/>
					<form:errors path="countOfTeachers" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="countOfCandidates"><spring:message code="studie.CountOfCandidates"/></label>
					</div>
					<form:input path="countOfCandidates" name="countOfCandidates"/>
					<form:errors path="countOfCandidates" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="countOfProfessors"><spring:message code="studie.CountOfProfessors"/></label>
					</div>
					<form:input path="countOfProfessors" name="countOfProfessors"/>
					<form:errors path="countOfProfessors" cssClass="error"/>
				</div>
			</div>
			<div><div style="width: 230px;">
				<div class="textBox">
					<div><label cssClass="control-group" for="city"><spring:message code="studie.city"/></label>
					</div>
					<form:input path="city" name="city"/>
					<form:errors path="city" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="years"><spring:message code="studie.years"/></label>
					</div>
					<form:select path="years">
					   <form:option value="NONE" label="--- Select ---" />
					   <form:options items="${yearsList}" />
					</form:select>
					<form:errors path="years" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="status"><spring:message code="studie.status"/></label>
					</div>
					<form:select path="status">
					   <form:option value="NONE" label="--- Select ---" />
					   <form:options items="${statusList}" />
					</form:select>
					<form:errors path="status" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="accreditacion"><spring:message code="studie.accreditacion"/></label>
					</div>
					<form:select path="accreditacion">
					   <form:option value="NONE" label="--- Select ---" />
					   <form:options items="${lvlAccredList}" />
					</form:select>
					<form:errors path="accreditacion" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="onGraduation"><spring:message code="studie.onGraduation"/></label>
					</div>
					<form:select path="onGraduation">
					   <form:option value="NONE" label="--- Select ---" />
					   <form:options items="${docList}" />
					</form:select>
					<form:errors path="onGraduation" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="formOfTraining"><spring:message code="studie.FormOfTraining"/></label>
					</div>
					<form:select path="formOfTraining" items="${trainigFormsList}"
						multiple="true" />
					<form:errors path="formOfTraining" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="qualificationLevel"><spring:message code="studie.QualificationLevel"/></label>
					</div>
					<form:select path="qualificationLevel" items="${lvlQualifList}"
						multiple="true" />
					<form:errors path="qualificationLevel" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="adress"><spring:message code="studie.Adress"/></label>
					</div>
					<form:input path="adress" name="adress"/>
					<form:errors path="adress" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="phone"><spring:message code="studie.Phone"/></label>
					</div>
					<form:input path="phone" name="phone"/>
					<form:errors path="phone" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="phoneAdmissions"><spring:message code="studie.PhoneAdmissions"/></label>
					</div>
					<form:input path="phoneAdmissions" name="phoneAdmissions"/>
					<form:errors path="phoneAdmissions" cssClass="error"/>
				</div>
				<div class="textBox">
					<div><label cssClass="control-group" for="website"><spring:message code="studie.Website"/></label>
					</div>
					<form:input path="website" name="website"/>
					<form:errors path="website" cssClass="error"/>
				</div>
			</div></div>
			<table style="width: 100%;">
				<thead>
					<th>Faculties</th>
					<th>Spetial</th>
					<th></th>
				</thead>
				<tbody id="facultiesListContainer">
					<c:forEach items="${study.faculties}" var="Faculties" varStatus="i" begin="0" >
                        <tr class="facultet">
							<td class="faculIndex"><form:input path="faculties[${i.index}].nameOfFaculties" id="nameOfFaculties${i.index}" /></td>
							<td>
								<div class="specContainer">
									<c:forEach items="${Faculties.specialties}" var="Specialties" varStatus="j" begin="0" >
										<div class="specialty">
										<form:input path="faculties[${i.index}].specialties[${j.index}].nameOfSpecialties" value="" />
										<a href="#" class="removeSpecialty">Remove</a>
									</div>	
									</c:forEach>
								</div>
								<a href="#" class="addSpecialty">Add</a>
							</td>
							<td><a href="#" class="removeFaculties">Remove</a></td>
						</tr>
                    </c:forEach>
					<c:if test="${empty study.faculties}">
						<tr class="facultet defaultRow">
							<td class="faculIndex"><input type="text" name="faculties[].nameOfFaculties" value="" /></td>
							<td>
								<div class="specContainer"></div>
								<a href="#" class="addSpecialty">Add</a>
							</td>
							<td><a href="#" class="removeFaculties">Remove</a></td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<a href="#" id="addFaculties">Add</a>
			
		</div>
        <input type="submit" style="vertical-align: central; margin-top: 15px; " value="<spring:message
                                   code='<%=(request.isUserInRole("Administrator"))?"form.submit.admin"
                                                                                             :"form.submit.user"%>'/>"/>
    </form:form>
</div>
