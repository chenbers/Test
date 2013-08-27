package com.inthinc.pro.model.event;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.model.MeasurementType;

@XmlRootElement
public class TextMessageEvent extends Event {

        private static final long serialVersionUID = 1L;

        @EventAttrID(name="TEXT_LENGTH")
        private Integer textMsgLength;

        @EventAttrID(name="TEXT_MESSAGE")
        private String textMsg;

        @EventAttrID(name="EVENT_CODE")
        private Integer textId;
        
        private static EventAttr[] eventAttrList = {
            EventAttr.TEXT_LENGTH,
            EventAttr.TEXT_MESSAGE,
        };
        
        @Override
        public EventAttr[] getEventAttrList() {
            return eventAttrList;
        }


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
            this.textMsgLength = textMsg == null ? 0 : textMsg.length();
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
            setTextMsgLength(textMsg == null ? 0 : textMsg.length());
        }


        public Integer getTextMsgLength() {
            return textMsgLength;
        }


        public void setTextMsgLength(Integer textMsgLength) {
            this.textMsgLength = textMsgLength;
        }
        
        

}
