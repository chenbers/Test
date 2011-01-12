package com.inthinc.pro.service.validation.rules;

import java.util.Date;

import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import mockit.Expectations;
import mockit.Mocked;

import org.hibernate.validator.engine.ConstraintValidatorContextImpl;
import org.junit.Assert;
import org.junit.Test;

import com.inthinc.pro.service.impl.BaseUnitTest;
import com.inthinc.pro.service.params.IFTAReportsParamsBean;

/**
 * 
 */
public class StartEndDatesValidatorTest extends BaseUnitTest {
    
    private final Date startDate;
    private final Date goodEndDate;
    private final Date badEndDate;

    private StartEndDatesValidator validatorSUT = new StartEndDatesValidator();
    
    @Mocked
    private ConstraintValidatorContextImpl contextMock;
    @Mocked
    private ConstraintViolationBuilder builderMock;
    
    private IFTAReportsParamsBean beanSample = new IFTAReportsParamsBean();
    
    public StartEndDatesValidatorTest() {
        badEndDate = buildDateFromString("20101201");
        startDate  = buildDateFromString("20101215");
        goodEndDate= buildDateFromString("20101231");
    }
    
    @Test
    public void testIsValid() {
        beanSample.setStartDate(startDate);
        beanSample.setEndDate(badEndDate);
        
        new Expectations () {
            {
                contextMock.getDefaultConstraintMessageTemplate(); returns("{message}");
                contextMock.disableDefaultConstraintViolation(); returns(null);
                contextMock.buildConstraintViolationWithTemplate(anyString); returns(builderMock);
                builderMock.addConstraintViolation();returns(null);
            }
        };
        
        Assert.assertFalse(validatorSUT.isValid(beanSample, contextMock));
        
        beanSample.setEndDate(goodEndDate);
        Assert.assertTrue(validatorSUT.isValid(beanSample, contextMock));
        
    }
}
