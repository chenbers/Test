package com.inthinc.pro.backing.dao.ui;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.Duration;

public class DurationBinSizeList implements SelectList{

	List<SelectItem> selectList;
	public List<SelectItem> getSelectList() {
		
		if (selectList == null) {
			selectList = new ArrayList<SelectItem> ();

			selectList.add(new SelectItem(Integer.valueOf(Duration.BINSIZE_1_DAY), "DAY" ));
			selectList.add(new SelectItem(Integer.valueOf(Duration.BINSIZE_1_MONTH),"MONTH"));
		}
		
		return selectList;
	}

}
