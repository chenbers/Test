package com.inthinc.pro.backing;

import java.util.TimeZone;

import org.junit.Ignore;

import com.inthinc.pro.backing.PersonBean.PersonView;
import com.inthinc.pro.model.Gender;
import org.junit.Test;

// marking as ignore since won't work with new pagination stuff
@Ignore
public class PersonBeanTest extends BaseAdminBeanTest<PersonBean.PersonView>
{
    private final String OLD_EMP_ID = "13_char_empId";
    private final String NEW_EMP_ID = "9_char_id";
    private final int OLD_SIZE = 20;
    private final int NEW_SIZE = 10;

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
        editItem.setAcctID(1);
        editItem.setEmpid("TK624");
        editItem.setReportsTo("John Doe");
        editItem.setFirst("Test");
        editItem.setMiddle("Unit");
        editItem.setLast("Person");
        editItem.setGender(Gender.FEMALE);
        editItem.setHeight(68);
        editItem.setWeight(123);
    }

    @Override
    protected String[] getBatchUpdateFields()
    {
        return new String[] { "first", "empid" };
    }

    @Test
    public void testCorrectLength() {
        PersonBean personBean = getAdminBean();
        PersonView personView = personBean.getItem();

        //Get original id, so it doesn't break other tests
        int origId = personView.getPersonID();
        try{
            // Edit mode
            personView.setPersonID(1);

            // Set the bean up with an empId longer than 10
            personView.setEmpid(OLD_EMP_ID);
            assertTrue("Old emp id format not detected", personBean.getTransitionEmpIdMaxEditLength() == OLD_SIZE);

            // Set it up with null
            personView.setEmpid(null);
            assertTrue("New emp id format not detected", personBean.getTransitionEmpIdMaxEditLength() == NEW_SIZE);

            // Set it up with an empId smaller than 10
            personView.setEmpid(NEW_EMP_ID);
            assertTrue("New emp id format not detected", personBean.getTransitionEmpIdMaxEditLength() == NEW_SIZE);

            // In add mode, always expect 10
            personView.setPersonID(null);
            assertTrue("New emp id format not detected", personBean.getTransitionEmpIdMaxEditLength() == NEW_SIZE);
        } finally{
            // Re - set the original id
            personView.setPersonID(origId);
        }
    }
}
