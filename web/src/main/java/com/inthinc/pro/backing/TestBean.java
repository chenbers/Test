package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

public class TestBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(TestBean.class);
    private String testText;
    private String testNumber;
    
    public TestBean()
    {
        logger.debug("Test Bean constructor");
    }
    
    public String getTestNumber() {
		
    	if(testNumber == null)
    	{
    		setTestNumber("4.5");
    	}
    	
    	logger.debug("Test Bean - returning testNumber");
    	return testNumber;
	}
	public void setTestNumber(String testNumber) {
		this.testNumber = testNumber;
	}
    public String errorAction()
    {
        logger.info("Test Bean errorAction");
        setTestText("Error Action was called");
        getErrorBean().setErrorMessage("This is a test error.");
        
        return "go_test";
    }
    public String getTestText()
    {
        if (testText == null)
        {
            setTestText("This is a test......");
        }
        return testText;
    }
    public void setTestText(String testText)
    {
        this.testText = testText;
    }

}
