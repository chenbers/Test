package com.inthinc.pro.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class DateConvert extends BaseConvert
{
    // TODO: let user set in UI
    public static String        MST_TZ = "US/Mountain";

    private static final Logger logger = Logger.getLogger(DateConvert.class);

    public DateConvert()
    {

    }

    @Override
    public String convert(String input)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        // formatter.setTimeZone(TimeZone.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone(MST_TZ));
        try
        {
            Date date = formatter.parse(input);
            long ms = date.getTime();
            long sec = (ms / 1000l);

            logger.debug(input + " " + sec);
            return Long.valueOf(sec).toString();
        }
        catch (ParseException e)
        {
            // TODO: show user invalid input message
            return "0";
        }
    }

    @Override
    public String convert(Date input)
    {
        Date date = input;
        long ms = date.getTime();
        long sec = (ms / 1000l);

        logger.debug(input + " " + sec);
        return Long.valueOf(sec).toString();
    }

}
