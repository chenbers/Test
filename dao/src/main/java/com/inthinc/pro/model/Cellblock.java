package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlTransient;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.model.phone.CellStatusType;

public class Cellblock {
        @ID
        private Integer driverID;
        private Integer acctID;
        private String cellPhone;
        private CellStatusType cellStatus;
        private CellProviderType provider;
        private String providerUser;
        @Column(name="providerPass")
        private String providerPassword;
        
        public Cellblock() {
            super();
            provider = CellProviderType.UNDEFINED_PROVIDER;
            cellStatus = CellStatusType.ENABLED;
        }
        public String getProviderPassword() {
            return providerPassword;
        }
        @XmlTransient
        public void setProviderPassword(String providerPassword) {
            this.providerPassword = providerPassword;
        }
        public String getCellPhone() {
            return cellPhone;
        }
        public void setCellPhone(String cellPhone) {
            this.cellPhone = cellPhone;
        }
        public CellStatusType getCellStatus() {
            return cellStatus;
        }
        public void setCellStatus(CellStatusType cellStatus) {
            this.cellStatus = cellStatus;
        }
        public CellProviderType getProvider() {
            return provider;
        }
        public void setProvider(CellProviderType provider) {
            this.provider = provider;
        }
        public String getProviderUser() {
            return providerUser;
        }
        public void setProviderUser(String providerUser) {
            this.providerUser = providerUser;
        }
        public Integer getDriverID() {
            return driverID;
        }
        public void setDriverID(Integer driverID) {
            this.driverID = driverID;
        }
        public Integer getAcctID() {
            return acctID;
        }
        public void setAcctID(Integer acctID) {
            this.acctID = acctID;
        }   
}