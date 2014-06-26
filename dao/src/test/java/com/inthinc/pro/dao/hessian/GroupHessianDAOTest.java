package com.inthinc.pro.dao.hessian;


import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.List;

import com.inthinc.pro.dao.hessian.proserver.SiloService;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.IntegrationConfig;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Group;

public class GroupHessianDAOTest
{
    GroupHessianDAO groupHessianDAO;
    private static SiloService siloService;
    private static final String XML_DATA_FILE = "ReportTest.xml";
    private static ITData itData = new ITData();
    private static Integer groupID;
    private static Integer acctID;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator(host, port).getService();

        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);

        if (!itData.parseTestData(stream, siloService, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }

        groupID=itData.fleetGroup.getGroupID();
        acctID=itData.account.getAccountID();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Before
    public void setUp() throws Exception
    {


        groupHessianDAO = new GroupHessianDAO();
        groupHessianDAO.setSiloService(new SiloServiceCreator().getService());
    }

    @Test
    public void hierarchy() throws Exception
    {
        List<Group> groupList = groupHessianDAO.getGroupHierarchy(MockData.TOP_ACCOUNT_ID, MockData.TOP_GROUP_ID);
        
        assertEquals(14, groupList.size());
    }
    @Test
    public void updateTest(){

        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        groupHessianDAO.setSiloService(new com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator(host, port).getService());

        Group testGroup=groupHessianDAO.findByID(groupID);
        testGroup.setGlCode("GLTest");
        groupHessianDAO.update(testGroup);

        List<Group> subGroups = groupHessianDAO.getGroupHierarchy(acctID, groupID);

        for(Group gr:subGroups){
            assertEquals("GLTest", gr.getGlCode());
            gr.setGlCode("");
            groupHessianDAO.update(gr);
        }
    }
}
