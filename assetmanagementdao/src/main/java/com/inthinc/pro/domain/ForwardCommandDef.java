package com.inthinc.pro.domain;

public class ForwardCommandDef {

	public ForwardCommandDef(int forwardCommand, int dataLen) {
		super();
		this.forwardCommand = forwardCommand;
		this.dataLen = dataLen;
	}
	private int forwardCommand;
	private int dataLen;
	
	public int getForwardCommand() {
		return forwardCommand;
	}
	public int getDataLen() {
		return dataLen;
	}
}
