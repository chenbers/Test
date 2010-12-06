package com.inthinc.pro.service.security.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.service.adapters.BaseDAOAdapter;
import com.inthinc.pro.service.adapters.PersonDAOAdapter;
import com.inthinc.pro.service.security.TiwiproPrincipal;

/**
 * Authorization advice for PersonDAOAdapter.
 */
@Aspect
@Component
public class PersonAuthorizationAdvice implements EntityAuthorization<Person> {

    @Autowired
    private TiwiproPrincipal principal;

    @Autowired
    private BaseAuthorizationAdvice baseAuthorizationAdvice;

    @Autowired
    private AddressDAO addressDao;

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match all methods in the {@link PersonDAOAdapter} class.
     */
    @Pointcut("target(com.inthinc.pro.service.adapters.PersonDAOAdapter)")
    public void inPersonDAOAdapter() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the create(java.lang.Integer, {@link Person}) method on {@link PersonDAOAdapter}.
     */
    @Pointcut("execution(* com.inthinc.pro.service.adapters.PersonDAOAdapter.create(java.lang.Integer, com.inthinc.pro.model.Person))")
    public void createWithAccountJoinPoint() {}

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has permissions to create a {@link Person} entity with another AccountId. Access is granted only to Inthinc users.
     */
    @SuppressWarnings("unused")
    @Before(value = "createWithAccountJoinPoint() && args(id,entity)", argNames = "id,entity")
    private void doAccessCheck(Integer id, Person entity) {
        if (!principal.isInthincUser()) {
            throw new AccessDeniedException("Principal does not have the proper permission to create the resource.");
        }
    }

    /**
     * Advice definition.
     * <p/>
     * Access to Person entities also require that the address passes validation. Aspects are additive. So we only require to define the check for Address, since the check on
     * person is done by the BaseAuthenticationAspect.
     */
    @SuppressWarnings("unused")
    @Before(value = "inPersonDAOAdapter() && com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.receivesHasAccountIdObjectAsFirstArgument() && args(entity)", argNames = "entity")
    private void doPersonAccessCheck(Person entity) {
        doAccessCheck(entity);
    }

    /**
     * Advice definition.
     * <p/>
     * Access to Person entities also require that the address passes validation.
     * <p/>
     * It needs a distinct joinpoint and advice for delete so it does not conflict with other methods which receives an Integer.
     */
    @SuppressWarnings("unused")
    @Before(value = "inPersonDAOAdapter() && com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.deleteJoinPoint() && args(entityId)", argNames = "entityId")
    private void doDeleteAccessCheck(JoinPoint jp, Integer entityId) {
        BaseDAOAdapter<?> adapter = (BaseDAOAdapter<?>) jp.getTarget();
        Person entity = (Person) adapter.findByID(entityId);
        Address address = null;

        if (entity != null) {
            address = addressDao.findByID(entity.getAddressID());
        }

        baseAuthorizationAdvice.doAccessCheck(entity);
        baseAuthorizationAdvice.doAccessCheck(address);
    }

    /**
     * Advice definition.
     * <p/>
     * Access to Person entities also require that the address passes validation. Aspects are additive. So we only require to define the check for Address, since the check on
     * person is done by the BaseAuthenticationAspect.
     */
    @SuppressWarnings("unused")
    @AfterReturning(value = "inPersonDAOAdapter() && com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.findByJoinPoint()", returning = "retVal", argNames = "retVal")
    private void doFindByAccessCheck(Person retVal) {
        Address address = addressDao.findByID(retVal.getAddressID());
        baseAuthorizationAdvice.doAccessCheck(address);
    }

    /**
     * @see com.inthinc.pro.service.security.aspects.EntityAuthorization#doAccessCheck(java.lang.Object)
     */
    public void doAccessCheck(Person entity) {
        if (entity != null) {
            Address address = addressDao.findByID(entity.getAddressID());
            baseAuthorizationAdvice.doAccessCheck(address);
        }
    }

    public AddressDAO getAddressDao() {
        return addressDao;
    }

    public void setAddressDao(AddressDAO addressDao) {
        this.addressDao = addressDao;
    }
}
