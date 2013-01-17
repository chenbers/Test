var mapsbs;
var geocoder = new google.maps.Geocoder();
var marker;
//var gdir;
var addressMarker;
var currentEventListener;
var streetViewPoint;
var streetSegments = Array();
var error = false;
var errorField = "";
var segmentSelected = null;
var custommap1;
var speedLayer;
var streetviewOverlay;
var entryPoint = 1;
var address;
var lat;
var lon;
var eventLatLng;
var iconImage;
var limit;
var check = false;
var cursor;
//StreetView stuff
    var panorama;
    var markers;
    var currentMarkerNum;
    var client;
    var row;
    
//Variables to capture the precise mouse-click lat/lng
    var preciseLat = null;
    var preciseLng = null;
    
 // cj - used in driverSpeed.xhtml (speedLimitChangeRequestPanel)
    function StreetSegment(){     
    	this.id = "";
    	this.polyline = null;
    	this.selected = false;
    	this.bcolor= null;
    	this.row = null;
  	
    	this.initialize = function(id,polyline,bcolor, row) {
 	
    		this.id = id;
    		this.polyline = polyline;
    		this.selected = false;
    		this.bcolor = bcolor;
    		this.row = row;
    	};
    }	
 
 function findSegment(id){
 
 	for (var i = 0;i<streetSegments.length;i++){
 		if (streetSegments[i].id==id)return streetSegments[i];
 	}
 	return null;
 }

	function prepareEventSpeedByStreet(eLat, eLon){
		
		lat = eLat;
		lon = eLon;
		entryPoint = 0;
	}
	function prepareGenericSpeedByStreet(street,city,state,zip){
		
		address = street+","+city+","+state+","+zip;
		entryPoint = 1;
	}
	function showSelected(row){
	
		if (segmentSelected){
		
	 		row.style.backgroundColor = '#DfDfF8';
	 	}
	}
	
 	function selectSegment(id) {
 	 
 	 	if (check) {
 	 		// Don't select row if it a child element picked up click first 
 	 		check = false;
 	 		return;
 	 	}
 		var streetSegment = findSegment(id);
 		
		if (streetSegment.selected){
		
//		  	streetSegment.polyline.setStrokeStyle({color:"#666666"});
		  	setPolyLineColor(streetSegment.polyline, "#666666");
			streetSegment.selected = false;
			segmentSelected = null;
				  		
	 		streetSegment.row.style.backgroundColor = streetSegment.bcolor;
		}
 		else {
 		
		  	deselectAllSegments();
 		
		  	setPolyLineColor(streetSegment.polyline, "#ff0000");
			streetSegment.selected = true;
			segmentSelected = streetSegment;

	 		streetSegment.row.style.backgroundColor = '#DfDfF8';

	 		panTo(streetSegment);
		}
 	}
  	function deselectAllSegments() {
  	
  		if (segmentSelected){
  			console.log('deselectAll');

		  	setPolyLineColor(segmentSelected.polyline, "#666666");
			segmentSelected.selected = false;
	  		
	 		segmentSelected.row.style.backgroundColor = segmentSelected.bcolor;
	 		
	 		segmentSelected = null;
  			console.log('deselectAll-done');
	 	}
 	}
  	function setPolyLineColor(polyline, color) {
  		polyline.setOptions( {
  			strokeColor : color
  		});
  	}
 	function hoverSegment(id, pan) {
 	 
 	 	if (!segmentSelected){
 	 	
	 		hoverDeselectAllSegments();
	 		
	 		var streetSegment = findSegment(id);
	 		
	 		if(streetSegment){
	 		
		  		setPolyLineColor(streetSegment.polyline, "#ff0000");
		  		streetSegment.row.style.backgroundColor = '#DfDfF8';
		 		
		 		if (pan){
		 			panTo(streetSegment);
		 		}
			}
		}
 	}
  	function hoverDeselectAllSegments() {
 	 
	 	if (!segmentSelected){
	 	
	 		for(var i=0; i<streetSegments.length; i++){
	 
		  		setPolyLineColor(streetSegments[i].polyline, "#666666");
		 		streetSegments[i].row.style.backgroundColor = streetSegments[i].bcolor;
	  		}
	  	}
 	}
  	
	function setUnableToGeocodeError(){
		document.getElementById("speedLimitChangeRequestTable:items:caption").innerHTML = "#{messages.sbs_badLatLng}"; 	  
	}	

	function initializeLocation(lat,lng, fullAddress) {
      
      	geocoder.geocode({'location': new google.maps.LatLng(lat, lng)}, function (result, status) {
      		if (status != google.maps.GeocoderStatus.OK) {
      			console.log('Reverse Geocoding failed for lat, lng: ' +  lat + ', ' + lng + " status: "+ status);
      			setUnableToGeocodeError();
      		}
      		else {
      			point = result[0].geometry.location;
		    	streetViewPoint = point;
		    	var fullAddress = result[0].formatted_address;
		    	addSegmentWithoutRerendering(point.y, point.x, fullAddress, mapsbs.getZoom(), 1);
		    }
      	});

      }
      
    // addAddressToMap() is called when the geocoder returns an
    // answer.  It adds a marker to the mapsbs with an open info window
    // showing the nicely formatted version of the address and the country code.
