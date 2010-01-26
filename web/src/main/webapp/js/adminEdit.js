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

var formElements = [ "text", "checkbox", "radio", "select-one",
		"select-multiple", "textarea" ];

function checkMultiple(item) {
//	alert("checkmultiple");
	var id;
	if (typeof (item) == "string")
		id = item;
	else
		id = item.id;
	
	if (!id) return;

	var box;
	var index = id.lastIndexOf(":");
	var formName = "edit-form:update_";
	var relativeId = id.substring(index + 1);
	if (index > 0){
		formName = id.substring(0,index + 1);
		relativeId = "update_" + id.substring(index + 1,id.length);
	}
	
	var absoluteCheckBoxId = formName + relativeId;
	
	if (index > 0){
		box = document.getElementById(absoluteCheckBoxId);
	}else
		box = document.getElementById(absoluteCheckBoxId);
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

function adminSetCheckbox(item,formType,formContext,formIndex) {
//	alert("adminSetCheckbox: formContext " + formContext + " formType: " + formType + " formIndex: " + formIndex);
//	This function is similar to the above but is a substitute that should work
//		better relative to the unique id requirement for automation
	var id;
	if (typeof (item) == "string") {
		id = item;
	} else {
		id = item.id;
	}
	
	if (!id) return;

//	Find index 	
	var index = id.lastIndexOf(":");
	if (index > 0){		
		relativeId = id.substring(index + 1,id.length);		
	}	
	
//	Construct element name, find element, and set 
	var formElement = "edit-form:";	
	if (		formType == "speed") {
		formElement += formContext + relativeId;
		
	} else if (	formType == "redflag" ) {
		formElement += formContext + relativeId + "Selected";
		
	} else {
		formElement += "sensitivity:" + formIndex + ":" + formContext + "-update";
	}
//	alert("formElement " + formElement)
	if (index > 0){
		box = document.getElementById(formElement);	
	}
	
	if (box) {
		box.checked = true;
	}	
}