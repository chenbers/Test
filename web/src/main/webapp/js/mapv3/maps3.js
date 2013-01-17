/*
 	Usage:
 		include in your xhtml page within the <ui:define name="scripts"> section
		<a4j:loadScript src="/js/map/maps.js"/>  
 	Dependency:
		include google maps prior
		<a4j:loadScript src="#{googleMapURLBean.mapUrl}&amp;hl=#{localeBean.locale.language}" />


 */
	//<![CDATA[



	// set up the namespace
	var inthincMap =  inthincMap || {};  
	inthincMap = (function() {

			var geocoder = new google.maps.Geocoder();
			
			function MapState(map) {
				this.map = map;
				this.markers = new Array();
	    		this.openinfowindow = null;
			}
			var mapStates = new Array();
			
    		function findMapStateForMap(map) {
  				for (var j = 0; j < mapStates.length; j++) {
  					if (mapStates[j].map == map) {
  						return mapStates[j];
  					}
  				}
  				return null;
    		}
    		function clearMarkersForMap(map) {
  				for (var j = 0; j < mapStates.length; j++) {
  					if (mapStates[j].map == map) {
  						mapStates[j].markers = new Array();
  					}
  				}
    		}
    		
    		
    		
      		var wmsOverlays = (function(){
        		function MapLayers(map) {
        			this.map = map;
        			this.layers = new Array();
        			this.initialized = false;
        		}
        		var mapLayers = new Array();
        		
        		function findLayersForMap(map) {
      				for (var j = 0; j < mapLayers.length; j++) {
      					if (mapLayers[j].map == map) {
      						return mapLayers[j].layers;
      					}
      				}
      				return null;
        		}
        		function isInitialized(map) {
      				for (var j = 0; j < mapLayers.length; j++) {
      					if (mapLayers[j].map == map) {
      						return mapLayers[j].initialized;
      					}
      				}
      				return false;
        		}
        		function setInitialized(map, initialized) {
      				for (var j = 0; j < mapLayers.length; j++) {
      					if (mapLayers[j].map == map) {
      						mapLayers[j].initialized = initialized;
      						break;
      					}
      				}
        		}
        		function clearLayersForMap(map) {
      				for (var j = 0; j < mapLayers.length; j++) {
      					if (mapLayers[j].map == map) {
      						mapLayers[j].layers = new Array();
      						mapLayers[j].initialized = false;
      					}
      				}
        		}
      			
      			function isTemplate(wmsURL) {
      		    	return((wmsURL.indexOf('{X}') >= 0  || wmsURL.indexOf('{x}') >= 0) &&
     					   (wmsURL.indexOf('{Y}') >= 0  || wmsURL.indexOf('{y}') >= 0) &&
     					   (wmsURL.indexOf('{Z}') >= 0  || wmsURL.indexOf('{z}') >= 0));
      			}
      			
      			function createOverlayControl(map, overlayControlDiv, id, label) {
      				var layers = findLayersForMap(map);
      				if (layers == null)
      					return;

      				overlayControlDiv.setAttribute("id", id+'_div');
          			var overlaySelect = document.createElement('SELECT');
          			overlaySelect.setAttribute("multiple", "true");
          			overlaySelect.setAttribute("id", id);
          			overlayControlDiv.appendChild(overlaySelect);
  					for (var i = 0; i < layers.length; i++) {
      					var overlaySelectOption = document.createElement('OPTION');
      					overlaySelectOption.setAttribute('id', layers[i].id);
      					if (layers[i].selected) {
      						overlaySelectOption.setAttribute('selected', 'selected');
								map.overlayMapTypes.push(layers[i].layer);
      					}
      					overlaySelectOption.innerHTML = layers[i].displayName;
      					overlaySelect.appendChild(overlaySelectOption);
  					}
      			}
      			
      			function getTileUrl(url, tile, zoom) {
      				return url.replace("{Z}",zoom).replace("{z}", zoom)
      					.replace("{X}",tile.x).replace("{x}", tile.x)
      					.replace("{Y}",tile.y).replace("{y}", tile.y); 
      				
      			}
      			function getWMSTileUrl(url, layerName, tile, zoom, map) {
      		    	var projection = map.getProjection();
      		    	var zpow = Math.pow(2, zoom);
      		    	var ul = new google.maps.Point(tile.x * 256.0 / zpow, (tile.y + 1) * 256.0 / zpow);
      		    	var lr = new google.maps.Point((tile.x + 1) * 256.0 / zpow, (tile.y) * 256.0 / zpow);
      		    	var ulw = projection.fromPointToLatLng(ul);
      		    	var lrw = projection.fromPointToLatLng(lr);
      		    	var bbox = ulw.lng() + "," + ulw.lat() + "," + lrw.lng() + "," + lrw.lat();	
      		    	var wmsLayerUrl = url + "VERSION=1.1.1&REQUEST=GetMap&LAYERS=" + layerName + "&STYLES=default&SRS=EPSG:4326&BBOX=" + bbox + "&WIDTH=256&HEIGHT=256&FORMAT=image/png&TRANSPARENT=TRUE";
      		    	return wmsLayerUrl;
      		    };

      		    function initDropdownChecklist(map, controlId, label) {
			    	jQuery('#' + controlId).dropdownchecklist({ 
			    			icon: {
			    				placement: 'right', 
			    				toOpen: 'ui-icon-triangle-1-s', 
			    				toClose: 'ui-icon-triangle-1-n'
			    			}, 
  							width: 100, 
  							maxDropHeight: 150, 
  							explicitClose: 'Close',
  							onItemClick: function(item) {
  			      				var layers = findLayersForMap(map);
  								i = item.attr("index");
  								checked = item.attr("checked");
								if (checked) {
									map.overlayMapTypes.push(layers[i].layer);
  									layers[i].selected = true;
  								} else {
									var removeIndex = -1;
									for (var cnt = 0; cnt < map.overlayMapTypes.getLength(); cnt++) {
										if (map.overlayMapTypes.getAt(cnt).name === layers[i].id) {
											removeIndex = cnt;
										}
									}
									if (removeIndex > -1) {
										map.overlayMapTypes.removeAt(removeIndex);
      									layers[i].selected = false;
									}
									else {
										console.log("ERROR can't find layer");
									}
  								};
  								
 								google.maps.event.trigger(map, "layerselect", layers[i].id, checked);
  							},
  		      				textFormatFunction: function(options) {
								return "<b>"+ label + "</b>";
  		      				}

			    	});
				};
      			
      			return {
      				addOverlay: function(map, options) {
      					var id = options.id;
      					var baseURL = options.url;
      					var displayName = options.displayName;
      					var baseLayer = options.layerName;
      					var minZoom = options.minZoom ? options.minZoom : 1;
      					var maxZoom = options.maxZoom ? options.maxZoom : 30;
      					var opacityVal = options.opacity ? options.opacity : 1.0;
      					var usePng = options.usePng ? options.usePng : true;
      					var selected = options.selected ? options.selected : false;
      					var isTemplateURL = isTemplate(baseURL);

      					
  						var layer = new google.maps.ImageMapType({
  						    getTileUrl: function(tile, zoom) {
  						    	return isTemplateURL ? getTileUrl(baseURL, tile, zoom) : getWMSTileUrl(baseURL, baseLayer, tile, zoom, map);
  						    },
  						    opacity:opacityVal,
  						    isPng: usePng,
  						    name: id,
  						    tileSize: new google.maps.Size(256, 256)
  						});
  	      				var layers = findLayersForMap(map);
  	      				if (layers == null) {
  	      					mapLayers.push(new MapLayers(map));
  	      					layers = findLayersForMap(map);
  	      				}
      					layers.push({id : id, selected : selected, displayName : displayName, layer : layer});
   					
      				},
      				addControlToMap: function(map, controlId, label) {
      					var layersLabel = label ? label : "Layers";
      					
      	    			var overlayControlDiv = document.createElement('div');
      	    		    createOverlayControl(map, overlayControlDiv, controlId, layersLabel);
      					overlayControlDiv.index = 1;
  					    map.controls[google.maps.ControlPosition.RIGHT_TOP].push(overlayControlDiv);
  	
//  					    initDropdownChecklist(map, controlId, layersLabel);
  					    google.maps.event.addListener(map, 'idle', function(){
  					    	if (!isInitialized(map)) {
  		  					    setTimeout(function() {initDropdownChecklist(map, controlId, layersLabel);}, 1000);
  		  					    setInitialized(map, true);
  					    	}
  					    });
  					    
      				},
      				clear: function(map, controlId) {
      					jQuery("#"+controlId).empty();
      					jQuery("#"+controlId).dropdownchecklist("destroy");
      					map.overlayMapTypes.clear();
      					map.controls[google.maps.ControlPosition.RIGHT_TOP].clear();
      					clearLayersForMap(map);
      				},

      			};
      			
      		})();


      		function showInfoWindow(map, infowindow, marker) {
      			var mapState = findMapStateForMap(map);
				if (mapState && mapState.openinfowindow)
					mapState.openinfowindow.close();
				infowindow.open(map, marker ? marker : null);
				mapState.openinfowindow = infowindow;
      		}
      		
      		return {
      			/*
      			 * 
      			 * NOTES:
      			 * 	Old version had a googleBarOptions param.  I'm removing for now.  It was used in SBS request only.
      			 */
      			
//      			init: function(showOverviewControl, canvas, opts) {
      			init: function(options) {
					var zoom = options.zoom ? options.zoom : 10;
					var centerLat = options.center ? (options.center.lat ? options.center.lat : 0.0) : 38.5482;
					var centerLng = options.center ? (options.center.lng ? options.center.lng : 0.0) : -95.8008;
					var mapTypeId = options.mapTypeId ? options.mapTypeId : google.maps.MapTypeId.ROADMAP;
					var canvasID = options.canvasID ? options.canvasID : "map-canvas";
					var overviewMapControl = options.overviewMapControl ? options.overviewMapControl : false;
					
					 var mapOptions = {
							 zoom: zoom,
							 center: new google.maps.LatLng(centerLat, centerLng),
							 mapTypeId: mapTypeId,
							 mapTypeControl: true,
							 mapTypeControlOptions : {
								style : google.maps.MapTypeControlStyle.DROPDOWN_MENU, 
							 },
							 overviewMapControl: overviewMapControl,
							 overviewMapControlOptions: {
							      opened: overviewMapControl,
							 },


							 
					 };
					var map = new google.maps.Map(document.getElementById(canvasID), mapOptions);
					mapStates.push(new MapState(map));
					
					return map;
      			},
      			zoom: function(map, zoomTo) {
      				map.setZoom(zoomTo);
      			}, 
      			center: function(map, lat, lng) {
      				map.setCenter(
      					new google.maps.LatLng(lat, lng)
      				);
      			},
      			centerAndZoom : function(map, bounds) {
      				map.fitBounds(bounds);
      			},
  				lookupAddress: function(map, address, resultHandler) {
  					geocoder.geocode({'address': address}, resultHandler ? resultHandler : function (result, status) {
  			    	  if (status != google.maps.GeocoderStatus.OK) {
  			    		console.log('Geocoding failed for address: ' +  address + " status: "+ status);
  			    	  } else {
  			    		  map.fitBounds(result[0].geometry.viewport);
  			    	  }
  			   		});
  				},
  				reverseGeocode: function(map, lat, lng, resultHandler) {
  					geocoder.geocode({'location': new google.maps.LatLng(lat, lng)}, resultHandler ? resultHandler : function (result, status) {
  			    	  if (status != google.maps.GeocoderStatus.OK) {
  			    		console.log('Reverse Geocoding failed for lat, lng: ' +  lat + ', ' + lng + " status: "+ status);
  			    	  }
  			    	  else {
  			    		  map.fitBounds(result[0].geometry.viewport);
  			    		console.log("address: " + result[0].formatted_address);
  			    				
  			    	  }
  			   		});
  				},
      			addWMSLayer: function(map, options) {
      				wmsOverlays.addOverlay(map, options);

      			},
      			addOverlaysControl: function(map, controlId, label) {
      				wmsOverlays.addControlToMap(map, controlId, label);
      			},
      			reinit: function(map, controlId) {
		        	wmsOverlays.clear(map, controlId);
      			},
      			createMarker : function (map, options ) 
      		    {
          			var mapState = findMapStateForMap(map);
      				var position = options.position;		
      				// optional
      				var iconImage = options.iconImage ? options.iconImage : null;
      				
      		    	var marker;
					marker = new google.maps.Marker({ 
							position : position, 
						 	map: map,
						 	icon : iconImage,
						 	
					});
					mapState.markers.push(marker);
      		    	return marker;
      		    },
      		    clearMarkers : function(map) {
          			var mapState = findMapStateForMap(map);
      		    	while (mapState.markers.length > 0) {
      		    		marker = mapState.markers.pop();
      		    		marker.setMap(null);
      		    	}
      		    },
      			createInfoWindow : function (map, options) 
      		    {
      				var content = options.content ? options.content : "";
      				var marker = options.marker ? options.marker : null;
      				var addListener = options.addListener ? options.addListener : true;
      				
    				var infowindow = new google.maps.InfoWindow({
    					content: content
    			 	});
    				if (marker) {
    					showInfoWindow(map, infowindow, marker);
    					if (addListener) {
							google.maps.event.addListener(marker, 'click', function() {
		    					showInfoWindow(map, infowindow, marker);
							});
    					};
    				}
    				else if (options.position) {
    					infowindow.setPosition(options.position);
    					showInfoWindow(map, infowindow);
    				}

    				return infowindow;
      		    }
      		};    	
      	})();
  	//]]>
