package com.inthinc.pro.dao.hessian;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.UnitTestStats;
import com.inthinc.pro.dao.mock.proserver.CentralServiceCreator;
import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Trip;

public class TripHessianDAOTest
{
    TripHessianDAO tripDAO;
    
    @Before
    public void setUp() throws Exception
    {
        tripDAO = new TripHessianDAO();
        tripDAO.setSiloService(new SiloServiceCreator().getService());

    }
    
    @Test
    public void getTrips()
    {
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, 7);

        List<Trip>  tripList = tripDAO.getTrips(UnitTestStats.UNIT_TEST_DRIVER_ID, startDate, endDate);
        
        int numTrips = tripList.size();
        assertEquals(MockData.unitTestStats.totalTripsForDriver, numTrips);
        assertEquals(MockData.unitTestStats.totalEventsInLastTripForDriver, tripList.get(numTrips-1).getEvents().size());
    }

}
