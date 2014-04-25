package it.com.inthinc.pro.dao.jdbc;


import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.LocationHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.CrashReportJDBCDAO;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.CrashReportStatus;
import com.inthinc.pro.model.Trip;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.sql.DataSource;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CrashReportJDBCDAOTest extends SimpleJdbcDaoSupport {
    private static SiloService siloService;
    private static final String XML_DATA_FILE = "ReportTest.xml";
    private static ITData itData = new ITData();
    private Integer groupId;

    @Before
    public void setUpBeforeTest() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();

        initApp();
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);

        if (!itData.parseTestData(stream, siloService, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }

           groupId = itData.districtGroup.getGroupID();

    }

    @Test
    public void crudTest() {
        CrashReportJDBCDAO crashReportJDBCDAO = new CrashReportJDBCDAO();
        VehicleHessianDAO vehicleHessianDAO = new VehicleHessianDAO();
        vehicleHessianDAO.setSiloService(siloService);

        LocationHessianDAO locationHessianDAO = new LocationHessianDAO();
        locationHessianDAO.setSiloService(siloService);
        vehicleHessianDAO.setLocationDAO(locationHessianDAO);

        DriverHessianDAO driverHessianDAO = new DriverHessianDAO();
        driverHessianDAO.setSiloService(siloService);

        DataSource dataSource = new ITDataSource().getRealDataSource();
        crashReportJDBCDAO.setDataSource(dataSource);
        crashReportJDBCDAO.setVehicleDAO(vehicleHessianDAO);
        crashReportJDBCDAO.setDriverDAO(driverHessianDAO);
        boolean returnCrashID = false;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String date = df.format(toUTC(new Date()));

        CrashReport crashReportToInsert = new CrashReport();
        crashReportToInsert.setDriverID(7909);
        crashReportToInsert.setVehicleID(7977);
        crashReportToInsert.setOccupantCount(0);
        crashReportToInsert.setCrashReportStatus(CrashReportStatus.AGGRESSIVE_DRIVING);
        crashReportToInsert.setHasTrace(0);
        crashReportToInsert.setDate(new Date());
        crashReportToInsert.setLat(32.960110);
        crashReportToInsert.setLng(-117.210274);
        crashReportToInsert.setNoteID(78598764677l);

        Integer createCrashID = crashReportJDBCDAO.create(null, crashReportToInsert);
        returnCrashID = (createCrashID != null);
        assertTrue(returnCrashID);

        // findByGroupID
        List<CrashReport> crashReportList = crashReportJDBCDAO.findByGroupID(groupId);
        assertTrue(crashReportList != null);


        // find by id test method
        CrashReport createdCrashReport = crashReportJDBCDAO.findByID(createCrashID);

        assertEquals("12093", crashReportToInsert.getDriverID(), createdCrashReport.getDriverID());
        assertEquals("23398", crashReportToInsert.getVehicleID(), createdCrashReport.getVehicleID());
        assertEquals("0", crashReportToInsert.getOccupantCount(), createdCrashReport.getOccupantCount());
        assertEquals("CrashReportStatus.AGGRESSIVE_DRIVING", crashReportToInsert.getCrashReportStatus(), createdCrashReport.getCrashReportStatus());
        assertEquals(df.format(toUTC(crashReportToInsert.getDate())),df.format(toUTC(createdCrashReport.getDate())));
        assertEquals("0", crashReportToInsert.getHasTrace(), createdCrashReport.getHasTrace());
        assertEquals("32.960110", crashReportToInsert.getLat(), createdCrashReport.getLat());
        assertEquals("-117.210274", crashReportToInsert.getLng(), createdCrashReport.getLng());
        assertEquals("0", crashReportToInsert.getNoteID(), createdCrashReport.getNoteID());

        //get info for update
        CrashReport crashReportToUpdate = new CrashReport();
        crashReportToUpdate.setDriverID(7909);
        crashReportToUpdate.setVehicleID(7977);
        crashReportToUpdate.setOccupantCount(0);
        crashReportToUpdate.setCrashReportStatus(CrashReportStatus.AGGRESSIVE_DRIVING);
        crashReportToUpdate.setHasTrace(0);
        crashReportToUpdate.setDate(new Date());
        crashReportToUpdate.setLat(32.960110);
        crashReportToUpdate.setLng(-117.210274);
        crashReportToUpdate.setNoteID(82799457797774978l);
        crashReportToUpdate.setCrashReportID(createCrashID);
        //update method
        crashReportJDBCDAO.update(crashReportToUpdate);

        // find and veify again
        createdCrashReport = crashReportJDBCDAO.findByID(crashReportToUpdate.getCrashReportID());

        assertEquals("12093", crashReportToUpdate.getDriverID(), createdCrashReport.getDriverID());
        assertEquals("23398", crashReportToUpdate.getVehicleID(), createdCrashReport.getVehicleID());
        assertEquals("0", crashReportToUpdate.getOccupantCount(), createdCrashReport.getOccupantCount());
        assertEquals("CrashReportStatus.AGGRESSIVE_DRIVING", crashReportToUpdate.getCrashReportStatus(), createdCrashReport.getCrashReportStatus());
        assertEquals(df.format(toUTC(crashReportToUpdate.getDate())),df.format(toUTC(createdCrashReport.getDate())));
        assertEquals("0", crashReportToUpdate.getHasTrace(), createdCrashReport.getHasTrace());
        assertEquals("32.960110", crashReportToUpdate.getLat(), createdCrashReport.getLat());
        assertEquals("-117.210274", crashReportToUpdate.getLng(), createdCrashReport.getLng());
        assertEquals("0", crashReportToUpdate.getNoteID(), createdCrashReport.getNoteID());

        // delete it
        crashReportJDBCDAO.deleteByID(createCrashID);

        // find should not find anything now
        boolean found = true;
        try {
            createdCrashReport = crashReportJDBCDAO.findByID(crashReportToUpdate.getCrashReportID());
        }catch(EmptyResultDataAccessException e){
            // EXPECTED
            found = false;
        }

        assertFalse(found);
    }


    private static void initApp() throws Exception {
    }

    private Date toUTC(Date date) {
        DateTime dt = new DateTime(date.getTime()).toDateTime(DateTimeZone.UTC);
        return dt.toDate();
    }
}
