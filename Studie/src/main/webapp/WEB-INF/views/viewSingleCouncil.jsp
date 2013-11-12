<%@ page import="ua.dp.stud.studie.model.Council" %>
<%@ page import="ua.dp.stud.studie.model.CouncilMembers" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.theme.PortletDisplay" %>
<%@ page import="com.liferay.portal.model.Layout" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ua.dp.stud.StudPortalLib.model.ImageImpl" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include.jsp" %>

<portlet:defineObjects/>
<%
    ImageService imgService = (ImageService) pageContext.findAttribute("imageService");
    Council council = (Council) request.getAttribute("council");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
	Integer councilId =  (Integer) request.getAttribute("councilId");
	
%>
<html>
 <head> </head>
 <body >
     <portlet:renderURL var="home"><portlet:param name="currentPage" value="<%=currentPage.toString()%>"/>  </portlet:renderURL>
     <div id="singleView" style="float:center">
             <div class="portlet-content-controlpanel fs20">
                 <a href="${home}">
                     <!--<spring:message code="form.back"/>-->
                     <div class="panelbtn panelbtn-right fs20 icon-pcparrow-left" aria-hidden="true"></div>
                 </a>
                 <%if (request.isUserInRole("Administrator") || request.isUserInRole("User")) { %>
                 <% if (request.isUserInRole("Administrator")) { %>
                 <a style="float: right" href='<portlet:renderURL><portlet:param name="councilId" value="<%=council.getId().toString()%>"/><portlet:param name="currentPage" value="1"/><portlet:param name="delete" value="council" /></portlet:renderURL>'
                    onclick='return confirm("<spring:message code="form.confDelete"/>")'>
                     <div class="panelbtn panelbtn-right icon-pcpremove" aria-hidden="true"></div>
                     <!--<spring:message code="form.delete"/>-->
                 </a>
                 <%}%>
                 <a style="float: right" href= '<portlet:renderURL><portlet:param name="councilId" value="<%=council.getId().toString()%>"/><portlet:param name="edit" value="council" /></portlet:renderURL>'>
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

                 border-right: 1px solid red; /* ÐÐ¸Ð½Ð¸Ñ ÑÐ¿ÑÐ°Ð²Ð° Ð¾Ñ ÑÐµÐºÑÑÐ° */
                 padding: 0 10px;  /* Ð Ð°ÑÑÑÐ¾ÑÐ½Ð¸Ðµ Ð¼ÐµÐ¶Ð´Ñ Ð»Ð¸Ð½Ð¸ÐµÐ¹ Ð¸ ÑÐµÐºÑÑÐ¾Ð¼ */
                 margin: 0 10%; /* ÐÑÑÑÑÐ¿Ñ Ð¾Ñ ÐºÑÐ°Ñ Ð´Ð¾ Ð»Ð¸Ð½Ð¸Ð¸ */
                }
               </style>
             <div class="line" style="padding-left: 20px; float: left; width: 20%; overflow: hidden;  border-right: 1px solid red" title="${council.councilContact}">
                 ${council.councilContact}
             </div>

             <div class="councilDescription" style="width: 20%; float: left; padding-left: 10px; overflow: hidden;" title="${council.councilDescription} ">
                 ${council.councilDescription}
             </div>
        </div>     

