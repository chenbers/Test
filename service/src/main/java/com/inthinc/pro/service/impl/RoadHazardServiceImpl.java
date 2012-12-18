package com.inthinc.pro.service.impl;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.WeakHashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.enums.EnumUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.inthinc.pro.model.BoundingBox;
import com.inthinc.pro.model.Hazard;
import com.inthinc.pro.model.HazardType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.service.RoadHazardService;
import com.inthinc.pro.service.adapters.HazardDAOAdapter;
import com.inthinc.pro.service.security.TiwiproPrincipal;

public class RoadHazardServiceImpl extends AbstractService<Hazard, HazardDAOAdapter> implements RoadHazardService {
    private static final Logger logger = Logger.getLogger(RoadHazardService.class);
    
    /**
     * Header key name where error messages will be stored under.
     */
    public static final String HEADER_ERROR_MESSAGE = "ERROR_MESSAGE";
    
    @Autowired
    TiwiproPrincipal principal;

	@Override
	public Response getRH(
			@PathParam("acctID") Integer acctID,
			@PathParam("sw_lat") Double sw_latitude,
			@PathParam("sw_lng") Double sw_longitude,
			@PathParam("ne_lat") Double ne_latitude,
			@PathParam("ne_lng") Double ne_longitude) {
	    logger.debug("public Response getRH("+acctID+", "+sw_latitude+", "+sw_longitude+", "+ne_latitude+", "+ne_longitude+")");
        HazardDAOAdapter hdaoa = getDao();
        List<Hazard> responseList;
        Response response;
        try {
            BoundingBox box = new BoundingBox(sw_latitude, sw_longitude, ne_latitude, ne_longitude);
            //responseList = hdaoa.findHazardsByUserAcct(principal.getUser(), box );
            responseList = hdaoa.findInAccountBoundingBox(acctID, box);
            response = Response.ok(new GenericEntity<List<Hazard>>(responseList) {}).build();
        } catch (EmptyResultDataAccessException e){
            response = Response.noContent().build();
        }
        return response;
	}
	
	@Override
	@GET
	@Path("/roadhazard/getRH/{mcmID}/{lat}/{lng}/{distanceInMeters}")
	public Response getRH(@PathParam("mcmID") String mcmID,
			@PathParam("lat") Double latitude,
			@PathParam("lng") Double longitude,
			@PathParam("distanceInMeters") Integer distanceInMeters) {
		// TODO Auto-generated method stub
        HazardDAOAdapter hdaoa = getDao();
        
        List<Hazard> responseList;
        Response response;
        try {
            responseList = hdaoa.findByDeviceLocationRadius(mcmID, new LatLng(latitude, longitude), distanceInMeters*1000);
            response = Response.ok(new GenericEntity<List<Hazard>>(responseList) {}).build();
        } catch (EmptyResultDataAccessException e){
            response = Response.noContent().build();
        }
        return response;
	}
	
    @Override
    public Response getRH(String mcmID, Double latitude, Double longitude) {
        logger.info("public Response getRH(String "+mcmID+", Double "+latitude+", Double "+longitude+")");
        Integer twoHundredMilesInKm = 322;
        return getRH(mcmID, latitude, longitude, twoHundredMilesInKm);
    }

    private final WeakHashMap<Locale, ResourceBundle> cache = new WeakHashMap<Locale, ResourceBundle>();
    private MessageSource                 messageSource;
    @Override
    public Response types(String locale) {
        logger.info("public Response types(String "+locale+")");
        Locale theLocale = new Locale(locale);
//        ResourceBundle resources = cache.get(locale);
//        if(resources == null){
//            resources = ResourceBundle.getBundle("com.inthinc.pro.resources.Messages", theLocale);
//            cache.put(theLocale, resources);
//        }
        List<HazardType> responseList = Arrays.asList(HazardType.values());
        try {
            for(HazardType type: responseList){
                int endOfGroupIndex = type.toString().indexOf("_");
                endOfGroupIndex = (endOfGroupIndex != -1)?endOfGroupIndex:type.toString().length();
                String groupKey = type.toString().substring(0, endOfGroupIndex);
                try{
                    type.setGroup(messageSource.getMessage(type.getClass().getSimpleName()+"."+groupKey+".group", null, theLocale));
                    type.setName (messageSource.getMessage(type.getClass().getSimpleName()+"."+type.name()+".name", null, theLocale));
                } catch(NoSuchMessageException nsme){
                    logger.error(nsme);
                }
            }
            
        } catch(MissingResourceException mre) {
            logger.error(mre);
        }
        
        Response response = Response.ok(new GenericEntity<List<HazardType>>(responseList){}).build();
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

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    /***  Getters/Setters  ***/
//    public RoadHazardDAO getAdminHazardJDBCDAO() {
//        return adminHazardJDBCDAO;
//    }
//
//    public void setAdminHazardJDBCDAO(RoadHazardDAO adminHazardJDBCDAO) {
//        this.adminHazardJDBCDAO = adminHazardJDBCDAO;
//    }
}
