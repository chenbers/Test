package com.inthinc.pro.service.phonecontrol.impl;

import org.springframework.stereotype.Component;

import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapterFactory;

/**
 * Default implementation of {@link PhoneControlAdapterFactory}. This factory will provide adapter instances to cell phone providers remote endpoints.
 */
@Component
public class DefaultPhoneControlAdapterFactory implements PhoneControlAdapterFactory {

    /**
     * @see com.inthinc.pro.service.phonecontrol.PhoneControlAdapterFactory#createServiceEndpoint(com.inthinc.pro.model.phone.CellProviderType)
     */
    @Override
    public PhoneControlAdapter createServiceEndpoint(CellProviderType providerType) {
        // TODO Auto-generated method stub
        return null;
    }
}
