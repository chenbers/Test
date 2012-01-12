package com.inthinc.pro.automation.models;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.deviceEnums.DeviceEnums.LogoutMethod;
import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.device_emulation.DeviceBase;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.EventAttr;

public class AutomationDeviceEvents {
	
	public static FuelStopEvent fuelStop(DeviceState state, GeoPoint location, int vehicleGallons, int odometerX100, int trailerGallons, String locationS){
		return classes.new FuelStopEvent(state, location, vehicleGallons, odometerX100, trailerGallons, locationS);
	}
	
	public static void fuelStop(DeviceBase device, int vehicleGallons, int odometerX100, int trailerGallons, String locationS){
		device.addEvent(fuelStop(device.getState(), device.getCurrentLocation(), vehicleGallons, odometerX100, trailerGallons, locationS));
	}
	
	/**
	 * FUEL_STOP(73, EventAttr.VEHICLE_GALLONS, EventAttr.ODOMETER, EventAttr.TRAILER_GALLONS, EventAttr.LOCATION),
	 * @author dtanner
	 *
	 */
	public class FuelStopEvent extends AutomationDeviceEvents {
		private FuelStopEvent(DeviceState state, GeoPoint location, int vehicleGallons, int odometerX100, int trailerGallons, String locationS){
			super(DeviceNoteTypes.FUEL_STOP, state, location);
			
			if (state.getProductVersion().equals(ProductType.WAYSMART)){
				note.addAttr(EventAttr.VEHICLE_GALLONS, vehicleGallons);
				note.addAttr(EventAttr.ODOMETER, odometerX100);
				note.addAttr(EventAttr.TRAILER_GALLONS, trailerGallons);
				note.addAttr(EventAttr.LOCATION, locationS);
			}
		}
	}
	
	
	public class EnterZoneEvent extends AutomationDeviceEvents {
		private EnterZoneEvent(DeviceState state, GeoPoint location){
			super(DeviceNoteTypes.WSZONES_ARRIVAL_EX, state, location);

			if (state.getProductVersion().equals(ProductType.WAYSMART)){
				
			} else {
				note.addAttr(EventAttr.ZONE_ID, state.getZoneID());
			}
		}
	}
	
	public class IdlingEvent extends AutomationDeviceEvents {
		private IdlingEvent(DeviceState state, GeoPoint location){
			super(DeviceNoteTypes.IDLING, state, location);

			if (state.getProductVersion().equals(ProductType.WAYSMART)){
				
			} else {
				note.addAttr(EventAttr.LOW_IDLE, state.getLowIdle());
				note.addAttr(EventAttr.HIGH_IDLE, state.getHighIdle());
			}
			state.setLowIdle(0).setHighIdle(0);
		}
	}
	
	public class IgnitionOffEvent extends AutomationDeviceEvents {
        
		private IgnitionOffEvent(DeviceState state, GeoPoint location){
			super(DeviceNoteTypes.IGNITION_OFF, state, location);

			endOfTripAttrs(state, note);
			
			if (state.getProductVersion().equals(ProductType.WAYSMART)){
                
            } else {
            	
            }
		}
    }
	
	public class IgnitionOnEvent extends AutomationDeviceEvents{
        
        private IgnitionOnEvent(DeviceState state, GeoPoint location){
        	super(DeviceNoteTypes.IGNITION_ON, state, location);
        	
        	if (state.getProductVersion().equals(ProductType.WAYSMART)){
//              note.addAttr(EventAttr.DRIVER_STR, employeeID);
//              note.addAttr(EventAttr.DRIVER_ID, driverID);
          } else {
              
          }
        }
    }
	
	public class InstallEvent extends AutomationDeviceEvents {
        
        private InstallEvent(DeviceState state, GeoPoint location){
        	super(DeviceNoteTypes.INSTALL, state, location);
        	
            note.addAttr(EventAttr.VEHICLE_ID_STR, state.getVehicleID());
            note.addAttr(EventAttr.MCM_ID_STR, state.getMcmID());
            note.addAttr(EventAttr.COMPANY_ID, state.getAccountID());
        }
    }
	
