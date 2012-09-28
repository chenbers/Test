package com.inthinc.pro.table.model.provider;

import java.util.List;

public abstract class AdminPaginationTableDataProvider<T> extends GenericPaginationTableDataProvider<T> {
    /**
     * 
     */
    private static final long serialVersionUID = 3929372906877273665L;

    public abstract List<T> getItemsByRange(int firstRow, int endRow);

    public abstract int getRowCount();

}
