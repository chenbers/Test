package com.inthinc.pro.device;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.junit.runner.notification.StoppedByUserException;

import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.GenericHessianException;

@SuppressWarnings("unchecked")
public abstract class Device {
	
	private ArrayList<byte[]> sendingQueue = new ArrayList<byte[]>();
	protected ArrayList<byte[]> note_queue = new ArrayList<byte[]>();
	protected ArrayList<Integer> speed_points = new ArrayList<Integer>();
	protected ArrayList<Double[]> speed_loc = new ArrayList<Double[]>();
    
    protected Boolean ignition_state = false;
    protected Boolean power_state = false;
    protected Boolean speeding = false;
    protected Boolean rpm_violation = false;
    protected Boolean seatbelt_violation = false;
        
    protected Double latitude;
	protected Double longitude;
	protected Double last_lat, last_lng;
    protected Double speed_limit;
    
    protected Distance_Calc calculator = new Distance_Calc();
    
    protected HashMap<Integer, String> Settings;

    protected int heading=0;
    protected int speed;
    protected int WMP=17013, MSP = 50;
	protected int sats;
    protected int odometer;
    protected int note_count = 4;
    protected int productVersion = 5;
    protected int deviceDriverID;

    private final int[] dbErrors = { 302, 303, 402 };
    
    protected long time;
    protected long time_last;
    
    protected MCMProxy mcmProxy;
    

    protected Object reply;

    protected String imei;
    
    
    public Device( String IMEI, String server, HashMap<Integer, String> settings, Integer version ){
    	Settings = new HashMap<Integer, String>();
       	set_IMEI(IMEI, server, settings, version);
    }
    
    public Device( String IMEI, String server, HashMap<Integer, String> settings ){
    	this(IMEI, server, settings, 5);
    }
    
    
    private void ackFwdCmds( List<HashMap<String, Object>> reply){
    	try{ assert(reply.size()<=5);
    	}catch(AssertionError e){
    		e.printStackTrace();
    		System.out.println("The server is sending more than the 5 fwdCmd limit!!!");
    	}
    	Integer fwdID, fwdCmd;
    	Object fwdData;
    	HashMap<String, Object> fwd = new HashMap<String, Object>();
    	
    	if (!reply.isEmpty()){
    		Iterator<HashMap<String, Object>> itr = reply.iterator();
    		while (itr.hasNext()){
    			fwd = itr.next();
    			fwdID = (Integer) fwd.get("fwdID");
    			if (fwdID <= 100 ) continue;
    			fwdCmd = (Integer) fwd.get("fwdCmd");
    			fwdData = fwd.get("fwdData");
    			
    			createAckNote(fwdID, fwdCmd, fwdData);
    		}
    	}
    }
    
    protected abstract void createAckNote(Integer fwdID, Integer fwdCmd, Object fwdData);

    public abstract void add_location();
	

	public abstract void add_note();
    
    
    private void initiate_device(String server ){
        ignition_state = false;

        note_queue = new ArrayList<byte[]>();
        speed_points = new ArrayList<Integer>();
        speed_loc = new ArrayList<Double[]>();
        clear_internal_settings();
        
        get_time();
        set_url(server);

        set_satelites( 8 );
        set_location( 0.0, 0.0 );
        set_WMP( 17014 );
        set_MSP( 50 );
        set_vehicle_speed();
        
        ignition_state = false;
        power_state = false;
        speeding = false;
        rpm_violation = false;
        seatbelt_violation = false;
        speeding = false;
    }
    


    
    protected abstract void construct_note();
    protected abstract Integer get_note_count();
        
    protected void check_queue(){
        note_count = get_note_count();
        if ( note_queue.size() >= note_count ){
        	send_note();
        }
    }
    

	protected void clear_internal_settings(){
        
        odometer = 0;
        speed = 0;
        try{
            last_lat = latitude;
            last_lng = longitude;
            
        } catch(NullPointerException e){
            last_lat = 0.0;
            last_lng = 0.0;
        }
    }
    

    private void configurate_device() {
    	dump_settings();
    	get_changes();		
	}
    

	protected void dump_settings() {
		if (WMP >= 17013){
			reply = dbErrors[0];
			while (check_error(reply)){
				try{
					reply = mcmProxy.dumpSet(imei, productVersion, Settings);
				}catch(GenericHessianException e){
					reply = 0;
				}
			}
			if (reply instanceof Integer && (Integer)reply != 0){
				System.out.println(reply);
			}
		}
	}
	
