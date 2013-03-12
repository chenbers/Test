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
    String IMEI2 = "";//TODO: add imei for this later
    String IMEI3 = "";//TODO: add imei for this later
    String externActionID = "1001";
    String externActionID2 = "1002";
    String externActionID3 = "1003";
    String externActionID4 = "1004";
    String externActionID5 = "1005";
    String externActionID6 = "1006";
    String externActionID7 = "1007";
    String externActionID8 = "1008";
    String externActionID9 = "1009";
    String externActionID10 = "1010";
    String externActionID11 = "1011";
    String externActionID12 = "1012";
    String externActionID13 = "1013";
    String externActionID14 = "1014";


    @Given("I retrieve a standard route")
    public void givenIGoToTheRouteGetEndpointPage() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("{\"routeID\":\"1001\",\"actions\":[{\"actionID\":\"ACTION_1_1\",\"type\":\"couple\",\"lat\":38.5482,\"lng\":-95.8008,\"station\":null,\"productCode\":null,\"productKind\":null,\"amounts\":[],\"stopID\":\"STOP_1\",\"status\":\"NEW\",\"formID\":0,\"customer\":\"CUSTOMER_1\",\"customerID\":\"CUSTOMER_1\",\"address\":\"123 Test Street, Test City, UT 84222\"},{\"actionID\":\"ACTION_2_1\",\"type\":\"pickup\",\"lat\":39.5482,\"lng\":-94.8008,\"station\":{\"code\":\"TANK\",\"kind\":\"TANK\",\"capacities\":[{\"value\":10000.0,\"units\":\"GAL\"},{\"value\":5000.0,\"units\":\"LBS\"}]},\"productCode\":\"LINI\",\"productKind\":\"NITROGEN\",\"amounts\":[{\"value\":1000.0,\"units\":\"LBS\"},{\"value\":2000.0,\"units\":\"GAL\"}],\"stopID\":\"STOP_2\",\"status\":\"NEW\",\"formID\":0,\"customer\":\"CUSTOMER_2\",\"customerID\":\"CUSTOMER_2\",\"address\":\"123 Test Street, Test City, UT 84222\"},{\"actionID\":\"ACTION_2_2\",\"type\":\"pickup\",\"lat\":39.5482,\"lng\":-94.8008,\"station\":{\"code\":\"TANK\",\"kind\":\"TANK\",\"capacities\":[{\"value\":10000.0,\"units\":\"GAL\"},{\"value\":5000.0,\"units\":\"LBS\"}]},\"productCode\":\"LINI\",\"productKind\":\"NITROGEN\",\"amounts\":[{\"value\":1000.0,\"units\":\"LBS\"},{\"value\":2000.0,\"units\":\"GAL\"}],\"stopID\":\"STOP_2\",\"status\":\"NEW\",\"formID\":0,\"customer\":\"CUSTOMER_2\",\"customerID\":\"CUSTOMER_2\",\"address\":\"123 Test Street, Test City, UT 84222\"},{\"actionID\":\"ACTION_3_1\",\"type\":\"deliver\",\"lat\":40.5482,\"lng\":-93.8008,\"station\":{\"code\":\"TANK\",\"kind\":\"TANK\",\"capacities\":[{\"value\":10000.0,\"units\":\"GAL\"},{\"value\":5000.0,\"units\":\"LBS\"}]},\"productCode\":\"LINI\",\"productKind\":\"NITROGEN\",\"amounts\":[{\"value\":1000.0,\"units\":\"LBS\"},{\"value\":2000.0,\"units\":\"GAL\"}],\"stopID\":\"STOP_3\",\"status\":\"NEW\",\"formID\":0,\"customer\":\"CUSTOMER_3\",\"customerID\":\"CUSTOMER_3\",\"address\":\"123 Test Street, Test City, UT 84222\"},{\"actionID\":\"ACTION_3_2\",\"type\":\"deliver\",\"lat\":40.5482,\"lng\":-93.8008,\"station\":{\"code\":\"TANK\",\"kind\":\"TANK\",\"capacities\":[{\"value\":10000.0,\"units\":\"GAL\"},{\"value\":5000.0,\"units\":\"LBS\"}]},\"productCode\":\"LINI\",\"productKind\":\"NITROGEN\",\"amounts\":[{\"value\":1000.0,\"units\":\"LBS\"},{\"value\":2000.0,\"units\":\"GAL\"}],\"stopID\":\"STOP_3\",\"status\":\"NEW\",\"formID\":0,\"customer\":\"CUSTOMER_3\",\"customerID\":\"CUSTOMER_3\",\"address\":\"123 Test Street, Test City, UT 84222\"},{\"actionID\":\"ACTION_4_1\",\"type\":\"decouple\",\"lat\":41.5482,\"lng\":-92.8008,\"station\":null,\"productCode\":null,\"productKind\":null,\"amounts\":[],\"stopID\":\"STOP_4\",\"status\":\"NEW\",\"formID\":0,\"customer\":\"CUSTOMER_4\",\"customerID\":\"CUSTOMER_4\",\"address\":\"123 Test Street, Test City, UT 84222\"},{\"actionID\":\"ACTION_5_1\",\"type\":\"NEW_ACTION_TYPE\",\"lat\":42.5482,\"lng\":-91.8008,\"station\":{\"code\":\"NEW_STATION_CODE\",\"kind\":\"NEW_STATION_KIND\",\"capacities\":[{\"value\":10000.0,\"units\":\"NEW_UNIT2\"}]},\"productCode\":\"NEW_PROD_CODE\",\"productKind\":\"NEW_PROD_KIND\",\"amounts\":[{\"value\":1000.0,\"units\":\"NEW_UNIT\"}],\"stopID\":\"STOP_5\",\"status\":\"NEW\",\"formID\":0,\"customer\":\"CUSTOMER_5\",\"customerID\":\"CUSTOMER_5\",\"address\":\"123 Test Street, Test City, UT 84222\"}]}");
    	matched(result, original);
    }
      
    @Given("I retrieve a route with two deliveries and a load activity")
    public void givenIRetrieveARouteWithTwoDeliveriesAndALoadActivity() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID2);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it    	
    	matched(result, original);
    }
    
    @Given("I retrieve a route with two deliveries, a load, and a preload activity")
    public void givenIRetrieveARouteWithTwoDeliveriesALoadAndAPreloadActivity() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID3);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }
    
    @Given("I retrieve a route with two deliveries and a preload activity")
    public void givenIRetrieveARouteWithTwoDeliveriesAndAPreloadActivity() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID4);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }
    
    @Given("I retrieve a route with two deliveries")
    public void givenIRetrieveARouteWithTwoDeliveries() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID5);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }
    
    @Given("I retrieve a route with two deliveries and a preload activity, then cancel the preload activity")
    public void givenIRetrieveARouteWithTwoDeliveriesAndAPreloadActivityThenCancelThePreloadActivity() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID6);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    	//TODO: Add code to cancel a activity (or do we just replace it with the new route data?  The replace route is how I have it set up for now).
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + "1006_1");  //for dev
    	original = page._text().response().toString();
    	result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }
        
    @Given("I retrieve a route with two deliveries and a load activity and then later add a preload activity")
    public void givenIRetrieveARouteWithTwoDeliveriesAndALoadActivityAndThenLaterAddAPreloadActivity() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID7);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    	//TODO: Add code to add a preload activity
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID7);  //for dev
    	original = page._text().response().toString();
    	result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }
    
    @Given("I retrieve a route with three deliveries, then cancel the second delivery activity and add a new delivery into the third delivery slot")
    public void givenIRetrieveARouteWithThreeDeliveriesThenCancelTheSecondDeliveryActivityAndAddANewDeliveryIntoTheThirdDeliverySlot() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID8);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    	//TODO: Add code to cancel the second delivery activity
    	//TODO: Add code to add a new delivery activity into the third delivery slot
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID8);  //for dev
    	original = page._text().response().toString();
    	result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }    
    
    @Given("I retrieve a route with three deliveries, then cancel all delivery activities and add a new delivery")
    public void givenIRetrieveARouteWithThreeDeliveriesThenCancelAllDeliveryActivitiesAndAddANewDelivery() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID9);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    	//TODO: Add code to cancel all the delivery activities
    	//TODO: Add code to add one new delivery activity
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID9);  //for dev
    	original = page._text().response().toString();
    	result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }    
    
    @Given("I retrieve a route with three deliveries and the driver delivers them out of order")
    public void givenIRetrieveARouteWithThreeDeliveriesAndTheDriverDeliversThemOutOfOrder() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID10);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    	//TODO: Add code to mark all 3 deliveries as complete but out of order
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID10);  //for dev
    	original = page._text().response().toString();
    	result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }
    
    @Given("I retrieve a route with three deliveries and then the driver is sent to a dump site")
    public void givenIRetrieveARouteWithThreeDeliveriesAndThenIsSentToADumpSite() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID11);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    	//TODO: Add code to mark all deliveries as complete and then send the driver to a dump site
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID11);  //for dev
    	original = page._text().response().toString();
    	result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }
    
    @Given("I retrieve a route and then partially complete the route and move vehicles")
    public void givenIRetrieveARouteAndThenPartiallyCompleteTheRouteAndMoveVehicles() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI2 + "/" + externActionID12);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    	//TODO: Add code to mark one delivery complete and then move the driver to a new vehicle and retrieve the route
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI2 + "/" + externActionID12);  //for dev
    	original = page._text().response().toString();
    	result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }
    
    @Given("I retrieve a route and then partially complete the route and a different driver retrieves the route from the same vehicle")
    public void givenIRetrieveARouteAndThenPartiallyCompleteTheRouteAndDifferentDriverRetrievesTheRouteFromTheSameVehicle() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID13);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    	//TODO: Add code to mark one delivery complete and then a different driver retrieves the route from the same vehicle
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID13);  //for dev
    	original = page._text().response().toString();
    	result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }
    
    @Given("I retrieve a route and then partially complete the route and a different driver retrieves the route from a different vehicle")
    public void givenIRetrieveARouteAndThenPartiallyCompleteTheRouteAndDifferentDriverRetrievesTheRouteFromADifferentVehicle() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI3 + "/" + externActionID14);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    	//TODO: Add code to mark one delivery complete and then a different driver retrieves the route from a different vehicle
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI3 + "/" + externActionID14);  //for dev
    	original = page._text().response().toString();
    	result = original.equals("");//TODO: Add string once I know it
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