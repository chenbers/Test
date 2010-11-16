package com.inthinc.pro.service.security.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.service.adapters.DeviceDAOAdapter;

/**
 * Base authorization advice for DAO adapters which will only grant access to objects belonging to the same account as the currently logged user.
 */
@Aspect
@Component
public class DeviceAuthorizationAdvice {

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match all methods in the {@link DeviceDAOAdapter} class.
     */
    @Pointcut("target(com.inthinc.pro.service.adapters.DeviceDAOAdapter)")
    public void inDeviceDAOAdapter() {}

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has access to delete the entity.
     */
    @Before(value = "inDeviceDAOAdapter() && (com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.deleteJoinPoint()) && args(deviceId)", argNames = "deviceId")
    public void doDeleteAccessCheck(Integer deviceId) {
    // Method delete() is not yet implemented and there are no authorization rules defined.
    }

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has access to update the entity.
     */
    @Before(value = "inDeviceDAOAdapter() && (com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.receivesHasAccountIdObjectAsFirstArgument()) && args(device)", argNames = "device")
    public void doUpdateAccessCheck(Device device) {
    // Method update() is not yet implemented and there are no authorization rules defined.
    }
}
