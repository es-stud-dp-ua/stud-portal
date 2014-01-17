<form:form method="POST" commandName="grant" action="${addEdit}"
	enctype="multipart/form-data" id="grantForm" modelAttribute="grant">
	<input type="hidden" size="0" id="x1" name="t" value="0" />
	<input type="hidden" size="0" id="y1" name="l" value="0" />
	<input type="hidden" size="0" id="w" name="w" value="100" />
	<input type="hidden" size="0" id="h" name="h" value="100" />
  	<form:input type="hidden" path="id"/>
	<div>
		<br />
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
				<table>
				<tr>
				<td>
				<div class="textBox">
                    <div><spring:message code="form.grantUniversity"/></label>
                    </div>
                    <form:input path="university" name="university"/>
                    <p/><form:errors path="university" cssClass="error"/>
                </div>
                
                <div class="textBox">
                    <div><spring:message code="form.grantCity"/></label>
                    </div>
                    <form:input path="city" name="city"/>
                    <p/><form:errors path="city" cssClass="error"/>
                </div>
                <div class="textBox">
                    <div><spring:message code="form.grantCountry"/></label>
                    </div>
                    <form:input path="country" name="country"/>
                    <p/><form:errors path="country" cssClass="error"/>
                </div>
                <div class="textBox">
                    <div><spring:message code="form.grantSpeciality"/></label>
                    </div>
                    <form:input path="speciality" name="speciality"/>
                    <p/><form:errors path="speciality" cssClass="error"/>
                </div>
                </td>
                <td>
                <div class="textBox">
                    <div><spring:message code="form.grantQualification"/></label>
                    </div>
                    <form:input path="qualification" name="qualification"/>
                    <p/><form:errors path="qualification" cssClass="error"/>
                </div>
                <div class="textBox">
                    <div><spring:message code="form.grantEducationPeriod"/></label>
                    </div>
                    <form:input path="educationPeriod" name="educationPeriod"/>
                    <p/><form:errors path="educationPeriod" cssClass="error"/>
                </div>
                <div class="textBox">
                    <div><spring:message code="form.grantEducationLanguage"/></label>
                    </div>
                    <form:input path="educationLanguage" name="educationLanguage"/>
                    <p/><form:errors path="educationLanguage" cssClass="error"/>
                </div>
                <div class="textBox">
                    <div><spring:message code="form.grantReceiptOfDocuments"/></label>
                    </div>
                    <form:input path="receiptOfDocuments" name="receiptOfDocuments"/>
                    <p/><form:errors path="receiptOfDocuments" cssClass="error"/>
                </div>
                </td>
                
                
                <tr>
                <td>
				<div id="labels">
					<spring:message code="form.grantDescription" />
				</div>
				<form:errors path="description" cssClass="error" />
				<form:textarea path="description" 
					id="textInput" cols="20" rows="4"></form:textarea>
				</td>
				<td>
				<div id="labels">
					<spring:message code="form.grantDocuments" />
				</div>
				<form:errors path="documents" cssClass="error" />
				<form:textarea path="documents"
					id="textInput" cols="20" rows="4"></form:textarea>
				</td>
				</tr>
				</table>
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