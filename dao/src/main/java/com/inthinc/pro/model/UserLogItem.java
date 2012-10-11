package com.inthinc.pro.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserLogItem extends BaseEntity implements Comparable<UserLogItem>{
    
    private Integer userLogID;
    private String methodSignature;
    private String userName;
    private Object[] args;
    private Date called;
    
    public UserLogItem()
    {
        
    }
    
    public UserLogItem(String methodSignature, String userName, Object[] args, Date called){
        super();
        this.methodSignature = methodSignature;
        this.userName = userName;
        this.args = args;
        this.called = called;
    }
    
    @Override
    public int compareTo(UserLogItem o) {
        // natural order is log time descending (most recent first)
        return (o.getCalled().compareTo(getCalled()));
    }

    public Integer getUserLogID() {
        return userLogID;
    }

    public void setUserLogID(Integer userLogID) {
        this.userLogID = userLogID;
    }

    public String getMethodSignature() {
        return methodSignature;
    }

    public void setMethodSignature(String methodSignature) {
        this.methodSignature = methodSignature;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Date getCalled() {
        return called;
    }

    public void setCalled(Date called) {
        this.called = called;
    }

}
