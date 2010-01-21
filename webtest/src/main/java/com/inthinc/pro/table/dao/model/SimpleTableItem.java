package com.inthinc.pro.table.dao.model;

public class SimpleTableItem {

	String strItem;
	Integer intItem;
	Float floatItem;
	
	public SimpleTableItem()
	{
		
	}
	public SimpleTableItem(String strItem, Integer intItem,	Float floatItem)
	{
		this.strItem = strItem;
		this.intItem = intItem;
		this.floatItem = floatItem;
	}
	public String getStrItem() {
		return strItem;
	}
	public void setStrItem(String strItem) {
		this.strItem = strItem;
	}
	public Integer getIntItem() {
		return intItem;
	}
	public void setIntItem(Integer intItem) {
		this.intItem = intItem;
	}
	public Float getFloatItem() {
		return floatItem;
	}
	public void setFloatItem(Float floatItem) {
		this.floatItem = floatItem;
	}
}
