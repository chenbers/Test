package com.inthinc.pro.selenium.testSuites;


//TEST BEING TAKEN OFFLINE FOR NOW
//
///**
// * AdminUsersTest compares the user account to the my account for accuracy
// *
// */
//public class AdminUserTest extends WebRallyTest {
//	
//	private PageAdminUsers my;
//	private PageMyAccount myAccount;
//	private PageAdminUserDetails myAdminUserDetails;
//	private AutomationUser login;
//    
//	@Before
//	public void setupPage() {
//        login = AutomationUsers.getUsers().getOneBy(LoginCapability.RoleAdmin);
//		my = new PageAdminUsers();
//		myAccount = new PageMyAccount();
//		myAdminUserDetails = new PageAdminUserDetails();
//	}
//	
//	/**
//	 * AccountInformation test verifies MyAccount data with Admin User data.
//	 * testDependencies:
//	 * must have admin access
//	 * 
//	 */
//	
//	@Test
//	public void accountInformation(){
//	  	set_test_case("TC1266");
//				
//        LoginCapability hasThisCapability = null;
//        hasThisCapability = LoginCapability.RoleAdmin;
//    
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
//    
//        PageLogin login = new PageLogin();
//        login.loginProcess(user); 
//		my._link().admin().click();
//		
//		//ensure that username column is available before searching
//		if(my._link().sortByColumn(UserColumns.USER_NAME).isPresent()){
//    		my._link().editColumns().click();
//    		my._popUp().editColumns()._checkBox().row(UserColumns.USER_NAME).check();
//    		my._popUp().editColumns()._button().save().click();
//		}
//    		
//		//search for this username (this ensures that it will be on the first page of the table)
//		my._textField().search().type(user.getUsername());
//		my._button().search().click();
//		
//		Iterator<TextBased> iter = my._text().tableEntry(UserColumns.USER_NAME).iterator();
//		int rowNumber = 0;
//		boolean clicked = false;
//		pause(1, "Looking for a login");
//		while(iter.hasNext()&&!clicked){
//		    TextBased username = iter.next();
//		    rowNumber++;
//		    
//		    if(username.getText().equalsIgnoreCase(user.getUsername())){
//		        my._link().tableEntryUserFullName().row(rowNumber).click();
//		        clicked = true;
//		    }
//		}
//		if(!clicked) {
//		    addError("couldn't find user name", ErrorLevel.FATAL);
//		}
//	
//		
//		/*User Info*/
//		
//		UserDetailsTexts _text = myAdminUserDetails._text();
//        String firstname =_text.values(UserColumns.FIRST_NAME).getText();
//		String middlename =_text.values(UserColumns.MIDDLE_NAME).getText();
//		String lastname =_text.values(UserColumns.LAST_NAME).getText();
//		String suffix =_text.values(UserColumns.SUFFIX).getText();
//		
//		
//		/*Driver Info*/
//		
//        String team =_text.values(UserColumns.TEAM).getText();
//        Log.info(team);
//        
//        /*Employee Info*/
//      
//        String locale = _text.values(UserColumns.LOCALE).getText();
//        String measurement =_text.values(UserColumns.MEASUREMENT).getText();
//        String fuelratio =_text.values(UserColumns.FUEL_RATIO).getText();
//
//      
//        /* Login Info*/
//       
//        String loginname =_text.values(UserColumns.USER_NAME).getText();
//        String group =_text.values(UserColumns.GROUP).getText();
//               
//        /*Notifications*/
//        
//        String mailone =_text.values(UserColumns.EMAIL_1).getText();
//        String mailtwo =_text.values(UserColumns.EMAIL_2).getText();
//        String textone =_text.values(UserColumns.TEXT_MESSAGE_1).getText();
//        String texttwo =_text.values(UserColumns.TEXT_MESSAGE_2).getText();
//        String phoneone =_text.values(UserColumns.PHONE_1).getText();
//       
//        String phonetwo =_text.values(UserColumns.PHONE_2).getText();
//        String information =_text.values(UserColumns.INFORMATION).getText();
//        String warning =_text.values(UserColumns.WARNING).getText();
//        String critical =_text.values(UserColumns.CRITICAL).getText();
//        
//                
//        /*click into My Account to compare*/
//        
//        myAccount._link().myAccount().click();
//               
//        /*compare Account info */
//        String expectedName = (firstname +" "+ middlename+" "+lastname+" "+suffix).replace("  ", " ").trim();
//        myAccount._text().name().validate(expectedName);
//        myAccount._text().group().validate(group);
//        myAccount._text().team().validate(team);
//               
//        /*compare Login info */
//        myAccount._text().userName().validate(loginname);
//        myAccount._text().locale().validate(locale);
//        myAccount._text().measurement().validate(measurement);
//        myAccount._text().fuelEfficiency().validate(fuelratio);
//                     
//        /*compare Red flag preferences */
//        
//        myAccount._text().redFlagInfo().validate(information);
//        myAccount._text().redFlagWarn().validate(warning);
//        myAccount._text().redFlagCritical().validate(critical);
//         
//        /* compare contact info*/
//        myAccount._text().email1().validate(mailone);
//        myAccount._text().email2().validate(mailtwo);
//        myAccount._text().phone1().validate(phoneone);
//        myAccount._text().phone2().validate(phonetwo);
//        myAccount._text().textMessage1().validate(textone);
//        myAccount._text().textMessage2().validate(texttwo);
//                
//       	}   
//        
//}     
//        
	