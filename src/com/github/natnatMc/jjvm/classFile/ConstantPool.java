package com.github.natnatMc.jjvm.classFile;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

import com.github.natnatMc.jjvm.struct.CONSTANT_Class_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_Double_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_Fieldref_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_Float_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_Integer_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_InterfaceMethodref_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_Long_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_Methodref_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_NameAndType_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_String_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_Utf8_info;

public class ConstantPool {
	
	private ArrayList<ConstantPoolObject> objects=new ArrayList<ConstantPoolObject>();
	
	private int require(ConstantPoolObject obj) {
		int index=objects.indexOf(obj);
		if(index!=-1) return index+1;
		objects.add(obj);
		return objects.size();
	}
	private void put(ConstantPoolObject obj) {
		objects.add(obj);
	}
	
	public int requireString(String str) {
		int pos=requireUtf8(str);
		CONSTANT_String_info strInfo=new CONSTANT_String_info();
		strInfo.stringIndex=pos;
		return require(strInfo);
	}
	public int requireClass(String cName) {
		int pos=requireUtf8(cName);
		CONSTANT_Class_info info=new CONSTANT_Class_info();
		info.pos=pos;
		return require(info);
	}
	public int requireUtf8(String str) {
		CONSTANT_Utf8_info utf8=new CONSTANT_Utf8_info();
		utf8.value=str;
		return require(utf8);
	}
	
	public int requireInt(int i) {
		CONSTANT_Integer_info intInfo=new CONSTANT_Integer_info();
		intInfo.bytes=i;
		return require(intInfo);
	}
	public int requireFloat(float f) {
		CONSTANT_Float_info floatInfo=new CONSTANT_Float_info();
		floatInfo.bytes=f;
		return require(floatInfo);
	}
	public int requireDouble(double val) {
		CONSTANT_Double_info info=new CONSTANT_Double_info();
		info.bytes=val;
		return require(info);
	}
	public int requireLong(long val) {
		CONSTANT_Long_info info=new CONSTANT_Long_info();
		info.bytes=val;
		return require(info);
	}
	
	public int requireNameAndType(String name, String type) {
		int nPos=requireUtf8(name);
		int tPos=requireUtf8(type);
		CONSTANT_NameAndType_info info=new CONSTANT_NameAndType_info();
		info.nameIndex=nPos;
		info.descriptorIndex=tPos;
		return require(info);
	}
	
	public int requireFieldref(String cName, String name, String type) {
		int cPos=requireClass(cName);
		int ntPos=requireNameAndType(name, type);
		CONSTANT_Fieldref_info info=new CONSTANT_Fieldref_info();
		info.classIndex=cPos;
		info.nameAndTypeIndex=ntPos;
		return require(info);
	}
	public int requireMethodref(String cName, String name, String type) {
		int cPos=requireClass(cName);
		int ntPos=requireNameAndType(name, type);
		CONSTANT_Methodref_info info=new CONSTANT_Methodref_info();
		info.classIndex=cPos;
		info.nameAndTypeIndex=ntPos;
		return require(info);
	}
	public int requireInterfaceMethodref(String cName, String name, String type) {
		int cPos=requireClass(cName);
		int ntPos=requireNameAndType(name, type);
		CONSTANT_InterfaceMethodref_info info=new CONSTANT_InterfaceMethodref_info();
		info.classIndex=cPos;
		info.nameAndTypeIndex=ntPos;
		return require(info);
	}
	
	public ConstantPoolObject get(int pos) {
		return objects.get(pos-1);
	}
	
	public void export(DataOutputStream out) throws IOException {
		out.writeChar(objects.size()+1);
		for(ConstantPoolObject o:objects) {
			o.export(out);
		}
		out.flush();
	}
	public void read(DataInputStream in) throws IOException {
		int no=in.readUnsignedShort();
		for(int i=0; i<no-1; i++) {
			int type=in.readUnsignedByte();
			ConstantPoolObject obj=ConstantPoolObject.get(type);
			obj.read(in);
			put(obj);
		}
	}
	
	public void dump(PrintStream out) throws IllegalArgumentException, IllegalAccessException {
		out.print(objects.size());
		out.println(" elements:");
		for(ConstantPoolObject o:objects) {
			out.print('\t');
			out.print(o.getClass().getSimpleName());
			out.println(':');
			Field[] fields=o.getClass().getFields();
			for(Field field:fields) {
				if(field.getName().equals("type")) continue;
				out.print("\t\t");
				out.print(field.getName());
				out.print("\t=\t");
				out.println(field.get(o));
			}
		}
	}
	
	public ConstantPool clone() {
		ConstantPool clone=new ConstantPool();
		try {
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			export(new DataOutputStream(baos));
			baos.close();
			ByteArrayInputStream bais=new ByteArrayInputStream(baos.toByteArray());
			clone.read(new DataInputStream(bais));
			bais.close();
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		return clone;
	}
	
}
