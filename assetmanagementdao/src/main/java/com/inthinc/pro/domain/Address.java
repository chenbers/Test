package com.inthinc.pro.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="address")
public class Address{

    /**
     * 
     */
    private static final long serialVersionUID = 2811114744532172543L;
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    Integer addrID;
    String addr1;
    String addr2;
    String city;
	@ManyToOne
	@JoinColumn(name="stateID")
	@Basic(fetch=FetchType.LAZY)
    State state;
    String zip;
    @Column(name="acctID")
	Integer accountID;

    public Address() {
        super();
    }

    public Address(Integer addrID, String addr1, String addr2, String city, State state, String zip, Integer accountID) {
        super();
        this.addrID = addrID;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.accountID = accountID;
    }

    public Integer getAddrID() {
        return addrID;
    }

    public void setAddrID(Integer addrID) {
        this.addrID = addrID;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    public Integer getAccountID() {
		return accountID;
	}

	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}
	
    @Override
    public String toString() {
        return "Address [addr1=" + addr1 + ", addr2=" + addr2 + ", addrID=" + addrID + ", city=" + city + ", state=" + state + ", zip=" + zip + ", accountID=" + accountID + "]";
    }
    
    public String getDisplayString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append((addr1 == null) ? "" : addr1);
        buffer.append((addr2 == null) ? "" : (", " + addr2));
        buffer.append((city == null) ? "" : (", " + city));
        buffer.append((state == null) ? "" : (", " + state.getAbbrev()));
        buffer.append((zip == null) ? "" : (", " + zip));
        if (buffer.length() > 0 && buffer.charAt(0) == ',')
            buffer.deleteCharAt(0);
        
        return buffer.toString();
        
    }
    public Boolean isEmpty(){
        return (addr1 == null || addr1.isEmpty()) && 
        	   (addr2 == null || addr2.isEmpty()) && 
        	   (city == null || city.isEmpty()) && 
        	   (state == null ) && 
        	   (zip == null || zip.isEmpty());
    }
}
