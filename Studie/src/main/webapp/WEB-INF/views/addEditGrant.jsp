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
                    <form:input path="university" id="univer"  maxlength="100"
                         onkeypress="return isNotMax(event,this)" name="university"/>
                    <p/><form:errors path="university" cssClass="error"/>
                </div>
                
                <div class="textBox">
                    <div><spring:message code="form.grantCity"/></label>
                    </div>
                    <form:input path="city" id="city"  maxlength="100"
                        onkeypress="return isNotMax(event,this)" name="city"/>
                    <p/><form:errors path="city" cssClass="error"/>
                </div>
                <div class="textBox">
                    <div><spring:message code="form.grantCountry"/></label>
                    </div>
                    <form:input path="country" id="country"  maxlength="100"
                        onkeypress="return isNotMax(event,this)" name="country"/>
                    <p/><form:errors path="country" cssClass="error"/>
                </div>
                <div class="textBox">
                    <div><spring:message code="form.grantSpeciality"/></label>
                    </div>
                    <form:input path="speciality" id="spec"  maxlength="100"
                        onkeypress="return isNotMax(event,this)" name="speciality"/>
                    <p/><form:errors path="speciality" cssClass="error"/>
                </div>
                </td>
                <td>
                <div class="textBox">
                    <div><spring:message code="form.grantQualification"/></label>
                    </div>
                    <form:input path="qualification" id="qualif"  maxlength="100"
                        onkeypress="return isNotMax(event,this)" name="qualification"/>
                    <p/><form:errors path="qualification" cssClass="error"/>
                </div>
                <div class="textBox">
                    <div><spring:message code="form.grantEducationPeriod"/></label>
                    </div>
                    <form:input path="educationPeriod" id="period"  maxlength="100"
                        onkeypress="return isNotMax(event,this)" name="educationPeriod"/>
                    <p/><form:errors path="educationPeriod" cssClass="error"/>
                </div>
                <div class="textBox">
                    <div><spring:message code="form.grantEducationLanguage"/></label>
                    </div>
                    <form:input path="educationLanguage" id="language"  maxlength="100"
                        onkeypress="return isNotMax(event,this)" name="educationLanguage"/>
                    <p/><form:errors path="educationLanguage" cssClass="error"/>
                </div>
                <div class="textBox">
                    <div><spring:message code="form.grantReceiptOfDocuments"/></label>
                    </div>
                    <form:input path="receiptOfDocuments" id="receiptDocuments"  maxlength="100"
                        onkeypress="return isNotMax(event,this)" name="receiptOfDocuments"/>
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
				     style="resize: none; width: 430px; height: 471px; font-size: 12px;"
					 cols="20" rows="8" maxlength="3000"
                     onkeypress="return isNotMax(event,this)"></form:textarea>
				</td>
				<td>
				<div id="labels">
					<spring:message code="form.grantDocuments" />
				</div>
				<form:errors path="documents" cssClass="error" />
				<form:textarea path="documents"
				     style="resize: none; width: 430px; height: 471px;  font-size: 12px; "
					 cols="20" rows="8" maxlength="3000"
                    onkeypress="return isNotMax(event,this)"></form:textarea>
				</td>
				</tr>
				</table>
				<div id="sbm" align="center">
                <input type="submit" value="<spring:message
                       code="form.submit.council"/>"/>
            	</div>
			</div>

    <script src="${pageContext.request.contextPath}/js/a.js" type="text/javascript"></script>
                <script src="${pageContext.request.contextPath}/js/setCoords.js" type="text/javascript"></script>
                <script src="${pageContext.request.contextPath}/js/handleFile.js" type="text/javascript"></script>



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

       <script>
		$(document)
				.ready(
						function() {

							document.getElementById('mainImage')
									.addEventListener('change',
											handleFileSelect, false);
						})
	</script>

	<script>
                         $(document).ready(function() {
                         $.validator.setDefaults({ ignore: [] });
                                 $('#grantForm').validate({

                         rules: {
                         university: {
                         required: true,
                                 minlength: 5,
                                 maxlength: 100
                         },
                         city: {
                           required: true,
                                 minlength: 5,
                                 maxlength: 100
                         },
                         country: {
                         required: true,
                                 minlength: 5,
                                 maxlength: 100
                         },
                         speciality: {
                         required: true,
                                 minlength: 5,
                                 maxlength: 100
                         },
                         qualification: {
                         required: true,
                                 minlength: 5,
                                 maxlength: 100
                         },
                         educationPeriod: {
                         required: true,
                                 minlength: 5,
                                 maxlength: 100
                         },
                         educationLanguage: {
                         required: true,
                                 minlength: 5,
                                 maxlength: 100
                         },
                         receiptOfDocuments: {
                         required: true,
                                 minlength: 5,
                                 maxlength: 100
                         },
                         description: {
                         required: true,
                                 minlength: 100,
                                 maxlength: 3000
                         },
                         documents: {
                         required: true,
                                 minlength: 100,
                                 maxlength: 3000
                         }

                         },
                                 messages: {
                         university: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.title.minlength"/>",
                                 maxlength:  "<spring:message code="val.title.maxlength"/>"
                         },
                         city: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.title.minlength"/>",
                                 maxlength:  "<spring:message code="val.title.maxlength"/>"
                         },
                         country: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.title.minlength"/>",
                                 maxlength:  "<spring:message code="val.title.maxlength"/>"
                         },
                         speciality: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.title.minlength"/>",
                                 maxlength:  "<spring:message code="val.title.maxlength"/>"
                         },
                         qualification: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.title.minlength"/>",
                                 maxlength:  "<spring:message code="val.title.maxlength"/>"
                         },
                         receiptOfDocuments: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.title.minlength"/>",
                                 maxlength:  "<spring:message code="val.title.maxlength"/>"
                         },
                         educationPeriod: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.title.minlength"/>",
                                 maxlength:  "<spring:message code="val.title.maxlength"/>"
                         },
                         educationLanguage: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.title.minlength"/>",
                                 maxlength:  "<spring:message code="val.title.maxlength"/>"
                         },
                         documents: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.textcouncil.minlength"/>",
                                 maxlength:  "<spring:message code="val.textcouncil.maxlength"/>"
                         },
                         description: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.textcouncil.minlength"/>",
                                 maxlength:  "<spring:message code="val.textcouncil.maxlength"/>"
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

</form:form>