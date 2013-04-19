// extends MarkerCluster for additional functionality
// TODO: haven't got this working yet.

function inheritPrototype(subType, superType){
    var prototype = new Object(superType.prototype);   //create object
    prototype.constructor = subType;               //augment object
    subType.prototype = prototype;                 //assign object
}

function MarkerClustererWithMergedMarkerSets(map, opt_markers, opt_options, opt_custom) {
  this.extend(MarkerClustererWithMergedMarkerSets, MarkerClusterer);
  MarkerClusterer.call(this, map, opt_markers, opt_options);

  this.customCluster_opts_ = opt_custom || {};
  this.markerSets_ = new Array();
};

inheritPrototype(MarkerClustererWithMergedMarkerSets, MarkerClusterer);

/**
 * Extends a objects prototype by anothers.
 *
 * @param {Object} obj1 The object to be extended.
 * @param {Object} obj2 The object to extend with.
 * @return {Object} The new extended object.
 * @ignore
 */
MarkerClustererWithMergedMarkerSets.prototype.extend = function(obj1, obj2) {
  return (function(object) {
    for (var property in object.prototype) {
      this.prototype[property] = object.prototype[property];
    }
    return this;
  }).apply(obj1, [obj2]);
};

//overrides
  MarkerClustererWithMergedMarkerSets.prototype.isMarkerInBounds_ = function(marker, bounds) {
	  console.log('MarkerClustererWithMergedMarkerSets.prototype.isMarkerInBounds_');
	  return bounds.contains(marker.originalLatLng);
  };

  MarkerClustererWithMergedMarkerSets.prototype.addToClosestCluster_ = function(marker) {
	  
	  console.log('MarkerClustererWithMergedMarkerSets.prototype.addToClosestCluster_');
  		  var distance = 40000; // Some large number
  		  var clusterToAddTo = null;
  		  for (var i = 0, cluster; cluster = this.clusters_[i]; i++) {
  		    var center = cluster.getCenter();
  		    if (center) {
  		      var d = this.distanceBetweenPoints_(center, marker.originalLatLng);
  		      if (d < distance) {
  		        distance = d;
  		        clusterToAddTo = cluster;
  		      }
  		    }
  		  }

  		  if (clusterToAddTo && clusterToAddTo.isMarkerInClusterBounds(marker)) {
  		    clusterToAddTo.addMarker(marker);
  		  } else {
  		    var cluster = new LabeledCluster(this, this.customCluster_opts_);

  		    cluster.addMarker(marker);
  		    this.clusters_.push(cluster);
  		  }
  };

  
  
  // new methods
  
  MarkerClustererWithMergedMarkerSets.prototype.showMarkers = function(markerSetIndex) {
	if (!this.markerSets_[markerSetIndex].isAdded){
		  this.markerSets_[markerSetIndex].isAdded = true;
		  this.addMarkers(this.markerSets_[markerSetIndex].markers, true);
		  this.resetViewport();
		  this.redraw();

		  if(this.markerSets_.length == 1){
			  var that = this;
			  this.mcfn_ = google.maps.event.addListener(this.map_, "idle", function () {
				    that.resetViewport(false);
			  });
		  }
	}
};
MarkerClustererWithMergedMarkerSets.prototype.hideMarkers = function(markerSetIndex) {
	  if(this.markerSets_[markerSetIndex] && this.markerSets_[markerSetIndex].isAdded){
		  this.removeMarkers(this.markerSets_[markerSetIndex].markers);
		  this.markerSets_[markerSetIndex].isAdded = false;

		  if (this.markerSets_.length == 0){
			  google.maps.event.removeListener(this.mcfn_);
		  }
	  }

};

MarkerClustererWithMergedMarkerSets.prototype.clearAllMarkers = function() {
	
	this.clearMarkers();
	this.markerSets_ = [];
};

