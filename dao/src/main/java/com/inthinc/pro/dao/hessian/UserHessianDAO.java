package com.inthinc.pro.dao.hessian;

import java.util.Map;

import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.annotations.Converter;
import com.inthinc.pro.dao.service.CentralService;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Role;

public class UserHessianDAO extends GenericHessianDAO<User, Integer, CentralService> implements UserDAO
{
  @Override
  public User findByEmail(String email)
  {
    Integer userId = getReturnKey(getService().getUserIDByEmail(email));
    return findByID(userId);
  }

  @Override
  public User findByUserName(String username)
  {
    Integer userId = getReturnKey(getService().getUserIDByName(username));
    return findByID(userId);
  }

  @Converter(columnName = "phone")
  public void setPhoneNumbers(User user, Object value)
  {
    if (user == null || value == null)
      return;

    if (value instanceof String)
    {
      String[] phoneNumbers = ((String) value).split(";", 2);
      if (phoneNumbers.length > 0)
        user.setPrimaryPhone(phoneNumbers[0]);
      if (phoneNumbers.length > 1)
        user.setSecondaryPhone(phoneNumbers[1]);
    }
  }

  @Converter(columnName = "sms")
  public void setSMSNumbers(User user, Object value)
  {
    if (user == null || value == null)
      return;

    if (value instanceof String)
    {
      String[] smsNumbers = ((String) value).split(";", 2);
      if (smsNumbers.length > 0)
        user.setPrimaryPhone(smsNumbers[0]);
      if (smsNumbers.length > 1)
        user.setSecondaryPhone(smsNumbers[1]);
    }
  }
  @Converter(columnName = "role")
  public void setRole(User user, Object value)
  {
    if (user == null || value == null)
      return;

    if (value instanceof Integer)
    {
        user.setRole(Role.getRole((Integer)value));
    }
  }
  
  public void test(Object o)
  {
    Map<String, Object> map = convertToMap(o);
    for(Map.Entry<String, Object> entry : map.entrySet())
    {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
  }
}
