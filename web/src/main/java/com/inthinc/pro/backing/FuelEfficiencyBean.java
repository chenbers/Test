package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.util.MessageUtil;

public class FuelEfficiencyBean extends BaseBean {
    private List<SelectItem> fuelEfficiencyTypeItems = new ArrayList<SelectItem>();

//    public void init() {
//        for (FuelEfficiencyType type : FuelEfficiencyType.values()) {
//            fuelEfficiencyTypeItems.add(new SelectItem(type, MessageUtil.getMessageString(type.toString(), getLocale())));
//        }
//    }

    public List<SelectItem> getFuelEfficiencyTypeItems() {
        return fuelEfficiencyTypeItems;
    }

    public void init(){
    	
     	fuelEfficiencyTypeItems = new ArrayList<SelectItem>();
   	
    	if (getPerson().getMeasurementType().equals(MeasurementType.METRIC)){
    		
            fuelEfficiencyTypeItems.add(new SelectItem(FuelEfficiencyType.KMPL, MessageUtil.getMessageString(FuelEfficiencyType.KMPL.toString(), getLocale())));
            fuelEfficiencyTypeItems.add(new SelectItem(FuelEfficiencyType.LP100KM, MessageUtil.getMessageString(FuelEfficiencyType.LP100KM.toString(), getLocale())));
               		
    	}
    	else{
    		
            fuelEfficiencyTypeItems.add(new SelectItem(FuelEfficiencyType.MPG_US, MessageUtil.getMessageString(FuelEfficiencyType.MPG_US.toString(), getLocale())));
            fuelEfficiencyTypeItems.add(new SelectItem(FuelEfficiencyType.MPG_UK, MessageUtil.getMessageString(FuelEfficiencyType.MPG_UK.toString(), getLocale())));
    	}
    	
    }
    public void init(MeasurementType measurementType){
    	
     	fuelEfficiencyTypeItems = new ArrayList<SelectItem>();
   	
    	if (measurementType.equals(MeasurementType.METRIC)){
    		
            fuelEfficiencyTypeItems.add(new SelectItem(FuelEfficiencyType.KMPL, MessageUtil.getMessageString(FuelEfficiencyType.KMPL.toString(), getLocale())));
            fuelEfficiencyTypeItems.add(new SelectItem(FuelEfficiencyType.LP100KM, MessageUtil.getMessageString(FuelEfficiencyType.LP100KM.toString(), getLocale())));
               		
    	}
    	else{
    		
            fuelEfficiencyTypeItems.add(new SelectItem(FuelEfficiencyType.MPG_US, MessageUtil.getMessageString(FuelEfficiencyType.MPG_US.toString(), getLocale())));
            fuelEfficiencyTypeItems.add(new SelectItem(FuelEfficiencyType.MPG_UK, MessageUtil.getMessageString(FuelEfficiencyType.MPG_UK.toString(), getLocale())));
    	}
    	
    }
}
