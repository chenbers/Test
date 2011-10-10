package com.inthinc.vehicleSettingTests;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.domain.ForwardCommandHttp;
import com.inthinc.pro.service.ForwardCommandHttpService;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ForwardCommandTest {
    @Autowired
	private ForwardCommandHttpService forwardCommandService;
	
    @Test
    public void forwardCommandCountTest(){
    	Number count = forwardCommandService.getForwardCommandCount();
    	System.out.println(count.toString());
    }
    
    @Test
    @Ignore
    public void getForwardCommandTest(){
    	ForwardCommandHttp forwardCommand = forwardCommandService.getForwardCommandByID(11462);
    	System.out.println(forwardCommand.toString());
    	
    }
    @Test
    @Ignore
    public void getForwardCommandsByVehicleID(){
    	List<ForwardCommandHttp> forwardCommands = forwardCommandService.getForwardCommandsByVehicleID(2);
    	System.out.println("forward commands for vehicle 2 are ");
    	for(ForwardCommandHttp forwardCommand : forwardCommands){
    		System.out.println(forwardCommand.toString());
    	}
    }
}
