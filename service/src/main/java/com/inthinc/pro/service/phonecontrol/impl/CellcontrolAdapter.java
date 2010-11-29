package com.inthinc.pro.service.phonecontrol.impl;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.client.CellcontrolEndpoint;

/**
 * {@link PhoneControlAdapter} implementation for Cellcontrol service provider.
 */
public class CellcontrolAdapter implements PhoneControlAdapter {

    private static final Logger logger = Logger.getLogger(CellcontrolAdapter.class);

    private CellcontrolEndpoint cellcontrolEndpoint;

    /**
     * Creates a new {@link CellcontrolAdapter} instance.
     * 
     * @param cellcontrolEndpoint
     *            The {@link CellcontrolEndpoint} stub to Cellcontrol service.
     */
    public CellcontrolAdapter(CellcontrolEndpoint cellcontrolEndpoint) {
        this.cellcontrolEndpoint = cellcontrolEndpoint;
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.PhoneControlAdapter#disablePhone(java.lang.String)
     */
    // TODO Return type or exception handling might change once retry user story is implemented.
    @Override
    public void disablePhone(String cellPhoneNumber) {
        Response response = cellcontrolEndpoint.disablePhone(cellPhoneNumber);
        logger.debug("A request was sent to Cellcontrol endpoint to disable PH#-" + cellPhoneNumber + ". Response status = " + response.getStatus() + ".");
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.PhoneControlAdapter#enablePhone(java.lang.String)
     */
    // TODO Return type or exception handling might change once retry user story is implemented.
    @Override
    public void enablePhone(String cellPhoneNumber) {
        Response response = cellcontrolEndpoint.enablePhone(cellPhoneNumber);
        logger.debug("A request was sent to Cellcontrol endpoint to enable PH#-" + cellPhoneNumber + ". Response status = " + response.getStatus() + ".");
    }
}
