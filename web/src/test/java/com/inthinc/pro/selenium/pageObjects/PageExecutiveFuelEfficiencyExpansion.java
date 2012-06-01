package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.FuelEfficiencyExpansionEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.DurationEnumeration;

public class PageExecutiveFuelEfficiencyExpansion extends ExecutiveExpansionBar {

    public PageExecutiveFuelEfficiencyExpansion() {
        // TODO Auto-generated constructor stub
    }
    
    
    public class FuelEfficiencyExpansionLinks extends ExpansionBarLinks{
        
        public TextLink duration(DurationEnumeration duration){
            return new TextLink(FuelEfficiencyExpansionEnum.DURATION);
        }
        
        public TextTableLink entryGroup(){
            return new TextTableLink(FuelEfficiencyExpansionEnum.ENTRY_DIVISION_TEAM);
        }
        
        public TextLink sortGroup(){
            return new TextLink(FuelEfficiencyExpansionEnum.SORT_DIVISION_TEAM);
        }
        
        public TextLink sortLight(){
            return new TextLink(FuelEfficiencyExpansionEnum.SORT_LIGHT);
        }
        
        public TextLink sortMedium(){
            return new TextLink(FuelEfficiencyExpansionEnum.SORT_MEDIUM);
        }
        
        public TextLink sortHeavy(){
            return new TextLink(FuelEfficiencyExpansionEnum.SORT_HEAVY);
        }
    }
    public class FuelEfficiencyExpansionTexts extends ExpansionBarTexts{
        
        public Text title(){
            return new Text(FuelEfficiencyExpansionEnum.TITLE);
        }
        
        public TextTable entryLight(){
            return new TextTable(FuelEfficiencyExpansionEnum.ENTRY_LIGHT);
        }
        
        public TextTable entryMedium(){
            return new TextTable(FuelEfficiencyExpansionEnum.ENTRY_MEDIUM);
        }
        
        public TextTable entryHeavy(){
            return new TextTable(FuelEfficiencyExpansionEnum.ENTRY_HEAVY);
        }
    }
    public class FuelEfficiencyExpansionTextFields extends ExpansionBarTextFields{}
    public class FuelEfficiencyExpansionButtons extends ExpansionBarButtons{
        
        public Button tools(){
            return new Button(FuelEfficiencyExpansionEnum.TOOLS);
        }
        
        public Button emailReport(){
            return new Button(FuelEfficiencyExpansionEnum.EMAIL_REPORT);
        }
        
        public Button exportPDF(){
            return new Button(FuelEfficiencyExpansionEnum.EXPORT_PDF);
        }
        
        public Button restore(){
            return new Button(FuelEfficiencyExpansionEnum.RESTORE);
        }
    }
    public class FuelEfficiencyExpansionDropDowns extends ExpansionBarDropDowns{}
    public class FuelEfficiencyExpansionPopUps extends MastheadPopUps{
        public FuelEfficiencyExpansionPopUps(){
            super(page, Types.REPORT, 2);
        }
        
        public Email emailReport(){
            return new Email();
        }
    }
    
    
    public class FuelEfficiencyExpansionPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    
    public FuelEfficiencyExpansionPager _page(){
        return new FuelEfficiencyExpansionPager();
    }
    
    public FuelEfficiencyExpansionLinks _link(){
        return new FuelEfficiencyExpansionLinks();
    }
    
    public FuelEfficiencyExpansionTexts _text(){
        return new FuelEfficiencyExpansionTexts();
    }
        
    public FuelEfficiencyExpansionButtons _button(){
        return new FuelEfficiencyExpansionButtons();
    }
    
    public FuelEfficiencyExpansionTextFields _textField(){
        return new FuelEfficiencyExpansionTextFields();
    }
    
    public FuelEfficiencyExpansionDropDowns _dropDown(){
        return new FuelEfficiencyExpansionDropDowns();
    }
    
    public FuelEfficiencyExpansionPopUps _popUp(){
        return new FuelEfficiencyExpansionPopUps();
    }

    @Override
    public SeleniumEnums setUrl() {
        return FuelEfficiencyExpansionEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().duration(DurationEnumeration.THIRTY_DAYS).isPresent();
    }
    
    

}
