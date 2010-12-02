package com.inthinc.pro.model.zone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.geotools.data.shapefile.dbf.DbaseFileHeader;
import org.geotools.data.shapefile.dbf.DbaseFileReader;
import org.junit.Test;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.zone.option.ZoneAvailableOption;
import com.inthinc.pro.model.zone.option.ZoneOption;
import com.inthinc.pro.model.zone.option.ZoneOptionType;
import com.inthinc.pro.model.zone.option.type.OffOn;
import com.inthinc.pro.model.zone.option.type.OffOnDevice;
import com.inthinc.pro.model.zone.option.type.OptionValue;
import com.inthinc.pro.model.zone.option.type.SpeedValue;
import com.inthinc.pro.model.zone.option.type.ZoneVehicleType;

public class ZonePublishTest {
    
    private static final Logger logger = Logger.getLogger(ZonePublishTest.class);

    
    @Test
    public void dbfFile() throws IOException {
        List<Zone> zoneList = getMockZoneList();
        ZonePublisher zonePublish = new ZonePublisher();
        
        byte[] published = zonePublish.publish(zoneList, ZoneVehicleType.ALL);
        assertNotNull("publish failed", published);

        
    }
    
    @Test
    public void gainDataDBF() throws IOException, URISyntaxException {
        List<Zone> zoneList = getGainZoneList();
        ZonePublisher zonePublish = new ZonePublisher();

        ByteArrayOutputStream dbfOS = new ByteArrayOutputStream();
        int cnt = zonePublish.getDbase(zoneList, dbfOS, ZoneVehicleType.HEAVY);
        assertEquals("expected 2 zones", 2,  cnt);

        byte[] dbfBytes = dbfOS.toByteArray();
        compareDBF(dbfBytes, "Zones.dbf");
    }

    @Test
    public void gainDataSHPSHX() throws IOException, URISyntaxException {
        List<Zone> zoneList = getGainZoneList();
        ZonePublisher zonePublish = new ZonePublisher();
        ByteArrayOutputStream shpOS = new ByteArrayOutputStream();
        ByteArrayOutputStream shxOS = new ByteArrayOutputStream();
        
        zonePublish.getShpShx(zoneList, shpOS, shxOS, ZoneVehicleType.HEAVY, 2);

        
        byte[] shpBytes = shpOS.toByteArray();
        byte[] shxBytes = shxOS.toByteArray();
        byte[] expectedShpBytes = dumpFile("Zones.shp");
        byte[] expectedShxBytes = dumpFile("Zones.shx");

        assertEquals("shp size", expectedShpBytes.length,  shpBytes.length);
        for (int i = 0; i < expectedShpBytes.length; i++) {
            assertEquals("shp file byte " + i, expectedShpBytes[i], shpBytes[i]);
        }
        
        assertEquals("shx size", expectedShxBytes.length,  shxBytes.length);
        for (int i = 0; i < expectedShxBytes.length; i++) {
            assertEquals("shx file byte " + i, expectedShxBytes[i], shxBytes[i]);
        }
    }

    @Test
    public void gainDataPublish() throws IOException {
        List<Zone> zoneList = getGainZoneList();
        ZonePublisher zonePublish = new ZonePublisher();
        
        byte[] publishBytes = zonePublish.publish(zoneList, ZoneVehicleType.HEAVY);
        assertNotNull("publish failed", publishBytes);
    }


