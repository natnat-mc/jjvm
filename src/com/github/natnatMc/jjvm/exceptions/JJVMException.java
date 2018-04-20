package com.github.natnatMc.jjvm.exceptions;

@SuppressWarnings("serial")
public class JJVMException extends Exception {

	public JJVMException(String a) {
		super(a);
	}
	public JJVMException(String a, Throwable t) {
		super(a, t);
	}
	public JJVMException(Throwable t) {
		super(t);
	}
	
}
