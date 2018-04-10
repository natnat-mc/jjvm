package com.github.natnatMc.jjvm.interpreter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
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
	
	protected boolean ro;
	
	public JClass() {
		pool=new ConstantPool();
		interfaces=new String[0];
		methods=new JMethod[0];
		fields=new JField[0];
		ro=false;
	}
	
	public JClass(ClassFile f) throws MalformedClassException {
		this(f, true);
	}
	public JClass(ClassFile f, boolean protect) throws MalformedClassException {
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
		ro=protect;
	}
	
	public String getName() {
		return name;
	}
	public String getSuper() {
		return superName;
	}
	public List<String> getInterfaces() {
		return ro?Collections.unmodifiableList(Arrays.asList(interfaces)):Arrays.asList(interfaces);
	}
	public int getFlags() {
		return flags;
	}
	public List<JField> getFields() {
		return ro?Collections.unmodifiableList(Arrays.asList(fields)):Arrays.asList(fields);
	}
	public List<JMethod> getMethods() {
		return ro?Collections.unmodifiableList(Arrays.asList(methods)):Arrays.asList(methods);
	}
	public ConstantPool getConstantPool() {
		return ro?pool.clone():pool;
	}
	
	public void setName(String name) {
		if(ro) throw new IllegalStateException("Cannot modify ro class");
		this.name=name;
	}
	public void setSuper(String superName) {
		if(ro) throw new IllegalStateException("Cannot modify ro class");
		this.superName=superName;
	}
	public void setInterfaces(List<String> interfaces) {
		if(ro) throw new IllegalStateException("Cannot modify ro class");
		this.interfaces=interfaces.toArray(new String[0]);
	}
	public void setFlags(int flags) {
		if(ro) throw new IllegalStateException("Cannot modify ro class");
		this.flags=flags;
	}
	public void setFields(List<JField> fields) {
		if(ro) throw new IllegalStateException("Cannot modify ro class");
		this.fields=fields.toArray(new JField[0]);
	}
	public void setMethods(List<JMethod> methods) {
		if(ro) throw new IllegalStateException("Cannot modify ro class");
		this.methods=methods.toArray(new JMethod[0]);
	}
	public void setConstantPool(ConstantPool pool) {
		if(ro) throw new IllegalStateException("Cannot modify ro class");
		this.pool=pool;
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
				if(f.constant instanceof String) {
					out.print('"');
					out.print(f.constant.toString());
					out.print('"');
				} else {
					out.print(f.constant.toString());
				}
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
	
	public ClassFile export() throws IOException {
		//create class and give it basic attributes
		ClassFile cFile=new ClassFile();
		cFile.setName(getName());
		cFile.setSuper(getSuper());
		cFile.setInterfaces(interfaces);
		cFile.setPool(pool.clone());
		
		//generate low-level methods from JMethod instances
		ArrayList<ClassMethod> methods=new ArrayList<ClassMethod>();
		for(int i=0; i<this.methods.length; i++) {
			JMethod method=this.methods[i];
			ClassMethod cMethod=new ClassMethod();
			
			//create method descriptor
			StringBuilder descriptor=new StringBuilder();
			writeDescriptor(descriptor, method.returnType);
			descriptor.append('(');
			String[] params=method.parameterTypes;
			for(int j=0; j<params.length; j++) {
				writeDescriptor(descriptor, params[i]);
			}
			descriptor.append(')');
			cMethod.descriptor=descriptor.toString();
			
			//set method flags
			cMethod.flags=method.flags;
			
			//set method name
			cMethod.name=method.name;
			
			//create attributes
			ArrayList<ClassAttribute> attr=new ArrayList<ClassAttribute>();
			
			//exceptions
			String[] ex=method.exceptions;
			if(ex!=null&&ex.length!=0) {
				ClassAttribute exceptions=new ClassAttribute();
				exceptions.name="Exceptions";
				ByteArrayOutputStream baos=new ByteArrayOutputStream();
				DataOutputStream dos=new DataOutputStream(baos);
				dos.writeChar(ex.length);
				for(int j=0; j<ex.length; j++) {
					dos.writeChar(pool.requireClass(ex[j]));
				}
				dos.close();
				exceptions.data=ByteBuffer.wrap(baos.toByteArray());
				exceptions.len=exceptions.data.capacity();
				attr.add(exceptions);
			}
			
			//code
			if(method.code!=null) {
				ClassAttribute code=new ClassAttribute();
				code.name="Code";
				//TODO continue this
			}
		}
		
		//TODO add fields
		//TODO add the rest
		
		return cFile;
	}
	
	public static void writeDescriptor(StringBuilder descriptor, String type) {
		switch(type) {
			case "void":
				descriptor.append('V');
				break;
			case "int":
				descriptor.append('I');
				break;
			case "short":
				descriptor.append('S');
				break;
			case "double":
				descriptor.append('D');
				break;
			case "float":
				descriptor.append('F');
				break;
			case "byte":
				descriptor.append('B');
				break;
			case "boolean":
				descriptor.append('Z');
				break;
			case "char":
				descriptor.append('C');
				break;
			case "long":
				descriptor.append('J');
				break;
			default:
				descriptor.append("L");
				descriptor.append(type.replace('.', '/'));
				descriptor.append(';');
				break;
		}
	}
}

