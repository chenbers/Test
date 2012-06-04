package com.inthinc.pro.backing.paging;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.table.BasePaginationTable;
import com.inthinc.pro.table.columns.ColumnsChangedListener;
import com.inthinc.pro.table.model.provider.AdminPaginationTableDataProvider;

public abstract class BasePagingAdminBean<T> extends BaseBean implements ColumnsChangedListener {
    private static final long serialVersionUID = 5503973069975512109L;
    private AdminPaginationTableDataProvider<T> tableDataProvider;
    private BasePaginationTable<T> table;
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
}
