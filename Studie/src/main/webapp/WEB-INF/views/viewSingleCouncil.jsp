<%@ page import="ua.dp.stud.studie.model.Council"%>
<%@ page import="ua.dp.stud.studie.model.CouncilMembers"%>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@ page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@ page import="com.liferay.portal.theme.PortletDisplay"%>
<%@ page import="com.liferay.portal.model.Layout"%>
<%@ page import="java.util.Collection"%>
<%@ page import="ua.dp.stud.StudPortalLib.model.ImageImpl"%>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include.jsp"%>

<portlet:defineObjects />
<%
	ImageService imgService = (ImageService) pageContext.findAttribute("imageService");
    Council council = (Council) request.getAttribute("council");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
	Integer councilId =  (Integer) request.getAttribute("councilId");
%>
<html>
<head>
</head>
<body>
	<portlet:renderURL var="home">
		<portlet:param name="view" value="allcouncils" />
	</portlet:renderURL>
	<div id="singleView" style="float: center">
		<div class="portlet-content-controlpanel fs20">
			<a href="${home}"> <!--<spring:message code="form.back"/>-->
				<div class="panelbtn panelbtn-right fs20 icon-pcparrow-left"
					aria-hidden="true"></div>
			</a>
			<%
				if (request.isUserInRole("Administrator") || request.isUserInRole("User")) {
			%>
			<%
				if (request.isUserInRole("Administrator")) {
			%>
			<a style="float: right"
				href='<portlet:renderURL><portlet:param name="councilId" value="<%=council.getId().toString()%>"/><portlet:param name="currentPage" value="1"/><portlet:param name="delete" value="council" /></portlet:renderURL>'
				onclick='return confirm("<spring:message code="form.confDelete"/>")'>
				<div class="panelbtn panelbtn-right icon-pcpremove"
					aria-hidden="true"></div> <!--<spring:message code="form.delete"/>-->
			</a>
			<%
				}
			%>
			<a style="float: right"
				href='<portlet:renderURL><portlet:param name="councilId" value="<%=council.getId().toString()%>"/><portlet:param name="edit" value="council" /></portlet:renderURL>'>
				<!--<spring:message code="viewSingle.Edit"/>-->
				<div class="panelbtn panelbtn-right icon-pcppencil"
					aria-hidden="true"></div>
			</a>
			<%
				}
			%>
		</div>
		<div class="councilHeader" style="float: left">
			<img src="${mainImage}" alt="" />


		</div>
		<div style="padding-left: 230px; width: 20%">
			${council.studie.title}</div>
		<br />
		<style>
.line {
	border-right: 1px solid red
	padding: 0 10px;

	margin: 0 10%;
}
</style>
		<div class="line"
			style="padding-left: 20px; float: left; width: 20%; overflow: hidden; border-right: 1px solid red"
			title="${council.councilContact}">${council.councilContact}</div>

		<div class="councilDescription"
			style="width: 20%; float: left; padding-left: 10px; overflow: hidden;"
			title="${council.councilDescription} ">
			${council.councilDescription}</div>
	</div>

	<br />
	<br />
	<br />
	<br />
<form id="members" name="members">

