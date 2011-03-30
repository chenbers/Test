package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlTransient;

import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.model.phone.CellStatusType;

public class Cellblock {
    
        private String cellPhone;
        private CellStatusType cellStatus;
        private CellProviderType provider;
        private String providerUsername;
        
        private String providerPassword;
        
        public Cellblock() {
            super();
            provider = CellProviderType.UNDEFINED_PROVIDER;
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
        public String getProviderUsername() {
            return providerUsername;
        }
        public void setProviderUsername(String providerUsername) {
            this.providerUsername = providerUsername;
        }   
}