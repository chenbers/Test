package com.inthinc.pro.automation.models;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Account extends BaseEntity implements Comparable<Account> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2388000038869935798L;

    private Integer                   accountID;	
	private String                    acctName;
	
    private Status                    status;    
    private Integer                   unkDriverID;

    private AccountAttributes         props;
    
    private Integer                   addressID;
    
    // not currently used
    private Integer                   billID;   


    private Address                   address;
    
    private AccountHOSType            hos;
    
    private Date    zonePublishDate;                      

    public Account()
    {
        super();
        props = new AccountAttributes();
    }
    
    public Account(Integer accountID, Status status)
    {
        super();
        this.accountID = accountID;
        this.status = status;
    }
    
    public Account(Integer accountID, String acctName, Status status)
    {
        super();
        this.accountID = accountID;
        this.acctName = acctName;
        this.status = status;
    }
    
    public Account(Integer accountID, String acctName, Status status, AccountAttributes props)
    {
        super();
        this.accountID = accountID;
        this.acctName = acctName;
        this.status = status;
        this.props = props;
    }    
    public Integer getAccountID()
    {
        return accountID;
    }
    public void setAccountID(Integer acctID)
    {
        this.accountID = acctID;
    }

    /*
     * Note: The getAcctID() method should be flagged as deprecated and this method should be used instead.
     */
//    public Integer getAccountID()
//    {
//        return accountID;
//    }
//
//    public void setAccountID(Integer accountID)
//    {
//        this.accountID = accountID;
//    }


    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }
    public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

    public Integer getUnkDriverID() {
        return unkDriverID;
    }

    public void setUnkDriverID(Integer unkDriverID) {
        this.unkDriverID = unkDriverID;
    }

    public AccountAttributes getProps() {
        return props;
    }

    public void setProps(AccountAttributes props) {
        this.props = props;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getAddressID() {
        return addressID;
    }

    public void setAddressID(Integer addressID) {
        this.addressID = addressID;
    }

    public Integer getBillID() {
        return billID;
    }

    public void setBillID(Integer billID) {
        this.billID = billID;
    }

    public AccountHOSType getHos() {
        return hos;
    }

    public void setHos(AccountHOSType hos) {
        this.hos = hos;
    }

    public Date getZonePublishDate() {
        return zonePublishDate;
    }

    public void setZonePublishDate(Date zonePublishDate) {
        this.zonePublishDate = zonePublishDate;
    }

    
    @Override
    public int compareTo(Account otherAccount) {
        
        return acctName.compareToIgnoreCase(otherAccount.getAcctName());
    }

    @Override
    public String toString() {
        return "Account [accountID=" + accountID + ", acctName=" + acctName + ", status=" + status + ", unkDriverID=" + unkDriverID + "]";
    }
    
    public Boolean hasWaySmartSupport() {
        AccountAttributes options = getProps();
        if (options == null)
            return false;
        
        String waySmart = options.getWaySmart();
        if (waySmart == null)
            return false;
        
        return Boolean.valueOf(waySmart);
        
    }
}
