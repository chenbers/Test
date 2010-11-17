/**
 * 
 */
package com.inthinc.pro.service.security.stubs;

import java.util.List;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;

/**
 * @author dfreitas
 * 
 */
public class GroupDaoStub implements GroupDAO {

    private Group expectedGroup;

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GroupDAO#getGroupHierarchy(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<Group> getGroupHierarchy(Integer acctID, Integer groupID) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GroupDAO#getGroupsByAcctID(java.lang.Integer)
     */
    @Override
    public List<Group> getGroupsByAcctID(Integer acctID) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#create(java.lang.Object, java.lang.Object)
     */
    @Override
    public Integer create(Integer id, Group entity) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#deleteByID(java.lang.Object)
     */
    @Override
    public Integer deleteByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#findByID(java.lang.Object)
     */
    @Override
    public Group findByID(Integer id) {
        return expectedGroup;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inthinc.pro.dao.GenericDAO#update(java.lang.Object)
     */
    @Override
    public Integer update(Group entity) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param group
     */
    public void setExpectedGroup(Group group) {
        this.expectedGroup = group;
    }

}
