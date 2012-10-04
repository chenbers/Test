package com.inthinc.pro.model.pagination;

import java.io.Serializable;

import com.inthinc.pro.dao.annotations.Column;

public class TableFilterField implements Serializable{

	private String field;
	private Object filter;	
    @Column(updateable = false)
	private FilterOp filterOp;
	
    public TableFilterField(String field, Object filter) {
		this(field, filter, FilterOp.LIKE);
	}
	
    public TableFilterField(String field, Object filter, FilterOp filterOp) {
        super();
        this.field = field;
        this.filter = filter;
        this.filterOp = filterOp;
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
    public FilterOp getFilterOp() {
        return filterOp;
    }


    public void setFilterOp(FilterOp filterOp) {
        this.filterOp = filterOp;
    }


	
	@Override
	public String toString()
	{
		return field + ": " + filter;
	}

}
