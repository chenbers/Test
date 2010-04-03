/**
 * @name MarkerClusterer
 * @version 1.0
 * @author Xiaoxi Wu
 * @copyright (c) 2009 Xiaoxi Wu
 * @fileoverview
 * This javascript library creates and manages per-zoom-level 
 * clusters for large amounts of markers (hundreds or thousands).
 * This library was inspired by the <a href="http://www.maptimize.com">
 * Maptimize</a> hosted clustering solution.
 * <br /><br/>
 * <b>How it works</b>:<br/>
 * The <code>MarkerClusterer</code> will group markers into clusters according to
 * their distance from a cluster's center. When a marker is added,
 * the marker cluster will find a position in all the clusters, and 
 * if it fails to find one, it will create a new cluster with the marker.
 * The number of markers in a cluster will be displayed
 * on the cluster marker. When the map viewport changes,
 * <code>MarkerClusterer</code> will destroy the clusters in the viewport 
 * and regroup them into new clusters.
 *
 */

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * @name MarkerClustererOptions
 * @class This class represents optional arguments to the {@link MarkerClusterer}
 * constructor.
 * @property {Number} [maxZoom] The max zoom level monitored by a
 * marker cluster. If not given, the marker cluster assumes the maximum map
 * zoom level. When maxZoom is reached or exceeded all markers will be shown
 * without cluster.
 * @property {Number} [gridSize=60] The grid size of a cluster in pixel. Each
 * cluster will be a square. If you want the algorithm to run faster, you can set
 * this value larger.
 * @property {Array of MarkerStyleOptions} [styles]
 * Custom styles for the cluster markers.
 * The array should be ordered according to increasing cluster size,
 * with the style for the smallest clusters first, and the style for the
 * largest clusters last.
 */

/**
 * @name MarkerStyleOptions
 * @class An array of these is passed into the {@link MarkerClustererOptions}
 * styles option.
 * @property {String} [url] Image url.
 * @property {Number} [height] Image height.
 * @property {Number} [height] Image width.
 * @property {Array of Number} [opt_anchor] Anchor for label text, like [24, 12]. 
 *    If not set, the text will align center and middle.
 * @property {String} [opt_textColor="black"] Text color.
 */

/**
 * Creates a new MarkerClusterer to cluster markers on the map.
 *
 * @constructor
 * @param {GMap2} map The map that the markers should be added to.
 * @param {Array of GMarker} opt_markers Initial set of markers to be clustered.
 * @param {MarkerClustererOptions} opt_opts A container for optional arguments.
 */
