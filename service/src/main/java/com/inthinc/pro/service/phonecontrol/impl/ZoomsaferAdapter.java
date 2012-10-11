package com.inthinc.pro.service.phonecontrol.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.inthinc.pro.service.exceptions.RemoteErrorException;
import com.inthinc.pro.service.phonecontrol.Clock;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.client.ZoomsaferEndPoint;

/**
 * {@link PhoneControlAdapter} implementation for Cellcontrol service provider.
 */
public class ZoomsaferAdapter implements PhoneControlAdapter {

    private static final Logger logger = Logger.getLogger(ZoomsaferAdapter.class);

    public static final String ZOOM_SAFER_TIMESTAMP_FORMAT = "yyyy.MM.dd HH:mm:ss.SSS";

    private ZoomsaferEndPoint zoomsaferEndpoint;
    private Clock clock;
    private SimpleDateFormat dateFormatter;

    /**
     * Creates a new {@link ZoomsaferAdapter} instance.
     * 
     * @param zoomsaferEndpoint
     *            The {@link ZoomsaferEndPoint} stub to Zoomsafer service.
     * @param clock
     *            The clock implementation to use. The clock is used to get the current timestamp required by Zoomsafer api.
     */
    public ZoomsaferAdapter(ZoomsaferEndPoint zoomsaferEndpoint, Clock clock) {
        this.zoomsaferEndpoint = zoomsaferEndpoint;
        this.clock = clock;
        this.dateFormatter = new SimpleDateFormat(ZOOM_SAFER_TIMESTAMP_FORMAT);
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.PhoneControlAdapter#disablePhone(java.lang.String)
     */
    @Override
    public void disablePhone(String cellPhoneNumber) {
        Response response = zoomsaferEndpoint.disablePhone(ZoomsaferEndPoint.DISABLE_PHONE_EVENT_TYPE, cellPhoneNumber, getFormattedTime());
        logger.debug("A request was sent to Zoomsafer endpoint to disable PH#-" + cellPhoneNumber + ". Response status = " + response.getStatus() + ".");

        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new RemoteErrorException("Request to Zoomsafer endpoint returned an unexpected status code: " + response.getStatus());
        }
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.PhoneControlAdapter#enablePhone(java.lang.String)
     */
    @Override
    public void enablePhone(String cellPhoneNumber) {
         Response response = zoomsaferEndpoint.enablePhone(ZoomsaferEndPoint.ENABLE_PHONE_EVENT_TYPE, cellPhoneNumber, getFormattedTime());
        logger.debug("A request was sent to Zoomsafer endpoint to enable PH#-" + cellPhoneNumber + ". Response status = " + response.getStatus() + ".");

        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new RemoteErrorException("Request to Zoomsafer endpoint returned an unexpected status code: " + response.getStatus());
        }
    }
    private String getFormattedTime(){
        Date now = clock.getNow();
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormatter.format(now);
 
    }
}