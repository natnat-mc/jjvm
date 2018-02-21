package com.github.natnatMc.jjvm.interpreter;

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
import com.github.natnatMc.jjvm.types.IntHolder;
import com.github.natnatMc.jjvm.types.JDecimal;
import com.github.natnatMc.jjvm.types.JInteger;
import com.github.natnatMc.jjvm.types.JObject;
import com.github.natnatMc.jjvm.types.JString;

public class JField {
	
	protected int flags;
	
	protected String name;
	protected String type;
	
	protected JObject constant;
	
	void read(ClassField field, ConstantPool pool) throws MalformedClassException {
		flags=field.flags;
		name=field.name;
		type=JMethod.readType(new IntHolder(), field.descriptor);
		
		for(ClassAttribute attr:field.attributes) {
			if(attr.name.equals("ConstantValue")) {
				int pos=attr.data.getChar(0);
				ConstantPoolObject obj=pool.get(pos);
				if(obj instanceof CONSTANT_Integer_info) {
					constant=new JInteger(((CONSTANT_Integer_info) obj).bytes);
				} else if(obj instanceof CONSTANT_Float_info) {
					constant=new JDecimal(((CONSTANT_Float_info) obj).bytes);
				} else if(obj instanceof CONSTANT_Double_info) {
					constant=new JDecimal(((CONSTANT_Double_info) obj).bytes);
				} else if(obj instanceof CONSTANT_Long_info) {
					constant=new JInteger(((CONSTANT_Long_info) obj).bytes);
				} else if(obj instanceof CONSTANT_String_info) {
					pos=((CONSTANT_String_info) obj).stringIndex;
					CONSTANT_Utf8_info utf8=(CONSTANT_Utf8_info) pool.get(pos);
					constant=new JString(utf8.value);
				} else {
					throw new MalformedClassException("illegal constant type");
				}
			}
		}
	}
	
}
