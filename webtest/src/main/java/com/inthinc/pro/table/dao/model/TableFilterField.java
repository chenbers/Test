package com.inthinc.pro.table.dao.model;

public class TableFilterField {

	private String field;
	private String filter;		//regex
	
	public TableFilterField(String field, String filter) {
		super();
		this.field = field;
		this.filter = filter;
	}
	
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
}
