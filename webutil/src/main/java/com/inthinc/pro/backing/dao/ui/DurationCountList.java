package com.inthinc.pro.backing.dao.ui;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import com.inthinc.pro.model.Duration;

public class DurationCountList implements SelectList{

	List<SelectItem> selectList;
	public List<SelectItem> getSelectList() {
		
		if (selectList == null) {
			selectList = new ArrayList<SelectItem> ();

			for(Duration d : EnumSet.allOf(Duration.class))
				selectList.add(new SelectItem(Integer.valueOf(d.getDvqCount()), "" + d.getDvqCount()));
		}
		
		return selectList;
	}
}
