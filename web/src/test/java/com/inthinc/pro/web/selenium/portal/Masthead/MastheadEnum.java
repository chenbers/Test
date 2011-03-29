package com.inthinc.pro.web.selenium.portal.Masthead;

import java.util.Calendar;

import org.apache.commons.lang.StringEscapeUtils;

import com.inthinc.pro.web.selenium.SeleniumEnums;

public enum MastheadEnum implements SeleniumEnums {
    URL("/logout", "/login", null, null), //TODO: jwimmer: question for DTanner: I like the ideaof having ALL the page constants for each page in an enum.  how do you feel about keeping url/path info here?  
	/* Login Logo */
	LOGIN(null,"login_logo","//body/div[1]/div/img",null),
	LOGO(null,"headerForm:headerInitDashboard","//form[@id='headerForm']/div[@id='logo']/a/img",null),
	
	/* Header Elements */
	HELP("Help", "headerForm:contextSensitiveHelp", "//a[@class='tb-help']","//div[@id='horz_nav']/ul/li[1]/span/a"),
	LOGOUT("Log Out", null, "//div[@id='horz_nav']/ul/li[4]/a", "//a[@class='tb-logout']", "link=Log Out"),
	MY_ACCOUNT("My Account", "headerForm:headerMyAccount", "//div[@id='horz_nav']/ul/li[3]/span/a", "//a[@href='/tiwipro/app/account']"),
	MY_MESSAGES("My Messages", "headerForm:headerMyMessages", "//form[@id='headerForm']/ul/li[2]/span/a", "//a[@href='/tiwipro/app/messages/']"),
	
	
	/* Footer Elements */
	COPYRIGHT(StringEscapeUtils.unescapeHtml("&#169;" + String.valueOf(Calendar.getInstance().get(Calendar.YEAR)) + " inthinc"),null,"//li[@class='first']","//form[@id='footerForm']/ul/li[1]"),
	LEGAL("Legal Notice", "footerForm:legal", "//form[@id='footerForm']/ul/li[5]/a", "link=Legal Notice" ),
	PRIVACY("Privacy Policy", "footerForm:privacy", "//form[@id='footerForm']/ul/li[3]/a", null ),
	SUPPORT("Support", "footerForm:customerSupport", "//form[@id='footerForm']/ul/li[7]", null),
	VERSION(null, "footerForm:version", "//form[@id='footerForm']/ul/li[2]", "//li[@class='last']"),
	
	
	/* HTML Pages */
	LEGAL_NOTICE("THE MATERIAL AND INFORMATION ON THIS WEBSITE ARE BEING PROVIDED FOR YOUR INFORMATION ONLY, " +
			"\"AS IS, WHERE IS,\" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION ANY WARRANTY FOR INFORMATION, " +
			"SERVICES OR PRODUCTS PROVIDED BY OR THROUGH THIS WEBSITE. INTHINC EXPRESSLY DISCLAIMS ANY IMPLIED WARRANTY FOR MERCHANTABILITY, " +
			"SATISFACTORY QUALITY, FITNESS FOR A PARTICULAR PURPOSE, EXPECTATION OF PRIVACY OR NON-INFRINGEMENT.  " +
			"YOU MAY USE THIS WEBSITE AT YOUR OWN RISK. EXCEPT AS REQUIRED BY LAW, " +
			"INTHINC WILL NOT BE RESPONSIBLE OR LIABLE FOR ANY DAMAGE OR INJURY CAUSED BY, AMONG OTHER THINGS, ANY FAILURE OF PERFORMANCE; " +
			"ERROR, OMISSION, INTERRUPTION, DELETION, DEFECT OR DELAY IN OPERATION OR TRANSMISSION; COMPUTER VIRUS; COMMUNICATION LINE FAILURE; " +
			"THEFT, DESTRUCTION OR UNAUTHORIZED ACCESS TO, ALTERATION OF OR USE OF ANY DATA. " +
			"YOU, NOT INTHINC, ASSUME THE ENTIRE COST OF ALL NECESSARY SERVICING, REPAIR OR CORRECTION DUE TO YOUR USE OF THIS WEBSITE.  " +
			"INTHINC WILL NOT BE LIABLE FOR THE ACTIONS OF THIRD PARTIES.",null,"//p[8]",null),
			
	PRIVACY_POLICY(StringEscapeUtils.unescapeHtml("We at inthinc take your privacy very seriously. " +
			"This Privacy Policy describes how we handle personally identifiable information " +
			"(&#8220;Personal Information&#8221;) and other information that we collect or receive " +
			"through the operation of inthinc products and services, any websites, portals, " +
			"telecommunications, technical or customer service support or information and as part " +
			"of any of our other business activities.  &#8220;Personal Information&#8221; " +
			"in this context is information that is identifiable to a particular person, including " +
			"when the information is combined with other information about that individual.  " +
			"We endeavor to carefully guard and protect the privacy of any " +
			"Personal Information that we collect or otherwise receive."), null, "//p[4]", null)
	;

	
private String text, ID, xpath, xpath_alt, link=null;
	
	private MastheadEnum( String text, String ID, String xpath, String xpath_alt) {
		this.text=text;
		this.ID=ID;
		this.xpath=xpath;
		this.xpath_alt=xpath_alt;
	}
	
	private MastheadEnum( String text, String ID, String xpath, String xpath_alt, String link) {
		this(text, ID, xpath, xpath_alt);
		this.link = link;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text=text;
	}

	public String getID() {
		return ID;
	}

	public String getXpath() {
		return xpath;
	}

	public String getXpath_alt() {
		return xpath_alt;
	}
	
	public String getLink() {
		return link;
	}
}
