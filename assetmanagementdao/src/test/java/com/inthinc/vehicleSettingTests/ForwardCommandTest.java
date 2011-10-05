package com.inthinc.vehicleSettingTests;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.domain.ForwardCommand;
import com.inthinc.pro.service.ForwardCommandService;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ForwardCommandTest {
    @Autowired
	private ForwardCommandService forwardCommandService;
	
    @Test
    public void forwardCommandCountTest(){
    	Number count = forwardCommandService.getForwardCommandCount();
    	System.out.println(count.toString());
    }
    
    @Test
    public void getForwardCommandTest(){
    	ForwardCommand forwardCommand = forwardCommandService.getForwardCommandByID(11462);
    	System.out.println(forwardCommand.toString());
    	
    }
    @Test
    public void getForwardCommandsByVehicleID(){
    	List<ForwardCommand> forwardCommands = forwardCommandService.getForwardCommandsByVehicleID(2);
    	System.out.println("forward commands for vehicle 2 are ");
    	for(ForwardCommand forwardCommand : forwardCommands){
    		System.out.println(forwardCommand.toString());
    	}
    }
}
