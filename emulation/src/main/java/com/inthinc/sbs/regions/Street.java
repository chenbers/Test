package com.inthinc.sbs.regions;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.inthinc.sbs.simpledatatypes.LatLong;
import com.inthinc.sbs.utils.SbsUtils;


public final class Street extends SbsStreet {

	/**
	 * Intended for testing use only.
	 */
	Street(int limit){
		category = -1;
		speedlimit = limit;
	}
	
	/**
	 * @param buf - buffer containing the street data
	 * @param baseVer - baseline version of the map we are parsing
	 * @param minLat - minimum latitude of the map being parsed
	 * @param minLon - minimum longitude of the map being parsed
	 * @param fileAsInt - fileAsInt of containing map.
	 */
	public Street(ByteBuffer buf,int baseVer,double minLat,double minLon,int fileAsInt){
		parseHeader(buf,baseVer,minLat,minLon);
		parseBody(buf,baseVer,minLat,minLon);
		this.fileAsInt = fileAsInt;
	}

	@Override
	public String toString() {
		StringBuilder streetheader = new StringBuilder();
		streetheader.append("Street: ");
		streetheader.append(gid);
		streetheader.append(",");
		streetheader.append(category);
		streetheader.append(",");
		streetheader.append(nVerticies);
		return streetheader.toString();
	}
	

	@Override
	public double getDistanceToStreet(double lat, double lng,int course,
			int window, int maxangle) {
		
		double dist = 9999.0;
		double tdist = 0.0;
		int numPoints = points.length;
		for(int i = 0;i < (numPoints - 1);i++){
			tdist = SbsUtils.getPointLineDistance(lat,lng,
					course,points[i].latitude,points[i].longitude,
					points[i+1].latitude,points[i+1].longitude,
					window, maxangle);
			if(tdist < dist){
				dist = tdist;
			}
		}
		
		return dist;
	}

	@Override
	public LatLong getLowerLeft() {
		return lowerLeftCorner;
	}

	@Override
	public LatLong getUpperRight() {
		return upperRightCorner;
	}

	public int getNumVerticies(){
		return nVerticies;
	}
	
	private void parseHeader(ByteBuffer buf,int baseVer, double minLat,double minLon){
		
		
		gid = (long) ((buf.get() & 0xFF) | ((buf.get() << 8) & 0xFF00) | ((buf.get() << 16) & 0xFF0000) | ((buf.get() << 24) & 0xFF000000));
		
		
		byte val = buf.get();
		category = (int)(val & (byte) 0x0F);
		numLanes = (int)((val & (byte) 0x0F) >> 4);
		speedlimit = buf.get();
		short sval = (short) ((buf.get() & 0xFF) | ((buf.get() << 8) & 0xFF00));
		isKph = (((sval & 0x8000) >> 15) == 1);
		nVerticies = (int) (sval & 0x7FFF);
		
		
		ByteBuffer hashbuf = buf.duplicate();
		streethashcode = 0;
		streethashcode |= ((hashbuf.get() & 0xFF) << 24);
		streethashcode |= ((hashbuf.get() & 0xFF) << 16);
		streethashcode |= ((hashbuf.get() & 0xFF) << 8);
		streethashcode |= (hashbuf.get() & 0xFF);
		
		lowerLeftCorner = SbsStreet.parsePoint(baseVer, buf, minLat, minLon);
		upperRightCorner = SbsStreet.parsePoint(baseVer, buf, minLat, minLon);
		
		//TODO:  Add envelope padding.
		
		
		
		int nameLen = buf.get();
		if(nameLen > 0){
			byte [] n = new byte[nameLen];
			buf.get(n);
			
			this.name = new String(n, Charset.forName("US-ASCII"));
		}
		
	}

	private void parseBody(ByteBuffer buf,int baseVer, double minLat,double minLon){
		points = new LatLong[nVerticies];
		for(int i = 0;i < nVerticies;i++){
			points[i] = SbsStreet.parsePoint(baseVer, buf, minLat, minLon);
		}
	}

}
