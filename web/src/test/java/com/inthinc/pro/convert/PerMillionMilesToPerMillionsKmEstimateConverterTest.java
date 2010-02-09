package com.inthinc.pro.convert;


import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.backing.BaseBeanTest;
import com.inthinc.pro.backing.LocaleBean;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.security.userdetails.ProUser;


public class PerMillionMilesToPerMillionsKmEstimateConverterTest extends BaseBeanTest {
	
	public PerMillionMilesToPerMillionsKmEstimateConverterTest()
	{
		
	}
	public PerMillionMilesToPerMillionsKmEstimateConverterTest(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	
    @Test
    @Ignore
    public void getAsString()
    {
    	
    	ProUser user = loginUser("custom101");
    	LocaleBean localeBean = new LocaleBean();
    	localeBean.getLocale();
    
//    	this.getProUser().getUser().setLocale(LocaleBean.getCurrentLocale());
    	
    	PerMillionsMilesToPerMillionsKmEstimateConverter converter = new PerMillionsMilesToPerMillionsKmEstimateConverter();
    	
    	// English units
    	String str = converter.getAsString(this.facesContext, null, 100.0);
    	assertEquals(PerMillionsMilesToPerMillionsKmEstimateConverter.ABOVE_FIFTY, str);
    	
    	str = converter.getAsString(this.facesContext, null, 2.024);
    	assertEquals("2.0", str);

    	str = converter.getAsString(this.facesContext, null, 2.051);
    	assertEquals("2.1", str);
    	
    	str = converter.getAsString(this.facesContext, null, 0.001);
    	assertEquals(PerMillionsMilesToPerMillionsKmEstimateConverter.BELOW_POINT_ZERO_ONE, str);
    	
    	str = converter.getAsString(this.facesContext, null, 0.0);
    	assertEquals(PerMillionsMilesToPerMillionsKmEstimateConverter.BELOW_POINT_ZERO_ONE, str);
    	
   	// Metric units  (conversion: perMilMiles * 0.62137 = perMilKM)
    	user.getUser().getPerson().setMeasurementType(MeasurementType.METRIC);
    	str = converter.getAsString(this.facesContext, null, 100.0);		// 100 pmm = 62 pmkm 
    	assertEquals(PerMillionsMilesToPerMillionsKmEstimateConverter.ABOVE_FIFTY, str);
    	
    	str = converter.getAsString(this.facesContext, null, 2.024);		// 2.024 pmm = 1.25765 pmkm
    	assertEquals("1.3", str);

    	
    	str = converter.getAsString(this.facesContext, null, 0.001);		// .001 pmm = 0.00062137 pmkm
    	assertEquals(PerMillionsMilesToPerMillionsKmEstimateConverter.BELOW_POINT_ZERO_ONE, str);
    }


}
