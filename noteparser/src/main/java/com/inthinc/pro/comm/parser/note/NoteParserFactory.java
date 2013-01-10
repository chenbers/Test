package com.inthinc.pro.comm.parser.note;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoteParserFactory {

	private static NoteParser TIWIPRO_PARSER = new TiwiParser();
	private static NoteParser NOTEWS2_PARSER = new NotewsParser2();
	private static NoteParser NOTEBC_PARSER = new NotebcParser();

	private static Logger logger = LoggerFactory.getLogger(NoteParserFactory.class.getName());

	public final static int NOTE_TIWIPRO = 1;
	public final static int NOTE_WAYSMART_V2 = 2;
	public final static int NOTE_WAYSMART_V3 = 3;
	public final static int NOTEBC = 4;
	
	public final static String NOTEBC_METHOD = "notebc";
	public final static String NOTEWS_METHOD = "notews";
    public final static String NOTE_WAYSMART850_METHOD = "notes";
	public final static String NOTE_METHOD = "note";
	
    public static NoteParser getParserForNoteVersion(int version)
    {
    	switch (version)
    	{
			case NOTE_TIWIPRO:
				return TIWIPRO_PARSER;

			case NOTE_WAYSMART_V2:
    			return NOTEWS2_PARSER;
    			
    		case NOTE_WAYSMART_V3:
    		case NOTEBC:
    			return NOTEBC_PARSER;
    	}
        return null;
    }

    public static NoteParser getParserForMethod(String method)
    {
    	
    	NoteParser parser = null;
		if (method.equalsIgnoreCase(NOTEBC_METHOD)) 
			parser =  NOTEBC_PARSER;
		if (method.equalsIgnoreCase(NOTEWS_METHOD)) 
			parser = NOTEWS2_PARSER;
		if (method.equalsIgnoreCase(NOTE_METHOD)) 
			parser = TIWIPRO_PARSER;
        if (method.equalsIgnoreCase(NOTE_WAYSMART850_METHOD))
            return NOTEBC_PARSER;

		
		if (parser == null)
			logger.error("No parser for method: " + method);

		return parser;
    }


}
