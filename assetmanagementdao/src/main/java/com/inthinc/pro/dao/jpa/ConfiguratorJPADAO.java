package com.inthinc.pro.dao.jpa;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.jdbc.FwdCmdSpoolWSIridiumJDBCDAO;
import com.inthinc.pro.domain.ActualVehicleSetting;
import com.inthinc.pro.domain.ConfiguratorForwardCommand;
import com.inthinc.pro.domain.DesiredVehicleSetting;
import com.inthinc.pro.domain.DeviceSettingDefinitionJPA;
import com.inthinc.pro.domain.ForwardCommandDef;
import com.inthinc.pro.domain.SensitivitySliderValue;
import com.inthinc.pro.domain.VehicleSettingHistoryJPA;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommandSpool;
import com.inthinc.pro.model.SensitivitySliderValues;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.model.configurator.VehicleSettingHistory;
import com.inthinc.pro.service.ActualVehicleSettingService;
import com.inthinc.pro.service.DesiredVehicleSettingService;
import com.inthinc.pro.service.DeviceSettingDefinitionService;
import com.inthinc.pro.service.SensitivitySliderSettingService;
import com.inthinc.pro.service.VehicleService;
import com.inthinc.pro.service.VehicleSettingHistoryService;

public class ConfiguratorJPADAO implements ConfiguratorDAO{

    private static final Logger logger = Logger.getLogger(ConfiguratorJPADAO.class);

    private static final Pattern choiceEliminator = Pattern.compile("\\[|\\]|\\(|\\)|\\{|\\}");

	private SensitivitySliderSettingService sensitivitySliderSettingService;
    private DeviceSettingDefinitionService deviceSettingDefinitionService;
	private VehicleSettingHistoryService vehicleSettingHistoryService;
	private ActualVehicleSettingService actualVehicleSettingService;
	private DesiredVehicleSettingService desiredVehicleSettingService;
	private VehicleService vehicleService;
	private FwdCmdSpoolWSIridiumJDBCDAO fwdCmdSpoolWSIridiumJDBCDAO;
	

	@Override
	public List<DeviceSettingDefinition> getDeviceSettingDefinitions() {
		List<DeviceSettingDefinitionJPA> deviceSettingDefinitionJPAs = deviceSettingDefinitionService.getDeviceSettingDefinitions();
		List<DeviceSettingDefinition> deviceSettingDefinitions = convertDeviceSettingDefinitions(deviceSettingDefinitionJPAs);
		return deviceSettingDefinitions;
	}

	@Override
	public VehicleSetting getVehicleSettings(Integer vehicleID) {
		List<ActualVehicleSetting> actualVehicleSettings = actualVehicleSettingService.getActualVehicleSettingsForVehicle(vehicleID);
		List<DesiredVehicleSetting> desiredVehicleSettings = desiredVehicleSettingService.getDesiredVehicleSettingsForVehicle(vehicleID);
		VehicleSetting vehicleSetting = createVehicleSetting(vehicleID,actualVehicleSettings,desiredVehicleSettings);
		return vehicleSetting;
	}
	@Override
	public List<VehicleSetting> getVehicleSettingsByGroupIDDeep(Integer groupID) {
		List<Integer> vehicleIDsByGroupIDDeep =getVehicleIDsByGroupIDDeep(groupID);
		List<VehicleSetting> vehicleSettingsByGroupIDDeep = new ArrayList<VehicleSetting>();
		for(Integer vehicleID : vehicleIDsByGroupIDDeep){
			VehicleSetting vehicleSetting = getVehicleSettings(vehicleID);
			vehicleSettingsByGroupIDDeep.add(vehicleSetting);
		}
		return vehicleSettingsByGroupIDDeep;
	}

	@Override
	public List<Integer> getVehicleIDsByGroupIDDeep(Integer groupID) {
		List<Integer> vehicleIDsByGroupIDDeep = vehicleService.getVehicleIDsByGroupIDDeep(groupID);
		return vehicleIDsByGroupIDDeep;
	}
	@Override
	public void setVehicleSettings(Integer vehicleID,
			Map<Integer, String> setMap, Integer userID, String reason) {
		Device device = getDevice(vehicleID);
		List<DesiredVehicleSetting> desiredVehicleSettings = getDesiredVehicleSettings(null,vehicleID, device.getDeviceID(),setMap, userID,reason);
		setSettings(desiredVehicleSettings);
		queueForwardCommands(device, setMap);
		
	}

