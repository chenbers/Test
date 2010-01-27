	function get_place(latlng) {
		var zm = map.getZoom(); 
		if(zm < 8) {
			zm=8;
			}

		var proj = map.getCurrentMapType().getProjection();
		var pt = proj.fromLatLngToPixel(latlng,zm);

		var tile_coord_x = Math.floor(pt.x/256);
		var tile_coord_y = Math.floor(pt.y/256);
		var a = new GPoint(tile_coord_x,tile_coord_y);
		var lULP = new GPoint(a.x*256,(a.y+1)*256);
		var lLRP = new GPoint((a.x+1)*256,a.y*256);

		var lUL = G_NORMAL_MAP.getProjection().fromPixelToLatLng(lULP,zm,false);
		var lLR = G_NORMAL_MAP.getProjection().fromPixelToLatLng(lLRP,zm,false);

		var lBbox=lUL.x+","+lUL.y+","+lLR.x+","+lLR.y;

		var local_x = pt.x % 256;
		var local_y = pt.y % 256;

		var coords = '&x='+local_x+'&y='+local_y; 

		//alert(lBbox);
		
	    var unencoded =  "http://"+host+":"+port+url+"&service=WMS&version=1.1.0&request=GetFeatureInfo&layers=";
	    unencoded +=wmsLayer;
	    unencoded +="&query_layers=";
	    unencoded +=wmsLayer;
	    unencoded +="&styles="

		unencoded += '&bbox=' + lBbox;
		unencoded += '&crs=EPSG%3A4326';
		unencoded += coords;
		unencoded += '&feature_count=10&height=256&width=512&';
		

		var urlstr = proxy + unencoded; 
				
		GDownloadUrl(urlstr,function(doc, responseCode) {
		  // To ensure against HTTP errors that result in null or bad data,
		  // always check status code is equal to 200 before processing the data
	  	  if(responseCode == 200) {
	  		  
	  		  if (doc.indexOf("no results")==-1){
	  			  
				var info = '<div style="width:350px;"><small>';
				info += '<pre>';
				info += doc;
				info += '</pre></small></div>';
				map.openInfoWindow(latlng, info);
	  		  }
			}
			else {
				
				var info = '<div style="width:350px;"><small>';
				info += 'Error: ' +responseCode;
				info += urlstr + '<br />';
				info += '</small></div>';
				map.openInfoWindow(latlng, info);
			}
			
			  });
			  
			 
	}
	function addListeners(){
        // add listener for queryable WMS Layer
        GEvent.addListener(map, 'click', function(overlay,latlng) {

        	if (!overlay && respondToClick){
			
        		get_place(latlng);
        	}
        });
        GEvent.addListener(map, 'maptypechanged', function(){
        	
        	respondToClick = false;
        	for(var i = 0;i < queryableLayers.length; i++){
        		
        		if(map.getCurrentMapType() == queryableLayers[i]){
        			respondToClick = true;
        			return;
        		}
        	}
        });
	}

	function isQueryable(){
		
		return 	wmsLayer && (wmsLayer.length != 0);
	}
	