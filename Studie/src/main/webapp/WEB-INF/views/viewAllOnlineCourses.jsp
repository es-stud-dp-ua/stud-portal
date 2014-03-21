<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="ua.dp.stud.studie.model.OnlineCourse" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page isELIgnored="false" %>
<%@include file="include.jsp" %>
<%@page import="java.util.Arrays"%>
<%@include file="leftBar.jsp" %>

<% ImageService imageServices = (ImageService) pageContext.findAttribute("imageService"); %>


<%@ taglib prefix="theme" uri="http://liferay.com/tld/theme" %>
<%
	String[] name = (String[])request.getAttribute("names");
    Collection<OnlineCourse> courses = (Collection) request.getAttribute("onlineCourses");
    int pagesCount = (Integer) request.getAttribute("pagesCount");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    //todo: remove unused variables
    int nearbyPages = (Integer) request.getAttribute("nearbyPages"); //number of pages to show to left and right of current
    int overallPages = (Integer) request.getAttribute("overallPages"); //overall number of pages
    int leftPageNumb = (Integer) request.getAttribute("leftPageNumb"),
            rightPageNumb = (Integer) request.getAttribute("rightPageNumb");
    boolean skippedBeginning = (Boolean) request.getAttribute("skippedBeginning"),
            skippedEnding = (Boolean) request.getAttribute("skippedEnding");

%>

<html>
<head>
  <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

</head>
<body>
<div >


<portlet:actionURL var="search" name="searchOnlineCourses"></portlet:actionURL>
  <div style="float: left;">
          <form:form action="${search}" style="display: inline;" >
                   <spring:message code="course.Search" />
  				   <input type="text" id="tags" name="title" style="height: 16px;">
          </form:form>
		  <portlet:actionURL var="gotype" name="getOnlineCoursesByType"></portlet:actionURL>
		  <form:form id="typeForm" action="${gotype}" style="display: inline; margin-left: 10px;" >
					<select onchange="this.form.submit()" name="type" style="width: 170px">
						<option selected="selected"><spring:message code="course.SelectCourseType" /></option>
					    <c:forEach var="postProfile" items="${onlineCourseTypes}">
					    <option value="<c:out value="${postProfile.id}" />">
					        <c:out value="${postProfile.kindOfCourse}" />
					    </option>
					    </c:forEach>
					</select>	  
	         </form:form>
          <%if (request.isUserInRole("Administrator")){ %>
		  <portlet:renderURL var="categories">
                <portlet:param name="view" value="onlineCoursesCategories"/>
          </portlet:renderURL>

				 <a href="${categories}" id='changeBut' class="icon-pcppencil fs20" aria-hidden="true"></a>
		    
		       <portlet:renderURL var="LinkAddCourse">
       				 <portlet:param name="add" value="onlineCourse"/>
    		   </portlet:renderURL></div>
    			<div class="fs20"style="width: 10.15%;float: right;">
                		<a href="${LinkAddCourse}">
                    		<!--<spring:message code="viewSingle.Edit"/>-->
                    		<div class="panelbtn panelbtn-right icon-pcpplus" aria-hidden="true"></div>
                		</a>
			    </div>
		    <%} %>
  </div>

		    <br><br>
   <div >
		    <% if (!courses.isEmpty()) {
    for (OnlineCourse course : courses){%>
        <div  style="padding-top: 15px; float:right;  width: 83%; ">
                         <a href='<portlet:renderURL>
                                  <portlet:param name="id" value="<%=course.getId().toString()%>"/>
                                  <portlet:param name="view" value="singleOnlineCourse"/>
                                  </portlet:renderURL>'>
					<img src="<%= imageServices.getPathToMicroblogImage(course.getMainImage(),course) %>"
					 class="newsImage" style="float: left">
 			</a>
                     <div class="newsHeader" style="padding-top: 40px; font-size: 14pt;">
                          <b>
                          <a href='<portlet:renderURL>
                                      <portlet:param name="id" value="<%=course.getId().toString()%>"/>
                                      <portlet:param name="view" value="singleOnlineCourse"/>
                                      </portlet:renderURL>'><%=course.getOnlineCourseName()%>
                          </a>
                          </b>
                            <% if (request.isUserInRole("Administrator")) { %>
                                      <a style="float: right;"
                                        href="<portlet:actionURL><portlet:param name="id" value="<%=course.getId().toString()%>"/><portlet:param name="view" value="deleteOnlineCourse" /></portlet:actionURL>">
                                        <div class="icon-pcpremove fs20" aria-hidden="true"></div>
                                       </a>
                                 <a style="float: right;"
                                    href="<portlet:renderURL><portlet:param name="id" value="<%=course.getId().toString()%>"/><portlet:param name="view" value="editOnlineCourse" /></portlet:renderURL>">
                                    <div class="icon-pcppencil fs20" aria-hidden="true"></div>
                                 </a>
		                    <%}%>
                     
          	         </div>
        </div>
           <br/><br/>
             <%}}%>

  
   </div>