    private void compareDBF(byte[] dbfBytes, String string) throws IOException, URISyntaxException {
        
        DBF gen = parseDbase3File(Channels.newChannel(new ByteArrayInputStream(dbfBytes)));
        DBF expected = expectedDbase3File("Zones.dbf");
        
        assertEquals("dbf sizes differ", expected.data.size(), gen.data.size());
        
        for (int i = 0; i < expected.header.getNumFields(); i++) {
            assertEquals(i + " header name ", expected.header.getFieldName(i), gen.header.getFieldName(i));
            assertEquals(i + " header field len ", expected.header.getFieldLength(i), gen.header.getFieldLength(i));
            assertEquals(i + " header field type ", expected.header.getFieldType(i), gen.header.getFieldType(i));
        }
        
        int rowCnt = 0;
        for (List<Object> expRow : expected.data) {
            List<Object> resultRow = gen.data.get(rowCnt);
            int colCnt = 0;
            for (Object exp : expRow) {
                if (expected.header.getFieldName(colCnt).equals("DOTRULE"))
                    continue;
                Object result = resultRow.get(colCnt);
                
                assertEquals(rowCnt + " " + expected.header.getFieldName(colCnt), exp, result);
                colCnt++;
            }
            
            rowCnt++;
            
        }
        
    }

    private DBF parseDbase3File(ReadableByteChannel in) throws IOException {
        DbaseFileReader r = new DbaseFileReader( in, true, Charset.defaultCharset() );
        DBF dbf = new DBF();
        dbf.header = r.getHeader();
        dbf.data  = new ArrayList<List<Object>>();
        Object[] fields = new Object[r.getHeader().getNumFields()]; 
        while (r.hasNext()) {
            r.readEntry(fields); 
            List<Object> row = new ArrayList<Object> ();
            for (int i = 0; i <  dbf.header.getNumFields(); i++) {
                row.add(fields[i]);
            }
            dbf.data.add(row);
        } 
        r.close();
        in.close();
        return dbf;
    }
    
    class DBF {
        public DbaseFileHeader header;
        List<List<Object>> data;
    }

    private DBF expectedDbase3File(String filename) throws IOException, URISyntaxException {
        ///zonePublish/Zones.dbf

        String path = "zonePublish" + File.separator;
        logger.info("expected dbf path " + (path + filename));
        logger.info("expected dbf URL " + Thread.currentThread().getContextClassLoader().getResource(path + filename));
        File file = new File(Thread.currentThread().getContextClassLoader().getResource(path + filename).toURI());
        FileChannel in = new FileInputStream(file).getChannel();
        return parseDbase3File(in);
    }

