	function rowAdded(rowElement) {
		//clear the imput fields for the row
		$(rowElement).find("input").val('');
		$(rowElement).find('.specContainer:last').empty();
	}
    $(document).ready(function () {
		document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);
		new Dynamic_List_Special();
		var config = {
			rowClass : 'facultet',
			addRowId : 'addFaculty',
			removeRowClass : 'removeFaculty',
			formId : 'studyForm',
			rowContainerId : 'facultiesListContainer',
			indexedPropertyName : 'faculties',
			indexedPropertyMemberNames : 'nameOfFaculties',
			rowAddedListener : rowAdded
		};
		new DynamicListHelper(config);
    });

    function isNotMax(e, id) {
        var validateValueTextArea = document.getElementById(id);
        validateValueTextArea.value = validateValueTextArea.value.substr(0, validateValueTextArea.getAttribute('maxlength'));
    }