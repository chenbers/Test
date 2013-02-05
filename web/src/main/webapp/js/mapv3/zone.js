
var ZONE_COLOR = "#0033FF"; // the color of the zone on the map

// outline is either a string in form "(x1,y1);(x2,y2);(x3,y3)"  or array of google.maps.LatLng
function Zone(outline, options_) {
	if (typeof(outline) == "string") {
		this.points = fromOutlineString(outline);
	}
	else {
		this.points = outline;
	}
	this.zoneOverlay = null;
	this.editable = false;
	var options  = options_ ? options_ : new Array();
	this.strokeColor = options.strokeColor ? options.strokeColor : ZONE_COLOR;
	this.strokeWeight = options.strokeWeight ? options.strokeWeight : 2;
	this.strokeOpacity = options.strokeOpacity ? options.strokeOpacity : 0.7;
	this.fillColor = options.fillColor ? options.fillColor : ZONE_COLOR;
	this.fillOpacity = options.fillOpacity ? options.fillOpacity : 0.2;

}
Zone.prototype = {
		    constructor: Zone,
		    clear : function() {
		    	if (this.zoneOverlay != null) {
		    		this.zoneOverlay.setMap(null);
		    	}
		    },
		    displayOnMap : function(map, fitBounds, editable) {
		    	this.editable = (editable) ? editable : false;
		    	this.zoneOverlay = new google.maps.Polygon({
		    		path : this.points,
		    		strokeColor : this.strokeColor,
		    		strokeWeight: this.strokeWeight,
		    		strokeOpacity: this.strokeOpacity,
		    		fillColor : this.fillColor,
		    		fillOpacity: this.fillOpacity,
		    		editable : this.editable
		    	});
		    	var bounds = this.getBounds();
		    	if (fitBounds) {
		    		map.fitBounds(bounds);
		    	}
		    	this.zoneOverlay.setMap(map);
		    	return bounds;
		    },
		    getBounds : function() {
		    	if (this.points) {
		    		var bounds = new google.maps.LatLngBounds();
		    	    for (var i = 0; i < this.points.length; i++) {
		    	    	bounds.extend(this.points[i]);
		    	  	}
		    	    return bounds;
		    	}
		    	return null;
	    	},
	    	getPointsStr : function() {
		    	if (this.points) {
		    		return toOutlineString(this.points);
		    	}
	    		return "";
	    	},
	    	getOverlay : function () {
	    		return this.zoneOverlay;
	    	}
	    	
};
		    


/* helper functions */

/**
 * Returns an array of google.maps.LatLng instances from a string in the form created by toOutlineString.
 *
 *  outlineString: a string in the form of "(x1,y1);(x2,y2);(x3,y3)" where x1, y1, etc. are latitudes and
 *                longitudes on the map.
 */
function fromOutlineString(outlineString) {
	if (outlineString) {
		var segs = outlineString.split(";");
		if (segs.length >= 3) {
			outline = new Array();
			for (var i = 0; i < segs.length-1; i++) {
				var ll = segs[i].substring(1, segs[i].length - 1).split(",");
				var pt = new google.maps.LatLng(parseFloat(ll[0]), parseFloat(ll[1]));
				outline.push(pt);
			}
			return outline;
		}
	}
	return null;
}

function toOutlineString(outline)
{
	var outlineString = "";
	if (outline && outline.length > 0) {
		for (var i = 0; i < outline.length; i++) {
			if (outlineString.length > 0)
			  	outlineString += ";";
			outlineString += "(" + outline[i].toUrlValue() + ")";
	  	}
		// re-add the first point since backend expects a closed poly
		outlineString += ";";
		outlineString += "(" + outline[0].toUrlValue() + ")";
  	}
	return outlineString;
}



/* common stuff need to refactor */

var CIRCLE_VERTICES = 16;   // the number of vertices in a circle
function getCirclePath(circle)
{
	var origin = circle.getCenter();
	var earthRadius = 6371000; // meters

	//latitude in radians
	var lat = (origin.lat() * Math.PI) / 180;

	//longitude in radians
	var lon = (origin.lng() * Math.PI) / 180;
	
	//angular distance covered on earth's surface
	var d = parseFloat(circle.getRadius()) / earthRadius;
	polyPoints = new Array();

	var space = 22.5; // 360/16
	for (var i = 0; i < CIRCLE_VERTICES; i++) {
		  var bearing = (i*space) * Math.PI / 180; //rad
		  var vertexLat = Math.asin(Math.sin(lat) * Math.cos(d) + Math.cos(lat) * Math.sin(d) * Math.cos(bearing));
		  var vertexLng = ((lon + Math.atan2(Math.sin(bearing) * Math.sin(d) * Math.cos(lat), Math.cos(d) - Math.sin(lat) * Math.sin(vertexLat))) * 180) / Math.PI;
		  vertexLat = (vertexLat * 180) / Math.PI;
		  var point = new google.maps.LatLng(	parseFloat(vertexLat), parseFloat(vertexLng));
		  polyPoints.push(point);
	}

	return polyPoints;

}


function getRectanglePath(rect)
{
  var bounds = rect.getBounds();
  var ne = bounds.getNorthEast();
  var sw = bounds.getSouthWest();

  var nw = new google.maps.LatLng(sw.lat(), ne.lng());
  var se = new google.maps.LatLng(ne.lat(), sw.lng());

  return [nw, ne, se, sw, nw];
}