/*	
    function addAddressToList(result, status) {
      if (!response || response.Status.code != 200) {
    	  
    	  setUnableToGeocodeError();
      } else {
      
         place = response.Placemark[0];
        point = new GLatLng(place.Point.coordinates[1],
                            place.Point.coordinates[0]);
        streetViewPoint = point;
        var fullAddress = place.address;
        addSegmentWithoutRerendering(point.y, point.x, fullAddress, mapsbs.getZoom(), 1);
       }
    }
*/    
	function reverseGeocode(lat,lng, fullAddress, markerAtLatLng) {
		preciseLat = lat;
		preciseLng = lng;
      
		if (markerAtLatLng) {
			eventLatLng = new google.maps.LatLng(lat,lng);
			iconImage = markerAtLatLng;
			limit=10;
		}
		else {
			limit = 1;
		}
		geocoder.geocode({'location': new google.maps.LatLng(lat, lng)}, addAddressToMap);
	}
	
	function addMarker() {
		if (eventLatLng != null) {
			marker = new google.maps.Marker({ 
				position : eventLatLng, 
				map : mapsbs,
				icon : iconImage,
			});
		}
	}
     
    // addAddressToMap() is called when the geocoder returns an
    // answer.  It adds a marker to the mapsbs with an open info window
    // showing the nicely formatted version of the address and the country code.
    	function addAddressToMap(result, status) {
    		if (status != google.maps.GeocoderStatus.OK) {
    			console.log('Reverse Geocoding failed status: '+ status);
    			setUnableToGeocodeError();
    		}
    		else {
    			point = result[0].geometry.location;
    			streetViewPoint = point;
    			var fullAddress = result[0].formatted_address;
        
    			// If a mouse click, use it, if an address entered, use what google returned
    			if ( preciseLat == null && preciseLng == null ) {
    				addSegment(point.lat(), point.lng(), fullAddress, mapsbs.getZoom(), limit);  
    			} else {
    				addSegment(preciseLat, preciseLng, fullAddress, mapsbs.getZoom(), limit);
    				preciseLat = null;
    				preciseLng = null;
    			}
    		}
    	}

    // showLocation() is called when you click on the Search button
    // in the form.  It geocodes the address entered into the form
    // and adds a marker to the mapsbs at that location.
    	function showLocation(address) {
    		geocoder.geocode({'address' : address}, addAddressToMap);
    	}

    	
