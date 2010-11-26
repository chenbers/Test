package com.inthinc.pro.service.phonecontrol.impl;

import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.client.CellcontrolEndpoint;

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
        cellcontrolEndpoint.disablePhone(cellPhoneNumber);
        // TODO Add logging
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.PhoneControlAdapter#enablePhone(java.lang.String)
     */
    // TODO Return type or exception handling might change once retry user story is implemented.
    @Override
    public void enablePhone(String cellPhoneNumber) {
        cellcontrolEndpoint.enablePhone(cellPhoneNumber);
        // TODO Add logging
    }
}
