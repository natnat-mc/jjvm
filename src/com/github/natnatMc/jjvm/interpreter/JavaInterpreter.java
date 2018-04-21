package com.github.natnatMc.jjvm.interpreter;

import java.util.*;
import java.util.concurrent.locks.*;

import com.github.natnatMc.jjvm.exceptions.JJVMException;

public class JavaInterpreter {
	protected HashMap<String, JavaClass> classes;
	protected HashSet<JavaObject> objects;
	
	protected ReentrantLock lock;
	
	public JavaInterpreter() {
		this.classes=new HashMap<String, JavaClass>();
		this.objects=new HashSet<JavaObject>();
		this.lock=new ReentrantLock();
		
		this.createPrimitives();
	}
	
	//run the garbage collector
	public void gc() {
		this.lock.lock();
		
		//remove all objects with no strong references
		Iterator<JavaObject> it=this.objects.iterator();
		while(it.hasNext()) {
			JavaObject obj=it.next();
			if(obj.references==0) {
				//TODO maybe finalize()V the object
				it.remove();
			}
		}
		
		this.lock.unlock();
	}
	
	//create primitive types
	private void createPrimitives() {
		this.classes.put("boolean", new JavaPrimitiveClass("boolean", this));
		this.classes.put("byte", new JavaPrimitiveClass("byte", this));
		this.classes.put("char", new JavaPrimitiveClass("char", this));
		this.classes.put("short", new JavaPrimitiveClass("short", this));
		this.classes.put("int", new JavaPrimitiveClass("int", this));
		this.classes.put("float", new JavaPrimitiveClass("float", this));
		this.classes.put("long", new JavaPrimitiveClass("long", this));
		this.classes.put("double", new JavaPrimitiveClass("double", this));
	}
	
	//execute a method
	protected JavaObject execute(JavaClass type, JavaMethod method, JavaObject thisValue, List<JavaObject> arguments, JavaContext context) throws JJVMException {
		throw new JJVMException("Nope, unimplemented");
		//FIXME implement me, baka
	}
}
