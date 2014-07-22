package com.inthinc.pro.util;

import junit.framework.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Test case for {@link BeanUtil}.
 */
public class BeanUtilTest {

    @Test
    public void testMakeNotNull() {
        TestClass testClass = new TestClass();
        TestClass newTestClass = BeanUtil.makeNullValuesNotNull(testClass);

        Assert.assertEquals("dontChangeMe", newTestClass.getDontChangeMe());
        Assert.assertEquals(Integer.valueOf(0), newTestClass.getIntVal());
        Assert.assertEquals(BigDecimal.valueOf(0), newTestClass.getNumbVal());
        Assert.assertEquals("", newTestClass.getStrVal());
    }

    @Test
    public void testMakeNotNullList() {
        TestClass testClass1 = new TestClass();
        TestClass testClass2 = new TestClass();
        TestClass testClass3 = new TestClass();
        List<TestClass> testClassList = Arrays.asList(testClass1, testClass2, testClass3);

        List<TestClass> newTestClassList = BeanUtil.makeListValuesNotNull(testClassList);

        for (TestClass newTestClass : newTestClassList) {
            Assert.assertEquals("dontChangeMe", newTestClass.getDontChangeMe());
            Assert.assertEquals(Integer.valueOf(0), newTestClass.getIntVal());
            Assert.assertEquals(BigDecimal.valueOf(0), newTestClass.getNumbVal());
            Assert.assertEquals("", newTestClass.getStrVal());
        }
    }
}

class TestClass {
    private Integer intVal;
    private String strVal;
    private Number numbVal;
    private String dontChangeMe = "dontChangeMe";

    public Integer getIntVal() {
        return intVal;
    }

    public void setIntVal(Integer intVal) {
        this.intVal = intVal;
    }

    public String getStrVal() {
        return strVal;
    }

    public void setStrVal(String strVal) {
        this.strVal = strVal;
    }

    public Number getNumbVal() {
        return numbVal;
    }

    public void setNumbVal(Number numbVal) {
        this.numbVal = numbVal;
    }

    public String getDontChangeMe() {
        return dontChangeMe;
    }

    public void setDontChangeMe(String dontChangeMe) {
        this.dontChangeMe = dontChangeMe;
    }
}