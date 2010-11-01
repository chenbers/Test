//Maintained as .JAVA

package Portal;

import org.testng.annotations.*;
import org.openqa.selenium.server.SeleniumServer;
import com.thoughtworks.selenium.*;
import static org.testng.AssertJUnit.*;
import Portal.Singleton;



public class VEHICLE
{
	/* THIS CLASS IS A FUNCTIONAL AREA OF THE AUT
	 * THIS CLASS SHOULD REMAIN IN .JAVA FORM
	 * THIS CLASS SHOULD BE MAINTAINED
	 */
	
	
	//define local vars
	Portal.Singleton tvar = Portal.Singleton.getSingleton() ; 
	Selenium selenium = tvar.getSelenium();
		
	
			
		
		
		//Maintain the keyword Count
		private int KeywordCount;
		
		
		
		public int getKeywordCount(){
			return KeywordCount;
		}
		
		


}
