
/**
 * Functions for drawing zones on a map.  Supports polygon, rectangle and circle zones.  Polygon zones are
 * drawn by clicking to add points.  Rectangle and circle zones may be drawn by clicking to choose start/end
 * points, or by clicking and dragging.  After being drawn, all zones are converted to editable polygons,
 * i.e. you can click on the polygon's points and drag them to change the shape.
 *
 * Requires a variable named "map" to have been defined as an instance of GMap2.  Also requires the following
 * div elements to be defined directly after the map div element:
 *
 * <div id="map-cover" style="position:absolute; display:none; overflow:hidden; cursor:crosshair; zIndex:101;">
 *   <div id="rect-outline" style="position:absolute; display:none; border:2px dotted #0033FF"></div>
 *   <div id="circle-outline" style="display:none"></div>
 * </div>
 *
 * To start drawing a zone, call one of startRect(), startCircle() or startPolygon().  To stop editing
 * (possibly completing the shape) call stopDrawing().  Call loadZone(outline) to display a zone from the
 * passed-in outline.  toOutlineString(), fromOutlineString() and getPolygonOutline() may be used as needed.
 *
 * Uses the GEvent framework to send events.  Use GEvent.addListener(drawZone, type) to add event listeners.
 * The following events are supported:
 *   startZone: sent when a zone type is selected for drawing; the single argument is a string type.
 *     endZone: sent when a zone is done being drawn; the first argument is a string type and the second is an
 *              array of google.maps.LatLng instances containing the polygon's current coordinates.
 *  cancelZone: sent when a zone is canceled being drawn; the single argument is a string type.
 *  updateZone: sent when a zone is updated by dragging or deleting a point; the single argument is an array
 *              of google.maps.LatLng instances containing the polygon's current coordinates.
 */

var ZONE_COLOR = "#0033FF"; // the color of the zone on the map
var CIRCLE_VERTICES = 16;   // the number of vertices in a circle
var MAX_VERTICES = 255;     // the most vertices allowed in a free-edit polygon
var drawZone = {};          // for adding event listeners to
var polygon;                // after drawing or calling loadZone, this is set to an instance of GPolygon
var zoneOverlay;

/**
 * Stops any zone drawing currently in progress and initializes for drawing a rectangle zone.
 */
/*
function startRect()
{
  stopDrawing();
  polygon = null;
  GEvent.trigger(drawZone, "startZone", "rectangle");
  startShape_("rect-outline", initRect_, drawRect_, endRect_, "rectangle");
}
*/
/**
 * Stops any zone drawing currently in progress and initializes for drawing a circle zone.
 */
/*
function startCircle()
{
  stopDrawing();
  polygon = null;
  GEvent.trigger(drawZone, "startZone", "circle");
  startShape_("circle-outline", initCircle_, drawCircle_, endCircle_, "circle");
}
*/
/**
 * Stops any zone drawing currently in progress and initializes for drawing a polygon zone.
 */
/*
function startPolygon()
{
  stopDrawing();
  polygon = null;
  GEvent.trigger(drawZone, "startZone", "polygon");
  polygon = new GPolygon([], ZONE_COLOR, 2, 0.7, ZONE_COLOR, 0.2);
  initPoly_(polygon);
}
*/
/**
 * Stops any zone creation in progress. If a zone isn't complete it is canceled; otherwise it is completed.
 */
/*
function stopDrawing()
{
  if (shape_)
  {
    if (shape_.type == "rectangle")
      endRect_();
    else if (shape_.type == "circle")
      endCircle_();
    else if (shape_.type == "polygon")
      endPolygon_(polygon, true);
  }
}
*/
/**
 * Loads and displays a zone from the given outline.
 *
 *  outline: the zone's polygon outline, either as an array of google.maps.LatLng instances or as a string in the form
 *           created by toOutlineString.
 * editable: if provided, whether the zone should be editable (default is true).
 *   return: whether the zone was loaded and displayed.
 */

function loadZone(outline, editable, map)
{
  if (typeof(outline) == "string")
    outline = fromOutlineString(outline);

  if (outline)
  {
    polygon = createGPolygon_(outline, editable != false, map);
    return true;
  }
  return false;
}

