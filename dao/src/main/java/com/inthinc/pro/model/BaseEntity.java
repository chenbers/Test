package com.inthinc.pro.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.inthinc.pro.dao.annotations.Column;

@XmlRootElement
public abstract class BaseEntity implements Serializable {

    @Column(updateable = false)
    private Date modified;
    @Column(updateable = false)
    private Date created;

    private Locale locale;

    @XmlTransient
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

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

    //TODO: is this necessary in the BaseEntity? Display what? The DAO shouldn't know about the view.
    public void prepareForDisplay() {
    }
}
