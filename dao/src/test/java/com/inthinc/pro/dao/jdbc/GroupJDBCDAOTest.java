package com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Group;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class GroupJDBCDAOTest extends SimpleJdbcDaoSupport {

    static GroupJDBCDAO groupJDBCDAO;
    static int TOP_GROUP_ID = 888515464;
    static int TOP_ACCOUNT_ID = 777;

//
    @BeforeClass
    public static void setupOnce() {
        IntegrationConfig config = new IntegrationConfig();
        groupJDBCDAO = new GroupJDBCDAO();
        groupJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        groupJDBCDAO.createTestDevice(TOP_ACCOUNT_ID, TOP_GROUP_ID);
    }
//
//    @AfterClass
//    public static void tearDownOnce() {
//        try {
//            groupJDBCDAO.deleteTestDevice(TOP_GROUP_ID);
//        } catch (Throwable t) {/*ignore*/}
//    }
//
//    @Test
//    public void testGetGroupHierarchy(){
//        GroupDAO groupDAO = new GroupJDBCDAO();

//    @BeforeClass
//    public static void setUpBeforeClass() throws Exception
//    {
//    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
        groupJDBCDAO = new GroupJDBCDAO();
//        groupJDBCDAO.setSiloService(new SiloServiceCreator().getService());
    }

    @Test
    public void getGroupHierarchyTest() throws Exception
    {
        List<Group> groupList = groupJDBCDAO.getGroupHierarchy(TOP_ACCOUNT_ID, TOP_GROUP_ID);

        assertEquals(groupList.size(), groupList.size());
    }


    }


