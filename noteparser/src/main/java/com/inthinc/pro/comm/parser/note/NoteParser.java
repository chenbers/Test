package com.inthinc.pro.comm.parser.note;

import java.util.Map;


public interface NoteParser {
	public Map parseNote(byte[] data, int noteTypeCode);
}
