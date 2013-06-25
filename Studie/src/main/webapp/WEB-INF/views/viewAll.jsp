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
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.CustomFunctions" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@include file="include.jsp" %>


<portlet:defineObjects/>
<%
    List<Studie> studie = (List) request.getAttribute("studie");
    
    ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    String imagePath = new StringBuilder(themeDisplay.getPortalURL()).append('/')
            .append(themeDisplay.getPathImage()).append("/image_gallery?img_id=").toString();
    ImageService imageService = new ImageService();
    //todo: remove
    Locale locale = (Locale) request.getSession().getAttribute("org.apache.struts.action.LOCALE");
    String language = locale.getLanguage();
    String country = locale.getCountry();
    ResourceBundle res = ResourceBundle.getBundle("messages", new Locale(language, country));
%>

<html>
<head>

</head>

<body >
 <portlet:renderURL var="home"> </portlet:renderURL>
 <form method="post" action="<portlet:renderURL/>"> 
 
<liferay-ui:success message='<%=res.getString("msg.successAdd")%>' key="success-add"/>
<div id="contentDiv">
  
    <br/><br/>
    <%if (request.isUserInRole("Administrator") || request.isUserInRole("User")) { %>
    <a style="float: right" href="<portlet:renderURL/>&mode=add">
        <spring:message code="viewAll.addStudie"/>
    </a>
   
    <%} %>
   
    <ul style="margin-left: -80px;" >
               <!-- todo: WTF IS THIS???!-->
    <li>
              <% if (request.getParameter("buttonId")!=null) { %>
            <%  if (Integer.valueOf(request.getParameter("buttonId"))==0) { %>
                <div  class="ribbon-wrapper" ><button  class="btnselected" style=" width: 150px; height: 40px;  margin-left: -10px;  background-color: rgba(0, 122, 255, 0.47); border-color: rgba(68, 115, 185, 0);" name="buttonId" value="0"> 
                         <spring:message code="form.studie"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
           <% } else { %>
                           <div  class="ribbon-wrapper" ><button  class="btntype" style=" width: 150px; height: 40px;  background:  #4473B9; margin-left: -10px;  border-color: #4473B9;" name="buttonId" value="0"> 
                         <spring:message code="form.studie"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
 
           <%} } else {%>
                         <div  class="ribbon-wrapper" ><button  class="btntype" style=" width: 150px; height: 40px;  background:  #4473B9; margin-left: -10px;  border-color: #4473B9;" name="buttonId" value="0"> 
                         <spring:message code="form.studie"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
 
           <%} %>
                </li>
                 <li> 
                 <% if (request.getParameter("buttonId")!=null) {
              if (Integer.valueOf(request.getParameter("buttonId"))==1) { %>
                <div  class="ribbon-wrapper" ><button  class="btnselected" style=" width: 150px; height: 40px;   margin-left: -10px; background-color: rgba(0, 122, 255, 0.47); border-color: rgba(68, 115, 185, 0);" name="buttonId" value="1"> 
                         <spring:message code="form.entry"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
          <% } else { %>
                         <div  class="ribbon-wrapper" ><button  class="btntype" style=" width: 150px; height: 40px;  background:  #4473B9; margin-left: -10px;  border-color: #4473B9;" name="buttonId" value="1"> 
                         <spring:message code="form.entry"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
            <%} } else {%>
                           <div  class="ribbon-wrapper" ><button  class="btntype" style=" width: 150px; height: 40px;  background:  #4473B9; margin-left: -10px;  border-color: #4473B9;" name="buttonId" value="1"> 
                         <spring:message code="form.entry"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
             <%} %>
                </li>
                 <li>
                 <% if (request.getParameter("buttonId")!=null) {
              if (Integer.valueOf(request.getParameter("buttonId"))==2) { %>
                <div  class="ribbon-wrapper" ><button  class="btnselected" style=" width: 150px; height: 40px;   margin-left: -10px;  background-color: rgba(0, 122, 255, 0.47); border-color: rgba(68, 115, 185, 0);" name="buttonId" value="2"> 
                         <spring:message code="form.helps"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
          <% } else { %>
                          <div  class="ribbon-wrapper" ><button  class="btntype" style=" width: 150px; height: 40px;  background:  #4473B9; margin-left: -10px;  border-color: #4473B9;" name="buttonId" value="2"> 
                         <spring:message code="form.helps"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
      
          <%} } else {%>
                          <div  class="ribbon-wrapper" ><button  class="btntype" style=" width: 150px; height: 40px;  background:  #4473B9; margin-left: -10px;  border-color: #4473B9;" name="buttonId" value="2"> 
                         <spring:message code="form.helps"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
           <%} %>
                </li>
                 <li>
           <% if (request.getParameter("buttonId")!=null) {
              if (Integer.valueOf(request.getParameter("buttonId"))==3) { %>
                <div  class="ribbon-wrapper" ><button  class="btnselected" style=" width: 150px; height: 40px;   margin-left: -10px;  background-color: rgba(0, 122, 255, 0.47); border-color: rgba(68, 115, 185, 0);" name="buttonId" value="3"> 
                         <spring:message code="form.informal"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
            <% } else { %>
                            <div  class="ribbon-wrapper" ><button  class="btntype" style=" width: 150px; height: 40px;  background:  #4473B9; margin-left: -10px;  border-color: #4473B9;" name="buttonId" value="3"> 
                         <spring:message code="form.informal"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 

            <%} } else {%>
                            <div  class="ribbon-wrapper" ><button  class="btntype" style=" width: 150px; height: 40px;  background:  #4473B9; margin-left: -10px;  border-color: #4473B9;" name="buttonId" value="3"> 
                         <spring:message code="form.informal"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 

              <%} %>
                </li>
                 <li>
                 <% if (request.getParameter("buttonId")!=null) {
              if (Integer.valueOf(request.getParameter("buttonId"))==4) { %>
                 <div  class="ribbon-wrapper" ><button  class="btnselected" style=" width: 150px; height: 40px;  margin-left: -10px;  background-color: rgba(0, 122, 255, 0.47); border-color: rgba(68, 115, 185, 0);" name="buttonId" value="4"> 
                         <spring:message code="form.scholarships"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
            <% } else { %>
                             <div  class="ribbon-wrapper" ><button  class="btntype" style=" width: 150px; height: 40px;  background:  #4473B9; margin-left: -10px;  border-color: #4473B9;" name="buttonId" value="4"> 
                         <spring:message code="form.scholarships"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
   
            <%} } else {%>
                             <div  class="ribbon-wrapper" ><button  class="btntype" style=" width: 150px; height: 40px;  background:  #4473B9; margin-left: -10px;  border-color: #4473B9;" name="buttonId" value="4"> 
                         <spring:message code="form.scholarships"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
   
            <%} %>
                 </li>
                 <li>
                    <% if (request.getParameter("buttonId")!=null) {
              if (Integer.valueOf(request.getParameter("buttonId"))==5) { %>
                 <div  class="ribbon-wrapper" ><button  class="btnselected" style=" width: 150px; height: 40px;  margin-left: -10px;   background-color: rgba(0, 122, 255, 0.47); border-color: rgba(68, 115, 185, 0);" name="buttonId" value="5"> 
                         <spring:message code="form.grants"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
         <% } else { %>
                          <div  class="ribbon-wrapper" ><button  class="btntype" style=" width: 150px; height: 40px;  background:  #4473B9; margin-left: -10px; border-color: #4473B9;" name="buttonId" value="5"> 
                         <spring:message code="form.grants"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
 
          <%} } else {%>
                           <div  class="ribbon-wrapper" ><button  class="btntype" style=" width: 150px; height: 40px;  background:  #4473B9; margin-left: -10px;  border-color: #4473B9;" name="buttonId" value="5"> 
                         <spring:message code="form.grants"/></button><div class="ribbon-edge-topleft"></div><div class="ribbon-edge-bottomleft"></div></div> 
 
           <%} %>
          </li>
		  </ul>
               
               <% if (!studie.isEmpty())
                                    { %>
    
    <table id="newsTable" >
       <%
                                   
       if ((studie.size()>=3))
                           {
            for (int i=0; i<studie.size(); i=i+3) 
                                     {
    %>
        <tr style="vertical-align: top;">
            <td style="text-align: center; margin: 10px 10px 10px 10px;">
         
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
            </td>
          <td  style="text-align: center; margin: 10px 10px 10px 10px;">
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
            </td>
            <td style="text-align: center; margin: 10px 10px 10px 10px;">
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
            </td>
        </tr>
           <%
                 }}  if ((studie.size()==2)){ 
            for (int i=0; i<studie.size(); i=i+2) {
    %>
         <tr style="vertical-align: top;">
            <td style="text-align: center; margin: 10px 10px 10px 10px;">
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
            </td>
          <td  style="text-align: center; margin: 10px 10px 10px 10px;">
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
            </td>
            
        </tr>
        <%
             }}
           
       if ((studie.size()==1)) 
                     {
   %>
         <tr style="vertical-align: top;">
            <td style="text-align: center; margin: 10px 10px 10px 10px;">
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
            </td>
        </tr>
       
      <%}%>
    </table>  
<%}%>  
  
           
              
    
</div>
  </form> 
</body>
</html>