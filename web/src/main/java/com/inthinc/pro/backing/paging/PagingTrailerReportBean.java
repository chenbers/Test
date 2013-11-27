package com.inthinc.pro.backing.paging;

import java.util.List;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;

public class PagingTrailerReportBean extends BasePagingReportBean<TrailerReportItem>{
    private static final long serialVersionUID = 1L;

    @Override
    public TableSortField getDefaultSort() {
        return new TableSortField(SortOrder.ASCENDING, "trailerName");
    }

    @Override
    protected ReportCriteria getReportCriteria() {
        ReportCriteria reportCriteria =  getReportCriteriaService().getTrailerReportCriteria(getUser().getGroupID(), Duration.TWELVE, getLocale(), false);
      
      TableSortField originalSort = getTableDataProvider().getSort();
      List<TableFilterField> originalFilterList = getTableDataProvider().getFilters();
      
      Integer rowCount = getTableDataProvider().getRowCount();
      
      getTableDataProvider().getItemsByRange(0, rowCount);
      reportCriteria.setMainDataset(getTableDataProvider().getItemsByRange(0, rowCount));

      getTableDataProvider().setSort(originalSort);
      getTableDataProvider().setFilters(originalFilterList);
      
      return reportCriteria;
    }
    
}
