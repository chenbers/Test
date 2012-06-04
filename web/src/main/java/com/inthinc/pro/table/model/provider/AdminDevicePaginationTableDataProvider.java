package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.backing.DevicesBean;
import com.inthinc.pro.backing.DevicesBean.DeviceView;
import com.inthinc.pro.dao.jdbc.AdminDeviceJDBCDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.pagination.PageParams;

public class AdminDevicePaginationTableDataProvider extends AdminPaginationTableDataProvider<DevicesBean.DeviceView> {
    private static final long serialVersionUID = 7174258339086174126L;
    private DevicesBean devicesBean;
    private AdminDeviceJDBCDAO adminDeviceJDBCDAO;

    @Override
    public List<DevicesBean.DeviceView> getItemsByRange(int firstRow, int endRow) {
        PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
        List<Device> deviceList = adminDeviceJDBCDAO.getDevices(devicesBean.getAccountID(), pageParams);
        List<DeviceView> items = new ArrayList<DeviceView>();
        for (final Device device : deviceList) {
            items.add(devicesBean.createDeviceView(device));
        }
        devicesBean.setItems(items);
        return items;
    }

    @Override
    public int getRowCount() {
        return adminDeviceJDBCDAO.getCount(devicesBean.getAccountID(), getFilters());
    }

    public DevicesBean getDevicesBean() {
        return devicesBean;
    }

    public void setDevicesBean(DevicesBean devicesBean) {
        this.devicesBean = devicesBean;
    }

    public AdminDeviceJDBCDAO getAdminDeviceJDBCDAO() {
        return adminDeviceJDBCDAO;
    }

    public void setAdminDeviceJDBCDAO(AdminDeviceJDBCDAO adminDeviceJDBCDAO) {
        this.adminDeviceJDBCDAO = adminDeviceJDBCDAO;
    }
}
