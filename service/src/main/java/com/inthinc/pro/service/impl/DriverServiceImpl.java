package com.inthinc.pro.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.joda.time.Interval;
import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.service.DriverService;
import com.inthinc.pro.service.adapters.DriverDAOAdapter;
import com.inthinc.pro.service.model.BatchResponse;
import com.inthinc.pro.util.DateUtil;

public class DriverServiceImpl extends AbstractService<Driver, DriverDAOAdapter> implements DriverService {
	private static final Logger logger = Logger.getLogger(DriverServiceImpl.class);
    private static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    //2011-08-29T08:31:25-0600
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'hh:mm:ssZ";
    static final Integer DEFAULT_PAST_TIMING = -30;

    @Override
    public Response getAll() {
        List<Driver> list = getDao().getAll();
        return Response.ok(new GenericEntity<List<Driver>>(list) {}).build();
    }

    @Override
    public Response getSpeedingEvents(Integer driverID) {
        List<Event> eventList = getDao().getSpeedingEvents(driverID);
        if (eventList != null && !eventList.isEmpty()) {
            return Response.ok(new GenericEntity<List<Event>>(eventList) {}).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response create(List<Driver> drivers, UriInfo uriInfo) {

        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Driver driver : drivers) {
            BatchResponse batchResponse = new BatchResponse();
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("driver");
            Integer id = getDao().create(driver);
            if (id == null) {
                batchResponse.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
            } else {
                batchResponse.setStatus(Status.CREATED.getStatusCode());
                batchResponse.setUri(uriBuilder.path(id.toString()).build().toString());
            }
            responseList.add(batchResponse);
        }
        return Response.ok(new GenericEntity<List<BatchResponse>>(responseList) {}).build();
    }

    @Override
    public Response getScore(Integer driverID, Integer numberOfDays) {
        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            Score score = getDao().getScore(driverID, duration);
            if (score != null)
                return Response.ok(score).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
	public Response getScore(Integer driverID, String month) {
    	try {
			Interval interval = DateUtil.getIntervalFromMonth(month);
			//round about way of getting this with existing hessian methods
			Driver driver = getDao().findByID(driverID);
			List<DriverVehicleScoreWrapper> list = getDao().getDriverScores(driver.getGroupID(), interval);
			if (!list.isEmpty()){
				Score score = extractScore(list, driverID);
			    return Response.ok(score).build();
			}
		} catch (ParseException e) {
	        return Response.status(Status.BAD_REQUEST).build();
		}
        
        return Response.status(Status.NOT_FOUND).build();
	}
	private Score extractScore(List<DriverVehicleScoreWrapper> list, Integer driverID){
		for(DriverVehicleScoreWrapper dvsw : list){
			if (driverID.equals(dvsw.getDriver().getDriverID())){
				return dvsw.getScore();
			}
		}
		return null;
	}

    @Override
	public Response getScore(Integer driverID) {
		// TODO Auto-generated method stub
		return getScore(driverID, "");
	}
	@Override
    public Response getTrend(Integer driverID, Integer numberOfDays) {
        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            List<Trend> list = getDao().getTrend(driverID, duration);
            if (!list.isEmpty())
                return Response.ok(new GenericEntity<List<Trend>>(list) {}).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.DriverService#getLastTrip(java.lang.Integer)
     */
    @Override
    public Response getLastTrip(Integer driverID) {
        if (driverID != null) {
            try{
                Trip trip = this.getDao().getLastTrip(driverID);
                if( trip != null)
                    return Response.ok(new GenericEntity<Trip>(trip) {}).build();
                else
                    return Response.status(Status.NOT_FOUND).build();
            }
            catch(AccessDeniedException ade){
                return Response.status(Status.NOT_FOUND).build();
            }
        }
        else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.DriverService#getLastTrips(java.lang.Integer, java.lang.String)
     */
    @Override
    public Response getLastTrips(Integer driverID, String date) {
        Date startDate = buildDateFromString(date);
        Date today = new Date();
        
        if(driverID != null && startDate != null ) {
            long diff = today.getTime() - startDate.getTime();
            long diffDays = diff / (1000 * 60 * 60 * 24) ;
            
            if(diffDays <= 30L) {
                List<Trip> trips =  this.getDao().getLastTrips(driverID, startDate);
                if(trips == null) {
                    trips = new ArrayList<Trip>();
                }
                return Response.ok(new GenericEntity<List<Trip>>(trips) {}).build();
            }
            else {
                return Response.status(Status.BAD_REQUEST).build();
            }
        }
        return Response.status(Status.NOT_FOUND).build();

    }

    private Date buildDateFromString(String strDate) {
        if(strDate == null)
            return null;
        
        DateFormat df = new SimpleDateFormat(SIMPLE_DATE_FORMAT); 
        try
        {
            Date convertedDate = df.parse(strDate);           
            return convertedDate;
        } catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.DriverService#getLastTrips(java.lang.Integer)
     */
    @Override
    public Response getLastTrips(Integer driverID) {
        DateFormat df = new SimpleDateFormat(SIMPLE_DATE_FORMAT); 
        Calendar c = Calendar.getInstance(); 
        c.add(Calendar.DATE, DEFAULT_PAST_TIMING);      
        return getLastTrips(driverID,  df.format(c.getTime()));
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.DriverService#getLastLocation(java.lang.Integer)
     */
    @Override
    public Response getLastLocation(Integer driverID) {
        if (driverID != null) {
            LastLocation location = this.getDao().getLastLocation(driverID);
            if( location != null)
                return Response.ok(new GenericEntity<LastLocation>(location) {}).build();
            else
                return Response.status(Status.NOT_FOUND).build();
        }
        else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.GroupService#getGroupDriverLocations(java.lang.Integer)
     */
    @Override
    public Response getGroupDriverLocations(Integer groupID) {
        List<DriverLocation> list = null;
        try {
            list = this.getDao().getDriverLocations(groupID);
        } catch (ProxyException e) {
            logger.error(e);
        }
        if (list == null || list.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(new GenericEntity<List<DriverLocation>>(list) {}).build();
    }

    public static String getSimpleDateFormat() {
        return SIMPLE_DATE_FORMAT;
    }

	@Override
	public Response getTrips(Integer driverID, String fromDateTime,String toDateTime) {
        Date fromDate = buildDateTimeFromString(fromDateTime);
        Date toDate = buildDateTimeFromString(toDateTime);
        if(!validDateRange(fromDate, toDate)) return  Response.status(Status.BAD_REQUEST).build();
        
        if(driverID != null && fromDate != null && toDate != null) {
            long diff = toDate.getTime() - fromDate.getTime();
            long diffDays = diff / (1000 * 60 * 60 * 24) ;
            
            if(diffDays <= 30L) {
                List<Trip> trips =  this.getDao().getTrips(driverID, fromDate,toDate);
                if(trips == null) {
                    trips = new ArrayList<Trip>();
                }
                return Response.ok(new GenericEntity<List<Trip>>(trips) {}).build();
            }
            else {
                return Response.status(Status.BAD_REQUEST).build();
            }
        }
        return Response.status(Status.NOT_FOUND).build();
	}
    private Date buildDateTimeFromString(String strDate) {
        if(strDate == null)
            return null;
        
        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT); 
        try
        {
            Date convertedDate = df.parse(strDate);           
            return convertedDate;
        } catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    private boolean validDateRange(Date fromDate, Date toDate){
    	return toDate.after(fromDate);
    }
}
