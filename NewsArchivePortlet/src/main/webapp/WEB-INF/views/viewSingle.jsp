<%@ page import="ua.dp.stud.StudPortalLib.model.News" %>
<%@ page import="com.liferay.portal.model.Layout" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.ImageImpl" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@include file="include.jsp" %>

<portlet:defineObjects/>
<%
    ImageService imgService = (ImageService) pageContext.findAttribute("imageService");
    News news = (News) request.getAttribute("news");

    Collection<ImageImpl> additionalImages = (Collection<ImageImpl>) request.getAttribute("additionalImages");
%>
<html>
<head>
    <script>
        function ConfirmImage() {
            return confirm("<spring:message code="form.confDeleteImg"/>");
        }
        <% if (additionalImages != null) {%>
        $(window).load(function () {
            var size = 0;
            var maxSize = 0;
            $('.ownGelery').each(function () {
                size = $(this).find('img').height();
                if (maxSize < size)
                    maxSize = size;
                $(this).width($(this).find('img').width());
                $(this).height($(this).find('img').height());
            });
            $(".next").height(maxSize);
            $(".prev").height(maxSize);
            $(".middle").height(maxSize);
            $(".image_carousel").height(maxSize);
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
            $('.ajax-loader').hide();
            $('.image_carousel').css("left", "0px");
        });
        <%}%>
        (function (d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s);
            js.id = id;
            js.src = "//connect.facebook.net/ru_RU/all.js#xfbml=1";
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));
        !function (d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (!d.getElementById(id)) {
                js = d.createElement(s);
                js.id = id;
                js.src = "//platform.twitter.com/widgets.js";
                fjs.parentNode.insertBefore(js, fjs);
            }
        }(document, "script", "twitter-wjs");
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
</head>

<body>
<portlet:renderURL var="home">
    <portlet:param name="nAction" value="home"/>
</portlet:renderURL>
<div id="singleView">
    <div class="portlet-content-controlpanel fs20">
        <a href="${home}">
            <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
        </a>
        <%if (request.isUserInRole("Administrator") || request.isUserInRole("User")) { %>
        <% if (request.isUserInRole("Administrator")) { %>
        <a style="float: right"
           href="<portlet:renderURL><portlet:param name="newsId" value="<%=news.getId().toString()%>"/><portlet:param name="mode" value="delete" /></portlet:renderURL>"
           onclick='return confirm("<spring:message code="form.confDelete"/>")'>
            <div class="panelbtn panelbtn-right icon-pcpremove" aria-hidden="true"></div>
        </a>
        <%}%>
        <a style="float: right"
           href="<portlet:renderURL><portlet:param name="newsId" value="<%=news.getId().toString()%>"/><portlet:param name="mode" value="edit" /></portlet:renderURL>">
           <div class="panelbtn panelbtn-right icon-pcppencil" aria-hidden="true"></div>
        </a>
        <%}%>
    </div>
    <div class="newsHeader">${news.topic}</div>
    <div class="reply_link_wrap" style="text-align: center;">
        <span class="rel_author">${news.author}</span>
        <span class="rel_view">${news.numberOfViews}</span>
        <span class="rel_date"><%=CustomFunctions.getCreationDate(news.getPublication())%></span>
    </div>
    <img src="${mainImage}" alt="" class="mainImage"/>

    <div class="newsText">
        ${news.text}
    </div>
</div>
<c:if test="${additionalImages != null}">
    <div class="ajax-loader" style="margin-top: 30px; width: 639px; height: 21px;"></div>
    <div class="image_carousel" style="width: 639px; left: -10000px;">
        <a href="javascript:" class="carousel-control next pagination-right" rel="next"></a>
        <a href="javascript:" class="carousel-control prev pagination-left" rel="prev"></a>

        <div class="middle" style="width: 557px;">
            <div class="singleGelery" id="inner">
                <%for (ImageImpl image : additionalImages) {%>
                <div class="ownGelery" style="margin-left: 5px;">
                    <a class="fancybox-thumbs" data-fancybox-group="thumb"
                       href="<%=imgService.getPathToLargeImage(image, news) %>"
                       <% if (request.isUserInRole("Administrator")) { %>title='<a href="<portlet:renderURL><portlet:param name="imageId" value="<%=image.getId().toString()%>"/><portlet:param name="mode" value="delImage" /></portlet:renderURL>" onclick="return ConfirmImage()"> <spring:message code="form.delete"/></a>'<%}%>>
                        <img src="<%=imgService.getPathToSmallImage(image, news) %>" alt=""/>
                    </a>
                </div>
                <%}%>
            </div>
        </div>
    </div>
</c:if>
<br><br>

<div id="Social_networks_Likes">
    <div id="fb-root"></div>
    <div class="fb-like" data-send="true" data-width="450" data-show-faces="true" data-font="arial"></div>
    <a href="https://twitter.com/share" class="twitter-share-button">Tweet</a>
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
<div id="mc-container"></div>
<!--<a href="http://cackle.me" id="mc-link"><spring:message code="viewSingle.copyright"/> <b style="color:#4FA3DA">CACKL</b><b
        style="color:#F65077">E</b></a>-->
<%--commentz support--%>
<br/>
</body>
</html>