<br/><br/>
     <% if (council.getCouncilMembers()!=null) {
for (CouncilMembers member : council.getCouncilMembers()){%>
<div id='member_<%=member.getMemberId()%>'>
	<fieldset>
	<div id='each_<%=member.getMemberId()%>' style="float:left; display:inline">
 				<div><%=member.getMemberName()%></div>
             	<div><%=member.getMemberPosition()%></div>
            	<div><%=member.getMemberContact()%></div>
    </div>
      <div style="display:inline; float:right"   class="icon-pcppencil fs20" onclick="editMember(<%=member.getMemberId()%>);" aria-hidden="true"></div>
      <div style="display:inline; float:right"   class="icon-pcpremove fs20" onclick="removeMember(<%=member.getMemberId()%>);" aria-hidden="true"></div>
    <div id='edit_<%=member.getMemberId()%>'  style="display:none; float:left">
                     <div id="labels"><spring:message code="form.councilMemberName"/></div>
                        <input id="edit_name_<%=member.getMemberId()%>" type="text" value='<%=member.getMemberName()%>'/>
                    <div id="labels"><spring:message code="form.councilMemberContact"/></div>
                        <input id="edit_contact_<%=member.getMemberId()%>" type="text" value='<%=member.getMemberContact()%>'/>
                    <div id="labels"><spring:message code="form.councilMemberPosition"/></div>
                        <input id="edit_position_<%=member.getMemberId()%>" type="text" value='<%=member.getMemberPosition()%>'/>
                    <input type="button" value="Save" onclick="updateMember(<%=member.getMemberId()%>)">
   </div>
     </fieldset> 
     <br/>      
 </div>
 
         <%}%>
     <%}%>
     
     		
     		<portlet:resourceURL var="linkAdd" id="addMember"/>
			<portlet:resourceURL var="linkRemove" id="removeMember"/>
          	<portlet:resourceURL var="linkEdit" id="editMember"/>	
     
     
     
			<script type="text/javascript">
			 $(document).ready(function () {
			$(".splLink").click(function () {
        			$(this).parent().children("div.splCont").toggle("normal");
        			var a = $(this).children("span#spoiler");
        			a.html((a.html() == "+") ? "-" : "+");
        			return false
        		});
			    });
             </script>
             <br/>
            
            <div style="padding-left: 230px; width:20%" class="splLink">
				<span id="spoiler">+</span>
				<span>Add member</span>
			</div>
			
			
            
            <div class="splCont" style="display:none;">
               
                     <div id="labels"><spring:message code="form.councilMemberName"/></div>
                        <input id="input_name" type="text"/>
                    
                    <div id="labels"><spring:message code="form.councilMemberContact"/></div>
                        <input id="input_contact" type="text"/>
                    
                    <div id="labels"><spring:message code="form.councilMemberPosition"/></div>
                        <input id="input_position" type="text"/>
                        
                    <input type="button" value="Add" onclick="addMember()">
                        
            </div>
                
        
			
          <script>
       
          
          function editMember(id) {
        	  $('#each_'+id).attr('style','display:none;');
        	  $('#edit_'+id).attr('style','display:inline;');
      }
          
          function updateMember(ID) {
        	  var membersName = $("#edit_name_"+ID).val();
        	  var membersPosition = $("#edit_position_"+ID).val();
        	  var membersContact = $("#edit_contact_"+ID).val();
        	  var CID = <%=council.getId()%>;
        	  $.ajax({
                  url: "${linkEdit}",
                  cache: false,
                  dataType: "html",
                  data: {name: membersName, position: membersPosition, contact: membersContact, memberId: ID, cid: CID},
                  type: "GET",
                  contentType: "application/json;charset=utf-8",
                  success: function (data) {
                	  $('#each_'+ID).attr('style','display:inline;');
                	  $('#each_'+ID).html('<div>'+ membersName+'<div>'
                			 			 +'<div>'+ membersPosition+'<div>'
                			 			 +'<div>'+ membersContact+'<div>');
                	  $("#edit_"+ID).attr('style','display:none;');
                  },
              });
      }
       
          function removeMember(id) {
              $.ajax({
                  url: "${linkRemove}",
                  cache: false,
                  dataType: "html",
                  data: {memberId: id},
                  type: "GET",
                  contentType: "application/json;charset=utf-8",
                  success: function (data) {
                      $('#member_'+id).remove();
                  },
              });
      }
       
          function addMember() {
              
        	  var memberName = $("#input_name").val();
        	  var memberPosition = $("#input_position").val();
        	  var memberContact = $("#input_contact").val();
        	  var ID = <%=council.getId()%>;
        	  $.ajax({
                  url: "${linkAdd}",
                  cache: false,
                  dataType: "html",
                  data: {name: memberName, position: memberPosition, contact: memberContact, id: ID},
                  type: "GET",
                  contentType: "application/json;charset=utf-8",
                  success: function (data) {
                	  location.reload();
                  },
              });
      }
          </script>
     
</body>
</html>


