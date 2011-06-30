package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.util.MessageUtil;

public class FuelEfficiencyBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8995522918384544909L;
	private List<SelectItem> fuelEfficiencyTypeItems = new ArrayList<SelectItem>();

    public List<SelectItem> getFuelEfficiencyTypeItems() {
        return fuelEfficiencyTypeItems;
    }

    public void init(MeasurementType measurementType){
    	
     	fuelEfficiencyTypeItems = new ArrayList<SelectItem>();
     	if(measurementType == null) measurementType = MeasurementType.ENGLISH;
     	
        for(FuelEfficiencyType fet : measurementType.getValidFuelEfficiencyTypes()){
            fuelEfficiencyTypeItems.add(new SelectItem(fet, MessageUtil.getMessageString(fet.toString(), getLocale())));
        }
   	
//    	if (measurementType != null && measurementType.equals(MeasurementType.METRIC)){
//    		
//            fuelEfficiencyTypeItems.add(new SelectItem(FuelEfficiencyType.KMPL, MessageUtil.getMessageString(FuelEfficiencyType.KMPL.toString(), getLocale())));
//            fuelEfficiencyTypeItems.add(new SelectItem(FuelEfficiencyType.LP100KM, MessageUtil.getMessageString(FuelEfficiencyType.LP100KM.toString(), getLocale())));
//               		
//    	}
//    	else{
//    		
//            fuelEfficiencyTypeItems.add(new SelectItem(FuelEfficiencyType.MPG_US, MessageUtil.getMessageString(FuelEfficiencyType.MPG_US.toString(), getLocale())));
//            fuelEfficiencyTypeItems.add(new SelectItem(FuelEfficiencyType.MPG_UK, MessageUtil.getMessageString(FuelEfficiencyType.MPG_UK.toString(), getLocale())));
//    	}
    	
    }
}
