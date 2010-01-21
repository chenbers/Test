package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import org.richfaces.component.html.HtmlDatascroller;

import com.inthinc.pro.table.dao.model.TableFilterField;
import com.inthinc.pro.table.dao.model.TableSortField;
import com.inthinc.pro.table.dao.pagination.PaginationDAOService;


public class BasePaginationTableDataProvider<T> implements PaginationDataProvider<T> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7139790279034588222L;
	private PaginationDAOService<T> paginationDAOService;
	private List<TableFilterField> filters;
	private List<TableSortField> sorts;

	@Override
    public List<T> getItemsByRange(int firstRow, int endRow) {

        return paginationDAOService.getItemsByRange(firstRow, endRow, getSorts(), getFilters());

    }

	@Override
    public int getRowCount() {

        return paginationDAOService.countAll(getFilters());

    }

	@Override
	public T getItemByKey(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getKey(T rec) {
		return rec;
	}
	
	@Override
	public void addSortField(TableSortField sortField)
	{
		// just one sort
		getSorts().clear();
		
		System.out.println("### addSortField: " + sortField.getOrder());
		
		getSorts().add(sortField);
/*		
		TableSortField removeField = null;
		for (TableSortField tableSortField : getSorts()) {
			if (tableSortField.getField().equals(sortField.getField())) {
				removeField = tableSortField;
				break;
			}
		}
		if (removeField != null)
			getSorts().remove(removeField);
		getSorts().add(sortField);
*/		
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
		if (filterField.getFilter() == null || filterField.getFilter().isEmpty())
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
	public void setFilters(List<TableFilterField> filters) {
		this.filters = filters;
	}
	public List<TableSortField> getSorts() {
		if (sorts == null)
		{
			sorts = new ArrayList<TableSortField>();
		}
		return sorts;
	}
	public void setSorts(List<TableSortField> sorts) {
		this.sorts = sorts;
	}

	
	public PaginationDAOService<T> getPaginationDAOService() {
		return paginationDAOService;
	}

	public void setPaginationDAOService(PaginationDAOService<T> paginationDAOService) {
		this.paginationDAOService = paginationDAOService;
	}


}

