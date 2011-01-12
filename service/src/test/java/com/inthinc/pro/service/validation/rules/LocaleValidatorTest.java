package com.inthinc.pro.service.validation.rules;

import java.util.Locale;

import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import mockit.Expectations;
import mockit.Mocked;

import org.hibernate.validator.engine.ConstraintValidatorContextImpl;
import org.junit.Assert;
import org.junit.Test;

import com.inthinc.pro.service.impl.BaseUnitTest;

public class LocaleValidatorTest extends BaseUnitTest {

    private final Locale goodLocale = Locale.CANADA;
    private final Locale badLocale = new Locale("BAD");
    
    private LocaleValidator validatorSUT = new LocaleValidator();
    
    @Mocked
    private ConstraintValidatorContextImpl contextMock;
    @Mocked
    private ConstraintViolationBuilder builderMock;
    
    @Test
    public void testIsValid() {
        
        new Expectations () {
            {
                contextMock.getDefaultConstraintMessageTemplate(); returns("{message}");
                contextMock.disableDefaultConstraintViolation(); returns(null);
                contextMock.buildConstraintViolationWithTemplate(anyString); returns(builderMock);
                builderMock.addConstraintViolation();returns(null);
            }
        };
        
        Assert.assertFalse(validatorSUT.isValid(badLocale, contextMock));
        
        Assert.assertTrue(validatorSUT.isValid(goodLocale, contextMock));
        
    }

}
