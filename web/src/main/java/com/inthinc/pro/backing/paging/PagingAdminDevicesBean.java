package com.inthinc.pro.backing.paging;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.backing.DevicesBean;
import com.inthinc.pro.backing.paging.filters.DeviceStatusFilter;
import com.inthinc.pro.backing.paging.filters.ProductTypeFilter;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;

@KeepAlive
public class PagingAdminDevicesBean extends BasePagingAdminBean<DevicesBean.DeviceView> {
    private static final long serialVersionUID = 1239753414611862228L;
    private DeviceStatusFilter deviceStatusFilter;
    private ProductTypeFilter productTypeFilter;
    private DevicesBean devicesBean;

    @Override
    public void init() {
        super.init();
        deviceStatusFilter = new DeviceStatusFilter();
        productTypeFilter = new ProductTypeFilter();
        devicesBean.setColumnsChangedListener(this);
    }

    @Override
    public TableSortField getDefaultSort() {
        return new TableSortField(SortOrder.ASCENDING, "name");
    }

    public DeviceStatusFilter getDeviceStatusFilter() {
        return deviceStatusFilter;
    }

    public void setDeviceStatusFilter(DeviceStatusFilter deviceStatusFilter) {
        this.deviceStatusFilter = deviceStatusFilter;
    }

    public ProductTypeFilter getProductTypeFilter() {
        return productTypeFilter;
    }

    public void setProductTypeFilter(ProductTypeFilter productTypeFilter) {
        this.productTypeFilter = productTypeFilter;
    }

    public DevicesBean getDevicesBean() {
        return devicesBean;
    }

    public void setDevicesBean(DevicesBean devicesBean) {
        this.devicesBean = devicesBean;
    }
}
