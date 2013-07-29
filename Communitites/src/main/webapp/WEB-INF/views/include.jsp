<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page contentType="text/html" isELIgnored="false" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.CustomFunctions" %>
<%@ page import="ua.dp.stud.StudPortalLib.util.ImageService" %>

<% ImageService imageService = (ImageService) pageContext.findAttribute("imageService"); %>
<script type="text/javascript">
    $(document).ready(function () {

        $('#jform').validate({

            ignore: [],
            rules: {
                title: {
                    required: true,
                    minlength: 5,
                    maxlength: 100
                },
                text1: {
                    required: true,
                    minlength: 500,
                    maxlength: 10000
                }
            },
            messages: {
                title: {
                    required: "<spring:message code="val.required"/>",
                    minlength: "<spring:message code="val.title.minlength"/>",
                    maxlength: "<spring:message code="val.title.maxlength"/>"
                },
                text1: {
                    required: "<spring:message code="val.required"/>",
                    minlength: "<spring:message code="val.text.minlength"/>",
                    maxlength: "<spring:message code="val.text.maxlength"/>"
                }
            },
            highlight: function (label) {
                $(label).closest('.control-group').addClass('error');
            },
            success: function (label) {
                label
                        .text("OK").addClass('valid')
                        .closest('.control-group').addClass('success');
            }

        });
        $("#jform").on("submit", function (event) {
            event.preventDefault()
            $("#text1").text($(".cke_wysiwyg_frame").contents().find("body").html());
            console.error($(".cke_wysiwyg_frame").contents().find("body").html());
            console.error($("#text1"));
            console.error(this)
            if ($(this).valid()) {
                this.submit()
            }
        })
    });
</script>
