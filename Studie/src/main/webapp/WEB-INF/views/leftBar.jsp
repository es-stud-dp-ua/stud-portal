	<div style="margin-left: -10px;" class="cmt-types">
	<portlet:renderURL var="LinkStudie">
		</portlet:renderURL>
		<div class="ribbon-wrapper">
			<a class="btnselected" href="${LinkStudie}"
					style=" width: 150px; height: 40px;  margin-left: -10px;  background-color: rgba(0, 122, 255, 0.47); border-color: rgba(68, 115, 185, 0);"
					name="buttonId">
				<spring:message code="form.studie"/></a>
			<div class="ribbon-edge-topleft"></div>
			<div class="ribbon-edge-bottomleft"></div>
		</div>
		<portlet:renderURL var="LinkStudentCouncil">
			<portlet:param name="view" value="allcouncils"/>
		</portlet:renderURL>
		<div class="ribbon-wrapper">
			<a class="btnselected" href="${LinkStudentCouncil}"
					style=" width: 150px; height: 40px;  margin-left: -10px;  background-color: rgba(0, 122, 255, 0.47); border-color: rgba(68, 115, 185, 0);"
					name="buttonId">
				<spring:message code="form.StudentCouncil"/></a>
			<div class="ribbon-edge-topleft"></div>
			<div class="ribbon-edge-bottomleft"></div>
		</div>
		<portlet:renderURL var="LinkSchedule">
			<portlet:param name="view" value="schedule"/>
		</portlet:renderURL>
		<div class="ribbon-wrapper">
			<a class="btnselected" href="${LinkSchedule}"
					style=" width: 150px; height: 40px;  margin-left: -10px;  background-color: rgba(0, 122, 255, 0.47); border-color: rgba(68, 115, 185, 0);"
					name="buttonId" value="">
				<spring:message code="form.schedule"/></a>
			<div class="ribbon-edge-topleft"></div>
			<div class="ribbon-edge-bottomleft"></div>
		</div>
		<portlet:renderURL var="LinkCourses">
			<portlet:param name="view" value="allcourses"/>
		</portlet:renderURL>
		<div class="ribbon-wrapper">
			<a class="btnselected" href="${LinkCourses}"
					style=" width: 150px; height: 40px;  margin-left: -10px;  background-color: rgba(0, 122, 255, 0.47); border-color: rgba(68, 115, 185, 0);"
					name="buttonId">
				<spring:message code="form.Courses"/></a>
			<div class="ribbon-edge-topleft"></div>
			<div class="ribbon-edge-bottomleft"></div>
		</div>

		<portlet:renderURL var="LinkGrants">
			<portlet:param name="" value=""/>
		</portlet:renderURL>
		<div class="ribbon-wrapper">
			<a class="btnselected" href="${LinkGrants}"
					style=" width: 150px; height: 40px;  margin-left: -10px;  background-color: rgba(0, 122, 255, 0.47); border-color: rgba(68, 115, 185, 0);"
					name="buttonId">
				<spring:message code="form.grants"/></a>
			<div class="ribbon-edge-topleft"></div>
			<div class="ribbon-edge-bottomleft"></div>
		</div>
	</div>