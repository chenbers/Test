package com.inthinc.pro.service.test.mock.aspects;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.phone.CellProviderType;

/**
 * Test advice which adds stub behavior to {@link DriverHessianDAO}. It intercepts calls to {@link DriverHessianDAO#findByID(Integer)} and provides test data for driver
 * information. The advice will return specific driver information based on certain test driver ids. The exact specs can be found below.
 * <p/>
 * <table border="1">
 * <tr>
 * <th>Driver ID</th>
 * <th>Data</th>
 * </tr>
 * <tr>
 * <td>77700</td>
 * <td>Driver without cell phone number nor provider information.</td>
 * </tr>
 * <tr>
 * <td>77710</td>
 * <td>Driver with cell phone number but no provider information. The cell phone number is equal to {@link DriverDaoStubBehaviourAdvice#STUBBED_PHONE_NUMBER}.</td>
 * </tr>
 * <tr>
 * <td>77701</td>
 * <td>Driver without cell phone number but with {@link CellProviderType#CELL_CONTROL} provider.</td>
 * </tr>
 * <tr>
 * <td>77702</td>
 * <td>Driver without cell phone number but with {@link CellProviderType#ZOOM_SAFER} provider.</td>
 * </tr>
 * <tr>
 * <td>77711</td>
 * <td>Driver with cell phone number and {@link CellProviderType#CELL_CONTROL} provider. The cell phone number is equal to
 * {@link DriverDaoStubBehaviourAdvice#STUBBED_CELLCONTROL_PHONE_NUMBER}.</td>
 * </tr>
 * <tr>
 * <td>77712</td>
 * <td>Driver with cell phone number and {@link CellProviderType#ZOOM_SAFER} provider. The cell phone number is equal to
 * {@link DriverDaoStubBehaviourAdvice#STUBBED_ZOOMSAFER_PHONE_NUMBER}.</td>
 * </tr>
 * </table>
 * <p/>
 * The IDs of the test drivers follows the following criteria:<br/>
 * 
 * <pre>
 * 777XY
 * Where X can be 0 or 1 (0 = no cell phone number, 1 = with cell phone number)
 * Where Y can be 0, 1 or 2 (0 = no provider info, 1 = CELL_CONTROL provider, 2 = ZOOM_SAFER provider)
 * </pre>
 * 
 * <p/>
 * To enable this advice, make sure resource <tt>spring/applicationContext-test.xml</tt> is loaded by Spring, along with the {@link DriverHessianDAO} bean.
 */
@SuppressWarnings("serial")
// TODO This aspect and package must be moved back to src/test once QA testing is performed.
@Aspect
@Component
public class DriverDaoStubBehaviourAdvice {

    public static final String CELLCOPNTROL_USERNAME = "inthincapi";
    public static final String CELLCONTROL_PASSWORD = "1qa@WS3ed";
    public static final String ZOOMSAFER_USERNAME = "8012438873";
    public static final String ZOOMSAFER_PASSWORD = "password";
    
    /*
     * Test data stored in a map by driverId.
     */
    private Map<Integer, Driver> testDrivers = new HashMap<Integer, Driver>() {
        {
            Driver driver77700 = new Driver();
            driver77700.setDriverID(77700);

            Driver driver77710 = new Driver();
            driver77710.setCellblock(new Cellblock());
            driver77710.getCellblock().setCellPhone(STUBBED_PHONE_NUMBER);
            driver77710.setDriverID(77710);

            Driver driver77701 = new Driver();
            driver77701.setCellblock(new Cellblock());
            driver77701.getCellblock().setProvider(CellProviderType.CELL_CONTROL);
            driver77701.getCellblock().setProviderUsername(CELLCOPNTROL_USERNAME);
            driver77701.getCellblock().setProviderPassword(CELLCONTROL_PASSWORD);
            driver77701.setDriverID(77701);

            Driver driver77702 = new Driver();
            driver77702.setCellblock(new Cellblock());
            driver77702.getCellblock().setProvider(CellProviderType.ZOOM_SAFER);
            driver77702.getCellblock().setProviderUsername(ZOOMSAFER_USERNAME);
            driver77702.getCellblock().setProviderPassword(ZOOMSAFER_PASSWORD);
            driver77702.setDriverID(77702);

            Driver driver77711 = new Driver();
            driver77711.setLicense("123465AA");
            driver77711.setCellblock(new Cellblock());
            driver77711.getCellblock().setProvider(CellProviderType.CELL_CONTROL);
            driver77711.getCellblock().setProviderUsername(CELLCOPNTROL_USERNAME);
            driver77711.getCellblock().setProviderPassword(CELLCONTROL_PASSWORD);
            driver77711.getCellblock().setCellPhone(STUBBED_CELLCONTROL_PHONE_NUMBER);
            driver77711.setDriverID(77711);

            Driver driver77712 = new Driver();
            driver77712.setCellblock(new Cellblock());
            driver77712.getCellblock().setProvider(CellProviderType.ZOOM_SAFER);
            driver77712.getCellblock().setProviderUsername(ZOOMSAFER_USERNAME);
            driver77712.getCellblock().setProviderPassword(ZOOMSAFER_PASSWORD);
            driver77712.getCellblock().setCellPhone(STUBBED_ZOOMSAFER_PHONE_NUMBER);
            driver77712.setDriverID(77712);

            put(77700, driver77700);
            put(77710, driver77710);
            put(77701, driver77701);
            put(77702, driver77702);
            put(77711, driver77711);
            put(77712, driver77712);
        }
    };

