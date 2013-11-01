
$("#jform").on("submit",function(event){

                                document.getElementById('text1').innerHTML = CKEDITOR.instances.text.getData();

                            });


                    $(document).ready(function() {
            $.validator.setDefaults({ ignore: [] });
                    $('#jform').validate({



            rules: {
                topic: {
                    required: true,
                    minlength: 5,
                    maxlength: 100
                },
                text1: {
                    required: true,
                    minlength: 100,
                    maxlength: 10000
                },
                mainImage: {
                    required: true,
                    accept: "jpg|jpeg|png"
                }
            },
            messages: {
            topic: {
            required: "<spring:message code="news.topic.empty"/>",
                    minlength:  "<spring:message code="news.topic.between"/>",
                    maxlength:  "<spring:message code="news.topic.between"/>"
            },
                    text1: {
            required: "<spring:message code="news.text.empty"/>",
                    minlength:  "<spring:message code="news.text.between"/>",
                    maxlength:  "<spring:message code="news.text.between"/>"
            },
                    mainImage: {
            required: "<spring:message code="news.text.empty"/>",
                    accept: "<spring:message code="news.mainImage.accept"/>"
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
