package com.inthinc.pro.dao.hessian.proserver;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.ProDAOException;



public interface MCMService extends HessianService
{
	List<Map> note(String mcmID, List<byte[]> noteList);
	List<Map> crash(String mcmID, List<byte[]> noteList) throws ProDAOException;
}
