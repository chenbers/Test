package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.model.MeasurementType;

@XmlRootElement
public class TextMessageEvent extends Event {

        private static final long serialVersionUID = 1L;
        private String textMessage;

        public TextMessageEvent()
        {
            super();
            this.textMessage="TEXT MESSAGE NEEDED FOR RED FLAGS";
        }

        public TextMessageEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude, 
                String textMessage)
        {
            super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
            this.textMessage = textMessage;
        }
        
        @Override
        public String getDetails(String formatStr,MeasurementType measurementType,String... mString)
        {
            return this.textMessage;        
        }  
        
        public EventType getEventType()
        {
            return EventType.TEXT_MESSAGE;
        }
        
        @Override
        public boolean isValidEvent() {
            // TODO Auto-generated method stub
            return (textMessage != null);
        }

}
