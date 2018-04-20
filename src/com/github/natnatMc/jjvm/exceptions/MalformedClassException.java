package com.github.natnatMc.jjvm.exceptions;

@SuppressWarnings("serial")
public class MalformedClassException extends JJVMException {

	public MalformedClassException(String a) {
		super(a);
	}
	public MalformedClassException(String a, Throwable t) {
		super(a, t);
	}
	public MalformedClassException(Throwable t) {
		super(t);
	}
	
}
