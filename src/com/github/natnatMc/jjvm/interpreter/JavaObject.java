package com.github.natnatMc.jjvm.interpreter;

import java.util.*;

import com.github.natnatMc.jjvm.exceptions.*;

public class JavaObject {
	
	protected JavaClass type;
	protected HashMap<String, JavaObject> properties;
	protected int references;
	protected JavaInterpreter interpreter;
	
	public JavaObject(JavaClass type, JavaInterpreter interpreter) {
		this.interpreter=interpreter;
		
		this.type=type;
		this.properties=new HashMap<String, JavaObject>();
		
		this.references=0;
		this.interpreter.objects.add(this);
	}
	protected JavaObject(JavaInterpreter interpreter) {
		this.interpreter=interpreter;
	}
	
	public boolean isPrimitive() {
		return false;
	}
	public boolean isNull() {
		return false;
	}
	public boolean isArray() {
		return false;
	}
	public JavaClass getType() throws JJVMException {
		return this.type;
	}
	
	//write a field
	public void setField(String name, JavaObject value, JavaContext context) throws JJVMException {
		if(name==null||context==null||value==null) throw new NullPointerException("All arguments must be supplied");
		
		//make sure we can cast the value
		JavaClass type=this.type.fieldTypes.get(name);
		if(type==null) throw new JJVMNoSuchFieldException("No such field named "+name+" in class "+this.type.name);
		type.checkCast(value);
		
		//make sure we're allowed to access the field
		int mod=this.type.fieldModifiers.get(name);
		context.checkAccess(this, mod);
		
		//remove last value for property and decrement its number of active references
		JavaObject lastValue=this.properties.get(name);
		lastValue.references--;
		
		//set value and increment its number of active references
		this.properties.put(name, value);
		value.references++;
	}
	//read a field
	public JavaObject getField(String name, JavaContext context) throws JJVMException {
		if(name==null||context==null) throw new NullPointerException("Name and context must not be null");
		
		//make sure there is a field
		JavaClass type=this.type.fieldTypes.get(name);
		if(type==null) throw new JJVMNoSuchFieldException("No such field named "+name+" in class "+this.type.name);
		
		//make sure we're allowed to access the field
		int mod=this.type.fieldModifiers.get(name);
		context.checkAccess(this, mod);
		
		//return the value
		return this.properties.get(name);
	}
	//call a method
	public JavaObject callMethod(String descriptor, List<JavaObject> args, JavaContext context) throws JJVMException {
		if(descriptor==null||context==null||args==null) throw new NullPointerException("Descriptor, argument list and context must not be null");
		
		//make sure there is such a method
		JavaMethod method=this.type.methods.get(descriptor);
		if(method==null) throw new JJVMNoSuchMethodException("No such method with descriptor "+descriptor+" in class "+this.type.name);
		
		//make sure we're allowed to call the method
		int mod=method.modifier;
		context.checkAccess(this, mod);
		
		//call the method and return the value
		return interpreter.execute(this.type, method, this, args, context);
	}
	
	public int getInt() throws JJVMException {
		throw new JJVMIllegalOperationException("Attempt to read an object as an int");
	}
	public float getFloat() throws JJVMException {
		throw new JJVMIllegalOperationException("Attempt to read an object as a float");
	}
	public long getLong() throws JJVMException {
		throw new JJVMIllegalOperationException("Attempt to read an object as a long");
	}
	public double getDouble() throws JJVMException {
		throw new JJVMIllegalOperationException("Attempt to read an object as a double");
	}
	public JavaObject getArray(int index) throws JJVMException {
		throw new JJVMIllegalOperationException("Attempt to use an object as an array");
	}
	public void setArray(int index, JavaObject value) throws JJVMException {
		throw new JJVMIllegalOperationException("Attempt to use an object as an array");
	}
	public int getArrayLength() throws JJVMException {
		throw new JJVMIllegalOperationException("Attempt to get length of an object");
	}
	
}
