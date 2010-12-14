package com.inthinc.pro.convert;

import org.junit.Test;

import com.inthinc.pro.backing.BaseBeanTest;
import com.inthinc.pro.backing.LocaleBean;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.security.userdetails.ProUser;

public class MilesToKilometersConvertInputTest  extends BaseBeanTest {
    
    public MilesToKilometersConvertInputTest() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MilesToKilometersConvertInputTest(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    @Test
    public void milesToKilometersTest() {
        
        ProUser user = loginUser("custom101");
        LocaleBean localeBean = new LocaleBean();
        localeBean.getLocale();
        
        user.getUser().getPerson().setMeasurementType(MeasurementType.METRIC);

        Integer miles = 0;
        MilesToKilometersConverterInput converter = new MilesToKilometersConverterInput();
        String km = converter.getAsString(null, null, miles);
        assertEquals(0.0,Double.parseDouble(km));
    }

    @Test
    public void milesToKilometersToMilesTest() {
        
        ProUser user = loginUser("custom101");
        LocaleBean localeBean = new LocaleBean();
        localeBean.getLocale();
        
        user.getUser().getPerson().setMeasurementType(MeasurementType.METRIC);

        Integer miles = 1000;
        MilesToKilometersConverterInput converter = new MilesToKilometersConverterInput();
        String km = converter.getAsString(null, null, miles);
        Object convertedMiles = converter.getAsObject(null, null, km);
        assertEquals(miles,(Integer)convertedMiles);
        
        miles = 55555;
        converter = new MilesToKilometersConverterInput();
        km = converter.getAsString(null, null, miles);
        convertedMiles = converter.getAsObject(null, null, km);
        assertEquals(miles,(Integer)convertedMiles);

        miles = 100;
        converter = new MilesToKilometersConverterInput();
        km = converter.getAsString(null, null, miles);
        convertedMiles = converter.getAsObject(null, null, km);
        assertEquals(miles,(Integer)convertedMiles);
    }
}
