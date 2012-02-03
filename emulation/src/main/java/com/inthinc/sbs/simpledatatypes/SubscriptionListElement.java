package com.inthinc.sbs.simpledatatypes;

/**
 * Class that is a container for elements used in processing the
 * results of checkSbsSubscribed.
 * @author jlitzinger
 */
public final class SubscriptionListElement {
	public final int fileAsInt;
	public final int version;
	public final int base;
	public final int distanceInSquares;
	
	public SubscriptionListElement(int fileAsInt,int version,int base,int distanceInSquares){
		this.fileAsInt = fileAsInt;
		this.version = version;
		this.base = base;
		this.distanceInSquares = distanceInSquares;
	}

};
