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
                span.innerHTML = ['<img id="cropbox" class="thumb" width="453px" src="', e.target.result,
                    '" title="', escape(theFile.name), '"/>'].join('');
                document.getElementById('list').insertBefore(span, null);
                a();
            };
            a();
        })(f);
		if(document.getElementById('img') != null)
			document.getElementById('img').parentNode.removeChild(document.getElementById('img'));
        // Read in the image file as a data URL.
        reader.readAsDataURL(f);
        a();
    }
    function isNotMax(e, id) {
        var validateValueTextArea = document.getElementById(id);
        validateValueTextArea.value = validateValueTextArea.value.substr(0, validateValueTextArea.getAttribute('maxlength'));
    }
$(document).ready(function () {
        
        $.datepicker.setDefaults($.datepicker.regional['ru']);
		document.getElementById('mainImage').addEventListener('change', handleFileSelect, false);
        $("#calendarDate").datepicker({dateFormat: 'yy-mm-dd'});
		$(".greenbtn").each(function() {
			$(this).click(function() {
				var fotoInput = $(this).parent().find(".nphotos");
				fotoInput.click();
			});
		});
    });