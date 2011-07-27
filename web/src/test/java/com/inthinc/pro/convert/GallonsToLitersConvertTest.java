package com.inthinc.pro.convert;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;
import org.richfaces.component.html.HtmlInputText;

import com.inthinc.pro.backing.BaseBeanTest;
import com.inthinc.pro.backing.LocaleBean;
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
        String liters = converter.getAsString(context, uiInput, gallons);
        assertEquals(0.0,Double.parseDouble(liters));
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
        assertEquals(gallons,(Float)convertedGallons);
        
        gallons = 55555f;
        converter = new GallonsToLitersConverter();
        liters = converter.getAsString(context, uiInput, gallons);
        convertedGallons = converter.getAsObject(context, uiInput, liters);
        assertEquals(gallons,(Float)convertedGallons);

        gallons = 100f;
        converter = new GallonsToLitersConverter();
        liters = converter.getAsString(context, uiInput, gallons);
        convertedGallons = converter.getAsObject(context, uiInput, liters);
        assertEquals(gallons,(Float)convertedGallons);
    }


}
