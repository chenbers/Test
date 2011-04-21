package com.inthinc.pro.i18nutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NonTranslated {
    
    
    // add list of any keys that should not be translated to this map
    
    static Map<String, List<String>> nonTranslated_ro = new HashMap<String, List<String>>();
    static {
        List<String> nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("button_ok");
        nonTranslatedList.add("dateFormat");
        nonTranslatedList.add("hosLog_status");
        nonTranslated_ro.put("/hoskiosk/src/main/resources/com/inthinc/pro/resources/Messages", nonTranslatedList);

        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("HOS");
        nonTranslatedList.add("NONE");
        nonTranslatedList.add("RedFlagLevel.INFO");
        nonTranslatedList.add("ScoreType.SCORE_SPEEDING_21_30");
        nonTranslatedList.add("ScoreType.SCORE_SPEEDING_21_30.metric");
        nonTranslatedList.add("ScoreType.SCORE_SPEEDING_31_40");
        nonTranslatedList.add("ScoreType.SCORE_SPEEDING_31_40.metric");
        nonTranslatedList.add("ScoreType.SCORE_SPEEDING_41_54");
        nonTranslatedList.add("ScoreType.SCORE_SPEEDING_41_54.metric");
        nonTranslatedList.add("ScoreType.SCORE_SPEEDING_55_64");
        nonTranslatedList.add("ScoreType.SCORE_SPEEDING_55_64.metric");
        nonTranslatedList.add("ScoreType.SCORE_SPEEDING_65_80");
        nonTranslatedList.add("ScoreType.SCORE_SPEEDING_65_80.metric");
        nonTranslatedList.add("justDay");
        nonTranslatedList.add("monthDay");
        nonTranslatedList.add("no");
        nonTranslatedList.add("notApplicable");
        nonTranslatedList.add("redflags_catHOS");
        nonTranslatedList.add("report.crashhistory.status");
        nonTranslatedList.add("report.crashhistory.status.EXCLUDE");
        nonTranslatedList.add("report.device.imei");
        nonTranslatedList.add("report.device.status");
        nonTranslatedList.add("report.dvspeed.21.30");
        nonTranslatedList.add("report.dvspeed.21.30.metric");
        nonTranslatedList.add("report.dvspeed.31.40");
        nonTranslatedList.add("report.dvspeed.31.40.metric");
        nonTranslatedList.add("report.dvspeed.41.54");
        nonTranslatedList.add("report.dvspeed.41.54.metric");
        nonTranslatedList.add("report.dvspeed.55.64");
        nonTranslatedList.add("report.dvspeed.55.64.metric");
        nonTranslatedList.add("report.dvspeed.65");
        nonTranslatedList.add("report.dvspeed.65.metric");
        nonTranslatedList.add("report.header.system.name");
        nonTranslatedList.add("report.hos.dateTimeFormat");
        nonTranslatedList.add("report.idling.percent");
        nonTranslatedList.add("report.kph");
        nonTranslatedList.add("report.mi");
        nonTranslatedList.add("report.mi.metric");
        nonTranslatedList.add("report.mph");
        nonTranslatedList.add("report.mph.metric");
        nonTranslatedList.add("report.teamstats.idle_percentage");
        nonTranslatedList.add("report.vehicle.odometer");
        nonTranslatedList.add("timeFormat");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/ReportMessages", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("column.vehicleName.raw");
        nonTranslatedList.add("column.expired.raw");
        nonTranslatedList.add("column.customerId.raw");
        nonTranslatedList.add("column.customerName.raw");
        nonTranslatedList.add("column.imei.raw");
        nonTranslatedList.add("column.warrantyStartDate.raw");
        nonTranslatedList.add("column.warrantyEndDate.raw");
        nonTranslatedList.add("footer.confidential");
        nonTranslatedList.add("column.imei");
        nonTranslatedList.add("column.groupName.raw");

        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/asset/i18n/warrantyList", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("column.1.raw");
        nonTranslatedList.add("column.2.raw");
        nonTranslatedList.add("column.3.raw");
        nonTranslatedList.add("column.4.raw");
        nonTranslatedList.add("column.5.raw");
        nonTranslatedList.add("column.6.raw");
        nonTranslatedList.add("column.7.raw");
        nonTranslatedList.add("column.8.raw");
        nonTranslatedList.add("column.9.raw");
        nonTranslatedList.add("dot.1");
        nonTranslatedList.add("dot.10");
        nonTranslatedList.add("dot.11");
        nonTranslatedList.add("dot.12");
        nonTranslatedList.add("dot.13");
        nonTranslatedList.add("dot.14");
        nonTranslatedList.add("dot.2");
        nonTranslatedList.add("dot.3");
        nonTranslatedList.add("dot.4");
        nonTranslatedList.add("dot.5");
        nonTranslatedList.add("dot.6");
        nonTranslatedList.add("dot.7");
        nonTranslatedList.add("dot.8");
        nonTranslatedList.add("dot.9");
        nonTranslatedList.add("footer.confidential");
        nonTranslatedList.add("footer.page");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/hos/i18n/dotHoursRemaining", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("column.1.raw");
        nonTranslatedList.add("column.10.raw");
        nonTranslatedList.add("column.11.raw");
        nonTranslatedList.add("column.2");
        nonTranslatedList.add("column.2.raw");
        nonTranslatedList.add("column.3.raw");
        nonTranslatedList.add("column.4");
        nonTranslatedList.add("column.4.raw");
        nonTranslatedList.add("column.4.tabular");
        nonTranslatedList.add("column.5");
        nonTranslatedList.add("column.5.raw");
        nonTranslatedList.add("column.5.tabular");
        nonTranslatedList.add("column.6.raw");
        nonTranslatedList.add("column.7.raw");
        nonTranslatedList.add("column.8.raw");
        nonTranslatedList.add("column.9.raw");
        nonTranslatedList.add("footer.confidential");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/hos/i18n/driverDotLog", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("column.1.raw");
        nonTranslatedList.add("column.2.raw");
        nonTranslatedList.add("column.3.raw");
        nonTranslatedList.add("column.4.raw");
        nonTranslatedList.add("column.5.raw");
        nonTranslatedList.add("footer.confidential");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/hos/i18n/drivingTimeViolations", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("report.ddl.graph.legend.original");
        nonTranslatedList.add("report.ddl.header.startOdometer");
        nonTranslatedList.add("report.ddl.header.vehicle");
        nonTranslatedList.add("report.ddl.none");
        nonTranslatedList.add("report.ddl.recap");
        nonTranslatedList.add("report.ddl.recap.hrsAvailTomorrow");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/hos/i18n/HOSDriverDailyLog", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("column.1.raw");
        nonTranslatedList.add("column.2.raw");
        nonTranslatedList.add("column.3.raw");
        nonTranslatedList.add("column.4.raw");
        nonTranslatedList.add("column.4.tabular");
        nonTranslatedList.add("column.5.raw");
        nonTranslatedList.add("column.6.raw");
        nonTranslatedList.add("column.7.raw");
        nonTranslatedList.add("column.8.raw");
        nonTranslatedList.add("column.9.raw");
        nonTranslatedList.add("column.hosStatus");
        nonTranslatedList.add("column.logTime");
        nonTranslatedList.add("footer.confidential");
        nonTranslatedList.add("status.6");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/hos/i18n/hosEdits", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        //nonTranslatedList.add();
        nonTranslatedList.add("column.1.raw");
        nonTranslatedList.add("column.10.tabular");
        nonTranslatedList.add("column.11.raw");
        nonTranslatedList.add("column.11.tabular");
        nonTranslatedList.add("column.12.raw");
        nonTranslatedList.add("column.12.tabular");
        nonTranslatedList.add("column.13.raw");
        nonTranslatedList.add("column.13.tabular");
        nonTranslatedList.add("column.14.raw");
        nonTranslatedList.add("column.14to16.tabularHeader");
        nonTranslatedList.add("column.15.raw");
        nonTranslatedList.add("column.16.raw");
        nonTranslatedList.add("column.2.raw");
        nonTranslatedList.add("column.2.tabular");
        nonTranslatedList.add("column.3.raw");
        nonTranslatedList.add("column.3.tabular");
        nonTranslatedList.add("column.4.raw");
        nonTranslatedList.add("column.4.tabular");
        nonTranslatedList.add("column.5.raw");
        nonTranslatedList.add("column.5.tabular");
        nonTranslatedList.add("column.6.raw");
        nonTranslatedList.add("column.6.tabular");
        nonTranslatedList.add("column.7.raw");
        nonTranslatedList.add("column.7.tabular");
        nonTranslatedList.add("column.8.raw");
        nonTranslatedList.add("column.8.tabular");
        nonTranslatedList.add("column.9.raw");
        nonTranslatedList.add("column.9.tabular");
        nonTranslatedList.add("footer.confidential");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/hos/i18n/hosViolations", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("column.1.raw");
        nonTranslatedList.add("column.2.raw");
        nonTranslatedList.add("column.3.raw");
        nonTranslatedList.add("footer.confidential");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/hos/i18n/hosZeroMiles", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("column.1.raw");
        nonTranslatedList.add("column.2.raw");
        nonTranslatedList.add("column.3.raw");
        nonTranslatedList.add("footer.confidential");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/hos/i18n/nonDOTViolations", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("CANADA");
        nonTranslatedList.add("CANADA_2007_60_DEGREES_CYCLE_1");
        nonTranslatedList.add("CANADA_2007_60_DEGREES_CYCLE_2");
        nonTranslatedList.add("CANADA_2007_60_DEGREES_OIL");
        nonTranslatedList.add("CANADA_2007_CYCLE_1");
        nonTranslatedList.add("CANADA_2007_CYCLE_2");
        nonTranslatedList.add("CANADA_2007_OIL");
        nonTranslatedList.add("CANADA_60_DEGREES");
        nonTranslatedList.add("CANADA_ALBERTA");
        nonTranslatedList.add("CANADA_HOME_OFFICE");
        nonTranslatedList.add("TEXAS");
        nonTranslatedList.add("US");
        nonTranslatedList.add("column.1.raw");
        nonTranslatedList.add("column.10.raw");
        nonTranslatedList.add("column.11.raw");
        nonTranslatedList.add("column.12.raw");
        nonTranslatedList.add("column.13.raw");
        nonTranslatedList.add("column.14.raw");
        nonTranslatedList.add("column.15.raw");
        nonTranslatedList.add("column.16.raw");
        nonTranslatedList.add("column.17.raw");
        nonTranslatedList.add("column.18.raw");
        nonTranslatedList.add("column.19.raw");
        nonTranslatedList.add("column.2.raw");
        nonTranslatedList.add("column.20.raw");
        nonTranslatedList.add("column.21.raw");
        nonTranslatedList.add("column.3.raw");
        nonTranslatedList.add("column.4.raw");
        nonTranslatedList.add("column.5.raw");
        nonTranslatedList.add("column.6.raw");
        nonTranslatedList.add("column.7.raw");
        nonTranslatedList.add("column.8.raw");
        nonTranslatedList.add("column.9.raw");
        nonTranslatedList.add("footer.confidential");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/hos/i18n/violationsDetail", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("column.roadStatus.raw");
        nonTranslatedList.add("column.total");
        nonTranslatedList.add("column.total.raw");
        nonTranslatedList.add("footer.confidential");
        nonTranslatedList.add("total");
        nonTranslatedList.add("total.group");
        nonTranslatedList.add("uom.metric");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/ifta/i18n/stateMileage", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("footer.confidential");
        nonTranslatedList.add("total.date");
        nonTranslatedList.add("total.driver");
        nonTranslatedList.add("total.group");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/performance/i18n/driverHours", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("column.1.raw");
        nonTranslatedList.add("column.10.raw");
        nonTranslatedList.add("column.11.raw");
        nonTranslatedList.add("column.12.raw");
        nonTranslatedList.add("column.13.raw");
        nonTranslatedList.add("column.14.raw");
        nonTranslatedList.add("column.15.raw");
        nonTranslatedList.add("column.2.raw");
        nonTranslatedList.add("column.3.raw");
        nonTranslatedList.add("column.4.raw");
        nonTranslatedList.add("column.5.raw");
        nonTranslatedList.add("column.6.raw");
        nonTranslatedList.add("column.7.raw");
        nonTranslatedList.add("column.8.tabular");
        nonTranslatedList.add("column.9.raw");
        nonTranslatedList.add("footer.confidential");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/performance/i18n/payrollDetail", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("column.1.raw");
        nonTranslatedList.add("column.10.raw");
        nonTranslatedList.add("column.11.raw");
        nonTranslatedList.add("column.12.raw");
        nonTranslatedList.add("column.13.raw");
        nonTranslatedList.add("column.14.raw");
        nonTranslatedList.add("column.15.raw");
        nonTranslatedList.add("column.2.raw");
        nonTranslatedList.add("column.3.raw");
        nonTranslatedList.add("column.4.raw");
        nonTranslatedList.add("column.5.raw");
        nonTranslatedList.add("column.6.raw");
        nonTranslatedList.add("column.7.raw");
        nonTranslatedList.add("column.9.raw");
        nonTranslatedList.add("footer.confidential");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/performance/i18n/payrollSignOff", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("column.1.raw");
        nonTranslatedList.add("column.10.raw");
        nonTranslatedList.add("column.11.raw");
        nonTranslatedList.add("column.12.raw");
        nonTranslatedList.add("column.13.raw");
        nonTranslatedList.add("column.14.raw");
        nonTranslatedList.add("column.15.raw");
        nonTranslatedList.add("column.2.raw");
        nonTranslatedList.add("column.3.raw");
        nonTranslatedList.add("column.4.raw");
        nonTranslatedList.add("column.5.raw");
        nonTranslatedList.add("column.6.raw");
        nonTranslatedList.add("column.7.raw");
        nonTranslatedList.add("column.9.raw");
        nonTranslatedList.add("footer.confidential");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/performance/i18n/payrollSummary", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("column.1");
        nonTranslatedList.add("column.1.raw");
        nonTranslatedList.add("column.2.raw");
        nonTranslatedList.add("column.3.raw");
        nonTranslatedList.add("column.4.raw");
        nonTranslatedList.add("column.5.raw");
        nonTranslatedList.add("column.6.raw");
        nonTranslatedList.add("footer.confidential");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/performance/i18n/tenHourDayViolations", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        //nonTranslatedList.add();
        nonTranslatedList.add("column.1.raw");
        nonTranslatedList.add("column.10.raw");
        nonTranslatedList.add("column.11.raw");
        nonTranslatedList.add("column.12.raw");
        nonTranslatedList.add("column.2.raw");
        nonTranslatedList.add("column.3.raw");
        nonTranslatedList.add("column.4.raw");
        nonTranslatedList.add("column.5.raw");
        nonTranslatedList.add("column.6.raw");
        nonTranslatedList.add("column.7.raw");
        nonTranslatedList.add("column.8.raw");
        nonTranslatedList.add("column.9.raw");
        nonTranslatedList.add("footer.confidential");
        nonTranslated_ro.put("/reports/src/main/resources/com/inthinc/pro/reports/jasper/performance/i18n/vehicleUsageReport", nonTranslatedList);
        
        nonTranslatedList = new ArrayList<String>();
        //none
        nonTranslated_ro.put("/scheduler/src/main/resources/messages", nonTranslatedList);
        
        
        nonTranslatedList = new ArrayList<String>();
        nonTranslatedList.add("FuelEfficiencyType.KMPL_MPG");
        nonTranslatedList.add("FuelEfficiencyType.KMPL_mpg");
        nonTranslatedList.add("FuelEfficiencyType.LP100KM_MPG");
        nonTranslatedList.add("FuelEfficiencyType.LP100KM_mpg");
        nonTranslatedList.add("FuelEfficiencyType.MPG_UK_MPG");
        nonTranslatedList.add("FuelEfficiencyType.MPG_UK_mpg");
        nonTranslatedList.add("FuelEfficiencyType.MPG_US_MPG");
        nonTranslatedList.add("FuelEfficiencyType.MPG_US_mpg");
        nonTranslatedList.add("MeasurementType.ENGLISH_MPH");
        nonTranslatedList.add("MeasurementType.ENGLISH_SCORE_SPEEDING_21_30");
        nonTranslatedList.add("MeasurementType.ENGLISH_SCORE_SPEEDING_31_40");
        nonTranslatedList.add("MeasurementType.ENGLISH_SCORE_SPEEDING_41_54");
        nonTranslatedList.add("MeasurementType.ENGLISH_SCORE_SPEEDING_55_64");
        nonTranslatedList.add("MeasurementType.ENGLISH_SCORE_SPEEDING_65_80");
        nonTranslatedList.add("MeasurementType.ENGLISH_editRedFlag_mph");
        nonTranslatedList.add("MeasurementType.ENGLISH_lbs");
        nonTranslatedList.add("MeasurementType.ENGLISH_mph");
        nonTranslatedList.add("MeasurementType.ENGLISH_sbs_mph");
        nonTranslatedList.add("MeasurementType.METRIC");
        nonTranslatedList.add("MeasurementType.METRIC_MPH");
        nonTranslatedList.add("MeasurementType.METRIC_SCORE_SPEEDING_21_30");
        nonTranslatedList.add("MeasurementType.METRIC_SCORE_SPEEDING_31_40");
        nonTranslatedList.add("MeasurementType.METRIC_SCORE_SPEEDING_41_54");
        nonTranslatedList.add("MeasurementType.METRIC_SCORE_SPEEDING_55_64");
        nonTranslatedList.add("MeasurementType.METRIC_SCORE_SPEEDING_65_80");
        nonTranslatedList.add("MeasurementType.METRIC_editRedFlag_mph");
        nonTranslatedList.add("MeasurementType.METRIC_lbs");
        nonTranslatedList.add("MeasurementType.METRIC_mi");
        nonTranslatedList.add("MeasurementType.METRIC_mph");
        nonTranslatedList.add("MeasurementType.METRIC_sbs_mph");
        nonTranslatedList.add("NotApplicable");
        nonTranslatedList.add("button_email");
        nonTranslatedList.add("button_ok");
        nonTranslatedList.add("crashReport_gps");
        nonTranslatedList.add("crashReport_obd");
        nonTranslatedList.add("crashReport_rpm");
        nonTranslatedList.add("customer_support_email");
        nonTranslatedList.add("deviceReports_device_imei");
        nonTranslatedList.add("devicesHeader_imei");
        nonTranslatedList.add("devicesHeader_status");
        nonTranslatedList.add("driverReports_exclude");
        nonTranslatedList.add("driverReports_include");
        nonTranslatedList.add("drivertrip_quality");
        nonTranslatedList.add("editAccount_support_contact0");
        nonTranslatedList.add("editAccount_support_contact1");
        nonTranslatedList.add("editAccount_support_contact2");
        nonTranslatedList.add("editAccount_support_contact3");
        nonTranslatedList.add("editAccount_support_contact4");
        nonTranslatedList.add("editAlerts_status");
        nonTranslatedList.add("editDevice_imei");
        nonTranslatedList.add("editDevice_status");
        nonTranslatedList.add("editPerson_dob");
        nonTranslatedList.add("editPerson_driver_dot");
        nonTranslatedList.add("editPerson_driver_rfid1");
        nonTranslatedList.add("editPerson_driver_rfid2");
        nonTranslatedList.add("editPerson_priEmail");
        nonTranslatedList.add("editPerson_secEmail");
        nonTranslatedList.add("editPerson_suffixIII");
        nonTranslatedList.add("editPerson_suffixJr");
        nonTranslatedList.add("editPerson_suffixSr");
        nonTranslatedList.add("editVehicle_model");
        nonTranslatedList.add("editVehicle_status");
        nonTranslatedList.add("error_invalid");
        nonTranslatedList.add("excluded");
        nonTranslatedList.add("footer_contact");
        nonTranslatedList.add("footer_copyright");
        nonTranslatedList.add("group_contact");
        nonTranslatedList.add("group_geofence");
        nonTranslatedList.add("group_manager");
        nonTranslatedList.add("group_supervisor");
        nonTranslatedList.add("idlingReports_totalPercent");
        nonTranslatedList.add("included");
        nonTranslatedList.add("justDay");
        nonTranslatedList.add("km_label");
        nonTranslatedList.add("kmh_label");
        nonTranslatedList.add("label_two_parameters");
        nonTranslatedList.add("measurement_speed");
        nonTranslatedList.add("mi_label");
        nonTranslatedList.add("miles1000");
        nonTranslatedList.add("miles500");
        nonTranslatedList.add("miles5000");
        nonTranslatedList.add("monthDay");
        nonTranslatedList.add("mph_label");
        nonTranslatedList.add("myAccount_alertTextSeparator");
        nonTranslatedList.add("myAccount_priEmail");
        nonTranslatedList.add("myAccount_secEmail");
        nonTranslatedList.add("navigation_admin");
        nonTranslatedList.add("no");
        nonTranslatedList.add("notes_clear");
        nonTranslatedList.add("notes_crashhistory_exclude");
        nonTranslatedList.add("notes_crashhistory_status");
        nonTranslatedList.add("notes_diagnostics_clear");
        nonTranslatedList.add("notes_diagnostics_reinstate");
        nonTranslatedList.add("notes_emergency_clear");
        nonTranslatedList.add("notes_emergency_reinstate");
        nonTranslatedList.add("notes_redflags_clear");
        nonTranslatedList.add("notes_redflags_reinstate");
        nonTranslatedList.add("notes_reinstate");
        nonTranslatedList.add("notes_safety_clear");
        nonTranslatedList.add("notes_safety_reinstate");
        nonTranslatedList.add("notes_zone_alert_clear");
        nonTranslatedList.add("notes_zone_alert_reinstate");
        nonTranslatedList.add("personHeader_dob");
        nonTranslatedList.add("personHeader_driver_dot");
        nonTranslatedList.add("personHeader_priEmail");
        nonTranslatedList.add("personHeader_rfid1");
        nonTranslatedList.add("personHeader_rfid2");
        nonTranslatedList.add("personHeader_secEmail");
        nonTranslatedList.add("phoneFormat");
        nonTranslatedList.add("redFlagsHeader_status");
        nonTranslatedList.add("redflags_catNONE");
        nonTranslatedList.add("redflags_clear");
        nonTranslatedList.add("redflags_reinstate");
        nonTranslatedList.add("reportSchedule_email");
        nonTranslatedList.add("reports_distanceDrivenInUnits");
        nonTranslatedList.add("reports_email");
        nonTranslatedList.add("reports_imei");
        nonTranslatedList.add("reports_percent");
        nonTranslatedList.add("reports_time");
        nonTranslatedList.add("sbs_email_from");
        nonTranslatedList.add("sbs_kph");
        nonTranslatedList.add("sbs_mph");
        nonTranslatedList.add("stopsDateTimeFormat");
        nonTranslatedList.add("teamLabels");
        nonTranslatedList.add("teamOverallNA");
        nonTranslatedList.add("teamOverallfourtofive");
        nonTranslatedList.add("teamOverallonetotwo");
        nonTranslatedList.add("teamOverallthreetofour");
        nonTranslatedList.add("teamOveralltwotothree");
        nonTranslatedList.add("teamOverallzerotoone");
        nonTranslatedList.add("teamTotal");
        nonTranslatedList.add("team_total");
        nonTranslatedList.add("timeFormat");
        nonTranslatedList.add("timePicker_am");
        nonTranslatedList.add("timePicker_pm");
        nonTranslatedList.add("time_period_am");
        nonTranslatedList.add("time_period_pm");
        nonTranslatedList.add("title");
        nonTranslatedList.add("vehicleBubble_distance");
        nonTranslatedList.add("vehicleReports_exclude");
        nonTranslatedList.add("vehicleReports_include");
        nonTranslatedList.add("vehiclesHeader_model");
        nonTranslatedList.add("vehicletrip_quality");
        nonTranslatedList.add("zoneAlertsHeader_status");
        nonTranslatedList.add("ALL");
        nonTranslatedList.add("AlertMessageType.ALERT_TYPE_HOS");
        nonTranslatedList.add("CANADA");
        nonTranslatedList.add("CANADA_2007_60_DEGREES_CYCLE_1");
        nonTranslatedList.add("CANADA_2007_60_DEGREES_CYCLE_2");
        nonTranslatedList.add("CANADA_2007_60_DEGREES_OIL");
        nonTranslatedList.add("CANADA_2007_CYCLE_1");
        nonTranslatedList.add("CANADA_2007_CYCLE_2");
        nonTranslatedList.add("CANADA_2007_OIL");
        nonTranslatedList.add("CANADA_60_DEGREES");
        nonTranslatedList.add("CANADA_ALBERTA");
        nonTranslatedList.add("CANADA_HOME_OFFICE");
        nonTranslatedList.add("CAUTION_AREA");
        nonTranslatedList.add("DEVICE");
        nonTranslatedList.add("DOTStoppedState.CARGO");
        nonTranslatedList.add("OFF");
        nonTranslatedList.add("ON");
        nonTranslatedList.add("PhoneEscalationStatus.SUCCESS");
        nonTranslatedList.add("TEXAS");
        nonTranslatedList.add("US");
        nonTranslatedList.add("devicesHeader_mcmid");
        nonTranslatedList.add("editAccount_hos");
        nonTranslatedList.add("editDevice_dot");
        nonTranslatedList.add("editDevice_ifta");
        nonTranslatedList.add("editDevice_mcmid");
        nonTranslatedList.add("editRedFlag_ALERT_TYPE_HOS");
        nonTranslatedList.add("editRedFlag_notype");
        nonTranslatedList.add("editVehicle_DOT");
        nonTranslatedList.add("editVehicle_IFTA");
        nonTranslatedList.add("hosLog_dot");
        nonTranslatedList.add("hosLog_service");
        nonTranslatedList.add("hosLog_status");
        nonTranslatedList.add("hosLogs_service");
        nonTranslatedList.add("hosLogs_status");
        nonTranslatedList.add("longDateTimeFormat");
        nonTranslatedList.add("navigation_hos");
        nonTranslatedList.add("navigation_searchWaysmart");
        nonTranslatedList.add("notes_hos_clear");
        nonTranslatedList.add("redFlagDetailsPopupEscalationEmail");
        nonTranslatedList.add("redflags_catHOS");
        nonTranslatedList.add("report_waysmart");
        nonTranslatedList.add("reports_emailLink");
        nonTranslatedList.add("reports_exportExcel");
        nonTranslatedList.add("reports_exportExcelLink");
        nonTranslatedList.add("reports_exportPDF");
        nonTranslatedList.add("reports_exportPDFLink");
        nonTranslatedList.add("reports_renderHTMLLink");
        nonTranslatedList.add("subNavigation_waysmart");
        nonTranslatedList.add("txtMsg_inbox");
        nonTranslatedList.add("txtMsg_startDate");
        nonTranslatedList.add("vehiclesHeader_DOT");
        nonTranslatedList.add("vehiclesHeader_IFTA");
        nonTranslatedList.add("waySmartAccess");
        nonTranslatedList.add("waysmartOnly");
        nonTranslatedList.add("ZOOM_SAFER");
        nonTranslatedList.add("zoneOption");
        nonTranslatedList.add("zones_download");
        nonTranslatedList.add("zones_reset");
        nonTranslated_ro.put("/web/src/main/resources/com/inthinc/pro/resources/Messages", nonTranslatedList);
    }    

    
    static public List<String> getList(String lang, String key) {
        if (lang.equals("ro")) 
            return nonTranslated_ro.get(key);
        
        return null;        
    }
}
