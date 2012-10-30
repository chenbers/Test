package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import com.inthinc.pro.dao.RoadHazardDAO;
import com.inthinc.pro.dao.hessian.proserver.MCMService;
import com.inthinc.pro.model.Hazard;
import com.inthinc.pro.model.HazardStatus;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.service.RoadHazardService;
import com.inthinc.pro.service.adapters.HazardDAOAdapter;
import com.inthinc.pro.service.adapters.UserDAOAdapter;
import com.inthinc.pro.service.model.BatchResponse;
import com.inthinc.pro.service.note.Attribute;
import com.inthinc.pro.service.note.AttributeType;
import com.inthinc.pro.service.note.Note;

public class RoadHazardServiceImpl extends AbstractService<Hazard, HazardDAOAdapter> implements RoadHazardService {
    private static final Logger logger = Logger.getLogger(RoadHazardService.class);
    
    private MCMService mcmService;
    //private CrashReportDAO crashReportDAO;
    //private RoadHazardDAO adminHazardJDBCDAO;
    
    /**
     * Header key name where error messages will be stored under.
     */
    public static final String HEADER_ERROR_MESSAGE = "ERROR_MESSAGE";

//    public String createSpeedEvent(String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit, Integer averageSpeed, Integer distance) {
//        Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT, speedLimit);
//        Attribute averageSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_AVG_SPEED, averageSpeed);
//        Attribute topSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_TOP_SPEED, speed);
//        Attribute distanceAttribute = new Attribute(AttributeType.ATTR_TYPE_DISTANCE, distance);
//        Note note = new Note(NoteType.SPEEDING, new Date(), latitude, longitude, speed, 10, 9, 1, speedLimitAttribute, averageSpeedAttribute, topSpeedAttribute, distanceAttribute);
//        List<byte[]> byteList = new ArrayList<byte[]>();
//        byteList.add(note.getBytes());
//        List<Map<String, Object>> mapList = mcmService.note(imei, byteList);
//        if (mapList.size() > 0) {
//            return mapList.get(0).get("data").toString();
//        }
//        return "1";
//    }
    
    @Override
    public Response getRH(Integer deviceID, Double latitude, Double longitude) {
        logger.error("public Response getRH(Integer "+deviceID+", Double "+latitude+", Double "+longitude+")");
        //TODO: jwimmer: replace with real implementation
        HazardDAOAdapter hdaoa = getDao();
        logger.error("hdaoa: "+hdaoa);
        logger.error("hdaoa.accountID: "+hdaoa.getAccountID());
        List<Hazard> responseList = hdaoa.findAllInAccount(getDao().getAccountID());
//        Hazard fake1 = new Hazard();
//        
//        
//        Hazard fake2 = new Hazard();
//        fake2.setAccountID(1);
//        fake2.setDescription("fake 2");
//        fake2.setDriverID(1);
//        fake2.setStatus(HazardStatus.ACTIVE);
//        responseList.add(fake1);
//        responseList.add(fake2);
//        
//        Person p1 = new Person();
//        p1.setAcctID(1);
//        Person p2 = new Person();
//        p2.setAcctID(1);
//        p2.setFirst("first");
//        List<Person> persons = new ArrayList<Person>();
//        persons.add(p1);
//        persons.add(p2);
        
//        List<User> list = getDao().getAll();
//        return Response.ok(new GenericEntity<List<User>>(list) {
//        }).build();
        // TODO Auto-generated method stub
        Response response = Response.ok(new GenericEntity<List<Hazard>>(responseList) {}).build();
        //Response response = Response.ok(new GenericEntity<List<Person>>(persons) {}).build();
        return response;
    }
    
    /***  Many methods from GenericService have not been implemented yet because they are not needed for this inital Road Hazard Webservice  ***/
//    @Override
//    public Response get(Integer id) {
//        // TODO not yet implemented, since not needed for initial web service needs
//        throw new NotImplementedException();
//    }
//
    @Override
    public Response getAll() {
     // TODO not yet implemented, since not needed for initial web service needs
        throw new NotImplementedException();
    }
//
//    @Override
//    public Response create(Hazard object, UriInfo uriInfo) {
//        //hindsight... perhaps this would have been cleaner?
//     // TODO not yet implemented, since not needed for initial web service needs
//        throw new NotImplementedException();
//    }
//
//    @Override
//    public Response update(Hazard object) {
//     // TODO not yet implemented, since not needed for initial web service needs
//        throw new NotImplementedException();
//    }
//
//    @Override
//    public Response delete(Integer id) {
//     // TODO not yet implemented, since not needed for initial web service needs
//        throw new NotImplementedException();
//    }
//
    @Override
    public Response create(List<Hazard> list, UriInfo uriInfo) {
     // TODO not yet implemented, since not needed for initial web service needs
        throw new NotImplementedException();
    }
//
//    @Override
//    public Response update(List<Hazard> list) {
//     // TODO not yet implemented, since not needed for initial web service needs
//        throw new NotImplementedException();
//    }
//
//    @Override
//    public Response delete(List<Integer> list) {
//     // TODO not yet implemented, since not needed for initial web service needs
//        throw new NotImplementedException();
//    }
    
    /***  Getters/Setters  ***/
//    public RoadHazardDAO getAdminHazardJDBCDAO() {
//        return adminHazardJDBCDAO;
//    }
//
//    public void setAdminHazardJDBCDAO(RoadHazardDAO adminHazardJDBCDAO) {
//        this.adminHazardJDBCDAO = adminHazardJDBCDAO;
//    }
}
