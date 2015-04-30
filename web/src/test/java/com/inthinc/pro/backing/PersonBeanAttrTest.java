package com.inthinc.pro.backing;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.model.AccountAttributes;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;
import org.junit.Test;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.TestingAuthenticationToken;

import javax.faces.model.SelectItem;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class PersonBeanAttrTest {


    @Test
    public void testTexasOilRule() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setStatus(Status.ACTIVE);

        ProUser proUser = new ProUser(user, false, false, new GrantedAuthority[]{});
        Authentication authentication = new TestingAuthenticationToken(proUser, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        PersonBean personBean = new PersonBean();
        AccountAttributes accountAttributes = new AccountAttributes();
        proUser.setAccountAttributes(accountAttributes);

        accountAttributes.setTexasOilRule(RuleSetType.TEXAS.getCode().toString());
        assertEquals(personBean.getTexasOilRule(), RuleSetType.TEXAS.getCode().toString());

        accountAttributes.setTexasOilRule(RuleSetType.TEXAS_DOD15_7DAY.getCode().toString());
        assertEquals(personBean.getTexasOilRule(), RuleSetType.TEXAS_DOD15_7DAY.getCode().toString());
    }

    @Test
    public void filteredTest() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setStatus(Status.ACTIVE);

        ProUser proUser = new ProUser(user, false, false, new GrantedAuthority[]{});
        Authentication authentication = new TestingAuthenticationToken(proUser, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        PersonBean personBean = new PersonBean();
        AccountAttributes accountAttributes = new AccountAttributes();
        proUser.setAccountAttributes(accountAttributes);

        // first rule test
        accountAttributes.setTexasOilRule(RuleSetType.TEXAS.getCode().toString());
        List<SelectItem> selectItemList = personBean.getDotTypesFiltered();
        assertNotNull(selectItemList);
        assertFalse(selectItemList.isEmpty());

        for (SelectItem selectItem : selectItemList) {
            assertFalse(selectItem.getValue().equals(RuleSetType.TEXAS_DOD15_7DAY));
        }

        // second rule test
        accountAttributes.setTexasOilRule(RuleSetType.TEXAS_DOD15_7DAY.getCode().toString());
        selectItemList = personBean.getDotTypesFiltered();
        assertNotNull(selectItemList);
        assertFalse(selectItemList.isEmpty());

        for (SelectItem selectItem : selectItemList) {
            assertFalse(selectItem.getValue().equals(RuleSetType.TEXAS));
        }
    }
}
