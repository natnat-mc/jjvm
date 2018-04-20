package com.github.natnatMc.jjvm.exceptions;

@SuppressWarnings("serial")
public class JJVMIllegalOperationException extends JJVMException {
	
	public JJVMIllegalOperationException(String a) {
		super(a);
	}
	public JJVMIllegalOperationException(String a, Throwable t) {
		super(a, t);
	}
	public JJVMIllegalOperationException(Throwable t) {
		super(t);
	}
	
}
