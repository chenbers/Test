var map;
var marker;
//var gdir;
var geocoder = null;
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
var entryPoint;
var address;
var lat;
var lon;

//var baseURL="http://testteen.iwiglobal.com:8780/SpeedByStreet/servlet/iwiglobal";
//var baseURL="http://testteen.iwiglobal.com:8780/SpeedByStreet/servlet/iwiglobal";
//var baseURL="http://localhost:8080/SpeedByStreet/servlet/iwiglobal";
var baseURL="../servlet/iwiglobal";
//StreetView stuff
    var panorama;
    var markers;
    var currentMarkerNum;
    var client;
    
 //
 //Streetsegment
 	function StreetSegment(){     
 	this.id = "";
 	this.name = "";
 	this.zip = "";
 	this.cat = 0;
 	this.speedLimit = 0;
 	this.newSpeedLimit = 0;
 	this.polyline = null;
 	this.hipoly = null;
 	this.selected = false;
 	this.comment = "";
 	
 	this.initialize = function(id,name,zip,cat,speedLimit,polyline,hipoly) {
 	
	 	this.id = id;
	 	this.name = name;
	 	this.zip = zip;
	 	this.cat = cat;
	 	this.speedLimit = speedLimit;
	 	this.newSpeedLimit = 0;
	 	this.polyline = polyline;
	 	this.hipoly = hipoly;
	 	this.selected = false;
	}
	this.setNewSpeedlimit = function(newSpeedLimit) {
	
 	 	this.newSpeedLimit = newSpeedLimit;
 	}
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
		
    function initializeMap() {
    
      if (GBrowserIsCompatible()) {
        var opts = { onMarkersSetCallback: processMarkers, resultList : G_GOOGLEBAR_RESULT_LIST_SUPPRESS, showOnLoad: true};
          map = new GMap2(document.getElementById("map-area"), {googleBarOptions: opts});
	       map.setCenter(new GLatLng(40.723535,-111.9334),2);
 	       map.addControl(new GLargeMapControl());
 	       map.addControl(new GScaleControl()); 
	       map.addControl(new GMapTypeControl());
	       map.enableScrollWheelZoom();
 	       geocoder = new GClientGeocoder();
 	       
 	       if (entryPoint==1){
 	       
		       if (geocoder) {
			        geocoder.getLatLng(
			          address,
			          function(point) {
			            if (point) {
			            
			              	map.setCenter(point, 16);
			            }
			          }
			        );
	      	   }
	      	   
	       }
	       else {
	       
	       		reverseGeocode(lat,lon,"");
			   	map.setCenter(new GLatLng(lat,lon), 16);
	       }	       
	       
 	       
 	       createStreetLayer();
 	       			
//         var ovmap = new GOverviewMapControl(new GSize(200,200));
//        map.addControl(ovmap);
//		streetviewOverlay = new GStreetviewOverlay();
//        map.addOverlay(streetviewOverlay);
        map.enableGoogleBar();

//        client = new GStreetviewClient();
        streetSegments = new Array();
        
       	GEvent.addListener(map, "click", function(overlay, point) {

        if (overlay) {
          
          	var segment = whichSegment(overlay);
          	if (segment != null){
          	
 				selectSegment(segment.id);
 			}	
          } else {
          
            reverseGeocode(point.y,point.x,"");
            
	      }
        });
        
       	GEvent.addListener(map, "singlerightclick", function(point, source, overlay) {
          if (overlay) {
          	var segment = whichSegment(overlay);
          	if (segment != null){
          	
          		var point = segment.polyline.getVertex(0);
          	
				openPanoramaBubblePolyline(point);
 			}	
        }
      });
    }
}


	function showSelected(){
	
		if (segmentSelected){
		
	  		var row = document.getElementById("r"+segmentSelected.id);
	 		row.style.backgroundColor = '#DfDfF8';
	 	}
	}
	
 	function selectSegment(id) {
 	 
 		var streetSegment = findSegment(id);
 		
		if (streetSegment.selected){
		
 	 		streetSegment.hipoly.hide();
	  		streetSegment.polyline.show();
			streetSegment.selected = false;
			segmentSelected = null;
				  		
			var row = document.getElementById("r"+streetSegment.id);
	 		row.style.backgroundColor = '#ffffff';
		}
 		else {
 		
		  	deselectAllSegments();
 		
	 		streetSegment.polyline.hide();
	 		streetSegment.hipoly.show();
			streetSegment.selected = true;
			segmentSelected = streetSegment;	
	  		var row = document.getElementById("r"+id);
	 		row.style.backgroundColor = '#ff9999';
	 		
	 		panTo(streetSegment);
		}
 	}
  	function deselectAllSegments() {
  	
  		if (segmentSelected){

	 		segmentSelected.hipoly.hide();
	  		segmentSelected.polyline.show();
			segmentSelected.selected = false;
	  		
			var row = document.getElementById("r"+segmentSelected.id);
			if (row){
			
	 			row.style.backgroundColor = '#ffffff'
	 		}
	 		
	 		segmentSelected = null;
	 	}
 	}
 	function hoverSegment(id) {
 	 
 	 	if (!segmentSelected){
 	 	
	 		hoverDeselectAllSegments();
	 		
	 		var streetSegment = findSegment(id);
	 		
	 		if(streetSegment){
	 		
		 		streetSegment.polyline.hide();
		 		streetSegment.hipoly.show();
		 		var row = document.getElementById("r"+id);
		 		row.style.backgroundColor = '#ff9999'
		       	var vertexCount = streetSegment.polyline.getVertexCount();
		       	var half = (vertexCount+vertexCount%2)/2;
				map.panTo(streetSegment.polyline.getVertex(half));
			}
		}
 	}
  	function hoverDeselectAllSegments() {
 	 
	 	if (!segmentSelected){
	 	
	 		for(var i=0; i<streetSegments.length; i++){
	 
		 		streetSegments[i].hipoly.hide();
		  		streetSegments[i].polyline.show();
		  		
		  		var row = document.getElementById("r"+streetSegments[i].id);
		  		
		 		row.style.backgroundColor = '#ffffff'
	  		}
	  	}
 	}
 	
 /*     function displayLine(id) {
      
     	
           var request = GXmlHttp.create();

            request.open("GET", 'http://localhost:8080/SpeedByStreet/servlet/iwiglobal?srv=tigerChain&&id=' + id, true);
            request.onreadystatechange = function() {
             if (request.readyState == 4) {
                var xmlDoc = request.responseXML;
                var pointXml = xmlDoc.documentElement.getElementsByTagName("point");

                 var gLatLngArray = new Array();
                for (var i = 0; i < pointXml.length; i++) {
                  var lat = parseFloat(pointXml[i].getElementsByTagName("lat")[0].childNodes[0].nodeValue);
                  var lng = parseFloat(pointXml[i].getElementsByTagName("lng")[0].childNodes[0].nodeValue);
                  gLatLngArray.push(new GLatLng(lat,lng));
                }
                var polyline = new GPolyline(gLatLngArray, "#ff0000", 10);
                map.addOverlay(polyline);
				
              }
            } // function
            request.send(null);
       }*/

    function extractStreetSegments(lines, fullAddress){
    
           var id = lines[0].getElementsByTagName("id")[0].childNodes[0].nodeValue;
           
           var segcheck = findSegment(id);
           if(!segcheck){
            
            	//alert("full address is: "+fullAddress);
  	      		 if (fullAddress.length==0){
 	      		 
		         	 name = '? (street with no name)';
		         	 
			         if (lines[0].getElementsByTagName("name")[0].childNodes[0] != null) {
			         
			           name = lines[0].getElementsByTagName("name")[0].childNodes[0].nodeValue;
					}
			         var zip = '';
			         if (lines[0].getElementsByTagName("postalcode")[0].childNodes[0] != null) {
			           zip  = lines[0].getElementsByTagName("postalcode")[0].childNodes[0].nodeValue;
			         }
			         name+=(" "+zip);
			         geocoder.getLocations(name,  function(response){
			         
				           if (!response || response.Status.code != 200) {
						        alert("Sorry, we were unable to geocode that address");
						   } 
						   else {
	      		 				var seg = new StreetSegment();
						        place = response.Placemark[0];
						        
						       	name = place.address;
            					//alert("full address is: "+name);
						         var speedCat = '';
						         if (lines[0].getElementsByTagName("speed_cat")[0].childNodes[0] != null) {
						           speedCat  = lines[0].getElementsByTagName("speed_cat")[0].childNodes[0].nodeValue;
						         }
						         var frSpeedLimit ='';
						         var toSpeedLimit ='';
						         var speedLimit = '';
						         if (lines[0].getElementsByTagName("fr_spd_lim")[0].childNodes[0] != null) {
						           frSpeedLimit  = lines[0].getElementsByTagName("fr_spd_lim")[0].childNodes[0].nodeValue;
						         }
						         if (lines[0].getElementsByTagName("to_spd_lim")[0].childNodes[0] != null) {
						           toSpeedLimit  = lines[0].getElementsByTagName("to_spd_lim")[0].childNodes[0].nodeValue;
						         }
								speedLimit =''+Math.max(parseInt(frSpeedLimit,toSpeedLimit));
						        // Do polyline for segment    
						         var pointXml = lines[0].getElementsByTagName("point");
						         var gLatLngArray = new Array();
						         for (var j = 0; j < pointXml.length; j++) {
						           var lat = parseFloat(pointXml[j].getElementsByTagName("lat")[0].childNodes[0].nodeValue);
						           var lng = parseFloat(pointXml[j].getElementsByTagName("lng")[0].childNodes[0].nodeValue);
						           gLatLngArray.push(new GLatLng(lat,lng));
						         }
						         
						         var polyline = new GPolyline(gLatLngArray, "#666666", 10);
						         var hipoly = new GPolyline(gLatLngArray, "#DfDfF8", 10);
						        seg.initialize(id,name,zip,speedCat,speedLimit,polyline,hipoly);
						        streetSegments.push(seg);
						        
					      		map.addOverlay(seg.hipoly);
					      		seg.hipoly.hide();
						        map.addOverlay(seg.polyline);
						        
						        refreshSegments();
	 
	 							selectSegment(streetSegments[streetSegments.length-1].id);
						        
				         	}
		         	 	});
		         	 	
		         }
		         else {
		      		 var seg = new StreetSegment();
	 	      		 var name = fullAddress;
			         var speedCat = '';
			         if (lines[0].getElementsByTagName("speed_cat")[0].childNodes[0] != null) {
			           speedCat  = lines[0].getElementsByTagName("speed_cat")[0].childNodes[0].nodeValue;
			         }
			         var frSpeedLimit ='';
			         var toSpeedLimit ='';
			         var speedLimit = '';
			         if (lines[0].getElementsByTagName("fr_spd_lim")[0].childNodes[0] != null) {
			           frSpeedLimit  = lines[0].getElementsByTagName("fr_spd_lim")[0].childNodes[0].nodeValue;
			         }
			         if (lines[0].getElementsByTagName("to_spd_lim")[0].childNodes[0] != null) {
			           toSpeedLimit  = lines[0].getElementsByTagName("to_spd_lim")[0].childNodes[0].nodeValue;
			         }
					speedLimit =''+Math.max(parseInt(frSpeedLimit,toSpeedLimit));
			        // Do polyline for segment    
			         var pointXml = lines[0].getElementsByTagName("point");
			         var gLatLngArray = new Array();
			         for (var j = 0; j < pointXml.length; j++) {
			           var lat = parseFloat(pointXml[j].getElementsByTagName("lat")[0].childNodes[0].nodeValue);
			           var lng = parseFloat(pointXml[j].getElementsByTagName("lng")[0].childNodes[0].nodeValue);
			           gLatLngArray.push(new GLatLng(lat,lng));
			         }
			         
			         var polyline = new GPolyline(gLatLngArray, "#666666", 10);
			         var hipoly = new GPolyline(gLatLngArray, "#DfDfF8", 10);
			        seg.initialize(id,name,zip,speedCat,speedLimit,polyline,hipoly);
			        streetSegments.push(seg);
			        
		      		map.addOverlay(seg.hipoly);
		      		seg.hipoly.hide();
			        map.addOverlay(seg.polyline);
			        
			 		refreshSegments();
			 
			 		selectSegment(streetSegments[streetSegments.length-1].id);
			        	
		        }
		  	 }
		}
 /*    function reverseGeocode(lat,lng, fullAddress) {
     
        if (streetSegments.length >=5) return;
    
        document.getElementById("addressDiv").innerHTML = 'loading ...';

        var request = GXmlHttp.create();


      //  request.open("GET", 'http://localhost:8080/SpeedByStreet/servlet/iwiglobal?srv=tigerCompleteChains&lat=' + lat + '&lng=' + lng, true);

         request.open("GET", baseURL+'?srv=tigerCompleteChains&lat=' + lat + '&lng=' + lng, true);
        request.onreadystatechange = function() {
         if (request.readyState == 4) {
            var xmlDoc = request.responseXML;
            var lines = xmlDoc.documentElement.getElementsByTagName("line");
            extractStreetSegments(lines, fullAddress);
            
          }
        } // function
        request.send(null);
    }*/

    // addAddressToMap() is called when the geocoder returns an
    // answer.  It adds a marker to the map with an open info window
    // showing the nicely formatted version of the address and the country code.
    function addAddressToMap(response) {
 		clearList();
      if (!response || response.Status.code != 200) {
        alert("Sorry, we were unable to geocode that address");
      } else {
        place = response.Placemark[0];
        point = new GLatLng(place.Point.coordinates[1],
                            place.Point.coordinates[0]);
        streetViewPoint = point;
        
        var fullAddress = place.address;
        makeMarker(point);
                
        addSegment(point.y,point.x, fullAddress);
        
        marker.openInfoWindowHtml(place.address + '<br>' +
          '<b>Country code:</b> ' + place.AddressDetails.Country.CountryNameCode);
          
 		 window.setTimeout(function() {
		  marker.closeInfoWindow();
		}, 5000);
       }
    }

    // showLocation() is called when you click on the Search button
    // in the form.  It geocodes the address entered into the form
    // and adds a marker to the map at that location.
    function showLocation(address) {

      geocoder.getLocations(document.forms.speedLimitChangeRequestTable["speedLimitChangeRequestTable:address"].value, addAddressToMap);
    }

function goToStreetView(){

	var url="http://maps.google.com/maps?layer=c&cbll=";
	 	url+=streetViewPoint.lat();
		url+=",";
		url+=streetViewPoint.lng();
		url+="&cbp=1,0,,0,5";
		
//		alert("goToStreetView "+url);
	window.open(url,'Google Maps',null);
}
  
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

  var iw = map.getInfoWindow();
  GEvent.addListener(iw, "maximizeend", function() {
    panorama.setContainer(contentNode);  
    window.setTimeout("panorama.checkResize()", 5);
  });
}
 function openPanoramaBubblePolyline(point) {
  var contentNode = document.createElement('div');
  contentNode.style.textAlign = 'center';
  contentNode.style.width = '600px';
  contentNode.style.height = '300px';
  contentNode.innerHTML = 'Loading panorama';
  map.openInfoWindow(point,"<div id='pano' style='width:400px;height:200px;'></div>", {maxContent: contentNode, maxTitle: "Full screen"});

  panorama = new GStreetviewPanorama(document.getElementById("pano"));
  panorama.setLocationAndPOV(point, null);
  GEvent.addListener(panorama, "newpano", onNewLocation);
  GEvent.addListener(panorama, "yawchanged", onYawChange); 

  var iw = map.getInfoWindow();
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
  var latlng = new GLatLng(lat, lng);
  marker.setLatLng(latlng);
}

