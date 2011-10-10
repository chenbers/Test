package com.inthinc.pro.dao.jpa;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.inthinc.pro.configurator.model.VehicleSetting;
import com.inthinc.pro.domain.Device;
import com.inthinc.pro.domain.settings.DesiredVehicleSetting;
import com.inthinc.pro.domain.settings.VehicleSettingHistory;
import com.inthinc.pro.service.DeviceService;
import com.inthinc.pro.service.VehicleService;
import com.inthinc.pro.service.VehicleSettingHistoryService;

public class ConfiguratorJPADAO{

//    private static final Logger logger = Logger.getLogger(ConfiguratorJPADAO.class);

    @Autowired
	private VehicleService vehicleService;
    @Autowired
	private DeviceService deviceService;
    @Autowired
    private ForwardCommandHelper forwardCommandHelper;
    @Autowired
    private VehicleSettingHelper vehicleSettingHelper;
    @Autowired
	private VehicleSettingHistoryService vehicleSettingHistoryService;


	public VehicleSetting getVehicleSettings(Integer vehicleID) {
		VehicleSetting vehicleSetting = vehicleSettingHelper.getVehicleSettings(vehicleID);
		return vehicleSetting;
	}
	public List<VehicleSetting> getVehicleSettingsByGroupIDDeep(Integer groupID) {
		List<Integer> vehicleIDsByGroupIDDeep = getVehicleIDsByGroupIDDeep(groupID);
		List<VehicleSetting> vehicleSettingsByGroupIDDeep = vehicleSettingHelper.getVehicleSettingsByGroupIDDeep(vehicleIDsByGroupIDDeep);
		return vehicleSettingsByGroupIDDeep;
	}

	public List<Integer> getVehicleIDsByGroupIDDeep(Integer groupID) {
		List<Integer> vehicleIDsByGroupIDDeep = vehicleService.getVehicleIDsByGroupIDDeep(groupID);
		return vehicleIDsByGroupIDDeep;
	}
	public void setVehicleSettings(Integer vehicleID, Map<Integer, String> setMap, Integer userID, String reason) {
		Device device = getDevice(vehicleID);
		List<DesiredVehicleSetting> createdSettings = vehicleSettingHelper.createDesiredVehicleSettings(vehicleID, device.getDeviceID(),setMap, userID,reason);
		if (createdSettings.size() > 0) queueForwardCommands(device, setMap);
	}

	public void updateVehicleSettings(Integer desiredVehicleSettingID, Integer vehicleID, 
									  Map<Integer, String> setMap, Integer userID, String reason) {
		Device device = getDevice(vehicleID);
		List<DesiredVehicleSetting> updatedSettings = vehicleSettingHelper.updateDesiredVehicleSettings(desiredVehicleSettingID,vehicleID, device.getDeviceID(),setMap, userID,reason);
		if (updatedSettings.size() > 0) queueForwardCommands(device, setMap);
	}

	private void queueForwardCommands(Device device, Map<Integer, String> setMap){
		forwardCommandHelper.queueForwardCommands(device, setMap);
	}


	public List<VehicleSettingHistory> getVehicleSettingsHistory(Integer vehicleID, Date startTime, Date endTime) {
		List<VehicleSettingHistory> vehicleSettingHistories = vehicleSettingHistoryService.getVehicleSettingHistoryForVehicle(vehicleID, startTime, endTime);
		
		return vehicleSettingHistories;
	}

	public Map<Integer,DesiredVehicleSetting> findDesiredVehicleSettings(Integer vehicleID){
		Map<Integer,DesiredVehicleSetting> desiredVehicleSettingsMap = vehicleSettingHelper.findDesiredVehicleSettings(vehicleID);
		return desiredVehicleSettingsMap;
	}

	private Device getDevice(Integer vehicleID){
		
		Device device = deviceService.getDeviceByVehicleID(vehicleID);
		return device;
	}


	public Integer deleteByID(Integer id) {
		// not implemented
		return null;
	}

	public void setVehicleService(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public void updateVehicleSettings(Integer vehicleID,
			Map<Integer, String> setMap, Integer userID, String reason) {
		
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public void setForwardCommandHelper(ForwardCommandHelper forwardCommandHelper) {
		this.forwardCommandHelper = forwardCommandHelper;
	}

	public void setVehicleSettingHelper(VehicleSettingHelper vehicleSettingHelper) {
		this.vehicleSettingHelper = vehicleSettingHelper;
	}

}
