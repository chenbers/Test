package com.inthinc.pro.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.dao.EventStatisticsDAO;
import com.inthinc.pro.dao.RawScoreDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.jdbc.AdminVehicleJDBCDAO;
import com.inthinc.pro.dao.report.DriverReportDAO;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.*;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.service.PersonService;
import com.inthinc.pro.service.adapters.PersonDAOAdapter;
import com.inthinc.pro.service.model.BatchResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonServiceImpl extends AbstractService<Person, PersonDAOAdapter> implements PersonService {
    private final Integer X_TEN = 10;

    @Autowired
    DriverReportDAO driverReportDAO;

    @Autowired
    EventStatisticsDAO eventStatisticsDAO;

    @Override
    public Response getAll() {
        List<Person> list = getDao().getAll();
        return Response.ok(new GenericEntity<List<Person>>(list) {
        }).build();
    }

    @Override
    public Response getPersonAndScores(Integer personID, Integer numberOfDays) {
        Person person = getDao().findByID(personID);
        if (person != null && person.getDriver() != null && person.getDriver().getDriverID() != null) {
            Score score = null;
            Integer scoreNumDays = 0;
            Integer driverID = person.getDriver().getDriverID();

            Duration duration = Duration.getDurationByDays(numberOfDays);
            if (duration != null) {
                score = driverReportDAO.getScore(driverID, duration);
            }

            CustomDuration customDuration = CustomDuration.getDurationByDays(numberOfDays);
            if (customDuration != null) {
                score = driverReportDAO.getScore(driverID, customDuration);
            }

            // standard person data
            PersonScoresView personScoresView = new PersonScoresView(person);

            // scoring data
            if (score != null) {
                personScoresView.setSpeeding(score.getSpeeding() != null ? score.getSpeeding().doubleValue() : 0d);
                personScoresView.setAggressiveAccel(score.getAggressiveAccel() != null ? score.getAggressiveAccel().doubleValue() : 0d);
                personScoresView.setAggressiveAccelEvents(score.getAggressiveAccelEvents() != null ? score.getAggressiveAccelEvents().doubleValue() : 0d);
                personScoresView.setAggressiveBrake(score.getAggressiveBrake() != null ? score.getAggressiveBrake().doubleValue() : 0d);
                personScoresView.setAggressiveBrakeEvents(score.getAggressiveBrakeEvents() != null ? score.getAggressiveBrakeEvents().doubleValue() : 0d);
                personScoresView.setAggressiveBumpEvents(score.getAggressiveBumpEvents() != null ? score.getAggressiveBumpEvents().doubleValue() : 0d);
                personScoresView.setOverall(score.getOverall() != null ? score.getOverall().doubleValue() : 0d);
                personScoresView.setMilesDriven(score.getMilesDriven() != null ? score.getMilesDriven().doubleValue() : 0d);

                // calculate custom field - turn
                Number numAgressiveLeftEvents = score.getAggressiveLeftEvents();
                Number numAgressiveRightEvents = score.getAggressiveRightEvents();
                Double aggressiveLeftEvents = numAgressiveLeftEvents != null ? numAgressiveLeftEvents.doubleValue() : 0d;
                Double aggressiveRightEvents = numAgressiveRightEvents != null ? numAgressiveRightEvents.doubleValue() : 0d;
                personScoresView.setAggressiveTurnsEvents(aggressiveLeftEvents + aggressiveRightEvents);

                // custom data from custom dao - event statistics
                if (duration != null) {
                    scoreNumDays = duration.getNumberOfDays();
                } else if (customDuration != null) {
                    scoreNumDays = customDuration.getNumberOfDays();
                }
                if (scoreNumDays != 0) {
                    Integer maxSpeed = eventStatisticsDAO.getMaxSpeedForPastDays(person.getDriverID(), scoreNumDays, null, null);
                    Double speedTime = eventStatisticsDAO.getSpeedingTimeInSecondsForPastDays(person.getDriverID(), scoreNumDays, null, null).doubleValue();
                    personScoresView.setSpeedTime(speedTime);
                    personScoresView.setMaxSpeed(convertToKmIfNeeded(person.getMeasurementType(), maxSpeed));
                }

            }

            return Response.ok(personScoresView).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response create(List<Person> persons, UriInfo uriInfo) {

        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Person person : persons) {
            BatchResponse batchResponse = new BatchResponse();
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("person");
            Integer id = getDao().create(person);
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
    public Response create(Integer id, Person person, UriInfo uriInfo) {
        Integer personID = getDao().create(id, person);
        if (personID != null) {
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            URI uri = uriBuilder.path(personID.toString()).build();
            person = getDao().findByID(personID);
            return Response.created(uri).entity(person).build();
        }
        return Response.serverError().build();
    }

    /**
     * Converts a value from miles (db) to km if needed based on the given measurement type.
     *
     * @param measurementType measurement type
     * @param value           value
     * @return converted value (if needed)
     */
    private Integer convertToKmIfNeeded(MeasurementType measurementType, Integer value) {
        if (measurementType == null)
            return value;

        if (value == null)
            return null;

        if (measurementType.equals(MeasurementType.ENGLISH))
            return value;

        return MeasurementConversionUtil.fromMilesToKilometers(value).intValue();
    }

    public EventStatisticsDAO getEventStatisticsDAO() {
        return eventStatisticsDAO;
    }

    public void setEventStatisticsDAO(EventStatisticsDAO eventStatisticsDAO) {
        this.eventStatisticsDAO = eventStatisticsDAO;
    }

    public DriverReportDAO getDriverReportDAO() {
        return driverReportDAO;
    }

    public void setDriverReportDAO(DriverReportDAO driverReportDAO) {
        this.driverReportDAO = driverReportDAO;
    }
}
