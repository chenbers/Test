package com.inthinc.pro.automation.jbehave;

import static java.util.Arrays.asList;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ClassUtils;
import org.jbehave.core.steps.StepCreator.PendingStep;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.Assert;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.Validate;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationNumberManager;

public class AutoMethodParser {

    public static Object[] getParameters(String stepAsString, Method method) throws NoSuchMethodException {
        try {
            Class<?>[] parameters = method.getParameterTypes();
            Object[] passParameters = new Object[parameters.length];

            for (int i = 0; i < parameters.length; i++) {
                Class<?> next = parameters[i];
                if (next.isPrimitive()) {
                    next = ClassUtils.primitiveToWrapper(next);
                }
                if (next.equals(Boolean.class)) {
                    passParameters[i] = AutoStepVariables.checkBoolean(stepAsString);
                } else if (next.equals(Integer.class)) {
                    Integer param = AutomationNumberManager.extractXNumber(stepAsString, 1);
                    passParameters[i] = param == null || param == 0 ? 1 : param;
                } else if (next.equals(AutomationCalendar.class)) {
                    passParameters[i] = getCalendarParameter(stepAsString);
                } else {
                    next = String.class;
                    passParameters[i] = AutoStepVariables.getComparator(stepAsString);
                }
                if (passParameters[i] == null || !passParameters[i].getClass().equals(next)) {
                    throw new NoSuchMethodException("We are missing parameters for " + method.getName() + ", working on step " + stepAsString);
                }
            }
            return passParameters;
        } catch (NullPointerException e) {
            throw new NoSuchMethodException("Could not find a method for step: " + stepAsString);
        }
    }

    public static Object getCalendarParameter(String stepAsString) {
        Object parameter;

        AutomationCalendar var = new AutomationCalendar();
        if (stepAsString.contains("\"")) {
            int first = stepAsString.indexOf("\"") + 1;
            int last = stepAsString.lastIndexOf("\"");
            var = new AutomationCalendar(stepAsString.substring(first, last));
        } else {
            for (Map.Entry<String, String> variable : AutoStepVariables.getVariables().entrySet()) {
                if (stepAsString.contains(variable.getKey())) {
                    try {
                        var = new AutomationCalendar(variable.getValue());
                        break;
                    } catch (IllegalArgumentException e) {
                        continue;
                    }
                }
            }
        }

        parameter = var;

        int sign = 1;
        if (Pattern.compile(RegexTerms.calendarSubtract).matcher(stepAsString).find()) {
            sign = -1;
        }

        String days = RegexTerms.getMatch(RegexTerms.calendarDayDelta, stepAsString);
        String months = RegexTerms.getMatch(RegexTerms.calendarMonthDelta, stepAsString);
        String years = RegexTerms.getMatch(RegexTerms.calendarYearDelta, stepAsString);
        if (!days.equals("")) {
            var.addToDay(Integer.parseInt(days) * sign);
        }
        if (!months.equals("")) {
            var.addToMonth(Integer.parseInt(months) * sign);
        }
        if (!years.equals("")) {
            var.addToYear(Integer.parseInt(years) * sign);
        }

        return parameter;
    }

    public static Map<Method, Object[]> parseValidationStep(PendingStep step, Class<?> clazz) throws NoSuchMethodException {
        String stepAsString = step.stepAsString();
        Map<String, List<Method>> methods = null;
        Map<Method, Object[]> validateMethod = new HashMap<Method, Object[]>(2);
        String validationType = "validate";
        Class<? extends Annotation> ann = Validate.class;
        if (stepAsString.contains("assert")) {
            ann = Assert.class;
            validationType = "assert";
        }
        methods = getMethods(clazz, ann);
        if (methods == null) {
            throw new NoSuchMethodException("Could not find a validation method for: " + stepAsString);
        }
        boolean trueFalse = AutoStepVariables.checkBoolean(stepAsString);
        Set<String> names = methods.keySet();
        String variable = AutoStepVariables.getComparator(stepAsString);

        List<Method> methodList = new ArrayList<Method>();
        String shortLowerName = "";
        shortLowerName = stepAsString.replaceAll(variable, "").replace(" ", "").toLowerCase();

        String current = "";
        for (String name : names) {
            String shorter = name.replace(validationType, "");
            if (shortLowerName.contains(shorter) && shorter.length() > 0 && shorter.length() > current.length()) {
                methodList = methods.get(name);
                current = shorter;
            }
        }
        if (methodList.isEmpty()) {
            if (methods.containsKey(validationType)) {
                methodList = methods.get(validationType);
            }
        }

        if (methodList != null) {
            for (Method match : methodList) {
                Class<?>[] params = match.getParameterTypes();
                if (params.length > 0) {
                    if (variable != null && params[0].equals(variable.getClass())) {
                        validateMethod.put(match, new Object[] { variable });
                    } else if (params[0].equals(Boolean.class)) {
                        validateMethod.put(match, new Object[] { trueFalse });
                    }
                    return validateMethod;
                }
            }
        }
        throw new NoSuchMethodException("Could not find a validation step for " + stepAsString);
    }

