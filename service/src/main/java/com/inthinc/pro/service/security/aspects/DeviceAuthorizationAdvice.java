package com.inthinc.pro.service.security.aspects;

import org.apache.commons.lang.NotImplementedException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.service.adapters.DeviceDAOAdapter;

/**
 * Authorization advice for DeviceDAOAdapter.
 */
@Aspect
@Component
public class DeviceAuthorizationAdvice implements EntityAuthorization<Device> {

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
    @SuppressWarnings("unused")
    @Before(value = "inDeviceDAOAdapter() && (com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.deleteJoinPoint()) && args(deviceId)", argNames = "deviceId")
    private void doDeleteAccessCheck(Integer deviceId) {
    // Method delete() is not yet implemented and there are no authorization rules defined.
    }

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has access to update the entity.
     */
    @SuppressWarnings("unused")
    @Before(value = "inDeviceDAOAdapter() && (com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.receivesHasAccountIdObjectAsFirstArgument()) && args(device)", argNames = "device")
    private void doUpdateAccessCheck(Device device) {
    // Method update() is not yet implemented and there are no authorization rules defined.
    }

    /**
     * @see com.inthinc.pro.service.security.aspects.EntityAuthorization#doAccessCheck(java.lang.Object)
     */
    @Override
    public void doAccessCheck(Device entity) {
        throw new NotImplementedException("Method not implemented.");
    }
}