    private byte[] dumpFile(String filename) throws IOException, URISyntaxException {
        
        String path = "zonePublish" + File.separator;
        File file = new File(Thread.currentThread().getContextClassLoader().getResource(path + filename).toURI());
        if (file == null) {
            System.out.println(path+filename + " results in null file");
        }
        
        StringBuffer expectedString = new StringBuffer();
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            
            FileInputStream expected = new FileInputStream(file);
            byte[] data = new byte[1024];
            int byteCount;

            // Create a loop that reads from the buffered input stream and writes
            // to the tar output stream until the bis has been entirely read.

            int total = 0;
            while ((byteCount = expected.read(data)) > -1){
                expectedString.append(dumpBytes(data, byteCount));
                os.write(data, 0, byteCount);
                total += byteCount;
            }
            
            return os.toByteArray();
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    
    // from actual data extracted from gain
    private List<Zone> getGainZoneList() {
        List<Zone> zoneList = new ArrayList<Zone>();

        Zone zone2 = new Zone(91580, 1, Status.ACTIVE, "Light-Heavy test 10mph", "", 1);
        zone2.setPoints(getPoints("-112.006066,40.707589,-112.006001,40.702318,-112.005401,40.702318,-112.005401,40.70754,-112.006066,40.707589"));
        zone2.setOptions(getZoneOptions("10,false,false,true,true,false,0,0,true,true,true,true,true,false,0,true,true,true,true,true,false,,91580,false,2,,,"));
        zoneList.add(zone2);

        Zone zone1 = new Zone(12777, 1, Status.ACTIVE, "Tim-Home", "", 1);
        zone1.setPoints(getPoints("-112.0224,40.624752,-112.0221,40.624736,-112.0218,40.624746,-112.021652,40.624748,-112.021641,40.62483,-112.021572,40.624828,-112.021577,40.624756,-112.021472,40.624746,-112.021314,40.624773,-112.02118,40.624807,-112.021086,40.624858,-112.020931,40.624815,-112.020933,40.624724,-112.020968,40.624673,-112.021046,40.624653,-112.021126,40.624661,-112.021212,40.624691,-112.021306,40.624677,-112.021416,40.624667,-112.021518,40.624661,-112.021666,40.624657,-112.021818,40.624663,-112.021987,40.624651,-112.022164,40.624655,-112.022296,40.624657,-112.022403,40.624646,-112.02236,40.624669,-112.0224,40.624752"));
        zone1.setOptions(getZoneOptions("30,false,false,true,true,false,0,0,true,true,true,true,true,false,0,true,true,true,true,true,false,,12777,false,2,,,"));
        zoneList.add(zone1);

        return zoneList;
    }


    private List<ZoneOption> getZoneOptions(String optString) {
//        speed_limit,caution_area,hazmat_area,report_on_arrival,report_on_departure,disable_RF,preferred_comm,position_update,seatbelt_violation,
//        speeding_violation,driverID_violation,ignition_on_event,ignition_off_event,theft_alert,DOT_rule_set,master_buzzer,
//        hard_turn_event,hard_vertical_event,hard_brake_event,monitor_idle,display_on_portal,Flag,IntId,deleted,vehicleType,blast_zone,lightning_present,blast_enabled
        List<ZoneOption> options = new ArrayList<ZoneOption>();
        String[] opts = optString.split(",");
        options.add(new ZoneOption(ZoneAvailableOption.SPEED_LIMIT, new SpeedValue(Integer.valueOf(opts[0]))));
        options.add(new ZoneOption(ZoneAvailableOption.CAUTION_AREA, getOptionValue(ZoneAvailableOption.CAUTION_AREA, opts[1])));
        // skip hazmat_area opts[2]
        options.add(new ZoneOption(ZoneAvailableOption.REPORT_ON_ARRIVAL_DEPARTURE, getOptionValue(ZoneAvailableOption.REPORT_ON_ARRIVAL_DEPARTURE, opts[3])));
        // skip report on departure opts[4]
        // skip disable rf opts[5]
        // skip perferred comm opts[6]
        // skip position update opts[7]
        options.add(new ZoneOption(ZoneAvailableOption.SEATBELT_VIOLATION, getOptionValue(ZoneAvailableOption.SEATBELT_VIOLATION, opts[8])));
        options.add(new ZoneOption(ZoneAvailableOption.SPEEDING_VIOLATION, getOptionValue(ZoneAvailableOption.SPEEDING_VIOLATION, opts[9])));
        options.add(new ZoneOption(ZoneAvailableOption.DRIVER_ID_VIOLATION, getOptionValue(ZoneAvailableOption.DRIVER_ID_VIOLATION, opts[10])));
        // skip ignition on/off opts[11], [12]
        // skip theft opts[13]
        // skip dot rule opts[14]
        options.add(new ZoneOption(ZoneAvailableOption.MASTER_BUZZER, getOptionValue(ZoneAvailableOption.MASTER_BUZZER, opts[15])));
        options.add(new ZoneOption(ZoneAvailableOption.HARD_TURN_EVENT, getOptionValue(ZoneAvailableOption.HARD_TURN_EVENT, opts[16])));
        options.add(new ZoneOption(ZoneAvailableOption.HARD_VERTICAL_EVENT, getOptionValue(ZoneAvailableOption.HARD_VERTICAL_EVENT, opts[17])));
        options.add(new ZoneOption(ZoneAvailableOption.HARD_BRAKE_EVENT, getOptionValue(ZoneAvailableOption.HARD_BRAKE_EVENT, opts[18])));
        options.add(new ZoneOption(ZoneAvailableOption.MONITOR_IDLE, getOptionValue(ZoneAvailableOption.MONITOR_IDLE, opts[19])));
        // display_on_portal,Flag,IntId,deleted (20,21,22,23)
        options.add(new ZoneOption(ZoneAvailableOption.VEHICLE_TYPE, ZoneVehicleType.HEAVY));
        

        return options;
    }

    private OptionValue getOptionValue(ZoneAvailableOption zoneOpt, String string) {
        Boolean opt = null;
        if (string == null || string.isEmpty())
            return zoneOpt.getDefaultValue();
        
        opt = Boolean.valueOf(string);
        
        if (zoneOpt.getOptionType() == ZoneOptionType.OFF_ON)
            return (opt) ? OffOn.ON : OffOn.OFF;
        return (opt) ? OffOnDevice.DEVICE : OffOnDevice.OFF;
    }

    private List<LatLng> getPoints(String pointStr) {
        String[] pts = pointStr.split(",");
        List<LatLng> points = new ArrayList<LatLng>();
        for (int i = 0; i < pts.length; i+=2)
            points.add(new LatLng(Double.valueOf(pts[i+1]), Double.valueOf(pts[i])));
        
        return points;
    }


    /** Hex chars */
    private static final byte[] HEX_CHAR = new byte[]
        { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };


    /**
     * Helper function that dump an array of bytes in hex form
     * 
     * @param buffer
     *            The bytes array to dump
     * @return A string representation of the array of bytes
     */
    public static final String dumpBytes( byte[] buffer, int cnt )
    {
        if ( buffer == null )
        {
            return "";
        }

        StringBuffer sb = new StringBuffer();

        for ( int i = 0; i < cnt; i++ )
        {
            sb.append( "0x" ).append( ( char ) ( HEX_CHAR[( buffer[i] & 0x00F0 ) >> 4] ) ).append(
                ( char ) ( HEX_CHAR[buffer[i] & 0x000F] ) ).append( " " );
        }

        return sb.toString();
    }


    private List<Zone> getMockZoneList() {
        List<Zone> mockZoneList = new ArrayList<Zone>();
        Zone lightZone = new Zone(1, 1, Status.ACTIVE, "Zone Light", "Zone Light Address", 1);
        lightZone.setPoints(getMockZonePoints());
        lightZone.setOptions(getMockZoneOptions(ZoneVehicleType.LIGHT));
        Zone heavyZone = new Zone(2, 1, Status.ACTIVE, "Zone Heavy", "Zone Heavy Address", 1);
        heavyZone.setPoints(getMockZonePoints());
        heavyZone.setOptions(getMockZoneOptions(ZoneVehicleType.HEAVY));
        Zone allZone = new Zone(3, 1, Status.ACTIVE, "Zone All", "Zone All Address", 1);
        allZone.setPoints(getMockZonePoints());
        allZone.setOptions(getMockZoneOptions(ZoneVehicleType.ALL));
       
        
        mockZoneList.add(lightZone);
        mockZoneList.add(heavyZone);
        mockZoneList.add(allZone);
        
        return mockZoneList;
    }

    private List<ZoneOption> getMockZoneOptions(ZoneVehicleType vehicleType) {
        
        List<ZoneOption> options = new ArrayList<ZoneOption>();
        for (ZoneAvailableOption p : EnumSet.allOf(ZoneAvailableOption.class)) {
            ZoneOption option = null;
            if (p == ZoneAvailableOption.VEHICLE_TYPE)
                option = new ZoneOption(p, vehicleType);
            else option = new ZoneOption(p, p.getDefaultValue());
            options.add(option);
        }

        return options;
    }

    private List<LatLng> getMockZonePoints() {
        List<LatLng> points = new ArrayList<LatLng>();
        for (int i = 0; i < 10; i++)
            points.add(new LatLng(i*0.0d, i*0.0d));
        
        return points;
    }

}
