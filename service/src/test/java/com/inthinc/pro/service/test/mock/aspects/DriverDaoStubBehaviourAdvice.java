package com.inthinc.pro.service.test.mock.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.HasAccountId;
import com.inthinc.pro.model.phone.CellProviderType;

/**
 * Test advice which adds stub behavior to {@link DriverHessianDAO}. It intercepts calls to {@link DriverHessianDAO#findByID(Integer)} and provides test data for driver
 * information. The advice will return specific driver information based on certain test driver ids. The exact spects can be found below.
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
 * <td>Driver with cell phone number but no provider information. The cell phone number is equal to {@link DriverDaoStubBehaviourAdvice#STUBBED_CELL_PHONE_NUMBER}.</td>
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
 * {@link DriverDaoStubBehaviourAdvice#STUBBED_CELL_PHONE_NUMBER}.</td>
 * </tr>
 * <tr>
 * <td>77712</td>
 * <td>Driver with cell phone number and {@link CellProviderType#ZOOM_SAFER} provider. The cell phone number is equal to
 * {@link DriverDaoStubBehaviourAdvice#STUBBED_CELL_PHONE_NUMBER}.</td>
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
@Aspect
@Component
public class DriverDaoStubBehaviourAdvice {

    /**
     * Cell phone number returned in all test drivers returned by this stub advice.
     */
    public static final String STUBBED_CELL_PHONE_NUMBER = "15145555555";

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
     * Advice definition.
     * <p/>
     * AfterReturning advice which checks if the user has access to the returned {@link HasAccountId} instance of the findById() method. Access is granted if the entity has the
     * same account id as the currently logged user.
     * <p/>
     * Note that AspectJ will only invoke this advice if the returning object is of type {@link HasAccountId}.
     */
    @Around(value = "inDriverHessianDAO() && findByIdPointcut()")
    public Object mockFindById(ProceedingJoinPoint pjp) {
        DriverDAO targetDao = (DriverDAO) pjp.getTarget();
        Integer driverId = (Integer) pjp.getArgs()[0];

        Driver driver = new Driver();

        switch (driverId) {
            case 77700:
                break;
            case 77710:
                driver.setCellPhone(STUBBED_CELL_PHONE_NUMBER);
                break;
            case 77701:
                driver.setProvider(CellProviderType.CELL_CONTROL);
                break;
            case 77702:
                driver.setProvider(CellProviderType.ZOOM_SAFER);
                break;
            case 77711:
                driver.setCellPhone(STUBBED_CELL_PHONE_NUMBER);
                driver.setProvider(CellProviderType.CELL_CONTROL);
                break;
            case 77712:
                driver.setCellPhone(STUBBED_CELL_PHONE_NUMBER);
                driver.setProvider(CellProviderType.CELL_CONTROL);
                break;
            default:
                driver = targetDao.findByID(driverId);
        }

        return driver;
    }
}
