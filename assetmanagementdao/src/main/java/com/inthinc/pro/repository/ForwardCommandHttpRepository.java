package com.inthinc.pro.repository;

import java.util.List;

import com.inthinc.pro.domain.ForwardCommandHttp;

public interface ForwardCommandHttpRepository  extends GenericRepository<ForwardCommandHttp, Integer>{

	public List<ForwardCommandHttp> getForwardCommandsByVehicleID(Integer vehicleID);


}
