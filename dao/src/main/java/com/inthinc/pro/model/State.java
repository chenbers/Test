package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;

import com.inthinc.pro.dao.annotations.ID;

public enum State implements BaseEnum
{
    AL(1, "AL", "Alabama"),
    AK(2, "AK", "Alaska"),
    AZ(3, "AZ", "Arizona"),
    AR(4, "AR", "Arkansas"),
    CA(5, "CA", "California"),
    CO(6, "CO", "Colorado"),
    CT(7, "CT", "Connecticut"),
    DE(8, "DE", "Delaware"),
    DC(9, "DC", "District of Columbia"),
    FL(10, "FL", "Florida"),
    GA(11, "GA", "Georgia"),
    HI(12, "HI", "Hawaii"),
    ID(13, "ID", "Idaho"),
    IL(14, "IL", "Illinois"),
    IN(15, "IN", "Indiana"),
    IA(16, "IA", "Iowa"),
    KS(17, "KS", "Kansas"),
    KY(18, "KY", "Kentucky"),
    LA(19, "LA", "Louisiana"),
    ME(20, "ME", "Maine"),
    MD(21, "MD", "Maryland"),
    MA(22, "MA", "Massachusetts"),
    MI(23, "MI", "Michigan"),
    MN(24, "MN", "Minnesota"),
    MS(25, "MS", "Mississippi"),
    MO(26, "MO", "Missouri"),
    MT(27, "MT", "Montana"),
    NE(28, "NE", "Nebraska"),
    NV(29, "NV", "Nevada"),
    NH(30, "NH", "New Hampshire"),
    NJ(31, "NJ", "New Jersey"),
    NM(32, "NM", "New Mexico"),
    NY(33, "NY", "New York"),
    NC(34, "NC", "North Carolina"),
    ND(35, "ND", "North Dakota"),
    OH(36, "OH", "Ohio"),
    OK(37, "OK", "Oklahoma"),
    OR(38, "OR", "Oregon"),
    PA(39, "PA", "Pennsylvania"),
    RI(40, "RI", "Rhode Island"),
    SC(41, "SC", "South Carolina"),
    SD(42, "SD", "South Dakota"),
    TN(43, "TN", "Tennessee"),
    TX(44, "TX", "Texas"),
    UT(45, "UT", "Utah"),
    VT(46, "VT", "Vermont"),
    VA(47, "VA", "Virginia"),
    WA(48, "WA", "Washington"),
    WV(49, "WV", "West Virginia"),
    WI(50, "WI", "Wisconsin"),
    WY(51, "WY", "Wyoming"),

    AS(52, "AS", "American Samoa"),
    FM(53, "FM", "Federated States of Micronesia"),
    GU(54, "GU", "Guam"),
    MH(55, "MH", "Marshall Islands"),
    MP(56, "MP", "Northern Mariana Islands"),
    PW(57, "PW", "Palau"),
    PR(58, "PR", "Puerto Rico"),
    VI(59, "VI", "Virgin Islands"),
    UM(60, "UM", "U.S. Minor Outlying Islands"),

    AE_A(61, "AE", "Armed Forces Africa"),
    AA(62, "AA", "Armed Forces Americas"),
    AE_C(63, "AE", "Armed Forces Canada"),
    AE_E(64, "AE", "Armed Forces Europe"),
    AE_ME(65, "AE", "Armed Forces Middle East"),
    AP(66, "AP", "Armed Forces Pacific");

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

    @Override
    public Integer getCode()
    {
        return stateID;
    }

    public String getAbbrev()
    {
        return abbrev;
    }

    public String getName()
    {
        return name;
    }

    private static final HashMap<Integer, State> lookup = new HashMap<Integer, State>();
    static
    {
        for (State p : EnumSet.allOf(State.class))
        {
            lookup.put(p.stateID, p);
        }
    }

    public static State valueOf(Integer stateID)
    {
        return lookup.get(stateID);
    }
}
