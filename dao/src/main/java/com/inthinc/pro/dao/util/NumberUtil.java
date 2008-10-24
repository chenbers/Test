package com.inthinc.pro.dao.util;

public class NumberUtil 
{
	
	public static final double roundDouble(double d, int places) {
		return Math.round(d * Math.pow(10, (double) places)) / Math.pow(10, (double) places);
	}
}
