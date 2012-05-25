package com.inthinc.pro.automation.models;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseEntity implements Serializable {

    /**
     * Auto generated serial version
     */
    private static final long serialVersionUID = 514782827757426880L;
    private Date modified;
    private Date created;

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    
}
