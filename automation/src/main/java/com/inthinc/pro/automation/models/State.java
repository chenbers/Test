package com.inthinc.pro.automation.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class State extends BaseEntity implements Comparable<State> {
    private static final long serialVersionUID = -5519902086559670626L;

    private Integer stateID;

    private String name;
    private String abbrev;

    public State() {
        super();
    }

    public State(Integer id, String name, String abbrev) {
        this.stateID = id;
        this.name = name;
        this.abbrev = abbrev;
    }

    public State(StateInfo state, Integer id) {
        this.name = state.getName();
        this.abbrev = state.getCode();
        this.stateID = id;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStateID() {
        return stateID;
    }

    public void setStateID(Integer stateID) {
        this.stateID = stateID;
    }

    public Integer retrieveID() {
        return stateID;
    }

    public static State valueOf(Integer ID) {
        return new State(stateMapArray[ID], ID);
    }

    @Override
    public int compareTo(State state) {
        return state.name.compareToIgnoreCase(this.name);
    }

    @Override
    public String toString() {
        return getName();
    }

    private static StateInfo stateMapArray[] = new StateInfo[] {
        new StateInfo("AL", "Alabama"           , "Ala."  ),
        new StateInfo("AK", "Alaska"            , "Alaska"),
        new StateInfo("AS", "American"          , "Samoa" ),
        new StateInfo("AZ", "Arizona"           , "Ariz." ),
        new StateInfo("AR", "Arkansas"          , "Ark."  ),
        new StateInfo("CA", "California"        , "Calif."),
        new StateInfo("CO", "Colorado"          , "Colo." ),
        new StateInfo("CT", "Connecticut"       , "Conn." ),
        new StateInfo("DE", "Delaware"          , "Del."  ),
        new StateInfo("DC", "Dist. of Columbia" , "D.C."  ),
        new StateInfo("FL", "Florida"           , "Fla."  ),
        new StateInfo("GA", "Georgia"           , "Ga."   ),
        new StateInfo("GU", "Guam"              , "Guam"  ),
        new StateInfo("HI", "Hawaii"            , "Hawaii"),
        new StateInfo("ID", "Idaho"             , "Idaho" ),
        new StateInfo("IL", "Illinois"          , "Ill."  ),
        new StateInfo("IN", "Indiana"           , "Ind."  ),
        new StateInfo("IA", "Iowa"              , "Iowa"  ),
        new StateInfo("KS", "Kansas"            , "Kans." ),
        new StateInfo("KY", "Kentucky"          , "Ky."   ),
        new StateInfo("LA", "Louisiana"         , "La."   ),
        new StateInfo("ME", "Maine"             , "Maine" ),
        new StateInfo("MD", "Maryland"          , "Md."   ),
        new StateInfo("MH", "Marshall Islands"  , "MH"    ),
        new StateInfo("MA", "Massachusetts"     , "Mass." ),
        new StateInfo("MI", "Michigan"          , "Mich." ),
        new StateInfo("FM", "Micronesia"        , "FM"    ),
        new StateInfo("MN", "Minnesota"         , "Minn." ),
        new StateInfo("MS", "Mississippi"       , "Miss." ),
        new StateInfo("MO", "Missouri"          , "Mo."   ),
        new StateInfo("MT", "Montana"           , "Mont." ),
        new StateInfo("NE", "Nebraska"          , "Nebr." ),
        new StateInfo("NV", "Nevada"            , "Nev."  ),
        new StateInfo("NH", "New Hampshire"     , "N.H."  ),
        new StateInfo("NJ", "New Jersey"        , "N.J."  ),
        new StateInfo("NM", "New Mexico"        , "N.M."  ),
        new StateInfo("NY", "New York"          , "N.Y."  ),
        new StateInfo("NC", "North Carolina"    , "N.C."  ),
        new StateInfo("ND", "North Dakota"      , "N.D."  ),
        new StateInfo("MP", "Northern Marianas" , "MP"    ),
        new StateInfo("OH", "Ohio"              , "Ohio"  ),
        new StateInfo("OK", "Oklahoma"          , "Okla." ),
        new StateInfo("OR", "Oregon"            , "Ore."  ),
        new StateInfo("PW", "Palau"             , "PW"    ),
        new StateInfo("PA", "Pennsylvania"      , "Pa."   ),
        new StateInfo("PR", "Puerto Rico"       , "P.R."  ),
        new StateInfo("RI", "Rhode Island"      , "R.I."  ),
        new StateInfo("SC", "South Carolina"    , "S.C."  ),
        new StateInfo("SD", "South Dakota"      , "S.D."  ),
        new StateInfo("TN", "Tennessee"         , "Tenn." ),
        new StateInfo("TX", "Texas"             , "Tex."  ),
        new StateInfo("UT", "Utah"              , "Utah"  ),
        new StateInfo("VT", "Vermont"           , "Vt."   ),
        new StateInfo("VA", "Virginia"          , "Va."   ),
        new StateInfo("VI", "Virgin Islands"    , "V.I."  ),
        new StateInfo("WA", "Washington"        , "Wash." ),
        new StateInfo("WV", "West Virginia"     , "W.Va." ),
        new StateInfo("WI", "Wisconsin"         , "Wis."  ),
        new StateInfo("WY", "Wyoming"           , "Wyo."  ),
    };
    
    private static class StateInfo {

        private String code = null;
        private String name = null;

        public StateInfo(String code, String name, String abbrev) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

    }

    public static State getStateByName(String name) {
        if (name != null) {
            int i = 0;
            for (StateInfo state : stateMapArray) {
                i ++;
                if (state.getName().equalsIgnoreCase(name)) {
                    return new State(state, i);
                }
            }
            return null;
        } else {
            return null;
        }
    }
    
    public static State getStateByAbbrev(String abbrev) {
        if (abbrev != null) {
            int i = 0;
            for (StateInfo state : stateMapArray) {
                i ++;
                if (state.getCode().equalsIgnoreCase(abbrev)) {
                    return new State(state, i);
                }
            }
            return null;
        } else {
            return null;
        }
    }
}
