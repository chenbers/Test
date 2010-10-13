package com.inthinc.pro.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.Interval;

import com.inthinc.pro.dao.StateMileageDAO;

import com.inthinc.pro.model.StateMileage;

public class StateMileageJDBCDAO  extends GenericJDBCDAO  implements StateMileageDAO{

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(StateMileageJDBCDAO.class);

    public List<StateMileage> getStateMileageByGroupAndMonth(Integer groupID, Interval interval, Boolean dotOnly)
    {
        return null;
    }
    
    public List<StateMileage> getStateMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly)
    {
        return null;
    }
    
    public List<StateMileage> getStateMileageByVehicleRoad(Integer groupID, Interval interval, Boolean dotOnly)
    {
        return null;
    }
    
    public List<StateMileage> getFuelStateMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly)
    {
        return null;
    }

    public List<StateMileage> getMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly)
    {
        return null;
    }

}
