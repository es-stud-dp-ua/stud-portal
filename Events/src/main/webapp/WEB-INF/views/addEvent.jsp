<<<<<<< HEAD
<%@ page import="ua.dp.stud.StudPortalLib.model.Events" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.EventsType" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="include.jsp" %>

<portlet:defineObjects/>
<html>
    <head>
        <script>
            $(function() {
            $.datepicker.setDefaults($.datepicker.regional['ru']);
                    $("#datepicker1").datepicker({ dateFormat: "mm/dd/yy", showAnim:'slide', showButtonPanel:true});
                    $("#datepicker2").datepicker({ dateFormat: "mm/dd/yy", showAnim:'slide', showButtonPanel:true});
            });</script>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <% Boolean russianLocaleEnabled = request.getLocale().getLanguage().equals("ru");  %>
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
                    $('#defaultEntry').timeEntry().change(function() {
            var log = $('#log');
                    log.val(log.val() + ($('#defaultEntry').val() || 'blank') + '\n');
            });
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
            }
            ;</script>
        <script language="javascript" type="text/javascript">
                    $(document).ready(function() {
            });
                    function isNotMax(e, id) {
                    var validateValueTextArea = document.getElementById(id);
                            validateValueTextArea.value = validateValueTextArea.value.substr(0, validateValueTextArea.getAttribute('maxlength'));
                    }
        </script>
    <portlet:renderURL var="home"> </portlet:renderURL>

    <portlet:actionURL var="actionLink" name="addEvents"></portlet:actionURL>
    <div class="portlet-content-controlpanel fs20">
        <a href="${home}">
            <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
        </a>
    </div>

    <liferay-ui:error key="no-images" message='<spring:message code="msg.noImages"/>'/>
    <liferay-ui:error key="dplTopic" message='<spring:message code="msg.dplTopic"/>'/>
    <c:if test="${exception}">
        ${exception}
    </c:if>

    <div width="100%" align="center">
        <form:form method="post" id="jform" name="jform" action="${actionLink}"  enctype="multipart/form-data" commandName="event" modelAttribute="event">
            <input type="hidden" size="0" id="x1" name="t" />
            <input type="hidden" size="0" id="y1" name="l" />
            <input type="hidden" size="0" id="w" name="w" />
            <input type="hidden" size="0" id="h" name="h" />
            <table width="100%" margin-bottom="15px">
                <tr>
                    <td width="50%" align="center">
                        <style>
                            .thumb {
                                height: 253px;
                                width: 443px;
                            }
                        </style>
                        <div id="lup">
                        </div>
                        <div id="mainPic"
                             style="background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
                            <!-- Output for our douwnload Image-->
                            <output id="list"></output>
                        </div>
                        <div id="rdn">
                        </div>
                        <div id="mainImageLoader">
                            <div id="mainImgloaderBtn">
                                <input type="file" id="mainImage" name="mainImage">
                                <div id="nt"><spring:message code="form.addMainPictures"/></div>
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
                                    span.innerHTML = ['<img id="cropbox" width="453px"  class="thumb" src="', e.target.result,
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
                            document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);</script>

                        <br/>
                    </td>
                    <td rowspan=2 width="50%" align="left">
                        <div id="labels"><spring:message code="form.title"/></div><div id="redStar1">*</div>
                            <form:input path="title" id="title" cols="90" rows="2" maxlength="100"  name="title"/>
                            <form:errors path="title" cssClass="error"></form:errors>
                            <div id="labels"><spring:message code="form.text"/></div><div id="redStar2">*</div>
                            <div style="margin-left: 5px;">
                                <textarea path="text" class="ckeditor" id="text" cols="60" rows="10" maxlength="10000"
                                          name="text" ></textarea>
                                <textarea style="visibility: hidden;width: 0px;height:0px;" id="text1" name="text1"  ></textarea>
                            </div>
                        <form:errors path="text" cssClass="error" ></form:errors>
                            <br/><br/>

                            <br/><br/>
                        </td>
                    </tr>
                    <tr>
                        <td width="50%" align="right">
                            <div id="eventSetting">
                                <div style="margin-right: 10px; margin-top: 115px;"><spring:message code="form.dateStart"/><div id="redStar4">*</div></div><input type="text" style="width: 40%;" name="EventDateStart" id="datepicker1"/><input type="text" placeholder="HH:mm" maxlength="5" style="width: 15%;margin-left: 1%; text-align: center;" name="startTime" id="defaultEntry"/>
                                <div style="margin-right: 10px;"><spring:message code="form.dateEnd"/></div><input type="text" style="width: 40%;" name="EventDateEnd" id="datepicker2"/><input type="text" placeholder="HH:mm" maxlength="5" style="width: 15%;margin-left: 1%;text-align: center;" name="endTime" id="endTime"/>
                                <div style="margin-right: 10px;"><spring:message code="form.location"/></div> <form:input path="location" id="location" cols="60" rows="2" maxlength="100"  name="location"/>
                            <div style="margin-right: 10px;"><spring:message code="form.tags"/></div><input type="text" id="tags" name="tags" style="width:60%;text-align: center;" placeholder="<spring:message code='form.tags.placeholder'/>" />
                            <div style="font-size:14px">
                                <div style="float: right; margin-top: 0px; ">
                                    <table>
                                        <tr><label>
                                            <div style="font-weight: bold; "><spring:message
                                                    code="addEvent.type"/></div>
                                        </label></tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.SPORTS"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= EventsType.SPORTS %>"
                                                       checked="" style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px; "><spring:message
                                                        code="form.MUSIC"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= EventsType.MUSIC %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.VACANCY"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= EventsType.VACANCY %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.CONFERENCE"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= EventsType.CONFERENCE %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.WEBINAR"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= EventsType.WEBINAR %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.THEATRE"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= EventsType.THEATRE %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                    </table>
                                </div>
                                <br/>

                            </div>
                        </div>
                    </td>
                </tr>
            </table>
            <div id="sbm">
                <input type="submit" value="<spring:message
                       code='<%=(request.isUserInRole("Administrator"))?"form.submit.admin"
                                                                                             :"form.submit.user"%>'/>"/>
            </div>
        </form:form>
        <script >
            $("#jform").on("submit",function(event){

                         document.getElementById('text1').innerHTML = CKEDITOR.instances.text.getData();

                                                                                 });
                                 $(document).ready(function() {
                         $.validator.setDefaults({ ignore: [] });
                                 $('#jform').validate({
                         rules: {
                         title: {
                         required: true,
                                 minlength: 5,
                                 maxlength: 100
                         },
                                 text1: {
                         required: true,
                                 minlength: 500,
                                 maxlength: 10000
                         },
                        EventDateStart:
                                     {required: true },
                         EventDateEnd:
                                     {required: true }

                         },
                                 messages: {
                         title: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.title.minlength"/>",
                                 maxlength:  "<spring:message code="val.title.maxlength"/>"
                         },
                                 text1: {
                         required: "<spring:message code="val.required"/>",
                                 minlength:  "<spring:message code="val.text.minlength"/>",
                                 maxlength:  "<spring:message code="val.text.maxlength"/>"
                         },
                         EventDateStart:
                                 {required: "<spring:message code="val.required"/>" },
                         EventDateEnd:
                                 {required: "<spring:message code="val.required"/>" }
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

    </div>
</body>
</html>
=======
<%@ page import="ua.dp.stud.StudPortalLib.model.Events" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.EventsType" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="include.jsp" %>

<portlet:defineObjects/>
<html>
    <head>
        <script>
            $(function() {
            $.datepicker.setDefaults($.datepicker.regional['ru']);
                    $("#datepicker1").datepicker({ dateFormat: "mm/dd/yy", showAnim:'slide', showButtonPanel:true});
                    $("#datepicker2").datepicker({ dateFormat: "mm/dd/yy", showAnim:'slide', showButtonPanel:true});
            });</script>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <% Boolean russianLocaleEnabled = request.getLocale().getLanguage().equals("ru");  %>
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
                    $('#defaultEntry').timeEntry().change(function() {
            var log = $('#log');
                    log.val(log.val() + ($('#defaultEntry').val() || 'blank') + '\n');
            });
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
            }
            ;</script>
        <script language="javascript" type="text/javascript">
                    $(document).ready(function() {
            });
                    function isNotMax(e, id) {
                    var validateValueTextArea = document.getElementById(id);
                            validateValueTextArea.value = validateValueTextArea.value.substr(0, validateValueTextArea.getAttribute('maxlength'));
                    }
        </script>
    <portlet:renderURL var="home">
        <portlet:param name="nAction" value="home"/>
    </portlet:renderURL>
    
    <portlet:actionURL var="actionLink" name="addEvents"></portlet:actionURL>
    <div class="portlet-content-controlpanel fs20">
        <a href="${home}">
            <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
        </a>
    </div>

    <liferay-ui:error key="no-images" message='<spring:message code="msg.noImages"/>'/>
    <liferay-ui:error key="dplTopic" message='<spring:message code="msg.dplTopic"/>'/>
    <c:if test="${exception}">
        ${exception}
    </c:if>

    <div width="100%" align="center">
        <form:form method="post" id="jform" name="jform" action="${actionLink}"  enctype="multipart/form-data" commandName="event" modelAttribute="event">
            <input type="hidden" size="0" id="x1" name="t" />
            <input type="hidden" size="0" id="y1" name="l" />
            <input type="hidden" size="0" id="w" name="w" />
            <input type="hidden" size="0" id="h" name="h" />
            <table width="100%" margin-bottom="15px">
                <tr>
                    <td width="50%" align="center">
                        <style>
                            .thumb {
                                height: 253px;
                                width: 443px;
                            }
                        </style>
                        <div id="lup">
                        </div>
                        <div id="mainPic"
                             style="background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
                            <!-- Output for our douwnload Image-->
                            <output id="list"></output>
                        </div>
                        <div id="rdn">
                        </div>
                        <div id="mainImageLoader">
                            <div id="mainImgloaderBtn">
                                <input type="file" id="mainImage" name="mainImage">
                                <div id="nt"><spring:message code="form.addMainPictures"/></div>
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
                                    span.innerHTML = ['<img id="cropbox" width="453px"  class="thumb" src="', e.target.result,
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
                            document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);</script>

                        <br/>
                    </td>
                    <td rowspan=2 width="50%" align="left">
                        <div id="labels"><spring:message code="form.title"/></div><div id="redStar1">*</div>

                            <form:errors path="title" cssClass="error"></form:errors>
                            <div id="labels"><spring:message code="form.text"/></div><div id="redStar2">*</div>
                            <div style="margin-left: 5px;">
                                <textarea path="text" class="ckeditor" id="text" cols="60" rows="10" maxlength="10000"
                                          name="text" ></textarea>
                                <textarea style="visibility: hidden;width: 0px;height:0px;" id="text1" name="text1"  ></textarea>
                            </div>
                        <form:errors path="text" cssClass="error" ></form:errors>
                            <br/><br/>

                            <br/><br/>
                        </td>
                    </tr>
                    <tr>
                        <td width="50%" align="right">
                            <div id="eventSetting">
                                <div style="margin-right: 10px; margin-top: 115px;"><spring:message code="form.dateStart"/><div id="redStar4">*</div></div><input type="text" style="width: 40%;" name="EventDateStart" id="datepicker1"/><input type="text" placeholder="HH:mm" maxlength="5" style="width: 15%;margin-left: 1%; text-align: center;" name="startTime" id="defaultEntry"/>
                                <div style="margin-right: 10px;"><spring:message code="form.dateEnd"/></div><input type="text" style="width: 40%;" name="EventDateEnd" id="datepicker2"/><input type="text" placeholder="HH:mm" maxlength="5" style="width: 15%;margin-left: 1%;text-align: center;" name="endTime" id="endTime"/>
                                <div style="margin-right: 10px;"><spring:message code="form.location"/></div> <form:input path="location" id="location" cols="60" rows="2" maxlength="100"  name="location"/>
                            <div style="margin-right: 10px;"><spring:message code="form.tags"/></div><input type="text" id="tags" name="tags" style="width:60%;text-align: center;" placeholder="<spring:message code='form.tags.placeholder'/>" />
                            <div style="font-size:14px">
                                <div style="float: right; margin-top: 0px; ">
                                    <table>
                                        <tr><label>
                                            <div style="font-weight: bold; "><spring:message
                                                    code="addEvent.type"/></div>
                                        </label></tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.SPORTS"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= EventsType.SPORTS %>"
                                                       checked="" style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px; "><spring:message
                                                        code="form.MUSIC"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= EventsType.MUSIC %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.VACANCY"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= EventsType.VACANCY %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.CONFERENCE"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= EventsType.CONFERENCE %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.WEBINAR"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= EventsType.WEBINAR %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="form.THEATRE"/></div>
                                            </td>
                                            <td><input type="radio" name="type" value="<%= EventsType.THEATRE %>"
                                                       style="float: right;"/></td>
                                        </tr>
                                    </table>
                                </div>
                                <br/>

                            </div>
                        </div>
                    </td>
                </tr>
            </table>
            <div id="sbm">
                <input type="submit" value="<spring:message
                       code='<%=(request.isUserInRole("Administrator"))?"form.submit.admin"
                                                                                             :"form.submit.user"%>'/>"/>
            </div>
        </form:form>
        <script type="text/javascript">
                    function f(){
                    window.setInterval("document.getElementById('text1').innerHTML = CKEDITOR.instances.text.getData(); if(document.getElementById('text').value!=CKEDITOR.instances.text.getData()) $('#jform').valid()", 500);
                    }
            window.onload = f;
                    $(document).ready(function() {
            $.validator.setDefaults({ ignore: [] });
                    $('#jform').validate({
            rules: {
            title: {
            required: true,
                    minlength: 5,
                    maxlength: 100
            },
                    text1: {
            required: true,
                    minlength: 500,
                    maxlength: 10000
            }

            },
                    messages: {
            title: {
            required: "<spring:message code="val.required"/>",
                    minlength:  "<spring:message code="val.title.minlength"/>",
                    maxlength:  "<spring:message code="val.title.maxlength"/>"
            },
                    text1: {
            required: "<spring:message code="val.required"/>",
                    minlength:  "<spring:message code="val.text.minlength"/>",
                    maxlength:  "<spring:message code="val.text.maxlength"/>"
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

    </div>
</body>
</html>
>>>>>>> 327e8e87cab2e2f4723915d3dab12714a18968fa
