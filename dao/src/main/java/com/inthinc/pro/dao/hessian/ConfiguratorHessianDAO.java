package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.ConfiguratorMapper;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.SensitivitySliderValues;
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

//    @Override
//    public Map<SensitivityType, SensitivityForwardCommandMapping> getSensitivityMaps()
//    {
//        Map<SensitivityType, SensitivityForwardCommandMapping> returnMap = new HashMap<SensitivityType, SensitivityForwardCommandMapping>();
//        List<SensitivityForwardCommandMapping> list = getMapper().convertToModelObject(getSiloService().getSensitivityMaps(), SensitivityForwardCommandMapping.class);
//        for (SensitivityForwardCommandMapping s : list)
//        {
//            returnMap.put(s.getSetting(), s);
//        }
//        return returnMap;
//    }
    @Override
	public List<SensitivitySliderValues> getSensitivitySliderValues() {

        try{
            return getMapper().convertToModelObject(getSiloService().getSensitivitySliderValues(), SensitivitySliderValues.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }

	}


}
