package com.inthinc.pro.dao.jpa;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.configurator.model.ConfiguratorForwardCommand;
import com.inthinc.pro.configurator.model.ForwardCommandDef;
import com.inthinc.pro.configurator.model.ProductType;
import com.inthinc.pro.domain.Device;
import com.inthinc.pro.domain.ForwardCommandHttp;
import com.inthinc.pro.service.ForwardCommandHttpService;

@Component
public class ForwardCommandHelper {
    @Autowired
    private ForwardCommandHttpService forwardCommandService;

	public void queueForwardCommands(Device device, Map<Integer, String> setMap){
		if (device.isWaySmart()){
			queueIndividualForwardCommands(device,setMap);
			queueUpdateConfigurationForwardCommand(device,setMap.size());
		}
		else if (deviceIsTiwiPro(device)){
			
			if (deviceIsConfiguratorAware(device)){
				queueUpdateConfigurationForwardCommand(device,setMap.size());
			}
			else{
				queueIndividualForwardCommands(device,setMap);
			}
		}
	}
	private boolean deviceIsTiwiPro(Device device){
		return device.getProductVersion().equals(ProductType.TIWIPRO_R71.getVersion())||
				device.getProductVersion().equals(ProductType.TIWIPRO_R74.getVersion());
	}
	private boolean deviceIsConfiguratorAware(Device device){
		return (device.getFirmwareVersion() != null) && (device.getFirmwareVersion()>= 17004);
	}
	private void queueIndividualForwardCommands(Device device,Map<Integer, String> setMap){
		for(Integer settingID :setMap.keySet()){
			ConfiguratorForwardCommand cfc = ConfiguratorForwardCommand.valueOf(settingID);
			if(cfc!= null){
				for(ForwardCommandDef fcd : cfc.getForwardCommands()){
					try {
						queueForwardCommand(device.getDeviceID(),setMap.get(settingID).getBytes(),fcd.getForwardCommand());
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
			queueForwardCommand(device.getDeviceID(),null,2178);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void queueForwardCommand(Integer deviceID, byte[] data, int command) throws SQLException{
		ForwardCommandHttp forwardCommand = ForwardCommandHttpBuilder.buildBinaryForwardCommand(data, deviceID, command);
		forwardCommandService.queueForwardCommand(forwardCommand);
	 }

}
