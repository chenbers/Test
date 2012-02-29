package com.inthinc.pro.automation.test;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.StackToString;

public class BrowserTest extends Test{

    protected final Logger logger = Logger.getLogger(BrowserTest.class);
    
    private SeleniumEnumWrapper webVersionID;

    public BrowserTest(SeleniumEnums version){
    	webVersionID = new SeleniumEnumWrapper(version);
    }

    
    @Override
    public void after() {
        super.after();
        if (!skip) {
            try {
                setBuildNumber(getSelenium().getText(webVersionID));
            } catch (Exception e) {
                logger.fatal(StackToString.toString(e));
            }finally{
                    killSelenium();
            }
        } else {
            logger.info(" skip ");
        }
    }

    @Override
    public void before() {
        super.before();
        try {
            super.getSelenium();
            setErrorCatcher(getSelenium().getErrorCatcher());
        } catch (Exception e) {
            print(e);
            skip = true;
            throw new NullPointerException();
        }
    }
}
