package com.github.natnatMc.jjvm.intermediate;

public class JExceptionHandler {
	
	public int startPC, endPC;
	public int handlerPC;
	public String exceptionType;
	
	public JExceptionHandler clone() {
		JExceptionHandler clone=new JExceptionHandler();
		clone.startPC=startPC;
		clone.endPC=endPC;
		clone.handlerPC=handlerPC;
		clone.exceptionType=exceptionType;
		return clone;
	}
	
}
