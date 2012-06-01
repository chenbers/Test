package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextScoreTable;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.OverallExpansionEnum;
import com.inthinc.pro.selenium.pageEnums.TAE.DurationEnumeration;

public class PageExecutiveOverallExpansion extends ExecutiveExpansionBar {
    
    public class OverallExpansionButtons extends ExpansionBarButtons{
        
        public TextButton emailReport(){
            return new TextButton(OverallExpansionEnum.EMAIL_REPORT);
        }
        
        public TextButton exportToPDF(){
            return new TextButton(OverallExpansionEnum.EXPORT_TO_PDF);
        }
        
        public Button restore(){
            return new Button(OverallExpansionEnum.RESTORE);
        }
        
        public Button tools(){
            return new Button(OverallExpansionEnum.TOOLS);
        }
    }

    
    public class OverallExpansionDropDowns extends ExpansionBarDropDowns{}
    public class OverallExpansionLinks extends ExpansionBarLinks{
        
        public TextLink drivingStyle(){
            return new TextLink(OverallExpansionEnum.LABEL_STYLE);
        }
        
        public TextLink duration(DurationEnumeration duration){
            return new TextLink(OverallExpansionEnum.DURATION, duration);
        }
        
        public TextLink seatbelt(){
            return new TextLink(OverallExpansionEnum.LABEL_SEATBELT);
        }
        
        public TextLink speeding(){
            return new TextLink(OverallExpansionEnum.LABEL_SPEEDING);
        }
    }
    public class OverallExpansionPager{
        public Paging pageIndex(){
            return new Paging();
        }
    }
    public class OverallExpansionPopUps extends MastheadPopUps{
        
        public OverallExpansionPopUps(){
            super(page, Types.SINGLE, 2);
        }
        
        public Email emailReport(){
            return new Email();
        }
        
    }
    public class OverallExpansionTextFields extends ExpansionBarTextFields{}
    public class OverallExpansionTexts extends ExpansionBarTexts{
        
        public Text labelOverallScoreBox(){
            return new Text(OverallExpansionEnum.LABEL_SCORE);
        }
        
        public TextScoreTable overallScore(){
            return new TextScoreTable(OverallExpansionEnum.OVERALL_SCORE);
        }
        
        public Text title(){
            return new Text(OverallExpansionEnum.TITLE);
        }
    }
    
    
    public PageExecutiveOverallExpansion() {
        // TODO Auto-generated constructor stub
    }
    
    public OverallExpansionButtons _button(){
        return new OverallExpansionButtons();
    }
    
    public OverallExpansionDropDowns _dropDown(){
        return new OverallExpansionDropDowns();
    }
        
    public OverallExpansionLinks _link(){
        return new OverallExpansionLinks();
    }
    
    public OverallExpansionPager _page(){
        return new OverallExpansionPager();
    }
    
    public OverallExpansionPopUps _popUp(){
        return new OverallExpansionPopUps();
    }
    
    public OverallExpansionTexts _text(){
        return new OverallExpansionTexts();
    }
    
    public OverallExpansionTextFields _textField(){
        return new OverallExpansionTextFields();
    }

    @Override
    public SeleniumEnums setUrl() {
        return OverallExpansionEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().duration(DurationEnumeration.THIRTY_DAYS).isPresent();
    }
}
