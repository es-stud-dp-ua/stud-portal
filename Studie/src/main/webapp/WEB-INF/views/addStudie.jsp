<%@ page import="ua.dp.stud.StudPortalLib.model.Category" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>

<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/myscripts.js"></script>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="include.jsp" %>

<%
    //todo: remove this
    Locale locale = (Locale) request.getSession().getAttribute("org.apache.struts.action.LOCALE");
    String language = locale.getLanguage();
    String country = locale.getCountry();
    ResourceBundle res = ResourceBundle.getBundle("messages", new Locale(language, country));
    
    String ex = (String) request.getAttribute("exception");

%>
<portlet:defineObjects/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <!-- todo: move css to separate file-->
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

        <script language="javascript" type="text/javascript">
            $(document).ready(function() {
                $.Placeholder.init({color: "#aaa"});
            });

            function isNotMax(e, id) {
                var validateValueTextArea = document.getElementById(id);
                validateValueTextArea.value = validateValueTextArea.value.substr(0, validateValueTextArea.getAttribute('maxlength'));
            }
        </script>

    <portlet:renderURL var="home"> </portlet:renderURL>

    <portlet:actionURL var="actionLink" name="addStudie"></portlet:actionURL>
    <div style=" width: 80%; font-size: 14px; text-align: right ">
        <a href="${home}"><spring:message code="form.back"/></a>
    </div>

    <liferay-ui:error key="no-images" message='<%=res.getString("msg.noImages")%>'/>

    <liferay-ui:error key="dplTopic" message='<%=res.getString("msg.dplTopic")%>'/>
    <%if (ex != null) {%>
    <%=ex%>
    <%}%>

    <div width="100%" align="center">
        <form method="POST" action="${actionLink}" enctype="multipart/form-data">
        <!-- todo: move javascript to separate file-->
            <script type="text/javascript">
                function a() {
                    jQuery('#cropbox').Jcrop({onChange: setCoords, onSelect: setCoords, bgColor: 'black',
                        bgOpacity: .4,
                        setSelect: [100, 0, 253, 353],
                        aspectRatio: 1});
                }
                function setCoords(c)
                {
                    jQuery('#x1').val(c.x);
                    jQuery('#y1').val(c.y);
                    jQuery('#x2').val(c.x2);
                    jQuery('#y2').val(c.y2);
                    jQuery('#w').val(c.w);
                    jQuery('#h').val(c.h);
                }

            </script>
            <input type="hidden" size="0" id="x1" name="t"/>
            <input type="hidden" size="0" id="y1" name="l"/>
            <input type="hidden" size="0" id="w" name="w"/>
            <input type="hidden" size="0" id="h" name="h"/>
            <table width="100%" margin-bottom="15px">
                <tr>
                    <td width="50%" align="center">

                        <!---->
                        <!--<input type="file" name="mainImage" accept="image/jpeg,image/png,image/gif" />-->
                        <style>
                            .thumb {
                                height: 253px;
                                width: 443px;
                            }
                        </style>
                        <div id="lup">
                        </div>
                        <div id="mainPic" style="background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
                            <!-- Output for our douwnload Image-->
                            <output id="list"></output>
                        </div>
                        <div id="rdn">
                        </div>   

                        <div id="mainImageLoader">
                            <div id="mainImgloaderBtn">
                                <input name="mainImage" type="file" id="mainImage" accept="image/jpeg,image/png,image/gif"/ multiple>
                                       <div id="nt"><spring:message code="form.addMainPicture"/></div>
                            </div>
                        </div>
                        <script>
                            function handleFileSelect(evt)
                            {
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
                                        span.innerHTML = ['<img id="cropbox" class="thumb" src="', e.target.result,
                                            '" title="', escape(theFile.name), '"/>'].join('');
                                        document.getElementById('list').insertBefore(span, null);
                                    a();
                                };a();
                            })(f);
                            // Read in the image file as a data URL.
                            reader.readAsDataURL(f);
                               a();
                            }

                            document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);
                            function Add() {
                                var txt = document.createElement('textarea');
                                txt.setAttribute("name", "facultetDnevn");
                                txt.setAttribute("cols", "60");
                                txt.setAttribute("rows", "1");
                                txt.setAttribute("id", "facultetInput");
                                txt.setAttribute("maxlength", "90");
                                txt.style.cssText = 'float: left;';
                                document.getElementById('facultetD').appendChild(txt);
                            }
                            function Add2() {
                                var txt = document.createElement('textarea');
                                txt.setAttribute("name", "facultetZaoch");
                                txt.setAttribute("cols", "60");
                                txt.setAttribute("rows", "1");
                                txt.setAttribute("id", "facultetInput");
                                txt.setAttribute("maxlength", "90");
                                txt.style.cssText = 'float: left;';
                                document.getElementById('facultetZ').appendChild(txt);
                            }
                        </script>
                        <div id="imageLoader">
                            <div id="imgloaderBtn">
                                <input name="images" type="file" id="aui_3_2_0_11607" accept="image/jpeg,image/png,image/gif"/ multiple>
                                       <div id="nt"><spring:message code="form.addPictures"/></div>
                            </div>
                        </div>
                        <br/>
                        <div style="margin-top: 420px;">
                        <div id="facultetD" style="float: left;">
                            <label style="font-size:16px; font-weight: bold;"><spring:message code="form.dnevn"/></label> <br/>
                            <textarea id="facultetInput" cols="60" rows="1" maxlength="90" onkeyup="isNotMax(event, getAttribute('id'))" name="facultetDnevn" style="float: left; margin-top: 15px;"></textarea>
                            <input type="button" id="plus1" name="plus1" value="+" onclick="Add()" style="float: right; margin-top: 18px; margin-left: 3px; "/>           
                        </div>
                        <br/> <br/>
                        <div  style="float: left;">
                            <label style="font-size:16px; font-weight: bold;"><spring:message code="form.adress"/></label> <br/>
                            <textarea  id="adressInput" cols="60" rows="1" maxlength="90" onkeyup="isNotMax(event, getAttribute('id'))" name="adress" style="float: left; margin-top: 15px;" ></textarea><br/><br/>
                        </div>
                        </div>
                    </td>
                    <td rowspan=2 width="50%" align="left" style="vertical-align: top ">
                        <textarea id="topicInput" cols="90" rows="2" maxlength="80" onkeyup="isNotMax(event, getAttribute('id'))" name="topic" placeholder="<spring:message code="form.topic"/>"></textarea><br/>
                        <div style="width: 450px; float: bottom ; padding-left: 8px;" >
                            <textarea class="ckeditor" id="textInput" cols="60" rows="10" maxlength="8000" onkeyup="isNotMax(event, getAttribute('id'))" name="text" placeholder="<spring:message code="form.text"/>"></textarea><br/><br/>
                        </div> <br/>
                        <div id="facultetZ" style="float: left;">
                            <label style="font-size:16px; font-weight: bold;"><spring:message code="form.zaoch"/></label> <br/>
                            <textarea id="facultetInput" cols="60" rows="1" maxlength="90" onkeyup="isNotMax(event, getAttribute('id'))" name="facultetZaoch" style="float: left; margin-top: 15px;"></textarea>
                            <input type="button" id="plus1" name="plus1" value="+" onclick="Add2()" style="float: right; margin-top: 18px; margin-left: 3px; "/>                       
                        </div>
                        <br/> <br/>
                        <div id="sbm" >
                            <br/> <br/>
                            <input type="submit" style="vertical-align: central; margin-top: 15px; " value="<spring:message
                                   code='<%=(request.isUserInRole("Administrator"))?"form.submit.admin"
                                                                                             :"form.submit.user"%>'/>"/>
                        </div>
                        <br/><br/>
                    </td>
                </tr>
                <tr>
                    <td width="50%" align="right">
                        <div id="eventSetting">
                            <div style="font-size:14px"> 
                                <br/>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>
