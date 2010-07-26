package com.inthinc.pro.backing;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition.VarType;


public class DeviceSettingDefinitionTest {
    @Test
    public void validateStringChoiceSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        List<String> choices = new ArrayList<String>();
        choices.add("one");
        choices.add("two");
        choices.add("three");
        dsd.setChoices(choices);
        assertTrue(dsd.validateValue( "one"));
        assertTrue(dsd.validateValue( "two"));
        assertTrue(dsd.validateValue( "three"));
        assertTrue(!dsd.validateValue( "four"));
        assertTrue(!dsd.validateValue( ""));
        assertTrue(dsd.validateValue( null));
        assertTrue(!dsd.validateValue( "ONE"));
        assertTrue(!dsd.validateValue( "One"));
    }
    @Test
    public void validateIntegerChoiceSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTINTEGER);
        List<String> choices = new ArrayList<String>();
        choices.add("1");
        choices.add("-1");
        choices.add("0");
        choices.add("234");
        dsd.setChoices(choices);
        assertTrue(dsd.validateValue( "1"));
        assertTrue(dsd.validateValue( "-1"));
        assertTrue(dsd.validateValue( "0"));
        assertTrue(dsd.validateValue( "234"));
        assertTrue(!dsd.validateValue( "4352345"));
        assertTrue(dsd.validateValue( null));
        assertTrue(!dsd.validateValue( "afass"));
        assertTrue(!dsd.validateValue( "-2323"));
    }
    @Test
    public void validateDoubleChoiceSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTDOUBLE);
        List<String> choices = new ArrayList<String>();
        choices.add("1.0");
        choices.add("-1.35");
        choices.add("0");
        choices.add("234.5");
        dsd.setChoices(choices);
        assertTrue(dsd.validateValue( "1.0"));
        assertTrue(dsd.validateValue( "-1.35"));
        assertTrue(dsd.validateValue( "0"));
        assertTrue(dsd.validateValue( "234.5"));
        assertTrue(!dsd.validateValue( "4352345.352"));
        assertTrue(dsd.validateValue( null));
        assertTrue(!dsd.validateValue( "afass"));
        assertTrue(!dsd.validateValue( "-2323"));
    }
    @Test
    public void validateBooleanChoiceSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTBOOLEAN);
        List<String> choices = new ArrayList<String>();
        choices.add("1.0");
        choices.add("-1.35");
        choices.add("0");
        choices.add("234.5");
        dsd.setChoices(choices);
        assertTrue(dsd.validateValue( "1.0"));
        assertTrue(dsd.validateValue( "-1.35"));
        assertTrue(dsd.validateValue( "0"));
        assertTrue(dsd.validateValue( "234.5"));
        assertTrue(!dsd.validateValue( "4352345.352"));
        assertTrue(dsd.validateValue( null));
        assertTrue(!dsd.validateValue( "afass"));
        assertTrue(!dsd.validateValue( "-2323"));
    }
    @Test
    public void validateStringnoNoRestraintSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        assertTrue(dsd.validateValue( "one"));
        assertTrue(dsd.validateValue( "o"));
        assertTrue(dsd.validateValue( "three"));
        assertTrue(dsd.validateValue( "four"));
        assertTrue(dsd.validateValue( ""));
        assertTrue(dsd.validateValue( null));
        assertTrue(dsd.validateValue( "ONE"));
        assertTrue(dsd.validateValue( "One"));
        assertTrue(dsd.validateValue( "A really huge long stringdsfjasjasjkfaskjghaksgh;asgkagk"));
    }
    @Test
    public void validateStringRangeMinSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMin("2");
        assertTrue(dsd.validateValue( "one"));
        assertTrue(!dsd.validateValue( "o"));
        assertTrue(dsd.validateValue( "three"));
        assertTrue(dsd.validateValue( "four"));
        assertTrue(!dsd.validateValue( ""));
        assertTrue(dsd.validateValue( null));
        assertTrue(dsd.validateValue( "ONE"));
        assertTrue(dsd.validateValue( "One"));
    }
    @Test
    public void validateStringRangeMaxSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMax("12");
        assertTrue(dsd.validateValue( "one"));
        assertTrue(dsd.validateValue( "o"));
        assertTrue(dsd.validateValue( "three"));
        assertTrue(!dsd.validateValue( "four hundred and seventy five"));
        assertTrue(dsd.validateValue( ""));
        assertTrue(dsd.validateValue( null));
        assertTrue(dsd.validateValue( "ONE"));
        assertTrue(dsd.validateValue( "One"));
    }
    @Test
    public void validateStringRangeMinMaxSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMin("2");
        dsd.setMax("12");
        
        assertTrue(dsd.validateValue( "one"));
        assertTrue(!dsd.validateValue( "o"));
        assertTrue(dsd.validateValue( "three"));
        assertTrue(!dsd.validateValue( "four hundred and seventy five"));
        assertTrue(!dsd.validateValue( ""));
        assertTrue(dsd.validateValue( null));
        assertTrue(dsd.validateValue( "ONE"));
        assertTrue(dsd.validateValue( "One"));
    }
    @Test
    public void validateStringRangeBadMinMaxSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMin("safasf");
        dsd.setMax("asdfasf");
        
        assertTrue(!dsd.validateValue( "one"));
        assertTrue(!dsd.validateValue( "o"));
        assertTrue(!dsd.validateValue( "three"));
        assertTrue(!dsd.validateValue( "four hundred and seventy five"));
        assertTrue(!dsd.validateValue( ""));
        assertTrue(dsd.validateValue( null));
        assertTrue(!dsd.validateValue( "ONE"));
        assertTrue(!dsd.validateValue( "One"));
    }
    @Test
    public void validateIntegerNoRestraintSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTINTEGER);
        
        assertTrue(!dsd.validateValue( "one"));
        assertTrue(dsd.validateValue( "0"));
        assertTrue(dsd.validateValue( "-2"));
        assertTrue(dsd.validateValue( "1"));
        assertTrue(dsd.validateValue( "11"));
        assertTrue(dsd.validateValue( null));
        assertTrue(dsd.validateValue( "12"));
        assertTrue(dsd.validateValue( "13"));
        assertTrue(!dsd.validateValue( "89458456895689564894658945689645"));
        assertTrue(!dsd.validateValue( "11.23344"));
   }

    @Test
    public void validateIntegerRangeMinMaxSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTINTEGER);
        dsd.setMin("2");
        dsd.setMax("12");
        
        assertTrue(!dsd.validateValue( "one"));
        assertTrue(!dsd.validateValue( "0"));
        assertTrue(dsd.validateValue( "2"));
        assertTrue(!dsd.validateValue( "1"));
        assertTrue(dsd.validateValue( "11"));
        assertTrue(dsd.validateValue( null));
        assertTrue(dsd.validateValue( "12"));
        assertTrue(!dsd.validateValue( "13"));
        assertTrue(!dsd.validateValue( "11.23344"));
    }
    @Test
    public void validateIntegerRangeMinSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTINTEGER);
        dsd.setMin("2");
        
        assertTrue(!dsd.validateValue( "one"));
        assertTrue(!dsd.validateValue( "0"));
        assertTrue(dsd.validateValue( "2"));
        assertTrue(!dsd.validateValue( "1"));
        assertTrue(dsd.validateValue( "11"));
        assertTrue(dsd.validateValue( null));
        assertTrue(dsd.validateValue( "12"));
        assertTrue(dsd.validateValue( "13"));
        assertTrue(!dsd.validateValue( "11.23344"));
   }
    @Test
    public void validateIntegerRangeMaxSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTINTEGER);
        dsd.setMax("12");
        
        assertTrue(!dsd.validateValue( "one"));
        assertTrue(dsd.validateValue( "0"));
        assertTrue(dsd.validateValue( "2"));
        assertTrue(dsd.validateValue( "1"));
        assertTrue(dsd.validateValue( "11"));
        assertTrue(dsd.validateValue( null));
        assertTrue(dsd.validateValue( "12"));
        assertTrue(!dsd.validateValue( "13"));
        assertTrue(!dsd.validateValue( "11.23344"));
    }
    @Test
    public void validateDoubleNoRestraintSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTDOUBLE);
        
        assertTrue(!dsd.validateValue( "one"));
        assertTrue(dsd.validateValue( "0"));
        assertTrue(dsd.validateValue( "2.5"));
        assertTrue(dsd.validateValue( "2.45555567"));
        assertTrue(dsd.validateValue( "2.534467"));
        assertTrue(dsd.validateValue( null));
        assertTrue(dsd.validateValue( "12.744444"));
        assertTrue(dsd.validateValue( "12.75"));
        assertTrue(dsd.validateValue( "12.7567785433"));
        assertTrue(dsd.validateValue( "12.76"));
        assertTrue(dsd.validateValue( "27"));
    }
    @Test
    public void validateDoubleRangeMinMaxSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTDOUBLE);
        dsd.setMin("2.5");
        dsd.setMax("12.75");
        
        assertTrue(!dsd.validateValue( "one"));
        assertTrue(!dsd.validateValue( "0"));
        assertTrue(dsd.validateValue( "2.5"));
        assertTrue(!dsd.validateValue( "2.45555567"));
        assertTrue(dsd.validateValue( "2.534467"));
        assertTrue(dsd.validateValue( null));
        assertTrue(dsd.validateValue( "12.744444"));
        assertTrue(dsd.validateValue( "12.75"));
        assertTrue(!dsd.validateValue( "12.7567785433"));
        assertTrue(!dsd.validateValue( "12.76"));
        assertTrue(dsd.validateValue( "11"));
    }
    @Test
    public void validateDoubleRangeMinSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTDOUBLE);
        dsd.setMin("2.5");
        
        assertTrue(!dsd.validateValue( "one"));
        assertTrue(!dsd.validateValue( "0"));
        assertTrue(dsd.validateValue( "2.5"));
        assertTrue(!dsd.validateValue( "2.45555567"));
        assertTrue(dsd.validateValue( "2.534467"));
        assertTrue(dsd.validateValue( null));
        assertTrue(dsd.validateValue( "12.744444"));
        assertTrue(dsd.validateValue( "12.75"));
        assertTrue(dsd.validateValue( "12.7567785433"));
        assertTrue(dsd.validateValue( "12.76"));
        assertTrue(dsd.validateValue( "11"));
    }
    @Test
    public void validateDoubleRangeMaxSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTDOUBLE);
        dsd.setMax("12.75");
        
        assertTrue(!dsd.validateValue( "one"));
        assertTrue(dsd.validateValue( "0"));
        assertTrue(dsd.validateValue( "2.5"));
        assertTrue(dsd.validateValue( "2.45555567"));
        assertTrue(dsd.validateValue( "2.534467"));
        assertTrue(dsd.validateValue( null));
        assertTrue(dsd.validateValue( "12.744444"));
        assertTrue(dsd.validateValue( "12.75"));
        assertTrue(!dsd.validateValue( "12.7567785433"));
        assertTrue(!dsd.validateValue( "12.76"));
        assertTrue(dsd.validateValue( "11"));
    }
    @Test
    public void validateBooleanSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTBOOLEAN);
        
        assertTrue(!dsd.validateValue( "one"));
        assertTrue(dsd.validateValue( "0"));
        assertTrue(dsd.validateValue( "1"));
        assertTrue(dsd.validateValue( "truE"));
        assertTrue(dsd.validateValue( "False"));
        assertTrue(dsd.validateValue( null));
        assertTrue(!dsd.validateValue( "enabled"));
        assertTrue(!dsd.validateValue( "disabled"));
        assertTrue(!dsd.validateValue( "dgsdgsdf"));
        assertTrue(!dsd.validateValue( "12.76"));
    }
    @Test
    public void validateMinString(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMin("2.5");
        
        assertTrue(!dsd.validateValue( "one"));
        
    }
    @Test
    public void validateMaxString(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMax("12.5");
        
        assertTrue(!dsd.validateValue( "one"));
        
    }
    @Test
    public void validateMinEmptyString(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMin("");
        
        assertTrue(dsd.validateValue( "one"));
        
    }
    @Test
    public void validateMaxEmptyString(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMax("");
        
        assertTrue(dsd.validateValue( "one"));
        
    }
    @Test
    public void validateMinInteger(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTINTEGER);
        dsd.setMin("2.5");
        
        assertTrue(!dsd.validateValue( "3"));
        
    }
    @Test
    public void validateMaxInteger(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTINTEGER);
        dsd.setMax("12.5");
        
        assertTrue(!dsd.validateValue( "11"));
    }
    @Test
    public void validateMinDouble(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTDOUBLE);
        dsd.setMin("dgsdgsdg");
        
        assertTrue(!dsd.validateValue( "3.2"));
        
    }
    @Test
    public void validateMaxDouble(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTDOUBLE);
        dsd.setMax("asfsfasdf");
        
        assertTrue(!dsd.validateValue( "11.5"));
    }

}
