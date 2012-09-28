package com.inthinc.pro.scheduler.dispatch;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.scheduler.i18n.LocalizedMessage;

public class PhoneDispatcher {

    private CallServiceMessageSender callServiceMessageSender;
    private Integer maxThreads;
    private ExecutorService executorService;

    private static Logger logger = Logger.getLogger(PhoneDispatcher.class);

    public void init() {
        executorService = Executors.newFixedThreadPool(maxThreads);
    }

    public void sendList(List<AlertMessageBuilder> userList) {

        executorService.submit(new PhoneListThread(userList));
    }

    public Integer getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(Integer maxThreads) {
        this.maxThreads = maxThreads;
    }

    public CallServiceMessageSender getCallServiceMessageSender() {
        return callServiceMessageSender;
    }

    public void setCallServiceMessageSender(CallServiceMessageSender callServiceMessageSender) {
        this.callServiceMessageSender = callServiceMessageSender;
    }

    public class PhoneListThread implements Runnable {
        private List<AlertMessageBuilder> userList;

        public PhoneListThread(List<AlertMessageBuilder> userList) {
            this.userList = userList;
        }

        @Override
        public void run() {

            for (AlertMessageBuilder message : userList) {

                String text = LocalizedMessage.getStringWithValues(message.getAlertMessageType().toString(), message.getLocale(),
                        (String[]) message.getParamterList().toArray(new String[message.getParamterList().size()]));
                logger.debug("PHONE Message: " + message.getAddress() + " " + text);

                getCallServiceMessageSender().sendMessage(message.getAddress(), text, message.getMessageID(), message.getAcknowledge());
            }
        }
    }
}
