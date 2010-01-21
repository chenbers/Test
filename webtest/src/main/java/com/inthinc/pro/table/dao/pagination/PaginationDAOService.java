package com.inthinc.pro.table.dao.pagination;

import java.util.List;

import com.inthinc.pro.table.dao.model.TableFilterField;
import com.inthinc.pro.table.dao.model.TableSortField;




public interface PaginationDAOService<T> {
	
	public List<T> getItemsByRange(int firstRow, int lastRow, List<TableSortField> sort, List<TableFilterField> filter);
	public int countAll(List<TableFilterField> filter);

}
