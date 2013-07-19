<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.Studie" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.Specialties" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.Faculties" %>
<%@ page import="ua.dp.stud.StudPortalLib.service.StudieService" %>
<%@ page import="ua.dp.stud.StudPortalLib.service.impl.StudieServiceImpl" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.theme.PortletDisplay" %>
<%@ page import="com.liferay.portal.model.Layout" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.ImageImpl" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include.jsp" %>

<portlet:defineObjects/>
<%
    Studie studie = (Studie) request.getAttribute("studie");
    int buttonId = 0;
    if (request.getParameter("buttonId") != null) {
        buttonId = Integer.parseInt(request.getParameter("buttonId"));
    }
    String temp;
    String[] type = {"studie", "StudentCouncil", "schedule", "Courses", "OnlineCourses", "grants"};
    Collection<ImageImpl> additionalImages = (Collection<ImageImpl>) request.getAttribute("additionalImages");
%>
<html>
<head>
    <style type="text/css">
        .fancybox-custom .fancybox-skin {
            box-shadow: 0 0 50px #222;
        }

    </style>
    <script>
        function ConfirmImage() {
            return confirm("<spring:message code="form.confDeleteImg"/>");
        }
        function showHide(divId, plusik) {
            if (document.getElementById(divId).style.display == 'block') {
                document.getElementById(divId).style.display = 'none';
                document.getElementById(plusik).innerHTML = '+';
            }
            else {
                document.getElementById(divId).style.display = 'block';
                document.getElementById(plusik).innerHTML = '-';
            }


        }
		$(document).ready(function () {
        $('.splLink').click(function () {
            $(this).parent().children('div.splCont').toggle('normal');
            $(this).children('span#spoiler').html(($(this).children('span#spoiler').html() == "+") ? "-" : "+");
            return false;
        });
    });
    </script>
</head>

<body>
<portlet:renderURL var="home"> </portlet:renderURL>
<div class="portlet-content-controlpanel fs20">
        <a href="${home}">
            <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
        </a>
        <%if (request.isUserInRole("Administrator") || request.isUserInRole("User")) { %>
        <% if (request.isUserInRole("Administrator")) { %>
		<portlet:renderURL var="deleteLink">
			<portlet:param name="studieId" value="<%=studie.getId().toString()%>"/>
			<portlet:param name="mode" value="delete"/>
		</portlet:renderURL>
        <a style="float: right" href="${deleteLink}"
           onclick='return confirm("<spring:message code="form.confDelete"/>")'>
            <div class="panelbtn panelbtn-right icon-pcpremove" aria-hidden="true"></div>
        </a>
        <%}%>
		<portlet:renderURL var="editLink">
			<portlet:param name="studieId" value="<%=studie.getId().toString()%>"/>
			<portlet:param name="mode" value="edit"/>
		</portlet:renderURL>
        <a style="float: right" href="${editLink}">
            <div class="panelbtn panelbtn-right icon-pcppencil" aria-hidden="true"></div>
        </a>
        <%}%>
    </div>
<div style="margin-left:-11px;" align="left" class="cmt-types">

    <% for (int i = 0; i < 6; i++) {
        if (buttonId == i) {
            temp = new String("form.".concat(type[i]));%>
    <div class="ribbon-wrapper">
        <button class="btnselected"
                style=" width: 150px; height: 40px;  margin-left: -10px;  background-color: rgba(0, 122, 255, 0.47); border-color: rgba(68, 115, 185, 0);"
                name="buttonId" value="<%=i%>">
            <spring:message code="<%=temp%>"/></button>
        <div class="ribbon-edge-topleft"></div>
        <div class="ribbon-edge-bottomleft"></div>
    </div>
    <%
    } else {
        temp = new String("form.".concat(type[i]));
    %>
    <div class="ribbon-wrapper">
        <button class="btntype"
                style=" width: 150px; height: 40px;  background:  #4473B9; margin-left: -10px;  border-color: #4473B9;"
                name="buttonId" value="<%=i%>">
            <spring:message code="<%=temp%>"/></button>
        <div class="ribbon-edge-topleft"></div>
        <div class="ribbon-edge-bottomleft"></div>
    </div>
    <%}%>
    <br/>
    <% }%>
