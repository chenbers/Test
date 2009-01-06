package com.inthinc.pro.model;

import java.util.Date;
import java.util.TimeZone;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class Person extends BaseEntity
{
	
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7162580776394490873L;
	
	@ID
    private Integer          personID;
    private Integer          groupID;
    @Column(name = "tzName")
    private TimeZone         timeZone;
    private Integer          costPerHour; // in cents
    // contact information
    @Column(name = "addrID")
    private Integer          addressID;
    @Column(updateable = false)
    private Address          address;
    private String           homePhone;
    private String           workPhone;
    private String           email;
    // employee information
    private String           empid;
    private Integer          reportsTo;  // userID
    private String           title;
    private String           dept;
    // personal information
    private String           first;
    private String           middle;
    private String           last;
    private String           suffix;
    private Gender           gender;
    private Integer          height;     // inches
    private Integer          weight;     // pounds
    private Date             dob;
    // user, driver (may be null)
    @Column(updateable = false)
    private User   user;
    @Column(updateable = false)
    private Driver driver;
    private Status         status;

    public Person()
    {
        super();
    }
    
    public Person(Integer personID, Integer groupID, TimeZone timeZone, Integer costPerHour, Integer addressID, String homePhone, String workPhone, String email,
            String empid, Integer reportsTo, String title, String dept, String first, String middle, String last, String suffix, Gender gender, Integer height, Integer weight,
            Date dob, Status status)
    {
        super();
        this.personID = personID;
        this.groupID = groupID;
        this.timeZone = timeZone;
        this.costPerHour = costPerHour;
        this.addressID = addressID;
        this.homePhone = homePhone;
        this.workPhone = workPhone;
        this.email = email;
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
        this.dob = dob;
        this.status=status;
    }

    public Integer getPersonID()
    {
        return personID;
    }

    public void setPersonID(Integer personID)
    {
        this.personID = personID;
    }

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }

    public TimeZone getTimeZone()
    {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone)
    {
        this.timeZone = timeZone;
    }

    public Integer getCostPerHour()
    {
        return costPerHour;
    }

    public void setCostPerHour(Integer costPerHour)
    {
        this.costPerHour = costPerHour;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public String getHomePhone()
    {
        return homePhone;
    }

    public void setHomePhone(String homePhone)
    {
        this.homePhone = homePhone;
    }

    public String getWorkPhone()
    {
        return workPhone;
    }

    public void setWorkPhone(String workPhone)
    {
        this.workPhone = workPhone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmpid()
    {
        return empid;
    }

    public void setEmpid(String empid)
    {
        this.empid = empid;
    }

    public Integer getReportsTo()
    {
        return reportsTo;
    }

    public void setReportsTo(Integer reportsTo)
    {
        this.reportsTo = reportsTo;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDept()
    {
        return dept;
    }

    public void setDept(String dept)
    {
        this.dept = dept;
    }
    
    public String getFullName()
    {
    	StringBuffer result = new StringBuffer();
    	if (first != null) result.append(first + " ");
    	if (middle != null) result.append(middle + " ");
    	if (last != null) result.append(last);
    	return result.toString().trim();
    }

    public String getFirst()
    {
        return first;
    }

    public void setFirst(String first)
    {
        this.first = first;
    }

    public String getMiddle()
    {
        return middle;
    }

    public void setMiddle(String middle)
    {
        this.middle = middle;
    }

    public String getLast()
    {
        return last;
    }

    public void setLast(String last)
    {
        this.last = last;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public void setSuffix(String suffix)
    {
        this.suffix = suffix;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public Integer getHeight()
    {
        return height;
    }

    public void setHeight(Integer height)
    {
        if ((height != null) && (height == 0))
            this.height = null;
        else
            this.height = height;
    }

    public Integer getWeight()
    {
        return weight;
    }

    public void setWeight(Integer weight)
    {
        if ((weight != null) && (weight == 0))
            this.weight = null;
        else
            this.weight = weight;
    }

    public Date getDob()
    {
        return dob;
    }

    public void setDob(Date dob)
    {
        this.dob = dob;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Driver getDriver()
    {
        return driver;
    }

    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    public Integer getAddressID()
    {
        return addressID;
    }

    public void setAddressID(Integer addressID)
    {
        this.addressID = addressID;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

}
