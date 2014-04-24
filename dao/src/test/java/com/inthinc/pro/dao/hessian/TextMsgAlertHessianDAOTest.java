package com.inthinc.pro.dao.hessian;


import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.MessageItem;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class TextMsgAlertHessianDAOTest {

    TextMsgAlertHessianDAO tmh;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
        IntegrationConfig integrationConfig = new IntegrationConfig();
        tmh = new TextMsgAlertHessianDAO();
        tmh.setSiloService(new SiloServiceCreator(integrationConfig.getProperty(IntegrationConfig.SILO_HOST),
                        Integer.valueOf(integrationConfig.getProperty(IntegrationConfig.SILO_PORT))).getService());
    }

    @Test
    public void getMsgAlertsByAcctIdTest() throws Exception
    {
        List<MessageItem> messages = tmh.getTextMsgAlertsByAcctID(1);
        assertTrue("Messages were found", messages.size() > 0);
    }

}
