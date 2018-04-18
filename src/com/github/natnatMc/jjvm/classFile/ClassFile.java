package com.github.natnatMc.jjvm.classFile;

import java.util.*;

import com.github.natnatMc.jjvm.struct.CONSTANT_Class_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_Utf8_info;
import com.github.natnatMc.jjvm.struct.ClassFlags;

import java.io.*;

public class ClassFile {
	
	private int minor;
	private int major;
	private ConstantPool constantPool;
	private int flags;
	private String className;
	private String superName;
	private ArrayList<String> interfaces=new ArrayList<String>();
	private ArrayList<ClassField> fields=new ArrayList<ClassField>();
	private ArrayList<ClassMethod> methods=new ArrayList<ClassMethod>();
	private ArrayList<ClassAttribute> attributes=new ArrayList<ClassAttribute>();
	
	public void read(DataInputStream in) throws IOException {
		//magic number
		int magic=in.readInt();
		if(magic!=0xcafebabe) throw new IOException("Class magic does not match");
		
		//class file version
		minor=in.readUnsignedShort();
		major=in.readUnsignedShort();
		
		//constant pool
		constantPool=new ConstantPool();
		constantPool.read(in);
		
		//class flags
		flags=in.readUnsignedShort();
		
		//class name
		int pos=in.readUnsignedShort();
		CONSTANT_Class_info info=(CONSTANT_Class_info) constantPool.get(pos);
		CONSTANT_Utf8_info utf8=(CONSTANT_Utf8_info) constantPool.get(info.pos);
		className=utf8.value;
		
		//superclass name
		pos=in.readUnsignedShort();
		if(pos!=0) {
			info=(CONSTANT_Class_info) constantPool.get(pos);
			utf8=(CONSTANT_Utf8_info) constantPool.get(info.pos);
			superName=utf8.value;
		}
		
		//interfaces
		int count=in.readUnsignedShort();
		for(int i=0; i<count; i++) {
			pos=in.readUnsignedShort();
			info=(CONSTANT_Class_info) constantPool.get(pos);
			utf8=(CONSTANT_Utf8_info) constantPool.get(info.pos);
			interfaces.add(utf8.value);
		}
		
		//fields
		count=in.readUnsignedShort();
		for(int i=0; i<count; i++) {
			ClassField field=new ClassField();
			field.flags=in.readUnsignedShort();
			pos=in.readUnsignedShort();
			utf8=(CONSTANT_Utf8_info) constantPool.get(pos);
			field.name=utf8.value;
			pos=in.readUnsignedShort();
			utf8=(CONSTANT_Utf8_info) constantPool.get(pos);
			field.descriptor=utf8.value;
			int count2=in.readUnsignedShort();
			for(int j=0; j<count2; j++) {
				ClassAttribute attr=new ClassAttribute();
				attr.read(in, constantPool);
				field.attributes.add(attr);
			}
			fields.add(field);
		}
		
		//methods
		count=in.readUnsignedShort();
		for(int i=0; i<count; i++) {
			ClassMethod method=new ClassMethod();
			method.flags=in.readUnsignedShort();
			pos=in.readUnsignedShort();
			utf8=(CONSTANT_Utf8_info) constantPool.get(pos);
			method.name=utf8.value;
			pos=in.readUnsignedShort();
			utf8=(CONSTANT_Utf8_info) constantPool.get(pos);
			method.descriptor=utf8.value;
			int count2=in.readUnsignedShort();
			for(int j=0; j<count2; j++) {
				ClassAttribute attr=new ClassAttribute();
				attr.read(in, constantPool);
				method.attributes.add(attr);
			}
			methods.add(method);
		}
		
		//attributes
		count=in.readUnsignedShort();
		for(int i=0; i<count; i++) {
			ClassAttribute attr=new ClassAttribute();
			attr.read(in, constantPool);
			attributes.add(attr);
		}
	}
	
