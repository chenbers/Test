var mapsbs = null;
var streetSegments = null;
var preciseLat = null;
var preciseLng = null;
var limit;

// colors
var POLYLINE_COLOR = '#666666';
var POLYLINE_SELECTED_COLOR = '#ff0000';
var ROW_SELECTED_COLOR = '#DfDfF8';
var ROW_ODD_COLOR = '#ffffff';
var ROW_EVEN_COLOR = '#EBFFCA';
    
	function initMap(lat, lng, zoom){
		if (!mapsbs) {
			mapsbs = inthincMap.init({
				'canvasID' : 'map-sbs',
				'center' : {
					'lat' : lat,
					'lng' : lng
				},
				'zoom' : zoom,
				'overviewMapControl' : true
			});
			initLayers(mapsbs, "sbs-layersControl");


			google.maps.event.addListener(mapsbs, "click", function(mouseEvent) {
				reverseGeocode(mouseEvent.latLng.lat(), mouseEvent.latLng.lng(), false);
	        });
		}
		else {
			inthincMap.clearMarkers(mapsbs);
		}
	}
    
    
	function StreetSegment(id, polyline, bcolor, row) {
		this.id = id;
		this.polyline = polyline;
		this.selected = false;
		this.bcolor = bcolor;
		this.row = row;
		this.polyline.setMap(mapsbs);

	}
	                   
	StreetSegment.prototype = {
	    constructor: StreetSegment
	};
	
	function StreetSegments() {
		this.streetSegments = new Array();
		this.segmentSelected = null;
		this.check = false;
	}
	StreetSegments.prototype = {
		    constructor: StreetSegments,
		    push : function(seg) {
		    	this.streetSegments.push(seg);
		    },
		    findSegment : function (id) {
		     	for (var i = 0; i < this.streetSegments.length; i++){
		     		if (this.streetSegments[i].id == id) {
		     			return this.streetSegments[i];
		     		}
		     	}
		     	return null;
		    	
		    },
		    selectSegment : function (id) {
		    	 
		 	 	if (this.check) {
		 	 		// Don't select row if it a child element picked up click first 
		 	 		this.check = false;
		 	 		return;
		 	 	}
		 		var streetSegment = this.findSegment(id);
		 		
				if (streetSegment.selected){
				  	setPolyLineColor(streetSegment.polyline, POLYLINE_COLOR);
					streetSegment.selected = false;
					this.segmentSelected = null;
			 		streetSegment.row.style.backgroundColor = streetSegment.bcolor;
				}
		 		else {
				  	this.deselectAllSegments();
				  	setPolyLineColor(streetSegment.polyline, POLYLINE_SELECTED_COLOR);
					streetSegment.selected = true;
					this.segmentSelected = streetSegment;
			 		streetSegment.row.style.backgroundColor = ROW_SELECTED_COLOR;
			 		panTo(streetSegment);
				}
		 	},
		    clearSegments : function() {
	    		for (var i = 0; i < this.streetSegments.length; i++) {
		    			this.streetSegments[i].polyline.setMap(null);
	    		}
		    	this.streetSegments = new Array();
		    	this.segmentSelected = null;
		    },
		    whichSegment : function (overlay) {
	    		for (var i = 0; i < this.streetSegments.length; i++) {
		        	if ((overlay == this.streetSegments[i].polyline)) {
		       			return this.streetSegments[i];
		       		}
		       	}
		       	return null;
			},
			deselectAllSegments : function () {
		  		if (this.segmentSelected){
				  	setPolyLineColor(this.segmentSelected.polyline, POLYLINE_COLOR);
				  	this.segmentSelected.selected = false;
				  	this.segmentSelected.row.style.backgroundColor = segmentSelected.bcolor;
				  	this.segmentSelected = null;
			 	}
		 	},
		 	hoverSegment : function (id, pan) {
		 	 	if (!this.segmentSelected){
			 		this.hoverDeselectAllSegments();
			 		var streetSegment = this.findSegment(id);
			 		if (streetSegment) {
				  		setPolyLineColor(streetSegment.polyline, POLYLINE_SELECTED_COLOR);
				  		streetSegment.row.style.backgroundColor = ROW_SELECTED_COLOR;
				 		if (pan) {
				 			panTo(streetSegment);
				 		}
					}
				}
		 	},
		 	hoverDeselectAllSegments : function() {
			 	if (!this.segmentSelected) {
		    		for (var i = 0; i < this.streetSegments.length; i++) {
		    			setPolyLineColor(this.streetSegments[i].polyline, POLYLINE_COLOR);
				 		this.streetSegments[i].row.style.backgroundColor = this.streetSegments[i].bcolor;
			  		}
			  	}
		 	}
	};

	function setUnableToGeocodeError(){
		document.getElementById("speedLimitChangeRequestTable:items:caption").innerHTML = "#{messages.sbs_badLatLng}"; 	  
	}	

  	function setPolyLineColor(polyline, color) {
  		polyline.setOptions( {
  			strokeColor : color
  		});
  	}

  	function reverseGeocode(lat,lng, isMarker) {
  		preciseLat = lat;
  		preciseLng = lng;
  		limit = (isMarker) ? 10 : 1;
  		inthincMap.reverseGeocode(mapsbs, lat, lng, addAddressToMap);
	}

  	function addAddressToMap(result, status) {
		if (status != google.maps.GeocoderStatus.OK) {
			setUnableToGeocodeError();
		}
		else {
			var fullAddress = result[0].formatted_address;
    
			// If a mouse click, use it, if an address entered, use what google returned
			if ( preciseLat == null && preciseLng == null ) {
				var point = result[0].geometry.location;
				addSegment(point.lat(), point.lng(), fullAddress, mapsbs.getZoom(), limit);  
			} else {
				addSegment(preciseLat, preciseLng, fullAddress, mapsbs.getZoom(), limit);
				preciseLat = null;
				preciseLng = null;
			}
		}
	}
  	
  	function addMarker(lat, lng, markerImage) {
  		inthincMap.createMarker(mapsbs, {
			position : new google.maps.LatLng(lat, lng), 
			iconImage : markerImage
  		}); 
  	}

  	function showLocation(address) {
  		inthincMap.lookupAddress(mapsbs, address, addAddressToMap);
  	}

  	function panTo(segment) {

  	    var segPath = segment.polyline.getPath();
  	    var vertexCount = segPath.getLength();
  	    var half = (vertexCount+vertexCount%2)/2;
  	    
  	    mapsbs.panTo(segPath.getAt(half));

  	}
