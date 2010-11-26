package com.inthinc.pro.service.phonecontrol.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.Response;

import com.inthinc.pro.service.phonecontrol.Clock;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.client.ZoomsaferEndPoint;

/**
 * {@link PhoneControlAdapter} implementation for Cellcontrol service provider.
 */
public class ZoomsaferAdapter implements PhoneControlAdapter {

    public static final String ZOOM_SAFER_TIMESTAMP_FORMAT = "yyyy.MM.dd HH:mm:ss.SSS Z";

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
        Date now = clock.getNow();
        Response resp = zoomsaferEndpoint.disablePhone(cellPhoneNumber, this.dateFormatter.format(now));
        System.out.println(resp);
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.PhoneControlAdapter#enablePhone(java.lang.String)
     */
    @Override
    public void enablePhone(String cellPhoneNumber) {
        Date now = clock.getNow();
        Response resp = zoomsaferEndpoint.enablePhone(cellPhoneNumber, this.dateFormatter.format(now));
        System.out.println(resp);
    }
}