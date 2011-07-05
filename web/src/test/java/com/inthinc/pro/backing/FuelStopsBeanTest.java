package com.inthinc.pro.backing;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.backing.FuelStopsBean.FuelStopView;
import com.inthinc.pro.backing.ui.DateRange;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.hos.HOSRecord;

public class FuelStopsBeanTest extends BaseBeanTest {

    private FuelStopsBean fuelStopsBean;
    private List<FuelStopsBean.FuelStopView> fuelStopsViews;
    private List<HOSRecord> fuelStops;
    private int countBefore;
    
    @Before
    public void before(){
        // team level login
        loginUser("custom101");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        fuelStopsBean = (FuelStopsBean)applicationContext.getBean("fuelStopsBean");        
        fuelStopsBean.setVehicleID(130);
        fuelStopsBean.setVehicle(new Vehicle(130,0,Status.ACTIVE,"vehicle130","Subaru","Impreza",2011,"blue",VehicleType.LIGHT,"12345678901234567", null,"123abc",State.valueOf(45)));
        fuelStops = fuelStopsBean.getHosDAO().getFuelStopRecordsForVehicle(130, new DateRange(new Locale("en_US"),TimeZone.getTimeZone("America/Denver")).getInterval());
        countBefore = fuelStops.size();
    }
    @Test
    public void beanInit()
    {        
        assertEquals(new Integer(15),fuelStopsBean.getPageData().getRowsPerPage());
        assertEquals(new Integer(0),fuelStopsBean.getPageData().getPageStartRow());
        assertEquals(TimeZone.getTimeZone("America/Denver"),fuelStopsBean.getDateRange().getTimeZone());
        assertEquals("US",fuelStopsBean.getDateRange().getLocale().getCountry());
        assertEquals("en",fuelStopsBean.getDateRange().getLocale().getLanguage());
    }
    
