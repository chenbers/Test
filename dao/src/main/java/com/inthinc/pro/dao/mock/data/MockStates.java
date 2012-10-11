package com.inthinc.pro.dao.mock.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.State;

public class MockStates
{
    static Map<Integer, State> allStates = new HashMap<Integer, State>();
    static
    {
        Integer key = 1;
        key = 1;
        allStates.put(key, new State(key++, "Alabama", "AL"));
        allStates.put(key, new State(key++, "Alaska", "AK"));
        allStates.put(key, new State(key++, "Arizona", "AZ"));
        allStates.put(key, new State(key++, "Arkansas", "AR"));
        allStates.put(key, new State(key++, "California", "CA")); // 5
        allStates.put(key, new State(key++, "Colorado", "CO"));
        allStates.put(key, new State(key++, "Connecticut", "CT"));
        allStates.put(key, new State(key++, "Delaware", "DE"));
        allStates.put(key, new State(key++, "District of Columbia", "DC"));
        allStates.put(key, new State(key++, "Florida", "FL"));
        allStates.put(key, new State(key++, "Georgia", "GA"));
        allStates.put(key, new State(key++, "Hawaii", "HI"));
        allStates.put(key, new State(key++, "Idaho", "ID"));
        allStates.put(key, new State(key++, "Illinois", "IL"));
        allStates.put(key, new State(key++, "Indiana", "IN"));
        allStates.put(key, new State(key++, "Iowa", "IA"));
        allStates.put(key, new State(key++, "Kansas", "KS"));
        allStates.put(key, new State(key++, "Kentucky", "KY"));
        allStates.put(key, new State(key++, "Louisiana", "LA"));
        allStates.put(key, new State(key++, "Maine", "ME"));
        allStates.put(key, new State(key++, "Maryland", "MD"));
        allStates.put(key, new State(key++, "Massachusetts", "MA"));
        allStates.put(key, new State(key++, "Michigan", "MI"));
        allStates.put(key, new State(key++, "Minnesota", "MN"));
        allStates.put(key, new State(key++, "Mississippi", "MS"));
        allStates.put(key, new State(key++, "Missouri", "MO"));
        allStates.put(key, new State(key++, "Montana", "MT"));
        allStates.put(key, new State(key++, "Nebraska", "NE"));
        allStates.put(key, new State(key++, "Nevada", "NV"));
        allStates.put(key, new State(key++, "New Hampshire", "NH"));
        allStates.put(key, new State(key++, "New Jersey", "NJ"));
        allStates.put(key, new State(key++, "New Mexico", "NM"));
        allStates.put(key, new State(key++, "New York", "NY"));
        allStates.put(key, new State(key++, "North Carolina", "NC"));
        allStates.put(key, new State(key++, "North Dakota", "ND"));
        allStates.put(key, new State(key++, "Ohio", "OH"));
        allStates.put(key, new State(key++, "Oklahoma", "OK"));
        allStates.put(key, new State(key++, "Oregon", "OR"));
        allStates.put(key, new State(key++, "Pennsylvania", "PA"));
        allStates.put(key, new State(key++, "Rhode Island", "RI"));
        allStates.put(key, new State(key++, "South Carolina", "SC"));
        allStates.put(key, new State(key++, "South Dakota", "SD"));
        allStates.put(key, new State(key++, "Tennessee", "TN"));
        allStates.put(key, new State(key++, "Texas", "TX"));
        allStates.put(key, new State(key++, "Utah", "UT")); // 45
        allStates.put(key, new State(key++, "Vermont", "VT"));
        allStates.put(key, new State(key++, "Virginia", "VA"));
        allStates.put(key, new State(key++, "Washington", "WA"));
        allStates.put(key, new State(key++, "West Virginia", "WV"));
        allStates.put(key, new State(key++, "Wisconsin", "WI"));
        allStates.put(key, new State(key++, "Wyoming", "WY"));
    }
    
    public static State randomState()
    {
        Integer key = randomInt(1, allStates.size());
        return allStates.get(key);
    }
    public static State getByAbbrev(String abbrev)
    {
        for (State state : allStates.values())
        {
            if (state.getAbbrev().equals(abbrev))
                return state;
        }
        
        return null;
    }
    static int randomInt(int min, int max)
    {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
    
    public static List<State> getAll()
    {
        
        List<State> stateList = new ArrayList<State>(allStates.values());
        return stateList;
    }

}
