package com.inthinc.pro.backing;


import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.MockData;

public class TeamOverviewBeanTest extends BaseBeanTest
{

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    public void driveStylePieDef()
    {
        // TODO: look into why spring isn't doing the injection for us
        TeamOverviewBean teamOverviewBean = new TeamOverviewBean();
        teamOverviewBean.setGraphicDAO(getGraphicDAO());
//        teamOverviewBean.setNavigation((NavigationBean)applicationContext.getBean("navigationBean"));
        NavigationBean navigationBean = new NavigationBean();
        navigationBean.setGroupID(MockData.TOP_GROUP_ID);
        teamOverviewBean.setNavigation(navigationBean);
//        teamOverviewBean.setGroupID(MockData.TOP_GROUP_ID);
        String pieDef = teamOverviewBean.getDriveStylePieDef();
        assertNotNull(pieDef);
        
    }
}
