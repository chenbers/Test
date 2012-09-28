package com.inthinc.pro.model;

import java.util.List;

public class IdlingReportData {

	List<IdlingReportItem> itemList;
	Integer total;
	
	public List<IdlingReportItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<IdlingReportItem> itemList) {
		this.itemList = itemList;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
