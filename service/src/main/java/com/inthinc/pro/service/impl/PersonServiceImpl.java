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
import com.inthinc.pro.dao.jdbc.AdminVehicleJDBCDAO;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.PersonScoresView;
import com.inthinc.pro.service.PersonService;
import com.inthinc.pro.service.adapters.PersonDAOAdapter;
import com.inthinc.pro.service.model.BatchResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonServiceImpl extends AbstractService<Person, PersonDAOAdapter> implements PersonService {
    private final Integer SCORE_NUM_DAYS = 6;
    private final Integer X_TEN=10;

    @Autowired
    RawScoreDAO rawScoreDAO;

    @Autowired 
    AdminVehicleJDBCDAO adminVehicleJDBCDAO;

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

            if (person.getDriverID()!=null) {
                //milesDriven
                personScoresView.setMilesDriven(adminVehicleJDBCDAO.getMilesDriven(person.getDriverID()));

                Map<String, Object> scoresMap = rawScoreDAO.getDScoreByDT(person.getDriverID(),SCORE_NUM_DAYS);

                //speeding
                personScoresView.setSpeeding(scoresMap.get("speeding")!=null?(Integer) scoresMap.get("speeding") * X_TEN:null);
                //aggresiveAccel
                personScoresView.setAggressiveAccel(scoresMap.get("aggressiveAccel")!=null ? (Integer) scoresMap.get("aggressiveAccel") * X_TEN:null);
                //aggressiveAccelEvents
                personScoresView.setAggressiveAccelEvents((Integer) scoresMap.get("aggressiveAccelEvents"));
                //aggressiveBrake
                personScoresView.setAggressiveBrake(scoresMap.get("aggressiveBrake")!=null ? (Integer) scoresMap.get("aggressiveBrake") * X_TEN:null);
                //aggressiveBrakeEvents
                personScoresView.setAggressiveBrakeEvents((Integer) scoresMap.get("aggressiveBrakeEvents"));
                //aggressiveBumpEvents
                personScoresView.setAggressiveBumpEvents((Integer) scoresMap.get("aggressiveBumpEvents"));
                //overall
                personScoresView.setOverall(scoresMap.get("overall")!=null ? (Integer) scoresMap.get("overall") * X_TEN:null);


                //aggressiveTurnEvents
                Integer aggressiveTotalEvents = 0;
                Integer aggressiveLeftEvents = (Integer) scoresMap.get("aggressiveLeftEvents");
                Integer aggressiveRightEvents = (Integer) scoresMap.get("aggressiveRightEvents");

                if (aggressiveLeftEvents != null)
                    aggressiveTotalEvents += aggressiveLeftEvents;
                if (aggressiveTotalEvents != null)
                    aggressiveTotalEvents += aggressiveRightEvents;

                personScoresView.setAggressiveTurnsEvents(aggressiveTotalEvents);


                // TODO - read custom fields from scoresMap and add to personScoresView
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
