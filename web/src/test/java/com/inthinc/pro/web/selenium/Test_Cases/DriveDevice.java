package com.inthinc.pro.web.selenium.Test_Cases;

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

public class DriveDevice {
    private static MCMSim mcmSim;

    public static void main(String[] args) {
    
        DriveDevice dd = new DriveDevice();
        List<Data> trip = dd.readRoute();  
        dd.mcmSimulator(trip);
    }
    
    public DriveDevice() {}
    
    private List<Data> readRoute() {
        List<Data> tmp = new ArrayList<Data>();
        
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        Resource resourceScript = applicationContext.getResource("classpath:script.txt");   // route info
        
        try {
            InputStream is = resourceScript.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // grab a line and parse
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line,",");
                
                Data d = new Data();
                
                d.setnType(Integer.parseInt(st.nextToken().trim()));
                d.setnTime(Integer.parseInt(st.nextToken().trim()));
                d.setSpeed(Integer.parseInt(st.nextToken().trim()));
                d.setOdometer(Integer.parseInt(st.nextToken().trim()));
                d.setFlags(Integer.parseInt(st.nextToken().trim()));
                d.setLatitude(Double.parseDouble(st.nextToken().trim()));
                d.setLongitude(Double.parseDouble(st.nextToken().trim()));
                d.setTopSpeed(Integer.parseInt(st.nextToken().trim()));
                d.setAvgSpeed(Integer.parseInt(st.nextToken().trim()));
                d.setSpeedLimit(Integer.parseInt(st.nextToken().trim()));
                d.setDistance(Integer.parseInt(st.nextToken().trim()));
                d.setDeltaX(Integer.parseInt(st.nextToken().trim()));
                d.setDeltaY(Integer.parseInt(st.nextToken().trim()));
                d.setDeltaZ(Integer.parseInt(st.nextToken().trim()));
                
                tmp.add(d);
            }

            br.close();
            is.close();
            
        } catch (FileNotFoundException fnf) {
           fnf.printStackTrace();
           
        } catch (IOException ioe) {
            ioe.printStackTrace();
            
        } 
        
        return tmp;
    }    
    
    private void mcmSimulator(List<Data> trip) {
        boolean rc;
    
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        try {
            mcmSim = (MCMSim) factory.create(
                MCMSim.class, 
                //"dev-pro.inthinc.com", 
                "localhost",
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
                rc = genEvent("500000000000002", NoteType.IGNITION_ON.getCode(), start.getLatitude(), start.getLongitude());
                System.out.println("==> Start insert: " + rc);
                Thread.sleep(15000);

                // locations and other notes
                for ( int i = startIndex + 1; i < trip.size() - 1; i++ ) {
                    Data d = trip.get(i);
                    System.out.println("====> index: " + i + " is a note of type: " + d.getnType());
                    
                    rc = genEvent("500000000000002", d.getnType(), d.getLatitude(), d.getLongitude());
                    System.out.println("==> insert: " + rc);
                    Thread.sleep(15000);
                }
                
                // trip end   --> ignition off 
                System.out.println("End index: " + (trip.size()-1));
                
                Data end = trip.get(trip.size()-1);
                rc = genEvent("500000000000002", NoteType.IGNITION_OFF.getCode(), end.getLatitude(), end.getLongitude() );
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