MarkerClustererWithMergedMarkerSets.prototype.addMarkerSet = function(markers) {
	  if (markers.markerSet instanceof Array){
		  var markerSet = [];
		  for(var i=0;i<markers.markerSet.length;i++){
			  var marker = markers.markerSet[i];
			  marker.isAdded = false;
			  marker.displayColor = markers.displayColor;
			  marker.originalLatLng = markers.markerSet[i].getPosition();
			  markerSet.push(marker);
		  }
		  this.markerSets_.push({'isAdded':true,
			  				'markers':markerSet});
		  
		  this.addMarkers(markerSet, true);
		  this.resetViewport();
		  this.redraw();
		  return this.markerSets_.length-1;  
	  }
};




/**
 * A cluster that contains markers.
 *
 * @param {MarkerClustererWithMergedMarkerSets} markerClusterer The markerclusterer that this
 *     cluster is associated with.
 * @constructor
 * @ignore
 */
function LabeledCluster(markerClusterer, clusterOpts) {
  this.markerClusterer_ = markerClusterer;
  this.map_ = markerClusterer.getMap();
  this.gridSize_ = markerClusterer.getGridSize();
  this.minClusterSize_ = markerClusterer.getMinClusterSize();
  this.averageCenter_ = markerClusterer.isAverageCenter();
  this.center_ = null;
  this.markers_ = [];
  this.bounds_ = null;
  this.clusterIcon_ = new StackedClusterIcon(this, markerClusterer.getStyles(),
      markerClusterer.getGridSize(), clusterOpts.canvasDrawFunction);

  //new
  this.clusterMarker_ = null;
  this.isMarkersStacked = false;
//  this.zoom_ = map_.getZoom();
  this.opts_ =  clusterOpts;
  this.label_ = this.opts_.labelText;
  this.isHidden = false;

}



/**
 * Determins if a marker is already added to the cluster.
 *
 * @param {google.maps.Marker} marker The marker to check.
 * @return {boolean} True if the marker is already added.
 */
LabeledCluster.prototype.isMarkerAlreadyAdded = function(marker) {
  if (this.markers_.indexOf) {
    return this.markers_.indexOf(marker) != -1;
  } else {
    for (var i = 0, m; m = this.markers_[i]; i++) {
      if (m == marker) {
        return true;
      }
    }
  }
  return false;
};


/**
 * Add a marker the cluster.
 *
 * @param {google.maps.Marker} marker The marker to add.
 * @return {boolean} True if the marker was added.
 */
LabeledCluster.prototype.addMarker = function(marker) {
  if (this.isMarkerAlreadyAdded(marker)) {
    return false;
  }

  if (!this.center_) {
//    this.center_ = marker.getPosition();
    this.center_ = marker.originalLatLng;
    this.calculateBounds_();
  } else {
    if (this.averageCenter_) {
      var l = this.markers_.length + 1;
//      var lat = (this.center_.lat() * (l-1) + marker.getPosition().lat()) / l;
//      var lng = (this.center_.lng() * (l-1) + marker.getPosition().lng()) / l;
      var lat = (this.center_.lat() * (l-1) + marker.originalLatLng.lat()) / l;
      var lng = (this.center_.lng() * (l-1) + marker.originalLatLng.lng()) / l;
      this.center_ = new google.maps.LatLng(lat, lng);
      this.calculateBounds_();
    }
  }

  marker.isAdded = true;
  this.markers_.push(marker);

  var len = this.markers_.length;
  if (len < this.minClusterSize_) { // && marker.getMap() != this.map_) {
    // Min cluster size not reached so show the marker.
//	  marker.setMap(this.map_);
	  this.stackMarker(marker, len, this.center_);
  }

  if (len == this.minClusterSize_) {
    // Hide the markers that were showing.
    for (var i = 0; i < len; i++) {
      this.markers_[i].setMap(null);
    }
  }

  if (len >= this.minClusterSize_) {
	  marker.setMap(null);
  }

  this.updateIcon();
  return true;
};

