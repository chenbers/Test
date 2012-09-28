package com.inthinc.pro.model;

import java.io.Serializable;

/**
 * Enums that extend BaseEnum should also have a static method valueOf(Integer), like below:
 * 
 * <pre>
 * private static final HashMap&lt;Integer, State&gt; lookup = new HashMap&lt;Integer, State&gt;();
 * static
 * {
 *     for (State p : EnumSet.allOf(State.class))
 *     {
 *         lookup.put(p.stateID, p);
 *     }
 * }
 * 
 * public static State valueOf(Integer stateID)
 * {
 *     return lookup.get(stateID);
 * }
 * </pre>
 */
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface BaseEnum extends Serializable
{
    public Integer getCode();
}
