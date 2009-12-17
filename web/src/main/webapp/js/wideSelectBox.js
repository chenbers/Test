	//<![CDATA[

  		// Disabling the select box stops the dropdown list happening 
  		var clickDown=false;
  		 function dropdown_mousedown(dropdown, listbox, newFocus) { 
  			 var focusStr = ''; 
  			 dropdown.disabled = true; 
  			 if (!listbox.disabled) { 
  				 clickDown = true; 
  				 focusStr = 'document.getElementById("' + dropdown.id + '").focus();' 
  			  } else { 
  				  newFocus.focus(); 
  		      } 
  		      setTimeout( 'document.getElementById("' + dropdown.id + '").disabled=false;' + focusStr ,10); 
  		} 

  		function dropdown_mouseup(dropdown, listbox) { 
  			if(!clickDown) { 
  				listbox.disabled=false;
  				listbox.style.top=dropdown.offsetHeight + findSelPosY(dropdown); // dropdown.offsetTop; 
  				listbox.style.left=findSelPosX(dropdown); // dropdown.offsetLeft;
  				listbox.style.zIndex=1000; 
  				 
  				for(var i = 0; i < dropdown.length;i++) { 
  					listbox.options[i] = new Option(dropdown.options[i].text, dropdown.options[i].value, dropdown.options[i].defaultSelected, dropdown.options[i].selected); 
  				} 
  				listbox.style.display='block'; 
  				listbox.focus(); 
  			} else { 
  				clickDown = false; 
  			} 
  		} 

  		function findSelPosY(obj) {
  			var posTop = 0;
  			while (obj.offsetParent) {
				if (!obj.className.match('rich-tabpanel-content-position'))
					posTop += obj.offsetTop; 
				obj = obj.offsetParent;
			}
  			return posTop;
  		}
  		function findSelPosX(obj) {
  			var posLeft = 0;
  			while (obj.offsetParent) {
				if (!obj.className.match('rich-tabpanel-content-position'))
					posLeft += obj.offsetLeft; 
				obj = obj.offsetParent;
			}
  			return posLeft;
  		}
  		  		

  		function select_click(dropdown, listbox) { 
  			dropdown.selectedIndex=listbox.selectedIndex; 
  			dropdown.focus(); 
  			listbox.style.display='none'; 
  			listbox.length = 0; 
  			listbox.disabled=true; 
  		} 

  		function select_blur(dropdown, listbox) { 
  			listbox.style.display='none'; 
  			listbox.disabled=true; 
  			if(!dropdown.disabled) { 
  				dropdown.focus(); 
  			} 
  		} 

  		function select_keypress(dropdown, listbox, event) { 
  			var charCode; 
  			if (event.keyCode) 
  				charCode = event.keyCode; 
  			else if (event.which) 
  				charCode = event.which; 
  			if ( (charCode == 13) || (charCode == 32)) { 
  				select_click(dropdown, listbox); 
  			} 
  		} 
  	//]]> 
