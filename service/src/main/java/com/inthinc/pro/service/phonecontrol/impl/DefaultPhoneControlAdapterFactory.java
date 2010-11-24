package com.inthinc.pro.service.phonecontrol.impl;

import java.util.Map;

import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapterFactory;

/**
 * Default implementation of {@link PhoneControlAdapterFactory}. This factory will provide adapter instances to cell phone providers remote endpoints.
 */
public class DefaultPhoneControlAdapterFactory implements PhoneControlAdapterFactory {

    private Map<CellProviderType, PhoneControlAdapter> factoryMap;

    /**
     * @see com.inthinc.pro.service.phonecontrol.PhoneControlAdapterFactory#createAdapter(com.inthinc.pro.model.phone.CellProviderType)
     */
    @Override
    public PhoneControlAdapter createAdapter(CellProviderType providerType) {
        return factoryMap.get(providerType);
    }

    public void setFactoryMap(Map<CellProviderType, PhoneControlAdapter> factoryMap) {
        this.factoryMap = factoryMap;
    }
}
