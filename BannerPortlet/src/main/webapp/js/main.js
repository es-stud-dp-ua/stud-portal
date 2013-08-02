$(document).ready(
    function () {
        var size = $(".images > a > img").length;
        var slideWidth = 450;
        var time = 15000;
        var currentSlide = 0;

        $('.banner .images').attr('width', size * slideWidth);
        if (size > 1)
            for (i = 0; i < size; i++) {
                $("<li><a href=" + i + "></li>").appendTo($(".banner .pagination ul"));
            }
        timerFunction = function () {
            if (currentSlide < (size - 1)) {
                $('.banner .images').animate({'left': '-=' + (slideWidth) + ''});
                $(".banner .pagination li:nth(" + currentSlide + ")").removeClass("current");
                currentSlide++;
                $(".banner .pagination li:nth(" + currentSlide + ")").addClass("current");
            }
            else {
                $('.images').animate({'left': '+=' + ((size - 1) * slideWidth) + ''});
                $(".banner .pagination li:nth(" + currentSlide + ")").removeClass("current");
                currentSlide = 0;
                $(".banner .pagination li:nth(" + currentSlide + ")").addClass("current");
            }
        }

        $(".banner .pagination li:nth(" + currentSlide + ")").addClass("current");
        var timer = setInterval(timerFunction, time);

        function goToSlide(ind) {
            clearInterval(timer);
            $('.banner .images').animate({'left': '+=' + ((currentSlide - ind) * slideWidth) + ''});
            $(".banner .pagination li:nth(" + currentSlide + ")").removeClass("current");
            currentSlide = ind;
            $(".banner .pagination li:nth(" + currentSlide + ")").addClass("current");
        }

        $(".banner").mouseover
        (
            function (eventObject) {
                if (size > 1)    clearInterval(timer);
            }
        );
        $(".banner").mouseout
        (
            function (eventObject) {
                if (size > 1)    timer = setInterval(timerFunction, time);
            }
        )
        $(".banner .pagination a").click
        (
            function (eventObject) {
                var link = $(this).attr("href");
                goToSlide(link);
                return false;
            }

        )

    }
);