function a() {
        jQuery('#cropbox').Jcrop({onChange: setCoords, onSelect: setCoords, bgColor: 'black',
            bgOpacity: .4,
            setSelect: [100, 0, 253, 353],
            aspectRatio: 1});
    }
    function setCoords(c) {
        jQuery('#x1').val(c.x);
        jQuery('#y1').val(c.y);
        jQuery('#x2').val(c.x2);
        jQuery('#y2').val(c.y2);
        jQuery('#w').val(c.w);
        jQuery('#h').val(c.h);
    }
    function handleFileSelect(evt) {
        var files = evt.target.files; // FileList object

        // Loop through the FileList and render image files as thumbnails.
        var f = files[files.length - 1];

        // Only process im11age files.
        document.getElementById('list').innerHTML = '';
        var reader = new FileReader();
        // Closure to capture the file information.
        reader.onload = (function (theFile) {
            return function (e) {
                // Render thumbnail.
                var span = document.createElement('span');
                span.innerHTML = ['<img id="cropbox" class="thumb" src="', e.target.result,
                    '" title="', escape(theFile.name), '"/>'].join('');
                document.getElementById('list').insertBefore(span, null);
                a();
            };
            a();
        })(f);
        // Read in the image file as a data URL.
        reader.readAsDataURL(f);
        a();
    }
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
			addRowId : 'addFaculties',
			removeRowClass : 'removeFaculties',
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