package com.inthinc.pro.automation.objects;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.deviceEnums.DeviceProps;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.models.DeviceNote;
import com.inthinc.pro.automation.models.DeviceNote.SatOnly;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.comm.util.VSettingsUtil;
import com.inthinc.pro.model.configurator.ProductType;

public class SatelliteStrippedConfigurator extends DeviceNote implements SatOnly {
	

	private final Map<DeviceProps, String> settings;
	
	public SatelliteStrippedConfigurator(DeviceNoteTypes type,
			DeviceState state, GeoPoint location, Map<DeviceProps, String> settings) {
		super(type, state.getTime(), location);
		this.settings = new HashMap<DeviceProps, String>();
		for (DeviceProps prop: settings.keySet()){
			this.settings.put(prop, settings.get(prop));
		}
	}

	@Override
	public byte[] Package() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		baos.write(0);  // length of struct - m_header
		longToByte(baos, type.getIndex(), 1);
		List<byte[]> list = VSettingsUtil.compressSettings(settings);
		for (byte[] bits : list){
			baos.write(bits, 0, bits.length);
		}
		byte[] temp = baos.toByteArray();
		temp[0] = (byte)(temp.length & 0xFF);
		return temp;
	}

	@Override
	public DeviceNote copy() {
		DeviceState state = new DeviceState(null, ProductType.WAYSMART);
		state.getTime().setDate(time);
		return new SatelliteStrippedConfigurator(type, state, location, settings) ;
	}

}
