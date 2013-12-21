<form:form method="POST" commandName="onlineCourse" action="${addEdit}"
	enctype="multipart/form-data" id="onlineCourseForm" modelAttribute="onlineCourse">
	<input type="hidden" size="0" id="x1" name="t" value="0" />
	<input type="hidden" size="0" id="y1" name="l" value="0" />
	<input type="hidden" size="0" id="w" name="w" value="100" />
	<input type="hidden" size="0" id="h" name="h" value="100" />
  	<form:input type="hidden" path="id"/>
	<div style="padding-left: 230px">
		<br />
		
			<div id="labels" style="width: 150px; font-size: 12pt">
			<spring:message code="course.TName" />
			</div>
			<form:textarea path="onlineCourseName" id="topicInput" cols="90" rows="2"></form:textarea>
			<form:errors path="onlineCourseName" cssClass="error" />

			<div id="labels" style="width: 150px; font-size: 12pt">
			<spring:message code="course.TName" />
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
				
				<div id="labels" style="width: 150px; font-size: 12pt">
					<spring:message code="course.TDesc" />
				</div>
				<br />
				<form:errors path="onlineCourseDescription" cssClass="error" />
				<form:textarea path="onlineCourseDescription" class="ckeditor"
					id="textInput" cols="60" rows="8"></form:textarea>
				<br />
				
				<div id="sbm" align="center">
                <input type="submit" value="<spring:message
                       code="form.submit.council"/>"/>
            	</div>
			</div>


	<script>
		function handleFileSelect(evt) {
			var files = evt.target.files; // FileList object
			// Loop through the FileList and render image files as thumbnails.
			var f = files[files.length - 1];

			// Only process im11age files.
			document.getElementById('list').innerHTML = '';
			var reader = new FileReader();
			// Closure to capture the file information.
			reader.onload = (function(theFile) {
				return function(e) {
					// Render thumbnail.
					var span = document.createElement('span');
					span.innerHTML = [
							'<img id="cropbox" class="thumb" width="443px" src="',
							e.target.result, '" title="', escape(theFile.name),
							'"/>' ].join('');
					document.getElementById('list').insertBefore(span, null);
					a();
				};
				a();
			})(f);
			// Read in the image file as a data URL.
			reader.readAsDataURL(f);
			a();
		}

		function a() {
			jQuery('#cropbox').Jcrop({
				onChange : setCoords,
				onSelect : setCoords,
				bgColor : 'black',
				bgOpacity : .4,
				setSelect : [ 100, 0, 253, 353 ],
				aspectRatio : 1
			});
		}

		function setCoords(c) {
			jQuery('#x1').val(c.x);
			jQuery('#y1').val(c.y);
			jQuery('#x2').val(c.x2);
			jQuery('#y2').val(c.y2);
			jQuery('#w').val(c.w);
			jQuery('#h').val(c.h);
		}

		$(document)
				.ready(
						function() {
							function isNotMax(e, id) {
								var validateValueTextArea = document
										.getElementById(id);
								validateValueTextArea.value = validateValueTextArea.value
										.substr(0, validateValueTextArea
												.getAttribute('maxlength'));
							}
							document.getElementById('mainImage')
									.addEventListener('change',
											handleFileSelect, false);
						})
	</script>

</form:form>