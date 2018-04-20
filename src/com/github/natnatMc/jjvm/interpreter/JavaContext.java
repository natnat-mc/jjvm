package com.github.natnatMc.jjvm.interpreter;

import com.github.natnatMc.jjvm.exceptions.JJVMAccessException;

public abstract class JavaContext {
	
	//throw if we are not allowed to access the object with the given modifier
	public abstract void checkAccess(JavaObject object, int modifiers) throws JJVMAccessException;
	
	//throw if we are not allowed to access the class with the given modifier
	public abstract void checkAccess(JavaClass type, int modifiers) throws JJVMAccessException;
	
}
