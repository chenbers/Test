package com.inthinc.pro.backing.importer.validator;

import com.inthinc.pro.model.app.States;

public class StateValidator  extends Validator {

    @Override
    public String getInvalidMessage(String value) {
        return "The state code: " + value + "  is invalid.";
    }

    @Override
    public boolean isValid(String value) {
        return States.getStateByAbbrev(value) != null;
    }

}
