package com.inthinc.pro.service.note;

import java.util.EnumSet;

public enum AttributeLength{
    ONE_BYTE(1,1,127),
    TWO_BYTES(2,128,191),
    FOUR_BYTES(4,192,254);
    
    int byteCount;
    int minId;
    int maxId;
    
    private AttributeLength(int byteCount,int minId,int maxId){
        this.byteCount = byteCount;
        this.minId = minId;
        this.maxId = maxId;
    }
    
    public static AttributeLength valueOf(int attributeId){
        for (AttributeLength e : EnumSet.allOf(AttributeLength.class))
        {
            if(attributeId >= e.minId && attributeId <= e.maxId){
                return e;
            }
        }
        
        return null;
    }
    
    public int getByteCount(){
        return byteCount;
    }
    
    

}
