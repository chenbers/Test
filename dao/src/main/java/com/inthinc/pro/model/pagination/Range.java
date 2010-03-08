package com.inthinc.pro.model.pagination;

public class Range {

	private Number min;
	private Number max;
	public Range() {
		
	}
	
	public Range(Number min, Number max) {
		super();
		this.min = min;
		this.max = max;
	}

	public Number getMin() {
		return min;
	}
	public void setMin(Number min) {
		this.min = min;
	}
	public Number getMax() {
		return max;
	}
	public void setMax(Number max) {
		this.max = max;
	}
	
}
