package com.inthinc.pro.automation.elements;

import java.util.Iterator;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.Assert;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.Validate;
import com.inthinc.pro.automation.elements.ElementInterface.CheckBoxTableBased;
import com.inthinc.pro.automation.elements.ElementInterface.Checkable;
import com.inthinc.pro.automation.enums.SeleniumEnumWrapper;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.MasterTest;

public class CheckBoxTable implements CheckBoxTableBased<Checkable> {

    private final SeleniumEnumWrapper myEnum;
    
    public CheckBoxTable(SeleniumEnums anEnum, Object ...objects) {
        myEnum = new SeleniumEnumWrapper(anEnum);
        myEnum.makeReplacements(objects);
    }

    @Override
    public TableIterator<Checkable> iterator() {
        return new TableIterator<Checkable>(new CheckBoxTable(myEnum));
    }


    @SuppressWarnings("unchecked")
	@Override
    public Checkable row(int rowNumber) {
        return new CheckBox(myEnum, rowNumber);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public Checkable row(IndexEnum index) {
        return row(index.getIndex());
    }

	@Override
    public void checkAll(){
        changeAll(true);
    }
    
    private void changeAll(boolean check){
        Iterator<Checkable> itr = iterator();
        while (itr.hasNext()){
            if (check){
                itr.next().check();
            } else {
                itr.next().uncheck();
            }
        }
    }

	@Override
    public void clickAll(){
        Iterator<Checkable> itr = iterator();
        while (itr.hasNext()){
            itr.next().click();
        }
    }

	@Override
    public void unCheckAll(){
        changeAll(false);
    }
    
    @Validate(englishName="are all checked")
	@Override
    public boolean validateAllChecked(){
        MasterTest test = new MasterTest(){};
        return test.validateTrue(allChecked(), myEnum + " some of the boxes are unchecked");
    }
    
    @Validate(englishName="are all unchecked")
	@Override
    public boolean validateAllUnChecked(){
        MasterTest test = new MasterTest(){};
        return test.validateTrue(allUnchecked(), myEnum + " some of the boxes are checked");
    }
    
    @Assert(englishName="are all checked")
	@Override
    public boolean assertAllChecked(){
        MasterTest test = new MasterTest(){};
        return test.validateTrue(allChecked(), myEnum + " some of the boxes are unchecked");
    }
    
    @Assert(englishName="are all unchecked")
	@Override
    public boolean assertAllUnChecked(){
        MasterTest test = new MasterTest(){};
        return test.validateTrue(allUnchecked(), myEnum + " some of the boxes are checked");
    }

	@Override
    public boolean allUnchecked(){
        return areChecked(false);
    }

	@Override
    public boolean allChecked(){
        return areChecked(true);
    }
    
    private boolean areChecked(boolean checked){
        Iterator<Checkable> itr = iterator();
        if (!itr.hasNext()){
            return true;
        }
        
        while (itr.hasNext()){
            if (itr.next().isChecked() != checked){
                return false;
            }
        }
        return true;
    }

}
