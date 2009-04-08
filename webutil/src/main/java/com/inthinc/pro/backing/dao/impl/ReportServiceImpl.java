package com.inthinc.pro.backing.dao.impl;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.dao.annotation.DaoParam;
import com.inthinc.pro.backing.dao.annotation.MethodDescription;
import com.inthinc.pro.dao.hessian.proserver.ReportService;


public class ReportServiceImpl implements ReportService
{

    
    
    @Override
    @MethodDescription(description="Returns a quintileMap with the 5 scores for the given group,duration,metric")
    public Map<String, Object> getDPctByGT(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration, 
            @DaoParam(name="driveQMetric", inputDesc="START_ODOMETER = 0;END_ODOMETER = 1;ODOMETER = 2;OVERALL = 3;SPEEDING = 4;SPEED1 = 5; SPEED2 = 6;SPEED3 = 7;SPEED4 = 8;SPEED5 = 9;STYLE = 10;BRAKE = 11;ACCEL = 12;TURN = 13;BUMP = 16;SEATBELT = 17;COACHING = 18;MPG_LIGHT = 19;MPG_MEDIUM = 20;MPG_HEAVY = 21;IDLE_LO = 22;IDLE_HI = 23;DRIVE_TIME = 24;")Integer metric)
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    @MethodDescription(description="Returns a driveQMap with the drivers scores for the specified duration")
    public Map<String, Object> getDScoreByDT(@DaoParam(name="driverID")Integer driverID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @MethodDescription(description="Returns a list of 'count' driveQMaps with the drivers scores for the specified aggregation type")
    public List<Map<String, Object>> getDTrendByDTC(@DaoParam(name="driverID")Integer driverID, 
            @DaoParam(name="aggType", inputDesc="0 (daily), 2 (monthly)")Integer duration,  
            @DaoParam(name="count", inputDesc="30 (30 days), 3 (3 months), 6 (6 months), 12 (12 months)")Integer count)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @MethodDescription(description="Returns a list of DVQMaps.  Each DVQMap contains a driverMap, an optional vehicleMap and a driveQMap.  The vehicleMap will be included if the driver is mapped to a vehicle.")  
    public List<Map<String, Object>> getDVScoresByGSE(@DaoParam(name="groupID")Integer groupID,             
            @DaoParam(name="startDate", inputDesc="MM/dd/yyyy hh:mm")Long startDate,
            @DaoParam(name="endDate", inputDesc="MM/dd/yyyy hh:mm")Long endDate
            )

    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @MethodDescription(description="Returns a list of DVQMaps.  Each DVQMap contains a driverMap, an optional vehicleMap and a driveQMap.  The vehicleMap will be included if the driver is mapped to a vehicle.")  
    public List<Map<String, Object>> getDVScoresByGT(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @MethodDescription(description="Returns list of driver scores for all drivers in the specified group and time frame specified by start/end date.")
    public Map<String, Object> getGDScoreByGSE(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="startDate", inputDesc="MM/dd/yyyy hh:mm")Long startDate,
            @DaoParam(name="endDate", inputDesc="MM/dd/yyyy hh:mm")Long endDate
            )
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @MethodDescription(description="Get list of driver score for the specified group and duration.")
    public Map<String, Object> getGDScoreByGT(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @MethodDescription(description="Returns a driveQMap for the specified group and duration. The scores represent the weighted average scores for all drivers in the group and its subgroups.")
    public Map<String, Object> getGDTrendByGTC(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @MethodDescription(description="Returns list of driver scores for all vehicles in the specified group and time frame specified by start/end date.")
    public Map<String, Object> getGVScoreByGSE(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="startDate", inputDesc="MM/dd/yyyy hh:mm")Long startDate,
            @DaoParam(name="endDate", inputDesc="MM/dd/yyyy hh:mm")Long endDate
            )
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    @MethodDescription(description="Returns a List of GQMaps for the specified groupID, duration. Scores returned for each subgroup are the weighted average of all drivers in the subgroup and its subordinate subgroups.")

    public List<Map<String, Object>> getSDScoresByGT(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @MethodDescription(description="Returns a List of GQLists for the specified  group, duration, count. Trend returned for each subgroup are the weighted average of all the drivers in the subgroup and its subordinate subgroups.")

    public List<Map<String, Object>> getSDTrendsByGTC(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="aggType", inputDesc="0 (daily), 2 (monthly)")Integer duration,  
            @DaoParam(name="count", inputDesc="30 (30 days), 3 (3 months), 6 (6 months), 12 (12 months)")Integer count)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @MethodDescription(description="Returns a list of DVQMaps. Each DVQMap contains an optional driverMap, an vehicleMap and a driveQMap.  The driverMap will be included if the vehicle is currently mapped to a driver.  In the event that the vehicle is not mapped to a driver, then no driver record will be included in the DVQMap.")
    public List<Map<String, Object>> getVDScoresByGT(@DaoParam(name="groupID")Integer groupID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    @MethodDescription(description="Returns a driveQMap with the vehicles scores for the specified timeScale.")
    public Map<String, Object> getVScoreByVT(@DaoParam(name="vehicleID")Integer vehicleID, 
            @DaoParam(name="duration", inputDesc="1 (30 days), 2 (3 months), 3 (6 months), 4 (12 months)")Integer duration)
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    @MethodDescription(description="Returns a list of 'count' driveQMaps with the vehicles scores for the specified aggregation type.")
    public List<Map<String, Object>> getVTrendByVTC(@DaoParam(name="vehicleID")Integer vehicleID, 
            @DaoParam(name="aggType", inputDesc="0 (daily), 2 (monthly)")Integer duration,  
            @DaoParam(name="count", inputDesc="30 (30 days), 3 (3 months), 6 (6 months), 12 (12 months)")Integer count)
    {
        // TODO Auto-generated method stub
        return null;
    }
}
