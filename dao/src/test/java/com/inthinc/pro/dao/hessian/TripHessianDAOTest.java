package com.inthinc.pro.dao.hessian;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
        tripDAO.setSiloServiceCreator(new SiloServiceCreator());

    }
    
    @Test
    public void getTrips()
    {
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, 7);

        List<Trip>  tripList = tripDAO.getTrips(1222, startDate, endDate);
        
        assertTrue(tripList.size() > 0);
    }

}
