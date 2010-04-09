	var map;
	var tripIcons;
	
	// This set of arrays stores all the trip data
	var driverIDArray = new Array(); 	// Array of driverIDs
	var tripsSelected = new Array(); 	// Whether the driver's trips are currently selected or not.
	var overlaysArray = new Array();	// array of arrays of trip overlays - one array per trip
	var markersArray = new Array();		// array of arrays of event markers for each trip
	var boundsArray = new Array();		// map bounds for the drivers trips
	var colorArray = new Array();		// color for the driver
	
	var markerClustererMaxZoom = 12;	// maximum zoom for 
	var markerClustererWithMergedMarkerSets; 	// version of the MarkerClusterer that can manage sets of markers that can be independently shown/hidden
												// and can stack up to a configured number of overlapping markers instead showing them as a cluster
	var geocoder = null;	//Google geocoder for reverse geocoding event addresses.
	var clickedMarker = null;
	var countDown;
/**
 * Matches up color coding between the driver data table in the page, and the trip data arrays.
 * @param indexOnPage - row index in the page
 * @return return corresponding data array index if we already have trip data for the given row, -1 if not.
 */
	function findColorIndex(indexOnPage){
		
		for(i=0; i<colorArray.length;i++){

			if(colorArray[i] == indexOnPage) return i;
		}
		return -1;

	}
/**
 * Discovers if we already have trip data for the driverID
 * @param driverID
 * @return trip data index, or -1 if we don't have it yet.
 */
	function findDriver(driverID){

		for(i=0; i<driverIDArray.length;i++){

			if(driverIDArray[i] == driverID) return i;
		}
		return -1;
	}
	
/** 
 * Hides the trip route overlays and markers for the designated driver
 * 
 * @param driverIndex
 * @return
 */

	function hideOverlays(driverIndex){
		
		var length = overlaysArray[driverIndex].length;
		for (var j=0; j<length; j++){
			
			overlaysArray[driverIndex][j].hide();
		}

		if (markerClustererWithMergedMarkerSets!=null) {
			
			markerClustererWithMergedMarkerSets.hideMarkers(driverIndex);
		}
	}
/** 
 * Shows the trip route overlays and markers for the designated driver
 * 
 * @param driverIndex
 * @return
 */
	function showOverlays(driverIndex){
		
		var length = overlaysArray[driverIndex].length;
		for (var j=0; j<length; j++){
						
			overlaysArray[driverIndex][j].show();
		}

		if (markerClustererWithMergedMarkerSets!=null) {
			
			markerClustererWithMergedMarkerSets.showMarkers(driverIndex);
		}
	}

/**
 * Creates the HTML for the custom markers.
 * 
 * @param letter		- representing the driver
 * @param colorIndex	- representing the driver
 * @param tripNumber	
 * @param tripIcon		- icon for the marker's event
 * @return String		- of customized HTML
 */
	function getSingleLabeledMarkerLabel(letter,colorIndex, tripNumber, tripIcon){
		
    	return '<div style="position: relative; line-height: 1.5em; background-color:white; border: 1px solid black; width: 48px;height: 16px">'+ 
    	'<div style="height: 16px; width: 16px; background-color: ' + colorIndex + ';vertical-align:middle;text-align: center;float:left">'+letter+'</div>'+ 
    	'<div style="clear:left;position: absolute; text-align: center; vertical-align:middle; width: 48px; height:16px;top: 0; left: 0;'+
    	'background: transparent url('+tripIcon+') no-repeat center right;">' + 
    			tripNumber + '</div></div>'; 

	}
//	function getClusteredLabeledMarkerLabel(letter, colorIndex){
//    	return '<div style="position: relative; line-height: 2.0em; background-color:white; border: 1px solid black; width: 48px;">'+ 
//    	'<div style="height: 24px; width: 24px; background-color: ' + colorIndex + ';vertical-align:middle">'+letter+'</div>'+ 
//    	'<div style="position: absolute; text-align: center; vertical-align:middle; width: 24px; height:24px;top: 0; left: 24px;">***</div></div>'; 
//		
//	}

