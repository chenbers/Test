package com.inthinc.pro.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.inthinc.pro.configurator.model.Status;

@Entity
@Table(name="user")
public class User{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Integer userID;
	@OneToOne
	@JoinColumn(name="personID")
	@Basic(fetch=FetchType.LAZY)
	private Person person;
//    private List<Integer> roles;
//    private List<AccessPoint> accessPoints;
    private Status status;
    private String username;
    private String password;
    private Integer groupID;
	@Temporal(TemporalType.DATE)
    private Date lastLogin;
	@Temporal(TemporalType.DATE)
    private Date passwordDT;
	@Temporal(TemporalType.DATE)
    private Date modified;

//    public User(Integer userID, Integer personID, Status status, String username, String password, Integer groupID) {
//        super();
//        this.userID = userID;
//        this.personID = personID;
////        this.roles = roles;
//        this.status = status;
//        this.username = username;
//        this.password = password;
//        this.groupID = groupID;
//    }

    public User() {
        super();
//        roles = new ArrayList<Integer>();
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }


//	public List<Integer> getRoles() {
//		return roles;
//	}
//
//	public void setRoles(List<Integer> roles) {
//		this.roles = roles;
//	}

//	public List<AccessPoint> getAccessPoints() {
//		return accessPoints;
//	}
//
//	public void setAccessPoints(List<AccessPoint> accessPoints) {
//		this.accessPoints = accessPoints;
//	}

    @Override
	public String toString() {
		return "User [userID=" + userID + ", person=" + person + ", status="
				+ status + ", username=" + username + ", password=" + password
				+ ", groupID=" + groupID + ", lastLogin=" + lastLogin
				+ ", passwordDT=" + passwordDT + ", modified=" + modified + "]";
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setPasswordDT(Date passwordDT) {
        this.passwordDT = passwordDT;
    }

    public Date getPasswordDT() {
        return passwordDT;
    }

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}
}
