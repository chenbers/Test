package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.TableText;
import com.inthinc.pro.automation.elements.TableTextLink;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextField;
import com.inthinc.pro.selenium.pageEnums.IdlingReportEnum;
import com.inthinc.pro.selenium.pageEnums.PopUpEnum;
import com.inthinc.pro.selenium.pageEnums.ReportsBarEnum;

public class PageIdlingReport extends ReportsBar {
	private String page = "idling";
	
	public class IdlingReportTexts extends ReportsBarTexts{
		public TableText idleSupportValue(){
			return new TableText(IdlingReportEnum.IDLE_SUPPORT_VALUE);
		}
		public TableText durationValue(){
			return new TableText(IdlingReportEnum.DURATION_VALUE);
		}
		public TableText lowIdleHoursValue(){
			return new TableText(IdlingReportEnum.LOW_IDLE_VALUE);
		}
		public TableText highIdleHoursValue(){
			return new TableText(IdlingReportEnum.HIGH_IDLE_VALUE);
		}
		public TableText totalIdleHoursValue(){
			return new TableText(IdlingReportEnum.TOTAL_IDLE_VALUE);
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
		
	    public TableTextLink groupValue() {
	        return new TableTextLink(IdlingReportEnum.GROUP_VALUE);   
	    }  
	    
	    public TableTextLink driverValue() {
	    	return new TableTextLink(IdlingReportEnum.DRIVER_VALUE); 
	    } 
	    
	    public TableTextLink tripsValue() {
	    	return new TableTextLink(IdlingReportEnum.TRIPS_LINK); 
	    }
	    
	}   
	
	public class IdlingReportPopUps extends PopUps{
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
}
