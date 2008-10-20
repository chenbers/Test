package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.Column;


public abstract class BaseEntity {

  @Column(updateable=false)
  private Date modified;
  @Column(updateable=false)
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