	public class LeaveZoneEvent extends AutomationDeviceEvents {
		private LeaveZoneEvent (DeviceState state, GeoPoint location){
			super(DeviceNoteTypes.WSZONES_ARRIVAL, state, location);

			if (state.getProductVersion().equals(ProductType.WAYSMART)){
				
			} else {
				note.addAttr(EventAttr.ZONE_ID, state.getZoneID());
			}
			state.setZoneID(0);
		}
	}
		
	public class LocationEvent extends AutomationDeviceEvents {
        
        private LocationEvent(DeviceState state, GeoPoint location){
        	super(DeviceNoteTypes.LOCATION, state, location);
            
            if (state.getProductVersion().equals(ProductType.WAYSMART)){
                
            } else {
            	
            }
        }
    }
	
	public class LogoutEvent extends AutomationDeviceEvents {
    	
		public LogoutEvent (DeviceState state, GeoPoint location){
			super(DeviceNoteTypes.CLEAR_DRIVER, state, location);

			if (state.getProductVersion().equals(ProductType.WAYSMART)){
				
			} else {
				note.addAttr(EventAttr.LOGOUT_TYPE, LogoutMethod.RFID_LOGOUT.getIndex());
				note.addAttr(
		                EventAttr.PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_,
		                state.getPointsPassedTheFilter());
				note.addAttr(EventAttr.MPG, state.getMpg());
				note.addAttr(EventAttr.MPG_DISTANCE, state.getMpgDistanceX100());
				note.addAttr(EventAttr.RFID0, state.getRfidHigh());
				note.addAttr(EventAttr.RFID1, state.getRfidLow());
			}
		}
	}
	
	public class LowBatteryEvent extends AutomationDeviceEvents {
		private LowBatteryEvent(DeviceState state, GeoPoint location){
			super(DeviceNoteTypes.LOW_BATTERY, state, location);

			endOfTripAttrs(state, note);
			
			if (state.getProductVersion().equals(ProductType.WAYSMART)){
				
			} else {
				
			}
		}
	}
	
	
	public class NoDriverEvent extends AutomationDeviceEvents {
    	private NoDriverEvent(DeviceState state, GeoPoint location){
    		super(DeviceNoteTypes.NO_DRIVER, state, location);

			if (state.getProductVersion().equals(ProductType.WAYSMART)){
				
			} else {
				
			}
    	}
    }
	
	public class NoteEvent extends AutomationDeviceEvents{
        
    	private final int deltaX;
    	private final int deltaY;
    	private final int deltaZ;
    	private final NoteEventTypes eventType;
    	
        private NoteEvent(DeviceState state, GeoPoint location, int deltaX, int deltaY, int deltaZ, NoteEventTypes eventType){
        	super(DeviceNoteTypes.NOTE_EVENT, state, location);
        	this.deltaX = deltaX;
        	this.deltaY = deltaY;
        	this.deltaZ = deltaZ;
            this.eventType = eventType;
            
            if (state.getProductVersion().equals(ProductType.WAYSMART)) {
                note.addAttr(EventAttr.DELTA_VS, packDeltaVS());

            } else {
                note.addAttr(EventAttr.DELTAV_X, deltaX);
                note.addAttr(EventAttr.DELTAV_Y, deltaY);
                note.addAttr(EventAttr.DELTAV_Z, deltaZ);
            }
        }
        
        public long packDeltaVS(){
            Long packedDeltaV = ((-deltaX) + 600l) * 1464100l + // Turn deltaX negative because the DB expects the opposite from waysmarts
                             ((deltaY) + 600l) * 1210l +
                             ((deltaZ) + 600l);
            
            MasterTest.print(packedDeltaV, Level.DEBUG);
            return packedDeltaV;
        }
        
