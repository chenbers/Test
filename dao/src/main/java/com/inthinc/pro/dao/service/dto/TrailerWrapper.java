package com.inthinc.pro.dao.service.dto;

import java.util.ArrayList;
import java.util.List;

public class TrailerWrapper {
    private List<Trailer> trailers ;

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailerList) {
        this.trailers = trailerList;
    }
    
    public TrailerWrapper(){
        this.trailers = new ArrayList<Trailer>(); 
    }
    public TrailerWrapper(List<Trailer> trailers) {
        this.trailers = trailers;
    }
    
    public void addTrailer(Trailer trailer) {
        this.trailers.add(trailer);
    }

}
