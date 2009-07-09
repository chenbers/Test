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
		if(attributeType.equals(AttributeType.ATTR_TYPE_MPG) || attributeType.equals(AttributeType.ATTR_TYPE_BOUNDRY)){
		    attributeBytes = new byte[3];
		}
		attributeBytes[0] = (byte) (attributeType.getCode() & 0x000000FF);
		if(attributeType.equals(AttributeType.ATTR_TYPE_MPG) || attributeType.equals(AttributeType.ATTR_TYPE_BOUNDRY)){
		    puti2(attributeBytes,0,value);
		}else{
		    attributeBytes[1] = (byte) (value & 0x000000FF);
		}
		return attributeBytes;
	}
	
	private static int puti4(byte[] eventBytes, int idx, Integer i)
    {
        eventBytes[idx++] = (byte) ((i >> 24) & 0x000000FF);
        eventBytes[idx++] = (byte) ((i >> 16) & 0x000000FF);
        eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
        eventBytes[idx++] = (byte) (i & 0x000000FF);
        return idx;
    }

    private static int puti2(byte[] eventBytes, int idx, Integer i)
    {
        eventBytes[++idx] = (byte) ((i >> 8) & 0x000000FF);
        eventBytes[++idx] = (byte) (i & 0x000000FF); 
        return idx;
    }


}
