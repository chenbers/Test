/*
*	Base Zone Class
*/
var Zone = Class.extend({
	construct: function(zoneOverlay)
	{
		if (zoneOverlay != null)
		{
			map.removeOverlay(zoneOverlay);
		}
		points = [];
		zoneOverlay = false;
		zoneName = false;
		latLngBounds = false;
	},
	getName: function()
	{
		return zoneName;
	},
	setName: function(zName)
	{
		zoneName = zName;
	},
	getPoints: function()
	{
		var returnStr = null;
		for(var x = 0; x < points.length; x++)
		{
			if (returnStr == null)
				returnStr = '';
			else returnStr = returnStr + ';';
			returnStr = returnStr + points[x].toString();
		}
		return returnStr;
	},
	getZone: function()
	{
		return zoneOverlay;
	},
	restoreZone:function(zone)
	{
		map.addOverlay(zone);
	},
	getSavePoints: function()
	{
		return this.getPoints();
	},
	redrawZone:function()
	{

	},
	isDrawingComplete: function()
	{
		return false;
	}

});

/*
*	Polygon Zone Class
*/
var PolygonZone = Zone.extend({

	construct: function(zoneOverlay)
	{
		arguments.callee.$.construct.call(this, zoneOverlay);
		LINE_COLOR = '#000080'
		BACK_COLOR = '#00FF77'
	},
	renderLine:function(showMarkers)
	{
		map.removeOverlay(startmarker);
		map.removeOverlay(endmarker);
		map.removeOverlay(zoneOverlay);
		if (points.length){
			zoneOverlay = new GPolygon(points, LINE_COLOR, 1, 1, BACK_COLOR, 0.2);
			map.addOverlay(zoneOverlay);
			nameMarker = false;
			if (zoneName && zoneName != null && zoneName != '')
			{
				zoneLabelClass = "zoneLabel";
				nameMarker=new LabelOverlay(zoneOverlay.getBounds().getCenter(),{labelClass:zoneLabelClass, labelText:zoneName, labelOffset:new GSize(1,1)});
				map.addOverlay(nameMarker);
			}
			if (showMarkers)
			{
				startmarker=new GMarker(points[0], {icon:arrowIcon});
				startmarker.type="start";
				map.addOverlay(startmarker);
				endmarker=new GMarker(points[points.length-1], {icon:arrowIcon});
				endmarker.type="end";
				map.addOverlay(endmarker);
			}
		}
	},
	startDrawing: function(map)
	{
		startmarker = false;
		endmarker = false;
		zoneOverlay = false;

		polygonDrawListener = GEvent.bind(map, "click", this, function(overlay,point) {
			if (point){	 // background clicked (so add a point)
				points.push(point);
				this.renderLine(true);
			} else if (overlay) {   // marker clicked (so delete)
				if (overlay.type=="start"){
					points.shift();
				} else if (overlay.type == "end"){
					points.pop();
				}
				this.renderLine(true);
			}
		});
	},
	endDrawing:function(map)
	{
		map.removeOverlay(startmarker);
		map.removeOverlay(endmarker);

		// connect last point to first to close the polygon
		if (points.length)
		{
			points.push(points[0]);
			this.renderLine(false);
		}

		GEvent.removeListener(polygonDrawListener);
	},
	redrawZone:function()
	{
		startmarker = false;
		endmarker = false;
		zoneOverlay = false;
		bounds = new GBounds(points);
		latLngBounds = new GLatLngBounds(new GLatLng(bounds.minY, bounds.minX), new GLatLng(bounds.maxY, bounds.maxX));
		map.setCenter(latLngBounds.getCenter());
		this.renderLine(false);
		return latLngBounds;
	},
	isDrawingComplete: function()
	{
		return (points.length > 2);
	}

});

