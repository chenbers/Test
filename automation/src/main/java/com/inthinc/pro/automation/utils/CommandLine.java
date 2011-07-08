package com.inthinc.pro.automation.utils;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.StringWriter;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//
///**
// * Usage of following class can go as ...
// * <P>
// * 
// * <PRE>
// * <CODE>
// *      SysCommandExecutor cmdExecutor = new SysCommandExecutor();
// *      cmdExecutor.setOutputLogDevice(new LogDevice());
// *      cmdExecutor.setErrorLogDevice(new LogDevice());
// *      int exitStatus = cmdExecutor.runCommand(commandLine);
// * </CODE>
// * </PRE>
// * 
// * </P>
// * 
// * OR
// * 
// * <P>
// * 
// * <PRE>
// * <CODE>
// *      SysCommandExecutor cmdExecutor = new SysCommandExecutor();      
// *      int exitStatus = cmdExecutor.runCommand(commandLine);
// * 
// *      String cmdError = cmdExecutor.getCommandError();
// *      String cmdOutput = cmdExecutor.getCommandOutput(); 
// * </CODE>
// * </PRE>
// * 
// * </P>
// */
public class CommandLine {
//
//    private final static Logger logger = Logger.getLogger(CommandLine.class);
//
//    private ILogDevice fOuputLogDevice = null;
//    private ILogDevice fErrorLogDevice = null;
//    private String fWorkingDirectory = null;
//    private List<EnvironmentVar> fEnvironmentVarList = null;
//
//    private StringBuffer fCmdOutput = null;
//    private StringBuffer fCmdError = null;
//    private AsyncStreamReader fCmdOutputThread = null;
//    private AsyncStreamReader fCmdErrorThread = null;
//    
//    public void setOutputLogDevice(ILogDevice logDevice) {
//        fOuputLogDevice = logDevice;
//    }
//
//    public void setErrorLogDevice(ILogDevice logDevice) {
//        fErrorLogDevice = logDevice;
//    }
//
//    public void setWorkingDirectory(String workingDirectory) {
//        fWorkingDirectory = workingDirectory;
//    }
//
//    public void setEnvironmentVar(String name, String value) {
//        if (fEnvironmentVarList == null){
//            fEnvironmentVarList = new ArrayList<EnvironmentVar>();
//        }
//        fEnvironmentVarList.add(new EnvironmentVar(name, value));
//    }
//
//    public String getCommandOutput() {
//        return fCmdOutput.toString();
//    }
//
//    public String getCommandError() {
//        return fCmdError.toString();
//    }
//
//    public int runCommandWithArgs(String commandLine) throws IOException {
//        /* run command */
//        Process process = runCommandHelperWithArgs(commandLine);
//
//        /* start output and error read threads */
//        startOutputAndErrorReadThreads(process.getInputStream(), process.getErrorStream());
//
//        /* wait for command execution to terminate */
//        int exitStatus = -1;
//        try {
//            exitStatus = process.waitFor();
//
//        } catch (InterruptedException e) {
//            logger.info(StackToString.toString(e));
//        } finally {
//            /* notify output and error read threads to stop reading */
//            notifyOutputAndErrorReadThreadsToStopReading();
//        }
//
//        return exitStatus;
//    }
//
//    public int runCommand(String commandLine) throws IOException {
//        /* run command */
//        Process process = runCommandHelper(commandLine);
//
//        /* start output and error read threads */
//        startOutputAndErrorReadThreads(process.getInputStream(), process.getErrorStream());
//
//        /* wait for command execution to terminate */
//        int exitStatus = -1;
//        try {
//            exitStatus = process.waitFor();
//
//        } catch (InterruptedException e) {
//            logger.info(StackToString.toString(e));
//        } finally {
//            /* notify output and error read threads to stop reading */
//            notifyOutputAndErrorReadThreadsToStopReading();
//        }
//
//        return exitStatus;
//    }
//
//    private Process runCommandHelper(String commandLine) throws IOException {
//        Process process = null;
//        if (fWorkingDirectory == null)
//            process = Runtime.getRuntime().exec(commandLine, getEnvTokens());
//        else
//            process = Runtime.getRuntime().exec(commandLine, getEnvTokens(), new File(fWorkingDirectory));
//
//        return process;
//    }
//
//    private Process runCommandHelperWithArgs(String commandLine) throws IOException {
//        Process process = null;
//        ProcessBuilder builder = new ProcessBuilder(commandLine, getEnvTokens());
//        builder.environment().putAll(System.getenv());
//        process = builder.start();
//
//        return process;
//    }
//
//    private void startOutputAndErrorReadThreads(InputStream processOut, InputStream processErr) {
//        fCmdOutput = new StringBuffer();
//        fCmdOutputThread = new AsyncStreamReader(processOut, fCmdOutput, fOuputLogDevice, "OUTPUT");
//        fCmdOutputThread.start();
//
//        fCmdError = new StringBuffer();
//        fCmdErrorThread = new AsyncStreamReader(processErr, fCmdError, fErrorLogDevice, "ERROR");
//        fCmdErrorThread.start();
//    }
//
//    private void notifyOutputAndErrorReadThreadsToStopReading() {
//        fCmdOutputThread.stopReading();
//        fCmdErrorThread.stopReading();
//    }
//
//    public String getEnvTokensAsString() {
//        if (fEnvironmentVarList == null)
//            return null;
//
//        StringWriter writer = new StringWriter();
//        Iterator<EnvironmentVar> envVarIter = fEnvironmentVarList.iterator();
//        while (envVarIter.hasNext() == true) {
//            EnvironmentVar envVar = envVarIter.next();
//            String envVarToken = envVar.fName + "=" + envVar.fValue + " ";
//            writer.write(envVarToken);
//        }
//        return writer.toString();
//
//    }
//
//    private List<String> getEnvTokens() {
//        if (fEnvironmentVarList == null)
//            return null;
//
//        List<String> envTokenArray = new ArrayList<String>();
//        Iterator<EnvironmentVar> envVarIter = fEnvironmentVarList.iterator();
//        while (envVarIter.hasNext() == true) {
//            EnvironmentVar envVar = envVarIter.next();
//            String envVarToken = envVar.fName + "=" + envVar.fValue;
//            envTokenArray.add(envVarToken);
//        }
//
//        return envTokenArray;
//    }
//
//    class AsyncStreamReader extends Thread {
//        private StringBuffer fBuffer = null;
//        private InputStream fInputStream = null;
//        private String fThreadId = null;
//        private boolean fStop = false;
//        private ILogDevice fLogDevice = null;
//
//        private String fNewLine = null;
//
//        public AsyncStreamReader(InputStream inputStream, StringBuffer buffer, ILogDevice logDevice, String threadId) {
//            fInputStream = inputStream;
//            fBuffer = buffer;
//            fThreadId = threadId;
//            fLogDevice = logDevice;
//
//            fNewLine = System.getProperty("line.separator");
//        }
//
//        public String getBuffer() {
//            return fBuffer.toString();
//        }
//
//        public void run() {
//            try {
//                readCommandOutput();
//            } catch (Exception ex) {
//                logger.debug(StackToString.toString(ex)); // DEBUG
//            }
//        }
//
//        private void readCommandOutput() throws IOException {
//            BufferedReader bufOut = new BufferedReader(new InputStreamReader(fInputStream));
//            String line = null;
//            while ((fStop == false) && ((line = bufOut.readLine()) != null)) {
//                fBuffer.append(line + fNewLine);
//                printToDisplayDevice(line);
//            }
//            bufOut.close();
//            logger.debug("END OF: " + fThreadId); // DEBUG
//        }
//
//        public void stopReading() {
//            fStop = true;
//        }
//
//        private void printToDisplayDevice(String line) {
//            if (fLogDevice != null)
//                fLogDevice.log(line);
//            else {
//                logger.debug(line);// DEBUG
//            }
//        }
//    }
//
//    class EnvironmentVar {
//        public String fName = null;
//        public String fValue = null;
//
//        public EnvironmentVar(String name, String value) {
//            fName = name;
//            fValue = value;
//        }
//    }
//
//    public interface ILogDevice {
//        public void log(String str);
//    }
}
