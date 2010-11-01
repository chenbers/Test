//Maintained as .JAVA

package com.inthinc.pro.web.selenium.portal;

/* This is our portal into the Functional Areas and must be maintained.
 * As new functional areas are created this root class must  be updated to reflect them.
 */

public class Functional_Area_Root {
	
	//functional area count - requires maintenance
	private int faCount;
	
	//functional areas - requires maintenance
	NAVIGATE func1;
	VEHICLE func2;
	
	public Functional_Area_Root(){
		//maintain the count
		faCount = 2;
		
		//initiate functional areas
		func1 = new NAVIGATE();
		func2 = new VEHICLE();
	}
	
	
	//MUST BE MODIFIED - ALWAYS FOLLOW TEMPLATE
	public void CallKeyword (String kWord, String [] argList){
		/* Each new functional area that is created must be represented here.
		 * Follow the format indicated for all additions
		 * Each of the following calls work in series.
		 * Its only at the end that we know if there was a problem with one of the keyword calls.
		 */
	/*	boolean bFound = false;
		if (!bFound) bFound = func1.keywordSelect(kWord, argList);
		if (!bFound) bFound = func2.keywordSelect(kWord, argList);
		//additional if statements for new SWITCH functions here
		
		//At this point we throw an error if we have not found any keywords that match the data
		if (!bFound){
			System.out.printf("**ERROR: MOST LIKELY AN UNKNOWN KEYWORD. \n"); 
			System.out.printf("**VERIFY ALL KEYWORDS ARE ACCOUNTED FOR IN THE TEK_KEYWORDSELECT CLASS. \n");
			System.out.printf("**THE KEYWORD CALLED: " + kWord + "\n");
		}*/
	}

	
	public int GetFunctionalAreaCount(){
		return faCount;
	}
	
}