/**
 * Fallback option if the <canvas> tag is not going to work for clusters - not actually using it at the moment, but it is set as the "labelText" 
 * for the cluster marker in initTeamTripsClusters
 */
	function getClusterMarkerLabel(){
    	return '<div style="position: relative; line-height: 2.0em; background-color:white; border: 1px solid black; width: 48px;">'+ 
    	'<div style="height: 24px; width: 24px; background-color: #888888;vertical-align:middle">All</div>'+ 
    	'<div style="position: absolute; text-align: center; vertical-align:middle; width: 24px; height:24px;top: 0; left: 24px;"></div></div>'; 
		
	}

/**
 * Initializes the marker clusterer and the icons for the events in the markers
 * 
 * @param map - GMap2 Google map
 * @param icons - array of event icons
 * @return
 */
	function initTeamTripsClusters(map,icons){
		
		tripIcons = icons;

		var icon = new GIcon();
		icon.image = tripIcons[6];
		icon.iconSize = new GSize(42, 42);
		icon.iconAnchor = new GPoint(0, 0);
		icon.infoWindowAnchor = new GPoint(21, 21);
			
		var markerClustererOptions = {'maxStacked':4,
									  'customClusters':true,
									  'gridSize_x':100,
									  'gridSize_y':80};
		var customClusterOptions ={ 
	          	  "icon": icon,
	          	  "clickable": true,
	          	  "labelText": getClusterMarkerLabel(),
				  "canvasDrawFunction":drawCustomCluster,
				  "labelOffset": new GSize(0, 0),
	          	  "labelClass":"trips_markerLabel"
	          	};
				  
		markerClustererWithMergedMarkerSets = new MarkerClustererWithMergedMarkerSets(map,markerClustererOptions, customClusterOptions);
	}
	/**
	 * Creates the cluster image.  Draws and fills colorcoded offset circles for each driver that has events in the cluster.
	 * Draws the count of events in the cluster in the center of the top circle.
	 * Uses the <canvas> tag.  To work with IE need to include excanvas.js
	 * 
	 * @param displayColors - array of colors
	 * @param count	- number of events	
	 * @return
	 */
	function drawCustomCluster(displayColors, count) { 
	  
  	   clusterDiv = document.createElement("canvas");
  	   if (clusterDiv.getContext) { 
  	    
         var ctx = clusterDiv.getContext("2d"); 
          
	     var x              = 17+(displayColors.length-1)*5;     // x coordinate  
	     var y              = 17;        // y coordinate  
	     var radius         = 16;        // Arc radius  
	     var startAngle     = 0;         // Starting point on circle  
	     var endAngle       = 2*Math.PI; // End point on circle
	           
         ctx.strokeStyle="black";
         ctx.lineWidth=2;
         var length = displayColors.length;
         for (var i=0; i<length; i++){
         
 	     	ctx.fillStyle = displayColors[i]; 
         
         	 ctx.beginPath();
	         ctx.arc(x,y,radius,startAngle,endAngle, false); 
	         ctx.stroke();
             ctx.fill();
	         x-=5; 
	     }
         //write label
         ctx.beginPath();
         ctx.lineWidth=1;
         ctx.font = "12px";
         
         ctx.strokeStyle="black";
         
         var textMetric = ctx.measureText(count+"");
		 var dx = (34-textMetric.width)/2;
         ctx.strokeText(count,dx,20);
         ctx.closePath();
         
       }
       return clusterDiv;  
     }  
