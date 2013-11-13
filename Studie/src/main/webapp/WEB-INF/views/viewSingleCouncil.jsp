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

<div id='newMember'>
     <% if (council.getCouncilMembers()!=null) {
for (CouncilMembers member : council.getCouncilMembers()){%>
<div id='member_<%=member.getId()%>'>
	<fieldset>
				<div id='each_<%=member.getId()%>' style="float:left; display:inline">
             	<div><i><%=member.getMemberPosition()%></i></div>
 				<div><b><%=member.getMemberName()%></b></div>
            	<div><%=member.getMemberContact()%></div>
    </div>
      <div style="display:inline; float:right"   class="icon-pcppencil fs20" onclick="editMember(<%=member.getId()%>);" aria-hidden="true"></div>
      <div style="display:inline; float:right"   class="icon-pcpremove fs20" onclick="removeMember(<%=member.getId()%>);" aria-hidden="true"></div>
    <div id='edit_<%=member.getId()%>'  style="display:none; float:left">
                     <div id="labels"><spring:message code="form.councilMemberName"/></div>
                        <input id="edit_name_<%=member.getId()%>" type="text" value='<%=member.getMemberName()%>'/>
                    <div id="labels"><spring:message code="form.councilMemberPosition"/></div>
                        <input id="edit_position_<%=member.getId()%>" type="text" value='<%=member.getMemberPosition()%>'/>
                    <div id="labels"><spring:message code="form.councilMemberContact"/></div>
                        <textarea id="edit_contact_<%=member.getId()%>" COLS="60" ROWS="5"><%=member.getMemberContact()%></textarea><br>
                    <input type="button" value="Save" onclick="updateMember(<%=member.getId()%>)">
   </div>
     </fieldset> 
     <br/>      
 </div>
 
         <%}%>
     <%}%>
     
  </div>
     		
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
                    
                    <div id="labels"><spring:message code="form.councilMemberPosition"/></div>
                        <input id="input_position" type="text"/>

					<div id="labels"><spring:message code="form.councilMemberContact"/></div>
                        <textarea id="input_contact" COLS="60" ROWS="5"></textarea><br>
                                            
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
                	  $('#each_'+ID).html('<div><i>'+ membersPosition+'</i><div>'
                			 			 +'<div><b>'+ membersName +'</b><div>'
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
                	  $('#newMember').append(data);
                	  $('.splCont').toggle();
                  },
              });
      }
          
          function a() {
              jQuery('#cropbox').Jcrop({onChange: setCoords, onSelect: setCoords, bgColor: 'black',
                      bgOpacity: .4,
                      setSelect: [100, 0, 253, 353],
                      aspectRatio: 1});
              }
      
          function setCoords(c) {
      		jQuery('#x1').val(c.x);
              jQuery('#y1').val(c.y);
              jQuery('#x2').val(c.x2);
              jQuery('#y2').val(c.y2);
              jQuery('#w').val(c.w);
              jQuery('#h').val(c.h);
      }
          
          
          </script>
         
           <script>
                            function handleFileSelect(evt) {
                            var files = evt.target.files; // FileList object
                                    // Loop through the FileList and render image files as thumbnails.
                                    var f = files[files.length - 1];
                                    // Only process im11age files.
                                    document.getElementById('list').innerHTML = '';
                                    var reader = new FileReader();
                                    // Closure to capture the file information.
                                    reader.onload = (function(theFile) {
                            return function(e) {
                            // Render thumbnail.
                            var span = document.createElement('span');
                                    span.innerHTML = ['<img id="cropbox" width="453px"  class="thumb" src="', e.target.result,
                                    '" title="', escape(theFile.name), '"/>'].join('');
                                    document.getElementById('list').insertBefore(span, null);
                                    a();
                            };
                                    a();
                            })(f);
                                    // Read in the image file as a data URL.
                                    reader.readAsDataURL(f);
                                    a();
                            }
                            document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);</script>
          
     
</body>
</html>


