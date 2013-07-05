<%@ page import="ua.dp.stud.bannerPortlet.model.BannerImage" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ page import="java.util.Collection" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="include.jsp"%>
<!--todo: javascript to separate file move you should-->

<portlet:defineObjects/>
<%
    Collection<BannerImage> bannerImages =
            (Collection<BannerImage>) request.getAttribute("bannerImages");
    ImageService imageService = (ImageService)pageContext.findAttribute("imageService");
    Locale locale = (Locale) request.getSession().getAttribute("org.apache.struts.action.LOCALE");
    String language = locale.getLanguage();
    String country = locale.getCountry();
    ResourceBundle res = ResourceBundle.getBundle("messages", new Locale(language, country));
%>
<liferay-ui:error key="error.no_images" message='<%=res.getString("msg.wrong.no_images")%>'></liferay-ui:error>
<liferay-ui:error key="error.dplBanner" message='<%=res.getString("msg.wrong.dplBanner")%>'></liferay-ui:error>
<liferay-ui:error key="error.toLow" message='<%=res.getString("msg.wrong.toLow")%>'></liferay-ui:error>
<liferay-ui:error key="error.unknown" message='<%=res.getString("msg.wrong.unknown")%>'></liferay-ui:error>

<portlet:renderURL var="home"> </portlet:renderURL>
<portlet:actionURL var="actionLink" name="updateImage"></portlet:actionURL>
<html>
<head>
</head>
<body>
<div style="width: 80%; margin-left: 10%; margin-right: 10%">
    <div class="portlet-content-controlpanel fs20" style="margin-top: -12px;!important"> 
        <a href="${home}">
	<div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
	</a>
    </div>
        <%if(bannerImages.isEmpty()){%>
        <b><spring:message code="noImages"/></b><br>
        <%}else{%>
        <spring:message code="currentImages"/><br>
            <%for(BannerImage image:bannerImages){%>
            <form method="post" class="banner-form"<%=image.getId()%>" action='${actionLink}' enctype="multipart/form-data">
                <input type="hidden" name="id" value="<%=image.getId()%>">
                <table width="100%">
                    <tr>
                        <td>
                            <div><label cssClass="control-group" for="ban-mainImage"><spring:message code="form.chooseImage"/></label></div>
                            <input type="file" id="ban_mainImage" name="ban_mainImage" accept="image/jpeg,image/png"/>
                            <img style="width: 100px" src="<%= imageService.getPathToLargeImage(image.getMainImage(),image) %>"  />
                            <div><label for="ban_mainImage" generated="true" class="error" style=""></label></div>
                        </td>
                        <td>
                            <div><label cssClass="control-group" for="url"><spring:message code="form.url"/></label></div>
                            <input type="text" id="url" name="url" value="<%=image.getUrl()%>">
                            <div><label for="url" generated="true" class="error" style=""></label></div>
                        </td>
                        <td width="75"><button style="margin: 5px" type="submit"><spring:message code="btn.update"/></button></td>
                        <td width="75"><a href="<portlet:renderURL/>&imgId=<%=image.getId()%>&mode=delete" onclick='return confirm("<spring:message code="form.confDelete"/>")'><div class="panelbtn panelbtn-right fs20 icon-pcpremove" aria-hidden="true" ></div></a></td>
                    </tr>
                </table>
            </form>
            <script type="text/javascript">
            	$(document).ready(function () {
            		$('.banner-form<%=image.getId()%>').validate({
            			rules: {
            				ban_mainImage: {
            					accept: "jpg|jpeg|png"
            				},
            				url: {
            					required:true,
            					url: true
            				}
            			},
            			messages: {
            				ban_mainImage: {
            					accept: "<spring:message code="messages.mainImage.accept"/>"
            				},
            				url: {
            					required:"<spring:message code="messages.url.required"/>",
            					url: "<spring:message code="messages.url.url"/>"
            				}
            			},
            			highlight:function (label) {
            				$(label).closest('.control-group').addClass('error');
            			}
            		});
            	});
            </script>
            <%}%>
        <%}%>
    </div>
</body>
</html>
