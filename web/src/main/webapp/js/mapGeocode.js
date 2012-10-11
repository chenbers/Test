var geocoder = new GClientGeocoder();

function centerMapAtAddress(address, zoomLevel)
{
	geocoder.getLatLng(address,
		function(point) {
			if (point) {
				alert(addressStr);
				map.setCenter(point, zoomLevel);
			}
		}
	);
}

function centerMapAtZone(latLngBounds)
{
	map.setCenter(latLngBounds.getCenter(),map.getBoundsZoomLevel(latLngBounds)-1);
}

function showAddress(address)
{
	if (address.length == 0)
	{
		map.setCenter(new GLatLng(39.0, -104.0), 4);
		return;
	}

	geocoder.getLatLng(address,
		function(point) {
			if (!point) {
				alert("The address: [" + address + "] was not found.");
				map.setCenter(new GLatLng(39.0, -104.0), 4);
			} else {
				map.setCenter(point, 13);
				var marker = new GMarker(point);
				map.addOverlay(marker);
				marker.openInfoWindowHtml(address);
				GEvent.addListener(marker, "click", function() {
					marker.openInfoWindowHtml(address);
				});
		   }
		}
  );
}

function showAddressAndZone(latLngBounds, address)
{
	if (address.length == 0)
	{
		if (latLngBounds)
			map.setCenter(latLngBounds.getCenter(),map.getBoundsZoomLevel(latLngBounds)-1);
		else
			map.setCenter(new GLatLng(39.0, -104.0), 4);
		return;
	}
	geocoder.getLatLng(address,
		function(point) {
			if (!point) {
				alert("The address: " + address + " was not found.");
			} else {
				if (!latLngBounds)
				{
					showAddress(address);
					return;
				}
				latLngBounds.extend(point);
				map.setCenter(latLngBounds.getCenter(),map.getBoundsZoomLevel(latLngBounds)-1);
				var marker = new GMarker(point);
				map.addOverlay(marker);
				marker.openInfoWindowHtml(address);
				GEvent.addListener(marker, "click", function() {
						marker.openInfoWindowHtml(address);
				});
		   }
		}
  );
}

/*
 * Displays the selected address at the appropriate zoom level. e.i. Typing in utah will display the entire state of utah
 * within the map.
 */
function showAddressWithAccuracy(address) {
	  
	  var geocoder = new GClientGeocoder();
	  var zoomLookup = new Array(2,4,6,10,12,13,16,16,17);
	  
	  geocoder.getLocations(address,
		    function(response) {
		      if(response.Status.code!=200){
		    	 alert("The address: [" + address + "] was not found.");
		      } else {
		    	  var place = response.Placemark[0];
			        var accuracy = place.AddressDetails.Accuracy;
			        var point = new GLatLng(place.Point.coordinates[1],place.Point.coordinates[0]);
			        map.setCenter(point, zoomLookup[accuracy]);
					
			        var marker = new GMarker(point);
			        map.addOverlay(marker);
			        marker.openInfoWindowHtml(address);
		      }
		    }
	  );										  
}