function validate(field, min,max){

	var input = parseInt(field.value);
	
	if (isNaN(input)||(input < min)||(input > max)){
	
		field.value = "";
		error = true;
		errorField = field;
		document.updateForm.submit.disabled = true;
		return;
	}
	else {
	
		field.value = ""+input;
		error = false;
		errorField = "";
		document.updateForm.submit.disabled = false;
	}
}
function updateSpeedLimits(){

	document.getElementById("status").innerHTML = 'updating ...';
//	var url = 'http://localhost:8080/SpeedByStreet/servlet/iwiglobal?srv=updateSpeedLimits'
	var url = baseURL+'?srv=updateSpeedLimits'
	var count = 0;
	for (var i=0; i<streetSegments.length; i++){
		var value = document.getElementById(streetSegments[i].id).value;
		if (value!=""){
			url+="&"+streetSegments[i].id+"="+streetSegments[i].speedLimit+"-"+value;
			count++;
		}
	}
	if (count > 0){
   var request = GXmlHttp.create();

    request.open("GET", url, true);
    request.onreadystatechange = function() {
    
	     if (request.readyState == 4) {
	     	document.getElementById("status").innerHTML = 'Update successful';
			for (var i=0; i<streetSegments.length; i++){
			
				var value = document.getElementById(streetSegments[i].id).value;
				if (value!=""){
				
					streetSegments[i].speedLimit = value;
				}
			}
			refreshSegments();
			refreshStreetLayer();
	     }
   	} // function
    request.send(null);
    }
	else {
	
	   document.getElementById("status").innerHTML = 'Nothing to update';
	}
	
}

