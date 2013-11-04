package com.inthinc.pro.selenium.pageEnums;

import java.util.Calendar;

import org.apache.commons.lang.StringEscapeUtils;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;


public enum MastheadEnum implements SeleniumEnums {
    
    /* Paging elements */
    FORWARD_ONE(null,Xpath.start().td(Id.text(StringEscapeUtils.unescapeHtml("&#187;"))).toString()),
    FORWARD_ALL(null,Xpath.start().td(Id.text(StringEscapeUtils.unescapeHtml("&#187;&#187;"))).toString()),
    BACK_ONE(null,Xpath.start().td(Id.text(StringEscapeUtils.unescapeHtml("&#171;"))).toString()),
    BACK_ALL(null,Xpath.start().td(Id.text(StringEscapeUtils.unescapeHtml("&#171;&#171;"))).toString()),
    CHOOSE_PAGE(null,Xpath.start().td(Id.text("###")).toString()),

    /* Login Logo */
    LOGO_IFRAME(null, "//a[@class='logo']"),

    /* Header Elements */
    FLEET_IFRAME("Fleet", "//a[@id='headerInitDashboard']"),
    FORMS_IFRAME("Forms", "//a[@href='https://qa.inthinc.com/forms']"),
    MY_MESSAGES_IFRAME("My Messages", "//a[@id='headerMyMessages']"),
    MY_ACCOUNT_IFRAME("My Account", "//a[@id='headerMyAccount']"),
    LOGOUT_IFRAME("Log Out", "//a[@id='template-settings-logout']"),

    /* Footer Elements */
    COPYRIGHT("&#169;" + String.valueOf(Calendar.getInstance().get(Calendar.YEAR)) + " inthinc", "//li[@class='first']", "//form[@id='footerForm']/ul/li[1]", "//div[2]/div/span"),
    LEGAL("Legal Notice", "footerForm:legal", "//form[@id='footerForm']/ul/li[5]/a", "link=Legal Notice", "//a[@href='https://s3.amazonaws.com/resources.inthinc.com/html/inthincCustomerTermsOfService.html']"),
    PRIVACY("Privacy Policy", "footerForm:privacy", "//form[@id='footerForm']/ul/li[3]/a", "//div/div[2]/div[1]/span[3]/a", "//a[@href='https://s3.amazonaws.com/resources.inthinc.com/html/inthincPrivacyPolicy.html']"),
    SUPPORT("Support", "footerForm:customerSupport", "//form[@id='footerForm']/ul/li[7]", "//a[@href='https://s3.amazonaws.com/resources.inthinc.com/html/inthincCustomerSupport.html']"),
    VERSION(null, "footerForm:version", "//form[@id='footerForm']/ul/li[2]", "//li[@class='last']"),

    /* HTML Pages */
    LEGAL_NOTICE("THE MATERIAL AND INFORMATION ON THIS WEBSITE ARE BEING PROVIDED FOR YOUR INFORMATION ONLY, "
            + "\"AS IS, WHERE IS,\" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION ANY WARRANTY FOR INFORMATION, "
            + "SERVICES OR PRODUCTS PROVIDED BY OR THROUGH THIS WEBSITE. INTHINC EXPRESSLY DISCLAIMS ANY IMPLIED WARRANTY FOR MERCHANTABILITY, "
            + "SATISFACTORY QUALITY, FITNESS FOR A PARTICULAR PURPOSE, EXPECTATION OF PRIVACY OR NON-INFRINGEMENT.  " + "YOU MAY USE THIS WEBSITE AT YOUR OWN RISK. EXCEPT AS REQUIRED BY LAW, "
            + "INTHINC WILL NOT BE RESPONSIBLE OR LIABLE FOR ANY DAMAGE OR INJURY CAUSED BY, AMONG OTHER THINGS, ANY FAILURE OF PERFORMANCE; "
            + "ERROR, OMISSION, INTERRUPTION, DELETION, DEFECT OR DELAY IN OPERATION OR TRANSMISSION; COMPUTER VIRUS; COMMUNICATION LINE FAILURE; "
            + "THEFT, DESTRUCTION OR UNAUTHORIZED ACCESS TO, ALTERATION OF OR USE OF ANY DATA. "
            + "YOU, NOT INTHINC, ASSUME THE ENTIRE COST OF ALL NECESSARY SERVICING, REPAIR OR CORRECTION DUE TO YOUR USE OF THIS WEBSITE.  "
            + "INTHINC WILL NOT BE LIABLE FOR THE ACTIONS OF THIRD PARTIES.", "//p[8]"),

    PRIVACY_POLICY("We at inthinc take your privacy very seriously. " + "This Privacy Policy describes how we handle personally identifiable information "
            + "(&#8220;Personal Information&#8221;) and other information that we collect or receive " + "through the operation of inthinc products and services, any websites, portals, "
            + "telecommunications, technical or customer service support or information and as part " + "of any of our other business activities.  &#8220;Personal Information&#8221; "
            + "in this context is information that is identifiable to a particular person, including " + "when the information is combined with other information about that individual.  "
            + "We endeavor to carefully guard and protect the privacy of any " + "Personal Information that we collect or otherwise receive.", "//p[4]"),

    CUSTOMER_SUPPORT_DEFAULT("For assistance, please contact Customer Support at:", "//h3"),
    
    ;

    private String text, url;
    private String[] IDs;
    
    private MastheadEnum(String url){
    	this.url = url;
    }
    private MastheadEnum(String text, String ...IDs){
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
}
