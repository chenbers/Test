  	var markerClicked=false;
  	var map;
//  	var markerManager;
  	var markerClusterer;
	var markers = [];
  	var bounds; 
  	var mapNeedsInit = true;
  	
    var baseIcon = new GIcon();
	baseIcon.iconSize = new GSize(25, 30);
	baseIcon.iconAnchor = new GPoint(6, 20);
	baseIcon.infoWindowAnchor = new GPoint(5, 1);
	baseIcon.shadow=null;
  	
  	function initMap(lat,lng,zoom)
  	{
		if (GBrowserIsCompatible()) {
			
			if(mapNeedsInit == true)
			{
		 		map = new GMap2(document.getElementById("map-canvas"));
		 		mapNeedsInit = false;
			}
			map.setMapType(G_NORMAL_MAP);
			map.setCenter(new GLatLng(lat, lng));
			map.addControl(new GLargeMapControl());
	 		map.addControl(new GMapTypeControl());
	 		map.addControl(new GOverviewMapControl()); 
			bounds = new GLatLngBounds();
			//markerManager = new MarkerManager(map);
			
		}
	}
  	
  	function centerMap() 
  	{
  		map.setZoom(map.getBoundsZoomLevel(bounds));
	    map.setCenter(bounds.getCenter());
	}
       
		// Creates a marker whose info window displays the link tot the driver corresponding
       // to the given index.
       function createMarker(point, driverId, iconImage) {
         // Create a colored icon for this point using our icon class
	       coloredIcon = new GIcon(baseIcon); 
	       coloredIcon.image = iconImage; 
//	       coloredIcon.shadow =null;
//	       coloredIcon.iconSize = new GSize(25, 30); 
//	       coloredIcon.iconAnchor = new GPoint(9, 34); 
//	       coloredIcon.infoWindowAnchor = new GPoint(9, 2); 
	      // coloredIcon.infoShadowAnchor = new GPoint(18, 25);
       						
         // Set up our GMarkerOptions object
         markerOptions = { icon:coloredIcon };
         var marker = new GMarker(point, markerOptions);
         
         //GEvent.addListener(marker, "mouseover", function() {
         //			var node = document.getElementById(driverId).cloneNode(true);
         // 			node.style.display = 'block';
	     //       if (!markerClicked) marker.openInfoWindow(node);  
         //});
         GEvent.addListener(marker, "click", function() {
         			var node = document.getElementById(driverId).cloneNode(true);
         			node.style.display = 'block';
         			marker.openInfoWindow(node);
           
         });
         
         //GEvent.addListener(marker, "mouseout", function() {
	     //       if (!markerClicked) marker.closeInfoWindow();
	     //});
         
         GEvent.addListener(marker, "infowindowclose", function() {
	            markerClicked = false;
           
         });
         
         bounds.extend(marker.getPoint());
         return marker;
       }
	
		function selectMarker(lat,lng)
		{
			
			map.panTo(new GLatLng(lat, lng));
		}
		
        function showInfoWindow(noteID, latLng)
        {
        	var node = document.getElementById(noteID).cloneNode(true);
  			node.style.display = 'block';

  			map.openInfoWindow(latLng, node);
        	
        }