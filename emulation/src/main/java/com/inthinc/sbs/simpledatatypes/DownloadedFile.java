package com.inthinc.sbs.simpledatatypes;

public final class DownloadedFile {

	public final byte [] data;
	public final boolean isException;
	public final int fileAsInt;
	public final int version;
	public final int errorCode;
	
	public DownloadedFile(int fileAsInt,byte [] data,boolean isException,int v){
		this.data = data;
		this.fileAsInt = fileAsInt;
		this.isException = isException;
		this.version = v;
		errorCode = 0;
	}
	
	public DownloadedFile(int errCode){
		this.errorCode = errCode;
		fileAsInt = -1;
		data = null;
		version = 0;
		isException = false;
	}
	
	
	public DownloadedFile(){
		this.errorCode = -1;
		fileAsInt = -1;
		data = null;
		version = 0;
		isException = false;
	}
	
}
