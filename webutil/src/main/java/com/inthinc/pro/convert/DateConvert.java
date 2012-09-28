package com.inthinc.pro.convert;

import java.util.Date;

public class DateConvert implements Convert<Long, Date>
{

	@Override
	public Long convert(Object input) {
		Date date = Date.class.cast(input);
        long ms = date.getTime();
        long sec = (ms / 1000l);

        return Long.valueOf(sec);
	}
	
}
