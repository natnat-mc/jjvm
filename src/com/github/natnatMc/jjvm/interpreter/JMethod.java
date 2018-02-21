package com.github.natnatMc.jjvm.interpreter;

import java.util.*;

import com.github.natnatMc.jjvm.classFile.ClassAttribute;
import com.github.natnatMc.jjvm.classFile.ClassMethod;
import com.github.natnatMc.jjvm.classFile.ConstantPool;
import com.github.natnatMc.jjvm.exceptions.MalformedClassException;
import com.github.natnatMc.jjvm.struct.CONSTANT_Class_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_Utf8_info;
import com.github.natnatMc.jjvm.types.IntHolder;

public class JMethod {
	
	protected int flags;
	
	protected JCode code;
	protected String[] exceptions;
	
	protected String name;
	protected String[] parameterTypes;
	protected String returnType;
	
	void read(ClassMethod method, ConstantPool pool) throws MalformedClassException {
		//set method name
		name=method.name;
		
		//set flags
		flags=method.flags;
		
		//read method parameters and return types
		ArrayList<String> params=new ArrayList<String>();
		String desc=method.descriptor;
		IntHolder pos=new IntHolder();
		pos.value=1;
		while(desc.charAt(pos.value)!=')') {
			params.add(readType(pos, desc));
		}
		pos.value++;
		if(desc.charAt(pos.value)=='V') returnType="void";
		else returnType=readType(pos, desc);
		parameterTypes=params.toArray(new String[0]);
		
		//read attributes
		for(ClassAttribute attr:method.attributes) {
			if(attr.name.equals("Code")) {
				code=new JCode();
				code.read(attr, pool);
			} else if(attr.name.equals("Exceptions")) {
				attr.data.position(0);
				int no=attr.data.getChar();
				exceptions=new String[no];
				for(int i=0; i<no; i++) {
					int p=attr.data.getChar();
					CONSTANT_Class_info info=(CONSTANT_Class_info) pool.get(p);
					CONSTANT_Utf8_info utf8=(CONSTANT_Utf8_info) pool.get(info.pos);
					exceptions[i]=utf8.value.replaceAll("/", ".");
				}
			}
		}
	}
	
	protected static String readType(IntHolder pos, String desc) throws MalformedClassException {
		char c=desc.charAt(pos.value++);
		switch(c) {
			case 'B':
				return "boolean";
			case 'C':
				return "char";
			case 'D':
				return "double";
			case 'F':
				return "float";
			case 'I':
				return "int";
			case 'J':
				return "long";
			case 'S':
				return "short";
			case 'Z':
				return "boolean";
			case '[':
				return readType(pos, desc)+"[]";
		}
		if(c=='L') {
			String name="";
			while(pos.value<desc.length()) {
				c=desc.charAt(pos.value++);
				if(c==';') break;
				if(c=='/') name+='.';
				else name+=c;
			}
			return name;
		} else {
			throw new MalformedClassException("unknown descriptor found :"+c);
		}
	}
}
