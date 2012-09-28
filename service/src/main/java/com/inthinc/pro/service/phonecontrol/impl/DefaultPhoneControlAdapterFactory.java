package com.inthinc.pro.service.phonecontrol.impl;

import java.text.MessageFormat;

import org.apache.commons.httpclient.HttpClient;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;

import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.service.phonecontrol.Clock;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapterFactory;
import com.inthinc.pro.service.phonecontrol.client.CellcontrolEndpoint;
import com.inthinc.pro.service.phonecontrol.client.HttpClientFactory;
import com.inthinc.pro.service.phonecontrol.client.ZoomsaferEndPoint;

/**
 * Default implementation of {@link PhoneControlAdapterFactory}. This factory will provide adapter instances to cell phone providers remote endpoints.
 */
public class DefaultPhoneControlAdapterFactory implements PhoneControlAdapterFactory {

    private static final String URL_PATTERN = "http://{0}:{1}";

    private HttpClientFactory cellcontrolHttpConnectionFactory;
    private HttpClientFactory zoomsaferHttpConnectionFactory;
    private Clock systemClock;

    /**
     * Creates an instance of this factory.
     * 
     * @param cellcontrolHttpClientFactory
     *            An {@link HttpClientFactory} setup with Cellcontrol host/port.
     * @param zoomsaferHttpClientFactory
     *            An {@link HttpClientFactory} setup with Zoomsafer host/port.
     * @param systemClock
     *            A {@link Clock} instance.
     */
    public DefaultPhoneControlAdapterFactory(HttpClientFactory cellcontrolHttpClientFactory, HttpClientFactory zoomsaferHttpClientFactory, Clock systemClock) {
        this.cellcontrolHttpConnectionFactory = cellcontrolHttpClientFactory;
        this.zoomsaferHttpConnectionFactory = zoomsaferHttpClientFactory;
        this.systemClock = systemClock;
    }

    /**
     * @see com.inthinc.pro.service.phonecontrol.PhoneControlAdapterFactory#createAdapter(com.inthinc.pro.model.phone.CellProviderType, String, String)
     */
    @Override
    public PhoneControlAdapter createAdapter(CellProviderType providerType, String username, String password) {
        HttpClient httpClient = null;
        ApacheHttpClientExecutor clientExecutor;
        String url = null;

        switch (providerType) {
            case CELL_CONTROL:
                url = MessageFormat.format(URL_PATTERN, cellcontrolHttpConnectionFactory.getHost(), cellcontrolHttpConnectionFactory.getPort());
                httpClient = cellcontrolHttpConnectionFactory.createHttpClient(username, password);
                clientExecutor = new ApacheHttpClientExecutor(httpClient);
                CellcontrolEndpoint cellcontrolEndpoint = ProxyFactory.create(CellcontrolEndpoint.class, url, clientExecutor);
                return new CellcontrolAdapter(cellcontrolEndpoint);
            case ZOOM_SAFER:
                url = MessageFormat.format(URL_PATTERN, zoomsaferHttpConnectionFactory.getHost(), zoomsaferHttpConnectionFactory.getPort());
                httpClient = zoomsaferHttpConnectionFactory.createHttpClient(username, password);
                clientExecutor = new ApacheHttpClientExecutor(httpClient);
                ZoomsaferEndPoint zoomsaferEndpoint = ProxyFactory.create(ZoomsaferEndPoint.class, url, clientExecutor);
                return new ZoomsaferAdapter(zoomsaferEndpoint, systemClock);
            default:
                throw new IllegalArgumentException("Provider type " + providerType + "is not supported.");
        }
    }

    public HttpClientFactory getCellcontrolHttpConnectionFactory() {
        return cellcontrolHttpConnectionFactory;
    }

    public HttpClientFactory getZoomsaferHttpConnectionFactory() {
        return zoomsaferHttpConnectionFactory;
    }

    public Clock getSystemClock() {
        return systemClock;
    }
}
