package com.inthinc.pro.rally;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IllegalFormatException;
import java.util.TimeZone;

/**
 * @author ebbuttn
 *
 * A utility class to facilitate converting Java Date instances to an XML Schema dateTime
 *
 * Formats Date instance to XSD compatible dateTime Strings with specified timezone -->
 * e.g. 2002-10-10T12:00:00-05:00 (noon on 10 October 2002, Eastern Standard Time in the U.S.)
 *
 * http://www.w3.org/TR/xmlschema-2/#isoformats
 */
public class XsdDatetimeFormat {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public XsdDatetimeFormat () {}

    public XsdDatetimeFormat (TimeZone timeZone)  {
        simpleDateFormat.setTimeZone(timeZone);
    }

    /**
    *  Parse a xml date string in the format produced by this class only.
    *  This method cannot parse all valid xml date string formats -
    *  so don't try to use it as part of a general xml parser
    */
    public synchronized Date parse(String xmlDateTime) throws ParseException  {
        if ( xmlDateTime.length() != 25 )  {
            throw new ParseException("Date not in expected xml datetime format", 0);
        }

        StringBuilder sb = new StringBuilder(xmlDateTime);
        sb.deleteCharAt(22);
        return simpleDateFormat.parse(sb.toString());
    }

    public synchronized String format(Date xmlDateTime) throws IllegalFormatException  {
        String s =  simpleDateFormat.format(xmlDateTime);
        StringBuilder sb = new StringBuilder(s);
        sb.insert(22, ':');
        return sb.toString();
    }

    public synchronized void setTimeZone(String timezone)  {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
    }
}