	public void export(DataOutputStream out) throws IOException {
		//push everything to the pool
		int cNameP=constantPool.requireClass(className);
		int sNameP=superName!=null?constantPool.requireClass(superName):0;
		for(String i:interfaces) {
			constantPool.requireClass(i);
		}
		for(ClassField f:fields) {
			constantPool.requireUtf8(f.descriptor);
			constantPool.requireUtf8(f.name);
			for(ClassAttribute a:f.attributes) {
				constantPool.requireUtf8(a.name);
			}
		}
		for(ClassMethod f:methods) {
			constantPool.requireUtf8(f.descriptor);
			constantPool.requireUtf8(f.name);
			for(ClassAttribute a:f.attributes) {
				constantPool.requireUtf8(a.name);
			}
		}
		for(ClassAttribute a:attributes) {
			constantPool.requireUtf8(a.name);
		}
		
		//magic number
		out.writeInt(0xcafebabe);
		
		//version
		out.writeChar(minor);
		out.writeChar(major);
		
		//constant pool
		constantPool.export(out);
		
		//flags
		out.writeChar(flags);
		
		//class name
		out.writeChar(cNameP);
		
		//superclass name
		out.writeChar(sNameP);
		
		//interfaces
		out.writeChar(interfaces.size());
		for(String i:interfaces) {
			out.writeChar(constantPool.requireClass(i));
		}
		
		//fields
		out.writeChar(fields.size());
		for(ClassField f:fields) {
			out.writeChar(f.flags);
			out.writeChar(constantPool.requireUtf8(f.name));
			out.writeChar(constantPool.requireUtf8(f.descriptor));
			out.writeChar(f.attributes.size());
			for(ClassAttribute a:f.attributes) {
				out.writeChar(constantPool.requireUtf8(a.name));
				out.writeInt(a.len);
				out.write(a.data.array());
			}
		}
		
		//methods
		out.writeChar(methods.size());
		for(ClassMethod f:methods) {
			out.writeChar(f.flags);
			out.writeChar(constantPool.requireUtf8(f.name));
			out.writeChar(constantPool.requireUtf8(f.descriptor));
			out.writeChar(f.attributes.size());
			for(ClassAttribute a:f.attributes) {
				out.writeChar(constantPool.requireUtf8(a.name));
				out.writeInt(a.len);
				out.write(a.data.array());
			}
		}
		
		//attributes
		out.writeChar(attributes.size());
		for(ClassAttribute a:attributes) {
			out.writeChar(constantPool.requireUtf8(a.name));
			out.writeInt(a.len);
			out.write(a.data.array());
		}
	}
	
	public ConstantPool getPool() {
		return constantPool;
	}
	public void setPool(ConstantPool cPool) {
		this.constantPool=cPool;
	}
	public String getName() {
		return className;
	}
	public void setName(String name) {
		this.className=name;
	}
	public String getSuper() {
		return superName;
	}
	public void setSuper(String name) {
		this.superName=name;
	}
	public int getFlags() {
		return flags;
	}
	public void setFlags(int flags) {
		this.flags=flags;
	}
	public String[] getInterfaces() {
		return interfaces.toArray(new String[0]);
	}
	public void setInterfaces(String[] interfaces) {
		this.interfaces.clear();
		for(int i=0; i<interfaces.length; i++) {
			this.interfaces.add(interfaces[i]);
		}
	}
	public ClassField[] getFields() {
		return fields.toArray(new ClassField[0]);
	}
	public void setFields(ClassField[] fields) {
		this.fields.clear();
		for(int i=0; i<fields.length; i++) {
			this.fields.add(fields[i]);
		}
	}
	public ClassMethod[] getMethods() {
		return methods.toArray(new ClassMethod[0]);
	}
	public void setMethods(ClassMethod[] methods) {
		this.methods.clear();
		for(int i=0; i<methods.length; i++) {
			this.methods.add(methods[i]);
		}
	}
	public ClassAttribute[] getAttributes() {
		return attributes.toArray(new ClassAttribute[0]);
	}
	public void setAttributes(ClassAttribute[] attributes) {
		this.attributes.clear();
		for(int i=0; i<attributes.length; i++) {
			this.attributes.add(attributes[i]);
		}
	}
	public int getMinor() {
		return minor;
	}
	public void setMinor(int min) {
		minor=min;
	}
	public int getMajor() {
		return major;
	}
	public void setMajor(int maj) {
		major=maj;
	}
	
	public void dump(PrintStream out) {
		out.print("Class version: ");
		out.print(major);
		out.print('.');
		out.println(minor);
		
		out.print("Class name: ");
		out.println(className);
		
		if(superName!=null) {
			out.print("Superclass name: ");
			out.println(superName);
		}
		
		if(!interfaces.isEmpty()) {
			out.println("Interfaces:");
			for(String i:interfaces) {
				out.print('\t');
				out.println(i);
			}
		}
		
		out.print("Class flags: ");
		out.println(ClassFlags.toStringClass(flags));
		
		if(!fields.isEmpty()) {
			out.println("Fields:");
			for(ClassField f:fields) {
				out.print('\t');
				out.println(f.name);
				out.print("\t\tDescriptor: ");
				out.println(f.descriptor);
				out.print("\t\tFlags: ");
				out.println(ClassFlags.toStringField(f.flags));
				if(!f.attributes.isEmpty()) {
					out.println("\t\tAttributes:");
					for(ClassAttribute a:f.attributes) {
						out.print("\t\t\t");
						out.print(a.name);
						out.print(": ");
						out.println(a.len);
					}
				}
			}
		}
		
		if(!methods.isEmpty()) {
			out.println("Methods:");
			for(ClassMethod f:methods) {
				out.print('\t');
				out.println(f.name);
				out.print("\t\tDescriptor: ");
				out.println(f.descriptor);
				out.print("\t\tFlags: ");
				out.println(ClassFlags.toStringMethod(f.flags));
				if(!f.attributes.isEmpty()) {
					out.println("\t\tAttributes:");
					for(ClassAttribute a:f.attributes) {
						out.print("\t\t\t");
						out.print(a.name);
						out.print(": ");
						out.println(a.len);
					}
				}
			}
		}
		
		if(!attributes.isEmpty()) {
			out.println("Attributes:");
			for(ClassAttribute a:attributes) {
				out.print('\t');
				out.print(a.name);
				out.print(": ");
				out.println(a.len);
			}
		}
	}
	
}
