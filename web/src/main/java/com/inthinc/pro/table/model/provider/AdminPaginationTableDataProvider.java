package com.inthinc.pro.table.model.provider;

import java.util.List;

import com.inthinc.pro.dao.AdminDAO;

public abstract class AdminPaginationTableDataProvider<T> extends GenericPaginationTableDataProvider<T> {
    /**
     * 
     */
    private static final long serialVersionUID = 3929372906877273665L;
    private AdminDAO                adminDAO;
    private Integer                 groupID;

    public abstract List<T> getItemsByRange(int firstRow, int endRow);
    public abstract int getRowCount();

    public AdminDAO getAdminDAO() {
        return adminDAO;
    }
    public void setAdminDAO(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }
}

