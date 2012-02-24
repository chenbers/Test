package com.inthinc.sbs;


/**
 * Wrapper class for a speed limit.  Sbs provides more detail associated with a speedlimit
 * than just the limit.  This provides a convenient wrapper.
 * @author jlitzinger
 *
 */
public class SpeedLimit {

	public final int speedLimit;
	public final long closestGID;
	public final long sourceGID;
	public final int fileAsInt;
	public final boolean isKph;
	public final int baseline;
	public final int exRev;
	
	
	public SpeedLimit(){
		this.speedLimit = -1;
		this.closestGID = 0;
		this.sourceGID = 0;
		this .fileAsInt = 0;
		this.isKph = false;
		this.baseline = 0;
		this.exRev = 0;
	}
	
	public SpeedLimit(int speedLimit,long closestGID, long sourceGID,
			int fileAsInt,boolean isKph,int base,int ex){
		
		this.speedLimit = speedLimit;
		this.closestGID = closestGID;
		this.sourceGID = sourceGID;
		this .fileAsInt = fileAsInt;
		this.isKph = isKph;
		this.baseline = base;
		this.exRev = ex;
		
	}
	
	@Override
	public String toString(){
		return String.format("Limit: %d, fileAsInt: %d", speedLimit, fileAsInt);
	}
	
	
}
