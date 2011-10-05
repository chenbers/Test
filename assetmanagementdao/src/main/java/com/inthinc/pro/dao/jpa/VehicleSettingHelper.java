package com.inthinc.pro.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.configurator.model.ProductType;
import com.inthinc.pro.configurator.model.VehicleSetting;
import com.inthinc.pro.domain.settings.ActualVehicleSetting;
import com.inthinc.pro.domain.settings.DesiredVehicleSetting;
import com.inthinc.pro.service.ActualVehicleSettingService;
import com.inthinc.pro.service.DesiredVehicleSettingService;

@Component
public class VehicleSettingHelper {
    @Autowired
	private ActualVehicleSettingService actualVehicleSettingService;
    @Autowired
	private DesiredVehicleSettingService desiredVehicleSettingService;

	public VehicleSetting getVehicleSettings(Integer vehicleID) {
		List<ActualVehicleSetting> actualVehicleSettings = actualVehicleSettingService.getActualVehicleSettingsForVehicle(vehicleID);
		List<DesiredVehicleSetting> desiredVehicleSettings = desiredVehicleSettingService.getDesiredVehicleSettingsForVehicle(vehicleID);
		VehicleSetting vehicleSetting = createVehicleSetting(vehicleID,actualVehicleSettings,desiredVehicleSettings);
		return vehicleSetting;
	}
	public List<VehicleSetting> getVehicleSettingsByGroupIDDeep(List<Integer> vehicleIDsByGroupIDDeep) {
		List<VehicleSetting> vehicleSettingsByGroupIDDeep = new ArrayList<VehicleSetting>();
		for(Integer vehicleID : vehicleIDsByGroupIDDeep){
			VehicleSetting vehicleSetting = getVehicleSettings(vehicleID);
			vehicleSettingsByGroupIDDeep.add(vehicleSetting);
		}
		return vehicleSettingsByGroupIDDeep;
	}
	public List<DesiredVehicleSetting> createDesiredVehicleSettings(Integer vehicleID, Integer deviceID,
			Map<Integer, String> setMap, Integer userID, String reason) {
		List<DesiredVehicleSetting> desiredVehicleSettings = getDesiredVehicleSettings(null,vehicleID, deviceID,setMap, userID,reason);
		List<DesiredVehicleSetting> createdSettings = createSettings(desiredVehicleSettings);
		return createdSettings;
	}
	public List<DesiredVehicleSetting> updateDesiredVehicleSettings(Integer desiredVehicleSettingID, Integer vehicleID, Integer deviceID, Map<Integer, String> setMap, Integer userID, String reason) {
		List<DesiredVehicleSetting> desiredVehicleSettings = getDesiredVehicleSettings(desiredVehicleSettingID,vehicleID, deviceID, setMap, userID,reason);
		List<DesiredVehicleSetting> updatedSettings = updateSettings(desiredVehicleSettings);
		return updatedSettings;
	}
	public List<DesiredVehicleSetting> getDesiredVehicleSettings(Integer desiredVehicleSettingID, Integer vehicleID, Integer deviceID,
			Map<Integer, String> setMap, Integer userID, String reason) {
		Date date = new Date();
		List<DesiredVehicleSetting> desiredVehicleSettings = new ArrayList<DesiredVehicleSetting>();
		for (Integer settingID : setMap.keySet()){
			DesiredVehicleSetting dvs = new DesiredVehicleSetting();
			dvs.setDesiredVehicleSettingID(desiredVehicleSettingID);
			dvs.setDeviceID(deviceID);
			dvs.setModified(date);
			dvs.setReason(reason);
			dvs.setSettingID(settingID);
			dvs.setUserID(userID);
			dvs.setValue(setMap.get(settingID));
			dvs.setVehicleID(vehicleID);
			
			desiredVehicleSettings.add(dvs);
		}
		return desiredVehicleSettings;
	}
	private VehicleSetting createVehicleSetting(Integer vehicleID, List<ActualVehicleSetting> actualVehicleSettings,List<DesiredVehicleSetting> desiredVehicleSettings){
		VehicleSetting vehicleSetting =  new VehicleSetting();
		vehicleSetting.setVehicleID(vehicleID);
		if (!actualVehicleSettings.isEmpty()){
			vehicleSetting.setDeviceID(actualVehicleSettings.get(0).getDeviceID());
			vehicleSetting.setProductType(actualVehicleSettings.get(0).getSettingID()<1000?ProductType.TIWIPRO_R74:ProductType.WAYSMART);
		}
		else{
			vehicleSetting.setProductType(ProductType.UNKNOWN);
		}
		vehicleSetting.setDesired(getDesiredSettingsMap(desiredVehicleSettings));
		vehicleSetting.setActual(getActualSettingsMap(actualVehicleSettings));
		return vehicleSetting;
	}
	private Map<Integer,String> getDesiredSettingsMap(List<DesiredVehicleSetting> desiredVehicleSettings){
		Map<Integer,String> settingsMap = new HashMap<Integer,String>();
		for (DesiredVehicleSetting dvs : desiredVehicleSettings){
			settingsMap.put(dvs.getSettingID(), dvs.getValue());
		}
		return settingsMap;
	}
	private Map<Integer,String> getActualSettingsMap(List<ActualVehicleSetting> actualVehicleSettings){
		Map<Integer,String> settingsMap = new HashMap<Integer,String>();
		for (ActualVehicleSetting avs : actualVehicleSettings){
			settingsMap.put(avs.getSettingID(), avs.getValue());
		}
		return settingsMap;
	}
	private List<DesiredVehicleSetting>  createSettings(List<DesiredVehicleSetting> desiredVehicleSettings){
		List<DesiredVehicleSetting> createdSettings = new ArrayList<DesiredVehicleSetting>();
		for(DesiredVehicleSetting dvs : desiredVehicleSettings){
			DesiredVehicleSetting createddvs = desiredVehicleSettingService.createDesiredVehicleSetting(dvs);
			if (createddvs != null){
				createdSettings.add(createddvs);
			}
		}
		return createdSettings;
	}
	private List<DesiredVehicleSetting> updateSettings(List<DesiredVehicleSetting> desiredVehicleSettings){
		List<DesiredVehicleSetting> updatedSettings = new ArrayList<DesiredVehicleSetting>();
		for(DesiredVehicleSetting dvs : desiredVehicleSettings){
			DesiredVehicleSetting updateddvs = desiredVehicleSettingService.updateDesiredVehicleSetting(dvs);
			if (updateddvs != null){
				updatedSettings.add(updateddvs);
			}
		}
		return updatedSettings;
	}
	public Map<Integer,DesiredVehicleSetting> findDesiredVehicleSettings(Integer vehicleID){
		List<DesiredVehicleSetting> desiredVehicleSettings = desiredVehicleSettingService.getDesiredVehicleSettingsForVehicle(vehicleID);
		Map<Integer,DesiredVehicleSetting> desiredVehicleSettingsMap = new HashMap<Integer,DesiredVehicleSetting>();
		for(DesiredVehicleSetting dvs : desiredVehicleSettings){
			desiredVehicleSettingsMap.put(dvs.getSettingID(), dvs);
		}
		return desiredVehicleSettingsMap;
	}

	public void setActualVehicleSettingService(
			ActualVehicleSettingService actualVehicleSettingService) {
		this.actualVehicleSettingService = actualVehicleSettingService;
	}

	public void setDesiredVehicleSettingService(
			DesiredVehicleSettingService desiredVehicleSettingService) {
		this.desiredVehicleSettingService = desiredVehicleSettingService;
	}

}