/**
 * Creates a custom marker for a trip event
 * 
 * Used for marker
 * @param point		 	- GLatLng of the marker/event
 * @param iconImage		- for us just the spacer x.gif as we are building the marker with CSS/html
 * @param label			- custom html for the marker
 * 
 * Used to retrieve event data for the marker infoWindow
 * @param driverID		- driver's ID
 * @param eventType		- start, end, in progress, violation, idling or tamper
 * @param tripNumber	- trip number for label
 * @param eventIndex	- event index in list
 * @param point 		- (used again)	GLatLng of the marker/event passed to event data 
 * 
 * @return LabeledMarker
 * 
 * This could be parameterized further to make it more generic eg iconSize, infoWindowAnchor, labelClass
 */
    function createLabeledMarker(point, iconImage, label, eventID) 
    {
    	//
    	var icon = new GIcon();
    	icon.image = iconImage;
    	icon.iconSize = new GSize(48, 16);
    	icon.iconAnchor = new GPoint(0, 0);
    	icon.infoWindowAnchor = new GPoint(25, 7);

    		
    	opts = { 
    	  "icon": icon,
    	  "clickable": true,
    	  "labelText": label,
    	  "labelOffset": new GSize(0, 0),
    	  "labelClass":"trips_markerLabel"
    	};
    	var marker = new LabeledMarker(point, opts);
		// Step 1 to display event data in a marker infoWindow.
  	  	var clickListener = GEvent.addListener(marker, "click", function() {
 
  	  	  clickedMarker = marker;
  		  // use a4j:jsFunction to go get the driver's name and the event time
  		  // callback for that call will start the reverse geocode for the address
  		  // the call back for that will display the bubble
  		    
  		  //Get event data from the backing bean
  	  	  getTripBubbleData(eventID);
   	  	});

    	return marker;


     	//
    }

/**
 * Step 2 to display event data in a marker infoWindow 
 * Function called oncomplete from the a4j:jsFunction getTripBubbleData 
 * which retrieves the event data.
 * 
 * @param eventData - JSON data from TeamTripsBean.EventData
 * @return
 */
    function getAddressForBubble(eventData){
    	
    	if (geocoder == null) geocoder = new GClientGeocoder();
    	var latlng = new GLatLng(eventData.lat, eventData.lng);
    	geocoder.getLocations(latlng, function(response){
    		var address = null;
	        if (!response || response.Status.code != 200) {
	        	 
	        	 address = "No address found";            	  
	        } 
	        else {
	        	  
	        	 address = response.Placemark[0].address;
	        }
	        
	        //Fill address element and display infowindow
		  	var windowElementTemplate = document.getElementById("bubbleElement");
		  	document.getElementById("teamBubbleForm:teamBubbleAddress").innerHTML=address;
			var windowElement = windowElementTemplate.cloneNode(true);	
		  	windowElement.style.display = 'block';
		  	clickedMarker.openInfoWindow(windowElement);
    	});
    }
///**
// * Step 3 to display event data in a marker infoWindow
// * Function called in the callback function on return from the geocoder request. 
// * Fills up the event bubble in the new_teamTrips.xhtml page using JSON data returned
// * by a4j:jsFunction getTripBubbleData
// * 
// * @param eventData - JSON data from TeamTripsBean.EventData
// * @param address - address returned by the geocoder
// * @return
// */   
//	function displayBubble(eventData, address){
//		
//	  	var windowElementTemplate = document.getElementById("bubbleElement");
//		
////	  	document.getElementById("teamBubbleForm:bubbleTitle").innerHTML = eventData.eventName;
////	  	document.getElementById("teamBubbleForm:teamBubbleDriver").innerHTML = eventData.driverName;
////	  	var pretty = document.getElementById("teamBubbleForm:teamBubbleDriverLink");
////	  	pretty.href = pretty.href+eventData.driverID;
//	  	document.getElementById("teamBubbleForm:teamBubbleAddress").innerHTML=address;
////	  	document.getElementById("teamBubbleForm:teamBubbleTime").innerHTML = eventData.timeString;
////	  	document.getElementById("teamBubbleForm:teamBubbleDescription").innerHTML = eventData.eventDescription;
//	  	
//		var windowElement = windowElementTemplate.cloneNode(true);	
//	  	windowElement.style.display = 'block';
//	  	clickedMarker.openInfoWindow(windowElement);
//	
//	}
    function getAddress(index){
	  	var addressElement = document.getElementById("clusterBubbleForm:clusterEvents:"+index+":clusterEventAddress");
	  	var latElement = document.getElementById("clusterBubbleForm:clusterEvents:"+index+":lat");
	  	var lngElement = document.getElementById("clusterBubbleForm:clusterEvents:"+index+":lng");
	
    	var latlng = new GLatLng(latElement.value, lngElement.value);
    	geocoder.getLocations(latlng, function(response){
    		
    		var address = null;
    		countDown--;
    		
	        if (!response || response.Status.code != 200) {
	        	 
	        	 address = "No address found";            	  
	        } 
	        else {
	        	  
	        	 address = response.Placemark[0].address;
	        }
    	  	addressElement.innerHTML=address;
	        if (countDown==0){
	        	var windowElementTemplate = document.getElementById("clusterBubbleTable");
	    		var windowElement = windowElementTemplate.cloneNode(true);	
	    	  	windowElement.style.display = 'block';
	    	  	clickedMarker.openInfoWindow(windowElement);
	        }

    	});
    }

    function addAddressesToClusterBubble(){
    	
		if (geocoder == null) geocoder = new GClientGeocoder();
	  	
	  	var i = 0;
	  	var numberOfEvents = jQuery('[id$=\\:clusterEventAddress]').length;
	  	countDown = numberOfEvents;
	  	
	  	for(var i= 0; i<numberOfEvents; i++){
	  		
	  		getAddress(i);
	  	}
    }
