package com.inthinc.sbs.regions;

import java.nio.ByteBuffer;

import com.inthinc.sbs.simpledatatypes.LatLong;
import com.inthinc.sbs.utils.SbsUtils;


public abstract class SbsStreet implements SbsRegion{
	
	protected long gid = 0;
	protected LatLong lowerLeftCorner = null;
	protected LatLong upperRightCorner = null;
	protected int category = 0;
	protected boolean isKph = false;
	protected int speedlimit = 0;
	protected int numLanes = 0;
	protected int nVerticies = 0;
	protected String name = null;
	protected LatLong [] points = null;
	protected long streethashcode = 0;
	protected int fileAsInt = 0;
	protected boolean isRamp = false;
	
	@Override
	public String toString(){
		return String.format("Segment: %d, Limit: %d, Kph: %s", fileAsInt, speedlimit, isKph);
	}
	
	/**
	 * Determine the distance from lat,lng to this street.  The heading weights the 
	 * calculation to avoid cross streets.
	 * @param lat
	 * @param lng
	 * @param course
	 * @param window
	 * @return Distance to street in feet.
	 */
	public abstract double getDistanceToStreet(double lat,double lng,int course,int window,int maxangle);
	
	public static SbsStreet fromByteBuf(ByteBuffer buf,int baseVer,double minLat,double minLon,int fileAsInt){
		return new Street(buf,baseVer,minLat,minLon,fileAsInt);
	}
	
	/**
	 * Parse a lat long from a binary buffer of data.
	 * @param baseVer - base version from which the point was obtained
	 * @param buf - buffer containing data, aligned at the start of the data.
	 * @param minLat - minimum latitude of the map
	 * @param minLong - minimum longitude of the map
	 * @return LatLong object containing the points.
	 * 
	 * A ByteBuffer is used here because this method is expected to be called as part
	 * of parsing a map.  It is static simply because it has no internal state, and is
	 * more independently testable than the remainder of the code.  This will increment
	 * the position of the byte buffer.
	 * 
	 */
	public static LatLong parsePoint(int baseVer,ByteBuffer buf,double minLat,double minLong)
	{
		int latitudeOffset = 0;
		long longitudeOffset = 0;
		double latitude = 0.0;
		double longitude = 0.0;
		final double DIVISOR = 80000.0;
		byte [] temp = new byte[3];
		
		if((buf == null)
				|| (buf.remaining() < 3)){
			return new LatLong();
		}
		
		temp[0] = buf.get();
		temp[1] = buf.get();
		temp[2] = buf.get();
		
		latitudeOffset = ((temp[2] & 0xff) << 4);
		latitudeOffset |= (((temp[1] & 0xff) & 0xF0) >> 4);
		
		longitudeOffset = (((temp[1] & 0xff) & 0x0F) << 8); 
		longitudeOffset |= (temp[0] & 0xff);
		
		if(baseVer <= 4)
		{
			latitudeOffset -= 40;
			longitudeOffset -= 40;
		}
		
		latitude = minLat + (((double) latitudeOffset) / DIVISOR);
		longitude = minLong + (((double) longitudeOffset) / DIVISOR);
		
		return new LatLong(latitude, longitude);
	}
	
	
	public static int getSpeedByCat(int cat){
		int lim = 0;
		switch (cat) {
		case 1:		
			lim = 75;
			break;
		case 2:
			lim = 75;
			break;
		case 3:
			lim = 60;
			break;
		case 4:
			lim = 50;
			break;
		case 5:
			lim = 40;
			break;
		case 6:
			lim = 30;
			break;
		case 7:
			lim = 20;
			break;
		case 8:
			lim = 5;
			break;
		default:
			break;
		}
		return lim;
	}
	
	public boolean isRamp(){
		return isRamp;
	}
	
	public static final double KPH_TO_MPH=0.621371192;
	
	/**
	 * @return Speedlimit x 100
	 */
	public int getSpeedLimit(){
		int limit = speedlimit;
		if(isRamp){
			limit = (speedlimit & (byte) 0x7F);
		}
		
		if(limit <= 0){
			limit = getSpeedByCat(category);
		}

		if(isKph){
			limit = (int)(limit * (KPH_TO_MPH + 0.5));
		}
		limit = limit * SbsUtils.SPEEDLIMIT_SCALE_FACTOR;
		return limit;
	}
	
	public int getFileAsInt(){
		return fileAsInt;
	}
	
	public int getCategory(){
		return category;
	}
	
	public int getNumLanes(){
		return numLanes;
	}
	
	public int getNumPointsInStreet(){
		if(points == null){
			return 0;
		}else{
			return points.length;
		}
	}
	
	@Override
	public boolean isWithin(int lat, int lng) {
		
		return SbsUtils.isWithinRegion(lat, lng, 
				lowerLeftCorner.ilatitude, upperRightCorner.ilatitude,
				lowerLeftCorner.ilongitude,upperRightCorner.ilongitude);
		
	}
	
	/**
	 * @return lower left point of street bounding box.
	 */
	public abstract LatLong getLowerLeft();
	
	/**
	 * @return upper right point of street bounding box.
	 */
	public abstract LatLong getUpperRight();
	
	public String getStreetName(){
		return name;
	}
	
	/**
	 * @return true if street limit is kph
	 */
	public boolean isKph(){
		return isKph;
	}
	
	public long getGID(){
		return gid;
	}
	
	public Long getStreetHash(){
		return Long.valueOf(streethashcode);
	}
	

	
}
