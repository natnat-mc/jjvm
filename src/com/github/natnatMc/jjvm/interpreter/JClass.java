package com.github.natnatMc.jjvm.interpreter;

import com.github.natnatMc.jjvm.classFile.*;
import com.github.natnatMc.jjvm.exceptions.MalformedClassException;

public class JClass {
	private ConstantPool pool;
	
	private String name;
	private String superName;
	private String[] interfaces;
	
	private int flags;
	
	private JMethod[] methods;
	private JField[] fields;
	
	public JClass(ClassFile f) throws MalformedClassException {
		pool=f.getPool();
		
		flags=f.getFlags();
		name=f.getName();
		superName=f.getSuper();
		interfaces=f.getInterfaces();
		
		ClassMethod[] cm=f.getMethods();
		methods=new JMethod[cm.length];
		for(int i=0; i<cm.length; i++) {
			methods[i]=new JMethod();
			methods[i].read(cm[i], pool);
		}
		
		ClassField[] cf=f.getFields();
		fields=new JField[cf.length];
		for(int i=0; i<cf.length; i++) {
			fields[i]=new JField();
			fields[i].read(cf[i], pool);
		}
	}
}
