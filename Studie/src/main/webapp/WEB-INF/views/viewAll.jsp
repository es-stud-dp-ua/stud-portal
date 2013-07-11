<%@ page import="ua.dp.stud.StudPortalLib.model.Studie" %>
<%@ page import="ua.dp.stud.StudPortalLib.service.StudieService" %>
<%@ page import="ua.dp.stud.StudPortalLib.service.impl.StudieServiceImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include.jsp" %>


<portlet:defineObjects/>
<%
    List<Studie> studie = (List) request.getAttribute("studie");
    int buttonId = 0;  
    if (request.getParameter("buttonId")!=null){
    buttonId = Integer.parseInt(request.getParameter("buttonId"));
       }
    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    String imagePath = new StringBuilder(themeDisplay.getPortalURL()).append('/')
            .append(themeDisplay.getPathImage()).append("/image_gallery?img_id=").toString();
    String temp;
    String[] type={"studie","entry","helps","informal","scholarships","grants"};
%>

<html>
<head>

</head>

<body >

 <portlet:renderURL var="home"> </portlet:renderURL>
 <form method="post" action="<portlet:renderURL/>"> 
 

<div id="contentDiv">
  
    <br/><br/>
    <%if (request.isUserInRole("Administrator") || request.isUserInRole("User")) { %>
    <a style="float: right" href="<portlet:renderURL/>&mode=add">
        <spring:message code="viewAll.addStudie"/>
    </a>
   
    <%} %>
   
    <div style="margin-left: -80px;" class="cmt-types" >
                   <% for(int i=0;i<6;i++){
                if (buttonId==i)  {  
                  temp=new String("form.".concat(type[i]));%>
                   <div  class="ribbon-wrapper" ><button  class="btnselected" style=" width: 150px; height: 40px;  margin-left: -10px;  background-color: rgba(0, 122, 255, 0.47); border-color: rgba(68, 115, 185, 0);" name="buttonId" value="<%=i%>"> 
                         <spring:message code="<%=temp%>"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
                 <%}else{
                    temp=new String("form.".concat(type[i])); %>
                        <div  class="ribbon-wrapper" ><button  class="btntype" style=" width: 150px; height: 40px;  background:  #4473B9; margin-left: -10px;  border-color: #4473B9;" name="buttonId" value="<%=i%>"> 
                         <spring:message code="<%=temp%>"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
                     <%}%>
                <br/>
                <% }%>
    </div>  
     <c:forEach items="${studie}" var="studie">
         <c:if test="${not empty studie}">
                
    <div style="display: table" id="newsTable">
       <%
                                   
       if ((studie.size()>=3))
                           {
            for (int i=0; i<studie.size(); i=i+3) 
                                     {
    %>
  
        <div style="vertical-align: top; display: table-row">
            <div style="text-align: center; margin: 10px 10px 10px 10px; display: table-cell">
         
                <div width="100%">
                    <div id="block">
                  <a href="<portlet:renderURL/>&studieID=<%=studie.get(i).getId()%>">  
                      <img src="<%= imageService.getPathToMicroblogImage(studie.get(i).getMainImage(),studie.get(i)) %>" class="studieImage"> </a>
                    <% if (request.isUserInRole("Administrator")) { %>
                                <a  href="<portlet:renderURL/>&studieId=<%=studie.get(i).getId()%>&mode=delete"
                                onclick='return confirm("<spring:message code="form.confDelete"/>")'>
                                   <span>  x</span></a> 
                                <%}%></div>
                    <div class="studieHeader" >
                        <a href="<portlet:renderURL/>&studieID=<%=studie.get(i).getId()%>">
                            <%=studie.get(i).getTitle()%>
                        </a>
                    </div>
                        
                </div>
            </div>
          <div  style="text-align: center; margin: 10px 10px 10px 10px; display: table-cell">
             <% if (i+1<studie.size()) { %>
                <div width="100%">
                   <div id="block">
                  <a href="<portlet:renderURL/>&studieID=<%=studie.get(i+1).getId()%>">  
                      <img src="<%= imageService.getPathToMicroblogImage(studie.get(i+1).getMainImage(),studie.get(i+1)) %>" class="studieImage"> </a>
                    <% if (request.isUserInRole("Administrator")) { %>
                                <a  href="<portlet:renderURL/>&studieId=<%=studie.get(i+1).getId()%>&mode=delete"
                                onclick='return confirm("<spring:message code="form.confDelete"/>")'>
                                    <span>  x</span></a>
                                <%}%></div>
                    <div class="studieHeader">
                        <a href="<portlet:renderURL/>&studieID=<%=studie.get(i+1).getId()%>">
                            <%=studie.get(i+1).getTitle()%>
                        </a>
                    </div>
                </div>
             <%}%>         
            </div>
            <div style="text-align: center; margin: 10px 10px 10px 10px; display: table-cell">
               <%if (i+2<studie.size()) { %>
                <div width="100%">
                    <div id="block">
                  <a href="<portlet:renderURL/>&studieID=<%=studie.get(i+2).getId()%>">  
                      <img src="<%= imageService.getPathToMicroblogImage(studie.get(i+2).getMainImage(),studie.get(i+2)) %>" class="studieImage"> </a>
                    <% if (request.isUserInRole("Administrator")) { %>
                                <a  href="<portlet:renderURL/>&studieId=<%=studie.get(i+2).getId()%>&mode=delete"
                                onclick='return confirm("<spring:message code="form.confDelete"/>")'>
                                    <span>  x</span></a>
                                <%}%></div>
                    <div class="studieHeader">
                        <a href="<portlet:renderURL/>&studieID=<%=studie.get(i+2).getId()%>">
                            <%=studie.get(i+2).getTitle()%>
                        </a>
                    </div>
                </div>
               <%}%>
            </div>
        </div>
           <%
                 }}  if ((studie.size()==2)){ 
            for (int i=0; i<studie.size(); i=i+2) {
    %>
         <div style="vertical-align: top; display: table-row">
            <div style="text-align: center; margin: 10px 10px 10px 10px; display: table-cell">
                <div width="100%">
                         <div id="block">
                  <a href="<portlet:renderURL/>&studieID=<%=studie.get(i).getId()%>">  
                      <img src="<%= imageService.getPathToMicroblogImage(studie.get(i).getMainImage(),studie.get(i)) %>" class="studieImage"> </a>
                    <% if (request.isUserInRole("Administrator")) { %>
                                <a  href="<portlet:renderURL/>&studieId=<%=studie.get(i).getId()%>&mode=delete"
                                onclick='return confirm("<spring:message code="form.confDelete"/>")'>
                                    <span>  x</span></a>
                                <%}%></div>
                    <div class="studieHeader">
                        <a href="<portlet:renderURL/>&studieID=<%=studie.get(i).getId()%>">
                            <%=studie.get(i).getTitle()%>
                        </a>
                    </div>
                </div>
            </div>
          <div  style="text-align: center; margin: 10px 10px 10px 10px; display: table-cell">
             <% if (i+1<studie.size()) { %>
                <div width="100%">
                       <div id="block">
                  <a href="<portlet:renderURL/>&studieID=<%=studie.get(i+1).getId()%>">  
                      <img src="<%= imageService.getPathToMicroblogImage(studie.get(i+1).getMainImage(),studie.get(i+1)) %>" class="studieImage"> </a>
                    <% if (request.isUserInRole("Administrator")) { %>
                                <a  href="<portlet:renderURL/>&studieId=<%=studie.get(i+1).getId()%>&mode=delete"
                                onclick='return confirm("<spring:message code="form.confDelete"/>")'>
                                    <span>  x</span></a>
                                <%}%></div>
                    <div class="studieHeader">
                        <a href="<portlet:renderURL/>&studieID=<%=studie.get(i+1).getId()%>">
                            <%=studie.get(i+1).getTitle()%>
                        </a>
                    </div>
                </div>
                <% }%>
            </div>
            
        </div>
        <%
             }}
           
       if ((studie.size()==1)) 
                     {
   %>
         <div style="vertical-align: top; display: table-row">
            <div style="text-align: center; margin: 10px 10px 10px 10px; display: table-cell">
                <div width="100%">
                         <div id="block">
                  <a href="<portlet:renderURL/>&studieID=<%=studie.get(0).getId()%>">  
                      <img src="<%= imageService.getPathToMicroblogImage(studie.get(0).getMainImage(),studie.get(0)) %>" class="studieImage"> </a>
                    <% if (request.isUserInRole("Administrator")) { %>
                                <a  href="<portlet:renderURL/>&studieId=<%=studie.get(0).getId()%>&mode=delete"
                                onclick='return confirm("<spring:message code="form.confDelete"/>")'>
                                    <span>  x</span></a>
                                <%}%></div>
                    <div class="studieHeader">
                        <a href="<portlet:renderURL/>&studieID=<%=studie.get(0).getId()%>">
                            <%=studie.get(0).getTitle()%>
                        </a>
                    </div>
                </div>
            </div>
        </div>
       
      <%}%>
    </div>  
      </c:if> 
  </c:forEach>
           
              
    
</div>
  </form> 

</body>
</html>