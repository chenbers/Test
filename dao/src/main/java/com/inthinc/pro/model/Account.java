package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.dao.annotations.SimpleName;

@XmlRootElement
@SimpleName(simpleName="Acct")
public class Account extends BaseEntity implements Comparable<Account> 
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2388000038869935798L;

	//TODO: refactor names of fields. Remove the prefix acct. Acct is in
	@ID
    private Integer                   acctID;	
	@Column(name="name")
	private String                    acctName;
	
    private Status                    status;    
    private Integer                   unkDriverID;

    private AccountAttributes         props;
    
    @Column(name="mailID")
    private Integer                   addressID;
    
    // not currently used
    private Integer                   billID;   


    @Column(updateable=false)
    private Address                   address;
    
    private AccountHOSType            hos;

    public Account()
    {
        super();
        props = new AccountAttributes();
    }
    
    public Account(Integer acctID, Status status)
    {
        super();
        this.acctID = acctID;
        this.status = status;
    }
    
    public Account(Integer acctID, String acctName, Status status)
    {
        super();
        this.acctID = acctID;
        this.acctName = acctName;
        this.status = status;
    }
    
    public Account(Integer acctID, String acctName, Status status, AccountAttributes props)
    {
        super();
        this.acctID = acctID;
        this.acctName = acctName;
        this.status = status;
        this.props = props;
    }    

    public Integer getAcctID()
    {
        return acctID;
    }

    public void setAcctID(Integer acctID)
    {
        this.acctID = acctID;
    }


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

    
    @Override
    public int compareTo(Account otherAccount) {
        
        return acctName.compareToIgnoreCase(otherAccount.getAcctName());
    }

    @Override
    public String toString() {
        return "Account [acctID=" + acctID + ", acctName=" + acctName + ", status=" + status + ", unkDriverID=" + unkDriverID + "]";
    }

}
