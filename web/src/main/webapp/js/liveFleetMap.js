  	var map;
  	var markerClusterer;
	var markers = [];
  	var mapNeedsInit = true;
  	var overviewMapControl;
    var baseIcon = new GIcon();
	baseIcon.iconSize = new GSize(25, 30);
	baseIcon.iconAnchor = new GPoint(6, 20);
	baseIcon.infoWindowAnchor = new GPoint(5, 1);
	baseIcon.shadow=null;
// poi 

//poi end
  	function initMap(lat,lng,zoom)
  	{
		if (GBrowserIsCompatible()) {
			
			if(mapNeedsInit == true)
			{
		 		map = new GMap2(document.getElementById("map-canvas"));
		 		mapNeedsInit = false;
			}
		    addLayers();

		    map.addControl(new GLargeMapControl());
		    map.addControl(new GScaleControl());
		    map.addControl(new GMenuMapTypeControl(true));

			map.setCenter(new GLatLng(lat, lng));
		    map.enableScrollWheelZoom();

			addListeners();
			
	 		overviewMapControl = new GOverviewMapControl();
	 		map.addControl(overviewMapControl); 
			bounds = new GLatLngBounds();
		    overviewMapControl.hide();
    		geocoder =new GClientGeocoder();
		}
	}
  	function centerMap() 
  	{
  		map.setZoom(map.getBoundsZoomLevel(bounds)-1);
	    map.setCenter(bounds.getCenter());
	}
       
	// Creates a marker whose info window displays the link tot the driver corresponding
   // to the given index.

	function selectMarker(lat,lng)
	{
		
		map.panTo(new GLatLng(lat, lng));
	}
		        