</div>

<div id="singleView">
	<table width="100%"><tbody><tr>
		<td style="width: 110px;"><img src="${mainImage}" alt="" id="headImage"/></td>
		<td><h1>${studie.title}</h1></td>
		<tr></tbody></table>
	<div id="mainProp">
		<div style="width: 300px; float: right;"><ul>
			<li><spring:message code="studie.FreeTrainig"/>&nbsp;<%=studie.getFreeTrainig()%></li>
			<li><spring:message code="studie.PaidTrainig"/>&nbsp;<%=studie.getPaidTrainig()%></li>
			<li><spring:message code="studie.MilitaryDepartment"/>&nbsp;<%=studie.getMilitaryDepartment()%></li>
			<li><spring:message code="studie.Hostel"/>&nbsp;<%=studie.getHostel()%></li>
			<li><spring:message code="studie.PostgraduateEducation"/>&nbsp;<%=studie.getPostgraduateEducation()%></li>
			<li><spring:message code="studie.PostgraduateAndDoctoralStudies"/>&nbsp;<%=studie.getPostgraduateAndDoctoralStudies()%></li>
			<li><spring:message code="studie.PreparatoryDepartment"/>&nbsp;<%=studie.getPreparatoryDepartment()%></li>
			<li><spring:message code="studie.CountOfStudents"/>&nbsp;<%=studie.getCountOfStudents()%></li>
			<li><spring:message code="studie.CountOfTeachers"/>&nbsp;<%=studie.getCountOfTeachers()%></li>
			<li><spring:message code="studie.CountOfCandidates"/>&nbsp;<%=studie.getCountOfCandidates()%></li>
			<li><spring:message code="studie.CountOfProfessors"/>&nbsp;<%=studie.getCountOfProfessors()%></li>
		<ul></div>
		<div style="width: 480px;"><ul>
			<li><spring:message code="studie.city"/>&nbsp;<%=studie.getCity()%></li>
			<li><spring:message code="studie.years"/>&nbsp;<%=studie.getYears()%></li>
			<li><spring:message code="studie.status"/>&nbsp;<%=studie.getStatus()%></li>
			<li><spring:message code="studie.accreditacion"/>&nbsp;<%=studie.getAccreditacion()%></li>
			<li><spring:message code="studie.city"/>&nbsp;<%=studie.getCity()%></li>
			<li><spring:message code="studie.FormOfTraining"/>&nbsp;<%=studie.getFormOfTraining()%></li>
			<li><spring:message code="studie.QualificationLevel"/>&nbsp;<%=studie.getQualificationLevel()%></li>
			<li><spring:message code="studie.Adress"/>&nbsp;<%=studie.getAdress()%></li>
			<li><spring:message code="studie.Phone"/>&nbsp;<%=studie.getPhone()%></li>
			<li><spring:message code="studie.PhoneAdmissions"/>&nbsp;<%=studie.getPhoneAdmissions()%></li>
			<li><spring:message code="studie.Website"/>&nbsp;<a href="<%=studie.getWebsite()%>" target="_blank"><%=studie.getWebsite()%></a></li>
		<ul></div>
	</div>
	<br><h2><spring:message code="studie.facul"/></h2>
	<%	Collection<Faculties> fuckList = studie.getFaculties();
		if (fuckList != null) {
		for(Faculties fuck : fuckList) {%>
	<div>
		<div class="splLink"><span id="spoiler" style="position: absolute;">+</span><h3 style="margin-left: 15px;"><%=fuck.getNameOfFaculties()%></h3></div>
		<div class="splCont" style="display:none;"><ul>
			<%	Collection<Specialties> specList = fuck.getSpecialties();
				if (specList != null) {
				for(Specialties spec : specList) {%>
				<li><%=spec.getNameOfSpecialties()%></li>
			<%}}%>
		</ul></div>
	</div>
	<%}}%>
	<br><h2><spring:message code="studie.enrollees"/></h2>
    <div class="newsText">${studie.enrollees}</div>
	<br><h2><spring:message code="studie.Text"/></h2>
	<div class="newsText">${studie.text}</div>
	
	<%if (additionalImages != null) {%>
	<h2><spring:message code="form.photo"/></h2>
	<div class="image_carousel" style="width: 770px">
		<a href="javascript:" class="carousel-control next pagination-right" rel="next"></a>
		<a href="javascript:" class="carousel-control prev pagination-left" rel="prev"></a>

		<div class="middle" style="width: 688px;">
			<div class="singleGelery" id="inner">
				<%for (ImageImpl image : additionalImages) {
						if (!image.equals(studie.getMainImage())) {%>
				<div class="ownGelery" style="margin-left: 5px;">
					<a class="fancybox-thumbs" data-fancybox-group="thumb"
					   href="<%=imageService.getPathToLargeImage(image, studie) %>"
					   <% if (request.isUserInRole("Administrator")) { %>title='<a href="<portlet:renderURL/>&imageId=<%=image.getId()%>&mode=delImage" onclick="return ConfirmImage()"> <spring:message code="form.delete"/></a>'<%}%>>
						<img src="<%=imageService.getPathToSmallImage(image, studie) %>" alt=""/>
					</a>
				</div>
				<%}}%>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(window).load(function () {
			var size = 0;
			$('.ownGelery').each(function () {
				size = $(this).find('img').height();
				$(this).width($(this).find('img').width());
				$(this).height($(this).find('img').height());
			});
			$(".next").height(size);
			$(".prev").height(size);
			$(".middle").height(size);
			$(".image_carousel").height(size);
			$("#inner").carouFredSel({
				circular: false,
				infinite: true,
				auto: false,
				prev: {
					button: ".prev",
					key: "left"
				},
				next: {
					button: ".next",
					key: "right"
				}
			});
		});
	</script>
	<%}%>
