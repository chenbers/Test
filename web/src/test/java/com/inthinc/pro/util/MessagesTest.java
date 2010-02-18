package com.inthinc.pro.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;

public class MessagesTest {

	
	static Map<String, String> nonTranslatedMap = new HashMap<String, String>();
	static {
		nonTranslatedMap.put("footer_copyright", "footer_copyright");
		nonTranslatedMap.put("title","title"); 
	}
	static Map<String, String> nonTranslatedMap_ro = new HashMap<String, String>();
	static {
		nonTranslatedMap_ro.put("footer_copyright", "footer_copyright");
		nonTranslatedMap_ro.put("title","title");
		
		nonTranslatedMap_ro.put("navigation_admin",  "not_translated"); // en: Admin ro: Admin
		nonTranslatedMap_ro.put("notes_crashhistory_exclude",  "notes_crashhistory_exclude"); // en: EXCLUDE ro: EXCLUDE
		nonTranslatedMap_ro.put("notes_redflags_clear", "notes_redflags_clear");	// en: exclude ro: Exclude
		nonTranslatedMap_ro.put("included", "included");			// en: included ro: included
		nonTranslatedMap_ro.put("MeasurementType.ENGLISH_SCORE_SPEEDING_55_64", ""); // en: 55-64 mph ro: 55-64 mph
		nonTranslatedMap_ro.put("km_label",  "not_translated"); //  en: km ro: km
		nonTranslatedMap_ro.put("FuelEfficiencyType.MPG_UK_mpg",  "not_translated"); //  en: mpg ro: mpg
		nonTranslatedMap_ro.put("crashReport_rpm",  "not_translated"); //  en: RPM ro: RPM
		nonTranslatedMap_ro.put("timePicker_am",  "not_translated"); //  en: am ro: am
		nonTranslatedMap_ro.put("MeasurementType.ENGLISH_SCORE_SPEEDING_21_30",  "not_translated"); //  en: 1-30 mph ro: 1-30 mph
		nonTranslatedMap_ro.put("vehiclesHeader_model",  "not_translated"); //  en: Model ro: Model
		nonTranslatedMap_ro.put("editVehicle_status",  "not_translated"); //  en: Status: ro: Status:
		nonTranslatedMap_ro.put("timeFormat",  "not_translated"); //  en: h:mm a (z) ro: h:mm a (z)
		nonTranslatedMap_ro.put("personHeader_rfid2",  "not_translated"); //  en: RFID 2 ro: RFID 2
		nonTranslatedMap_ro.put("personHeader_rfid1",  "not_translated"); //  en: RFID 1 ro: RFID 1
		nonTranslatedMap_ro.put("editPerson_suffixIII",  "not_translated"); //  en: III ro: III
		nonTranslatedMap_ro.put("miles500",  "not_translated"); //  en: 500mi ro: 500mi
		nonTranslatedMap_ro.put("miles1000",  "not_translated"); //  en: 1,000mi ro: 1,000mi
		nonTranslatedMap_ro.put("crashReport_obd",  "not_translated"); //  en: OBD ro: OBD
		nonTranslatedMap_ro.put("error_invalid",  "not_translated"); //  en: Invalid ro: Invalid
		nonTranslatedMap_ro.put("reportSchedule_email",  "not_translated"); //  en: E-mail ro: E-mail
		nonTranslatedMap_ro.put("excluded",  "not_translated"); //  en: excluded ro: excluded
		nonTranslatedMap_ro.put("vehicleReports_include",  "not_translated"); //  en: include ro: Include
		nonTranslatedMap_ro.put("measurement_speed",  "not_translated"); //  en: mph ro: mph
		nonTranslatedMap_ro.put("footer_contact",  "not_translated"); //  en: Contact  ro: Contact
		nonTranslatedMap_ro.put("notes_zone_alert_clear",  "not_translated"); //  en: exclude ro: Exclude
		nonTranslatedMap_ro.put("group_contact",  "not_translated"); //  en: Contact ro: Contact
		nonTranslatedMap_ro.put("redflags_reinstate",  "not_translated"); //  en: include ro: include
		nonTranslatedMap_ro.put("notes_diagnostics_clear",  "not_translated"); //  en: exclude ro: Exclude
		nonTranslatedMap_ro.put("MeasurementType.ENGLISH_sbs_mph",  "not_translated"); //  en: MPH ro: MPH
		nonTranslatedMap_ro.put("notes_zone_alert_reinstate",  "not_translated"); //  en: include ro: Include
		nonTranslatedMap_ro.put("devicesHeader_imei",  "not_translated"); //  en: IMEI ro: IMEI
		nonTranslatedMap_ro.put("notes_diagnostics_reinstate",  "not_translated"); //  en: include ro: Include
		nonTranslatedMap_ro.put("editPerson_secEmail",  "not_translated"); //  en: E-mail 2: ro: E-mail 2: 
		nonTranslatedMap_ro.put("notes_emergency_reinstate",  "not_translated"); //  en: include ro: include
		nonTranslatedMap_ro.put("editPerson_driver_dot",  "not_translated"); //  en: DOT: ro: DOT:
		nonTranslatedMap_ro.put("justDay",  "not_translated"); //  en: dd ro: dd
		nonTranslatedMap_ro.put("kmh_label",  "not_translated"); //  en: kmh ro: kmh
		nonTranslatedMap_ro.put("myAccount_secEmail",  "not_translated"); //  en: E-mail 2: ro: E-mail 2: 
		nonTranslatedMap_ro.put("notes_safety_clear",  "not_translated"); //  en: exclude ro: Exclude
		nonTranslatedMap_ro.put("label_two_parameters",  "not_translated"); //  en: {0} {1} ro: {0} {1}
		nonTranslatedMap_ro.put("notes_safety_reinstate",  "not_translated"); //  en: include ro: Include
		nonTranslatedMap_ro.put("FuelEfficiencyType.MPG_US_mpg",  "not_translated"); //  en: mpg ro: mpg
		nonTranslatedMap_ro.put("button_ok",  "not_translated"); //  en: OK ro: OK
		nonTranslatedMap_ro.put("driverReports_include",  "not_translated"); //  en: include ro: Include
		nonTranslatedMap_ro.put("editPerson_driver_rfid2",  "not_translated"); //  en: ID 2 ro: ID 2
		nonTranslatedMap_ro.put("editPerson_driver_rfid1",  "not_translated"); //  en: ID 1 ro: ID 1
		nonTranslatedMap_ro.put("MeasurementType.METRIC_mph",  "not_translated"); //  en: kph ro: kph
		nonTranslatedMap_ro.put("idlingReports_totalPercent",  "not_translated"); //  en: Total % ro: Total %
		nonTranslatedMap_ro.put("MeasurementType.ENGLISH_SCORE_SPEEDING_65_80",  "not_translated"); //  en: 65-80 mph ro: 65-80 mph
		nonTranslatedMap_ro.put("NotApplicable",  "not_translated"); //  en: N/A ro: N/A
		nonTranslatedMap_ro.put("MeasurementType.METRIC_sbs_mph",  "not_translated"); //  en: KPH ro: KPH
		nonTranslatedMap_ro.put("MeasurementType.ENGLISH_SCORE_SPEEDING_31_40",  "not_translated"); //  en: 31-40 mph ro: 31-40 mph
		nonTranslatedMap_ro.put("miles5000",  "not_translated"); //  en: 5,000mi ro: 5,000mi
		nonTranslatedMap_ro.put("notes_redflags_reinstate",  "not_translated"); //  en: include ro: Include
		nonTranslatedMap_ro.put("FuelEfficiencyType.KMPL_mpg",  "not_translated"); //  en: kmpl ro: kmpl
		nonTranslatedMap_ro.put("sbs_kph",  "not_translated"); //  en: KPH ro: KPH
		nonTranslatedMap_ro.put("reports_distanceDrivenInUnits",  "not_translated"); //  en: {0} {1} ro: {0} {1}
		nonTranslatedMap_ro.put("myAccount_alertTextSeparator",  "not_translated"); //  en: :  ro: :
		nonTranslatedMap_ro.put("FuelEfficiencyType.MPG_UK_MPG",  "not_translated"); //  en: MPG ro: MPG
		nonTranslatedMap_ro.put("reports_email",  "not_translated"); //  en: E-mail ro: E-mail
		nonTranslatedMap_ro.put("vehicleReports_exclude",  "not_translated"); //  en: exclude ro: Exclude
		nonTranslatedMap_ro.put("phoneFormat",  "not_translated"); //  en: ({0}) {1}-{2} ro: ({0}) {1}-{2}
		nonTranslatedMap_ro.put("MeasurementType.METRIC_SCORE_SPEEDING_55_64",  "not_translated"); //  en: 89-103 kph ro: 89-103 kph
		nonTranslatedMap_ro.put("MeasurementType.METRIC_SCORE_SPEEDING_21_30",  "not_translated"); //  en: 1-48 kph ro: 1-48 kph
		nonTranslatedMap_ro.put("FuelEfficiencyType.LP100KM_mpg",  "not_translated"); //  en: l/100km ro: l/100km
		nonTranslatedMap_ro.put("monthDay",  "not_translated"); //  en: MMM dd ro: MMM dd
		nonTranslatedMap_ro.put("redflags_catNONE",  "not_translated"); //  en: {0} ro: {0}
		nonTranslatedMap_ro.put("personHeader_priEmail",  "not_translated"); //  en: E-mail 1 ro: E-mail 1
		nonTranslatedMap_ro.put("mph_label",  "not_translated"); //  en: mph ro: mph
		nonTranslatedMap_ro.put("MeasurementType.METRIC_mi",  "not_translated"); //  en: km ro: km
		nonTranslatedMap_ro.put("mi_label",  "not_translated"); //  en: mi ro: mi
		nonTranslatedMap_ro.put("MeasurementType.ENGLISH_mph",  "not_translated"); //  en: mph ro: mph
		nonTranslatedMap_ro.put("crashReport_gps",  "not_translated"); //  en: GPS ro: GPS
		nonTranslatedMap_ro.put("editDevice_imei",  "not_translated"); //  en: IMEI: ro: IMEI:
		nonTranslatedMap_ro.put("redflags_clear",  "not_translated"); //  en: exclude ro: exclude
		nonTranslatedMap_ro.put("MeasurementType.METRIC_editRedFlag_mph",  "not_translated"); //  en: kph ro: kph
		nonTranslatedMap_ro.put("MeasurementType.ENGLISH_SCORE_SPEEDING_41_54",  "not_translated"); //  en: 41-54 mph ro: 41-54 mph
		nonTranslatedMap_ro.put("FuelEfficiencyType.MPG_US_MPG",  "not_translated"); //  en: MPG ro: MPG
		nonTranslatedMap_ro.put("time_period_pm",  "not_translated"); //  en: PM ro: PM
		nonTranslatedMap_ro.put("notes_clear",  "not_translated"); //  en: exclude ro: Exclude
		nonTranslatedMap_ro.put("MeasurementType.ENGLISH_editRedFlag_mph",  "not_translated"); //  en: mph ro: mph
		nonTranslatedMap_ro.put("MeasurementType.METRIC_MPH",  "not_translated"); //  en: KPH ro: KPH
		nonTranslatedMap_ro.put("personHeader_driver_dot",  "not_translated"); //  en: DOT ro: DOT
		nonTranslatedMap_ro.put("MeasurementType.METRIC",  "not_translated"); //  en: Metric ro: Metric
		nonTranslatedMap_ro.put("reports_percent",  "not_translated"); //  en: % ro: %
		nonTranslatedMap_ro.put("FuelEfficiencyType.KMPL_MPG",  "not_translated"); //  en: KMPL ro: KMPL
		nonTranslatedMap_ro.put("MeasurementType.METRIC_SCORE_SPEEDING_65_80",  "not_translated"); //  en: 104+ kph ro: 104+ kph
		nonTranslatedMap_ro.put("MeasurementType.METRIC_SCORE_SPEEDING_31_40",  "not_translated"); //  en: 49-64 kph ro: 49-64 kph
		nonTranslatedMap_ro.put("editDevice_status",  "not_translated"); //  en: Status: ro: Status:
		nonTranslatedMap_ro.put("button_email",  "not_translated"); //  en: E-mail ro: E-mail
		nonTranslatedMap_ro.put("sbs_mph",  "not_translated"); //  en: MPH ro: MPH
		nonTranslatedMap_ro.put("MeasurementType.METRIC_lbs",  "not_translated"); //  en: kg ro: kg
		nonTranslatedMap_ro.put("reports_imei",  "not_translated"); //  en: IMEI ro: IMEI
		nonTranslatedMap_ro.put("time_period_am",  "not_translated"); //  en: AM ro: AM
		nonTranslatedMap_ro.put("FuelEfficiencyType.LP100KM_MPG",  "not_translated"); //  en: L/100KM ro: L/100KM
		nonTranslatedMap_ro.put("editPerson_suffixSr",  "not_translated"); //  en: Sr. ro: Sr.
		nonTranslatedMap_ro.put("vehicleBubble_distance",  "not_translated"); //  en: {0} {1} ro: {0} {1}
		nonTranslatedMap_ro.put("personHeader_secEmail",  "not_translated"); //  en: E-mail 2 ro: E-mail 2
		nonTranslatedMap_ro.put("editVehicle_model",  "not_translated"); //  en: Model: ro: Model:
		nonTranslatedMap_ro.put("notes_emergency_clear",  "not_translated"); //  en: exclude ro: Exclude
		nonTranslatedMap_ro.put("driverReports_exclude",  "not_translated"); //  en: exclude ro: exclude
		nonTranslatedMap_ro.put("MeasurementType.ENGLISH_MPH",  "not_translated"); //  en: MPH ro: MPH
		nonTranslatedMap_ro.put("editPerson_priEmail",  "not_translated"); //  en: E-mail 1: ro: E-mail 1:
		nonTranslatedMap_ro.put("no",  "not_translated"); //  en: no ro: no
		nonTranslatedMap_ro.put("group_geofence",  "not_translated"); //  en: Geofence ro: Geofence 
		nonTranslatedMap_ro.put("myAccount_priEmail",  "not_translated"); //  en: E-mail 1: ro: E-mail 1: 
		nonTranslatedMap_ro.put("notes_reinstate",  "not_translated"); //  en: include ro: Include
		nonTranslatedMap_ro.put("editPerson_suffixJr",  "not_translated"); //  en: Jr. ro: Jr.
		nonTranslatedMap_ro.put("timePicker_pm",  "not_translated"); //  en: pm ro: pm
		nonTranslatedMap_ro.put("deviceReports_device_imei",  "not_translated"); //  en: IMEI ro: IMEI
		nonTranslatedMap_ro.put("notes_crashhistory_status",  "not_translated"); //  en: Status ro: Status
		nonTranslatedMap_ro.put("MeasurementType.ENGLISH_lbs",  "not_translated"); //  en: lbs ro: lbs
		nonTranslatedMap_ro.put("group_supervisor",  "not_translated"); //  en: Supervisor ro: Supervisor 
		nonTranslatedMap_ro.put("MeasurementType.METRIC_SCORE_SPEEDING_41_54",  "not_translated"); //  en: 65-88 kph ro: 65-88 kph
		nonTranslatedMap_ro.put("devicesHeader_status",  "not_translated"); //  en: Status ro: Status
		nonTranslatedMap_ro.put("sbs_email_from",  "not_translated"); //  en: tiwiPRO Support <speedbystreet@tiwi.com> ro: tiwiPRO Support <speedbystreet@tiwi.com>
		
	}
	
