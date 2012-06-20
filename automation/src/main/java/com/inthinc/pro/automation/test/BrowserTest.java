package com.inthinc.pro.automation.test;

import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.utils.AutomationStringUtil;

public class BrowserTest extends Test{

    
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
                Log.error(AutomationStringUtil.toString(e));
            }finally{
                    killSelenium();
            }
        } else {
            Log.info(" skip ");
        }
    }

    @Override
    public void before() {
        super.before();
        try {
            super.getSelenium();
            setErrorCatcher(getSelenium().getErrorCatcher());
        } catch (Exception e) {
            Log.error(e);
            skip = true;
            throw new NullPointerException();
        }
    }
}
