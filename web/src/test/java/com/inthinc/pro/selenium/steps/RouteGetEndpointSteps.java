package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.models.AutomationUser;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicles;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageFormsManage;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsDiagnostics;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsSafety;
import com.inthinc.pro.selenium.pageObjects.PageRouteGetEndpoint;

public class RouteGetEndpointSteps extends WebSteps {
	
    PageRouteGetEndpoint page = new PageRouteGetEndpoint();
    String IMEI = "915029181528300";
    String externActionID = "TEST_ORTEC_ROUTE";
    String IMEI2 = "";//TODO: add imei for this later
    String externActionID2 = "";//TODO: add actionID for this later
    String IMEI3 = "";//TODO: add imei for this later
    String externActionID3 = "";//TODO: add actionID for this later
    String IMEI4 = "";//TODO: add imei for this later
    String externActionID4 = "";//TODO: add actionID for this later
    String IMEI5 = "";//TODO: add imei for this later
    String externActionID5 = "";//TODO: add actionID for this later
    String IMEI6 = "";//TODO: add imei for this later
    String externActionID6 = "";//TODO: add actionID for this later
    String IMEI7 = "";//TODO: add imei for this later
    String externActionID7 = "";//TODO: add actionID for this later
    String IMEI8 = "";//TODO: add imei for this later
    String externActionID8 = "";//TODO: add actionID for this later
    String IMEI9 = "";//TODO: add imei for this later
    String externActionID9 = "";//TODO: add actionID for this later
    String IMEI10 = "";//TODO: add imei for this later
    String externActionID10 = "";//TODO: add actionID for this later
    String IMEI11 = "";//TODO: add imei for this later
    String externActionID11 = "";//TODO: add actionID for this later
    String IMEI12 = "";//TODO: add imei for this later
    String externActionID12 = "";//TODO: add actionID for this later
    String IMEI13 = "";//TODO: add imei for this later
    String externActionID13 = "";//TODO: add actionID for this later