/**
 * Returns a string from the given array of google.maps.LatLng instances in the form of "(x1,y1);(x2,y2);(x3,y3)" where
 * x1, y1, etc. are latitudes and longitudes on the map.
 *
 *  outline: an array of google.maps.LatLng instances comprising the zone's outline.
 */
/*
function toOutlineString(outline)
{
  var outlineString = "";
  if (outline)
    for (var i = 0; i < outline.length; i++)
    {
      if (outlineString.length > 0)
        outlineString += ";";
      outlineString += "(" + outline[i].toUrlValue() + ")";
    }
  return outlineString;
}
*/
/**
 * Returns an array of google.maps.LatLng instances from a string in the form created by toOutlineString.
 *
 *  outlineString: a string in the form of "(x1,y1);(x2,y2);(x3,y3)" where x1, y1, etc. are latitudes and
 *                longitudes on the map.
 */
function fromOutlineString(outlineString)
{
  if (outlineString)
  {
    var segs = outlineString.split(";");
    if (segs.length >= 3)
    {
      outline = new Array();
      for (var i = 0; i < segs.length; i++)
      {
        var ll = segs[i].substring(1, segs[i].length - 1).split(",");
        var pt = new google.maps.LatLng(parseFloat(ll[0]), parseFloat(ll[1]));
        outline.push(pt);
      }
      return outline;
    }
  }
  return null;
}

/**
 * Returns an array of google.maps.LatLng instances describing the outline of the currently displayed zone or null.
 */
function getPolygonOutline()
{
  if (!polygon)
    return null;

  outline = new Array();
  for (var i = 0; i < polygon.getVertexCount(); i++)
    outline.push(polygon.getVertex(i));

  return outline;
}
/**
 * Returns a google.maps.LatLngBounds for the given outline.
 *
 *  outline: the zone's polygon outline, either as an array of google.maps.LatLng instances or as a string in the form
 *           created by toOutlineString, or leave out to use the current polygon's outline.
 */
function getLatLngBounds(outline)
{
  if (!outline)
    outline = getPolygonOutline();
  else if (typeof(outline) == "string")
    outline = fromOutlineString(outline);

  if (outline)
  {
    var bounds = new google.maps.LatLngBounds();
    for (var i = 0; i < outline.length; i++) {
    	bounds.extend(outline[i]);
  	}
    return bounds;
  }
  return null;
}

/**
 * Disables editing the currently-displayed polygon, if any.
 */
/*
function disableEditing()
{
  if (polygon)
  {
	  polygon.disableEditing({onEvent: "mouseover"}); 
  }
}
*/
/**
 * Enables editing the currently-displayed polygon, if any.
 */
/*
function enableEditing()
{
  if (polygon)
    polygon.enableEditing({onEvent: "mouseover", maxVertices: MAX_VERTICES});
}
*/
/*
 * The following are private data and functions; don't use them directly.
 */
