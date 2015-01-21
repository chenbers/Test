package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.ConfiguratorMapper;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.SensitivitySliderValues;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.model.configurator.VehicleSettingHistory;

public class ConfiguratorHessianDAO extends GenericHessianDAO<DeviceSettingDefinition, Integer> implements ConfiguratorDAO {

	private static final long serialVersionUID = 1L;

    public ConfiguratorHessianDAO() {
        super();
        super.setMapper(new ConfiguratorMapper());
    }

    @Override
    public List<DeviceSettingDefinition> getDeviceSettingDefinitions() {
 
        return getMapper().convertToModelObject(getSiloService().getSettingDefs(), DeviceSettingDefinition.class);
    }

    @Override
    public List<VehicleSetting> getVehicleSettingsByGroupIDDeep(Integer groupID) {
        try {
            
            return getMapper().convertToModelObject(getSiloService().getVehicleSettingsByGroupIDDeep(groupID), VehicleSetting.class);
        } 
        catch (EmptyResultSetException e) {
            
            return Collections.emptyList();
        }
    }

    @Override
    public void setVehicleSettings(Integer vehicleID, Map<Integer, String> setMap, Integer userID, String reason) {
        getSiloService().setVehicleSettings(vehicleID, setMap, userID, reason);
    }

    @Override
    public  void updateVehicleSettings(Integer vehicleID, Map<Integer, String> setMap, Integer userID, String reason) {

        getSiloService().updateVehicleSettings(vehicleID, setMap, userID, reason);
    }

    @Override
    public List<VehicleSettingHistory> getVehicleSettingsHistory(Integer vehicleID, Date startTime, Date endTime) {
    	try{
    		return getMapper().convertToModelObject(getSiloService().getVehicleSettingsHistory(vehicleID, DateUtil.convertDateToSeconds(startTime), DateUtil.convertDateToSeconds(endTime)),VehicleSettingHistory.class);

    	} catch (EmptyResultSetException e) {
	        return Collections.emptyList();
	    }

    }

    @Override
    public VehicleSetting getVehicleSettings(Integer vehicleID) {

        try{
            return getMapper().convertToModelObject(getSiloService().getVehicleSettings(vehicleID), VehicleSetting.class);
            
        } catch (EmptyResultSetException e) {
            
            return null;
        }

    }

    @Override
    public Map<Integer, VehicleSetting> getVehicleSettingsForAll(List<Integer> vehicleIDs) {
        Map<Integer, VehicleSetting> vehicleSettingMap = new HashMap<Integer, VehicleSetting>(vehicleIDs.size());
        for (Integer vehicleID : vehicleIDs){
            vehicleSettingMap.put(vehicleID, getMapper().convertToModelObject(getSiloService().getVehicleSettings(vehicleID), VehicleSetting.class));
        }

        return vehicleSettingMap;
    }

    @Override
    public List<Integer> getVehicleIDsByGroupIDDeep(Integer groupID) {
        try {
            List<Vehicle> vehicles = getMapper().convertToModelObject(getSiloService().getVehiclesByGroupIDDeep(groupID), Vehicle.class);
            List<Integer> vehicleIDs = new ArrayList<Integer>();
            for(Vehicle vehicle:vehicles){
                
                vehicleIDs.add(vehicle.getVehicleID());
            }
            return vehicleIDs;
            
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
	public List<SensitivitySliderValues> getSensitivitySliderValues() {

        try{
            return getMapper().convertToModelObject(getSiloService().getSensitivitySliderValues(), SensitivitySliderValues.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }

	}


}