        @Override
        public String toString(){
        	return String.format("%s(DeltaX:%4d, DeltaY:%4d, DeltaZ:%4d)\n%s", eventType, deltaX, deltaY, deltaZ, super.toString());
        }

    }
	
	public static enum NoteEventTypes {
	    Hard_Accel,
	    Hard_Brake,
	    Hard_Bump,
	    Hard_Dip,
	    Hard_Turn_Left,
	    Hard_Turn_Right,
    }
	
	public class PowerOffEvent extends AutomationDeviceEvents {
		private PowerOffEvent(DeviceState state, GeoPoint location){
			super(DeviceNoteTypes.LOW_POWER_MODE, state, location);

			endOfTripAttrs(state, note);

			if (state.getProductVersion().equals(ProductType.WAYSMART)){
				
			} else {
				note.addAttr(EventAttr.FIRMWARE_VERSION, state.getWMP());
				note.addAttr(EventAttr.DMM_VERSION, state.getMSP());
				note.addAttr(EventAttr.GPS_LOCK_TIME, state.getGpsLockTime());
			}
		}
	}
	
	public class PowerOnEvent extends AutomationDeviceEvents {
    	
    	private PowerOnEvent(DeviceState state, GeoPoint location){
    		super(DeviceNoteTypes.POWER_ON, state, location);
			
			if (state.getProductVersion().equals(ProductType.WAYSMART)){
                
            } else {
            	
            }
    	}
    }
	
	
    public class RFKillEvent extends AutomationDeviceEvents {
        
        private RFKillEvent(DeviceState state, GeoPoint location){
        	super(DeviceNoteTypes.RF_KILL, state, location);

        	endOfTripAttrs(state, note);
        }
    }
    
    public class SeatBeltEvent extends AutomationDeviceEvents {

        private SeatBeltEvent(DeviceState state, GeoPoint location){
        	super(DeviceNoteTypes.SEATBELT, state, location);
            
            if (state.getProductVersion().equals(ProductType.WAYSMART)) {
                note.addAttr(EventAttr.TOP_SPEED, state.getTopSpeed());
                note.addAttr(EventAttr.DISTANCE, state.getSeatbeltViolationDistanceX100());
                note.addAttr(EventAttr.MAX_RPM, state.getMaxRpm());

            } else {
                note.addAttr(EventAttr.AVG_RPM, state.getAvgRpm());
                
                note.addAttr(EventAttr.PERCENTAGE_OF_TIME_SPEED_FROM_GPS_USED,
                        state.getGPSPercent());
                
                note.addAttr(EventAttr.TOP_SPEED, state.getTopSpeed());
                note.addAttr(EventAttr.AVG_SPEED, state.getAvgSpeed());
                note.addAttr(EventAttr.DISTANCE, state.getSeatbeltViolationDistanceX100());
            }
            state.setSeatbeltViolationDistanceX100(0);
        }

    }
    
    
    public class SpeedingEvent extends AutomationDeviceEvents {
    	private static final int FLAG = 1;
        
