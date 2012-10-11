package com.inthinc.pro.reports.tabular;

public class Result {

	String display;
	Object sort;
	
	public Result(String display, Object sort) {
		this.display = display;
		this.sort = sort;
	}
	public Result()
	{
		
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public Object getSort() {
		return sort;
	}
	public void setSort(Object sort) {
		this.sort = sort;
	}
}
