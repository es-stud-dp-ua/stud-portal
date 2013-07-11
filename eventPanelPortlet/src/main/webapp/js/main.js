$(function () {
    $('.event-panel').tooltip();
});
$(document).ready(function () {
    $('.newEvent').each(function () {
        $(this).qtip(
            {
                content: {
                    text: '<div class="loader"><div class="bar"></div><div class="bar"></div><div class="bar"></div></div>',
                    ajax: {
                        url: $(this).attr('rel'),
                        dataType: "html",
                        type: "GET",
                        contentType: "application/json;charset=utf-8",
                        data: {modelView: $(this).attr('dataclass')},
                        success: function (data, status) {
                            this.set('content.text', $('#eventContainer', data));
                        }
                    },
                    title: {
                        text: $(this).attr('title'),
                        button: true
                    }
                },
                position: {
                    at: 'bottom center',
                    my: 'top center',
                    viewport: $(window),
                    effect: false
                },
                show: {
                    event: 'click',
                    solo: true
                },
                hide: 'unfocus',
                style: {
                    classes: 'Event-window'
                }
            })
    })
        .click(function (event) {
            event.preventDefault();
        });
});