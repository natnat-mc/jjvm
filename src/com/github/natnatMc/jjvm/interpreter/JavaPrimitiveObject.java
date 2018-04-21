package com.github.natnatMc.jjvm.interpreter;

import java.util.List;

import com.github.natnatMc.jjvm.exceptions.JJVMException;
import com.github.natnatMc.jjvm.exceptions.JJVMIllegalOperationException;

public class JavaPrimitiveObject extends JavaObject {
	
	protected Number value;
	
	public JavaPrimitiveObject(JavaInterpreter interpreter, JavaClass type, Number value) throws JJVMException {
		super(interpreter);
		if(!type.isPrimitive()) throw new JJVMIllegalOperationException("Attempt to create primitive object with object class");
		this.type=type;
		
		this.value=value;
		
		this.references=0;
		this.interpreter.objects.add(this);
	}
	@Override
	public boolean isPrimitive() {
		return true;
	}
	
	@Override
	public JavaObject getField(String name, JavaContext context) throws JJVMException {
		throw new JJVMIllegalOperationException("Attempt to get statif field of primitive type");
	}
	@Override
	public void setField(String name, JavaObject value, JavaContext context) throws JJVMException {
		throw new JJVMIllegalOperationException("Attempt to set static field of primitive type");
	}
	@Override
	public JavaObject callMethod(String descriptor, List<JavaObject> args, JavaContext context) throws JJVMException {
		throw new JJVMIllegalOperationException("Attempt to call method of primary type");
	}
	
	@Override
	public int getInt() {
		return value.intValue();
	}
	@Override
	public float getFloat() {
		return value.floatValue();
	}
	@Override
	public long getLong() {
		return value.longValue();
	}
	@Override
	public double getDouble() {
		return value.doubleValue();
	}
}
