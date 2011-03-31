package com.inthinc.pro.web.selenium.Test_Cases;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import com.inthinc.pro.automation.selenium.InthincTest;
import com.inthinc.pro.web.selenium.portal.Login.Login;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class LoginScript extends InthincTest {
    
    // map of frequently used elements
    private HashMap<String,String> macro = new HashMap<String,String>();

    @Test
    public void UI() {

        // create instance of library objects, in particular, selenium
        Login l = new Login();
        
        // set up test data
        set_test_case("TC4632");

        // prototype for running a test driven by data only   
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        Resource resourceScript = applicationContext.getResource("classpath:script.txt");   // test to run
        Resource resourceMacro  = applicationContext.getResource("classpath:macro.txt");    // frequently used elements
        
        // load the frequently used 
        macro = getFrequentlyUsed(resourceMacro);
        
        try {
            InputStream is = resourceScript.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // grab a line and parse, if it is not a comment (// at start of line)
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("Evaluating line: " + line);
                
                // comment
                if ( line.substring(0,2).equalsIgnoreCase("//") ) {
                    continue;
                    
                // script of selenium commands
                } else if ( line.length() >= 7 && line.substring(0,7).equalsIgnoreCase("script:") ) {
                   runSeleniumScript(l,line,applicationContext); 
                   
                // a selenium command
                } else { 
                    String [] cmd = parseLine(line);
                    runSeleniumCommand(l,cmd);
                }
            }

            br.close();
            is.close();
            
        } catch (FileNotFoundException fnf) {
           fnf.printStackTrace();
           
        } catch (IOException ioe) {
            ioe.printStackTrace();
            
        } catch (NoSuchMethodException nsm) {
            nsm.printStackTrace();
            
        } catch (InvocationTargetException ite) {
            ite.printStackTrace();
            
        } catch (IllegalAccessException ia) {
            ia.printStackTrace();
        }
    }
    
    private HashMap<String,String> getFrequentlyUsed(Resource resourceMacro) {
        HashMap<String,String> tmp = new HashMap<String,String>();
        
        // load commonly used selenium elements
        try {
            InputStream is = resourceMacro.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // grab a line and parse, if it is not a comment (// at start of line)
            String line;
            while ((line = br.readLine()) != null) {
               
                if ( !line.substring(0,2).equalsIgnoreCase("//") ) {
                    StringTokenizer st = new StringTokenizer(line,"=");
                    String key = st.nextToken();
                    String value = st.nextToken();

                    tmp.put(key, value);
                }
            }

            br.close();
            is.close();
            
        } catch (FileNotFoundException fnf) {
           fnf.printStackTrace();
           
        } catch (IOException ioe) {
            ioe.printStackTrace();    
        } 
        
        return tmp;
    }
    
    private void runSeleniumCommand(Login l,String [] cmd) 
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        
//        Class<?> c = l.getSelenium().getClass();
//        Class<?>[] argTypes = loadArgs(cmd.length-1);
//        Method main = c.getDeclaredMethod(cmd[0], argTypes);
//        Object[] mainArgs = Arrays.copyOfRange(cmd, 1, cmd.length);
//        Object o = main.invoke(l.getSelenium(), mainArgs);
//
//        if ( o != null ) {
//            System.out.println(">>> Command returned: " + o.toString());
//        }
    }
    
    private void runSeleniumScript(Login l,String scriptLine,ApplicationContext applicationContext) 
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, FileNotFoundException, IOException {
        
        // get the script file name.  
        StringTokenizer st = new StringTokenizer(scriptLine,":");
        st.nextToken();
        
        // prep for read
        Resource resourceScript  = applicationContext.getResource(st.nextToken()); 
        InputStream is = resourceScript.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // grab a line of the script and parse, if it is not a comment (// at start of line), NO NESTED SCRIPTS!!!!
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println("Evaluating line of subscript: " + line);

            // not a comment 
            if ( !line.substring(0,2).equalsIgnoreCase("//") ) {
                String [] cmd = parseLine(line);
                runSeleniumCommand(l,cmd);
            } 
        }

        br.close();
        is.close();
    }    
    
    private String [] parseLine(String line) {
        StringTokenizer st = new StringTokenizer(line,",");
        String [] tmp = new String[st.countTokens()];
        int i = 0;
        
        while( st.hasMoreTokens() ) {
            String key = st.nextToken();
            
            // expand frequently used element
            if ( key.indexOf("macro:") != -1 ) {
                System.out.println(">>>>>>>>> Macro expansion for " + key);
                StringTokenizer stMacro = new StringTokenizer(key,":");
                stMacro.nextToken();
                key = macro.get(stMacro.nextToken());
            }
            
            tmp[i++] = key;
        }
        
        return tmp;    
    }
    
    private Class<?> [] loadArgs(int sz) {
        Class<?> [] tmp = new Class<?>[sz];
        
        for ( int i = 0; i < sz; i++ ) {
            tmp[i] = String.class;
        }
        
        return tmp;
    }
}
