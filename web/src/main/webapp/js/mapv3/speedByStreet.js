var mapsbs = null;
var streetSegments = null;
var preciseLat = null;
var preciseLng = null;
var limit;
var geocoder = new google.maps.Geocoder();

var iconImage;

    
	function initMap(lat, lng, zoom){
		console.log('sbs initMap');
// TODO: should this check for mapsbs == null and init or reinit?		
		mapsbs = new google.maps.Map(document.getElementById('map-sbs'), {
			 zoom: zoom,
			 center: new google.maps.LatLng(lat, lng),
			 mapTypeId: google.maps.MapTypeId.ROADMAP,
			 mapTypeControl: true,
			 mapTypeControlOptions : {
				style : google.maps.MapTypeControlStyle.DROPDOWN_MENU, 
			 },
			 overviewMapControl: true,
			 overviewMapControlOptions: {
			      opened: true,
			 }
		});
		
		console.log('sbs panel initMap - complete ' + mapsbs.getZoom());

		google.maps.event.addListener(mapsbs, "click", function(mouseEvent) {
			reverseGeocode(mouseEvent.latLng.lat(), mouseEvent.latLng.lng(), "", false);
        });

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
				  	setPolyLineColor(streetSegment.polyline, "#666666");
					streetSegment.selected = false;
					this.segmentSelected = null;
			 		streetSegment.row.style.backgroundColor = streetSegment.bcolor;
				}
		 		else {
				  	this.deselectAllSegments();
				  	setPolyLineColor(streetSegment.polyline, "#ff0000");
					streetSegment.selected = true;
					this.segmentSelected = streetSegment;
			 		streetSegment.row.style.backgroundColor = '#DfDfF8';
			 		panTo(streetSegment);
				}
		 	},
		    clearSegments : function() {
		    	console.log("clear segments - cnt = " + this.streetSegments.length);
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
		  			console.log('deselectAll');
				  	setPolyLineColor(this.segmentSelected.polyline, "#666666");
				  	this.segmentSelected.selected = false;
				  	this.segmentSelected.row.style.backgroundColor = segmentSelected.bcolor;
				  	this.segmentSelected = null;
		  			console.log('deselectAll-done');
			 	}
		 	},
		 	hoverSegment : function (id, pan) {
		 	 	if (!this.segmentSelected){
			 		this.hoverDeselectAllSegments();
			 		var streetSegment = this.findSegment(id);
			 		if (streetSegment) {
				  		setPolyLineColor(streetSegment.polyline, "#ff0000");
				  		streetSegment.row.style.backgroundColor = '#DfDfF8';
				 		if (pan) {
				 			panTo(streetSegment);
				 		}
					}
				}
		 	},
		 	hoverDeselectAllSegments : function() {
			 	if (!this.segmentSelected) {
		    		for (var i = 0; i < this.streetSegments.length; i++) {
		    			setPolyLineColor(this.streetSegments[i].polyline, "#666666");
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
		geocoder.geocode({
			'location': new google.maps.LatLng(lat, lng)
		}, addAddressToMap);
	}

  	function addAddressToMap(result, status) {
		if (status != google.maps.GeocoderStatus.OK) {
			console.log('Reverse Geocoding failed status: '+ status);
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
		new google.maps.Marker({ 
			position : new google.maps.LatLng(lat, lng), 
			map : mapsbs,
			icon : markerImage
		});
  	}

  	function showLocation(address) {
  		geocoder.geocode({
  			'address' : address
  		}, addAddressToMap);
  	}

  	function panTo(segment) {

  	    var segPath = segment.polyline.getPath();
  	    var vertexCount = segPath.getLength();
  	    var half = (vertexCount+vertexCount%2)/2;
  	    
  	    mapsbs.panTo(segPath.getAt(half));

  	}
