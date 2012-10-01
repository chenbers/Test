package com.inthinc.pro.selenium.steps;

import org.jbehave.core.annotations.When;

import com.inthinc.pro.selenium.pageObjects.PageUtil;

public class UtilSteps extends WebSteps {

    PageUtil utilPage = new PageUtil();
    
    @When("I create one thousand devices")
    public void whenICreateOneThousandDevices() {
    	int i = 674;
    	int imei = 674;
		utilPage._dropDown().method().select("createDevice");
		utilPage._textField().id().type("17913");
		utilPage._button().populate().click();
    	
    	while (i < 1001) {

		utilPage._textField().accountid().type("7956");
		utilPage._textField().deviceid().type(i);
		utilPage._textField().acctid().type("7956");
		utilPage._textField().name().type("FAKEIMEIDEVICE" + i);
		utilPage._textField().imei().type("000000000000" + imei);
		utilPage._textField().sim().type("FAKEIMEIDEVICE" + i);
		utilPage._textField().serialnum().type("SERIAL" + i);
		utilPage._button().run().click();
    	i++;
    	imei++;
    	}
    }

}