/*
var shape_;

function startShape_(outlineDiv, initShape, drawShape, endShape_, type)
{
  var cover = document.getElementById("map-cover");

  shape_ = {outline:document.getElementById(outlineDiv),
           dragging:false,
               type: type,
             mapPos:getElementPosition_(map.getContainer()),
          getRelPos:function(e)
          {
            var pos = getMousePosition_(e);
            return {x:(pos.x - this.mapPos.x), y:(pos.y - this.mapPos.y)};
          }};

  var mapSize = map.getSize();
  cover.style.display = "block";
  cover.style.background = "black";
  cover.style.opacity = 0;
  cover.style.filter = "alpha(opacity=0)";
  cover.style.left = shape_.mapPos.x + "px";
  cover.style.top = shape_.mapPos.y + "px";
  cover.style.width = mapSize.width + "px";
  cover.style.height = mapSize.height + "px";

  shape_.mousedown = GEvent.addDomListener(cover, 'mousedown', function(e)
  {
    if (!shape_.dragging)
    {
      var pos = shape_.getRelPos(e);
      shape_.start = pos;
      shape_.dragging = true;

      cover.style.background = "transparent";
      cover.style.opacity = 1;
      cover.style.filter = "alpha(opacity=100)";

      shape_.outline.style.display = "block";
      initShape();

      return false;
    }
  });
  shape_.mousemove = GEvent.addDomListener(document, 'mousemove', function(e)
  {
    if (shape_ && shape_.dragging)
    {
      shape_.end = shape_.getRelPos(e);
      drawShape();
      return false;
    }
  });
  shape_.mouseup = GEvent.addDomListener(document, 'mouseup', function(e)
  {
    shape_.end = shape_.getRelPos(e);
    if (shape_.start && (shape_.start.x != shape_.end.x) && (shape_.start.y != shape_.end.y))
      endShape_(e);
  });
}

function endRect_(e)
{
  endShape_(e, getRectMapPoints_, "rectangle");
}

function endCircle_(e)
{
  if (shape_)
    while (shape_.outline.childNodes.length > 0)
      shape_.outline.removeChild(shape_.outline.childNodes[0]);

  endShape_(e, getCircleOutline_, "circle");
}

function endShape_(e, getMapPoints)
{
  if (shape_)
  {
    if (shape_.dragging)
    {
      shape_.end = shape_.getRelPos(e);
      var mapSize = map.getSize();
      if ((shape_.end.x >= 0) && (shape_.end.x <= mapSize.width)
        && (shape_.end.y >= 0) && (shape_.end.y <= mapSize.width))
      {
        var points = getMapPoints();
        polygon = createGPolygon_(points, true);
        GEvent.trigger(drawZone, "endZone", shape_.type, points);
      }
      else
        GEvent.trigger(drawZone, "cancelZone", shape_.type);
    }
    else
        GEvent.trigger(drawZone, "cancelZone", shape_.type);

    GEvent.removeListener(shape_.mousedown);
    GEvent.removeListener(shape_.mousemove);
    GEvent.removeListener(shape_.mouseup);

    shape_.outline.style.display = "none";
    document.getElementById("map-cover").style.display = "none";

    shape_ = null;
  }
}
*/
function createGPolygon_(points, editable, map)
{
//  var poly = new GPolygon(points, ZONE_COLOR, 2, 0.7, ZONE_COLOR, 0.2);
	var poly = new google.maps.Polygon({
		path : points,
		strokeColor : ZONE_COLOR,
		strokeWeight: 2,
		strokeOpacity: 0.7,
		fillColor : ZONE_COLOR,
		fillOpacity: 0.2,
		editable : editable
	});
	
	zoneOverlay = poly;
	poly.setMap(map);
//	map.addOverlay(poly);
//  if (editable)
//    poly.enableEditing({onEvent: "mouseover", maxVertices: MAX_VERTICES});
//  poly.disableEditing({onEvent: "mouseout"});
//  addEditEvents_(poly);
	return poly;
}
/*
function addEditEvents_(poly)
{
  GEvent.addListener(poly, "click", function(latlng, index)
  {
    if (typeof index == "number")
    {
      poly.deleteVertex(index);
      GEvent.trigger(drawZone, "updateZone", getPolygonOutline());
    }
  });
  GEvent.addListener(poly, "lineupdated", function()
  {
    GEvent.trigger(drawZone, "updateZone", getPolygonOutline());
  });
}

function initRect_()
{
  shape_.outline.style.left = shape_.start.x + "px";
  shape_.outline.style.top = shape_.start.y + "px";
  shape_.outline.style.width = "1px";
  shape_.outline.style.height = "1px";
}

function drawRect_()
{
  var bounds = getRectBounds_();

  shape_.outline.style.left = bounds.left + "px";
  shape_.outline.style.top = bounds.top + "px";
  shape_.outline.style.width = (bounds.right - bounds.left) + "px";
  shape_.outline.style.height = (bounds.bottom - bounds.top) + "px";
}

function getRectMapPoints_()
{
  var bounds = getRectBounds_();

  var nw = map.fromContainerPixelToLatLng({x:bounds.left, y:bounds.top});
  var ne = map.fromContainerPixelToLatLng({x:bounds.right, y:bounds.top});
  var se = map.fromContainerPixelToLatLng({x:bounds.right, y:bounds.bottom});
  var sw = map.fromContainerPixelToLatLng({x:bounds.left, y:bounds.bottom});

  return [nw, ne, se, sw, nw];
}

function getRectBounds_()
{
  return {top:Math.min(shape_.start.y, shape_.end.y),
    left:Math.min(shape_.start.x, shape_.end.x),
    bottom:Math.max(shape_.start.y, shape_.end.y),
    right:Math.max(shape_.start.x, shape_.end.x)};
}

function initCircle_()
{}

function drawCircle_()
{
  var points = calcCirclePoints_();

  // add/remove points
  while (shape_.outline.childNodes.length < points.length)
  {
    var point = document.createElement("div");
    point.style.position = "absolute";
    point.style.width = point.style.height = "3px";
    point.style.backgroundColor = ZONE_COLOR;
    shape_.outline.appendChild(point);
  }
  while (shape_.outline.childNodes.length > points.length)
    shape_.outline.removeChild(shape_.outline.childNodes[shape_.outline.childNodes.length - 1]);

  // position points
  for (var i = 0; i < points.length; i++)
  {
    var point = shape_.outline.childNodes[i];
    point.style.left = (points[i].x - 1) + "px";
    point.style.top = (points[i].y - 1) + "px";
  }
}

function getCircleOutline_()
{
  var points = calcCirclePoints_(CIRCLE_VERTICES);
  for (var i = 0; i < CIRCLE_VERTICES; i++)
    points[i] = map.fromContainerPixelToLatLng(points[i]);
  points.push(points[0]);
  return points;
}

function calcCirclePoints_(numPoints)
{
  var x = (shape_.start.x - shape_.end.x);
  var y = (shape_.start.y - shape_.end.y);
  var radius = Math.sqrt((x * x) + (y * y));

  if (!numPoints)
    numPoints = Math.max(1, Math.round(radius / 2));

  var points = new Array();
  var radians = 2 * Math.PI / numPoints;
  for (var i = 0; i < numPoints; i++)
  {
    var angle = radians * i;
    points.push({x:(shape_.start.x + radius * Math.cos(angle)), y:(shape_.start.y + radius * Math.sin(angle))});
  }

  return points;
}

function initPoly_(poly)
{
  shape_ = {type:"polygon"};
  zoneOverlay = poly;
  map.addOverlay(poly);
  poly.enableDrawing({maxVertices: MAX_VERTICES});
  poly.enableEditing({onEvent: "mouseover", maxVertices: MAX_VERTICES});
  poly.disableEditing({onEvent: "mouseout"});
  GEvent.addListener(poly, "endline", function()
  {
    endPolygon_(poly);
  });
  GEvent.addListener(poly, "cancelline", function()
  {
    map.removeOverlay(poly);
    polygon = null;
    shape_ = null;
    GEvent.trigger(drawZone, "cancelZone", "polygon");
  });
}

function endPolygon_(poly, unfinished)
{
  if (!shape_)
    return;
  shape_ = null;

  var points = 4;
  if (unfinished)
  {
    poly.disableEditing();
    points = 3;
  }

  if (poly.getVertexCount() < points)
  {
    map.removeOverlay(poly);
    GEvent.trigger(drawZone, "cancelZone", "polygon");
    polygon = null;
  }
  else
  {
    if (unfinished)
    {
      var latlng = poly.getVertex(0);
      poly.insertVertex(poly.getVertexCount(), new google.maps.LatLng(latlng.lat(), latlng.lng()));
    }

    addEditEvents_(poly);

    GEvent.trigger(drawZone, "endZone", "polygon", getPolygonOutline());
  }
}

function getElementPosition_(element)
{
  var left = element.offsetLeft;          // initialize var to store calculations
  var top = element.offsetTop;            // initialize var to store calculations
  var parElement = element.offsetParent;     // identify first offset parent element
  while (parElement != null ) {                // move up through element hierarchy
    left += parElement.offsetLeft;      // appending left offset of each parent
    top += parElement.offsetTop;
    parElement = parElement.offsetParent;  // until no more offset parents exist
  }
  return {x:left, y:top};
}

function getMousePosition_(e)
{
  var posx = 0;
  var posy = 0;
  if (!e) var e = window.event;
  if (e.pageX || e.pageY)
  {
    posx = e.pageX;
    posy = e.pageY;
  }
  else if (e.clientX || e.clientY)
  {
    posx = e.clientX + (document.documentElement.scrollLeft?document.documentElement.scrollLeft:document.body.scrollLeft);
    posy = e.clientY + (document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop);
  }
  return {x:posx, y:posy};
}

*/

function removeZoneOverlay() {
	if (zoneOverlay != null) {
		zoneOverlay.setMap(null);
	}
}