</div>

<br/><br/>

<div id="underline-decoration" align="center">
    <table>
        <tr>
            <td width="60">
                <img width="60" class="newsDecorImage"
                     src="${pageContext.request.contextPath}/images/news-decor-line-left-end.png"/>
            </td>
            <td width="auto" align="left">
                <img width="100%" class="newsDecorImage"
                     src="${pageContext.request.contextPath}/images/news-decor-line.png"/>
            </td>
            <td width="52">
                <img width="52" class="newsDecorImage"
                     src="${pageContext.request.contextPath}/images/news-decor-center.png"/>
            </td>
            <td width="auto" align="right">
                <img width="100%" class="newsDecorImage"
                     src="${pageContext.request.contextPath}/images/news-decor-line.png"/>
            </td>
            <td width="50">
                <img width="50" class="newsDecorImage"
                     src="${pageContext.request.contextPath}/images/news-decor-line-right-end.png"/>
            </td>
        </tr>
    </table>
</div>
<%--commentz support--%>
<!--div id="mc-container"></div-->
<script type="text/javascript">

    // init  gallery images
    $(document).ready(function () {

        $('.fancybox-thumbs').fancybox({
            prevEffect: 'none',
            nextEffect: 'none',
            'overlayShow': true,
            'overlayColor': '#000',
            'overlayOpacity': 0.8,

            closeBtn: true,
            arrows: true,
            nextClick: true,

            helpers: {
                thumbs: {},
                title: {
                    type: 'inside'
                }
            }
        });
    });
    var mcChannel = "${news.id}";
    // will return en   or   ru
    var mcLocale = "<%= request.getLocale().getLanguage() %>";
    var mcSite = '13747';

    (function () {
        var mc = document.createElement('script');
        mc.type = 'text/javascript';
        mc.async = true;
        mc.src = 'http://cackle.me/mc.widget-min.js';
        (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(mc);
    })();
</script>

<br/><br/>
</body>
</html>