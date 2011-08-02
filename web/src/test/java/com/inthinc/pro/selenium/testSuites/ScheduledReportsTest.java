package com.inthinc.pro.selenium.testSuites;

import com.inthinc.pro.selenium.util.GmailUtilities;

public class ScheduledReportsTest {
	
	public void email(){
		GmailUtilities gmail = new GmailUtilities("inc.qahudson", "1ppaasswwoorrdd");
		System.out.println(gmail.isSubjectFound("Midnight GMT Test"));
	}
	
	
	
	
	public static void main(String[] args){
		ScheduledReportsTest test = new ScheduledReportsTest();
		test.email();
	}

}
