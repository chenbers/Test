package com.inthinc.pro.service.phonecontrol;

import com.inthinc.pro.model.phone.CellProviderType;

/**
 * {@link PhoneControlAdapter} is an adapter for the various service endpoints implementation of available {@link CellProviderType}s.
 * <p/>
 * All requests are idempotent (i.e. multiple calls for the same number have no additional effect). No error is returned from any of this class' calls. All requests are treated as
 * successful.
 */
public interface PhoneControlAdapter {

    /**
     * Sends a request to disable a phone.
     * 
     * @param cellPhoneNumber
     *            The number of the cell phone to be disabled.
     */
    // TODO This signature will likely change once the Retry user story is implemented.
    // Either a value will be returned or an exception will be thrown.
    void disablePhone(String cellPhoneNumber);

    /**
     * Sends a request to enable a phone.
     * 
     * @param cellPhoneNumber
     *            The number of the cell phone to be enabled.
     */
    // TODO This signature will likely change once the Retry user story is implemented.
    // Either a value will be returned or an exception will be thrown.
    void enablePhone(String cellPhoneNumber);
}
