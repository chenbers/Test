package com.inthinc.pro.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.inthinc.pro.configurator.model.AccountHOSType;
import com.inthinc.pro.configurator.model.Status;

@Entity
@Table(name="account")
public class Account implements Comparable<Account>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2388000038869935798L;

	//TODO: refactor names of fields. Remove the prefix acct. Acct is in
	@Id
    @Column(name="acctID")
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Integer                   accountID;	
	@Column(name="name")
	private String                    acctName;
	
    private Status                    status;    
    private Integer                   unkDriverID;
    
    @OneToMany(mappedBy="acctID")
	@Basic(fetch=FetchType.LAZY)
    private Collection<AccountProperty>         accountProperties;
    
    public Account() {
		super();
		accountProperties = new ArrayList<AccountProperty>();
	}

	@Column(name="mailID")
    private Integer                   addressID;
    
    // not currently used
    private Integer                   billID;   


	@OneToOne
	@JoinColumn(name="addressID")
	@Basic(fetch=FetchType.LAZY)
    private Address                   address;
    
    private AccountHOSType            hos;
    
	@Temporal(TemporalType.TIMESTAMP)
    private Date    zonePublishDate;                      

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
    
//    public Account(Integer accountID, String acctName, Status status, AccountAttributes props)
//    {
//        super();
//        this.accountID = accountID;
//        this.acctName = acctName;
//        this.status = status;
//        this.props = props;
//    }    
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
    public Collection<AccountProperty> getAccountProperties() {
		return accountProperties;
	}

	public void setAccountProperties(Collection<AccountProperty> accountProperties) {
		this.accountProperties = accountProperties;
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
    
}
