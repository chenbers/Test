package com.inthinc.pro.model.pagination;

import java.io.Serializable;

public class TableFilterField implements Serializable{

	private String field;
	private Object filter;	
	
	public TableFilterField(String field, Object filter) {
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
	public Object getFilter() {
		return filter;
	}
	public void setFilter(Object filter) {
		this.filter = filter;
	}
	
	@Override
	public String toString()
	{
		return field + ": " + filter;
	}

}
