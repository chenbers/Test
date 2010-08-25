package com.inthinc.pro.backing;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.mapper.ConfiguratorMapper;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.VarType;

@Ignore
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
        assertTrue(dsd.validate( "one"));
        assertTrue(dsd.validate( "two"));
        assertTrue(dsd.validate( "three"));
        assertTrue(!dsd.validate( "four"));
        assertTrue(!dsd.validate( ""));
        assertTrue(dsd.validate( null));
        assertTrue(!dsd.validate( "ONE"));
        assertTrue(!dsd.validate( "One"));
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
        assertTrue(dsd.validate( "1"));
        assertTrue(dsd.validate( "-1"));
        assertTrue(dsd.validate( "0"));
        assertTrue(dsd.validate( "234"));
        assertTrue(!dsd.validate( "4352345"));
        assertTrue(dsd.validate( null));
        assertTrue(!dsd.validate( "afass"));
        assertTrue(!dsd.validate( "-2323"));
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
        assertTrue(dsd.validate( "1.0"));
        assertTrue(dsd.validate( "-1.35"));
        assertTrue(dsd.validate( "0"));
        assertTrue(dsd.validate( "234.5"));
        assertTrue(!dsd.validate( "4352345.352"));
        assertTrue(dsd.validate( null));
        assertTrue(!dsd.validate( "afass"));
        assertTrue(!dsd.validate( "-2323"));
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
        assertTrue(dsd.validate( "1.0"));
        assertTrue(dsd.validate( "-1.35"));
        assertTrue(dsd.validate( "0"));
        assertTrue(dsd.validate( "234.5"));
        assertTrue(!dsd.validate( "4352345.352"));
        assertTrue(dsd.validate( null));
        assertTrue(!dsd.validate( "afass"));
        assertTrue(!dsd.validate( "-2323"));
    }
    @Test
    public void validateStringnoNoRestraintSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        assertTrue(dsd.validate( "one"));
        assertTrue(dsd.validate( "o"));
        assertTrue(dsd.validate( "three"));
        assertTrue(dsd.validate( "four"));
        assertTrue(dsd.validate( ""));
        assertTrue(dsd.validate( null));
        assertTrue(dsd.validate( "ONE"));
        assertTrue(dsd.validate( "One"));
        assertTrue(dsd.validate( "A really huge long stringdsfjasjasjkfaskjghaksgh;asgkagk"));
    }
    @Test
    public void validateStringRangeMinSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMin("2");
        assertTrue(dsd.validate( "one"));
        assertTrue(!dsd.validate( "o"));
        assertTrue(dsd.validate( "three"));
        assertTrue(dsd.validate( "four"));
        assertTrue(!dsd.validate( ""));
        assertTrue(dsd.validate( null));
        assertTrue(dsd.validate( "ONE"));
        assertTrue(dsd.validate( "One"));
    }
    @Test
    public void validateStringRangeMaxSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMax("12");
        assertTrue(dsd.validate( "one"));
        assertTrue(dsd.validate( "o"));
        assertTrue(dsd.validate( "three"));
        assertTrue(!dsd.validate( "four hundred and seventy five"));
        assertTrue(dsd.validate( ""));
        assertTrue(dsd.validate( null));
        assertTrue(dsd.validate( "ONE"));
        assertTrue(dsd.validate( "One"));
    }
    @Test
    public void validateStringRangeMinMaxSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMin("2");
        dsd.setMax("12");
        
        assertTrue(dsd.validate( "one"));
        assertTrue(!dsd.validate( "o"));
        assertTrue(dsd.validate( "three"));
        assertTrue(!dsd.validate( "four hundred and seventy five"));
        assertTrue(!dsd.validate( ""));
        assertTrue(dsd.validate( null));
        assertTrue(dsd.validate( "ONE"));
        assertTrue(dsd.validate( "One"));
    }
    @Test
    public void validateStringRangeBadMinMaxSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMin("safasf");
        dsd.setMax("asdfasf");
        
        assertTrue(!dsd.validate( "one"));
        assertTrue(!dsd.validate( "o"));
        assertTrue(!dsd.validate( "three"));
        assertTrue(!dsd.validate( "four hundred and seventy five"));
        assertTrue(!dsd.validate( ""));
        assertTrue(dsd.validate( null));
        assertTrue(!dsd.validate( "ONE"));
        assertTrue(!dsd.validate( "One"));
    }
    @Test
    public void validateIntegerNoRestraintSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTINTEGER);
        
        assertTrue(!dsd.validate( "one"));
        assertTrue(dsd.validate( "0"));
        assertTrue(dsd.validate( "-2"));
        assertTrue(dsd.validate( "1"));
        assertTrue(dsd.validate( "11"));
        assertTrue(dsd.validate( null));
        assertTrue(dsd.validate( "12"));
        assertTrue(dsd.validate( "13"));
        assertTrue(!dsd.validate( "89458456895689564894658945689645"));
        assertTrue(!dsd.validate( "11.23344"));
   }

    @Test
    public void validateIntegerRangeMinMaxSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTINTEGER);
        dsd.setMin("2");
        dsd.setMax("12");
        
        assertTrue(!dsd.validate( "one"));
        assertTrue(!dsd.validate( "0"));
        assertTrue(dsd.validate( "2"));
        assertTrue(!dsd.validate( "1"));
        assertTrue(dsd.validate( "11"));
        assertTrue(dsd.validate( null));
        assertTrue(dsd.validate( "12"));
        assertTrue(!dsd.validate( "13"));
        assertTrue(!dsd.validate( "11.23344"));
    }
    @Test
    public void validateIntegerRangeMinSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTINTEGER);
        dsd.setMin("2");
        
        assertTrue(!dsd.validate( "one"));
        assertTrue(!dsd.validate( "0"));
        assertTrue(dsd.validate( "2"));
        assertTrue(!dsd.validate( "1"));
        assertTrue(dsd.validate( "11"));
        assertTrue(dsd.validate( null));
        assertTrue(dsd.validate( "12"));
        assertTrue(dsd.validate( "13"));
        assertTrue(!dsd.validate( "11.23344"));
   }
    @Test
    public void validateIntegerRangeMaxSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTINTEGER);
        dsd.setMax("12");
        
        assertTrue(!dsd.validate( "one"));
        assertTrue(dsd.validate( "0"));
        assertTrue(dsd.validate( "2"));
        assertTrue(dsd.validate( "1"));
        assertTrue(dsd.validate( "11"));
        assertTrue(dsd.validate( null));
        assertTrue(dsd.validate( "12"));
        assertTrue(!dsd.validate( "13"));
        assertTrue(!dsd.validate( "11.23344"));
    }
    @Test
    public void validateDoubleNoRestraintSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTDOUBLE);
        
        assertTrue(!dsd.validate( "one"));
        assertTrue(dsd.validate( "0"));
        assertTrue(dsd.validate( "2.5"));
        assertTrue(dsd.validate( "2.45555567"));
        assertTrue(dsd.validate( "2.534467"));
        assertTrue(dsd.validate( null));
        assertTrue(dsd.validate( "12.744444"));
        assertTrue(dsd.validate( "12.75"));
        assertTrue(dsd.validate( "12.7567785433"));
        assertTrue(dsd.validate( "12.76"));
        assertTrue(dsd.validate( "27"));
    }
    @Test
    public void validateDoubleRangeMinMaxSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTDOUBLE);
        dsd.setMin("2.5");
        dsd.setMax("12.75");
        
        assertTrue(!dsd.validate( "one"));
        assertTrue(!dsd.validate( "0"));
        assertTrue(dsd.validate( "2.5"));
        assertTrue(!dsd.validate( "2.45555567"));
        assertTrue(dsd.validate( "2.534467"));
        assertTrue(dsd.validate( null));
        assertTrue(dsd.validate( "12.744444"));
        assertTrue(dsd.validate( "12.75"));
        assertTrue(!dsd.validate( "12.7567785433"));
        assertTrue(!dsd.validate( "12.76"));
        assertTrue(dsd.validate( "11"));
    }
    @Test
    public void validateDoubleRangeMinSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTDOUBLE);
        dsd.setMin("2.5");
        
        assertTrue(!dsd.validate( "one"));
        assertTrue(!dsd.validate( "0"));
        assertTrue(dsd.validate( "2.5"));
        assertTrue(!dsd.validate( "2.45555567"));
        assertTrue(dsd.validate( "2.534467"));
        assertTrue(dsd.validate( null));
        assertTrue(dsd.validate( "12.744444"));
        assertTrue(dsd.validate( "12.75"));
        assertTrue(dsd.validate( "12.7567785433"));
        assertTrue(dsd.validate( "12.76"));
        assertTrue(dsd.validate( "11"));
    }
    @Test
    public void validateDoubleRangeMaxSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTDOUBLE);
        dsd.setMax("12.75");
        
        assertTrue(!dsd.validate( "one"));
        assertTrue(dsd.validate( "0"));
        assertTrue(dsd.validate( "2.5"));
        assertTrue(dsd.validate( "2.45555567"));
        assertTrue(dsd.validate( "2.534467"));
        assertTrue(dsd.validate( null));
        assertTrue(dsd.validate( "12.744444"));
        assertTrue(dsd.validate( "12.75"));
        assertTrue(!dsd.validate( "12.7567785433"));
        assertTrue(!dsd.validate( "12.76"));
        assertTrue(dsd.validate( "11"));
    }
    @Test
    public void validateBooleanSettingTest(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTBOOLEAN);
        
        assertTrue(!dsd.validate( "one"));
        assertTrue(dsd.validate( "0"));
        assertTrue(dsd.validate( "1"));
        assertTrue(dsd.validate( "truE"));
        assertTrue(dsd.validate( "False"));
        assertTrue(dsd.validate( null));
        assertTrue(!dsd.validate( "enabled"));
        assertTrue(!dsd.validate( "disabled"));
        assertTrue(!dsd.validate( "dgsdgsdf"));
        assertTrue(!dsd.validate( "12.76"));
    }
    @Test
    public void validateMinString(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMin("2.5");
        
        assertTrue(!dsd.validate( "one"));
        
    }
    @Test
    public void validateMaxString(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMax("12.5");
        
        assertTrue(!dsd.validate( "one"));
        
    }
    @Test
    public void validateMinEmptyString(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMin("");
        
        assertTrue(dsd.validate( "one"));
        
    }
    @Test
    public void validateMaxEmptyString(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
        dsd.setMax("");
        
        assertTrue(dsd.validate( "one"));
        
    }
    @Test
    public void validateMinInteger(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTINTEGER);
        dsd.setMin("2.5");
        
        assertTrue(!dsd.validate( "3"));
        
    }
    @Test
    public void validateMaxInteger(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTINTEGER);
        dsd.setMax("12.5");
        
        assertTrue(!dsd.validate( "11"));
    }
    @Test
    public void validateMinDouble(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTDOUBLE);
        dsd.setMin("dgsdgsdg");
        
        assertTrue(!dsd.validate( "3.2"));
        
    }
    @Test
    public void validateMaxDouble(){
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTDOUBLE);
        dsd.setMax("asfsfasdf");
        
        assertTrue(!dsd.validate( "11.5"));
    }
    @Test
    public void validateRegex(){
		Pattern regex = Pattern.compile("([0-9]|[1-2][0-9]|30) ([0-9]|[1-2][0-9]|3[0-5]) ([0-9]|[1-3][0-9]|40) ([0-9]|[1-3][0-9]|4[0-5]) ([0-9]|[1-4][0-9]|50) ([5-9]|[1-4][0-9]|5[0-5]) ([1-5][0-9]|60) (1[5-9]|[2-5][0-9]|6[0-5]) ([2-6][0-9]|70) (2[5-9]|[3-6][0-9]|7[0-5]) ([3-7][0-9]|80) (3[5-9]|[4-7][0-9]|8[0-5]) ([4-8][0-9]|90) (4[5-9]|[5-8][0-9]|9[0-5]) ([5-9][0-9]|100)");
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();
        dsd.setSettingID(200);
        dsd.setVarType(VarType.VTSTRING);
		dsd.setRegex(regex);
		
       	assertTrue(!dsd.validate( "one"));
        assertTrue(!dsd.validate( "0"));
        assertTrue(!dsd.validate( "2.5"));
        assertTrue(!dsd.validate( "2.45555567"));
        assertTrue(!dsd.validate( "2.534467"));
        assertTrue(dsd.validate( null));
        assertTrue(!dsd.validate( "12.744444"));
        assertTrue(!dsd.validate( "12.75"));
        assertTrue(!dsd.validate( "12.7567785433"));
        assertTrue(!dsd.validate( "12.76"));
        assertTrue(!dsd.validate( "11"));
        assertTrue(dsd.validate( "5 10 15 20 25 30 35 40 45 50 55 60 65 70 75"));
        assertTrue(dsd.validate( "0 0 0 0 0 5 10 15 20 25 30 35 40 45 50"));
        assertTrue(dsd.validate( "30 35 40 45 50 55 60 65 70 75 80 85 90 95 100"));
        assertTrue(!dsd.validate( "56 67 45 3250 56 67 34 56 34 34 78 67 95 100"));
        assertTrue(!dsd.validate( "5 10 15 20 25 30 35 40 45"));
    }
    
    @Test 
    public void ConfiguratorMapperChoicesTest(){
    	ConfiguratorMapper configuratorMapper = new ConfiguratorMapper();
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();

    	configuratorMapper.regexToModel(dsd, "3000 900 1 300|3000 80 2 300|2200 70 3 275|2000 70 4 250|1800 65 5 225|1500 60 6 188|1300 55 7 163|1200 50 8 150|1000 50 9 125|950 45 10 119|900 45 11 113|850 45 12 106|750 35 13 94|650 30 14 81|500 25 15 63");
    	assertTrue((dsd.getChoices() != null) && !dsd.getChoices().isEmpty());
    	assertTrue(dsd.getChoices().size()==15);
    	assertTrue(dsd.getRegex() == null);
    }
    @Test 
    public void ConfiguratorMapperRangeTest(){
    	ConfiguratorMapper configuratorMapper = new ConfiguratorMapper();
        DeviceSettingDefinition dsd = new  DeviceSettingDefinition();

    	configuratorMapper.regexToModel(dsd, "0*([2-9]|[1-9][0-9]{1,2}|1[0-9]{3}|2000)");
    	assertTrue((dsd.getChoices()== null) || dsd.getChoices().isEmpty()) ;
    	assertTrue(dsd.getRegex() != null);
    }
}
