package com.inthinc.pro.table.model.provider;

import org.richfaces.model.DataProvider;

import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;


public interface PaginationDataProvider<T> extends DataProvider<T> {

	public void addSortField(TableSortField sortField);
	public void addFilterField(TableFilterField filterField);


}