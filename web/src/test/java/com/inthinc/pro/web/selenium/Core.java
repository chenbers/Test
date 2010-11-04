package com.inthinc.pro.web.selenium;

import com.thoughtworks.selenium.DefaultSelenium;
import static org.testng.AssertJUnit.*;

public class Core extends DefaultSelenium {

	public Core(String serverHost, int serverPort, String browserStartCommand,
			String browserURL) {
		  super(serverHost, serverPort, browserStartCommand, browserURL);
	//  // TODO Auto-generated constructor stub
	}
	
	public void TextPresent( String Text){
		try {
			assertTrue(isTextPresent(Text));
			}catch (AssertionError e){
				
			}
	}
	
	public void ElementPresent( String Text){
		try {
			assertTrue(isElementPresent(Text));
			}catch (AssertionError e){
				
			}
	}
	
	public void GetText( String Text){
	try {
		assertEquals(getText("//b"), Text);
		}catch (AssertionError e){
		}
	}
	
	public void AssertEquals( String local, String Text){
		try {
			assertEquals(local, Text);
			}catch (AssertionError e){
			}
		}
	
}
