package com.inthinc.pro.model.pagination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.NoteType;


public class EventCategoryFilter implements TableFilterFactory{
	private EventType key;
	private List<NoteType> typeList;
	private List<Integer> aggTypeList;
	
	
	public EventCategoryFilter() {
		
	}
	public EventCategoryFilter(EventType key, List<NoteType> typeList,
			List<Integer> aggTypeList) {
		super();
		this.key = key;
		this.typeList = typeList;
		this.aggTypeList = aggTypeList;
	}
	public EventCategoryFilter(EventType key, NoteType[] typeArray, Integer[] aggTypeArray) {
		super();
		this.key = key;
		if (typeArray == null)
			this.typeList = null;
		else this.typeList = Arrays.asList(typeArray);
		
		if (aggTypeArray == null)
			this.aggTypeList = null;
		else this.aggTypeList = Arrays.asList(aggTypeArray);
	}
	
    public EventCategoryFilter(NoteType[] typeArray, Integer[] aggTypeArray) {
        super();
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


	public List<NoteType> getTypeList() {
		return typeList;
	}


	public void setTypeList(List<NoteType> typeList) {
		this.typeList = typeList;
	}


	public List<Integer> getAggTypeList() {
		return aggTypeList;
	}


	public void setAggTypeList(List<Integer> aggTypeList) {
		this.aggTypeList = aggTypeList;
	}
	@Override
	public List<TableFilterField> getFilters(String propertyName) {
		
		List<TableFilterField>  tableFilterList = new ArrayList<TableFilterField>();
		tableFilterList.add(new TableFilterField("type", typeList));
		tableFilterList.add(new TableFilterField("aggType", aggTypeList));
		
		return tableFilterList;
	}
	


}