/**
 * creates all the trip route overlays (arrowed polylines) and event markers from the driverTrips data provided.
 * 
 * @param driverTrips - the trip data
 * @param driverIndex - the driver index
 * @return
 */
	 function createTripOverlays(driverTrips,driverIndex){
		 
			var overlays = overlaysArray[driverIndex];
			var markers = markersArray[driverIndex];
			var color = colorArray[driverIndex];
			
			if(tripsSelected[driverIndex]){
				
				var driverTripsLength = driverTrips.trips.length;							
				for(var j=0;j<driverTripsLength; j++){
					
					var tripNumber = j+1;
					// BDCCArrowedPolyline(points, color, weight, opacity, opts, gapPx, headLength, headColor, headWeight, headOpacity, headPointiness)
					var tripRoute = driverTrips.trips[j].route;
					var latLngArray = new Array();
					var tripRouteLength = tripRoute.length;
					for(var k=0; k<tripRouteLength; k++){
						
						latLngArray.push(new GLatLng(tripRoute[k].lat,tripRoute[k].lng));
					}
					var endlatlng = new GLatLng(driverTrips.trips[j].routeLastStep.lat, driverTrips.trips[j].routeLastStep.lng);
					latLngArray.push(endlatlng);
					
					var arrowPolyline = new BDCCArrowedPolyline(latLngArray,colors[color], 4, 0.9,{geodesic:true}, 
														-1, 15,"#000000",1,0.9,8);
					overlays.push(arrowPolyline);
					
					//Start of trip marker
					startlatlng = new GLatLng(driverTrips.trips[j].beginningPoint.lat, driverTrips.trips[j].beginningPoint.lng);
					marker = createLabeledMarker(startlatlng,tripIcons[6],
								getSingleLabeledMarkerLabel(labels[colorArray[driverIndex]],colors[colorArray[driverIndex]],tripNumber,tripIcons[0]),
								driverTrips.trips[j].startEventItem.eventID);
					markers.push({	eventID:driverTrips.trips[j].startEventItem.eventID,
			  						marker:marker});
					//End of trip marker
			
					var length = driverTrips.trips[j].violations.length;
					for(var k=0; k<length; k++){
						var violation = new GLatLng(driverTrips.trips[j].violations[k].latLng.lat, driverTrips.trips[j].violations[k].latLng.lng);
						marker = createLabeledMarker(violation,tripIcons[6],
									getSingleLabeledMarkerLabel(labels[color],colors[color],tripNumber,tripIcons[3]),
									driverTrips.trips[j].violations[k].eventID);
						markers.push({	eventID:driverTrips.trips[j].violations[k].eventID,
				  						marker:marker});
					}
					length = driverTrips.trips[j].idles.length;
					for(var k=0; k<length; k++){
						var idle = new GLatLng(driverTrips.trips[j].idles[k].lat,driverTrips.trips[j].idles[k].lng);
						marker = createLabeledMarker(idle,tripIcons[6],
									getSingleLabeledMarkerLabel(labels[color],colors[color],tripNumber,tripIcons[4]),
									driverTrips.trips[j].idles[k].eventID);
						markers.push({	eventID:driverTrips.trips[j].idles[k].eventID,
									  	marker:marker});
					}
					length = driverTrips.trips[j].tampers.length;
					for(var k=0; k<length; k++){
						var tamper = new GLatLng(driverTrips.trips[j].tampers[k].lat,driverTrips.trips[j].tampers[k].lng);
						marker = createLabeledMarker(tamper,tripIcons[6],
									getSingleLabeledMarkerLabel(labels[color],colors[color],tripNumber,tripIcons[5]),
									driverTrips.trips[j].tampers[k].eventID);
						markers.push({	eventID:driverTrips.trips[j].tampers[k].eventID,
							  			marker:marker});
					}
					
					if (driverTrips.trips[j].inProgress)
					{
						marker = createLabeledMarker(endlatlng,tripIcons[6],
									getSingleLabeledMarkerLabel(labels[color],colors[color],tripNumber,tripIcons[1]),
									driverTrips.trips[j].endEventItem.eventID);
						markers.push({	eventID:driverTrips.trips[j].endEventItem.eventID,
				  						marker:marker});
					}
					else
					{
						marker = createLabeledMarker(endlatlng,tripIcons[6],
									getSingleLabeledMarkerLabel(labels[color],colors[color],tripNumber,tripIcons[2]),
									driverTrips.trips[j].endEventItem.eventID);
						markers.push({	eventID:driverTrips.trips[j].endEventItem.eventID,
				  						marker:marker});
					}
				}
			}
			 
		 }
	 
