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
    		var map = null;
    		var saveLatLng;
    		var saveZoom;
    		var layers = new Array();
    		var markers = new Array();
    		var openinfowindow = null;
    		var mapLoaded = false;
    		
    		var geocoder = new google.maps.Geocoder();
    		
    		
      		var wmsOverlays = (function(){
      			
      			function isTemplate(wmsURL) {
      		    	return((wmsURL.indexOf('{X}') >= 0  || wmsURL.indexOf('{x}') >= 0) &&
     					   (wmsURL.indexOf('{Y}') >= 0  || wmsURL.indexOf('{y}') >= 0) &&
     					   (wmsURL.indexOf('{Z}') >= 0  || wmsURL.indexOf('{z}') >= 0));
      			}
      			
      			function createOverlayControl(overlayControlDiv, label) {
          			overlayControlDiv.setAttribute("id", "overlay-controls");
          			var overlaySelect = document.createElement('SELECT');
          			overlaySelect.setAttribute("multiple", "true");
          			overlaySelect.setAttribute("id", "overlay-select");
          			overlayControlDiv.appendChild(overlaySelect);
  					for (i = 0; i < layers.length; i++) {
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
      			function getWMSTileUrl(url, layerName, tile, zoom) {
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

      		    function initDropdownChecklist(label) {
			    	jQuery("#overlay-select").dropdownchecklist({ icon: {placement: 'right', toOpen: 'ui-icon-triangle-1-s', toClose: 'ui-icon-triangle-1-n'}, 
  							width: 100, maxDropHeight: 150, 
  							explicitClose: 'Close',
  							onItemClick: function(item) {
  								i = item.attr("index");
  								checked = item.attr("checked");
								if (checked) {
									console.log("push layer: " + layers[i].layer.opacity);
  									map.overlayMapTypes.push(layers[i].layer);
  									layers[i].selected = true;
  								} else {
									var removeIndex = -1;
									for (cnt = 0; cnt < map.overlayMapTypes.getLength(); cnt++) {
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
      				addOverlay: function(options) {
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
  						    	return isTemplateURL ? getTileUrl(baseURL, tile, zoom) : getWMSTileUrl(baseURL, baseLayer, tile, zoom);
  						    },
  						    opacity:opacityVal,
  						    isPng: usePng,
  						    name: id,
  						    tileSize: new google.maps.Size(256, 256)
  						});
      					layers.push({id : id, selected : selected, displayName : displayName, layer : layer});
   					
      				},
      				addControlToMap: function(label) {
      					var layersLabel = label ? label : "Layers";
      					
      	    			var overlayControlDiv = document.createElement('div');
      	    		    createOverlayControl(overlayControlDiv, layersLabel);
      					overlayControlDiv.index = 1;
  					    map.controls[google.maps.ControlPosition.RIGHT_TOP].push(overlayControlDiv);
  					    
  					    google.maps.event.addListener(map, 'idle', function(){
  					    	if(!mapLoaded){
  					    		mapLoaded = true;
  					    		initDropdownChecklist(layersLabel);
  					    	}
  					    });
  					    
      				},
      				clear: function() {
      					jQuery("#overlay-select").empty();
      					jQuery("#overlay-select").dropdownchecklist("destroy");
      					map.overlayMapTypes.clear();
      					map.controls[google.maps.ControlPosition.RIGHT_TOP].clear();
      					layers = new Array();
      				},

      			};
      			
      		})();


      		function showInfoWindow(infowindow, marker) {
				if (openinfowindow)
					openinfowindow.close();
				infowindow.open(map, marker ? marker : null);
				openinfowindow = infowindow;
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
					map = new google.maps.Map(document.getElementById(canvasID), mapOptions); 


					google.maps.event.addListener(map, 'center_changed', function() {
                  		saveLatLng = map.getCenter();
					});

					google.maps.event.addListener(map, "zoom_changed", function() {
                  		saveZoom = map.getZoom();
    				});
      			},
      			getMap: function() {
      				return map;
      			},
      			zoom: function(zoomTo) {
      				map.setZoom(zoomTo);
      			}, 
      			center: function(lat, lng) {
      				map.setCenter(
      					new google.maps.LatLng(lat, lng)
      				);
      			},
      			centerAndZoom : function(bounds) {
      				map.panToBounds(bounds);
      			},
  				lookupAddress: function(address, resultHandler) {
  					geocoder.geocode({'address': address}, resultHandler ? resultHandler : function (result, status) {
  			    	  if (status != google.maps.GeocoderStatus.OK) {
  			    		console.log('Geocoding failed for address: ' +  address + " status: "+ status);
  			    	  } else {
  			    		  map.fitBounds(result[0].geometry.viewport);
  			    	  }
  			   		});
  				},
  				reverseGeocode: function(lat, lng, resultHandler) {
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
      			addWMSLayer: function(options) {
      				wmsOverlays.addOverlay(options);

      			},
      			addOverlaysControl: function(label) {
      				wmsOverlays.addControlToMap(label);
      			},
      			reinit: function() {
		        	wmsOverlays.clear();
      			},
      			createMarker : function (options ) 
      		    {
      				var position = options.position;		
      				
      				// optional
      				var iconImage = options.iconImage ? options.iconImage : null;
      				
      		    	var marker;
					marker = new google.maps.Marker({ 
							position : position, 
						 	map: map,
						 	icon : iconImage,
						 	
					});
					markers.push(marker);
      		    	return marker;
      		    },
      		    clearMarkers : function() {
      		    	while (markers.length > 0) {
      		    		marker = markers.pop();
      		    		marker.setMap(null);
      		    	}
      		    },
      			createInfoWindow : function (options) 
      		    {
      				var content = options.content ? options.content : "";
      				var marker = options.marker ? options.marker : null;
      				var addListener = options.addListener ? options.addListener : true;
      				
    				var infowindow = new google.maps.InfoWindow({
    					content: content
    			 	});
    				if (marker) {
    					showInfoWindow(infowindow, marker);
    					if (addListener) {
							google.maps.event.addListener(marker, 'click', function() {
		    					showInfoWindow(infowindow, marker);
							});
    					};
    				}
    				else if (options.position) {
    					infowindow.setPosition(options.position);
    					showInfoWindow(infowindow);
    				}

    				return infowindow;
      		    }
      		};    	
      	})();
  	//]]>
