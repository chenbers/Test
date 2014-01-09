package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.richfaces.component.html.HtmlCalendar;
import org.richfaces.component.html.HtmlInputText;

import com.inthinc.pro.backing.FuelStopsBean.FuelStopView;
import com.inthinc.pro.backing.model.LocateVehicleByTime;
import com.inthinc.pro.backing.ui.DateRange;
import com.inthinc.pro.map.GoogleAddressLookup;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleName;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.table.PageData;

public class FuelStopsBeanTest extends BaseBeanTest {

    private FuelStopsBean fuelStopsBean;
    private List<FuelStopsBean.FuelStopView> fuelStopsViews;
    private List<HOSRecord> fuelStops;
    private int countBefore;
    private LocateVehicleByTime locateVehicleByTime;
    private GoogleAddressLookup googleAddressLookupBean;
    @Before
    public void before(){
        // team level login
        loginUser("custom101");
        
        // get the bean from the applicationContext (initialized by Spring injection)
        fuelStopsBean = (FuelStopsBean)applicationContext.getBean("fuelStopsBean");        
        fuelStopsBean.setVehicleID(130);
    	fuelStopsBean.setVehicleNameNow("vehicle130");

        fuelStopsBean.setVehicle(new Vehicle(130,0,Status.ACTIVE,"vehicle130","Subaru","Impreza",2011,
        		"blue",VehicleType.LIGHT,"12345678901234567", null,"123abc",State.valueOf(45)));
        fuelStops = fuelStopsBean.getHosDAO().getFuelStopRecordsForVehicle(130, new DateRange(new Locale("en_US"),TimeZone.getTimeZone("America/Denver")).getInterval());
        countBefore = fuelStops.size();
        
         locateVehicleByTime =(LocateVehicleByTime)applicationContext.getBean("locateVehicleByTime");
         googleAddressLookupBean = new GoogleAddressLookup();

         locateVehicleByTime.setGoogleAddressLookupBean(googleAddressLookupBean);
    }
    @After
    public void after(){
        countBefore = 0;
        fuelStops = null;
        fuelStopsBean = null;
    }
    @Test
    public void beanInit()
    {        
        assertEquals(new Integer(0),fuelStopsBean.getPageData().getCurrentPage());
        assertEquals(new Integer(0),fuelStopsBean.getPageData().getNumPages());
        assertEquals(new Integer(15),fuelStopsBean.getPageData().getRowsPerPage());
        assertEquals(new Integer(0),fuelStopsBean.getPageData().getPageStartRow());
        assertEquals(TimeZone.getTimeZone("America/Denver"),fuelStopsBean.getDateRange().getTimeZone());
        assertEquals("US",fuelStopsBean.getDateRange().getLocale().getCountry());
        assertEquals("en",fuelStopsBean.getDateRange().getLocale().getLanguage());
        assertTrue("Should be some fuel stops",fuelStops.size() > 0);
    }
    
