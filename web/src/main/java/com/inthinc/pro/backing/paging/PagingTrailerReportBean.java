package com.inthinc.pro.backing.paging;

import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.TrailerAssignedStatus;
import com.inthinc.pro.model.TrailerEntryMethod;
import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.table.model.provider.TrailerReportPaginationTableDataProvider;
import com.inthinc.pro.util.SelectItemUtil;

public class PagingTrailerReportBean extends BasePagingReportBean<TrailerReportItem> {
    private static final long serialVersionUID = 1L;
    private TrailerEntryMethod entryMethodFilter;
    private TrailerAssignedStatus assignedStatusFilter;

    @Override
    public void init() {
        super.init();
        ((TrailerReportPaginationTableDataProvider) getTableDataProvider()).setGroupIDList(getProUser().getGroupHierarchy().getGroupIDList(getUser().getGroupID()));
        ((TrailerReportPaginationTableDataProvider) getTableDataProvider()).setAcctID(getProUser().getUser().getPerson().getAccountID());
    }

    @Override
    public TableSortField getDefaultSort() {
        return new TableSortField(SortOrder.ASCENDING, "name");
    }

    @Override
    protected ReportCriteria getReportCriteria() {
        Integer groupID = getUser().getGroupID();
        ReportCriteria reportCriteria = getReportCriteriaService().getTrailerReportCriteria(groupID, getProUser().getGroupHierarchy().getGroupIDList(groupID), Duration.TWELVE, getLocale(), false);

        TableSortField originalSort = getTableDataProvider().getSort();
        List<TableFilterField> originalFilterList = getTableDataProvider().getFilters();

        Integer rowCount = getTableDataProvider().getRowCount();

        getTableDataProvider().getItemsByRange(0, rowCount);
        reportCriteria.setMainDataset(getTableDataProvider().getItemsByRange(0, rowCount));

        getTableDataProvider().setSort(originalSort);
        getTableDataProvider().setFilters(originalFilterList);

        return reportCriteria;
    }

    public List<SelectItem> getAssignedStatuses() {
        List<SelectItem> result = SelectItemUtil.toList(TrailerAssignedStatus.class, true);
        return result;
    }

    public List<SelectItem> getEntryMethods() {
        List<SelectItem> result = SelectItemUtil.toList(TrailerEntryMethod.class, true);
        return result;
    }

    public TrailerEntryMethod getEntryMethodFilter() {
        return entryMethodFilter;
    }

    public void setEntryMethodFilter(TrailerEntryMethod entryMethodFilter) {
        this.entryMethodFilter = entryMethodFilter;
    }

    public Integer getEntryMethodFilterID() {
        return (entryMethodFilter != null) ? entryMethodFilter.getCode() : null;
    }

    public void setEntryMethodFilterID(Integer entryMethodFilterID) {
        this.entryMethodFilter = TrailerEntryMethod.valueOf(entryMethodFilterID);
    }

    public TrailerAssignedStatus getAssignedStatusFilter() {
        return assignedStatusFilter;
    }

    public void setAssignedStatusFilter(TrailerAssignedStatus assignedStatusFilter) {
        this.assignedStatusFilter = assignedStatusFilter;
    }

    public Integer getAssignedStatusFilterID() {
        return (assignedStatusFilter != null) ? assignedStatusFilter.getCode() : null;
    }

    public void setAssignedStatusFilterID(Integer assignedStatusFilterID) {
        this.assignedStatusFilter = TrailerAssignedStatus.valueOf(assignedStatusFilterID);
    }
}
