package com.inthinc.pro.convert;

public class LongConvert  implements Convert<Long, String>
{
	@Override
	public Long convert(Object input) {
       return (input == null) ? null : Long.valueOf(input.toString());
	}

}
