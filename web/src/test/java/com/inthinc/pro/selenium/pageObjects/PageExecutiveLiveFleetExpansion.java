package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.LiveFleetExpansionEnum;

public class PageExecutiveLiveFleetExpansion extends ExecutiveExpansionBar {
    
    
    
    
    public class LiveFleetExpansionLinks extends ExpansionBarLinks{}
    public class LiveFleetExpansionTexts extends ExpansionBarTexts{
        
        public Text title(){
            return new Text(LiveFleetExpansionEnum.TITLE);
        }
        
        public Text fleetLegendTitle(){
            return new Text(LiveFleetExpansionEnum.LEGEND_TITLE);
        }
        
        public TextTable fleetLegendEntry(){
            return new TextTable(LiveFleetExpansionEnum.LEGEND_ENTRY);
        }
        
    }
    public class LiveFleetExpansionTextFields extends ExpansionBarTextFields{}
    public class LiveFleetExpansionButtons extends ExpansionBarButtons{
        
        public Button refresh(){
            return new Button(LiveFleetExpansionEnum.REFRESH);
        }
        
        public Button restore(){
            return new Button(LiveFleetExpansionEnum.RESTORE);
        }
    }
    public class LiveFleetExpansionDropDowns extends ExpansionBarDropDowns{}
    public class LiveFleetExpansionPopUps extends MastheadPopUps{}
    
    
    
    public LiveFleetExpansionLinks _link(){
        return new LiveFleetExpansionLinks();
    }
    
    public LiveFleetExpansionTexts _text(){
        return new LiveFleetExpansionTexts();
    }
        
    public LiveFleetExpansionButtons _button(){
        return new LiveFleetExpansionButtons();
    }
    
    public LiveFleetExpansionTextFields _textField(){
        return new LiveFleetExpansionTextFields();
    }
    
    public LiveFleetExpansionDropDowns _dropDown(){
        return new LiveFleetExpansionDropDowns();
    }
    
    public LiveFleetExpansionPopUps _popUp(){
        return new LiveFleetExpansionPopUps();
    }

    @Override
    public SeleniumEnums setUrl() {
        return LiveFleetExpansionEnum.DEFAULT_URL;
    }
    
    

}
