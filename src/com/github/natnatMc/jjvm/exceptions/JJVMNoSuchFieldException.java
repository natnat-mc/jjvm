package com.github.natnatMc.jjvm.exceptions;

@SuppressWarnings("serial")
public class JJVMNoSuchFieldException extends JJVMException {
	
	public JJVMNoSuchFieldException(String a) {
		super(a);
	}
	public JJVMNoSuchFieldException(String a, Throwable t) {
		super(a, t);
	}
	public JJVMNoSuchFieldException(Throwable t) {
		super(t);
	}
	
}
