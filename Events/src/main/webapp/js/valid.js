 $("#jform").on("submit",function(event){

                                                    document.getElementById('text1').innerHTML = CKEDITOR.instances.text.getData();

                                                });
                    $(document).ready(function() {
            $.validator.setDefaults({ ignore: [] });
                    $('#jform').validate({
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
                    minlength:  "<spring:message code="val.title.minlength"/>",
                    maxlength:  "<spring:message code="val.title.maxlength"/>"
            },
                    text1: {
            required: "<spring:message code="val.required"/>",
                    minlength:  "<spring:message code="val.text.minlength"/>",
                    maxlength:  "<spring:message code="val.text.maxlength"/>"
            }
            },
                    highlight: function(label) {
            $(label).removeClass("invisiblevalid");
                    $(label).closest('.control-group').addClass('error');
            },
                    success: function(label) {
            $(label).removeClass("error");
                    label.addClass("invisiblevalid");
            }
            });
            });