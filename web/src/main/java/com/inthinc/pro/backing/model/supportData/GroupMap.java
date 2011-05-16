package com.inthinc.pro.backing.model.supportData;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.BaseEntity;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;

public class GroupMap extends CacheItemMap<Group,Group> {

    private Integer groupID;
    private Integer acctID;
    private GroupDAO groupDAO;

    public GroupMap(Integer acctID,Integer groupID) {
        super();
        this.groupID = groupID;
        this.acctID = acctID;
    }

    @Override
    protected Group fetchItem(Integer key) {
        return groupDAO.findByID(key);
    }

    @Override
    protected List<Group> fetchItems() {
        return getGroups();
    }

    @Override
    public List<Group> getItems() {
        return new ArrayList<Group>(itemMap.values());
    }

    @Override
    public void refreshMap() {
        buildMap();
    }

    @Override
    protected GenericDAO<Group, Integer> getDAO() {
        return groupDAO;
    }

    @Override
    public void setDAO(GenericDAO<Group, Integer> dao) {
        if (dao instanceof GroupDAO){
            groupDAO = (GroupDAO) dao;
        }
    }

    @Override
    protected Integer getId(Group item) {
        return item.getGroupID();
    }
    protected List<Group> getGroups()
    {
        return groupDAO.getGroupHierarchy(acctID, groupID);
    }

}
