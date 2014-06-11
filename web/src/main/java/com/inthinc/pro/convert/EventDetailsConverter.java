package com.inthinc.pro.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.model.event.DVIREvent;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventType;
import com.inthinc.pro.model.event.StatusEvent;
import com.inthinc.pro.util.MessageUtil;

import java.text.MessageFormat;

public class EventDetailsConverter extends BaseConverter
{
	  @Override
	  public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value)
	  {
		  // not implemented
	      throw new ConverterException("Not Implemented");
	  }

	  @Override
	  public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value)
	  {
	      String formatString = value.toString();
		  Event event= (Event)uiComponent.getAttributes().get("event");

		  if (formatString == null || event == null)
			  return "";

		  if (event instanceof StatusEvent) {
	            String statusString = MessageUtil.getMessageString(((StatusEvent)event).getStatusMessageKey());
	            return event.getDetails(formatString, null, statusString);
	      }
		  else  if (event instanceof DVIREvent){
		      String pass = MessageUtil.getMessageString("dvir_pass");
		      String fail = MessageUtil.getMessageString("dvir_fail");
		      String pretrip = MessageUtil.getMessageString("dvir_pre");
		      String postTrip = MessageUtil.getMessageString("dvir_post");
              return event.getDetails(formatString, null, pass, fail, pretrip, postTrip);

		  } else if (event.getType() != null && event.getType().getEventType().equals(EventType.TAMPERING)) {
              if (event.getDeviceName()!=null && !event.getDeviceName().trim().isEmpty()){
                  String deviceName = event.getDeviceName().trim();
                  return MessageFormat.format(formatString, deviceName);
              }

              return formatString;
          } else{
              String mphString = MessageUtil.getMessageString(getMeasurementType().toString()+"_mph");
              String miString  = MessageUtil.getMessageString(getMeasurementType().toString() + "_miles");
              return event.getDetails(formatString, getMeasurementType(), mphString, miString);
          }
	  }



}

