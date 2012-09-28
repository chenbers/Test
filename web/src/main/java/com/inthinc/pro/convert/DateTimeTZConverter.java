package com.inthinc.pro.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.util.MessageUtil;

public class DateTimeTZConverter extends BaseConverter
{
  @Override
  public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value)
  {
      Date date = new Date();
	  SimpleDateFormat sdf = getDateFormatter(uiComponent);
      try
      {
         date =  sdf.parse(value);
      }catch(ParseException ex){
         throw new ConverterException(ex);
      }
      return date;
  }

  @Override
  public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value)
  {
      String returnString = null;
      if (value instanceof Date)
      {
    	  SimpleDateFormat sdf = getDateFormatter(uiComponent);

          Date date = (Date) value;
          returnString = sdf.format(date);
      }
      return returnString;
  }

  private SimpleDateFormat getDateFormatter(UIComponent uiComponent) {
	  String pattern = (String)uiComponent.getAttributes().get("pattern");
	  TimeZone timeZone = (TimeZone)uiComponent.getAttributes().get("timeZone");

	  SimpleDateFormat sdf = null;
	  if (pattern == null) {
		  sdf = new SimpleDateFormat(MessageUtil.getMessageString("dateTimeFormat"), getLocale());
	  }
	  else {
		  sdf = new SimpleDateFormat(pattern, getLocale());
	  }
	  if (timeZone == null) {
		  sdf.setTimeZone(TimeZone.getDefault());
	  }
	  else {
		  sdf.setTimeZone(timeZone);
	  }
	return sdf;
}


}
