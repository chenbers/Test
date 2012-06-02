package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.VehiclesBean;
import com.inthinc.pro.backing.VehiclesBean.VehicleView;
import com.inthinc.pro.backing.model.VehicleSettingManager;
import com.inthinc.pro.backing.model.VehicleSettingsFactory;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.pagination.PageParams;

public class AdminVehiclePaginationTableDataProvider extends AdminPaginationTableDataProvider<VehiclesBean.VehicleView>{

    private static final long serialVersionUID = 7174258339086174126L;

    private VehiclesBean vehiclesBean;

    




    @Override
    public List<VehiclesBean.VehicleView> getItemsByRange(int firstRow, int endRow) {
        
        PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
        List<Vehicle> vehicleList = getAdminDAO().getVehiclePage(getGroupID(), pageParams);
        
        // TODO: CJ NOT SURE ABOUT VEHICLE SETTINGS STUFF   
        //  it might be good to not do the group deep lookup, but instead just get the settings for list items (added method for this)
//        vehicleSettingManagers = vehicleSettingsFactory.retrieveVehicleSettings(getGroupID(), vehicleList);
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
        if (getGroupID() == null)
            return 0;

        return getAdminDAO().getVehicleCount(getGroupID(), getFilters());

    }

    public VehiclesBean getVehiclesBean() {
        return vehiclesBean;
    }

    public void setVehiclesBean(VehiclesBean vehiclesBean) {
        this.vehiclesBean = vehiclesBean;
    }
}
