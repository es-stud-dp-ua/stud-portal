<%@ page import="ua.dp.stud.StudPortalLib.model.KindOfCourse" %>
<%@ page import="ua.dp.stud.studie.model.OnlineCourseType"   %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include.jsp" %>
<html>
<body>
<%@include file="leftBar.jsp" %>

 <c:if test="${classOfCourses =='course'}">

    <portlet:resourceURL var="linkEdit" id="editKind"/>
    <portlet:resourceURL var="linkRemove" id="removeKind"/>
    <portlet:resourceURL var="linkAdd" id="addKind"/>
</c:if>

 <c:if test="${classOfCourses =='onlineCourse'}">
    <portlet:resourceURL var="linkEdit" id="editOnlineKind"/>
    <portlet:resourceURL var="linkRemove" id="removeOnlineKind"/>
    <portlet:resourceURL var="linkAdd" id="addOnlineKind"/>
</c:if>


    <div id="categoriesTable">
	    <c:if test="${not empty KindOfCourses}">
	        <c:forEach items="${KindOfCourses}" var="cat">
                <div id='categoria_${cat.id}'>
                    <input type="text" id='nameCategoria_${cat.id}' value="${cat.kindOfCourse}" disabled>
                    <div style="display:inline;" id='changeBut_${cat.id}' class="icon-pcppencil fs20" onclick="makeInputEditable(${cat.id});" aria-hidden="true"></div>
                    <div style="display:none; font-size:25px;" id='saveBut_${cat.id}' class="icon-pcpsave" onclick="rename('${cat.id}');" aria-hidden="true"></div>
                    <c:if test="${cat.countOfCourses==0}">
                        <div style="display:inline;" id='removeBut_${cat.id}' class="icon-pcpremove fs20" onclick="removeKind(${cat.id});" aria-hidden="true">${cat.countOfCourses}</div>
                    </c:if>
                </div>
            </c:forEach>
		</c:if>
		<div id='categoria_new'>
            <input type="text" id='nameCategoria_new' value="">
            <div style="display:inline;" id='addBut_new' class="icon-pcpplus fs20" onclick="addKind($('#nameCategoria_new').val());" aria-hidden="true"></div>
        </div>
	</div>
 </body>
    <script>
    function makeInputEditable(id) {
        $('#nameCategoria_'+id).removeAttr('disabled');
        $('#saveBut_'+id).attr('style','display:inline; font-size:25px;');
        $('#changeBut_'+id).attr('style', 'display:none');
    }
    function disableInput(id) {
        $('#nameCategoria_'+id).attr('disabled', 'true');
        $('#changeBut_'+id).attr('style','display:inline;');
        $('#saveBut_'+id).attr('style', 'display:none');
    }
    function rename(id) {
            $.ajax({
                url: "${linkEdit}",
                cache: false,
                dataType: "html",
                data: {kindOfCourseId: id, nameKindOfCourse: $('#nameCategoria_'+id).val()},
                type: "GET",
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    disableInput(id);
                },
            });
    }
    function removeKind(id) {
            $.ajax({
                url: "${linkRemove}",
                cache: false,
                dataType: "html",
                data: {kindOfCourseId: id},
                type: "GET",
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    $('#categoria_'+id).remove();
                },
            });
    }
    function addKind(name) {
            $.ajax({
                url: "${linkAdd}",
                cache: false,
                dataType: "html",
                data: {nameKindOfCourse: name},
                type: "GET",
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    location.reload();
                },
            });
    }
    </script>
</html>