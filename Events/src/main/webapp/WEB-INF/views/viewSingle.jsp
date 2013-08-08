<%@ page import="ua.dp.stud.StudPortalLib.model.Events" %>
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
    Events event = (Events) request.getAttribute("event");
    Collection<ImageImpl> additionalImages = (Collection<ImageImpl>) request.getAttribute("additionalImages");
%>
<html>
<head>
    <style type="text/css">
             label.valid {
            width: 24px;
            height: 24px;
            background: url(${pageContext.request.contextPath}/images/valid.png) center center no-repeat;
            display: inline-block;
            text-indent: -9999px;
        }
    </style>
    <script>
        function ConfirmImage() {
            return confirm("<spring:message code="form.confDeleteImg"/>");
        }
        $(function () {
            //scrollpane parts
            var scrollPane = $(".scroll-pane"),
                    scrollContent = $(".scroll-content");

            //build slider
            var scrollbar = $(".scroll-bar").slider({
                slide: function (event, ui) {
                    if (scrollContent.width() > scrollPane.width()) {
                        scrollContent.css("margin-left", Math.round(
                                ui.value / 100 * (scrollPane.width() - scrollContent.width())
                        ) + "px");
                    } else {
                        scrollContent.css("margin-left", 0);
                    }
                }
            });

            //append icon to handle
            var handleHelper = scrollbar.find(".ui-slider-handle")
                    .mousedown(function () {
                        scrollbar.width(handleHelper.width());
                    })
                    .mouseup(function () {
                        scrollbar.width("100%");
                    })
                    .append("<span class='ui-icon ui-icon-grip-dotted-vertical'></span>")
                    .wrap("<div class='ui-handle-helper-parent'></div>").parent();

            //change overflow to hidden now that slider handles the scrolling
            scrollPane.css("overflow", "hidden");

            //size scrollbar and handle proportionally to scroll distance
            function sizeScrollbar() {
                var remainder = scrollContent.width() - scrollPane.width();
                var proportion = remainder / scrollContent.width();
                var handleSize = scrollPane.width() - (proportion * scrollPane.width());
                scrollbar.find(".ui-slider-handle").css({
                    width: handleSize,
                    "margin-left": -handleSize / 2
                });
                handleHelper.width("").width(scrollbar.width() - handleSize);
            }

            //reset slider value based on scroll content position
            function resetValue() {
                var remainder = scrollPane.width() - scrollContent.width();
                var leftVal = scrollContent.css("margin-left") === "auto" ? 0 :
                        parseInt(scrollContent.css("margin-left"));
                var percentage = Math.round(leftVal / remainder * 100);
                scrollbar.slider("value", percentage);
            }

            //if the slider is 100% and window gets larger, reveal content
            function reflowContent() {
                var showing = scrollContent.width() + parseInt(scrollContent.css("margin-left"), 10);
                var gap = scrollPane.width() - showing;
                if (gap > 0) {
                    scrollContent.css("margin-left", parseInt(scrollContent.css("margin-left"), 10) + gap);
                }
            }

            //change handle position on window resize
            $(window).resize(function () {
                resetValue();
                sizeScrollbar();
                reflowContent();
            });
            //init scrollbar size
            setTimeout(sizeScrollbar, 10);//safari wants a timeout
        });
        $(function () {
            $('#inner1').tooltip();
        });
    </script>
</head>

<body>
<portlet:renderURL var="home"> </portlet:renderURL>
<div id="singleView">
    <div class="portlet-content-controlpanel fs20">
        <a href="${home}">
            <!--<spring:message code="form.back"/>-->
            <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
        </a>
            <%if (request.isUserInRole("Administrator") || request.isUserInRole("User")) { %>
        <% if (request.isUserInRole("Administrator")) { %>
        <a style="float: right" href="<portlet:renderURL/>&eventID=<%=event.getId()%>&mode=delete"
           onclick='return confirm("<spring:message code="form.confDelete"/>")'>
            <div class="panelbtn panelbtn-right icon-pcpremove" aria-hidden="true"></div>
            <!--<spring:message code="form.delete"/>-->
        </a>
        <%}%>
        <a style="float: right" href="<portlet:renderURL/>&eventId=<%=event.getId()%>&mode=edit">
            <!--<spring:message code="viewSingle.Edit"/>-->
            <div class="panelbtn panelbtn-right icon-pcppencil" aria-hidden="true"></div>
        </a>
            <%}%>
    </div>
    <div class="newsHeader">
        <img src="${mainImage}" alt=""/>
        ${event.title}
    </div>
    <br/>

    <div class="newsText">
        ${event.text}
    </div>
    <div class="reply_link_wrap">
        <span class="rel_author">${event.author}</span>
        <span class="rel_view">${event.views}</span>
        <span class="rel_date"><%=CustomFunctions.getCreationDate(event.getPublication())%></span>
    </div>
</div>
<br/>
<%
    boolean carusel = false;
    if (additionalImages != null) {
        carusel = true;
%>
<div style="text-align: center; font-weight: bold; font-size: 16px; margin-top: 200px;"><spring:message code="form.photo"/></div>
<div class="image_carousel" style="width: 639px; margin-left: 120px;">
    <a href="javascript:" class="carousel-control next pagination-right right" rel="next"></a>
    <a href="javascript:" class="carousel-control prev pagination-left left" rel="prev"></a>

    <div class="middle" style="width: 557px;">
        <div class="singleGelery" id="inner">
            <%
                for (ImageImpl image : additionalImages) {
                    if (!image.equals(event.getMainImage())) {
            %>
            <div class="ownGelery" style="margin-left: 5px;">
                <a class="fancybox-thumbs" data-fancybox-group="thumb"
                   href="<%=imageService.getPathToLargeImage(image, event) %>"
                   <% if (request.isUserInRole("Administrator")) { %>title='<a href="<portlet:renderURL/>&imageId=<%=image.getId()%>&mode=delImage" onclick="return ConfirmImage()"> <spring:message code="form.delete"/></a>'<%}%>>
                    <img src="<%=imageService.getPathToSmallImage(image, event) %>" alt=""/>
                </a>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>
</div>
<%}%>
<div id="Social_networks_Likes">
    <div id="fb-root"></div>
    <script>
        (function (d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id))
                return;
            js = d.createElement(s);
            js.id = id;
            js.src = "//connect.facebook.net/ru_RU/all.js#xfbml=1";
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));
    </script>
    <div class="fb-like" data-send="true" data-width="450" data-show-faces="true" data-font="arial"></div>
    <a href="https://twitter.com/share" class="twitter-share-button">Tweet</a>
    <script>
        !function (d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (!d.getElementById(id)) {
                js = d.createElement(s);
                js.id = id;
                js.src = "//platform.twitter.com/widgets.js";
                fjs.parentNode.insertBefore(js, fjs);
            }
        }(document, "script", "twitter-wjs");

    </script>
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
<a href="http://cackle.me" id="mc-link"><spring:message code="viewSingle.copyright"/> <b style="color:#4FA3DA">CACKL</b><b
        style="color:#F65077">E</b></a>
<%--commentz support--%>
<br/><br/>
</body>
</html>