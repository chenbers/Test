package com.inthinc.pro.service.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;

import org.jboss.resteasy.annotations.StringParameterUnmarshallerBinder;
import org.jboss.resteasy.spi.StringParameterUnmarshaller;

import com.inthinc.pro.service.formatters.DateFormatter;

/**
 * A {@link StringParameterUnmarshaller} annotation used to unmarshal Strings into dates following a date pattern.
 * <p/>
 * The value of this annotation will be used as the pattern parse date though a {@link SimpleDateFormat}.
 * <p/>
 * This code is based on the examples provided by the RESTeasy documentation at <a
 * href="http://docs.jboss.org/resteasy/docs/2.0.0.GA/userguide/html_single/index.html#StringParamUnmarshaller"
 * >http://docs.jboss.org/resteasy/docs/2.0.0.GA/userguide/html_single/index.html#StringParamUnmarshaller</a>.
 * 
 * @see DateFormatter
 */
@Retention(RetentionPolicy.RUNTIME)
@StringParameterUnmarshallerBinder(DateFormatter.class)
public @interface DateFormat {
    String value();
}
