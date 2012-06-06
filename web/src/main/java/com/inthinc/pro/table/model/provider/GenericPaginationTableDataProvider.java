package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;


public abstract class GenericPaginationTableDataProvider<T> implements PaginationDataProvider<T> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7139790279034588222L;
	private List<TableFilterField> filters;
	private TableSortField sort;

	@Override
    public abstract List<T> getItemsByRange(int firstRow, int endRow);

	@Override
    public abstract int getRowCount();


	@Override
	public T getItemByKey(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getKey(T rec) {
		//return rec;
		return null;
	}
	
	@Override
	public void addSortField(TableSortField sortField)
	{
		this.sort = sortField;
	}
	@Override
	public void addFilterField(TableFilterField filterField)
	{
		TableFilterField removeField = null;
		for (TableFilterField tableFilterField : getFilters()) {
			if (tableFilterField.getField().equals(filterField.getField())) {
				removeField = tableFilterField;
				break;
			}
		}
		if (removeField != null)
			getFilters().remove(removeField);
		if (filterField.getFilter() == null)
			return;
		getFilters().add(filterField);
	}

	public List<TableFilterField> getFilters() {
		if (filters == null)
		{
			filters = new ArrayList<TableFilterField>();
		}
		return filters;
	}
	
	protected List<TableFilterField> removeBlankFilters(List<TableFilterField> filters) {
		List<TableFilterField>  newFilters = new ArrayList<TableFilterField>();
		if (filters != null) {
			for (TableFilterField filter : filters) {
				if (filter.getFilter() == null || filter.getFilter().toString().isEmpty())
					continue;
				
				newFilters.add(filter);
			}
		}
		return newFilters;
	}

	public void setFilters(List<TableFilterField> filters) {
		this.filters = filters;
	}
	public TableSortField getSort() {
		return sort;
	}

	public void setSort(TableSortField sort) {
		this.sort = sort;
	}


}

