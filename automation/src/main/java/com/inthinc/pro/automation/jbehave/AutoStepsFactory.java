package com.inthinc.pro.automation.jbehave;

import static java.util.Arrays.asList;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.annotations.AsParameterConverter;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters.MethodReturningConverter;
import org.jbehave.core.steps.ParameterConverters.ParameterConverter;

public class AutoStepsFactory extends InstanceStepsFactory {
    
    private final Configuration configuration;

    public AutoStepsFactory(Configuration configuration, Object... stepsInstances) {
        this(configuration, asList(stepsInstances));
    }

    public AutoStepsFactory(Configuration configuration, List<Object> stepsInstances) {
        super(configuration, stepsInstances);
        this.configuration = configuration;
    }
    
    @Override
    public List<CandidateSteps> createCandidateSteps() {
        List<Class<?>> types = stepsTypes();
        List<CandidateSteps> steps = new ArrayList<CandidateSteps>();
        for (Class<?> type : types) {
            configuration.parameterConverters().addConverters(
                    methodReturningConverters(type));
            steps.add(new AutoSteps(configuration, type, this));
        }
        return steps;
    }
    
    /**
     * Create parameter converters from methods annotated with @AsParameterConverter
     */
    private List<ParameterConverter> methodReturningConverters(Class<?> type) {
        List<ParameterConverter> converters = new ArrayList<ParameterConverter>();

        for (Method method : type.getMethods()) {
            if (method.isAnnotationPresent(AsParameterConverter.class)) {
                converters.add(new MethodReturningConverter(method, type, this));
            }
        }

        return converters;
    }

}
