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
	$(element).find(".inner").each(function() {
		text += $(this).html();
	});
	return text;
}

function bindPopups() {
	$(".haveEvent").each(function () {
		var eventHolder = $(this).find(".pop-up");
		$(this).qtip({
			content:(function () {
				return getOutput(eventHolder)
			})(),
			position:{
				my:'bottom middle',
				at:'top middle'
			},
			hide:{
				fixed:true,
				delay:100
			},
			style:{
				widget:true,
				def:false
			}
		});
	});
}

function rewind(year, month, direction) {
	$.ajax({
		url:"${link}&mode=next",
		cache:false,
		data:{year:year, month:month, direction:direction},
		dataType:"html",
		type:"GET",
		contentType:"application/json;charset=utf-8",
		success:function (data) {
			var outerCalendarContainer = $("#outerCalendarContainer");
			outerCalendarContainer.html($('#outerCalendarContainer', data));
			setTableColumsSizes();
			bindPopups();
		}
	});
}

$(function () {
	setTableColumsSizes();
	bindPopups();
});