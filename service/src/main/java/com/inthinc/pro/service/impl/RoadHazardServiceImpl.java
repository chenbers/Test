package com.inthinc.pro.service.impl;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.enums.EnumUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;

import com.inthinc.pro.model.Hazard;
import com.inthinc.pro.model.HazardType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.service.RoadHazardService;
import com.inthinc.pro.service.adapters.HazardDAOAdapter;

public class RoadHazardServiceImpl extends AbstractService<Hazard, HazardDAOAdapter> implements RoadHazardService {
    private static final Logger logger = Logger.getLogger(RoadHazardService.class);
    
    /**
     * Header key name where error messages will be stored under.
     */
    public static final String HEADER_ERROR_MESSAGE = "ERROR_MESSAGE";

    
    @Override
    public Response getRH(String mcmID, Double latitude, Double longitude) {
        logger.error("public Response getRH(String "+mcmID+", Double "+latitude+", Double "+longitude+")");
        HazardDAOAdapter hdaoa = getDao();
        Integer TwoHundredMilesInKm = 322;
        List<Hazard> responseList;
        Response response;
        try {
            responseList = hdaoa.findByDeviceLocationRadius(mcmID, new LatLng(latitude, longitude), TwoHundredMilesInKm);
            response = Response.ok(new GenericEntity<List<Hazard>>(responseList) {}).build();
        } catch (EmptyResultDataAccessException e){
            response = Response.noContent().build();
        }
        return response;
    }

    @Override
    public Response types(String locale) {
        logger.error("public Response types(String "+locale+")");
//        for(Object type : EnumUtils.getEnumList(HazardType.class)){
//            ((HazardType)type).getCode();
//        }
        //EnumSet.allOf(HazardType.class);
        List<HazardType> responseList = Arrays.asList(HazardType.values());
        for(HazardType type: responseList)
            System.out.println("[[[ "+type+"  ]]]");
        //List<HazardType> responseList =EnumUtils.getEnumList(HazardType.class);
        Response response = Response.ok(new GenericEntity<List<HazardType>>(responseList){}).build();
        // TODO Auto-generated method stub
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
