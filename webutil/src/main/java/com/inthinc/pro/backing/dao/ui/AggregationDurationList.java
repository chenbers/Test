package com.inthinc.pro.backing.dao.ui;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.AggregationDuration;

public class AggregationDurationList implements SelectList{

	List<SelectItem> selectList;
	public List<SelectItem> getSelectList() {
		
		if (selectList == null) {
			selectList = new ArrayList<SelectItem> ();

			for(AggregationDuration d : EnumSet.allOf(AggregationDuration.class))
				selectList.add(new SelectItem(Integer.valueOf(d.getCode()), d.toString()));
		}
		
		return selectList;
	}

}
