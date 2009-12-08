package com.inthinc.pro.backing;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.AggressiveDrivingEvent;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.SeatBeltEvent;
import com.inthinc.pro.model.SpeedingEvent;
import com.inthinc.pro.scoring.Calculator;

public class ExtractDataBean {
    // gathered from UI
    private String type;
	private String id;
    private String days;
    private String fileName;
	private String errorMsg;
    private String successMsg;

    private Date startDate;
    private Date endDate;
    
    private EventDAO eventDAO;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm z");

    public EventDAO getEventDAO() {
		return eventDAO;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public void init() {
    }

    public void extractAction() {

    	setErrorMsg(null);
    	

    	List<Event> eventList = fetchEventList();
		
		if (writeCSV(eventList))
		{
        
			setSuccessMsg("Successful Data Extraction <br/>  From: " + dateFormat.format(startDate) + " <br/>  To " + dateFormat.format(endDate) + " <br/>  File: " + fileName);
		}
    }

	private boolean writeCSV(List<Event> eventList) {
		try {
			PrintStream out = new PrintStream(new FileOutputStream(fileName));
			
			for (Event event : eventList) {
				StringBuilder buffer = new StringBuilder();
				buffer.append(event.getOdometer());
				buffer.append(",");
				buffer.append(event.getType());
				buffer.append(",");
				if (event.getType().equals(EventMapper.TIWIPRO_EVENT_SEATBELT)) {
					SeatBeltEvent sbEvent = (SeatBeltEvent)event;
					buffer.append(sbEvent.getSpeed());
					buffer.append(",");
					buffer.append(sbEvent.getDistance());
					buffer.append(",,,,"); // no speed limit or delta x,y,z
				}
				else if (event.getType().equals(EventMapper.TIWIPRO_EVENT_NOTEEVENT)) {
					AggressiveDrivingEvent adEvent = (AggressiveDrivingEvent)event;
					buffer.append(adEvent.getSpeed());
					buffer.append(",,");	// no distance
					buffer.append(adEvent.getSpeedLimit());
					buffer.append(",");
					buffer.append(adEvent.getDeltaX());
					buffer.append(",");
					buffer.append(adEvent.getDeltaY());
					buffer.append(",");
					buffer.append(adEvent.getDeltaZ());
				}
				else if (event.getType().equals(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3)) {
					SpeedingEvent speedingEvent = (SpeedingEvent)event;
					buffer.append(speedingEvent.getTopSpeed());
					buffer.append(",");
					buffer.append(speedingEvent.getDistance());
					buffer.append(",");
					buffer.append(speedingEvent.getSpeedLimit());
					buffer.append(",,,"); // no delta x,y,z
				}
				else {
					buffer.append(event.getSpeed());
					buffer.append(",,,,,"); // no distance, speed limit or delta x,y,z
				}
				
				out.println(buffer.toString());
			}
			out.close();
		} catch (FileNotFoundException e) {
    		setErrorMsg("File cannot be created.");
    		return false;
		}
		return true;
	}
    
    public void calcScoresAction() {

    	setErrorMsg(null);
    	

    	List<Event> eventList = fetchEventList();
	
		
		if (writeCSV(eventList))
		{
        
			StringBuilder scoreData = new StringBuilder();
			
			Calculator calculator = new Calculator();
	
			double mileage = calculator.getDistance(eventList);
			double overall = calculator.getOverallScore(eventList, mileage);
			double speedScore = calculator.getSpeedingScore(eventList, mileage);
			double seatbeltScore = calculator.getSeatbeltScore(eventList, mileage);
			double drivingStyleScore = calculator.getDrivingStyleScore(eventList, mileage);
			
			scoreData.append("Date Range  From: " + dateFormat.format(startDate) + "<br/>To: " + dateFormat.format(endDate) + "<br/> File: " + fileName + "<br/>");
	
			scoreData.append("mileage: " + mileage + "<br/>");
			scoreData.append("overall: " + overall + "<br/>");
			scoreData.append("speedScore: " + speedScore + "<br/>");
			scoreData.append("seatbeltScore: " + seatbeltScore + "<br/>");
			scoreData.append("drivingStyleScore: " + drivingStyleScore + "<br/>");
			
			setSuccessMsg(scoreData.toString());
		}
    }

	private List<Event> fetchEventList() {
		int daysBack = 0;
    	try {
    		daysBack = new Integer(days).intValue();
    	}
    	catch (NumberFormatException ex) {
    		setErrorMsg("Days Back must be an integer between 1 and 30.");
    	}

    	int idNum = 0;
    	try {
    		idNum = new Integer(id).intValue();
    	}
    	catch (NumberFormatException ex) {
    		setErrorMsg("ID is not valid.");
    	}

    	endDate = new Date();
    	startDate = DateUtil.getDaysBackDate(endDate, daysBack);
		List<Integer> eventTypes = new ArrayList<Integer>();

		List<Event> eventList = null;
		if (type.equals("DRIVER")) {
			eventList = eventDAO.getEventsForDriver(idNum, startDate, endDate, eventTypes, 0);
		}
		else if (type.equals("VEHICLE")) {
			eventList = eventDAO.getEventsForVehicle(idNum, startDate, endDate, eventTypes, 0);
		}
//		else if (type.equals("GROUP")) {
//			eventList = eventDAO. .getEventsForGroup(idNum, startDate, endDate, eventTypes, 0);
//		}
		return eventList;
	}
    public void clearErrorAction() {
        setErrorMsg(null);
        setSuccessMsg(null);
    }

    public void reInitAction() {
        setErrorMsg(null);
        setSuccessMsg(null);
    }



    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
