package com.github.natnatMc.jjvm.classFile;

import java.util.*;

import com.github.natnatMc.jjvm.flags.ClassFlags;
import com.github.natnatMc.jjvm.struct.CONSTANT_Class_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_Utf8_info;

import java.io.*;

public class ClassFile {
	
	private int minor;
	private int major;
	private ConstantPool constantPool;
	private int flags;
	private String className;
	private String superName;
	private ArrayList<String> interfaces=new ArrayList<String>();
	/*private ArrayList<JField> fields;
	private ArrayList<JMethod> methods;
	private ArrayList<Attribute> attributes;*/
	
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
		className=utf8.value.replaceAll("/", ".");
		
		//superclass name
		pos=in.readUnsignedShort();
		if(pos!=0) {
			info=(CONSTANT_Class_info) constantPool.get(pos);
			utf8=(CONSTANT_Utf8_info) constantPool.get(info.pos);
			superName=utf8.value.replaceAll("/", ".");
		}
		
		//interfaces
		int count=in.readUnsignedShort();
		for(int i=0; i<count; i++) {
			pos=in.readUnsignedShort();
			info=(CONSTANT_Class_info) constantPool.get(pos);
			utf8=(CONSTANT_Utf8_info) constantPool.get(info.pos);
			interfaces.add(utf8.value.replaceAll("/", "."));
		}
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
		out.println(ClassFlags.toString(flags));
	}
	
}
