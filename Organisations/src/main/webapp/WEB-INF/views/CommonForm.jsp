 <form:form method="post" action="${actionLink}" id="jform" name="jform" enctype="multipart/form-data" commandName="organization" modelAttribute="organization">
             <input type="hidden" size="0" id="x1" name="t"/>
             <input type="hidden" size="0" id="y1" name="l"/>
             <input type="hidden" size="0" id="w" name="w"/>
             <input type="hidden" size="0" id="h" name="h"/>
             <form:input type="hidden" path="id" />

             <table width="100%" margin-bottom="15px">
                 <tr>
                     <td width="50%" align="center">
                         <style>
                             .thumb {
                                 height: 253px;
                                 width: 443px;
                             }
                         </style>


                         <c:choose>
                            <c:when test='${mainImage != null}' >
                                <div>
                                    <div id="mainPic" style="vertical-align: top; ">
                                         <img id="img"  style="vertical-align: top; "  src="${mainImage}"/>
                                         <output id="list"></output>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                 <div id="lup"></div>
                                    <div id="mainPic" style="vertical-align: top; background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
                                    <output id="list"></output>
                                 </div>
                            </c:otherwise>
                         </c:choose>

                         <div id="rdn">
                         </div>
                         <div id="mainImageLoader">
                             <div id="mainImgloaderBtn">
                                 <input type="file" value="<%=imageService.getPathToLargeImage(orgs.getMainImage(), orgs)%>" id="mainImage" name="mainImage">

                                 <div id="nt"><spring:message code="form.addMainPictures"/></div>
                             </div>
                             <div class="defImage"><spring:message  code='form.useDefaultImage'/>
                                 <input type="checkbox" name="defaultImage" id="defaultImage" value="<%=flag.toString()%>" onclick="<%if(flag) flag=false; else flag=true;%>" style="margin-left: 3px;" title="<spring:message code='form.useDefaultImageTitle'/>"/>
                             </div>
                         </div>

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
                             span.innerHTML = ['<img id="cropbox" width="443px" class="thumb" src="', e.target.result,
                             '" title="', escape(theFile.name), '"/>'].join('');
                             document.getElementById('list').insertBefore(span, null);
                             a();
                     };
                             a();
                     })(f);
                     if(document.getElementById('img'))
                     {
                             document.getElementById('img').parentNode.removeChild(document.getElementById('img'));
                     }
                             // Read in the image file as a data URL.
                             reader.readAsDataURL(f);
                             // Read in the image file as a data URL.
                             reader.readAsDataURL(f);
                             a();
                     }
             document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);

                         </script>
                         <br/>
                         <div id="imageLoader">
                             <div id="imgloaderBtn">
                                 <input name="images" type="file" id="aui_3_2_0_11607"
                                        accept="image/jpeg,image/png,image/gif"/ multiple>
                                        <div id="nt"><spring:message code="form.addPictures"/></div>
                             </div>
                         </div>
                         <br/>
                     </td>
                     <td rowspan=2 width="50%" align="left">
                         <div style="font-weight: bold;"><spring:message code="form.title"/> </div> <div id="redStar1">*</div>
                         <form:input title="${organisation.title}" path="title" id="title" cols="90" rows="2" maxlength="100" onkeypress="return isNotMax(event)"
                                     name="title"/>
                         <form:errors path="title" cssClass="error"></form:errors>
                             <div style="width: 450px; float: bottom ;padding-left: 8px;">
                             <div style="font-weight: bold;"><spring:message code="form.text"/> </div> <div id="redStar2" style="padding-bottom: 20px;">*</div>
                             <form:textarea path="text" title="${organisation.text}" class="ckeditor" id="text" cols="60" rows="10" maxlength="8000" gename="text"/>
                             <textarea style="visibility: hidden;width: 0px;" id="text1" name="text1"  ></textarea>
                             <form:errors path="text" cssClass="error" ></form:errors>
                             </div>
                         </td>
                     </tr>
                     <tr>
                         <td width="50%" align="right">
                             <div id="eventSetting">
                                 <div style="font-size:14px;margin-top: 130px;">
                                     <table>
                                         <tr><label>
                                             <div style="font-weight: bold;"><spring:message code="form.contacts"/> </div>
                                             <textarea path="contacts" id="contacts" cols="150" rows="5" maxlength="3000"  name="contacts"><%=(orgs.getContacts()==null) ? "" :orgs.getContacts()%></textarea>
                                         <form:errors path="contacts" cssClass="error"></form:errors>
                                             <div style="font-weight: bold; "><spring:message
                                                     code="addOrganisation.type"/></div>
                                         </label></tr>
                                     <% for (String currentType : allTypes) {
                                         temp = new String("form." + currentType);%>
                                     <tr>
                                         <td>
                                             <div style="float: right; margin-right: 10px;"><spring:message
                                                     code="<%=temp%>"/></div>
                                         </td>

                                         <td>
                                             <% if (orgs.getOrganizationType().toString() == currentType) {%>
                                             <input type="radio" name="type" value="<%=currentType%>" checked=""
                                                    style="float: right;"/>
                                             <%} else { %>
                                             <input type="radio" name="type" value="<%=currentType%>"
                                                    style="float: right;"/>
                                             <%}%>
                                         </td>
                                     </tr>
                                     <%}%>
                                 </table>
                             </div>
                         </div>
                         </div>
                     </td>
                 </tr>
             </table>
             <%@include file="otdelnaya.jsp" %>
             <div id="sbm">
                 <input style="width: 200px;margin-top:5px;" type="submit" value="<spring:message
                        code='<%=(request.isUserInRole("Administrator"))?"form.submit.save"
                                                                                              :"form.submit.user"%>'/>"/>
             </div>
         </form:form>