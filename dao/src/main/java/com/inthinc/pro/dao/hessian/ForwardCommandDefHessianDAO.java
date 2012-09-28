package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.ForwardCommandDefDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.ForwardCommandDef;

public class ForwardCommandDefHessianDAO  extends GenericHessianDAO<ForwardCommandDef, Integer> implements ForwardCommandDefDAO {

	private static final long serialVersionUID = 5833991114828131455L;

	@Override
	public List<ForwardCommandDef> getFwdCmdDefs() {
		try {
			List<ForwardCommandDef> list = getMapper().convertToModelObject(this.getSiloService().getFwdCmdDefs(), ForwardCommandDef.class);
			return list;
		}
		catch (EmptyResultSetException e)
		{
			return Collections.emptyList();
		}
	}

	@Override
	public void create(ForwardCommandDef forwardCommandDef) {
		getSiloService().createFwdCmdDef(getMapper().convertToMap(forwardCommandDef));
	}

	@Override
	public Integer update(ForwardCommandDef forwardCommandDef) {
		return getChangedCount(getSiloService().updateFwdCmdDef(getMapper().convertToMap(forwardCommandDef)));
	}
}
