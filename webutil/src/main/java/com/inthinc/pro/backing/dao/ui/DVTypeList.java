package com.inthinc.pro.backing.dao.ui;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class DVTypeList implements SelectList {

	List<SelectItem> selectList;
	public List<SelectItem> getSelectList() {
		
		if (selectList == null) {
			selectList = new ArrayList<SelectItem> ();
			selectList.add(new SelectItem(Integer.valueOf(1), "Driver"));
			selectList.add(new SelectItem(Integer.valueOf(2), "Vehicle"));
		}
		
		return selectList;
	}

}
