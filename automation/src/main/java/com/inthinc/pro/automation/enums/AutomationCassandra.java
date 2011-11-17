package com.inthinc.pro.automation.enums;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.inthinc.noteservice.NoteService;

public enum AutomationCassandra {
    
    Default("ec2-50-19-131-40.compute-1.amazonaws.com"),
    
    ;
    
    private String baseNode;
    private List<Integer> nodes;
    
    private AutomationCassandra(String baseNode){
        this.baseNode = baseNode;
    }
    
    public AutomationCassandra setNodes(List<Integer> nodes){
        this.nodes = nodes;
        return this;
    }
    
    public NoteService createNoteService(){
        return createNoteService(nodes);        
    }
    
    public NoteService createNoteService(List<Integer> nodes){
        if (nodes == null || nodes.isEmpty()){
            nodes = new ArrayList<Integer>();
            nodes.add(0);
            nodes.add(1);
        }
        StringWriter writer = new StringWriter();
        for (int i=0;i<nodes.size();i++){
            if (i>0 && i<nodes.size()){
                writer.write(",");
            }
            writer.write(baseNode.replace("###", nodes.get(i) + ""));
        }
        NoteService service = new NoteService("inthinc", "note", writer.toString());
        return service;
    }

    public static NoteService createNode(String cassandraNode) {
        return new NoteService("inthinc", "note", cassandraNode);
    }

}