function submitChangeRequests(){

	document.getElementById("status").innerHTML = 'updating ...';
	
	var count = 0;
	var link;
	var change;
	var comments;
	var address;
	var zip;
	
	for (var i=0; i<streetSegments.length; i++){
	
		link = "updateForm:changeRequest:"+count+":linkId";
		change = "updateForm:changeRequest:"+count+":change";
		comments = "updateForm:changeRequest:"+count+":comments";
		address = "updateForm:changeRequest:"+count+":address";
//		zip = "updateForm:changeRequest:"+count+":zip";
		
		var value = document.getElementById(streetSegments[i].id).value;
		
		if (value!=""){
		
			document.getElementById(link).value = streetSegments[i].id;

			document.getElementById(change).value=value;
			document.getElementById(comments).value = document.getElementById("t"+streetSegments[i].id).value;
			document.getElementById(address).value = streetSegments[i].name;
//			document.getElementById(zip).value = streetSegments[i].zip;
			count++;
		}
	}
	if (count > 0){
	
		document.forms.updateForm.submit();
    }
	else {
	
	   document.getElementById("status").innerHTML = 'Nothing to update';
	}
	
}
	function refreshSegments(){

		if (streetSegments.length > 0){
	        var html = '<table cellspacing="0" cellpadding="5" style="background: #ffffff"><tr class=restable><th></th><th style="text-align: left; font-size: 12px;">Street</th><th style="text-align: left; font-size: 12px;">MPH</th><th style="text-align: left; font-size: 12px;">New MPH</th><th style="text-align: left; font-size: 12px;">Comments</th></tr>';
	        for (var j = 0; j < streetSegments.length; j++) {
	        	
	        	var seg = streetSegments[j];
	        	
	        	if (parseInt(seg.speedLimit)==0){
	        	
	        		switch(parseInt(seg.cat)){
	        		case 1:
	        			seg.speedLimit = 75;
	        			break;
	        		case 2:
	        			seg.speedLimit = 75;
	        			break;
	        		case 3:
	        			seg.speedLimit = 60;
	        			break;
	        		case 4:
	        			seg.speedLimit = 50;
	        			break;
	        		case 5:
	        			seg.speedLimit = 40;
	        			break;
	        		case 6:
	        			seg.speedLimit = 30;
	        			break;
	        		case 7:
	        			seg.speedLimit = 20;
	        			break;
	        		case 8:
	        			seg.speedLimit = 5;
	        			break;
	        		default:
	        			seg.speedLimit = 40;
	        		}
	        	}
	     //      if (j % 2== 0) {
	             html = html + '<tr id="r'+seg.id+
	             '" onmouseover="hoverSegment('+
	             seg.id + ');" onmouseout="hoverDeselectAllSegments();" >';
	     //      } else {
	     //        html = html + '<tr class=\"odd\" id="r'+seg.id+'">';
	     //      }
	
	           html = html + '<td><input type="checkbox" id="c'+seg.id+'"></td> <td style="text-align: left; font-size: 12px;" onclick="selectSegment(' + seg.id + ');">' + seg.name + '</td>';
	           html = html + '<td style="text-align: center;  font-size: 12px;" onclick="selectSegment(' + seg.id + ');">' + 
	           seg.speedLimit + '</td><td style="text-align: center;  font-size: 12px;"><input type="text" maxlength="3" size="3" id="'+seg.id+'" onkeyup="validate(this,0,199)"/></td>'+
	           '<td style="text-align: center;  font-size: 12px;"><input type="text" maxlength="100" size="20" id="t'+seg.id+'"/></td></tr>';
		       
	        }
	
	        html = html + '<tr><td colspan=8><small>click on street name to see the segment on the map</small></td></tr>';
	        html = html + '</table>';
	
	        document.getElementById("addressDiv").innerHTML = html ;
	        	          
		    document.getElementById("status").innerHTML = '';
		}
		else {
		
			deselectAllSegments();
			document.getElementById("addressDiv").innerHTML = '<font color="red">click on the map to view reverse geocoded street names.</font>';
		}
	}	
	function clearList(){
		
		deselectAllSegments();
		streetSegments = new Array();
		document.getElementById("addressDiv").innerHTML = '<font color="red">click on the map to select a street to update.</font>';
		map.clearOverlays();
		marker = null;
	    document.getElementById("status").innerHTML = '';
	  	if (speedLayer) {
	  
			map.addOverlay(speedLayer);
		}
//		if (streetviewOverlay) {
		
//		    map.addOverlay(streetviewOverlay);
//		}	
	}
	function makeMarker(point){
	
     	marker = new GMarker(point,{draggable: true});
      	map.addOverlay(marker);
      	
/*       GEvent.addListener(marker, "click", function() {
  			openPanoramaBubble(marker);
       });*/
       
       GEvent.addListener(marker, "dragstart", function() {
         map.closeInfoWindow();
       });
       
       GEvent.addListener(marker, "dragend", function() {
       
 		  var latlng = marker.getLatLng();
 		  var latlngString = ""+latlng.lat+","+latlng.lng;
		  reverseGeocode(latlng.lat(),latlng.lng());
     });
 	}

	function whichSegment(overlay){
		
       	for (var i=0;  i < streetSegments.length; i++) {
       	
        	if ((overlay == streetSegments[i].polyline)||(overlay == streetSegments[i].hipoly)) {
       		
       			return streetSegments[i];
       		}
       	}
       	return null;
	}
	
	function createStreetLayer(){
	
		var streetsHyb = new GTileLayer(new GCopyrightCollection(''),1,17);
		streetsHyb.myLayers="streets";
		streetsHyb.myFormat="image/png";
		streetsHyb.myBaseURL="http://csr.iwiglobal.com:8086/cgi-bin/mapserv?map=/var/www/mapserver/saltlake/postgres.map&";
		streetsHyb.getTileUrl=CustomGetTileUrl;
		var layer1=[G_NORMAL_MAP.getTileLayers()[0],streetsHyb];
		custommap1 = new GMapType(layer1, G_SATELLITE_MAP.getProjection(), "Speed Limits", G_SATELLITE_MAP);
		
//		map.addMapType(custommap1);
		
		speedLayer = new GTileLayerOverlay(streetsHyb);
		map.addOverlay(speedLayer);
	}
	
	function refreshStreetLayer(){
	
		map.removeOverlay(speedLayer);
		createStreetLayer();
	}
	function toggleSpeed() {
	
	  if (!speedLayer) {
	  
			createStreetLayer();
	  } else {
	  
	    map.removeOverlay(speedLayer);
	    speedLayer = null;
	  }
	}
