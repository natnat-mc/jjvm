package com.github.natnatMc.jjvm.exceptions;

@SuppressWarnings("serial")
public class JJVMCastException extends JJVMException {
	
	public JJVMCastException(String a) {
		super(a);
	}
	public JJVMCastException(String a, Throwable t) {
		super(a, t);
	}
	public JJVMCastException(Throwable t) {
		super(t);
	}
	
}
