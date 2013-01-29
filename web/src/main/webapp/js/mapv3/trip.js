var ADDRESS_LOOKUP_SERVER_SIDE1 = 1;
var ADDRESS_LOOKUP_SERVER_SIDE2 = 2;
var ADDRESS_LOOKUP_CLIENT_SIDE = 3;
var VIOLATION_BUBBLE_ID = "eventBubble";
var VIOLATION_BUBBLE_ADDRESS_ID = "tripsMapForm:bubbleAddress";

function Trip(outline, imagePath, addressLookupType, inProgress) {
	this.points = outline;
	this.imagePath = imagePath;
	this.inProgress = inProgress;
	this.polyline = null;
	this.selectedViolationMarker = null;
	this.doReverseGeocode = (addressLookupType == ADDRESS_LOOKUP_CLIENT_SIDE);
	this.tripBounds = new google.maps.LatLngBounds();
	for (var i = 0; i < this.points.length; i++) {
		this.tripBounds.extend(this.points[i]);
	}
}

Trip.prototype = {
	constructor: Trip,
	displayOnMap: function(map) {
		this.polyline = inthincMap.addPolyline(map, this.points, {
			strokeColor:  "#0000ff",
			strokeWeight: 6,
			strokeOpacity : 0.5,
			show : true
		});

		var startTripMarker = inthincMap.createMarker(map, {
				position : this.points[0],
				iconImage : {
					anchor : new google.maps.Point(5, 13),
					scaledSize : new google.maps.Size(20, 20),
					url: this.imagePath+"/images/ico_start.png"
				}
		});
		
		this.createInfoWindow(map, startTripMarker, "tripStartBubble", "tripForm:tripStartAddress");

		var endTripMarker = inthincMap.createMarker(map, {
				position : this.points[this.points.length-1],
				iconImage : {
					anchor : new google.maps.Point(5, 13),
					scaledSize : new google.maps.Size(20, 20),
					url: this.imagePath + ((this.inProgress == true) ? "/images/ico_inprogress_trip.png" : "/images/ico_end_trip.png")
				}
		});
		
		if (this.inProgress == true) {
			this.createInfoWindow(map, endTripMarker, "tripInProgressBubble", "tripForm:tripInProgressAddress");
		}
		else {
			this.createInfoWindow(map, endTripMarker, "tripEndBubble", "tripForm:tripEndAddress");
		}

		inthincMap.centerAndZoom(map, this.tripBounds);
	},
	createInfoWindow : function(map, marker, bubbleDivID, addressDivID) {
		var doReverseGeocode = this.doReverseGeocode;
		var lat = marker.getPosition().lat();
		var lng = marker.getPosition().lng();
		google.maps.event.addListener(marker, 'click', function() {			
			if (doReverseGeocode) {
				reverseGeocodeAddress(map, lat, lng, addressDivID, function() {
					inthincMap.infoWindowAtMarker(map, bubbleDivID, marker);
				});
			}
			else {
				inthincMap.infoWindowAtMarker(map, bubbleDivID, marker);
			}
		});
	},
	addViolationMarker : function(map, position, iconImage, itemID, callback) {
		var violationMarker = inthincMap.createMarker(map, {
			position : position,
			iconImage : {
				anchor : new google.maps.Point(6, 20),
				scaledSize : new google.maps.Size(21, 20),
				url: iconImage
			}
		});
		var self = this;
		
		google.maps.event.addListener(violationMarker, 'click', function() {
     	 	self.selectedViolationMarker = violationMarker;
     	 	callback(itemID);
		});
	},
	displaySelectedViolationPopup : function() {
		var marker = this.selectedViolationMarker;
		if (this.doReverseGeocode) {
			reverseGeocodeAddress(map, marker.getPosition().lat(), marker.getPosition().lng(), VIOLATION_BUBBLE_ADDRESS_ID, function() {
				inthincMap.infoWindowAtMarker(map, VIOLATION_BUBBLE_ID, marker);
			});
		}
		else {
			inthincMap.infoWindowAtMarker(map, VIOLATION_BUBBLE_ID, marker);
		}
	}
};


function reverseGeocodeAddress(map, lat, lng, addressDivID, callback){
	inthincMap.reverseGeocode(map, lat, lng, function(result, status) {
    	  if (status != google.maps.GeocoderStatus.OK) {
				lookUpZone(lat, lng, addressDivID, callback);
    	  }
    	  else {
  			var addressEl = document.getElementById(addressDivID);                        
			addressEl.innerHTML = result[0].formatted_address;
			if (callback) {
				callback();
			}
    	  }
	});
}

