package com.github.natnatMc.jjvm.interpreter;

import java.util.*;

import com.github.natnatMc.jjvm.exceptions.JJVMCastException;
import com.github.natnatMc.jjvm.exceptions.JJVMException;
import com.github.natnatMc.jjvm.exceptions.JJVMIllegalOperationException;
import com.github.natnatMc.jjvm.exceptions.JJVMNoSuchFieldException;
import com.github.natnatMc.jjvm.exceptions.JJVMNoSuchMethodException;

public class JavaClass {
	
	protected String name;
	protected JavaClass superClass;
	protected ArrayList<JavaClass> interfaces;
	
	protected HashMap<String, JavaClass> fieldTypes;
	protected HashMap<String, Integer> fieldModifiers;
	protected HashMap<String, JavaMethod> methods;
	
	protected HashMap<String, JavaObject> staticFields;
	protected HashMap<String, JavaMethod> staticMethods;
	
	protected JavaInterpreter interpreter;
	
	public boolean isPrimitive() {
		return false;
	}
	public boolean isArray() {
		return false;
	}
	public boolean isDoubleOrLong() {
		return false;
	}
	
	//check if we can assign obj to a field of this type
	public void checkCast(JavaObject obj) throws JJVMCastException {
		//if we're null, then we're good
		if(obj.isNull()) return;
		
		//get the object's type
		JavaClass type=obj.type;
		
		//test quickly if there are problems with primitives
		if(type.isPrimitive()) throw new JJVMCastException("Attempt to cast a primitive to an object");
		
		//if the object's type is our parent, then we can safely cast
		if(this.hasParent(type)) return;
		
		//if we're still here, then we can't cast safely
		throw new JJVMCastException("Cannot cast "+type.name+" to "+this.name);
	}
	
	//returns true if this has parent somewhere in its inheritance
	public boolean hasParent(JavaClass parent) {
		//if we are the parent, then the parent is our parent
		if(this==parent) return true;
		
		//recurse for all of our interfaces
		if(this.interfaces!=null) {
			Iterator<JavaClass> it=this.interfaces.iterator();
			while(it.hasNext()) {
				JavaClass iface=it.next();
				if(iface.hasParent(parent)) return true;
			}
		}
		
		//recurse for our own parent
		if(this.superClass!=null) {
			if(this.superClass.hasParent(parent)) return true;
		}
		
		//if we can't find it, then no, it is not our parent
		return false;
	}
	
	//call a static method
	public JavaObject callStaticMethod(String descriptor, List<JavaObject> args, JavaContext context) throws JJVMException {
		if(descriptor==null||context==null||args==null) throw new NullPointerException("Descriptor, argument list and context must not be null");
		
		//make sure there is such a method
		JavaMethod method=this.staticMethods.get(descriptor);
		if(method==null) throw new JJVMNoSuchMethodException("No such method with descriptor "+descriptor+" in class "+this.name);
		
		//make sure we're allowed to call the method
		int mod=method.modifier;
		context.checkAccess(this, mod);
		
		//call the method and return the value
		return interpreter.execute(this, method, null, args, context);
	}
	//read a static field
	public JavaObject getField(String name, JavaContext context) throws JJVMException {
		if(name==null||context==null) throw new NullPointerException("Name and context must not be null");
		
		//make sure there is a field
		JavaClass type=this.fieldTypes.get(name);
		if(type==null) throw new JJVMNoSuchFieldException("No such field named "+name+" in class "+this.name);
		
		//make sure we're allowed to access the field
		int mod=this.fieldModifiers.get(name);
		context.checkAccess(this, mod);
		
		//return the value
		return this.staticFields.get(name);
	}
	//write a static field
	public void setField(String name, JavaObject value, JavaContext context) throws JJVMException {
		if(name==null||context==null||value==null) throw new NullPointerException("All arguments must be supplied");
		
		//make sure we can cast the value
		JavaClass type=this.fieldTypes.get(name);
		if(type==null) throw new JJVMNoSuchFieldException("No such field named "+name+" in class "+this.name);
		type.checkCast(value);
		
		//make sure we're allowed to access the field
		int mod=this.fieldModifiers.get(name);
		context.checkAccess(this, mod);
		
		//remove last value for property and decrement its number of active references
		JavaObject lastValue=this.staticFields.get(name);
		lastValue.references--;
		
		//set value and increment its number of active references
		this.staticFields.put(name, value);
		value.references++;
	}
	//get type of array elements
	public JavaClass getElementType() throws JJVMException {
		throw new JJVMIllegalOperationException("Can not get type of array elements of a non-array class");
	}
}
