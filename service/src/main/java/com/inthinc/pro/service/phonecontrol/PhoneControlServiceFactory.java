package com.inthinc.pro.service.phonecontrol;

import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.service.phonecontrol.client.PhoneControlService;

/**
 * Factory for phone control service endpoints.
 */
public interface PhoneControlServiceFactory {

    /**
     * Creates a service client's endpoint for the supplied provider type.
     * 
     * @param providerType
     *            The provider type to create a service client endpoint for.
     * 
     * @return A client endpoint for the respective remote {@link PhoneControlService}.
     */
    PhoneControlService createServiceEndpoint(CellProviderType providerType);
}
