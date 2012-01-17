package com.inthinc.pro.automation.enums;

import com.inthinc.pro.automation.interfaces.IndexEnum;

public enum ProductType implements IndexEnum {
	WAYSMART(2),
	TIWIPRO_R74(5)
	
	;
	
	private final int index;
	
	private ProductType(int index){
		this.index = index;
	}

	@Override
	public Integer getIndex() {
		return index;
	}
	
}