/*
 * cj in google v3 is this necessary?
     	
function goToStreetView(){

	var url="http://maps.google.com/maps?layer=c&cbll=";
	 	url+=streetViewPoint.lat();
		url+=",";
		url+=streetViewPoint.lng();
		url+="&cbp=1,0,,0,5";
		
//		alert("goToStreetView "+url);
	window.open(url,'Google Maps',null);
}
*/
    	
  function processMarkers(m) {
      markers = m;
      for (var i = 0; i < markers.length; i++) {
        addListener(markers[i].marker);
      }
    }

    function addListener(marker) {
 //     GEvent.addListener(marker, 'click', function() {
 //     openPanoramaBubble(marker);
 //    });
    }


 function openPanoramaBubble(marker) {
  var contentNode = document.createElement('div');
  contentNode.style.textAlign = 'center';
  contentNode.style.width = '600px';
  contentNode.style.height = '300px';
  contentNode.innerHTML = 'Loading panorama';
  marker.openInfoWindow("<div id='pano' style='width:400px;height:200px;'></div>", {maxContent: contentNode, maxTitle: "Full screen"});

  panorama = new GStreetviewPanorama(document.getElementById("pano"));
  panorama.setLocationAndPOV(marker.getLatLng(), null);
  GEvent.addListener(panorama, "newpano", onNewLocation);
  GEvent.addListener(panorama, "yawchanged", onYawChange); 

  var iw = mapsbs.getInfoWindow();
  GEvent.addListener(iw, "maximizeend", function() {
    panorama.setContainer(contentNode);  
    window.setTimeout("panorama.checkResize()", 5);
  });
}

 function openPanoramaBubblePolyline(point) {

  var contentNode = document.createElement('div');
  var panorama;
  contentNode.style.textAlign = 'center';
  contentNode.style.width = '600px';
  contentNode.style.height = '300px';
  contentNode.innerHTML = 'Loading panorama';
   var smallNode = document.createElement('div');
  smallNode.style.width = '200px';
  smallNode.style.height = '200px';
  smallNode.id = 'pano';
  mapsbs.openInfoWindow(point,smallNode, {maxContent: contentNode, maxTitle: "Full screen"});
  
//   mapsbs.openInfoWindow(point,"<div id='pano' style='width:400px;height:200px;'></div>", {maxContent: contentNode, maxTitle: "Full screen"});

  panorama = new GStreetviewPanorama(smallNode);
  panorama.setLocationAndPOV(point, null);
  GEvent.addListener(panorama, "newpano", onNewLocation);
  GEvent.addListener(panorama, "yawchanged", onYawChange); 
 
  var iw = mapsbs.getInfoWindow();
  GEvent.addListener(iw, "maximizeend", function() {
    panorama.setContainer(contentNode);  
    window.setTimeout("panorama.checkResize()", 5);
  });
  
}
 function onYawChange(newYaw) {
/*  var GUY_NUM_ICONS = 16;
  var GUY_ANGULAR_RES = 360/GUY_NUM_ICONS;
  if (newYaw < 0) {
    newYaw += 360;
  }
  guyImageNum = Math.round(newYaw/GUY_ANGULAR_RES) % GUY_NUM_ICONS;
  guyImageUrl = "http://maps.google.com/intl/en_us/mapfiles/cb/man_arrow-" + guyImageNum + ".png";
  marker.setImage(guyImageUrl);*/
}

function onNewLocation(lat, lng) {
//  var latlng = new GLatLng(lat, lng);
//  marker.setLatLng(latlng);
}

function validate(field, min,max){

	var input = parseInt(field.value);
	
	if (isNaN(input)||(input < min)||(input > max)){
	
		field.value = "";
		error = true;
		errorField = field;
//		document.updateForm.submit.disabled = true;
		return;
	}
	else {
	
		field.value = ""+input;
		error = false;
		errorField = "";
//		document.updateForm.submit.disabled = false;
	}
}
//	function clearList(){
		
//		deselectAllSegments();
//		streetSegments = new Array();
//		document.getElementById("addressDiv").innerHTML = '<font color="red">click on the mapsbs to select a street to update.</font>';
//		mapsbs.clearOverlays();
//		marker = null;
//	    document.getElementById("status").innerHTML = '';
//	  	if (speedLayer) {
	  
