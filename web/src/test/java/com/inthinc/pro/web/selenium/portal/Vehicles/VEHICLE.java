//Maintained as .JAVA

package com.inthinc.pro.web.selenium.portal.Vehicles;

import com.inthinc.pro.web.selenium.Singleton;
import com.thoughtworks.selenium.*;



public class VEHICLE
{
	/* THIS CLASS IS A FUNCTIONAL AREA OF THE AUT
	 * THIS CLASS SHOULD REMAIN IN .JAVA FORM
	 * THIS CLASS SHOULD BE MAINTAINED
	 */
	
	
	//define local vars
	Singleton tvar = Singleton.getSingleton() ; 
	Selenium selenium = tvar.getSelenium();
		
	
			
		
		
		//Maintain the keyword Count
		private int KeywordCount;
		
		
		
		public int getKeywordCount(){
			return KeywordCount;
		}
		
		


}
