package org.jbehave.core.steps;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

public class StepCandidateBehaviour {


    public static StepCandidate candidateMatchingStep(List<StepCandidate> candidates, String stepAsString) {
        for (StepCandidate candidate : candidates) {
            if (candidate.matches(stepAsString)){
                return candidate;
            } else if (Pattern.matches(candidate.getPatternAsString(), stepAsString)){
                return candidate;
            } 
        }
        return null;
    }    


    public static Method stepMethodFor(String methodName, Class<? extends Steps> stepsClass) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(stepsClass);
        for (MethodDescriptor md : beanInfo.getMethodDescriptors()) {
            if (md.getMethod().getName().equals(methodName)) {
                return md.getMethod();
            }
        }
        return null;
    }

}
