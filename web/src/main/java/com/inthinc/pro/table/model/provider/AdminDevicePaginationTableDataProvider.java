package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.backing.DevicesBean;
import com.inthinc.pro.backing.DevicesBean.DeviceView;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.pagination.PageParams;

public class AdminDevicePaginationTableDataProvider extends AdminPaginationTableDataProvider<DevicesBean.DeviceView>{

    private static final long serialVersionUID = 7174258339086174126L;

    private DevicesBean devicesBean;


    @Override
    public List<DevicesBean.DeviceView> getItemsByRange(int firstRow, int endRow) {
        
        PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
        List<Device> deviceList = getAdminDAO().getDevicePage(getGroupID(), pageParams);
        
        List<DeviceView> items = new ArrayList<DeviceView>();
        for (final Device device : deviceList) {
            items.add(devicesBean.createDeviceView(device));
        }
        
        devicesBean.setItems(items);

        return items;
    }

    @Override
    public int getRowCount() {
        if (getGroupID() == null)
            return 0;

        return getAdminDAO().getDeviceCount(getGroupID(), getFilters());

    }
    public DevicesBean getDevicesBean() {
        return devicesBean;
    }

    public void setDevicesBean(DevicesBean devicesBean) {
        this.devicesBean = devicesBean;
    }

}
