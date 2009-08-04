package com.inthinc.pro.dao.hessian;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.UnitTestStats;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.app.Roles;
import com.inthinc.pro.model.app.States;

public class RedFlagHessianDAOTest
{

    RedFlagHessianDAO      redFlagHessianDAO;


    @Before
    public void setUp() throws Exception
    {
        redFlagHessianDAO = new RedFlagHessianDAO();
        redFlagHessianDAO.setSiloService(new SiloServiceCreator().getService());

        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(new SiloServiceCreator().getService());
        
        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(new SiloServiceCreator().getService());

        Roles roles = new Roles();
        roles.setRoleDAO(roleDAO);
        roles.init();
    }

    
    @Test
    public void getRedFlags()
    {
        List<RedFlag>  redFlagList = redFlagHessianDAO.getRedFlags(UnitTestStats.UNIT_TEST_GROUP_ID, 500);
        
        assertNotNull(redFlagList);
        assertEquals(MockData.unitTestStats.totalRedFlags, redFlagList.size());     
    }

}
