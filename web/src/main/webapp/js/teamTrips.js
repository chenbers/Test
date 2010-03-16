	var map;
	var tripIcons;
	var driverIDArray = new Array();
	var tripsSelected = new Array();
	var overlaysArray = new Array();
	var markerClusterers = new Array();
	var markersArray = new Array();
	var boundsArray = new Array();
	var colorArray = new Array();
	var markerClustererMaxZoom = 12;
	var clickLatLng = null;
	
	var colors= ["#820f00","#ff4a12","#94b3c5","#74c6f1","#586b7a","#3e4f4f","#abc507","#eab239","#588e03",
				 "#8a8c81","#8173b1","#f99b49","#c6064f","#c4bdd9","#c8a77b"];
	var labels=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"];
	
	function findColorIndex(indexOnPage){
		
		for(i=0; i<colorArray.length;i++){

			if(colorArray[i] == indexOnPage) return i;
		}
		return -1;

	}
	function findDriver(driverID){

		for(i=0; i<driverIDArray.length;i++){

			if(driverIDArray[i] == driverID) return i;
		}
		return -1;
	}
	function driverSelected(driverID){
		
		var i = findDriver(driverID);
		if (i > -1) return tripsSelected[i];
		return false;
	}

	function hideOverlays(driverIndex){
		
		for (var j=0; j<overlaysArray[driverIndex].length; j++){
			
			overlaysArray[driverIndex][j].hide();
		}
		if (markerClusterers[driverIndex]!=null) {
			
			markerClusterers[driverIndex].clearMarkers();
		}
	}
	function showOverlays(driverIndex){
				
		for (var j=0; j<overlaysArray[driverIndex].length; j++){
						
			overlaysArray[driverIndex][j].show();
		}
	}

	function hideAllOverlays(){
		 
		for (var i=0; i<overlaysArray.length;i++){
			for (var j=0; j<overlaysArray[i].length;j++){
				
				overlaysArray[i][j].hide();
			}
		}
		for (var i=0; i<markerClusterers.length;i++){
				
			if (markerClusterers[i]!=null){
				
				markerClusterers[i].clearMarkers();
			}
		}
	}
	function createMarkers(index){
		
    	var icon = new GIcon();
    	icon.image = tripIcons[6];
    	icon.iconSize = new GSize(48, 24);
    	icon.iconAnchor = new GPoint(0, 0);
    	icon.infoWindowAnchor = new GPoint(25, 12);

    	var clusterOpts = { 
          	  "icon": icon,
          	  "clickable": true,
          	  "labelText": getClusteredLabeledMarkerLabel(labels[colorArray[index]],colors[colorArray[index]]),
          	  "labelOffset": new GSize(0, 0),
          	  "labelClass":"trips_markerLabel"
          	};

		markerClusterers[index] = new MarkerClustererWithStackedMarkers(map,
				markersArray[index],
				clusterOpts);

	}
	function showAllOverlays(){
		 
		for (var i=0; i<overlaysArray.length;i++){
			
			if(tripsSelected[i]){
				
				for (var j=0; j<overlaysArray[i].length;j++){
					
					overlaysArray[i][j].show();
				}
				createMarkers(i);
			}
		}
	}

	 function unselectDriver(driverID){

		 setDriverSelected(driverID,false);
	 }
	 
	function orderOfCreation(marker,b) 
	{
	      return 1;
	}
	function getSingleLabeledMarkerLabel(letter,colorIndex, tripNumber, tripIcon){
		
    	return '<div style="position: relative; line-height: 1.5em; background-color:white; border: 1px solid black; width: 48px;">'+ 
    	'<div style="height: 16px; width: 16px; background-color: ' + colorIndex + ';vertical-align:middle;text-align: center;">'+letter+'</div>'+ 
    	'<div style="position: absolute; text-align: center; vertical-align:middle; width: 48px; height:16px;top: 0; left: 0;'+
    	'background: transparent url('+tripIcon+') no-repeat center right;">' + 
    			tripNumber + '</div></div>'; 

	}
	function getClusteredLabeledMarkerLabel(letter, colorIndex){
    	return '<div style="position: relative; line-height: 2.0em; background-color:white; border: 1px solid black; width: 48px;">'+ 
    	'<div style="height: 24px; width: 24px; background-color: ' + colorIndex + ';vertical-align:middle">'+letter+'</div>'+ 
    	'<div style="position: absolute; text-align: center; vertical-align:middle; width: 24px; height:24px;top: 0; left: 24px;">***</div></div>'; 
		
	}
    function createLabeledMarker(point, iconImage, label) 
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
    	
    	GEvent.addListener(marker, "click", function() {
      	  marker.openInfoWindowHtml("I'm a Labeled Marker!");
      	});