LabeledCluster.prototype.stackMarker = function(marker, len, center) {
	var projection = this.markerClusterer_.getProjection();
	// pixel height is 20, what is it in lat
	var stackedPoint = projection.fromLatLngToDivPixel(center);
	stackedPoint.y += (20 * (len-1));
	stackedPoint.y -= 10;
	var stackedPosition = projection.fromDivPixelToLatLng(stackedPoint); 
	marker.setPosition(stackedPosition);
    marker.setMap(this.map_);
	
};


/**
 * Returns the marker clusterer that the cluster is associated with.
 *
 * @return {MarkerClusterer} The associated marker clusterer.
 */
LabeledCluster.prototype.getMarkerClusterer = function() {
  return this.markerClusterer_;
};


/**
 * Returns the bounds of the cluster.
 *
 * @return {google.maps.LatLngBounds} the cluster bounds.
 */
LabeledCluster.prototype.getBounds = function() {
  var bounds = new google.maps.LatLngBounds(this.center_, this.center_);
  var markers = this.getMarkers();
  for (var i = 0, marker; marker = markers[i]; i++) {
    bounds.extend(marker.getPosition());
  }
  return bounds;
};


/**
 * Removes the cluster
 */
LabeledCluster.prototype.remove = function() {
  this.clusterIcon_.remove();
  this.markers_.length = 0;
  delete this.markers_;
};


/**
 * Returns the center of the cluster.
 *
 * @return {number} The cluster center.
 */
LabeledCluster.prototype.getSize = function() {
  return this.markers_.length;
};


/**
 * Returns the center of the cluster.
 *
 * @return {Array.<google.maps.Marker>} The cluster center.
 */
LabeledCluster.prototype.getMarkers = function() {
  return this.markers_;
};


/**
 * Returns the center of the cluster.
 *
 * @return {google.maps.LatLng} The cluster center.
 */
LabeledCluster.prototype.getCenter = function() {
  return this.center_;
};


/**
 * Calculated the extended bounds of the cluster with the grid.
 *
 * @private
 */
LabeledCluster.prototype.calculateBounds_ = function() {
  var bounds = new google.maps.LatLngBounds(this.center_, this.center_);
  this.bounds_ = this.markerClusterer_.getExtendedBounds(bounds);
};


/**
 * Determines if a marker lies in the clusters bounds.
 *
 * @param {google.maps.Marker} marker The marker to check.
 * @return {boolean} True if the marker lies in the bounds.
 */
LabeledCluster.prototype.isMarkerInClusterBounds = function(marker) {
//  return this.bounds_.contains(marker.getPosition());
  return this.bounds_.contains(marker.originalLatLng);
};


/**
 * Returns the map that the LabeledCluster is associated with.
 *
 * @return {google.maps.Map} The map.
 */
LabeledCluster.prototype.getMap = function() {
  return this.map_;
};


/**
 * Updates the cluster icon
 */
LabeledCluster.prototype.updateIcon = function() {
  var zoom = this.map_.getZoom();
  var mz = this.markerClusterer_.getMaxZoom();

  if (mz && zoom > mz) {
    // The zoom is greater than our max zoom so show all the markers in cluster.
    for (var i = 0, marker; marker = this.markers_[i]; i++) {
      marker.setMap(this.map_);
    }
    return;
  }

  if (this.markers_.length < this.minClusterSize_) {
    // Min cluster size not yet reached.
    this.clusterIcon_.hide();
    return;
  }

  var numStyles = this.markerClusterer_.getStyles().length;
  var sums = this.markerClusterer_.getCalculator()(this.markers_, numStyles);
  this.clusterIcon_.setCenter(this.center_);
  this.clusterIcon_.setSums(sums);
  this.clusterIcon_.show();
};


