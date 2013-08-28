package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.report.TrailerReportDAO;
import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.pagination.PageParams;

public class TrailerReportPaginationTableDataProvider extends ReportPaginationTableDataProvider<TrailerReportItem> {
    private static final long serialVersionUID = -8946950315292490529L;
    
    private TrailerReportDAO trailerReportDAO;
    
    public TrailerReportDAO getTrailerReportDAO() {
        return trailerReportDAO;
    }

    public void setTrailerReportDAO(TrailerReportDAO trailerReportDAO) {
        this.trailerReportDAO = trailerReportDAO;
    }

    @Override
    public List<TrailerReportItem> getItemsByRange(int firstRow, int endRow) {
        if (endRow < 0) {
            return new ArrayList<TrailerReportItem>();
        }
        PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
        List<TrailerReportItem> retVal = getTrailerReportDAO().getTrailerReportItemByGroupPaging(getGroupID(), pageParams);
        return retVal;
    }

    @Override
    public int getRowCount() {
        if (getGroupID() == null)
            return 0;
        int rowCount = getTrailerReportDAO().getTrailerReportCount(getGroupID(), getFilters());
        return rowCount;
    }
    
}
