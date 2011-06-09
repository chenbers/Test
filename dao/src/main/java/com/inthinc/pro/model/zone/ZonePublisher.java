package com.inthinc.pro.model.zone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import org.geotools.data.shapefile.dbf.DbaseFileHeader;
import org.geotools.data.shapefile.dbf.DbaseFileWriter;
import org.geotools.data.shapefile.shp.ShapeType;
import org.geotools.data.shapefile.shp.ShapefileWriter;

import com.ice.tar.TarEntry;
import com.ice.tar.TarOutputStream;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.zone.option.ZoneAvailableOption;
import com.inthinc.pro.model.zone.option.type.OptionValue;
import com.inthinc.pro.model.zone.option.type.ZoneVehicleType;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;

public class ZonePublisher {
    
    private static final String SHP_FILE  = "Zones.shp";
    private static final String SHX_FILE = "Zones.shx";
    private static final String DBF_FILE = "Zones.dbf";

    private DBColumn[] dbColumns = {  
        new DBColumn("ZONEID",'C',30,0, ZoneFieldType.ID, null, null),
        new DBColumn("SPEEDLIMIT",'N',4,0, ZoneFieldType.OPTIONS, ZoneAvailableOption.SPEED_LIMIT, null),
        new DBColumn("CAUTION",'L',1,0, ZoneFieldType.OPTIONS, ZoneAvailableOption.CAUTION_AREA, null),
        new DBColumn("ZONEDESC",'C',30,0, ZoneFieldType.NAME, null, null),
        new DBColumn("ARRIVAL",'L',1,0, ZoneFieldType.OPTIONS, ZoneAvailableOption.REPORT_ON_ARRIVAL_DEPARTURE, null), // TODO: these have been combined into 1 options, see what Gary is doing
        new DBColumn("DEPARTURE",'L',1,0, ZoneFieldType.OPTIONS, ZoneAvailableOption.REPORT_ON_ARRIVAL_DEPARTURE, null),
        new DBColumn("RF",'L',1,0, ZoneFieldType.DEFAULT, null, Boolean.FALSE), //TODO: ??
        new DBColumn("PREFCOM",'N',1,0, ZoneFieldType.DEFAULT, null, Integer.valueOf(0)), //TODO: ??
        new DBColumn("POSUPD",'F',20,5, ZoneFieldType.DEFAULT, null, Float.valueOf(0.0f)), //TODO: ??
        new DBColumn("SEATBELT",'L',1,0, ZoneFieldType.OPTIONS, ZoneAvailableOption.SEATBELT_VIOLATION, null),
        new DBColumn("DRIVERID",'L',1,0, ZoneFieldType.OPTIONS, ZoneAvailableOption.DRIVER_ID_VIOLATION, null),
        new DBColumn("ON",'L',1,0, ZoneFieldType.DEFAULT, null, Boolean.TRUE),
        new DBColumn("OFF",'L',1,0, ZoneFieldType.DEFAULT, null, Boolean.TRUE),
        new DBColumn("THEFTALERT",'L',1,0, ZoneFieldType.DEFAULT, null, Boolean.FALSE),
        new DBColumn("DOTRULE",'N',1,0, ZoneFieldType.DEFAULT, null, Integer.valueOf(0)),
        new DBColumn("BUZZER",'L',1,0, ZoneFieldType.OPTIONS, ZoneAvailableOption.MASTER_BUZZER, null),
        new DBColumn("TURN",'L',1,0, ZoneFieldType.OPTIONS, ZoneAvailableOption.HARD_TURN_EVENT, null),
        new DBColumn("BRAKE",'L',1,0, ZoneFieldType.OPTIONS, ZoneAvailableOption.HARD_BRAKE_EVENT, null),
        new DBColumn("VERT",'L',1,0, ZoneFieldType.OPTIONS, ZoneAvailableOption.HARD_VERTICAL_EVENT, null),
        new DBColumn("IDLE",'L',1,0, ZoneFieldType.OPTIONS, ZoneAvailableOption.MONITOR_IDLE, null),
        new DBColumn("SPEEDV",'L',1,0, ZoneFieldType.OPTIONS, ZoneAvailableOption.SPEEDING_VIOLATION, null),
    };

