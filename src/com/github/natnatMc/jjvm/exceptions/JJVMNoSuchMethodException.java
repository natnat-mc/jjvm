package com.github.natnatMc.jjvm.exceptions;

@SuppressWarnings("serial")
public class JJVMNoSuchMethodException extends JJVMException {
	
	public JJVMNoSuchMethodException(String a) {
		super(a);
	}
	public JJVMNoSuchMethodException(String a, Throwable t) {
		super(a, t);
	}
	public JJVMNoSuchMethodException(Throwable t) {
		super(t);
	}
	
}