</div>

          <%if((courses == null ?0:courses.size())>9||currentPage>1){%>
        <table width="90%">
            <tr>
                <td width="80" align="left">
            <portlet:renderURL var="pagPrev">
                <portlet:param name="view" value="allOnlineCourses"/>
                <portlet:param name="direction" value="prev"/>
                <portlet:param name="currentPage" value="<%=String.valueOf(currentPage)%>"/>
            </portlet:renderURL>
            <c:if test="${currentPage!=1}">
                <a href="${pagPrev}">
                    <img class="paginationImage"
                         src="${pageContext.request.contextPath}/images/pagin-left.png"/>
                </a>
            </c:if>
            </td>

            <td width="auto" align="center" valign="center">
                <%-- PAGINATION --%>
                <%if (skippedBeginning) {%>
                <%-- HIDING FIRST PAGES --%>
                <a href="<portlet:renderURL><portlet:param name="view" value="allOnlineCourses"/><portlet:param name="direction" value="temp"/><portlet:param name="currentPage" value="1"/></portlet:renderURL>">1</a>
                <label> ... </label>
                <%}%>

                <%-- SHOWING CURRENT PAGE NEAREST FROM LEFT AND RIGHT --%>
                <%
                    for (int pageNumb = leftPageNumb; pageNumb <= rightPageNumb; ++pageNumb) {
                        if (pageNumb != currentPage) {
                %>
                <a href="<portlet:renderURL>
                                <portlet:param name="view" value="allOnlineCourses"/>
                <portlet:param name="currentPage" value="<%=String.valueOf(pageNumb)%>"/>
               <portlet:param name="direction" value="temp"/>
                </portlet:renderURL>"><%=pageNumb%>
                </a>
                <%if(pageNumb>0){ %>
                <label>  </label>
                <%}%>
                <%if(pageNumb>9){ %>
                <label>  </label>
                <%}%>
                <%if(pageNumb>99){ %>
                <label>  </label>
                <%}%>
                <%} else {%>
                <label style="color: #28477C; font-size: 40px;"><%=pageNumb%>
                </label>
                <%if(pageNumb>0){ %>
                <label>  </label>
                <%}%>
                <%if(pageNumb>9){ %>
                <label>  </label>
                <%}%>
                <%if(pageNumb>99){ %>
                <label>  </label>
                <%}%>
                <%}%>
                <%}%>

                <%if (skippedEnding) {%>
                <%-- HIDING LAST PAGES --%>
                <label> ... </label>
                <a href="<portlet:renderURL>
                <portlet:param name="view" value="allOnlineCourses"/>
                <portlet:param name="currentPage" value="<%=String.valueOf(pagesCount)%>"/>
                <portlet:param name="direction" value="temp"/>
                </portlet:renderURL>"><%=pagesCount%>
                </a>
                <%}%>
            </td>
            <td width="80" align="right">
            <portlet:renderURL var="pagNext">
                <portlet:param name="view" value="allOnlineCourses"/>
                <portlet:param name="direction" value="next"/>
                <portlet:param name="currentPage" value="<%=String.valueOf(currentPage)%>"/>
            </portlet:renderURL>
            <c:if test="${currentPage!=pagesCount}">
                <a href="${pagNext}">
                    <img class="paginationImage"
                         src="${pageContext.request.contextPath}/images/pagin-right.png"/>
                </a>
            </c:if>
            </td>
            </tr>
        </table>
            <%}%>

		    </body>
		    
<script>


  $(function() {
<%
StringBuffer values = new StringBuffer();
for (int i = 0; i < name.length; ++i) {
    if (values.length() > 0) {
        values.append(',');
    }
    values.append('"').append(name[i]).append('"');
}
%>
var foo = [ <%= values.toString() %> ];
    $("#tags").autocomplete({
      source: foo
    });
    
  });
  </script>
 </html>