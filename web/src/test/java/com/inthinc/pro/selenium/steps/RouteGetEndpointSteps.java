package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.Given;

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
    	boolean result = original.equals("{\"routeID\":1001,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"couple\",\"lat\":38.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":null,\"productKind\":null,\"instructions\":null,\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362790724},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"pickup\",\"lat\":38.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"LINI\",\"productKind\":\"NITROGEN\",\"instructions\":null,\"replacementValues\":{\"LBS.value\":\"1000.0\",\"GAL.capacity\":\"10000.0\",\"GAL.value\":\"2000.0\",\"LBS.capacity\":\"5000.0\"},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362793724},{\"stopID\":22,\"customerName\":\"CUSTOMER_2\",\"type\":\"pickup\",\"lat\":38.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"LINI\",\"productKind\":\"NITROGEN\",\"instructions\":null,\"replacementValues\":{\"LBS.value\":\"1000.0\",\"GAL.capacity\":\"10000.0\",\"GAL.value\":\"2000.0\",\"LBS.capacity\":\"5000.0\"},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362794024,\"plannedDeparture\":1362794324},{\"stopID\":31,\"customerName\":\"CUSTOMER_3\",\"type\":\"deliver\",\"lat\":38.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"LINI\",\"productKind\":\"NITROGEN\",\"instructions\":null,\"replacementValues\":{\"LBS.value\":\"1000.0\",\"GAL.capacity\":\"10000.0\",\"GAL.value\":\"2000.0\",\"LBS.capacity\":\"5000.0\"},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362797024,\"plannedDeparture\":1362797504},{\"stopID\":32,\"customerName\":\"CUSTOMER_3\",\"type\":\"deliver\",\"lat\":38.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"LINI\",\"productKind\":\"NITROGEN\",\"instructions\":null,\"replacementValues\":{\"LBS.value\":\"1000.0\",\"GAL.capacity\":\"10000.0\",\"GAL.value\":\"2000.0\",\"LBS.capacity\":\"5000.0\"},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362797504,\"plannedDeparture\":1362797924},{\"stopID\":41,\"customerName\":\"CUSTOMER_4\",\"type\":\"decouple\",\"lat\":38.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":null,\"productKind\":null,\"instructions\":null,\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362800624,\"plannedDeparture\":1362801524},{\"stopID\":51,\"customerName\":\"CUSTOMER_5\",\"type\":\"NEW_ACTION_TYPE\",\"lat\":38.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"NEW_PROD_CODE\",\"productKind\":\"NEW_PROD_KIND\",\"instructions\":null,\"replacementValues\":{\"NEW_UNIT2.capacity\":\"10000.0\",\"NEW_UNIT.value\":\"1000.0\"},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362804224,\"plannedDeparture\":1362805124}]}");
    	matched(result, original);
    }
      
    @Given("I retrieve a route with two deliveries and a load activity")
    public void givenIRetrieveARouteWithTwoDeliveriesAndALoadActivity() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID2);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("{\"routeID\":1002,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"pickup\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Load product from this area\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer 1, they have an entrance in the back\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224},{\"stopID\":31,\"customerName\":\"CUSTOMER_3\",\"type\":\"deliver\",\"lat\":38.5482,\"lng\":-97.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_3\",\"productKind\":\"PRODUCT_KIND_3\",\"instructions\":\"Deliver to customer 2, they have a silo on the side\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362797024,\"plannedDeparture\":1362798824}]}");    	
    	matched(result, original);
    }
    
    @Given("I retrieve a route with two deliveries, a load, and a preload activity")
    public void givenIRetrieveARouteWithTwoDeliveriesALoadAndAPreloadActivity() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID3);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("{\"routeID\":1003,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"pickup\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Load product from this area\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer 1, they have an entrance in the back\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224},{\"stopID\":31,\"customerName\":\"CUSTOMER_3\",\"type\":\"deliver\",\"lat\":38.5482,\"lng\":-97.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_3\",\"productKind\":\"PRODUCT_KIND_3\",\"instructions\":\"Deliver to customer 2, they have a silo on the side\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362797024,\"plannedDeparture\":1362798824}]}");
    	matched(result, original);
    }
    
    @Given("I retrieve a route with two deliveries and a preload activity")
    public void givenIRetrieveARouteWithTwoDeliveriesAndAPreloadActivity() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID4);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("{\"routeID\":1004,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"deliver\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Deliver to customer 1, they have an entrance in the back\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer 2, they have a silo on the side\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224},{\"stopID\":31,\"customerName\":\"CUSTOMER_3\",\"type\":\"pickup\",\"lat\":38.5482,\"lng\":-97.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_3\",\"productKind\":\"PRODUCT_KIND_3\",\"instructions\":\"Preload for tomorrows activities\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362797024,\"plannedDeparture\":1362798824}]}");
    	matched(result, original);
    }
    
    @Given("I retrieve a route with two deliveries")
    public void givenIRetrieveARouteWithTwoDeliveries() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID5);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("{\"routeID\":1005,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"deliver\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Deliver to customer 1, they have an entrance in the back\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer 2, they have a silo on the side\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224}]}");
    	matched(result, original);
    }
    
    @Given("I retrieve a route with two deliveries and a preload activity, then cancel the preload activity")
    public void givenIRetrieveARouteWithTwoDeliveriesAndAPreloadActivityThenCancelThePreloadActivity() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID6);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("{\"routeID\":1006,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"deliver\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Deliver to customer 1, they have an entrance in the back\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer 2, they have a silo on the side\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224},{\"stopID\":31,\"customerName\":\"CUSTOMER_3\",\"type\":\"pickup\",\"lat\":38.5482,\"lng\":-97.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_3\",\"productKind\":\"PRODUCT_KIND_3\",\"instructions\":\"Preload for tomorrows activities\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362797024,\"plannedDeparture\":1362798824}]}");
    	matched(result, original);
    	//TODO: Add code to cancel a activity (or do we just replace it with the new route data?  The replace route is how I have it set up for now).
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID6 + "1");  //for dev
    	original = page._text().response().toString();
    	result = original.equals("{\"routeID\":10061,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"deliver\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Deliver to customer 1, they have an entrance in the back\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer 2, they have a silo on the side\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224}]}");
    	matched(result, original);
    }
        
    @Given("I retrieve a route with two deliveries and a load activity and then later add a preload activity")
    public void givenIRetrieveARouteWithTwoDeliveriesAndALoadActivityAndThenLaterAddAPreloadActivity() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID7);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("{\"routeID\":1007,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"pickup\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Load product from this area\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer 1, they have an entrance in the back\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224},{\"stopID\":31,\"customerName\":\"CUSTOMER_3\",\"type\":\"deliver\",\"lat\":38.5482,\"lng\":-97.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_3\",\"productKind\":\"PRODUCT_KIND_3\",\"instructions\":\"Deliver to customer 2, they have a silo on the side\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362797024,\"plannedDeparture\":1362798824}]}");
    	matched(result, original);
    	//TODO: Add code to add a preload activity
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID7 + "1");  //for dev
    	original = page._text().response().toString();
    	result = original.equals("{\"routeID\":10071,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"pickup\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Load product from this area\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer 1, they have an entrance in the back\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224},{\"stopID\":31,\"customerName\":\"CUSTOMER_3\",\"type\":\"deliver\",\"lat\":38.5482,\"lng\":-97.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_3\",\"productKind\":\"PRODUCT_KIND_3\",\"instructions\":\"Deliver to customer 2, they have a silo on the side\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362797024,\"plannedDeparture\":1362798824}]}");
    	matched(result, original);
    }
    
    @Given("I retrieve a route with three deliveries, then cancel the second delivery activity and add a new delivery into the third delivery slot")
    public void givenIRetrieveARouteWithThreeDeliveriesThenCancelTheSecondDeliveryActivityAndAddANewDeliveryIntoTheThirdDeliverySlot() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID8);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("{\"routeID\":1008,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"deliver\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Deliver to customer A\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer B\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224},{\"stopID\":31,\"customerName\":\"CUSTOMER_3\",\"type\":\"deliver\",\"lat\":38.5482,\"lng\":-97.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_3\",\"productKind\":\"PRODUCT_KIND_3\",\"instructions\":\"Deliver to customer C\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362797024,\"plannedDeparture\":1362798824}]}");
    	matched(result, original);
    	//TODO: Add code to cancel the second delivery activity
    	//TODO: Add code to add a new delivery activity into the third delivery slot
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID8 + "1");  //for dev
    	original = page._text().response().toString();
    	result = original.equals("{\"routeID\":10081,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"deliver\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Deliver to customer A\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":38.5482,\"lng\":-97.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer C\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362797024,\"plannedDeparture\":1362798824},{\"stopID\":31,\"customerName\":\"CUSTOMER_3\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_3\",\"productKind\":\"PRODUCT_KIND_3\",\"instructions\":\"Deliver to customer D\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224}]}");//TODO: Add string once I know it
    	matched(result, original);
    }    
    
    @Given("I retrieve a route with three deliveries, then cancel all delivery activities and add a new delivery")
    public void givenIRetrieveARouteWithThreeDeliveriesThenCancelAllDeliveryActivitiesAndAddANewDelivery() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID9);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("{\"routeID\":1009,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"deliver\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Deliver to customer A\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer B\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224},{\"stopID\":31,\"customerName\":\"CUSTOMER_3\",\"type\":\"deliver\",\"lat\":38.5482,\"lng\":-97.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_3\",\"productKind\":\"PRODUCT_KIND_3\",\"instructions\":\"Deliver to customer C\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362797024,\"plannedDeparture\":1362798824}]}");
    	matched(result, original);
    	//TODO: Add code to cancel all the delivery activities
    	//TODO: Add code to add one new delivery activity
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID9 + "1");  //for dev
    	original = page._text().response().toString();
    	result = original.equals("{\"routeID\":10091,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"deliver\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Deliver to customer D\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624}]}");
    	matched(result, original);
    }    
    
    @Given("I retrieve a route with three deliveries and the driver delivers them out of order")
    public void givenIRetrieveARouteWithThreeDeliveriesAndTheDriverDeliversThemOutOfOrder() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID10);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("{\"routeID\":1010,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"deliver\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Deliver to customer A\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer B\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224},{\"stopID\":31,\"customerName\":\"CUSTOMER_3\",\"type\":\"deliver\",\"lat\":38.5482,\"lng\":-97.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_3\",\"productKind\":\"PRODUCT_KIND_3\",\"instructions\":\"Deliver to customer C\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362797024,\"plannedDeparture\":1362798824}]}");
    	matched(result, original);
    	//TODO: Add code to mark all 3 deliveries as complete but out of order
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID10 + "1");  //for dev
    	original = page._text().response().toString();
    	result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }
    
    @Given("I retrieve a route with three deliveries and then the driver is sent to a dump site")
    public void givenIRetrieveARouteWithThreeDeliveriesAndThenIsSentToADumpSite() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID11);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("{\"routeID\":1011,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"deliver\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Deliver to customer A\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer B\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224},{\"stopID\":31,\"customerName\":\"CUSTOMER_3\",\"type\":\"deliver\",\"lat\":38.5482,\"lng\":-97.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_3\",\"productKind\":\"PRODUCT_KIND_3\",\"instructions\":\"Deliver to customer C\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362797024,\"plannedDeparture\":1362798824}]}");
    	matched(result, original);
    	//TODO: Add code to mark all deliveries as complete and then send the driver to a dump site
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID11 + "1");  //for dev
    	original = page._text().response().toString();
    	result = original.equals("{\"routeID\":10111,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"deliver\",\"lat\":46.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Drop off remaining product at a dump site\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624}]}");
    	matched(result, original);
    }
    
    @Given("I retrieve a route and then partially complete the route and move vehicles")
    public void givenIRetrieveARouteAndThenPartiallyCompleteTheRouteAndMoveVehicles() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI2 + "/" + externActionID12);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("{\"routeID\":1012,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"deliver\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Deliver to customer A, then change vehicles\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer B\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224}]}");
    	matched(result, original);
    	//TODO: Add code to mark one delivery complete and then move the driver to a new vehicle and retrieve the route
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI2 + "/" + externActionID12 + "1");  //for dev
    	original = page._text().response().toString();
    	result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }
    
    @Given("I retrieve a route and then partially complete the route and a different driver retrieves the route from the same vehicle")
    public void givenIRetrieveARouteAndThenPartiallyCompleteTheRouteAndDifferentDriverRetrievesTheRouteFromTheSameVehicle() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID13);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("{\"routeID\":1013,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"deliver\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Deliver to customer A, then change vehicles\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer B\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224}]}");
    	matched(result, original);
    	//TODO: Add code to mark one delivery complete and then a different driver retrieves the route from the same vehicle
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + externActionID13 + "1");  //for dev
    	original = page._text().response().toString();
    	result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }
    
    @Given("I retrieve a route and then partially complete the route and a different driver retrieves the route from a different vehicle")
    public void givenIRetrieveARouteAndThenPartiallyCompleteTheRouteAndDifferentDriverRetrievesTheRouteFromADifferentVehicle() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI3 + "/" + externActionID14);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.equals("{\"routeID\":1014,\"stops\":[{\"stopID\":11,\"customerName\":\"CUSTOMER_1\",\"type\":\"deliver\",\"lat\":36.5482,\"lng\":-95.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_1\",\"productKind\":\"PRODUCT_KIND_1\",\"instructions\":\"Deliver to customer A, then change vehicles\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362789824,\"plannedDeparture\":1362791624},{\"stopID\":21,\"customerName\":\"CUSTOMER_2\",\"type\":\"deliver\",\"lat\":37.5482,\"lng\":-96.8008,\"address\":\"123 Test Street, Test City, UT 84222\",\"productCode\":\"PRODUCT_CODE_2\",\"productKind\":\"PRODUCT_KIND_2\",\"instructions\":\"Deliver to customer B\",\"replacementValues\":{},\"status\":\"NEW\",\"formID\":0,\"plannedArrival\":1362793424,\"plannedDeparture\":1362795224}]}");
    	matched(result, original);
    	//TODO: Add code to mark one delivery complete and then a different driver retrieves the route from a different vehicle
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI3 + "/" + externActionID14 + "1");  //for dev
    	original = page._text().response().toString();
    	result = original.equals("");//TODO: Add string once I know it
    	matched(result, original);
    }
    
    @Given("I provide an invalid IMEI")
    public void givenIProvideAnInvalidIMEI() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + "INVALIDIMEI" + "/" + externActionID);  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.contains("{\"status\":404,\"code\":100,\"message\":\"Device Not Found. Lookup failed for mcmID or IMEI: INVALIDIMEI");
    	matched(result, original);
    	
    }
    
    @Given("I provide an invalid RouteID")
    public void givenIProvideAnInvalidRouteID() {
    	page.open("http://dev.tiwipro.com:8080/forms_service/route/" + IMEI + "/" + "INVALID");  //for dev
    	String original = page._text().response().toString();
    	boolean result = original.contains("{\"status\":400,\"code\":500,\"message\":\"A route for RouteID: INVALID was not found.\",\"stackTrace\":");
    	matched(result, original);
    }
    
    public void matched(boolean result, String original) {
    	
    	if(result == true) {
    		System.out.println("Matched");
    	}
    	else {
    		System.out.println("ERROR: " + result + "\nDid not match:\n" + original);
    	}
    }

}