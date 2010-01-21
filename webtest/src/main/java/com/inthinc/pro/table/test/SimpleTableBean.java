package com.inthinc.pro.table.test;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.richfaces.component.html.HtmlDatascroller;

import com.inthinc.pro.table.BasePaginationTable;
import com.inthinc.pro.table.dao.model.SimpleTableItem;
import com.inthinc.pro.table.model.PaginationTableDataModel;
import com.inthinc.pro.table.model.provider.SimplePaginationTableDataProvider;

public class SimpleTableBean /*extends BaseBean */{

	protected static final Logger logger = LogManager.getLogger(SimpleTableBean.class);
	
	private SimplePaginationTableDataProvider simplePaginationTableDataProvider;
	BasePaginationTable<SimpleTableItem> table;
	/*
	String strFilter;
	String intFilter;
	String floatFilter;

	
	public String getStrFilter() {
		return strFilter;
	}

	public void setStrFilter(String strFilter) {
		this.strFilter = strFilter;
	}

	public String getIntFilter() {
		return intFilter;
	}

	public void setIntFilter(String intFilter) {
		this.intFilter = intFilter;
	}

	public String getFloatFilter() {
		return floatFilter;
	}

	public void setFloatFilter(String floatFilter) {
		this.floatFilter = floatFilter;
	}
*/
	public SimpleTableBean()
	{
		logger.info("in SimpleTableBean constructor");
	}
	
	public void init()
	{
		PaginationTableDataModel<SimpleTableItem> model = new PaginationTableDataModel<SimpleTableItem>(simplePaginationTableDataProvider);
		
		table = new BasePaginationTable<SimpleTableItem>();
		table.setModel(model);
		table.initModel(simplePaginationTableDataProvider);
	}


	public BasePaginationTable<SimpleTableItem> getTable() {
		return table;
	}

	public void setTable(BasePaginationTable<SimpleTableItem> table) {
		this.table = table;
	}

	
	public SimplePaginationTableDataProvider getSimplePaginationTableDataProvider() {
		return simplePaginationTableDataProvider;
	}

	public void setSimplePaginationTableDataProvider(SimplePaginationTableDataProvider simplePaginationTableDataProvider) {
		this.simplePaginationTableDataProvider = simplePaginationTableDataProvider;
	}

	
}