/*
*	Circle Zone Class
*/
var CircleZone = Zone.extend({
	construct: function(zoneOverlay)
	{
		arguments.callee.$.construct.call(this, zoneOverlay);
		LINE_COLOR = '#000080'
		BACK_COLOR = '#0077FF'
	},
	renderCircle:function(showMarkers)
	{
		map.removeOverlay(centerMarker);
		map.removeOverlay(radiusMarker);
		map.removeOverlay(zoneOverlay);
		if (points.length){
			zoneOverlay = new GPolygon(points, LINE_COLOR, 1, 1, BACK_COLOR, 0.2);
			map.addOverlay(zoneOverlay);
			nameMarker = false;
			if (zoneName && zoneName != null && zoneName != '')
			{
				zoneLabelClass = "zoneLabel";
				nameMarker=new LabelOverlay(zoneOverlay.getBounds().getCenter(),{labelClass:zoneLabelClass, labelText:zoneName, labelOffset:new GSize(1,1)});
				map.addOverlay(nameMarker);
			}
		}
		if (showMarkers)
		{
			if (centerPoint)
			{
				centerMarker=new GMarker(centerPoint,{icon:arrowIcon});
				centerMarker.type="center";
				map.addOverlay(centerMarker);
			}
			if (radiusPoint)
			{
				radiusMarker=new GMarker(radiusPoint, {icon:arrowIcon});
				radiusMarker.type="radius";
				map.addOverlay(radiusMarker);
			}
		}
	},
	startDrawing: function(map)
	{
		centerMarker = false;
		radiusMarker = false;
		zoneOverlay = false;
		centerPoint = false;
		radiusPoint = false;
		circleDrawListener = GEvent.bind(map, "click", this, function(overlay,point) {
			if (point){	 // background clicked (so add a point)
				if (centerPoint)
				{
					radiusPoint = point;
					points = getCirclePoly(centerPoint, radiusPoint);
				}
				else
				{
					centerPoint = point;
				}
				this.renderCircle(true);
			} else if (overlay) {   // marker clicked (so delete)
				if (overlay.type=="center")		// remove centerPoint and assign radius to center
				{
					centerPoint = radiusPoint;
					radiusPoint = false;
					points.length = 0;

				}
				else if (overlay.type == "radius")	// remove the radius point
				{
					radiusPoint = false;
					points.length = 0;
				}
				this.renderCircle(true);
			}
		});

	},
	endDrawing:function(map)
	{
		map.removeOverlay(centerMarker);
		map.removeOverlay(radiusMarker);
		GEvent.removeListener(circleDrawListener);
		if (points.length)
		{
			this.renderCircle(false);
		}
	},
	getSavePoints: function()
	{
		return centerPoint.toString() + ';' + radiusPoint.toString();
	},
	redrawZone:function()
	{
		centerMarker = false;
		radiusMarker = false;
		zoneOverlay = false;
		centerPoint = points[0];
		radiusPoint = points[1];
		points = getCirclePoly(centerPoint, radiusPoint);
		bounds = new GBounds(points);
		latLngBounds = new GLatLngBounds(new GLatLng(bounds.minY, bounds.minX), new GLatLng(bounds.maxY, bounds.maxX));
		map.setCenter(latLngBounds.getCenter());
		this.renderCircle(false);
		return latLngBounds;
	},
	isDrawingComplete: function()
	{
		return (centerMarker != false && radiusMarker != false);
	}

});