<div id='newMember'>

	<%if (council.getCouncilMembers()!=null) {
		 for (CouncilMembers member : council.getCouncilMembers()){%>
		   <div id='member_<%=member.getId()%>' class='memberList'>
			 <fieldset id="memberField_<%=member.getId()%>">
				<div id='each_<%=member.getId()%>'
					style="float: left; display: inline">

				     <% if (member.getMainImage() != null) { %>
					    <img id='image_<%=member.getId()%>'
						src="<%=imageService.getPathToMicroblogImage(member.getMainImage(),member)%>"
						style="float: left">
						   <%}%>
				  <div style="float: left">
					  <div id='position_<%=member.getId()%>'>
						<i><%=member.getMemberPosition()%></i>
					  </div>
					  <div id='name_<%=member.getId()%>'>
						<b><%=member.getMemberName()%></b>
					  </div>
					  <div id='contact_<%=member.getId()%>'>
					     <%=member.getMemberContact()%>
					  </div>
				  </div>
				</div>
				
				<div style="display: inline; float: right"
					class="icon-pcpremove fs20"
					onclick="removeMember(<%=member.getId()%>)" aria-hidden="true">
				</div>
				
				<div style="display: inline; float: right"
					class="icon-pcppencil fs20"
					onclick="editMember(<%=member.getId()%>)" aria-hidden="true">
				</div>
					
				<div style="display: inline; float: right" 
					class="icon-pcparrow-up fs20"
					onclick="memberUp(<%=member.getId()%>)" aria-hidden="true">
				</div>
				
				<div style="display: inline; float: right"
					class="icon-pcparrow-down fs20"
					onclick="memberDown(<%=member.getId()%>)" aria-hidden="true">
				</div>
				
				<div id='edit_<%=member.getId()%>'
					style="display: none; float: left">

		            <div style="left: 30%;" style="width: 460px;">
			            <div style="height: 300px;">
					        <% if (member.getMainImage() != null) { %>
				                <div id="mainPic" >
						            <img id="img_<%=member.getId()%>" style="vertical-align: top; " src="<%=imageService.getPathToLargeImage(member.getMainImage(),member)%>"/>
				            		<output id="edit_list_<%=member.getId()%>" class="edit_list"></output>
				                </div>
					        <% } else { %>
					            <div id="mainPic" style="vertical-align: top;">
				                	<img id="img_<%=member.getId()%>" style="vertical-align: top; " src="${pageContext.request.contextPath}/images/mainpic_443x253.png"/>
                                    <output id="edit_list_<%=member.getId()%>" class="edit_list"></output>
                                </div>
                             <% } %>
			            </div>
		            </div>


		            <div style="left: 30%;" id="mainImageLoader">
			            <div id="mainImgloaderBtn">
				            <input type="file" id="editImage_<%=member.getId()%>" class="editImage" 
					        accept="image/jpeg,image/png,image/gif" />
				            <div id="nt">
					            <spring:message code="form.addPicture" />
				            </div>
		                </div>
	                </div>

             
					<div id="labels_edit_name">
						<spring:message code="form.councilMemberName" />
					</div>
					<input id="edit_name_<%=member.getId()%>" type="text"
						value='<%=member.getMemberName()%>' />
					    <div id="labels_edit_position">
						  <spring:message code="form.councilMemberPosition" />
					    </div>
					  <input id="edit_position_<%=member.getId()%>" type="text"
						value='<%=member.getMemberPosition()%>' />
					   <div id="labels_edit_contact">
						 <spring:message code="form.councilMemberContact" />
					   </div>
					<textarea id="edit_contact_<%=member.getId()%>" COLS="60" ROWS="5"><%=member.getMemberContact()%></textarea>
					<br> <input type="button" value="Save"
						onclick="updateMember(<%=member.getId()%>)"/>
						<input type="button" value="Cancel"
						onclick="cancelUpdateMember(<%=member.getId()%>)"/>
				</div>
			 </fieldset>
			 <div id='est_<%=member.getId()%>' style='display:none' onclick="reAdd(<%=member.getId()%>)">cancel</div>
			 <br />
           </div>
	
		 <%}%>

	<%}%>



    <div id='member_$id$' class='memberList' style='display:none'>
        <fieldset id='memberField_$id$'>
            <div id='each_$id$' style='float:left; display:inline'>
                <c:if test="m_isPresent_m" >
                   <img id='image_$id$' src=m_urlSmall_m style='float: left'>
                </c:if>
                <div style='float: left'>
                    <div id='position_$id$'>
                        <i> m_position_m </i>
                    </div>
                    <div id='name_$id$'>
                        <b>m_name_m</b>
                    </div>
                    <div id='contact_$id$'>
                        m_contact_m
                    </div>
                </div>
            </div>

            <div style='display:inline; float:right' class='icon-pcpremove fs20'
                onclick="removeMember(m_id_m)" aria-hidden='true'>
            </div>
            <div style='display:inline; float:right'   class='icon-pcppencil fs20'
                onclick="editMember(m_id_m)" aria-hidden='true'>
            </div>
            <div style='display: inline; float: right' class='icon-pcparrow-up fs20'
                onclick="memberUp(m_id_m)" aria-hidden='true'>
            </div>
            <div style='display: inline; float: right' class='icon-pcparrow-down fs20'
		        onclick="memberDown(m_id_m)" aria-hidden='true'>
		    </div>
        
		    <div id='edit_$id$'  style='display:none; float:left'>
                <div style='left: 30%;' style='width: 460px;'>
                    <div style='height: 300px;'>
                      <c:if test="m_isPresent_m" >
                        <div id='mainPic'>
                            <img id='img_$id$' style='vertical-align: top; ' src=m_urlLarge_m/>
                            <output id='edit_list_$id$' class='edit_list'></output>
                        </div>
                       </c:if>
                      <c:if test="not m_isPresent_m" >
                        <div id='mainPic'>
                            <img id='img_$id$' style='vertical-align: top; ' src='${pageContext.request.contextPath}/images/mainpic_443x253.png"'/>
                            <output id='edit_list_$id$' class='edit_list'></output>
                        </div>
                      </c:if>
                    </div>
                </div>

                <div style='left: 30%;' id='mainImageLoader'>
                    <div id='mainImgloaderBtn'>
                        <input type='file' id='editImage_$id$' class='editImage' accept='image/jpeg,image/png,image/gif'/>
                        <div id='nt'><p>...</p></div>
                    </div>
                </div>

                <div id='labels_edit_name'>
                    <spring:message code='form.councilMemberName'/>
                </div>
                <input id='edit_name_$id$' type='text'
                    value=m_name_m />
                <div id='labels_edit_position'>
                    <spring:message code='form.councilMemberPosition'/>
                </div>
                <input id='edit_position_$id$' type='text'
                    value=m_position_m />
                <div id='labels_edit_contact'>
                    <spring:message code='form.councilMemberContact'/>
                </div>
                <textarea id='edit_contact_$id$' COLS='60' ROWS='5'>
                    m_contact_m
                </textarea>  <br/>
                <input type='button' value='Save'
                 onclick="updateMember(m_id_m)"/>
                    <input type='button' value='Cancel'
                     onclick="cancelUpdateMember(m_id_m)"/>
            </div>
        </fieldset>  <br/>

    </div>






