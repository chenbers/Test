package com.inthinc.pro.service.note;

public class Attribute {
	
	private AttributeType attributeType;
	private Integer value;
	
	public Attribute(AttributeType attributeType,Integer value){
		this.value = value;
		this.attributeType = attributeType;
	}
	
	public byte[] getBytes(){
		
		AttributeLength attributeLength = AttributeLength.valueOf(attributeType.getCode());
		int byteCount = attributeLength.getByteCount() + 1;
		
		byte[] attributeBytes = new byte[byteCount];
		
		attributeBytes[0] = (byte) (attributeType.getCode() & 0x000000FF);
		switch(attributeLength){
		case ONE_BYTE:
		    attributeBytes[1] = (byte) (value & 0x000000FF);
		    break;
		case TWO_BYTES:
		    puti2(attributeBytes,1,value);
		    break;
		case FOUR_BYTES:
		    puti4(attributeBytes,1, value);
		    break;
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
        eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
        eventBytes[idx++] = (byte) (i & 0x000000FF); 
        return idx;
    }


}
