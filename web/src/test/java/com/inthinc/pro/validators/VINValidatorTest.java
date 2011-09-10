package com.inthinc.pro.validators;

//import javax.faces.application.FacesMessage;
//import javax.faces.component.UIComponent;
//import javax.faces.context.FacesContext;
//import javax.faces.validator.ValidatorException;
//
//import mockit.Expectations;
//import mockit.Mock;
//import mockit.MockUp;
//import mockit.Mocked;
//
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.springframework.test.AssertThrows;
//
//import com.sun.faces.context.FacesContextImpl;

public class VINValidatorTest {
//    @Mocked
//    FacesContext mockFacesContext;
//    VINValidator vinValidator = new VINValidator();
//    @Mocked
//    UIComponent mockComponent;
//
//    @Test
//    public void validate_invalidCharactersInVIN_throwsException() {
//        final String INVALID_REGEX_VIN = "!@#$%^&*()!@#$%^&";
//        new AssertThrows(ValidatorException.class) {
//
//            @Override
//            public void test() throws Exception {
//                vinValidator.validate(mockFacesContext, mockComponent, INVALID_REGEX_VIN);
//            }
//        }.runTest();
//    }
//
//    @Test
//    public void validate_validVIN_noMessageNoException() {
//        // Record phase: expectations on mocks are recorded;
//        new Expectations() {
//            {
//                mockFacesContext.addMessage(anyString, (FacesMessage) any);
//                times = 0;
//            }
//        };
//        String VALID_VIN = "5TEHN72NX4Z421882";// 17 alphanumeric characters
//        vinValidator = new VINValidator();
//        vinValidator.validate(mockFacesContext, mockComponent, VALID_VIN);
//        // Verify phase: expectations on mocks are verified; empty if there is nothing to verify.
//        // no need to verify since strict
//        // noMessage: expectation was set that addMessage must NOT happen, and
//        // noException: test will be in error if validate(...) throws an exception
//
//    }
//
//    @Ignore
//    @Test
//    public void validate_tooLong_errorMessage() {
//        // Record phase: expectations on mocks are recorded; empty if there is nothing to record.
//        new MockUp<FacesContextImpl>() {
//            @SuppressWarnings("unused")
//            @Mock(invocations = 1)
//            public void addMessage(String clientId, FacesMessage message) {
//
//                Assert.assertEquals(FacesMessage.SEVERITY_ERROR, message.getSeverity());
//                Assert.assertNotNull(message);
//            }
//        };
//
//        // Replay phase: invocations on mocks are "replayed"; here the code under test is exercised.
//        String VALID_VIN = "5TEHN72NX4Z421882";// 17 alphanumeric characters
//        vinValidator.validate(new FacesContextImpl(), mockComponent, VALID_VIN + "ABC");
//
//    }
//
//    @Test
//    public void validate_tooShort_warnMessage() {
//
//        // Record phase: expectations on mocks are recorded; empty if there is nothing to record.
//        new MockUp<FacesContextImpl>() {
//            @SuppressWarnings("unused")
//            @Mock(invocations = 1)
//            public void addMessage(String clientId, FacesMessage message) {
//
//                Assert.assertEquals(FacesMessage.SEVERITY_WARN, message.getSeverity());
//                Assert.assertNotNull(message);
//            }
//        };
//
//        // Replay phase: invocations on mocks are "replayed"; here the code under test is exercised.
//        String INVALID_LENGTH_VIN = "ABC";
//        vinValidator = new VINValidator();
//        vinValidator.validate(new FacesContextImpl(), mockComponent, INVALID_LENGTH_VIN);
//
//        // Verify phase: expectations on mocks are verified; empty if there is nothing to verify.
//        // expectations already check that addMessage was called exactly once with a WARNING
//    }
}
