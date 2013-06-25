<%@ page import="ua.dp.stud.bannerPortlet.model.BannerImage" %>
<%@ page import="java.util.Collection" %>
<%@include file="include.jsp"%>

<portlet:renderURL var="home"> </portlet:renderURL>
<portlet:actionURL var="actionLink" name="addImage"></portlet:actionURL>

<html>
<head>
</head>
<body>
<div style="width: 80%; margin-left: 10%; margin-right: 10%">
    <div class="portlet-content-controlpanel fs20">
        <a href="${home}"><div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div></a>
    </div>
    <br>
     <!--todo:format code, change repeatable id, move javascript to separate file-->
    <div style="" align="center">
        <form method="POST" class="banner-form" action="${actionLink}" enctype="multipart/form-data" style="height: 213px;">
            <div align="left"><b><spring:message code="addImage"/></b><br></div>
			<div>
				<div id="block">
					<div><label cssClass="control-group" for="ban-mainImage"><spring:message code="form.chooseImage"/></label></div>
					<input type="file" id = "ban-mainImage" name="ban-mainImage" accept="image/jpeg,image/png"/>
					<div><label for="ban-mainImage" generated="true" class="error" style=""></label></div>
				</div>
				<div id="block">
					<div><label cssClass="control-group" for="url"><spring:message code="form.url"/></label></div>
					<input type="text" id="url" name="url">
					<div><label for="url" generated="true" class="error" style=""></label></div>
				</div>
			</div>
			<input type="submit" value=<spring:message code="btn.add"/>>
        </form>
    </div>
    <br><br>
</div>
<script type="text/javascript">
	$(document).ready(function () {
		$('.banner-form').validate({
			rules: {
				ban-mainImage: {
					accept: "jpg|jpeg|png",
					required:true
				},
				url: {
					required:true,
					url: true
				}
			},
			messages: {
				ban-mainImage: {
					accept: "<spring:message code="messages.mainImage.accept"/>",
					required:"<spring:message code="messages.mainImage.required"/>"
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
</body>
</html>
