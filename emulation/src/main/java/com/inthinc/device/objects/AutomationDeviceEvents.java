package com.inthinc.device.objects;

import java.util.HashSet;

import com.inthinc.device.devices.DeviceBase;
import com.inthinc.device.emulation.enums.DeviceEnums.LogoutMethod;
import com.inthinc.device.emulation.enums.DeviceEnums.TripFlags;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.EventAttr;
import com.inthinc.device.emulation.notes.DeviceNote;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.logging.Log;

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
				note.addAttr(EventAttr.NO_GPS_LOCK_LOCATION, locationS);
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
			
            note.addAttr(EventAttr.LOW_IDLE, state.getLowIdle());
            note.addAttr(EventAttr.HIGH_IDLE, state.getHighIdle());
            
			if (state.getProductVersion().equals(ProductType.WAYSMART)){
			    
			} else {
			    
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
	
	public static HOSChangeNoGPSEvent hosChangeNoGPSLock(DeviceState state, GeoPoint location, String locationStr){
		return classes.new HOSChangeNoGPSEvent(state, location, locationStr);
	}
	
	public static void hosChangeNoGPSLock(DeviceBase device, String location){
		device.addEvent(hosChangeNoGPSLock(device.getState(), device.getCurrentLocation(), location));
	}
	
	/**
	 * A_AND_D_SPACE___HOS_CHANGE_STATE_NO_GPS_LOCK(113, <br />
	 * EventAttr.DRIVER_STATUS, EventAttr.DRIVER_FLAG, <br />
	 * EventAttr.DRIVER_STR, EventAttr.LOCATION),
	 * @author dtanner
	 *
	 */
	public class HOSChangeNoGPSEvent extends AutomationDeviceEvents {
		
		private HOSChangeNoGPSEvent(DeviceState state, GeoPoint location, String locationStr){
			super(DeviceNoteTypes.HOS_CHANGE_STATE_NO_GPS_LOCK, state, location);
			if (locationStr.length() > EventAttr.NO_GPS_LOCK_LOCATION.getSize()){
				throw new IllegalArgumentException("Cannot have a location string longer than " + EventAttr.NO_GPS_LOCK_LOCATION.getSize());
			}
			note.addAttr(EventAttr.DRIVER_ID_STR, state.getEmployeeID());
			note.addAttr(EventAttr.DRIVER_HOS_STATE, state.getHosState());
			note.addAttr(EventAttr.NO_GPS_LOCK_LOCATION, locationStr);

			hosChangeState(state, note);
			
			if ((state.getTripFlags() & TripFlags.CLEAR_DRIVER.getIndex()) == TripFlags.CLEAR_DRIVER.getIndex()){
				endOfTripAttrs(state, note);
			}
			
		}
	}
	
	public static NewDriverHOSRuleEvent newDriverHosRule(DeviceState state, GeoPoint location){
		return classes.new NewDriverHOSRuleEvent(state, location);
	}
	
	public static void newDriverHosRule(DeviceBase device){
		device.addEvent(newDriverHosRule(device.getState(), device.getCurrentLocation()));
	}
	
	/**
	 * NEWDRIVER_HOSRULE(116, EventAttr.DRIVER_STR, EventAttr.MCM_RULESET),
	 * @author dtanner
	 *
	 */
	public class NewDriverHOSRuleEvent extends AutomationDeviceEvents {
		
		private NewDriverHOSRuleEvent(DeviceState state, GeoPoint location){
			super(DeviceNoteTypes.NEWDRIVER_HOSRULE, state, location);
			note.addAttr(EventAttr.DRIVER_ID_STR, state.getEmployeeID());
			note.addAttr(EventAttr.CURRENT_HOS_RULESET, state.getHosRuleSet());
			
		}
	}
	
	public static DriverStateChangeEvent changeDriverState(DeviceState state, GeoPoint location, String locationStr){
		return classes.new DriverStateChangeEvent(state, location, locationStr);
	}
	
	public static void changeDriverState(DeviceBase device, String locationStr){
		device.addEvent(changeDriverState(device.getState(), device.getCurrentLocation(), locationStr));
	}
	
	/**
	 * DRIVERSTATE_CHANGE(40, EventAttr.DRIVER_FLAG, EventAttr.TRIP_REPORT_FLAG, <br />
	 * EventAttr.TRIP_INSPECTION_FLAG, EventAttr.CLEAR_DRIVER_FLAG,<br />
	 *  EventAttr.DRIVER_STR, EventAttr.LOCATION, EventAttr.DRIVER_HOS_STATE)
	 * @author dtanner
	 *
	 */
	public class DriverStateChangeEvent extends AutomationDeviceEvents {
		private DriverStateChangeEvent(DeviceState state, GeoPoint location, String locationStr){
			super(DeviceNoteTypes.HOS_CHANGE_STATE, state, location);
			
			note.addAttr(EventAttr.DRIVER_ID_STR, state.getEmployeeID());
			note.addAttr(EventAttr.NO_GPS_LOCK_LOCATION, locationStr);
			note.addAttr(EventAttr.DRIVER_HOS_STATE, state.getHosState());
			
			if ((state.getTripFlags() & TripFlags.CLEAR_DRIVER.getIndex()) == TripFlags.CLEAR_DRIVER.getIndex()){
				endOfTripAttrs(state, note);
			}
			
			hosChangeState(state, note);
			
			state.setTripFlags();
		}
	}
	
	private static void hosChangeState(DeviceState state, DeviceNote note){
    	int tripFlag = state.getTripFlags() & 0xF0;

    	if ((tripFlag & TripFlags.PRE_TRIP_INSPECTION_REPORT.getIndex()) == TripFlags.PRE_TRIP_INSPECTION_REPORT.getIndex()){
    		note.addAttr(EventAttr.INSPECTION_TYPE, TripFlags.PRE_TRIP_INSPECTION_REPORT);
		}
    	if ((tripFlag & TripFlags.POST_TRIP_INSPECTION_REPORT.getIndex()) == TripFlags.POST_TRIP_INSPECTION_REPORT.getIndex()){
    		note.addAttr(EventAttr.INSPECTION_TYPE, TripFlags.POST_TRIP_INSPECTION_REPORT);
    	}

//		if ((tripFlag & TripFlags.CLEAR_DRIVER.getIndex()) == TripFlags.CLEAR_DRIVER.getIndex()){
//			note.addAttr(EventAttr.CLEAR_DRIVER_FLAG, TripFlags.CLEAR_DRIVER);
//		}
//		if ((tripFlag & TripFlags.INSPECTION_DONE.getIndex()) == TripFlags.INSPECTION_DONE.getIndex()){
//			note.addAttr(EventAttr.TRIP_REPORT_FLAG, TripFlags.INSPECTION_DONE);	
//		}
//		if ((tripFlag & TripFlags.INSPECTION_REPORT.getIndex()) == TripFlags.INSPECTION_REPORT.getIndex()){
//			note.addAttr(EventAttr.TRIP_INSPECTION_FLAG, TripFlags.INSPECTION_REPORT);	
//		}
//		if ((tripFlag & TripFlags.KIOSK_MODE.getIndex()) == TripFlags.KIOSK_MODE.getIndex()){
//			note.addAttr(EventAttr.TRIP_KIOSK_MODE, TripFlags.KIOSK_MODE);
	}
	
	public class LogoutEvent extends AutomationDeviceEvents {
    	
		public LogoutEvent (DeviceState state, GeoPoint location){
			super(DeviceNoteTypes.CLEAR_DRIVER, state, location);
			endOfTripAttrs(state, note);
			if (state.getProductVersion().equals(ProductType.WAYSMART)){
				note.addAttr(EventAttr.DRIVER_ID_STR, state.getEmployeeID());
			} else {
				note.addAttr(EventAttr.LOGOUT_TYPE, LogoutMethod.RFID_LOGOUT.getIndex());
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
	
	public class PowerInterruptionEvent extends AutomationDeviceEvents{
		private PowerInterruptionEvent(DeviceState state, GeoPoint location){
        	super(DeviceNoteTypes.POWER_INTERRUPTED, state, location);
        	
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
                note.addAttr(EventAttr.PACKED_DELTAV, packDeltaVS());

            } else {
                note.addAttr(EventAttr.DELTA_VX, deltaX);
                note.addAttr(EventAttr.DELTA_VY, deltaY);
                note.addAttr(EventAttr.DELTA_VZ, deltaZ);
            }
        }
        
        public long packDeltaVS(){
            Long packedDeltaV = ((-deltaX) + 600l) * 1464100l + // Turn deltaX negative because the DB expects the opposite from waysmarts
                             ((deltaY) + 600l) * 1210l +
                             ((deltaZ) + 600l);
            
            Log.debug("packedDeltaV: "+packedDeltaV);
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
				note.addAttr(EventAttr.GPS_LOCK_TIME, state.getGpsLockTime().getCalendar());
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
                
                note.addAttr(EventAttr.GPS_PCT,
                        state.getGPSPercent());
                
                note.addAttr(EventAttr.TOP_SPEED, state.getTopSpeed());
                note.addAttr(EventAttr.AVG_SPEED, state.getAvgSpeed());
                note.addAttr(EventAttr.DISTANCE, state.getSeatbeltViolationDistanceX100());
            }
            state.setSeatbeltViolationDistanceX100(0);
        }

    }
    
	public class SeatBeltClickEvent extends AutomationDeviceEvents{
		private SeatBeltClickEvent(DeviceState state, GeoPoint location){
        	super(DeviceNoteTypes.IGNITION_OFF, state, location);
        	note.addAttr(EventAttr.SEATBELT_CLICKS, 1);
        	endOfTripAttrs(state, note);
			if (state.getProductVersion().equals(ProductType.WAYSMART)){
				
			} else {
				
			}
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
            	note.addAttr(EventAttr.DISTANCE, state.getSpeedingDistanceX100() / 10); // WORD m_nDistance_10x;
            	
            	note.addAttr(EventAttr.MAX_RPM, state.getMaxRpm());
            	note.addAttr(EventAttr.SPEED_LIMIT, state.getSpeedingSpeedLimit());
            	note.addAttr(EventAttr.AVG_SPEED, state.getAvgSpeed());
            	note.addAttr(EventAttr.AVG_RPM, state.getAvgRpm());
            	
            	note.addAttr(EventAttr.SBS_LINK_ID, state.getSbsLinkID());// don't send if revision < 5
            	note.addAttr(EventAttr.ZONE_ID, state.getZoneID());// WSZonesAttrs iwi.h -  atoi(controller_state.last_wszone.zone.m_nZoneId)
            	note.addAttr(EventAttr.SPEEDING_TYPE, 3);
            	note.addAttr(EventAttr.SEATBELT_ENGAGED, state.isSeatbeltEngaged());
            	note.addAttr(EventAttr.START_TIME, state.getSpeedingStartTime());
            	note.addAttr(EventAttr.STOP_TIME, state.getSpeedingStopTime()); // offset from start
            	note.addAttr(EventAttr.MAX_TIME, state.getSpeedingStopTime().getDelta(state.getSpeedingStartTime()));// offset from start
            	note.addAttr(EventAttr.COURSE, state.getCourse());// GpsData iwi.h
            	note.addAttr(EventAttr.MAX_SPEED_LIMIT, state.getMaxSpeedLimit());// regular speeding seconds
            	note.addAttr(EventAttr.SBS_SPEED_LIMIT, state.getSbsSpeedLimit());
            	note.addAttr(EventAttr.ZONE_SPEED_LIMIT, state.getZoneSpeedLimit());// zone speed limit
            	note.addAttr(EventAttr.WEATHER_SPEED_LIMIT_PERCENT, state.getWeatherSpeedLimitPercent());// weather percentage
            	note.addAttr(EventAttr.SEVERE_SPEED_THRESHOLD, state.getSevereSpeedThreshold());// severe buffer size
            	note.addAttr(EventAttr.SPEEDING_BUFFER, state.getSpeedingBuffer());// regular speeding buffer size
            	note.addAttr(EventAttr.SPEEDING_GRACE_PERIOD, state.getGracePeriod());
            	note.addAttr(EventAttr.SEVERE_SPEED_SECONDS, state.getSeverSpeedSeconds());// severe seconds
            	note.addAttr(EventAttr.SPEED_MODULE_ENABLED, state.isSpeedModuleEnabled()); // sbs enabled
            	note.addAttr(EventAttr.SPEED_SOURCE, 1);

            } else {
                note.addAttr(EventAttr.DISTANCE, state.getSpeedingDistanceX100());
                note.addAttr(EventAttr.TOP_SPEED, state.getTopSpeed());
                note.addAttr(EventAttr.AVG_SPEED, state.getAvgSpeed());
                note.addAttr(EventAttr.SPEED_ID, 9999);
                note.addAttr(EventAttr.VIOLATION_FLAGS, SpeedingEvent.FLAG);
            }
        	state.setSpeedingDistanceX100(0);
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
    
    public static void powerInterruption(DeviceBase device){
		device.addEvent(powerInterruption(device.getState(), device.getCurrentLocation()));
	}
    
    public static PowerInterruptionEvent powerInterruption(DeviceState state, GeoPoint location){
		return classes.new PowerInterruptionEvent(state, location);
	}
    
    public static void lowBattery(DeviceBase device){
		device.addEvent(lowBattery(device.getState(), device.getCurrentLocation()));
	}
    
    public static LowBatteryEvent lowBattery(DeviceState state, GeoPoint location){
		return classes.new LowBatteryEvent(state, location);
	}
    
    public static void seatbeltClick(DeviceBase device){
		device.addEvent(seatbeltClick(device.getState(), device.getCurrentLocation()));
	}
    
    public static SeatBeltClickEvent seatbeltClick(DeviceState state, GeoPoint location){
		return classes.new SeatBeltClickEvent(state, location);
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
	
	public static final HashSet<DeviceNoteTypes> endOfTripNotes = new HashSet<DeviceNoteTypes>();
	
	static {
		endOfTripNotes.add(DeviceNoteTypes.IGNITION_OFF);
		endOfTripNotes.add(DeviceNoteTypes.CLEAR_DRIVER);
		endOfTripNotes.add(DeviceNoteTypes.FULLEVENT);
		endOfTripNotes.add(DeviceNoteTypes.ROLLOVER);
		endOfTripNotes.add(DeviceNoteTypes.UNPLUGGED);
		endOfTripNotes.add(DeviceNoteTypes.UNPLUGGED_WHILE_ASLEEP);
		endOfTripNotes.add(DeviceNoteTypes.INSTALL);
	}
	
	/***
    *
    * Add the attrs that need to be sent with an end of trip.  End of trip notes are types: 20,66,1,209,202<br />
    *  - elapsed time<br />
    *  - mpg<br />
    *  - mpg distance<br />
    *  - GPS filter %<br />
    **/
    public static void endOfTripAttrs(DeviceState state, DeviceNote note){
    	
        if (state.getMpgDistanceX100()!=0){
        	note.addAttr(EventAttr.MPG, state.getMpg());
        	note.addAttr(EventAttr.MPG_DISTANCE, state.getMpgDistanceX100());
        }
    	
    	note.addAttr(EventAttr.PERCENTAGE_GPS_FILTERED, state.getPointsPassedTheFilter() * 10);
    	note.addAttr(EventAttr.OBD_PCT, state.getOBDPercent() * 10);
    	note.addAttr(EventAttr.GPS_PCT, state.getGPSPercent() * 10);
    	
    	
    	
    	if (state.getProductVersion().equals(ProductType.WAYSMART)){
    	    if (state.getSeatbeltDistanceX100()!=0){
    	        note.addAttr(EventAttr.SEATBELT_TOP_SPEED, state.getSeatbeltTopSpeed());
                note.addAttr(EventAttr.SEATBELT_OUT_DISTANCE, state.getSeatbeltDistanceX100());    
    	    }
            if (state.getNoDriverDistanceX100()!=0){
                note.addAttr(EventAttr.NO_DRIVER_DISTANCE, state.getNoDriverDistanceX100());    
            }
            if (state.getNoTrailerDistanceX100()!=0){
                note.addAttr(EventAttr.NO_TRAILER_DISTANCE, state.getNoTrailerDistanceX100());    
            }
            
            if (state.getHeadlightOffDistanceX100()!=0){
                note.addAttr(EventAttr.HEADLIGHT_OFF_DISTANCE, state.getHeadlightOffDistanceX100());
            }
            
            if (state.getRFOffDistanceX100()!=0){
                note.addAttr(EventAttr.RF_OFF_DISTANCE, state.getRFOffDistanceX100());    
            }
    		
    		
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
    	note.getLocation().changeLocation(location);
    }


	public static PanicEvent panic(DeviceState state, GeoPoint location){
		return classes.new PanicEvent(state, location);
	}
	
	public static void panic(DeviceBase device){
		device.addEvent(panic(device.getState(), device.getCurrentLocation()));
	}
	
	public class PanicEvent extends AutomationDeviceEvents {
		
		private PanicEvent(DeviceState state, GeoPoint location){
			super(DeviceNoteTypes.PANIC, state, location);
		}
		
	}
}
