package com.github.natnatMc.jjvm.interpreter;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.natnatMc.jjvm.classFile.*;
import com.github.natnatMc.jjvm.exceptions.MalformedClassException;
import com.github.natnatMc.jjvm.flags.ClassFlags;

public class JClass {
	protected ConstantPool pool;
	
	protected String name;
	protected String superName;
	protected String[] interfaces;
	
	protected int flags;
	
	protected JMethod[] methods;
	protected JField[] fields;
	
	public JClass(ClassFile f) throws MalformedClassException {
		pool=f.getPool();
		
		flags=f.getFlags();
		name=f.getName().replaceAll("/", ".");
		superName=f.getSuper().replaceAll("/", ".");
		interfaces=f.getInterfaces();
		for(int i=0; i<interfaces.length; i++) {
			interfaces[i]=interfaces[i].replaceAll("/", ".");
		}
		
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
	
	public String getName() {
		return name;
	}
	public String getSuper() {
		return superName;
	}
	public List<String> getInterfaces() {
		return Collections.unmodifiableList(Arrays.asList(interfaces));
	}
	public int getFlags() {
		return flags;
	}
	public List<JField> getFields() {
		return Collections.unmodifiableList(Arrays.asList(fields));
	}
	public List<JMethod> getMethods() {
		return Collections.unmodifiableList(Arrays.asList(methods));
	}
	
	public void dump(PrintStream out) {
		out.print(ClassFlags.toStringClass(flags));
		if(!ClassFlags.toStringClass(flags).isEmpty()) out.print(' ');
		out.print("class ");
		out.print(name);
		if(superName!=null) {
			out.print(" extends ");
			out.print(superName);
		}
		for(int i=0; i<interfaces.length; i++) {
			if(i==0) out.print(" implements ");
			else out.print(", ");
			out.print(interfaces[i]);
		}
		out.println(" {");
		
		for(int i=0; i<fields.length; i++) {
			JField f=fields[i];
			out.print('\t');
			out.print(ClassFlags.toStringField(f.flags));
			if(!ClassFlags.toStringField(f.flags).isEmpty()) out.print(' ');
			out.print(f.type);
			out.print(' ');
			out.print(f.name);
			if(f.constant!=null) {
				out.print('=');
				out.print(f.constant.toString());
			}
			out.println(';');
		}
		
		for(int i=0; i<methods.length; i++) {
			JMethod m=methods[i];
			out.print('\t');
			out.print(ClassFlags.toStringMethod(m.flags));
			if(!ClassFlags.toStringMethod(m.flags).isEmpty()) out.print(' ');
			out.print(m.returnType);
			out.print(' ');
			out.print(m.name);
			out.print('(');
			for(int j=0; j<m.parameterTypes.length; j++) {
				if(j!=0) out.print(", ");
				out.print(m.parameterTypes[j]);
			}
			out.print(')');
			if(m.exceptions!=null) {
				for(int j=0; j<m.exceptions.length; j++) {
					if(j==0) out.print(" throws ");
					else out.print(", ");
					out.print(m.exceptions[j]);
				}
			}
			out.println(";");
		}
		
		out.println("}");
	}
}

