package com.github.natnatMc.jjvm.intermediate;

import com.github.natnatMc.jjvm.classFile.ClassAttribute;
import com.github.natnatMc.jjvm.classFile.ClassField;
import com.github.natnatMc.jjvm.classFile.ConstantPool;
import com.github.natnatMc.jjvm.classFile.ConstantPoolObject;
import com.github.natnatMc.jjvm.exceptions.MalformedClassException;
import com.github.natnatMc.jjvm.struct.CONSTANT_Double_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_Float_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_Integer_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_Long_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_String_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_Utf8_info;
import com.github.natnatMc.jjvm.struct.IntHolder;

public class JField {
	
	protected int flags;
	
	protected String name;
	protected String type;
	
	protected Object constant;
	
	protected boolean ro;
	
	void read(ClassField field, ConstantPool pool) throws MalformedClassException {
		flags=field.flags;
		name=field.name;
		type=JMethod.readType(new IntHolder(), field.descriptor);
		
		for(ClassAttribute attr:field.attributes) {
			if(attr.name.equals("ConstantValue")) {
				int pos=attr.data.getChar(0);
				ConstantPoolObject obj=pool.get(pos);
				if(obj instanceof CONSTANT_Integer_info) {
					constant=((CONSTANT_Integer_info) obj).bytes;
				} else if(obj instanceof CONSTANT_Float_info) {
					constant=((CONSTANT_Float_info) obj).bytes;
				} else if(obj instanceof CONSTANT_Double_info) {
					constant=((CONSTANT_Double_info) obj).bytes;
				} else if(obj instanceof CONSTANT_Long_info) {
					constant=((CONSTANT_Long_info) obj).bytes;
				} else if(obj instanceof CONSTANT_String_info) {
					pos=((CONSTANT_String_info) obj).stringIndex;
					CONSTANT_Utf8_info utf8=(CONSTANT_Utf8_info) pool.get(pos);
					constant=utf8.value;
				} else {
					throw new MalformedClassException("illegal constant type "+obj.type);
				}
			}
		}
		ro=true;
	}
	
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public Object getConstantValue() {
		return constant;
	}
	public int getFlags() {
		return flags;
	}
	
	public void setName(String name) {
		if(ro) throw new IllegalStateException("Cannot modify ro class");
		this.name=name;
	}
	public void setType(String type) {
		if(ro) throw new IllegalStateException("Cannot modify ro class");
		this.type=type;
	}
	public void setConstantValue(Object obj) {
		if(ro) throw new IllegalStateException("Cannot modify ro class");
		this.constant=obj;
	}
	public void setFlags(int flags) {
		this.flags=flags;
	}
	
}
