package com.inthinc.pro.repository;

import java.util.List;

import com.inthinc.pro.domain.Vehicle;

public interface VehicleRepository  extends
							GenericRepository<Vehicle, Integer>{

	List<Integer> getVehicleIDsByGroupIDDeep(Integer groupID);

}
