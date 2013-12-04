package com.inthinc.pro.dao.service.mapper;

import com.inthinc.pro.dao.service.dto.Group;

public class GroupMapper implements GenericMapper <com.inthinc.pro.dao.service.dto.Group, com.inthinc.pro.model.Group> {

    @Override
    public Group mapToDTO(com.inthinc.pro.model.Group group) {
        Group dtoGroup = new Group();
        dtoGroup.setGroupID(group.getGroupID());
        return dtoGroup;
    }

    @Override
    public com.inthinc.pro.model.Group mapFromDTO(Group dtoGroup) {
        
        com.inthinc.pro.model.Group group = new com.inthinc.pro.model.Group();
        group.setGroupID(dtoGroup.getGroupID());
        return group;
    }

}
