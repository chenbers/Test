package com.inthinc.pro.selenium.testSuites;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class TempATest extends WebTest {
    int failFreq = 10;
    public void fail(int freq) {
        int randomNumber = (int)(Math.random() * 100);
        assertTrue((randomNumber>failFreq));
    }
    
    public void primeNumbersAddition() {
        //                          0,1,2,3 ,4, 5, 6, 7
        int [] primes = new int [] {3,5,7,11,13,17,19,23};
        Set<Integer> sums = new HashSet<Integer>();
        //add original primes... necessary to test for duplicates when only ONE set was run
        for(int one: primes) {
            sums.add(one);
            for(int two: primes) {
                sums.add(one+two);
                //itterative approach works fine... obvious candidate for recursion... ?
            }
        }
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
}
