package com.inthinc.pro.model.pagination;

import java.io.Serializable;
import java.util.List;


public class PageParams  implements Serializable {
	
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

	public PageParams() {
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

	@Override
	public String toString() {
		
		return "startrow: " + (startRow == null ? "null" : startRow) +
		"endRow: " + (endRow == null ? "null" : endRow) +
		"sort: " + ((sort == null) ? "null" : sort.toString()) +
		"filterList: " + ((filterList == null) ? "null" : filterList.toString());
	}

}
