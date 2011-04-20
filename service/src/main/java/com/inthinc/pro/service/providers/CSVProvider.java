package com.inthinc.pro.service.providers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlRootElement;

import au.com.bytecode.opencsv.CSVWriter;

@SuppressWarnings("unchecked")
@Provider
@Produces("text/csv")
public class CSVProvider implements MessageBodyWriter<Object> {

//    @Context
//    protected Providers providers;
//             
    @Override
    public long getSize(Object object, Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if(genericType instanceof ParameterizedType){
            return true;
        }
        return type.isAnnotationPresent(XmlRootElement.class);
    }

    @Override
    public void writeTo(Object target, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        Writer outputStreamWriter = new OutputStreamWriter(entityStream);
        CSVWriter writer = new CSVWriter(outputStreamWriter, ',');
        String[] entries = ObjectToStringArray.convertObjectToStringArray(target, type, genericType);
        // feed in your array (or convert your data to an array)
        writer.writeNext(entries);
        writer.flush();
        writer.close();
    }
    
    
}
