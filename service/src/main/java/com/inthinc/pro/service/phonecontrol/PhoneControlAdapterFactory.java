package com.inthinc.pro.service.phonecontrol;

import com.inthinc.pro.model.phone.CellProviderType;

/**
 * Factory for phone control service endpoints.
 */
public interface PhoneControlAdapterFactory {

    /**
     * Creates a service client's endpoint for the supplied provider type.
     * 
     * @param providerType
     *            The provider type to create a service client endpoint for.
     * @param username
     *            The username for the Cellcontrol/Zoomsafer endpoint.
     * @param password
     *            The password for the Cellcontrol/Zoomsafer endpoint.
     * 
     * @return A client endpoint for the respective remote {@link PhoneControlAdapter}.
     */
    PhoneControlAdapter createAdapter(CellProviderType providerType, String username, String password);
}
