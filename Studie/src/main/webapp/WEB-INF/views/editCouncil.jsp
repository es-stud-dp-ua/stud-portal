<%@ page import="ua.dp.stud.studie.model.Council" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="include.jsp" %>

<portlet:defineObjects/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <%
    Boolean russianLocaleEnabled = request.getLocale().getLanguage().equals("ru");
    Council council = (Council) request.getAttribute("council");
		%>
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
            
            </script>
        <script language="javascript" type="text/javascript">
                    $(document).ready(function() {
            });
                    function isNotMax(e, id) {
                    var validateValueTextArea = document.getElementById(id);
                            validateValueTextArea.value = validateValueTextArea.value.substr(0, validateValueTextArea.getAttribute('maxlength'));
                    }
        </script>
    <portlet:renderURL var="home"> 
    </portlet:renderURL>

    <portlet:actionURL var="actionLink" name="editCouncil"></portlet:actionURL>
	
	<div class="portlet-content-controlpanel fs20">
        <a href="${home}">
            <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
        </a>
    </div>

    <liferay-ui:error key="no-images" message='<spring:message code="msg.noImages"/>'/>
    <liferay-ui:error key="dplTopic" message='<spring:message code="msg.dplTopic"/>'/>


    <div width="100%" align="center">
        <form:form method="post" id="councilForm"  action="${actionLink}"  enctype="multipart/form-data" commandName="council" modelAttribute="council">
            <input type="hidden" size="0" id="x1" name="t" value="0"/>
             <input type="hidden" size="0" id="y1" name="l" value="0"/>
             <input type="hidden" size="0" id="w" name="w" value="100"/>
             <input type="hidden" size="0" id="h" name="h" value="100"/>
             <input type="hidden" name="id" value="<%=council.getId()%>">

           <div id="labels" width="50%" align="center"><spring:message code="form.councilName"/></div>

                        <form:input path="councilName" id="councilName"  size = "100"  rows="2" maxlength="100"  name="councilName"/>
                       
                        <div style="width: 450px; padding-center: 20px;">
                        <div style="left: 30%;" style="width: 460px;">
                                        <div style="height: 300px;">
                                            
                                            <% if (council.getMainImage() == null) { %>
                                            <div id="mainPic"
                                                 style="background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
                                            <output id="list"></output>
                                            </div>
                                            <% } else { %>
                                              <div id="mainPic" >
                                                 <img id="img" style="vertical-align: top; " src="${mainImage}"/>
                                                 <output id="list"></output>
                                                 </div>
                                                 <% } %>
                                            </div>
                                        </div>
                        </div>
                       
                        <div style="left: 30%;" id="mainImageLoader">

                                                   <div id="mainImgloaderBtn">
                                                       <input type="file" id="mainImage" name="mainImage" accept="image/jpeg,image/png,image/gif"/>
                                                       <div id="nt"><spring:message code="form.addPicture"/></div>
                                                   </div>
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
                    </div>
            <table width="100%" margin-bottom="15px">
                   <tr>
                     <td  width="50%" align="center">
                         <div id="labels"><spring:message code="form.councilContact"/></div>
                            <div style="margin-left: 5px;">
                                <form:textarea title="${council.councilContact}" path="councilContact" class="ckeditor" id="councilContact" cols="60" rows="5" maxlength="10000"
                                          name="councilContact"/>
                            </div>   
                    </td>
                        <td width="50%" align="center">
                            <div id="labels"><spring:message code="form.councilDescription"/></div>
                            <div style="margin-left: 5px;">
                                <form:textarea title="${council.councilDescription}" path="councilDescription" class="ckeditor" id="councilDescription" cols="60" rows="5" maxlength="10000"
                                           name="councilDescription"/>
                            </div>
                    </td>
                </tr>
            </table>
            <br/>
            <div id="sbm" align="center">
                <input type="submit" value="<spring:message
                       code="form.submit.council"/>"/>
            </div>
        </form:form>
    </div>
</body>
</html>
