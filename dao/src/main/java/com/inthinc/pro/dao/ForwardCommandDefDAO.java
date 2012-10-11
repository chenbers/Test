package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.ForwardCommandDef;


public interface ForwardCommandDefDAO  extends GenericDAO<ForwardCommandDef, Integer> {

	List<ForwardCommandDef> getFwdCmdDefs();
	
	void create(ForwardCommandDef forwardCommandDef);
	
}