function MarkerClustererWithMergedMarkerSets(map, opt_opts, opts_customClusterOpts) {
  // private members
  var map_ = map;
  var me_ = this;
  var mcfn_ = null;
  
  // options
  var maxZoom_ = null;
  var gridSize_x = 60;
  var gridSize_y = 60;
  var sizes = [53, 56, 66, 78, 90];
  var styles_ = [];
  var maxStacked_ = 0;
  var customClusters_ = false;
  var customCluster_opts_ = null;
  
  //For all markers
  var clusters_ = [];
  var leftMarkers_ = [];
  
  //Per marker set
  var markerSets_ = new Array();
  
  var i = 0;
  for (i = 1; i <= 5; ++i) {
    styles_.push({
      'url': "http://gmaps-utility-library.googlecode.com/svn/trunk/markerclusterer/images/m" + i + ".png",
      'height': sizes[i - 1],
      'width': sizes[i - 1]
    });
  }

  if (typeof opt_opts === "object" && opt_opts !== null) {
    if (typeof opt_opts.gridSize_x === "number" && opt_opts.gridSize_x > 0) {
      gridSize_x = opt_opts.gridSize_x;
    }
    if (typeof opt_opts.gridSize_y === "number" && opt_opts.gridSize_y > 0) {
        gridSize_y = opt_opts.gridSize_y;
      }
    if (typeof opt_opts.maxZoom === "number") {
      maxZoom_ = opt_opts.maxZoom;
    }
    if (typeof opt_opts.styles === "object" && opt_opts.styles !== null && opt_opts.styles.length !== 0) {
      styles_ = opt_opts.styles;
    }
    if (typeof opt_opts.maxStacked === "number") {
        maxStacked_ = opt_opts.maxStacked;
    }
    if(typeof opt_opts.customClusters === "boolean") {
    	
    	if (opt_opts.customClusters && opts_customClusterOpts != null){
    		
    		//Validate custom cluster opts
        	if (  opts_customClusterOpts.icon != null &&
        		  opts_customClusterOpts.labelText != null &&
        		  opts_customClusterOpts.labelClass != null &&
        		  opts_customClusterOpts.canvasDrawFunction != null){

    			customClusters_ = opt_opts.customClusters;
    			customCluster_opts_ = opts_customClusterOpts;
        	}
    	}
    }

  }
  /**
   * When we add a marker, the marker may not in the viewport of map, then we don't deal with it, instead
   * we add the marker into a array called leftMarkers_. When we reset MarkerClusterer we should add the
   * leftMarkers_ into MarkerClusterer.
   */
  function addLeftMarkers_() {
	  
    if (leftMarkers_.length === 0) {
      return;
    }
    var leftMarkers = [];
    for (i = 0; i < leftMarkers_.length; ++i) {
      me_.addMarker(leftMarkers_[i], true, null, null, true);
    }
    leftMarkers_ = leftMarkers;
  }

   /**
   * Remove all markers from MarkerClusterer.
   */
  this.clearAllMarkers = function(){
	  
		  for (var j=0;j<clusters_.length;j++){
			  if(clusters_[j] !== null){
				  clusters_[j].clearMarkers();
			  }
	  }
	  clusters_ = [];
	  markerSets_ = [];
	  leftMarkers_ = [];
	  
	  GEvent.removeListener(mcfn_);
 };
  this.hideMarkers = function (markerSetIndex) {
	  
	  if(markerSets_[markerSetIndex] && markerSets_[markerSetIndex].isAdded){
		  
		  for (var i=0; i< markerSets_[markerSetIndex].markers.length; i++){
			  
			  markerSets_[markerSetIndex].markers[i].hidden = true;
			  
		  }
		  markerSets_[markerSetIndex].isAdded = false;
		  
		  me_.resetViewport(true);
		  
		  if (markerSets_.length == 0){
			  
			  GEvent.removeListener(mcfn_);
		  }
	  }
  };
  this.showMarkers = function (markerSetIndex) {
	  
	  if (!markerSets_[markerSetIndex].isAdded){
	  
		  for (var i=0; i< markerSets_[markerSetIndex].markers.length; i++){
			  
			  markerSets_[markerSetIndex].markers[i].hidden = false;
		  }
		  
		  markerSets_[markerSetIndex].isAdded = true;
		  this.addMarkers(markerSets_[markerSetIndex].markers);
		  me_.resetViewport(true);

		  if(markerSets_.length == 1){
			  
			  mcfn_ = GEvent.addListener(map_, "moveend", function () {
				    me_.resetViewport(false);
				  });
		  }
	  }

  };
 
  /**
   * add a new set of markers
   */
  this.addMarkerSet = function(markers){
	  
	  // initialize
	  if (markers.markerSet instanceof Array){
		  //we have some marker sets
		  //add each separately
		  var markerSet = [];
		  for(var i=0;i<markers.markerSet.length;i++){
			  markerSet.push({'isAdded':false,
				  			  'hidden':false,
				  			  'displayColor':markers.displayColor,
				  			  'originalLatLng':markers.markerSet[i].getLatLng(),
				  			  'marker':markers.markerSet[i]});
		  }
		  markerSets_.push({'isAdded':true,
			  				'markers':markerSet});
		  
		  this.addMarkers(markerSet);
		  		  
		  if(markerSets_.length == 1){
			  
			  mcfn_ = GEvent.addListener(map_, "moveend", function () {
				    me_.resetViewport(false);
				  });
		  }
		    me_.resetViewport(true);

	  }

  };
  /**
   * Check a marker, whether it is in current map viewport.
   * @private
   * @return {Boolean} if it is in current map viewport
   */
  function isLatLngInViewPoint_(latLng) {
    return map_.getBounds().containsLatLng(latLng);
  }

  /**
   * When reset MarkerClusterer, there will be some markers get out of its cluster.
   * These markers should be add to new clusters.
   * @param {Array of GMarker} markers Markers to add.
   */
  function reAddMarkers_(markers) {
    var len = markers.length;
    var clusters = [];
    for(var i=0; i<len;i++){
//    for (var i = len - 1; i >= 0; --i) {
//   this.addMarker = function (marker, opt_isNodraw, opt_isAdded, opt_clusters, opt_isNoCheck) {
      me_.addMarker(markers[i], true, markers[i].isAdded, clusters, true);
    }
    addLeftMarkers_();
  }

  /**
   * Add a marker.
   * @private
   * @param {GMarker} marker Marker you want to add
   * @param {Boolean} opt_isNodraw Whether redraw the cluster contained the marker
   * @param {Boolean} opt_isAdded Whether the marker is added to map. Never use it.
   * @param {Array of Cluster} opt_clusters Provide a list of clusters, the marker
   *     cluster will only check these cluster where the marker should join.
   */
  this.addMarker = function (marker, opt_isNodraw, opt_isAdded, opt_clusters, opt_isNoCheck) {
	
	if (opt_isNoCheck === true) {
		
		if (marker.hidden) return;
	}
	else {
		if (marker.hidden){
			
	        leftMarkers_.push(marker);
			return;
		}
	}	
	
    if (opt_isNoCheck !== true) {
      if (!isLatLngInViewPoint_(marker.originalLatLng)) {
        leftMarkers_.push(marker);
        return;
      }
    }

    var isAdded = opt_isAdded;
    var clusters = opt_clusters;
    var pos = map_.fromLatLngToDivPixel(marker.originalLatLng);

    if (typeof isAdded !== "boolean") {
      isAdded = false;
    }
    if (typeof clusters !== "object" || clusters === null) {
      clusters = clusters_;
    }

    var length = clusters.length;
    var cluster = null;
    for(var i=0;i<length;i++){
//    for (var i = length - 1; i >= 0; i--) {
      cluster = clusters[i];
      var center = cluster.getCenter();
      if (center === null) {
        continue;
      }
      center = map_.fromLatLngToDivPixel(center);

      // Found a cluster which contains the marker.
      if (pos.x >= center.x - gridSize_x && pos.x <= center.x + gridSize_x &&
          pos.y >= center.y - gridSize_y && pos.y <= center.y + gridSize_y) {
    	  
    	marker.isAdded = isAdded;
        cluster.addMarker(marker);
        
        if (!opt_isNodraw) {
          cluster.redraw_();
        }
        return;
      }
    }

    // No cluster contain the marker, create a new cluster.
    cluster = new LabeledCluster(this, customCluster_opts_);
    
	marker.isAdded = isAdded;
    cluster.addMarker(marker);
    
    if (!opt_isNodraw) {
      cluster.redraw_();
    }

    // Add this cluster both in clusters provided and clusters_
    clusters.push(cluster);
    if (clusters !== clusters_) {
      clusters_.push(cluster);
    }
  };

  /**
   * Remove a marker.
   *
   * @param {GMarker} marker The marker you want to remove.
   */

  this.removeMarker = function (marker) {
    for (var i = 0; i < clusters_.length; ++i) {
      if (clusters_[i].removeMarker(marker)) {
        clusters_[i].redraw_();
        return;
      }
    }
  };

  /**
   * Redraw all clusters in viewport.
   */
  this.redraw_ = function () {
    var clusters = this.getClustersInViewport_();
    for (var i = 0; i < clusters.length; ++i) {
      clusters[i].redraw_(true);
    }
  };

  /**
   * Get all clusters in viewport.
   * @return {Array of Cluster}
   */
  this.getClustersInViewport_ = function () {
    var clusters = [];
    var curBounds = map_.getBounds();
	for (var i = 0; i < clusters_.length; i ++) {
		if (clusters_[i].isInBounds(curBounds)) {
			clusters.push(clusters_[i]);
	    }
	}
    return clusters;
  };

  /**
   * Get max zoom level.
   * @private
   * @return {Number}
   */
  this.getMaxZoom_ = function () {
    return maxZoom_;
  };

  /**
   * Get map object.
   * @private
   * @return {GMap2}
   */
  this.getMap_ = function () {
    return map_;
  };
 
  /**
   * Get grid size
   * @private
   * @return {Number}
   */
  this.getGridSize_x = function () {
    return gridSize_x;
  };
  this.getGridSize_y = function () {
	    return gridSize_y;
	  };

  /**
   * Get total number of markers.
   * @return {Number}
   */
  this.getTotalMarkers = function () {
    var result = 0;
    for (var i = 0; i < clusters_.length; ++i) {
      result += clusters_[i].getTotalMarkers();
    }
    return result;
  };

  /**
   * Get total number of clusters.
   * @return {int}
   */
  this.getTotalClusters = function () {
    return clusters_.length;
  };
/**
 * 
 * @return {int}
 */
  this.getMaxStacked = function () {
	    return maxStacked_;
	  };
 /**
   * Collect all markers of clusters in viewport and regroup them.
   */
  this.resetViewport = function (ignoreZoom) {
		    var clusters = this.getClustersInViewport_();
		    var tmpMarkers = [];
		    var removed = 0;
		
		    for (var i = 0; i < clusters.length; ++i) {
		      var cluster = clusters[i];
		      var oldZoom = cluster.getCurrentZoom();
		      if (oldZoom === null) {
		        continue;
		      }
		      var curZoom = map_.getZoom();
		      if (ignoreZoom ||(curZoom !== oldZoom)) {
		
		        // If the cluster zoom level changed then destroy the cluster
		        // and collect its markers.
		        var mks = cluster.getMarkers();
		        tmpMarkers = tmpMarkers.concat(mks);
//		        for (var j = 0; j < mks.length; ++j) {
//		          var newMarker = {
//		            'isAdded': false,
//		            'marker': mks[j].marker
//		          };
//		          tmpMarkers.push(mks[j]);
//		        }
		        cluster.clearMarkers();
		        removed++;
		        for (j = 0; j < clusters_.length; ++j) {
		          if (cluster === clusters_[j]) {
		            clusters_.splice(j, 1);
		          }
		        }
		      }
		    }
		
		    // Add the markers collected into marker cluster to reset
		    reAddMarkers_(tmpMarkers);
		    this.redraw_();
  };


  /**
   * Add a set of markers.
   *
   * @param {Array of GMarker} markers The markers you want to add.
   */
  this.addMarkers = function (markers) {
    for (var i = 0; i < markers.length; ++i) {
      this.addMarker(markers[i], true);
    }
//    this.redraw_();
  };

  // when map move end, regroup.
  mcfn_ = GEvent.addListener(map_, "moveend", function () {
    me_.resetViewport(false);
  });
}

