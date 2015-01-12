package com.inthinc.pro.service.impl;

import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.*;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;
import com.inthinc.pro.service.VehicleServiceExt;
import com.inthinc.pro.service.adapters.VehicleDAOAdapter;
import com.inthinc.pro.service.model.VehicleStatus;
import com.inthinc.pro.util.VehicleStatusUtil;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class VehicleServiceExtImpl extends AbstractService<Vehicle, VehicleDAOAdapter> implements VehicleServiceExt {
    private static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    //2011-08-29T08:31:25-0600
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    @Autowired
    VehicleStatusUtil vehicleStatusUtil;
    private AddressLookup addressLookup;

    @Override
    public Response get(String name) {
        Vehicle vehicle = getDao().findByName(name);
        if (vehicle != null)
            return Response.ok(vehicle).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getAllWithLastTrip() {
        // get vehicles and trips
        List<Vehicle> vehicles = getDao().getAll();
        List<Trip> trips = getDao().getAllLastTrips();

        // save the vehicles in a map to be retrieved by id
        Map<Integer, Vehicle> vehicleMap = new HashMap<Integer, Vehicle>(vehicles.size());
        for (Vehicle vehicle: vehicles){
            vehicleMap.put(vehicle.getVehicleID(), vehicle);
        }

        // create the vehicle trip view list
        VehicleTripViewList vehicleTripViewList = new VehicleTripViewList();
        List<VehicleTripView> vehicleTripViews = new ArrayList<VehicleTripView>();
        for (Trip trip : trips) {
            Integer vehicleID = trip.getVehicleID();
            Date endTime = trip.getEndTime();
            if (endTime != null && vehicleID != null && vehicleMap.containsKey(vehicleID)) {
                Vehicle vehicle = vehicleMap.get(vehicleID);
                VehicleTripView vehicleTripView = new VehicleTripView(vehicle);
                vehicleTripView.setLastTrip(endTime);
                vehicleTripViews.add(vehicleTripView);
            }
        }

        // store the list in the return bean
        vehicleTripViewList.setVehicleTripViews(vehicleTripViews);

        // return response
        return Response.ok(vehicleTripViewList).build();
    }

    @Override
    public Response getVehicleAndLastTripDate(String name) {
        Vehicle vehicle = getDao().findByName(name);

        if (vehicle == null)
            return Response.status(Status.NOT_FOUND).build();

        // find last trip
        Trip trip = getDao().getLastTrip(vehicle.getVehicleID());

        // create the view object
        VehicleTripView vehicleTripView = new VehicleTripView(vehicle);

        // add last trip date if not null
        if (trip != null) {
            vehicleTripView.setLastTrip(trip.getEndTime());
        }

        return Response.ok(vehicleTripView).build();
    }

    @Override
    public Response getAll() {
        List<Vehicle> list = getDao().getAll();
        return Response.ok(new GenericEntity<List<Vehicle>>(list) {
        }).build();
    }

    @Override
    public Response findByVIN(String vin) {
        Vehicle vehicle = getDao().findByVIN(vin);
        if (vehicle != null)
            return Response.ok(vehicle).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getScore(String name, Integer numberOfDays) {
        Integer vehicleID = getVehicleIdByName(name);
        if (vehicleID == null)
            return Response.status(Status.NOT_FOUND).build();

        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            Score score = getDao().getScore(vehicleID, duration);
            if (score != null)
                return Response.ok(score).build();
        }

        CustomDuration customDuration = CustomDuration.getDurationByDays(numberOfDays);
        if (customDuration != null) {
            Score score = getDao().getScore(vehicleID, customDuration);
            if (score != null)
                return Response.ok(score).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getTrend(String name, Integer numberOfDays) {
        Integer vehicleID = getVehicleIdByName(name);
        if (vehicleID == null)
            return Response.status(Status.NOT_FOUND).build();

        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            List<Trend> list = getDao().getTrend(vehicleID, duration);
            if (!list.isEmpty())
                return Response.ok(new GenericEntity<List<Trend>>(list) {
                }).build();
        }

        CustomDuration customDuration = CustomDuration.getDurationByDays(numberOfDays);
        if (customDuration != null) {
            List<Trend> list = getDao().getTrend(vehicleID, customDuration);
            if (!list.isEmpty())
                return Response.ok(new GenericEntity<List<Trend>>(list) {
                }).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }


    @Override
    public Response getLastLocation(String name) {
        Integer vehicleID = getVehicleIdByName(name);
        if (vehicleID == null)
            return Response.status(Status.NOT_FOUND).build();

        LastLocation location = getDao().getLastLocation(vehicleID);
        if (location != null) {
            return Response.ok(location).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getLastLocationExtraInfo(String name) {
        Integer vehicleID = getVehicleIdByName(name);
        if (vehicleID == null)
            return Response.status(Status.NOT_FOUND).build();

        LastLocation location = getDao().getLastLocation(vehicleID);
        if (location != null) {
            Trip trip = getDao().getLastTrip(vehicleID);
            VehicleStatus vehicleStatus = vehicleStatusUtil.determineStatusByVehicleId(vehicleID);

            if (trip == null) {
                return Response.status(Status.NOT_FOUND).build();
            }

            location.setLastTripTime(trip.getStartTime());
            location.setVehicleStatus(vehicleStatus.toString());

            return Response.ok(location).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }


    @Override
    public Response getLastTrip(String name) {
        Integer vehicleID = getVehicleIdByName(name);
        if (vehicleID == null)
            return Response.status(Status.NOT_FOUND).build();

        try {
            Trip trip = this.getDao().getLastTrip(vehicleID);
            if (trip != null)
                return Response.ok(new GenericEntity<Trip>(trip) {
                }).build();
            else
                return Response.status(Status.NOT_FOUND).build();
        } catch (AccessDeniedException ade) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response getTrips(String name, String day) {
        Integer vehicleID = getVehicleIdByName(name);
        if (vehicleID == null)
            return Response.status(Status.NOT_FOUND).build();

        DateTimeFormatter formatter = DateTimeFormat.forPattern(SIMPLE_DATE_FORMAT);
        DateTime startDate = formatter.parseDateTime(day).minusDays(1);
        List<Trip> list = getDao().getTrips(vehicleID, startDate.toDate(), new Date());

        for (Trip trip : list) {
            if (trip.hasGoodRoute()) {
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
        return Response.ok(new GenericEntity<List<Trip>>(list) {
        }).build();
    }

    @Override
    public Response getTrips(String name) {
        Integer vehicleID = getVehicleIdByName(name);
        if (vehicleID == null)
            return Response.status(Status.NOT_FOUND).build();

        DateTime startDate = new DateTime().minusDays(30);
        List<Trip> list = getDao().getTrips(vehicleID, startDate.toDate(), new Date());
        return Response.ok(new GenericEntity<List<Trip>>(list) {
        }).build();
    }


    public AddressLookup getAddressLookup() {
        return addressLookup;
    }

    public void setAddressLookup(AddressLookup addressLookup) {
        this.addressLookup = addressLookup;
    }

    @Override
    public Response getTrips(String name, String fromDateTime, String toDateTime) {
        Integer vehicleID = getVehicleIdByName(name);
        if (vehicleID == null)
            return Response.status(Status.NOT_FOUND).build();

        Date fromDate = buildDateTimeFromString(fromDateTime);
        Date toDate = buildDateTimeFromString(toDateTime);
        if (!validDateRange(fromDate, toDate)) return Response.status(Status.BAD_REQUEST).build();

        if (vehicleID != null && fromDate != null && toDate != null) {
            long diff = toDate.getTime() - fromDate.getTime();
            long diffDays = diff / (1000 * 60 * 60 * 24);

            if (diffDays <= 30L) {
                List<Trip> trips = this.getDao().getTrips(vehicleID, fromDate, toDate);
                if (trips == null) {
                    trips = new ArrayList<Trip>();
                }
                return Response.ok(new GenericEntity<List<Trip>>(trips) {
                }).build();
            } else {
                return Response.status(Status.BAD_REQUEST).build();
            }
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    private Date buildDateTimeFromString(String strDate) {
        if (strDate == null)
            return null;

        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            Date convertedDate = df.parse(strDate);
            return convertedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean validDateRange(Date fromDate, Date toDate) {
        return toDate.after(fromDate);
    }

    private Integer getVehicleIdByName(String name) {
        Integer vehicleId = null;
        Vehicle vehicle = getDao().findByName(name);
        if (vehicle != null) {
            vehicleId = vehicle.getVehicleID();
        }
        return vehicleId;
    }

    @Override
    public Response create(List<Vehicle> list, UriInfo uriInfo) {
        throw new NotImplementedException();
    }
}