    public static Method parseStep(String stepAsString, String elementType, Class<?> clazz) throws SecurityException, NoSuchMethodException {
        Map<String, List<Method>> methods = getMethods(clazz, null);
        String regex = RegexTerms.getMethod;
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(stepAsString);
        String potentialMethod;
        List<Method> matchingMethods = null;
        while (mat.find()) {
            potentialMethod = stepAsString.substring(mat.start(), mat.end()).replace(" ", "");
            if (methods.containsKey(potentialMethod)) {
                matchingMethods = methods.get(potentialMethod);
            }
            regex += RegexTerms.addLowercaseWordSpaceBefore;
            pat = Pattern.compile(regex);
            mat = pat.matcher(stepAsString);
        }

        if (matchingMethods == null) {
            throw new NoSuchMethodException("Could not find a method for : " + stepAsString);
        }

        if (matchingMethods.size() == 1) {
            return matchingMethods.get(0);
        } else {
            String name = AutoStepVariables.getComparator(stepAsString);
            if (name == null) {

            } else {
                for (Method method : matchingMethods) {
                    List<Class<?>> classes = asList(method.getParameterTypes());
                    if (classes.contains(String.class) || classes.contains(Object.class)) {
                        return method;
                    }
                }
            }
            return matchingMethods.get(0);
        }
    }

    public static boolean methodsMatch(Method methodA, Method methodB) {
        boolean matches = true;
        for (Class<?> methodClass : methodA.getParameterTypes()) {
            for (Class<?> deprecatedClass : methodB.getParameterTypes()) {
                matches &= methodClass == deprecatedClass;
            }
        }
        return matches;
    }

    public static Map<String, List<Method>> getMethods(Class<?> clazz, Class<? extends Annotation> filter) throws SecurityException, NoSuchMethodException {
        Map<String, List<Method>> methods = new HashMap<String, List<Method>>();
        Set<Method> deprecated = new HashSet<Method>();
        Set<String> deprecatedNames = new HashSet<String>();
        for (Method method : clazz.getMethods()) {

            String methodName = method.getName().toLowerCase();
            boolean matchesDeprecated = false;
            if (deprecatedNames.contains(methodName)) {
                for (Method methodD : deprecated) {
                    if (methodD.getName().equals(methodName)) {
                        matchesDeprecated |= methodsMatch(method, methodD);
                    }
                }
            }
            if (matchesDeprecated) {
                continue;
            }

            if (getAnnotation(method, Deprecated.class) != null) {
                deprecated.add(method);
                deprecatedNames.add(methodName);
                if (methods.containsKey(methodName)) {
                    Iterator<Method> itr = methods.get(methodName).iterator();
                    while (itr.hasNext()) {
                        if (methodsMatch(method, itr.next())) {
                            itr.remove();
                        }
                    }
                }
                continue;
            }

            if (filter != null) {
                Annotation ann = getAnnotation(method, filter);
                if (ann != null) {
                    String englishName = "";
                    String testName = "";
                    if (ann instanceof Validate) {
                        englishName = ((Validate) ann).englishName();
                        testName = ((Validate) ann).testName();
                    } else if (ann instanceof Assert) {
                        englishName = ((Assert) ann).englishName();
                        testName = ((Assert) ann).testName();
                    } else {
                        continue;
                    }
                    englishName = testName + englishName.replace(" ", "").toLowerCase();

                    if (!methods.containsKey(englishName)) {
                        methods.put(englishName, new ArrayList<Method>());
                    }
                    methods.get(englishName).add(method);

                    if (!methods.containsKey(methodName)) {
                        methods.put(methodName, new ArrayList<Method>());
                    }
                    methods.get(methodName).add(method);

                    continue;
                } else {
                    continue;
                }
            }

            if (!methods.containsKey(methodName)) {
                methods.put(methodName, new ArrayList<Method>());
            }

            methods.get(methodName).add(method);
        }
        return methods;
    }

    public static Annotation getAnnotation(Method method, Class<? extends Annotation> annotation) {
        Set<Annotation> set = getAnnotations(method);
        for (Annotation ann : set) {
            if (annotation.equals(ann.annotationType())) {
                return ann;
            }
        }
        return null;
    }

    public static Set<Annotation> getAnnotations(Method method) {
        Set<Annotation> ann = new HashSet<Annotation>();
        ann.addAll(asList(method.getAnnotations()));
        for (Object interfaces : ClassUtils.getAllInterfaces(method.getDeclaringClass())) {
            try {
                Method superMethod = ((Class<?>) interfaces).getMethod(method.getName(), method.getParameterTypes());
                ann.addAll(asList(superMethod.getAnnotations()));
            } catch (SecurityException e) {
                Log.debug(e);
            } catch (NoSuchMethodException e) {
                Log.debug(e);
            }
        }
        return ann;
    }

}