        private SpeedingEvent(DeviceState state, GeoPoint location){
        	super(state.getProductVersion().equals(ProductType.WAYSMART) ? 
        			DeviceNoteTypes.SPEEDING_EX4 : DeviceNoteTypes.SPEEDING_EX3, 
        			state, location);
        	
        	if (state.getProductVersion().equals(ProductType.WAYSMART)) {
            	
            	
            	note.addAttr(EventAttr.TOP_SPEED, state.getTopSpeed());
            	note.addAttr(EventAttr.DISTANCE, state.getSpeedingDistanceX100() / 10);
            	note.addAttr(EventAttr.MAX_RPM, state.getMaxRpm());
            	note.addAttr(EventAttr.SPEED_LIMIT, state.getSpeedingSpeedLimit());
            	note.addAttr(EventAttr.AVG_SPEED, state.getAvgSpeed());
            	note.addAttr(EventAttr.AVG_RPM, state.getAvgRpm());
            	
            	note.addAttr(EventAttr.SBS_LINK_ID, state.getSbsLinkID());
            	note.addAttr(EventAttr.ZONE_ID, state.getZoneID());
            	note.addAttr(EventAttr.SPEEDING_TYPE, 3);
            	note.addAttr(EventAttr.SEATBELT_ENGAGED, state.isSeatbeltEngaged());
            	note.addAttr(EventAttr.START_TIME, state.getSpeedingStartTime());
            	note.addAttr(EventAttr.STOP_TIME, state.getSpeedingStopTime());
            	note.addAttr(EventAttr.MAX_TIME, state.getSpeedingStopTime().getDelta(state.getSpeedingStartTime()));
            	note.addAttr(EventAttr.COURSE, state.getCourse());
            	note.addAttr(EventAttr.MAX_SPEED_LIMIT, state.getMaxSpeedLimit());
            	note.addAttr(EventAttr.SBS_SPEED_LIMIT, state.getSbsSpeedLimit());
            	note.addAttr(EventAttr.ZONE_SPEED_LIMIT, state.getZoneSpeedLimit());
            	note.addAttr(EventAttr.WEATHER_SPEED_LIMIT_PERCENT, state.getWeatherSpeedLimitPercent());
            	note.addAttr(EventAttr.SEVERE_SPEED_THRESHOLD, state.getSevereSpeedThreshold());
            	note.addAttr(EventAttr.SPEEDING_BUFFER, state.getSpeedingBuffer());
            	note.addAttr(EventAttr.SPEEDING_GRACE_PERIOD, state.getGracePeriod());
            	note.addAttr(EventAttr.SEVERE_SPEED_SECONDS, state.getSeverSpeedSeconds());
            	note.addAttr(EventAttr.SPEED_MODULE_ENABLED, state.isSpeedModuleEnabled());
            	note.addAttr(EventAttr.SPEED_SOURCE, 1);

            } else {
                note.addAttr(EventAttr.DISTANCE, state.getSpeedingDistanceX100());
                note.addAttr(EventAttr.TOP_SPEED, state.getTopSpeed());
                note.addAttr(EventAttr.AVG_SPEED, state.getAvgSpeed());
                note.addAttr(EventAttr.SPEED_ID, 9999);
                note.addAttr(EventAttr.VIOLATION_FLAGS, SpeedingEvent.FLAG);
            }
        }
    }
	public class StatisticsEvent extends AutomationDeviceEvents {
		private StatisticsEvent(DeviceState state, GeoPoint location){
			super(DeviceNoteTypes.STATS, state, location);

			if (state.getProductVersion().equals(ProductType.WAYSMART)){
				
			} else {
				note.addAttr(EventAttr.BASE_VER, 0);
				note.addAttr(EventAttr.EMU_HASH_1, -1517168504);
				note.addAttr(EventAttr.EMU_HASH_2, 154129909);
				note.addAttr(EventAttr.EMU_HASH_3, 1825195881);
				note.addAttr(EventAttr.EMU_HASH_4, 1627500918);
				note.addAttr(EventAttr.TOTAL_AGPS_BYTES, 60000);
			}
		}
	}
    
	public class TamperingEvent extends AutomationDeviceEvents {
    	
    	public TamperingEvent(DeviceState state, GeoPoint location){
    		super(DeviceNoteTypes.UNPLUGGED, state, location);
    		endOfTripAttrs(state, note);
    		
    		if (state.getProductVersion().equals(ProductType.WAYSMART)){
    			
    		} else {
    			note.addAttr(EventAttr.BACKUP_BATTERY, 6748);
    		}
    	}
    }
	
    private final static AutomationDeviceEvents classes = new AutomationDeviceEvents();

    public static EnterZoneEvent enterZone(DeviceState state, GeoPoint location) {
		return classes.new EnterZoneEvent(state, location);
	}
    
