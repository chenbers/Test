	var map;
	var tripIcons;
	var driverIDArray = new Array();
	var tripsSelected = new Array();
	var overlaysArray = new Array();
	var markersArray = new Array();
	var boundsArray = new Array();
	var colorArray = new Array();
	var readdOverlays = false;
	var loadNow = false;
	
	var colors= ["#820f00","#ff4a12","#94b3c5","#74c6f1","#586b7a","#3e4f4f","#abc507","#eab239","#588e03",
				 "#8a8c81","#8173b1","#f99b49","#c6064f","#c4bdd9","#c8a77b"];
	 
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
		for (var k=0; k<markersArray[driverIndex].length;k++){
			
			markersArray[driverIndex][k].hide();
		}

	}
	function showOverlays(driverIndex){
				
		for (var j=0; j<overlaysArray[driverIndex].length; j++){
						
			overlaysArray[driverIndex][j].show();
		}
		for (var k=0; k<markersArray[driverIndex].length;k++){
			
			markersArray[driverIndex][k].show();
		}
	}

	function hideAllOverlays(){
		 
		for (var i=0; i<overlaysArray.length;i++){
			for (var j=0; j<overlaysArray[i].length;j++){
				
				overlaysArray[i][j].hide();
			}
		}
		for (var i=0; i<markersArray.length;i++){
			for (var j=0; j<markersArray[i].length;j++){
				
				markersArray[i][j].hide();
			}
		}
	}
	function showAllOverlays(){
		 
		for (var i=0; i<overlaysArray.length;i++){
			
			if(tripsSelected[i]){
				
				for (var j=0; j<overlaysArray[i].length;j++){
					
//					overlaysArray[i][j].recalc();
					overlaysArray[i][j].show();
				}
				for (var j=0; j<markersArray[i].length;j++){
					
					markersArray[i][j].show();
				}
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
		
	    function createMarker(point, iconImage) 
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
					
					// BDCCArrowedPolyline(points, color, weight, opacity, opts, gapPx, headLength, headColor, headWeight, headOpacity, headPointiness)
					var tripRoute = driverTrips.trips[j].route;
					var latLngArray = new Array();
					for(var k=0; k<tripRoute.length; k++){
						
						latLngArray.push(new GLatLng(tripRoute[k].lat,tripRoute[k].lng));
					}
					var endlatlng = new GLatLng(driverTrips.trips[j].routeLastStep.lat, driverTrips.trips[j].routeLastStep.lng);
					latLngArray.push(endlatlng);
					
					var arrowPolyline = new BDCCArrowedPolyline(latLngArray,colors[colorArray[index]], 4, 0.9,{geodesic:true}, 
														200, 20,"#000000",1,0.9,8);
						//new GPolyline(latLngArray,colors[colorArray[index]], 4, 0.9,{geodesic:true});
					
					overlays.push(arrowPolyline);
					//Start of trip marker
					startlatlng = new GLatLng(driverTrips.trips[j].beginningPoint.lat, driverTrips.trips[j].beginningPoint.lng);
					marker = createMarker(startlatlng,tripIcons[0]);
					markers.push(marker);
					//End of trip marker
			
					if (driverTrips.trips[j].inProgress)
					{
						marker = createMarker(endlatlng, tripIcons[1]);
					}
					else
					{
						marker = createMarker(endlatlng,tripIcons[2]);
					}
					markers.push(marker);
				}
			
				for(var k=0; k<driverTrips.violations.length; k++){
					var violation = new GLatLng(driverTrips.violations[k].lat, driverTrips.violations[k].lng);
					var marker = createMarker(violation,tripIcons[3]);
					markers.push(marker);
				}
				for(var k=0; k<driverTrips.idles.length; k++){
					var idle = new GLatLng(driverTrips.idles[k].lat,driverTrips.idles[k].lng);
					var marker = createMarker(idle, tripIcons[4]);
					markers.push(marker);
				}
				for(var k=0; k<driverTrips.tampers.length; k++){
					var tamper = new GLatLng(driverTrips.tampers[k].lat,driverTrips.tampers[k].lng);
					var marker = createMarker(tamper, tripIcons[5]);
					markers.push(marker);
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
		 var markers = markersArray[index];
		 
		 for(var k=0;k<markers.length; k++){
			 
			 map.addOverlay(markers[k]);
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
		 map.setZoom(map.getBoundsZoomLevel(bounds));
		 map.setCenter(bounds.getCenter());

		 showAllOverlays();
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
			 createTripOverlays(driverTrips,i);
			 boundsArray.push(calculateBounds(overlaysArray[i]));
			 showNewTrips(i);
		 }
		 //data has previously been sent but is being replaced
		 else if (overlaysArray[i].length == 0){
			 
			 overlaysArray[i]=new Array(); 	//just a place holder for now
			 markersArray[i]=new Array();
			 createTripOverlays(driverTrips,i);
			 boundsArray[i]=calculateBounds(overlaysArray[i]);
			 showNewTrips(i);
			 
		}
		//data is already there and just needs to be redisplayed
		else{

			hideAllOverlays();
			setDriverSelected(i, true);
			 bounds = setupMap();
			 map.setZoom(map.getBoundsZoomLevel(bounds));
			 map.setCenter(bounds.getCenter());
			showAllOverlays();

		 }			 
	 }

	function processDriverTrips(colorIndex, driverTrips){
		
		if (driverTrips.selected){
			
			addDriverTrips(colorIndex,driverTrips);
		}
		else{
			
			unselectDriver(findDriver(driverTrips.driverID));
		}
	}
	function redrawAllSelectedDriversTrips(){
		
		
		for(var i=0;i<driverIDArray.length;i++){
			
			if (tripsSelected[i]){
				
				addTripOverlaysAndMarkersToMap(i);
			}
		}
		 bounds = setupMap();
		 map.setZoom(map.getBoundsZoomLevel(bounds));
		 map.setCenter(bounds.getCenter());
	}
	function clearOverlays(){
		
		for(var i=0; i<overlaysArray.length;i++){
			for (var j=0; j< overlaysArray[i].length; j++){
				
				map.removeOverlay(overlaysArray[i][j]);
			}
			for (var j=0; j< markersArray[i].length; j++){
				
				map.removeOverlay(markersArray[i][j]);
			}
		}
	}
	 function clearDownTrips(){

		if(map){
			
			clearOverlays();
		}
		for(var i=0; i< overlaysArray.length;i++){
			
			overlaysArray[i] = new Array();
			markersArray[i] = new Array();
		}

	 }	

	function redisplayAllTrips(drivers, timeFrameChanged){
		if (!timeFrameChanged){
			
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
       	ifTimeFrameChangedReloadAllTrips();
    }

