		var map = null;
	  	var markerClusterer;
		var markers = [];
	    var addressLatLng = null;
		var mapNeedsInit = true;
		var overviewMapControl;
		
	    var baseIcon = new GIcon();
			baseIcon.iconSize = new GSize(25, 30);
			baseIcon.iconAnchor = new GPoint(6, 20);
			baseIcon.infoWindowAnchor = new GPoint(5, 1);
			baseIcon.shadow=null;

	    function initMap() {
	       		
	      if (GBrowserIsCompatible()) {
	    	  
	    	  if (mapNeedsInit) {
	    	  
		       	map = new GMap2(document.getElementById("map-canvas"));
	        	geocoder = new GClientGeocoder();
	
		        addLayers();
	
		        map.addControl(new GLargeMapControl());
		        map.addControl(new GScaleControl());
		        map.addControl(new GMenuMapTypeControl(true));
				map.setCenter(mapDefaultLoc);
		        map.enableScrollWheelZoom();
	
				addListeners();
				
				bounds = new GLatLngBounds();
			    var marker = createMarker(mapDefaultLoc, "defaultMessage", null, null, null, 3);
				map.addOverlay(marker);
				bounds.extend(marker.getPoint());
			
				var node = document.getElementById("defaultMessage").cloneNode(true);
				node.style.display = 'block';
			    marker.openInfoWindow(node);
			
			    mapNeedsInit = false;

	    	  }
	      }
		}
		


//		function initMap()
//		{
//	        if (GBrowserIsCompatible())
//	        {
//		        if(mapNeedsInit == true)
//		        {
//		        	map = new GMap2(document.getElementById("map-canvas"));
//		        	geocoder = new GClientGeocoder();
//					map.addControl(new GLargeMapControl());
//					map.addControl(new GMapTypeControl());
	//			 		overviewMapControl = new GOverviewMapControl();
	//			 		map.addControl(overviewMapControl); 
	//					map.setMapType(G_NORMAL_MAP);
	//					map.setCenter(mapDefaultLoc);
	//			 		overviewMapControl.hide();
	//					bounds = new GLatLngBounds();

	//		    	    var marker = createMarker(mapDefaultLoc, "defaultMessage", null);
	//					map.addOverlay(marker);
	//					bounds.extend(marker.getPoint());
	//		
	//					var node = document.getElementById("defaultMessage").cloneNode(true);
	//		  			node.style.display = 'block';
	//		            marker.openInfoWindow(node);
		
	//		            mapNeedsInit = false;
	//		        }
	//        	}
	//		}

		function refresh()
		{
			processForm('liveFleetAddressTextBox', 'countComboBox');
		}
	    
   		function processForm(addressId, countId)
   		{
			//Get form elements
   			var address = document.getElementById(addressId).value;
   			var count = document.getElementById(countId).value;

   			// Clear all existing markers and zoom boundary.
			map.clearOverlays();
			bounds = new GLatLngBounds();
   			
			//Text field is blank, process form for only number of vehicles dropdown.
	   		if(address.length == 0)  
   			{
	   			notifyBean(mapDefaultLoc.lat(), mapDefaultLoc.lng(), count);
   				return;
   	   		}

	   	    if (geocoder) {
	   	        geocoder.getLatLng(
	   	          address,
	   	          function(point) {
	   	        	  if (!point) {
	   	        		  alert(address + " not found");
	   	        	  } else {
	  	     
		   				 //Create Marker
						 var marker = new GMarker(point);
						 GEvent.addListener(marker, "click", function() {
					         	marker.openInfoWindowHtml("<b>" + address + "</b>");  });
						 
						 map.addOverlay(marker);
						 bounds.extend(marker.getPoint());
						
						 //Immediately display info window.
						 marker.openInfoWindow("<b>" + address + "</b>");
						         	  
						 //Pass Geocode lookup LatLng and max count to Bean.
						 var loc = marker.getLatLng();
						 notifyBean(loc.lat(), loc.lng(), count);
	    	         }
	   	          }
	   	        );
	   	    }
   	    }

		/* Check if Enter pressed in form.
		 * Process form if user pressed enter in form fields.
		 */
   		function checkEnter(e)
   		{ 
   			var characterCode;

   			if(e.which){ 
   			e = e;
   			characterCode = e.which; //NN4's which property
   			}
   			else{
   			e = event;
   			characterCode = e.keyCode;//IE's keyCode property
   			}
   			
   			//if generated character code is equal to ascii 13 (if enter key)
   			if(characterCode == 13) {    				
					processForm('liveFleetAddressTextBox', 'countComboBox');
   					return false; }
   			else
   	   			{	return true;  }
   		}
