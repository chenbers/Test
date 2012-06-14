package com.inthinc.pro.service.params;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.service.validation.annotations.ValidGroupProperties;

public class GroupParam {

    @ValidGroupProperties
    private Group group;

    public Group getGroup() {
        return group;
    }

    public GroupParam(Group group) {
        this.group = group;
    }
}
