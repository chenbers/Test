package com.inthinc.pro.backing.dao.impl;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.dao.annotation.DaoParam;
import com.inthinc.pro.dao.hessian.proserver.ReportService;


public class ReportServiceImpl implements ReportService
{

    
    
    @Override
    public Map<String, Object> getDPctByGT(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration, 
            @DaoParam(name="driveQMetric", inputDesc="START_ODOMETER = 0;END_ODOMETER = 1;ODOMETER = 2;OVERALL = 3;SPEEDING = 4;SPEED1 = 5; SPEED2 = 6;SPEED3 = 7;SPEED4 = 8;SPEED5 = 9;STYLE = 10;BRAKE = 11;ACCEL = 12;TURN = 13;BUMP = 16;SEATBELT = 17;COACHING = 18;MPG_LIGHT = 19;MPG_MEDIUM = 20;MPG_HEAVY = 21;IDLE_LO = 22;IDLE_HI = 23;DRIVE_TIME = 24;")Integer metric)
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Map<String, Object> getDScoreByDT(@DaoParam(name="driverID")Integer driverID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getDTrendByDTC(@DaoParam(name="driverID")Integer driverID, 
            @DaoParam(name="aggType", inputDesc="0 (daily), 2 (monthly)")Integer duration,  
            @DaoParam(name="count", inputDesc="30 (30 days), 3 (3 months), 6 (6 months), 12 (12 months)")Integer count)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getDVScoresByGSE(@DaoParam(name="groupID")Integer groupID,             
            @DaoParam(name="startDate", isDate=true, inputDesc="MM/dd/yyyy hh:mm", inputConvert=com.inthinc.pro.convert.DateConvert.class)Integer startDate,
            @DaoParam(name="endDate", isDate=true, inputDesc="MM/dd/yyyy hh:mm", inputConvert=com.inthinc.pro.convert.DateConvert.class)Integer endDate
            )

    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getDVScoresByGT(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getGDScoreByGSE(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="startDate", isDate=true, inputDesc="MM/dd/yyyy hh:mm", inputConvert=com.inthinc.pro.convert.DateConvert.class)Integer startDate,
            @DaoParam(name="endDate", isDate=true, inputDesc="MM/dd/yyyy hh:mm", inputConvert=com.inthinc.pro.convert.DateConvert.class)Integer endDate
            )
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getGDScoreByGT(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getGDTrendByGTC(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getGVScoreByGSE(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="startDate", isDate=true, inputDesc="MM/dd/yyyy hh:mm", inputConvert=com.inthinc.pro.convert.DateConvert.class)Integer startDate,
            @DaoParam(name="endDate", isDate=true, inputDesc="MM/dd/yyyy hh:mm", inputConvert=com.inthinc.pro.convert.DateConvert.class)Integer endDate
            )
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<Map<String, Object>> getSDScoresByGT(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getSDTrendsByGTC(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="aggType", inputDesc="0 (daily), 2 (monthly)")Integer duration,  
            @DaoParam(name="count", inputDesc="30 (30 days), 3 (3 months), 6 (6 months), 12 (12 months)")Integer count)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getVDScoresByGT(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Map<String, Object> getVScoreByVT(@DaoParam(name="vehicleID")Integer vehicleID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<Map<String, Object>> getVTrendByVTC(@DaoParam(name="vehicleID")Integer vehicleID, 
            @DaoParam(name="aggType", inputDesc="0 (daily), 2 (monthly)")Integer duration,  
            @DaoParam(name="count", inputDesc="30 (30 days), 3 (3 months), 6 (6 months), 12 (12 months)")Integer count)
    {
        // TODO Auto-generated method stub
        return null;
    }
}
