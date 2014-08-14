package com.inthinc.pro.model.event;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;

import javax.xml.bind.annotation.XmlRootElement;
import java.text.MessageFormat;
import java.util.*;

@XmlRootElement
public class MaintenanceEvent extends Event implements MultipleEventTypes {

    private static final long serialVersionUID = 1L;
    @EventAttrID(name="ATTR_BATTERY_VOLTAGE")
    private Integer batteryVoltage;
    @EventAttrID(name="ATTR_ENGINE_TEMP")
    private Integer engineTemp;
    @EventAttrID(name="ATTR_TRANSMISSION_TEMP")
    private Integer transmissionTemp;
    @EventAttrID(name="ATTR_DPF_FLOW_RATE")
    private Integer dpfFlowRate;
    @EventAttrID(name="ATTR_OIL_PRESSURE")
    private Integer oilPresure;
    @EventAttrID(name="ATTR_MALFUNCTION_INDICATOR_LAMP")
    private Integer malfunctionIndicatorLamp;
    @EventAttrID(name="ATTR_CHECK_ENGINE")
    private Integer checkEngine;
    @EventAttrID(name="ENGINE_HOURS_X100")
    private Integer engineHours;
    @EventAttrID(name="ODOMETER")
    private Integer odometer;

    private String threshold = "";

    private Map<Object, Object> attrMap = new HashMap<Object, Object>();

    private static EventAttr[] eventAttrList = {
            EventAttr.ATTR_BATTERY_VOLTAGE,
            EventAttr.ATTR_ENGINE_TEMP,
            EventAttr.ATTR_TRANSMISSION_TEMP,
            EventAttr.ATTR_DPF_FLOW_RATE,
            EventAttr.ATTR_OIL_PRESSURE,
            EventAttr.ATTR_MALFUNCTION_INDICATOR_LAMP,
            EventAttr.ENGINE_HOURS_X100,
            EventAttr.ODOMETER
    };

    public MaintenanceEvent() {
        super();
    }

    public MaintenanceEvent(Long noteID, Integer vehicleID, NoteType type,
                         Date time, Integer speed, Integer odometer, Double latitude,
                         Double longitude, Map<Object, Object> attrMap) {
        super(noteID, vehicleID, type, time, speed, odometer, latitude,
                longitude);
        this.attrMap = attrMap;

    }

