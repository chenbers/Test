package com.inthinc.pro.backing.ui;

public enum BreakdownSelections
{
    OVERALL {
        public String toString() {
            return "Overall";
        }
    },

    TWENTYONE {
        public String toString() {
            return "21-30mph";
        }
    },
    
    THIRTYONE {
        public String toString() {
            return "31-40mph";
        }
    },
    
    FORTYONE {
        public String toString() {
            return "41-50mph";
        }
    },
    
    FIFTYFIVE {
        public String toString() {
            return "55-64mph";
        }
    },
    
    SIXTYFIVE {
        public String toString() {
            return "65-80mph";
        }
    }
}