    public static void hardAccel(DeviceBase device, int deltaX){
    	device.addEvent(hardAccel(device.getState(), device.getCurrentLocation(), Math.abs(deltaX)));
    }
    
    public static NoteEvent hardAccel(DeviceState state, GeoPoint location, int deltaX){
        return classes.new NoteEvent(state, location, Math.abs(deltaX), 5, 5, NoteEventTypes.Hard_Accel);
    }
    
    public static void hardBrake(DeviceBase device, int deltaX){
    	device.addEvent(hardBrake(device.getState(), device.getCurrentLocation(), -Math.abs(deltaX)));
    }
    
    public static NoteEvent hardBrake(DeviceState state, GeoPoint location, int deltaX){
        return classes.new NoteEvent(state, location, -Math.abs(deltaX), 5, 5, NoteEventTypes.Hard_Brake);
    }
    

    public static void hardBump(DeviceBase device, int deltaZ){
    	device.addEvent(hardBump(device.getState(), device.getCurrentLocation(), -Math.abs(deltaZ)));
    }

    
    public static NoteEvent hardBump(DeviceState state, GeoPoint location, int deltaZ){
        return classes.new NoteEvent(state, location, 5, 5, -Math.abs(deltaZ), NoteEventTypes.Hard_Bump);
    }
    
    public static void hardDip(DeviceBase device, int deltaZ){
    	device.addEvent(hardDip(device.getState(), device.getCurrentLocation(), Math.abs(deltaZ)));
    }
    
    public static NoteEvent hardDip(DeviceState state, GeoPoint location, int deltaZ){
        return classes.new NoteEvent(state, location, 5, 5, Math.abs(deltaZ), NoteEventTypes.Hard_Dip);
    }
    
    public static void hardLeft(DeviceBase device, int deltaY){
    	device.addEvent(hardLeft(device.getState(), device.getCurrentLocation(), Math.abs(deltaY)));
    }
    
    public static NoteEvent hardLeft(DeviceState state, GeoPoint location, int deltaY){
        return classes.new NoteEvent(state, location, 5, Math.abs(deltaY), 5, NoteEventTypes.Hard_Turn_Left);
    }
    
    public static void hardRight(DeviceBase device, int deltaY){
    	device.addEvent(hardRight(device.getState(), device.getCurrentLocation(),-Math.abs(deltaY)));
    }
    
    public static NoteEvent hardRight(DeviceState state, GeoPoint location, int deltaY){
        return classes.new NoteEvent(state, location, 5, -Math.abs(deltaY), 5, NoteEventTypes.Hard_Turn_Right);
    }
    
    public static void idling(DeviceBase device){
		device.addEvent(idling(device.getState(), device.getCurrentLocation()));
	}
    
    public static IdlingEvent idling(DeviceState state, GeoPoint location){
		return classes.new IdlingEvent(state, location);
	}
    
    public static void ignitionOff(DeviceBase device){
    	device.addEvent(ignitionOff(device.getState(), device.getCurrentLocation()));
    }
    
    public static IgnitionOffEvent ignitionOff(DeviceState state, GeoPoint location){
    	return classes.new IgnitionOffEvent(state, location);
    }
    
    public static void ignitionOn(DeviceBase device){
    	device.addEvent(ignitionOn(device.getState(), device.getCurrentLocation()));
    }
    
    public static IgnitionOnEvent ignitionOn(DeviceState state, GeoPoint location){
    	return classes.new IgnitionOnEvent(state, location);
    }
    
    public static void install(DeviceBase device){
    	device.addEvent(install(device.getState(), device.getCurrentLocation()));
    }
    
    public static InstallEvent install(DeviceState state, GeoPoint location){
    	return classes.new InstallEvent(state, location);
    }
    
    

    public static LeaveZoneEvent leaveZone(DeviceState state, GeoPoint location){
		return classes.new LeaveZoneEvent(state, location);
	}
    
