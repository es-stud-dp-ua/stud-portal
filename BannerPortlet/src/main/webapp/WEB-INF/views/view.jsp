<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ page import="ua.dp.stud.bannerPortlet.model.BannerImage" %>
<%@include file="include.jsp"%>

<portlet:defineObjects/>
<%
    Collection<BannerImage> bannerImages =
            (Collection<BannerImage>) request.getAttribute("bannerImages");
    ImageService imageService = new ImageService();
    //todo: remove all stuff about locale
    Locale locale = (Locale) request.getSession().getAttribute("org.apache.struts.action.LOCALE");
    String language = locale.getLanguage();
    String country = locale.getCountry();
    ResourceBundle res = ResourceBundle.getBundle("messages", new Locale(language, country));
%>

<html>
<head>
	<script src="${pageContext.request.contextPath}/js/banner.js"></script>
</head>
<body>
<liferay-ui:success message='<%=res.getString("msg.successAdd")%>' key="success"/>
<div style="width: 100%">
	<% if(request.isUserInRole("Administrator")){%>
            <div style="margin-right: 450px;">
                <a href="<portlet:renderURL/>&mode=add"><div class="panelbtn panelbtn-right fs20 icon-pcpplus" aria-hidden="true"></div></a>&nbsp;&nbsp;&nbsp;
                <a href="<portlet:renderURL/>&mode=edit"><div class="panelbtn panelbtn-right fs20 icon-pcppencil" aria-hidden="true"></div></a>
            </div>
            <%}%>
    <div align="center">
		<img id="left-top" src="${pageContext.request.contextPath}/images/frame2.png">
		<img id="left-bottom" src="${pageContext.request.contextPath}/images/frame1.png">
		<img id="right-top" src="${pageContext.request.contextPath}/images/frame1.png">
		
		<% if (!bannerImages.isEmpty()) {%>
		<div  class="banner">
			
			<div align="left" class="images">
				<%for(BannerImage image:bannerImages){%>
				<a href="<%=image.getUrl()%>">
                        <img src="<%= imageService.getPathToLargeImage(image.getMainImage(),image) %>" >
                    </a>
				<%}%>				
			</div>
			<div class="pagination">
				<ul>
				</ul>
			</div>
		</div>
		<% }%>

    </div>

</div>
</body>
</html>