/**
 * Calculates the map bounds for the provided set of overlays
 * 
 * @param overlays
 * @return GLatLngBounds
 */
	 function calculateBounds(overlays){

			if (overlays.length == 0){
				return null;
			}
			else{ 
				
				var bounds = new GLatLngBounds();
				var overlaysLength = overlays.length; 
				for(var j=0;j<overlaysLength; j++){
						
						bounds.extend(overlays[j].getBounds().getSouthWest());
						bounds.extend(overlays[j].getBounds().getNorthEast());
				}
				return bounds;
			}
		 }
	 
/**
 * Adds the route overlays for all the trips for the driver.
 * 
 * @param driverIndex
 * @return
 */
	 function addTripOverlaysToMap(driverIndex){
		 
		 var overlays = overlaysArray[driverIndex];
		 var overlaysLength = overlays.length; 
		 for(var j=0;j<overlaysLength; j++){
			 
			 map.addOverlay(overlays[j]);
		 }
	 }

/**
 * Calculates the bound for all the selected drivers' trips.
 * 
 * @return
 */
	 function calculateTripBounds(){
		 
		 var bounds = null;
		 var boundsPoints = new Array();
		 var boundsArrayLength = boundsArray.length;
		 for(var i=0; i<boundsArrayLength; i++){

			if((boundsArray[i] != null) && (tripsSelected[i])){
				 
				boundsPoints.push(boundsArray[i].getSouthWest());
				boundsPoints.push(boundsArray[i].getNorthEast());
			}
		 }
		 if (boundsPoints.length > 1){
			 bounds = new GLatLngBounds(boundsPoints[0], boundsPoints[1]);
			 var boundsPointsLength =boundsPoints.length;
			 for(var i=2; i < boundsPointsLength;i++){

				 bounds.extend(boundsPoints[i]);
			 }
		 }
		 return bounds;
	 }

/**
 * Prepares the map bounds and center, and then adds the trip routes and markers
 * for the driver's trips.
 * 
 * @param driverIndex
 * @return
 */
	 function showNewTrips(driverIndex){
		 
		 addTripOverlaysToMap(driverIndex);
		 bounds = calculateTripBounds();
		 
		 if (bounds != null){
			 
			 map.setZoom(map.getBoundsZoomLevel(bounds));
			 map.setCenter(bounds.getCenter());
			 markerClustererWithMergedMarkerSets.addMarkerSet({'displayColor':colors[colorArray[driverIndex]],
												'markerSet':markersArray[driverIndex]
				  								});
		 }
	 }
			 