//			mapsbs.addOverlay(speedLayer);
//		}
////		if (streetviewOverlay) {
		
////		    mapsbs.addOverlay(streetviewOverlay);
////		}	
//	}
	function makeMarker(point){
	
     	marker = new GMarker(point,{draggable: true});
      	mapsbs.addOverlay(marker);
      	
/*       GEvent.addListener(marker, "click", function() {
  			openPanoramaBubble(marker);
       });*/
       
       GEvent.addListener(marker, "dragstart", function() {
         mapsbs.closeInfoWindow();
       });
       
       GEvent.addListener(marker, "dragend", function() {
       
 		  var latlng = marker.getLatLng();
 		  var latlngString = ""+latlng.lat+","+latlng.lng;
		  reverseGeocode(latlng.lat(),latlng.lng());
     });
 	}

// cj - used in driverSpeed.xhtml (speedLimitChangeRequestPanel)
	function whichSegment(overlay){
		
       	for (var i=0;  i < streetSegments.length; i++) {
       	
        	if ((overlay == streetSegments[i].polyline)) {
       		
       			return streetSegments[i];
       		}
       	}
       	return null;
	}
	
	function createStreetLayer(){
	
		var streetsHyb = new GTileLayer(new GCopyrightCollection(''),1,17);
		streetsHyb.myLayers="streets";
		streetsHyb.myFormat="image/png";
		streetsHyb.myBaseURL="http://csr.iwiglobal.com:8086/cgi-bin/mapserv?mapsbs=/var/www/mapserver/saltlake/postgres.mapsbs&";
		streetsHyb.getTileUrl=CustomGetTileUrl;
		var layer1=[G_NORMAL_MAP.getTileLayers()[0],streetsHyb];
		custommap1 = new GMapType(layer1, G_SATELLITE_MAP.getProjection(), "Speed Limits", G_SATELLITE_MAP);
		
//		mapsbs.addMapType(custommap1);
		
		speedLayer = new GTileLayerOverlay(streetsHyb);
		mapsbs.addOverlay(speedLayer);
	}
	
	function refreshStreetLayer(){
	
		mapsbs.removeOverlay(speedLayer);
		createStreetLayer();
	}
	function toggleSpeed() {
	
	  if (!speedLayer) {
	  
			createStreetLayer();
	  } else {
	  
	    mapsbs.removeOverlay(speedLayer);
	    speedLayer = null;
	  }
	}
function toggleStreetView() {
 /* if (!streetviewOverlay) {
    streetviewOverlay = new GStreetviewOverlay();
    mapsbs.addOverlay(streetviewOverlay);
  } else {
    mapsbs.removeOverlay(streetviewOverlay);
    streetviewOverlay = null;
  }*/
}
function panTo(segment){

    var segPath = segment.polyline.getPath();
    var vertexCount = segPath.getLength();
    var half = (vertexCount+vertexCount%2)/2;
    
    mapsbs.panTo(segPath.getAt(half));

}

function updateWait(){
	document.getElementById("addressDiv").innerHTML = 'Updating database....It could take several minutes';
}
function deleteSelected(){
  	for (var i=streetSegments.length-1;  i >=0; i--) {
  		
  		var streetSegment = streetSegments[i];
   			alert("deleteSelected "+i);
  	
   		if (document.getElementById("speedLimitChangeRequestTable:items:"+i+":c") && document.getElementById("speedLimitChangeRequestTable:items:"+i+":c").checked) {
  			alert("deleteSelected checked  "+i);
  			if (streetSegment == segmentSelected) segmentSelected = null;
  			streetSegments[i].polyline.hide();
  			
  		}
  	}
  	showSelected();
  	
}
function clearSegments() {
	if (streetSegments) {
		for (var i = 0; i < streetSegments.length; i++) {
			streetSegments[i].polyline.setMap(null);
		}
	}
}
