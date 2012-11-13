package com.inthinc.pro.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class HazardTypeSerializer extends JsonSerializer<HazardType> {
    public HazardTypeSerializer(){
        super();
    }

    @Override
    public void serialize(HazardType value, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
        generator.writeStartObject();

        generator.writeNumberField("type", value.getCode());
        generator.writeBooleanField("urgent", value.isUrgent());
        generator.writeNumberField("radiusMeters", value.getRadius());
        generator.writeStringField("group", value.getGroup());
        generator.writeStringField("details", value.getDetails());
        generator.writeNumberField("shelfLifeSeconds",value.getShelfLifeSeconds());

        generator.writeEndObject();
    }
}