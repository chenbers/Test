package com.inthinc.pro.service.validation.util;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;
import org.junit.Assert;
import org.junit.Test;

import com.inthinc.pro.service.exceptions.ForbiddenException;
import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.params.IFTAReportsParamsBean;

public class ViolationToExceptionMapperTest extends BaseUnitTest {
    private static final String BAD_REQUEST_MSG = "BAD_REQUEST#";
    private static final String FORBIDDEN_MSG = "FORBIDDEN#";
    private static final String NOT_FOUND_MSG = "NOT_FOUND#";
    private static final String MESSAGE = "User message";
    
    private ViolationToExceptionMapper mapperSUT = new ViolationToExceptionMapper();
    @Mocked 
    private ConstraintViolation<IFTAReportsParamsBean> violationBadRequestMock;
    @Mocked 
    private ConstraintViolation<IFTAReportsParamsBean> violationForbiddenMock;
    @Mocked 
    private ConstraintViolation<IFTAReportsParamsBean> violationNotFoundMock;
    
    @Test(expected=BadRequestException.class)
    public void raiseExceptionIfConstraintViolatedTest() {
        Set<ConstraintViolation<IFTAReportsParamsBean>> violations = 
            new HashSet<ConstraintViolation<IFTAReportsParamsBean>>();
        
        new NonStrictExpectations() {
            {
                violationBadRequestMock.getMessage(); returns(BAD_REQUEST_MSG + MESSAGE);
                violationForbiddenMock.getMessage(); returns(FORBIDDEN_MSG + MESSAGE);
                violationNotFoundMock.getMessage(); returns(NOT_FOUND_MSG + MESSAGE);
            }
        };

        // if no violations, nothing to raise
        mapperSUT.raiseExceptionIfConstraintViolated(violations);
        
        // check bad request
        violations.add(violationBadRequestMock);
        try {
            mapperSUT.raiseExceptionIfConstraintViolated(violations);
            Assert.fail("BadRequestException expected");
        } catch(BadRequestException e) {
            Assert.assertEquals(MESSAGE, e.getMessage());
        }

        // check forbidden
        violations.clear();
        violations.add(violationForbiddenMock);
        try {
            mapperSUT.raiseExceptionIfConstraintViolated(violations);
            Assert.fail("ForbiddenException expected");
        } catch(ForbiddenException e) {
            Assert.assertEquals(MESSAGE, e.getMessage());
        }

        // check bad request
        violations.clear();
        violations.add(violationNotFoundMock);
        try {
            mapperSUT.raiseExceptionIfConstraintViolated(violations);
            Assert.fail("NotFoundException expected");
        } catch(NotFoundException e) {
            Assert.assertEquals(MESSAGE, e.getMessage());
        }
        
        // check multiple violations
        violations.add(violationForbiddenMock);
        violations.add(violationBadRequestMock);
        mapperSUT.raiseExceptionIfConstraintViolated(violations);
        Assert.fail("BadRequestException expected");
    }
    
}
