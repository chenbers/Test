package com.inthinc.pro.service.phonecontrol.impl;

import static junit.framework.Assert.assertSame;
import static mockit.Mockit.tearDownMocks;
import mockit.Mocked;
import mockit.Verifications;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.service.phonecontrol.PhoneControlAdapter;
import com.inthinc.pro.service.phonecontrol.client.HttpClientFactory;

public class DefaultPhoneControlAdapterFactoryTest {

    private static final String USERNAME = "uname";
    private static final String PASSWORD = "pwd";

    @Mocked
    private HttpClientFactory cellcontrolHttpClientFactory;
    @Mocked
    private HttpClientFactory zoomsaferHttpClientFactory;

    private DefaultPhoneControlAdapterFactory factorySUT;

    @Before
    public void setUp() {
        factorySUT = new DefaultPhoneControlAdapterFactory(cellcontrolHttpClientFactory, zoomsaferHttpClientFactory, null);
    }

    @After
    public void tearDown() {
        tearDownMocks();
    }

    @Test
    public void testCreateCellcontrolClient() {

        PhoneControlAdapter adapter = factorySUT.createAdapter(CellProviderType.CELL_CONTROL, USERNAME, PASSWORD);

        new Verifications() {
            {
                cellcontrolHttpClientFactory.createHttpClient(USERNAME, PASSWORD);
                times = 1;
                
                zoomsaferHttpClientFactory.createHttpClient(USERNAME, PASSWORD);
                times = 0;
            }
        };
        
        assertSame(CellcontrolAdapter.class, adapter.getClass());
    }

    @Test
    public void testCreateZoomsaferClient() {
        
        PhoneControlAdapter adapter = factorySUT.createAdapter(CellProviderType.ZOOM_SAFER, USERNAME, PASSWORD);
        
        new Verifications() {
            {
                cellcontrolHttpClientFactory.createHttpClient(USERNAME, PASSWORD);
                times = 0;
                
                zoomsaferHttpClientFactory.createHttpClient(USERNAME, PASSWORD);
                times = 1;
            }
        };
        
        assertSame(ZoomsaferAdapter.class, adapter.getClass());
    }
}
