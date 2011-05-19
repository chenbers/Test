package com.inthinc.pro.selenium.testSuites;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TempGTest extends WebTest {
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
    @Test
    public void i(){
        fail(failFreq);
    }
    @Test
    public void j(){
        fail(failFreq);
    }
    @Test
    public void k(){
        fail(failFreq);
    }
    @Test
    public void l(){
        fail(failFreq);
    }
    @Test
    public void m(){
        fail(failFreq);
    }
    @Test
    public void n(){
        fail(failFreq);
    }
    @Test
    public void o(){
        fail(failFreq);
    }
    @Test
    public void p(){
        fail(failFreq);
    }
    @Test
    public void q(){
        fail(failFreq);
    }
    
    @Test
    public void r(){
        fail(failFreq);
    }
    @Test
    public void s(){
        fail(failFreq);
    }
    @Test
    public void t(){
        fail(failFreq);
    }
    @Test
    public void u(){
        fail(failFreq);
    }
    @Test
    public void v(){
        fail(failFreq);
    }
    @Test
    public void w(){
        fail(failFreq);
    }
    @Test
    public void x(){
        fail(failFreq);
    }
    @Test
    public void y(){
        fail(failFreq);
    }
    @Test
    public void z(){
        fail(failFreq);
    }
    
}
