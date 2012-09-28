package com.inthinc.pro.service.validation.rules;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.hibernate.validator.engine.ConstraintValidatorContextImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.service.adapters.GroupDAOAdapter;
import com.inthinc.pro.service.impl.BaseUnitTest;

public class GroupIDsValidatorTest extends BaseUnitTest {

    // Group IDs for each possible case
    private final String GROUP_ID_GOOD = "1";
    private final String GROUP_ID_NEGATIVE = "-1";
    private final String GROUP_ID_NOT_IN_HIER = "2";
    private final String GROUP_ID_BAD = "3AA";

    private final List<String> groupIdList = new ArrayList<String>();

    private GroupIDsValidator validatorSUT = new GroupIDsValidator();

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
                groupAdapterMock.findByID(Integer.parseInt(GROUP_ID_NOT_IN_HIER));
                result = new AccessDeniedException(null);
                groupAdapterMock.findByID(Integer.parseInt(GROUP_ID_GOOD));
                returns(new Group());
            }
        };

        // Negative testing
        Assert.assertFalse(validatorSUT.isValid(groupIdList, contextMock));

        groupIdList.add(GROUP_ID_NEGATIVE);
        Assert.assertFalse(validatorSUT.isValid(groupIdList, contextMock));

        groupIdList.clear();
        groupIdList.add(GROUP_ID_BAD);
        Assert.assertFalse(validatorSUT.isValid(groupIdList, contextMock));

        groupIdList.clear();
        groupIdList.add(GROUP_ID_NOT_IN_HIER);
        Assert.assertFalse(validatorSUT.isValid(groupIdList, contextMock));

        // positive testing
        groupIdList.clear();
        groupIdList.add(GROUP_ID_GOOD);
        Assert.assertTrue(validatorSUT.isValid(groupIdList, contextMock));

        // Check for completeness
        new Verifications() {
            {
                groupAdapterMock.findByID(anyInt);
                times = 2;
                contextMock.buildConstraintViolationWithTemplate(anyString);
                times = 4;
                builderMock.addConstraintViolation();
                times = 4;
            }
        };
    }
}
