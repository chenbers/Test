var REGULAR_IMG = 0;
var MOUSE_OVER_IMG = 1;
var VISITED_IMG = 2;
var SHADOW_IMG = 3;

var eventImages = [];
var eventIcon;
var startImages = [];
var startIcon;
var endImages = [];
var endIcon;
var arrowImages = [];
var arrowIcon;


function initMapIcons(serverNameId, portId, contextPathId)
{
	var serverName = eval("document.getElementById('"+serverNameId+"').value");
	var port = eval("document.getElementById('"+portId+"').value");
	var contextPath = eval("document.getElementById('"+contextPathId+"').value");
	
	// TODO: can add different color images for mouseover, visited to help when we have multiple
	// events that overlap on the map -- right now all are the same
	// Aggressive Driving Event
	eventImages = [
		"http://" + serverName + ":" + port + contextPath + "/images/map_marker_event.png",	// Regular
		"http://" + serverName + ":" + port + contextPath + "/images/map_marker_event.png",	// MouseOver
		"http://" + serverName + ":" + port + contextPath + "/images/map_marker_event.png",	// Visited
		"http://" + serverName + ":" + port + contextPath + "/images/map_marker_event.png",	// Shadow
	];

	eventIcon = new GIcon();
	eventIcon.image = eventImages[REGULAR_IMG];
	eventIcon.shadow = eventImages[SHADOW_IMG];
	eventIcon.iconSize = new GSize(16, 16);
	eventIcon.shadowSize = new GSize(0, 0);
	eventIcon.iconAnchor = new GPoint(6, 16);
	eventIcon.infoWindowAnchor = new GPoint(5, 1);

	// Start Trip
	startImages = [
		"http://" + serverName + ":" + port + contextPath + "/images/map_marker_start.png",	// Regular
	];

	startIcon = new GIcon();
	startIcon.image = startImages[REGULAR_IMG];
	startIcon.shadow = startImages[REGULAR_IMG];
	startIcon.iconSize = new GSize(16, 16);
	startIcon.shadowSize = new GSize(0, 0);
	startIcon.iconAnchor = new GPoint(6, 16);
	startIcon.infoWindowAnchor = new GPoint(5, 1);

	// End Trip
	endImages = [
		"http://" + serverName + ":" + port + contextPath + "/images/map_marker_stop.png",	// Regular
	];

	endIcon = new GIcon();
	endIcon.image = endImages[REGULAR_IMG];
	endIcon.shadow = endImages[REGULAR_IMG];
	endIcon.iconSize = new GSize(16, 16);
	endIcon.shadowSize = new GSize(0, 0);
	endIcon.iconAnchor = new GPoint(6, 16);
	endIcon.infoWindowAnchor = new GPoint(5, 1);

    // Zone Drawing 
    arrowImages = [
        "http://" + serverName + ":" + port + contextPath + "/images/arrow.png",  // Regular
        "",
        "",
        "http://" + serverName + ":" + port + contextPath + "/images/arrow_shadow.png",  // shadow
    ];

    arrowIcon = new GIcon();
    arrowIcon.image = arrowImages[REGULAR_IMG];
    arrowIcon.shadow = arrowImages[SHADOW_IMG];
    arrowIcon.iconSize = new GSize(39, 34);
    arrowIcon.shadowSize = new GSize(0, 0);
    arrowIcon.iconAnchor = new GPoint(10, 34);
    arrowIcon.infoWindowAnchor = new GPoint(5, 1);

}
