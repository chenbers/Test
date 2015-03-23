package it.com.inthinc.pro.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.com.inthinc.pro.dao.model.ITData;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;

import com.inthinc.pro.backing.dao.impl.SiloServiceImpl;
import com.inthinc.pro.backing.dao.model.DaoMethod;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.NoteType;

// Tests the Hessian vs dao implementations of the SiloService interface to verify the they return the same data (within reason)
// Currently testing only READ methods.
public class SiloServiceImpTest extends BaseServiceImpTest {

    private static SiloService siloService;


    private List<String> ignoredaoMethod = Arrays.asList(
            "getNote",                      // throws 417 in hessian because Noteserver is off so just tested manually
            "getStops"                      // hessian impl and cassandra impl are quite different (this should be looked at)
    );
    
    static {
        // These field are returned in Hessian map but our model objects do not include them, so we can safely ignore them
        // TODO: productType is a problem because you need to really keep 2 in values to be able to map back and forth between 
        // the enum (Device object does this).  Just marking as ignore for now so the model objects don't need to change
        ignoreFieldMap.put("getAcct", Arrays.asList("props.serialVersionUID"));
        ignoreFieldMap.put("getCrash", Arrays.asList("deviceID"));
        ignoreFieldMap.put("getCrashes", Arrays.asList("deviceID"));        
        ignoreFieldMap.put("getDevice", Arrays.asList("emuFeatureMask", "autoLogoff"));
        ignoreFieldMap.put("getDevicesByAcctID", Arrays.asList("emuFeatureMask", "autoLogoff"));
        ignoreFieldMap.put("getDVLByGroupIDDeep", Arrays.asList("head", "vehicle.groupPath", "vehicle.hos",  "driver.groupPath"));
        ignoreFieldMap.put("getDriver", Arrays.asList("groupPath"));
        ignoreFieldMap.put("getDriverByPersonID", Arrays.asList("groupPath"));
        ignoreFieldMap.put("getDriversByGroupID", Arrays.asList("groupPath"));
        ignoreFieldMap.put("getDriversByGroupIDDeep", Arrays.asList("groupPath"));
        ignoreFieldMap.put("getFwdCmds", Arrays.asList("senderName", "vehicleName", "driverName", "deviceID"));
        ignoreFieldMap.put("getGroup", Arrays.asList("groupPath"));
        ignoreFieldMap.put("getGroupsByAcctID", Arrays.asList("groupPath"));
        ignoreFieldMap.put("getNoteNearLoc", Arrays.asList("isAbsOdo"));
        ignoreFieldMap.put("getSensitivitySliderValues", Arrays.asList("pk", "productType"));
        ignoreFieldMap.put("getStates", Arrays.asList("gainID"));
        ignoreFieldMap.put("getTimezones", Arrays.asList("enabled"));
        ignoreFieldMap.put("getVehicle", Arrays.asList("groupPath", "hos"));
        ignoreFieldMap.put("getVehicleByDriverID", Arrays.asList("groupPath", "hos"));
        ignoreFieldMap.put("getVehicleSettings", Arrays.asList("productVer"));
        ignoreFieldMap.put("getVehicleSettingsByGroupIDDeep", Arrays.asList("productVer"));
        ignoreFieldMap.put("getVehiclesByGroupID", Arrays.asList("groupPath", "hos"));
        ignoreFieldMap.put("getVehiclesByGroupIDDeep", Arrays.asList("groupPath", "hos"));
        ignoreFieldMap.put("getVehiclesNearLoc", Arrays.asList("vehicle.groupPath", "vehicle.hos", "driver.groupPath"));

        ignoreFieldMap.put("getDriverEventPage", Arrays.asList("duration"));
        ignoreFieldMap.put("getRedFlagsPage", Arrays.asList("event.duration"));
        ignoreFieldMap.put("getDriverReportPage", Arrays.asList("milesDriven")); // 199500 vs 199500.0
        ignoreFieldMap.put("getIdlingReportPage", Arrays.asList("lowIdleTime", "highIdleTime","driveTime")); // hessian Long and jdbc Integer
        ignoreFieldMap.put("getIdlingVehicleReportPage", Arrays.asList("lowIdleTime", "highIdleTime","driveTime")); // hessian Long and jdbc Integer
        ignoreFieldMap.put("getVehicleReportPage", Arrays.asList("milesDriven", "odometer", "seatbeltScore")); // hessian Long and jdbc Integer for odometer, milesDriven


        // TODO: The cassandra impl is missing several fields that hessian impl provided (not sure but may need to fix)
        ignoreFieldMap.put("getDriverNote", Arrays.asList("isAbsOdo", "deviceID", "state", "groupID", "created", "sats", "attrMap", "heading"));
        ignoreFieldMap.put("getVehicleNote", Arrays.asList("isAbsOdo", "deviceID", "state", "groupID", "created", "sats", "attrMap", "heading"));

    }

