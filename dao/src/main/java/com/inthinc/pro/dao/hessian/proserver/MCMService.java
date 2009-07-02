package com.inthinc.pro.dao.hessian.proserver;

import java.util.List;
import java.util.Map;



public interface MCMService extends HessianService
{
	List<Map> note(String mcmID, List<byte[]> noteList);
}
