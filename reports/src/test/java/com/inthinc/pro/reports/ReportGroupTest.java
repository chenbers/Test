package com.inthinc.pro.reports;

import static org.junit.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ReportGroupTest {
    @Test
    public void codeValuesMustBeUnique() {
        Set<Integer> codes = new HashSet<Integer>();
        for (ReportGroup group : ReportGroup.values()) {
            assertFalse(codes.contains(group.getCode()));
            codes.add(group.getCode());
        }
    }
}
