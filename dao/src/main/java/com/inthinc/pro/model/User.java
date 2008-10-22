package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class User extends BaseEntity
{
  @ID
  private Integer userID;

  private String username;
  private String password;
  private Role role;
  private Boolean active;
  
  // private Account account;
  private String first;
  private String last;
  private String primaryPhone;
  private String secondaryPhone;
  private String primarySms;
  private String secondarySms;
  private String email;
  private Integer spam;
  private Integer enabled;
  private Integer expired;

  @Column(name = "acctID")
  private Integer accountID;

  @Column(name = "tzName")
  private String timeZone;
  
  public User()
  {
      
  }
  public User(Integer userID, String username, String password, Role role, boolean active)
  {
      this.userID = userID;
      this.username = username;
      this.password = password;
      this.role = role;
      this.active = active;
  }

  public Integer getUserID()
  {
    return userID;
  }

  public void setUserID(Integer userID)
  {
    this.userID = userID;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getFirst()
  {
    return first;
  }

  public void setFirst(String first)
  {
    this.first = first;
  }

  public String getLast()
  {
    return last;
  }

  public void setLast(String last)
  {
    this.last = last;
  }

  public String getPrimaryPhone()
  {
    return primaryPhone;
  }

  public void setPrimaryPhone(String primaryPhone)
  {
    this.primaryPhone = primaryPhone;
  }

  public String getSecondaryPhone()
  {
    return secondaryPhone;
  }

  public void setSecondaryPhone(String secondaryPhone)
  {
    this.secondaryPhone = secondaryPhone;
  }

  public String getPrimarySms()
  {
    return primarySms;
  }

  public void setPrimarySms(String primarySms)
  {
    this.primarySms = primarySms;
  }

  public String getSecondarySms()
  {
    return secondarySms;
  }

  public void setSecondarySms(String secondarySms)
  {
    this.secondarySms = secondarySms;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getTimeZone()
  {
    return timeZone;
  }

  public void setTimeZone(String timeZone)
  {
    this.timeZone = timeZone;
  }

  public Integer getSpam()
  {
    return spam;
  }

  public void setSpam(Integer spam)
  {
    this.spam = spam;
  }

  public Integer getEnabled()
  {
    return enabled;
  }

  public void setEnabled(Integer enabled)
  {
    this.enabled = enabled;
  }

  public Integer getExpired()
  {
    return expired;
  }

  public void setExpired(Integer expired)
  {
    this.expired = expired;
  }

  public Integer getAccountID()
  {
    return accountID;
  }

  public void setAccountID(Integer accountID)
  {
    this.accountID = accountID;
  }

public Role getRole()
{
    return role;
}

public void setRole(Role role)
{
    this.role = role;
}

public Boolean getActive()
{
    return active;
}

public void setActive(Boolean active)
{
    this.active = active;
}
}
