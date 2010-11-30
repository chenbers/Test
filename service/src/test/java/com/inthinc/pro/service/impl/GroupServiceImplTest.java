package com.inthinc.pro.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import mockit.Cascading;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.DriverLocation;

/**
 * Unit Test for GroupServiceImpl class.
 */
public class GroupServiceImplTest extends BaseUnitTest {
    
    private final Integer groupID = 1;
    
    @Mocked private DriverDAO driverDaoMock;
    @Cascading private DriverLocation locationMock;
    
    private GroupServiceImpl serviceSUT = new GroupServiceImpl();
    
    /**
     * Test if the right API is used when getGroupDriverLocations() is called.
     * Test if the Response returns what is expected.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetGroupDriverLocations(){
        
        serviceSUT.setDriverDAO(driverDaoMock);
        
        new Expectations() {
            {
                driverDaoMock.getDriverLocations(groupID); returns(new ArrayList<DriverLocation>());
                
                List<DriverLocation> list = new ArrayList<DriverLocation>();
                list.add(locationMock);                
                driverDaoMock.getDriverLocations(groupID); returns(list);
            }
        };
        // check when empty list
        Response response = serviceSUT.getGroupDriverLocations(groupID);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        
        // check the content
        GenericEntity<List<DriverLocation>> entity = (GenericEntity<List<DriverLocation>>) response.getEntity();        
        assertNull(entity);

        // check when at least an element is returned
        response = serviceSUT.getGroupDriverLocations(groupID);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        // check the content
        entity = (GenericEntity<List<DriverLocation>>) response.getEntity();        
        assertNotNull(entity);
        assertEquals(1, entity.getEntity().size());
        assertEquals(locationMock, entity.getEntity().get(0));
    }
    
    /*TODO to add tests for:
     *create()
     *getDriverScores()
     *getVehicleScores()
     *getSubGroupsDriverTrends()
     *getSubGroupsDriverScores()
     *getGroupDriverLocations()
     */
}