    @Test
    public void beanAdd(){
        
        assertTrue("Should be some fuel stops",fuelStops.size() > 0);
        
        String result = fuelStopsBean.add();
        
        assertEquals("pretty:fuelStopEdit",result);
        FuelStopsBean.FuelStopView item = fuelStopsBean.getItem();
        
        assertEquals(new Integer(130), item.getVehicleID());
        item.setTruckGallons(20.0f);
        item.setTrailerGallons(34.5f);
        item.setLocation("Sandy, UT");
        item.setLogTime(new Date());
        
        result = fuelStopsBean.save();
        
        assertEquals("pretty:fuelStops",result);
        fuelStops = fuelStopsBean.getHosDAO().getFuelStopRecordsForVehicle(130, new DateRange(new Locale("en_US"),TimeZone.getTimeZone("America/Denver")).getInterval());
        assertEquals(countBefore+1,fuelStops.size());
        assertEquals(null, fuelStopsBean.getItem());
    }
    @Test
    public void beanEdit(){
        
        assertTrue("Should be some fuel stops",fuelStops.size() > 0);
        fuelStopsViews = fuelStopsBean.getItems();

        fuelStopsViews.get(fuelStops.size()-1);
        fuelStopsBean.setItem(fuelStopsViews.get(fuelStops.size()-1));
        
        String result = fuelStopsBean.edit();
        
        assertEquals("pretty:fuelStopEdit",result);
        FuelStopsBean.FuelStopView item = fuelStopsBean.getItem();
        Long hosLogID = item.getHosLogID();
        assertEquals(new Integer(130), item.getVehicleID());
        item.setTruckGallons(50.0f);
        item.setTrailerGallons(54.5f);
        item.setLocation("Sandy, UT");
        item.setLogTime(new Date());
        
        result = fuelStopsBean.save();
        
        assertEquals(null,fuelStopsBean.getItem());
        HOSRecord hosLog = fuelStopsBean.getHosDAO().findByID(hosLogID);
        assertEquals(50.0f, hosLog.getTruckGallons());
        assertEquals("pretty:fuelStops",result);
    }
    @Test
    public void beanDelete(){
        assertTrue("Should be some fuel stops",fuelStops.size() > 0);
        fuelStopsViews = fuelStopsBean.getItems();

        fuelStopsViews.get(fuelStops.size()-1);
        fuelStopsBean.setItem(fuelStopsViews.get(fuelStopsViews.size()-1));

        String result = fuelStopsBean.deleteSingle();
        
        assertEquals("pretty:fuelStops",result);
        assertEquals(null,fuelStopsBean.getItem());
        fuelStops = fuelStopsBean.getHosDAO().getFuelStopRecordsForVehicle(130, new DateRange(new Locale("en_US"),TimeZone.getTimeZone("America/Denver")).getInterval());
        
        assertTrue("FuelStop should have been deleted",countBefore == fuelStops.size()+1);
    }
    @Test
    public void beanCancelAdd(){
        
        assertTrue("Should be some fuel stops",fuelStops.size() > 0);
        
        String result = fuelStopsBean.add();
        assertEquals("pretty:fuelStopEdit",result);
        FuelStopsBean.FuelStopView item = fuelStopsBean.getItem();
        
        assertEquals(new Integer(130), item.getVehicleID());
        item.setTruckGallons(20.0f);
        item.setTrailerGallons(34.5f);
        item.setLocation("Sandy, UT");
        item.setLogTime(new Date());
        
        result = fuelStopsBean.cancel();
        
        assertEquals("pretty:fuelStops",result);
        assertEquals(null,fuelStopsBean.getItem());
        fuelStops = fuelStopsBean.getHosDAO().getFuelStopRecordsForVehicle(130, new DateRange(new Locale("en_US"),TimeZone.getTimeZone("America/Denver")).getInterval());
        
        assertTrue("FuelStop should not have been added",countBefore == fuelStops.size());
    }
    @Test
    public void beanCancelEdit(){
        assertTrue("Should be some fuel stops",fuelStops.size() > 0);
        fuelStopsViews = fuelStopsBean.getItems();
        fuelStopsBean.setItem(fuelStopsViews.get(fuelStopsViews.size()-1));
        String result = fuelStopsBean.edit();
        assertEquals("pretty:fuelStopEdit",result);
        FuelStopsBean.FuelStopView item = fuelStopsBean.getItem();
        Long hosLogID = item.getHosLogID();
        assertEquals(new Integer(130), item.getVehicleID());
        item.setTruckGallons(20.0f);
        item.setTrailerGallons(34.5f);
        item.setLocation("Sandy, UT");
        item.setLogTime(new Date());
        
        result = fuelStopsBean.cancel();
        
        assertEquals("pretty:fuelStops",result);
        assertEquals(null,fuelStopsBean.getItem());
        HOSRecord hosLog = fuelStopsBean.getHosDAO().findByID(hosLogID);
        assertEquals(90.0f, hosLog.getTruckGallons());
    }
    @Test
    public void beanBatchDelete(){
        fuelStopsViews = fuelStopsBean.getItems();
        Boolean selected = true;
        int count = 0;
        for(FuelStopView fsv : fuelStopsViews){
            fsv.setSelected(selected);
            count += selected?1:0;
            selected = !selected;
        }
        assertEquals(count,fuelStopsBean.getSelectedItems().size());
        String result = fuelStopsBean.delete();
        
        assertEquals("pretty:fuelStops",result);
        fuelStops = fuelStopsBean.getHosDAO().getFuelStopRecordsForVehicle(130, new DateRange(new Locale("en_US"),TimeZone.getTimeZone("America/Denver")).getInterval());
        
        assertTrue("FuelStops should have been deleted",countBefore == fuelStops.size()+count);
    }
    @Test
    public void beanSelect(){
        fuelStopsViews = fuelStopsBean.getItems();
        int totalFuelStops = fuelStopsViews.size();
        fuelStopsBean.setAllSelected(true);
        fuelStopsBean.selectAllDependingOnAllSelected();
        List<FuelStopView> selected = fuelStopsBean.getSelectedItems();
        assertEquals(totalFuelStops,selected.size());
        fuelStopsBean.setAllSelected(false);
        fuelStopsBean.selectAllDependingOnAllSelected();
        selected = fuelStopsBean.getSelectedItems();
        assertEquals(0,selected.size());
        
        fuelStopsBean.setAllSelected(true);
        fuelStopsBean.selectAllDependingOnAllSelected();
        selected = fuelStopsBean.getSelectedItems();
        assertEquals(totalFuelStops,selected.size());
        assertTrue(fuelStopsBean.isAllSelected());
        fuelStopsBean.unselectAll();
        selected = fuelStopsBean.getSelectedItems();
        assertEquals(0,selected.size());
        assertFalse(fuelStopsBean.isAllSelected());

        fuelStopsBean.setAllSelected(true);
        fuelStopsBean.selectAllDependingOnAllSelected();
        selected = fuelStopsBean.getSelectedItems();
        assertEquals(totalFuelStops,selected.size());
        assertTrue(fuelStopsBean.isAllSelected());

        FuelStopView fsv = selected.get(0);
        fsv.setSelected(false);
        assertFalse(fuelStopsBean.isAllSelected());
        fsv.setSelected(true);
        assertTrue(fuelStopsBean.isAllSelected());
    }
}
