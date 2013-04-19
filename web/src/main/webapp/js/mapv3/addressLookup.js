		var addressLookup = (function() {
        	var collectAddressElements = function(divID) {
        		var addressElements = [];
   				jQuery('#'+divID).each( function() {
	   					var addressElement = {
							"lat" : jQuery(this).find('*[id$="addressLookupLat"]')[0].value,
	   						"lng" :  jQuery(this).find('*[id$="addressLookupLng"]')[0].value,
	   						"domElement" : jQuery(this).find('*[id$="addressLookupElement"]')[0],
	   						"altText" : jQuery(this).find('*[id$="addressLookupAltText"]')[0].value
						};
	   					addressElements.push(addressElement);
   				});
   				return addressElements;
    		};
        	return {
        		findAddress : function(divID, callback) {
		    		inthincMap.reverseGeocodeList(collectAddressElements(divID), callback);
        		}
        	};
        })();
