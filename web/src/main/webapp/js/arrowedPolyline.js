// Polyline with arrows
//
// Bill Chadwick May 2008
//
// Free for any use
//

// Constructor params exactly as GPolyline then
// 1) arrow spacing in pixels,
// 2) arrow head length in pixels
// 3) arrow colour
// 4) arrow thickness in pixels
// 5) arrow opacity

function BDCCArrowedPolyline(points, color, weight, opacity, opts, gapPx, 
				headLength, headColor, headWeight, headOpacity, headPointiness) {    
   
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
    this.headPointiness = headPointiness;
    this.heads = new Array();
    this.line = new GPolyline(this.points,this.color,this.weight,this.opacity,this.opts);
    this.isHidden = false;
   
}
BDCCArrowedPolyline.prototype = new GOverlay();


BDCCArrowedPolyline.prototype.initialize = function(map) {

    this.map = map;  
    this.prj = map.getCurrentMapType().getProjection();
    var rdrw = GEvent.callback(this,this.recalc );
        this.lstnMoveEnd = GEvent.addListener(map,"zoomend",function(){rdrw ();});
        this.lstnType = GEvent.addListener(map,"maptypechanged",function(){rdrw ();});
    	this.map.addOverlay(this.line);
    	this.recalc();//first draw
}

BDCCArrowedPolyline.prototype.remove = function() {
//	alert("Removing line1");

    try{
        if (this.line){
        	
            this.map.removeOverlay(this.line);
        }
        this.removeHeads();
        GEvent.removeListener(this.lstnMoveEnd);
        GEvent.removeListener(this.lstnType);
        this.map = null;
    }
    catch(ex)
    {
    	alert("Removing line");
    	
    }
}
BDCCArrowedPolyline.prototype.removeHeads = function() {

    try{
        for(var i=0; i<this.heads.length; i++){
        	
        	if(this.heads[i]){
        		
	            this.map.removeOverlay(this.heads[i]);
        	}
        }
        this.heads.clear();
      }
    catch(ex)
    {
    	alert("Removing heads");
   	
    }
}

BDCCArrowedPolyline.prototype.hide = function() {
	isHidden = true;
    try{
        if (this.line)
            this.line.hide();
        for(var i=0; i<this.heads.length; i++)
            this.heads[i].hide();
    }
    catch(ex)
    {
    	alert("hide");
    }
}
BDCCArrowedPolyline.prototype.show = function() {
	isHidden = false;
    try{
        if (this.line)
            this.line.show();
        for(var i=0; i<this.heads.length; i++)
            this.heads[i].show();
    }
    catch(ex)
    {
    	alert("Show");
   }
}
BDCCArrowedPolyline.prototype.getBounds = function() {
	var bounds = null;
    try{
        if (this.line){
            bounds = new GLatLngBounds(	this.line.getBounds().getSouthWest(),
            							this.line.getBounds().getNorthEast());
        }
        return bounds;
    }
    catch(ex)
    {
    	alert("bounds");
    	return null;
    }
}

BDCCArrowedPolyline.prototype.redraw = function(force) {
    return;//do nothing, the GPolyline line and heads draw themselves
}


BDCCArrowedPolyline.prototype.copy = function(map) {
//	alert("copy");
    return new BDCCArrowedPolyline(this.points,this.color,this.weight,this.opacity,this.opts,this.gapPx, 
    		this.headLength, this.headColor, this.headWeight, this.headOpacity, this.headPointiness);
}


BDCCArrowedPolyline.prototype.recalc = function() {
//	alert("recalc line is " +this.line.getVertexCount());
   var zoom = this.map.getZoom();
 //  this.remove();

   //the main polyline
//   this.line = new GPolyline(this.points,this.color,this.weight,this.opacity,this.opts);
//   this.map.addOverlay(this.line);
   this.removeHeads();
    // the arrow heads
//   this.heads = new Array();

   var p1; 
   //= this.prj.fromLatLngToPixel(this.points[0],  zoom);//first point
   var p2;//next point
   var dx;
   var dy;
   var sl;//segment length
   var theta;//segment angle
   var ta;//distance along segment for placing arrows
   
   var segmentsLength = 0; // Accumulate segments until they are >= gapPx and then add an arrow
   
   for (var i=1; i<this.points.length; i++){

	  p1 = this.prj.fromLatLngToPixel(this.points[i-1],  zoom);

      p2 = this.prj.fromLatLngToPixel(this.points[i],  zoom);
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
        	
        	if (sl < this.gapPx){
        		
        		segmentsLength += sl;
        		
        		if (segmentsLength >= this.gapPx){
        			
                    var x = p1.x + ((sl/2) * Math.cos(theta));
                    var y = p1.y - ((sl/2) * Math.sin(theta));
                    this.addHead(x,y,theta,zoom);
                    segmentsLength = 0;
        		}
       			
        	}
        	else {
        		
        		ta = this.gapPx;
        		while(ta < sl){
            	  
	                var x = p1.x + (ta * Math.cos(theta));
	                var y = p1.y - (ta * Math.sin(theta));
	                this.addHead(x,y,theta,zoom);
	                ta += this.gapPx;  
	                segmentsLength = 0;
        		}
        	}
     
                //line too short, put one arrow in its middle every segmentInterval
//              if(ta == this.gapPx){
//        	
//                        var x = p1.x + ((sl/2) * Math.cos(theta));
//                        var y = p1.y - ((sl/2) * Math.sin(theta));
//                        this.addHead(x,y,theta,zoom);
//              }
//        }
      }

//      p1 = p2;  
   }
}

BDCCArrowedPolyline.prototype.addHead = function(x,y,theta,zoom) {

    //add an arrow head at the specified point
    var t = theta + (Math.PI/this.headPointiness) ;
    if(t > Math.PI)
        t -= 2*Math.PI;
    var t2 = theta - (Math.PI/this.headPointiness) ;
    if(t2 <= (-Math.PI))
        t2 += 2*Math.PI;
    var pts = new Array();
    var x1 = x-Math.cos(t)*this.headLength;
    var y1 = y+Math.sin(t)*this.headLength;
    var x2 = x-Math.cos(t2)*this.headLength;
    var y2 = y+Math.sin(t2)*this.headLength;
    pts.push(this.prj.fromPixelToLatLng(new GPoint(x1,y1), zoom));
    pts.push(this.prj.fromPixelToLatLng(new GPoint(x,y), zoom));    
    pts.push(this.prj.fromPixelToLatLng(new GPoint(x2,y2), zoom));
    pts.push(this.prj.fromPixelToLatLng(new GPoint(x1,y1), zoom));
      this.heads.push(new GPolygon(pts, this.headColor,this.headWeight,this.headOpacity,  this.color, this.headOpacity, {clickable:false})); 
//    this.heads.push(new GPolyline(pts,this.headColor,this.headWeight,this.headOpacity,this.opts));
    this.map.addOverlay(this.heads[this.heads.length-1]);
}