    public static void location(DeviceBase device){
    	device.addEvent(location(device.getState(), device.getCurrentLocation()));
    }

    public static LocationEvent location(DeviceState state, GeoPoint location){
    	return classes.new LocationEvent(state, location);
    }
    
    public static void logout(DeviceBase device){
    	device.addEvent(logout(device.getState(), device.getCurrentLocation()));
    }
    
    public static LogoutEvent logout(DeviceState state, GeoPoint location){
		return classes.new LogoutEvent(state, location);
	}
    
    public static void lowBattery(DeviceBase device){
		device.addEvent(lowBattery(device.getState(), device.getCurrentLocation()));
	}
    
    public static LowBatteryEvent lowBattery(DeviceState state, GeoPoint location){
		return classes.new LowBatteryEvent(state, location);
	}
    
    public static void noDriver(DeviceBase device){
    	device.addEvent(noDriver(device.getState(), device.getCurrentLocation()));
    }
    
    public static NoDriverEvent noDriver(DeviceState state, GeoPoint location){
    	return classes.new NoDriverEvent(state, location);
    }
    
    public static void noteEvent(DeviceBase device, int deltaX, int deltaY, int deltaZ, 
    		NoteEventTypes expectedType){
    	device.addEvent(noteEvent(device.getState(), device.getCurrentLocation(), deltaX, deltaY, deltaZ, expectedType));
    }
    
    public static NoteEvent noteEvent(DeviceState state, GeoPoint location, int deltaX, int deltaY, int deltaZ, 
    		NoteEventTypes expectedType){
    	return classes.new NoteEvent(state, location, deltaX, deltaY, deltaZ, expectedType);
    }
    
    public static void powerOff(DeviceBase device){
		device.addEvent(powerOff(device.getState(), device.getCurrentLocation()));
	}
    
    public static PowerOffEvent powerOff(DeviceState state, GeoPoint location){
		return classes.new PowerOffEvent(state, location);
	}
    
    public static void powerOn(DeviceBase device){
		device.addEvent(powerOn(device.getState(), device.getCurrentLocation()));
	}
    
    public static PowerOnEvent powerOn(DeviceState state, GeoPoint location){
		return classes.new PowerOnEvent(state, location);
	}
    
    public static void rfKill(DeviceBase device){
    	device.addEvent(rfKill(device.getState(), device.getCurrentLocation()));
    }
    
    public static RFKillEvent rfKill(DeviceState state, GeoPoint location){
        return classes.new RFKillEvent(state, location);
    }
    

    public static void seatbelt(DeviceBase device){
    	device.addEvent(seatbelt(device.getState(), device.getCurrentLocation()));
    }
    
    
    public static SeatBeltEvent seatbelt(DeviceState state, GeoPoint location){
    	return classes.new SeatBeltEvent(state, location);
    }
    
    
    public static void speeding(DeviceBase device){
    	device.addEvent(speeding(device.getState(), device.getCurrentLocation()));
    }
    
    public static SpeedingEvent speeding(DeviceState state, GeoPoint location){
    	return classes.new SpeedingEvent(state, location);
    }
    
    public static void statistics(DeviceBase device){
		device.addEvent(statistics(device.getState(), device.getCurrentLocation()));
	}
    
    public static StatisticsEvent statistics(DeviceState state, GeoPoint location){
		return classes.new StatisticsEvent(state, location);
	}
    
    public static void tampering(DeviceBase device){
    	device.addEvent(tampering(device.getState(), device.getCurrentLocation()));
    }
    
    public static TamperingEvent tampering(DeviceState state, GeoPoint location){
    	return classes.new TamperingEvent(state, location);
    }
    

    protected DeviceNote note;

    
    
    protected final DeviceNoteTypes type;

	private AutomationDeviceEvents() {
		this.type = DeviceNoteTypes.STATS;
	}
	
