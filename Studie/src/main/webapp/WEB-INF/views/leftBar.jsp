	<style>
		.selected {
			width: 150px;
			height: 40px;
			margin-left: -10px;
			background-color: rgba(0, 122, 255, 0.47);
			border-color: rgba(68, 115, 185, 0);
		}
		.non {
			width: 150px;
			height: 40px;
			margin-left: -10px;
			border-color: #4473B9;
		}
	</style>
	<div style="margin-left: -10px;" class="cmt-types">
	<portlet:renderURL var="LinkStudie">
		</portlet:renderURL>
		<div class="ribbon-wrapper">
			<a href="${LinkStudie}" name="buttonId"><button id="study" class="btntype non "><spring:message code="form.studie"/></button></a>
			<div class="ribbon-edge-topleft"></div>
			<div class="ribbon-edge-bottomleft"></div>
		</div><br>
		<portlet:renderURL var="LinkStudentCouncil">
			<portlet:param name="view" value="allcouncils"/>
		</portlet:renderURL>
		<div class="ribbon-wrapper">
			<a href="${LinkStudentCouncil}" name="buttonId"><button id="council" class="btntype non "><spring:message code="form.StudentCouncil"/></button></a>
			<div class="ribbon-edge-topleft"></div>
			<div class="ribbon-edge-bottomleft"></div>
		</div><br>
		<portlet:renderURL var="LinkSchedule">
			<portlet:param name="view" value="schedule"/>
		</portlet:renderURL>
		<div class="ribbon-wrapper">
			<a href="${LinkSchedule}" name="buttonId" value=""><button id="schedule" class="btntype non "><spring:message code="form.schedule"/></button></a>
			<div class="ribbon-edge-topleft"></div>
			<div class="ribbon-edge-bottomleft"></div>
		</div><br>
		<portlet:renderURL var="LinkCourses">
			<portlet:param name="view" value="allcourses"/>
		</portlet:renderURL>
		<div class="ribbon-wrapper">
			<a href="${LinkCourses}" name="buttonId"><button id="courses" class="btntype non "><spring:message code="form.Courses"/></button></a>
			<div class="ribbon-edge-topleft"></div>
			<div class="ribbon-edge-bottomleft"></div>
		</div><br>

		<portlet:renderURL var="LinkGrants">
			<portlet:param name="" value=""/>
		</portlet:renderURL>
		<div class="ribbon-wrapper">
			<a href="${LinkGrants}" name="buttonId"><button id="grants" class="btntype non "><spring:message code="form.grants"/></button></a>
			<div class="ribbon-edge-topleft"></div>
			<div class="ribbon-edge-bottomleft"></div>
		</div>
	</div>