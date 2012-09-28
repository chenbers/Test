package com.inthinc.pro.dao.hessian;


import static org.junit.Assert.*;

import java.util.List;

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
        groupHessianDAO = new GroupHessianDAO();
        groupHessianDAO.setSiloService(new SiloServiceCreator().getService());
    }

    @Test
    public void hierarchy() throws Exception
    {
        List<Group> groupList = groupHessianDAO.getGroupHierarchy(MockData.TOP_ACCOUNT_ID, MockData.TOP_GROUP_ID);
        
        assertEquals(14, groupList.size());
    }
}
