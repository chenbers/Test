package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ForwardCommandParamType implements BaseEnum {
	NONE(0),
	INTEGER(1),
	STRING(2),
    BINARY(3);

	Integer code;
	private ForwardCommandParamType(Integer code)
	{
		this.code = code;
	}

	@Override
	public Integer getCode() {
		return code;
	}
    private static final Map<Integer, ForwardCommandParamType> lookup = new HashMap<Integer, ForwardCommandParamType>();
    static
    {
        for (ForwardCommandParamType p : EnumSet.allOf(ForwardCommandParamType.class))
        {
            lookup.put(p.code, p);
        }
    }
    
    public static ForwardCommandParamType valueOf(Integer code)
    {
        return lookup.get(code);
    }


}
