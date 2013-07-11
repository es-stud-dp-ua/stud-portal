<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.Studie" %>
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
    String[] type = {"studie", "entry", "helps", "informal", "scholarships", "grants"};
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
    </script>
</head>

<body>
<portlet:renderURL var="home"> </portlet:renderURL>
<br/> <br/>

<div style="margin-left:-11px; margin-right: 20px" align="left" class="cmt-types">

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

    <%if (request.isUserInRole("Administrator") || request.isUserInRole("User")) { %>
    <a style="float: right; margin-right: 40px;" href="<portlet:renderURL/>&studieId=<%=studie.getId()%>&mode=edit">
        <spring:message code="viewSingle.Edit"/>
    </a>
    <%}%>

    <% if (request.isUserInRole("Administrator")) { %>
    <div style="margin-right: 16px; float: right">
        <a style="float: right" href="<portlet:renderURL/>&studieId=<%=studie.getId()%>&mode=delete"
           onclick='return confirm("<spring:message code="form.confDelete"/>")'>
            <spring:message code="form.delete"/>
        </a>

    </div>

    <%}%>
    <a style="float: right; margin-right: 16px;" href="${home}"><spring:message code="form.back"/></a>
    <br/>

    <div class="newsHeader">
        <img src="${mainImage}" alt=""/>
        ${studie.title}
    </div>
    <br/>

    <div class="newsText">
        ${studie.text}
    </div>

    <br/>
</div>
<%
    if (additionalImages != null) {
%>
<div style="text-align: center; font-weight: bold; font-size: 16px;"><spring:message code="form.photo"/></div>
<div class="image_carousel" style="width: 639px">
    <a href="javascript:" class="carousel-control next pagination-right" rel="next"></a>
    <a href="javascript:" class="carousel-control prev pagination-left" rel="prev"></a>

    <div class="middle" style="width: 557px;">
        <div class="singleGelery" id="inner">
            <%
                for (ImageImpl image : additionalImages) {
                    if (!image.equals(studie.getMainImage())) {
            %>
            <div class="ownGelery" style="margin-left: 5px;">
                <a class="fancybox-thumbs" data-fancybox-group="thumb"
                   href="<%=imageService.getPathToLargeImage(image, studie) %>"
                   <% if (request.isUserInRole("Administrator")) { %>title='<a href="<portlet:renderURL/>&imageId=<%=image.getId()%>&mode=delImage" onclick="return ConfirmImage()"> <spring:message code="form.delete"/></a>'<%}%>>
                    <img src="<%=imageService.getPathToSmallImage(image, studie) %>" alt=""/>
                </a>
            </div>
            <%
                    }
                }
            %>
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
<br/><br/>
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

<label onclick="showHide('spisok','plusik');" style="font-size:16px; font-weight: bold; "><spring:message
        code="form.dnevnforma"/></label>
<label id="plusik" onclick="showHide('spisok','plusik');" style="font-size:18px; font-weight: bold; ">+</label>

<div id="spisok" style="display:none">
    <ul>
        <c:forEach items="${facultetDnevn}" var="fd">
            <li style="font-size:14px;">
                -- ${fd}
            </li>
        </c:forEach>
    </ul>
</div>
<br/>
<label onclick="showHide('spisok2','plusik2');" style="font-size:16px; font-weight: bold; "><spring:message
        code="form.zaochforma"/></label>
<label id="plusik2" onclick="showHide('spisok2','plusik2');" style="font-size:18px; font-weight: bold; ">+
</label>

<div id="spisok2" style="display:none">
    <ul>
        <c:forEach items="${facultetZaoch}" var="fz">
            <li style="font-size:14px;">
                -- ${fz}
            </li>
        </c:forEach>
    </ul>
</div>
<br/> <br/>

<p style="font-size:14px;">${adress}</p>

<br/><br/>

<div style="font-size:14px;">
    <spring:message code="form.show"/>&nbsp; <span style="font-size:14px; font-weight: bold">${studie.title}</span>
    &nbsp;
    <spring:message code="form.on"/>
    <a href=""><spring:message code="form.map"/></a>
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