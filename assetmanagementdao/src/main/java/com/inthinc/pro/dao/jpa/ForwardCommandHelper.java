package com.inthinc.pro.dao.jpa;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.configurator.model.ConfiguratorForwardCommand;
import com.inthinc.pro.configurator.model.ProductType;
import com.inthinc.pro.domain.Device;
import com.inthinc.pro.domain.ForwardCommand;
import com.inthinc.pro.domain.settings.ForwardCommandDef;
import com.inthinc.pro.service.ForwardCommandService;

@Component
public class ForwardCommandHelper {
    @Autowired
    private ForwardCommandService forwardCommandService;

	public void queueForwardCommands(Device device, Map<Integer, String> setMap){
		if (device.isWaySmart()){
			queueIndividualForwardCommands(device,setMap);
			queueUpdateConfigurationForwardCommand(device,setMap.size());
		}
		else if (device.getProductVersion().equals(ProductType.TIWIPRO_R71.getVersion())||device.getProductVersion().equals(ProductType.TIWIPRO_R74.getVersion())){
			
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
						queueForwardCommand(device.getDeviceID(), device.getImei(),setMap.get(settingID).getBytes(),fcd.getForwardCommand());
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
			queueForwardCommand(device.getDeviceID(), device.getImei(),null,2178);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void queueForwardCommand(Integer deviceID, String address, byte[] data, int command) throws SQLException{
		ForwardCommand forwardCommand = ForwardCommandBuilder.buildBinaryForwardCommand(data, deviceID, command, address);
		forwardCommandService.queueForwardCommand(forwardCommand);
	 }

}
