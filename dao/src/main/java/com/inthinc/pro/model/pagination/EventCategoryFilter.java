package com.inthinc.pro.model.pagination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.inthinc.pro.model.EventType;


public class EventCategoryFilter implements TableFilterFactory{
	private EventType key;
	private List<Integer> typeList;
	private List<Integer> aggTypeList;
	
	
	public EventCategoryFilter() {
		
	}
	public EventCategoryFilter(EventType key, List<Integer> typeList,
			List<Integer> aggTypeList) {
		super();
		this.key = key;
		this.typeList = typeList;
		this.aggTypeList = aggTypeList;
	}
	public EventCategoryFilter(EventType key, Integer[] typeArray, Integer[] aggTypeArray) {
		super();
		this.key = key;
		if (typeArray == null)
			this.typeList = null;
		else this.typeList = Arrays.asList(typeArray);
		
		if (aggTypeArray == null)
			this.aggTypeList = null;
		else this.aggTypeList = Arrays.asList(aggTypeArray);
	}
	
	
	public EventType getKey() {
		return key;
	}


	public void setKey(EventType key) {
		this.key = key;
	}


	public List<Integer> getTypeList() {
		return typeList;
	}


	public void setTypeList(List<Integer> typeList) {
		this.typeList = typeList;
	}


	public List<Integer> getAggTypeList() {
		return aggTypeList;
	}


	public void setAggTypeList(List<Integer> aggTypeList) {
		this.aggTypeList = aggTypeList;
	}
	@Override
	public List<TableFilterField> getFilters() {
		
		List<TableFilterField>  tableFilterList = new ArrayList<TableFilterField>();
		tableFilterList.add(new TableFilterField("type", typeList));
		tableFilterList.add(new TableFilterField("aggType", aggTypeList));
		
		return tableFilterList;
	}
	


}
