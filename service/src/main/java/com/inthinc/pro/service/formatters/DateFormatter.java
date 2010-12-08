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

            validateInputString(str);

            return formatter.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The JDK has a bug in {@link SimpleDateFormat} which gives a wrong date as a result of the parsing instead of throwing a {@link ParseException} when there are dashes included
     * in either the pattern or input string. Details for the bug can be found here:
     * http://bugs.sun.com/bugdatabase/view_bug.do;jsessionid=7d89d1669acc5303b141a69a327f?bug_id=6609727.
     * <p/>
     * This method does a pattern match on the input string to perform the validation that {@link SimpleDateFormat} should have done. It throws a {@link ParseException} if the
     * input string is not composed of 8 digits.
     */
    private void validateInputString(String str) throws ParseException {
        if (!str.matches("\\d{8}")) {
            throw new ParseException("Unparseable date: \"" + str + "\"", 0);
        }
    }
}
