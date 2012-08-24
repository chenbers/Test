package com.inthinc.pro.convert;

import static org.junit.Assert.assertEquals;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import org.junit.Test;

import com.inthinc.pro.backing.BaseBeanTest;
import com.inthinc.pro.model.event.DVIREvent;
import com.inthinc.pro.util.MessageUtil;

public class EventDetailsConverterTest  extends BaseBeanTest {
    
    @Test
    public void DVIREventDetailsConverterTest(){
        
        DVIREvent dvirEvent = new DVIREvent();
        dvirEvent.setInspectionType(1); //pretrip
        dvirEvent.setVehicleSafeToOperate(0); //not safe
        UIComponent uiComponent = new HtmlOutputText();
        uiComponent.getAttributes().put("event",dvirEvent);
        EventDetailsConverter eventDetailsConverter = new EventDetailsConverter();
        String message = eventDetailsConverter.getAsString(FacesContext.getCurrentInstance(), uiComponent, MessageUtil.getMessageString("redflags_detailsEventType.DVIR"));
        assertEquals("Vehicle failed a pre-trip inspection.",message);

        dvirEvent.setInspectionType(2); //posttrip
        dvirEvent.setVehicleSafeToOperate(0); //not safe
        uiComponent = new HtmlOutputText();
        uiComponent.getAttributes().put("event",dvirEvent);
        eventDetailsConverter = new EventDetailsConverter();
        message = eventDetailsConverter.getAsString(FacesContext.getCurrentInstance(), uiComponent, MessageUtil.getMessageString("redflags_detailsEventType.DVIR"));
        assertEquals("Vehicle failed a post-trip inspection.",message);
        
        dvirEvent.setInspectionType(2); //posttrip
        dvirEvent.setVehicleSafeToOperate(1); //is safe
        uiComponent = new HtmlOutputText();
        uiComponent.getAttributes().put("event",dvirEvent);
        eventDetailsConverter = new EventDetailsConverter();
        message = eventDetailsConverter.getAsString(FacesContext.getCurrentInstance(), uiComponent, MessageUtil.getMessageString("redflags_detailsEventType.DVIR"));
        assertEquals("Vehicle passed a post-trip inspection.",message);

        dvirEvent.setInspectionType(1); //posttrip
        dvirEvent.setVehicleSafeToOperate(1); //is safe
        uiComponent = new HtmlOutputText();
        uiComponent.getAttributes().put("event",dvirEvent);
        eventDetailsConverter = new EventDetailsConverter();
        message = eventDetailsConverter.getAsString(FacesContext.getCurrentInstance(), uiComponent, MessageUtil.getMessageString("redflags_detailsEventType.DVIR"));
        assertEquals("Vehicle passed a pre-trip inspection.",message);

    }

}
