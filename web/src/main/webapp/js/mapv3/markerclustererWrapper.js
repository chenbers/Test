var markerClusterer;

function getMarkerClustererStyles(imagePath) { 
	//Pulled out of markerclusterer.js
	var sizes = [53, 56, 66, 78, 90];
	var styles = [];

	for (var i = 1; i <= 5; ++i) {
		styles.push({
	      'url': imagePath + "/images/markerClusterer/m" + i + ".png",
	      'height': sizes[i - 1],
	      'width': sizes[i - 1]
	    });
	}
	return styles;
}

