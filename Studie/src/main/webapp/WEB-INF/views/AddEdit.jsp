<form:form method="POST" commandName="course" action="${add}" enctype="multipart/form-data" id="courseForm">

             <input type="hidden" size="0" id="x1" name="t" value="0"/>
             <input type="hidden" size="0" id="y1" name="l" value="0"/>
             <input type="hidden" size="0" id="w" name="w" value="100"/>
             <input type="hidden" size="0" id="h" name="h" value="100"/>
             <form:input type="hidden" path="id"/>

       <div style="padding-left: 230px">

            <br/>
            <div id="labels" style="width: 150px; font-size: 12pt">
                        <spring:message code="course.TName"/>
                        </div>




            <div style="left: 30%; width: 450px; padding-center: 8px;">

                    <form:textarea path="courseName" id="topicInput" cols="90" rows="2" maxlength="80" onkeyup="isNotMax(event, getAttribute('id'))"></form:textarea>
                                <form:errors path="courseName" cssClass="error"/>

                    <div id="labels" style="width: 150px; font-size: 12pt; left: 30%;">
                                            <spring:message code="course.TLogo"/>
                                            </div>

                        <div style="vertical-align: top; ">

                       <c:choose>
                                            <c:when test='${mainImage == null}' >
                                            <div>
                                                                                            <div id="lup"></div>
                                                                                            <div id="mainPic" style="float:none; background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
                                                                                                <output id="list"></output>
                                                                                            </div>
                                                                                            </div>
                                            </c:when>
                                             <c:otherwise>
                                              <div id="mainPic" style="float:none">
                                                                                                  <img id="img"  src="${mainImage}"/>
                                                                                                  <output id="list"></output>
                                                                                              </div>
                                             </c:otherwise>
                        </c:choose>

                        </div>
                        </br>
                        <div>
                        <div style="left: 30%;" id="mainImageLoader">

                                                   <div id="mainImgloaderBtn">
                                                       <input type="file" id="mainImage" name="mainImage" accept="image/jpeg,image/png,image/gif"/>
                                                       <div id="nt"><spring:message code="form.addPicture"/></div>
                                                   </div>
                        </div>

                        <br/>
                        <br/>
                                                        <div id="labels" style="width: 150px; font-size: 12pt; padding-left: 130px">
                                                                                <spring:message code="course.Ttype"/>
                                                                                </div>

                                               	<div class="textBox" style="padding-left: 130px">
                                               	<form:select path="coursesType">
                                               		<c:forEach var="type" items="${coursesType}">
                                               		<option value="${type}">${type}</option>
                                               		</c:forEach>
                                               	</form:select>
                                                </div>


                                                        <div id="labels" style="width: 150px; font-size: 12pt; padding-left: 130px">
                                                                                <spring:message code="course.Tkind"/>
                                                                                </div>
                                            <div class="textBox" style="padding-left: 130px">
                                                <form:select path="kindOfCourse.id">
                                                 <form:options items="${kindOfCourse}" itemValue="id" itemLabel="kindOfCourse"/>
                                                </form:select>
                                            </div>

                                                        <div id="labels" style="width: 150px; font-size: 12pt">
                                                                                <spring:message code="course.Tinfo"/>
                                                                                </div>
                        <form:textarea style="height: 124px; width: 436px" path="coursesContact" id="contact" maxlength="300"></form:textarea>

                                                                                <br/>
                                                        <div id="labels" style="width: 150px; font-size: 12pt">
                                                                                <spring:message code="course.TDesc"/>
                                                                                </div>
                                                                                <br/>

                        <form:errors path="coursesDescription" cssClass="error"/>
                        <form:textarea path="coursesDescription" class="ckeditor" id="textInput" cols="60" rows="8" maxlength="8000" onkeyup="isNotMax(event, getAttribute('id'))"></form:textarea>
                         <br/>
                                <div id="sbm" style="padding-left: 125px">
                                    <input type="submit" value="<spring:message
                                           code='<%=(request.isUserInRole("Administrator"))?"course.add"
                                                                                                      :"form.submit.user"%>'/>"/>
                                </div>
                        </div>
            </div>

     </div>
     <script src="${pageContext.request.contextPath}/js/a.js" type="text/javascript"></script>
                 <script src="${pageContext.request.contextPath}/js/setCoords.js" type="text/javascript"></script>
                 <script src="${pageContext.request.contextPath}/js/handleFile.js" type="text/javascript"></script>
     <script>


                            $(document).ready(function () {
                		    document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);
                    })
                    
                    
                            $("#jform").on("submit",function(event){

                         document.getElementById('text1').innerHTML = CKEDITOR.instances.text.getData();

                                                                                 });
                                 $(document).ready(function() {
                         $.validator.setDefaults({ ignore: [] });
                                 $('#jform').validate({
                         rules: {
                         courseName: {
                       			  required: true,
                         },
                         mainImage: { 
                        	 required: true,
                         },
                         coursesDescription: {
                         			required: true,
                         },
                         coursesContact: {
                         			required: true,
                         },
                                 messages: {
                         courseName: {
                         required: "<spring:message code="val.required"/>"
                         },
                         mainImage:{
                        	 required: "<spring:message code="val.required"/>"
                         },
                         coursesDescription: {
                         required: "<spring:message code="val.required"/>"
                         },
                         coursesContact: {
                         required: "<spring:message code="val.required"/>"
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