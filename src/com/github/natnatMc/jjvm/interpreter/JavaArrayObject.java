package com.github.natnatMc.jjvm.interpreter;

import com.github.natnatMc.jjvm.exceptions.JJVMException;
import com.github.natnatMc.jjvm.exceptions.JJVMIllegalOperationException;
import com.github.natnatMc.jjvm.exceptions.JJVMWrappedException;

public class JavaArrayObject extends JavaObject {
	
	protected JavaObject[] values;
	
	protected JavaArrayObject(JavaClass type, int len, JavaInterpreter interpreter) throws JJVMException {
		super(type, interpreter);
		if(!type.isArray()) throw new JJVMIllegalOperationException("Attempt to create array object with non-array class");
		
		this.values=new JavaObject[len];
		for(int i=0; i<len; i++) {
			this.values[i]=this.interpreter.nullObject;
		}
		
		JavaPrimitiveObject prim=this.interpreter.getIntPrimitive(len);
		prim.references++;
		this.properties.put("length", prim);
	}
	
	@Override
	public boolean isArray() {
		return true;
	}
	
	@Override
	public JavaObject getArray(int index) throws JJVMException {
		if(index>0&&index<this.values.length) {
			return this.values[index];
		}
		throw new JJVMWrappedException(new ArrayIndexOutOfBoundsException());
	}
	@Override
	public void setArray(int index, JavaObject value) throws JJVMException {
		if(index>0&&index<this.values.length) {
			JavaObject oldValue=this.values[index];
			oldValue.references--;
			this.values[index]=value;
			value.references++;
		}
		throw new JJVMWrappedException(new ArrayIndexOutOfBoundsException());
	}
	@Override
	public int getArrayLength() throws JJVMException {
		return this.values.length;
	}
	
}
