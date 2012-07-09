package com.inthinc.pro.automation.elements;

import java.util.Iterator;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.Assert;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.Validate;
import com.inthinc.pro.automation.elements.ElementInterface.TableBased;
import com.inthinc.pro.automation.elements.ElementInterface.TextBased;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.interfaces.TextEnum;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.utils.MasterTest;

public class TextTable implements TableBased<TextBased> {

    protected SeleniumEnumWrapper myEnum;

    public TextTable(SeleniumEnums anEnum, Object ...objects) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.makeReplacements(objects);
    }


    public TextTable(SeleniumEnums anEnum, String page, TextEnum column) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.replaceWord(page);
        myEnum.replaceOldWithNew("*column*", column.getText());
    }


    @Override
    public TableIterator<TextBased> iterator() {
        return new TableIterator<TextBased>(this);
    }

    @Override
    public Text row(int rowNumber) {
        return new Text(myEnum, rowNumber);
    }

    @Override
    public TextBased row(IndexEnum indexByName) {
        return row(indexByName.getIndex());
    }
    
    public boolean isAscending(){
        return isSorted(true);
    }
    
    public boolean isDescending(){
        return isSorted(false);
    }
    
    @Validate(englishName = "sorted in ascending order")
    public boolean validateAscending(){
        MasterTest test = new MasterTest(){};
        return test.validateTrue(isSorted(true), myEnum + " is not sorted ascending");
    }
    
    @Validate(englishName = "sorted in descending order")
    public boolean validateDescending(){
        MasterTest test = new MasterTest(){};
        return test.validateTrue(isSorted(false), myEnum + " is not sorted descending");
    }
    
    @Assert(englishName = "sorted in ascending order")
    public boolean assertAscending(){
        MasterTest test = new MasterTest(){};
        return test.assertTrue(isSorted(true), myEnum + " is not sorted ascending");
    }
    
    @Assert(englishName = "sorted in descending order")
    public boolean assertDescending(){
        MasterTest test = new MasterTest(){};
        return test.assertTrue(isSorted(false), myEnum + " is not sorted descending");
    }
    

    private boolean isSorted(boolean ascending){
        Iterator<TextBased> itr = iterator();
        
        if (!itr.hasNext()){
            return true;
        }
        
        String t1 = itr.next().getText();
        boolean isCalendar = false;
        AutomationCalendar time1 = null;
        try {
            time1 = new AutomationCalendar(t1);
            isCalendar = true;
        } catch (IllegalArgumentException e){
            isCalendar = false;
        }
        while (itr.hasNext()){
            String t2 = itr.next().getText();
            
            if (isCalendar && time1 != null){
                if ((time1.compareTo(t2) > 0 == ascending)){
                    return false;
                }
            } else if ((t1.compareTo(t2) > 0) == ascending){
                return false;
            }
            t1 = t2;
        }
        return true;
    }
}
