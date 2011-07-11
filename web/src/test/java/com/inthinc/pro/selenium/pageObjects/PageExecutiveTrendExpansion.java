package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.selenium.pageEnums.TAE.TimeDuration;
import com.inthinc.pro.selenium.pageEnums.TrendExpansionEnum;

public class PageExecutiveTrendExpansion extends ExecutiveExpansionBar {

    public PageExecutiveTrendExpansion() {
        // TODO Auto-generated constructor stub
    }
    
    
    public class TrendExpansionLinks extends ExpansionBarLinks{
        
        public TextTableLink entryDivisionTeam(){
            return new TextTableLink(TrendExpansionEnum.ENTRY_GROUP_NAME);
        }
        
        public TextLink duration(TimeDuration duration){
            return new TextLink(TrendExpansionEnum.TIME_SELECTOR, duration);
        }
    }
    public class TrendExpansionTexts extends ExpansionBarTexts{
        
        public Text title(){
            return new Text(TrendExpansionEnum.TITLE);
        }
        
        public Text groupAverage(){
            return new Text(TrendExpansionEnum.AVERAGE_GROUP_NAME);
        }
    }
    public class TrendExpansionTextFields extends ExpansionBarTextFields{}
    public class TrendExpansionButtons extends ExpansionBarButtons{
        
        public Button restore(){
            return new Button(TrendExpansionEnum.RETURN);
        }
        
        public Button tools(){
            return new Button(TrendExpansionEnum.TOOLS);
        }
        
        public Button emailReport(){
            return new Button(TrendExpansionEnum.EMAIL_REPORT);
        }
        
        public Button exportToPDF(){
            return new Button(TrendExpansionEnum.EXPORT_PDF);
        }
    }
    public class TrendExpansionDropDowns extends ExpansionBarDropDowns{}
    public class TrendExpansionPopUps extends MastheadPopUps{}
    
    
    public class TrendExpansionPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    
    public TrendExpansionLinks _link(){
        return new TrendExpansionLinks();
    }
    
    public TrendExpansionTexts _text(){
        return new TrendExpansionTexts();
    }
        
    public TrendExpansionButtons _button(){
        return new TrendExpansionButtons();
    }
    
    public TrendExpansionTextFields _textField(){
        return new TrendExpansionTextFields();
    }
    
    public TrendExpansionDropDowns _dropDown(){
        return new TrendExpansionDropDowns();
    }
    
    public TrendExpansionPopUps _popUp(){
        return new TrendExpansionPopUps();
    }
    
    public TrendExpansionPager _page(){
        return new TrendExpansionPager();
    }
    
    
    

}