    public EventType getEventType() {
            if(this.getAttribs() != null){
                String[] attribsList = this.getAttribs().split(";");
                for(String s : attribsList){
                    if(!s.trim().equals("")){
                        attrMap.put(s.split("=")[0],s.split("=")[1]);
                    }
                }
                if(attrMap.containsKey(EventAttr.ATTR_BATTERY_VOLTAGE.getCode() + "")){
                    threshold = attrMap.get(EventAttr.ATTR_BATTERY_VOLTAGE.getCode()+"").toString();
                    return EventType.BATTERY_VOLTAGE;
                }else if(attrMap.containsKey(EventAttr.ATTR_ENGINE_TEMP.getCode()+"")){
                    threshold = attrMap.get(EventAttr.ATTR_ENGINE_TEMP.getCode()+"").toString();
                    return EventType.ENGINE_TEMP;
                }else if(attrMap.containsKey(EventAttr.ATTR_TRANSMISSION_TEMP.getCode()+"")){
                    threshold = attrMap.get(EventAttr.ATTR_TRANSMISSION_TEMP.getCode()+"").toString();
                    return EventType.TRANSMISSION_TEMP;
                }else if(attrMap.containsKey(EventAttr.ATTR_DPF_FLOW_RATE.getCode()+"")){
                    threshold = attrMap.get(EventAttr.ATTR_DPF_FLOW_RATE.getCode()+"").toString();
                    return EventType.DPF_FLOW_RATE;
                }else if(attrMap.containsKey(EventAttr.ATTR_OIL_PRESSURE.getCode()+"")){
                    threshold = attrMap.get(EventAttr.ATTR_OIL_PRESSURE.getCode()+"").toString();
                    return EventType.OIL_PRESSURE;
                }
                else if(attrMap.containsKey(EventAttr.ATTR_CHECK_ENGINE.getCode()+"")){
                    threshold = attrMap.get(EventAttr.ATTR_CHECK_ENGINE.getCode()+"").toString();
                    if (Integer.valueOf(attrMap.get(EventAttr.ATTR_CHECK_ENGINE.getCode()+"").toString()) == 1) {
                        return EventType.RED_STOP;
                    } else if (Integer.valueOf(attrMap.get(EventAttr.ATTR_CHECK_ENGINE.getCode()+"").toString()) == 2) {
                        return EventType.AMBER_WARNING;
                    } else if (Integer.valueOf(attrMap.get(EventAttr.ATTR_CHECK_ENGINE.getCode()+"").toString()) == 3) {
                        return EventType.PROTECT;
                    } else {
                        return EventType.UNKNOWN;
                    }
                }else if(attrMap.containsKey(EventAttr.ENGINE_HOURS_X100.getCode()+"")){
                    threshold = attrMap.get(EventAttr.ENGINE_HOURS_X100.getCode()+"").toString();
                    return EventType.ATTR_ENGINE_HOURS;
                }else if(attrMap.containsKey(EventAttr.ODOMETER.getCode()+"")){
                    threshold = attrMap.get(EventAttr.ODOMETER.getCode()+"").toString();
                    return EventType.ODOMETER;
                }else if(attrMap.containsKey(EventAttr.ATTR_MALFUNCTION_INDICATOR_LAMP.getCode()+"")){
                    threshold = attrMap.get(EventAttr.ATTR_MALFUNCTION_INDICATOR_LAMP.getCode()+"").toString();
                    return EventType.MALFUNCTION_INDICATOR_LAMP;
                }else return EventType.UNKNOWN;
            }else{
                if(batteryVoltage != null){
                    threshold = batteryVoltage + "";
                    return EventType.BATTERY_VOLTAGE;
                }else if(engineTemp != null){
                    threshold = engineTemp + "";
                    return  EventType.ENGINE_TEMP;
                }else if(transmissionTemp != null) {
                    threshold = transmissionTemp + "";
                    return EventType.TRANSMISSION_TEMP;
                }else if(dpfFlowRate != null) {
                    threshold = dpfFlowRate + "";
                    return EventType.DPF_FLOW_RATE;
                }else if(oilPresure != null) {
                    threshold = oilPresure + "";
                    return EventType.OIL_PRESSURE;
                }else if(malfunctionIndicatorLamp != null) {
                    threshold = malfunctionIndicatorLamp + "";
                    return EventType.MALFUNCTION_INDICATOR_LAMP;
                } else if (checkEngine != null) {
                    threshold = checkEngine + "";
                    if (checkEngine == 1) {
                        return EventType.RED_STOP;
                    } else if (checkEngine == 2) {
                        return EventType.AMBER_WARNING;
                    } else if (checkEngine == 3) {
                        return EventType.PROTECT;
                    } else {
                        return EventType.UNKNOWN;
                    }
                }else if(engineHours != null) {
                    threshold = engineHours + "";
                    return EventType.ATTR_ENGINE_HOURS;
                }else if(odometer != null) {
                    threshold = odometer + "";
                    return EventType.ODOMETER;
                }else return EventType.UNKNOWN;
            }
    }

    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }

    public Map<Object, Object> getAttrMap() {
        return attrMap;
    }

    public void setAttrMap(Map<Object, Object> attrMap) {
        this.attrMap = attrMap;
    }

    public Integer getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(Integer batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public Integer getEngineTemp() {
        return engineTemp;
    }

    public void setEngineTemp(Integer engineTemp) {
        this.engineTemp = engineTemp;
    }

    public Integer getTransmissionTemp() {
        return transmissionTemp;
    }

    public void setTransmissionTemp(Integer transmissionTemp) {
        this.transmissionTemp = transmissionTemp;
    }

    public Integer getDpfFlowRate() {
        return dpfFlowRate;
    }

    public void setDpfFlowRate(Integer dpfFlowRate) {
        this.dpfFlowRate = dpfFlowRate;
    }

    public Integer getOilPresure() {
        return oilPresure;
    }

    public void setOilPresure(Integer oilPresure) {
        this.oilPresure = oilPresure;
    }

    public Integer getMalfunctionIndicatorLamp() {
        return malfunctionIndicatorLamp;
    }

    public void setMalfunctionIndicatorLamp(Integer malfunctionIndicatorLamp) {
        this.malfunctionIndicatorLamp = malfunctionIndicatorLamp;
    }

    public Integer getCheckEngine() {
        return checkEngine;
    }

    public void setCheckEngine(Integer checkEngine) {
        this.checkEngine = checkEngine;
    }

    public Integer getEngineHours() {
        return engineHours;
    }

    public void setEngineHours(Integer engineHours) {
        this.engineHours = engineHours;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    @Override
    public Set<EventType> getEventTypes() {
        return EnumSet.of(EventType.RED_STOP, EventType.AMBER_WARNING, EventType.PROTECT,EventType.BATTERY_VOLTAGE, EventType.ENGINE_TEMP, EventType.TRANSMISSION_TEMP, EventType.DPF_FLOW_RATE,EventType.OIL_PRESSURE, EventType.MALFUNCTION_INDICATOR_LAMP, EventType.ATTR_ENGINE_HOURS, EventType.ODOMETER );
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
