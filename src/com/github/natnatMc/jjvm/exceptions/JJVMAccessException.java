package com.github.natnatMc.jjvm.exceptions;

@SuppressWarnings("serial")
public class JJVMAccessException extends JJVMException {
	
	public JJVMAccessException(String a) {
		super(a);
	}
	public JJVMAccessException(String a, Throwable t) {
		super(a, t);
	}
	public JJVMAccessException(Throwable t) {
		super(t);
	}
	
}
