package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.IdlingReportEnum;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;
import com.inthinc.pro.selenium.pageEnums.ReportsBarEnum;

// TODO: Page needs to be split into two, there is now a Idling Drivers and Idling Vehicles page

public class PageReportsIdling extends ReportsBar {
	private String page = "idling";
	
	public PageReportsIdling(){
		checkMe.add(IdlingReportEnum.IDLING_COUNTER);
		checkMe.add(IdlingReportEnum.START_DATE);
		checkMe.add(IdlingReportEnum.END_DATE);
	}
	
	public class IdlingReportTexts extends ReportsBarTexts{
		public TextTable idleSupportValue(){
			return new TextTable(IdlingReportEnum.IDLE_SUPPORT_VALUE);
		}
		public TextTable durationValue(){
			return new TextTable(IdlingReportEnum.DURATION_VALUE);
		}
		public TextTable lowIdleHoursValue(){
			return new TextTable(IdlingReportEnum.LOW_IDLE_VALUE);
		}
		public TextTable highIdleHoursValue(){
			return new TextTable(IdlingReportEnum.HIGH_IDLE_VALUE);
		}
		public TextTable totalIdleHoursValue(){
			return new TextTable(IdlingReportEnum.TOTAL_IDLE_VALUE);
		}
		
		public Text counter(){
			return new Text(ReportsBarEnum.COUNTER, page);
		}
		
		public Text idlingCounter(){
			return new Text(IdlingReportEnum.IDLING_COUNTER);
		}
		
	}
	public class IdlingReportDropDowns extends ReportsBarDropDowns{}
	
	public class IdlingReportTextFields extends ReportsBarTextFields{
    
	    public TextField startDate() {
	        return new TextField(IdlingReportEnum.START_DATE);
	    }
	    
	    public TextField endDate() {
	    	return new TextField(IdlingReportEnum.END_DATE);
	    }
	    
	    public TextField driverSearch() {
	    	return new TextField(IdlingReportEnum.DRIVER_SEARCH);        
	    }
	    
	    public TextField teamSearch() {
	    	return new TextField(IdlingReportEnum.GROUP_SEARCH);        
	    }    
	    
	}
	
	public class IdlingReportButtons extends ReportsBarButtons{
		
	    public TextButton refresh() {
	        return new TextButton(IdlingReportEnum.REFRESH);
	    }
	    
	    public TextButton editColumns() {
			return new TextButton(PopUpEnum.EDIT_COLUMNS, page);
		}

		public TextButton exportEmail() {
			return new TextButton(ReportsBarEnum.TOOL_EMAIL, page);
		}

		public TextButton exportExcel() {
			return new TextButton(ReportsBarEnum.TOOL_EXCEL, page);
		}

		public TextButton exportPDF() {
			return new TextButton(ReportsBarEnum.TOOL_PDF, page);
		}

		public Button tools() {
			return new Button(PopUpEnum.TOOL_BUTTON, page);
		}
	}
    
	public class IdlingReportLinks extends ReportsBarLinks{
		
	    public TextTableLink groupValue() {
	        return new TextTableLink(IdlingReportEnum.GROUP_VALUE);   
	    }  
	    
	    public TextTableLink driverValue() {
	    	return new TextTableLink(IdlingReportEnum.DRIVER_VALUE); 
	    } 
	    
	    public TextTableLink tripsValue() {
	    	return new TextTableLink(IdlingReportEnum.TRIPS_LINK); 
	    }
	    
	}   
	
	public class IdlingReportPopUps extends MastheadPopUps{
    	public IdlingReportPopUps(){
    		super(page, Types.REPORT, 3);
    	}
    	
    	public Email emailReport(){
    		return new Email();
    	}
    	
    	public EditColumns editColumns(){
    		return new EditColumns();
    	}
    }

	public IdlingReportPopUps _popUp() {
		return new IdlingReportPopUps();
	}
	
	public IdlingReportLinks _link() {
	    return new IdlingReportLinks();
	}
	
	public IdlingReportButtons _button(){
	    return new IdlingReportButtons();
	}
	
	
    @Override
    public SeleniumEnums setUrl() {
        return IdlingReportEnum.DEFAULT_URL;
    }
    @Override
    protected boolean checkIsOnPage() {
        return _button().editColumns().isPresent();
    }
}
