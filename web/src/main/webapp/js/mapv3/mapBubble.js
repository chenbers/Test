// weird gloabl var
var currrentMarker = null;


var defaultAddressElement = null;
var bubbleElement = null;
var errorMessage = null;
var addressLookupAddressFormat = null;
var bounds = null; 
var markerClicked=false;
var mapLatLng = null;


// internal
    function addAddressToBubbleForMarker(map, latlng,addressElement, address){ 
    	document.getElementById(addressElement).innerHTML = address;
    	displayCurrentMarkerWindow(map);
    }
    function displayCurrentMarkerWindow(map){
 	
		var node = document.getElementById(bubbleElement).cloneNode(true);
			
		node.style.display = 'block';
		
		inthincMap.createInfoWindow(map, { 
			content : node,
			marker : currentMarker,
			addListener : false
		});
//    	inthincMap.createInfoWindowFromDiv(map, bubbleElement, latLng);
    }
    function addAddressToBubbleForMap(map, latlng,addressElement, address){
    	document.getElementById(addressElement).innerHTML = address;
		var node = document.getElementById(bubbleElement).cloneNode(true);
		
		node.style.display = 'block';
		inthincMap.createInfoWindow(map, { 
			content : node,
			position : latlng,
			addListener : false
		});
    }

	function reverseGeocodeAddress3(map, latlng, addressElement, callback) {	
			inthincMap.reverseGeocode(map, latlng.lat(), latlng.lng(), function(result, status) {
		    	  if (status != google.maps.GeocoderStatus.OK) {
		    		  
			 			var currentAddress = jQuery('#dispatchForm\\:foundZoneName').val();	
			 			var dat = currentAddress.split(",");
			 			var name = dat[0];
	            	    callback(map, latlng, addressElement, name);            	  
		    	  }
		    	  else {
		              if (callback != null){	            	 
		            	  callback(map, latlng, addressElement, result[0].formatted_address);
		              }
		              else if (addressElement != null){	            	  
			              addressElement.innerHTML = result[0].formatted_address;
		              }
		    		  
		    	  }
			});
	}
