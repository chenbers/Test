package com.inthinc.pro.dao.service;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.TrailerDAO;
import com.inthinc.pro.dao.service.mapper.MapperFactory;
import com.inthinc.pro.dao.service.mapper.TrailerMapper;
import com.inthinc.pro.model.Trailer;


public class TrailerServiceDAO extends GenericServiceDAO<Trailer, Integer> implements TrailerDAO {

    private static final long serialVersionUID = 1L;
    
    private TrailerMapper trailerMapper;
    private RestAPI restAPI;
    
    public TrailerServiceDAO(String assetAPI, String username, String password) {
        this.restAPI = new RestAPI(assetAPI, username, password);
        this.trailerMapper = (TrailerMapper) MapperFactory.getMapper("Trailer");
    }
    
    @Override
    public Integer create(Integer id, Trailer trailer) {
        try {
            com.inthinc.pro.dao.service.dto.Trailer dtoTrailer = trailerMapper.mapToDTO(trailer);
        dtoTrailer = restAPI.postObject(com.inthinc.pro.dao.service.dto.Trailer.class, dtoTrailer,  "/trailer");
             Trailer newTrailer = trailerMapper.mapFromDTO(dtoTrailer);
             return newTrailer.getTrailerID();
        } catch (Exception e) {
            throw new ProDAOException("create trailer failed", e);
        }
    }

    @Override
    public Integer update(Trailer entity) {
        throw new NotImplementedException();
    }

    @Override
    public Integer deleteByID(Integer id) {
        throw new NotImplementedException();
    }

    @Override
    public Trailer findByID(Integer trailerID) {
        
        com.inthinc.pro.dao.service.dto.Trailer dtoTrailer;
        try {
            dtoTrailer = restAPI.getObject(com.inthinc.pro.dao.service.dto.Trailer.class,  "/trailer/"+trailerID);
            return trailerMapper.mapFromDTO(dtoTrailer);
        } catch (Exception e) {
            throw new ProDAOException("findByID trailer failed", e);
        }
    }

    @Override
    public void switchSilo(Integer siloID) {
    }
    
}
