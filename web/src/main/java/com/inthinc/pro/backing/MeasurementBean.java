package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.util.MessageUtil;

public class MeasurementBean extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -884893762620025514L;
	private List<SelectItem> measurementTypeItems = new ArrayList<SelectItem>();
	private FuelEfficiencyBean fuelEfficiencyBean;

	public void init() {
        for (MeasurementType type : MeasurementType.values()) {
            measurementTypeItems.add(new SelectItem(type, MessageUtil.getMessageString(type.toString(), getLocale())));
        }
		fuelEfficiencyBean.init(getPerson().getMeasurementType());
    }
    public void measurementTypeChosenAction(){
    	
    		fuelEfficiencyBean.init(getPerson().getMeasurementType());
    }
    public List<SelectItem> getMeasurementTypeItems() {
        return measurementTypeItems;
    }
    public FuelEfficiencyBean getFuelEfficiencyBean() {
		return fuelEfficiencyBean;
	}
	public void setFuelEfficiencyBean(FuelEfficiencyBean fuelEfficiencyBean) {
		this.fuelEfficiencyBean = fuelEfficiencyBean;
	}
}
