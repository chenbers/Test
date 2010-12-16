package com.inthinc.pro.service.impl;

import static mockit.Mockit.tearDownMocks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;

/**
 * Parent class for all Service unit tests.
 */
public abstract class BaseUnitTest {

    private static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * Generic method to reset all mocked classes after each test.
     */
    @After
    public void resetTest() {
        tearDownMocks();
    }

    protected Date buildDateFromString(String strDate) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date convertedDate = df.parse(strDate);
            return convertedDate;
        } catch (ParseException e) {
            return null;
        }
    }

}
