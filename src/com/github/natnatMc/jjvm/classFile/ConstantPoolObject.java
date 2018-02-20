package com.github.natnatMc.jjvm.classFile;

import java.io.*;

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

public abstract class ConstantPoolObject {
	public abstract void export(DataOutputStream out) throws IOException;
	public abstract void read(DataInputStream in) throws IOException;
	public int type;
	
	public static ConstantPoolObject get(int type) {
		switch(type) {
			case 1:
				return new CONSTANT_Utf8_info();
			case 3:
				return new CONSTANT_Integer_info();
			case 4:
				return new CONSTANT_Float_info();
			case 5:
				return new CONSTANT_Long_info();
			case 6:
				return new CONSTANT_Double_info();
			case 7:
				return new CONSTANT_Class_info();
			case 8:
				return new CONSTANT_String_info();
			case 9:
				return new CONSTANT_Fieldref_info();
			case 10:
				return new CONSTANT_Methodref_info();
			case 11:
				return new CONSTANT_InterfaceMethodref_info();
			case 12:
				return new CONSTANT_NameAndType_info();
		}
		return null;
	}
}
