
function getRectPoly(lpt1, lpt2)
{
    cpts = [];
    
    cpts.push(lpt1);
    cpts.push(new GLatLng(lpt1.lat(), lpt2.lng()));
    cpts.push(lpt2);
    cpts.push(new GLatLng(lpt2.lat(), lpt1.lng()));
    cpts.push(lpt1);
    
    return cpts;
}

function getCirclePoly(lpt1, lpt2)
{
    cpts = [];
    var m = Math.PI / 180;
    var sides = 40;
    var v = 360/sides;


    var dis = distanceMiles(lpt1, lpt2);

    var minD = dis;
    var maxD = dis;
    
    // Convert Lat/Lng to Mercator Projection
    var mpt1 = latlng2merc(lpt1);
    var mpt2 = latlng2merc(lpt2);

    // Find distance between points
    //var r = mpt1.distanceFrom(mpt2) ;
	var dx = mpt1.x - mpt2.x;
	var dy = mpt1.y - mpt2.y;
	var r = Math.sqrt(dx*dx+dy*dy);

    for(var i=0; i<=360; i+= v)
    {
        var pt = new GPoint( mpt1.x + (r * Math.sin(i * m)), mpt1.y + (r * Math.cos(i * m)));
        // Convert back to lat/lng from Mercator
		var tmpp = merc2latlng(pt);
		var m3 = Math.pow(10, 6);
		tmpp.x =  Math.round(tmpp.x * m3) / m3;
		tmpp.y =  Math.round(tmpp.y * m3) / m3;
        cpts.push(tmpp);
    }

    return cpts;
}

// Convert Lat/Lng to Mercator Projection
function latlng2merc(llpoint) {
    return new GPoint(llpoint.x, Math.log(Math.tan((Math.PI/4)+llpoint.y*(Math.PI/180.0)/2))*(180.0/Math.PI));
}

// Convert Mercator Projection to Lat/Lng
function merc2latlng(merpoint) {
    return new GPoint(merpoint.x, (2*Math.atan(Math.exp(merpoint.y*(Math.PI/180)))-Math.PI/2)*(180/Math.PI));
}


function distanceMiles(pt1, pt2)
{
    var rpt1 = new GPoint();
    rpt1.x = pt1.x * (Math.PI/180);
    rpt1.y = pt1.y * (Math.PI/180);

    var rpt2 = new GPoint();
    rpt2.x = pt2.x * (Math.PI/180);
    rpt2.y = pt2.y * (Math.PI/180);

    var R = 3963.1;
    var dLat = rpt2.y - rpt1.y;
    var dLong = rpt2.x - rpt1.x;
    var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.cos(rpt1.y) * Math.cos(rpt2.y) * Math.sin(dLong/2) * Math.sin(dLong/2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    var d = R * c;



    return d;

}

