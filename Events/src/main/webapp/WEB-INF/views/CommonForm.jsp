<form:form method="post" action="${actionLink}"  enctype="multipart/form-data" commandName="event" modelAttribute="event">
            <input type="hidden" size="0" id="x1" name="t"/>
            <input type="hidden" size="0" id="y1" name="l"/>
            <input type="hidden" size="0" id="w" name="w"/>
            <input type="hidden" size="0" id="h" name="h"/>
            <input type="hidden" name="eventId" value="<%=event.getId()%>">

            <table width="100%" margin-bottom="15px">
                <tr>
                    <td width="50%" align="center">
                        <style>
                            .thumb {
                                height: 253px;
                                width: 443px;
                            }
                        </style>
                        <div id="lup">
                        </div>
                        <% if (event.getMainImage() == null) { %>
                        <div id="mainPic" style="vertical-align: top; background: url(${pageContext.request.contextPath}/images/mainpic_443x253.png) no-repeat">
                            <!-- Output for our download Image-->
                            <output id="list"></output>
                        </div>
                        <% } else { %>

                        <div id="mainPic" style="vertical-align: top; ">
                            <img id="img" style="vertical-align: top; " src="${mainImage}"/>
                            <output id="list"></output>
                        </div>
                        <% } %>


                        <div id="rdn">
                        </div>
                        <div id="mainImageLoader">
                            <div id="mainImgloaderBtn">
                                <input type="file" id="mainImage" name="mainImage">
                                <div id="nt"><spring:message code="form.addMainPictures"/></div>
                            </div>
                            <div class="defImage"><spring:message  code='form.useDefaultImage'/>
                                <input type="checkbox" name="defaultImage" id="defaultImage" value="<%=flag.toString()%>" onclick="<%if(flag) flag=false; else flag=true;%>" style="margin-left: 3px;" title="<spring:message code='form.useDefaultImageTitle'/>"/>
                            </div>
                        </div>
                        <script src="${pageContext.request.contextPath}/js/handleFile.js" type="text/javascript"></script>
                        <script>


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
                    </td>
                    <td rowspan=2 width="50%" align="left">

                     <div id="labels"><spring:message code="form.title"/></div><div id="redStar1">*</div>
                        <form:input title="${event.title}" path="title" style="margin-left:2%;width:95%;" id="topicInput" cols="90" rows="2" maxlength="100" onkeypress="return isNotMax(event)"
                                    name="title"/>
                        <form:errors path="title" cssClass="error"></form:errors>
                        <div id="labels"><spring:message code="form.text"/></div><div id="redStar2">*</div>
                            <div style="width: 450px; float: bottom ;padding-left: 8px;">
                            <form:textarea path="text" title="${event.text}" class="ckeditor" id="textInput" cols="60" rows="10" maxlength="8000"
                                           onkeypress="return isNotMax(event)" name="text"/>
                             <textarea style="visibility: hidden;width: 0px;" id="text1" name="text1"  ></textarea>
                            <form:errors path="text" cssClass="error" ></form:errors>
                                <br/><br/>
                            </div>

                            <br/><br/>
                        </td>
                    </tr>
                    <tr>
                        <td width="50%" align="right">
                            <div id="eventSetting" style="margin-top: -115px;">
                                <div style="margin-right: 10px; margin-top: 55px;"><spring:message code="form.dateStart"/><div id="redStar4">*</div></div><input type="text" name="EventDateStart" value="<%=dateFormat.format(event.getEventDateStart()) %>" id="datepicker1"/><input type="text" value="<%=timeFormat.format(event.getEventDateStart())%>" placeholder="HH:mm" maxlength="5" style="width: 15%;margin-left: 1%;" name="startTime" id="defaultEntry"/>
                            <div style="margin-right: 10px;"><spring:message code="form.dateEnd"/></div><input type="text" name="EventDateEnd" id="datepicker2" <%if(event.getEventDateEnd()!=null){%>value="<%= dateFormat.format(event.getEventDateEnd()) %>"<%}%>/><input type="text" placeholder="HH:mm" <%if(event.getEventDateEnd()!=null){%>value="<%=timeFormat.format(event.getEventDateEnd())%>"<%}%> maxlength="5" style="width: 15%;margin-left: 1%;" name="endTime" id="endTime"/>
                            <div style="margin-right: 10px;"><spring:message code="form.location"/></div> <form:input path="location" title="<%=event.getLocation()%>"id="location" cols="60" rows="2" maxlength="100"  name="location"/>
                            <div style="margin-right: 10px;"><spring:message code="form.tags"/></div><input type="text" id="tags" name="tags" style="width:60%;text-align: center;" placeholder="<spring:message code='form.tags.placeholder'/>" />
                            <div style="font-size:14px">
                                <div style="float: right;  ">
                                    <table>
                                        <tr><label>
                                            <div style="font-weight: bold; "><spring:message
                                                    code="addEvent.type"/></div>
                                        </label></tr>
                                        <% for (String currentType : allTypes) {
                                        temp = new String("form." + currentType);%>
                                        <tr>
                                            <td>
                                                <div style="float: right; margin-right: 10px;"><spring:message
                                                        code="<%=temp%>"/></div>
                                            </td>
                                            <td>
                                                <% if (event.getType().toString() == currentType) {%>
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
            <br/><br/><br/>
            <div id="sbm">
                <input style="width: 190px;
                       position: absolute;
                       bottom: 1%;
                       right: 40%;" type="submit" value="<spring:message
                       code='<%=(request.isUserInRole("Administrator"))?"form.submit.save"
                                                                                             :"form.submit.user"%>'/>"/>
            </div>
        </form:form>