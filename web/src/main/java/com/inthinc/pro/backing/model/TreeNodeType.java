package com.inthinc.pro.backing.model;

public enum TreeNodeType {
	FLEET(0),
	DIVISION(1),
	TEAM(2),
	DRIVER(3),
	VEHICLE(4),
	DEVICE(5),
	USER(6);
	
	private Integer code;
	
	private TreeNodeType(Integer code){
	    this.code = code;
	};
	
	public Integer getCode()
	{
	    return this.code;
	}

}
