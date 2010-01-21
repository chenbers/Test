package com.inthinc.pro.table.dao.model;

import java.util.List;


public class PageParams {
	
	Integer startRow;
	Integer endRow;
	
	List<TableSortField> sortList;
	List<TableFilterField> filterList;

	public PageParams(Integer startRow, Integer endRow,
			List<TableSortField> sortList, List<TableFilterField> filterList) {
		super();
		this.startRow = startRow;
		this.endRow = endRow;
		this.sortList = sortList;
		this.filterList = filterList;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public List<TableSortField> getSortList() {
		return sortList;
	}

	public void setSortList(List<TableSortField> sortList) {
		this.sortList = sortList;
	}

	public List<TableFilterField> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<TableFilterField> filterList) {
		this.filterList = filterList;
	}


}