	@Ignore
	@Test
	public void roTest(){
		
		Properties english = getProperties("com/inthinc/pro/resources/Messages.properties");
		Properties translated = getProperties("com/inthinc/pro/resources/Messages_ro.properties");

		int errorCount =  0;
			
		System.out.println("Checking Language: ro");
		Enumeration<?> propNames = english.propertyNames(); 
		while (propNames.hasMoreElements()) {
			String key = propNames.nextElement().toString();
// System.out.println(" " + key);				
			String value = english.getProperty(key);
			String langValue = translated.getProperty(key);
			
			
			if (langValue == null) {
				System.out.println("MISSING: " + key + " en: " + value);
				errorCount++;
			}
			else if (langValue.isEmpty() && value.isEmpty())
				continue;
			else if (nonTranslatedMap.containsKey(key))
				continue;
			else if (nonTranslatedMap_ro.containsKey(key))
				continue;
			else if (langValue.trim().equalsIgnoreCase(value.trim())) { 
				System.out.println("NOT TRANSLATED: " + key + " en: " + value + " ro: " + langValue);
				errorCount++;
			}
		}
				
		assertEquals("ro messages need translations", 0, errorCount);
		
		
	}

	private Properties getProperties(String propFile) {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFile);
        
        Properties properties = new Properties();
        try {
			properties.load(stream);
		} catch (IOException e) {
			System.out.println("ERROR loading: " + propFile);
			e.printStackTrace();
		}

		return properties;
	}
	
	
}
