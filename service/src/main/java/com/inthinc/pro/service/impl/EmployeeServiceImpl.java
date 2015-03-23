package com.inthinc.pro.service.impl;

import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.model.CustomDuration;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.service.EmployeeService;
import com.inthinc.pro.service.adapters.DriverDAOAdapter;
import com.inthinc.pro.service.model.BatchResponse;
import com.inthinc.pro.util.DateUtil;
import org.apache.log4j.Logger;
import org.joda.time.Interval;
import org.springframework.security.AccessDeniedException;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmployeeServiceImpl extends AbstractService<Driver, DriverDAOAdapter> implements EmployeeService {
    private static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);
    private static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";

    static final Integer DEFAULT_PAST_TIMING = -30;

    @Override
    public Response getAll() {
        List<Driver> list = getDao().getAll();
        return Response.ok(new GenericEntity<List<Driver>>(list) {
        }).build();
    }

    @Override
    public Response get(String employeeID) {
        Integer driverID = null;
        try {
            driverID = getDao().getDriverIDByEmpID(employeeID);
        } catch(EmptyResultSetException e){
            return Response.status(Status.NOT_FOUND).build();
        }

        if (driverID != null) {
            Driver driver = getDao().findByID(driverID);
            if (driver != null)
                return Response.ok(driver).build();
        }

        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getSpeedingEvents(String employeeID) {
        Integer driverID = null;
        try {
            driverID = getDao().getDriverIDByEmpID(employeeID);
        } catch(EmptyResultSetException e){
            return Response.status(Status.NOT_FOUND).build();
        }

        if (driverID != null) {
            List<Event> eventList = getDao().getSpeedingEvents(driverID);
            if (eventList != null && !eventList.isEmpty()) {
                return Response.ok(new GenericEntity<List<Event>>(eventList) {
                }).build();
            }
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
        return Response.ok(new GenericEntity<List<BatchResponse>>(responseList) {
        }).build();
    }

    @Override
    public Response getScore(String employeeID, Integer numberOfDays) {
        Integer driverID = null;
        try {
            driverID = getDao().getDriverIDByEmpID(employeeID);
        } catch(EmptyResultSetException e){
            return Response.status(Status.NOT_FOUND).build();
        }

        if (driverID != null) {
            Duration duration = Duration.getDurationByDays(numberOfDays);
            if (duration != null) {
                Score score = getDao().getScore(driverID, duration);
                if (score != null)
                    return Response.ok(score).build();
            }

            CustomDuration customDuration = CustomDuration.getDurationByDays(numberOfDays);
            if (customDuration != null) {
                Score score = getDao().getScore(driverID, customDuration);
                if (score != null)
                    return Response.ok(score).build();
            }
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getScore(String employeeID, String month) {
        try {
            Integer driverID = null;
            try {
                driverID = getDao().getDriverIDByEmpID(employeeID);
            } catch(EmptyResultSetException e){
                return Response.status(Status.NOT_FOUND).build();
            }

            if (driverID != null) {

                Interval interval = DateUtil.getIntervalFromMonth(month);
                //round about way of getting this with existing hessian methods
                Driver driver = getDao().findByID(driverID);
                List<DriverVehicleScoreWrapper> list = getDao().getDriverScores(driver.getGroupID(), interval);
                if (!list.isEmpty()) {
                    Score score = extractScore(list, driverID);
                    return Response.ok(score).build();
                }
            }
        } catch (ParseException e) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        return Response.status(Status.NOT_FOUND).build();
    }

    private Score extractScore(List<DriverVehicleScoreWrapper> list, Integer driverID) {
        for (DriverVehicleScoreWrapper dvsw : list) {
            if (driverID.equals(dvsw.getDriver().getDriverID())) {
                return dvsw.getScore();
            }
        }

        return null;
    }

    @Override
    public Response getScore(String employeeID) {
        // TODO Auto-generated method stub
        return getScore(employeeID, "");
    }

    @Override
    public Response getTrend(String employeeID, Integer numberOfDays) {
        Integer driverID = null;
        try {
            driverID = getDao().getDriverIDByEmpID(employeeID);
        } catch(EmptyResultSetException e){
            return Response.status(Status.NOT_FOUND).build();
        }

        if (driverID != null) {
            Duration duration = Duration.getDurationByDays(numberOfDays);
            if (duration != null) {
                List<Trend> list = getDao().getTrend(driverID, duration);
                if (!list.isEmpty())
                    return Response.ok(new GenericEntity<List<Trend>>(list) {
                    }).build();
            }

            CustomDuration customDuration = CustomDuration.getDurationByDays(numberOfDays);
            if (customDuration != null) {
                List<Trend> list = getDao().getTrend(driverID, customDuration);
                if (!list.isEmpty())
                    return Response.ok(new GenericEntity<List<Trend>>(list) {}).build();
            }
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    /**
     * {@inheritDoc}
     *
     * @see com.inthinc.pro.service.DriverService#getLastTrip(Integer)
     */
    @Override
    public Response getLastTrip(String employeeID) {
        if (employeeID != null) {
            try {
                Integer driverID = null;
                try {
                    driverID = getDao().getDriverIDByEmpID(employeeID);
                } catch(EmptyResultSetException e){
                    return Response.status(Status.NOT_FOUND).build();
                }

                if (driverID != null) {
                    Trip trip = this.getDao().getLastTrip(driverID);
                    if (trip != null)
                        return Response.ok(new GenericEntity<Trip>(trip) {
                        }).build();
                    else
                        return Response.status(Status.NOT_FOUND).build();
                }
            } catch (AccessDeniedException ade) {
                return Response.status(Status.NOT_FOUND).build();
            }
        }

        return Response.status(Status.NOT_FOUND).build();
    }

    /**
     * {@inheritDoc}
     *
     * @see com.inthinc.pro.service.DriverService#getLastTrips(Integer, String)
     */
    @Override
    public Response getLastTrips(String employeeID, String date) {
        Date startDate = buildDateFromString(date);
        Date today = new Date();

        if (employeeID != null && startDate != null) {
            Integer driverID = null;
            try {
                driverID = getDao().getDriverIDByEmpID(employeeID);
            } catch(EmptyResultSetException e){
                return Response.status(Status.NOT_FOUND).build();
            }

            if (driverID != null) {
                long diff = today.getTime() - startDate.getTime();
                long diffDays = diff / (1000 * 60 * 60 * 24);

                if (diffDays <= 30L) {
                    List<Trip> trips = this.getDao().getLastTrips(driverID, startDate);
                    if (trips == null) {
                        trips = new ArrayList<Trip>();
                    }
                    return Response.ok(new GenericEntity<List<Trip>>(trips) {
                    }).build();
                } else {
                    return Response.status(Status.BAD_REQUEST).build();
                }
            }
        }

        return Response.status(Status.NOT_FOUND).build();
    }

    private Date buildDateFromString(String strDate) {
        if (strDate == null)
            return null;

        DateFormat df = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        try {
            Date convertedDate = df.parse(strDate);
            return convertedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see com.inthinc.pro.service.DriverService#getLastTrips(Integer)
     */
    @Override
    public Response getLastTrips(String employeeID) {
        DateFormat df = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, DEFAULT_PAST_TIMING);
        return getLastTrips(employeeID, df.format(c.getTime()));
    }

    /**
     * {@inheritDoc}
     *
     * @see com.inthinc.pro.service.DriverService#getLastLocation(Integer)
     */
    @Override
    public Response getLastLocation(String employeeID) {
        if (employeeID != null) {
            Integer driverID = null;
            try {
                driverID = getDao().getDriverIDByEmpID(employeeID);
            } catch(EmptyResultSetException e){
                return Response.status(Status.NOT_FOUND).build();
            }

            if (driverID != null) {
                LastLocation location = this.getDao().getLastLocation(driverID);
                if (location != null)
                    return Response.ok(new GenericEntity<LastLocation>(location) {
                    }).build();
                else
                    return Response.status(Status.NOT_FOUND).build();
            }
        }

        return Response.status(Status.NOT_FOUND).build();
    }

    public static String getSimpleDateFormat() {
        return SIMPLE_DATE_FORMAT;
    }

    @Override
    public Response getTrips(String employeeID, String fromDateTime, String toDateTime) {
        Date fromDate = DateUtil.buildDateTimeFromString(fromDateTime);
        Date toDate = DateUtil.buildDateTimeFromString(toDateTime);
        if (!validDateRange(fromDate, toDate)) return Response.status(Status.BAD_REQUEST).build();

        if (employeeID != null && fromDate != null && toDate != null) {
            Integer driverID = null;
            try {
                driverID = getDao().getDriverIDByEmpID(employeeID);
            } catch(EmptyResultSetException e){
                return Response.status(Status.NOT_FOUND).build();
            }

            if (driverID != null) {
                long diff = toDate.getTime() - fromDate.getTime();
                long diffDays = diff / (1000 * 60 * 60 * 24);

                if (diffDays <= 30L) {
                    List<Trip> trips = this.getDao().getTrips(driverID, fromDate, toDate);
                    if (trips == null) {
                        trips = new ArrayList<Trip>();
                    }
                    return Response.ok(new GenericEntity<List<Trip>>(trips) {
                    }).build();
                } else {
                    return Response.status(Status.BAD_REQUEST).build();
                }
            }
        }
        return Response.status(Status.NOT_FOUND).build();
    }



    private boolean validDateRange(Date fromDate, Date toDate) {
        return toDate.after(fromDate);
    }

    /**
     * Gets the driverID associated with a person that has the given employee id.
     * It's used to convert from empId based requests to driverId based requests.
     * If not found, it returns null. This should be treated as a NOT_FOUND http error.
     *
     * @param empID employee id
     * @return driverID or null if not found
     */
    private Integer getDriverIDByEmpID(String empID) {
        return getDao().getDriverIDByEmpID(empID);
    }
}
