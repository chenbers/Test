package com.inthinc.pro.backing.importer;

import com.inthinc.pro.backing.importer.validator.Validator;
import com.inthinc.pro.backing.importer.validator.StringValidator;

public class ColumnFormat {
    
    private String name;
    private boolean manditory;
    private int maxLength;
    private Validator validator;
    
    public ColumnFormat(String name, boolean manditory, int maxLength) {
        super();
        this.name = name;
        this.manditory = manditory;
        this.maxLength = maxLength;
        validator = new StringValidator(maxLength);
    }
    
    public ColumnFormat(String name, boolean manditory, Validator validator) {
        super();
        this.name = name;
        this.manditory = manditory;
        this.validator = validator;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setManditory(boolean manditory) {
        this.manditory = manditory;
    }
    public boolean isManditory() {
        return manditory;
    }
    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
