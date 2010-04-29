package com.inthinc.pro.convert;

import java.util.Date;

public class BaseConvert implements Convert
{

    public BaseConvert()
    {
        
    }
    public String convert(String input)
    {
        return input;
    }

    public String convert(Date date)
    {
        return "";
    }
	@Override
	public Object convert(Object input) {
		// TODO Auto-generated method stub
		return null;
	}
}
