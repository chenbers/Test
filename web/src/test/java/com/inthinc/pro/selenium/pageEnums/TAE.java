package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumValueEnums;
import com.inthinc.pro.automation.enums.TextEnum;

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
	
	public enum Measurement implements TextEnum{
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
	
	public enum Fuel_Ratio implements TextEnum{
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
		
	
	
	public enum DurationEnumeration implements TextEnum {
	    DAYS_30("durationPanelHeaderDays"),
	    MONTHS_3("durationPanelHeaderThreeMonths"),
	    MONTHS_6("durationPanelHeaderSixMonths"),
	    MONTHS_12("durationPanelHeaderTwelveMonths"), 
	    ;

	    private String duration;

	    private DurationEnumeration(String duration) {
	        this.duration = duration;
	    }

	    public String getText() {
	        return duration;
	    }
	}

	public static enum RedFlagPrefs implements SeleniumValueEnums, TextEnum {
	    EMAIL1(1, MyAccountEnum.EMAIL1_TEXTFIELD, MyAccountEnum.EMAIL1_TITLE),
	    EMAIL2(2, MyAccountEnum.EMAIL2_TEXTFIELD, MyAccountEnum.EMAIL2_TITLE),
	    PHONE1(3, MyAccountEnum.PHONE1_TEXTFIELD, MyAccountEnum.PHONE1_TITLE),
	    PHONE2(4, MyAccountEnum.PHONE2_TEXTFIELD, MyAccountEnum.PHONE2_TITLE),
	    TEXT1(5, MyAccountEnum.TEXT_MESSAGES1_TEXTFIELD, MyAccountEnum.TEXT_MESSAGES1_TITLE),
	    TEXT2(6, MyAccountEnum.TEXT_MESSAGES2_TEXTFIELD, MyAccountEnum.TEXT_MESSAGES2_TITLE);
	
	    private String value, text;
	    private MyAccountEnum ID, prefix;
	    private Integer position;
	
	    private RedFlagPrefs(Integer position, MyAccountEnum ID, MyAccountEnum prefix) {
	        this.value = position.toString();
	        this.position = position;
            this.ID = ID;
            this.prefix = prefix;
            this.text = prefix.getText();
	    }
	    public MyAccountEnum getID() {
	        return ID;
	    }
	    
	    public String getText(){
	    	return text;
	    }
	
	    public MyAccountEnum getPrefix() {
	        return prefix;
	    }
	
	    public String getValue() {
	        return value;
	    }
	    
	    public Integer getPosition() {
	        return position;
	    }
	}
}