/*
*	Rectangle Zone Class
*/
var RectZone = Zone.extend({
	construct: function(zoneOverlay)
	{
		arguments.callee.$.construct.call(this, zoneOverlay);
		corner1Marker = false;
		corner2Marker = false;
		LINE_COLOR = '#000080'
		BACK_COLOR = '#B22222'
	},
	renderRect:function(showMarkers)
	{
		map.removeOverlay(corner1Marker);
		map.removeOverlay(corner2Marker);
		map.removeOverlay(zoneOverlay);
		if (points.length){
			zoneOverlay = new GPolygon(points, LINE_COLOR, 2, 1, BACK_COLOR, 0.2);
			map.addOverlay(zoneOverlay);
			nameMarker = false;
			if (zoneName && zoneName != null && zoneName != '')
			{
				zoneLabelClass = "zoneLabel";
				nameMarker=new LabelOverlay(zoneOverlay.getBounds().getCenter(),{labelClass:zoneLabelClass, labelText:zoneName, labelOffset:new GSize(1,1)});
				map.addOverlay(nameMarker);
			}
		}
		if (showMarkers)
		{
			if (corner1Point)
			{
//				Todo: implement draggable markers corner1Marker=new GMarker(corner1Point, {draggable:true});
				corner1Marker=new GMarker(corner1Point, {icon:arrowIcon});
				corner1Marker.type="corner1";
				map.addOverlay(corner1Marker);
			}
			if (corner2Point)
			{
				corner2Marker=new GMarker(corner2Point, {icon:arrowIcon});
				corner2Marker.type="corner2";
				map.addOverlay(corner2Marker);
			}
		}
	},
	startDrawing: function(map)
	{
		corner1Marker = false;
		corner2Marker = false;
		zoneOverlay = false;
		corner1Point = false;
		corner2Point = false;
		rectDrawListener = GEvent.bind(map, "click", this, function(overlay,point) {
			if (point){	 // background clicked (so add a point)
				if (corner1Point)
				{
					corner2Point = point;
					points = getRectPoly(corner1Point, corner2Point);
				}
				else
				{
					corner1Point = point;
				}
				this.renderRect(true);
			} else if (overlay) {   // marker clicked (so delete)
				if (overlay.type=="corner1")		// remove centerPoint and assign radius to center
				{
					corner1Point = corner2Point;
					corner2Point = false;
					points.length = 0;

				}
				else if (overlay.type == "corner2")	// remove the radius point
				{
					corner2Point = false;
					points.length = 0;
				}
				this.renderRect(true);
			}
		});
	},
	endDrawing:function(map)
	{
		map.removeOverlay(corner1Marker);
		map.removeOverlay(corner2Marker);
		GEvent.removeListener(rectDrawListener);
		if (points.length)
		{
			this.renderRect(false);
		}
	},
	getSavePoints: function()
	{
		return corner1Point.toString() + ';' + corner2Point.toString();
	},
	redrawZone:function()
	{
		corner1Marker = false;
		corner2Marker = false;
		zoneOverlay = false;

		corner1Point = points[0];
		corner2Point = points[1];
		points = getRectPoly(corner1Point, corner2Point);
		bounds = new GBounds(points);
		latLngBounds = new GLatLngBounds(new GLatLng(bounds.minY, bounds.minX), new GLatLng(bounds.maxY, bounds.maxX));
		map.setCenter(latLngBounds.getCenter());
		this.renderRect(false);

		return latLngBounds;
	},
	isDrawingComplete: function()
	{
		return (corner1Marker != false && corner2Marker != false);
	}

});


//----------------------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------------------
var NONE_MODE = 0;
var CIRCLE_MODE = 1;
var POLYGON_MODE = 2;
var RECT_MODE = 3;

var modeId = [
'NONE_MODE',
'CIRCLE_MODE',
'POLYGON_MODE',
'RECT_MODE'
];

var selectedDrawingMode = NONE_MODE;
var drawingZone;
var zone = null;
var bounds = null;
var points = [];
var prefix = 'zones-form:';