    private static Integer TABLE_PREF_ID = 10;
    private static Integer TABLE_PREF_USER_ID = 1;

    private static Integer REPORT_PREF_ID = 21058;
    private static Integer REPORT_PREF_ACCT_ID = 3699;
    private static Integer REPORT_PREF_USER_ID = 13797;
    
    private static Integer CRASH_ID = 9800;
    
    private static Integer CELLBLOCK_ACCT_ID = 1;

    private static Integer INCLUDE_FORGIVEN = 1;
    
    private static Long NOTE_ID = 89836589837727003L;
    private static Double LAT = 33.0139;
    private static Double LNG = -117.1122;
    
    private static final String BARCODE="speedRacerRFID";

    @Override
    protected void init() throws Exception{
        super.init();
        
        // HESSIAN
        siloService = (SiloService) applicationContext.getBean("siloServiceBean");
    }
    

    @Override
    protected void initArgs() {
        
        List<Map<String, Object>> filterList = new ArrayList<Map<String, Object>>();
        Map<String, Object> pageParams = new HashMap<String, Object>();
        pageParams.put("startRow", 0);
        pageParams.put("endRow", 2);
        
        Long startDate = DateUtil.convertDateToSeconds(new DateTime().minusDays(1).toDate());
        Long endDate = DateUtil.convertDateToSeconds(new DateTime().toDate());
        
        List<NoteType> noteTypes = EventCategory.VIOLATION.getNoteTypesInCategory();
        List<Integer> eventTypesList = new ArrayList<Integer>();
        for (NoteType noteType : noteTypes) {
            eventTypesList.add(noteType.getCode());
        }
        Integer[] eventTypes = (Integer[])eventTypesList.toArray(new Integer[0]); 
        Integer[] aggressiveEventTypes = {2};
        
        methodArgMap = new HashMap<String, Object[]>();

        methodArgMap.put("forgive", new Object[] {itData.teamGroupData.get(ITData.BAD).driver.getDriverID(), NOTE_ID});
        methodArgMap.put("getAcct", new Object[] {itData.account.getAccountID()});
        methodArgMap.put("getAddr", new Object[] {itData.address.getAddrID()});
        methodArgMap.put("getAlert", new Object[] {itData.redFlagAlertList.get(0).getAlertID()});
        methodArgMap.put("getAlertsByAcctID", new Object[] {itData.account.getAccountID()});
        methodArgMap.put("getAlertsByTeamGroupID", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID()});
        methodArgMap.put("getAlertsByUserID", new Object[] {itData.districtUser.getUserID()});
        methodArgMap.put("getAlertsByUserIDDeep", new Object[] {itData.fleetUser.getUserID()});
        methodArgMap.put("getAllAcctIDs", new Object[] {});
        methodArgMap.put("getCellblock", new Object[] {1});
        methodArgMap.put("getCellblocksByAcctID", new Object[] {CELLBLOCK_ACCT_ID});
        methodArgMap.put("getCrash", new Object[] {CRASH_ID});
        methodArgMap.put("getCrashes", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), startDate, endDate, 1});
        methodArgMap.put("getDVLByGroupIDDeep", new Object[] {itData.fleetGroup.getGroupID()});
        methodArgMap.put("getDevice", new Object[] {itData.teamGroupData.get(ITData.BAD).device.getDeviceID()});
        methodArgMap.put("getDeviceReportCount", new Object[] {itData.fleetGroup.getGroupID(), filterList});
        methodArgMap.put("getDeviceReportPage", new Object[] {itData.fleetGroup.getGroupID(), pageParams});
        methodArgMap.put("getDevicesByAcctID", new Object[] {itData.account.getAccountID()});
        methodArgMap.put("getDriver", new Object[] {itData.teamGroupData.get(ITData.BAD).driver.getDriverID()});
        methodArgMap.put("getDriverByPersonID", new Object[] {itData.teamGroupData.get(ITData.BAD).driver.getPerson().getPersonID()});
        methodArgMap.put("getDriverEventCount", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), startDate, endDate, INCLUDE_FORGIVEN, filterList, eventTypes});
        methodArgMap.put("getDriverEventPage", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), startDate, endDate, INCLUDE_FORGIVEN, pageParams, eventTypes});
        methodArgMap.put("getDriverNamesByGroupIDDeep", new Object[] {itData.fleetGroup.getGroupID()});
        methodArgMap.put("getDriverNote", new Object[] {itData.teamGroupData.get(ITData.BAD).driver.getDriverID(), startDate, endDate, INCLUDE_FORGIVEN, aggressiveEventTypes});
        methodArgMap.put("getDriverReportCount", new Object[] {itData.fleetGroup.getGroupID(), filterList});
        methodArgMap.put("getDriverReportPage", new Object[] {itData.fleetGroup.getGroupID(), pageParams});
        methodArgMap.put("getDriversByGroupID", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID()});
        methodArgMap.put("getDriversByGroupIDDeep", new Object[] {itData.fleetGroup.getGroupID()});
        methodArgMap.put("getFwdCmdDefs", new Object[] {});
        methodArgMap.put("getFwdCmds", new Object[] {itData.teamGroupData.get(ITData.WS).device.getDeviceID(), ForwardCommandStatus.STATUS_ALL.getCode()});
        methodArgMap.put("getGroup", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID()});
        methodArgMap.put("getGroupsByAcctID", new Object[] {itData.account.getAccountID()});
        methodArgMap.put("getGroupsByGroupIDDeep", new Object[] {itData.fleetGroup.getGroupID()});
        methodArgMap.put("getID", new Object[] {"username", itData.fleetUser.getUsername()});
        methodArgMap.put("getIdlingReportCount", new Object[] {itData.fleetGroup.getGroupID(), startDate, endDate, filterList});
        methodArgMap.put("getIdlingReportPage", new Object[] {itData.fleetGroup.getGroupID(), startDate, endDate, pageParams});
        methodArgMap.put("getIdlingVehicleReportCount", new Object[] {itData.fleetGroup.getGroupID(), startDate, endDate, filterList});
        methodArgMap.put("getIdlingVehicleReportPage", new Object[] {itData.fleetGroup.getGroupID(), startDate, endDate, pageParams});
        methodArgMap.put("getLastLoc", new Object[] {itData.teamGroupData.get(ITData.BAD).driver.getDriverID(), 1});
        methodArgMap.put("getLastTrip", new Object[] {itData.teamGroupData.get(ITData.BAD).driver.getDriverID(), 1});
        methodArgMap.put("getMessages", new Object[] {1, AlertMessageDeliveryType.EMAIL.getCode()});
        methodArgMap.put("getNextSilo", new Object[] {});
        methodArgMap.put("getNote", new Object[] {NOTE_ID});
        methodArgMap.put("getNoteNearLoc", new Object[] {itData.teamGroupData.get(ITData.BAD).driver.getDriverID(), LAT, LNG, startDate, endDate});
        methodArgMap.put("getPerson", new Object[] {itData.teamGroupData.get(ITData.BAD).driver.getPerson().getPersonID()});
        methodArgMap.put("getPersonByEmpid", new Object[] {itData.account.getAccountID(), itData.teamGroupData.get(ITData.BAD).driver.getPerson().getEmpid()});
        methodArgMap.put("getPersonsByAcctID", new Object[] {itData.account.getAccountID()});
        methodArgMap.put("getRedFlagsCount", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), startDate, endDate, 1, filterList});
        methodArgMap.put("getRedFlagsPage", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), startDate, endDate, 1, pageParams});
        methodArgMap.put("getReportPref", new Object[] {REPORT_PREF_ID});
        methodArgMap.put("getReportPrefsByAcctID", new Object[] {REPORT_PREF_ACCT_ID});
        methodArgMap.put("getReportPrefsByUserID", new Object[] {REPORT_PREF_USER_ID});
        methodArgMap.put("getReportPrefsByUserIDDeep", new Object[] {REPORT_PREF_USER_ID});
        methodArgMap.put("getRfidsForBarcode", new Object[] {BARCODE});
        methodArgMap.put("getRole", new Object[] {itData.fleetUser.getRoles().get(0)});
        methodArgMap.put("getRolesByAcctID", new Object[] {itData.account.getAccountID()});
        methodArgMap.put("getSensitivitySliderValues", new Object[] {});
        methodArgMap.put("getSentTextMsgsByGroupID", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), startDate, endDate});
        methodArgMap.put("getSettingDefs", new Object[] {});
        methodArgMap.put("getSiteAccessPts", new Object[] {});
        methodArgMap.put("getStates", new Object[] {});
        methodArgMap.put("getStops", new Object[] {itData.teamGroupData.get(ITData.INTERMEDIATE).driver.getDriverID(), startDate, endDate});
        methodArgMap.put("getTablePref", new Object[] {TABLE_PREF_ID});
        methodArgMap.put("getTablePrefsByUserID", new Object[] {TABLE_PREF_USER_ID});
        methodArgMap.put("getTextMsgCount", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), startDate, endDate, filterList});
        methodArgMap.put("getTextMsgPage", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), startDate, endDate, filterList, pageParams});
        methodArgMap.put("getTimezones", new Object[] {});
        methodArgMap.put("getTrips", new Object[] {itData.teamGroupData.get(ITData.BAD).driver.getDriverID(), 1, startDate, endDate});
        methodArgMap.put("getUser", new Object[] {itData.fleetUser.getUserID()});
        methodArgMap.put("getUserByPersonID", new Object[] {itData.fleetUser.getPersonID()});
        methodArgMap.put("getUsersAccessPts", new Object[] {itData.fleetUser.getUserID()});
        methodArgMap.put("getUsersByGroupID", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID()});
        methodArgMap.put("getUsersByGroupIDDeep", new Object[] {itData.fleetGroup.getGroupID()});
        methodArgMap.put("getVehicle", new Object[] {itData.teamGroupData.get(ITData.BAD).vehicle.getVehicleID()});
        methodArgMap.put("getVehicleByDriverID", new Object[] {itData.teamGroupData.get(ITData.BAD).driver.getDriverID()});
        methodArgMap.put("getVehicleNamesByGroupIDDeep", new Object[] {itData.fleetGroup.getGroupID()});
        methodArgMap.put("getVehicleNote", new Object[] {itData.teamGroupData.get(ITData.BAD).vehicle.getVehicleID(), startDate, endDate, INCLUDE_FORGIVEN, aggressiveEventTypes});
        methodArgMap.put("getVehicleReportCount", new Object[] {itData.fleetGroup.getGroupID(), filterList});
        methodArgMap.put("getVehicleReportPage", new Object[] {itData.fleetGroup.getGroupID(), pageParams});
        methodArgMap.put("getVehicleSettings", new Object[] {itData.teamGroupData.get(ITData.BAD).vehicle.getVehicleID()});
        methodArgMap.put("getVehicleSettingsByGroupIDDeep", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID()});
        methodArgMap.put("getVehicleSettingsHistory", new Object[] {itData.teamGroupData.get(ITData.BAD).vehicle.getVehicleID(), startDate, endDate});
        methodArgMap.put("getVehiclesByGroupID", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID()});
        methodArgMap.put("getVehiclesByGroupIDDeep", new Object[] {itData.fleetGroup.getGroupID()});
        methodArgMap.put("getVehiclesNearLoc", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), 10, itData.teamGroupData.get(ITData.BAD).group.getMapLat(), itData.teamGroupData.get(ITData.BAD).group.getMapLng() });
        methodArgMap.put("getZone", new Object[] {itData.zone.getZoneID()});
        methodArgMap.put("getZonesByAcctID", new Object[] {itData.account.getAccountID()});
        methodArgMap.put("isSuperuser", new Object[] {itData.fleetUser.getUserID()});
        methodArgMap.put("unforgive", new Object[] {itData.teamGroupData.get(ITData.BAD).driver.getDriverID(), NOTE_ID});
        
    }
    @SuppressWarnings("unchecked")
    @Test
    public void testSiloServiceHessianvsdaoImpl() {
        
        try {
            init();
        } catch (Exception e1) {
            e1.printStackTrace();
            fail("init failed");
        }
        
        Map<String, DaoMethod> methodMap = initMethodMap(SiloServiceImpl.class, false);
        for (String methodName : methodMap.keySet()) {
            boolean hasdaoImpl = hasdaoImpl(methodMap.get(methodName));
            if (hasdaoImpl && !ignoredaoMethod.contains(methodName)) {
//System.out.println(methodName);
//if (!methodName.contains("getVehicleNote")) {
//    continue;
//}
                DaoMethod daoMethod = methodMap.get(methodName);
                Object args[] = methodArgMap.get(methodName);
                Object hessianResult = null;
                Object daoResult = null;
                try {
                    hessianResult = hessianResult(daoMethod, args);
                } catch (EmptyResultSetException e) {
                    hessianResult = null;
                } catch (Throwable e) {
                    e.printStackTrace();
                    fail("Hessian call failed for " + methodName);
                }
                try {
                    daoResult = daoResult(daoMethod, args);
                } catch (Throwable e) {
                    e.printStackTrace();
                    fail("dao call failed for " + methodName);
                }
                
                if ((hessianResult == null && daoResult == null) || (hessianResult == null && List.class.isInstance(daoResult) && ((List<?>)daoResult).isEmpty())) {
                    continue;
                }
                
                // Compare results
                if (List.class.isInstance(hessianResult)) {
                    assertTrue(methodName + ": Hessian Result is a LIST but dao result is not", List.class.isInstance(daoResult));
                    List<?> hessianResultList = (List<?>)hessianResult;
                    List<?> daoResultList = (List<?>)daoResult;
                    
                    assertEquals(methodName + ": Result list sizes do not match", hessianResultList.size(), daoResultList.size());
                    
                    for (int i = 0; i < hessianResultList.size(); i++) {
                    	if (Map.class.isInstance(hessianResultList.get(i))) {
                    		compareResultMaps(methodName, "", (Map<Object, Object>) hessianResultList.get(i), (Map<Object, Object>)daoResultList.get(i));
                    	}
                    	else {
                    		assertEquals(methodName + " - result index: " + i, hessianResultList.get(i), daoResultList.get(i));
                    	}
                    }
                }
                else {
                    compareResultMaps(methodName, "", (Map<Object, Object>) hessianResult, (Map<Object, Object>)daoResult);
                }
                
            }
        }
    }

    public Object hessianResult(DaoMethod daoMethod, Object[] args) throws Throwable {
        InvocationHandler handler = Proxy.getInvocationHandler(siloService);

        Method method = daoMethod.getMethod();
        return handler.invoke(siloService, method, args);

    }

}
