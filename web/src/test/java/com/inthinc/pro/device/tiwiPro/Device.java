package com.inthinc.pro.device.tiwiPro;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.device.*;


public class Device {
    
    private Boolean ignition_state = false;
    private Boolean power_state = false;
    private Boolean speeding = false;
    private Boolean rpm_violation = false;
    private Boolean seatbelt_violation = false;
    private Boolean speeding_violation = false;
    
    private Double lat, lng, last_lat, last_lng;
    
    public HashMap<Integer, String> Settings;

    private int heading=0;
    private int speed_limit, speed;
    private int WMP=17013, MSP = 50;
    private int trip_start, trip_stop, sats;
    private int odometer;
    private int note_count = 0;
    
    private final int device_version = 5;

    private final int[] dbErrors = { 302, 303, 402 };
    
    long time, time_last;
    
    private MCMProxy mcmProxy;
    
    private ArrayList<byte[]> note_queue = new ArrayList<byte[]>();
    private ArrayList<Double[][]> speed_points = new ArrayList<Double[][]>();
        
    private String IMEI;
    
    
    public Device( String imei, String server, HashMap<Integer, String> settings ){
        IMEI = imei;
        Settings = settings;
    }
    
    public Device( String imei, String server ){
        Settings = Defaults.get_defaults();
        IMEI = imei;
        
        
    }

    public Device( String IMEI ){
        Settings = Defaults.get_defaults();
        String Server = "QA";
    }
    
    public void add_location(){
        HashMap<Integer, Integer> attrs = new HashMap<Integer, Integer>();
        if (speeding){ 
            attrs.put( Constants.ATTR_TYPE_VIOLATION_FLAGS.getCode(), Constants.VIOLATION_MASK_SPEEDING.getCode() );
        }
        
        else if (rpm_violation){ 
            attrs.put( Constants.ATTR_TYPE_VIOLATION_FLAGS.getCode(), Constants.VIOLATION_MASK_RPM.getCode() );
        }
        
        else if (seatbelt_violation){ 
            attrs.put( Constants.ATTR_TYPE_VIOLATION_FLAGS.getCode(), Constants.VIOLATION_MASK_SEATBELT.getCode() );
        }
        
        //TODO construct_note( Constants.NOTE_TYPE_LOCATION, attrs );
    
    }
    
    
    public void initiate_device(String server){
        ignition_state = false;
        
        speed_limit = Integer.parseInt(Settings.get( Constants.PROPERTY_SPEED_LIMIT.getCode()));
        note_queue = new ArrayList<byte[]>();
        speed_points = new ArrayList<Double[][]>();
        clear_internal_settings();
        
//        TODO get_time();
//        TODO set_server();
//        TODO get_server();


//        TODO set_satelites( 8 );
//        TODO set_location( 0.0, 0.0 );
        set_WMP( 17014 );
        set_MSP( 50 );
//        TODO set_vehicle_speed();
        
        ignition_state = false;
        power_state = false;
        speeding = false;
        rpm_violation = false;
        seatbelt_violation = false;
        speeding_violation = false;
        
        
    }
    
    
    public void add_note( Package_Note note ){
        
        byte[] packaged = note.Package();
        note_queue.add(packaged);
        check_queue();
        
    }
    
    public void change_IMEI( String imei, String server, HashMap<Integer, String> settings){
        
        set_IMEI( imei );
        Settings = settings;
        set_url( server );
    }
    
    private void check_queue(){
        
        if ( note_queue.size() >= Integer.parseInt( Settings.get( Constants.PROPERTY_SET_MSGS_PER_NOTIFICATION.getCode() ) ) ){
            //TODO send_note();
        }
    
    }
    
    private void clear_internal_settings(){
        
        odometer = 0;
        speed = 0;
        try{
            last_lat = lat;
            last_lng = lng;
            
        } catch(NullPointerException e){
            last_lat = 0.0;
            last_lng = 0.0;
        }
    }
    
    public void get_time(){
        
        time = System.currentTimeMillis() / 1000;
        time_last = time;
    }
    
    public void increment_time( Integer increment ){
        
        time_last = time;
        time += increment;
    }
    
    public void power_on_device(Integer time_now){
        
//        TODO set_time( time_now );
        set_power();
//        TODO configurate_device();
        time_now += 30;
//        TODO set_time( time_now );
        
                
    }
    
    public void power_off_device(){
        
        set_power();
    }
    
    private void set_IMEI( String imei ){
        
        IMEI = imei;
    }

    
    public void set_MSP( Integer version ){
        
        MSP = version;
    }
    
