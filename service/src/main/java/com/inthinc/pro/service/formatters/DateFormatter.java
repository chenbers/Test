package com.inthinc.pro.service.formatters;

import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.resteasy.spi.StringParameterUnmarshaller;
import org.jboss.resteasy.util.FindAnnotation;

import com.inthinc.pro.service.annotations.DateFormat;

/**
 * A {@link StringParameterUnmarshaller} used to parse dates according to the pattern defined by {@link DateFormat}.
 * <p/>
 * This code is based on the examples provided by the RESTeasy documentation at <a
 * href="http://docs.jboss.org/resteasy/docs/2.0.0.GA/userguide/html_single/index.html#StringParamUnmarshaller"
 * >http://docs.jboss.org/resteasy/docs/2.0.0.GA/userguide/html_single/index.html#StringParamUnmarshaller</a>.
 * 
 * @see DateFormat
 */
public class DateFormatter implements StringParameterUnmarshaller<Date> {

    private SimpleDateFormat formatter;

    /**
     * @see org.jboss.resteasy.spi.StringParameterUnmarshaller#setAnnotations(java.lang.annotation.Annotation[])
     */
    public void setAnnotations(Annotation[] annotations) {
        DateFormat format = FindAnnotation.findAnnotation(annotations, DateFormat.class);
        formatter = new SimpleDateFormat(format.value());
    }

    /**
     * @see org.jboss.resteasy.spi.StringParameterUnmarshaller#fromString(java.lang.String)
     */
    public Date fromString(String str) {
        try {
            return formatter.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