	@Override
	public void updateVehicleSettings(Integer desiredVehicleSettingID, Integer vehicleID, Map<Integer, String> setMap, Integer userID, String reason) {
		Device device = getDevice(vehicleID);
		List<DesiredVehicleSetting> desiredVehicleSettings = getDesiredVehicleSettings(desiredVehicleSettingID,vehicleID, device.getDeviceID(),setMap, userID,reason);
		updateSettings(desiredVehicleSettings);
		queueForwardCommands(device, setMap);
	}

	@Override
	public List<SensitivitySliderValues> getSensitivitySliderValues() {
		List<SensitivitySliderValue> sensitivitySliderValues = sensitivitySliderSettingService.getSensitivitySliderSettings();
		//Testing
		for(SensitivitySliderValue sensitivitySliderValue : sensitivitySliderValues){
			System.out.println(sensitivitySliderValue.toString());
		}
		List<SensitivitySliderValues> convertedSensitivitySliderValues= convertSensitivitySliderValues(sensitivitySliderValues);
		return convertedSensitivitySliderValues;
	}
	public Map<Integer,DesiredVehicleSetting> findDesiredVehicleSettings(Integer vehicleID){
		List<DesiredVehicleSetting> desiredVehicleSettings = desiredVehicleSettingService.getDesiredVehicleSettingsForVehicle(vehicleID);
		Map<Integer,DesiredVehicleSetting> desiredVehicleSettingsMap = new HashMap<Integer,DesiredVehicleSetting>();
		for(DesiredVehicleSetting dvs : desiredVehicleSettings){
			desiredVehicleSettingsMap.put(dvs.getSettingID(), dvs);
		}
		return desiredVehicleSettingsMap;
	}
	
