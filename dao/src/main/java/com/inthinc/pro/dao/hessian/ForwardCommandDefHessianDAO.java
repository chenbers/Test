package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.ForwardCommandDefDAO;
import com.inthinc.pro.model.ForwardCommandDef;
import com.inthinc.pro.model.ForwardCommandParamType;

public class ForwardCommandDefHessianDAO  extends GenericHessianDAO<ForwardCommandDef, Integer> implements ForwardCommandDefDAO {

	private static final long serialVersionUID = 5833991114828131455L;

	@Override
	public List<ForwardCommandDef> getFwdCmdDefs() {
		// TODO do real impl when backend is ready
		List<ForwardCommandDef> list = new ArrayList<ForwardCommandDef> ();
		list.add(new ForwardCommandDef(11, "Send Power Cycle", "Reboot the unit", ForwardCommandParamType.NONE, Boolean.TRUE));
		list.add(new ForwardCommandDef(365, "Download new Firmware", "Download new firmware. Parameter: firmware version", ForwardCommandParamType.INTEGER, Boolean.TRUE));
		list.add(new ForwardCommandDef(666, "Bogus", "Bogus string", ForwardCommandParamType.STRING, Boolean.TRUE));
		
		return list;
	}

}
