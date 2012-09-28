package it.com.inthinc.pro.dao;

import static org.junit.Assert.assertEquals;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.IntegrationConfig;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.LocationHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.DriverStopReport;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.notegen.MCMSimulator;
import com.inthinc.pro.notegen.NoteFileParser;
import com.inthinc.pro.notegen.NoteGenerator;
import com.inthinc.pro.notegen.TiwiProNoteSender;
import com.inthinc.pro.notegen.WSNoteSender;


public class TeamStopsTest {
//    private static final Logger logger = Logger.getLogger(TeamStopsTest.class);
    private static SiloService siloService;
    private static ITData itData;
    private static MCMSimulator mcmSim;
    

    private static final String BASE_DATA_XML = "TeamStops.xml";

    DriverStops[] expectedDriverStops = {
        new DriverStops(11771, 12750, "VehicleINTERMEDIATE", 34.66848d, -92.340576d, 0l, null, 1314706917l, 0, 0, null),
        new DriverStops(11771, 12750, "VehicleINTERMEDIATE", 34.66848d, -92.340576d, 0l, 1314706917l, 1314706980l, 68, 18, null),
        new DriverStops(11771, 12750, "VehicleINTERMEDIATE", 34.670361d, -92.377899d, 328l, 1314707308l, 1314707640l, 332, 0, null),
        new DriverStops(11771, 12750, "VehicleINTERMEDIATE", 34.62093d, -92.49765d, 536l, 1314708176l, 1314708240l, 64, 0, null),
        new DriverStops(11771, 12750, "VehicleINTERMEDIATE", 34.623398d, -92.499512d, 347l, 1314708587l, null, 73, 0, "Zone With Alerts")
    };
    
    Integer expectedIdleLoTotal = 464;
    Integer expectedIdleHiTotal = 18;
    Integer expectedWaitTotal = 0;
    Long expectedDriveTimeTotal = 1211l;
    
    private static NoteGenerator noteGenerator;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();
        
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));

        noteGenerator = new NoteGenerator();
        WSNoteSender wsNoteSender = new WSNoteSender();
        wsNoteSender.setUrl(config.get(IntegrationConfig.MINA_HOST).toString());
        wsNoteSender.setPort(Integer.valueOf(config.get(IntegrationConfig.MINA_PORT).toString()));
        noteGenerator.setWsNoteSender(wsNoteSender);

        TiwiProNoteSender tiwiProNoteSender = new TiwiProNoteSender();
        tiwiProNoteSender.setMcmSimulator(mcmSim);
        noteGenerator.setTiwiProNoteSender(tiwiProNoteSender);

        initApp();
        itData = new ITData();

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(BASE_DATA_XML);
        if (!itData.parseTestData(stream, siloService, false, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }
        
  }

    private static void initApp() throws Exception {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);

        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

    }

    @Test
    public void stops() {
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        EventHessianDAO eventDAO = new EventHessianDAO();
        eventDAO.setSiloService(siloService);
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        driverDAO.setVehicleDAO(vehicleDAO);
        LocationHessianDAO locationDAO = new LocationHessianDAO();
        locationDAO.setSiloService(siloService);
        locationDAO.setVehicleDAO(vehicleDAO);
        locationDAO.setDriverDAO(driverDAO);
        driverDAO.setLocationDAO(locationDAO);
        vehicleDAO.setLocationDAO(locationDAO);
        
        // generate data
        GroupData team = itData.teamGroupData.get(ITData.INTERMEDIATE);
        
        DateTimeZone dateTimeZone = DateTimeZone.getDefault();
        DateTime currentDay = new DateMidnight(new Date(), dateTimeZone).toDateTime();
        Interval interval = new Interval(currentDay.minusDays(1), currentDay);
            
        NoteFileParser noteFileParser = new NoteFileParser();
        List<Event> eventList = noteFileParser.parseFile("stops" + File.separator + "driverNotes.csv");
        
        offsetEventDates(eventList, new DateMidnight(new Date()).toDateTime());
        System.out.println("team.driver: " + team.driver.getDriverID());
        try {
            noteGenerator.genTrip(eventList, team.device);
        } catch (Exception e) {

            e.printStackTrace();
        }
        List<DriverStops> stopsList = driverDAO.getStops(team.driver.getDriverID(), team.driver.getPerson().getFullName(), interval);

        assertEquals("Unexpected number of driverStops records.", expectedDriverStops.length, stopsList.size());
        int cnt = 0;
        for (DriverStops stop : stopsList) {
            DriverStops expected = expectedDriverStops[cnt++];
            assertEquals(cnt + " DriveTime", expected.getDriveTime(), stop.getDriveTime());
            assertEquals(cnt + " IdleHi", expected.getIdleHi(), stop.getIdleHi());
            assertEquals(cnt + " IdleLo", expected.getIdleLo(), stop.getIdleLo());
        }
        
        
        DriverStopReport driverStopReport = new DriverStopReport(team.group.getName(), team.driver.getDriverID(), "", TimeFrame.ONE_DAY_AGO, stopsList);
        
        assertEquals("Total DriveTime", expectedDriveTimeTotal, driverStopReport.getDriveTimeTotal());
        assertEquals("Total IdleHi", expectedIdleHiTotal,driverStopReport.getIdleHiTotal());
        assertEquals("Total IdleLo", expectedIdleLoTotal, driverStopReport.getIdleLoTotal());
        assertEquals("Total Wait", expectedWaitTotal, driverStopReport.getWaitTotal());
        
    }


    private void offsetEventDates(List<Event> eventList, DateTime offsetFrom) {
        Date maxDate = new Date(0);

        for (Event event : eventList)
            if (event.getTime().after(maxDate))
                maxDate = event.getTime();
        
        System.out.println("maxDate: " + maxDate);
        int dayOffset = (int)((offsetFrom.toDate().getTime() - maxDate.getTime()) / DateUtil.MILLISECONDS_IN_DAY);
        System.out.println("dayOffset: " + dayOffset);

        for (Event event : eventList) {
            DateTime newEventTime = new DateTime(event.getTime()).plusDays(dayOffset);
            event.setTime(newEventTime.toDate());
        }
    }



}
