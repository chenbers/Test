package com.inthinc.pro.convert;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;
import org.richfaces.component.html.HtmlInputText;

import com.inthinc.pro.backing.BaseBeanTest;
import com.inthinc.pro.backing.LocaleBean;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.security.userdetails.ProUser;

public class GallonsToLitersConvertTest extends BaseBeanTest {
	
	private FacesContext context;
	private UIInput uiInput;
	
    @Before
	public void setup(){
    	context = FacesContext.getCurrentInstance();
    	uiInput = new HtmlInputText();
    }
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
        String liters = converter.getAsString(context, uiInput, gallons.floatValue());
        assertEquals(0.0,Double.parseDouble(liters), .01f);
    }

    @Test
    public void gallonsToLitersToGallonsTest() {
        
        ProUser user = loginUser("custom101");
        LocaleBean localeBean = new LocaleBean();
        localeBean.getLocale();
        
        user.getUser().getPerson().setMeasurementType(MeasurementType.METRIC);

        Float gallons = 1000.0f;
        GallonsToLitersConverter converter = new GallonsToLitersConverter();
        String liters = converter.getAsString(context, uiInput, gallons);
        Object convertedGallons = converter.getAsObject(context, uiInput, liters);
        assertEquals(gallons,(Float)convertedGallons, .3f);
        
        gallons = 55555f;
        converter = new GallonsToLitersConverter();
        liters = converter.getAsString(context, uiInput, gallons);
        convertedGallons = converter.getAsObject(context, uiInput, liters);
        assertEquals(gallons,(Float)convertedGallons, .3f);

        gallons = 100f;
        converter = new GallonsToLitersConverter();
        liters = converter.getAsString(context, uiInput, gallons);
        convertedGallons = converter.getAsObject(context, uiInput, liters);
        assertEquals(gallons,(Float)convertedGallons, .1f);

        converter = new GallonsToLitersConverter();
        liters = "123.00";
        convertedGallons = converter.getAsObject(context, uiInput, liters);
        String convertedLiters = converter.getAsString(context, uiInput, convertedGallons);
        assertEquals(Float.parseFloat(liters),Float.parseFloat(convertedLiters),0.1f);
        
    }
    @Test
    public void measurementConversionGallonsToLitersTest(){
    	
    	float liters = 123.00f;
    	float gallons = (Float)MeasurementConversionUtil.fromLitersToGallonsExact(liters);
    	float convertedLiters = (Float)MeasurementConversionUtil.fromGallonsToLitersExact(gallons);
        assertEquals(liters,convertedLiters, .1f);
    }
}
