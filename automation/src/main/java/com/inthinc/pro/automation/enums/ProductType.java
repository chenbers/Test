package com.inthinc.pro.automation.enums;

import com.inthinc.pro.automation.interfaces.IndexEnum;

public enum ProductType implements IndexEnum {
	WAYSMART(2),
	TIWIPRO_R71(3), 
	TIWIPRO_R74(5, "R74"), 
	TIWIPRO_R747(7, "R747"),
	WAYSMART_850(12),
	;
	
	private final int index;
	private final String version;
	
	private ProductType(int index){
		this(index, "");
	}
	
	private ProductType(int index, String version){
	    this.index = index;
	    this.version = version;
	}

	@Override
	public Integer getIndex() {
		return index;
	}
	
	public String getTiwiVersion(){
	    return version;
	}

    public boolean isWaysmart() {
        return name().contains("WAYSMART");
    }

    public boolean shouldSendNoteHessian() {
        return this.equals(WAYSMART_850);
    }
	
}