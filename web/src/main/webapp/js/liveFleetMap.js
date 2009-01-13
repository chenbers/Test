  	var markerClicked=false;
  	var map;
  	var mgr;
  	function initMap(lat,lng,zoom){
		if (GBrowserIsCompatible()) {
	 		map = new GMap2(document.getElementById("map-canvas"));
			map.addControl(new GLargeMapControl());
	 		map.addControl(new GMapTypeControl());
	 		map.addControl(new GOverviewMapControl()); 
			map.setMapType(G_NORMAL_MAP);
			map.setCenter(new GLatLng(lat, lng), zoom);
			mgr = new MarkerManager(map);
		}
	}
       
		// Creates a marker whose info window displays the link tot the driver corresponding
       // to the given index.
       function createMarker(point, driverId, iconImage) {
         // Create a colored icon for this point using our icon class
       coloredIcon = new GIcon(G_DEFAULT_ICON); 
       coloredIcon.image = iconImage; 
       coloredIcon.iconSize = new GSize(25, 30); 
       coloredIcon.iconAnchor = new GPoint(9, 34); 
       coloredIcon.infoWindowAnchor = new GPoint(9, 2); 
       coloredIcon.infoShadowAnchor = new GPoint(18, 25);
       						
         // Set up our GMarkerOptions object
         markerOptions = { icon:coloredIcon };
         var marker = new GMarker(point, markerOptions);
         
         GEvent.addListener(marker, "mouseover", function() {
         			var node = document.getElementById(driverId).cloneNode(true);
         			node.style.display = 'block';
	            if (!markerClicked) marker.openInfoWindow(node);
           
         });
         GEvent.addListener(marker, "click", function() {
         			var node = document.getElementById(driverId).cloneNode(true);
         			node.style.display = 'block';
	            if (markerClicked) marker.openInfoWindow(node);
	            markerClicked =true;
           
         });
         GEvent.addListener(marker, "mouseout", function() {
	            if (!markerClicked) marker.closeInfoWindow();
	            
           
         });
         GEvent.addListener(marker, "infowindowclose", function() {
	            markerClicked = false;
           
         });
         return marker;
       }
	
