// Polyline with arrows
//
// Bill Chadwick May 2008 (Ported to v3 by Peter Bennett)
// 
// Free for any use
//


//Constructor
// cj - several modifications made to original
function BDCCArrowedPolyline(points, color, weight, opacity, opts, gapPx, headLength, headColor, headWeight, headOpacity, map) {	
    
    this.gapPx = gapPx;
    this.points = points;
    this.color = color;
    this.weight = weight;
    this.opacity = opacity;
    this.headLength = headLength;
    this.headColor = headColor;
    this.headWeight = headWeight;
    this.headOpacity = headOpacity;
    this.opts = opts;
    this.heads = new Array();
    this.line = null;
    this.map = map;   
    this.setMap(map);
    this.prj = map.getProjection();
/*
    var projection = this.prj;
    if (!this.prj)
	google.maps.addListenerOnce(map, "projection_changed", function() {
          this.prj = this.getProjection();
          console.log(this.prj);
	});
    else console.log(this.prj);
*/	 
}

// Overlay v3
BDCCArrowedPolyline.prototype = new google.maps.OverlayView();

// onRemove function
BDCCArrowedPolyline.prototype.onRemove = function() {
	
    try{
   		for(var i=0; i<this.heads.length; i++) {
   			this.heads[i].setMap(null);
   		}
        if (this.line) 
			this.line.setMap(null);
    }
    catch(ex)
    {
    }

};


BDCCArrowedPolyline.prototype.onAdd = function() {
   this.line = new google.maps.Polyline({
               		clickable:false,
					editable:false,
					geodesic:false,
					map:this.map,
					path:this.points,
					strokeColor:this.color,
					strokeOpacity:this.opacity,
					strokeWeight:this.weight,
					visible:true
					});
	
};

BDCCArrowedPolyline.prototype.draw = function() {
	
	var zoom = this.map.getZoom();
	for(var i=0; i<this.heads.length; i++) {
			this.heads[i].setMap(null);
	}
   
   // the arrow heads
   this.heads = new Array();

   this.prj = this.getProjection();

   var p1 = this.prj.fromLatLngToContainerPixel(this.points[0]);//first point
   
   var p2;//next point
   var dx;
   var dy;
   var sl;//segment length
   var theta;//segment angle
   var ta;//distance along segment for placing arrows

   var segmentsLength = 0; // Accumulate segments until they are >= gapPx and then add an arrow
   var adjustedGapPx = this.gapPx;
   if (this.gapPx == -1){
	   	//space depending on zoom level
	   	if (zoom < 5){
	   		return;
	   	}
	   	else if((zoom >=5) && (zoom <=14)) {
	   		adjustedGapPx = 200;
	   	}
	   	else if (zoom >=14) {
	   		adjustedGapPx = 500;
	   	}
   }
   for (var i=1; i<this.points.length; i++){

		  p2 = this.prj.fromLatLngToContainerPixel(this.points[i]);
	      dx = p2.x-p1.x;
	      dy = p2.y-p1.y;
	      sl = Math.sqrt((dx*dx)+(dy*dy));
	      theta = Math.atan2(-dy,dx);
	     
	        if(this.gapPx == 0){
	                //just put one arrow at the end of the line
	                this.addHead(p2.x,p2.y,theta,zoom);
	        }
	        else if(this.gapPx == 1) {
	                //just put one arrow in the middle of the line
	                var x = p1.x + ((sl/2) * Math.cos(theta));
	                var y = p1.y - ((sl/2) * Math.sin(theta));
	                this.addHead(x,y,theta,zoom); 

	        }
	        else{
	        //iterate along the line segment placing arrow markers
	        //don't put an arrow within gapPx of the beginning or end of the segment
	        	
	        	if (sl < adjustedGapPx){
	        		
	        		segmentsLength += sl;
	        		
	        		if (segmentsLength >= adjustedGapPx){
	        			
	                    var x = p1.x + ((sl/2) * Math.cos(theta));
	                    var y = p1.y - ((sl/2) * Math.sin(theta));
	                    this.addHead(x,y,theta,zoom);
	                    segmentsLength = 0;
	        		}
	       			
	        	}
	        	else {
	        		
	        		ta = adjustedGapPx;
	        		while(ta < sl){
	            	  
		                var x = p1.x + (ta * Math.cos(theta));
		                var y = p1.y - (ta * Math.sin(theta));
		                this.addHead(x,y,theta,zoom);
		                ta += adjustedGapPx;  
		                segmentsLength = 0;
	        		}
	        	}
	      }

	      p1 = p2;  
	   }

};

BDCCArrowedPolyline.prototype.addHead = function(x,y,theta,zoom) {
    //add an arrow head at the specified point
    var t = theta + (Math.PI/8) ;
    if(t > Math.PI)
        t -= 2*Math.PI;
    var t2 = theta - (Math.PI/8) ;
    if(t2 <= (-Math.PI))
        t2 += 2*Math.PI;
    var pts = new Array();
    var x1 = x-Math.cos(t)*this.headLength;
    var y1 = y+Math.sin(t)*this.headLength;
    var x2 = x-Math.cos(t2)*this.headLength;
    var y2 = y+Math.sin(t2)*this.headLength;
    pts.push(this.prj.fromContainerPixelToLatLng(new google.maps.Point(x1,y1)));
    pts.push(this.prj.fromContainerPixelToLatLng(new google.maps.Point(x,y)));    
    pts.push(this.prj.fromContainerPixelToLatLng(new google.maps.Point(x2,y2)));

 	var arrow = new google.maps.Polygon({
                clickable:false,
					 path:pts,
					 map : this.map,
					 strokeColor:'#000000',
					 fillColor:this.color,
					 strokeOpacity:this.opacity,
					 fillOpacity:this.opacity,
					 strokeWeight:1,
					 visible:true
					 });
 
    this.heads.push(arrow);
};


BDCCArrowedPolyline.prototype.getBounds = function() {
	var bounds = new google.maps.LatLngBounds();
	for (var i = 0; i < this.points.length; i++) {
		bounds.extend(this.points[i]);
	}
	return bounds;
};
