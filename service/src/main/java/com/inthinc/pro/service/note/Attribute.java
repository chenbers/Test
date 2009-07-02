package com.inthinc.pro.service.note;

public class Attribute {
	
	private AttributeType attributeType;
	private Integer value;
	
	public Attribute(AttributeType attributeType,Integer value){
		this.value = value;
		this.attributeType = attributeType;
	}
	
	public byte[] getBytes(){
		byte[] attributeBytes = new byte[2];
		attributeBytes[0] = (byte) (attributeType.getCode() & 0x000000FF);
		attributeBytes[1] = (byte) (value & 0x000000FF);
		return attributeBytes;
	}

}
