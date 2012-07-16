package com.inthinc.device.scoring;

import java.util.Map;

import com.inthinc.device.emulation.enums.UnitType;
import com.inthinc.device.emulation.interfaces.SiloService;
import com.inthinc.device.hessian.tcp.AutomationHessianFactory;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class ScoringFactory {
    

    private SiloService hessian;
    private ScoringNoteProcessor processor;
    private ScoringNoteSorter sorter;
    
    public ScoringFactory(AutoSilos server){
        hessian = new AutomationHessianFactory().getPortalProxy(server);
        sorter = new ScoringNoteSorter();
        processor = new ScoringNoteProcessor(sorter);
    }
    

    public void setServer(AutoSilos server){
        hessian = new AutomationHessianFactory().getPortalProxy(server);
    }


    private void getVehicleNotes(Integer ID, AutomationCalendar start, AutomationCalendar stop, ProductType deviceType){
        sorter.preProcessNotes(hessian.getVehicleNote(ID, start.epochSeconds(), stop.epochSeconds(), 1, new Integer[]{}), deviceType);
        processor.calculateScores();
    }
    
    private void getDriverNotes(Integer ID, AutomationCalendar start, AutomationCalendar stop, ProductType deviceType){
        sorter.preProcessNotes(hessian.getDriverNote(ID, start.epochSeconds(), stop.epochSeconds(), 1, new Integer[]{}), deviceType );
        processor.calculateScores();
    }
    
    public void scoreMe(UnitType type, Integer ID, AutomationCalendar start, AutomationCalendar stop, ProductType deviceType) {
        if (type == UnitType.DRIVER){
            getDriverNotes(ID, start, stop, deviceType);
        }
        else {
            getVehicleNotes(ID, start, stop, deviceType);
        }    
    }
    
    public Map<String, Double> getOverallScores(){
        return processor.getOverall();
    }
}
