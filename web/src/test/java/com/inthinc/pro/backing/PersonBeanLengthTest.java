package com.inthinc.pro.backing;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountAttributes;
import com.inthinc.pro.model.Person;
import mockit.Mocked;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PersonBeanLengthTest {

    private final int NO_WAYSMART_SIZE = 30;
    private final int WAYSMART_SIZE = 10;
    public PersonBean personBean;
    public Account account;
    public Person person;
    public PersonBean.PersonView personView;
    public AccountAttributes accountAttributes;

    @Mocked
    AccountDAO accountDAO;

    @Before
    public void init(){
        accountAttributes = new AccountAttributes();
        personBean = new PersonBean();
        personView = new PersonBean.PersonView();
        person = new Person();
        person.setAcctID(1);
        personBean = new PersonBean(){
            @Override
            public Account getAccount() {
                return account;
            }

            @Override
            public PersonView getItem() {
                return personView;
            }
        };
        personBean.item = personView;
        personView.setBean(personBean);
        account = new Account();
        account.setAccountID(1);
    }

    @Test
    public void testCorrectLength() {
        accountAttributes.setWaySmart("false");
        personBean.getAccount().setProps(accountAttributes);
        assertEquals(NO_WAYSMART_SIZE, personBean.getTransitionEmpIdMaxEditLength());

        accountAttributes.setWaySmart("true");
        personBean.getAccount().setProps(accountAttributes);
        assertEquals(WAYSMART_SIZE, personBean.getTransitionEmpIdMaxEditLength());
    }

}
