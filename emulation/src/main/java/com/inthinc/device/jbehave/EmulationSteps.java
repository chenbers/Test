package com.inthinc.device.jbehave;

import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;

import com.inthinc.device.cassandras.AutomationCassandraDAO;
import com.inthinc.device.emulation.enums.UnitType;
import com.inthinc.device.emulation.notes.DeviceNote;
import com.inthinc.pro.automation.jbehave.AutoCustomSteps;
import com.inthinc.pro.automation.jbehave.AutoStepVariables;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.models.Driver;
import com.inthinc.pro.automation.models.Group;
import com.inthinc.pro.automation.models.Person;
import com.inthinc.pro.automation.models.Vehicle;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.rest.RestCommands;

public class EmulationSteps {
    
    public static void main(String[] args){
        AutoStepVariables.saveVariable(AutoCustomSteps.getUserVariable(), "0001");
        AutoStepVariables.saveVariable(AutoCustomSteps.getPasswordVariable(), "password");
        
        EmulationSteps step = new EmulationSteps();
        step.getCommunicatedVehicle("8");
        
        Log.info(AutoStepVariables.getValue(vehicleVariable));
        Log.info(AutoStepVariables.getValue(vehicleGroupVariable));
        Log.info(AutoStepVariables.getValue(vehicleDriverVariable));
    }
    
    private RestCommands rest;
    
    private static final String vehicleVariable = "my vehicle";
    private static final String vehicleGroupVariable = "my vehicles group";
    private static final String vehicleDriverVariable = "my vehicles driver";
    
    @Given("I am using a vehicle that has communicated in the last $timePeriod days")
    public void getCommunicatedVehicle(@Named("timePeriod")String timePeriod){
        String userName = AutoStepVariables.getValue(AutoCustomSteps.getUserVariable());
        String password = AutoStepVariables.getValue(AutoCustomSteps.getPasswordVariable());

        AutomationCassandraDAO dao = new AutomationCassandraDAO();
        
        AutomationCalendar start = new AutomationCalendar();
        AutomationCalendar stop = start.copy();
        start.addToDay(-1 * Integer.parseInt(timePeriod));
        
        rest = new RestCommands(userName, password);
        List<Vehicle> vehicles = rest.getBulk(Vehicle.class);
        
        for (Vehicle vehicle : vehicles){
            List<DeviceNote> note = dao.getEvents(UnitType.VEHICLE, vehicle.getVehicleID(), start, stop);
            if (!note.isEmpty()){
                Group group =   rest.getObject(Group.class,  vehicle.getGroupID() );
                Driver driver = rest.getObject(Driver.class, vehicle.getDriverID());
                Person person = rest.getObject(Person.class, driver.getPersonID() );
                
                String fullName = person.getFirst() + 
                        person.getMiddle() == null ? "" : " " + person.getMiddle() +
                        " " + person.getLast() +
                        person.getSuffix() == null ? "" : " " + person.getSuffix();

                AutoStepVariables.saveVariable(vehicleVariable,       vehicle.getName());
                AutoStepVariables.saveVariable(vehicleDriverVariable, fullName);
                AutoStepVariables.saveVariable(vehicleGroupVariable,  group.getName());
                return;
            }
        }
    }

}
