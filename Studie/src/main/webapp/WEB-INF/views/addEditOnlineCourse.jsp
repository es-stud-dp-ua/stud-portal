<form:form method="POST" commandName="onlineCourse" action="${addEdit}"
	enctype="multipart/form-data" id="jform" modelAttribute="onlineCourse">
	<input type="hidden" size="0" id="x1" name="t" value="0" />
	<input type="hidden" size="0" id="y1" name="l" value="0" />
	<input type="hidden" size="0" id="w" name="w" value="100" />
	<input type="hidden" size="0" id="h" name="h" value="100" />
  	<form:input type="hidden" path="id"/>



	<div style="padding-left: 230px">
		<br />
		    <div>
			<div id="labels" style="width: 150px; font-size: 12pt"><div id="redStar1">*</div>
			<spring:message code="course.TName" />
			</div>
			<form:textarea path="onlineCourseName" id="topicInput" cols="90" rows="2" maxlength="100"
			           onkeypress="return isNotMax(event,this)" name="onlineCourseName"></form:textarea>
			<form:errors path="onlineCourseName" cssClass="error" />
            </div>

			<div id="labels" style="width: 150px; font-size: 12pt">
			<spring:message code="course.TTheme" />
			</div>
			 <form:select path="onlineCourseType.id">
               <form:options items="${onlineCourseType}" itemValue="id" itemLabel="kindOfCourse"/>
             </form:select>
			
			
			<div id="labels" style="width: 150px; font-size: 12pt; left: 30%;">
				<spring:message code="course.TLogo" />
			</div>
			<div style="vertical-align: top;">

				<c:choose>
					<c:when test='${mainImage == null}'>
						<div>
							<div id="lup"></div>
							<div id="mainPic"
								style="float:none; background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
								<output id="list"></output>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div id="mainPic" style="float: none">
							<img id="img" src="${mainImage}" />
							<output id="list"></output>
						</div>
					</c:otherwise>
				</c:choose>

			</div>
			</br>
				<div style="left: 30%;" id="mainImageLoader">

					<div id="mainImgloaderBtn">
						<input type="file" id="mainImage" name="mainImage"
							accept="image/jpeg,image/png,image/gif" />
						<div id="nt">
							<spring:message code="form.addPicture" />
						</div>
					</div>
				</div>

				<br /> <br />
				<div>
				<div id="labels" style="width: 150px; font-size: 12pt">
					<spring:message code="course.TDesc" /> <div id="redStar2">*</div>
				</div>
				<br />

				<form:textarea path="onlineCourseDescription" class="ckeditor"
					id="text" cols="60" rows="8" maxlength="3000" onkeypress="return isNotMax(event,this)"   name="onlineCourseDescription"/>
                <textarea style="visibility: hidden;width: 0px;" id="text1" name="text1"  ></textarea>
                <form:errors path="onlineCourseDescription" cssClass="error" />
				<br />
				</div>
				
				<div id="sbm" align="center">
                <input type="submit" value="<spring:message
                       code="form.submit.council"/>"/>
            	</div>
			</div>

<script src="${pageContext.request.contextPath}/js/a.js" type="text/javascript"></script>
            <script src="${pageContext.request.contextPath}/js/setCoords.js" type="text/javascript"></script>
            <script src="${pageContext.request.contextPath}/js/handleFile.js" type="text/javascript"></script>
	<script>
      document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);

	</script>

</form:form>