function Dynamic_List_Special()
{
	init();
	
	function init() {
		addSpecClick();
		$('.removeSpecialty').each(function() {
			$(this).click( function() {
				var parent = $(this).closest('.specContainer').parent();
				var name = $(this).closest("tr").children(".faculIndex").find('input').attr('name').split('.')[0];
				$(this).closest('.specialty').remove();
				checkIndexing(parent, name);
			});
		});
	}
	
	function checkIndexing(parent, name) {
		var container = parent.find('.specContainer');
		container.find('input').each(function(index) {
			$(this).attr('name', name + '.specialties[' + index + '].nameOfSpecialties');
		});
	}
	
	function addRow(parent, name) {
		var container = parent.find('.specContainer');
		//todo: replace unicode with spring:message 
		container.append('<div class="specialty"><input type="text" name="faculties[].specialties[].nameOfSpecialties" value="" /><a href="#" class="removeSpecialty">\u0423\u0434\u0430\u043b\u0438\u0442\u044c \u0441\u043F\u0435\u0446\u0438\u0430\u043B\u044C\u043D\u043E\u0441\u0442\u044C</a></div>');
		container.find('.specialty').find('input:last').select();
		container.find('.removeSpecialty:last').click(function(event) {
			event.preventDefault();
			$(this).closest('.specialty').remove();
			checkIndexing(parent, name);
		});
	}
	function addSpecClick() {
		$('.addSpecialty').each(function() {
			$(this).click(function(event) {
				event.preventDefault();
				var name = $(this).closest("tr").children(".faculIndex").find('input').attr('name').split('.')[0];
				addRow($(this).parent(), name);
				checkIndexing($(this).parent(), name);
			});
		});
	}
}