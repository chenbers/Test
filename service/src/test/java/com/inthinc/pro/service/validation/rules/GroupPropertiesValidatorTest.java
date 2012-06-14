package com.inthinc.pro.service.validation.rules;

import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.ws.rs.core.UriInfo;

import mockit.Deencapsulation;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.hibernate.validator.engine.ConstraintValidatorContextImpl;
import org.junit.Assert;
import org.junit.Test;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.service.adapters.GroupDAOAdapter;
import com.inthinc.pro.service.impl.BaseUnitTest;

public class GroupPropertiesValidatorTest  extends BaseUnitTest {
// Group IDs for each possible case
//private final String GROUP_ID_GOOD = "1";
//private final String GROUP_ID_NEGATIVE = "-1";
//private final String GROUP_ID_NOT_IN_HIER = "2";
//private final String GROUP_ID_BAD = "3AA";
private final Integer managerIDInOtherAccount = 1032; // QA - this person is in account 1
private final Integer nonExistentParentID = 4;
private final Integer otherAccount = 99;
//private final List<String> groupIdList = new ArrayList<String>();
private final Group group = new Group();
private final Person manager = new Person();
private final UriInfo uriInfo = null;

private GroupPropertiesValidator validatorSUT = new GroupPropertiesValidator();

@Mocked
private ConstraintValidatorContextImpl contextMock;
@Mocked
private ConstraintViolationBuilder builderMock;
@Mocked
private GroupDAOAdapter groupAdapterMock;

@Test
public void testIsValid() {
    // Use mocked adapter
    validatorSUT.setGroupDAOAdapter(groupAdapterMock);
    group.setAccountID(2);
    group.setManagerID(managerIDInOtherAccount);
    group.setParentID(nonExistentParentID);
    manager.setPersonID(managerIDInOtherAccount);
    manager.setStatus(com.inthinc.pro.model.Status.ACTIVE);
    Deencapsulation.setField(manager, "acctID", otherAccount);
    new NonStrictExpectations() {
        {
            contextMock.getDefaultConstraintMessageTemplate();
            returns("{message}");
            contextMock.disableDefaultConstraintViolation();
            returns(null);
            contextMock.buildConstraintViolationWithTemplate(anyString);
            returns(builderMock);
            builderMock.addConstraintViolation();
            returns(null);
            // expected behavior of the adapter
            groupAdapterMock.create(group);result = group;
            groupAdapterMock.getPersonByID(group.getManagerID());result = manager;
        }
    };

    
    // Negative testing
    Assert.assertFalse(validatorSUT.isValid(group, contextMock));

//    groupIdList.add(GROUP_ID_NEGATIVE);
//    Assert.assertFalse(validatorSUT.isValid(groupIdList, contextMock));
//
//    groupIdList.clear();
//    groupIdList.add(GROUP_ID_BAD);
//    Assert.assertFalse(validatorSUT.isValid(groupIdList, contextMock));
//
//    groupIdList.clear();
//    groupIdList.add(GROUP_ID_NOT_IN_HIER);
//    Assert.assertFalse(validatorSUT.isValid(groupIdList, contextMock));
//
//    // positive testing
//    groupIdList.clear();
//    groupIdList.add(GROUP_ID_GOOD);
//    Assert.assertTrue(validatorSUT.isValid(groupIdList, contextMock));

    // Check for completeness
    new Verifications() {
        {
            contextMock.buildConstraintViolationWithTemplate(anyString);
            times = 1;
            builderMock.addConstraintViolation();
            times = 1;
        }
    };
}

}
