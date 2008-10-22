package com.inthinc.pro.dao.mock.data;

import java.util.Map;

import com.inthinc.pro.model.Role;

public class UserMockData extends BaseMockData
{
    public static final String PASSWORD="nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password

    Data userData = new Data(
          new String[] {"userID", "username", "email"},     // primary keys (can lookup on these)
          new String[] {"password", "role", "active"},      // any other data
                                                            // data in order of primary keys and then other keys
                                                            // should follow types/format that hessian backend will return
          new Object[][] {{Integer.valueOf(1), "expired", "expired@email.com", PASSWORD, Role.ROLE_NORMAL_USER.getCode(), Integer.valueOf(0)},
                          {Integer.valueOf(2), "custom", "custom@email.com", PASSWORD, Role.ROLE_CUSTOM_USER.getCode(), Integer.valueOf(1)},
                          {Integer.valueOf(3), "normal", "normal@email.com", PASSWORD, Role.ROLE_NORMAL_USER.getCode(), Integer.valueOf(1)},
                          {Integer.valueOf(4), "readonly", "readonly@email.com", PASSWORD, Role.ROLE_READONLY.getCode(), Integer.valueOf(1)},
                          {Integer.valueOf(5), "superuser", "superuser@email.com", PASSWORD, Role.ROLE_SUPER_USER.getCode(), Integer.valueOf(1)},
                          {Integer.valueOf(6), "supervisor", "supervisor@email.com", PASSWORD, Role.ROLE_SUPERVISOR.getCode(), Integer.valueOf(1)},
                          }
    );

    
    Map<String, Map <Object, Map<String, Object>>> userLookupMaps; 

    public UserMockData()
    {
    }

    @Override
    protected Map<String, Map<Object, Map<String, Object>>> getLookupMaps()
    {
        if (userLookupMaps == null)
        {
            userLookupMaps = initData(userData);
        }
        return userLookupMaps;
    }

}
