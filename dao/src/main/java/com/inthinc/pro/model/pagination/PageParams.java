package com.inthinc.pro.model.pagination;

import java.util.List;


public class PageParams {
	
	private Integer startRow;
	private Integer endRow;
	
	private TableSortField sort;
	private List<TableFilterField> filterList;

	public PageParams(Integer startRow, Integer endRow,
			TableSortField sort, List<TableFilterField> filterList) {
		super();
		this.startRow = startRow;
		this.endRow = endRow;
		this.sort = sort;
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

	public TableSortField getSort() {
		return sort;
	}

	public void setSort(TableSortField sort) {
		this.sort = sort;
	}

	public List<TableFilterField> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<TableFilterField> filterList) {
		this.filterList = filterList;
	}


}
