package com.inthinc.pro.selenium.testSuites;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.model.event.AggressiveDrivingEvent;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.FullEvent;
import com.inthinc.pro.model.event.IgnitionOffEvent;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.event.SeatBeltEvent;
import com.inthinc.pro.model.event.SpeedingEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import com.inthinc.pro.automation.device_emulation.MCMProxy;

public class DriveDevice {
    private static MCMProxy mcmSim;
    private String [] routeInfo = {
        "19,15, 0, 0, 57,33.0104,-117.1110, 0, 0,45, 0,  0,0,0",    
         "6,15, 0, 0,103,33.0104,-117.1110, 0, 0,45, 0,  0,0,0",    
         "2,15,25, 5,103,33.0100,-117.1130, 0, 0,45, 0,108,5,5",    
         "6,15,45,12,105,33.0097,-117.1153, 0, 0,45, 0,  0,0,0",   
       "117,15, 2,44,  9,33.0150,-117.1160, 0, 0,30, 0,  0,0,0",    
         "6,15,32,13, 25,33.0163,-117.1159, 0, 0,30, 0,  0,0,0",   
         "6,15, 0, 5, 25,33.0180,-117.1153, 0, 0,55, 0,  0,0,0",   
         "6,15,37, 5,105,33.0188,-117.1180, 0, 0,55, 0,  0,0,0",   
         "6,15,31,21,120,33.0192,-117.1199, 0, 0,40, 0,  0,0,0",   
         "6,15,46, 7, 25,33.0210,-117.1190, 0, 0,40, 0,  0,0,0",    
         "6,15,40,33, 57,33.0220,-117.1140, 0, 0,40, 0,  0,0,0",   
         "6,15,30,13, 57,33.0205,-117.1110, 0, 0,40, 0,  0,0,0",   
         "6,15,48,14, 41,33.0200,-117.1090, 0, 0,50, 0,  0,0,0",   
         "6,15,56, 7, 25,33.0200,-117.1080, 0, 0,50, 0,  0,0,0",    
         "6,15,59,24, 41,33.0220,-117.1040, 0, 0,50, 0,  0,0,0",   
         "6,15,55, 9, 57,33.0217,-117.1030,60,58,50,34,  0,0,0",    
         "6,15,30,11, 73,33.0213,-117.1015, 0, 0,45, 0,  0,0,0",   
         "6,15,39,17, 73,33.0185,-117.1019, 0, 0,45, 0,  0,0,0",   
         "6,15,31, 9, 89,33.0170,-117.1020, 0, 0,45, 0,  0,0,0",   
         "6,15,41,15, 89,33.0150,-117.1032, 0, 0,45, 0,  0,0,0",   
         "6,15,41,17, 89,33.0130,-117.1050, 0, 0,45, 0,  0,0,0",   
         "6,15,42,18, 89,33.0110,-117.1060, 0, 0,45, 0,  0,0,0",   
         "6,15,36,18, 89,33.0108,-117.1080, 0, 0,45, 0,  0,0,0",   
       "118,15,10, 5, 89,33.0108,-117.1090, 0, 0,45, 0,  0,0,0",    
         "6,15,10, 5, 89,33.0106,-117.1100, 0, 0,45, 0,  0,0,0",   
        "51,15, 0, 6, 57,33.0104,-117.1110, 0, 0,45, 0,  0,0,0",    
        "66,15, 0, 0, 57,33.0104,-117.1110, 0, 0,45, 0,  0,0,0",    
        "20,15, 0, 0, 57,33.0104,-117.1110, 0, 0,45, 0,  0,0,0"};    

    public static void main(String[] args) {
    
        DriveDevice dd = new DriveDevice();
        List<Data> trip = dd.readRoute();  
        dd.mcmSimulator(trip);
    }
    
    public DriveDevice() {}
    
