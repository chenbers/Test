package com.inthinc.pro.backing.dao.impl;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.dao.annotation.DaoParam;
import com.inthinc.pro.backing.dao.annotation.MethodDescription;
import com.inthinc.pro.backing.dao.model.CrudType;
import com.inthinc.pro.backing.dao.validator.ValidatorType;
import com.inthinc.pro.dao.hessian.proserver.ReportService;


public class ReportServiceImpl implements ReportService
{

	private static final long serialVersionUID = -8644863464620429512L;


	@Override
    @MethodDescription(description="Returns 5 percentage scores for the given group,duration,metric", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.QuintileMap.class)
    public Map<String, Object> getDPctByGT(@DaoParam(name="groupID", validator=ValidatorType.GROUP)Integer groupID, 
            @DaoParam(name="duration", type=com.inthinc.pro.backing.dao.ui.DurationCodeList.class)Integer duration, 
            @DaoParam(name="score metric", type=com.inthinc.pro.backing.dao.ui.DriveQMetricList.class, inputDesc="Scoring Information to fetch")Integer metric)
    {
        return null;
    }


    @Override
    @MethodDescription(description="Returns the drivers scores for the specified duration", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.DriveQMap.class)
    public Map<String, Object> getDScoreByDT(@DaoParam(name="driverID", validator=ValidatorType.DRIVER)Integer driverID, 
            @DaoParam(name="duration", type=com.inthinc.pro.backing.dao.ui.DurationDvqCodeList.class)Integer duration)
    {
        return null;
    }

    @Override
    @MethodDescription(description="Returns a list the drivers scores for the specified aggregation type", crudType=CrudType.READ, modelClass=com.inthinc.pro.model.DriveQMap.class)
    public List<Map<String, Object>> getDTrendByDTC(@DaoParam(name="driverID", validator=ValidatorType.DRIVER)Integer driverID, 
            @DaoParam(name="aggregation type", type=com.inthinc.pro.backing.dao.ui.DurationBinSizeList.class)Integer duration,  
            @DaoParam(name="count", type=com.inthinc.pro.backing.dao.ui.DurationCountList.class)Integer count)
    {
        return null;
    }

    @Override
    @MethodDescription(description="Returns a list where each record contains a driver, an optional vehicle and scores.  The vehicle will be included if the driver is mapped to a vehicle.", crudType=CrudType.READ, 
    					modelClass=com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper.class)  
    public List<Map<String, Object>> getDVScoresByGSE(@DaoParam(name="groupID", validator=ValidatorType.GROUP)Integer groupID,             
            @DaoParam(name="startDate", type=java.util.Date.class,  inputDesc="MM/dd/yyyy hh:mm")Long startDate,
            @DaoParam(name="endDate", type=java.util.Date.class, inputDesc="MM/dd/yyyy hh:mm")Long endDate)
    {
        return null;
    }

    
    @Override
    @MethodDescription(description="Returns a list where each record contains a driver, a vehicle and scores.  The vehicle will be included if the driver is mapped to a vehicle.", crudType=CrudType.READ,  
    					modelClass=com.inthinc.pro.model.DVQMap.class)  
    public List<Map<String, Object>> getDVScoresByGT(@DaoParam(name="groupID", validator=ValidatorType.GROUP)Integer groupID, 
            @DaoParam(name="aggregation duration", type=com.inthinc.pro.backing.dao.ui.AggregationDurationList.class)Integer duration)
    {
        return null;
    }

    @Override
    @MethodDescription(description="Returns list of driver scores for all drivers in the specified group and time frame specified by start/end date.", crudType=CrudType.READ,
    					modelClass=com.inthinc.pro.model.aggregation.Score.class)  
    public Map<String, Object> getGDScoreByGSE(@DaoParam(name="groupID", validator=ValidatorType.GROUP)Integer groupID, 
            @DaoParam(name="startDate", type=java.util.Date.class, inputDesc="MM/dd/yyyy hh:mm")Long startDate,
            @DaoParam(name="endDate", type=java.util.Date.class, inputDesc="MM/dd/yyyy hh:mm")Long endDate
            )
    {
        return null;
    }

    @Override
    @MethodDescription(description="Returns the list of driver scores for the specified group and duration.", crudType=CrudType.READ,
    					modelClass=com.inthinc.pro.model.DriveQMap.class)  
    public Map<String, Object> getGDScoreByGT(@DaoParam(name="groupID", validator=ValidatorType.GROUP)Integer groupID, 
            @DaoParam(name="duration", type=com.inthinc.pro.backing.dao.ui.DurationCodeList.class)Integer duration)
    {
        return null;
    }

