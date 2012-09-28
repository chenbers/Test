package com.inthinc.device.cassandras;

import com.inthinc.pro.automation.utils.AutoServers;

public class CassandraFactory {
    
    private final EventCassandraDAO ecdao;
    private final LocationCassandraDAO lcdao;
    
    public CassandraFactory(){
        ecdao = new EventCassandraDAO();
        lcdao = new LocationCassandraDAO(new AutoServers());
        CassandraDB cassandraDB = new CassandraDB("Iridium Archive", "note", "10.0.35.40:9160", 10, false);
        ecdao.setCassandraDB(cassandraDB);
        lcdao.setCassandraDB(cassandraDB);
    }
    
    public EventCassandraDAO getEventDAO(){
        return ecdao;
    }
    
    public LocationCassandraDAO getLocationDAO(){
        return lcdao;
    }

}
