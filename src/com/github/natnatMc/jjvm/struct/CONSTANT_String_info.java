package com.github.natnatMc.jjvm.struct;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.github.natnatMc.jjvm.classFile.ConstantPoolObject;

public class CONSTANT_String_info extends ConstantPoolObject {
	
	{
		this.type=8;
	}
	
	public int stringIndex;
	
	@Override
	public boolean equals(Object other) {
		if(other.getClass()!=this.getClass()) return false;
		CONSTANT_String_info o=(CONSTANT_String_info) other;
		return o.stringIndex==this.stringIndex;
	}

	@Override
	public void export(DataOutputStream out) throws IOException {
		out.writeByte(type);
		out.writeChar(stringIndex);
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		stringIndex=in.readUnsignedShort();
	}
	
}
