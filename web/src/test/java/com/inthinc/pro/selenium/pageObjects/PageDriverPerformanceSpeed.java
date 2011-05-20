package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceSpeedEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;


public class PageDriverPerformanceSpeed extends NavigationBar {
	
	public DriverSpeedButtons _button(){
		return new DriverSpeedButtons();
	}
	
	public class DriverSpeedButtons{}
	
	public DriverSpeedLinks _link(){
		return new DriverSpeedLinks();
	}
	
	public class DriverSpeedLinks{
		
		public TextLink timeFrame(TimeDuration duration){
			return new TextLink(DriverPerformanceSpeedEnum.OVERALL_TIME_FRAME_SELECTOR, duration);
		}
		
		public TextTableLink exclude(){
			return new TextTableLink(DriverPerformanceSpeedEnum.EXCLUDE);
		}
		public TextTableLink include(){
			return new TextTableLink(DriverPerformanceSpeedEnum.INCLUDE);
		}
		
	}

	public DriverSpeedTexts _text(){
		return new DriverSpeedTexts();
	}
	
	public class DriverSpeedTexts{}

	public DriverSpeedTextFields _textFields(){
		return new DriverSpeedTextFields();
	}
	
	public class DriverSpeedTextFields{}

	public DriverSpeedDropDowns _dropDown(){
		return new DriverSpeedDropDowns();
	}
	
	public class DriverSpeedDropDowns{}

	

}
