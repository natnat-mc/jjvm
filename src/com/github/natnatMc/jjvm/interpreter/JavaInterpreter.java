package com.github.natnatMc.jjvm.interpreter;

import java.util.*;

import com.github.natnatMc.jjvm.exceptions.JJVMException;

public class JavaInterpreter {
	protected HashMap<String, JavaClass> classes;
	protected HashSet<JavaObject> objects;
	protected HashMap<Number, JavaPrimitiveObject> primitiveCache;
	protected JavaNullObject nullObject;
	
	public JavaInterpreter() {
		this.classes=new HashMap<String, JavaClass>();
		this.objects=new HashSet<JavaObject>();
		this.primitiveCache=new HashMap<Number, JavaPrimitiveObject>();
		this.nullObject=new JavaNullObject(this);
		
		this.createPrimitives();
	}
	
	//run the garbage collector
	public void gc() {
		//TODO check if there are circular references
		//remove all objects with no strong references
		Iterator<JavaObject> it=this.objects.iterator();
		while(it.hasNext()) {
			JavaObject obj=it.next();
			if(obj.references==0) {
				//TODO maybe finalize()V the object
				it.remove();
			}
		}
		
		//clean up primitive cache
		Iterator<Number> pr=this.primitiveCache.keySet().iterator();
		while(pr.hasNext()) {
			Number key=pr.next();
			JavaPrimitiveObject prim=this.primitiveCache.get(key);
			if(prim.references==0) {
				if(prim.value.doubleValue()>10||prim.value.doubleValue()<-10) {
					this.primitiveCache.remove(key);
				}
			}
		}
	}
	
	//create primitive types
	private void createPrimitives() {
		this.classes.put("void", new JavaPrimitiveClass("void", this));
		this.classes.put("boolean", new JavaPrimitiveClass("boolean", this));
		this.classes.put("byte", new JavaPrimitiveClass("byte", this));
		this.classes.put("char", new JavaPrimitiveClass("char", this));
		this.classes.put("short", new JavaPrimitiveClass("short", this));
		this.classes.put("int", new JavaPrimitiveClass("int", this));
		this.classes.put("float", new JavaPrimitiveClass("float", this));
		this.classes.put("long", new JavaPrimitiveClass("long", this));
		this.classes.put("double", new JavaPrimitiveClass("double", this));
	}
	
	//create or give a primitive object
	protected JavaPrimitiveObject getIntPrimitive(int value) throws JJVMException {
		JavaPrimitiveObject prim=this.primitiveCache.get(value);
		JavaClass type=this.classes.get("int");
		if(prim!=null&&prim.type==type) {
			return prim;
		}
		prim=new JavaPrimitiveObject(type, value, this);
		this.primitiveCache.put(value, prim);
		return prim;
	}
	protected JavaPrimitiveObject getFloatPrimitive(float value) throws JJVMException {
		JavaPrimitiveObject prim=this.primitiveCache.get(value);
		JavaClass type=this.classes.get("float");
		if(prim!=null&&prim.type==type) {
			return prim;
		}
		prim=new JavaPrimitiveObject(type, value, this);
		this.primitiveCache.put(value, prim);
		return prim;
	}
	protected JavaPrimitiveObject getLongPrimitive(long value) throws JJVMException {
		JavaPrimitiveObject prim=this.primitiveCache.get(value);
		JavaClass type=this.classes.get("int");
		if(prim!=null&&prim.type==type) {
			return prim;
		}
		prim=new JavaPrimitiveObject(type, value, this);
		this.primitiveCache.put(value, prim);
		return prim;
	}
	protected JavaPrimitiveObject getDoublePrimitive(double value) throws JJVMException {
		JavaPrimitiveObject prim=this.primitiveCache.get(value);
		JavaClass type=this.classes.get("float");
		if(prim!=null&&prim.type==type) {
			return prim;
		}
		prim=new JavaPrimitiveObject(type, value, this);
		this.primitiveCache.put(value, prim);
		return prim;
	}
	
	//get array type
	protected JavaClass getArrayType(JavaClass type) {
		JavaClass array=this.classes.get(type.name+"[]");
		if(array==null) {
			array=new JavaArrayClass(type, this);
			this.classes.put(array.name, array);
		}
		return array;
	}
	
	//execute a method
	protected JavaObject execute(JavaClass type, JavaMethod method, JavaObject thisValue, List<JavaObject> arguments, JavaContext context) throws JJVMException {
		throw new JJVMException("Nope, unimplemented");
		//FIXME implement me, baka
	}
}
