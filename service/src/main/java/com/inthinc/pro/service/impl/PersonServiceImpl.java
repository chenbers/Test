package com.inthinc.pro.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.dao.*;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.dao.jdbc.AdminVehicleJDBCDAO;
import com.inthinc.pro.dao.report.DriverReportDAO;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.*;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Speed;
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

    @Autowired ScoreDAO scoreDAO;

    @Override
    public Response getAll() {
        List<Person> list = getDao().getAll();
        return Response.ok(new GenericEntity<List<Person>>(list) {
        }).build();
    }

    @Override
    public Response getAllWithScore(Integer numberOfDays) {
        List<Person> persons = filterOutPersonsWithNoDriverId(getDao().getAll());
        Duration duration = Duration.getDurationByDays(numberOfDays);
        CustomDuration customDuration = CustomDuration.getDurationByDays(numberOfDays);

        List<PersonScoresView> personViews = null;
        PersonScoresViewList personScoresViewList = new PersonScoresViewList();
        if (duration != null) {
            personViews = populateScoresAndSpeed(persons, duration);
        } else if (customDuration != null) {
            personViews = populateScoresAndSpeed(persons, customDuration);
        }

        if (personViews == null)
            personViews = new ArrayList<PersonScoresView>();

        personScoresViewList.setPersonScores(personViews);

        return Response.ok(personScoresViewList).build();
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

            if (duration != null) {
                scoreNumDays = duration.getNumberOfDays();
            } else if (customDuration != null) {
                scoreNumDays = customDuration.getNumberOfDays();
            }

            Speed speed = eventStatisticsDAO.getSpeedInfoForPastDays(person.getDriverID(), person.getMeasurementType(), scoreNumDays, null, null);

            // standard person data
            PersonScoresView personScoresView = new PersonScoresView(person, score, speed);

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

    private List<Person> filterOutPersonsWithNoDriverId(List<Person> persons){
        List<Person> retList = new ArrayList<Person>(persons.size());
        for (Person person: persons){
            if (person != null && person.getDriver() != null && person.getDriver().getDriverID() != null)
                retList.add(person);
        }
        return retList;
    }

    private List<PersonScoresView> populateScoresAndSpeed(List<Person> persons, Integer numberOfDays, Integer dvqCode){
        Integer groupID = getDao().getGroupID();
        Map<Integer, Speed> speedMap = eventStatisticsDAO.getSpeedInfoForPersons(persons, numberOfDays);
        List<DVQMap> dvqList = scoreDAO.getDriveQMapList(groupID, dvqCode);
        Map<Integer, Score> scoreMap = getPersonScoreMap(dvqList);
        List<PersonScoresView> personScoresViews = new ArrayList<PersonScoresView>();

        for (Person person: persons){
            PersonScoresView personScoresView = new PersonScoresView();
            personScoresView.setPerson(person);
            personScoresView.setScore(scoreMap.get(person.getDriverID()));
            personScoresView.setSpeed(speedMap.get(person.getDriverID()));
            personScoresViews.add(personScoresView);
        }
        return personScoresViews;
    }

    private List<PersonScoresView> populateScoresAndSpeed(List<Person> persons, Duration duration) {
        return populateScoresAndSpeed(persons, duration.getNumberOfDays(), duration.getDvqCode());
    }

    private List<PersonScoresView> populateScoresAndSpeed(List<Person> persons, CustomDuration customDuration) {
        return populateScoresAndSpeed(persons, customDuration.getNumberOfDays(), customDuration.getDvqCode());
    }

    private Map<Integer, Score> getPersonScoreMap(List<DVQMap> dvqMapList){
        Map<Integer, Score> scoreMap = new HashMap<Integer, Score>(dvqMapList.size());

        for (DVQMap dvqMap: dvqMapList){
            Driver driver = dvqMap.getDriver();
            if (driver!=null && driver.getDriverID() != null){
                Integer driverID = driver.getDriverID();
                DriveQMap driveQMap = dvqMap.getDriveQ();
                if (driveQMap != null) {
                    Score score = new Score();
                    score.setAggressiveAccel(driveQMap.getAggressiveAccel());
                    score.setAggressiveAccelEvents(driveQMap.getAggressiveAccelEvents());
                    score.setAggressiveBrake(driveQMap.getAggressiveBrake());
                    score.setAggressiveBrakeEvents(driveQMap.getAggressiveBrakeEvents());
                    score.setAggressiveBump(driveQMap.getAggressiveBump());
                    score.setAggressiveBumpEvents(driveQMap.getAggressiveBumpEvents());
                    score.setAggressiveEvents(driveQMap.getAggressiveEvents());
                    score.setAggressiveLeftEvents(driveQMap.getAggressiveLeftEvents());
                    score.setAggressiveRightEvents(driveQMap.getAggressiveRightEvents());
                    score.setAggressiveTurn(driveQMap.getAggressiveTurn());
                    score.setStartingOdometer(driveQMap.getStartingOdometer());
                    score.setEndingOdometer(driveQMap.getEndingOdometer());

                    scoreMap.put(driverID, score);
                }
            }
        }

        return scoreMap;
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
