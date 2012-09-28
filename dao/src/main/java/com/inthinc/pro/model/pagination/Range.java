package com.inthinc.pro.model.pagination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Range implements TableFilterFactory, Serializable {

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

	@Override
	public List<TableFilterField> getFilters(String propertyName) {
		List<TableFilterField>  tableFilterList = new ArrayList<TableFilterField>();
		tableFilterList.add(new TableFilterField(propertyName, this));
		return tableFilterList;
	}
	
}
