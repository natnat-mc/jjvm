package com.github.natnatMc.jjvm.exceptions;

@SuppressWarnings("serial")
public class JJVMWrappedException extends JJVMException {
	
	public JJVMWrappedException(String a) {
		super(a);
	}
	public JJVMWrappedException(String a, Throwable t) {
		super(a, t);
	}
	public JJVMWrappedException(Throwable t) {
		super(t);
	}
	
}