	private Boolean check_error(Object reply){
		if (reply == null)return false;
		try{
			reply = (Integer)reply;
		}catch(ClassCastException e){
			return false;
		}
		for (int i=0; i<dbErrors.length;i++){
			if (dbErrors[i] == (Integer)reply)return true;
		}
		return false;
	}
	

	protected void get_changes() {
		if (WMP >= 17013){
			reply = dbErrors[0];
			while (check_error(reply)){
				try{
					reply = mcmProxy.reqSet( imei );
				}catch(EmptyResultSetException e){
					reply = 304;
				}
			}
			if (reply instanceof Integer && (Integer)reply!= 304){
				System.out.println(reply + " We failed to get any changes");
			}
			else if (reply instanceof HashMap<?,?>){
				set_settings((HashMap<Integer, String>)reply);
			}
		}
	}
	
	public abstract String get_setting();
	public abstract Integer get_setting_int();

	
    public void get_time(){
        
        time = System.currentTimeMillis() / 1000;
        time_last = time;
    }
    
	public int getDeviceDriverID() {
		return deviceDriverID;
	}
	
	private void heading(){
		Integer direction = calculator.get_heading(last_lat, last_lng, latitude, longitude);
//		if (productVersion==5){
			Integer[] headers = {0,45,90,135,180,225,270,315,360};
//		}
//		else if (productVersion==2){
//			Integer[] headers = {0,45,90,135,180,225,270,315,360};
//		}
		
		for (int heading=0;heading<headers.length-1;heading++){
			if (direction < headers[heading+1]){
				Integer deltaA = Math.abs( headers[heading]-direction);
				Integer deltaB = Math.abs( headers[heading+1]-direction);
				Integer winner = Math.min(deltaA,deltaB);
				if (winner == deltaA){
					direction = heading;
					break;
				}
				else if(winner == deltaB){
					direction = heading+1;
					break;
				}
			}
		}
		if (direction==9)direction=0;
		this.heading = direction;
	}
    
    public void increment_time( Integer increment ){
        
        time_last = time;
        time += increment;
    }
    
    private void is_speeding() {
    	Double[] point = {latitude, longitude};
		if (speed > speed_limit && !speeding){
			speeding = true;
			speed_loc.add(point);
			speed_points.add(speed);
		}
		else if(speed>speed_limit && speeding){
			speed_loc.add(point);
			speed_points.add(speed);
		}
		else if(speed<speed_limit && speeding){
			speeding = false;
			speed_loc.add(point);
			speed_points.add(speed);
			was_speeding();
		}
	}
    

	public void power_off_device(Integer time_delta){
		if (power_state){
			increment_time(time_delta);
			set_power();	
		}
		else{
			System.out.println("The device is already off.");
		}
    }

	public void power_on_device(Integer time_now){
		assert(latitude!=0.0&&longitude!=0.0);
        if (!power_state){
			set_time( time_now );
		    set_power();
		    configurate_device();
		    time_now += 30;
		    set_time( time_now );
        }else{
        	System.out.println("The device is already on.");
        }
    }public void power_on_device(Long time_now){
        power_on_device((int)(time_now / 1000));
    }public void power_on_device(){
    	power_on_device((int)(System.currentTimeMillis()/1000));
    }

	
    public abstract Integer processCommand(Integer fwdID, Integer fwdCmd, Object fwdData);
    
	protected void send_note() {
		assert(note_queue instanceof ArrayList<?>);
		assert(imei instanceof String);
		while (!note_queue.isEmpty()){
			sendingQueue = new ArrayList<byte[]>();
			for (int i=0; i < note_count; i++){
				if (note_queue.isEmpty()) break;
				sendingQueue.add(note_queue.remove(0));
			}
			reply = dbErrors[0];
			while (check_error(reply)){
				reply = mcmProxy.note(imei, sendingQueue);
			}
			if (reply instanceof Integer){
				System.out.println(reply.toString() + " We failed to send a note");
				throw new StoppedByUserException();
			}
			else if (reply instanceof ArrayList<?>){
				ackFwdCmds((List<HashMap<String, Object>>) reply);
			}
		}
	}
	

	public void setDeviceDriverID(int deviceDriverID) {
		this.deviceDriverID = deviceDriverID;
	}
    
    protected void set_IMEI( String imei, String server, HashMap<Integer, String> settings, Integer version ){
        
        this.imei = imei;
        this.Settings = settings;
        set_version(version);
        initiate_device( server );
    }


