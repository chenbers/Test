package com.inthinc.pro.backing.paging;

import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.TrailerEntryMethod;
import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.util.SelectItemUtil;

public class PagingTrailerReportBean extends BasePagingReportBean<TrailerReportItem>{
    private static final long serialVersionUID = 1L;
    private TrailerEntryMethod entryMethodFilter;
    private Integer entryMethodFilterID;
    

    @Override
    public TableSortField getDefaultSort() {
        return new TableSortField(SortOrder.ASCENDING, "name");
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
    public List<SelectItem> getEntryMethods(){
        List<SelectItem> result = SelectItemUtil.toList(TrailerEntryMethod.class, true);
        return result;
    }
    public TrailerEntryMethod getEntryMethodFilter(){
        System.out.println("getEntryMethodtatusFilter()");
        return entryMethodFilter;
    }
    public void setEntryMethodFilter(TrailerEntryMethod entryMethodFilter){
        System.out.println("setStatusFilter("+entryMethodFilter+")");
        this.entryMethodFilter = entryMethodFilter;
    }
    public Integer getEntryMethodFilterID(){
        return (entryMethodFilter!=null)?entryMethodFilter.getCode():null;
    }
    public void setEntryMethodFilterID(Integer entryMethodFilterID){
        this.entryMethodFilter = TrailerEntryMethod.valueOf(entryMethodFilterID);
    }
}