</div>

	<portlet:resourceURL var="linkAdd" id="addMember" />
	<portlet:resourceURL var="linkRemove" id="removeMember" />
	<portlet:resourceURL var="linkEdit" id="editMember" />
	<portlet:resourceURL var="linkMemberUp" id="memberUp" />
	<portlet:resourceURL var="linkMemberDown" id="memberDown" />
	<portlet:resourceURL var="linkReAdd" id="reAddMember" />

	<script type="text/javascript">
			 $(document).ready(function () {
			$("#newMember").on("change",".editImage",function(event){handleEditFileSelect(event,this)});
			$(".splLink").click(function () {
        			$(this).parent().children("div.splCont").toggle("normal");
        			var a = $(this).children("span#spoiler");
        			a.html((a.html() == "+") ? "-" : "+");
        			return false;
        		});
			
			
			    });
             </script>
	<br />

	<div style="padding-left: 230px; width: 20%" class="splLink">
	<input type="button" value="<spring:message code="form.councilMemberAdding"/>"
				style="float: right" onclick="updateMember()"/>
	</div>




	<div class="splCont" style="display: none;">


		<div style="left: 30%;" style="width: 460px;">
			<div style="height: 300px;">
				<div id="mainPic"
					style="background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
					<output id="list"></output>
				</div>
			</div>
		</div>


		<div style="left: 30%;" id="mainImageLoader">
			<div id="mainImgloaderBtn">
				<input type="file" id="mainImage" 
					accept="image/jpeg,image/png,image/gif" />
				<div id="nt">
					<spring:message code="form.addPicture" />
				</div>
			</div>
		</div>
		

		<div id="labels_add_name">
			<spring:message code="form.councilMemberName" />
		</div>
		<input id="input_name" name="input_name" type="text" />

		<div id="labels_add_position">
			<spring:message code="form.councilMemberPosition" />
		</div>
		<input id="input_position" name="input_position" type="text" />

		<div id="labels_add_contact">
			<spring:message code="form.councilMemberContact" />
		</div>
		<textarea id="input_contact" COLS="60" ROWS="5"></textarea>
		<br/> <input type="button" value="Add" onclick="addMember()"/>
	</div>
	</form>
	<script>
	
	function validateAdd(){
		$('#members').validate({
			  rules: {
				  input_name: {
			      required: true,
			    },
			    input_position: {
				  required: true,
				    }
			  },
               messages: {
            	  input_name: {
    			      required: "<spring:message code="NotEmpty.Studie.text"/>",
    			    },
    			    input_position: {
      			      required: "<spring:message code="NotEmpty.Studie.text"/>",
      			    }
              }
			});
	}
       		
          function addMember(){
        	  validateAdd();
        	  if ($('#members').valid()) {
        	  var oMyForm = new FormData();
        	  var inp_name = $("#input_name").val();
        	  var inp_position = $("#input_position").val();
        	  var inp_contact = $("#input_contact").val();
        	  var c_id = <%=council.getId()%>;
        	  oMyForm.append("file", mainImage.files[0]);
        	  oMyForm.append("name",inp_name);
        	  oMyForm.append("position",inp_position);
        	  oMyForm.append("contact",inp_contact);
        	  oMyForm.append("council_id",c_id);
        	  $.ajax({
        	    url: '${linkAdd}',
        	    data: oMyForm,
        	    dataType: 'json',
        	    processData: false,
        	    contentType: false,
        	    type: 'POST',
        	    success: function(data){
        	        var objJSON = jQuery.parseJSON(data);

        	        $('#newMember').appendTo($("#member_$id$").clone());

                    $('#newMember').replace('m_id_m',objJSON.memberId);
                    var tmpId="#"+"member_"+objJSON.memberId;
                    $(tmpId).replace(/m_id_m/g,objJSON.memberId);
                    $(tmpId).replace(/m_urlSmall_m/g,objJSON.microBlogImg);
                    $(tmpId).replace(/m_urlLarge_m/g,objJSON.largeImg);
                    $(tmpId).replace(/m_isPresent_m/g,objJSON.mainImgIsPresent);
                    $(tmpId).replace(/m_contact_m/g,);
                    $(tmpId).replace(/m_position_m/g,);
                    $(tmpId).replace(/m_name_m/g,);

                    $(tmpId).show();

                    $('.splCont').toggle();
                    $("#input_name").val("");
                    $("#input_position").val("");   
                    $("#input_contact").val("");
                    $("#newMember").on("change",".editImage",function(event){handleEditFileSelect(event,this)});
                    $("#list").hide();
                    
        	    }
        	  });}
        	}




          function updateMember(ID){
        	  var oMyForm = new FormData();
        	  var membersName = $("#edit_name_"+ID).val();
        	  var membersPosition = $("#edit_position_"+ID).val();
        	  var membersContact = $("#edit_contact_"+ID).val();
        	  var CID = <%=council.getId()%>;
        	  var image = $("#editImage_"+ID)[0].files[0];
        	  oMyForm.append("file", image);
        	  oMyForm.append("name",membersName);
        	  oMyForm.append("position",membersPosition);
        	  oMyForm.append("contact",membersContact);
        	  oMyForm.append("council_id",CID);
        	  oMyForm.append("member_id",ID);
        	  $.ajax({
        	    url: '${linkEdit}',
        	    data: oMyForm,
        	    dataType: 'text',
        	    processData: false,
        	    contentType: false,
        	    type: 'POST',
        	    success: function(data){
        	    	$('#each_'+ID).attr('style','display:inline;');
              	 if(!data){
              		$('#name_'+ID).html('<b>'+ membersName +'</b>');
              	  	$('#position_'+ID).html('<i>'+ membersPosition+'</i>');
              	  	$('#contact_'+ID).html(membersContact);
              		$('#image_'+ID).fadeOut();
              	 }
              	 else{
              		$('#name_'+ID).html('<b>'+ membersName +'</b>');
              	  	$('#position_'+ID).html('<i>'+ membersPosition+'</i>');
              	  	$('#contact_'+ID).html(membersContact);
              	  	if ($('#image_'+ID).length){
              	  	$('#image_'+ID).attr('src',data).fadeIn();
              	    }
              	  	else{
              	  	$('#name_'+ID).parent().parent().prepend('<img id="image_'+ID+'" src="'+data+'" style="float: left">');
              	  	}
              	 }
              	 
              	  $("#edit_"+ID).attr('style','display:none;');
        	    }
        	    
        	  });
        	}
          
          function editMember(id) {
        	  $('#each_'+id).attr('style','display:none;');
        	  $('#edit_'+id).attr('style','display:inline;');
      }
       
          function cancelUpdateMember(id) {
        	  $('#each_'+id).attr('style','display:inline;');
        	  $('#edit_'+id).attr('style','display:none;');
        	  $('#edit_name_'+id).val($('#name_'+id).text());
        	  $('#edit_position_'+id).val($('#position_'+id).text());
        	  $('#edit_contact_'+id).val($('#contact_'+id).text());
      }
          
             function removeMember(id) {
            	 var img = new Image();
      			img.src = $('#img_'+id).attr("src");
            	 $.ajax({
                  url: "${linkRemove}",
                  cache: false,
                  dataType: "html",
                  data: {memberId: id},
                  type: "GET",
                  contentType: "application/json;charset=utf-8",
                  success: function (data) {
                	  $("#est_"+id).insertAfter('#member_'+id);
                	  $("#est_"+id).show();
                      $('#member_'+id).hide();
                      $("#img_"+id).attr("src",img.src);
                  },
              });
      }
             function reAdd(id){
              var oMyForm = new FormData();
			  var image = new Image();
			  image.src = $('#img_'+id).attr("src");
              var name = $("#edit_name_"+id).val();
           	  var position = $("#edit_position_"+id).val();
           	  var contact = $("#edit_contact_"+id).val();
           	  var CID = <%=council.getId()%>;
           	  oMyForm.append("file", image);
           	  oMyForm.append("name",name);
           	  oMyForm.append("position",position);
           	  oMyForm.append("contact",contact);
           	  oMyForm.append("council_id",CID);
           	  $.ajax({
           	    url: '${linkReAdd}',
           	    data: oMyForm,
           	    dataType: 'text',
           	    processData: false,
           	    contentType: false,
           	    type: 'POST',
           	    success: function(data){
           	     $("#est_"+id).hide();
           	     $('#member_'+id).show();
           	     $("#img_"+id).attr("id","img_"+data);
           	  	 $('#edit_name_'+id).attr("id","edit_name_"+data);
        	 	 $('#edit_position_'+id).attr("id","edit_position_"+data);
        	  	 $('#edit_contact_'+id).attr("id","edit_contact_"+data);
           	    }
           	  });
             }
             
             function memberUp(id) {
            	 var CID = <%=council.getId()%>;
            	 $.ajax({
                     url: "${linkMemberUp}",
                     cache: false,
                     dataType: "html",
                     data: {member_id: id, council_id: CID},
                     type: "GET",
                     contentType: "application/json;charset=utf-8",
                     success: function (data) {
                    	 $("#memberField_"+id).closest(".memberList").after($("#memberField_"+id).closest(".memberList").prev());
                     },
                 });
         }
       
             function memberDown(id) {
            	 var CID = <%=council.getId()%>;
            	 $.ajax({
                     url: "${linkMemberDown}",
                     cache: false,
                     dataType: "html",
                     data: {member_id: id, council_id: CID},
                     type: "GET",
                     contentType: "application/json;charset=utf-8",
                     success: function (data) {
                    	 $("#memberField_"+id).closest(".memberList").before($("#memberField_"+id).closest(".memberList").next());
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
                                    $("#list").show();
                                    a();
                            };
                                    a();
                            })(f);
                                    // Read in the image file as a data URL.
                                    reader.readAsDataURL(f);
                                    a();
                            }
                            
                            
                            
                            function handleEditFileSelect(evt,element) {
				console.error(element);
								var elementId = element.id
								var id = elementId.substring(elementId.indexOf("_")+1,elementId.length)
                                var files = evt.target.files; // FileList object
                                        var f = files[files.length - 1];
                                        document.getElementById('edit_list_'+id).innerHTML = '';
                                        var reader = new FileReader();
                                        reader.onload = (function (theFile) {
                                return function (e) {
                                // Render thumbnail.
                                var span = document.createElement('span');
                                        span.innerHTML = ['<img id="cropbox" class="thumb" width="443px" src="', e.target.result,
                                        '" title="', escape(theFile.name), '"/>'].join('');
                                        document.getElementById('edit_list_'+id).insertBefore(span, null); 
                                        $("#list").show();
                                        a();
                                };
                                        a();
                                })(f);
                                        if (document.getElementById('img_'+id) != null){
                                        document.getElementById('img_'+id).parentNode.removeChild(document.getElementById('img_'+id));}
                                        // Read in the image file as a data URL.
                                        reader.readAsDataURL(f);
                                        a();
                                }
                            document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);
                            
                            
                            </script>


</body>
</html>


