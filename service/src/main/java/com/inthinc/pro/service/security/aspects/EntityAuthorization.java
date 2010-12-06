package com.inthinc.pro.service.security.aspects;

/**
 * Defines a common interface for entity authorization so functionality can be shared and reused between aspects.
 * 
 * @param <T>
 *            The type of the entity being checked.
 */
public interface EntityAuthorization<T> {

    /**
     * Performs access verification on the given entity.
     * 
     * @param entity
     *            The entity to be checked.
     */
    public void doAccessCheck(T entity);
}
