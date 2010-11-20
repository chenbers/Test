package com.inthinc.pro.backing;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.event.AggressiveDrivingEvent;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.event.SeatBeltEvent;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.inthinc.pro.scoring.Calculator;

public class ExtractDataBean {
	
	private static final Logger logger = Logger.getLogger(ExtractDataBean.class);
	
    // gathered from UI
    private String type;
	private String id;
    private String days;
    private String fileName = "extract.txt";
	private String errorMsg;
    private String successMsg;

    private Date startDate;
    private Date endDate;
    
    private EventDAO eventDAO;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm z");

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
    	
    	
    	if (export(FacesContext.getCurrentInstance(), eventList))
		{
        
			setSuccessMsg("Successful Data Extraction <br/>  From: " + dateFormat.format(startDate) + " <br/>  To " + dateFormat.format(endDate) + " <br/>  File: " + fileName);
		}
    }

	private boolean writeCSV(List<Event> eventList, OutputStream outStream) {
		try {
//			PrintStream out = new PrintStream(new FileOutputStream(fileName));
			PrintStream out = new PrintStream(outStream);
			
			for (Event event : eventList) {
				StringBuilder buffer = new StringBuilder();
				buffer.append(event.getOdometer());
				buffer.append(",");
				buffer.append(event.getType());
				buffer.append(",");
				if (event.getType().equals(NoteType.SEATBELT)) {
					SeatBeltEvent sbEvent = (SeatBeltEvent)event;
					buffer.append(sbEvent.getSpeed());
					buffer.append(",");
					buffer.append(sbEvent.getDistance());
					buffer.append(",,,,"); // no speed limit or delta x,y,z
				}
				else if (event.getType().equals(NoteType.NOTEEVENT)) {
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
				else if (event.getType().equals(NoteType.SPEEDING_EX3)) {
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
				buffer.append(",");
				buffer.append(event.getForgiven());
				
				out.println(buffer.toString());
			}
			out.close();
		} catch (Exception e) {
    		setErrorMsg("File cannot be created.");
    		return false;
		}
		return true;
	}
    
    public void calcScoresAction() {

    	setErrorMsg(null);
    	

    	List<Event> eventList = fetchEventList();
	
		
//    	if (export(FacesContext.getCurrentInstance(), eventList))
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
    	startDate = DateUtil.convertTimeInSecondsToDate(DateUtil.getDaysBackDate(DateUtil.convertDateToSeconds(endDate), daysBack, "US/Mountain"));
		List<NoteType> eventTypes = new ArrayList<NoteType>();

		List<Event> eventList = null;
		if (type.equals("DRIVER")) {
			eventList = eventDAO.getEventsForDriver(idNum, startDate, endDate, eventTypes, 1);
		}
		else if (type.equals("VEHICLE")) {
			eventList = eventDAO.getEventsForVehicle(idNum, startDate, endDate, eventTypes, 1);
		}
//		else if (type.equals("GROUP")) {
//			eventList = eventDAO. .getEventsForGroup(idNum, startDate, endDate, eventTypes, 0);
//		}
		return eventList;
	}
	
	
    private boolean export(FacesContext facesContext, List<Event> eventList)
    {
        HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
        response.setContentType("text/plain");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        OutputStream out = null;
        try
        {
            out = response.getOutputStream();
            writeCSV(eventList, out);
            
            out.flush();
            facesContext.responseComplete();
        }
        catch (IOException e)
        {
            logger.error(e);
            return false;
        }
       
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                logger.error(e);
            }
        }
        return true;
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
