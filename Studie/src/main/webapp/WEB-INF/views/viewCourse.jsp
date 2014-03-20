<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.liferay.portal.model.Layout" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.ImageImpl" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@include file="include.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<portlet:renderURL var="LinkEditCourse">
            <portlet:param name="id" value="${course.id}"/>
 			<portlet:param name="showEdit" value="course"/>
</portlet:renderURL>

<portlet:actionURL var="LinkDeleteCourse">
            <portlet:param name="id" value="${course.id}"/>
 			<portlet:param name="delete" value="course"/>
</portlet:actionURL>

<portlet:renderURL var="LinkHomeCourse">
    <portlet:param name="view" value="allcourses" />
</portlet:renderURL>'>

 <div class="portlet-content-controlpanel fs20"style="width: 10.15%;float: right;" >
    <a style="float: right" href="${LinkHomeCourse}">
       	<div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
        </a>

  <c:if test='${isShown}'>

    <a style="float: right" href="${LinkDeleteCourse}">
    <div class="panelbtn panelbtn-right icon-pcpremove" aria-hidden="true"></div>
    </a>
    <a style="float: right" href="${LinkEditCourse}">
    <div class="panelbtn panelbtn-right icon-pcppencil" aria-hidden="true"></div>
    </a>

  </c:if>

 </div>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">


               <div  style="float:left">
                  <img style="width:80px" src="${mainImage}" alt=""/>
                  </div>

                  <div style ="padding-left: 140px; font-size: 20px; width:20%;padding-top: 35px;">
                  ${course.courseName}
                  </div>



        <br/>
        <div style="padding-top: 40px;" >
            ${course.coursesContact}
        </div>
        <br/>
        <div id="labels" style="width: 150px; font-size: 12pt; left: 10%;">
                <strong> <spring:message code="More"/>    </strong>
        </div>
        <br/>
            ${course.coursesDescription}
        </div>
        <br/>

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

              var mcChannel = "${course.id}";

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