    @Test
    public void beanAdd(){
        
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
    
    private void setupItemsAndItem(){
        fuelStopsViews = fuelStopsBean.getItems();
        fuelStopsViews.get(fuelStops.size()-1);
        fuelStopsBean.setItem(fuelStopsViews.get(fuelStops.size()-1));    	
    }
    @Test
    public void beanEdit(){
        
    	setupItemsAndItem();
    	
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
//    @Test
//    public void beanEditInvalid(){
//        
//    	setupItemsAndItem();
//        
//        String result = fuelStopsBean.edit();
//        
//        assertEquals("pretty:fuelStopEdit",result);
//        FuelStopsBean.FuelStopView item = fuelStopsBean.getItem();
//
//        assertEquals(new Integer(130), item.getVehicleID());
//        item.setTruckGallons(null);
//        item.setTrailerGallons(-0.1f);
//        item.setLocation("Sandy, UT");
//        item.setLogTime(new DateTime().plusDays(1).toDate());
//        
//        result = fuelStopsBean.save();
//        Iterator<FacesMessage> messages = FacesContext.getCurrentInstance().getMessages();
//        assertTrue(messages.hasNext());
//        
//        assertEquals(null,result);
//    }
    @Test
    public void beanDelete(){
    	setupItemsAndItem();

        String result = fuelStopsBean.deleteSingle();
        
        assertEquals("pretty:fuelStops",result);
        assertEquals(null,fuelStopsBean.getItem());
        fuelStops = fuelStopsBean.getHosDAO().getFuelStopRecordsForVehicle(130, new DateRange(new Locale("en_US"),TimeZone.getTimeZone("America/Denver")).getInterval());
        
        assertTrue("FuelStop should have been deleted",countBefore == fuelStops.size()+1);
    }
    @Test
    public void beanCancelAdd(){
    	fuelStopsBean.setVehicleNameNow("vehicle130");
        
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
        List<FuelStopView> nonEditables = new ArrayList<FuelStopView>();
        for (FuelStopView fsv :fuelStopsViews){
            if(!fsv.getEditable()){
                nonEditables.add(fsv);
            }
        }
        fuelStopsBean.setAllSelected(true);
        fuelStopsBean.selectAllDependingOnAllSelected();
        List<FuelStopView> selected = fuelStopsBean.getSelectedItems();
        assertEquals(totalFuelStops-nonEditables.size(),selected.size());
        fuelStopsBean.setAllSelected(false);
        fuelStopsBean.selectAllDependingOnAllSelected();
        selected = fuelStopsBean.getSelectedItems();
        assertEquals(0,selected.size());
        
        fuelStopsBean.setAllSelected(true);
        fuelStopsBean.selectAllDependingOnAllSelected();
        selected = fuelStopsBean.getSelectedItems();
        nonEditables = new ArrayList<FuelStopView>();
        for (FuelStopView fsv :fuelStopsViews){
            if(!fsv.getEditable()){
                nonEditables.add(fsv);
            }
        }
        assertEquals(totalFuelStops-nonEditables.size(),selected.size());
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
    @Test
    public void pageTest(){
        
        fuelStopsViews = fuelStopsBean.getItems();
        PageData pageData = fuelStopsBean.getPageData();
        assertEquals(new Integer(1),pageData.getCurrentPage());
        assertEquals(new Integer(15),pageData.getRowsPerPage());
        assertEquals(new Integer(1),pageData.getNumPages());
    }
    @Test
    public void cantEditBeforeDate(){
    	fuelStopsBean.setVehicleNameNow("vehicle130");

        String result = fuelStopsBean.add();
        
        assertEquals("pretty:fuelStopEdit",result);
        FuelStopsBean.FuelStopView item = fuelStopsBean.getItem();
        
        assertEquals(new Integer(130), item.getVehicleID());
        item.setTruckGallons(20.0f);
        item.setTrailerGallons(34.5f);
        item.setLocation("Sandy, UT");
        LocalDate localDate = new LocalDate(new DateMidnight(new Date(), DateTimeZone.forID("America/Denver")));
        DateTime logTime = localDate.toDateTimeAtStartOfDay(DateTimeZone.forID("America/Denver")).minusDays(25);
        
        item.setLogTime(logTime.toDate());
        
        assertFalse(item.getEditable());

        localDate = new LocalDate(new DateMidnight(new Date(), DateTimeZone.forID("America/Denver")));
        logTime = localDate.toDateTimeAtStartOfDay(DateTimeZone.forID("America/Denver")).minusDays(24);
        item.setLogTime(logTime.toDate());
        
        assertTrue(item.getEditable());
    }
    @Test
    public void locationOfVehicleByTime(){
        String result = fuelStopsBean.add();
        
        assertEquals("pretty:fuelStopEdit",result);
        FuelStopsBean.FuelStopView item = fuelStopsBean.getItem();
        
        assertEquals(new Integer(130), item.getVehicleID());
        item.setTruckGallons(20.0f);
        item.setTrailerGallons(34.5f);
        
        DateTime logTime = new DateTime().minusDays(1);
        
        UIComponent component = new HtmlCalendar();
        ValueChangeEvent event = new ValueChangeEvent(component, item.getLogTime(), logTime.toDate());
        fuelStopsBean.updateDateAndLocation(event);

        assertTrue(item.getLocation().equals("Grand Junction, Colorado"));
    }
    @Test
    public void locationOfVehicleByTimeNullVehicle(){
        String result = fuelStopsBean.add();
        
        assertEquals("pretty:fuelStopEdit",result);
        FuelStopsBean.FuelStopView item = fuelStopsBean.getItem();
        item.setVehicleID(null);
        
        DateTime logTime = new DateTime().minusDays(1);
        
        UIComponent component = new HtmlCalendar();
        ValueChangeEvent event = new ValueChangeEvent(component, item.getLogTime(), logTime.toDate());
        fuelStopsBean.updateDateAndLocation(event);

        assertTrue(item.getLocation().equals(""));
    }
    @Test
    public void changeJustTheItemLogDatetest(){
        
        String result = fuelStopsBean.add();
        
        assertEquals("pretty:fuelStopEdit",result);
        FuelStopsBean.FuelStopView item = fuelStopsBean.getItem();
        
        assertEquals(new Integer(130), item.getVehicleID());
        item.setLocation("Sandy, UT");
        LocalDate localDate = new LocalDate(new DateMidnight(new Date(), DateTimeZone.forID("America/Denver")));
        DateTime logTime = localDate.toDateTimeAtStartOfDay(DateTimeZone.forID("America/Denver")).minusDays(2).plusHours(6);
        
        item.setLogTime(logTime.toDate());
        
        assertEquals(logTime.toDate(),fuelStopsBean.getItem().getLogTime());
        DateTime newDate = localDate.toDateTimeAtStartOfDay(DateTimeZone.forID("America/Denver")).minusDays(1);
        DateTime expectedDate = localDate.toDateTimeAtStartOfDay(DateTimeZone.forID("America/Denver")).minusDays(1).plusHours(6);
        
        fuelStopsBean.changeJustTheItemLogDate(newDate.toDate());
        assertEquals(expectedDate.toDate().getTime(),fuelStopsBean.getItem().getLogTime().getTime());
    }
    @Test
    public void fuelInvalidValidationTest(){
        String result = fuelStopsBean.add();
        
        assertEquals("pretty:fuelStopEdit",result);
        FuelStopsBean.FuelStopView item = fuelStopsBean.getItem();

        UIInput truckGallonsUI = new HtmlInputText();
        UIInput trailerGallonsUI = new HtmlInputText();
        truckGallonsUI.setValue(null);
        trailerGallonsUI.setValue(new Float(-1.0f));
        fuelStopsBean.setTruckGallonsUI(truckGallonsUI);
        fuelStopsBean.setTrailerGallonsUI(trailerGallonsUI);
        item.setTrailerID("trailer");
    	FacesContext facesContext = FacesContext.getCurrentInstance();//Mock instance
    	UIInput hidden = new HtmlInputText();
    	String value="hidden";
        fuelStopsBean.validateFuel(facesContext, hidden, value);
        assertFalse(truckGallonsUI.isValid());
        Iterator<FacesMessage> messages = facesContext.getMessages();
        assertTrue(messages.hasNext()); 
        FacesMessage message = (FacesMessage)messages.next();
        assertEquals("Vehicle or Trailer fuel required.",message.getSummary());
        assertEquals(FacesMessage.SEVERITY_ERROR,message.getSeverity());
    }
    @Test
    public void fuelValidNoTrailerValidationTest(){
        String result = fuelStopsBean.add();
        
        assertEquals("pretty:fuelStopEdit",result);
        FuelStopsBean.FuelStopView item = fuelStopsBean.getItem();

        UIInput truckGallonsUI = new HtmlInputText();
        UIInput trailerGallonsUI = new HtmlInputText();
        truckGallonsUI.setValue(new Float(10.0f));
        fuelStopsBean.setTruckGallonsUI(truckGallonsUI);
        fuelStopsBean.setTrailerGallonsUI(trailerGallonsUI);
        item.setTrailerID(null);
    	FacesContext facesContext = FacesContext.getCurrentInstance();//Mock instance
    	UIInput hidden = new HtmlInputText();
    	String value="hidden";
        fuelStopsBean.validateFuel(facesContext, hidden, value);
        assertTrue(truckGallonsUI.isValid());
        Iterator<FacesMessage> messages = facesContext.getMessages();
        assertFalse(messages.hasNext()); 
//        FacesMessage message = (FacesMessage)messages.next();
//        assertEquals("Vehicle or Trailer fuel required.",message.getSummary());
//        assertEquals(FacesMessage.SEVERITY_ERROR,message.getSeverity());
    }
    @Test
    public void fuelValidWithTrailerValidationTest(){
        String result = fuelStopsBean.add();
        
        assertEquals("pretty:fuelStopEdit",result);
        FuelStopsBean.FuelStopView item = fuelStopsBean.getItem();

        UIInput truckGallonsUI = new HtmlInputText();
        UIInput trailerGallonsUI = new HtmlInputText();
        truckGallonsUI.setValue(null);
        trailerGallonsUI.setValue(new Float(10.0f));
        fuelStopsBean.setTruckGallonsUI(truckGallonsUI);
        fuelStopsBean.setTrailerGallonsUI(trailerGallonsUI);
        item.setTrailerID("trailer");
    	FacesContext facesContext = FacesContext.getCurrentInstance();//Mock instance
    	UIInput hidden = new HtmlInputText();
    	String value="hidden";
        fuelStopsBean.validateFuel(facesContext, hidden, value);
        assertTrue(truckGallonsUI.isValid());
        Iterator<FacesMessage> messages = facesContext.getMessages();
        assertFalse(messages.hasNext()); 
    }
    @Test
    public void fuelValidWithGoodTruckBadTrailerValidationTest(){
        String result = fuelStopsBean.add();
        
        assertEquals("pretty:fuelStopEdit",result);
        FuelStopsBean.FuelStopView item = fuelStopsBean.getItem();

        UIInput truckGallonsUI = new HtmlInputText();
        UIInput trailerGallonsUI = new HtmlInputText();
        truckGallonsUI.setValue(new Float(10.0f));
        trailerGallonsUI.setValue(new Float(-10.0f));
        fuelStopsBean.setTruckGallonsUI(truckGallonsUI);
        fuelStopsBean.setTrailerGallonsUI(trailerGallonsUI);
        item.setTrailerID("trailer");
    	FacesContext facesContext = FacesContext.getCurrentInstance();//Mock instance
    	UIInput hidden = new HtmlInputText();
    	String value="hidden";
        fuelStopsBean.validateFuel(facesContext, hidden, value);
        assertTrue(truckGallonsUI.isValid());
        Iterator<FacesMessage> messages = facesContext.getMessages();
        assertFalse(messages.hasNext()); 
    }
    @Test
    @Ignore
    public void autocompleteTest(){
    	List<VehicleName> suggestions = fuelStopsBean.autocomplete("1");
    }
    @Test
    public void checkDataTest(){
    	fuelStopsBean.checkData();
    }
    
    private static final DateTimeZone testTimeZone = DateTimeZone.forID("US/Mountain");
    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withZone(testTimeZone);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy hh:mm:ss a (z)").withZone(testTimeZone);

    @Test
    public void testItemTime() {
        
        FuelStopView fuelStopView = fuelStopsBean.new FuelStopView();
        fuelStopView.setTimeZone(testTimeZone.toTimeZone());
        DateTime daylightSavingsEndDay = dateFormatter.parseDateTime("11/03/2013");
        fuelStopView.setLogTime(daylightSavingsEndDay.toDate());

        fuelStopView.setTimeInSec(0);
        assertEquals("Expect start of day", "11/03/2013 12:00:00 AM (MDT)", dateTimeFormatter.print(new DateTime(fuelStopView.getLogTime())));
        
        fuelStopView.setTimeInSec(3600);
        assertEquals("Expect 1 am", "11/03/2013 01:00:00 AM (MDT)", dateTimeFormatter.print(new DateTime(fuelStopView.getLogTime())));
        
        fuelStopView.setTimeInSec(7140);
        assertEquals("Expect 01:59 am", "11/03/2013 01:59:00 AM (MDT)", dateTimeFormatter.print(new DateTime(fuelStopView.getLogTime())));

        fuelStopView.setTimeInSec(7200);
        assertEquals("Expect 2 am", "11/03/2013 02:00:00 AM (MST)", dateTimeFormatter.print(new DateTime(fuelStopView.getLogTime())));

        fuelStopView.setTimeInSec(10800);
        assertEquals("Expect 3 am", "11/03/2013 03:00:00 AM (MST)", dateTimeFormatter.print(new DateTime(fuelStopView.getLogTime())));

        fuelStopView.setTimeInSec(10799);
        assertEquals("Expect 2:59:59 am", "11/03/2013 02:59:59 AM (MST)", dateTimeFormatter.print(new DateTime(fuelStopView.getLogTime())));
    }

}
