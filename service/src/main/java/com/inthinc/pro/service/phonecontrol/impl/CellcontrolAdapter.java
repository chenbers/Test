package com.inthinc.pro.service.phonecontrol.impl;

import javax.ws.rs.core.Response;

import com.inthinc.pro.service.client.CellcontrolEndpoint;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;

/**
 * {@link PhoneControlAdapter} implementation for Cellcontrol service provider.
 */
public class CellcontrolAdapter implements PhoneControlAdapter {

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
        // TODO Add logging
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.PhoneControlAdapter#enablePhone(java.lang.String)
     */
    // TODO Return type or exception handling might change once retry user story is implemented.
    @Override
    public void enablePhone(String cellPhoneNumber) {
        Response response = cellcontrolEndpoint.enablePhone(cellPhoneNumber);
        // TODO Add logging
    }
}
