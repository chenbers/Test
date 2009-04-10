jQuery(document).ready( function() {
	/*
	 * Toggles the required indicator if in batch edit mode
	 */
	jQuery("input:checkbox").click( function() {
		var requiredFlagID = "#" + this.id + "_required_flag";
		requiredFlagID = requiredFlagID.replace(":", "\\:");
		var inputFieldID = this.id.replace("update_", "");

		var checked = this.checked;
		jQuery(requiredFlagID).attr("style", function(index) {
			if (checked) {
				return "display:inline";
			} else {
				return "display:none";
			}
		});

		if (!checked) {
			var inputID = this.id.replace("update_", "");
			inputID = inputID.replace("edit-form:", "");
			jQuery("input:text[id*='" + inputID + "']").val('');
			jQuery("select[id*='" + inputID + "']").val('');
		}

	});

	jQuery("input:checkbox").keydown( function() {
		checkMultiple(this);
	});

});

function checkMultiple(item) {
	var id;
	if (typeof (item) == "string")
		id = item;
	else
		id = item.id;

	var box;
	var index = id.indexOf(":");
	if (index > 0)
		box = document.getElementById("edit-form:update_"
				+ id.substring(index + 1));
	else
		box = document.getElementById("edit-form:update_" + id);
	if (box) {
		box.checked = true;
	}

	var span;
	if (index > 0)
		span = document.getElementById("edit-form:update_"
				+ id.substring(index + 1) + "_required_flag");
	else
		span = document.getElementById("edit-form:update_" + id
				+ "_required_flag");
	if (span) {
		span.style.display = "inline";
	}
}

for ( var i = 0; int < array.length; int++) {
	
}