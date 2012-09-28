package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.Map;

public class IconMap {
	
	Map<Integer, String> icons;
	

	public IconMap() {
		super();
		icons = new HashMap<Integer,String>();
	}

	public Map<Integer, String> getIcons() {
		return icons;
	}

	public void setIcons(Map<Integer, String> icons) {
		this.icons = icons;
	}
	
	public void addIcon(Integer groupId, String icon){
		
		icons.put(groupId,icon);
	}
	
}