	private AutomationDeviceEvents(DeviceNoteTypes type, DeviceState state, GeoPoint location){
		this.type = type;
		note = DeviceNote.constructNote(type, location, state);
	}
	
	/***
    *
    * Add the attrs that need to be sent with an end of trip.  End of trip notes are types: 20,66,1,209,202<br />
    *  - elapsed time<br />
    *  - mpg<br />
    *  - mpg distance<br />
    *  - GPS filter %<br />
    **/
    private void endOfTripAttrs(DeviceState state, DeviceNote note){
    	
    	note.addAttr(EventAttr.MPG, state.getMpg());
    	note.addAttr(EventAttr.MPG_DISTANCE, state.getMpgDistanceX100());
    	
    	note.addAttr(EventAttr.PERCENTAGE_OF_POINTS_THAT_PASSED_THE_FILTER_, state.getPointsPassedTheFilter() * 10);
    	note.addAttr(EventAttr.PERCENTAGE_OF_TIME_SPEED_FROM_OBD_USED, state.getOBDPercent() * 10);
    	note.addAttr(EventAttr.PERCENTAGE_OF_TIME_SPEED_FROM_GPS_USED, state.getGPSPercent() * 10);
    	
    	note.addAttr(EventAttr.SEATBELT_TOP_SPEED, state.getSeatbeltTopSpeed());
    	note.addAttr(EventAttr.SEATBELT_OUT_DISTANCE, state.getSeatbeltDistanceX100());
    	
		note.addAttr(EventAttr.NO_DRIVER_DISTANCE, state.getNoDriverDistanceX100());
    	
    	if (state.getProductVersion().equals(ProductType.WAYSMART)){
    		note.addAttr(EventAttr.HEADLIGHT_OFF_DISTANCE, state.getHeadlightOffDistanceX100());
    		note.addAttr(EventAttr.NO_TRAILER_DISTANCE, state.getNoTrailerDistanceX100());
    		note.addAttr(EventAttr.RF_OFF_DISTANCE, state.getRFOffDistanceX100());
    		
    		note.addAttr(EventAttr.SPEED_LIMIT_1_TO_30_DISTANCE,  state.getnSpeedLimit1to30MilesX100());
    		note.addAttr(EventAttr.SPEED_LIMIT_31_TO_40_DISTANCE, state.getnSpeedLimit31to40MilesX100());
    		note.addAttr(EventAttr.SPEED_LIMIT_41_TO_54_DISTANCE, state.getnSpeedLimit41to54MilesX100());
    		note.addAttr(EventAttr.SPEED_LIMIT_55_TO_64_DISTANCE, state.getnSpeedLimit55to64MilesX100());
    		note.addAttr(EventAttr.SPEED_LIMIT_65_TO_80_DISTANCE, state.getnSpeedLimit65to80MilesX100());
    	} else {
    		note.addAttr(EventAttr.LOW_POWER_MODE_TIMEOUT, state.getLowPowerTimeout());
    	}
    	
    	
    	state.setMpgDistanceX100(0);
    	
    	state.setSeatbeltDistanceX100(0);
    	state.setSeatBeltTopSpeed(0);
    	
    	state.setNoDriverDistanceX100(0);
    	state.setNoTrailerDistanceX100(0);
    	
    	state.setHeadlightOffDistanceX100(0);
    	state.setRFOffDistanceX100(0);
    	
    	state.setnSpeedLimit1to30MilesX100(0);
		state.setnSpeedLimit31to40MilesX100(0);
		state.setnSpeedLimit41to54MilesX100(0);
		state.setnSpeedLimit55to64MilesX100(0);
		state.setnSpeedLimit65to80MilesX100(0);
    }

	public DeviceNote getNote(){
    	return note;
    }
	
	public DeviceNoteTypes getNoteType(){
    	return note.getType();
    }
	
	@Override
    public String toString(){
    	return note.toString();
    }
	
	public void updateLocation(GeoPoint location){
    	note.location.changeLocation(location);
    }



}