    public abstract void set_ignition( Integer time_delta);
	
    
    public void set_location(double lat, double lng, Integer time_delta) {
		try{
			if (last_lat != latitude)last_lat = latitude;
			else if (last_lat == null)last_lat = lat;
		}catch(Exception e){
			last_lat = lat;
		}try{
			if (last_lng != longitude)last_lng = longitude;
			else if (last_lng == null)last_lng = lat;
		}catch(Exception e){
			last_lng = lng;
		}
		latitude = lat;
		longitude = lng;
		
		heading();
		Integer new_time = (int)(time + time_delta);
		set_time(new_time);
		
		if ( latitude != last_lat || longitude != last_lng){
			set_odometer();
			set_vehicle_speed();
		}
	}
    public void set_location(double lat, double lng){
    	set_location(lat, lng, 0);
    }
	

	public void set_MSP( Integer version ){
        MSP = version;
    }

	private void set_odometer() { 
		Double miles = calculator.calc_distance(last_lat, last_lng, latitude, longitude);
		int tenths_of_mile = (int)(miles*100);
		odometer = tenths_of_mile;
	}

    
    protected abstract void set_power();
    

	public void set_satelites( Integer satelites ){
        sats = satelites;
    }

    protected abstract void set_server();

    public abstract void set_speed_limit( Integer limit );
    
  
    public void set_settings( HashMap<Integer, String> changes){

        Iterator<Integer> itr = changes.keySet().iterator();
        while (itr.hasNext()){
        	Integer next = itr.next();
            Settings.put(next, changes.get(next));
        }
        dump_settings();
    }
    public void set_settings( Integer key, String value){
    	HashMap<Integer, String> change = new HashMap<Integer, String>();
    	change.put(key, value);
    	set_settings(change);
    }
    
    public void set_time( Date time_now ){
        
        Calendar cal = new GregorianCalendar();
        cal.setTime(time_now);
        
        set_time( (int)(cal.getTimeInMillis()/1000));
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
        
        set_time( (int)(cal.getTimeInMillis()/1000));
    }
    
    public void set_time( Integer time_now ){
        
        time = time_now;
        time_last = time;
    }
    
    public abstract void set_url( String url, String port );
    
    public void set_url(String server){
        Addresses port = Addresses.QA_MCM_PORT;
        Addresses url = Addresses.QA_MCM;
        
        
        if (server.compareToIgnoreCase("dev")==0){
            url = Addresses.DEV_MCM_PORT;
            port = Addresses.DEV_MCM;
        }
        
        else if (server.compareToIgnoreCase("qa")==0){
            url = Addresses.QA_MCM;
            port = Addresses.QA_MCM_PORT;
        }
        
        else if (server.compareToIgnoreCase("qa2")==0){
            url = Addresses.QA2_MCM;
            port = Addresses.QA2_MCM_PORT;
        }
        
        else if (server.compareToIgnoreCase("prod")==0){
            url = Addresses.PROD_MCM;
            port = Addresses.PROD_MCM_PORT;
            
        }
        else if (server.compareToIgnoreCase("teen_qa")==0){
            url = Addresses.TEEN_MCM_QA;
            port = Addresses.TEEN_MCM_PORT_QA;
        }
        
//        else if (server.compareToIgnoreCase("teen_prod")==0){
//      	  url = Addresses.;
//            port = Addresses.;
//            
//        }
//        else if (server.compareToIgnoreCase("teen_dev")==0){
//      	  url = Addresses.;
//            port = Addresses.;

        set_url(url, port);

    }
    
	private void set_url(Addresses url, Addresses port) {
		set_url(url.getCode(),port.getCode());
	}

	private void set_vehicle_speed(){
		if (speeding){
			Integer timeDelta = (int)(time - time_last);
			Double distance = calculator.calc_distance(last_lat, last_lng, latitude, longitude);
			try{speed = (int)( (distance/timeDelta)*3600);
			}catch (Exception e){
				speed = 0;
			}
		}
		try{
			is_speeding();
		}catch(Exception e){
//			e.printStackTrace();
		}
	}
	
	public void set_version(Integer version){
		assert(version==1||version==2||version==3||version==5);
		productVersion = version;
	}
    
    

	public void set_WMP( Integer version ){
        WMP = version;
    }
    
    protected abstract void was_speeding() ;

}