function onZoneDrawingModeSelected(mode)
{
	if (mode == null)
		mode = selectedDrawingMode;

	if (selectedDrawingMode == NONE_MODE)	// start drawing
	{
		hideArea('zone-type');
		switch(mode)
		{
			case POLYGON_MODE:
				drawingZone = new PolygonZone(zone);
				break;
			case RECT_MODE:
				drawingZone = new RectZone(zone);
				break;
			case CIRCLE_MODE:
				drawingZone = new CircleZone(zone);
				break;
		}
		selectedDrawingMode = mode;
		showArea(modeId[selectedDrawingMode]);
		showArea('draw-buttons');
		drawingZone.startDrawing(map);
	}
	else									// finish drawing
	{
		if (mode == NONE_MODE)
		{
			points.length = 0;
			map.removeOverlay(zoneOverlay);
		}
		drawingZone.endDrawing(map);
		hideArea(modeId[selectedDrawingMode]);
		hideArea('draw-buttons');
		if (mode != NONE_MODE) // OK
		{
			zone = drawingZone.getZone();
			transferData(mode, drawingZone);
		}
		else					// Cancel (restore previous zone if there is one)
		{
			if (zone)
			{
				new Zone(drawingZone.getZone()).restoreZone(zone);
			}
		}
		selectedDrawingMode = NONE_MODE;
		showArea('zone-type');
	}
}

function transferData(mode, drawingZone)
{
	eval("document.getElementById('"+prefix+"zoneType').value='" + mode + "'");
	pointsList = drawingZone.getSavePoints();
	eval("document.getElementById('"+prefix+"points').value='" +pointsList + "'");
}

function restoreZone(zType, zPoints, zName)
{
	var zoneType;
	if (!zType || zType == null)
		zoneType = eval("document.getElementById('"+prefix+"zoneType').value");
	else zoneType = zType;
	switch(parseInt(zoneType))
	{
		case NONE_MODE:
			return;
		case POLYGON_MODE:
			drawingZone= new PolygonZone(null);
			break;
		case RECT_MODE:
			drawingZone= new RectZone(null);
			break;
		case CIRCLE_MODE:
			drawingZone= new CircleZone(null);
			break;
	}
	if (zName && zName != null && zName != '')
		drawingZone.setName(zName);
	getPoints(zPoints);
	bounds = drawingZone.redrawZone();
	zone = drawingZone.getZone();

	return bounds;
}

function getPoints(pointsStr)
{
	var pstring;
	if (!pointsStr || pointsStr == null)
		pstring = eval("document.getElementById('"+prefix+"points').value");
	else pstring = pointsStr;
	var pointVals = pstring.split(';');
	points = [];

	for(var i = 0; i < pointVals.length; i++)
	{
		var pointPair = pointVals[i].substring(1, pointVals[i].length-1).split(',');
		if (pointPair && pointPair.length == 2)
		{

			lat = parseFloat(pointPair[0]);
			lng = parseFloat(pointPair[1]);

			var pt = new GLatLng(lat, lng);
			points.push(pt);
		}
	}
}

function hideArea(areaName)
{
	eval("document.getElementById('" + areaName +"').style.display='none'")
}

function showArea(areaName)
{
	eval("document.getElementById('" + areaName +"').style.display=''")
}

function cancelDrawing()
{
	if (selectedDrawingMode != NONE_MODE)
	{
		if (drawingZone.isDrawingComplete())
		{
			onZoneDrawingModeSelected(selectedDrawingMode);
			return true;
		}

		if (confirm('Warning: The zone you are currently drawing is not complete and cannot be saved.  Select Cancel to go back and finish drawing the zone.'))
		{
			onZoneDrawingModeSelected(NONE_MODE);
		}
		return false;
	}
	return true;
}

function zoneDrawn()
{
	getPoints();
	if (points.length < 2)
	{
		alert('Please select a shape and draw the zone.');
		return false;
	}
	return true;
}

// from trips
function initZones(zoneTypesId, zonePointsId, zoneNamesId)
{
	var allzType = eval("document.getElementById('"+zoneTypesId+"').value");
	if (allzType.length == 0)   // no zones
		return;
	var allzPoints = eval("document.getElementById('"+zonePointsId+"').value");
	var allzName= eval("document.getElementById('"+zoneNamesId+"').value");

	var allZonePoints = allzPoints.split('~');
	var allZoneTypes = allzType.split('~');
	var allZoneNames = allzName.split('~');
	for(var i = 0; i < allZonePoints.length; i++)
	{
		restoreZone(allZoneTypes[i], allZonePoints[i], allZoneNames[i]);
	}
}
