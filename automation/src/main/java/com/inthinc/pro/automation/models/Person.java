package com.inthinc.pro.automation.models;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.objects.AutomationCalendar;

@XmlRootElement
public class Person extends BaseEntity implements Comparable<Person> {

    private static final long serialVersionUID = -7162580776394490873L;
    private Integer personID;
    private TimeZone timeZone;
    // contact information
    private Integer addressID;
    private Address address;
    
    @JsonSerialize(include = Inclusion.NON_NULL)
    private String priEmail;
    private String secEmail;
    private String priPhone;
    private String secPhone;
    private String priText;
    private String secText;
    private Integer info;
    private Integer warn;
    private Integer crit;
    // employee information

    @JsonSerialize(include = Inclusion.NON_NULL)
    private String empid;
    private String reportsTo;
    private String title;

    @JsonSerialize(include = Inclusion.NON_NULL)
    private String dept;
    // personal information
    private String first;
    private String middle;
    private String last;
    private String suffix;
    private Gender gender;
    private Integer height; // inches
    private Integer weight; // pounds
    
    @JsonIgnore
    private final AutomationCalendar dob = new AutomationCalendar(WebDateFormat.RALLY_DATE_FORMAT);
    // user, driver (may be null)
    private User user;
    private Driver driver;
    private Status status;
    private Integer acctID;
    private MeasurementType measurementType;
    private FuelEfficiencyType fuelEfficiencyType;
    private Locale locale;
    
    

    public Person() {
        super();
    }
    public Person(Integer personID, Integer acctID, TimeZone timeZone, Integer addressID, String priEmail, String secEmail, String priPhone, String secPhone,
            String priText, String secText, Integer info, Integer warn, Integer crit, String empid, String reportsTo, String title, String dept, String first, String middle,
            String last, String suffix, Gender gender, Integer height, Integer weight, Date dob, Status status, MeasurementType measurementType,
            FuelEfficiencyType fuelEfficiencyType, Locale locale) {
        super();
        this.acctID = acctID;
        this.personID = personID;
        this.timeZone = timeZone;
        this.addressID = addressID;
        this.priEmail = priEmail;
        this.secEmail = secEmail;
        this.priPhone = priPhone;
        this.secPhone = secPhone;
        this.priText = priText;
        this.secText = secText;
        this.info = info;
        this.warn = warn;
        this.crit = crit;
        this.empid = empid;
        this.reportsTo = reportsTo;
        this.title = title;
        this.dept = dept;
        this.first = first;
        this.middle = middle;
        this.last = last;
        this.suffix = suffix;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.dob.setDate(dob.getTime());
        this.status = status;
        this.measurementType = measurementType;
        this.fuelEfficiencyType = fuelEfficiencyType;
        this.locale = locale;
    }

    public Integer getPersonID() {
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }
    
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getDisplayTimeZone() {
        if (timeZone != null)
            return timeZone.getDisplayName(timeZone.inDaylightTime(new GregorianCalendar(timeZone).getTime()), TimeZone.LONG);
        else
            return null;
    }

    @JsonSerialize(include = Inclusion.NON_NULL)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPriEmail() {
        return priEmail;
    }

    public void setPriEmail(String priEmail) {
        this.priEmail = priEmail;
    }

    public String getSecEmail() {
        return secEmail;
    }

    public void setSecEmail(String secEmail) {
        this.secEmail = secEmail;
    }

    public String getPriPhone() {
        return priPhone;
    }

    public void setPriPhone(String priPhone) {
        this.priPhone = priPhone;
    }

    public String getSecPhone() {
        return secPhone;
    }

    public void setSecPhone(String secPhone) {
        this.secPhone = secPhone;
    }

    public String getPriText() {
        return priText;
    }

    public void setPriText(String priText) {
        this.priText = priText;
    }

    public String getSecText() {
        return secText;
    }

    public void setSecText(String secText) {
        this.secText = secText;
    }

    public Integer getInfo() {
        return info;
    }

    public void setInfo(Integer info) {
        this.info = info;
    }

    public Integer getWarn() {
        return warn;
    }

    public void setWarn(Integer warn) {
        this.warn = warn;
    }

    public Integer getCrit() {
        return crit;
    }

    public void setCrit(Integer crit) {
        this.crit = crit;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(String reportsTo) {
        this.reportsTo = reportsTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public AutomationCalendar getDob() {
        return dob;
    }

    @JsonProperty("dob")
    public void setDob(String dob) {
        this.dob.setDate(dob);
    }
    
    @JsonSerialize(include = Inclusion.NON_NULL)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    public Integer getUserID() {
    	if(user==null)
    		return null;
        return user.getUserID();
    }

    @JsonSerialize(include = Inclusion.NON_NULL)
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @JsonIgnore
    public Integer getDriverID() {
    	if(driver==null)
    		return null;
        return driver.getDriverID();
    }
    
    public Integer getAddressID() {
        return addressID;
    }

    public void setAddressID(Integer addressID) {
        this.addressID = addressID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getAcctID()
    {
        return acctID;
    }

    public void setAcctID(Integer acctID) {
        this.acctID = acctID;
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    public FuelEfficiencyType getFuelEfficiencyType() {
        return fuelEfficiencyType;
    }

    public void setFuelEfficiencyType(FuelEfficiencyType fuelEfficiencyType) {
        this.fuelEfficiencyType = fuelEfficiencyType;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    @Override
    public int compareTo(Person o) {
        // for now, use the name fields to determine the natural order of person objects
        int result = compareName(last, o.getLast());
        if (result == 0)
            result = compareName(first, o.getFirst());
        if (result == 0){
            return compareName(middle, o.getMiddle());
        }
        return result;
    }
    private int compareName(String n1, String n2) {
        if (n1 == null && n2==null) return 0;
        
        if (n1 == null)
            return -1;
        if (n2 == null)
            return 1;
        
        return n1.toUpperCase().compareTo(n2.toUpperCase());
        
    }
    
    @Override
    public int hashCode(){
        return getPersonID();
    }

    @Override
    public String toString() {
        return "Person [acctID=" + acctID + ", address=" + address + ", addressID=" + addressID + ", crit=" + crit + ", dept=" + dept + ", dob="
                + dob + ", empid=" + empid + ", first=" + first + ", fuelEfficiencyType=" + fuelEfficiencyType + ", gender=" + gender + ", height=" + height
                + ", info=" + info + ", last=" + last + ", locale=" + locale + ", measurementType=" + measurementType + ", middle=" + middle + ", personID=" + personID
                + ", priEmail=" + priEmail + ", priPhone=" + priPhone + ", priText=" + priText + ", reportsTo=" + reportsTo + ", secEmail=" + secEmail + ", secPhone=" + secPhone
                + ", secText=" + secText + ", status=" + status + ", suffix=" + suffix + ", timeZone=" + timeZone + ", title=" + title + ", warn=" + warn
                + ", weight=" + weight + "]";
    }
}
