package com.inthinc.pro.dao.hessian.proserver;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.ProDAOException;



public interface MCMService extends HessianService
{
	List<Map> note(String mcmID, List<byte[]> noteList);
	Integer crash(String mcmID, List<byte[]> crashDataList) throws ProDAOException;

	List<Map> notews(String imei, Integer type, List<byte[]> noteList);
}
