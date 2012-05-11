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
import com.inthinc.pro.automation.test.Test;



/**
 * @author dtanner
 *
 */
public class AutomationAnnotations {
    

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Validate{
        String englishName();
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Assert{
        String englishName();
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface PageObjects{
        Class<? extends AbstractPage>[] list();
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface TestType{
        Class<Test> test();
        String buildID();
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface Uri{
        String uri();
    }
    

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface StoryPath{
        String[] paths() default "";
        String path() default "";
    }
}
