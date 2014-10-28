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

    @Override
    public Response getAll() {
        List<Person> list = getDao().getAll();
        return Response.ok(new GenericEntity<List<Person>>(list) {
        }).build();
    }

    @Override
    public Response getPersonAndScores(Integer personID) {
        Person person = getDao().findByID(personID);
        if (person != null) {
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



                Integer aggressiveTotalEvents = 0;
                Integer aggressiveLeftEvents = (Integer) scoresMap.get("aggressiveLeftEvents");
                Integer aggressiveRightEvents = (Integer) scoresMap.get("aggressiveRightEvents");

                if (aggressiveLeftEvents != null)
                    aggressiveTotalEvents += aggressiveLeftEvents;
                if (aggressiveTotalEvents != null)
                    aggressiveRightEvents += aggressiveRightEvents;

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

    public RawScoreDAO getRawScoreDAO() {
        return rawScoreDAO;
    }

    public void setRawScoreDAO(RawScoreDAO rawScoreDAO) {
        this.rawScoreDAO = rawScoreDAO;
    }
}
