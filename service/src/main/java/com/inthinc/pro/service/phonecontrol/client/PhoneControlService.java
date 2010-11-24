package com.inthinc.pro.service.phonecontrol.client;

import com.inthinc.pro.model.phone.CellProviderType;

/**
 * {@link PhoneControlService} is an adapter for the various service endpoints implementation of available {@link CellProviderType}s.
 */
public interface PhoneControlService {

    /**
     * Sends a request to disable a phone.
     * <p/>
     * Requests are idempotent (i.e. multiple calls for the same number have no additional effect). It can be assumed that the requests are always successfully sent.
     * 
     * @param cellPhoneNumber
     *            The number of the cell phone to be disabled.
     */
    // TODO This signature will likely change once the Retry user story is implemented.
    // Either a value will be returned or an exception will be thrown.
    void disablePhone(String cellPhoneNumber);
}
