package com.inthinc.pro.table;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.table.model.PaginationTableDataModel;
import com.inthinc.pro.table.model.provider.BasePaginationTableDataProvider;


// T is the data item for one table row
// class holds page information about the table (current page info, total Pages, rows Per Page)
// and model

public class BasePaginationTable<T> {

	protected static final Logger logger        = LogManager.getLogger(BasePaginationTable.class);
			
	private PaginationTableDataModel<T> model;
	private PageData pageData;

	public PageData getPageData() {
		return pageData;
	}
	public void setPage(PageData pageData) {
		this.pageData = pageData;
	}
	public void initModel(BasePaginationTableDataProvider<T> dataProvider)
	{
		System.out.println("initModel");
        model = new PaginationTableDataModel<T>(dataProvider);
        pageData = new PageData();
        pageData.initPage(model.getRowCount());
        model.setPageData(pageData);
	}
	public PaginationTableDataModel<T> getModel() {
		return model;
	}
	public void setModel(PaginationTableDataModel<T> model) {
		this.model = model;
	}

    public void scrollerListener(DataScrollerEvent se)
    {
    	Integer pageNum = se.getPage();
    	System.out.println("#### scrollerListener: " + pageNum);
    	model.resetPage();
    	pageData.initPage(pageNum, model.getRowCount());
    }
	
}
