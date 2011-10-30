package com.inthinc.pro.domain;

import java.util.Date;
import java.util.Locale;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.inthinc.pro.configurator.model.FuelEfficiencyType;
import com.inthinc.pro.configurator.model.Gender;
import com.inthinc.pro.configurator.model.MeasurementType;
import com.inthinc.pro.configurator.model.Status;

@Entity
@Table(name="person")
public class Person implements Comparable<Person> {

    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Integer personID;
	@ManyToOne
	@JoinColumn(name="tzID")
	@Basic(fetch=FetchType.LAZY)
	private SupportedTimeZone timeZone;
    // contact information
	@OneToOne
	@JoinColumn(name="addressID")
	@Basic(fetch=FetchType.LAZY)
    private Address address;
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
    private String empid;
    private String reportsTo;
    private String title;
    private String dept;
    // personal information
    private String first;
    private String middle;
    private String last;
    private String suffix;
    private Gender gender;
    private Integer height; // inches
    private Integer weight; // pounds
	@Temporal(TemporalType.DATE)
    private Date dob;
    // user, driver (may be null)
	@OneToOne
	@JoinColumn(name="userID")
	@Basic(fetch=FetchType.LAZY)
    private User user;
	@OneToOne
	@JoinColumn(name="driverID")
	@Basic(fetch=FetchType.LAZY)
    private Driver driver;
    private Status status;
	@ManyToOne
	@JoinColumn(name="acctID")
	@Basic(fetch=FetchType.LAZY)
	private Account  account;
    private MeasurementType measurementType;
    @Column(name = "fuelEffType")
    private FuelEfficiencyType fuelEfficiencyType;
    
    private Locale locale;

    public Person() {
        super();
    }
//    public Person(Integer personID, Integer acctID, TimeZone timeZone, Integer addressID, String priEmail, String secEmail, String priPhone, String secPhone,
//            String priText, String secText, Integer info, Integer warn, Integer crit, String empid, String reportsTo, String title, String dept, String first, String middle,
//            String last, String suffix, Gender gender, Integer height, Integer weight, Date dob, Status status, MeasurementType measurementType,
//            FuelEfficiencyType fuelEfficiencyType, Locale locale) {
//        super();
//        this.acctID = acctID;
//        this.personID = personID;
//        this.timeZone = timeZone;
//        this.addressID = addressID;
//        this.priEmail = priEmail;
//        this.secEmail = secEmail;
//        this.priPhone = priPhone;
//        this.secPhone = secPhone;
//        this.priText = priText;
//        this.secText = secText;
//        this.info = info;
//        this.warn = warn;
//        this.crit = crit;
//        this.empid = empid;
//        this.reportsTo = reportsTo;
//        this.title = title;
//        this.dept = dept;
//        this.first = first;
//        this.middle = middle;
//        this.last = last;
//        this.suffix = suffix;
//        this.gender = gender;
//        this.height = height;
//        this.weight = weight;
//        this.dob = dob;
//        this.status = status;
//        this.measurementType = measurementType;
//        this.fuelEfficiencyType = fuelEfficiencyType;
//        this.locale = locale;
//    }

