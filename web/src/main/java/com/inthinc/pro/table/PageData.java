package com.inthinc.pro.table;


public class PageData {
	
	private static final Integer DEFAULT_ROWS_PER_PAGE = 15;
	private Integer currentPage;
	private Integer rowsPerPage;
	private Integer numPages;
	private Integer pageStartRow;
	private Integer pageEndRow;
	private Integer totalRows;

	public void initPage(int rowCount) {
        currentPage = (rowCount > 0) ? 1 : 0;
        numPages = (rowCount + getRowsPerPage()-1) / getRowsPerPage();
        pageStartRow = (rowCount > 0) ? 1 : 0;
        pageEndRow = (getRowsPerPage() > rowCount) ? rowCount : getRowsPerPage();
        setTotalRows(rowCount);
	}
	public void initPage(int page, int rowCount) {
        currentPage = page;
        pageStartRow = (page - 1) * getRowsPerPage() + 1;
	    pageEndRow = page * getRowsPerPage();
	
	    if (pageEndRow > rowCount)  {
	      	pageEndRow = rowCount;
	    }
        setTotalRows(rowCount);
	}
	public void updatePage(int rowCount) {
        numPages = (rowCount + getRowsPerPage()-1) / getRowsPerPage();
		currentPage = (currentPage == null) ? numPages+1 : currentPage;
        if (currentPage > numPages || currentPage == 0) {
        	currentPage = (rowCount > 0) ? 1 : 0;
            pageStartRow = (rowCount > 0) ? 1 : 0;
        }
	    pageEndRow = currentPage * getRowsPerPage();
	    if (pageEndRow > rowCount)  {
	      	pageEndRow = rowCount;
	    }
        setTotalRows(rowCount);
	}
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getNumPages() {
		return numPages;
	}

	public void setNumPages(Integer numPages) {
		this.numPages = numPages;
	}
	public Integer getRowsPerPage() {
		if (rowsPerPage == null)
			rowsPerPage = DEFAULT_ROWS_PER_PAGE;
		return rowsPerPage;
	}
	public void setRowsPerPage(Integer rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	
	public Integer getPageStartRow() {
		return pageStartRow;
	}

	public void setPageStartRow(Integer pageStartRow) {
		this.pageStartRow = pageStartRow;
	}

	public Integer getPageEndRow() {
		return pageEndRow;
	}

	public void setPageEndRow(Integer pageEndRow) {
		this.pageEndRow = pageEndRow;
	}

	public Integer getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
}