    /**
     * Cell phone number returned in all test drivers associated with Cellcontrol.
     */
    public static final String STUBBED_CELLCONTROL_PHONE_NUMBER = "2259388363";

    /**
     * Cell phone number returned in all test drivers associated with Zoomsafer.
     */
    public static final String STUBBED_ZOOMSAFER_PHONE_NUMBER = "8012438873";

    /**
     * Cell phone number returned in all test drivers without provider information.
     */
    public static final String STUBBED_PHONE_NUMBER = "15145555555";

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match any joinpoints (methods) within the {@link DriverHessianDAO}.
     */
    @Pointcut("target(com.inthinc.pro.dao.hessian.DriverHessianDAO)")
    public void inDriverHessianDAO() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the findByID() method.
     */
    @Pointcut("execution(* *.findByID(java.lang.Object))")
    public void findByIdPointcut() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the findByPhoneNumber() method.
     */
    @Pointcut("execution(* *.findByPhoneNumber(java.lang.String))")
    public void findByPhoneNumber() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the update() method.
     */
    @Pointcut("execution(* *.update(java.lang.Object))")
    public void updatePointcut() {}

    /**
     * Advice definition.
     * <p/>
     * Around advice for stubbing data for findById().
     * 
     * @throws Throwable
     *             If unable to proceed with the join point.
     */
    @Around(value = "inDriverHessianDAO() && findByIdPointcut()")
    public Object mockFindById(ProceedingJoinPoint pjp) throws Throwable {
        Integer driverId = (Integer) pjp.getArgs()[0];

        switch (driverId) {
            case 77700:
            case 77710:
            case 77701:
            case 77702:
            case 77711:
            case 77712:
                return testDrivers.get(driverId);
            default:
                return (Driver) pjp.proceed();
        }
    }

    /**
     * Advice definition.
     * <p/>
     * Around advice for stubbing data for findByPhoneNumber().
     * 
     * @throws Throwable
     *             If unable to proceed with the join point.
     */
    @Around(value = "inDriverHessianDAO() && findByPhoneNumber()")
    public Object mockFindByPhoneNumber(ProceedingJoinPoint pjp) throws Throwable {
        String phoneNumber = (String) pjp.getArgs()[0];

        if (phoneNumber.equals(STUBBED_PHONE_NUMBER)) {
            return testDrivers.get(77710);
        } else if (phoneNumber.equals(STUBBED_CELLCONTROL_PHONE_NUMBER)) {
            return testDrivers.get(77711);
        } else if (phoneNumber.equals(STUBBED_ZOOMSAFER_PHONE_NUMBER)) {
            return testDrivers.get(77712);
        } else {
            return null;
        }
    }

    /**
     * Advice definition.
     * <p/>
     * Around advice for stubbing data for update().
     * 
     * @throws Throwable
     *             If unable to proceed with the join point.
     */
    @Around(value = "inDriverHessianDAO() && updatePointcut()")
    public Object mockUpdate(ProceedingJoinPoint pjp) throws Throwable {
        Integer driverId = ((Driver) pjp.getArgs()[0]).getDriverID();

        if (driverId == null) {
            return null;
        }

        switch (driverId) {
            case 77700:
            case 77710:
            case 77701:
            case 77702:
            case 77711:
            case 77712:
                return driverId;
            default:
                return pjp.proceed();
        }
    }
}