    public Integer getPersonID() {
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

//    @XmlJavaTypeAdapter(value=com.inthinc.pro.model.adapter.TimeZoneXmlAdapter.class)
//    public TimeZone getTimeZone() {
//        return timeZone;
//    }
//    public void setTimeZone(TimeZone timeZone) {
//        this.timeZone = timeZone;
//    }
//
//    public String getDisplayTimeZone() {
//        if (timeZone != null)
//            return timeZone.getDisplayName(timeZone.inDaylightTime(new GregorianCalendar(timeZone).getTime()), TimeZone.LONG);
//        else
//            return null;
//    }

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

    public String getFullName() {
        StringBuilder result = new StringBuilder();
        if (first != null)
            result.append(first);
        if (middle != null && !middle.isEmpty()) {
            if (result.length() > 0)
                result.append(' ');
            result.append(middle);
        }
        if (last != null && !last.isEmpty()) {
            if (result.length() > 0)
                result.append(' ');
            result.append(last);
        }
        if (suffix != null && !suffix.isEmpty()) {
            if (result.length() > 0)
                result.append(' ');
            result.append(suffix);
        }
        return result.toString();
    }
    public String getFullNameLastFirst() {
        StringBuilder result = new StringBuilder();
        if (last != null) {
            result.append(last);
            result.append(",");
        }
        if (first != null && !first.isEmpty()) {
            if (result.length() > 0)
                result.append(' ');
            result.append(first);
        }
        if (middle != null && !middle.isEmpty()) {
            if (result.length() > 0)
                result.append(' ');
            result.append(middle);
        }
        if (suffix != null && !suffix.isEmpty()) {
            if (result.length() > 0)
                result.append(' ');
            result.append(suffix);
        }
        return result.toString();
    }

    public String getFullNameWithId() {
        StringBuilder result = new StringBuilder();
        if (empid != null && !empid.isEmpty()) {
            result.append(getFullName());
            result.append(' ');
            result.append("(" + empid + ")");
        }
        else {
            result.append(getFullName());
        }
        return result.toString();
    }
    public String getFullNameWithPriEmail() {
        StringBuilder result = new StringBuilder();
        if (this.priEmail != null && !this.priEmail.isEmpty()) {
            result.append(getFullName());
            result.append(' ');
            result.append("[ " + this.priEmail + " ]");
        }
        else {
            result.append(getFullName());
        }
        return result.toString();
    }
    public String getFullNameWithPriPhone() {
        StringBuilder result = new StringBuilder();
        if (this.priPhone != null && !this.priPhone.isEmpty()) {
            result.append(getFullName());
            result.append(' ');
            result.append("[ " + this.priPhone + " ]");
        }
        else {
            result.append(getFullName());
        }
        return result.toString();
    }
    
    public String getFullNameWithSecPhone() {
        StringBuilder result = new StringBuilder();
        if (this.secPhone != null && !this.secPhone.isEmpty()) {
            result.append(getFullName());
            result.append(' ');
            result.append("[ " + this.secPhone + " ]");
        }
        else {
            result.append(getFullName());
        }
        return result.toString();
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUserID() {
    	if(user==null)
    		return null;
        return user.getUserID();
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Integer getDriverID() {
    	if(driver==null)
    		return null;
        return driver.getDriverID();
    }
    
//    public Integer getAddressID() {
//        return addressID;
//    }
//
//    public void setAddressID(Integer addressID) {
//        this.addressID = addressID;
//    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

//    public Integer getAcctID()
//    {
//        return acctID;
//    }

    /*
     * Note: The getAcctID() method should be flagged as deprecated and this method should be used instead.
     */
//    public Integer getAccountID()
//    {
//        return acctID;
//    }
//
//    public void setAcctID(Integer acctID) {
//        this.acctID = acctID;
//    }

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

//    @XmlJavaTypeAdapter(value=com.inthinc.pro.model.adapter.LocaleXmlAdapter.class)
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

	public SupportedTimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(SupportedTimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Person [personID=" + personID + ", timeZone=" + timeZone
				+ ", address=" + address + ", priEmail=" + priEmail
				+ ", secEmail=" + secEmail + ", priPhone=" + priPhone
				+ ", secPhone=" + secPhone + ", priText=" + priText
				+ ", secText=" + secText + ", info=" + info + ", warn=" + warn
				+ ", crit=" + crit + ", empid=" + empid + ", reportsTo="
				+ reportsTo + ", title=" + title + ", dept=" + dept
				+ ", first=" + first + ", middle=" + middle + ", last=" + last
				+ ", suffix=" + suffix + ", gender=" + gender + ", height="
				+ height + ", weight=" + weight + ", dob=" + dob + ", user="
				+ user + ", driver=" + driver + ", status=" + status
				+ ", account=" + account + ", measurementType="
				+ measurementType + ", fuelEfficiencyType="
				+ fuelEfficiencyType + ", locale=" + locale + "]";
	}


//    @Override
//    public String toString() {
//        return "Person [acctID=" + acctID + ", address=" + address + ", addressID=" + addressID + ", crit=" + crit + ", dept=" + dept + ", dob="
//                + dob + ", empid=" + empid + ", first=" + first + ", fuelEfficiencyType=" + fuelEfficiencyType + ", gender=" + gender + ", height=" + height
//                + ", info=" + info + ", last=" + last + ", locale=" + locale + ", measurementType=" + measurementType + ", middle=" + middle + ", personID=" + personID
//                + ", priEmail=" + priEmail + ", priPhone=" + priPhone + ", priText=" + priText + ", reportsTo=" + reportsTo + ", secEmail=" + secEmail + ", secPhone=" + secPhone
//                + ", secText=" + secText + ", status=" + status + ", suffix=" + suffix + ", timeZone=" + timeZone + ", title=" + title + ", warn=" + warn
//                + ", weight=" + weight + "]";
//    }
}