    private List<Data> readRoute() {
        List<Data> tmp = new ArrayList<Data>();
        
        try {
            
            // grab a line and parse
            for ( int i = 0; i < routeInfo.length; i++ ) {
                String line = routeInfo[i];
                StringTokenizer st = new StringTokenizer(line,",");
                
                Data d = new Data();
                
                d.setnType(Integer.parseInt(st.nextToken().trim()));
                d.setnTime(Integer.parseInt(st.nextToken().trim()));
                d.setSpeed(Integer.parseInt(st.nextToken().trim()));
                d.setOdometer(Integer.parseInt(st.nextToken().trim()));
                d.setFlags(Integer.parseInt(st.nextToken().trim()));
                d.setLatitude(Double.parseDouble(st.nextToken()));
                d.setLongitude(Double.parseDouble(st.nextToken()));
                d.setTopSpeed(Integer.parseInt(st.nextToken().trim()));
                d.setAvgSpeed(Integer.parseInt(st.nextToken().trim()));
                d.setSpeedLimit(Integer.parseInt(st.nextToken().trim()));
                d.setDistance(Integer.parseInt(st.nextToken().trim()));
                d.setDeltaX(Integer.parseInt(st.nextToken().trim()));
                d.setDeltaY(Integer.parseInt(st.nextToken().trim()));
                d.setDeltaZ(Integer.parseInt(st.nextToken().trim()));
                
                tmp.add(d);
            }
            
        } catch (Exception e) {
           e.printStackTrace();
           System.exit(0);
           
        } 
        
        return tmp;
    }    
    
    private void mcmSimulator(List<Data> trip) {
        boolean rc;
    
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        try {
            mcmSim = (MCMProxy) factory.create(
                    MCMProxy.class, 
                "dev-pro.inthinc.com", 
//                "localhost",
                8090);
        } catch ( Exception e) {
            System.out.println("Unable to get the mcmproxy: " + e.getMessage());
        }
        try{
        // loop, generate trips
        int startIndex = 0;
//        for (;;) {
            startIndex = (int)(Math.random() * trip.size() );
           
            if ( startIndex != 0 && startIndex != (trip.size()-1) ) {
                
                // trip start -- > ignition on 
                System.out.println("Start index: " + startIndex);
                
                Data start = trip.get(startIndex);
                rc = genEvent("500000000007272", NoteType.IGNITION_ON.getCode(), start.getLatitude(), start.getLongitude());
                System.out.println("==> Start insert: " + rc);
                Thread.sleep(15000);

                // locations and other notes
                for ( int i = startIndex + 1; i < trip.size() - 1; i++ ) {
                    Data d = trip.get(i);
                    System.out.println("====> index: " + i + " is a note of type: " + d.getnType() + 
                            " lat " + d.getLatitude() + " lng: " + d.getLongitude());
                    
                    rc = genEvent("500000000007272", d.getnType(), d.getLatitude(), d.getLongitude());
                    System.out.println("==> insert: " + rc);
                    Thread.sleep(15000);
                }
                
                // trip end   --> ignition off 
                System.out.println("End index: " + (trip.size()-1));
                
                Data end = trip.get(trip.size()-1);
                rc = genEvent("500000000007272", NoteType.IGNITION_OFF.getCode(), end.getLatitude(), end.getLongitude() );
                System.out.println("==> End index: " + rc);
            }
//        }
        } catch ( Exception e ) {
            System.out.println("Problem in note loop: " + e.getMessage());
        }
    }
    
