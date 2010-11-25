/**
 * 
 */
package com.inthinc.pro.service.test.mock;

import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.model.Address;

/**
 * @author dfreitas
 *
 */
@Component
public class AddressDAOStub implements AddressDAO {
    private Address expectedAddress;

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#create(java.lang.Object, java.lang.Object)
     */
    @Override
    public Integer create(Integer id, Address entity) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#deleteByID(java.lang.Object)
     */
    @Override
    public Integer deleteByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#findByID(java.lang.Object)
     */
    @Override
    public Address findByID(Integer id) {
        return expectedAddress;
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#update(java.lang.Object)
     */
    @Override
    public Integer update(Address entity) {
        return 0;
    }

    public Address getExpectedAddress() {
        return expectedAddress;
    }

    public void setExpectedAddress(Address expectedAddress) {
        this.expectedAddress = expectedAddress;
    }

}
