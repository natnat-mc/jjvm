package com.github.natnatMc.jjvm.interpreter;

import java.util.List;

import com.github.natnatMc.jjvm.exceptions.JJVMException;
import com.github.natnatMc.jjvm.exceptions.JJVMWrappedException;

public class JavaNullObject extends JavaObject {

	protected JavaNullObject(JavaInterpreter interpreter) {
		super(interpreter);
	}
	
	@Override
	public boolean isNull() {
		return true;
	}
	@Override
	public JavaClass getType() throws JJVMException {
		throw new JJVMWrappedException(new NullPointerException("Attempt to get type of null"));
	}
	@Override
	public void setField(String name, JavaObject value, JavaContext context) throws JJVMException {
		throw new JJVMWrappedException(new NullPointerException("Attempt to set field of null"));
	}
	@Override
	public JavaObject getField(String name, JavaContext context) throws JJVMException {
		throw new JJVMWrappedException(new NullPointerException("Attempt to get field of null"));
	}
	@Override
	public JavaObject callMethod(String descriptor, List<JavaObject> args, JavaContext context) throws JJVMException {
		throw new JJVMWrappedException(new NullPointerException("Attempt to call method of null"));
	}
	@Override
	public int getInt() throws JJVMException {
		throw new JJVMWrappedException(new NullPointerException("Attempt to get int value of null"));
	}
	@Override
	public float getFloat() throws JJVMException {
		throw new JJVMWrappedException(new NullPointerException("Attempt to get float value of null"));
	}
	@Override
	public long getLong() throws JJVMException {
		throw new JJVMWrappedException(new NullPointerException("Attempt to get long value of null"));
	}
	@Override
	public double getDouble() throws JJVMException {
		throw new JJVMWrappedException(new NullPointerException("Attempt to get double value of null"));
	}
	@Override
	public JavaObject getArray(int index) throws JJVMException {
		throw new JJVMWrappedException(new NullPointerException("Attempt to get array value of null"));
	}
	@Override
	public void setArray(int index, JavaObject value) throws JJVMException {
		throw new JJVMWrappedException(new NullPointerException("Attempt to set array value of null"));
	}
	@Override
	public int getArrayLength() throws JJVMException {
		throw new JJVMWrappedException(new NullPointerException("Attempt to get array length of null"));
	}
	
}
