// weird gloabl var
var currrentMarker = null;


var defaultAddressElement = null;
var bubbleElement = null;
var errorMessage = null;
var addressLookupAddressFormat = null;
var bounds = null; 
var markerClicked=false;
var geocoder = null;
var mapLatLng = null;

function orderOfCreation(marker,b) 
{
      return 1;
}	
	/* CreateMarker(GLatLng, divID, GIcon or null)
	 *	
	 * Creates a marker using specified GIcon or
	 * default icon if null is passed for iconImage.
	 * itemID is how you will locate the item from a map of items (Collection-type map not geographic-type map.) 
	 * fillBubbleMarker is a context specific a4j:jsfunction that will set up the bubble data and oncomplete will initiate the reverse geocode 
	 */
/*
    function createMarker(point, itemID, subID, iconImage, call, callFuncID ) 
    {
    	var markerIcon;
    	var marker;

		//Use passed in image.
    	if(iconImage != null)
    	{
			markerIcon = new GIcon(baseIcon);
       	    markerIcon.image = iconImage;
       	 	markerOptions = { icon:markerIcon,zIndexProcess:orderOfCreation  };
       	 	marker = new GMarker(point, markerOptions);
    	}
    	//Use default GoogleMap marker image.
    	else
    	{
			marker = new GMarker(point);
    	}
    	if (call != null){

	   		GEvent.addListener(marker, "click", function() {
	   			var latlng = marker.getLatLng();
	     	 	currentMarker = marker;	
		     	 
	     	 	// Server side call, do the bubble AFTER
	     	 	if ( subID == null ) {
	     	 		subID = -1;
	     	 	}
	   			lkFrZnBubble(latlng.lat(),latlng.lng(),0,0,itemID,subID,callFuncID);	
		 		});
    	}
        GEvent.addListener(marker, "infowindowclose", function() {
	            markerClicked = false;
        
        	});

        bounds.extend(marker.getPoint());

    	return marker;
    }
*/

    function addAddressToBubbleForMarker(latlng,addressElement, address){ 
    	document.getElementById(addressElement).innerHTML = address;
    	displayCurrentMarkerWindow();
    }
    function displayCurrentMarkerWindow(){  
		var node = document.getElementById(bubbleElement).cloneNode(true);
			
		node.style.display = 'block';
//		currentMarker.openInfoWindow(node);
		
		inthincMap.createInfoWindow({ 
			content : node,
			marker : currentMarker,
			addListener : false
		});

    }
    function addAddressToBubbleForMap(latlng,addressElement, address){
    	document.getElementById(addressElement).innerHTML = address;
		var node = document.getElementById(bubbleElement).cloneNode(true);
		
		node.style.display = 'block';
		inthincMap.createInfoWindow({ 
			content : node,
			position : latlng,
			addListener : false
		});
    }

    /*    

	function setUnableToGeocodeError(addressElement){		
	  	document.getElementById(addressElement).innerHTML = errorMessage; 	  
	  		
	}	
*/	
	function getAddressForCurrentMarker() {	
 	 	if((addressLookupAddressFormat == 1)||(addressLookupAddressFormat == 2)){
 	 		displayCurrentMarkerWindow();
   	 	}
 	 	else if (addressLookupAddressFormat == 3) { 
 	 		console.log('currentMarker pos : ' + currentMarker.getPosition());
 	 		console.log('defaultAddressElement : ' + defaultAddressElement);
 	 		reverseGeocodeAddress3(currentMarker.getPosition(), defaultAddressElement, addAddressToBubbleForMarker);
 	 	}
	}
	function getAddressForPosition(lat,lng){
 	 	if((addressLookupAddressFormat == 1)||(addressLookupAddressFormat == 2)){

  			var node = document.getElementById(bubbleElement).cloneNode(true);
  			
  			node.style.display = 'block';
  				
  			inthincMap.createInfoWindow({ 
  				content : node,
  				position : new google.maps.LatLng(lat,lng),
  				addListener : false
  			});
 	 	}
 	 	else if (addressLookupAddressFormat == 3){ 	 	
 	 		reverseGeocodeAddress3(new google.maps.LatLng(lat,lng),defaultAddressElement,addAddressToBubbleForMap);
 	 	}
	}
	/*	
	function getAddressForEvent(){
 	 	if((addressLookupAddressFormat == 1)||(addressLookupAddressFormat == 2)){

  			var node = document.getElementById(bubbleElement).cloneNode(true);
  			
  			node.style.display = 'block';
  			map.openInfoWindow(mapLatLng,node);
 	 	}
 	 	else if (addressLookupAddressFormat == 3){
 	 		
 	 		reverseGeocodeAddress(mapLatLng,defaultAddressElement,addAddressToBubbleForMap);
 	 	}
	}
*/	
	function reverseGeocodeAddress3(latlng, addressElement, callback) {	
/*		
		if (geocoder == null) geocoder = new GClientGeocoder();

    	geocoder.getLocations(latlng, function(response){
	         if (!response || response.Status.code != 200) {	 	       
	 			var currentAddress = jQuery('#dispatchForm\\:foundZoneName').val();	
	 			var dat = currentAddress.split(",");
	 			var name = dat[0];
            	  callback(latlng, addressElement, name);            	  
	          } 
	          else {
	              if (callback != null){	            	 
	            	  callback(latlng, addressElement, response.Placemark[0].address);
	              }
	              else if (addressElement != null){	            	  
		              addressElement.innerHTML = response.Placemark[0].address;
	              }
	          }
	    	});
*/
			inthincMap.reverseGeocode(latlng.lat(), latlng.lng(), function(result, status) {
		    	  if (status != google.maps.GeocoderStatus.OK) {
		    		  
			 			var currentAddress = jQuery('#dispatchForm\\:foundZoneName').val();	
			 			var dat = currentAddress.split(",");
			 			var name = dat[0];
	            	    callback(latlng, addressElement, name);            	  
		    	  }
		    	  else {
		              if (callback != null){	            	 
		            	  callback(latlng, addressElement, result[0].formatted_address);
		              }
		              else if (addressElement != null){	            	  
			              addressElement.innerHTML = result[0].formatted_address;
		              }
		    		  
		    	  }
			});
	}
/*	
	function fillTripBubble(itemID, subID){

	 	var addressElement = null;
	 	
		if (subID == 0){
			
			bubbleElement = "tripStartBubble";
			addressElement = "tripsMapForm:tripStartAddress";
		}
		else if (subID == 1){
			
			bubbleElement = "tripInProgressBubble";
			addressElement = "tripsMapForm:tripInProgressAddress";
		}
		else if (subID == 2){
			
			bubbleElement = "tripEndBubble";
			addressElement = "tripsMapForm:tripEndAddress";
		}
			
	 	if((addressLookupAddressFormat == 1)||(addressLookupAddressFormat == 2)){
	
	 		displayCurrentMarkerWindow()
	 	}
	 	else if (addressLookupAddressFormat == 3){

			reverseGeocodeAddress(currentMarker.getPoint(),addressElement,addAddressToBubbleForMarker);
 	 	}
	}

	function fillEventBubble(itemID,subID){
		
		bubbleElement = "eventBubble";
		defaultAddressElement = "tripsMapForm:bubbleAddress";

		fillBubbleMarker(itemID);
	}
	function fillEventBubbleFromList(itemID,lat,lng){
  	  	  
	  	  	 mapLatLng = new GLatLng(lat,lng);
			 bubbleElement = "eventBubble";
			 defaultAddressElement = "tripsMapForm:bubbleAddress";

			 fillBubbleMap(itemID);
	}
*/	