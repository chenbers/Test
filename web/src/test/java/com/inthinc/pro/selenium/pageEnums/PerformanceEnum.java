package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum PerformanceEnum implements SeleniumEnums {

    /* Crash History Summary */
    CRASH_TITLE("Crash History Summary", Xpath.start().span(Id.clazz("crash")).toString()),
    CRASHES_PER_TEXT("Crashes per million miles", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("1").toString()),
    CRASHES_PER_NUMBER(null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("1").toString()),
    CRASHES_PER_TIME_FRAME("(Last 12 months)", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("3").td("1").toString()),
    TOTAL_CRASHES_TEXT("Total Crashes:", Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("1").td("2").toString()),
    TOTAL_CRASHES_NUMBER(null, Xpath.start().table(Id.id("crashSummaryTable")).tbody().tr("2").td("2").toString()),

    /* Overall Score */
    OVERALL_TIME_FRAME(null, "dateLinksForm:*page*Performance***"),
    
    OVERALL_TITLE("Overall Score", Xpath.start().span(Id.clazz("overall")).toString()),
    OVERALL_SCORE_LABEL("Overall Score", Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("1").h4().toString()),
    OVERALL_SCORE_NUMBER(null, Xpath.start().div(Id.id("overallScoreBox_body")).table().tbody().tr().td("2").table().tbody().tr().td().toString()),

    OVERALL_TOOLS(null, "dateLinksForm:toolsIcon"),
    OVERALL_EMAIL(emailReport, "dateLinksForm:emailMenuItem:anchor"),
    OVERALL_PDF(exportPDF, "dateLinksForm:export_menu_item:anchor"),

    /* Speed */
    SPEED_TITLE("Speed", Xpath.start().span(Id.clazz("speed")).toString()),
    SPEED_TIME_FRAME(null, "speedForm:*page*PerformanceSpeed-dateLinksToolsDetail***"),
    SPEED_SCORE_BOX(null, Xpath.start().div(Id.id("speedScoreBox_body")).table().tbody().tr().td().toString()),
    SPEED_DETAILS(null, "speedForm:*page*PerformanceSpeed-dateLinksToolsDetailShow"),

    /* Driving Style */
    STYLE_TITLE("Driving Style", Xpath.start().span(Id.clazz("style")).toString()),
    STYLE_TIME_FRAME(null, "styleForm:*page*PerformanceStyle-dateLinksToolsDetail***"),
    STYLE_SCORE_BOX(null, Xpath.start().div(Id.id("styleScoreBox_body")).table().tbody().tr().td().toString()),
    STYLE_DETAILS(null, "styleForm:*page*PerformanceStyle-dateLinksToolsDetailShow"),

    /* SeatBelt */
    SEATBELT_TITLE("Seat Belt", Xpath.start().span(Id.clazz("seatbelt")).toString()),
    SEATBELT_TIME_FRAME(null, "seatBeltForm:*page*PerformanceSeatBelt-dateLinksToolsDetail***"),
    SEATBELT_SCORE_BOX(null, Xpath.start().div(Id.id("seatBeltScoreBox_body")).table().tbody().tr().td().toString()),
    SEATBELT_DETAILS(null, "seatBeltForm:*page*PerformanceSeatBelt-dateLinksToolsDetailShow"),

    /* Fuel Efficiency */
    MPG_TITLE("Fuel Efficiency", Xpath.start().span(Id.clazz("gas")).toString()),
    MPG_TIME_FRAME(null, "mpgForm:dateLinks***"),

    /* Fuel Efficiency */
    COACHING_TITLE("Coaching Events", Xpath.start().span(Id.clazz("coaching")).toString()),
    COACHING_TIME_FRAME(null, "coachingForm:dateLinks***"),

    

    /* Details */
    DETAILS_TITLE("Details", Xpath.start().span(Id.clazz("details")).toString()),
    DETAILS_X_OF_Y("Showing XXX to YYY of ZZZ records", "headerEvents"),

	
	;

    private String text, url;
    private String[] IDs;
    
    private PerformanceEnum(String url){
    	this.url = url;
    }
    private PerformanceEnum(String text, String ...IDs){
        this.text=text;
    	this.IDs = IDs;
    }

    @Override
    public String[] getIDs() {
        return IDs;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getURL() {
        return url;
    }
    
    public PerformanceEnum replacePage(String page){
    	String[] newone = new String[IDs.length];
    	for(int i=0;i<IDs.length;i++){
    		newone[i] = IDs[i].replace("*page*", page);
    	}
    	this.IDs = newone;
    	newone = null;
    	return this;
    }
}
