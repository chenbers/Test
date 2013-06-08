package it.util.hos;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;

public class TestCaseResults {

    RuleSetType dotType;
    RuleViolationTypes expectedViolationType;
    int expectedOnDutyRemaining;
    int expectedDrivingRemaining;
    
    boolean propcarry14hrValid;

    public RuleSetType getDotType() {
        return dotType;
    }

    public void setDotType(RuleSetType dotType) {
        this.dotType = dotType;
    }

    public int getExpectedDrivingRemaining() {
        return expectedDrivingRemaining;
    }

	public void setExpectedDrivingRemaining(int expectedDrivingRemaining) {
        this.expectedDrivingRemaining = expectedDrivingRemaining;
    }

    public int getExpectedOnDutyRemaining() {
        return expectedOnDutyRemaining;
    }

    public void setExpectedOnDutyRemaining(int expectedOnDutyRemaining) {
        this.expectedOnDutyRemaining = expectedOnDutyRemaining;
    }

    public RuleViolationTypes getExpectedViolationType() {
        return expectedViolationType;
    }

    public void setExpectedViolationType(RuleViolationTypes expectedViolationType) {
        this.expectedViolationType = expectedViolationType;
    }

    public boolean isPropcarry14hrValid() {
		return propcarry14hrValid;
	}

	public void setPropcarry14hrValid(boolean propcarry14hrValid) {
		this.propcarry14hrValid = propcarry14hrValid;
	}
}
