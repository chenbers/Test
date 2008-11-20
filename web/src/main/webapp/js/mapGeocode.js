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

