package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.backing.VehiclesBean;
import com.inthinc.pro.backing.VehiclesBean.VehicleView;
import com.inthinc.pro.dao.jdbc.AdminVehicleJDBCDAO;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.pagination.PageParams;

public class AdminVehiclePaginationTableDataProvider extends AdminPaginationTableDataProvider<VehiclesBean.VehicleView> {
    private static final long serialVersionUID = 7174258339086174126L;
    private VehiclesBean vehiclesBean;
    private AdminVehicleJDBCDAO adminVehicleJDBCDAO;

    @Override
    public List<VehiclesBean.VehicleView> getItemsByRange(int firstRow, int endRow) {
        PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
        List<Vehicle> vehicleList = adminVehicleJDBCDAO.getVehicles(vehiclesBean.getGroupIDList(), pageParams);
        // TODO: CJ NOT SURE ABOUT VEHICLE SETTINGS STUFF
        // it might be good to not do the group deep lookup, but instead just get the settings for list items (added method for this)
        // vehicleSettingManagers = vehicleSettingsFactory.retrieveVehicleSettings(getGroupID(), vehicleList);
        vehiclesBean.setVehicleSettingManagers(vehiclesBean.getVehicleSettingsFactory().retrieveVehicleSettings(vehicleList));
        List<VehicleView> items = new ArrayList<VehicleView>();
        for (final Vehicle vehicle : vehicleList) {
            VehicleView vehicleView = vehiclesBean.createVehicleView(vehicle);
            vehicleView.setEditableVehicleSettings(vehiclesBean.getVehicleSettingManagers().get(vehicle.getVehicleID()).associateSettings(vehicle.getVehicleID()));
            items.add(vehicleView);
        }
        vehiclesBean.setItems(items);
        return items;
    }

    @Override
    public int getRowCount() {
        return adminVehicleJDBCDAO.getCount(vehiclesBean.getGroupIDList(), getFilters());
    }

    public VehiclesBean getVehiclesBean() {
        return vehiclesBean;
    }

    public void setVehiclesBean(VehiclesBean vehiclesBean) {
        this.vehiclesBean = vehiclesBean;
    }

    public AdminVehicleJDBCDAO getAdminVehicleJDBCDAO() {
        return adminVehicleJDBCDAO;
    }

    public void setAdminVehicleJDBCDAO(AdminVehicleJDBCDAO adminVehicleJDBCDAO) {
        this.adminVehicleJDBCDAO = adminVehicleJDBCDAO;
    }
}
