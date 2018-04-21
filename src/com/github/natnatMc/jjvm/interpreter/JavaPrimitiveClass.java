package com.github.natnatMc.jjvm.interpreter;

import java.util.List;

import com.github.natnatMc.jjvm.exceptions.JJVMCastException;
import com.github.natnatMc.jjvm.exceptions.JJVMException;
import com.github.natnatMc.jjvm.exceptions.JJVMIllegalOperationException;

public class JavaPrimitiveClass extends JavaClass {
	
	public JavaPrimitiveClass(String name, JavaInterpreter interpreter) {
		this.name=name;
		this.interpreter=interpreter;
	}
	
	@Override
	public boolean isPrimitive() {
		return true;
	}
	@Override
	public boolean isDoubleOrLong() {
		return this.name.equals("double")||this.name.equals("long");
	}
	
	@Override
	public void checkCast(JavaObject obj) throws JJVMCastException {
		//check if the type of the object is the right class
		if(obj.type==this) return;
		
		//if not, then we probably can't cast
		throw new JJVMCastException("Can't cast "+obj.type.name+" to "+this.name);
	}
	@Override
	public boolean hasParent(JavaClass parent) {
		return parent==this;
	}
	
	@Override
	public JavaObject callStaticMethod(String descriptor, List<JavaObject> args, JavaContext context) throws JJVMException {
		throw new JJVMIllegalOperationException("Attempt to call static method of primitive type");
	}
	@Override
	public JavaObject getField(String name, JavaContext context) throws JJVMException {
		throw new JJVMIllegalOperationException("Attempt to get statif field of primitive type");
	}
	@Override
	public void setField(String name, JavaObject value, JavaContext context) throws JJVMException {
		throw new JJVMIllegalOperationException("Attempt to set static field of primitive type");
	}
	
}
