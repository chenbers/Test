package com.inthinc.pro.automation.models;

import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.objects.NoteBC;
import com.inthinc.pro.automation.objects.TiwiNote;
import com.inthinc.pro.automation.objects.WSNoteVersion2;
import com.inthinc.pro.automation.objects.WSNoteVersion3;
import com.inthinc.pro.automation.objects.WaysmartDevice.Direction;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.EventAttr;

public abstract class DeviceNote {
	
	protected final AutomationCalendar time;
	protected final DeviceNoteTypes type;
	protected final GeoPoint location;
	protected final DeviceAttributes attrs;
	
	@Override
	public String toString(){
		return String.format("%s(NoteType: %s, Time: '%s', Location: %s, Attrs: %s)", this.getClass().getSimpleName(), type, time, location, attrs);
	}
	
	public DeviceNote (DeviceNoteTypes type, AutomationCalendar time, GeoPoint location){
		this.type = type;
		this.time = time.copy();
		this.location = location.copy();
		attrs = new DeviceAttributes();
	}
	
    public GeoPoint getLocation(){
		return location.copy();
	}
	
    public DeviceNoteTypes getType(){
    	return type;
    }
    
    public AutomationCalendar getTime(){
    	return time;
    }
    

	public void addAttr(EventAttr id, Integer value) {
		try {
			attrs.addAttribute(id, value);    
        } catch (Exception e) {
            throw new NullPointerException("Cannot add " + id + " with value " + value);
        }
	}

	public void addAttrs(DeviceAttributes attrs) {
		for (EventAttr key : attrs){
            addAttr(key, attrs.getValue(key));
        }
	}
	

	public void addAttr(EventAttr id, Object value) {
		if (value instanceof Number || value instanceof String){
        	attrs.addAttribute(id, value);
        } else if (value instanceof IndexEnum){
            addAttr(id, ((IndexEnum) value).getIndex());
        } else if (value instanceof Boolean){
        	addAttr(id, (Boolean) value ? 1:0);
        } else if (value instanceof AutomationCalendar){
        	addAttr(id, ((AutomationCalendar)value).toInt());
        } else if (value == null){
        	addAttr(id, 0);
        } else {
            throw new IllegalArgumentException("Cannot add value of type: " + value.getClass());
        }
	}
    
    
    public static DeviceNote constructNote(DeviceNoteTypes type, GeoPoint location, DeviceState state) {
        DeviceNote note = null;
        
        if (location == null){
        	location = new GeoPoint(0.0, 0.0);
        }
        
        if (state == null){
        	note = new TiwiNote(type);        	
        } else if (state.getProductVersion().equals(ProductType.WAYSMART)){
        	if (state.getWaysDirection().equals(Direction.sat)){
        		note = new WSNoteVersion3(type, state, location);
        	}
        	else if (NoteBC.types.contains(type)){
                note = new NoteBC(type, state, location);
            } else {
                note = new WSNoteVersion2(type, state, location);
            }
        } else {
            note = new TiwiNote(type, state, location);
            note.addAttr(EventAttr.SPEED_LIMIT, state.getSpeedLimit());
            state.setOdometerX100(0);
        }
        
        if (state.getViolationFlags() != 0x00){
        	note.addAttr(EventAttr.VIOLATION_FLAGS, state.getViolationFlags());	
        }
        
        return note;
    }

	public DeviceAttributes getAttrs() {
		return attrs;
	}
	

    
    public abstract byte[] Package();
    public abstract DeviceNote copy();
	        
	        
}
