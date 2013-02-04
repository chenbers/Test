	var tripIcons;
	
	// This set of arrays stores all the trip data
	var driverIDArray = new Array(); 		// Array of driverIDs
	var tripsSelected = new Array(); 		// Whether the driver's trips are currently selected or not.
	var overlaysArray = new Array();		// array of arrays of trip overlays - one array per trip
	var markersArray = new Array();			// array of arrays of event markers for each trip
	var markerSetIndexArray = new Array();  // array for marker set indexes within the marker clusterer
	var boundsArray = new Array();			// map bounds for the drivers trips
	var colorArray = new Array();			// color for the driver
	
	var markerClustererMaxZoom = 12;	// maximum zoom for 
	var markerClustererWithMergedMarkerSets; 	// version of the MarkerClusterer that can manage sets of markers that can be independently shown/hidden
												// and can stack up to a configured number of overlapping markers instead showing them as a cluster
	var clickedMarker = null;
	
	function closeInfoWindow(map) {
		inthincMap.clearInfoWindow(map);
	}
/**
 * Matches up color coding between the driver data table in the page, and the trip data arrays.
 * @param indexOnPage - row index in the page
 * @return return corresponding data array index if we already have trip data for the given row, -1 if not.
 */
	function findColorIndex(indexOnPage){
		
		for(var i=0; i<colorArray.length;i++){

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

		for(var i=0; i<driverIDArray.length;i++){

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

	function hideOverlays(map,driverIndex){
		closeInfoWindow(map);
		
		var length = overlaysArray[driverIndex].length;
		for (var j=0; j<length; j++){
			overlaysArray[driverIndex][j].setMap(null);
		}

		if (markerClustererWithMergedMarkerSets!=null) {
			
			markerClustererWithMergedMarkerSets.hideMarkers(markerSetIndexArray[driverIndex]);
		}
	}
/** 
 * Shows the trip route overlays and markers for the designated driver
 * 
 * @param driverIndex
 * @return
 */
	function showOverlays(map, driverIndex){
		
		var length = overlaysArray[driverIndex].length;
		for (var j=0; j<length; j++){
			overlaysArray[driverIndex][j].setMap(map);
		}

		if (markerClustererWithMergedMarkerSets!=null) {
			
			markerClustererWithMergedMarkerSets.showMarkers(markerSetIndexArray[driverIndex]);
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
	function getSingleLabeledMarkerLabel(letter,letterColor, colorIndex, tripNumber, tripIcon){
		
    	return '<div style="position: relative; line-height: 1.5em; background-color:white; border: 1px solid black; width: 48px;height: 16px">'+ 
    	'<div style="height: 16px; width: 16px; color:'+letterColor+'; background-color: ' + colorIndex + ';vertical-align:middle;text-align: center;float:left">'+letter+'</div>'+ 
    	'<div style="clear:left;position: absolute; text-align: center; vertical-align:middle; width: 48px; height:16px;top: 0; left: 0;'+
    	'background: transparent url('+tripIcon+') no-repeat center right;">' + 
    			tripNumber + '</div></div>'; 

	}

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
 * @param map - google.maps.map api 3 Google map
 * @param icons - array of event icons
 * @return
 */
	function initTeamTripsClusters(map,icons){
		
		tripIcons = icons;
		var icon = {
			url : tripIcons[6],
			anchor : new google.maps.Point(0, 0),
			size : new google.maps.Size(42, 42)
		};
			
		var markerClustererOptions = {'minimumClusterSize':4,
									  'zoomOnClick' : false,
									  'gridSize': 80
//									  'gridSize_x':100,			// do we really need this?
//									  'gridSize_y':80
		};
		var customClusterOptions ={ 
				"canvasDrawFunction":drawCustomCluster,
      	};
				  
		markerClustererWithMergedMarkerSets = new MarkerClustererWithMergedMarkerSets(map, null, markerClustererOptions, customClusterOptions);
		
		
		google.maps.event.addListener(markerClustererWithMergedMarkerSets, "clusterclick", function(cluster, position) {
			var markers = cluster.markers_;
			if (markers.length > 10)
				return;
			var eventsJSONArray = "{'lat':" + position.lat() + ",'lng':" + position.lng() + ",";
			eventsJSONArray +="'events':[";
			for (var i=0;i<markers.length;i++){
				if(i > 0) {
					eventsJSONArray+=",";
				}
				eventsJSONArray+=markers[i].eventID;
			}
			eventsJSONArray+=']}';
			getEventData(eventsJSONArray);

		});

	}
	/**
	 * Creates the cluster image.  Draws and fills colorc oded offset circles for each driver that has events in the cluster.
	 * Draws the count of events in the cluster in the center of the top circle.
	 * Uses the <canvas> tag.  To work with IE need to include excanvas_b4.js
	 * 
	 * @param displayColors - array of colors
	 * @param count	- number of events	
	 * @param markerElement - the parent element for the marker
	 * @return
	 */
	function drawCustomCluster(markers, count, markerElement, pos) {
		
		var displayColors = [];	
		for (var i=0; i < markers.length; i++) {
			var colorCounted = false;
			for (var j = 0; j<displayColors.length; j++){
				if (markers[i].displayColor == displayColors[j]){
					colorCounted = true;
					break;
				}
			}
			if(!colorCounted) {
				displayColors.push(markers[i].displayColor);
			}
			colorCounted = false;
		}
	  

	    if (markers.length < 11) {
	    	markerElement.style.cursor = 'pointer';
	    }
	    var width = (35+(displayColors.length-1)*5); 
	    var height = 35; 
		
	    
	    markerElement.style.position = "absolute";
	    markerElement.style.top = (pos.y - parseInt(height / 2, 10)) + 'px';
	    markerElement.style.left = (pos.x - parseInt(width / 2, 10)) + 'px';
	    
  	   var clusterDiv = document.createElement("canvas");
  	   markerElement.appendChild(clusterDiv);
 	   
  	   if(typeof G_vmlCanvasManager != 'undefined'){
  		   
  		   clusterDiv = G_vmlCanvasManager.initElement(clusterDiv);
		   clusterDiv.style.width = width;
		   clusterDiv.style.height = height;
		   for(var i=0; i< clusterDiv.childNodes.length;i++){
			   
			   clusterDiv.childNodes[i].style.width = width + "px";
			   clusterDiv.childNodes[i].style.height = height + "px";
		   }
 	   }
  		   
	   clusterDiv.width = width;
	   clusterDiv.height = height;
	   
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
         ctx.textAlign="center";
         ctx.textBaseline="middle";
		 ctx.font = "14px Helvetica";         
         ctx.fillStyle="white";
         
         ctx.fillText(count,16,17);
         ctx.closePath();
         
       }
  	   

     }  
/**
 * Creates a custom marker for a trip event
 * 
 * Used for marker
 * @param point		 	- google.maps.LatLng of the marker/event
 * @param iconImage		- for us just the spacer x.gif as we are building the marker with CSS/html
 * @param label			- custom html for the marker
 * 
 * Used to retrieve event data for the marker infoWindow
 * @param driverID		- driver's ID
 * @param eventType		- start, end, in progress, violation, idling or tamper
 * @param tripNumber	- trip number for label
 * @param eventIndex	- event index in list
 * @param point 		- (used again)	google.maps.LatLng of the marker/event passed to event data 
 * 
 * @return LabeledMarker
 * 
 * This could be parameterized further to make it more generic eg iconSize, infoWindowAnchor, labelClass
 */
    function createLabeledMarker(point, iconImage, label, eventID) 
    {
		var icon = {
				url : iconImage,
				anchor : new google.maps.Point(0, 0),
				size : new google.maps.Size(48, 16)
			};

    	 var marker = new MarkerWithLabel({
    		 "icon": icon,
    		 "clickable": true,
    	     position: point,
    	     labelContent: label,
    	     labelClass: "trips_markerLabel"
    	});
    	 
    	marker.eventID = eventID;

		// Step 1 to display event data in a marker infoWindow.
  	  	google.maps.event.addListener(marker, "click", function() {
  	  		clickedMarker = marker;
  	  		getTripBubbleData(marker.eventID);
   	  	});
    	return marker;
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
		inthincMap.reverseGeocode(TeamTrips.map, eventData.lat, eventData.lng, function (result, status) {
			var address = null;
			if (status != google.maps.GeocoderStatus.OK) {
	        	 address = "No address found";            	  
	        } 
	        else {
	        	 address = result[0].formatted_address;
	        }
	        
		  	var pretty = document.getElementById("teamBubbleForm:teamBubbleDriverLink");
		  	pretty.href = pretty.href+eventData.driverID;

		  	document.getElementById("teamBubbleForm:bubbleTitle").innerHTML=eventData.eventName;
		  	document.getElementById("teamBubbleForm:teamBubbleTime").innerHTML=eventData.timeString;
		  	document.getElementById("teamBubbleForm:teamBubbleDriver").innerHTML=eventData.driverName;
		  	document.getElementById("teamBubbleForm:teamBubbleDescription").innerHTML=eventData.eventDescription;
		  	document.getElementById("teamBubbleForm:teamBubbleAddress").innerHTML=address;

		  	inthincMap.infoWindowAtMarker(TeamTrips.map, "bubbleElement", clickedMarker);
    	});
    }
/**
 * Multiple event infowindow.
 * 
 * @return
 */
    function showClusterBubble(map,clusterLatLng){
	  	inthincMap.infoWindowAtPosition(map, "clusterBubbleTable", new google.maps.LatLng(clusterLatLng.lat, clusterLatLng.lng));
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
					var tripRoute = driverTrips.trips[j].route;
					var latLngArray = new Array();
					var tripRouteLength = tripRoute.length;
					for(var k=0; k<tripRouteLength; k++){
						
						latLngArray.push(new google.maps.LatLng(tripRoute[k].lat,tripRoute[k].lng));
					}
					var endlatlng = new google.maps.LatLng(driverTrips.trips[j].routeLastStep.lat, driverTrips.trips[j].routeLastStep.lng);
					latLngArray.push(endlatlng);

					var arrowPolyline = new BDCCArrowedPolyline(latLngArray,colors[color], 4, 0.9,{geodesic:true}, -1, 15,"#000000",1,0.9,TeamTrips.map);
					overlays.push(arrowPolyline);
					
					//Start of trip marker
					startlatlng = new google.maps.LatLng(driverTrips.trips[j].beginningPoint.lat, driverTrips.trips[j].beginningPoint.lng);
					marker = createLabeledMarker(startlatlng,tripIcons[6],
								getSingleLabeledMarkerLabel(labels[colorArray[driverIndex]], textColors[color],colors[colorArray[driverIndex]],tripNumber,tripIcons[0]),
								driverTrips.trips[j].startEventItem.eventID);
					markers.push(marker);

					var length = driverTrips.trips[j].violations.length;
					for(var k=0; k<length; k++){
						var violation = new google.maps.LatLng(driverTrips.trips[j].violations[k].latLng.lat, driverTrips.trips[j].violations[k].latLng.lng);
						marker = createLabeledMarker(violation,tripIcons[6],
									getSingleLabeledMarkerLabel(labels[color], textColors[color], colors[color],tripNumber,tripIcons[3]),
									driverTrips.trips[j].violations[k].eventID);
						markers.push(marker);
					}
					length = driverTrips.trips[j].idles.length;
					for(var k=0; k<length; k++){
						var idle = new google.maps.LatLng(driverTrips.trips[j].idles[k].latLng.lat,driverTrips.trips[j].idles[k].latLng.lng);
						marker = createLabeledMarker(idle,tripIcons[6],
									getSingleLabeledMarkerLabel(labels[color],textColors[color],colors[color],tripNumber,tripIcons[4]),
									driverTrips.trips[j].idles[k].eventID);
						markers.push(marker);
					}
					length = driverTrips.trips[j].tampers.length;
					for(var k=0; k<length; k++){
						var tamper = new google.maps.LatLng(driverTrips.trips[j].tampers[k].latLng.lat,driverTrips.trips[j].tampers[k].latLng.lng);
						marker = createLabeledMarker(tamper,tripIcons[6],
									getSingleLabeledMarkerLabel(labels[color],textColors[color],colors[color],tripNumber,tripIcons[5]),
									driverTrips.trips[j].tampers[k].eventID);
						markers.push(marker);
					}
					if (driverTrips.trips[j].inProgress)
					{
						marker = createLabeledMarker(endlatlng,tripIcons[6],
									getSingleLabeledMarkerLabel(labels[color],textColors[color],colors[color],tripNumber,tripIcons[1]),
									driverTrips.trips[j].endEventItem.eventID);
						markers.push(marker);
					}
					else
					{
						marker = createLabeledMarker(endlatlng,tripIcons[6],
									getSingleLabeledMarkerLabel(labels[color],textColors[color],colors[color],tripNumber,tripIcons[2]),
									driverTrips.trips[j].endEventItem.eventID);
						markers.push(marker);
					}
				}
			}
			 
		 }
	 
/**
 * Calculates the map bounds for the provided set of overlays
 * 
 * @param overlays
 * @return google.maps.LatLngBounds
 */
	 function calculateBounds(overlays){

			if (overlays.length == 0){
				return null;
			}
			else{ 
				
				var bounds = new google.maps.LatLngBounds();
				var overlaysLength = overlays.length; 
				for(var j=0;j<overlaysLength; j++){
					bounds = bounds.union(overlays[i].getBounds());
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
	 function addTripOverlaysToMap(map, driverIndex){
		 
		 var overlays = overlaysArray[driverIndex];
		 var overlaysLength = overlays.length; 
		 for(var j=0;j<overlaysLength; j++){
			 overlays[j].setMap(map);
		 }
	 }

/**
 * Calculates the bound for all the selected drivers' trips.
 * 
 * @return
 */
	 function calculateTripBounds(){
		 var bounds = null;
		 var boundsArrayLength = boundsArray.length;
		 for(var i=0; i<boundsArrayLength; i++){
			 if((boundsArray[i] != null) && (tripsSelected[i])) {
				 if (bounds == null) {
					 bounds = new google.maps.LatLngBounds();
				 }
				 bounds = bounds.union(boundsArray[i]);
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
	 function showNewTrips(map,driverIndex){
		 
		 addTripOverlaysToMap(map,driverIndex);
		 bounds = calculateTripBounds();
		 
		 if (bounds != null){
			 
			 inthincMap.centerAndZoom(map, bounds);
			 var markerSetIndex = markerClustererWithMergedMarkerSets.addMarkerSet({
				 								'displayColor':colors[colorArray[driverIndex]],
												'markerSet':markersArray[driverIndex]
				  								});
			 if(markerSetIndexArray.length > driverIndex){
				 markerSetIndexArray[driverIndex] = markerSetIndex;
			 }
			 else {
				 markerSetIndexArray.push(markerSetIndex);
			 }
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
	function addDriverTrips(map, pageIndex, driverTrips){

		var i = findDriver(driverTrips.driverID);
		 
		//add data if it's not already here
		if (i == -1) {
			 
			 i = driverIDArray.length;
			 driverIDArray.push(driverTrips.driverID);
			 tripsSelected.push(document.getElementById("tripsTableForm:driversTrips:"+pageIndex+":checkDriver").checked);
			 colorArray.push(pageIndex%25);
			 overlaysArray.push(new Array()); 	//just a place holder for now
			 markersArray.push(new Array());
			 createTripOverlays(driverTrips,i);
			 boundsArray.push(calculateBounds(overlaysArray[i]));
		}
		 //data has previously been sent but is being replaced
		else if (overlaysArray[i].length == 0){
			 
			 overlaysArray[i]=new Array(); 	//just a place holder for now
			 markersArray[i]=new Array();
			 tripsSelected[i] = document.getElementById("tripsTableForm:driversTrips:"+pageIndex+":checkDriver").checked;			 
			 createTripOverlays(driverTrips,i);
			 boundsArray[i] = calculateBounds(overlaysArray[i]);
		}
		showNewTrips(map, i);
	 }
	 
/**
 * Shows existing trip data on the map for driver with index.
 * 
 * @param index - the index of the data arrays that represent this driver
 * @return
 */
	function showExistingDriverTrips(map, driverIndex){
		
		bounds = calculateTripBounds();
		if (bounds != null){
			inthincMap.centerAndZoom(map, bounds);
			showOverlays(map, driverIndex);
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
	function toggleDriverTrips(map, pageIndex, driverTrips){
		// i will be the index of the data arrays that contains existing data
		// for this driver.
		// Selection needs to go off whether the checkbox is checked rather than the selected array.
		var colorIndex = pageIndex%25;
		var i = findColorIndex(colorIndex);
		
		if ((i>-1) && driverIDArray[i] && (overlaysArray[i].length != 0)){
			
			//Already have these markers
			tripsSelected[i] = document.getElementById("tripsTableForm:driversTrips:"+pageIndex+":checkDriver").checked; 
			if (tripsSelected[i]){
				showExistingDriverTrips(map,i);
			}
			else {
				hideOverlays(map,i);
			}
		}
		else{
			addDriverTrips(map, pageIndex,driverTrips);
		}
	}

/**
 * Clears out all the trip data, but retains the driver data
 * 
 * @return
 */	
	function clearDownTrips(map){
		if(map){
			
			closeInfoWindow(map);
			
			if (markerClustererWithMergedMarkerSets !== null) {
				markerClustererWithMergedMarkerSets.clearAllMarkers();
			}

			var overlaysArrayLength = overlaysArray.length;
			for(var i=0; i<overlaysArrayLength;i++){
				
				var overlaysArrayILength = overlaysArray[i].length;
				for (var j=0; j< overlaysArrayILength; j++){
					overlaysArray[i][j].setMap(null);
				}
				overlaysArray[i] = new Array();
				markersArray[i] = new Array();
				markerSetIndexArray[i] = null;
			}
		}
	}
	
/**
 * Displays the route overlays and event markers for an existing list of drivers
 * 
 * @param driversTrips 
 * @return
 */
	function displayAllTrips(map, driversTrips){
	    clearDownTrips(map);
	    
		var length = driversTrips.length;
		for (var i=0;i<length;i++){
			addDriverTrips(map, driversTrips[i].driverIndex,driversTrips[i]);
		}
	}

/**
 * Displays the route overlays and event markers for an existing list of drivers
 * 
 * @param driversTrips 
 * @return
 */
	function redisplayAllTrips(map, driversTrips){
		
		closeInfoWindow(map);	
		var length = driversTrips.length;
		for (var i=0;i<length;i++){
			
			addDriverTrips(map, driversTrips[i].driverIndex,driversTrips[i]);
		}
	}
/**
 * Keeps selected drivers, and refreshes their trip data either on 
 * time frame change or navigating back to this tab
 * 
 * @return
 */	
    function resetTripsForSelectedDrivers(map){
    	
       	clearDownTrips(map);
       	getLatestTrips(); //a4j:jsFunction gets trip data from backing bean
    }
    