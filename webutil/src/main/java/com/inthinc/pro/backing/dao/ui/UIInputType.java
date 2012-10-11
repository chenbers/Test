package com.inthinc.pro.backing.dao.ui;

public enum UIInputType {

	DEFAULT,
	ACCOUNT_ID,
	BOOLEAN,
	CALENDAR,
	SELECT_LIST,
	PICK_LIST;

	public static UIInputType forType(Class<?> actualType) {
		
		if (actualType == null)
			return DEFAULT;
		
		if (actualType.equals(java.util.Date.class))
			return CALENDAR;
		if (actualType.equals(Boolean.class))
			return BOOLEAN;
		if (com.inthinc.pro.backing.dao.ui.SelectList.class.isAssignableFrom(actualType))
			return SELECT_LIST;
		if (com.inthinc.pro.backing.dao.ui.PickList.class.isAssignableFrom(actualType))
			return PICK_LIST;
		return DEFAULT;
	}
	
	
	
	
}
