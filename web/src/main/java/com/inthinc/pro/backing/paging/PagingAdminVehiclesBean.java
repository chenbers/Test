package com.inthinc.pro.backing.paging;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.backing.VehiclesBean;
import com.inthinc.pro.backing.paging.filters.DeviceStatusFilter;
import com.inthinc.pro.backing.paging.filters.ProductTypeFilter;
import com.inthinc.pro.backing.paging.filters.VehicleTypeFilter;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;

@KeepAlive
public class PagingAdminVehiclesBean extends BasePagingAdminBean<VehiclesBean.VehicleView> {
    private static final long serialVersionUID = 1239753414611862228L;
    private DeviceStatusFilter deviceStatusFilter;
    private ProductTypeFilter productTypeFilter;
    private VehicleTypeFilter vehicleTypeFilter;
    private VehiclesBean vehiclesBean;

    public VehiclesBean getVehiclesBean() {
        return vehiclesBean;
    }

    public void setVehiclesBean(VehiclesBean vehiclesBean) {
        this.vehiclesBean = vehiclesBean;
    }

    @Override
    public void init() {
        super.init();
        deviceStatusFilter = new DeviceStatusFilter();
        productTypeFilter = new ProductTypeFilter();
        vehicleTypeFilter = new VehicleTypeFilter();
        vehiclesBean.setColumnsChangedListener(this);
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

    public VehicleTypeFilter getVehicleTypeFilter() {
        return vehicleTypeFilter;
    }

    public void setVehicleTypeFilter(VehicleTypeFilter vehicleTypeFilter) {
        this.vehicleTypeFilter = vehicleTypeFilter;
    }
}
