<%@ page import="ua.dp.stud.studie.model.KindOfCourse" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.servlet.ImageServletTokenUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include.jsp" %>
<html>
<body>
<portlet:resourceURL var="linkEdit" id="editKind"/>
<portlet:resourceURL var="linkRemove" id="removeKind"/>
    <div id="categoriesTable">
	    <c:if test="${not empty KindOfCourses}">
	        <c:forEach items="${KindOfCourses}" var="cat">
                <div id='categoria_${cat.typeId}'>
                    <input type="text" id='nameCategoria_${cat.typeId}' value="${cat.kindOfCourse}" disabled>
                    <div style="display:inline;" id='changeBut_${cat.typeId}' class="icon-pcppencil fs20" onclick="makeInputEditable(${cat.typeId});" aria-hidden="true"></div>
                    <div style="display:none; font-size:25px;" id='saveBut_${cat.typeId}' class="icon-pcpsave" onclick="rename('${cat.typeId}'); disableInput(${cat.typeId});" aria-hidden="true"></div>
                    <c:if test="${cat.countOfCourses==0}">
                        <div style="display:inline;" id='removeBut_${cat.typeId}' class="icon-pcpremove fs20" onclick="removeKind(${cat.typeId});" aria-hidden="true"></div>
                    </c:if>
                </div>
            </c:forEach>
		</c:if>
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
    </script>
</html>