/**
 * A cluster icon
 *
 * @param {Cluster} cluster The cluster to be associated with.
 * @param {Object} styles An object that has style properties:
 *     'url': (string) The image url.
 *     'height': (number) The image height.
 *     'width': (number) The image width.
 *     'anchor': (Array) The anchor position of the label text.
 *     'textColor': (string) The text color.
 *     'textSize': (number) The text size.
 *     'backgroundPosition: (string) The background postition x, y.
 * @param {number=} opt_padding Optional padding to apply to the cluster icon.
 * @constructor
 * @extends google.maps.OverlayView
 * @ignore
 */
function StackedClusterIcon(cluster, styles, opt_padding, drawFunction) {
  cluster.getMarkerClusterer().extend(StackedClusterIcon, google.maps.OverlayView);


  this.styles_ = styles;
  this.padding_ = opt_padding || 0;
  this.cluster_ = cluster;
  this.center_ = null;
  this.map_ = cluster.getMap();
  this.div_ = null;
  this.sums_ = null;
  this.visible_ = false;
  this.drawFunction_ = drawFunction; 

  this.setMap(this.map_);
}


/**
 * Triggers the clusterclick event and zoom's if the option is set.
 */
StackedClusterIcon.prototype.triggerClusterClick = function() {
  var markerClusterer = this.cluster_.getMarkerClusterer();
  google.maps.event.trigger(markerClusterer, 'clusterclick', this.cluster_, this.center_);

};


/**
 * Adding the cluster icon to the dom.
 * @ignore
 */
StackedClusterIcon.prototype.onAdd = function() {
  this.div_ = document.createElement('DIV');
  var panes = this.getPanes();
  panes.overlayMouseTarget.appendChild(this.div_);

  var that = this;
  google.maps.event.addDomListener(this.div_, 'click', function() {
    that.triggerClusterClick();
  });
};


/**
 * Returns the position to place the div dending on the latlng.
 *
 * @param {google.maps.LatLng} latlng The position in latlng.
 * @return {google.maps.Point} The position in pixels.
 * @private
 */
StackedClusterIcon.prototype.getPosFromLatLng_ = function(latlng) {
  var pos = this.getProjection().fromLatLngToDivPixel(latlng);
  return pos;
};


/**
 * Draw the icon.
 * @ignore
 */
StackedClusterIcon.prototype.draw = function() {
	if (this.visible_) {
		var pos = this.getPosFromLatLng_(this.center_);
		if (!this.div_ || this.div_ == null) {
			this.div_ = document.createElement('DIV');
		}
	    this.drawFunction_(this.cluster_.markers_, this.sums_.text, this.div_, pos);
  }
};


/**
 * Hide the icon.
 */
StackedClusterIcon.prototype.hide = function() {
  if (this.div_) {
    this.div_.style.display = 'none';
  }
  this.visible_ = false;
};


/**
 * Position and show the icon.
 */
StackedClusterIcon.prototype.show = function() {
	if (this.div_) {
    this.div_.style.display = '';
  }
  this.visible_ = true;
};


/**
 * Remove the icon from the map
 */
StackedClusterIcon.prototype.remove = function() {
  this.setMap(null);
};


/**
 * Implementation of the onRemove interface.
 * @ignore
 */
StackedClusterIcon.prototype.onRemove = function() {
  if (this.div_ && this.div_.parentNode) {
    this.hide();
    this.div_.parentNode.removeChild(this.div_);
    this.div_ = null;
  }
};

/**
 * Set the sums of the icon.
 *
 * @param {Object} sums The sums containing:
 *   'text': (string) The text to display in the icon.
 */
StackedClusterIcon.prototype.setSums = function(sums) {
  this.sums_ = sums;
  this.text_ = sums.text;
};
/**
 * Sets the center of the icon.
 *
 * @param {google.maps.LatLng} center The latlng to set as the center.
 */
StackedClusterIcon.prototype.setCenter = function(center) {
  this.center_ = center;
};


