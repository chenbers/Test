/*
 	Usage:
 		include in your xhtml page within the <ui:define name="scripts"> section
		<a4j:loadScript src="/js/map/maps.js"/>  
 	Dependency:
		include google maps prior
		<a4j:loadScript src="#{googleMapURLBean.mapUrl}&amp;hl=#{localeBean.locale.language}" />


 */
	//<![CDATA[

    function WMSTileLayer(wmsURL, wmsLayer, map, minZoom, maxZoom, opacity, usePng) {
    	if (!(this instanceof arguments.callee)) {
    	    return new arguments.callee(wmsURL, wmsLayer, map, minZoom, maxZoom, opacity, usePng);
    	}
    	this.wmsURL = wmsURL;
    	this.wmsLayer = wmsLayer;
    	this.map = map;
    	this.minZoom = minZoom;
    	this.maxZoom = maxZoom;
    	this.opacity = opacity;
    	this.usePng = usePng;
    	    	    	
    }
    WMSTileLayer.prototype = new GTileLayer(null);
    WMSTileLayer.prototype.isPng = function(){return this.usePng;};
    WMSTileLayer.prototype.getOpacity = function(){return this.opacity;};

    WMSTileLayer.prototype.getTileUrl = function(wmsTile, wmsZoom) {
    	var projection = this.map.getCurrentMapType().getProjection();
    	var zpow = Math.pow(2, wmsZoom);
    	var ul = new GPoint(wmsTile.x * 256.0 / zpow, (wmsTile.y + 1) * 256.0 / zpow);
    	var lr = new GPoint((wmsTile.x + 1) * 256.0 / zpow, (wmsTile.y) * 256.0 / zpow);
    	var ulw = projection.fromPixelToLatLng(ul);
    	var lrw = projection.fromPixelToLatLng(lr);
    	var bbox = ulw.lng() + "," + ulw.lat() + "," + lrw.lng() + "," + lrw.lat();	
    	var wmsLayerUrl = this.wmsURL + "VERSION=1.1.1&REQUEST=GetMap&LAYERS=" + this.wmsLayer + "&STYLES=default&SRS=EPSG:4326&BBOX=" + bbox + "&WIDTH=256&HEIGHT=256&FORMAT=image/png&TRANSPARENT=TRUE";
    	return wmsLayerUrl;
    };


	var portalmap = (function() {
    		var map;
    		var overlays = new Array();
    		var overlaysState = new Array();
    		var overlayID = new Array();
    		var saveLatLng;
    		var saveZoom;
    		var overlayControl;
    		saveLatLng = new GLatLng(38.5482, -95.8008);
    		saveZoom = 10;
	
      		var wmsOverlays = (function(){
      			var overlayControlDiv = document.createElement('DIV');
      			overlayControlDiv.setAttribute("id", "overlay-controls");
      			var overlaySelect = document.createElement('SELECT');
      			overlaySelect.setAttribute("multiple", "true");
      			overlaySelect.setAttribute("id", "overlay-select");
      			overlayControlDiv.appendChild(overlaySelect);
      			
      			function isTemplate(wmsURL) {
      		    	return((wmsURL.indexOf('{X}') >= 0  || wmsURL.indexOf('{x}') >= 0) &&
     					   (wmsURL.indexOf('{Y}') >= 0  || wmsURL.indexOf('{y}') >= 0) &&
     					   (wmsURL.indexOf('{Z}') >= 0  || wmsURL.indexOf('{z}') >= 0));
      			}
      			
      			function OverlayControl(){}
      			OverlayControl.prototype = new GControl();
      			OverlayControl.prototype.initialize = function(map) {
      				map.getContainer().appendChild(overlayControlDiv);
      				return overlayControlDiv;
      			};
      			OverlayControl.prototype.getDefaultPosition = function() {
      			  return new GControlPosition(G_ANCHOR_TOP_RIGHT, new GSize(7, 30));
      			};
      			
      			return {
      				addOverlay: function(wmsURL, displayName, wmsLayer, isSelectedDefault, minZ, maxZ, opacity, isUsePng, id) {
      					var selected = isSelectedDefault ? isSelectedDefault : false;
      					var minZoom = minZ ? minZ : 1;
      					var maxZoom = maxZ ? maxZ : 30;
      					var opacityVal = opacity ? opacity : 1.0;
      					var usePng = isUsePng ? isUsePng : true;
      					var layer;
      					if (isTemplate(wmsURL)) {
      						layer = new GTileLayer(null, minZoom, maxZoom, {
      							tileUrlTemplate:wmsURL,
      					      	isPng:usePng,
      					      	opacity:opacityVal }); 
      					}
      					else {
      						layer = new WMSTileLayer(wmsURL, wmsLayer, map, minZoom, maxZoom, opacityVal, usePng); 
      					}
      					var overlay = new GTileLayerOverlay(layer);
      					
      					var overlaySelectOption = document.createElement('OPTION');
      					overlaySelectOption.setAttribute('id', wmsLayer + "-option");
      					if (selected)
      						overlaySelectOption.setAttribute('selected', 'selected');
      					overlaySelectOption.innerHTML = displayName;
      					overlaySelect.appendChild(overlaySelectOption);
      					if(selected) map.addOverlay(overlay);
      					overlays.push(overlay);
      					overlaysState.push(selected);
      					overlayID.push(id);
   					
      				},
      				addControlToMap: function() {
      					overlayControl = new OverlayControl();
      					map.addControl(overlayControl);
      					jQuery("#overlay-select").dropdownchecklist({ icon: {placement: 'right', toOpen: 'ui-icon-triangle-1-s', toClose: 'ui-icon-triangle-1-n'}, 
      							width: 100, maxDropHeight: 150, 
      							explicitClose: 'Close',
      							onItemClick: function(item) {
      								i = item.attr("index");
      								checked = item.attr("checked");
      								if (checked) {
      									map.addOverlay(overlays[i]);
      									overlaysState[i] = true;
      								} else {
      									map.removeOverlay(overlays[i]);
      									overlaysState[i] = false;
      								};
      								
      								GEvent.trigger(map, "layerselect", overlayID[i], checked);

      							},
      		      				textFormatFunction: function(options) {
    								return "<b>Layers</b>";
      		      				}

      					});
      				},
      				clearOverlayData: function() {
      					jQuery("#overlay-select").empty();
      					jQuery("#overlay-select").dropdownchecklist("destroy");
      					map.removeControl(overlayControl);
      					overlayControl = null;
      					overlays = new Array();
      				},
      				restoreOverlayState: function() {
      					for (i = 0; i < overlaysState.length; i++)
      						if (overlaysState[i])
									map.addOverlay(overlays[i]);
      							
      				},
      				getOverlayState : function() {
      					return overlaysState;
      				}

      			};
      			
      		})();

      		return {
      			init: function(showOverviewControl, canvas, opts) {
					var overviewControl = showOverviewControl ? showOverviewControl : false;
					var canvasID = canvas ? canvas : "map-canvas";
					
					if (opts)
						map = new GMap2(document.getElementById(canvasID), {googleBarOptions: opts});
					else map = new GMap2(document.getElementById(canvasID));
      	            map.addControl(new GLargeMapControl());  	            
    		        map.addControl(new GMapTypeControl(true));
    		        if (overviewControl)
    		        	map.addControl(new GOverviewMapControl()); 

      	    		map.setCenter(saveLatLng, saveZoom);		
    		        map.enableScrollWheelZoom();
    		        GEvent.addListener(map, "dragend", function() {
                  		saveLatLng = map.getCenter();
    				});
    		        GEvent.addListener(map, "zoomend", function(oldlevel, newlevel) {
                  		saveZoom = newlevel;
    				});
      			},
      			getMap: function() {
      				return map;
      			},
      			zoom: function(zoomTo) {
      				map.setZoom(zoomTo);
      			}, 
      			center: function(latLng) {
      				map.setCenter(latLng);
      			}, 
      			centerInBounds: function() { 
      				bounds = new GLatLngBounds();
      		  		map.setZoom(map.getBoundsZoomLevel(bounds)-1);
      			    map.setCenter(bounds.getCenter());
      			},
      			resize: function() {
      				map.checkResize();
      			}, 
      			reinit: function() {
      				map.clearOverlays();
		        	wmsOverlays.clearOverlayData();
      			},
      			restoreLayersState: function() {
		        	wmsOverlays.restoreOverlayState();
      			},
      			getLayersState: function() {
		        	return wmsOverlays.getOverlayState();
      			},
      			addWMSLayer: function(id, url, displayName, layerName, minZoom, maxZoom, opacityVal, usePng, selected) {
					var isSelected = selected ? selected : false;
      				wmsOverlays.addOverlay(url, displayName, layerName, selected, minZoom, maxZoom, opacityVal, usePng, id);

      			},
      			addOverlaysControl: function() {
      				wmsOverlays.addControlToMap();
      			},
  				lookupAddress: function(address) {
  					geocoder = new GClientGeocoder();
	  		   	    if (geocoder) {
	  		   	        geocoder.getLatLng(
	  		   	          address,
	  		   	          function(point) {
	  		   	        	  if (!point) {
	  		   	        		  alert(address + ' not found');
	  		   	        	  } else {
	  							 saveLatLng = point;
	  	  						 map.setCenter(saveLatLng, saveZoom);
	  		    	         }
	  		   	          }
	  		   	        );
	  	   		    }
  				}
      		};    	
      	})();
  	    GEvent.addDomListener(window, 'unload', GUnload);
		//]]>
