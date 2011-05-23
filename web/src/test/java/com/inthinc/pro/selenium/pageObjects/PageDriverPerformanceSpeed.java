package com.inthinc.pro.selenium.pageObjects;

import org.apache.tools.ant.taskdefs.email.EmailAddress;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.DriverPerformanceSpeedEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;


public class PageDriverPerformanceSpeed extends NavigationBar {
	private String page = "speedForm";
	
	public DriverSpeedButtons _button(){
		return new DriverSpeedButtons();
	}
	
	public class DriverSpeedButtons{
		public Button tools(){
			return new Button(DriverPerformanceSpeedEnum.OVERALL_TOOLS);
		}
		
		public Button restore(){
			return new Button(DriverPerformanceSpeedEnum.RETURN);
		}
		
		public Button pdf(){
			return new Button(DriverPerformanceSpeedEnum.OVERALL_PDF_TOOL);
		}
		
		public Button exportToExcel(){
			return new Button(DriverPerformanceSpeedEnum.OVERALL_EXCEL_TOOL);
		}
		
		public Button emailReport(){
			return new Button(DriverPerformanceSpeedEnum.OVERALL_EMAIL_TOOL);
		}
	}
	
	public DriverSpeedLinks _link(){
		return new DriverSpeedLinks();
	}
	
	public class DriverSpeedLinks{
		
		public TextLink overall(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_OVERALL_LINK);
		}
		
		public TextLink _1_30(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_1_30_LINK);
		}
		
		public TextLink _31_40(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_31_40_LINK);
		}
		
		public TextLink _41_54(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_41_54_LINK);
		}
		
		
		public TextLink _55_64(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_55_64_LINK);
		}
		
		public TextLink _65_80(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_65_80_LINK);
		}
		
		public TextTableLink location(){
			return new TextTableLink(DriverPerformanceSpeedEnum.LOCATION_ENTRY);
		}
		
		public TextLink timeFrame(TimeDuration duration){
			return new TextLink(DriverPerformanceSpeedEnum.TIME_FRAME_SELECTOR, duration);
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
	
	public class DriverSpeedTexts{
		
		public TextLink overall(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_OVERALL_SCORE);
		}
		
		public TextLink _1_30(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_1_30_SCORE);
		}
		
		public TextLink _31_40(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_31_40_SCORE);
		}
		
		public TextLink _41_54(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_41_54_SCORE);
		}
		
		
		public TextLink _55_64(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_55_64_SCORE);
		}
		
		public TextLink _65_80(){
			return new TextLink(DriverPerformanceSpeedEnum.BREAKDOWN_65_80_SCORE);
		}
	}

	public DriverSpeedTextFields _textFields(){
		return new DriverSpeedTextFields();
	}
	
	public class DriverSpeedTextFields{}

	public DriverSpeedDropDowns _dropDown(){
		return new DriverSpeedDropDowns();
	}
	
	public class DriverSpeedDropDowns{}

	
	public class DriverSpeedPopUp extends PopUps{
		public DriverSpeedPopUp(){
			super(page, Types.SINGLE, 3);
		}
		public Email emailReport(){
			return new Email();
		}
	}
	
	public DriverSpeedPopUp _popUp(){
		return new DriverSpeedPopUp();
	}

}
