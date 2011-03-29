package com.inthinc.QA.enums;

import com.inthinc.QA.window.Calculator;
import com.inthinc.QA.window.CreateUser;
import com.inthinc.QA.window.DeviceWindow;
import com.inthinc.QA.window.JustAccounts;
import com.inthinc.QA.window.Windows;

public enum Menus {
	DEVICEM(new DeviceWindow()),
	SCORESM(new Calculator()),
	USERM(new CreateUser()), 
	ACCOUNTS(new JustAccounts());
//	VEHICLEM,
//	PERSONM,
//	GETIDM;
	
	private Windows window;
	
	private Menus(Windows constructor){
		window=constructor;
	}
	
	public Windows getWindow(){
		return window;
	}

}
