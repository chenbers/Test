/**
 * 
 */
package com.inthinc.pro.automation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.inthinc.pro.automation.selenium.AbstractPage;

/**
 * @author dtanner
 * 
 */
public class AutomationAnnotations {

    /**
     * This is just a method to specify to the Automation<br />
     * which methods are Validation methods.
     * 
     * @author dtanner
     * 
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Validate {
        /**
         * Specifys an alternative to how this method can be identified.
         * 
         * @return
         */
        String englishName();

        /**
         * Gives us a quick way to group the method.
         * 
         * @return
         */
        String testName() default "validate";
    }

    /**
     * This is just a method to specify to the Automation<br />
     * which methods are Assertion methods.
     * 
     * @author dtanner
     * 
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Assert {
        /**
         * Specifys an alternative to how this method can be identified.
         * 
         * @return
         */
        String englishName();

        /**
         * Gives us a quick way to group the method.
         * 
         * @return
         */
        String testName() default "assert";
    }

    /**
     * Following the JBehave Annotation style tests to provide<br />
     * page objects to use when interacting with the browser.
     * 
     * @author dtanner
     * 
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface PageObjects {
        /**
         * Just a varArgs list of page objects.
         * 
         * @return
         */
        Class<? extends AbstractPage>[] list();
    }

    /**
     * Overriding the JBehave method of getting story path(s)<br />
     * We don't necessarily need to always provide a list of stories.<br />
     * Only one method should be set
     * 
     * @author dtanner
     * 
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface StoryPath {
        /**
         * Specifies a list of stories to run.
         * 
         * @return
         */
        String[] paths() default "";

        /**
         * Specifies a single story to be run.
         * 
         * @return
         */
        String path() default "";
    }
}
