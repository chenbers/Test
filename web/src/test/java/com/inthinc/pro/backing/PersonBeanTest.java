package com.inthinc.pro.backing;

import java.util.Date;
import java.util.TimeZone;

import com.inthinc.pro.backing.PersonBean.PersonView;
import com.inthinc.pro.model.Gender;

public class PersonBeanTest extends BaseAdminBeanTest<PersonBean.PersonView>
{
    @Override
    protected PersonBean getAdminBean()
    {
        return (PersonBean) applicationContext.getBean("personBean");
    }

    @Override
    protected String getFilterValue()
    {
        return "s";
    }

    @Override
    protected void populate(PersonView editItem, BaseAdminBean<PersonBean.PersonView> adminBean)
    {
        editItem.setTimeZone(TimeZone.getTimeZone("UTC"));
        editItem.setCostPerHour(1000);
        editItem.setHomePhone("123-456-7890");
        editItem.setWorkPhone("098-765-4321");
        editItem.setEmail("nobody@nowhere.com");
        editItem.setEmpid("TK624");
        editItem.setReportsTo("John Doe");
        editItem.setFirst("Test");
        editItem.setMiddle("Unit");
        editItem.setLast("Person");
        editItem.setGender(Gender.FEMALE);
        editItem.setHeight(68);
        editItem.setWeight(123);
        editItem.setDob(new Date());
    }

    @Override
    protected String[] getBatchUpdateFields()
    {
        return new String[] { "first", "empid" };
    }
}
