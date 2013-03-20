/*
 	Usage:
 		include in your xhtml page within the <ui:define name="scripts"> section
		<a4j:loadScript src="/js/mapv3/maps3.js"/>  
 	Dependency:
		include google maps prior
		<a4j:loadScript src="#{googleMapURLBean.mapUrl}&amp;hl=#{localeBean.locale.language}" />


 */
	//<![CDATA[



	// set up the namespace
	var inthincMap =  inthincMap || {};  
	inthincMap = (function() {

			var geocoder = new google.maps.Geocoder();
        	var addressCache = {};
			
			function MapState(map) {
				this.map = map;
				this.markers = new Array();
				this.overlays = new Array();
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
        		function clearZones(zones) {
        			for (var z = 0; z < zones.length; z++) {
        				zones[z].clear();
        			}
        			
        		}
        		function clearLayersForMap(map) {
      				for (var j = 0; j < mapLayers.length; j++) {
      					if (mapLayers[j].map == map) {
      						var currentMapLayers = mapLayers[j].layers;
      	  		    		for (var k = 0; k < currentMapLayers; k++) {
      	  		    			if (currentMapLayers[k].type == 'zones') {
      	  		    				clearZones(currentMapLayers[k].layer);
      	  		    				currentMapLayers[k].layer = new Array();
      	  		    			}
      	      	      		}
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
      						if(layers[i].type != 'zones') {
      							map.overlayMapTypes.push(layers[i].layer);
      						}
      						else {
								toggleZonesLayer(map, layers[i].layer, true);
      						}
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
      		    
      		    function toggleZonesLayer(map, zones, displayZones) {
  		    		for (var i = 0; i < zones.length; i++) {
  		    			if (displayZones) {
      		    			zones[i].displayOnMap(map, false, false, true);
      		    		}
  		    			else {
  		    				zones[i].clear();
  		    			}
      		    	}
      		    }

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
  								i = item.attr("index");
  								checked = item.attr("checked");
  			      				var layers = findLayersForMap(map);
  			      				if (layers[i].type == 'zones') {
									toggleZonesLayer(map, layers[i].layer, checked);
  									layers[i].selected = checked;
  			      				}
  			      				else {
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
  			      				}
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
      					layers.push({id : id, selected : selected, displayName : displayName, layer : layer, type : 'image'});
   					
      				},
          		    addZonesOverlay : function (map, options) {
      					var id = options.id;
      					var displayName = options.displayName;
      					var selected = options.selected ? options.selected : false;

      					var zones = new Array();
          		    	for (var i = 0; i < options.zones.length; i++) {
          		    		var zonePts = options.zones[i].outline;
          		    		var zone = new Zone(zonePts, {
          		    			fillColor : '#333333',
          		    			fillOpacity : 0.1,
          		    			strokeWeight : 1,
          		    			strokeOpacity :0.5,
          		    			strokeColor : '#000000',
          		    			label : options.zones[i].label
          		    		});
          		    		zones.push(zone);
          		    	}
  	      				var layers = findLayersForMap(map);
  	      				if (layers == null) {
  	      					mapLayers.push(new MapLayers(map));
  	      					layers = findLayersForMap(map);
  	      				}
      					layers.push({id : id, selected : selected, displayName : displayName, layer : zones, type : 'zones'});
          		    },
      				addControlToMap: function(map, controlId, label) {
      					var layersLabel = label ? label : "Layers";
      					
      	    			var overlayControlDiv = document.createElement('div');
      	    		    createOverlayControl(map, overlayControlDiv, controlId, layersLabel);
      					overlayControlDiv.index = 1;
  					    map.controls[google.maps.ControlPosition.RIGHT_TOP].push(overlayControlDiv);
  	
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
      				}
      			};
      			
      		})();


      		
      		return {
      			init: function(options_) {
      				var options  = options_ ? options_ : new Array();
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
							 panControl: true,
							 mapTypeControlOptions : {
								style : google.maps.MapTypeControlStyle.DROPDOWN_MENU
							 },
							 overviewMapControl: overviewMapControl,
							 overviewMapControlOptions: {
							      opened: overviewMapControl
							 }


							 
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
      			triggerResize : function (map) {
      				google.maps.events.trigger(map, 'resize');
      			},
  				lookupAddress: function(map, address, resultHandler) {
  					geocoder.geocode({'address': address }, resultHandler ? resultHandler : function (result, status) {
  			    	  if (status != google.maps.GeocoderStatus.OK) {
  			    		console.log('Geocoding failed for address: ' +  address + " status: "+ status);
  			    	  }
  			    	  else {
  			    		  map.fitBounds(result[0].geometry.viewport);
  			    		console.log("address: " + result[0].formatted_address);
  			    				
  			    	  }
  			   		});
  				},
  				showAddress : function (map, address, callback) {
  					if (address.length == 0)
  					{
  						map.setCenter(new GLatLng(39.0, -104.0));
  						map.setZoom(4);
  						return;
  					}
  					geocoder.geocode({'address': address }, function (result, status) {
    			    	  if (status != google.maps.GeocoderStatus.OK) {
							alert("The address: [" + address + "] was not found.");
    			    		console.log('Geocoding failed for address: ' +  address + " status: "+ status);
    			    	  } else {
    			    		  map.fitBounds(result[0].geometry.viewport);
    			    		  var marker = inthincMap.createMarker(map, {
    			    			  position : result[0].geometry.location
    			    		  });
    			    		  inthincMap.createInfoWindow(map,  {
    			      				content : result[0].formatted_address + "<br/>" + result[0].geometry.location,
    			      				marker: marker
   			      			  });
    			    		  if (callback) {
    			    			  callback(result[0].geometry.location, marker);
    			    		  }
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
  			    		  addressCache[lat + lng] = result[0].formatted_address;
  			    				
  			    	  }
  			   		});
  				},
  				// addressElement:
  				//			lat
  				//			lng
  				//			domElement (sets innerHTML)
  				//			altText
  				reverseGeocodeList : function(addressElements, callback) {
  					var delay = 250;
  					var reverseGeocode = function() {
  						if (addressElements.length == 0) {
  							if (callback) {
  								callback();
  							}
  							return;
  						}
  						var element = addressElements.shift();
  						if (addressCache[element.lat + element.lng]) {
  							element.domElement.innerHTML = addressCache[element.lat + element.lng];
  	    			       	reverseGeocode();
  						} else {
  		  					setTimeout(function() { 
  		  						geocoder.geocode({'location': new google.maps.LatLng(element.lat, element.lng)}, function (result, status) {
  									if (status == google.maps.GeocoderStatus.OK) {
  										element.domElement.innerHTML = result[0].formatted_address;
  				    			        addressCache[element.lat + element.lng] = result[0].formatted_address;
  									}
  									else if (status == google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {
  										addressElements.push(element);
  									}
  									else {
  										element.domElement.innerHTML = element.altText;
  									}
  									//console.log("address: " + element.domElement.innerHTML + " altText: " + element.altText);
  				    			    reverseGeocode();
  		  						})
  		  					}, delay);
  						}
  					};
  					reverseGeocode();
  	        	},

      			addWMSLayer: function(map, options) {
      				wmsOverlays.addOverlay(map, options);

      			},
      			addOverlaysControl: function(map, controlId, label) {
      				wmsOverlays.addControlToMap(map, controlId, label);
      			},

      			addZonesLayer: function(map, options) {
      				// if we switch to tiles this can be used
      				// wmsOverlays.addOverlay(map, options);
      				wmsOverlays.addZonesOverlay(map, options);
      				

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
							optimized : false,
						 	map: map,
						 	icon : iconImage
					});
					mapState.markers.push(marker);
      		    	return marker;
      		    },
      		    clearMarkers : function(map) {
          			var mapState = findMapStateForMap(map);
          			if (mapState) {
	      		    	while (mapState.markers.length > 0) {
	      		    		marker = mapState.markers.pop();
	      		    		marker.setMap(null);
	      		    	}
          			}
      		    },
      			createInfoWindow : function (map, options) {
      				var content = options.content ? options.content : "";
      				var marker = options.marker ? options.marker : null;
      				var addListener = options.addListener ? options.addListener : true;
      				
    				var infowindow = new google.maps.InfoWindow({
    					content: content
    			 	});
    				if (marker) {
    					this.showInfoWindow(map, infowindow, marker);
    					if (addListener) {
    						var that = this;
							google.maps.event.addListener(marker, 'click', function() {
		    					that.showInfoWindow(map, infowindow, marker);
							});
    					};
    				}
    				else if (options.position) {
    					infowindow.setPosition(options.position);
    					this.showInfoWindow(map, infowindow);
    				}

    				return infowindow;
      		    },
      			createInfoWindowFromDiv : function (map, divID, location, options_) {
      				var options  = options_ ? options_ : new Array();
      				var node = document.getElementById(divID).cloneNode(true);
      		    	node.style.display = 'block';
      				var marker = this.createMarker(map, { 
      					  position: location,
      					  iconImage : options.iconImage
      				});
    				var infowindow = new google.maps.InfoWindow({
    					content: node
    			 	});
    				if (!options.hideInfoWindow) {
   						this.showInfoWindow(map, infowindow, marker);
    				}
    				var that = this;
					google.maps.event.addListener(marker, 'click', function() {
    					that.showInfoWindow(map, infowindow, marker);
					});
    				return infowindow;
      		    },
      			infoWindowAtMarker : function (map, divID, marker) {
      				var node = document.getElementById(divID).cloneNode(true);
      		    	node.style.display = 'block';
    				var infowindow = new google.maps.InfoWindow({
    					content: node
    			 	});
    				this.showInfoWindow(map, infowindow, marker);
    				return infowindow;
      		    },
      			infoWindowAtPosition : function (map, divID, position) {
      				var node = document.getElementById(divID).cloneNode(true);
      		    	node.style.display = 'block';
    				var infowindow = new google.maps.InfoWindow({
    					content: node
    			 	});
					infowindow.setPosition(position);
					this.showInfoWindow(map, infowindow);
    				return infowindow;
      		    },
          		showInfoWindow : function (map, infowindow, marker) {
          			var mapState = findMapStateForMap(map);
    				if (mapState && mapState.openinfowindow != null)
    					mapState.openinfowindow.close();
    				infowindow.open(map, marker ? marker : null);
    				mapState.openinfowindow = infowindow;
          		},
      		    addPolyline : function (map, latLngArray,  options_) {
      				var options  = options_ ? options_ : new Array();
    				var polyline = new google.maps.Polyline({
    					path: latLngArray,
    					strokeColor: options.strokeColor ? options.strokeColor : '#ffffff',
    					strokeWeight: options.strokeWeight ? options.strokeWeight : 1,
    					strokeOpacity : options.strokeOpacity ? options.strokeOpacity : 1,
    					map : options.show ? map : null
    				});
          			var mapState = findMapStateForMap(map);
          			mapState.overlays.push(polyline);
    				return polyline;

      		    },
      		    setReadOnly : function(map, readOnly) {
					map.setOptions({
						disableDefaultUI : readOnly,
						draggable : !readOnly,
						scrollwheel : !readOnly,
						disableDoubleClickZoom : readOnly
					});
      		    },
      		    clearOverlays : function(map) {
          			var mapState = findMapStateForMap(map);
      		    	while (mapState.overlays.length > 0) {
      		    		overlay = mapState.overlays.pop();
      		    		overlay.setMap(null);
      		    	}
      		    },
      		    clearInfoWindow : function (map) {
          			var mapState = findMapStateForMap(map);
          		    if (mapState && mapState.openinfowindow != null) {
    					mapState.openinfowindow.close();
    					mapState.openinfowindow = null;
          		    }
      		    },
      		    clear : function(map) {
      		    	this.clearMarkers(map);
      		    	this.clearOverlays(map);
      		    	this.clearInfoWindow(map);
      		    },
      		    getMarkers : function(map) {
          			var mapState = findMapStateForMap(map);
          			if (mapState)
          				return mapState.markers;
          			
          			return new Array();
      		    }
      		};    	
      	})();
  	//]]>
