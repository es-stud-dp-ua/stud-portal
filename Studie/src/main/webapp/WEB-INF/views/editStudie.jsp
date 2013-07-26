<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.Category" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.ImageImpl" %>
<%@ page import="ua.dp.stud.studie.model.Studie" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/myscripts.js"></script>
<%@include file="include.jsp" %>

<%
    Locale locale = (Locale) request.getSession().getAttribute("org.apache.struts.action.LOCALE");
    String language = locale.getLanguage();
    String country = locale.getCountry();
    ResourceBundle res = ResourceBundle.getBundle("messages", new Locale(language, country));
    Collection<ImageImpl> additionalImages = (Collection<ImageImpl>) request.getAttribute("additionalImages");
    Studie studie = (Studie) request.getAttribute("studie");
%>
<portlet:defineObjects/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>
<body>
<script type="text/javascript">

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
    function Add() {
        var txt = document.createElement('textarea');
        txt.setAttribute("name", "facultetDnevn");
        txt.setAttribute("cols", "60");
        txt.setAttribute("rows", "1");
        txt.setAttribute("id", "facultetInput");
        txt.setAttribute("maxlength", "1000");
        txt.style.cssText = 'float: left;';
        document.getElementById('facultetD').appendChild(txt);
    }
    function Add2() {
        var txt = document.createElement('textarea');
        txt.setAttribute("name", "facultetZaoch");
        txt.setAttribute("cols", "60");
        txt.setAttribute("rows", "1");
        txt.setAttribute("id", "facultetInput");
        txt.setAttribute("maxlength", "1000");
        txt.style.cssText = 'float: left;';
        document.getElementById('facultetZ').appendChild(txt);
    }

</script>

<portlet:renderURL var="home"> </portlet:renderURL>

<portlet:actionURL var="actionLink" name="editStudie"></portlet:actionURL>
<div style="margin-right: 16px; float: right">
    <a href="${home}"><spring:message code="form.back"/></a>
</div>
    <% if (request.isUserInRole("Administrator")) { %>
<div style="margin-right: 16px; float: right">
    <a style="float: right" href="<portlet:renderURL/>&studieId=<%=studie.getId()%>&mode=delete"
       onclick='return confirm("<spring:message code="form.confDelete"/>")'>
        <spring:message code="form.delete"/>
    </a>
</div>
    <%}%>

<liferay-ui:error key="no-images" message='<%=res.getString("msg.noImages")%>'/>
<liferay-ui:error key="dplTopic" message='<%=res.getString("msg.dplTopic")%>'/>
<c:if test="${exception != null}">
    ${exception}
</c:if>

<div width="100%" align="center">
    <form method="POST" action="${actionLink}" enctype="multipart/form-data">
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
            }

        </script>
        <input type="hidden" size="0" id="x1" name="t"/>
        <input type="hidden" size="0" id="y1" name="l"/>
        <input type="hidden" size="0" id="w" name="w"/>
        <input type="hidden" size="0" id="h" name="h"/>
        <table width="100%" margin-bottom="15px">
            <input type="hidden" name="studieId" value="<%=studie.getId()%>">

            <table width="100%" margin-bottom="15px">
                <tr align="top">
                    <td width="50%" align="center" style="vertical-align: top;">
                        <div id="lup">
                        </div>
                        <% if (studie.getMainImage() == null) { %>
                        <div id="mainPic" style="vertical-align: top; z-index: 2;"
                             style="background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
                            <!-- Output for our douwnload Image-->
                            <input type="file" id="mainImage" name="mainImage"/>
                            <output id="list"></output>
                        </div>
                        <% } else { %>

                        <div id="mainPic" style="vertical-align: top; z-index: 2;">
                            <img id="img" style="vertical-align: top; z-index: 1;" src="${mainImage}"/>
                            <!-- Output for our douwnload Image-->
                            <input type="file" id="mainImage" name="mainImage"/>
                            <output id="list"></output>
                        </div>
                        <% } %>

                        <div id="rdn">
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
                                reader.onload = (function (theFile) {
                                    return function (e) {
                                        // Render thumbnail.
                                        var span = document.createElement('span');
                                        span.innerHTML = ['<img class="thumb" src="', e.target.result,
                                            '" title="', escape(theFile.name), '"/>'].join('');
                                        document.getElementById('list').insertBefore(span, null);
                                    };
                                })(f);
                                document.getElementById('img').parentNode.removeChild(document.getElementById('img'));
                                // Read in the image file as a data URL.
                                reader.readAsDataURL(f);

                            }
                            document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);
                        </script>
                        <br/>

                        <div id="imageLoader">
                            <div id="imgloaderBtn">
                                <input name="images" type="file" id="aui_3_2_0_11607"
                                       accept="image/jpeg,image/png,image/gif"/ multiple>
                                <div id="nt"><spring:message code="form.addPictures"/></div>
                            </div>
                        </div>
                        <br/><br/>

                        <div id="facultetD" style="float: left;">
                            <label style="font-size:16px; font-weight: bold;"><spring:message
                                    code="form.dnevn"/></label> <br/>
                            <c:forEach items="${facultetDnevn}" var="fd">
                                <textarea id="facultetInput" cols="60" rows="1" maxlength="1000" name="facultetDnevn"
                                          style="float: left; margin-top: 15px;">${fd}</textarea>

                            </c:forEach>
                            <input type="button" id="plus1" name="plus1" value="+" onclick="Add()"
                                   style="float: right; margin-top: 18px; margin-left: 3px; "/>
                        </div>
                        <br/> <br/>

                        <div style="float: left;">
                            <br/> <br/>
                            <label style="font-size:16px; font-weight: bold;"><spring:message
                                    code="form.adress"/></label> <br/>
                            <textarea id="adressInput" cols="60" rows="1" maxlength="100" name="adress"
                                      style="float: left; margin-top: 15px;">${adress}</textarea><br/><br/>
                        </div>

                    </td>
                    <td rowspan=2 width="50%">
                        <textarea id="topicInput" cols="90" rows="2" maxlength="80" onkeypress="return isNotMax(event)"
                                  name="topic">${studie.title}
                        </textarea><br/>

                        <div style="width: 450px; float: bottom ;padding-left: 8px;">
                            <textarea class="ckeditor" id="textInput" cols="60" rows="10" maxlength="8000"
                                      onkeypress="return isNotMax(event)" name="text">${studie.text}
                            </textarea><br/><br/>
                        </div>
                        <div id="facultetZ" style="float: left;">
                            <label style="font-size:16px; font-weight: bold;"><spring:message
                                    code="form.zaoch"/></label> <br/>
                            <c:forEach items="${facultetZaoch}" var="fz">
                                <textarea id="facultetInput" cols="60" rows="1" maxlength="1000" name="facultetZaoch"
                                          style="float: left; margin-top: 15px;">${fz}</textarea>

                            </c:forEach>
                            <input type="button" id="plus2" name="plus1" value="+" onclick="Add2()"
                                   style="float: right; margin-top: 18px; margin-left: 3px; "/>
                        </div>
                        <br/> <br/>

                        <div id="sbm">
                            <br/><br/>
                            <input style="vertical-align: central; margin-top: 15px;" type="submit" value="<spring:message
                                             code='<%=(request.isUserInRole("Administrator"))?"form.submit.save"
                                                                                             :"form.submit.user"%>'/>"/>
                        </div>
                        <br/><br/>
                    </td>
                </tr>


            </table>
    </form>
</div>


</script>
<
/body>
< /html>
