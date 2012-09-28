package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumValueEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;

public class TAE {
	public static enum ScoreSelectEnum implements TextEnum{

	    CLEAR(""),
	    _0_1("0.0 - 1.0"),
	    _1_2("1.1 - 2.0"),
	    _2_3("2.1 - 3.0"),
	    _3_4("3.1 - 4.0"),
	    _4_5("4.1 - 5.0"),
	    
	    ;
	    
	    private String text;
	    private ScoreSelectEnum(String text){
	    	this.text = text;
	    }
	    
	    public String getText(){
	    	return text;
	    }
	}
	
	public static enum Locale implements TextEnum{
		ENGLISH("English (United States)"),
		ROMANIAN("rom&acirc;n&#259;"),
		GERMAN("Deutsch"),
		FRENCH("fran&ccedil;ais"),
		SPANISH("espa&ntilde;ol"),
		JAPANESE("&#26085;&#26412;&#35486;"),
		;

	    private String text;

	    private Locale(String text) {
	        this.text = text;
	    }

	    public String getText() {
	        return text;
	    }
		
	}
	
	public static enum Measurement implements TextEnum{
		ENGLISH("English"),
		METRIC("Metric"),
		
		;
		private String text;

	    private Measurement(String text) {
	        this.text = text;
	    }

	    public String getText() {
	        return text;
	    }
	}
	
	public static enum Fuel_Ratio implements TextEnum{
		ENGLISH_MILES_US("Miles Per Gallon (US)"),
		ENGLISH_MILES_UK("Miles Per Gallon (UK)"),
		METRIC_KILO_PER_LITER("Kilometers Per Liter"),
		METRIC_LITER_PER_KILO("Liters Per 100 Kilometers"),
		
		;
		private String text;

	    private Fuel_Ratio(String text) {
	        this.text = text;
	    }

	    public String getText() {
	        return text;
	    }
	}
	
	public static enum TimeDuration implements TextEnum {
		THIRTY_DAYS("Days"),
	    THREE_MONTHS("ThreeMonths"),
	    SIX_MONTHS("SixMonths"),
	    TWELVE_MONTHS("TwelveMonths"), 
	    ;

	    private String duration;

	    private TimeDuration(String duration) {
	        this.duration = duration;
	    }

	    public String getText() {
	        return duration;
	    }
	}
		
	
	
	public static enum DurationEnumeration implements TextEnum {
	    THIRTY_DAYS("durationPanelHeaderDays"),
	    THREE_MONTHS("durationPanelHeaderThreeMonths"),
	    SIX_MONTHS("durationPanelHeaderSixMonths"),
	    TWELVE_MONTHS("durationPanelHeaderTwelveMonths"), 
	    ;

	    private String duration;

	    private DurationEnumeration(String duration) {
	        this.duration = duration;
	    }

	    public String getText() {
	        return duration;
	    }
	}

	public static enum RedFlagPrefs implements SeleniumValueEnums, TextEnum{
	    EMAIL1(1, MyAccountEnum.EMAIL1_TEXTFIELD),
	    EMAIL2(2, MyAccountEnum.EMAIL2_TEXTFIELD),
	    PHONE1(3, MyAccountEnum.PHONE1_TEXTFIELD),
	    PHONE2(4, MyAccountEnum.PHONE2_TEXTFIELD),
	    TEXT1(5, MyAccountEnum.TEXT_MESSAGES1_TEXTFIELD),
	    TEXT2(6, MyAccountEnum.TEXT_MESSAGES2_TEXTFIELD);
	
	    private String text;
	    private MyAccountEnum ID;
	    private Integer position;
	
	    private RedFlagPrefs(Integer position, MyAccountEnum ID) {
	        this.position = position;
            this.ID = ID;
            this.text = ID.getText();
	    }
	    
	    @Override
	    public MyAccountEnum getID() {
	        return ID;
	    }
 
	    @Override
	    public String getText(){
	    	return text;
	    }
	    
	    @Override
	    public Integer getPosition() {
	        return position;
	    }
	}
	
	
	
	public static enum DOT implements TextEnum{
		None("None"),
		US_8_Day("US 8 Day"),
		US_Oil_8_Day("US Oil 8 Day"),
		Canada("Canada"),
		Canada_60_Degrees("Canada 60 Degrees"),
		US_Home_Office("US Home Office"),
		Canada_Home_Office("Canada Home Office"),
		Texas("Texas"),
		Canada_Alberta("Canada Alberta"),
		Canada_2007_Cycle_1("Canada 2007 Cycle 1"),
		Canada_2007_Cycle_2("Canada 2007 Cycle 2"),
		Canada_2007_60_Degrees_Cycle_1("Canada 2007 60 Degrees Cycle 1"),
		Canada_2007_60_Degrees_Cycle_2("Canada 2007 60 Degrees Cycle 2"),
		Canada_2007_Oil_Field_Permit("Canada 2007 Oil Field Permit"),
		Canada_2007_60_Degrees_Oil_Field_Permit("Canada 2007 60 Degrees Oil Field Permit"),
		US_7_Day("US 7 Day"),
		US_Oil_7_Day("US Oil 7 Day"),
		
		;
		
		private String text;
		private DOT(String text){
			this.text = text;
		}

		public String getText(){
			return text;
		}
		
	}
}