    enum ZoneFieldType {
       OPTIONS,
       DEFAULT, 
       ID ,
       NAME;
    }
    class DBColumn {
        public String name;
        public char type;
        public int len;
        public int decimalCnt;
        public ZoneFieldType zoneFieldType;
        public ZoneAvailableOption option;
        public Object defaultValue;
        
        
        public DBColumn(String name, char type, int len, int decimalCnt, ZoneFieldType zoneFieldType, ZoneAvailableOption option, Object defaultValue ) 
        {
            this.name = name;
            this.type = type;
            this.len = len;
            this.decimalCnt = decimalCnt;
            this.zoneFieldType = zoneFieldType;
            this.option = option;
            this.defaultValue = defaultValue;
        }
    }
    
    public byte[] publish(List<Zone> zoneList, ZoneVehicleType zoneVehicleType) {
        
        try {
            List<ZonePublishInfo> zonePublishInfoList= new ArrayList<ZonePublishInfo>();

            ByteArrayOutputStream dbfOS = new ByteArrayOutputStream();
            ByteArrayOutputStream shpOS = new ByteArrayOutputStream();
            ByteArrayOutputStream shxOS = new ByteArrayOutputStream();
            
            int rowCnt = getDbase(zoneList, dbfOS, zoneVehicleType);
            getShpShx(zoneList, shpOS, shxOS, zoneVehicleType, rowCnt);
            
            zonePublishInfoList.add(new ZonePublishInfo(dbfOS.toByteArray(), DBF_FILE, dbfOS.toByteArray().length));
            zonePublishInfoList.add(new ZonePublishInfo(shpOS.toByteArray(), SHP_FILE, shpOS.toByteArray().length));
            zonePublishInfoList.add(new ZonePublishInfo(shxOS.toByteArray(), SHX_FILE, shxOS.toByteArray().length));
            
            byte[] tarBytes =  createTar(zonePublishInfoList);
            return gzipTar (tarBytes);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    class ZonePublishInfo {
        private byte[] bytes;
        private String name;
        private long size;
      
        public ZonePublishInfo(byte[] bytes, String name, long size) {
            super();
            this.bytes = bytes;
            this.name = name;
            this.size = size;
        }
        public byte[] getBytes() {
            return bytes;
        }
        public void setBytes(byte[] bytes) {
            this.bytes = bytes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

    }
    
    int getDbase(List<Zone> zoneList, OutputStream os, ZoneVehicleType zoneVehicleType) throws IOException {
        WritableByteChannel dbfIn = Channels.newChannel(os);
        
        
        DbaseFileHeader header = new DbaseFileHeader();
        for (DBColumn dbColumn : dbColumns)
            header.addColumn(dbColumn.name, dbColumn.type, dbColumn.len, dbColumn.decimalCnt);
        header.setNumRecords(zoneList.size());
        
        DbaseFileWriter w = new DbaseFileWriter(header,dbfIn);
        int rowCnt = 0;
        for (Zone zone : zoneList) {
            Map<ZoneAvailableOption, OptionValue> zoneOptionsMap = zone.getOptionsMap();
            
            if (!includeZone(zone, zoneVehicleType))
                continue;
            
            Object [] fields = new Object[dbColumns.length];
            int fieldIdx = 0;
            for (DBColumn dbColumn : dbColumns) {
                switch (dbColumn.zoneFieldType) {
                    case ID:
                        fields[fieldIdx] = zone.getZoneID();
                        break;
                    case NAME:
                        fields[fieldIdx] = zone.getName();
                        break;
                    case OPTIONS:
                        OptionValue value = zoneOptionsMap.get(dbColumn.option);
                        if (value != null) {
                            if (dbColumn.type == 'L') {
                                fields[fieldIdx] = value.getBooleanValue();    
                            }
                            else fields[fieldIdx] = value.getValue();
                        }
                        break;
                    case DEFAULT:
                        fields[fieldIdx] = dbColumn.defaultValue;
                        break;
                }
                fieldIdx++;
            }
            
            w.write(fields);
            rowCnt++;
        }

        w.close();
        return rowCnt;
    }
    void getShpShx(List<Zone> zoneList, OutputStream shpOS, OutputStream shxOS, ZoneVehicleType zoneVehicleType, int rowCnt) throws IOException {

        GeometryFactory gFactory = new GeometryFactory();
        Geometry[] geometryArray = new Geometry[rowCnt];

        // Now deal with each polygon.
        //-112.00990676879883,40.74972786714098,-112.04166412353516,40.74257499754292,-112.03359603881836,40.72280306615735,-112.01505661010742,40.72293316385307,-112.00990676879883,40.74972786714098
        int polyCnt = 0;
        double radiCnt = 1.00;
        for (Zone zone : zoneList) {
            Map<ZoneAvailableOption, OptionValue> zoneOptionsMap = zone.getOptionsMap();
            if (!includeZone(zone, zoneVehicleType))
                continue;
            // all are polygon in tiwipro
            // now get the count of the pairs
            // and build the constructor
            List<Coordinate> coordinateList = new ArrayList<Coordinate>();
            for (LatLng point : zone.getPoints())  {
                coordinateList.add(new Coordinate(point.getLng(), point.getLat()));
            }
            LinearRing wsRing = gFactory.createLinearRing((Coordinate[])coordinateList.toArray(new Coordinate[coordinateList.size()]));
            Geometry wsgeom = new GeometryFactory().createPolygon(wsRing,null);
            geometryArray[polyCnt] = wsgeom;
            polyCnt++;
            radiCnt++;
        }
        GeometryCollection geoms = new GeometryCollection(geometryArray,new GeometryFactory());

        FileChannel shpIn = new WriteableByteFileChannel(Channels.newChannel(shpOS));
        FileChannel shxIn = new WriteableByteFileChannel(Channels.newChannel(shxOS));
        ShapefileWriter writer = new ShapefileWriter(shpIn,shxIn);
        writer.write(geoms,ShapeType.POLYGON);

        
        shpIn.close();
        shxIn.close();
        
        
    }
    
    private boolean includeZone(Zone zone, ZoneVehicleType zoneVehicleType) {
        Map<ZoneAvailableOption, OptionValue> zoneOptionsMap = zone.getOptionsMap();
        ZoneVehicleType zoneType = (ZoneVehicleType)zoneOptionsMap.get(ZoneAvailableOption.VEHICLE_TYPE);
        return zoneVehicleType == ZoneVehicleType.ALL || zoneType == ZoneVehicleType.ALL || zoneVehicleType == zoneType;
    }
    

    byte[] createTar(List<ZonePublishInfo> zonePublishInfoList) throws IOException, NoSuchAlgorithmException {
        
        ByteArrayOutputStream tarOS = new ByteArrayOutputStream();
        
        TarOutputStream out = new TarOutputStream(tarOS);
        for (ZonePublishInfo publishInfo : zonePublishInfoList)
            addToTar(out, publishInfo);
        out.close();
        tarOS.close();
        
        return tarOS.toByteArray();

    }
    
    
    private void addToTar(TarOutputStream tos, ZonePublishInfo zonePublishInfo) throws IOException
    {
        TarEntry fileEntry = new TarEntry(zonePublishInfo.getName());
        fileEntry.setSize(zonePublishInfo.getSize());
        tos.putNextEntry(fileEntry);
        tos.write(zonePublishInfo.getBytes());
        tos.closeEntry();
    }
    
    public byte[] gzipTar(byte[] tarBytes)throws IOException, NoSuchAlgorithmException {

        // Create the GZIP output stream
        ByteArrayOutputStream gzipBytesOS = new ByteArrayOutputStream();
        GZIPOutputStream out = new GZIPOutputStream(gzipBytesOS);

        ByteArrayInputStream in = new ByteArrayInputStream(tarBytes);
        // Transfer bytes from the input file to the GZIP output stream
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        // Complete the GZIP file
        out.finish();
        out.close();

        //calculate md5 for gzip file
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(gzipBytesOS.toByteArray());
        
        byte[] md5sum = digest.digest();
        int timestamp = Long.valueOf(new Date().getTime() / 1000l).intValue();
        int length = gzipBytesOS.toByteArray().length;

        //Also write zone gzip file out in new format with header added
        ByteArrayOutputStream outNewFormat = new ByteArrayOutputStream();
        outNewFormat.write(md5sum, 0, md5sum.length);
       
        //write out file timestamp
        ByteBuffer intBuffer = ByteBuffer.allocate(4);
        intBuffer.putInt(timestamp);
        outNewFormat.write(intBuffer.array(), 0, 4);

        //Need to pad header structure out 8 bytes to reach file size field
        intBuffer = ByteBuffer.allocate(8);
        outNewFormat.write(intBuffer.array(), 0, 8);
       
        //write out file length
        intBuffer = ByteBuffer.allocate(4);
        intBuffer.putInt(length);
        outNewFormat.write(intBuffer.array(), 0, 4);

        //Pad header structure to end
        intBuffer = ByteBuffer.allocate(64);
        outNewFormat.write(intBuffer.array(), 0, 64);
       
        outNewFormat.write(gzipBytesOS.toByteArray(), 0, length);
        outNewFormat.close();

       return outNewFormat.toByteArray();
   }
    
    /*
     *  This is an extension of the FileChannel class that just redirects to a WriteableByteChannel.  This was necessary because
        ShapefileWriter needed a FileChannel, but I didn't want to have to use the file system and wanted to just use a ByteArrayOutputStream
        as the underlying Stream instead of a FileOutStream.   The only methods called are position(0) and write(byte[]).
     */
    class WriteableByteFileChannel extends FileChannel {
        
        WritableByteChannel channel;
        
        public WriteableByteFileChannel(WritableByteChannel channel) {
            this.channel = channel;
        }

        @Override
        public void force(boolean metaData) throws IOException {
        }

        @Override
        public FileLock lock(long position, long size, boolean shared) throws IOException {
            return null;
        }

        @Override
        public MappedByteBuffer map(MapMode mode, long position, long size) throws IOException {
            return null;
        }

        @Override
        public long position() throws IOException {
            return 0;
        }

        @Override
        public FileChannel position(long newPosition) throws IOException {
            return null;
        }

        @Override
        public int read(ByteBuffer dst) throws IOException {
            return 0;
        }

        @Override
        public int read(ByteBuffer dst, long position) throws IOException {
            return 0;
        }

        @Override
        public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
            return 0;
        }

        @Override
        public long size() throws IOException {
            return 0;
        }

        @Override
        public long transferFrom(ReadableByteChannel src, long position, long count) throws IOException {
            return 0;
        }

        @Override
        public long transferTo(long position, long count, WritableByteChannel target) throws IOException {
            return 0;
        }

        @Override
        public FileChannel truncate(long size) throws IOException {
            return null;
        }

        @Override
        public FileLock tryLock(long position, long size, boolean shared) throws IOException {
            return null;
        }

        @Override
        public int write(ByteBuffer src) throws IOException {
            return channel.write(src);
        }

        @Override
        public int write(ByteBuffer src, long position) throws IOException {
            return 0;
        }

        @Override
        public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
            return 0;
        }

        @Override
        protected void implCloseChannel() throws IOException {
            channel.close();        
        }
    }
    
}