    @Given("I retrieve a standard route")
    public void givenIGoToTheRouteGetEndpointPage() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("{\"routeID\":\"TEST_ORTEC_ROUTE\",\"actions\":[{\"actionID\":\"ACTION_1_1\",\"type\":\"couple\",\"lat\":38.5482,\"lng\":-95.8008,\"station\":null,\"productCode\":null,\"productKind\":null,\"amounts\":[],\"stopID\":\"STOP_1\",\"status\":\"NEW\",\"formID\":0,\"customer\":\"CUSTOMER_1\",\"customerID\":\"CUSTOMER_1\",\"address\":\"123 Test Street, Test City, UT 84222\"},{\"actionID\":\"ACTION_2_1\",\"type\":\"pickup\",\"lat\":39.5482,\"lng\":-94.8008,\"station\":{\"code\":\"TANK\",\"kind\":\"TANK\",\"capacities\":[{\"value\":10000.0,\"units\":\"GAL\"},{\"value\":5000.0,\"units\":\"LBS\"}]},\"productCode\":\"LINI\",\"productKind\":\"NITROGEN\",\"amounts\":[{\"value\":1000.0,\"units\":\"LBS\"},{\"value\":2000.0,\"units\":\"GAL\"}],\"stopID\":\"STOP_2\",\"status\":\"NEW\",\"formID\":0,\"customer\":\"CUSTOMER_2\",\"customerID\":\"CUSTOMER_2\",\"address\":\"123 Test Street, Test City, UT 84222\"},{\"actionID\":\"ACTION_2_2\",\"type\":\"pickup\",\"lat\":39.5482,\"lng\":-94.8008,\"station\":{\"code\":\"TANK\",\"kind\":\"TANK\",\"capacities\":[{\"value\":10000.0,\"units\":\"GAL\"},{\"value\":5000.0,\"units\":\"LBS\"}]},\"productCode\":\"LINI\",\"productKind\":\"NITROGEN\",\"amounts\":[{\"value\":1000.0,\"units\":\"LBS\"},{\"value\":2000.0,\"units\":\"GAL\"}],\"stopID\":\"STOP_2\",\"status\":\"NEW\",\"formID\":0,\"customer\":\"CUSTOMER_2\",\"customerID\":\"CUSTOMER_2\",\"address\":\"123 Test Street, Test City, UT 84222\"},{\"actionID\":\"ACTION_3_1\",\"type\":\"deliver\",\"lat\":40.5482,\"lng\":-93.8008,\"station\":{\"code\":\"TANK\",\"kind\":\"TANK\",\"capacities\":[{\"value\":10000.0,\"units\":\"GAL\"},{\"value\":5000.0,\"units\":\"LBS\"}]},\"productCode\":\"LINI\",\"productKind\":\"NITROGEN\",\"amounts\":[{\"value\":1000.0,\"units\":\"LBS\"},{\"value\":2000.0,\"units\":\"GAL\"}],\"stopID\":\"STOP_3\",\"status\":\"NEW\",\"formID\":0,\"customer\":\"CUSTOMER_3\",\"customerID\":\"CUSTOMER_3\",\"address\":\"123 Test Street, Test City, UT 84222\"},{\"actionID\":\"ACTION_3_2\",\"type\":\"deliver\",\"lat\":40.5482,\"lng\":-93.8008,\"station\":{\"code\":\"TANK\",\"kind\":\"TANK\",\"capacities\":[{\"value\":10000.0,\"units\":\"GAL\"},{\"value\":5000.0,\"units\":\"LBS\"}]},\"productCode\":\"LINI\",\"productKind\":\"NITROGEN\",\"amounts\":[{\"value\":1000.0,\"units\":\"LBS\"},{\"value\":2000.0,\"units\":\"GAL\"}],\"stopID\":\"STOP_3\",\"status\":\"NEW\",\"formID\":0,\"customer\":\"CUSTOMER_3\",\"customerID\":\"CUSTOMER_3\",\"address\":\"123 Test Street, Test City, UT 84222\"},{\"actionID\":\"ACTION_4_1\",\"type\":\"decouple\",\"lat\":41.5482,\"lng\":-92.8008,\"station\":null,\"productCode\":null,\"productKind\":null,\"amounts\":[],\"stopID\":\"STOP_4\",\"status\":\"NEW\",\"formID\":0,\"customer\":\"CUSTOMER_4\",\"customerID\":\"CUSTOMER_4\",\"address\":\"123 Test Street, Test City, UT 84222\"},{\"actionID\":\"ACTION_5_1\",\"type\":\"NEW_ACTION_TYPE\",\"lat\":42.5482,\"lng\":-91.8008,\"station\":{\"code\":\"NEW_STATION_CODE\",\"kind\":\"NEW_STATION_KIND\",\"capacities\":[{\"value\":10000.0,\"units\":\"NEW_UNIT2\"}]},\"productCode\":\"NEW_PROD_CODE\",\"productKind\":\"NEW_PROD_KIND\",\"amounts\":[{\"value\":1000.0,\"units\":\"NEW_UNIT\"}],\"stopID\":\"STOP_5\",\"status\":\"NEW\",\"formID\":0,\"customer\":\"CUSTOMER_5\",\"customerID\":\"CUSTOMER_5\",\"address\":\"123 Test Street, Test City, UT 84222\"}]}");
    	matched(result, original);
    }
      
    @Given("I retrieve a route with two deliveries and a load activity")
    public void givenIRetrieveARouteWithTwoDeliveriesAndALoadActivity() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI2 + "/" + externActionID2);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it    	
    	matched(result, original);
    }
    
    @Given("I retrieve a route with two deliveries, a load, and a preload activity")
    public void givenIRetrieveARouteWithTwoDeliveriesALoadAndAPreloadActivity() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI3 + "/" + externActionID3);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }
    
    @Given("I retrieve a route with two deliveries and a preload activity")
    public void givenIRetrieveARouteWithTwoDeliveriesAndAPreloadActivity() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI4 + "/" + externActionID4);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }
    
    @Given("I provide an invalid IMEI")
    public void givenIProvideAnInvalidIMEI() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + "INVALIDIMEI" + "/" + "externActionID");  //for dev
    	
    }
    
    @Given("I provide an invalid RouteID")
    public void givenIProvideAnInvalidRouteID() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + "INVALID_ORTEC_ROUTE");  //for dev
    	
    }
    
    public void matched(boolean result, String original) {
    	
    	if(result == true) {
    		System.out.println("Matched");
    	}
    	else {
    		System.out.println("ERROR: " + result + "Did not match:\n" + original);
    	}
    }

}