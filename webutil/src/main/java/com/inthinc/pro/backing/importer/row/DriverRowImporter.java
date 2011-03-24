package com.inthinc.pro.backing.importer.row;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.backing.importer.DriverTemplateFormat;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.security.Role;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class DriverRowImporter extends RowImporter {

    private PersonDAO personDAO;
    private DriverDAO driverDAO;
    private UserDAO userDAO;
    private static StrongPasswordEncryptor passwordEncryptor;

    @Override
    public String importRow(List<String> rowData) {
        
        try {
        
            List<String> errorList = new ArrayList<String>();
            
            String accountName = rowData.get(DriverTemplateFormat.ACCOUNT_NAME_IDX);
            Account account = this.getAccountMap().get(accountName);
            
            if (account == null) { 
                return "Account " + accountName + " does not exist.";
            }
            
    
            String groupPath = rowData.get(DriverTemplateFormat.TEAM_PATH_IDX);
            Integer groupID = findOrCreateGroupByPath(groupPath, account.getAccountID());
            if (groupID == null) {
                return "Group path " + groupPath + " must start with fleet level group.";
                
            }
            
            
            Person person = findPersonByEmployeeID(account.getAccountID(), rowData.get(DriverTemplateFormat.EMPLOYEE_ID_IDX));
            Driver driver = null;
            User user = null;
            if (person != null) {
                driver = person.getDriver();
                user = person.getUser();
            }
            
            TimeZone timeZone = TimeZone.getTimeZone(rowData.get(DriverTemplateFormat.TIMEZONE_IDX));
            Locale locale =  new Locale(rowData.get(DriverTemplateFormat.LANGUAGE_IDX), rowData.get(DriverTemplateFormat.COUNTRY_IDX));
            MeasurementType measurementType = MeasurementType.valueOf(rowData.get(DriverTemplateFormat.MEASUREMENT_TYPE_IDX));
            FuelEfficiencyType fuelEfficiencyType = FuelEfficiencyType.valueOf(rowData.get(DriverTemplateFormat.FUEL_EFFICIENCY_TYPE_IDX));
    
            person= createOrUpdatePerson(person, account.getAccountID(), 
                    rowData.get(DriverTemplateFormat.LAST_NAME_IDX),
                    rowData.get(DriverTemplateFormat.FIRST_NAME_IDX),
                    rowData.get(DriverTemplateFormat.MIDDLE_NAME_IDX),
                    rowData.get(DriverTemplateFormat.EMPLOYEE_ID_IDX),
                    rowData.get(DriverTemplateFormat.EMAIL_IDX),
                    timeZone, locale, measurementType, fuelEfficiencyType);
            driver = createOrUpdateDriver(driver, person, groupID, rowData.get(DriverTemplateFormat.BARCODE_IDX)); 
            
            if (!rowData.get(DriverTemplateFormat.USERNAME_IDX).isEmpty()) {
                user = createOrUpdateUser(user, person, groupID, getAdminRole(account.getAccountID()), rowData.get(DriverTemplateFormat.USERNAME_IDX), rowData.get(DriverTemplateFormat.PASSWORD_IDX));
            }
        }
        catch (ProDAOException proEx) {
            return proEx.getMessage();
        }
        catch (Throwable t) {
            return t.getMessage();
        }
        return null;
    }


    public StrongPasswordEncryptor getPasswordEncryptor() {
        if (passwordEncryptor == null)
            passwordEncryptor = new StrongPasswordEncryptor();
        return passwordEncryptor;
    }


    private Person createOrUpdatePerson(Person person, Integer accountID, String last, String first, String middle, String empID, String email, 
            TimeZone timeZone, Locale locale, MeasurementType measurementType, FuelEfficiencyType fuelEfficiencyType) 
    {
        boolean isCreate = false;
        if (person == null) {
            isCreate = true;
            person = new Person();
        }
        person.setAcctID(accountID);
        person.setPriEmail(email);
        person.setEmpid(empID);
        person.setFirst(first);
        person.setLast(last);
        person.setFuelEfficiencyType(fuelEfficiencyType);
        person.setLocale(locale);
        person.setMeasurementType(measurementType);
        person.setMiddle(middle);
        person.setStatus(Status.ACTIVE);
        person.setTimeZone(timeZone);

        if (isCreate) {
            Integer personID = personDAO.create(accountID, person);
            person.setPersonID(personID);
        }
        else {
            personDAO.update(person);
        }
        return person;
    }
    
    private Driver createOrUpdateDriver(Driver driver, Person person, Integer teamID, String barcode)
    {
        boolean isCreate = false;
        if (driver == null) {
            isCreate = true;
            driver = new Driver();
            // TODO: add DOT TYPE to spreadsheet?
            driver.setDot(RuleSetType.NON_DOT);
        }
        driver.setGroupID(teamID);
        driver.setPersonID(person.getPersonID());
        driver.setStatus(Status.ACTIVE);
        driver.setBarcode(barcode);
        
        if (isCreate) {
            Integer driverID = driverDAO.create(person.getPersonID(), driver);
            driver.setDriverID(driverID);
        }
        else {
            driverDAO.update(driver);
        }
        
        return driver;
        
    }

    private Person findPersonByEmployeeID(Integer accountID, String employeeID) {
        List<Person> persons = personDAO.getPeopleInGroupHierarchy(getTopGroupID(accountID));
        for (Person person : persons) { 
            if (person.getStatus()!=null && person.getStatus().equals(Status.ACTIVE) && employeeID.trim().equals(person.getEmpid())) {
                return person;
            }
        }
        return null;
    }

    private User createOrUpdateUser(User user, Person person, Integer groupID, Role adminRole, String username, String password) {

        boolean isCreate = false;
        if (user == null) {
            isCreate = true;
            user = new User();
            List<Integer> roleList = new ArrayList<Integer>();
            roleList.add(adminRole.getRoleID());
            user.setRoles(roleList);
            user.setPersonID(person.getPersonID());
        }
        user.setGroupID(groupID);
        user.setUsername(username);
        user.setPassword(encryptPassword(password));
        user.setStatus(Status.ACTIVE);

        if (isCreate) {
            Integer userID = userDAO.create(person.getPersonID(), user);
            user.setUserID(userID);
        }
        else {
            userDAO.update(user);
        }
        return user;
    }

    
    private String encryptPassword(String pwd)
    {
        return getPasswordEncryptor().encryptPassword(pwd);
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


}
