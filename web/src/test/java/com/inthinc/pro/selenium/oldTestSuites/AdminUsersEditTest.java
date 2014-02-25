package com.inthinc.pro.selenium.oldTestSuites;


//TEST BEING TAKEN OFFLINE FOR NOW
//
//public class AdminUsersEditTest extends WebRallyTest {
//
//    private PageAdminUsers usersPage = new PageAdminUsers();
//    private PageAdminAddEditUser edituser = new PageAdminAddEditUser();
//    private PageAdminUserDetails details = new PageAdminUserDetails();
//    private AutomationUser login;
//
//    @Before
//    public void beforeClass() {
//        login = AutomationUsers.getUsers().getOneBy(LoginCapability.RoleAdmin, AccountCapability.HOSEnabled, LoginCapability.IsDriver);
//        usersPage = new PageAdminUsers();
//        edituser = new PageAdminAddEditUser();
//        details = new PageAdminUserDetails();
//    }
//
//    @Test
//    public void usersNameLink() {
//        set_test_case("TC764");
//
//        // 1- Login
//        LoginCapability hasThisCapability = null;
//        hasThisCapability = LoginCapability.RoleAdmin;
//        
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
//        
//        PageLogin login = new PageLogin();
//        login.loginProcess(user); 
//
//        // 2- Click on Admin
//        usersPage._link().admin().click();
//
//        // 3- Take note of the top user's name
//        String fullname = usersPage._link().tableEntryUserFullName().row(1)
//                .getText();
//
//        // 4- Click on the top name
//        usersPage._link().tableEntryUserFullName().row(1).click();
//
//        // 5- Verify the page is the correct page
//        String firstName = details._text().values(UserColumns.FIRST_NAME)
//                .getText();
//        String middleName = details._text()
//                .values(UserColumns.MIDDLE_NAME).getText();
//        String lastName = details._text().values(UserColumns.LAST_NAME)
//                .getText();
//        String suffix = details._text().values(UserColumns.SUFFIX)
//                .getText();
//        assertEquals(fullname, (firstName + " " + middleName + " " + lastName
//                + " " + suffix).replace("  ", " ").trim());
//
//    }
//
//    @Test
//    public void usersEditLink() {
//        // set_test_case("TC762");//TODO: if this test is ready to run in hudson, uncomment this line; if not add @ignore
//
//        // 1- Login
//        LoginCapability hasThisCapability = null;
//        hasThisCapability = LoginCapability.RoleAdmin;
//        
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
//        
//        PageLogin login = new PageLogin();
//        login.loginProcess(user); 
//
//        // 2- Click on Admin
//        usersPage._link().admin().click();
//
//        // 3- Take note of the top user's employee ID
//        String employeeID = usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).getText();// TODO: this test needs to ensure that the EMPLOYEE_ID line is visible before trying to getText on it
//
//        // 4- Click on the Edit link for the top name
//        usersPage._link().edit().row(1).click();
//
//        // 5- Verify the page is the correct page
//        edituser._textField().personFields(UserColumns.EMPLOYEE_ID)
//                .assertEquals(employeeID);
//
//    }
//
//    @Test
//    public void usersSearch() {
//        // set_test_case("TC766");//TODO: if this test is ready to run in hudson, uncomment this line; if not add @ignore
//
//        // 1- Login
//        LoginCapability hasThisCapability = null;
//        hasThisCapability = LoginCapability.RoleAdmin;
//        
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
//        
//        PageLogin login = new PageLogin();
//        login.loginProcess(user); 
//
//        // 2- Click on Admin
//        usersPage._link().admin().click();
//
//        // 3- Enter a user name into the search field
//        String name = "Danni";
//        usersPage._textField().search().type(name);
//
//        // 4- Click Search
//        usersPage._button().search().click();
//
//        // 5- Verify only that name shows on the list
//        boolean hasOnlyExpectedUsernames = true;
//        boolean hasThisRow = true;
//        // TODO: this test might benefit from users._link().tableEntryUserName().iterator()
//        for (int i = 1; i < 10 && hasOnlyExpectedUsernames && hasThisRow; i++) {
//            hasThisRow = usersPage._link().tableEntryUserFullName().row(i)
//                    .isPresent();
//            if (hasThisRow) {
//                hasOnlyExpectedUsernames &= usersPage._link()
//                        .tableEntryUserFullName().row(i).validateContains(name);
//
//            }
//        }
//    }
//
//    @Test
//    public void usersEditColumnsCancel() {
//        // set_test_case("TC769");//TODO: if this test is ready to run in hudson, uncomment this line; if not add @ignore
//
//        // 1- Login
//        LoginCapability hasThisCapability = null;
//        hasThisCapability = LoginCapability.RoleAdmin;
//        
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
//        
//        PageLogin login = new PageLogin();
//        login.loginProcess(user); 
//
//        // 2- Click on Admin
//        usersPage._link().admin().click();
//
//        // 3- Take note of what information shows in the columns
//        boolean originallyHadGroupColumnName = usersPage._link()
//                .tableEntryUserFullName().row(1).isPresent();
//        boolean originallyHadGroupColumnEmpID = usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent();
//        boolean originallyHadGroupColumnDOB = usersPage._text()
//                .tableEntry(UserColumns.DOB_MAIN).row(1).isPresent();
//        boolean originallyHadGroupColumnLicense = usersPage._text()
//                .tableEntry(UserColumns.LICENSE_NUMBER).row(1)
//                .isPresent();
//
//        // 4- Click on Edit Columns
//        usersPage._link().editColumns().click();
//
//        // 5- Click on one or more boxes
//        usersPage._popUp().editColumns()._checkBox().row(1).click();
//        usersPage._popUp().editColumns()._checkBox().row(12).click();
//        usersPage._popUp().editColumns()._checkBox().row(15).click();
//
//        // 6- Click Cancel
//        usersPage._popUp().editColumns()._button().cancel().click();
//
//        // 7- Verify nothing has changed
//        if (originallyHadGroupColumnName != usersPage._link()
//                .tableEntryUserFullName().row(1).isPresent()) {
//            addError("expected line to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnEmpID != usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent()) {
//            addError("expected line to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDOB != usersPage._text()
//                .tableEntry(UserColumns.DOB_MAIN).row(1).isPresent()) {
//            addError("expected line to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnLicense != usersPage._text()
//                .tableEntry(UserColumns.LICENSE_NUMBER).row(1)
//                .isPresent()) {
//            addError("expected line to remain the same", ErrorLevel.FAIL);
//        }
//    }
//
//    @Test
//    public void usersEditColumnsCancelNoChange() {
//        // set_test_case("TC770");//TODO: if this test is ready to run in hudson, uncomment this line; if not add @ignore
//
//        // 1- Login
//        LoginCapability hasThisCapability = null;
//        hasThisCapability = LoginCapability.RoleAdmin;
//        
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
//        
//        PageLogin login = new PageLogin();
//        login.loginProcess(user); 
//
//        // 2- Click on Admin
//        usersPage._link().admin().click();
//
//        // 3- Take note of what information shows in the columns
//        boolean originallyHadGroupColumnName = usersPage._link()
//                .tableEntryUserFullName().row(1).isPresent();
//        boolean originallyHadGroupColumnEmpID = usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent();
//        boolean originallyHadGroupColumnDOB = usersPage._text()
//                .tableEntry(UserColumns.DOB_MAIN).row(1).isPresent();
//        boolean originallyHadGroupColumnLicense = usersPage._text()
//                .tableEntry(UserColumns.LICENSE_NUMBER).row(1)
//                .isPresent();
//
//        // 4- Click on Edit Columns
//        usersPage._link().editColumns().click();
//
//        // 5- Click Cancel
//        usersPage._popUp().editColumns()._button().cancel().click();
//
//        // 6- Verify nothing has changed
//        if (originallyHadGroupColumnName != usersPage._link()
//                .tableEntryUserFullName().row(1).isPresent()) {
//            addError("something", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnEmpID != usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent()) {
//            addError("expected line to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDOB != usersPage._text()
//                .tableEntry(UserColumns.DOB_MAIN).row(1).isPresent()) {
//            addError("expected line to remain the same", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnLicense != usersPage._text()
//                .tableEntry(UserColumns.LICENSE_NUMBER).row(1)
//                .isPresent()) {
//            addError("expected line to remain the same", ErrorLevel.FAIL);
//        }
//    }
//
//    @Test
//    public void usersEditColumnsRetention() {
//        // set_test_case("TC773");//TODO: if this test is ready to run in hudson, uncomment this line; if not add @ignore
//
//        // 1- Login
//        LoginCapability hasThisCapability = null;
//        hasThisCapability = LoginCapability.RoleAdmin;
//        
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
//        
//        PageLogin login = new PageLogin();
//        login.loginProcess(user); 
//
//        // 2- Click on Admin
//        usersPage._link().admin().click();
//
//        // 3- Take note of what information shows in the columns
//        boolean originallyHadGroupColumnName = usersPage._link()
//                .tableEntryUserFullName().row(1).isPresent();
//        boolean originallyHadGroupColumnEmpID = usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent();
//        boolean originallyHadGroupColumnDOB = usersPage._text()
//                .tableEntry(UserColumns.DOB_MAIN).row(1).isPresent();
//        boolean originallyHadGroupColumnLicense = usersPage._text()
//                .tableEntry(UserColumns.LICENSE_NUMBER).row(1)
//                .isPresent();
//
//        // 4- Click on Edit Columns
//        usersPage._link().editColumns().click();
//
//        // 5- Click on one or more boxes
//        usersPage._popUp().editColumns()._checkBox().row(1).click();
//        usersPage._popUp().editColumns()._checkBox().row(12).click();
//        usersPage._popUp().editColumns()._checkBox().row(15).click();
//
//        // 6- Click Save
//        usersPage._popUp().editColumns()._button().save().click();
//
//        // 7- Verify changes are there
//        if (originallyHadGroupColumnName == usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent()) {
//            addError("Expected line to change", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnEmpID == usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent()) {
//            addError("Expected line to change", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDOB == usersPage._text()
//                .tableEntry(UserColumns.DOB_MAIN).row(1).isPresent()) {
//            addError("Expected line to change", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnLicense != usersPage._text()
//                .tableEntry(UserColumns.LICENSE_NUMBER).row(1)
//                .isPresent()) {
//            addError("expected line to remain the same", ErrorLevel.FAIL);
//        }
//
//        // 8- Click on another page
//        usersPage._link().hos().click();
//
//        // 9- Go back to the Admin page
//        usersPage._link().admin().click();
//
//        // 10- Verify changes remain
//        if (originallyHadGroupColumnName == usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent()) {
//            addError("Expected line to change", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnEmpID == usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent()) {
//            addError("Expected line to change", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDOB == usersPage._text()
//                .tableEntry(UserColumns.DOB_MAIN).row(1).isPresent()) {
//            addError("Expected line to change", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnLicense != usersPage._text()
//                .tableEntry(UserColumns.LICENSE_NUMBER).row(1)
//                .isPresent()) {
//            addError("expected line to remain the same", ErrorLevel.FAIL);
//        }
//
//        // 11- Click on Edit Columns
//        usersPage._link().editColumns().click();
//
//        // 12- Click on the same boxes again
//        usersPage._popUp().editColumns()._checkBox().row(1).click();
//        usersPage._popUp().editColumns()._checkBox().row(3).click();
//        usersPage._popUp().editColumns()._checkBox().row(5).click();
//
//        // 13- Click Save to return to default
//        usersPage._popUp().editColumns()._button().save().click();
//
//    }
//
//    @Test
//    public void usersEditColumnsSave() {
//        // set_test_case("TC775");//TODO: if this test is ready to run in hudson, uncomment this line; if not add @ignore
//
//        // 1- Login
//        LoginCapability hasThisCapability = null;
//        hasThisCapability = LoginCapability.RoleAdmin;
//        
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
//        
//        PageLogin login = new PageLogin();
//        login.loginProcess(user); 
//
//        // 2- Click on Admin
//        usersPage._link().admin().click();
//
//        // 3- Take note of what information shows in the columns
//        boolean originallyHadGroupColumnName = usersPage._link()
//                .tableEntryUserFullName().row(1).isPresent();
//        boolean originallyHadGroupColumnEmpID = usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent();
//        boolean originallyHadGroupColumnDOB = usersPage._text()
//                .tableEntry(UserColumns.DOB_MAIN).row(1).isPresent();
//        boolean originallyHadGroupColumnLicense = usersPage._text()
//                .tableEntry(UserColumns.LICENSE_NUMBER).row(1)
//                .isPresent();
//
//        // 4- Click on Edit Columns
//        usersPage._link().editColumns().click();
//
//        // 5- Click on one or more boxes
//        usersPage._popUp().editColumns()._checkBox().row(1).click();
//        usersPage._popUp().editColumns()._checkBox().row(12).click();
//        usersPage._popUp().editColumns()._checkBox().row(15).click();
//
//        // 6- Click Save
//        usersPage._popUp().editColumns()._button().save().click();
//
//        // 7- Verify changes are there
//        if (originallyHadGroupColumnName == usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent()) {
//            addError("Expected line to change", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnEmpID == usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent()) {
//            addError("Expected line to change", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDOB == usersPage._text()
//                .tableEntry(UserColumns.DOB_MAIN).row(1).isPresent()) {
//            addError("Expected line to change", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnLicense != usersPage._text()
//                .tableEntry(UserColumns.LICENSE_NUMBER).row(1)
//                .isPresent()) {
//            addError("expected line to remain the same", ErrorLevel.FAIL);
//        }
//
//        // 8- Click on Edit Columns
//        usersPage._link().editColumns().click();
//
//        // 9- Click on the same boxes again
//        usersPage._popUp().editColumns()._checkBox().row(1).click();
//        usersPage._popUp().editColumns()._checkBox().row(3).click();
//        usersPage._popUp().editColumns()._checkBox().row(5).click();
//
//        // 10- Click Save to return to default
//        usersPage._popUp().editColumns()._button().save().click();
//    }
//
//    @Test
//    public void usersEditColumnsNewSession() {
//        // set_test_case("TC776");//TODO: if this test is ready to run in hudson, uncomment this line; if not add @ignore
//
//        // 1- Login
//        LoginCapability hasThisCapability = null;
//        hasThisCapability = LoginCapability.RoleAdmin;
//        
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
//        
//        PageLogin login = new PageLogin();
//        login.loginProcess(user); 
//
//        // 2- Click on Admin
//        usersPage._link().admin().click();
//
//        // 3- Take note of what information shows in the columns
//        boolean originallyHadGroupColumnName = usersPage._link()
//                .tableEntryUserFullName().row(1).isPresent();
//        boolean originallyHadGroupColumnEmpID = usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent();
//        boolean originallyHadGroupColumnDOB = usersPage._text()
//                .tableEntry(UserColumns.DOB_MAIN).row(1).isPresent();
//        boolean originallyHadGroupColumnLicense = usersPage._text()
//                .tableEntry(UserColumns.LICENSE_NUMBER).row(1)
//                .isPresent();
//
//        // 4- Click on Edit Columns
//        usersPage._link().editColumns().click();
//
//        // 5- Click on one or more boxes
//        usersPage._popUp().editColumns()._checkBox().row(1).click();
//        usersPage._popUp().editColumns()._checkBox().row(12).click();
//        usersPage._popUp().editColumns()._checkBox().row(15).click();
//
//        // 6- Click Save
//        usersPage._popUp().editColumns()._button().save().click();
//
//        // 7- Verify changes are there
//        if (originallyHadGroupColumnName == usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent()) {
//            addError("Expected line to change", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnEmpID == usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent()) {
//            addError("Expected line to change", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDOB == usersPage._text()
//                .tableEntry(UserColumns.DOB_MAIN).row(1).isPresent()) {
//            addError("Expected line to change", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnLicense != usersPage._text()
//                .tableEntry(UserColumns.LICENSE_NUMBER).row(1)
//                .isPresent()) {
//            addError("expected line to remain the same", ErrorLevel.FAIL);
//        }
//
//        // 8- Logout
//        usersPage._link().logout().click();
//
//        // 9- Log back in
//        login.loginProcess(user); 
//
//        // 10- Click on Admin
//        usersPage._link().admin().click();
//
//        // 11- Verify changes are still there
//        if (originallyHadGroupColumnName == usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent()) {
//            addError("Expected line to change", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnEmpID == usersPage._text()
//                .tableEntry(UserColumns.EMPLOYEE_ID).row(1).isPresent()) {
//            addError("Expected line to change", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnDOB == usersPage._text()
//                .tableEntry(UserColumns.DOB_MAIN).row(1).isPresent()) {
//            addError("Expected line to change", ErrorLevel.FAIL);
//        }
//        if (originallyHadGroupColumnLicense != usersPage._text()
//                .tableEntry(UserColumns.LICENSE_NUMBER).row(1)
//                .isPresent()) {
//            addError("expected line to remain the same", ErrorLevel.FAIL);
//        }
//
//        // 12- Click on Edit Columns
//        usersPage._link().editColumns().click();
//
//        // 13- Click on the same boxes again
//        usersPage._popUp().editColumns()._checkBox().row(1).click();
//        usersPage._popUp().editColumns()._checkBox().row(3).click();
//        usersPage._popUp().editColumns()._checkBox().row(5).click();
//
//        // 14- Click Save to return to default
//        usersPage._popUp().editColumns()._button().save().click();
//
//    }
//
//    @Test
//    public void sevenDayRuleSet() {
//        set_test_case("TC5676");
//        rulesetTestHelper("US 7 Day");
//    }
//
//    @Test
//    public void sevenDayUSOilRuleSet() {
//        set_test_case("TC5677");
//        rulesetTestHelper("US Oil 7 Day");
//    }
//
//    @Test
//    public void eightDayRuleSet() {
//        set_test_case("TC5678");
//        rulesetTestHelper("US 8 Day");
//    }
//
//    @Test
//    public void eightDayUSOilRuleSet() {
//        set_test_case("TC5679");
//        rulesetTestHelper("US Oil 8 Day");
//    }
//
//    @Test
//    public void canadaRuleSet() {
//        set_test_case("TC5680");
//        rulesetTestHelper("Canada");
//
//    }
//
//    @Test
//    public void canadaSixtyDegrees() {
//        set_test_case("TC5681");
//        rulesetTestHelper("Canada 60 Degrees");
//
//    }
//
//    @Test
//    public void usHomeOffice() {
//        set_test_case("TC5682");
//        rulesetTestHelper("US Home Office");
//
//    }
//
//    @Test
//    public void canadaHomeOffice() {
//        set_test_case("TC5683");
//        rulesetTestHelper("Canada Home Office");
//
//    }
//
//    @Test
//    public void texasRuleSet() {
//        set_test_case("TC5684");
//        rulesetTestHelper("Texas");
//
//    }
//
//    @Test
//    public void canadaAlberta() {
//        set_test_case("TC5685");
//        rulesetTestHelper("Canada Alberta");
//
//    }
//
//    @Test
//    public void canada2007CycleOne() {
//        set_test_case("TC5686");
//        rulesetTestHelper("Canada 2007 Cycle 1");
//
//    }
//
//    @Test
//    public void canada2007CycleTwo() {
//        set_test_case("TC5687");
//        rulesetTestHelper("Canada 2007 Cycle 2");
//
//    }
//
//    @Test
//    public void canada2007SixtyDegreesCycleOne() {
//        set_test_case("TC5688");
//        rulesetTestHelper("Canada 2007 60 Degrees Cycle 1");
//
//    }
//
//    @Test
//    public void canada2007SixtyDegreesCycleTwo() {
//        set_test_case("TC5689");
//        rulesetTestHelper("Canada 2007 60 Degrees Cycle 2");
//
//    }
//
//    @Test
//    public void canada2007OilFieldPermit() {
//        set_test_case("TC5690");
//        rulesetTestHelper("Canada 2007 Oil Field Permit");
//
//    }
//
//    @Test
//    public void canada2007SixtyDegreesOilFieldPermit() {
//        set_test_case("TC5691");
//        rulesetTestHelper("Canada 2007 60 Degrees Oil Field Permit");
//
//    }
//
//    private void rulesetTestHelper(String hosRuleset) {
//
//        // 1- Login
//        LoginCapability hasThisCapability = null;
//        hasThisCapability = LoginCapability.RoleAdmin;
//        
//        AutomationUser user = AutomationUsers.getUsers().getOneBy(hasThisCapability);
//        
//        PageLogin login = new PageLogin();
//        login.loginProcess(user); 
//
//        // 2- Click on Admin
//        usersPage._link().admin().click();
//        usersPage._link().editColumns().click();
//        int column_name = 1;
//        int column_userName = 21;
//        usersPage._popUp().editColumns()._checkBox().row(column_name).check();
//        usersPage._popUp().editColumns()._checkBox().row(column_userName)
//                .check();
//        usersPage._popUp().editColumns()._button().save().click();
//
//        // 3. Search and select driver
//        usersPage._textField().search().type(user.getUsername());
//        usersPage._button().search().click();
//
//        boolean foundWhoIWasLookingFor = false;
//        int rowNumber = 0;
//        Iterator<TextBased> itr = usersPage._text()
//                .tableEntry(UserColumns.USER_NAME).iterator();
//        while (itr.hasNext()) {
//            TextBased row = itr.next();
//            rowNumber++;
//            if (row.getText().equals(user.getUsername())) {
//                usersPage._link().tableEntryUserFullName().row(rowNumber)
//                        .click();
//                foundWhoIWasLookingFor = true;
//                break;
//            }
//        }
//        if (!foundWhoIWasLookingFor)
//            addError("Test did not find the user it was looking for",
//                    ErrorLevel.FAIL);
//        // 4. Select DOT rule set
//        details._button().edit().click();
//        edituser._dropDown().dot().select(hosRuleset);
//        edituser._button().saveBottom().click();
//        details._text().values(UserColumns.DOT).assertEquals(hosRuleset);
//
//    }
//}
