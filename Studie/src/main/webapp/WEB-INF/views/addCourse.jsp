<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>
<!--<%@include file="include.jsp" %>-->
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

        <style type="text/css">
            .error {
                color: #ff0000;
            }
            label.valid {
                width: 24px;
                height: 24px;
                background: url(${pageContext.request.contextPath}/images/valid.png) center center no-repeat;
                display: inline-block;
                text-indent: -9999px;
            }
            label.error {
                font-weight: bold;
                color: red;
                padding: 2px 8px;
                margin-top: 2px;
            }
        </style>
</head>
<body>

        <script type="text/javascript">
            function a() {
            jQuery('#cropbox').Jcrop({onChange: setCoords, onSelect: setCoords, bgColor: 'black',
                    bgOpacity: .4,
                    setSelect: [100, 0, 253, 353],
                    aspectRatio: 1});
            }
            function setCoords(c) {
            jQuery('#x1').val(c.x);
                    jQuery('#y1').val(c.y);
                    jQuery('#x2').val(c.x2);
                    jQuery('#y2').val(c.y2);
                    jQuery('#w').val(c.w);
                    jQuery('#h').val(c.h);
            };
            function handleFileSelect(evt) {
                    var files = evt.target.files; // FileList object

                    // Loop through the FileList and render image files as thumbnails.
                    var f = files[files.length - 1];

                    // Only process im11age files.
                    document.getElementById('list').innerHTML = '';
                    var reader = new FileReader();
                    // Closure to capture the file information.
                    reader.onload = (function (theFile) {
                        return function (e) {
                            // Render thumbnail.
                            var span = document.createElement('span');
                            span.innerHTML = ['<img id="cropbox" class="thumb" width="443px" src="', e.target.result,
                                '" title="', escape(theFile.name), '"/>'].join('');
                            document.getElementById('list').insertBefore(span, null);
                            a();
                        };
                        a();
                    })(f);
                    // Read in the image file as a data URL.
                    reader.readAsDataURL(f);
                    a();
                }
            </script>
        <script language="javascript" type="text/javascript">
                    $(document).ready(function() {
            });
                    function isNotMax(e, id) {
                    var validateValueTextArea = document.getElementById(id);
                            validateValueTextArea.value = validateValueTextArea.value.substr(0, validateValueTextArea.getAttribute('maxlength'));
                    }

        </script>

<portlet:actionURL var="add" name="saveNewCourse"></portlet:actionURL>
<script src="${pageContext.request.contextPath}/js/dynamic_list_helper.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/dynamic_list_special.js" type="text/javascript"></script>
<form:form method="POST" commandName="course" action="${add}" enctype="multipart/form-data" id="courseForm">

        <input type="hidden" size="0" id="x1" name="t"/>
        <input type="hidden" size="0" id="y1" name="l"/>
        <input type="hidden" size="0" id="w" name="w"/>
        <input type="hidden" size="0" id="h" name="h"/>
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


                    <div style="width: 450px; padding-center: 20px;">

                        <div style="left: 30%;" style="width: 460px;">
                                        <div style="height: 300px;">
                                            <div id="lup"></div>
                                            <div id="mainPic"
                                                 style="background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
                                            <output id="list"></output>
                                            </div>
                                        </div>
                        </div>
                        <div style="left: 30%;" id="mainImageLoader">

                                                   <div id="mainImgloaderBtn">
                                                       <input type="file" id="mainImage" name="mainImage" accept="image/jpeg,image/png,image/gif"/>
                                                       <div id="nt"><spring:message code="form.addPicture"/></div>
                                                   </div>
                        </div>

                        <br/>
                        <br/>
                                                        <div id="labels" style="width: 150px; font-size: 12pt; padding-left: 125px">
                                                                                <spring:message code="course.Ttype"/>
                                                                                </div>
                                              <div class="textBox" style="padding-left: 125px">
                                               <form:select path="coursesType">
                                                   <form:option value="NONE" label="--- Выбрать ---" />
                                                   <form:options items="${statusList}" />
                                               </form:select>
                                               <p/><form:errors path="coursesType" cssClass="error"/>
                                           </div>
                                                        <div id="labels" style="width: 150px; font-size: 12pt; padding-left: 125px">
                                                                                <spring:message code="course.Tkind"/>
                                                                                </div>
                                                                                <br/>
                                             <div class="textBox" style="padding-left: 125px">
                                                       <form:select path="kindOfCourse">
                                                          <form:option value="NONE" label="--- Выбрать ---" />
                                                           <form:options items="${kindOfCourses}" />
                                                      </form:select>
                                             <p/><form:errors path="kindOfCourse" cssClass="error"/>
                                                    </div>
                                                        <div id="labels" style="width: 150px; font-size: 12pt">
                                                                                <spring:message code="course.Tinfo"/>
                                                                                </div>
                        <textarea style="height: 124px; width: 436px" path="coursesContact" id="contact" maxlength="300"  name="contacts"></textarea>

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
                                           code='<%=(request.isUserInRole("Administrator"))?"form.submit.admin"
                                                                                                                 :"form.submit.user"%>'/>"/>
                                </div>
                    </div>
            </div>

     </div>
</form:form>
</body>
</html>