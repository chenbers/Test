package com.inthinc.pro.selenium.pageObjects;


import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.RouteGetEndpointEnum;

/****************************************************************************************
 * Purpose: Testing the Get route webservice endpoint
 * 
 * @author mweiss Last Update: 3/06/13
 */

public class PageRouteGetEndpoint extends Masthead {
	public PageRouteGetEndpoint(){}
	
    public class RouteGetEndpointPopUps extends MastheadPopUps{}
    
    public class RouteGetEndpointButtons extends MastheadButtons{}
    
    public class RouteGetEndpointTextFields extends MastheadTextFields{}
    
    public class RouteGetEndpointLinks extends MastheadLinks{}
    
    public class RouteGetEndpointDropDowns extends MastheadDropDowns{}
    
    public RouteGetEndpointPopUps _popUp(){
    	return new RouteGetEndpointPopUps();
    }
    
    public class RouteGetEndpointTexts extends MastheadTexts{   	
    	public Text response() {
    		return new Text(RouteGetEndpointEnum.RESPONSE);
    	}
    }

    public RouteGetEndpointButtons _button(){
    	return new RouteGetEndpointButtons();
    }
      
    public RouteGetEndpointTexts _text(){
    	return new RouteGetEndpointTexts();
    }

    public RouteGetEndpointTextFields _textField(){
    	return new RouteGetEndpointTextFields();
    }

    public RouteGetEndpointLinks _link(){
    	return new RouteGetEndpointLinks();
    }

    public RouteGetEndpointDropDowns _dropDown(){
    	return new RouteGetEndpointDropDowns();
    }

    @Override
    public SeleniumEnums setUrl() {
        return RouteGetEndpointEnum.URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _text().response().isPresent();
    }
}
