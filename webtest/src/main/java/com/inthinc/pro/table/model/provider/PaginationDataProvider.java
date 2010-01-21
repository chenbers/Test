package com.inthinc.pro.table.model.provider;

import org.richfaces.model.DataProvider;

import com.inthinc.pro.table.dao.model.TableFilterField;
import com.inthinc.pro.table.dao.model.TableSortField;


public interface PaginationDataProvider<T> extends DataProvider<T> {

	public void addSortField(TableSortField sortField);
	public void addFilterField(TableFilterField sortField);


}