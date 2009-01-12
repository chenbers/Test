package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.dao.annotations.SimpleName;

@SimpleName(simpleName="Acct")
public class Account extends BaseEntity
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2388000038869935798L;

	@ID
    private Integer       acctID;
	
	@Column(name="name")
	private String        acctName;

	private Integer       mailID;
    private Integer       billID;
    private Status status;

    public Account()
    {
        super();
    }
    
    public Account(Integer acctID, Integer mailID, Integer billID, Status status)
    {
        super();
        this.acctID = acctID;
        this.mailID = mailID;
        this.billID = billID;
        this.status = status;
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

}
