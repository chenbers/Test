package com.inthinc.pro.backing.paging;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import org.apache.log4j.Logger;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.table.BasePaginationTable;
import com.inthinc.pro.table.columns.ColumnsChangedListener;
import com.inthinc.pro.table.model.provider.AdminPaginationTableDataProvider;

import java.util.Date;

public abstract class BasePagingAdminBean<T> extends BaseBean implements ColumnsChangedListener {
    private static final long serialVersionUID = 5503973069975512109L;
    private AdminPaginationTableDataProvider<T> tableDataProvider;
    private BasePaginationTable<T> table;
    private ReportRenderer reportRenderer;
    private ReportCriteriaService reportCriteriaService;
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(BasePagingReportBean.class);

    public void init() {
        table = new BasePaginationTable<T>();
        tableDataProvider.setSort(getDefaultSort());
        getTable().initModel(tableDataProvider);
    }

    public abstract TableSortField getDefaultSort();

    public BasePaginationTable<T> getTable() {
        return table;
    }

    public void setTable(BasePaginationTable<T> table) {
        this.table = table;
    }

    public void allAction() {
        table.reset();
    }

    public void exportReportToExcel()
    {
        getReportRenderer().exportReportToExcel(buildReportCriteria(), getFacesContext());
    }

    protected ReportCriteria buildReportCriteria()
    {
        ReportCriteria reportCriteria = getReportCriteria();
        reportCriteria.setDuration(Duration.TWELVE);
        reportCriteria.setReportDate(new Date(), getUser().getPerson().getTimeZone());
        reportCriteria.setUseMetric(getMeasurementType() == MeasurementType.METRIC);
        reportCriteria.setMeasurementType(getMeasurementType());
        reportCriteria.setFuelEfficiencyType(getFuelEfficiencyType());

        return reportCriteria;
    }

    protected abstract ReportCriteria getReportCriteria();

    public AdminPaginationTableDataProvider<T> getTableDataProvider() {
        return tableDataProvider;
    }

    public void setTableDataProvider(AdminPaginationTableDataProvider<T> tableDataProvider) {
        this.tableDataProvider = tableDataProvider;
    }

    @Override
    public void columnChangedHandler() {
        table.resetSortsAndFilters();
        tableDataProvider.setFilters(null);
        tableDataProvider.setSort(null);
    }

    public ReportRenderer getReportRenderer() {
        return reportRenderer;
    }

    public void setReportRenderer(ReportRenderer reportRenderer) {
        this.reportRenderer = reportRenderer;
    }

    public ReportCriteriaService getReportCriteriaService() {
        return reportCriteriaService;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService) {
        this.reportCriteriaService = reportCriteriaService;
    }
}
