package Debug;

import TEK_CORE.TEK_PRIMARY_DRIVER;


public class TEK_EXECUTION {
	/* This will allow us to execute 1 test at a time
	 * Not intended for primary execution mechanism
	 */
	
	static TEK_PRIMARY_DRIVER testDrive;
	
	
	public static void main(String[] args) {
		//we could create functions to read a test set in this class
		//for now we will hard code the test path
		//we have to figure out how to interface our call to the primary driver with selenium
		testDrive = new TEK_PRIMARY_DRIVER("C:\\TestingThis.xls", "Test");
		testDrive.TEK_TEST_DRIVER();
	}
	
}
