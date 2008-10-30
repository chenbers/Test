package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.dao.annotations.ID;

public enum State
{
    AK(1, "AK", "Alaska"),
    AL(2, "AL", "Alabama"),
    AR(3, "AR", "Arkansas"),
    AZ(4, "AZ", "Arizona"),
    CA(5, "CA", "California"),
    CO(6, "CO", "Colorado"),
    CT(7, "CT", "Connecticut"),
    DC(8, "DC", "Washington D.C."),
    DE(9, "DE", "Delaware"),
    FL(10, "FL", "Florida"),
    GA(11, "GA", "Georgia"),
    HI(12, "HI", "Hawaii"),
    IA(13, "IA", "Iowa"),
    ID(14, "ID", "Idaho"),
    IL(15, "IL", "Illinois"),
    IN(16, "IN", "Indiana"),
    KS(17, "KS", "Kansas"),
    KY(18, "KY", "Kentucky"),
    LA(19, "LA", "Louisiana"),
    MA(20, "MA", "Massachusetts"),
    MD(21, "MD", "Maryland"),
    ME(22, "ME", "Maine"),
    MI(23, "MI", "Michigan"),
    MN(24, "MN", "Minnesota"),
    MO(25, "MO", "Missourri"),
    MS(26, "MS", "Mississippi"),
    MT(27, "MT", "Montana"),
    NC(28, "NC", "North Carolina"),
    ND(29, "ND", "North Dakota"),
    NE(30, "NE", "Nebraska"),
    NH(31, "NH", "New Hampshire"),
    NJ(32, "NJ", "New Jersey"),
    NM(33, "NM", "New Mexico"),
    NV(34, "NV", "Nevada"),
    NY(35, "NY", "New York"),
    OH(36, "OH", "Ohio"),
    OK(37, "OK", "Oklahoma"),
    OR(38, "OR", "Oregon"),
    PA(39, "PA", "Pennsylvania"),
    PR(40, "PR", "Puerto Rico"),
    RI(41, "RI", "Rhode Island"),
    SC(42, "SC", "South Carolina"),
    SD(43, "SD", "South Dakota"),
    TN(44, "TN", "Tennessee"),
    TX(45, "TX", "Texas"),
    UT(46, "UT", "Utah"),
    VA(47, "VA", "Virginia"),
    VT(48, "VT", "Vermont"),
    WA(49, "WA", "Washington"),
    WI(50, "WI", "Wisconsin"),
    WV(51, "WV", "West Virginia"),
    WY(52, "WY", "Wyoming");

    @ID
    private Integer stateID;
    private String  name;
    private String  abbrev;

    private State(Integer stateID, String abbrev, String name)
    {
        this.stateID = stateID;
        this.abbrev = abbrev;
        this.name = name;
    }

    public String getAbbrev()
    {
        return abbrev;
    }

    public void setAbbrev(String abbrev)
    {
        this.abbrev = abbrev;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    private static final Map<String, State> lookup = new HashMap<String, State>();
    static
    {
        for (State p : EnumSet.allOf(State.class))
        {
            lookup.put(p.abbrev, p);
        }
    }

    public static State getState(String abbrev)
    {
        return lookup.get(abbrev);
    }
}