	private List<SensitivitySliderValues> convertSensitivitySliderValues(List<SensitivitySliderValue> sensitivitySliderValues){
		List<SensitivitySliderValues> convertedSensitivitySliderValues = new ArrayList<SensitivitySliderValues>();
		for(SensitivitySliderValue sensitivitySliderValue : sensitivitySliderValues){
			SensitivitySliderValues convertedSensitivitySliderValue = convertSensitivitySliderValue(sensitivitySliderValue);
			convertedSensitivitySliderValues.add(convertedSensitivitySliderValue);
		}
		return convertedSensitivitySliderValues;
	}
	private SensitivitySliderValues convertSensitivitySliderValue(SensitivitySliderValue sensitivitySliderValue){
		SensitivitySliderValues sensitivitySliderValues = new SensitivitySliderValues();
		
		sensitivitySliderValues.setDefaultValueIndex(sensitivitySliderValue.getDefaultValueIndex());
		String[] ignoreProperties={"forwardCommandName","productType","values"};
		BeanUtils.copyProperties(sensitivitySliderValue,sensitivitySliderValues,ignoreProperties);
		sensitivitySliderValues.setForwardCommandName(null);
		sensitivitySliderValues.setProductType(convertProductType(sensitivitySliderValue.getProductType()));
		sensitivitySliderValues.setValues(getSliderValues(sensitivitySliderValue));
		return sensitivitySliderValues;
	}
	private ProductType convertProductType(Integer productType){
		return ProductType.getProductTypeFromVersion(productType);
	}
	private List<String> getSliderValues(SensitivitySliderValue sensitivitySliderValue){
		List<String> sliderValues = new ArrayList<String>();
		sliderValues.add(sensitivitySliderValue.getV0());
		sliderValues.add(sensitivitySliderValue.getV1());
		sliderValues.add(sensitivitySliderValue.getV2());
		sliderValues.add(sensitivitySliderValue.getV3());
		sliderValues.add(sensitivitySliderValue.getV4());
		sliderValues.add(sensitivitySliderValue.getV5());
		sliderValues.add(sensitivitySliderValue.getV6());
		sliderValues.add(sensitivitySliderValue.getV7());
		sliderValues.add(sensitivitySliderValue.getV8());
		sliderValues.add(sensitivitySliderValue.getV9());
		if(sensitivitySliderValue.getV10() != null)
			sliderValues.add(sensitivitySliderValue.getV10());
		if(sensitivitySliderValue.getV11() != null)
			sliderValues.add(sensitivitySliderValue.getV11());
		if(sensitivitySliderValue.getV12() != null)
			sliderValues.add(sensitivitySliderValue.getV12());
		if(sensitivitySliderValue.getV13() != null)
			sliderValues.add(sensitivitySliderValue.getV13());
		if(sensitivitySliderValue.getV14() != null)
			sliderValues.add(sensitivitySliderValue.getV14());
		if(sensitivitySliderValue.getV15() != null)
			sliderValues.add(sensitivitySliderValue.getV15());
		if(sensitivitySliderValue.getV16() != null)
			sliderValues.add(sensitivitySliderValue.getV16());
		if(sensitivitySliderValue.getV17() != null)
			sliderValues.add(sensitivitySliderValue.getV17());
		if(sensitivitySliderValue.getV18() != null)
			sliderValues.add(sensitivitySliderValue.getV18());
		if(sensitivitySliderValue.getV19() != null)
			sliderValues.add(sensitivitySliderValue.getV19());
		return sliderValues;
	}
	@Override
	public List<VehicleSettingHistory> getVehicleSettingsHistory(
			Integer vehicleID, Date startTime, Date endTime) {
		List<VehicleSettingHistoryJPA> vehicleSettingHistories = vehicleSettingHistoryService.getVehicleSettingHistoryForVehicle(vehicleID, startTime, endTime);
		
		return convertVehicleSettingHistories(vehicleSettingHistories);
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
	private List<DeviceSettingDefinition> convertDeviceSettingDefinitions(List<DeviceSettingDefinitionJPA> deviceSettingDefinitionJPAs){
		List<DeviceSettingDefinition> deviceSettingDefinitions = new ArrayList<DeviceSettingDefinition>();
		for (DeviceSettingDefinitionJPA dsdJPA : deviceSettingDefinitionJPAs){
			DeviceSettingDefinition dsd = convertDeviceSettingDefinitionJPA(dsdJPA);
			deviceSettingDefinitions.add(dsd);
		}
		return deviceSettingDefinitions;
	}
	private DeviceSettingDefinition convertDeviceSettingDefinitionJPA(DeviceSettingDefinitionJPA dsdJPA){
		DeviceSettingDefinition dsd = new DeviceSettingDefinition();
		String[] ignoreProperties={"choices","regex"};
		BeanUtils.copyProperties(dsdJPA,dsd,ignoreProperties);
		processRegex(dsd, dsdJPA.getRegex());
		
		return dsd;
		
	}
	private void processRegex(DeviceSettingDefinition deviceSettingDefinition, String regexCandidate){
		if (regexCandidate == null) return;
    	try {
    		
    		Pattern regex = Pattern.compile(regexCandidate);
    		
           	//Maybe just choices - then should interpret as such
        	if(containsChoicesOnly(regexCandidate)){
        		
                deviceSettingDefinition.setChoices(Arrays.asList(regexCandidate.split("\\|")));
        	}
        	else {
        		
         		deviceSettingDefinition.setRegex(regex);
        	}
    	}
    	catch (PatternSyntaxException pse){
    	    
    	    deviceSettingDefinition.setRegex(null);
    	    deviceSettingDefinition.setChoices(null);
    		logger.info("regex "+pse.getPattern()+" threw a PatternSyntaxException - "+pse.getMessage());
    	}
    }    	
	private boolean containsChoicesOnly(String choiceCandidate){
	
		return !choiceEliminator.matcher(choiceCandidate).find() && choiceCandidate.contains("|");
	}
	private Device getDevice(Integer vehicleID){
		//TODO get real device
		Device device = new Device();
		device.setDeviceID(2);
		device.setImei("imei");
		device.setProductVersion(ProductType.TIWIPRO_R74);
		device.setFirmwareVersion(0);
		return device;
	}
	private List<DesiredVehicleSetting> getDesiredVehicleSettings(Integer desiredVehicleSettingID, Integer vehicleID, Integer deviceID,
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
	private void setSettings(List<DesiredVehicleSetting> desiredVehicleSettings){
		for(DesiredVehicleSetting dvs : desiredVehicleSettings){
			desiredVehicleSettingService.createDesiredVehicleSetting(dvs);
		}
	}
	private void updateSettings(List<DesiredVehicleSetting> desiredVehicleSettings){
		for(DesiredVehicleSetting dvs : desiredVehicleSettings){
			desiredVehicleSettingService.updateDesiredVehicleSetting(dvs);
		}
	}
	private void queueForwardCommands(Device device, Map<Integer, String> setMap){
		if (device.isWaySmart()){
			queueIndividualForwardCommands(device,setMap);
			queueUpdateConfigurationForwardCommand(device,setMap.size());
		}
		else if (device.getProductVersion().equals(ProductType.TIWIPRO_R71)||device.getProductVersion().equals(ProductType.TIWIPRO_R74)){
			
			if ((device.getFirmwareVersion() != null) && (device.getFirmwareVersion()>= 17004)){
				queueUpdateConfigurationForwardCommand(device,setMap.size());
			}
			else{
				queueIndividualForwardCommands(device,setMap);
			}
		}
	}
	private void queueIndividualForwardCommands(Device device,Map<Integer, String> setMap){
		for(Integer settingID :setMap.keySet()){
			ConfiguratorForwardCommand cfc = ConfiguratorForwardCommand.valueOf(settingID);
			if(cfc!= null){
				for(ForwardCommandDef fcd : cfc.getForwardCommands()){
					try {
						queueForwardCommand(device.getImei(),setMap.get(settingID).getBytes(),fcd.getForwardCommand());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	private void queueUpdateConfigurationForwardCommand(Device device,Integer changedValueCount){
		try {
			queueForwardCommand(device.getImei(),null,2178);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void queueForwardCommand(String address, byte[] data, int command) throws SQLException{
		ForwardCommandSpool fcs = new ForwardCommandSpool(data, command, address);
		fwdCmdSpoolWSIridiumJDBCDAO.add(fcs);
	 }

	private List<VehicleSettingHistory> convertVehicleSettingHistories(List<VehicleSettingHistoryJPA> vehicleSettingHistories){
		List<VehicleSettingHistory> convertedVehicleSettingHistories = new ArrayList<VehicleSettingHistory>();
		for(VehicleSettingHistoryJPA vshJPA : vehicleSettingHistories){
			convertedVehicleSettingHistories.add(convertVehicleSettingHistory(vshJPA));
		}
		return convertedVehicleSettingHistories;
	}
	private VehicleSettingHistory convertVehicleSettingHistory(VehicleSettingHistoryJPA vshJPA){
		VehicleSettingHistory vsh = new VehicleSettingHistory();
		BeanUtils.copyProperties(vshJPA, vsh);
		return vsh;
	}
	public void setVehicleSettingHistoryService(
			VehicleSettingHistoryService vehicleSettingHistoryService) {
		this.vehicleSettingHistoryService = vehicleSettingHistoryService;
	}

	public void setActualVehicleSettingService(
			ActualVehicleSettingService actualVehicleSettingService) {
		this.actualVehicleSettingService = actualVehicleSettingService;
	}

	public void setDesiredVehicleSettingService(
			DesiredVehicleSettingService desiredVehicleSettingService) {
		this.desiredVehicleSettingService = desiredVehicleSettingService;
	}

	public void setFwdCmdSpoolWSIridiumJDBCDAO(
			FwdCmdSpoolWSIridiumJDBCDAO fwdCmdSpoolWSIridiumJDBCDAO) {
		this.fwdCmdSpoolWSIridiumJDBCDAO = fwdCmdSpoolWSIridiumJDBCDAO;
	}

	public void setDeviceSettingDefinitionService(
			DeviceSettingDefinitionService deviceSettingDefinitionService) {
		this.deviceSettingDefinitionService = deviceSettingDefinitionService;
	}
	@Override
	public DeviceSettingDefinition findByID(Integer id) {
		// not implemented
		return null;
	}
	@Override
	public Integer create(Integer id, DeviceSettingDefinition entity) {
		// not implemented
		return null;
	}

	@Override
	public Integer update(DeviceSettingDefinition entity) {
		// not implemented
		return null;
	}

	@Override
	public Integer deleteByID(Integer id) {
		// not implemented
		return null;
	}

	public void setSensitivitySliderSettingService(
			SensitivitySliderSettingService sensitivitySliderSettingService) {
		this.sensitivitySliderSettingService = sensitivitySliderSettingService;
	}

	public void setVehicleService(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	@Override
	public void updateVehicleSettings(Integer vehicleID,
			Map<Integer, String> setMap, Integer userID, String reason) {
		throw new NotImplementedException();
		
	}

}