function toggleStreetView() {
 /* if (!streetviewOverlay) {
    streetviewOverlay = new GStreetviewOverlay();
    map.addOverlay(streetviewOverlay);
  } else {
    map.removeOverlay(streetviewOverlay);
    streetviewOverlay = null;
  }*/
}
function  deleteChecked(){

  	for (var i=streetSegments.length-1;  i >=0; i--) {
  		
  		var streetSegment = streetSegments[i];
  	
   		if (document.getElementById("c"+streetSegment.id).checked) {
  		
  			if (streetSegment == segmentSelected) segmentSelected = null;
  			map.removeOverlay(streetSegments[i].polyline);
  			map.removeOverlay(streetSegments[i].hipoly);
  			streetSegments.splice(i,1);
  		}
  	}
  	refreshSegments();
  	showSelected();
}	
function panTo(segment){

    var vertexCount = segment.polyline.getVertexCount();
    var half = (vertexCount+vertexCount%2)/2;
    map.panTo(segment.polyline.getVertex(half)); 
}

function updateByZipCat(){

    var request;
    if (window.XMLHttpRequest) { // Non-IE browsers
    
      request = new XMLHttpRequest();
    }
    else {
    
      request = new ActiveXObject("Microsoft.XMLHTTP");
    }
	if (request){
	
		document.getElementById("addressDiv").innerHTML = 'Updating database....It could take several minutes';
		var zip = document.forms.zipCatForm["zipCatForm:zip"].value;
		var cat = document.forms.zipCatForm["zipCatForm:cat"].value;
		var speed = document.forms.zipCatForm["zipCatForm:speed"].value;
	    request.open("GET", baseURL+'?srv=zipcat&zip=' + zip + '&cat=' + cat+ '&speed=' + speed, true);
	    request.onreadystatechange = function() {
	    
		    if (request.readyState == 4) {
		    
				document.getElementById("addressDiv").innerHTML = 'Update Successful';
		    
		    }
	    } // function
	    request.send(null);
	}
}
function updateWait(){
	document.getElementById("addressDiv").innerHTML = 'Updating database....It could take several minutes';
}
