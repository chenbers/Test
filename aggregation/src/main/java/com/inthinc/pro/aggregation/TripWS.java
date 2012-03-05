package com.inthinc.pro.aggregation;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import com.inthinc.pro.aggregation.model.DeviceDay;
import com.inthinc.pro.aggregation.model.Note;
import com.inthinc.pro.aggregation.model.Trip;
import com.inthinc.pro.aggregation.model.TripStatus;
import com.inthinc.pro.aggregation.util.DateUtil;
import com.inthinc.pro.aggregation.db.DBUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TripWS {

	private static Logger logger = LoggerFactory.getLogger(TripWS.class.getName());
	
	private final String NEW_ODOMETER = "32801";
	private final String LOW_IDLE = "219";
	private final String HIGH_IDLE = "220";
	
	
	public static void main(String [] args)
	{

		
		if(args.length == 0)
		{
			System.out.println("Properties File was not passed In");
			System.exit(0);
		}

		
		String filePath = (String) args[0];
		AggregationProperties.initProperties(filePath);
		AggregationProperties properties = AggregationProperties.getInstance(); 
		DBUtil.setDataSource(properties);
		
		TripWS tripWS = new TripWS();
		try {
			tripWS.updateTripsWS();
		} catch (Exception e)
		{
			logger.error("Exception: " + e);
			e.printStackTrace();
		}
				
	}	
	
	
	public void updateTripsWS() throws SQLException
	{
		List<DeviceDay> ddList = DBUtil.getDeviceDay2Agg();
		GregorianCalendar endDayCalendar = new GregorianCalendar();
		
		for (DeviceDay dd : ddList)
		{
			endDayCalendar.setTime(dd.getDay());
			endDayCalendar.add(Calendar.DATE, 1);
//			int startTS = (int) (dd.getDay().getTime()/1000);
//			int endTS = (int) (endDayCalendar.getTime().getTime()/1000);
			logger.debug("Day: " + dd.getDay());
			
			Date startTS = DBUtil.fetchTripEndTimeStampBeforeDay(dd.getDeviceID(), dd.getDay());
			Date endTS = DBUtil.fetchTripStartTimeStampAfterDay(dd.getDeviceID(), endDayCalendar.getTime());
			List<Note> noteList = DBUtil.getWSTripNotesForDevice(dd.getDeviceID(), startTS, endTS);
			DBUtil.deleteTrips(dd.getDeviceID(), startTS, endTS);
			updateTripsWSForDeviceDay(noteList, dd.getDay());
			
			String driverTZName = getTriverTZNameFromNotes(noteList);
			updateAggs(dd.getDay(), dd.getDeviceID(), driverTZName);
			
			DBUtil.deleteDeviceDay2Agg(dd.getDeviceID(), dd.getDay());
		}
	}
	
	
	private String getTriverTZNameFromNotes(List<Note> noteList)
	{
		String driverTZ = "UTC";
		for (Note note : noteList)
		{
			driverTZ = DBUtil.getDriverTimeZone(note.getDriverID());
			logger.debug("getTriverTZNameFromNotes driverId: " + note.getDriverID() + " driverTZ: " + driverTZ);
			if (driverTZ != null && !driverTZ.equalsIgnoreCase("UTC"))
				break;
		}
		if (driverTZ == null)
			driverTZ = "UTC";
		
		return driverTZ;
	}
	
	
	public void updateTripsWSForDeviceDay(List<Note> noteList, Date day) throws SQLException
	{
		Trip trip = null;
		boolean tripStarted = false;
		boolean tripEnded = false;
		try {
		for (Note note : noteList)
		{
		
			logger.debug("noteID: " + note.getNoteID() + " NoteType: " + note.getType() + " NoteTime: " + DateUtil.getDateFormat(TimeZone.getTimeZone("UTC")).format(note.getTime()) + " Odometer: " + note.getOdometer());			
			switch (note.getType())
			{
				case Note.TYPE_NEWDRIVER:
				case Note.TYPE_NEWDRIVER_HOSRULE:
				case Note.TYPE_IGNITION_ON:
					if (tripEnded && trip != null)
					{
						//We had an start/end before a start
						tripStarted = false;
						tripEnded = false;
						trip.setStatus(TripStatus.TRIP_COMPLETED);
						if (isRealTrip(trip))
							insertTrip(trip, day);
					}

					if (!tripStarted)
					{
						trip = new Trip();
						tripStarted = true;
						setTripStartFields(trip, note);
					}
					
					setTripEndFields(trip, note);
					break;
					
				case Note.TYPE_CLEAR_DRIVER:
				case Note.TYPE_IGNITION_OFF:
				case Note.TYPE_LOW_BATTERY:
				case Note.TYPE_HOS_CHANGE_STATE_EX:
				case Note.TYPE_HOS_CHANGE_STATE_NO_GPS_LOCK:
				case Note.TYPE_RFKILL:
					//We don't write out trip here because there are frequently two end trip notes
					//in a row.  For example CLEAR_DRIVER followed by IGNITION_OFF.
					if (tripStarted)
					{
						trip.setStatus(TripStatus.TRIP_COMPLETED);
						setTripEndFields(trip, note);
						tripEnded = true;
					}
					break;
	
				case Note.TYPE_FUEL_STOP:
				case Note.TYPE_FUEL_STOP_EX:
					if (!tripStarted)
					{
							trip = new Trip();
							setTripStartFields(trip, note);
							tripStarted = true;

					}	

					int newOdometer = note.getAttributeAsInt(NEW_ODOMETER);
					logger.debug("Fuel Stop newOdomter: " + newOdometer);
					if (newOdometer > 0)
					{
						long deltaMileage = (note.getOdometer() - (newOdometer * 100));
						trip.setMileageOffset(trip.getMileageOffset() + deltaMileage);
					}
					setTripEndFields(trip, note);

					break;
				case Note.TYPE_IDLING:
				case Note.TYPE_IDLING2:
					if (!tripStarted)
					{
							trip = new Trip();
							setTripStartFields(trip, note);
							tripStarted = true;

					}	
					int idleLo = note.getAttributeAsInt(LOW_IDLE);
					int idleHi = note.getAttributeAsInt(HIGH_IDLE);
					trip.setIdleTime(trip.getIdleTime() + idleLo + idleHi);
//					tripDAO.updateAgg(trip, 0, 0);			
					setTripEndFields(trip, note);
					
					break;
				default:
					break;
			}
		
		}
		
		if (trip != null)
		{
			if (tripEnded)
			{
				trip.setStatus(TripStatus.TRIP_COMPLETED);
				if (isRealTrip(trip))
					insertTrip(trip, day);
			}
			else
			{
				trip.setStatus(TripStatus.TRIP_ACTIVE);
				if (trip.getEndTime() == null)
				{
					
					Date currentTime = new Date();
					trip.setEndTime(currentTime.before(trip.getStartTime()) ? trip.getStartTime() : currentTime);
				}
				insertTrip(trip, day);
			}
		}
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

	private void updateAggs(Date tripDay, Long deviceId, String tzName) throws SQLException
	{
		logger.debug("updateAggs tripDay: " + tripDay);

		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		String strDay = dateFormat.format(tripDay);
		logger.debug("strDay: " + strDay);

	    TimeZone driverTZ = TimeZone.getTimeZone(tzName);
	    
	    GregorianCalendar tripDayEndCal = new GregorianCalendar();
	    tripDayEndCal.setTime(tripDay);
	    tripDayEndCal.add(Calendar.DATE, 1);
	    
		//We are converting a Trip Day (UTC) into a Driver Day (Driver TZ)
	    GregorianCalendar driverDayCal = new GregorianCalendar();
	    driverDayCal.setTimeZone(driverTZ);
	    int year = Integer.parseInt(strDay.substring(0,4));
	    int month = Integer.parseInt(strDay.substring(5,7));
	    int day = Integer.parseInt(strDay.substring(8,10));
	    driverDayCal.set(year, month-1, day, 0, 0, 0);

		logger.debug("updateAggs driverDayCal: " + driverDayCal);
//	    if (tripDay.before(driverDayCal.getTime()))
//	    	driverDayCal.add(Calendar.DATE, -1);
	    
		//We update the aggs that the newly aggregated trips may have effected.
	    while (driverDayCal.getTime().before(tripDayEndCal.getTime()))
	    {
			updateAgg(deviceId, driverDayCal.getTime(), driverTZ);
	    	driverDayCal.add(Calendar.DATE, 1);
	    }
	}

	
	
	private void updateAgg(Long deviceId, Date startDayTimeDriver, TimeZone driverTZ)  throws SQLException
	{
		logger.debug("updateAgg startDayTimeDriver: " + startDayTimeDriver);
		
		GregorianCalendar endDayCalendar = new GregorianCalendar();
		endDayCalendar.setTime(startDayTimeDriver);
		endDayCalendar.add(Calendar.DATE, 1);
		
		//Fetch the trips for the driver's agg day. We are filtering using driver's TZ and trip start/end times are converted to driver TZ.
        List<Trip> tripList = DBUtil.fetchTripsForAggDay(startDayTimeDriver, endDayCalendar.getTime(), deviceId, driverTZ);

        DBUtil.clearAgg(deviceId, startDayTimeDriver, driverTZ);
        for (Trip trip : tripList)
        {
			//Update the agg with this trip. If trip spans agg days it's prorated
        	DBUtil.updateAgg(trip, startDayTimeDriver, driverTZ);
        }

	}
	
	private void insertTrip(Trip trip, Date day) throws SQLException
	{
		DBUtil.insertTrip(trip);
	}
	
	private boolean isRealTrip(Trip trip)
	{
		if (trip == null)
		{
			logger.error("Called isRealTrip for trip that is null");		
			return false;
		}

		return (trip.getMileage() > 0 || trip.getIdleTime() > 0);
	}

	
	private void setTripEndFields(Trip trip, Note note)
	{
		if (trip == null)
		{
			logger.error("Trying to set tripEndFields of trip that is null");		
			return;
		}
	
		int startTS = (int)(trip.getStartTime().getTime()/1000);
		int endTS = (int)(note.getTime().getTime()/1000);
		float totalHrs = (endTS-startTS)/3600F;
		int totalMiles = (int)(note.getOdometer() -  trip.getStartOdometer() - trip.getMileageOffset());
		
		logger.debug("note.getOdometer(): " + note.getOdometer() + " trip.getStartOdometer(): " + trip.getStartOdometer() + " trip.getMileageOffset(): " + trip.getMileageOffset() + " totalMiles: " + totalMiles + " totalHrs: " + totalHrs + " (totalMiles/100)/totalHrs: " + (totalMiles/100)/totalHrs);		
		
		
		//Check that Odometer isn't going backwards and mileage is reasonable (mph < 100)
		if (totalMiles > 0 && note.getOdometer() > trip.getEndOdometer() && (totalHrs > 0 && (totalMiles/100)/totalHrs < 100))
		{
			trip.setEndOdometer(note.getOdometer());
			trip.setMileage(totalMiles);
		}

		//Catch instance where empty trip
		if (totalMiles == 0 && note.getOdometer() == trip.getEndOdometer())
		{
			trip.setEndOdometer(note.getOdometer());
			trip.setMileage(totalMiles);
		}
		
		trip.setEndNoteID(note.getNoteID());
		trip.setEndNoteType(note.getType());
		trip.setEndTime(note.getTime());
	}

	private void setTripStartFields(Trip trip, Note note)
	{
		trip.setDeviceID(note.getDeviceID());
		trip.setVehicleID(note.getVehicleID());
		trip.setDriverID(note.getDriverID());
		trip.setStartNoteID(note.getNoteID());
		trip.setStartNoteType(note.getType());
		trip.setStartOdometer(note.getOdometer());
		trip.setStartTime(note.getTime());
		trip.setStatus(TripStatus.TRIP_IN_TRIP);
	}

}
