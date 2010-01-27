	        // salt lake city
			//map.setCenter(new GLatLng(40.7101, -111.993), 13);
			// Buzwagi
			//map.setCenter(new GLatLng(-3.842, 32.617), 13);
				// WMS c
				var streetsHyb = new GTileLayer(new GCopyrightCollection(''),1,17);
					streetsHyb.myLayers="rompoly";
					streetsHyb.myFormat="image/png";
					streetsHyb.myBaseURL="http://" + host +":8086/cgi-bin/mapserv?map=/var/www/mapserver/romania/romania.map&";
					//streetsHyb.myBaseURL="http://192.168.1.12:8086/cgi-bin/mapserv?map=/var/www/mapserver/saltlake/postgres.map&";
					streetsHyb.getTileUrl=CustomGetTileUrl;

				var layer1=[G_NORMAL_MAP.getTileLayers()[0],streetsHyb];

				var custommap1 = new GMapType(layer1, G_NORMAL_MAP.getProjection(), "POI", G_SATELLITE_MAP);
				map.addMapType(custommap1);

				// WMS c
				var streetsHyb = new GTileLayer(new GCopyrightCollection(''),1,17);
					streetsHyb.myLayers="rompoly";
					streetsHyb.myFormat="image/png";
					streetsHyb.myBaseURL="http://" + host +":8086/cgi-bin/mapserv?map=/var/www/mapserver/romania/romania.map&";
					//streetsHyb.myBaseURL="http://192.168.1.12:8086/cgi-bin/mapserv?map=/var/www/mapserver/saltlake/postgres.map&";
					streetsHyb.getTileUrl=CustomGetTileUrl;

				var layer1=[G_SATELLITE_MAP.getTileLayers()[0],streetsHyb];

				var custommap1 = new GMapType(layer1, G_NORMAL_MAP.getProjection(), "POI Sat", G_SATELLITE_MAP);
				map.addMapType(custommap1);
					var streetsHyb = new GTileLayer(new GCopyrightCollection(''),1,17);
						streetsHyb.myLayers="streets";
						streetsHyb.myFormat="image/png";
						streetsHyb.myBaseURL="http://" + host +":8086/cgi-bin/mapserv?map=/var/www/mapserver/saltlake/postgres.map&";
						//streetsHyb.myBaseURL="http://www.iwiglobal.com:8080/cgi-bin/mapserv?map=/var/www/mapserver/saltlake/postgres.map&";
						streetsHyb.getTileUrl=CustomGetTileUrl;

	//http://192.168.1.219:8086/cgi-bin/mapserv?map=/var/www/mapserver/saltlake/postgres.map&&REQUEST=GetMap&SERVICE=WMS&VERSION=1.1.1&LAYERS=streets&STYLES=&FORMAT=image/png&TRANSPARENT=TRUE&SRS=EPSG:4326&BBOX=-98.15460205078125,35.72421761691415,-98.15185546875,35.72644736208901&WIDTH=256&HEIGHT=256&reaspect=false

					var layer1=[G_SATELLITE_MAP.getTileLayers()[0],streetsHyb];
					var custommap1 = new GMapType(layer1, G_NORMAL_MAP.getProjection(), "Streets", G_SATELLITE_MAP);


					map.addMapType(custommap1);



					// WMS c
					var streetsHyb = new GTileLayer(new GCopyrightCollection(''),1,17);
						streetsHyb.myLayers="buzwagi";
						streetsHyb.myFormat="image/png";
						//streetsHyb.myBaseURL="http://csr.iwiglobal.com:8086/cgi-bin/mapserv?map=/var/www/mapserver/saltlake/barrick.map&";
						streetsHyb.myBaseURL="http://" + host +":8086/cgi-bin/mapserv?map=/var/www/mapserver/saltlake/barrick.map&";
						streetsHyb.getTileUrl=CustomGetTileUrl;

					var layer1=[G_NORMAL_MAP.getTileLayers()[0],streetsHyb];

					var custommap1 = new GMapType(layer1, G_NORMAL_MAP.getProjection(), "Bar", G_SATELLITE_MAP);
					map.addMapType(custommap1);

//		var gicon = new GIcon();
//		gicon.image = "http://www.rescan.com/images/PageDesigns/ActiveMapMarker.png";
//		gicon.shadow = "http://www.rescan.com/images/PageDesigns/MapMarkerShadow.png";
//		gicon.iconSize = new GSize(15, 15);
//		gicon.shadowSize = new GSize(15, 15);
//		gicon.iconAnchor = new GPoint(6, 20);
//		gicon.infoWindowAnchor = new GPoint(5, 1);



		//var point = new GPoint(-70.194054, 18.960195);
		////var marker001 = new GMarker(point,gicon);
		//map.addOverlay(marker001);
		//marker001.openInfoWindowHtml("<b>Pueblo Viejo Project</b><p>Dominican Republic</p>");



					// WMS c
					var streetsHyb = new GTileLayer(new GCopyrightCollection(''),1,17);
						streetsHyb.myLayers="buzwagi";
						streetsHyb.myFormat="image/png";
						//streetsHyb.myBaseURL="http://" + host +":8086/cgi-bin/mapserv?map=/var/www/mapserver/Shell/shell.map&";
						//streetsHyb.myBaseURL="http://10.0.35.21:8080/cgi-bin/mapserv?map=/var/www/mapserver/Shell/shell.map&";
						streetsHyb.myBaseURL="http://" + host +":8086/cgi-bin/mapserv?map=/var/www/mapserver/saltlake/barrick.map&";
						streetsHyb.getTileUrl=CustomGetTileUrl;

					var layer1=[G_SATELLITE_MAP.getTileLayers()[0],streetsHyb];

					var custommap1 = new GMapType(layer1, G_NORMAL_MAP.getProjection(), "Barrick", G_SATELLITE_MAP);
					map.addMapType(custommap1);
					// dominican
					//map.setCenter( new GLatLng(26.08154296875 ,44.76818687659432 ), 10 );


					        //map.setCenter(new GLatLng(42.680,-109.83),13);
			        //var ovmap = new GOverviewMapControl(new GSize(200,200));
			        //map.addControl(ovmap);








			        geocoder = new GClientGeocoder();
					//GEvent.addListener(map, "mouseover", clicked);




//					var icon = new GIcon();
//					icon.image =  "http://www.iwiwitness.com:80/asip/asip/images/mm_20_blue.png";
//					icon.shadow =  "http://www.iwiwitness.com:80/asip/asip/images/mm_20_shadow.png";
//					icon.iconSize = new GSize(12, 20);
//					icon.shadowSize = new GSize(22, 20);
//					icon.iconAnchor = new GPoint(6, 20);
//					icon.infoWindowAnchor = new GPoint(5, 1);


				// tanzania

				//var gx = new GGeoXml("http://192.168.1.219:8086/zones.kml");
				//map.addOverlay(gx);

//				map.addOverlay(new GMarker(new GPoint(23.282655 ,45.0462), icon));
//				map.addOverlay(new GMarker(new GPoint(32.739258, -3.469557), icon));


//				 map.addOverlay(new GMarker(new GPoint(33.216026, -4.086839), icon));

				// romania

				//map.addOverlay(new GMarker(new GPoint( 26.102442, 44.435674), icon));



//				map.openInfoWindow(map.getCenter(),
//				                   document.createTextNode("Hello, world"));

				      //var point = new GLatLng(44.435674,26.102442);
				      //var url = "right.html";
				      //var marker = createTestMarker(point,'this is a test')
				      //map.addOverlay(marker); 
				//
				// Shell wells
				//
