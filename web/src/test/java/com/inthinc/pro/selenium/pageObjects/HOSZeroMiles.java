package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.selenium.pageEnums.HOSZeroMilesEnum;

public class HOSZeroMiles {
    
    public HOSZeroMilesTexts _text(){
        return new HOSZeroMilesTexts();
    }
    
    public HOSZeroMilesLinks _link(){
        return new HOSZeroMilesLinks();
    }
    
    public class HOSZeroMilesTexts {
        public TextTable entryLocation(){
            return new TextTable(HOSZeroMilesEnum.LOCATION);
        }
        
        public TextTable entryVehicle(){
            return new TextTable(HOSZeroMilesEnum.VEHICLE);
        }
        
        public TextTable entryTotalMilesNoDriver(){
            return new TextTable(HOSZeroMilesEnum.TOTAL_MILES_NO_DRIVER);
        }
    }
    
    public class HOSZeroMilesLinks {
        public TextTable sortLocation(){
            return new TextTable(HOSZeroMilesEnum.LOCATION);
        }
        
        public TextTable sortVehicle(){
            return new TextTable(HOSZeroMilesEnum.VEHICLE);
        }
        
        public TextTable sortTotalMilesNoDriver(){
            return new TextTable(HOSZeroMilesEnum.TOTAL_MILES_NO_DRIVER);
        }
    }
}
