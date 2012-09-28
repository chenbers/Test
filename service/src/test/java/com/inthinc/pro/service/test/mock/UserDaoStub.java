/**
 * 
 */
package com.inthinc.pro.service.test.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.User;

/**
 * @author dfreitas
 *
 */
@Component
public class UserDaoStub implements UserDAO {

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.UserDAO#findByUserName(java.lang.String)
     */
    @Override
    public User findByUserName(String username) {
        return new User();
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.UserDAO#getUserByPersonID(java.lang.Integer)
     */
    @Override
    public User getUserByPersonID(Integer personID) {
        return new User();
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.UserDAO#getUsersInGroupHierarchy(java.lang.Integer)
     */
    @Override
    public List<User> getUsersInGroupHierarchy(Integer groupID) {
        return new ArrayList<User>();
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#create(java.lang.Object, java.lang.Object)
     */
    @Override
    public Integer create(Integer id, User entity) {
        return 0;
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#deleteByID(java.lang.Object)
     */
    @Override
    public Integer deleteByID(Integer id) {
        return 0;
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#findByID(java.lang.Object)
     */
    @Override
    public User findByID(Integer id) {
        return new User();
    }

    /* (non-Javadoc)
     * @see com.inthinc.pro.dao.GenericDAO#update(java.lang.Object)
     */
    @Override
    public Integer update(User entity) {
        return 0;
    }

}
