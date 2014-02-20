<form:form method="POST" commandName="course" action="${add}" enctype="multipart/form-data" id="courseForm" modelAttribute="course">

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

                    <form:textarea path="courseName" id="" cols="60" rows="2" maxlength="100"
                    onkeyup="isNotMax(event,this)" name="courseName"></form:textarea>
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
                        <form:textarea style="height: 124px; width: 436px" path="coursesContact" id="contact"  cols="60" rows="4"
                         onkeyup="isNotMax(event, this)"  maxlength="500" name="coursesContact"></form:textarea>
                        <form:errors path="coursesContact" cssClass="error"/>
                                                                                <br/>
                         <div id="labels" style="width: 150px; font-size: 12pt">
                             <spring:message code="course.TDesc"/>
                         </div>
                                                                                <br/>


                        <form:textarea path="coursesDescription" class="ckeditor" id="text" cols="60" rows="8" maxlength="3000"
                                onkeyup="isNotMax(event, this)"></form:textarea>
                        <textarea style="visibility: hidden;width: 0px;" id="text1" name="text1"  ></textarea>
                        <form:errors path="coursesDescription" cssClass="error"/>
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
      document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);

	</script>
     
</form:form>