/**
 * Create a cluster to collect markers.
 * A cluster includes some markers which are in a block of area.
 * If there are more than one markers in cluster, the cluster
 * will create a {@link LabeledMarker} and show the total number
 * of markers in cluster, or if there are 3 or less will stack the markers
 *
 * @constructor
 * @private
 * @param {LabeledCluster} markerClusterer The marker cluster object
 */
function LabeledCluster(markerClusterer, clusterOpts) {
  var center_ = null;
  var markers_ =[];
  var markerClusterer_ = markerClusterer;
  var map_ = markerClusterer.getMap_();
  var clusterMarker_ = null;
  var isMarkersStacked = false;
  var zoom_ = map_.getZoom();
  var opts_ =  clusterOpts;
  var label_ = opts_.labelText;
  var isHidden = false;

  /**
   * Get markers of this cluster.
   *
   * @return {Array of GMarker}
   */
  this.getMarkers = function () {
    return markers_;
  };

  /**
   * If this cluster intersects certain bounds.
   *
   * @param {GLatLngBounds} bounds A bounds to test
   * @return {Boolean} Is this cluster intersects the bounds
   */
  this.isInBounds = function (bounds) {
    if (center_ === null) {
      return false;
    }

    if (!bounds) {
      bounds = map_.getBounds();
    }
    var sw = map_.fromLatLngToDivPixel(bounds.getSouthWest());
    var ne = map_.fromLatLngToDivPixel(bounds.getNorthEast());

    var centerxy = map_.fromLatLngToDivPixel(center_);
    var inViewport = true;
    var gridSize_x = markerClusterer.getGridSize_x();
    var gridSize_y = markerClusterer.getGridSize_y();
    if (zoom_ !== map_.getZoom()) {
      var dl = map_.getZoom() - zoom_;
      gridSize_x = Math.pow(2, dl) * gridSize_x;
      gridSize_y = Math.pow(2, dl) * gridSize_y;
    }
    if (ne.x !== sw.x && (centerxy.x + gridSize_x < sw.x || centerxy.x - gridSize_x > ne.x)) {
      inViewport = false;
    }
    if (inViewport && (centerxy.y + gridSize_y < ne.y || centerxy.y - gridSize_y > sw.y)) {
      inViewport = false;
    }
    return inViewport;
  };

  /**
   * Get cluster center.
   *
   * @return {GLatLng}
   */
  this.getCenter = function () {
    return center_;
  };

  /**
   * Add a marker.
   *
   * @param {Object} marker An object of marker you want to add:
   *   {Boolean} isAdded If the marker is added on map.
   *   {GMarker} marker The marker you want to add.
   */
  this.addMarker = function (marker) {
    if (center_ === null) {
      center_ = marker.originalLatLng;
    }
    markers_.push(marker);
  };

  /**
   * Remove a marker from cluster.
   *
   * @param {GMarker} marker The marker you want to remove.
   * @return {Boolean} Whether find the marker to be removed.
   */
  this.removeMarker = function (marker) {
    for (var i = 0; i < markers_.length; ++i) {
      if (marker === markers_[i].marker) {
        if (markers_[i].isAdded) {
          map_.removeOverlay(markers_[i].marker);
        }
        markers_.splice(i, 1);
        return true;
      }
    }
    return false;
  };

  /**
   * Get current zoom level of this cluster.
   * Note: the cluster zoom level and map zoom level not always the same.
   *
   * @return {Number}
   */
  this.getCurrentZoom = function () {
    return zoom_;
  };
  
  /**
   * Redraw a cluster.
   * @private
   * @param {Boolean} isForce If redraw by force, no matter if the cluster is
   *     in viewport.
   */
  this.redraw_ = function (isForce) {
    if (!isForce && !this.isInBounds()) {
      return;
    }

    // Set cluster zoom level.
    zoom_ = map_.getZoom();
    var i = 0;
    var mz = markerClusterer.getMaxZoom_();
    if (mz === null) {
      mz = map_.getCurrentMapType().getMaximumResolution();
    }
    if (/*zoom_ >= mz || */this.getTotalMarkers() === 1) {

      // If current zoom level is beyond the max zoom level or the cluster
      // have only one marker, the marker(s) in cluster will be showed on map.
      for (i = 0; i < markers_.length; ++i) {
        if (markers_[i].isAdded) {
          if (markers_[i].marker.isHidden()) {
            markers_[i].marker.show();
          }
        } else {
          map_.addOverlay(markers_[i].marker);
          markers_[i].isAdded = true;
        }
		markers_[i].marker.setLatLng(markers_[i].originalLatLng);
		isMarkersStacked = false;
      }
      if (clusterMarker_ !== null) {
        clusterMarker_.hide();
      }      
    } else {

      if ((clusterMarker_ === null)&&(!isMarkersStacked)){
//        clusterMarker_ = new ClusterMarker_(center_, this.getTotalMarkers(), markerClusterer_.getStyles_(), markerClusterer_.getGridSize_());
    	  if (zoom_ < 8 || this.getTotalMarkers() > markerClusterer_.getMaxStacked()){
    		  
    		  clusterMarker_ = this.makeCanvasClusterMarker();
     	  }
    	  else{
    		  //reposition to stack one on top of the other center on center_
    		  this.makeStackedMarkers();
        	  isMarkersStacked = true;
    	  }
      } else if(clusterMarker_!== null) {
        if (clusterMarker_.isHidden()) {
          clusterMarker_.show();
        }
        clusterMarker_.redraw(true);
      }
    }
  };
  this.makeClusterMarker = function(){
	  
	  var count = new String(""+this.getTotalMarkers());
	  var labelDiv = document.createElement("div");
	  labelDiv.innerHTML = label_;
	  labelDiv.firstChild.childNodes[1].innerHTML = count;

	  var thisOpts = { 
      	  "icon": opts_.icon,
      	  "clickable": opts_.click,
      	  "labelText": labelDiv.innerHTML,
      	  "labelOffset": opts_.labelOffset,
      	  "labelClass":opts_.labelClass
      	};
  	  clusterMarker = new LabeledMarker(center_, thisOpts);
  	
	  var clickListener = GEvent.addListener(clusterMarker, "click", function() {
		//format all the markers into a list
	  var windowHtml = "<ul style='list-style-type: none; margin-left: 0px;padding-left: 0px;'>";
	  for (var i=0;i<markers_.length;i++){
			windowHtml +="<li>";
			windowHtml += markers_[i].marker.labelText_;
			windowHtml +="</li>";
	  }
	  windowHtml+="</ul>";
	  clusterMarker.openInfoWindowHtml(windowHtml);
	  });

	  	map_.addOverlay(clusterMarker);
		return clusterMarker;
  };
  this.makeCanvasClusterMarker = function(){
	  
	  var drawFunction = opts_.canvasDrawFunction;
	  if ( typeof drawFunction == 'function'){
		  
		  //Collect up how many sets of markers there are so we know what colors to include
		  var displayColors = [];	
		  var markers_length = markers_.length;
		  for (var i=0;i < markers_length;i++){
			  
			  var colorCounted = false;
			  for (var j = 0; j<displayColors.length; j++){
				  
				  if (markers_[i].displayColor == displayColors[j]){
					  
					  colorCounted = true;
					  break;
				  }
			  }
			  if(!colorCounted){
				  
				  displayColors.push(markers_[i].displayColor);
			  }
			  colorCounted = false;
		  }
		  //plug into the draw function
		  var thisOpts = { 
		      	  "icon": opts_.icon,
		      	  "clickable": opts_.click,
		      	  "labelText": "",
		      	  "labelOffset": opts_.labelOffset,
		      	  "labelClass":opts_.labelClass
		      	};
		  var nextPoint = map_.fromLatLngToDivPixel(center_);
		  nextPoint.x -= 20;
		  nextPoint.y -= 20;
		  var offsetPosition = map_.fromDivPixelToLatLng(nextPoint);
		  var clusterMarker = new LabeledMarker(offsetPosition, thisOpts);
		  	
		  var clickListener = GEvent.addListener(clusterMarker, "click", function() {
				//format all the markers into a list
			  //This is to be changed depending on how many markers there are and to be configurable
			  var windowHtml = "<ul style='list-style-type: none; margin-left: 0px;padding-left: 0px;'>";
			  for (var i=0;i<markers_.length;i++){
					windowHtml +="<li>";
					windowHtml += markers_[i].marker.labelText_;
					windowHtml +="</li>";
			  }
			  windowHtml+="</ul>";
			  clusterMarker.openInfoWindowHtml(windowHtml);
		 });
		  
		  var preRenderedCanvas = drawFunction(displayColors, this.getTotalMarkers());

		  map_.addOverlay(clusterMarker);
		  
		  var width = opts_.icon.iconSize.width +(displayColors.length-1)*5;
		  var height = opts_.icon.iconSize.height;
		  clusterMarker.div_.innerHTML ="<canvas style='position: absolute;left:0;top:0' width='"+width+"' height='"+height+"'></canvas>";
	  	  var canvasInner = clusterMarker.div_.firstChild;
	  	  if (canvasInner.getContext) { 
	  	    
	  		  var ctx = canvasInner.getContext("2d"); 
	          ctx.drawImage(preRenderedCanvas,0,0);
	      }

		  return clusterMarker;
	  }
	  else {
		  
		  return this.makeClusterMarker();
	  }
  };
  this.makeStackedMarkers = function(){
	  
		var nextPoint = map_.fromLatLngToDivPixel(center_);
		nextPoint = new GPoint(nextPoint.x-(10 * markers_.length), nextPoint.y-24);

		var markers_length = markers_.length;
		for (var i=0;i<markers_length;i++){
	        if (markers_[i].isAdded) {
	            if (markers_[i].marker.isHidden()) {
	              markers_[i].marker.show();
	            }
	          } else {
	            map_.addOverlay(markers_[i].marker);
	            markers_[i].isAdded = true;
	          }

			markers_[i].marker.setLatLng(map_.fromDivPixelToLatLng(nextPoint));
			nextPoint = new GPoint(nextPoint.x, nextPoint.y+20);
		}
  };
  /**
   *  Hide cluster
   */
	this.hideMarkers = function() {
		if (clusterMarker_ !== null) {
			clusterMarker_.hide();
		}

		var markers_length = markers_.length;
		for (var i = 0; i < markers_length; ++i) {
			if (markers_[i].isAdded) {
				markers_[i].marker.hide();
			}
		}
		isHidden = true;
	};
  /**
   *  Show cluster
   */
  this.showMarkers = function() {
	    if (clusterMarker_ !== null) {
	        clusterMarker_.show();
	      }
		var markers_length = markers_length;
		for (var i = 0; i < markers_.length; ++i) {
	        if (markers_[i].isAdded) {
	          markers_[i].marker.show();
	        }
	      }
	  	isHidden = false;
  };

  /**
   * Remove all the markers from this cluster.
   */
  this.clearMarkers = function () {
    if (clusterMarker_ !== null) {
      map_.removeOverlay(clusterMarker_);
      clusterMarker_ = null;
    }
	var markers_length = markers_.length;
	for (var i = 0; i < markers_length; ++i) {
      if (markers_[i].isAdded) {
        map_.removeOverlay(markers_[i].marker);
        markers_[i].isAdded = false;
      }
    }
    markers_ = [];

  };

  /**
   * Get number of markers.
   * @return {Number}
   */
  this.getTotalMarkers = function () {
    return markers_.length;
  };
}
