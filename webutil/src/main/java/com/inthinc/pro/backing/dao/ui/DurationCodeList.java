package com.inthinc.pro.backing.dao.ui;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.Duration;

public class DurationCodeList implements SelectList{

	List<SelectItem> selectList;
	public List<SelectItem> getSelectList() {
		
		if (selectList == null) {
			selectList = new ArrayList<SelectItem> ();

			for(Duration d : EnumSet.allOf(Duration.class))
				selectList.add(new SelectItem(Integer.valueOf(d.getCode()), d.getDurationValue()));
		}
		
		return selectList;
	}
    @Override
    public Object valueOf(Object value) {
        return value;
    }

}
