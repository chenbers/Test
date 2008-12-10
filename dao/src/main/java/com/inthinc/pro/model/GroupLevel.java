package com.inthinc.pro.model;

public enum GroupLevel {
	FLEET(1),
	DIVISION(2),
	TEAM(3);
	
	private int code;
	
	private GroupLevel(int code){
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
}
