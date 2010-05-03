package com.inthinc.pro.model;

import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.dao.annotations.SimpleName;

@XmlRootElement
@SimpleName(simpleName="Acct")
public class Account extends BaseEntity
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
	private Integer                   mailID;
    private Integer                   billID;
    private Status                    status;    
    private Integer                   unkDriverID;

    private AccountAttributes         props;

    public Account()
    {
        super();
        props = new AccountAttributes();
    }
    
    public Account(Integer acctID, Integer mailID, Integer billID, Status status)
    {
        super();
        this.acctID = acctID;
        this.mailID = mailID;
        this.billID = billID;
        this.status = status;
    }
    
    public Account(Integer acctID, String acctName, Integer mailID, Integer billID, Status status)
    {
        super();
        this.acctID = acctID;
        this.acctName = acctName;
        this.mailID = mailID;
        this.billID = billID;
        this.status = status;
    }
    
    public Account(Integer acctID, String acctName, Integer mailID, Integer billID, Status status, AccountAttributes props)
    {
        super();
        this.acctID = acctID;
        this.acctName = acctName;
        this.mailID = mailID;
        this.billID = billID;
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

    public Integer getMailID()
    {
        return mailID;
    }

    public void setMailID(Integer mailID)
    {
        this.mailID = mailID;
    }

    public Integer getBillID()
    {
        return billID;
    }

    public void setBillID(Integer billID)
    {
        this.billID = billID;
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

    @Override
    public String toString() {
        return "Account [acctID=" + acctID + ", acctName=" + acctName + ", billID=" + billID + ", mailID=" + mailID + ", status=" + status + ", unkDriverID=" + unkDriverID + "]";
    }

}
