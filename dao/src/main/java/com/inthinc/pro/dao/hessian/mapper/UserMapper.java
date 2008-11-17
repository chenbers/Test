package com.inthinc.pro.dao.hessian.mapper;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.model.User;

public class UserMapper extends AbstractMapper
{
    @ConvertColumnToField(columnName = "phone")
    public void phoneNumbersToModel(User user, Object value)
    {
        if (user == null || value == null)
            return;

        if (value instanceof String)
        {
            String[] phoneNumbers = ((String) value).split(";", 2);
            if (phoneNumbers.length > 0)
                user.getPerson().setWorkPhone(phoneNumbers[0]);
            if (phoneNumbers.length > 1)
                user.getPerson().setHomePhone(phoneNumbers[1]);
        }
    }
}
