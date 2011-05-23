package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.model.MeasurementType;
import com.mysql.jdbc.Messages;

@XmlRootElement
public class TextMessageEvent extends Event {

        private static final long serialVersionUID = 1L;
        private Integer textId;
        private String textMsg;

        public TextMessageEvent()
        {
            super();
        }

        public TextMessageEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude, 
                Integer textId, String textMsg)
        {
            super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
            this.textId = textId;
            this.textMsg = textMsg;
        }
        
        @Override
        public String getDetails(String formatStr,MeasurementType measurementType,String... mString)
        {
            return getTextMsg(); 
        }  
        
        public EventType getEventType()
        {
            return EventType.TEXT_MESSAGE;
        }
        
        @Override
        public boolean isValidEvent() {
            // TODO Auto-generated method stub
            return (textMsg != null);
        }

        public Integer getTextId() {
            return textId;
        }

        public void setTextId(Integer textId) {
            this.textId = textId;
        }

        public String getTextMsg() {
            return textMsg;
        }

        public void setTextMsg(String textMsg) {
            this.textMsg = textMsg;
        }

}
