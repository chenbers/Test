package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.CheckBox;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextScoreTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.TAE.DurationEnumeration;
import com.inthinc.pro.selenium.pageEnums.TrendExpansionEnum;

public class PageExecutiveTrendExpansion extends ExecutiveExpansionBar {
    

    public PageExecutiveTrendExpansion() {
        // TODO Auto-generated constructor stub
    }

    public class TrendExpansionLinks extends ExpansionBarLinks {

        public TextTableLink entryDivisionTeam() {
            return new TextTableLink(TrendExpansionEnum.ENTRY_GROUP_NAME);
        }

        public TextLink duration(DurationEnumeration duration) {
            return new TextLink(TrendExpansionEnum.TIME_SELECTOR, duration);
        }

        public TextLink sortDivisionTeam() {
            return new TextLink(TrendExpansionEnum.HEADER_DIVISION_TEAM);
        }

        public TextLink sortScore() {
            return new TextLink(TrendExpansionEnum.HEADER_SCORE);
        }

        public TextLink sortCrashPerMil() {
            return new TextLink(TrendExpansionEnum.HEADER_CRASH_COUNT);
        }
    }

    public class TrendExpansionTexts extends ExpansionBarTexts {

        public Text title() {
            return new Text(TrendExpansionEnum.TITLE);
        }

        public Text averageGroupName() {
            return new Text(TrendExpansionEnum.AVERAGE_GROUP_NAME);
        }

        public Text averageCrashPerMillion() {
            return new Text(TrendExpansionEnum.AVERAGE_CRASH_COUNT);
        }

        public Text entryCrashPerMillion() {
            return new Text(TrendExpansionEnum.ENTRY_CRASH_COUNT);
        }

        public TextScoreTable entryScore() {
            return new TextScoreTable(TrendExpansionEnum.ENTRY_SCORE_BOX);
        }
    }

    public class TrendExpansionTextFields extends ExpansionBarTextFields {}

    public class TrendExpansionButtons extends ExpansionBarButtons {

        public Button restore() {
            return new Button(TrendExpansionEnum.RETURN);
        }

        public Button tools() {
            return new Button(TrendExpansionEnum.TOOLS);
        }

        public Button emailReport() {
            return new Button(TrendExpansionEnum.EMAIL_REPORT);
        }

        public Button exportToPDF() {
            return new Button(TrendExpansionEnum.EXPORT_PDF);
        }
    }

    public class TrendExpansionDropDowns extends ExpansionBarDropDowns {}

    public class TrendExpansionPopUps extends MastheadPopUps {
        
        public TrendExpansionPopUps(){
            super(page, Types.SINGLE, 2);
        }
        
        public Email emailReport(){
            return new Email();
        }
    }
    
    public class TrendExpansionCheckBoxs {
        
        public CheckBox entry(){
            return new CheckBox(TrendExpansionEnum.AVERAGE_CHECKBOX);
        }
        
        public CheckBox average(){
            return new CheckBox(TrendExpansionEnum.AVERAGE_CHECKBOX);
        }
    }

    public class TrendExpansionPager {
        public Paging pageIndex() {
            return new Paging();
        }
    }

    public TrendExpansionLinks _link() {
        return new TrendExpansionLinks();
    }

    public TrendExpansionTexts _text() {
        return new TrendExpansionTexts();
    }

    public TrendExpansionButtons _button() {
        return new TrendExpansionButtons();
    }

    public TrendExpansionTextFields _textField() {
        return new TrendExpansionTextFields();
    }

    public TrendExpansionDropDowns _dropDown() {
        return new TrendExpansionDropDowns();
    }

    public TrendExpansionPopUps _popUp() {
        return new TrendExpansionPopUps();
    }

    public TrendExpansionPager _page() {
        return new TrendExpansionPager();
    }
    
    public TrendExpansionCheckBoxs _checkBox(){
        return new TrendExpansionCheckBoxs();
    }

    @Override
    public SeleniumEnums setUrl() {
        return TrendExpansionEnum.DEFAULT_URL;
    }

}
