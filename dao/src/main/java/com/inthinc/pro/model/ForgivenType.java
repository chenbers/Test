package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum ForgivenType implements BaseEnum {
        EXCLUDE(0), INCLUDE(1);
        private int code;

        ForgivenType(int code) {
            this.code = code;
        }
        private static final Map<Integer, ForgivenType> lookup = new HashMap<Integer, ForgivenType>();
        static {
            for (ForgivenType p : EnumSet.allOf(ForgivenType.class)) {
                lookup.put(p.code, p);
            }
        }
        
        public static ForgivenType getForgivenType(Integer code) {
            return lookup.get(code);
        }
        
        @Override
        public Integer getCode() {
            return this.code;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
            sb.append(".");
            sb.append(this.name());
            return sb.toString();
        }
}
