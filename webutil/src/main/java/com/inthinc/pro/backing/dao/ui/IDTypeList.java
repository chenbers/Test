package com.inthinc.pro.backing.dao.ui;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class IDTypeList implements SelectList {

	List<SelectItem> selectList;
	public List<SelectItem> getSelectList() {
		
		if (selectList == null) {
			selectList = new ArrayList<SelectItem> ();
			selectList.add(new SelectItem("username", "User name (gets UserID)"));
			selectList.add(new SelectItem("priEmail", "e-mail (gets PersonID)"));
			selectList.add(new SelectItem("imei", "IMEI (gets DeviceID"));
			selectList.add(new SelectItem("serialNum", "Serial Number (gets DeviceID"));
			selectList.add(new SelectItem("vin", "VIN (gets VehicleID)"));
			selectList.add(new SelectItem("barcode", "Bar Code (gets DriverID)"));
			
		}
		
		return selectList;
	}

}
