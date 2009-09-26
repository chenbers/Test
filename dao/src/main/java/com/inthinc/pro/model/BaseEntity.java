package com.inthinc.pro.model;

import java.io.Serializable;
import java.util.Date;

import com.inthinc.pro.dao.annotations.Column;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class BaseEntity implements Serializable {

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