    private void set_power(){
        
        HashMap<Integer, Integer> attrs = new HashMap<Integer, Integer>();
        
        power_state = !power_state; // Change the power state between on and off
        if (power_state){
            attrs.put(Constants.ATTR_TYPE_FIRMWARE_VERSION.getCode(), WMP);
            attrs.put(Constants.ATTR_TYPE_DMM_VERSION.getCode(), MSP);
            attrs.put(Constants.ATTR_TYPE_GPS_LOCK_TIME.getCode(), 10);
//            TODO construct_note( Constants.NOTE_TYPE_POWER_ON.getCode(), attrs );
            check_queue();
            
        } else if (!power_state){
            attrs.put(Constants.ATTR_TYPE_LOW_POWER_MODE_TIMEOUT.getCode(), Integer.parseInt(Settings.get(Constants.PROPERTY_LOW_POWER_MODE_SECONDS.getCode())));
//            TODO construct_note( Constants.NOTE_TYPE_LOW_POWER_MODE.getCode(), attrs );
            check_queue();
//            TODO if (note_queue.size() != 0) sent_note();
        }
        
    }
    
    public void set_satelites( Integer satelites ){
        
        sats = satelites;
    }

    public void set_server(){
            
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        try {
			mcmProxy = (MCMProxy)factory.create( MCMProxy.class, 
			        Settings.get(Constants.PROPERTY_SERVER_URL.getCode()), 
			        Integer.parseInt(Settings.get(Constants.PROPERTY_SERVER_PORT.getCode())));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
        
    }

    public void set_speed_limit( Integer limit ){
        
        speed_limit = limit;
    }
    
  
    
    private void set_settings( HashMap<Integer, String> changes){

        Iterator<Integer> itr = changes.keySet().iterator();
        while (itr.hasNext()){
            Integer next = itr.next();
            Settings.put(next, changes.get(next));
        }
//      TODO dump_settings();
    }
    
    public void set_time( Date time_now ){
        
        Calendar cal = new GregorianCalendar();
        cal.setTime(time_now);
        
        time = cal.getTimeInMillis()/1000;
        time_last = time;
    }
      
    
    public void set_time( Integer time_now ){
        
        time = time_now;
        time_last = time;
    }
    
    public void set_time( String time_now ){
        
        Date date = new Date();
        SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getInstance();
        df.applyPattern("yyyy MMM dd HH:mm:ss");
        try{ date = df.parse(time_now); }
        catch (ParseException e){
            System.out.println("Hello, We Failed to parse your time");
            time = System.currentTimeMillis()/1000;
        }
        System.out.println(date);
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        
        time = cal.getTimeInMillis()/1000;
        time_last = time;
    }
    
    public void set_url( String url, String port ){

        Settings.put(Constants.PROPERTY_SERVER_PORT.getCode(), port);
        Settings.put(Constants.PROPERTY_SERVER_URL.getCode(), url);
//        TODO set_server();
    }
    
    
    public void set_url(String server){
        server = server.toLowerCase();
        
        if (server == "dev"){
            Settings.put(Constants.PROPERTY_SERVER_PORT.getCode(), Addresses.DEV_MCM_PORT.getCode());
            Settings.put(Constants.PROPERTY_SERVER_URL.getCode(), Addresses.DEV_MCM.getCode());
        }
        
        else if (server == "qa"){
            Settings.put(Constants.PROPERTY_SERVER_PORT.getCode(), Addresses.QA_MCM_PORT.getCode());
            Settings.put(Constants.PROPERTY_SERVER_URL.getCode(), Addresses.QA_MCM.getCode());
        }
        
        else if (server == "qa2"){
            Settings.put(Constants.PROPERTY_SERVER_PORT.getCode(), Addresses.QA2_MCM_PORT.getCode());
            Settings.put(Constants.PROPERTY_SERVER_URL.getCode(), Addresses.QA2_MCM.getCode());
        }
        
        else if (server == "prod"){
            Settings.put(Constants.PROPERTY_SERVER_PORT.getCode(), Addresses.PROD_MCM_PORT.getCode());
            Settings.put(Constants.PROPERTY_SERVER_URL.getCode(), Addresses.PROD_MCM.getCode());
            
        }
        else if (server == "teen_qa"){
            Settings.put(Constants.PROPERTY_SERVER_PORT.getCode(), Addresses.TEEN_MCM_PORT_QA.getCode());
            Settings.put(Constants.PROPERTY_SERVER_URL.getCode(), Addresses.TEEN_MCM_QA.getCode());
        }
//        else if (server == "teen_prod"){
//            Settings.put(Constants.PROPERTY_SERVER_PORT.getCode(), Addresses.getCode());
//            Settings.put(Constants.PROPERTY_SERVER_URL.getCode(), Addresses.QA_MCM.getCode());
//            
//        }
//        else if (server == "teen_dev"){
//            Settings.put(Constants.PROPERTY_SERVER_PORT.getCode(), Addresses.QA_MCM_PORT.getCode());
//            Settings.put(Constants.PROPERTY_SERVER_URL.getCode(), Addresses.QA_MCM.getCode());
        
//        TODO set_server();
            
    }
    
      
    public void set_WMP( Integer version ){
        
        WMP = version;
    }
            
}
