package com.inthinc.pro.service.security.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.service.adapters.VehicleDAOAdapter;

/**
 * Authorization advice for UserDAOAdapter.
 */
@Aspect
@Component
public class VehicleAuthorizationAdvice {

    @Autowired
    private GroupAuthorizationAdvice groupAuthorizationAdvice;

    @Autowired
    private BaseAuthorizationAdvice baseAuthorizationAdvice;

    @Autowired
    private DriverAuthorizationAdvice driverAuthorizationAdvice;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private DriverDAO driverDAO;

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match all methods in the {@link VehicleDAOAdapter} class.
     */
    @Pointcut("target(com.inthinc.pro.service.adapters.VehicleDAOAdapter)")
    public void inVehicleDAOAdapter() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the assignDevice(Integer, Integer) method.
     */
    @Pointcut("execution(* com.inthinc.pro.service.adapters.VehicleDAOAdapter.assignDevice(java.lang.Integer, java.lang.Integer))")
    public void assignDeviceJoinPoint() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the assignDriver(Integer, Integer) method.
     */
    @Pointcut("execution(* com.inthinc.pro.service.adapters.VehicleDAOAdapter.assignDriver(java.lang.Integer, java.lang.Integer))")
    public void assignDriverJoinPoint() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match any joinpoints (methods) which receives a runtime instance of User as the first parameter.
     */
    @Pointcut("args(com.inthinc.pro.model.Vehicle,..)")
    public void receivesVehicleObjectAsFirstArgument() {}

//    /**
//     * 
//     * Pointcut definition.
//     * <p/>
//     * This pointcut will match all methods which receives a Integer as first argument.
//     * <p/>
//     * Overriden from the BaseAuthorizationAdvce so it does not affect other methods in the adapter.
//     */
//    @Pointcut("execution(* com.inthinc.pro.service.adapters.*.*(java.lang.Integer,..))")
//    public void receivesIntegerAs1StArgumentJoinPoint() {}

    /**
     * Advice definition.
     * <p/>
     * Advice which checks if the user has access to the {@link User} resource. Access check to User entities are performed through Group and Person.
     */
    @Before(value = "inVehicleDAOAdapter() && com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.receivesIntegerAs1stArgumentJoinPoint() && !com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.findByJoinPoint()")
    public void doAccessCheck(JoinPoint jp) {
        Vehicle vehicle = ((VehicleDAOAdapter) jp.getTarget()).findByID((Integer) jp.getArgs()[0]);
        doAccessCheck(vehicle);
    }

    /**
     * Advice definition.
     * <p/>
     * Advice which checks if the user has access to the {@link User} instance. Access check to User entities are performed through Group and Person.
     * <p/>
     * Vehicle is already verified by doAccessCheck() so only the check on device is required.
     */
    @Before(value = "assignDeviceJoinPoint()")
    public void doAssignDeviceAccessCheck(JoinPoint jp) {
        Device device = deviceDAO.findByID((Integer) jp.getArgs()[1]);
        baseAuthorizationAdvice.doAccessCheck(device);
    }

    /**
     * Advice definition.
     * <p/>
     * Advice which checks if the user has access to the {@link User} instance. Access check to User entities are performed through Group and Person.
     * <p/>
     * Vehicle is already verified by doAccessCheck() so only the check on driver is required.
     */
    @Before(value = "assignDriverJoinPoint()")
    public void doAssignDriverAccessCheck(JoinPoint jp) {
        Driver driver = driverDAO.findByID((Integer) jp.getArgs()[1]);
        driverAuthorizationAdvice.doAccessCheck(driver);
    }

    /**
     * Advice definition.
     * <p/>
     * AfterReturning advice which checks if the user has access to the returned {@link User} instance. Access check to User entities are performed through Group and Person.
     */
    @AfterReturning(value = "com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.findByJoinPoint() && inVehicleDAOAdapter()", returning = "retVal", argNames = "retVal")
    public void doFindByAccessCheck(Vehicle retVal) {
        doAccessCheck(retVal);
    }

    /**
     * Advice definition.
     * <p/>
     * Access check to {@link User} entities are performed through Group and Person.
     */
    @Before(value = "inVehicleDAOAdapter() && receivesVehicleObjectAsFirstArgument() && args(entity)", argNames = "entity")
    public void doAccessCheck(Vehicle entity) {
        /*
         * TODO Use the DAOs directly. If using the adapter, they will be advised as well, making unnecessary additional calls to the validation framework. Optionally, just use the
         * adapters to do findById and the access rules will automatically be applied. First approach is best though as there are no guarantees that the adapters are being advised.
         */
        Group group = groupDAO.findByID(entity.getGroupID());

        groupAuthorizationAdvice.doAccessCheck(group);
    }
}
