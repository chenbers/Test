package com.inthinc.pro.backing;

import java.util.Iterator;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.reports.ReportRenderer;

public class BaseReportBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(BaseReportBean.class);
    
    private boolean mainMenu;
    private AccountDAO accountDAO;
    private String emailAddress;
    private ReportRenderer reportRenderer;
   
    
    public BaseReportBean(){
        
    }

    protected String checkForRequestMap()
    {
        String searchFor = "";
        mainMenu = false;

        Map m = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Iterator imap = m.entrySet().iterator();

        //if there is a map, the request came from the
        //  main menu search, so grab it
        while (imap.hasNext()) {
            Map.Entry entry = (Map.Entry) imap.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();

            //search parm, either from the search in the main menu or
            //  one from the report
            if ( key.equalsIgnoreCase("searchFor")  ) {              
                searchFor = value;
                mainMenu = true;
            }
        }
        
        return searchFor;
    }
    
    protected Integer floatToInteger(float value)
    {       
        Float fTmp = new Float(value*10.0);             
        return fTmp.intValue() ;
    }   
    
    protected String formatPhone(String incoming) 
    {
        return  "(" + 
            incoming.substring(0,3) +
                ") " +
            incoming.substring(3,6) +
                "-" +
            incoming.substring(6,10);
    }

    public boolean isMainMenu()
    {
        return mainMenu;
    }

    public void setMainMenu(boolean mainMenu)
    {
        this.mainMenu = mainMenu;
    }

    public void setAccountDAO(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    public AccountDAO getAccountDAO()
    {
        return accountDAO;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
    }
}
