<%@ page import="ua.dp.stud.studie.model.Council" %>
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
<%                                                                                                        w
    ImageService imgService = (ImageService) pageContext.findAttribute("imageService");
    Council council = (Council) request.getAttribute("council");
    Integer currentPage = (Integer) request.getAttribute("currentPage");

%>
<html>
 <head> </head>
 <body >
     <portlet:renderURL var="home"><portlet:param name="currentPage" value="<%=currentPage.toString()%>"/>  </portlet:renderURL>
     <div id="singleView">
             <div class="portlet-content-controlpanel fs20">
                 <a href="${home}">
                     <!--<spring:message code="form.back"/>-->
                     <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
                 </a>
                 <%if (request.isUserInRole("Administrator") || request.isUserInRole("User")) { %>
                 <% if (request.isUserInRole("Administrator")) { %>
                 <a style="float: right" href='<portlet:renderURL><portlet:param name="councilID" value="${council.id}"/><portlet:param name="currentPage" value="1"/><portlet:param name="delete" value="council" /></portlet:renderURL>'
                    onclick='return confirm("<spring:message code="form.confDelete"/>")'>
                     <div class="panelbtn panelbtn-right icon-pcpremove" aria-hidden="true"></div>
                     <!--<spring:message code="form.delete"/>-->
                 </a>
                 <%}%>
                 <a style="float: right" href= '<portlet:renderURL><portlet:param name="councilID" value="<%=council.getId().toString()%>"/><portlet:param name="mode" value="edit" /></portlet:renderURL>'>
                     <!--<spring:message code="viewSingle.Edit"/>-->
                     <div class="panelbtn panelbtn-right icon-pcppencil" aria-hidden="true"></div>
                 </a>
                 <%}%>
             </div>
             <div class="councilHeader" style="float:left">
                  <img src="${mainImage}" alt=""/>


             </div>
              <div style="padding-left: 230px; width:20%" >
                              ${council.councilName}
              </div>
             <br/>
             <style>
                .line {

                 border-right: 1px solid red; /* Линия справа от текста */
                 padding: 0 10px;  /* Расстояние между линией и текстом */
                 margin: 0 10%; /* Отступы от края до линии */
                }
               </style>
             <div class="line" style="padding-left: 20px; float: left; width: 20%; overflow: hidden;  border-right: 1px solid red" title="${council.councilContact}">
                 ${council.councilContact}
             </div>

             <div class="councilDescription" style="width: 20%; float: left; padding-left: 10px; overflow: hidden;" title="${council.councilDescription} ">
                 ${council.councilDescription}
             </div>
     </div>
          <div id="Social_networks_Likes">
                 <div id="fb-root"></div>
                 <script>
                     (function(d, s, id) {
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
                     !function(d, s, id) {
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

     <%--commentz support--%>
        <div id="mc-container"></div>
        <script type="text/javascript">

            // init  gallery images
            $(document).ready(function() {

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

            var mcChannel = "${council.id}";

            var mcLocale = "<%= request.getLocale().getLanguage() %>";
            var mcSite = '13747';

            (function() {
                var mc = document.createElement('script');
                mc.type = 'text/javascript';
                mc.async = true;
                mc.src = 'http://cackle.me/mc.widget-min.js';
                (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(mc);
            })();
        </script>
 <%--commentz support--%>
    <div id="mc-container"></div>
    <!--<a href="http://cackle.me" id="mc-link"><spring:message code="viewSingle.copyright"/> <b style="color:#4FA3DA">CACKL</b><b
            style="color:#F65077">E</b></a>-->
    <%--commentz support--%><br/>
    <script>



                function ConfirmImage() {
                    return confirm("<spring:message code="form.confDeleteImg"/>");
                }
                $(function() {
                    //scrollpane parts
                    var scrollPane = $(".scroll-pane"),
                            scrollContent = $(".scroll-content");

                    //build slider
                    var scrollbar = $(".scroll-bar").slider({
                        slide: function(event, ui) {
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
                            .mousedown(function() {
                        scrollbar.width(handleHelper.width());
                    })
                            .mouseup(function() {
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
                    $(window).resize(function() {
                        resetValue();
                        sizeScrollbar();
                        reflowContent();
                    });
                    //init scrollbar size
                    setTimeout(sizeScrollbar, 10);//safari wants a timeout
                });
                $(function() {
                    $('#inner1').tooltip();
                });
            </script>

</body>
</html>