    @Override
    @MethodDescription(description="Returns scores for the specified group and duration. The scores represent the weighted average scores for all drivers in the group and its subgroups.", crudType=CrudType.READ,
    					modelClass=com.inthinc.pro.model.DriveQMap.class)  
    public List<Map<String, Object>> getGDTrendByGTC(@DaoParam(name="groupID", validator=ValidatorType.GROUP)Integer groupID, 
    		@DaoParam(name="aggregation type", type=com.inthinc.pro.backing.dao.ui.DurationBinSizeList.class)Integer duration,  
            @DaoParam(name="count", type=com.inthinc.pro.backing.dao.ui.DurationCountList.class)Integer count)
    {
        return null;
    }
/*
    @Override
    @MethodDescription(description="Returns list of driver scores for all vehicles in the specified group and time frame specified by start/end date.", crudType=CrudType.READ,
    					modelClass=com.inthinc.pro.model.DriveQMap.class)  
    public Map<String, Object> getGVScoreByGSE(@DaoParam(name="groupID", validator=ValidatorType.GROUP)Integer groupID, 
            @DaoParam(name="startDate", type=java.util.Date.class, inputDesc="MM/dd/yyyy hh:mm")Long startDate,
            @DaoParam(name="endDate", type=java.util.Date.class, inputDesc="MM/dd/yyyy hh:mm")Long endDate
            )
    {
        return null;
    }

*/
    @Override
    @MethodDescription(description="Returns a list of scores for the specified groupID, duration. Scores returned for each subgroup are the weighted average of all drivers in the subgroup and its subordinate subgroups.", crudType=CrudType.READ,
    				modelClass=com.inthinc.pro.model.aggregation.GroupScoreWrapper.class)
    public List<Map<String, Object>> getSDScoresByGT(@DaoParam(name="groupID", validator=ValidatorType.GROUP)Integer groupID, 
    		@DaoParam(name="duration", type=com.inthinc.pro.backing.dao.ui.DurationCodeList.class)Integer duration)
    {
        return null;
    }

    @Override
    @MethodDescription(description="Returns a list of scores for subgroups of the specified group.  Trend scores returned for each subgroup are the weighted average of all the drivers in the subgroup and its subordinate subgroups.", crudType=CrudType.READ,
    				modelClass=com.inthinc.pro.model.aggregation.GroupTrendWrapper.class)
    public List<Map<String, Object>> getSDTrendsByGTC(@DaoParam(name="groupID", validator=ValidatorType.GROUP)Integer groupID, 
            @DaoParam(name="aggregation type", type=com.inthinc.pro.backing.dao.ui.DurationBinSizeList.class)Integer duration,  
            @DaoParam(name="count", type=com.inthinc.pro.backing.dao.ui.DurationCountList.class)Integer count)
    {
        return null;
    }

    @Override
    @MethodDescription(description="Returns a list where each record contains a vehicle, a driver and scores. The driver will be included if the vehicle is currently mapped to a driver.", crudType=CrudType.READ,
    					modelClass=com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper.class)
    public List<Map<String, Object>> getVDScoresByGT(@DaoParam(name="groupID", validator=ValidatorType.GROUP)Integer groupID, 
            @DaoParam(name="aggregation duration", type=com.inthinc.pro.backing.dao.ui.AggregationDurationList.class)Integer duration)
    {
        return null;
    }

    @Override
    @MethodDescription(description="Returns a list where each record contains a driver, an optional vehicle and scores.  The vehicle will be included if the driver is mapped to a vehicle.", crudType=CrudType.READ, 
    					modelClass=com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper.class)  
    public List<Map<String, Object>> getVDScoresByGSE(@DaoParam(name="groupID", validator=ValidatorType.GROUP)Integer groupID,             
            @DaoParam(name="startDate", type=java.util.Date.class,  inputDesc="MM/dd/yyyy hh:mm")Long startDate,
            @DaoParam(name="endDate", type=java.util.Date.class, inputDesc="MM/dd/yyyy hh:mm")Long endDate)
    {
        return null;
    }

    @Override
    @MethodDescription(description="Returns the vehicle's scores for the specified duration.", crudType=CrudType.READ,
			modelClass=com.inthinc.pro.model.DriveQMap.class)
    public Map<String, Object> getVScoreByVT(@DaoParam(name="vehicleID", validator=ValidatorType.VEHICLE)Integer vehicleID, 
            @DaoParam(name="duration", type=com.inthinc.pro.backing.dao.ui.DurationDvqCodeList.class)Integer duration)
    {
        return null;
    }


    @Override
    @MethodDescription(description="Returns a list the vehicle scores for the specified aggregation type", crudType=CrudType.READ,
    		modelClass=com.inthinc.pro.model.DriveQMap.class)
    public List<Map<String, Object>> getVTrendByVTC(@DaoParam(name="vehicleID", validator=ValidatorType.VEHICLE)Integer vehicleID, 
            @DaoParam(name="aggregation type", type=com.inthinc.pro.backing.dao.ui.DurationBinSizeList.class)Integer duration,  
            @DaoParam(name="count", type=com.inthinc.pro.backing.dao.ui.DurationCountList.class)Integer count)
    {
        return null;
    }

}
