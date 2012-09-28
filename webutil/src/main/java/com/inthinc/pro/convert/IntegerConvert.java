package com.inthinc.pro.convert;


public class IntegerConvert implements Convert<Integer, String>
{

	@Override
	public Integer convert(Object input) {
       return (input == null) ? null : Integer.valueOf(input.toString());
	}
}