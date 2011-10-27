package com.inthinc.pro.comm.parser.note;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoteParserFactory {

	private static NoteParser TIWIPRO_PARSER = new TiwiParser();
	private static NoteParser NOTEWS2_PARSER = new NotewsParser2();
	private static NoteParser NOTEWS3_PARSER = new NotewsParser3();
	private static NoteParser NOTEBC_PARSER = new NotebcParser();

	private static Logger logger = LoggerFactory.getLogger(NoteParserFactory.class.getName());

	public final static int NOTE_TIWIPRO = 1;
	public final static int NOTE_WAYSMART_V2 = 2;
	public final static int NOTE_WAYSMART_V3 = 3;
	public final static int NOTEBC = 4;
	
    public static NoteParser getParserForNoteVersion(int version)
    {
    	switch (version)
    	{
			case NOTE_TIWIPRO:
				return TIWIPRO_PARSER;

			case NOTE_WAYSMART_V2:
    			return NOTEWS2_PARSER;
    			
    		case NOTE_WAYSMART_V3:
    			return NOTEWS3_PARSER;

    		case NOTEBC:
    			return NOTEBC_PARSER;
    	}
        return null;
    }

}
