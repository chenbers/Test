package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.backing.VehiclesBean;
import com.inthinc.pro.backing.VehiclesBean.VehicleView;
import com.inthinc.pro.dao.jdbc.AdminVehicleJDBCDAO;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleIdentifiers;
import com.inthinc.pro.model.pagination.PageParams;

public class AdminVehiclePaginationTableDataProvider extends AdminPaginationTableDataProvider<VehiclesBean.VehicleView> {
    private static final long serialVersionUID = 7174258339086174126L;
    private VehiclesBean vehiclesBean;
    private AdminVehicleJDBCDAO adminVehicleJDBCDAO;

    @Override
    public List<VehiclesBean.VehicleView> getItemsByRange(int firstRow, int endRow) {
        PageParams pageParams = new PageParams(firstRow, endRow, getSort(), removeBlankFilters(getFilters()));
        List<Vehicle> vehicleList = adminVehicleJDBCDAO.getVehicles(vehiclesBean.getGroupIDList(), pageParams);
        vehiclesBean.setVehicleSettingManagers(vehiclesBean.getVehicleSettingsFactory().retrieveVehicleSettings(vehiclesBean.getVehicleSettingManagers(), vehicleList));
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
//        return adminVehicleJDBCDAO.getCount(vehiclesBean.getGroupIDList(), getFilters());
        
        List<VehicleIdentifiers> vehicleIdentifiersList = adminVehicleJDBCDAO.getFilteredVehicleIDs(vehiclesBean.getGroupIDList(), removeBlankFilters(getFilters()));
        vehiclesBean.initVehicleIdentifierList(vehicleIdentifiersList);
        return vehicleIdentifiersList.size();
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
