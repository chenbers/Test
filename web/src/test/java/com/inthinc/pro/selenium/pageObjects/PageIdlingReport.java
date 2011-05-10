package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TableTextLink;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.selenium.pageEnums.IdlingReportEnum;

public class PageIdlingReport extends ReportsBar {
	
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
	    	return new TextField(IdlingReportEnum.TEAM_SEARCH);        
	    }    
	    
	}
	
	public class IdlingReportButtons extends ReportsBarButtons{
		
	    public TextButton refresh() {
	        return new TextButton(IdlingReportEnum.REFRESH);
	    }
	}
    
	public class IdlingReportLinks extends ReportsBarLinks{
		
	    public TableTextLink teamValue() {
	        return new TableTextLink(IdlingReportEnum.TEAM);   
	    }  
	    
	    public TableTextLink driverValue() {
	    	return new TableTextLink(IdlingReportEnum.DRIVER); 
	    } 
	    
	    public TableTextLink tripsValue() {
	    	return new TableTextLink(IdlingReportEnum.TRIPS); 
	    }
	    
	}    
}
