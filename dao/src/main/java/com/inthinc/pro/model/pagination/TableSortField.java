package com.inthinc.pro.model.pagination;

public class TableSortField {
	
	private SortOrder order;
	private String field;
	
	
	public TableSortField(SortOrder order, String field) {
		super();
		this.order = order;
		this.field = field;
	}
	

	public SortOrder getOrder() {
		return order;
	}
	public void setOrder(SortOrder order) {
		this.order = order;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	
}
