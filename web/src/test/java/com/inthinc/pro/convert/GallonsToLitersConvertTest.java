package com.inthinc.pro.convert;

import org.junit.Test;

import com.inthinc.pro.backing.BaseBeanTest;
import com.inthinc.pro.backing.LocaleBean;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.security.userdetails.ProUser;

public class GallonsToLitersConvertTest extends BaseBeanTest {
    
    public GallonsToLitersConvertTest() {
        super();
    }

    public GallonsToLitersConvertTest(String name) {
        super(name);
    }
    
    @Test
    public void gallonsToLitersTest() {
        
        ProUser user = loginUser("custom101");
        LocaleBean localeBean = new LocaleBean();
        localeBean.getLocale();
        
        user.getUser().getPerson().setMeasurementType(MeasurementType.METRIC);

        Integer gallons = 0;
        GallonsToLitersConverter converter = new GallonsToLitersConverter();
        String liters = converter.getAsString(null, null, gallons);
        assertEquals(0.0,Double.parseDouble(liters));
    }

    @Test
    public void milesToKilometersToMilesTest() {
        
        ProUser user = loginUser("custom101");
        LocaleBean localeBean = new LocaleBean();
        localeBean.getLocale();
        
        user.getUser().getPerson().setMeasurementType(MeasurementType.METRIC);

        Float gallons = (float) 1000.0;
        GallonsToLitersConverter converter = new GallonsToLitersConverter();
        String liters = converter.getAsString(null, null, gallons);
        Object convertedGallons = converter.getAsObject(null, null, liters);
        assertEquals(gallons,(Float)convertedGallons);
        
        gallons = (float) 55555;
        converter = new GallonsToLitersConverter();
        liters = converter.getAsString(null, null, gallons);
        convertedGallons = converter.getAsObject(null, null, liters);
        assertEquals(gallons,(Float)convertedGallons);

        gallons = (float) 100;
        converter = new GallonsToLitersConverter();
        liters = converter.getAsString(null, null, gallons);
        convertedGallons = converter.getAsObject(null, null, liters);
        assertEquals(gallons,(Float)convertedGallons);
    }


}
