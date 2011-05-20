package com.inthinc.pro.selenium.testSuites;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TempCTest extends WebTest {
    int failFreq = 10;
    public void fail(int freq) {
        int randomNumber = (int)(Math.random() * 100);
        assertTrue((randomNumber>failFreq));
    }
    
    @Test
    public void a(){
        fail(failFreq);
    }
    
    @Test
    public void b(){
        fail(failFreq);
    }
    @Test
    public void c(){
        fail(failFreq);
    }
    @Test
    public void d(){
        fail(failFreq);
    }
    @Test
    public void e(){
        fail(failFreq);
    }
    @Test
    public void f(){
        fail(failFreq);
    }
    @Test
    public void g(){
        fail(failFreq);
    }
    @Test
    public void h(){
        fail(failFreq);
    }
}
