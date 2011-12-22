package it.com.inthinc.pro.dao;

import static org.junit.Assert.assertEquals;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.IntegrationConfig;
import it.util.MCMSimulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.DriverStopReport;
import com.inthinc.pro.model.DriverStops;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;


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

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());

        siloService = new SiloServiceCreator(host, port).getService();
        
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));


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
        
        // generate data
        GroupData team = itData.teamGroupData.get(ITData.INTERMEDIATE);
        
        DateTimeZone dateTimeZone = DateTimeZone.getDefault();
        DateTime currentDay = new DateMidnight(new Date(), dateTimeZone).toDateTime();
        Interval interval = new Interval(currentDay.minusDays(1), currentDay);
            
        List<Event> eventList = eventDAO.getEventsForDriver(team.driver.getDriverID(), interval.getStart().toDate(), interval.getEnd().toDate(), 
                new ArrayList<NoteType>(), 1);
        if (eventList == null || eventList.size() == 0) 
            generateNotes("stops" + File.separator + "driverNotes.csv", team.device.getImei());
        
        List<DriverStops> stopsList = driverDAO.getStops(team.driver.getDriverID(), interval);

        assertEquals("Unexpected number of driverStops records.", expectedDriverStops.length, stopsList.size());
        int cnt = 0;
        for (DriverStops stop : stopsList) {
            DriverStops expected = expectedDriverStops[cnt++];
            assertEquals(cnt + " driverID", expected.getDriverID(), stop.getDriverID());
            assertEquals(cnt + " DriveTime", expected.getDriveTime(), stop.getDriveTime());
            assertEquals(cnt + " IdleHi", expected.getIdleHi(), stop.getIdleHi());
            assertEquals(cnt + " IdleLo", expected.getIdleLo(), stop.getIdleLo());
            assertEquals(cnt + " VehicleID", expected.getVehicleID(), stop.getVehicleID());
            assertEquals(cnt + " ZoneName", expected.getZoneName(), stop.getZoneName());
        }
        
        
        DriverStopReport driverStopReport = new DriverStopReport(team.driver.getDriverID(), "", TimeFrame.ONE_DAY_AGO, stopsList);
        
        assertEquals("Total DriveTime", expectedDriveTimeTotal, driverStopReport.getDriveTimeTotal());
        assertEquals("Total IdleHi", expectedIdleHiTotal,driverStopReport.getIdleHiTotal());
        assertEquals("Total IdleLo", expectedIdleLoTotal, driverStopReport.getIdleLoTotal());
        assertEquals("Total Wait", expectedWaitTotal, driverStopReport.getWaitTotal());
        
    }



    // TODO: Put in it's own class
    private void generateNotes(String path, String imei)
    {
        
        BufferedReader in;
        try {

            Date maxDate = new Date(0);
            List<String[]> lines = new ArrayList<String[]>();
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        
            in = new BufferedReader(new InputStreamReader(stream));
            //  skip first line of titles
            in.readLine();
            String str; 
            while ((str = in.readLine()) != null) {  
                String values[] = str.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
                for (int i = 0; i < values.length; i++) {
                    if (values[i].startsWith("\"") && values[i].endsWith("\"")) {
                        values[i] = values[i].substring(1, values[i].length()-1);
                    }
                }
                lines.add(values);
                Date noteTime = getNoteTime(values[TIME_COL]);
                if (noteTime.after(maxDate))
                    maxDate = noteTime;
            }
        
            System.out.println("maxDate: " + maxDate);
            DateTime currentDay = new DateMidnight(new Date()).toDateTime();
            int dayOffset = (int)((currentDay.toDate().getTime() - maxDate.getTime()) / DateUtil.MILLISECONDS_IN_DAY);
            Collections.reverse(lines);
            for (String[] values : lines)
                genEvent(values, imei, dayOffset);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    // attrMap,avgSpeed,created,deviceID,distance,driverID,forgiven,groupID,heading,lat,lng,maprev,noteID,odometer,sats,speed,speedLimit,state,time,topSpeed,type,vehicleID
    private static int ATTR_MAP_COL = 0;
    private static int AVG_SPEED_COL = 1;
    private static int DISTANCE_COL = 4;
    private static int LAT_COL = 9;
    private static int LNG_COL = 10;
    private static int MAP_REV_COL = 11;
    private static int ODOMETER_COL = 13;
    private static int SATS_COL = 14;
    private static int SPEED_COL = 15;
    private static int SPEEDLIMIT_COL = 16;
    private static int STATE_COL = 17;
    private static int TIME_COL = 18;
    private static int TOP_SPEED_COL = 19;
    private static int TYPE_COL = 20;
    
    
    private static DateTimeFormatter NOTE_DATE_FORMAT = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
    private Date getNoteTime(String dateString) {
        return NOTE_DATE_FORMAT.parseDateTime(dateString).toDate();
    }
    private Date getNoteTime(String dateString, int dayOffset) {
        
        return new Date(getNoteTime(dateString).getTime() + dayOffset * DateUtil.MILLISECONDS_IN_DAY);
    }

    private NoteType getNoteType(String noteTypeStr) {
        int i1 = noteTypeStr.indexOf("(");
        int i2 = noteTypeStr.indexOf(")");
        if (i1 != -1 && i2 != -1) {
            
            return NoteType.valueOf(Integer.valueOf(noteTypeStr.substring(i1+1, i2)));
        }
        return NoteType.BASE;
    }

    private boolean genEvent(String values[], String imei, Integer dayOffset) {
        List<byte[]> noteList = new ArrayList<byte[]>();

        byte[] eventBytes = createDataBytesFromEventValues(values, dayOffset);
        noteList.add(eventBytes);
        boolean errorFound = false;
        int retryCnt = 0;
        while (!errorFound) {
            try {
                mcmSim.note(imei, noteList);
                break;
            }
            catch (ProxyException ex) {
                if (ex.getErrorCode() != 414) {
                    System.out.print("RETRY EVENT GEN because: " + ex.getErrorCode() + "");
                    errorFound = true;
                }
                else {
                    if (retryCnt == 300) {
                        System.out.println("Retries failed after 5 min.");
                        errorFound = true;
                    }
                    else {
                        if (retryCnt == 0)
                            System.out.println("waiting for IMEI to show up in central server");
                        try {
                            Thread.sleep(1000l);
                            retryCnt++;
                        }
                        catch (InterruptedException e) {
                            errorFound = true;
                            e.printStackTrace();
                        }
                        System.out.print(".");
                        if (retryCnt % 25 == 0)
                            System.out.println();
                    }
                }
            }
            catch (RemoteServerException re) {
                re.printStackTrace();
                if (re.getErrorCode() != 302 ) {
                    errorFound = true;
                }
                
            }
            catch (Exception e) {
                e.printStackTrace();
                errorFound = true;
            }
        }
        return !errorFound;
    }

    private final static int ATTR_TYPE_TOP_SPEED=1;
    private final static int ATTR_TYPE_AVG_SPEED=2;
    private final static int ATTR_TYPE_SPEED_LIMIT=3;
    private final static int ATTR_TYPE_AVG_RPM   =  4;
    private final static int ATTR_TYPE_RESET_REASON =5;
    private final static int ATTR_TYPE_MANRESET_REASON= 6;
    private final static int ATTR_TYPE_FWDCMD_STATUS= 7;
    private final static int ATTR_TYPE_SEVERITY=   24;
                                                        // 128-192 2 byte values
    private final static int ATTR_TYPE_DISTANCE =129;
    private final static int ATTR_TYPE_MAX_RPM =  130;
    private final static int ATTR_TYPE_DELTAVX =  131;
    private final static int ATTR_TYPE_DELTAVY =  132;
    private final static int ATTR_TYPE_DELTAVZ =  133;
    private final static int ATTR_TYPE_MPG = 149;
    private final static int ATTR_TYPE_GPS_QUALITY = 166;
                                                            // 192 up has 4 byte values
    private final static int ATTR_TYPE_ZONE_ID =  192;
    private final static int ATTR_SPEED_ID = 201;
    private final static int ATTR_TYPE_LO_IDLE  = 219;
    private final static int ATTR_TYPE_HI_IDLE  = 220;
    private final static int ATTR_TYPE_MPG_DISTANCE  = 224;
    private final static int ATTR_TYPE_DRIVETIME  = 225;

    Date lastNoteTime = new Date();

    public byte[] createDataBytesFromEventValues(String values[], Integer dayOffset)
    {
        NoteType noteType = getNoteType(values[TYPE_COL]);
        Date noteTime = getNoteTime(values[TIME_COL], dayOffset);
//        if (noteTime.equals(lastNoteTime))
//            noteTime = new Date(noteTime.getTime() - 1000l);
//        lastNoteTime = noteTime;
        System.out.println(noteTime + " " + noteType);
        Integer avgSpeed = intValue(values[AVG_SPEED_COL]);
        Integer distance = intValue(values[DISTANCE_COL]);
        Double lat = Double.valueOf(values[LAT_COL]);
        Double lng = Double.valueOf(values[LNG_COL]);
        Integer mapRev = intValue(values[MAP_REV_COL]);
        Integer odometer = intValue(values[ODOMETER_COL]);
        Integer sats = intValue(values[SATS_COL]);
        Integer speed = intValue(values[SPEED_COL]);
        Integer speedLimit = intValue(values[SPEEDLIMIT_COL]);
        Integer topSpeed = intValue(values[TOP_SPEED_COL]);
        
        byte[] eventBytes = new byte[200];
        int idx = 0;
        eventBytes[idx++] = (byte) (noteType.getCode() & 0x000000FF);
        idx = puti4(eventBytes, idx, (int)(noteTime.getTime()/1000l));
        if (sats == null || sats.intValue() == 0)
            sats = 10;
        eventBytes[idx++] = (byte) (sats & 0x000000FF);
        eventBytes[idx++] = (byte) (mapRev & 0x000000FF); 
        idx = putlat(eventBytes, idx, lat);
        idx = putlng(eventBytes, idx, lng);
        eventBytes[idx++] = (byte) (speed & 0x000000FF);
        idx = puti2(eventBytes, idx, odometer);
        eventBytes[idx++] = (byte) (ATTR_TYPE_SPEED_LIMIT & 0x000000FF);
        eventBytes[idx++] = (byte) ((speedLimit==null ? 55 : speedLimit) & 0x000000FF);
        
        Map<Integer, String> attrMap = parseAttrMap(values[ATTR_MAP_COL]);

        
        if (noteType == NoteType.SPEEDING_EX3)
        {
            eventBytes[idx++] = (byte) (ATTR_TYPE_AVG_SPEED & 0x000000FF);
            eventBytes[idx++] = (byte) (avgSpeed & 0x000000FF);
            eventBytes[idx++] = (byte) (ATTR_TYPE_TOP_SPEED & 0x000000FF);
            eventBytes[idx++] = (byte) (topSpeed & 0x000000FF);
            eventBytes[idx++] = (byte) (ATTR_TYPE_DISTANCE & 0x000000FF);
            idx = puti2(eventBytes, idx, distance);
        }
        else if (noteType == NoteType.SEATBELT)
        {
            eventBytes[idx++] = (byte) (ATTR_TYPE_AVG_SPEED & 0x000000FF);
            eventBytes[idx++] = (byte) (avgSpeed & 0x000000FF);
            eventBytes[idx++] = (byte) (ATTR_TYPE_TOP_SPEED & 0x000000FF);
            eventBytes[idx++] = (byte) (topSpeed & 0x000000FF);
            eventBytes[idx++] = (byte) (ATTR_TYPE_DISTANCE & 0x000000FF);
            idx = puti2(eventBytes, idx, distance);
        }
        else if (noteType == NoteType.NOTEEVENT)
        {
            eventBytes[idx++] = (byte) (ATTR_TYPE_AVG_SPEED & 0x000000FF);
            eventBytes[idx++] = (byte) (speed & 0x000000FF);
            eventBytes[idx++] = (byte) (ATTR_TYPE_DELTAVX & 0x000000FF);
            idx = puti2(eventBytes, idx, intValue(attrMap.get(ATTR_TYPE_DELTAVX)));
            eventBytes[idx++] = (byte) (ATTR_TYPE_DELTAVY & 0x000000FF);
            idx = puti2(eventBytes, idx, intValue(attrMap.get(ATTR_TYPE_DELTAVY)));
            eventBytes[idx++] = (byte) (ATTR_TYPE_DELTAVZ & 0x000000FF);
            idx = puti2(eventBytes, idx, intValue(attrMap.get(ATTR_TYPE_DELTAVZ)));
        }
        else if (noteType == NoteType.FULLEVENT)
        {
            eventBytes[idx++] = (byte) (ATTR_TYPE_AVG_SPEED & 0x000000FF);
            eventBytes[idx++] = (byte) (speed & 0x000000FF);
            eventBytes[idx++] = (byte) (ATTR_TYPE_DELTAVX & 0x000000FF);
            idx = puti2(eventBytes, idx, intValue(attrMap.get(ATTR_TYPE_DELTAVX)));
            eventBytes[idx++] = (byte) (ATTR_TYPE_DELTAVY & 0x000000FF);
            idx = puti2(eventBytes, idx, intValue(attrMap.get(ATTR_TYPE_DELTAVY)));
            eventBytes[idx++] = (byte) (ATTR_TYPE_DELTAVZ & 0x000000FF);
            idx = puti2(eventBytes, idx, intValue(attrMap.get(ATTR_TYPE_DELTAVZ)));
            eventBytes[idx++] = (byte) (ATTR_TYPE_GPS_QUALITY & 0x000000FF);
            idx = puti2(eventBytes, idx, intValue(attrMap.get(ATTR_TYPE_GPS_QUALITY)));
        }
       else if (noteType == NoteType.WSZONES_ARRIVAL_EX ||
                noteType == NoteType.WSZONES_DEPARTURE_EX)
        {
            eventBytes[idx++] = (byte) (ATTR_TYPE_ZONE_ID & 0x000000FF);
            idx = puti4(eventBytes, idx, intValue(attrMap.get(ATTR_TYPE_ZONE_ID)));
        }
        else if (noteType == NoteType.IGNITION_OFF)
        {
            eventBytes[idx++] = (byte) (ATTR_TYPE_MPG & 0x000000FF);
            idx = puti2(eventBytes, idx, intValue(attrMap.get(ATTR_TYPE_MPG)));
            eventBytes[idx++] = (byte) (ATTR_TYPE_MPG_DISTANCE & 0x000000FF);
            idx = puti4(eventBytes, idx, intValue(attrMap.get(ATTR_TYPE_MPG_DISTANCE)));
            eventBytes[idx++] = (byte) (ATTR_TYPE_DRIVETIME & 0x000000FF);
            idx = puti4(eventBytes, idx, intValue(attrMap.get(ATTR_TYPE_DRIVETIME)));
            eventBytes[idx++] = (byte) (ATTR_TYPE_GPS_QUALITY & 0x000000FF);
            idx = puti2(eventBytes, idx, intValue(attrMap.get(ATTR_TYPE_GPS_QUALITY)));
            
        }
        else if (noteType == NoteType.IDLE)
        {
            eventBytes[idx++] = (byte) (ATTR_TYPE_LO_IDLE & 0x000000FF);
            idx = puti4(eventBytes, idx, Integer.valueOf(attrMap.get(ATTR_TYPE_LO_IDLE)));
            eventBytes[idx++] = (byte) (ATTR_TYPE_HI_IDLE & 0x000000FF);
            idx = puti4(eventBytes, idx, Integer.valueOf(attrMap.get(ATTR_TYPE_HI_IDLE)));
        }
//        else if (event.getType().equals(NoteType.COACHING_SPEEDING))
//        {
//            eventBytes[idx++] = (byte) (ATTR_SPEED_ID & 0x000000FF);
//            idx = puti4(eventBytes, idx, 2); //DateUtil.convertMillisecondsToSeconds(event.getTime().getTime()));
//        }
        return Arrays.copyOf(eventBytes, idx);
    }
    
    private Integer intValue(String str) {
        if (str == null || str.isEmpty())
            return 0;
        
        return Integer.valueOf(str);
    }

    //"{(231)=11142556, zoneID(192)=449}",,
    private Map<Integer, String> parseAttrMap(String string) {
        
        Map<Integer, String> attrMap = new HashMap<Integer, String>();
        String attrValues[] = string.split(",");

        for (String attrStr : attrValues) {
            int i1 = attrStr.indexOf("(");
            int i2 = attrStr.indexOf(")");
            if (i1 != -1 && i2 != -1) {
                Integer key = Integer.valueOf(attrStr.substring(i1+1, i2));
                int i3 = attrStr.indexOf("=");
                String value = attrStr.substring(i3+1);
                if (value.endsWith("}"))
                    value = value.substring(0, value.length()-1);
                
                attrMap.put(key, value);
            }
        }
        
        return attrMap;
    }

    private static int putlat(byte[] eventBytes, int idx, Double latitude)
    {
        latitude = 90.0 - latitude;
        latitude = latitude / 180.0;

        return putlatlng(eventBytes, idx, latitude);
    }

    private static int putlng(byte[] eventBytes, int idx, Double longitude)
    {
        if (longitude < 0.0)
            longitude = longitude + 360;
        longitude = longitude / 360.0;

        return putlatlng(eventBytes, idx, longitude);
    }

    private static int putlatlng(byte[] eventBytes, int idx, Double latlng)
    {
        int i = (int) (latlng * 0x00ffffff);
        eventBytes[idx++] = (byte) ((i >> 16) & 0x000000FF);
        eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
        eventBytes[idx++] = (byte) (i & 0x000000FF);
        return idx;
    }
    private static int putlat4(byte[] eventBytes, int idx, Double latitude)
    {
        latitude = 90.0 - latitude;
        latitude = latitude / 180.0;

        return putlatlng4(eventBytes, idx, latitude);
    }

    private static int putlng4(byte[] eventBytes, int idx, Double longitude)
    {
        if (longitude < 0.0)
            longitude = longitude + 360;
        longitude = longitude / 360.0;

        return putlatlng4(eventBytes, idx, longitude);
    }

    private static int putlatlng4(byte[] eventBytes, int idx, Double latlng)
    {
        int i = (int) (latlng * 0x00ffffff);
        eventBytes[idx++] = (byte) ((i >> 24) & 0x000000FF);
        eventBytes[idx++] = (byte) ((i >> 16) & 0x000000FF);
        eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
        eventBytes[idx++] = (byte) (i & 0x000000FF);
        return idx;
    }

    private static int puti4(byte[] eventBytes, int idx, Integer i)
    {
        eventBytes[idx++] = (byte) ((i >> 24) & 0x000000FF);
        eventBytes[idx++] = (byte) ((i >> 16) & 0x000000FF);
        eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
        eventBytes[idx++] = (byte) (i & 0x000000FF);
        return idx;
    }

    private static int puti2(byte[] eventBytes, int idx, Integer i)
    {
        eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
        eventBytes[idx++] = (byte) (i & 0x000000FF);
        return idx;
    }


}
