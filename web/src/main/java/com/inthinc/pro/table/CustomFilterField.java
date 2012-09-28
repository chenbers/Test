package com.inthinc.pro.table;

import javax.el.Expression;

import org.richfaces.model.ExtendedFilterField;

public class CustomFilterField extends ExtendedFilterField {

	Object filterObject;
	
	public CustomFilterField(Expression expression, Object filterObject) {
		super(expression, (filterObject == null) ? "" : filterObject.toString());
		this.filterObject = filterObject;
	}

	public Object getFilterObject() {
		return filterObject;
	}

	public void setFilterObject(Object filterObject) {
		this.filterObject = filterObject;
	}

}
