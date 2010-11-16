package com.inthinc.pro.service.impl;

import static mockit.Mockit.tearDownMocks;

import org.junit.After;

/**
 * Parent class for all Service unit tests.
 */
public abstract class BaseUnitTest {

    /**
     * Generic method to reset all mocked classes after each test.
     */
    @After
    public void resetTest() {
        tearDownMocks();
    }
}