/**
 * If this driver has never been selected add all the trip data
 * If the driver's data has previously been selected, but has been cleared set it up.
 * 
 * @param colorIndex
 * @param driverTrips
 * @return
 */
	function addDriverTrips(pageIndex, driverTrips){

		var i = findDriver(driverTrips.driverID);
		 
		//add data if it's not already here
		if (i == -1) {
			 
			 i = driverIDArray.length;
			 driverIDArray.push(driverTrips.driverID);
			 tripsSelected.push(true);
			 colorArray.push(pageIndex);
			 overlaysArray.push(new Array()); 	//just a place holder for now
			 markersArray.push(new Array());
			 createTripOverlays(driverTrips,i);
			 boundsArray.push(calculateBounds(overlaysArray[i]));
		}
		 //data has previously been sent but is being replaced
		else if (overlaysArray[i].length == 0){
			 
			 overlaysArray[i]=new Array(); 	//just a place holder for now
			 markersArray[i]=new Array();
			 tripsSelected[i] = true;			 
			 createTripOverlays(driverTrips,i);
			 boundsArray[i] = calculateBounds(overlaysArray[i]);
		}
		showNewTrips(i);
	 }
	 
/**
 * Shows existing trip data on the map for driver with index.
 * 
 * @param index - the index of the data arrays that represent this driver
 * @return
 */
	function showExistingDriverTrips(driverIndex){
		
		tripsSelected[driverIndex] = true;
		
		bounds = calculateTripBounds();
		if (bounds != null){
			
			map.setZoom(map.getBoundsZoomLevel(bounds));
			map.setCenter(bounds.getCenter());
			showOverlays(driverIndex);
		}
	}
	
/**
 * If the driver is being unselected, hides all the trips and markers on the map
 * If the driver has previously been selected will display the existing data
 * If the driver has not been previously selected, sets up all the trip data and
 *  displays it.
 * 
 * @param pageIndex - the row number in the page
 * @param driverTrips - trip data
 * @return
 */
	function processDriverTrips(pageIndex, driverTrips){
		
		// i will be the index of the data arrays that contains existing data
		// for this driver.
		var i = findColorIndex(pageIndex);
		
		if ((i>-1) && driverIDArray[i] && (overlaysArray[i].length != 0)){
			//Already have these markers
			if (tripsSelected[i]){

				tripsSelected[i] = false;
				hideOverlays(i);
			}
			else {
				
				showExistingDriverTrips(i);
			}
		}
		else{
			
			addDriverTrips(pageIndex,driverTrips);
		}
	}
/**
 * Clears out all the trip data, but retains the driver data
 * 
 * @return
 */	
	function clearDownTrips(){

		if(map){
			
			if (markerClustererWithMergedMarkerSets !== null) markerClustererWithMergedMarkerSets.clearAllMarkers();

			var overlaysArrayLength = overlaysArray.length;
			for(var i=0; i<overlaysArrayLength;i++){
				
				var overlaysArrayILength = overlaysArray[i].length;
				for (var j=0; j< overlaysArrayILength; j++){
					
					map.removeOverlay(overlaysArray[i][j]);
				}
				overlaysArray[i] = new Array();
				markersArray[i] = new Array();
			}
		}
	}	
/**
 * Displays the route overlays and event markers for an existing list of drivers
 * 
 * @param driversTrips 
 * @return
 */
	function redisplayAllTrips(driversTrips){
			
		var length = driversTrips.length;
		for (var i=0;i<length;i++){

			var colorIndex = findDriver(driversTrips[i].driverID);
			
			if(colorIndex > -1){
				
				processDriverTrips(colorArray[colorIndex],driversTrips[i]);
			}
		}
	}
/**
 * Keeps selected drivers, and refreshes their trip data either on 
 * time frame change or navigating back to this tab
 * 
 * @return
 */	
    function resetTripsForSelectedDrivers(){
    	
       	clearDownTrips();
       	getLatestTrips(); //a4j:jsFunction gets trip data from backing bean
    }
    