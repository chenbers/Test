package com.inthinc.pro.dao.jdbc;

import it.config.ITDataSource;
import it.config.IntegrationConfig;
import junit.framework.Assert;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for {@link DriverJDBCDAO}.
 */
public class LocationJDBCDAOTest {
    static LocationJDBCDAO locationJDBCDAO;

    @BeforeClass
    public static void setupOnce() {
        IntegrationConfig config = new IntegrationConfig();
        locationJDBCDAO = new LocationJDBCDAO();
        locationJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
    }

    /**
     * Tests that list work.
     */
    @Test
    public void testSumMiles() {
        Integer aDriverId = locationJDBCDAO.getSimpleJdbcTemplate().queryForInt("select a.driverID from " +
                "(select sum(coalesce(mileage,0)) mileageSum, driverID from trip " +
                "where startTime > CURRENT_DATE() - 10 group by driverID) a " +
                "where mileageSum > 0 limit 1");

        Integer foundTripMileage = locationJDBCDAO.getTripMileageCountForDriver(aDriverId, new DateTime().minusDays(10).toDate(), new DateTime().toDate());
        Assert.assertNotNull(foundTripMileage);
        Assert.assertTrue(foundTripMileage > 0);
    }
}
