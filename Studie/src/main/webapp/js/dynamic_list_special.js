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
		container.append('<div class="specialty"><input type="text" name="faculties[].specialties[].nameOfSpecialties" value="" /><a href="#" class="removeSpecialty">Remove</a></div>');
		container.find('.specialty').find('input:last').select();
		container.find('.removeSpecialty:last').click(function() {
			$(this).closest('.specialty').remove();
			checkIndexing(parent, name);
		});
	}
	function addSpecClick() {
		$('.addSpecialty').each(function() {
			$(this).click(function() {
				var name = $(this).closest("tr").children(".faculIndex").find('input').attr('name').split('.')[0];
				addRow($(this).parent(), name);
				checkIndexing($(this).parent(), name);
			});
		});
	}
}