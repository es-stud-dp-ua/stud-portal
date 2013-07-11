function setTableColumsSizes() {
    var calendarColumns = $("#calendarTable tr").children();
    var columnsCount = calendarColumns.length;
    var sizePerColumn = 100.0 / columnsCount;
    sizePerColumn += "%";
    $(calendarColumns).each(function () {
        $(this).css("width", sizePerColumn);
    });
}

function getOutput(element) {
    var text = "";
    $(element).find(".inner").each(function () {
        text += $(this).html();
    });
    return text;
}

function bindPopups() {
    $(".haveEvent").each(function () {
        var eventHolder = $(this).find(".pop-up");
        $(this).qtip({
            content: (function () {
                return getOutput(eventHolder)
            })(),
            position: {
                my: 'bottom middle',
                at: 'top middle'
            },
            hide: {
                fixed: true,
                delay: 100
            },
            style: {
                widget: true,
                def: false
            }
        });
    });
}

$(function () {
    setTableColumsSizes();
    bindPopups();
});