    private boolean genEvent(String imei, int noteType, double lat, double lng) {
        Event event = null;
    
        if (        noteType == NoteType.SEATBELT.getCode() )
                event = new SeatBeltEvent(0l, 0, NoteType.SEATBELT, new Date(), 60, 1000, 
                    lat, lng, 80, 100, 20);
        
        else if (   noteType == NoteType.NOTEEVENT.getCode() )
            event = new AggressiveDrivingEvent(0l, 0, NoteType.NOTEEVENT, new Date(), 60, 1000, 
                    lat, lng, 80, 11, -22, -33, 30);
        
        else if (   noteType == NoteType.FULLEVENT.getCode() )
            event = new FullEvent(0l, 0, NoteType.FULLEVENT, new Date(), 60, 1000, 
                    lat, lng, 80, 24, -22, -21);
        
        else if (   noteType == NoteType.UNPLUGGED.getCode() )
            event = new Event(0l, 0, NoteType.UNPLUGGED, new Date(), 60, 1000, 
                    lat, lng);
        
        else if (   noteType == NoteType.LOW_BATTERY.getCode() )
            event = new Event(0l, 0, NoteType.LOW_BATTERY, new Date(), 60, 1000, 
                    lat, lng);
        
        else if (   noteType == NoteType.NO_DRIVER.getCode() )
            event = new Event(0l, 0, NoteType.NO_DRIVER, new Date(), 60, 1000, 
                    lat, lng);
        
        else if (   noteType == NoteType.SPEEDING_EX3.getCode() )
            event = new SpeedingEvent(0l, 0, NoteType.SPEEDING_EX3, new Date(), 100, 1000, 
                    lat, lng, 100, 80, 70, 100, 100);
        
        else if (   noteType == NoteType.IGNITION_ON.getCode() )
            event = new Event(0l, 0, NoteType.IGNITION_ON, new Date(), 30, 1000, lat, lng);
        
        else if (   noteType == NoteType.IGNITION_OFF.getCode() )
            event = new IgnitionOffEvent(0l, 0, NoteType.IGNITION_OFF, new Date(), 30, 1000, 
                    lat, lng, 1000, 1000, 100);
        
        else if (   noteType == NoteType.LOCATION.getCode() )
            event = new Event(0l, 0, NoteType.LOCATION, new Date(), 30, 1000, lat, lng);
        
        else 
            return false;
        
        return genEvent(event, imei);
    }

    private boolean genEvent(Event event, String imei) {
        List<byte[]> noteList = new ArrayList<byte[]>();

        byte[] eventBytes = EventGen.createDataBytesFromEvent(event);
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
    
    public class Data {
        private int nType;
        private int nTime;
        private int speed;
        private int odometer;
        private int flags;
        private double latitude;
        private double longitude;
        private int topSpeed;
        private int avgSpeed;
        private int speedLimit;
        private int distance;
        private int deltaX;
        private int deltaY;
        private int deltaZ;
        
        public int getnType() {
            return nType;
        }
        public void setnType(int nType) {
            this.nType = nType;
        }
        public int getnTime() {
            return nTime;
        }
        public void setnTime(int nTime) {
            this.nTime = nTime;
        }
        public int getSpeed() {
            return speed;
        }
        public void setSpeed(int speed) {
            this.speed = speed;
        }
        public int getOdometer() {
            return odometer;
        }
        public void setOdometer(int odometer) {
            this.odometer = odometer;
        }
        public int getFlags() {
            return flags;
        }
        public void setFlags(int flags) {
            this.flags = flags;
        }
        public double getLatitude() {
            return latitude;
        }
        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
        public double getLongitude() {
            return longitude;
        }
        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
        public int getTopSpeed() {
            return topSpeed;
        }
        public void setTopSpeed(int topSpeed) {
            this.topSpeed = topSpeed;
        }
        public int getAvgSpeed() {
            return avgSpeed;
        }
        public void setAvgSpeed(int avgSpeed) {
            this.avgSpeed = avgSpeed;
        }
        public int getSpeedLimit() {
            return speedLimit;
        }
        public void setSpeedLimit(int speedLimit) {
            this.speedLimit = speedLimit;
        }
        public int getDistance() {
            return distance;
        }
        public void setDistance(int distance) {
            this.distance = distance;
        }
        public int getDeltaX() {
            return deltaX;
        }
        public void setDeltaX(int deltaX) {
            this.deltaX = deltaX;
        }
        public int getDeltaY() {
            return deltaY;
        }
        public void setDeltaY(int deltaY) {
            this.deltaY = deltaY;
        }
        public int getDeltaZ() {
            return deltaZ;
        }
        public void setDeltaZ(int deltaZ) {
            this.deltaZ = deltaZ;
        }
    }
}