//    	GEvent.addListener(marker, "mouseover", function() {
//        	  marker.showMapBlowup({zoomLevel:20});
//        	});

    	return marker;


     	//
    }
	
    function createTripMarker(point, iconImage) 
    {
    	var markerIcon;
    	var marker;
    	var baseIcon = new GIcon();
    	baseIcon.iconSize = new GSize(21, 20);
    	baseIcon.iconAnchor = new GPoint(6, 20);
    	baseIcon.infoWindowAnchor = new GPoint(5, 1);

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
	      	markerIcon = new GIcon();
			marker = new GMarker(point);
    	}
    	return marker;
    }

	 function createTripOverlays(driverTrips,index){
		 
			var overlays = overlaysArray[index];
			var markers = markersArray[index];

			if(tripsSelected[index]){
											
				for(var j=0;j<driverTrips.trips.length; j++){
					var tripNumber = j+1;
					// BDCCArrowedPolyline(points, color, weight, opacity, opts, gapPx, headLength, headColor, headWeight, headOpacity, headPointiness)
					var tripRoute = driverTrips.trips[j].route;
					var latLngArray = new Array();
					for(var k=0; k<tripRoute.length; k++){
						
						latLngArray.push(new GLatLng(tripRoute[k].lat,tripRoute[k].lng));
					}
					var endlatlng = new GLatLng(driverTrips.trips[j].routeLastStep.lat, driverTrips.trips[j].routeLastStep.lng);
					latLngArray.push(endlatlng);
					
					var arrowPolyline = new BDCCArrowedPolyline(latLngArray,colors[colorArray[index]], 4, 0.9,{geodesic:true}, 
														-1, 20,"#000000",1,0.9,8);
					
					overlays.push(arrowPolyline);
					//Start of trip marker
					startlatlng = new GLatLng(driverTrips.trips[j].beginningPoint.lat, driverTrips.trips[j].beginningPoint.lng);
					marker = createLabeledMarker(startlatlng,tripIcons[6],
								getSingleLabeledMarkerLabel(labels[colorArray[index]],colors[colorArray[index]],tripNumber,tripIcons[0]));
							markers.push(marker);
					//End of trip marker
			
					if (driverTrips.trips[j].inProgress)
					{
						marker = createLabeledMarker(endlatlng,tripIcons[6],
									getSingleLabeledMarkerLabel(labels[colorArray[index]],colors[colorArray[index]],tripNumber,tripIcons[1]));
					}
					else
					{
						marker = createLabeledMarker(endlatlng,tripIcons[6],
									getSingleLabeledMarkerLabel(labels[colorArray[index]],colors[colorArray[index]],tripNumber,tripIcons[2]));
					}
					markers.push(marker);
			
					for(var k=0; k<driverTrips.trips[j].violations.length; k++){
						var violation = new GLatLng(driverTrips.trips[j].violations[k].lat, driverTrips.trips[j].violations[k].lng);
						marker = createLabeledMarker(violation,tripIcons[6],
									getSingleLabeledMarkerLabel(labels[colorArray[index]],colors[colorArray[index]],tripNumber,tripIcons[3]));
						markers.push(marker);
					}
					for(var k=0; k<driverTrips.trips[j].idles.length; k++){
						var idle = new GLatLng(driverTrips.trips[j].idles[k].lat,driverTrips.trips[j].idles[k].lng);
						marker = createLabeledMarker(idle,tripIcons[6],
									getSingleLabeledMarkerLabel(labels[colorArray[index]],colors[colorArray[index]],tripNumber,tripIcons[4]));
						markers.push(marker);
					}
					for(var k=0; k<driverTrips.trips[j].tampers.length; k++){
						var tamper = new GLatLng(driverTrips.trips[j].tampers[k].lat,driverTrips.trips[j].tampers[k].lng);
						marker = createLabeledMarker(tamper,tripIcons[6],
									getSingleLabeledMarkerLabel(labels[colorArray[index]],colors[colorArray[index]],tripNumber,tripIcons[5]));
						markers.push(marker);
					}
				}
			}
			 
		 }
	 function calculateBounds(overlays){

			if (overlays.length == 0){
				return null;
			}
			else{ 
				
				var bounds = new GLatLngBounds();
				 
				for(var j=0;j<overlays.length; j++){
						
						bounds.extend(overlays[j].getBounds().getSouthWest());
						bounds.extend(overlays[j].getBounds().getNorthEast());
				}
				return bounds;
			}
		 }
	 function addTripOverlaysAndMarkersToMap(index){
		 
		 var overlays = overlaysArray[index];
		 
		 for(var j=0;j<overlays.length; j++){
			 
			 map.addOverlay(overlays[j]);
		 }
	 }
	 function setupMap(){
		 
		 var boundsPoints = new Array();
		 for(var i=0; i<boundsArray.length; i++){

			if((boundsArray[i] != null) && (tripsSelected[i])){
				 
				boundsPoints.push(boundsArray[i].getSouthWest());
				boundsPoints.push(boundsArray[i].getNorthEast());
			}
		 }
		 if (boundsPoints.length > 1){
			 var bounds = new GLatLngBounds(boundsPoints[0], boundsPoints[1]);

			 for(var i=2; i < boundsPoints.length;i++){

				 bounds.extend(boundsPoints[i]);
			 }
		 }
		 return bounds;
	 }
	 function showNewTrips(i){
		 
		 hideAllOverlays();
		 addTripOverlaysAndMarkersToMap(i);
		 bounds = setupMap();
		 var zoom = map.getZoom();
		 map.setZoom(map.getBoundsZoomLevel(bounds));
		 map.setCenter(bounds.getCenter());

		 showAllOverlays(zoom!=map.getZoom());
	 }
			 
	 function setDriverSelected(driverIndex, selected){
		 
		 if(driverIndex < 0) return;
		 
		 tripsSelected[driverIndex] = selected;
		 if(selected){
			 
			 showOverlays(driverIndex);
		 }
		 else{
			 
			 hideOverlays(driverIndex);
		 }
	 }

	 function addDriverTrips(colorIndex, driverTrips){

		 var i = findDriver(driverTrips.driverID);
		 
		 //add data if it's not already here
		 if (i == -1) {
			 
			 i = driverIDArray.length;
			 driverIDArray.push(driverTrips.driverID);
			 tripsSelected.push(true);
			 colorArray.push(colorIndex);
			 overlaysArray.push(new Array()); 	//just a place holder for now
			 markersArray.push(new Array());
			 markerClusterers.push(null);
			 createTripOverlays(driverTrips,i);
			 boundsArray.push(calculateBounds(overlaysArray[i]));
			 showNewTrips(i);
		 }
		 //data has previously been sent but is being replaced
		 else if (overlaysArray[i].length == 0){
			 
			 overlaysArray[i]=new Array(); 	//just a place holder for now
			 markersArray[i]=new Array();
			 tripsSelected[i] = true;
			 createTripOverlays(driverTrips,i);
			 boundsArray[i]=calculateBounds(overlaysArray[i]);
			 showNewTrips(i);
			 
		}
	 }
	function showExistingDriverTrips(index){
		
		hideAllOverlays();
		setDriverSelected(index, true);
		 bounds = setupMap();
		 var zoom = map.getZoom();
		 map.setZoom(map.getBoundsZoomLevel(bounds));
		 map.setCenter(bounds.getCenter());
		showAllOverlays(zoom!=map.getZoom());

	}
	function processDriverTrips(colorIndex, driverTrips){
		
		var i = findColorIndex(colorIndex);
		
		if ((i>-1) && driverIDArray[i] && (overlaysArray[i].length != 0)){
			
			if (tripsSelected[i]){
				
				unselectDriver(i);
			}
			else {
				
				showExistingDriverTrips(i);
			}
		}
		else{
			
			addDriverTrips(colorIndex,driverTrips);
		}
	}
	function redrawAllSelectedDriversTrips(){
		
		
		for(var i=0;i<driverIDArray.length;i++){
			
			if (tripsSelected[i]){
				showNewTrips(i);
//				addTripOverlaysAndMarkersToMap(i);
			}
		}
//		 bounds = setupMap();
//		 map.setZoom(map.getBoundsZoomLevel(bounds));
//		 map.setCenter(bounds.getCenter());
	}
	function clearOverlays(){
		
		for(var i=0; i<overlaysArray.length;i++){
			for (var j=0; j< overlaysArray[i].length; j++){
				
				map.removeOverlay(overlaysArray[i][j]);
			}
			overlaysArray[i] = new Array();
			
			if(markerClusterers[i] != null) {
				
				markerClusterers[i].clearMarkers();
			}
			for (var j=0; j< markersArray[i].length; j++){
				
				map.removeOverlay(markersArray[i][j]);
			}
			markersArray[i] = new Array();
		}
	}
	 function clearDownTrips(){

		if(map){
			
			clearOverlays();
		}
	 }	

	function redisplayAllTrips(drivers){
		//if no data has been returned then just redraw what we have
		if (drivers.length == 0){
			
			redrawAllSelectedDriversTrips();
		}
		else{
			
			for (var i=0;i<drivers.length;i++){
	
				var j = findDriver(drivers[i].driverID);
				
				if(j > -1){
					
					processDriverTrips(colorArray[j],drivers[i]);
				}
			}
		}
	}
    function resetTripsForSelectedDrivers(){
    	
       	clearDownTrips();
       	getLatestTrips();
    }
    