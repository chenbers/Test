package it.com.inthinc.pro.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.com.inthinc.pro.dao.model.ITData;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;

import com.inthinc.pro.backing.dao.impl.ReportServiceImpl;
import com.inthinc.pro.backing.dao.model.DaoMethod;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.DriveQMetric;
import com.inthinc.pro.model.Duration;

public class ReportServiceImpTest extends BaseServiceImpTest {

    private static ReportService reportService;

    private static final Integer COUNT=5;

    // TODO these are methods that failed in the dao impl and need to be fixed
    private List<String> ignoredaoMethod = Arrays.asList(

    );
    
    @Override
    protected void init() throws Exception{
        super.init();
        
        // HESSIAN
        reportService = (ReportService) applicationContext.getBean("reportServiceBean");
    }
    

    @Override
    protected void initArgs() {
        
        Long startDate = DateUtil.convertDateToSeconds(new DateTime().minusDays(1).toDate());
        Long endDate = DateUtil.convertDateToSeconds(new DateTime().toDate());
        
        
        methodArgMap = new HashMap<String, Object[]>();
        methodArgMap.put("getDScoreByDT", new Object[] {itData.teamGroupData.get(ITData.BAD).driver.getDriverID(), Duration.DAYS.getCode()});
        methodArgMap.put("getDTrendByDTC", new Object[] {itData.teamGroupData.get(ITData.BAD).driver.getDriverID(), Duration.DAYS.getCode(), COUNT});
        methodArgMap.put("getVScoreByVT", new Object[] {itData.teamGroupData.get(ITData.BAD).vehicle.getVehicleID(), Duration.DAYS.getCode()});
        methodArgMap.put("getVTrendByVTC", new Object[] {itData.teamGroupData.get(ITData.BAD).vehicle.getVehicleID(), Duration.DAYS.getCode(), COUNT});
        methodArgMap.put("getGDScoreByGT", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), Duration.DAYS.getCode()});
        methodArgMap.put("getGDTrendByGTC", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), Duration.DAYS.getCode(),  COUNT});
        methodArgMap.put("getGDScoreByGSE", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), startDate, endDate});
        methodArgMap.put("getDVScoresByGT", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), Duration.DAYS.getCode()});
        methodArgMap.put("getVDScoresByGT", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), Duration.DAYS.getCode()});
        methodArgMap.put("getDPctByGT", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), Duration.DAYS.getCode(), DriveQMetric.DRIVEQMETRIC_OVERALL});
        methodArgMap.put("getDVScoresByGSE", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), startDate, endDate});
        methodArgMap.put("getVDScoresByGSE", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), startDate, endDate});
        methodArgMap.put("getSDScoresByGT", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), Duration.DAYS.getCode()});
        methodArgMap.put("getSDTrendsByGTC", new Object[] {itData.teamGroupData.get(ITData.BAD).group.getGroupID(), Duration.DAYS.getCode(), COUNT});

    }
    @SuppressWarnings("unchecked")
    @Test
    public void testReportServiceHessianvsdaoImpl() {
        
        try {
            init();
        } catch (Exception e1) {
            e1.printStackTrace();
            fail("init failed");
        }
        
        Map<String, DaoMethod> methodMap = initMethodMap(ReportServiceImpl.class, false);
        for (String methodName : methodMap.keySet()) {
            boolean hasdaoImpl = hasdaoImpl(methodMap.get(methodName));
            if (hasdaoImpl && !ignoredaoMethod.contains(methodName)) {
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
        InvocationHandler handler = Proxy.getInvocationHandler(reportService);

        Method method = daoMethod.getMethod();
        return handler.invoke(reportService, method, args);

    }

}
