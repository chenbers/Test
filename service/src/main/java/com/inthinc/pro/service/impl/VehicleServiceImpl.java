package com.inthinc.pro.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.NoAddressFoundException;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;
import com.inthinc.pro.service.VehicleService;
import com.inthinc.pro.service.adapters.VehicleDAOAdapter;
import com.inthinc.pro.service.model.BatchResponse;

public class VehicleServiceImpl extends AbstractService<Vehicle, VehicleDAOAdapter> implements VehicleService {

    private static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    //2011-08-29T08:31:25-0600
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    private AddressLookup addressLookup;

    @Override
    public Response getAll() {
        List<Vehicle> list = getDao().getAll();
        return Response.ok(new GenericEntity<List<Vehicle>>(list) {}).build();
    }

    @Override
    public Response findByVIN(String vin) {
        Vehicle vehicle = getDao().findByVIN(vin);
        if (vehicle != null)
            return Response.ok(vehicle).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getScore(Integer vehicleID, Integer numberOfDays) {
        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            Score score = getDao().getScore(vehicleID, duration);
            if (score != null)
                return Response.ok(score).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getTrend(Integer vehicleID, Integer numberOfDays) {
        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            List<Trend> list = getDao().getTrend(vehicleID, duration);
            if (!list.isEmpty())
                return Response.ok(new GenericEntity<List<Trend>>(list) {}).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response create(List<Vehicle> vehicles, UriInfo uriInfo) {
        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Vehicle vehicle : vehicles) {
            BatchResponse batchResponse = new BatchResponse();
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("vehicle");
            Integer id = getDao().create(vehicle);
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
    public Response getLastLocation(Integer vehicleID) {
        LastLocation location = getDao().getLastLocation(vehicleID);
        if (location != null) {
            return Response.ok(location).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    // TODO do we implement these?
    // getDao().getLastTrip(driverID);
    // getDao().getVehiclesNearLoc(groupID, numof, lat, lng);
    // getDao().getVehiclesInGroup(groupID);
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.service.DriverService#getLastTrip(java.lang.Integer)
     */
    @Override
    public Response getLastTrip(Integer vehicleID) {
        if (vehicleID != null) {
            try{
                Trip trip = this.getDao().getLastTrip(vehicleID);
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

    @Override
    public Response getTrips(Integer vehicleID, String day) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(SIMPLE_DATE_FORMAT);
        DateTime startDate = formatter.parseDateTime(day).minusDays(1);
        List<Trip> list = getDao().getTrips(vehicleID, startDate.toDate(), new Date());

        for (Trip trip : list) {
            if(trip.hasGoodRoute()){
                try {
                    trip.setStartAddressStr(addressLookup.getAddress(trip.getStartLoc()));
                } catch (NoAddressFoundException nafe) {
    
                    trip.setStartAddressStr("Address Not Found at" + nafe.getLat() + "," + nafe.getLng());
                }
    
                try {
                    trip.setEndAddressStr(addressLookup.getAddress(trip.getEndLoc()));
                } catch (NoAddressFoundException nafe) {
                    trip.setStartAddressStr("Address Not Found at" + nafe.getLat() + "," + nafe.getLng());
                }
            }
        }
        return Response.ok(new GenericEntity<List<Trip>>(list) {}).build();
    }

    @Override
    public Response getTrips(Integer vehicleID) {
        DateTime startDate = new DateTime().minusDays(30);
        List<Trip> list = getDao().getTrips(vehicleID, startDate.toDate(), new Date());
        return Response.ok(new GenericEntity<List<Trip>>(list) {}).build();
    }

//    @Override
//    public Response getEvents(Integer vehicleID, String day) {
//        DateTimeFormatter formatter = DateTimeFormat.forPattern(SIMPLE_DATE_FORMAT);
//        DateTime startDate = formatter.parseDateTime(day).minusDays(1);
//        List<Event> list = getDao().getEvents(vehicleID, startDate.toDate(), new Date());
//
//        return Response.ok(new GenericEntity<List<Event>>(list) {}).build();
//    }
//
//    @Override
//    public Response getEvents(Integer vehicleID) {
//        DateTime startDate = new DateTime().minusDays(30);
//        List<Event> list = getDao().getEvents(vehicleID, startDate.toDate(), new Date());
//        return Response.ok(new GenericEntity<List<Event>>(list) {}).build();
//    }

    // fuel consumption for vehicle (parameter:day)"
    // @Override
    // public Response getVehicleMPG(Integer id) {
    // List<MpgEntity> list = getDao().getVehicleMPG(id);
    // return Response.ok(new GenericEntity<List<MpgEntity>>(list) {}).build();
    // }

    @Override
    public Response assignDevice(Integer id, Integer deviceID) {
        Vehicle vehicle = getDao().assignDevice(id, deviceID);
        if (vehicle != null) {
            return Response.ok(vehicle).build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    @Override
    public Response assignDriver(Integer id, Integer driverID) {
        Vehicle vehicle = getDao().assignDriver(id, driverID);
        if (vehicle != null) {
            return Response.ok(vehicle).build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    public AddressLookup getAddressLookup() {
        return addressLookup;
    }

    public void setAddressLookup(AddressLookup addressLookup) {
        this.addressLookup = addressLookup;
    }
	@Override
	public Response getTrips(Integer vehicleID, String fromDateTime,String toDateTime) {
        Date fromDate = buildDateTimeFromString(fromDateTime);
        Date toDate = buildDateTimeFromString(toDateTime);
        if(!validDateRange(fromDate, toDate)) return  Response.status(Status.BAD_REQUEST).build();

        if(vehicleID != null && fromDate != null && toDate != null) {
            long diff = toDate.getTime() - fromDate.getTime();
            long diffDays = diff / (1000 * 60 * 60 * 24) ;
            
            if(diffDays <= 30L) {
                List<Trip> trips =  this.getDao().getTrips(vehicleID, fromDate,toDate);
                if(trips != null && !trips.isEmpty()) {
                    return Response.ok(new GenericEntity<List<Trip>>(trips) {}).build();
                }
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
