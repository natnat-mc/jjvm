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
	
	//execute a method
	protected JavaObject execute(JavaClass type, JavaMethod method, JavaObject thisValue, List<JavaObject> arguments, JavaContext context) throws JJVMException {
		throw new JJVMException("Nope, unimplemented");
		//FIXME implement me, baka
	}
}
