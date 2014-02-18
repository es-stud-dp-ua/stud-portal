<div width="100%" align="center">
        <form:form method="post" id="councilForm"  action="${actionLink}"
           enctype="multipart/form-data" commandName="council" modelAttribute="council">
             <input type="hidden" size="0" id="x1" name="t" value="0"/>
             <input type="hidden" size="0" id="y1" name="l" value="0"/>
             <input type="hidden" size="0" id="w" name="w" value="100"/>
             <input type="hidden" size="0" id="h" name="h" value="100"/>


           <div id="labels" width="50%" align="center"><spring:message code="form.councilName"/></div>

                        <div style="width: 450px; padding-center: 20px;">

                                      <form:select path="studie.id">
                                             <form:options items="${studie}" itemValue="id" itemLabel="title"/>
                                      </form:select>
                          </div>
                        <br/>

            <table width="100%" margin-bottom="15px">
                   <tr>
                     <td  width="50%" align="center">
                         <div id="labels"><spring:message code="form.councilContact"/></div><div id="redStar3" style="left:288px;">*</div>
                            <div style="margin-left: 5px;">
                                <form:textarea title="${council.councilContact}" path="councilContact" class="ckeditor"
                                id="councilContact" cols="60" rows="5" maxlength="3000"
                                          onkeypress="return isNotMax(event,this)" name="councilContact"/>
                                <textarea style="visibility: hidden;width: 0px;" id="text11" name="text11"  ></textarea>
                                <form:errors path="councilContact" cssClass="error" ></form:errors>
                            </div>
                    </td>
                        <td width="50%" align="center">
                            <div id="labels"><spring:message code="form.councilDescription"/></div><div id="redStar3" style="right:159px;">*</div>
                            <div style="margin-left: 5px;">
                                <form:textarea title="${council.councilDescription}" path="councilDescription" class="ckeditor" id="councilDescription" cols="60" rows="5" maxlength="3000"
                                           onkeypress="return isNotMax(event,this)" name="councilDescription"/>
                                <textarea style="visibility: hidden;width: 0px;" id="text1" name="text1"  ></textarea>
                                <form:errors path="councilDescription" cssClass="error" ></form:errors>
                            </div>
                    </td>
                </tr>
            </table>
            <br/>
            <div id="sbm" align="center">
                <input type="submit" value="<spring:message
                       code="form.submit.council"/>"/>
            </div>
        </form:form>
    </div>



