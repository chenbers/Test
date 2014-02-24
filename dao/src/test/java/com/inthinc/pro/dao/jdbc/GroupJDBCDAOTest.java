package com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.model.Group;
import it.config.ITDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GroupJDBCDAOTest extends SimpleJdbcDaoSupport {

    static GroupJDBCDAO groupJDBCDAO;
    static int GROUP_ID = 888515464;
    static int ACCOUNT_ID = 777;

    @BeforeClass
    public static void setupOnce() {
        groupJDBCDAO = new GroupJDBCDAO();
        groupJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        groupJDBCDAO.createTestDevice(ACCOUNT_ID, GROUP_ID);
    }

    @AfterClass
    public static void tearDownOnce() {
        try {
            groupJDBCDAO.deleteTestDevice(GROUP_ID);
        } catch (Throwable t) {/*ignore*/}
    }

    @Test
         public void getGroupHierarchyTest() throws Exception
    {
        List<Group> groupList = groupJDBCDAO.getGroupHierarchy(ACCOUNT_ID, GROUP_ID);

        assertTrue(groupList.size() > 0);
    }

    @Test
    public void getGroupsByAcctIDTest() throws Exception
    {
        List<Group> groupListByAccount = groupJDBCDAO.getGroupsByAcctID(ACCOUNT_ID);

        assertTrue(groupListByAccount.size() > 0);
    }


    }


