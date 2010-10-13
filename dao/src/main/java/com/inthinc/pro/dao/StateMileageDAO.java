package com.inthinc.pro.dao;

import java.util.List;
import org.joda.time.Interval;


import com.inthinc.pro.model.StateMileage;

public interface StateMileageDAO
{
    List<StateMileage> getStateMileageByGroupAndMonth(Integer groupID, Interval interval, Boolean dotOnly);
    List<StateMileage> getStateMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly);
    List<StateMileage> getStateMileageByVehicleRoad(Integer groupID, Interval interval, Boolean dotOnly);
    List<StateMileage> getFuelStateMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly);
    List<StateMileage> getMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly);
}
